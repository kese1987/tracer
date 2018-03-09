package com.shareshipping.utils.tracer.impl.writer;

import com.shareshipping.utils.tracer.ILogWriter;

public class ConsoleWriter implements ILogWriter {

	@Override
	public void write(String line) {
		System.out.println(line);

	}

	@Override
	public void shutdown() {
	}

}
