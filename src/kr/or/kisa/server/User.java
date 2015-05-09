package kr.or.kisa.server;

import java.io.Serializable;

public class User  implements Serializable{

	private String nickName;
	private String userName;
	private String userId;
	private String passwd;
	private byte gender;
	private String birth;
	private String email;
	
	
	
	public User(){
		this(null, null, null, null, (byte) 0, null, null);
	}
	public User(String nickName, String userName, String userId, String passwd,
			byte gender, String birth, String email) {
		super();
		this.nickName = nickName;
		this.userName = userName;
		this.userId = userId;
		this.passwd = passwd;
		this.gender = gender;
		this.birth = birth;
		this.email = email;
	}
	
	
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public byte getGender() {
		return gender;
	}
	public void setGender(byte gender) {
		this.gender = gender;
	}
	public String getBirth() {
		return birth;
	}
	public void setBirth(String birth) {
		this.birth = birth;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String infoo(){
		String sung = null;
		if(gender ==0){
			sung = "남";
		}else if(gender ==1){
			sung = "여";
		}
		return "닉네임 : " + nickName + "\n이름 : " + userName
				+ "\n아이디 : " + userId + "\n성별 : " + sung + "\n생일 : "
				+ birth + "\nE-Mail : " + email;
	}

	
	
	
}
