package com.xunpay.money.utils;

import java.util.Random;

public class RandomUtils {

	private static final char[] codeSequence = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
		'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
		'n', 'o', 'p', 'q', 'r', 's', 't', 'v', 'u', 'w', 'x', 'y', 'z',
		'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
		'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'V', 'U', 'W', 'X', 'Y', 'Z' };
	
	public static String randomNumber(int count) {
		StringBuilder result = new StringBuilder();
		Random rand = new Random();
		for (int i = 0; i < count; i++) {
			result.append(rand.nextInt(10));
		}
		return result.toString();
	}
	
	public static String randomString(int count) {
		StringBuilder result = new StringBuilder();
		Random rand = new Random();
		for (int i = 0; i < count; i++) {
			result.append(codeSequence[rand.nextInt(codeSequence.length)]);
		}
		return result.toString();
	}
	
	public static String randomString(char[] chs, int count) {
		StringBuilder result = new StringBuilder();
		Random rand = new Random();
		for (int i = 0; i < count; i++) {
			result.append(chs[rand.nextInt(chs.length)]);
		}
		return result.toString();
	}

	public static int nextInt(int min, int max) {
		return new Random().nextInt(max - min + 1) + min;
	}
	
	/**
	 * 命中率
	 * @param rate  概率，10% = 0.1
	 * @return true 命中， false 命中
	 */
	public static boolean hitRate(double rate) {
		if (rate >= 1) {
			return true;
		}
		if (rate <= 0) {
			return false;
		}
		Random rand = new Random();
		String rateStr = String.valueOf(rate);
		int max = 1;
		for (int i = 0; i < rateStr.length() - 2; i++) {
			max *= 10;
		}
		return rand.nextInt(max) < rate * max;
	}
	
	public static void main(String[] args) {
		double h = Double.parseDouble("0.1");
		int j = 1;
		for (int i = 0; i < 50000; i++) {
			if (hitRate(h)) {
				System.out.println(j++);
			}
		}
	}
	
}
