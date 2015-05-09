package kr.or.kisa.client.model;

public class Room {
	private String roomName;
	private String roomMaker;
	private int enterCount;
	private int maxCount;
	private int isRock;
	private String password;

	public Room(String roomName, String roomMaker, int enterCount,
			int maxCount, int isRock) {
		this.roomName = roomName;
		this.roomMaker = roomMaker;
		this.enterCount = enterCount;
		this.maxCount = maxCount;
		this.isRock = isRock;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getRoomMaker() {
		return roomMaker;
	}

	public void setRoomMaker(String roomMaker) {
		this.roomMaker = roomMaker;
	}

	public int getEnterCount() {
		return enterCount;
	}

	public int getMaxCount() {
		return maxCount;
	}

	public int getisRock() {
		return isRock;
	}

	public void setEnterCount(int enterCount) {
		this.enterCount = enterCount;
	}

}
