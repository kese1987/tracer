package com.shareshipping.utils.tracer;

import java.util.concurrent.LinkedBlockingDeque;

public abstract interface ITracer {

	public abstract void setComponent(String component);

	public abstract ITracer key(String key);

	public abstract void setBuffer(LinkedBlockingDeque<String> buffer);

	public abstract ITracer INFO();

	public abstract ITracer ERROR();

	public abstract ITracer WARNING();

	public abstract ITracer VERBOSE();

	public abstract ITracer DEBUG();

	public abstract ITracer token(String key, String value);

	public abstract ITracer token(String value, String... values);

	public abstract void end();

	public abstract void setThreadId(long id);

}
