package com.shareshipping.utils.tracer.impl;

import com.google.inject.Inject;
import com.shareshipping.utils.settingsManager.ISettingsManager;
import com.shareshipping.utils.tracer.ITracer;
import com.shareshipping.utils.tracer.ITracerFactory;
import com.shareshipping.utils.tracer.ITracerProvider;

public class TracerProvider implements ITracerProvider {

	private final ITracerFactory tracerFactory;
	private final ISettingsManager configManager;

	@Inject
	public TracerProvider(ITracerFactory tracerFactory, ISettingsManager configManager) {
		this.tracerFactory = tracerFactory;
		this.configManager = configManager;
	}

	@Override
	public ITracer get() {
		ITracer tracer = new Tracer(configManager);
		tracer.setBuffer(tracerFactory.getBuffer());

		return tracer;
	}

}
