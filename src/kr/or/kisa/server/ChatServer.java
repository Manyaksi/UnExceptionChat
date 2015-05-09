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
	
	// ������ Ŭ���̾�Ʈ ������ Collection
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
	 * ���� ����
	 * @throws IOException 
	 */
	public void startServer() throws IOException{
		serverSocket = new ServerSocket(port);
		String serverIp = InetAddress.getLocalHost().getHostAddress();
		System.out.println("ChatSever["+serverIp+" :"+port+"] Start..");
		
		// ����Ŭ���̾�Ʈ�� ���� ����
		while(!stop){
			Socket socket = serverSocket.accept();
			String clientIp = socket.getInetAddress().getHostAddress();
			System.out.println("Client["+clientIp+"] Connect..");
			
			MessageHandler client = new MessageHandler(this, socket);
			client.start();
		}
	}

	/**
	 * ���� ���� �Ϸ� �޽��� ����
	 * @throws IOException 
	 */
	public void sendToClient(String message) throws IOException{
		Socket socket = serverSocket.accept();
		MessageHandler client = new MessageHandler(this, socket);
		client.sendMessage(message);
	}
	
	
	/**
	 * ������ ��� Ŭ���̾�Ʈ�鿡�� �޽��� ����
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
	 * �ӼӸ� ������
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
	 * �ڽſ���..
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
	 * ��ȭ�� ó�� �����κ��̶���
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
	
	/** ��ȭ�濡 ������ Ŭ���̾�Ʈ�鿡�� �޽��� ����
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
	 * �� ���Կ�
	 */
	public void joinRoom(String owner, MessageHandler client){
		Room room = getRoomByOwner(owner);
		room.joinRoom(client);
		
	}
	
	/**
	 * �� ���� �ǽð�
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
	 * ���� Ŭ���̾�Ʈ ���� (���� �г��� �� ���� ����)
	 */
	public boolean addClient(MessageHandler client){
		if(clients.containsKey(client.getNickName())){
			return false;
		}
		clients.put(client.getNickName(), client);
		System.out.println("���� ������ ����� Ŭ���̾�Ʈ �� : " + clients.size());
		return true;
	}
	
	/**
	 * ��ȭ�� ���� Ŭ���̾�Ʈ ���� (���� �г��� �� ���� ����) ���� �� ��. ������
	 */
	public void addClientChat(String roomName, int roomMaxUser, int isRock, String password, String roomOwner, MessageHandler client){

		clientsroom.put(client.getNickName(), new Room(roomName, roomMaxUser, isRock, password, roomOwner, client));
		System.out.println("���� ��ȭ�濡 ����� Ŭ���̾�Ʈ �� : " + clientsroom.size());
	}
	
	/**
	 * ���� ���� Ŭ���̾�Ʈ ����
	 */
	public void removeClient(MessageHandler client){
		if(!(clients.size() == 0)){
			clients.remove(client.getNickName());
		}
	}
	
	/**
	 * ���� Ŭ���̾�Ʈ���� ��ȭ�� ����� CSV�� ��ȯ
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
	 * ���� ����
	 */
	public void stopServer(){
		try {
			if(serverSocket != null) serverSocket.close();
		} catch (IOException e) {}
	}
}
