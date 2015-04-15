package edu.itu.util;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import org.apache.commons.lang3.mutable.MutableBoolean;
import org.apache.log4j.Logger;

import com.google.protobuf.MessageOrBuilder;

public class ClassDeepCopy {
	static Logger logger = Log4jUtil.getLogger(ClassDeepCopy.class);
    
	/**
	 * copy bean to google protobuff.
	 * 
	 * @param bean
	 *            the bean to be copied
	 * @param proto
	 *            the proto to be filled in
	 * @param exceptions
	 * @return true copy right false copy failure.
	 */
	public static <T extends MessageOrBuilder> boolean CopyBeanToProto(Object bean, T proto, String... exceptions) {
		Class<? extends Object> beanClass = bean.getClass();
		Class<? extends Object> protoClass = proto.getClass();
		MutableBoolean flag = new MutableBoolean(true);

		// loop methods witch starts with "get"
		Arrays.stream(beanClass.getDeclaredMethods())
				.filter(x -> x.getName().startsWith("get"))
				.forEach(beanGetMethod -> {
					String getMsgName = beanGetMethod.getName();
					logger.debug(String.format("getMsdName:%s", getMsgName));

					// if method included in exceptions, ignoring it.
						String mMethod = getMsgName.substring(3);
						logger.debug(String.format("field Name:%s", mMethod));
						if (Arrays.stream(exceptions).anyMatch(y -> y.toLowerCase().equals(mMethod.toLowerCase()))) {
							logger.info(String.format("Field %s is aborted..\n", mMethod));
							return;
						}

						// init set method, it's tail must the same as get
						// method.
						String setMsgName = "set" + mMethod;
						logger.debug(String.format("setMsgName :%s", setMsgName));

						Optional<Method> opMethod = Arrays.stream(protoClass.getMethods()).filter(y -> y.getName().equals(setMsgName)).findFirst();
						Method setMethod;

						try {
							if (opMethod.isPresent()) {
								setMethod = opMethod.get();
								Object beanVal = beanGetMethod.invoke(bean);
								Object value = beanVal;
								if (value != null) {
									// 1. we need do specail for BigDecimal
									if (beanVal.getClass().equals(BigDecimal.class)) {
										logger.debug("we need do specail for BigDecimal," + beanVal.getClass().getName() + " "
												+ setMethod.getParameterTypes()[0].getName());
										if (setMethod.getParameterTypes()[0].equals(float.class)) {
											logger.debug("convert value to bigdecimal");
											value = ((BigDecimal) beanVal).floatValue();
										}
									}

									// logger.debug("beanVal.getClass():" +
									// beanVal.getClass());
									// 2. do some special for timestamp
									if (beanVal.getClass().equals(Date.class) || beanVal.getClass().equals(java.sql.Timestamp.class)) {
										Date beanValnew = (Date) beanVal;
										logger.debug("this is a date");
										Class<?> setMethodParamType = setMethod.getParameterTypes()[0];
										DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
										logger.debug("setMethodParamType:" + setMethodParamType.getName());
										if (setMethodParamType.equals(String.class)) {
											logger.debug("val is string");
											try {
												value = df.format((Date) beanValnew);
											} catch (Exception e) {
												logger.debug(e.getMessage(), e);
											}
										} else if (setMethodParamType.equals(long.class)) {
											logger.debug("val is long");
											value = DateUtils.toUnixTime((Date) beanValnew);
										}
									}

									setMethod.invoke(proto, value);
									logger.debug(String.format("field %s set correct, valeu is %s", mMethod, value.toString()));
								} else {
									logger.info(String.format("field %s is null!", mMethod));
								}
							} else {
								logger.debug(String.format("setMsgName :%s is not present in dest", setMsgName));
							}
						} catch (Exception e) {
							logger.debug(String.format("method %s error: %s", mMethod, e.getMessage()));
							logger.error("failed", e);
							e.printStackTrace();
							flag.setFalse();
						}

						logger.debug("\n");
					});
		return flag.getValue();
		// return false;
	}

	/**
	 * copy gootle proto buffer to bean.
	 * 
	 * @param proto
	 *            the proto copied from
	 * @param bean
	 *            the bean copied to
	 * @param exceptions
	 * @return true copy right false copy failure.
	 */
	public static <T extends MessageOrBuilder> boolean CopyProtoToBean(T proto, Object bean, String... exceptions) {
		Class<? extends Object> beanClass = bean.getClass();
		Class<? extends Object> protoClass = proto.getClass();
		MutableBoolean flag = new MutableBoolean(true);
		// new big
		// loop methods witch starts with "get"
		Arrays.stream(beanClass.getDeclaredMethods())
				.filter(x -> x.getName().startsWith("set"))
				.forEach(beanSetMethod -> {
					String setMethodName = beanSetMethod.getName();
					logger.debug(String.format("setMethodName:%s", setMethodName));

					// if method included in exceptions, ignoring it.
						String mMethod = setMethodName.substring(3);
						logger.debug(String.format("field Name:%s", mMethod));
						if (Arrays.stream(exceptions).anyMatch(y -> y.toLowerCase().equals(mMethod.toLowerCase()))) {
							logger.info(String.format("Field %s is aborted..\n", mMethod));
							return;
						}

						// init set method, it's tail must the same as get
						// method.
						String getMethodName = "get" + mMethod;
						logger.debug(String.format("getMethodName :%s", getMethodName));

						Optional<Method> opProtoGetMethod = Arrays.stream(protoClass.getMethods()).filter(y -> y.getName().equals(getMethodName))
								.findFirst();
						Method protoGetMethod;
						try {
							if (opProtoGetMethod.isPresent()) {
								protoGetMethod = opProtoGetMethod.get();
								Object getMsdResult = protoGetMethod.invoke(proto);
								Object value = getMsdResult;
								if (value != null) {
									// 1. we need do specail for BigDecimal
									if (beanSetMethod.getParameterTypes()[0].equals(BigDecimal.class)) {
										// logger.debug("we need do specail for BigDecimal"
										// + val.getClass().getName());
										if (getMsdResult.getClass().equals(Float.class)) {
											// logger.debug("convert value to bigdecimal");
											value = new BigDecimal((float) getMsdResult).setScale(2, RoundingMode.HALF_UP);
										}
									}

									// 2. do some special for timestamp
									if (beanSetMethod.getParameterTypes()[0].equals(Date.class)) {
										logger.debug("this is a date");
										DateFormat format = new SimpleDateFormat(DateUtils.FOMAT_DATE);
										// logger.debug("getmsdresult name:"+getMsdResult.getClass().getName());
										if (getMsdResult.getClass().equals(String.class)) {
											logger.debug("val is string");
											try {
												value = format.parse((String) getMsdResult);
											} catch (Exception e) {
												logger.debug(e.getMessage(), e);
											}
										} else if (getMsdResult.getClass().equals(java.lang.Long.class)) {
											logger.debug("val is long");
											value = DateUtils.fromUnixTime((long) getMsdResult);
										}
									}

									logger.debug(String.format("%s invoked, value is %s", getMethodName, value));

									// logger.debug("length" +
									// x.getParameterTypes().length);
									// logger.debug(x.getParameterTypes()[0].equals(BigDecimal.class));
									beanSetMethod.invoke(bean, value);
									logger.debug(String.format("field %s set correct, valeu is %s", mMethod, value.toString()));
								} else {
									logger.info(String.format("field %s is null!", mMethod));
								}
							} else {
								logger.debug(String.format("getMethodName :%s is not present in dest", getMethodName));
							}
						} catch (Exception e) {
							logger.debug(String.format("method %s error: %s", mMethod, e.getMessage()));
							logger.error("failed", e);
							e.printStackTrace();
							flag.setFalse();
						}

						logger.debug("\n");
					});
		return flag.getValue();
		// return false;
	}

	public static boolean CopyObjects(Object source, Object dest, String... exceptions) {
		Class<? extends Object> c1 = source.getClass();
		Class<? extends Object> c2 = dest.getClass();
		MutableBoolean flag = new MutableBoolean(true);

		if (dest instanceof MessageOrBuilder) {
			logger.info("dest is message builder");
		} else if (source instanceof MessageOrBuilder) {
			logger.info("source is message builder");
		} else {
			logger.info("neither is message builder");
		}
		// loop methods witch starts with "get"
		Arrays.stream(c1.getDeclaredMethods()).filter(x -> x.getName().startsWith("get")).forEach(x -> {
			String getMsgName = x.getName();
			logger.debug(String.format("getMsdName:%s", getMsgName));

			// if method included in exceptions, ignoring it.
				String mMethod = getMsgName.substring(3);
				logger.debug(String.format("field Name:%s", mMethod));
				if (Arrays.stream(exceptions).anyMatch(y -> y.toLowerCase().equals(mMethod.toLowerCase()))) {
					logger.info(String.format("Field %s is aborted..\n", mMethod));
					return;
				}

				// init set method, it's tail must the same as get method.
				String setMsgName = "set" + mMethod;
				logger.debug(String.format("setMsgName :%s", setMsgName));

				Class<?> type;
				if (dest instanceof MessageOrBuilder) {
					type = getProtoType(x.getReturnType());
				} else if (source instanceof MessageOrBuilder) {
					type = getBeanType(x.getReturnType());
				} else {
					type = x.getReturnType();
				}
				try {
					Method m = c2.getMethod(setMsgName, type);
					Object value = x.invoke(source);
					if (value != null)
						m.invoke(dest, value);
					else
						logger.debug(String.format("%s is null!", mMethod));
				} catch (Exception e) {
					logger.debug(String.format("method %s error: %s", mMethod, e.getMessage()));
					logger.error("failed", e);
					e.printStackTrace();
					flag.setFalse();
				}

				logger.debug("\n");
			});
		return flag.getValue();
		// return false;
	}

	/**
	 * change Integer type to Primitive type.
	 * 
	 * @param type
	 * @return
	 */
	public static Class<?> getProtoType(Class<?> type) {
		Class<?> newtype;
		if (type.equals(Integer.class)) {
			newtype = Integer.TYPE;
		} else if (type.equals(Double.class)) {
			newtype = Double.TYPE;
		} else if (type.equals(Boolean.class)) {
			newtype = Boolean.TYPE;
		} else if (type.equals(Byte.class)) {
			newtype = Byte.TYPE;
		} else if (type.equals(Short.class)) {
			newtype = Short.TYPE;
		} else if (type.equals(Long.class)) {
			newtype = Long.TYPE;
		} else if (type.equals(Float.class)) {
			newtype = Float.TYPE;
		} else if (type.equals(Character.class)) {
			newtype = Character.TYPE;
		} else {
			newtype = type;
		}
		return newtype;
	}

	public static Class<?> getBeanType(Class<?> type) {
		Class<?> newtype;
		if (type.equals(Integer.TYPE)) {
			newtype = Integer.class;
		} else if (type.equals(Double.TYPE)) {
			newtype = Double.class;
		} else if (type.equals(Boolean.TYPE)) {
			newtype = Boolean.class;
		} else if (type.equals(Byte.TYPE)) {
			newtype = Byte.class;
		} else if (type.equals(Short.TYPE)) {
			newtype = Short.class;
		} else if (type.equals(Long.TYPE)) {
			newtype = Long.class;
		} else if (type.equals(Float.TYPE)) {
			newtype = Float.class;
		} else if (type.equals(Character.TYPE)) {
			newtype = Character.class;
		} else {
			newtype = type;
		}
		return newtype;
	}
}
