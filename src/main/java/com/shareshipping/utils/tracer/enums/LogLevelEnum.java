package com.shareshipping.utils.tracer.enums;

import java.util.HashMap;

import com.google.common.collect.Maps;

public enum LogLevelEnum {
	VERBOSE("VERBOSE", 5), DEBUG("DEBUG", 4), INFO("INFO", 3), WARNING("WARN", 2), ERROR("ERROR", 1), OFF("OFF",
			0), UNDEF("UNDEF", -1);

	private final static HashMap<String, LogLevelEnum> stringValues = Maps.newHashMap();
	private final static HashMap<Integer, LogLevelEnum> intValues = Maps.newHashMap();

	private final String stringValue;
	private final int intValue;

	static {
		for (LogLevelEnum l : LogLevelEnum.values()) {
			stringValues.put(l.stringValue, l);
			intValues.put(l.intValue, l);
		}
	}

	LogLevelEnum(String key, int value) {
		stringValue = key;
		intValue = value;
	}

	public static LogLevelEnum fromStringValue(String key) {
		return stringValues.get(key);
	}

	public static LogLevelEnum fromIntValue(int value) {
		return intValues.get(value);
	}

	public String getStringValue() {
		return stringValue;
	}

	public int getIntegerValue() {
		return intValue;
	}

}
