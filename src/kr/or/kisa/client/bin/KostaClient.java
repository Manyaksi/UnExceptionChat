package kr.or.kisa.client.bin;

import kr.or.kisa.client.view.ChatUI;
import kr.or.kisa.client.view.GUIUtil;




public class KostaClient {

	public static void main(String[] args) {
		ChatUI chatUI = new ChatUI("UnExeption Chat");
		chatUI.eventRegist();
		GUIUtil.setCenterScreen(chatUI);
	}

}
