
package kr.or.kisa.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Enumeration;
import java.util.Vector;

public class UserDao {

	/** ���� ���� ��� */
	private static final String FILE_PATH = "users.ser";
	File file ;


	/**
    * ȸ�� ���� ��
    * @return
    * @throws Exception
    */
	public int getUserCount() throws Exception{
		return readUsers() != null ? readUsers().size() : 0;
	}


	/**
	 * ����� ���� ���� ����
	 * 
	 * @param user
	 * @throws IOException
	 * @throws ClassNotFoundException 
	 */
	
	//������ ���ٸ�...
	
	public void writeUser(User user) throws IOException, ClassNotFoundException {
		// ���Ͽ� ����� ����� ������ �б�
		Vector<User> users =  readUsers();
		
		if(users == null){
			users = new Vector<User>();
		}
		users.addElement(user);
		ObjectOutputStream out = null;
		try{
			out = new ObjectOutputStream(new FileOutputStream(FILE_PATH));
			out.writeObject(users);
		}finally{
			if(out != null) out.close();
		}
	}
	
	/**
	 * ȸ�� ���� ����
	 * @param id
	 * @return
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public boolean isExist(String id, String nickName) throws ClassNotFoundException, IOException{
		isExistFile(FILE_PATH);
		Vector<User> users =  readUsers();
		Enumeration<User> e =  users.elements();
		while (e.hasMoreElements()) {
			User user = e.nextElement();
			if(user.getUserId().equals(id) || user.getNickName().equals(nickName)){
				return true;
			}
		}
		return false;
	}


	
	/**
	 * ��ü ����� ����
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public Vector<User> readUsers() throws IOException, ClassNotFoundException {
		ObjectInputStream in = null;
		Vector<User> users = null;
		try{
			File file = new File(FILE_PATH);
			if(file.exists()){
				in = new ObjectInputStream(new FileInputStream(file));
				users = (Vector<User>) in.readObject();
			}
		}finally{
			if(in != null){
				in.close();
			}
		}
		return users;
	}


	/**
	 * ����� ���� �� ����� ���� ��ȯ
	 * @param id
	 * @param pw
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public User getUser(String id, String pw) throws IOException, ClassNotFoundException {
		isExistFile(FILE_PATH);
		Vector<User> users =  readUsers();
		Enumeration<User> e =  users.elements();
		while (e.hasMoreElements()) {
			User user = e.nextElement();
			if(user.getUserId().equals(id) && user.getPasswd().equals(pw)){
				return user;
			}
			
		}
		return null;
	}
	
	/**
	 * ������ ���̵� ����
	 *
	 */
	public User getUserID(String userName, String birth, String eMailaddress) throws IOException, ClassNotFoundException {
		isExistFile(FILE_PATH);
		Vector<User> users =  readUsers();
		Enumeration<User> e =  users.elements();
		while (e.hasMoreElements()) {
			User user = e.nextElement();
			if(user.getEmail().equals(eMailaddress)&& user.getUserName().equals(userName)
					&& user.getBirth().equals(birth)){
				return user;
			}
			
		}
		return null;
	}
	
	/**
	 * ������ �н����� ����
	 */
	public User getUserPW(String userid, String birth, String eMailaddress) throws IOException, ClassNotFoundException {
		isExistFile(FILE_PATH);
		Vector<User> users =  readUsers();
		Enumeration<User> e =  users.elements();
		while (e.hasMoreElements()) {
			User user = e.nextElement();
			if(user.getEmail().equals(eMailaddress)&& user.getUserId().equals(userid)
					&& user.getBirth().equals(birth)){
				return user;
			}
			
		}
		return null;
	}
	
	/**
	 * �г��� ������ ��� ���� ����
	 */
/*	public User getUser(String nickName) throws IOException, ClassNotFoundException {
		isExistFile(FILE_PATH);
		Vector<User> users =  readUsers();
		Enumeration<User> e =  users.elements();
		while (e.hasMoreElements()) {
			User user = e.nextElement();
			if(user.getUserId().equals(nickName)){
				return user;
			}
			
		}
		return null;
	}*/
	public String getUserList(String nickName) throws IOException, ClassNotFoundException {
		isExistFile(FILE_PATH);
		Vector<User> users =  readUsers();
		Enumeration<User> e =  users.elements();
		while (e.hasMoreElements()) {
			User user = e.nextElement();
			if(user.getNickName().equals(nickName)){
				return user.infoo();
			}
			
		}
		return null;
	}
	
	
	/**
	 * ������ ���� ��� ��� ����
	 * @param path
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private void isExistFile(String path) throws IOException, ClassNotFoundException{
		File file = new File(path);
		if(!file.exists()){
			writeUser(new User("���", "admin", "���", "1234", (byte)0 , "ggg", "2323"));
		}
	}

}
