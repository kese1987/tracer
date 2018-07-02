package com.shareshipping.utils.tracer.impl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

import com.google.common.collect.Queues;
import com.google.inject.Inject;
import com.shareshipping.utils.tracer.ILogWriter;
import com.shareshipping.utils.tracer.ITracer;
import com.shareshipping.utils.tracer.ITracerFactory;
import com.shareshipping.utils.tracer.ITracerProvider;

public class TracerFactory implements ITracerFactory {

	private LinkedBlockingDeque<String> buffer;
	private ExecutorService logWriter;
	private final ITracerProvider tracerProvider;
	private static boolean shutdownRequest = false;

	@Inject
	public TracerFactory(ITracerProvider tracerProvider, ILogWriter writer) {

		this.tracerProvider = tracerProvider;
		buffer = Queues.newLinkedBlockingDeque();
		logWriter = Executors.newSingleThreadExecutor();

		Runnable writeLog = new Runnable() {

			@Override
			public void run() {
				while (true) {
					try {

						String line = buffer.poll(200, TimeUnit.MILLISECONDS);

						if (line != null) {
							writer.write(line);
						}

						if (buffer.size() == 0 && shutdownRequest) {
							writer.shutdown();
							break;
						}
					} catch (InterruptedException e) {
						break;
					}
				}
			}
		};

		logWriter.execute(writeLog);

	}

	@Override
	public ITracer create(String component, String key) {

		ITracer tracer = tracerProvider.get();
		tracer.setComponent(component);
		tracer.key(key);
		return tracer;
	}

	@Override
	public void shutdown() {
		shutdownRequest = true;
		logWriter.shutdown();
		try {
			logWriter.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public LinkedBlockingDeque<String> getBuffer() {
		return buffer;
	}

	@Override
	public ITracer create(String component) {
		ITracer tracer = tracerProvider.get();
		tracer.setComponent(component);
		return tracer;
	}

}
