package kr.or.kisa.server;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * 원격클라이언트(Socket)의 채팅 메시지를 처리하는 스레드
 * @author 김기정
 */
public class MessageHandler extends Thread {
	User user;
	
	
	private boolean stop;
	private Socket socket;
	private DataInputStream in;
	private DataOutputStream out;

	private Room room;
	private String nickName;
	
	private ChatServer chatServer;
	
	private UserDao userDao = new UserDao();

	public MessageHandler(ChatServer chatServer, Socket socket) throws IOException{
		this.chatServer = chatServer;
		this.socket = socket;
		out = new DataOutputStream(socket.getOutputStream());
		in  = new DataInputStream(socket.getInputStream());
	}
	
	public Socket getSocket() {
		return socket;
	}

	public DataInputStream getIn() {
		return in;
	}

	public DataOutputStream getOut() {
		return out;
	}
	
	public String getNickName() {
		return nickName;
	}

	/**
	 * 클라이언트의 요청메시지 처리
	 * @throws ClassNotFoundException 
	 * @throws IOException
	 */
	public void handleMessage() throws ClassNotFoundException{
		UserDao userdao = new UserDao();
				
		try{
			while(!stop){
				String clientMessage = in.readUTF();
				
				// 디버깅을 위한 임시 콘솔 출력
				System.out.println("[클라이언트로부터 수신메시지] : " + clientMessage);
				
				String[] tokens = clientMessage.split("\\|\\*\\|");// 정규식 활용 토큰 분리
				String messageType = tokens[0];
				//String message = tokens[1];
				
				switch (messageType) {
					// 서버연결 메시지
					case Message.REQUEST_CLIENT_CONNECT:
						
						// 접속한 모든 클라이언트들에게 새로운 클라이언트 접속을 알림(101|*|대화명)
						sendMessage(Message.RESPONSE_CLIENT_CONNECT + Message.DELIMETER + "TRUE");
						// 접속한 모든 클라이언트들에게 접속자 목록 전송(102|*|대화명|*|접속자1,접속자2,,,)

						break;
						
					//로그인 메시지
					case Message.REQUEST_LOGIN:
						
						String id = tokens[1];
						String passwd = tokens[2];
						String nickNames = chatServer.getClientNickNames();
						String[] token = nickNames.split("\\,");
						
						try {
							user = userdao.getUser(id, passwd);
							
						} catch (ClassNotFoundException e) {
						}
						if(user != null){
							boolean same = false;
							nickName = user.getNickName();
							
							for (String string : token) {
								if(string.trim().equals(nickName)){
									sendMessage(Message.RESPONSE_LOGIN + Message.DELIMETER + "FALSE"+ Message.DELIMETER+ "EQUAL");
									same = true;
									break;
								}
							}
							if(same) break;
							
							chatServer.addClient(this);
							chatServer.sendToAll(Message.RESPONSE_LOGIN + Message.DELIMETER + "TRUE" + Message.DELIMETER + user.getNickName());
						
							// 접속한 모든 클라이언트들에게 접속자 목록 전송(102|*|대화명|*|접속자1,접속자2,,,)
							nickNames = chatServer.getClientNickNames();
							chatServer.sendToAll(Message.RESPONSE_LIST + Message.DELIMETER + nickName + Message.DELIMETER + nickNames);
							if(chatServer.getAllRoomInfo() != null){
								chatServer.sendToAll(Message.RESPONSE_ROOMLIST + Message.DELIMETER + chatServer.getAllRoomInfo());
							}
							
		
						}else{
							sendMessage(Message.RESPONSE_LOGIN + Message.DELIMETER + "FALSE"+ Message.DELIMETER + "NULL");
						}
						break;
						
					//회원가입 메시지
					case Message.REQUEST_JOIN:
						String message = tokens[1];
						String[] joinTokens = message.split("\\,");
						String joinNickname = joinTokens[0];
						String joinName = joinTokens[1];
						String joinId = joinTokens[2];
						String joinpw = joinTokens[3];
						byte joingender = Byte.parseByte(joinTokens[4]);
						String joinBirth = joinTokens[5];
						String joinEmail = joinTokens[6];
						if(!(userDao.isExist(joinId, joinNickname))){
							userDao.writeUser(new User(joinNickname, joinName, joinId, joinpw, joingender, joinBirth, joinEmail));
							sendMessage(Message.RESPONSE_JOIN + Message.DELIMETER + "TRUE");
						}else{
							sendMessage(Message.RESPONSE_JOIN + Message.DELIMETER + "FALSE");
						}
						
						break;
						
					// 아이디 찾기 요청
					case Message.REQUEST_FINDID:
//						String name = tokens[0];
						String info = tokens[1];
						String[] tokenss = info.split(",");
						String name = tokenss[0];
						String birth = tokenss[1];
						String email = tokenss[2];
						if(userDao.getUserID(name, birth ,email) != null){
							sendMessage(Message.RESPONSE_FINDID + Message.DELIMETER + "TRUE" +Message.DELIMETER+ userDao.getUserID(name,birth, email).getUserId());
						}else{
							sendMessage(Message.RESPONSE_FINDID + Message.DELIMETER + "FALSE");
						}
						break;
						
					case Message.REQUEST_FINDPW:
						String info1 = tokens[1];
						String[] tokenss1 = info1.split(",");
						String userid = tokenss1[0];
						String birth1 = tokenss1[1];
						String email1 = tokenss1[2];
						if(userDao.getUserPW(userid, birth1 ,email1) != null){
							sendMessage(Message.RESPONSE_FINDPW + Message.DELIMETER + "TRUE" +Message.DELIMETER+ userDao.getUserPW(userid, birth1, email1).getPasswd());
						}else{
							sendMessage(Message.RESPONSE_FINDPW + Message.DELIMETER + "FALSE");
						}
						break;
					
						
					// 대화방생성 메시지
					case Message.REQUEST_CREATROOM:
						String chatroom = tokens[1];
						String[] chatroomInfo = chatroom.split("\\,");
						String roomName = chatroomInfo[0];
						int roomMaxUser = Integer.parseInt(chatroomInfo[1]);
						int isRock = Integer.parseInt(chatroomInfo[2]);
						String password = chatroomInfo[3];
						String roomOwner = this.getNickName();
						
//						public Room(String roomName, int roomMaxUser, boolean isRock, String password, String roomOwner, MessageHandler client){

//			            room = new Room(roomName, roomMaxUser, isRock, password, roomOwner, this);
			            chatServer.removeClient(this);
			            chatServer.addClientChat(roomName, roomMaxUser, isRock, password, roomOwner, this);
			            room = chatServer.getRoomByOwner(roomOwner);
			            String nicksss = chatServer.getClientNickNames();
			            chatServer.sendToAll(Message.RESPONSE_LIST
								+ Message.DELIMETER + nickName + Message.DELIMETER
								+ nicksss);
			            String nicksroom = room.getClientNickNames();
			            int joinNumber = room.getClientNumber();
			            int joinisRock = room.getisRock();
			            
			            
			            	room.sendToAll(Message.RESPONSE_JOINLIST + Message.DELIMETER + nickName + Message.DELIMETER
								+ nicksroom);
						//if(대화방 이름이 중복이 아닐 경우){
							chatServer.sendToAll(Message.RESPONSE_CREATROOM + Message.DELIMETER + "TRUE" + Message.DELIMETER + roomName + "," + roomOwner + "," + joinNumber + "," + roomMaxUser + "," + joinisRock);
					//	}else{
							//sendMessage(Message.RESPONSE_CREATROOM + Message.DELIMETER + "FALSE");
					//	}
						break;
						
					//대화방참여 메시지
					case Message.REQUEST_JOINROOM:
						String isRocks = tokens[1];
						
						String ownerRoom = tokens[2];
						room = chatServer.getRoomByOwner(ownerRoom);
						if(room.getroomMaxUser() == room.getClientNumber()){
							sendMessage(Message.RESPONSE_JOINROOM + Message.DELIMETER + "FALSE" + Message.DELIMETER + "FULL" + Message.DELIMETER + ownerRoom);
							break;
						}
						if(isRocks.equals("UNROCK")){
							chatServer.removeClient(this);
							chatServer.joinRoom(ownerRoom, this);
							String nickssss = chatServer.getClientNickNames();
							chatServer.sendToAll(Message.RESPONSE_LIST
								+ Message.DELIMETER + nickName + Message.DELIMETER
								+ nickssss);
							String nicksroom2 = room.getClientNickNames();
							
							room.sendToAll(Message.RESPONSE_JOINLIST + Message.DELIMETER + nickName + Message.DELIMETER + nicksroom2);
							//행 , 열 정보를 준다.
							chatServer.sendToAll(Message.RESPONSE_JOINROOM + Message.DELIMETER + "TRUE" + Message.DELIMETER + ownerRoom);
							chatServer.sendToAll(Message.RESPONSE_ROOMLIST + Message.DELIMETER + chatServer.getAllRoomInfo());
						}else{
							String receivepassword = tokens[3];
							if(!room.getpassword().equals(receivepassword)){
								sendMessage(Message.RESPONSE_JOINROOM + Message.DELIMETER + "FALSE" + Message.DELIMETER +"ISNOT" + Message.DELIMETER + ownerRoom);
								break;
							}else{
								chatServer.removeClient(this);
//								room.getpassword().equals()
								chatServer.joinRoom(ownerRoom, this);
								String nickssss = chatServer.getClientNickNames();
								chatServer.sendToAll(Message.RESPONSE_LIST
									+ Message.DELIMETER + nickName + Message.DELIMETER
									+ nickssss);
								String nicksroom2 = room.getClientNickNames();
							
								room.sendToAll(Message.RESPONSE_JOINLIST + Message.DELIMETER + nickName + Message.DELIMETER + nicksroom2);
								//행 , 열 정보를 준다.
								chatServer.sendToAll(Message.RESPONSE_JOINROOM + Message.DELIMETER + "TRUE" + Message.DELIMETER + ownerRoom);
								chatServer.sendToAll(Message.RESPONSE_ROOMLIST + Message.DELIMETER + chatServer.getAllRoomInfo());
							}
							
						}
						break;
					
					
					// 대화방상세정보 요청 메시지
					case Message.REQUEST_DETAILROOM:
						String roomOwnerName = tokens[1];
						room = chatServer.getRoomByOwner(roomOwnerName);
						sendMessage(Message.RESPONSE_DETAILROOM + Message.DELIMETER + room.getClientNickNames());
						break;
						
					// 대기방 로그아웃 요청 메시지
					case Message.REQUEST_WAITROOMOUT:
						chatServer.removeClient(this);
						chatServer.sendToAll(Message.RESPONSE_WAITROOMOUT
								+ Message.DELIMETER + nickName);
						String nickss = chatServer.getClientNickNames();
						chatServer.sendToAll(Message.RESPONSE_LIST
								+ Message.DELIMETER + nickName + Message.DELIMETER
								+ nickss);
						
						break;
						
					// 참가자 정보 요청
					case Message.REQUEST_PERSONINFOR:
						String nickname = tokens[1];
						sendMessage(Message.RESPONSE_PERSONINFOR + Message.DELIMETER + userDao.getUserList(nickname));
						
						
						break;
						
					//대화방 참가자 정보 요청
						
					case Message.REQUEST_ROOM_PERSONINFOR:
						nickname = tokens[1];
						userDao.getUserList(nickname);
						
						sendMessage(Message.RESPONSE_ROOM_PERSONINFOR + Message.DELIMETER + userDao.getUserList(nickname));
						
						break;
						
					// 대화방 로그아웃 요청
					case Message.REQUEST_CHATROOMOUT:
						chatServer.addClient(this);
						room.removeClient(this);
						String nicksOut = chatServer.getClientNickNames();
			            chatServer.sendToAll(Message.RESPONSE_LIST
								+ Message.DELIMETER + nickName + Message.DELIMETER
								+ nicksOut);
			            String nicksroomOut = room.getClientNickNames();
			            chatServer.sendToAll(Message.RESPONSE_ROOMLIST + Message.DELIMETER + chatServer.getAllRoomInfo());
			            room.sendToAll(Message.RESPONSE_JOINLIST + Message.DELIMETER + nickName + Message.DELIMETER
								+ nicksroomOut);
			            chatServer.sendToAll(Message.RESPONSE_ROOMLIST + Message.DELIMETER + chatServer.getAllRoomInfo());
						break;
						
					//대화방 테이블 값 보여주기
					case Message.REQUEST_ROOMLIST:
						chatServer.sendToAll(Message.RESPONSE_ROOMLIST + Message.DELIMETER + chatServer.getAllRoomInfo());
						break;
						
					// 대기방채팅 메시지
					case Message.REQUEST_WAITROOMCHAT:
						String chatMessage = tokens[1];
						// 접속한 모든 클라이언트들에게 채팅메시지 릴레이(201|*|대화명|*|채팅메시지)
						chatServer.sendToAll(Message.RESPONSE_WAITROOMCHAT + Message.DELIMETER + nickName + Message.DELIMETER + chatMessage);
						break;
						
						
					// 채팅방 귓속말
					case Message.REQUEST_CHATTO:
						boolean equal = false;
						String roomNickNames = room.getClientNickNames();
						String[] enterRoomId = roomNickNames.split("\\,");
						String chatMessageTo = tokens[1];
						String toId = tokens[2];
						if(toId.equals(nickName)){
							room.sendToMe(nickName, Message.RESPONSE_CHATTO + Message.DELIMETER  +"FALSE" + Message.DELIMETER + "SAME" + Message.DELIMETER + toId);
							break;
						}
						for (String string : enterRoomId) {
							if (string.equals(toId)){ 
								room.sendToOne(toId, Message.RESPONSE_CHATTO + Message.DELIMETER +"TRUE" + Message.DELIMETER + "TO" + Message.DELIMETER + nickName + Message.DELIMETER + chatMessageTo);
								room.sendToMe(nickName, Message.RESPONSE_CHATTO + Message.DELIMETER +"TRUE" + Message.DELIMETER + "FOR"  + Message.DELIMETER + toId + Message.DELIMETER + chatMessageTo);
								equal = true;
								break;
							}
						}
						if(equal) break;
						room.sendToMe(nickName, Message.RESPONSE_CHATTO + Message.DELIMETER  +"FALSE" + Message.DELIMETER + "NOBODY" + Message.DELIMETER + toId);
						
						break;
					
					//대기방 귓속말
					case Message.REQUEST_WAITCHATTO:
						boolean equalW = false;
						String waitNickNames = chatServer.getClientNickNames();
						String[] enterwaitId = waitNickNames.split("\\,");
						String chatMessageToW = tokens[1];
						String toIdW = tokens[2];
						if(toIdW.equals(nickName)){ 
							chatServer.sendToMe(nickName, Message.RESPONSE_WAITCHATTO + Message.DELIMETER  +"FALSE" + Message.DELIMETER + "SAME" + Message.DELIMETER + toIdW);
							break;
						}
						for (String string : enterwaitId) {
							if (string.equals(toIdW)){ 
								chatServer.sendToOne(toIdW, Message.RESPONSE_WAITCHATTO + Message.DELIMETER +"TRUE" + Message.DELIMETER + "TO" + Message.DELIMETER + nickName + Message.DELIMETER + chatMessageToW);
								chatServer.sendToMe(nickName, Message.RESPONSE_WAITCHATTO + Message.DELIMETER +"TRUE" + Message.DELIMETER + "FOR"  + Message.DELIMETER + toIdW + Message.DELIMETER + chatMessageToW);
								equalW = true;
								break;
							}
						}
						if(equalW) break;
						chatServer.sendToMe(nickName, Message.RESPONSE_WAITCHATTO + Message.DELIMETER  +"FALSE" + Message.DELIMETER + "NOBODY" + Message.DELIMETER + toIdW);
						
						break;
						
						// 채팅방 메시지
					case Message.REQUEST_CHAT:
						String chatMessageRoom = tokens[1];
						room.sendToAll(Message.RESPONSE_CHAT + Message.DELIMETER + nickName + Message.DELIMETER + chatMessageRoom);
						break;
						
					// 서버연결끊기 메시지
					case Message.REQUEST_CLIENT_DISCONNECT:
						// 서버에서 클라이언트 제거
						chatServer.removeClient(this);
						// 접속한 모든 클라이언트들에게 클라이언트 접속 끊기 알림(901|*|대화명)
						chatServer.sendToAll(Message.RESPONSE_CLIENT_CONNECT + Message.DELIMETER + nickName);
						// 접속자 목록 전송
						String nicks = chatServer.getClientNickNames();
						chatServer.sendToAll(Message.RESPONSE_LIST + Message.DELIMETER + nickName + Message.DELIMETER + nicks);
						// 리소스 해제
						closeResource();
						break;
				}
			}
		}catch(IOException e){
			closeResource();
		}
	}
	
	/** 
	 * 대화방 참여 중복 메소드
	 */
	public void JoinRoom(){
		
	}
	
	/**
	 * 자기자신에게 메시지 전송
	 * @throws IOException 
	 */
	public void sendMessage(String message) throws IOException{
		out.writeUTF(message);		
	}
	
	/**
	 * 소켓 닫기
	 */
	public void closeResource(){
		try{
			if(in != null) {in.close();}
			if(out != null){out.close();}
			if(socket != null){	socket.close();}
		}catch(IOException e){}
				
	}
	public void run(){
		try {
			handleMessage();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
