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
			JOptionPane.showMessageDialog(null, "포트("+server.getPort()+") 충돌로 서버를 실행할 수 없습니다.", "서버 에러", JOptionPane.ERROR_MESSAGE);
		}

	}

}
