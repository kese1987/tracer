package com.shareshipping.utils.tracer;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.shareshipping.utils.tracer.impl.TracerFactory;
import com.shareshipping.utils.tracer.impl.TracerProvider;
import com.shareshipping.utils.tracer.impl.writer.FileWriter;

public class TracerModule extends AbstractModule {

	@Override
	protected void configure() {

		bind(ILogWriter.class).to(FileWriter.class).in(Scopes.SINGLETON);

		bind(ITracerFactory.class).to(TracerFactory.class).in(Scopes.SINGLETON);
		bind(ITracerProvider.class).to(TracerProvider.class).in(Scopes.SINGLETON);

		bind(ITracer.class).toProvider(ITracerProvider.class).in(Scopes.SINGLETON);

	}

}
