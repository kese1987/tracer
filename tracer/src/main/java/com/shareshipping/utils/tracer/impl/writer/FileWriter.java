package com.shareshipping.utils.tracer.impl.writer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.inject.Inject;
import com.shareshipping.utils.settingsManager.ISettingsManager;
import com.shareshipping.utils.settingsManager.impl.Utils;
import com.shareshipping.utils.tracer.ILogWriter;
import com.shareshipping.utils.tracer.enums.FileSizeEnum;

public class FileWriter implements ILogWriter {

	private String logBaseName;
	private final int logRotate;
	private final String logPath;
	private java.io.FileWriter fw;
	private BufferedWriter bw;
	private PrintWriter out;
	private int maxSize;
	private int logNumber = 0;
	private String logName;
	private long byteWritten = 0;

	@Inject
	public FileWriter(ISettingsManager configManager) {

		logBaseName = configManager.getStringProperty("logName", "log.txt");
		logRotate = configManager.getIntProperty("logRotate", 1);
		String logRotateSize = configManager.getStringProperty("logRotateSize", "1Gb");
		logPath = Utils.getResourcePath("LOGS");

		Pattern p = Pattern.compile("\\s*(\\d+)\\s*(\\w+)\\s*");
		Matcher m = p.matcher(logRotateSize);

		initLogNumber();

		logName = logBaseName + ((logNumber > 0) ? "." + String.valueOf(logNumber) : "");

		if (m.matches()) {

			FileSizeEnum unit = FileSizeEnum.fromStringValue(m.group(2));
			if (unit != null) {
				maxSize = Integer.parseInt(m.group(1)) * unit.getIntegerValue();
			} else {
				maxSize = FileSizeEnum.Gb.getIntegerValue();
			}

		} else {
			maxSize = FileSizeEnum.Gb.getIntegerValue();
		}

		openFile();
	}

	private void initLogNumber() {

		Pattern p = Pattern.compile(".*(\\d+)");
		Matcher m;

		File[] files = Utils.listFiles(logPath, new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.matches(".*(\\d+)");
			}
		});

		for (File file : files) {
			m = p.matcher(file.getName());
			if (m.matches()) {
				if (Integer.parseInt(m.group(1)) >= logNumber) {
					logNumber++;
				}
			}
		}

	}

	@Override
	public void write(String line) {

		if (logRotate == 1 && byteWritten > maxSize) {

			byteWritten = 0;
			out.close();
			logNumber++;
			logName = logBaseName + ((logNumber > 0) ? "." + String.valueOf(logNumber) : "");
			openFile();

		}
		out.println(line);
		byteWritten += line.length();

	}

	private void openFile() {

		try {

			fw = new java.io.FileWriter(logPath + "/" + logName, true);
			bw = new BufferedWriter(fw);
			out = new PrintWriter(bw);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void shutdown() {
		out.flush();
		out.close();
	}

}
