package com.shareshipping.utils.tracer.enums;

import java.util.HashMap;

import com.google.common.collect.Maps;

public enum FileSizeEnum {

	Gb("Gb", 1024 * 1024 * 1024), Mb("Mb", 1024 * 1024), Kb("Kb", 1024), B("B", 1);

	private final static HashMap<String, FileSizeEnum> stringValues = Maps.newHashMap();
	private final static HashMap<Integer, FileSizeEnum> intValues = Maps.newHashMap();

	private final String stringValue;
	private final int intValue;

	static {
		for (FileSizeEnum l : FileSizeEnum.values()) {
			stringValues.put(l.stringValue, l);
			intValues.put(l.intValue, l);
		}
	}

	FileSizeEnum(String key, int value) {
		stringValue = key;
		intValue = value;
	}

	public static FileSizeEnum fromStringValue(String key) {
		return stringValues.get(key);
	}

	public static FileSizeEnum fromIntValue(int value) {
		return intValues.get(value);
	}

	public String getStringValue() {
		return stringValue;
	}

	public int getIntegerValue() {
		return intValue;
	}

}
