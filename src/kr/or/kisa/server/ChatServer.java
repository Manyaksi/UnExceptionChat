package kr.or.kisa.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

public class ChatServer {
	
	private int port;
	private ServerSocket serverSocket;
	private boolean stop;
	
	// 접속한 클라이언트 관리를 Collection
	private Hashtable<String, MessageHandler> clients;
	private Hashtable<String, Room> clientsroom;
	
	public ChatServer() {
		this(8888);
	}
	
	public ChatServer(int port) {
		this.port = port;
		clients = new Hashtable<String, MessageHandler>();
		clientsroom = new Hashtable<String, Room>();
	}
	
	public int getPort() {
		return port;
	}

	public boolean isStop() {
		return stop;
	}

	public void setStop(boolean stop) {
		this.stop = stop;
	}

	public ServerSocket getServerSocket() {
		return serverSocket;
	}
	
	/**
	 * 서버 시작
	 * @throws IOException 
	 */
	public void startServer() throws IOException{
		serverSocket = new ServerSocket(port);
		String serverIp = InetAddress.getLocalHost().getHostAddress();
		System.out.println("ChatSever["+serverIp+" :"+port+"] Start..");
		
		// 원격클라이언트의 연결 수신
		while(!stop){
			Socket socket = serverSocket.accept();
			String clientIp = socket.getInetAddress().getHostAddress();
			System.out.println("Client["+clientIp+"] Connect..");
			
			MessageHandler client = new MessageHandler(this, socket);
			client.start();
		}
	}

	/**
	 * 서버 연결 완료 메시지 전송
	 * @throws IOException 
	 */
	public void sendToClient(String message) throws IOException{
		Socket socket = serverSocket.accept();
		MessageHandler client = new MessageHandler(this, socket);
		client.sendMessage(message);
	}
	
	
	/**
	 * 접속한 모든 클라이언트들에게 메시지 전송
	 * @throws IOException 
	 */
	public void sendToAll(String message) throws IOException{
		Enumeration<String> keys = clients.keys();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			MessageHandler client = clients.get(key);
			client.sendMessage(message);
		}
	}
	
	/**
	 * 귓속말 보내기
	 * @param targetNickName
	 * @param sendOneMessage
	 * @throws IOException
	 */
	public void sendToOne(String targetNickName, String sendOneMessage) throws IOException {
		Enumeration<MessageHandler> e = clients.elements();
		while (e.hasMoreElements()) {
			MessageHandler client = (MessageHandler) e.nextElement();
			if (client.getNickName().equals(targetNickName)) {
				client.sendMessage(sendOneMessage);
			}
		}
	}
	/**
	 * 자신에게..
	 * @param attackNickName
	 * @param sendOneMessage
	 * @throws IOException
	 */
	public void sendToMe(String attackNickName, String sendOneMessage) throws IOException {
		Enumeration<MessageHandler> e = clients.elements();
		while (e.hasMoreElements()) {
			MessageHandler client = (MessageHandler) e.nextElement();
			if (client.getNickName().equals(attackNickName)) {
				client.sendMessage(sendOneMessage);
			}
		}
	}
	
	/**
	 * 대화방 처음 생성부분이랄까
	 */
	public Room getRoomInfo(String owner, MessageHandler client){
		if(clientsroom.containsKey(client.getNickName())){
			return null;
		}
		Room room = getRoomByOwner(owner);
		return room;
	}
	
	public Room getRoomByOwner(String owner){
		Enumeration<String> keys = clientsroom.keys();
		while (keys.hasMoreElements()) {
			String string = (String) keys.nextElement();
			if(string.equals(owner)){
				return clientsroom.get(string);
				
			}
		}
		return null;
	}
	
	/** 대화방에 접속한 클라이언트들에게 메시지 전송
	 * @throws IOException 
	 */
	public void sendToRoom(String message) throws IOException{
		Enumeration<String> keys = clients.keys();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			MessageHandler client = clients.get(key);
			client.sendMessage(message);
		}
	}
	
	/**
	 * 방 들어갈게요
	 */
	public void joinRoom(String owner, MessageHandler client){
		Room room = getRoomByOwner(owner);
		room.joinRoom(client);
		
	}
	
	/**
	 * 방 정보 실시간
	 * @return
	 */
	public String getAllRoomInfo(){
	      if (!clientsroom.isEmpty()) {
	         Enumeration<String> keys = clientsroom.keys();
	         String roomInfos = "";
	         while (keys.hasMoreElements()) {
	            String key = (String) keys.nextElement();
	            Room room = clientsroom.get(key);
	            roomInfos += room.getRoomName()+","+room.getRoomOwner()+","+room.getClientNumber() + "," + room.getroomMaxUser() +","+room.getisRock()+"//";
	            
	         }
	         return roomInfos;
	      }
	      return null;
	      
	   }
	
	
	/**
	 * 연결 클라이언트 저장 (연결 닉네임 및 성별 저장)
	 */
	public boolean addClient(MessageHandler client){
		if(clients.containsKey(client.getNickName())){
			return false;
		}
		clients.put(client.getNickName(), client);
		System.out.println("현재 서버에 연결된 클라이언트 수 : " + clients.size());
		return true;
	}
	
	/**
	 * 대화방 연결 클라이언트 저장 (연결 닉네임 및 성별 저장) 아직 안 함. 구상중
	 */
	public void addClientChat(String roomName, int roomMaxUser, int isRock, String password, String roomOwner, MessageHandler client){

		clientsroom.put(client.getNickName(), new Room(roomName, roomMaxUser, isRock, password, roomOwner, client));
		System.out.println("현재 대화방에 연결된 클라이언트 수 : " + clientsroom.size());
	}
	
	/**
	 * 연결 해제 클라이언트 제거
	 */
	public void removeClient(MessageHandler client){
		if(!(clients.size() == 0)){
			clients.remove(client.getNickName());
		}
	}
	
	/**
	 * 연결 클라이언트들의 대화명 목록을 CSV로 반환
	 */
	public String getClientNickNames(){
		StringBuilder sb = new StringBuilder();
		Enumeration<String> keys = clients.keys();
		while (keys.hasMoreElements()) {
			String nickName = keys.nextElement();
			sb.append(nickName+",");
		}
		return sb.toString();
	}
	
	
	/**
	 * 서버 종료
	 */
	public void stopServer(){
		try {
			if(serverSocket != null) serverSocket.close();
		} catch (IOException e) {}
	}
}
