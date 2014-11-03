package bupt.openstack.common.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import bupt.openstack.common.conf.Configure;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.ConfigRepository;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.OpenSSHConfig;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.jcraft.jsch.SftpProgressMonitor;

public class RemoteExecutor implements AutoCloseable{
	private String host;
	private String username;
	private String password;
	private int port = 22;
	private ChannelSftp channelSftp = null;
	private Session session = null;
	private static int timeout = 5000;
	private int exitCode = -1;

	public RemoteExecutor(String host, String username, String password) {
		this(host, username, password, 22);
	}

	public RemoteExecutor(String host, String username,
			String password, int port) {
		this.host = host;
		this.username = username;
		this.password = password;
		this.port = port;
		try {
			open();
		} catch (JSchException e) {
			e.printStackTrace();
		}
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void open() throws JSchException {
		JSch jsch = new JSch();
		String conf = (String)Configure.getConfigure().getString("SshConfPath");
		if (new File(conf).exists()) {
			try {
				ConfigRepository configRepository = OpenSSHConfig.parseFile(conf);
				jsch.setConfigRepository(configRepository);
			}catch(IOException ignore) {
				
			}
		}
		session = jsch.getSession(this.username, this.host, this.port);
		session.setConfig("StrictHostKeyChecking", "no");
		session.setPassword(this.password);
		session.setTimeout(timeout);
		session.connect();
		channelSftp = (ChannelSftp) session.openChannel("sftp");
		channelSftp.connect();
	}
	@Override
	public void close() {
		if (session != null)
			session.disconnect();
		if (channelSftp != null)
			channelSftp.disconnect();
	}

	@Override
	public void finalize() {
		close();
	}

	public void put(String src, String dest, int mode) throws SftpException {
		File file = new File(src);
		channelSftp.put(src, dest, new Monitor(file.length()), mode);
	}

	public void put(String src, String dest) throws SftpException {
		put(src, dest, ChannelSftp.OVERWRITE);
	}

	public void get(String src, String dest) throws SftpException {
		File file = new File(src);
		channelSftp.get(src, dest, new Monitor(file.length()));

	}
	public String exec(String cmd, String input) throws JSchException,
			IOException {
		ChannelExec channel = (ChannelExec) session.openChannel("exec");
		channel.setCommand(cmd);
		channel.setInputStream(null);
		channel.setErrStream(System.err);
		channel.setOutputStream(System.out);
		InputStream in = channel.getInputStream();
		OutputStream out = channel.getOutputStream();
		channel.connect();
		if (input != null) {
			out.write(input.getBytes());
		}
		out.flush();
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		StringBuilder sb = new StringBuilder();
		String line;
		while (true) {
			while ((line = reader.readLine()) != null) {
				sb.append(line);
				sb.append('\n');
			}
			if (channel.isClosed()) {
				exitCode = channel.getExitStatus();
				break;
			}
		}
		channel.disconnect();
		return sb.toString();

	}

	public String exec(String cmd) throws JSchException, IOException {
		return exec(cmd, null);
	}

	public String sudoExec(String cmd, String input) throws JSchException,
			IOException {
		return exec("sudo -S -p '' " + cmd, password + "\n" + input);
	}

	public String sudoExec(String cmd) throws JSchException, IOException {
		return exec("sudo -S -p '' " + cmd, password + "\n");
	}

	public int getExitStatus() {
		return exitCode;
	}

	public ChannelShell getShell() throws JSchException {
		ChannelShell shell = (ChannelShell) session.openChannel("shell");
		shell.setInputStream(System.in);
		shell.setOutputStream(System.out);
		shell.connect();
		return shell;
	}

	private class Monitor extends TimerTask implements SftpProgressMonitor {
		private long progressInterval = 1000;
		private boolean isEnd = false;
		private long transfered;
		private long fileSize;
		private Timer timer;
		private boolean isScheduled = false;
		private Date startTime;
		private Date endTime;

		public Monitor(long fileSize) {
			this.fileSize = fileSize;
		}

		private boolean isEnd() {
			return isEnd;
		}

		private void setEnd(boolean end) {
			this.isEnd = end;
		}

		private long getTransfered() {
			return transfered;
		}

		@Override
		public void run() {
			if (!isEnd()) {
				long transfered = getTransfered();
				if (transfered != fileSize) {
					sendProgessMessage(transfered);
				} else {
					System.out.println("File transfering is done!");
					isEnd = true;
				}
			} else {
				stop();
				return;
			}
		}

		public void stop() {
			if (timer != null) {
				timer.cancel();
				timer.purge();
				timer = null;
				isScheduled = false;
			}
		}

		public void start() {
			if (timer == null)
				timer = new Timer();
			timer.schedule(this, 1000, progressInterval);
			isScheduled = true;
		}

		public void sendProgessMessage(long transfered) {
			if (fileSize != 0) {
				double d = ((double) transfered * 100) / (double) fileSize;
				DecimalFormat df = new DecimalFormat("#.##");
				System.out
						.printf("Currently transferred total size %d bytes, completed : %s%%\n",
								transfered, df.format(d));
			}
		}
		@Override
		public boolean count(long count) {
			if (isEnd())
				return false;
			if (!isScheduled) {
				start();
			}
			add(count);
			return true;
		}
		@Override
		public void end() {
			setEnd(true);
			endTime = new Date();
			long spendTime = (endTime.getTime() - startTime.getTime()) / 1000;
			double speed = (fileSize * 1.0 / spendTime * 1.0)
					/ (1024 * 1024 * 1.0);
			System.out.printf(
					"Transfer successfully, takes %d s, speed %.4f MB/s\n",
					spendTime, speed);
		}

		private synchronized void add(long count) {
			transfered += count;
		}
		@Override
		public void init(int op, String src, String dest, long fileSize) {
			String target = username + "@" + host + ":" + dest;
			System.out.printf("Transfer from %s to %s, file size : %d bytes\n",
					src, target, fileSize);
			startTime = new Date();
		}
	}
/*
	public static void main(String[] args) throws JSchException, IOException {
		try (RemoteExecutor remote = new RemoteExecutor("localhost", "fgp", "linuxfgp")) {
			System.out.println(remote.exec("ls"));
		}
	}
	*/
}
