package com.shareshipping.utils.tracer;

import java.util.concurrent.LinkedBlockingDeque;

public interface ITracerFactory {
	public abstract ITracer create(String component, String key);

	public abstract ITracer create(String component);

	public abstract LinkedBlockingDeque<String> getBuffer();

	public abstract void shutdown();
}
