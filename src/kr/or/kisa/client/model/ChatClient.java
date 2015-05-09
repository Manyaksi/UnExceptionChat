package kr.or.kisa.client.model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

public class ChatClient {

	private String ip;
	private int port;
	private Socket socket;
	private DataOutputStream out;
	private DataInputStream in;

	public ChatClient() {
		this("127.0.0.1", 8888);
	}

	public ChatClient(String ip, int port) {
		this.ip = ip;
		this.port = port;
	}

	public String getIp() {
		return ip;
	}

	public int getPort() {
		return port;
	}

	public Socket getSocket() {
		return socket;
	}

	/**
	 * ä�ü��� ����
	 * 
	 * @throws IOException
	 * @throws UnknownHostException
	 */
	public void connect() throws UnknownHostException, IOException {
		socket = new Socket(ip, port);
		out = new DataOutputStream(socket.getOutputStream());
		in = new DataInputStream(socket.getInputStream());
		System.out.println("ChatServer Connect...");
	}

	/**
	 * ä�ø޽��� ������ ����
	 * 
	 * @throws IOException
	 */
	public void sendMessage(String message) {
		try {
			out.writeUTF(message);
		} catch (IOException e) {
			String erroMessage = "��Ʈ��ũ ��ְ� �߻��Ͽ����ϴ�.\n����� �ٽ� �õ��Ͽ� �ּ���.";
			JOptionPane.showMessageDialog(null, erroMessage, "��Ʈ��ũ ����",
					JOptionPane.ERROR_MESSAGE);
			disConnect();
		}
	}

	/**
	 * ä�ü��� ���� ����
	 */
	public void disConnect() {
		try {
			if (in != null)
				in.close();
			if (out != null)
				out.close();
			if (socket != null)
				socket.close();
		} catch (IOException e) {
		}

	}

}
