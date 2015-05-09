package kr.or.kisa.server;

/**
 * 메시지 종류를 나타내는 상수 집합
 * @author 김기정
 *
 */
public class Message {
	
	public static final String DELIMETER = "|*|";
	
	public static final String REQUEST_CLIENT_CONNECT = "000";
	public static final String RESPONSE_CLIENT_CONNECT = "001";
	
	public static final String REQUEST_LOGIN = "100";
	public static final String RESPONSE_LOGIN = "101";
	public static final String RESPONSE_LIST = "102";
	
	public static final String REQUEST_JOIN = "110";
	public static final String RESPONSE_JOIN = "111";
	
	public static final String REQUEST_FINDID = "120";
	public static final String RESPONSE_FINDID = "121";
	
	public static final String REQUEST_FINDPW = "130";
	public static final String RESPONSE_FINDPW = "131";
	
	public static final String REQUEST_CREATROOM = "200";
	public static final String RESPONSE_CREATROOM = "201";

	public static final String REQUEST_JOINROOM = "210";
	public static final String RESPONSE_JOINROOM = "211";
	public static final String RESPONSE_JOINLIST = "212";
	
	public static final String REQUEST_DETAILROOM = "220";
	public static final String RESPONSE_DETAILROOM = "221";
	
	public static final String REQUEST_WAITROOMOUT = "230";
	public static final String RESPONSE_WAITROOMOUT = "231";
	
	public static final String REQUEST_PERSONINFOR = "300";
	public static final String RESPONSE_PERSONINFOR = "301";
	
	public static final String REQUEST_ROOM_PERSONINFOR = "310";
	public static final String RESPONSE_ROOM_PERSONINFOR = "311";

	public static final String REQUEST_CHATROOMOUT = "330";
	public static final String RESPONSE_CHATROOMOUT = "331";
	
	public static final String RESPONSE_ROOMLIST = "340";
	public static final String REQUEST_ROOMLIST = "341";
	
	public static final String REQUEST_WAITROOMCHAT = "400";
	public static final String RESPONSE_WAITROOMCHAT = "401";
	public static final String REQUEST_WAITCHATTO = "500";
	public static final String RESPONSE_WAITCHATTO = "501";
	public static final String REQUEST_CHATTO = "510";
	public static final String RESPONSE_CHATTO = "511";
	public static final String REQUEST_CHAT = "600";
	public static final String RESPONSE_CHAT = "601";
	
	public static final String REQUEST_FILESEND = "610";
	public static final String RESPONSE_FILESEND = "611";
	
	public static final String REQUEST_CLIENT_DISCONNECT = "900";
	public static final String RESPONSE_CLIENT_DISCONNECT = "901";
}
