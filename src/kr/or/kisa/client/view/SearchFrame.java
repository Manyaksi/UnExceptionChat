package kr.or.kisa.client.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import kr.or.kisa.client.model.ChatClient;
import kr.or.kisa.server.Message;

public class SearchFrame extends JFrame {
	ChatClient chatClient;
	ChatUI ui;

	SearchID searchID;
	SearchP searchP;

	JPanel main;

	CardLayout cardLayout;
	
	public SearchFrame(ChatUI ui){
		this.ui=ui;
	}

	public SearchFrame(String title, ChatUI ui) {
		super(title);
		this.ui = ui;

		chatClient = new ChatClient();

		cardLayout = new CardLayout();

		searchID = new SearchID(this);
		searchP = new SearchP(this);

		main = new JPanel();
		GUIUtil.setCenterScreen(this);
	}

	public void setContents() {

		searchID.setContents();
		searchP.setContents();
		// eventRegist
		searchID.eventRegist();
		searchP.eventRegist();

		main.setLayout(cardLayout);

		main.add("���̵�", searchID);
		main.add("���", searchP);

		add(main, BorderLayout.CENTER);
		pack();
		// setSize(400, 600);
	}

	public void sendSearchID() {
		
		String message = Message.REQUEST_FINDID + Message.DELIMETER
				+ searchID.nameTF.getText()+","
				+ searchID.yearType.getSelectedItem()+"��"
				+ searchID.monthType.getSelectedItem()+"��"
				+ searchID.dayType.getSelectedItem()+"��,"
				+ searchID.emailTF.getText();
		ui.uisendMessage(message);
	}
	
	public void sendSearchPW(){
		String message = Message.REQUEST_FINDPW + Message.DELIMETER
				+ searchP.idTF.getText()+","
				+ searchP.yearType.getSelectedItem()+"��"
				+ searchP.monthType.getSelectedItem()+"��"
				+ searchP.dayType.getSelectedItem()+"��,"
				+searchP.emailTF.getText();
		ui.uisendMessage(message);
	}
	

	public void change(String name) {
		cardLayout.show(main, name);
	}

	/*
	 * public static void main(String[] args) { searchFrame sf = new
	 * searchFrame(" ã��"); sf.setContents(); sf.setVisible(true); }
	 */

}
