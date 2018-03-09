package com.shareshipping.utils.tracer.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.LinkedBlockingDeque;

import org.apache.commons.lang3.StringUtils;

import com.shareshipping.utils.settingsManager.ISettingsManager;
import com.shareshipping.utils.tracer.ITracer;
import com.shareshipping.utils.tracer.enums.LogLevelEnum;

public class Tracer implements ITracer {

	private String component;
	private String key;
	private LinkedBlockingDeque<String> buffer;
	private volatile static long lineNr = 0;
	private final StringBuilder strBuilder;

	private int currentLogLevel;
	private final int configLogLevel;
	private long threadId;

	public Tracer(ISettingsManager config) {

		strBuilder = new StringBuilder();
		configLogLevel = LogLevelEnum
				.fromStringValue(config.getStringProperty("logLevel", LogLevelEnum.INFO.getStringValue()))
				.getIntegerValue();

	}

	@Override
	public void setComponent(String component) {
		this.component = component;
	}

	@Override
	public ITracer key(String key) {
		this.key = key;
		return this;
	}

	@Override
	public void setBuffer(LinkedBlockingDeque<String> buffer) {
		this.buffer = buffer;
	}

	private void buildHeader(String error) {
		if (currentLogLevel < configLogLevel) {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = new Date();
			strBuilder.append(dateFormat.format(date) + "\t|\t" + lineNr + "\t|\t").append(threadId + "\t|\t")
					.append(error + "\t|\t").append(component).append(":").append(key).append("\t|\t");
		}
	}

	@Override
	public ITracer INFO() {
		currentLogLevel = LogLevelEnum.INFO.getIntegerValue();
		buildHeader(LogLevelEnum.INFO.getStringValue());
		return this;
	}

	@Override
	public ITracer token(String key, String value) {
		if (currentLogLevel < configLogLevel) {
			strBuilder.append(" {" + key + ":" + value + "} ");
		}
		return this;
	}

	@Override
	public void end() {
		if (currentLogLevel < configLogLevel) {
			lineNr++;
			String line = strBuilder.toString();
			if (StringUtils.isNotBlank(line)) {
				buffer.add(line);
				strBuilder.delete(0, line.length());
			}
		}
	}

	@Override
	public ITracer token(String value, String... values) {

		if (currentLogLevel < configLogLevel) {
			strBuilder.append(" " + value + " ");

			for (String s : values) {
				strBuilder.append(" " + s + " ");
			}
		}
		return this;
	}

	@Override
	public ITracer ERROR() {
		currentLogLevel = LogLevelEnum.ERROR.getIntegerValue();
		buildHeader(LogLevelEnum.ERROR.getStringValue());
		return this;
	}

	@Override
	public ITracer WARNING() {
		currentLogLevel = LogLevelEnum.WARNING.getIntegerValue();
		buildHeader(LogLevelEnum.WARNING.getStringValue());
		return this;
	}

	@Override
	public ITracer VERBOSE() {
		currentLogLevel = LogLevelEnum.VERBOSE.getIntegerValue();
		buildHeader(LogLevelEnum.VERBOSE.getStringValue());
		return this;
	}

	@Override
	public ITracer DEBUG() {
		currentLogLevel = LogLevelEnum.DEBUG.getIntegerValue();
		buildHeader(LogLevelEnum.DEBUG.getStringValue());
		return this;
	}

	@Override
	public void setThreadId(long threadId) {
		this.threadId = threadId;

	}

}
