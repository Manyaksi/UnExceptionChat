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

		main.add("����", loginPanel);
		main.add("�Ҷ�", registPanel);
		main.add("����", waitRoomPanel);
		main.add("�μ�", chatRoomPanel);

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
	 * ������ �޼��� ����
	 */
	public void uisendMessage(String message) {
		chatClient.sendMessage(message);
	}

	/**
	 * ������ ��� ���
	 */
	public void setUserList(String nickname, String[] users) {

		data.removeAllElements();
		for (String user : users) {
			/*
			 * if(nickname.equals(user)){ user = "��"; }
			 */
			data.addElement(user);
			waitRoomPanel.userList.setModel(data);
		}

	}

	/**
	 * �� ������ ��� ���
	 */
	public void setUserListRoom(String nickname, String[] users) {

		data.removeAllElements();
		for (String user : users) {
			/*
			 * if(nickname.equals(user)){ user = "��"; }
			 */
			data.addElement(user);
			chatRoomPanel.userList.setModel(data);
		}

	}

	/**
	 * ���� ����
	 */
	public void connectServer() {
		chatClient = new ChatClient();
		try {
			chatClient.connect();

			// �������� �ǽð� �޽��� ������ ���� ������ ���� �� ����
			messageReceiver = new MessageReceiver(this, chatClient.getSocket());
			messageReceiver.start();

			// ���� ���� �޼��� ����.
			chatClient.sendMessage(Message.REQUEST_CLIENT_CONNECT
					+ Message.DELIMETER);

			setContents();
			setSize(400, 600);
			// ui.pack();
			setVisible(true);

		} catch (UnknownHostException e) {
			JOptionPane.showMessageDialog(this, "ä�ü����� ã�� �� �����ϴ�.");
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "������ְ� �߻��Ͽ� ������ ������ �� �����ϴ�.");
		}
	}

	/**
	 * ���� ä�� ����
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
				JOptionPane.showMessageDialog(this, "�ӼӸ� ���� : /w ����г��� ä�ó���");
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
		 * // 200||��ȭ��||��Ƽä�ø޽��� String message = Message.REQUEST_WAITROOMCHAT +
		 * Message.DELIMETER + chatMessage; System.out.println(message);
		 * 
		 * chatClient.sendMessage(message); waitRoomPanel.messageTF.setText("");
		 */
	}

	/**
	 * ��ȭ�� ä�� ����
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
				JOptionPane.showMessageDialog(this, "�ӼӸ� ���� : /w ����г��� ä�ó���");
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
	 * ���� ä��
	 * 
	 * @param message
	 */
	public void appendMessage(String message) {
		waitRoomPanel.messageTA.append(message + "\r\n");
		 waitRoomPanel.messageTaSp.getVerticalScrollBar().setValue(
		 waitRoomPanel.messageTaSp.getVerticalScrollBar().getMaximum());
	}

	/**
	 * ��ȭ�� ä��
	 */
	public void appendMessageRoom(String message) {
		chatRoomPanel.chatTA.append(message + "\r\n");
		 chatRoomPanel.chatTaSp.getVerticalScrollBar().setValue(
		 chatRoomPanel.chatTaSp.getVerticalScrollBar().getMaximum());
	}

	/**
	 * ��ȭ�� ����
	 */
	public void createChatRoom(String message) {
		chatClient.sendMessage(message);
		setSize(400, 600);
		change("�μ�");
	}

	/**
	 * ��ȭ�� ����Ұ�
	 */
	public void dontJoinRoom() {
		JOptionPane.showMessageDialog(this, "��й�ȣ�� Ʋ���ϴ�.");
		setSize(850, 600);
		change("����");
	}

	public void dontJoinRoomFull() {
		JOptionPane.showMessageDialog(this, "�� �ο��� �� á���ϴ�.");
		setSize(850, 600);
		change("����");

	}

	/**
	 * ��ȭ�� ��� ����
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
	 * �������� �޽��� ����
	 */
	public void sendCloseMessage() {
		// 900|*|��ȭ��
		chatClient.sendMessage(Message.REQUEST_CLIENT_DISCONNECT
				+ Message.DELIMETER);
	}

	/**
	 * ���� �α׾ƿ� �޽��� ����
	 */
	public void sendLogoutMessage() {
		chatClient.sendMessage(Message.REQUEST_WAITROOMOUT + Message.DELIMETER);
	}

	/**
	 * Ŭ���̾�Ʈ ���ҽ� ����
	 */
	public void closeResource() {
		chatClient.disConnect();
		messageReceiver.disConnect();
	}

	/**
	 * ���α׷� ����
	 */
	public void exit() {
		sendLogoutMessage();
		closeResource();
		setVisible(false);
		dispose();
		System.exit(0);
	}

	// �α��� �κ�
	public void login() {
		if (loginPanel.isNull()) {

			chatClient.sendMessage(Message.REQUEST_LOGIN + Message.DELIMETER
					+ loginPanel.idTF.getText() + Message.DELIMETER
					+ new String(loginPanel.passwdTF.getPassword()));
		} else {
			loginPanel.idTF.setText("");
			loginPanel.passwdTF.setText("");
			JOptionPane.showMessageDialog(this, "���̵� & ��й�ȣ�� �Է��ϼ���.");
		}
	}

	// ȸ������ �κ�
	public void join() {
		if (registPanel.isNull() && registPanel.isEqualPw()) {
			chatClient.sendMessage(Message.REQUEST_JOIN + Message.DELIMETER
					+ registPanel.nickNameTF.getText() + ","
					+ registPanel.nameTF.getText() + ","
					+ registPanel.idTF.getText() + ","
					+ new String(registPanel.pwcF.getPassword()) + ","
					+ registPanel.gender + ","
					+ registPanel.yearType.getSelectedItem() + "��"
					+ registPanel.monthType.getSelectedItem() + "��"
					+ registPanel.dayType.getSelectedItem() + "��" + ","
					+ registPanel.eMailTF.getText());
			change("����");
		} else if (!registPanel.isNull() && registPanel.isEqualPw()) {
			JOptionPane.showMessageDialog(this, "��ĭ�� �־��.");
		} else {
			JOptionPane.showMessageDialog(this, "��й�ȣ�� ��ġ���� �ʽ��ϴ�.");
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
		change("����");
	}

	public void sendJoinRoom(String message) {
		chatClient.sendMessage(message);
	}

	public void sendFileMessage(String idFileTo) {

	}

	/**
	 * �ش� ä�ù� ������ ���
	 * 
	 * @param enterRoomInfor
	 */
	public void showRoomInfor(String enterRoomInfor) {
		JOptionPane.showMessageDialog(null, enterRoomInfor, "������ ����",
				JOptionPane.INFORMATION_MESSAGE);
	}

}
