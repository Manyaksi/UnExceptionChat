package kr.or.kisa.client.view;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JOptionPane;

import kr.or.kisa.client.model.Room;
import kr.or.kisa.client.model.RoomTableModel;
import kr.or.kisa.server.Message;

/**
 * 서버에서 전송되는 채팅메시지 수신
 * 
 * @author 김기정
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
	 * 서버에서 전송되는 응답메시지에 대한 처리
	 * 
	 * @throws IOException
	 */
	public void receiveMessage() {
		try {
			while (!stop) {
				String serverMessage = in.readUTF();
				// 디버깅을 위한 임시 콘솔 출력
				System.out.println("[서버로부터 수신메시지] : " + serverMessage);

				String[] tokens = serverMessage.split("\\|\\*\\|");// 정규식 활용 토큰
																	// 분리
				String messageType = tokens[0];
				String fmessage = tokens[1];

				switch (messageType) {

				// 서버 연결 결과 응답
				case Message.RESPONSE_CLIENT_CONNECT:
					break;

				// 로그인 결과 응답
				case Message.RESPONSE_LOGIN:
					String nick = tokens[2];

					if (fmessage.trim().equals("TRUE")) {
						chatUI_Test.change("원영");
						chatUI_Test.setSize(850, 600);
						chatUI_Test.appendMessage("[" + nick + "] : "
								+ "님이 접속 하셨습니다.");
						// chatUI_Test.waitRoomPanel.model.fireTableStructureChanged();
					} else if (tokens[2].trim().equals("NULL")) {
						JOptionPane.showMessageDialog(chatUI_Test,
								"아이디와 비밀번호를 확인하십시오.");
					} else {
						JOptionPane.showMessageDialog(chatUI_Test,
								"이미 로그인중입니다.");
					}

					break;

				// 회원가입 결과 응답
				case Message.RESPONSE_JOIN:

					if (fmessage.trim().equals("TRUE")) {
						chatUI_Test.registPanel.clear();
						chatUI_Test.change("선민");
						chatUI_Test.setSize(400, 600);

					} else {
						JOptionPane.showMessageDialog(chatUI_Test,
								"중복된 아이디입니다.");

					}
					break;

				// 접속자 리스트 응답
				case Message.RESPONSE_LIST:
					String nickname = tokens[1];
					String nickNames = tokens[2];
					String[] users = nickNames.split(",");
					chatUI_Test.setUserList(nickname, users);
					break;

				// 아이디 찾기 결과 응답
				case Message.RESPONSE_FINDID:
					if (fmessage.trim().equals("TRUE")) {
						String id = tokens[2];
						chatUI_Test.loginPanel.idTF.setText(id);

					} else {
						JOptionPane.showMessageDialog(chatUI_Test,
								"정보를 다시 입력하세요");
					}
					break;

				case Message.RESPONSE_FINDPW:
					if (fmessage.trim().equals("TRUE")) {
						String pw = tokens[2];
						String message = "당신의 비밀번호 :" + pw;
						// chatUI_Test.loginPanel.idTF.setText(id);
						JOptionPane.showMessageDialog(chatUI_Test, message);

					} else {
						JOptionPane.showMessageDialog(chatUI_Test,
								"정보를 다시 입력하세요");
					}
					break;

				// 대화방 생성 결과 응답
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

				// 대화방 입장 결과 응답
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

				// 대화방 참여자 리스트 응답
				case Message.RESPONSE_JOINLIST:
					String nicknameroom = tokens[1];
					String nickNamesroom = tokens[2];
					String[] usersroom = nickNamesroom.split(",");
					chatUI_Test.setUserListRoom(nicknameroom, usersroom);
					break;

				// 대화방상세정보 결과 응답
				case Message.RESPONSE_DETAILROOM:
					String enterRoomInfor = tokens[1];
					chatUI_Test.showRoomInfor(enterRoomInfor);
					break;

				// 대기방 로그아웃 결과 응답
				case Message.RESPONSE_WAITROOMOUT:
					String logoutNickName = tokens[1];
					chatUI_Test.appendMessage("★☆★[" + logoutNickName
							+ "]님이 로그아웃하였습니다 ★☆★");
					break;

				// 참가자 정보 결과 응답
				case Message.RESPONSE_PERSONINFOR:
					String message = tokens[1];
					// chatUI_Test.waitRoomPanel.userList.setToolTipText(MultiLineTooltips.splitToolTip(message));
					chatUI_Test.waitRoomPanel.userList.setToolTipText(message);
					// chatUI_Test.waitRoomPanel.userList.setToolTipText(message);
					// JOptionPane.showMessageDialog(chatUI_Test, message);
					break;

				// 대화방 참가자 정보 결과 응담(민수);

				case Message.RESPONSE_ROOM_PERSONINFOR:
					message = tokens[1];

					chatUI_Test.chatRoomPanel.userList.setToolTipText(message);

					break;

				// 대화방 로그아웃 결과 응답
				case Message.RESPONSE_CHATROOMOUT:
					String nicknameroomOut = tokens[1];
					String nickNamesroomOut = tokens[2];
					String[] usersroomOut = nickNamesroomOut.split(",");
					chatUI_Test.setUserListRoom(nicknameroomOut, usersroomOut);
					break;

				// 대화방 리스트 결과 응답
				case Message.RESPONSE_ROOMLIST:
					String roominfo = tokens[1];
					chatUI_Test.showChatRoomList(roominfo);
					break;

				// 대기자 채팅 응답
				case Message.RESPONSE_WAITROOMCHAT:
					String nickName = tokens[1];
					String chatMessage = tokens[2];
					chatUI_Test.appendMessage("[" + nickName + "] : "
							+ chatMessage);
					break;

				// 대화방 채팅 결과 응답
				case Message.RESPONSE_CHAT:
					String nickNameRoom = tokens[1];
					String chatMessageRoom = tokens[2];
					chatUI_Test.appendMessageRoom("[" + nickNameRoom + "] : "
							+ chatMessageRoom);
					break;

				// 귓속말 결과 응답 받기
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
						chatUI_Test.appendMessageRoom("본인에게 귓속말을 보낼 수 없습니다.");
					} else {
						String nickNameFor = tokens[3];
						chatUI_Test.appendMessageRoom(nickNameFor
								+ "이라는 사람은 이 방에 없습니다.");
					}
					break;

				// 대기방 귓속말 결과 응답 받기
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
						chatUI_Test.appendMessage("본인에게 귓속말을 보낼 수 없습니다.");
					} else {
						String nickNameFor = tokens[3];
						chatUI_Test.appendMessage(nickNameFor
								+ "이라는 사람은 이 방에 없습니다.");
					}
					break;

				// 대화방 채팅 결과 응답
				case Message.RESPONSE_FILESEND:

					break;

				// 서버 연결 종료 결과 응답
				case Message.REQUEST_CLIENT_DISCONNECT:
					chatUI_Test.appendMessage("@@@ [" + fmessage
							+ "]님이 연결 해제하였습니다 @@@");
					break;
				}
			}
		} catch (IOException e) {
		}
	}

	/**
	 * 채팅서버 연결 끊기
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
