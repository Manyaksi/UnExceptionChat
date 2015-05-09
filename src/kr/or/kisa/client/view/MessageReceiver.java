package kr.or.kisa.client.view;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JOptionPane;

import kr.or.kisa.client.model.Room;
import kr.or.kisa.client.model.RoomTableModel;
import kr.or.kisa.server.Message;

/**
 * �������� ���۵Ǵ� ä�ø޽��� ����
 * 
 * @author �����
 *
 */
public class MessageReceiver extends Thread {

	private Socket socket;
	private DataInputStream in;
	private ChatUI chatUI_Test;
	private boolean stop;
	private RoomTableModel model;
	private Room room;

	public MessageReceiver(ChatUI chatUI_Test, Socket socket2)
			throws IOException {
		// TODO Auto-generated constructor stub
		this.chatUI_Test = chatUI_Test;
		this.socket = socket2;
		in = new DataInputStream(socket2.getInputStream());
	}

	/**
	 * �������� ���۵Ǵ� ����޽����� ���� ó��
	 * 
	 * @throws IOException
	 */
	public void receiveMessage() {
		try {
			while (!stop) {
				String serverMessage = in.readUTF();
				// ������� ���� �ӽ� �ܼ� ���
				System.out.println("[�����κ��� ���Ÿ޽���] : " + serverMessage);

				String[] tokens = serverMessage.split("\\|\\*\\|");// ���Խ� Ȱ�� ��ū
																	// �и�
				String messageType = tokens[0];
				String fmessage = tokens[1];

				switch (messageType) {

				// ���� ���� ��� ����
				case Message.RESPONSE_CLIENT_CONNECT:
					break;

				// �α��� ��� ����
				case Message.RESPONSE_LOGIN:
					String nick = tokens[2];

					if (fmessage.trim().equals("TRUE")) {
						chatUI_Test.change("����");
						chatUI_Test.setSize(850, 600);
						chatUI_Test.appendMessage("[" + nick + "] : "
								+ "���� ���� �ϼ̽��ϴ�.");
						// chatUI_Test.waitRoomPanel.model.fireTableStructureChanged();
					} else if (tokens[2].trim().equals("NULL")) {
						JOptionPane.showMessageDialog(chatUI_Test,
								"���̵�� ��й�ȣ�� Ȯ���Ͻʽÿ�.");
					} else {
						JOptionPane.showMessageDialog(chatUI_Test,
								"�̹� �α������Դϴ�.");
					}

					break;

				// ȸ������ ��� ����
				case Message.RESPONSE_JOIN:

					if (fmessage.trim().equals("TRUE")) {
						chatUI_Test.registPanel.clear();
						chatUI_Test.change("����");
						chatUI_Test.setSize(400, 600);

					} else {
						JOptionPane.showMessageDialog(chatUI_Test,
								"�ߺ��� ���̵��Դϴ�.");

					}
					break;

				// ������ ����Ʈ ����
				case Message.RESPONSE_LIST:
					String nickname = tokens[1];
					String nickNames = tokens[2];
					String[] users = nickNames.split(",");
					chatUI_Test.setUserList(nickname, users);
					break;

				// ���̵� ã�� ��� ����
				case Message.RESPONSE_FINDID:
					if (fmessage.trim().equals("TRUE")) {
						String id = tokens[2];
						chatUI_Test.loginPanel.idTF.setText(id);

					} else {
						JOptionPane.showMessageDialog(chatUI_Test,
								"������ �ٽ� �Է��ϼ���");
					}
					break;

				case Message.RESPONSE_FINDPW:
					if (fmessage.trim().equals("TRUE")) {
						String pw = tokens[2];
						String message = "����� ��й�ȣ :" + pw;
						// chatUI_Test.loginPanel.idTF.setText(id);
						JOptionPane.showMessageDialog(chatUI_Test, message);

					} else {
						JOptionPane.showMessageDialog(chatUI_Test,
								"������ �ٽ� �Է��ϼ���");
					}
					break;

				// ��ȭ�� ���� ��� ����
				case Message.RESPONSE_CREATROOM:
					if (fmessage.trim().equals("TRUE")) {
						String message = tokens[2];

						// public Room(String roomName, String roomMaker, int
						// enterCount){
						// model = new RoomTableModel();

						// room = new Room(roomName, roomOwner, count);
						// chatUI_Test.joinRoom();
						chatUI_Test.showChatRoom(message);
					}
					break;

				// ��ȭ�� ���� ��� ����
				case Message.RESPONSE_JOINROOM:

					if (fmessage.trim().equals("TRUE")) {
						String message = tokens[2];
					} else {
						String reason = tokens[2];
						if (reason.equals("ISNOT")) {
							chatUI_Test.dontJoinRoom();
						} else if (reason.equals("FULL")) {
							chatUI_Test.dontJoinRoomFull();
						}

					}
					break;

				// ��ȭ�� ������ ����Ʈ ����
				case Message.RESPONSE_JOINLIST:
					String nicknameroom = tokens[1];
					String nickNamesroom = tokens[2];
					String[] usersroom = nickNamesroom.split(",");
					chatUI_Test.setUserListRoom(nicknameroom, usersroom);
					break;

				// ��ȭ������� ��� ����
				case Message.RESPONSE_DETAILROOM:
					String enterRoomInfor = tokens[1];
					chatUI_Test.showRoomInfor(enterRoomInfor);
					break;

				// ���� �α׾ƿ� ��� ����
				case Message.RESPONSE_WAITROOMOUT:
					String logoutNickName = tokens[1];
					chatUI_Test.appendMessage("�ڡ١�[" + logoutNickName
							+ "]���� �α׾ƿ��Ͽ����ϴ� �ڡ١�");
					break;

				// ������ ���� ��� ����
				case Message.RESPONSE_PERSONINFOR:
					String message = tokens[1];
					// chatUI_Test.waitRoomPanel.userList.setToolTipText(MultiLineTooltips.splitToolTip(message));
					chatUI_Test.waitRoomPanel.userList.setToolTipText(message);
					// chatUI_Test.waitRoomPanel.userList.setToolTipText(message);
					// JOptionPane.showMessageDialog(chatUI_Test, message);
					break;

				// ��ȭ�� ������ ���� ��� ����(�μ�);

				case Message.RESPONSE_ROOM_PERSONINFOR:
					message = tokens[1];

					chatUI_Test.chatRoomPanel.userList.setToolTipText(message);

					break;

				// ��ȭ�� �α׾ƿ� ��� ����
				case Message.RESPONSE_CHATROOMOUT:
					String nicknameroomOut = tokens[1];
					String nickNamesroomOut = tokens[2];
					String[] usersroomOut = nickNamesroomOut.split(",");
					chatUI_Test.setUserListRoom(nicknameroomOut, usersroomOut);
					break;

				// ��ȭ�� ����Ʈ ��� ����
				case Message.RESPONSE_ROOMLIST:
					String roominfo = tokens[1];
					chatUI_Test.showChatRoomList(roominfo);
					break;

				// ����� ä�� ����
				case Message.RESPONSE_WAITROOMCHAT:
					String nickName = tokens[1];
					String chatMessage = tokens[2];
					chatUI_Test.appendMessage("[" + nickName + "] : "
							+ chatMessage);
					break;

				// ��ȭ�� ä�� ��� ����
				case Message.RESPONSE_CHAT:
					String nickNameRoom = tokens[1];
					String chatMessageRoom = tokens[2];
					chatUI_Test.appendMessageRoom("[" + nickNameRoom + "] : "
							+ chatMessageRoom);
					break;

				// �ӼӸ� ��� ���� �ޱ�
				case Message.RESPONSE_CHATTO:
					String way = tokens[2];
					if (fmessage.trim().equals("TRUE")
							&& way.trim().equals("TO")) {
						String nickNameFor = tokens[3];
						String chatMessageRoomFor = tokens[4];
						chatUI_Test.appendMessageRoom("[To : " + nickNameFor
								+ "] : " + chatMessageRoomFor);
					} else if (fmessage.trim().equals("TRUE")
							&& way.trim().equals("FOR")) {
						String nickNameFor = tokens[3];
						String chatMessageRoomFor = tokens[4];
						chatUI_Test.appendMessageRoom("[For : " + nickNameFor
								+ "] : " + chatMessageRoomFor);
					} else if (fmessage.trim().equals("FALSE")
							&& way.trim().equals("SAME")) {
						chatUI_Test.appendMessageRoom("���ο��� �ӼӸ��� ���� �� �����ϴ�.");
					} else {
						String nickNameFor = tokens[3];
						chatUI_Test.appendMessageRoom(nickNameFor
								+ "�̶�� ����� �� �濡 �����ϴ�.");
					}
					break;

				// ���� �ӼӸ� ��� ���� �ޱ�
				case Message.RESPONSE_WAITCHATTO:
					String wayW = tokens[2];
					if (fmessage.trim().equals("TRUE")
							&& wayW.trim().equals("TO")) {
						String nickNameFor = tokens[3];
						String chatMessageFor = tokens[4];
						chatUI_Test.appendMessage("[To : " + nickNameFor
								+ "] : " + chatMessageFor);
					} else if (fmessage.trim().equals("TRUE")
							&& wayW.trim().equals("FOR")) {
						String nickNameFor = tokens[3];
						String chatMessageFor = tokens[4];
						chatUI_Test.appendMessage("[For : " + nickNameFor
								+ "] : " + chatMessageFor);
					} else if (fmessage.trim().equals("FALSE")
							&& wayW.trim().equals("SAME")) {
						chatUI_Test.appendMessage("���ο��� �ӼӸ��� ���� �� �����ϴ�.");
					} else {
						String nickNameFor = tokens[3];
						chatUI_Test.appendMessage(nickNameFor
								+ "�̶�� ����� �� �濡 �����ϴ�.");
					}
					break;

				// ��ȭ�� ä�� ��� ����
				case Message.RESPONSE_FILESEND:

					break;

				// ���� ���� ���� ��� ����
				case Message.REQUEST_CLIENT_DISCONNECT:
					chatUI_Test.appendMessage("@@@ [" + fmessage
							+ "]���� ���� �����Ͽ����ϴ� @@@");
					break;
				}
			}
		} catch (IOException e) {
		}
	}

	/**
	 * ä�ü��� ���� ����
	 */
	public void disConnect() {
		try {
			if (in != null)
				in.close();
			if (socket != null)
				socket.close();
		} catch (IOException e) {
		}

	}

	@Override
	public void run() {
		receiveMessage();
	}

}
