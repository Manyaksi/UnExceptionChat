package kr.or.kisa.server;

import java.io.IOException;

import javax.swing.JOptionPane;

public class KostaServer {
	public static void main(String[] args) {
		ChatServer server = new ChatServer();
		try {
			server.startServer();
			
			//server.stopServer();
			
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "��Ʈ("+server.getPort()+") �浹�� ������ ������ �� �����ϴ�.", "���� ����", JOptionPane.ERROR_MESSAGE);
		}

	}

}
