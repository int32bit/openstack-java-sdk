package bupt.openstack.common.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class IOConverter {
	private static Logger logger = LogManager.getLogger(IOConverter.class);

	public static void inputStreamToFile(InputStream in, File file)
			throws IOException {
		FileOutputStream fStream = new FileOutputStream(file);
		FileChannel out = fStream.getChannel();
		ReadableByteChannel input = Channels.newChannel(in);
		long offset = 0L;
		long quantum = 1048576L;
		long count;
		while ((count = out.transferFrom(input, offset, quantum)) > 0L) {
			offset += count;
		}
		input.close();
		out.close();
		fStream.close();
		in.close();
		logger.info("Write file '" + file.getAbsolutePath() + "' OK!");
	}

	public static void inputStreamToFile(InputStream in, String fileName)
			throws IOException {
		inputStreamToFile(in, new File(fileName));
	}

	public static void fileToOutputStream(File file, OutputStream out)
			throws IOException {
		WritableByteChannel output = Channels.newChannel(out);
		FileInputStream fStream = new FileInputStream(file);
		FileChannel input = fStream.getChannel();
		long offset = 0L;
		long quatum = 1048576L;
		long count;
		while ((count = input.transferTo(offset, quatum, output)) > 0L) {
			offset += count;
		}
		input.close();
		fStream.close();
		output.close();
		logger.info("Read from '" + file.getAbsolutePath() + "' Ok!");
	}

	public static void fileToOutputStream(String fileName, OutputStream out)
			throws IOException {
		fileToOutputStream(new File(fileName), out);
	}

	public static void copyFile(File src, File dest) {
	}
}