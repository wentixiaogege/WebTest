package com.itu.jetty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import javax.sound.midi.ControllerEventListener;

public class ThreadClass extends Thread implements Controller.IAciton {
	ArrayList<String> arrs = new ArrayList<String>();

	public synchronized void add(String s) {
		arrs.add(s);
	}

	public synchronized void print() {
		for (String string : arrs) {
			System.out.print(string + " ");
		}
		System.out.println();
	}

	public ThreadClass() {
		add("aa");
		add("bb");
	}

	@Override
	public void run() {
		while (true) {
			print();
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void doAction(String param) {
		add(param);
	}
}
