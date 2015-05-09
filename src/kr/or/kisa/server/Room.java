package kr.or.kisa.server;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
// hashtable
public class Room {
	private Vector<MessageHandler> clients; // 방 안에 들어온 사용자
	private Hashtable<String, Room> userhash; 
	public static int roomNumber = 0; // 방 번호
	private String roomName; // 방 이름
	private int roomMaxUer; // 방 최대 인원수
	private int roomUser; //몇명이 사용중인지
	private int isRock; //비공개인가 공개인가
	private String password; //비공개였을 경우 비밀번호
	private String roomOwner; //운영자는 누구신가요.
	
	public Room(String roomName, int roomMaxUser, int isRock, String password, String roomOwner, MessageHandler client){
		clients = new Vector<MessageHandler>(roomMaxUser, 2);
		this.roomName = roomName;
		this.roomMaxUer = roomMaxUser;
		this.roomUser = 0;
		this.isRock = isRock;
		this.password = password;
		this.roomOwner = roomOwner;
		this.userhash = new Hashtable<String, Room>(roomMaxUser);
		clients.add(client);
	}

	public Room(String roomname2, MessageHandler client) {
		clients = new Vector<MessageHandler>(5, 2);
		this.roomName = roomname2;
		clients.add(client);
	}

	public Room(String roomname2, int isRock, MessageHandler client) {
		clients = new Vector<MessageHandler>(5, 2);
		this.roomName = roomname2;
		this.isRock = isRock;
		clients.add(client);
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getRoomOwner() {
		return roomOwner;
	}

	public void setRoomOwner(String roomOwner) {
		this.roomOwner = roomOwner;
	}
	
	public int getisRock(){
		return isRock;
	}
	public String getpassword(){
		return password;
	}
	public int getroomMaxUser(){
		return roomMaxUer;
	}
	
	public void joinRoom(MessageHandler client){
		clients.add(client);
		System.out.println(clients.size());
	}
	
	public void sendToAll(String message) throws IOException{
		Enumeration<MessageHandler> e = clients.elements();
		while (e.hasMoreElements()) {
			MessageHandler client = (MessageHandler) e.nextElement();
			client.sendMessage(message);
		}
	}
	public void sendToOne(String targetNickName, String sendOneMessage) throws IOException {
		Enumeration<MessageHandler> e = clients.elements();
		while (e.hasMoreElements()) {
			MessageHandler client = (MessageHandler) e.nextElement();
			if (client.getNickName().equals(targetNickName)) {
				client.sendMessage(sendOneMessage);
			}
		}
	}
	public void sendToMe(String attackNickName, String sendOneMessage) throws IOException {
		Enumeration<MessageHandler> e = clients.elements();
		while (e.hasMoreElements()) {
			MessageHandler client = (MessageHandler) e.nextElement();
			if (client.getNickName().equals(attackNickName)) {
				client.sendMessage(sendOneMessage);
			}
		}
	}
	
	public int getClientNumber(){
		return clients.size();
	}
	public String getClientNickNames(){
		if (!clients.isEmpty()) {
			String nickNames = "";
			Enumeration<MessageHandler> e = clients.elements();
			while (e.hasMoreElements()) {
				MessageHandler client = (MessageHandler) e.nextElement();
				nickNames += (client.getNickName()+",");
			}
			return nickNames;
		}
		return null;
	}

	
	public void removeClient(MessageHandler client){
		clients.remove(client);
	}

	
	

}
