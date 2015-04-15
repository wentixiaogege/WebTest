package com.itu.dataserveraction;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.WeakHashMap;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import com.google.protobuf.Message;

//@Provider
//@Produces("application/x-protobuf")
//public class ProtobufMessageBodyWriter implements MessageBodyWriter<Object> {
//	/**
//	 * a cache to save the cost of duplicated call(getSize, writeTo) to one
//	 * object.
//	 */
//	private Map<Object, byte[]> buffer = new WeakHashMap<Object, byte[]>();
//
//	@Override
//	public boolean isWriteable(Class<?> type, Type genericType,
//			Annotation[] annotations, MediaType mediaType) {
//		// it will handle all model classes
//		return mediaType.toString().equals("application/x-protobuf");
//	}
//
//	@Override
//	public long getSize(Object message, Class<?> type, Type genericType,
//			Annotation[] annotations, MediaType mediaType) {
//		if (message instanceof CloudCommand) {
//			CloudCommand p = (CloudCommand) message;
//			byte[] bytes = p.toByteArray();
//			buffer.put(message, bytes);
//			return bytes.length;
//			// } else if (message instanceof AddressBook) {
//			// AddressBook p = (AddressBook) message;
//			// byte[] bytes = p.toByteArray();
//			// buffer.put(message, bytes);
//			// return bytes.length;
//		}
//		return 0;
//	}
//
//	@Override
//	public void writeTo(Object message, Class<?> type, Type genericType,
//			Annotation[] annotations, MediaType mediaType,
//			MultivaluedMap<String, Object> httpHeaders,
//			OutputStream entityStream) throws IOException,
//			WebApplicationException {
//		entityStream.write(buffer.remove(message));
//	}
//}

@Provider
@Produces("application/x-protobuf")
public class ProtobufMessageBodyWriter implements MessageBodyWriter<Message> {
    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return Message.class.isAssignableFrom(type);
    }

    private Map<Object, byte[]> buffer = new WeakHashMap<Object, byte[]>();

    public long getSize(Message m, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            m.writeTo(baos);
        } catch (IOException e) {
            return -1;
        }
        byte[] bytes = baos.toByteArray();
        buffer.put(m, bytes);
        return bytes.length;
    }

    public void writeTo(Message m, Class type, Type genericType, Annotation[] annotations, 
                MediaType mediaType, MultivaluedMap httpHeaders,
                OutputStream entityStream) throws IOException, WebApplicationException {
        entityStream.write(buffer.remove(m));
    }
}
