package com.shareshipping.utils.tracer;

public interface ILogWriter {

	public abstract void write(String line);

	public abstract void shutdown();

}
