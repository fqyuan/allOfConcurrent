package com.fqy.pinyin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

//https://github.com/BlankKelly/Pinyin
public class Pinyin {
	private static HashMap<String, String> PINYIN = new HashMap<>();
	// Singleton Design Pattern 单例设计模式
	private static Pinyin pinyin;

	static {
		try {
			PINYIN = pinyinToDic(new File("/Users/fqyuan/Desktop/pinyin.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Pinyin() {
	}

	public static Pinyin instance() {
		if (pinyin == null) {
			pinyin = new Pinyin();
		}
		return pinyin;
	}

	/**
	 * 拼音文件转成字典
	 *
	 * @param file
	 *            拼音文件
	 * @return 字典
	 * @throws IOException
	 */
	private static HashMap<String, String> pinyinToDic(File file) throws IOException {
		// File: An abstract representation of file and directory pathnames.
		// FileInputStream: Creates a FileInputStream by opening a connection to
		// an actual file, the file named by the File object file in the file
		// system. A new FileDescriptor object is created to represent this file
		// connection.
		// InputStreamReader: Creates an InputStreamReader that uses the default
		// charset.
		// BufferedReader: Reads text from a character-input stream, buffering
		// characters so as to provide for the efficient reading of characters,
		// arrays, and lines.
		InputStreamReader isr = new InputStreamReader(new FileInputStream(file));
		BufferedReader br = new BufferedReader(isr);
		HashMap<String, String> result = new HashMap<>();
		String line = "";

		while ((line = br.readLine()) != null) {
			String[] ret = line.split("=");
			result.put(ret[0].replace('\'', ' ').replace(',', ' ').trim(),
					ret[1].replace('\'', ' ').replace(',', ' ').trim());
		}
		br.close();
		return result;
	}

	/**
	 * 将字符串转换成单个字组成的数组
	 *
	 * @param str
	 *            字符串
	 * @return 单个字符串组成的数组
	 */
	private static String[] stringToArray(String str) {
		int len = str.length();
		String[] ret = new String[len];

		for (int i = 0; i < len; i++) {
			ret[i] = str.substring(i, i + 1);
		}

		return ret;
	}

	/**
	 * 单个字符转成拼音
	 *
	 * @param str
	 *            单个字符
	 * @return 字符对应的拼音（若存在对应的拼音，西文字符保持不变）
	 */
	private static String chineseToPin(String str) {
		String ret = "";

		if (PINYIN.containsKey(str)) {
			ret = PINYIN.get(str);
		} else {
			ret = str;
		}

		return ret;
	}

	/**
	 * 汉字转拼音
	 *
	 * @param str
	 *            汉字
	 * @return 拼音
	 */
	public static String getPinyin(String str) {
		String[] strArr = stringToArray(str);
		StringBuilder ret = new StringBuilder();

		for (int i = 0; i < strArr.length; i++) {
			ret.append(chineseToPin(strArr[i]));
			ret.append(" ");
		}

		return ret.toString();
	}

	/**
	 * 汉字转短拼音
	 *
	 * @param str
	 *            汉字
	 * @return 短拼音
	 */
	public static String getShortString(String str) {
		String[] strArr = stringToArray(str);
		StringBuilder ret = new StringBuilder();

		for (int i = 0; i < strArr.length; i++) {
			ret.append(chineseToPin(strArr[i]).substring(0, 1));
			ret.append(" ");
		}

		return ret.toString();
	}

	public static void main(String[] args) {
		String s = "中华人民共和国abc";
		String[] result = stringToArray(s);
		System.out.println(chineseToPin("早"));
		System.out.println(getPinyin(s));
		System.out.println(getShortString(s));
		System.out.println(PINYIN.size());
	}

}
