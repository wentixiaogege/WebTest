package com.itu.logic;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;

import com.itu.bean.SmartMeterData;
import com.itu.util.HibernateUtil;

import static java.lang.System.out;

public class HibernateDateTest {
	public static void main(String[] args) {

		org.hibernate.classic.Session session = HibernateUtil.getSessionFactory().openSession();

		session.beginTransaction();

		List list = session.createSQLQuery("SELECT * FROM emsdb.smart_meter_data limit 5;")
				.addEntity(SmartMeterData.class).list();
		
		list.forEach(x -> {
			SmartMeterData data = (SmartMeterData) x;
			Date timestamp = data.getTimestamp();
			out.println(timestamp);
			LocalDateTime ldt = LocalDateTime.ofInstant(timestamp.toInstant(), ZoneId.systemDefault());
			out.println(ldt);
			out.println();
		});

		// System.out.println("Maven + Hibernate + MySQL");
		// Session session = HibernateUtil.getSessionFactory().openSession();
		// // Hibernate:
		// session.beginTransaction();
		// Stock stock = new Stock();
		//
		// stock.setStockCode("4176");
		// stock.setStockName("PETER");
		//
		// session.save(stock);
		//
		// session.getTransaction().commit();
	}
}
