package kr.or.kisa.client.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.StringTokenizer;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import kr.or.kisa.client.model.ChatClient;
import kr.or.kisa.client.model.Room;
import kr.or.kisa.server.Message;

public class ChatUI extends JFrame {
	chatRoomPanel chatRoomPanel;
	RegistPanel registPanel;
	LoginPanel loginPanel;
	WaitRoomPanel waitRoomPanel;

	DefaultListModel<String> data;

	SearchFrame searchFrame;
	ChatClient chatClient;
	MessageReceiver messageReceiver;

	String roomOwner;
	CardLayout cardLayout;
	JPanel main;

	Room room;

	public ChatUI(String title) {
		super(title);
		searchFrame = new SearchFrame(this);
		chatRoomPanel = new chatRoomPanel(this);
		registPanel = new RegistPanel(this);
		loginPanel = new LoginPanel(this);
		waitRoomPanel = new WaitRoomPanel(this);
		chatRoomPanel = new chatRoomPanel(this);

		cardLayout = new CardLayout();
		main = new JPanel();
		data = new DefaultListModel<String>();
		connectServer();
	}

	public void setContents() {
		// setContents
		chatRoomPanel.setContents();
		registPanel.setContents();
		waitRoomPanel.setContents();
		loginPanel.setContents();
		// eventRegist
		chatRoomPanel.eventRegist();
		registPanel.eventRegist();
		waitRoomPanel.eventRegist();
		loginPanel.eventRegist();

		main.setLayout(cardLayout);

		main.add("선민", loginPanel);
		main.add("소라", registPanel);
		main.add("원영", waitRoomPanel);
		main.add("민수", chatRoomPanel);

		add(main, BorderLayout.CENTER);
		setSize(400, 600);
	}

	public void change(String name) {
		cardLayout.show(main, name);
	}

	public void setOwner(String roomOwner) {
		this.roomOwner = roomOwner;

	}

	/**
	 * 서버로 메세지 전송
	 */
	public void uisendMessage(String message) {
		chatClient.sendMessage(message);
	}

	/**
	 * 접속자 목록 출력
	 */
	public void setUserList(String nickname, String[] users) {

		data.removeAllElements();
		for (String user : users) {
			/*
			 * if(nickname.equals(user)){ user = "나"; }
			 */
			data.addElement(user);
			waitRoomPanel.userList.setModel(data);
		}

	}

	/**
	 * 방 접속자 목록 출력
	 */
	public void setUserListRoom(String nickname, String[] users) {

		data.removeAllElements();
		for (String user : users) {
			/*
			 * if(nickname.equals(user)){ user = "나"; }
			 */
			data.addElement(user);
			chatRoomPanel.userList.setModel(data);
		}

	}

	/**
	 * 서버 연결
	 */
	public void connectServer() {
		chatClient = new ChatClient();
		try {
			chatClient.connect();

			// 서버에의 실시간 메시지 수신을 위한 스레드 생성 및 실행
			messageReceiver = new MessageReceiver(this, chatClient.getSocket());
			messageReceiver.start();

			// 서버 연결 메세지 수신.
			chatClient.sendMessage(Message.REQUEST_CLIENT_CONNECT
					+ Message.DELIMETER);

			setContents();
			setSize(400, 600);
			// ui.pack();
			setVisible(true);

		} catch (UnknownHostException e) {
			JOptionPane.showMessageDialog(this, "채팅서버를 찾을 수 없습니다.");
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "서버장애가 발생하여 서버를 연결할 수 없습니다.");
		}
	}

	/**
	 * 대기방 채팅 연결
	 */
	public void sendChatMessage() {
		String chatMessage = waitRoomPanel.messageTF.getText();
		if (chatMessage == null || chatMessage.trim().length() == 0) {
			return;
		}
		String messageTo;
		String toId;
		if (chatMessage.startsWith("/w")) {
			String[] st = chatMessage.split(" ");
			if (st.length < 3) {
				JOptionPane.showMessageDialog(this, "귓속말 사용법 : /w 상대방닉네임 채팅내용");
				return;
			}
			toId = st[1];
			messageTo = st[2];
			String message = Message.REQUEST_WAITCHATTO + Message.DELIMETER
					+ messageTo + Message.DELIMETER + toId;
			chatClient.sendMessage(message);
		} else {
			String message = Message.REQUEST_WAITROOMCHAT + Message.DELIMETER
					+ chatMessage;
			chatClient.sendMessage(message);
		}
		waitRoomPanel.messageTF.setText("");

		/*
		 * // 200||대화명||멀티채팅메시지 String message = Message.REQUEST_WAITROOMCHAT +
		 * Message.DELIMETER + chatMessage; System.out.println(message);
		 * 
		 * chatClient.sendMessage(message); waitRoomPanel.messageTF.setText("");
		 */
	}

	/**
	 * 대화방 채팅 연결
	 */
	public void sendChatMessageRoom() {
		String chatMessage = chatRoomPanel.messageTF.getText();
		if (chatMessage == null || chatMessage.trim().length() == 0) {
			return;
		}

		String messageTo;
		String toId;
		if (chatMessage.startsWith("/w")) {
			String[] st = chatMessage.split(" ");
			if (st.length < 3) {
				JOptionPane.showMessageDialog(this, "귓속말 사용법 : /w 상대방닉네임 채팅내용");
				return;
			}
			toId = st[1];
			messageTo = st[2];
			String message = Message.REQUEST_CHATTO + Message.DELIMETER
					+ messageTo + Message.DELIMETER + toId;
			chatClient.sendMessage(message);
		} else {
			String message = Message.REQUEST_CHAT + Message.DELIMETER
					+ chatMessage;
			chatClient.sendMessage(message);
		}
		chatRoomPanel.messageTF.setText("");
	}

	/**
	 * 대기방 채팅
	 * 
	 * @param message
	 */
	public void appendMessage(String message) {
		waitRoomPanel.messageTA.append(message + "\r\n");
		 waitRoomPanel.messageTaSp.getVerticalScrollBar().setValue(
		 waitRoomPanel.messageTaSp.getVerticalScrollBar().getMaximum());
	}

	/**
	 * 대화방 채팅
	 */
	public void appendMessageRoom(String message) {
		chatRoomPanel.chatTA.append(message + "\r\n");
		 chatRoomPanel.chatTaSp.getVerticalScrollBar().setValue(
		 chatRoomPanel.chatTaSp.getVerticalScrollBar().getMaximum());
	}

	/**
	 * 대화방 생성
	 */
	public void createChatRoom(String message) {
		chatClient.sendMessage(message);
		setSize(400, 600);
		change("민수");
	}

	/**
	 * 대화방 입장불가
	 */
	public void dontJoinRoom() {
		JOptionPane.showMessageDialog(this, "비밀번호가 틀립니다.");
		setSize(850, 600);
		change("원영");
	}

	public void dontJoinRoomFull() {
		JOptionPane.showMessageDialog(this, "방 인원이 꽉 찼습니다.");
		setSize(850, 600);
		change("원영");

	}

	/**
	 * 대화방 목록 생성
	 */
	public void showChatRoom(String str) {
		String[] joinTokens = str.split("\\,");
		String roomName = joinTokens[0];
		String roomOwner = joinTokens[1];
		int count = Integer.parseInt(joinTokens[2]);
		int maxCount = Integer.parseInt(joinTokens[3]);
		int roomisRock = Integer.parseInt(joinTokens[4]);
		waitRoomPanel.model.addRoom(new Room(roomName, roomOwner, count,
				maxCount, roomisRock));
	}

	public void showChatRoomList(String str) {
		waitRoomPanel.model.addRoom(str);
	}

	/**
	 * 접속종료 메시지 전송
	 */
	public void sendCloseMessage() {
		// 900|*|대화명
		chatClient.sendMessage(Message.REQUEST_CLIENT_DISCONNECT
				+ Message.DELIMETER);
	}

	/**
	 * 대기방 로그아웃 메시지 전송
	 */
	public void sendLogoutMessage() {
		chatClient.sendMessage(Message.REQUEST_WAITROOMOUT + Message.DELIMETER);
	}

	/**
	 * 클라이언트 리소스 해제
	 */
	public void closeResource() {
		chatClient.disConnect();
		messageReceiver.disConnect();
	}

	/**
	 * 프로그램 종료
	 */
	public void exit() {
		sendLogoutMessage();
		closeResource();
		setVisible(false);
		dispose();
		System.exit(0);
	}

	// 로그인 부분
	public void login() {
		if (loginPanel.isNull()) {

			chatClient.sendMessage(Message.REQUEST_LOGIN + Message.DELIMETER
					+ loginPanel.idTF.getText() + Message.DELIMETER
					+ new String(loginPanel.passwdTF.getPassword()));
		} else {
			loginPanel.idTF.setText("");
			loginPanel.passwdTF.setText("");
			JOptionPane.showMessageDialog(this, "아이디 & 비밀번호를 입력하세요.");
		}
	}

	// 회원가입 부분
	public void join() {
		if (registPanel.isNull() && registPanel.isEqualPw()) {
			chatClient.sendMessage(Message.REQUEST_JOIN + Message.DELIMETER
					+ registPanel.nickNameTF.getText() + ","
					+ registPanel.nameTF.getText() + ","
					+ registPanel.idTF.getText() + ","
					+ new String(registPanel.pwcF.getPassword()) + ","
					+ registPanel.gender + ","
					+ registPanel.yearType.getSelectedItem() + "년"
					+ registPanel.monthType.getSelectedItem() + "월"
					+ registPanel.dayType.getSelectedItem() + "일" + ","
					+ registPanel.eMailTF.getText());
			change("선민");
		} else if (!registPanel.isNull() && registPanel.isEqualPw()) {
			JOptionPane.showMessageDialog(this, "빈칸이 있어요.");
		} else {
			JOptionPane.showMessageDialog(this, "비밀번호가 일치하지 않습니다.");
		}

	}

	public void eventRegist() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				exit();
				sendCloseMessage();
			}
		});
	}

	public void sendRoomOutMessage(String message) {
		chatClient.sendMessage(message);
		setSize(850, 600);
		change("원영");
	}

	public void sendJoinRoom(String message) {
		chatClient.sendMessage(message);
	}

	public void sendFileMessage(String idFileTo) {

	}

	/**
	 * 해당 채팅방 참여자 목록
	 * 
	 * @param enterRoomInfor
	 */
	public void showRoomInfor(String enterRoomInfor) {
		JOptionPane.showMessageDialog(null, enterRoomInfor, "참여자 정보",
				JOptionPane.INFORMATION_MESSAGE);
	}

}
