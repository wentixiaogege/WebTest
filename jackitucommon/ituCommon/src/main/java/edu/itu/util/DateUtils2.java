package edu.itu.util;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public final class DateUtils2 {

	// some constant string
	public static final String FOMAT_DATE = "yyyy-MM-dd HH:mm:ss";
	public static final String FOMAT_DATE2 = "MM/dd/yyyy";
	public static final String FOMAT_DATE3 = "yyyy-MM-dd";
	


	/**
	 * get local date time
	 * 
	 * @return
	 */
	public static LocalDateTime getDateTimeNow() {
		return LocalDateTime.now();
	}

	public static LocalDate getDateNow() {
		return LocalDate.now();
	}

	public static LocalTime getTimeNow() {
		return LocalTime.now();
	}

	/**
	 * to unix timestamp, from localDatetime
	 * @param ldt
	 * @return
	 */
	public static long toUnixTime(LocalDateTime ldt) {
		return ldt.atZone(ZoneId.systemDefault()).toInstant().getEpochSecond();
	}

	/**
	 * convert localdate to unixtimestamp
	 * 
	 * @param ld
	 * @return
	 */
	public static long toUnixTime(LocalDate ld) {
		return toUnixTime(LocalDateTime.of(ld, LocalTime.MIN));
	}

	public static long toUnixTime(String dtStr, String format) {
		LocalDateTime dateTime = LocalDateTime.parse(dtStr, DateTimeFormatter.ofPattern(format));
		return toUnixTime(dateTime);
	}

	public static long toUnixTimeDate(String dtStr, String format) {
		LocalDate date = LocalDate.parse(dtStr, DateTimeFormatter.ofPattern(format));
		return toUnixTime(date);
	}

	public static long toUnixTimeDate(String dtStr) {
		LocalDate date = LocalDate.parse(dtStr);
		return toUnixTime(date);
	}
	/**
	 * 以Unixtime的形式返回今天日期
	 * 
	 * @return
	 */
	public static long toUnixTimeToday() {
		return toUnixTime(LocalDate.now());
	}

	/**
	 * return today with yyyy-MM-dd
	 * 
	 * @return
	 */
	public static String getTodaySimplestr() {
		return LocalDate.now().toString();
	}


	/**
	 * 把Unix时间戳转化为java日期
	 * 
	 * @param uTime
	 * @return
	 */
	public static LocalDateTime fromUnixTime(long uTime) {
		Instant fromUnixTimestamp = Instant.ofEpochSecond(uTime);
		return LocalDateTime.ofInstant(fromUnixTimestamp, ZoneId.systemDefault());
	}

	/**
	 * 把Unix时间戳转化为java日期，并格式化为yyyy-MM-dd HH:mm:ss的形式。
	 * 
	 * @param uTime
	 * @return
	 */
	public static String fromUnixTimeStr(long day) {
		return fromUnixTimeStr(day, FOMAT_DATE);
	}

	/**
	 * 把Unix时间戳转化为java日期，并格式化为yyyy-MM-dd HH:mm:ss的形式。
	 * 
	 * @param uTime
	 * @return
	 */
	public static String fromUnixTimeStr(long day, String format) {
		Instant fromUnixTimestamp = Instant.ofEpochSecond(day);
		LocalDateTime ldt = LocalDateTime.ofInstant(fromUnixTimestamp, ZoneId.systemDefault());
		return ldt.format(DateTimeFormatter.ofPattern(format));
	}


}
