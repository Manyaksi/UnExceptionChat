package kr.or.kisa.client.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolTip;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.metal.MetalToolTipUI;

import kr.or.kisa.client.model.RoomTableModel;
import kr.or.kisa.server.Message;

public class WaitRoomPanel extends JPanel {
	ChatUI ui;

	GridBagLayout gridBagLayout;
	GridBagConstraints constraints;

	RoomTableModel model;

	JLabel teamNameL, userLisetL, UnexceptionL, ChatL, kisaL, unExceptionL,
			chatL, logoImg;
	JTextField messageTF;
	JButton positionB, creationB, informationB, logoutB, sendB;
	JTextArea messageTA;
	JList userList;

	JTable table;

	JScrollPane messageTaSp, tableSp, userListSp;
	String roomOwner;
	String isRock;
	JPanel southNullP, inputMessageP, buttonP, tableP, northNullP, westNullP,
			eastNullP, logoP, centerP, chatP, userListP;
	DefaultListModel<String> data;

	public WaitRoomPanel(ChatUI ui) {
		this.ui = ui;
		data = new DefaultListModel<String>();

		northNullP = new JPanel();
		northNullP.setBackground(Color.WHITE);

		westNullP = new JPanel();
		westNullP.setBackground(Color.WHITE);

		eastNullP = new JPanel();
		eastNullP.setBackground(Color.WHITE);

		logoP = new JPanel();
		logoP.setBackground(Color.WHITE);

		unExceptionL = new JLabel("UnException");
		unExceptionL.setFont(new Font("³ª´®°íµñ", Font.PLAIN, 13));

		chatL = new JLabel("Chat", SwingConstants.LEADING);
		chatL.setFont(new Font("³ª´®°íµñ", Font.BOLD, 13));

		logoImg = new JLabel("");
		logoImg.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
			}
		});
		logoImg.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(
				getClass().getResource("/images/waitChatB.png"))));

		centerP = new JPanel();
		centerP.setBackground(Color.WHITE);

		chatP = new JPanel();
		chatP.setBorder(new LineBorder(Color.BLACK));
		chatP.setBackground(Color.WHITE);

		messageTaSp = new JScrollPane();
		messageTaSp.setBorder(new EmptyBorder(0, 0, 0, 0));

		messageTA = new JTextArea();
		messageTA.setFont(new Font("³ª´®°íµñ", Font.PLAIN, 10));
		messageTA.setBorder(new EmptyBorder(0, 0, 0, 0));
		messageTaSp.setViewportView(messageTA);

		userListP = new JPanel();
		userListP.setBackground(Color.WHITE);

		userListSp = new JScrollPane();
		userListSp.setBorder(new EmptyBorder(0, 0, 0, 0));

		userList = new JList(){
			public JToolTip createToolTip(){
				MultiLineToolTip tip = new MultiLineToolTip();
				tip.setComponent(this);
				return tip;
				}
		};
		
		userList.setBorder(new TitledBorder(new MatteBorder(1, 1, 1, 1,
				(Color) new Color(0, 0, 0)), "\uB300\uAE30\uC790\uBAA9\uB85D",
				TitledBorder.LEFT, TitledBorder.TOP, null, Color.BLACK));
		userListSp.setViewportView(userList);

		tableP = new JPanel();
		tableP.setBackground(Color.WHITE);

		tableSp = new JScrollPane();
		tableSp.setBackground(Color.WHITE);
		tableSp.setBorder(new TitledBorder(new MatteBorder(1, 1, 1, 1,
				(Color) new Color(0, 0, 0)), "\uB300\uD654\uBC29\uBAA9\uB85D",
				TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLACK));

		model = new RoomTableModel();
		table = new JTable();
		table.setModel(model);
		table.setBackground(Color.WHITE);
		table.setBorder(new EmptyBorder(0, 0, 0, 0));
		table.getColumnModel().getColumn(0).setPreferredWidth(50);
		table.getColumnModel().getColumn(1).setPreferredWidth(50);
		table.getColumnModel().getColumn(2).setPreferredWidth(10);
		table.getColumnModel().getColumn(3).setPreferredWidth(10);
		table.getColumnModel().getColumn(4).setPreferredWidth(20);

		tableSp.setViewportView(table);
		tableSp.getViewport().setBackground(table.getBackground());
		buttonP = new JPanel();
		buttonP.setBackground(Color.WHITE);

		positionB = new JButton("´ëÈ­¹æÀÔÀå");
		positionB.setPreferredSize(new Dimension(95, 50));
		positionB.setMinimumSize(new Dimension(95, 50));
		positionB.setMaximumSize(new Dimension(95, 50));
		positionB.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				positionB.setBackground(Color.lightGray);
				positionB.setForeground(Color.white);
			}

			public void mouseExited(java.awt.event.MouseEvent evt) {
				positionB.setForeground(Color.white);
				positionB.setBackground(Color.BLACK);
			}
		});
		positionB.setForeground(Color.white);
		positionB.setBackground(Color.BLACK);
		positionB.setFont(new Font("³ª´®°íµñ", Font.BOLD, 10));
		positionB.setOpaque(true);
		positionB.setBorderPainted(false);

		creationB = new JButton("´ëÈ­¹æ»ý¼º");
		creationB.setMaximumSize(new Dimension(95, 29));
		creationB.setMinimumSize(new Dimension(95, 50));
		creationB.setPreferredSize(new Dimension(95, 50));
		creationB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		creationB.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				creationB.setBackground(Color.lightGray);
				creationB.setForeground(Color.white);
			}

			public void mouseExited(java.awt.event.MouseEvent evt) {
				creationB.setForeground(Color.white);
				creationB.setBackground(Color.BLACK);
			}
		});
		creationB.setFont(new Font("³ª´®°íµñ", Font.BOLD, 10));
		creationB.setBackground(Color.BLACK);
		creationB.setForeground(Color.WHITE);
		creationB.setOpaque(true);
		creationB.setBorderPainted(false);

		informationB = new JButton("¹æ»ó¼¼Á¤º¸º¸±â");
		informationB.setMaximumSize(new Dimension(115, 50));
		informationB.setPreferredSize(new Dimension(110, 50));
		informationB.setMinimumSize(new Dimension(115, 50));
		informationB.setFont(new Font("³ª´®°íµñ", Font.BOLD, 10));
		informationB.setForeground(Color.WHITE);
		informationB.setBackground(Color.BLACK);
		informationB.setOpaque(true);
		informationB.setBorderPainted(false);
		informationB.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				informationB.setBackground(Color.lightGray);
				informationB.setForeground(Color.white);
			}

			public void mouseExited(java.awt.event.MouseEvent evt) {
				informationB.setForeground(Color.white);
				informationB.setBackground(Color.BLACK);
			}
		});

		logoutB = new JButton("³ª°¡±â");
		logoutB.setPreferredSize(new Dimension(77, 50));
		logoutB.setMinimumSize(new Dimension(77, 50));
		logoutB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		logoutB.setFont(new Font("³ª´®°íµñ", Font.BOLD, 10));
		logoutB.setForeground(Color.WHITE);
		logoutB.setBackground(Color.BLACK);
		logoutB.setOpaque(true);
		logoutB.setBorderPainted(false);
		logoutB.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				logoutB.setBackground(Color.lightGray);
				logoutB.setForeground(Color.white);
			}

			public void mouseExited(java.awt.event.MouseEvent evt) {
				logoutB.setForeground(Color.white);
				logoutB.setBackground(Color.BLACK);
			}
		});

		inputMessageP = new JPanel();
		inputMessageP.setBackground(Color.WHITE);
		inputMessageP.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));

		messageTF = new JTextField();
		messageTF.setPreferredSize(new Dimension(14, 35));
		messageTF.setFont(new Font("³ª´®°íµñ", Font.PLAIN, 15));
		messageTF.setBorder(null);
		messageTF.setColumns(10);

		sendB = new JButton("Àü¼Û");
		sendB.setMinimumSize(new Dimension(75, 30));
		sendB.setPreferredSize(new Dimension(75, 35));
		sendB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});

		sendB.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				sendB.setBackground(Color.lightGray);
				sendB.setForeground(Color.white);
			}

			public void mouseExited(java.awt.event.MouseEvent evt) {
				sendB.setForeground(Color.white);
				sendB.setBackground(Color.BLACK);
			}
		});
		sendB.setFont(new Font("³ª´®°íµñ", Font.BOLD, 10));
		sendB.setForeground(Color.WHITE);
		sendB.setBackground(Color.BLACK);
		sendB.setOpaque(true);
		sendB.setBorderPainted(false);

		southNullP = new JPanel();
		southNullP.setBackground(Color.WHITE);

		teamNameL = new JLabel("ÐþïÒÞÀãù");
		teamNameL.setFont(new Font("³ª´®°íµñ", Font.PLAIN, 10));

	}

	// ¹èÄ¡°ü¸®ÀÚ ¼³Á¤ ¹× ÄÄÆ÷³ÍÆ® ¹èÄ¡
	public void setContents() {
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 10, 200, 5, 230, 60, 15, 0 };
		gbl_contentPane.rowHeights = new int[] { 20, 28, 440, 15, 20, 0 };
		gbl_contentPane.columnWeights = new double[] { 1.0, 1.0, 1.0, 1.0, 1.0,
				0.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 1.0, 1.0, 1.0, 1.0, 1.0,
				Double.MIN_VALUE };
		this.setLayout(gbl_contentPane);

		this.setBounds(100, 100, 800, 600);
		this.setBackground(Color.WHITE);
		this.setBorder(new EmptyBorder(0, 0, 0, 0));

		GridBagConstraints gbc_northNullP = new GridBagConstraints();
		gbc_northNullP.gridwidth = 4;
		gbc_northNullP.insets = new Insets(0, 0, 0, 0);
		gbc_northNullP.fill = GridBagConstraints.BOTH;
		gbc_northNullP.gridx = 1;
		gbc_northNullP.gridy = 0;
		this.add(northNullP, gbc_northNullP);

		GridBagConstraints gbc_westNullP = new GridBagConstraints();
		gbc_westNullP.gridheight = 5;
		gbc_westNullP.insets = new Insets(0, 0, 0, 0);
		gbc_westNullP.fill = GridBagConstraints.BOTH;
		gbc_westNullP.gridx = 0;
		gbc_westNullP.gridy = 0;
		this.add(westNullP, gbc_westNullP);

		GridBagConstraints gbc_eastNullP = new GridBagConstraints();
		gbc_eastNullP.gridheight = 5;
		gbc_eastNullP.fill = GridBagConstraints.BOTH;
		gbc_eastNullP.gridx = 5;
		gbc_eastNullP.gridy = 0;
		this.add(eastNullP, gbc_eastNullP);

		GridBagConstraints gbc_logoP = new GridBagConstraints();
		gbc_logoP.insets = new Insets(0, 0, 0, 0);
		gbc_logoP.fill = GridBagConstraints.BOTH;
		gbc_logoP.gridx = 1;
		gbc_logoP.gridy = 1;
		this.add(logoP, gbc_logoP);

		GridBagLayout gbl_logoP = new GridBagLayout();
		gbl_logoP.columnWidths = new int[] { 88, 76, 0, 0 };
		gbl_logoP.rowHeights = new int[] { 35, 0 };
		gbl_logoP.columnWeights = new double[] { 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		gbl_logoP.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		logoP.setLayout(gbl_logoP);

		GridBagConstraints gbc_unExceptionL = new GridBagConstraints();
		gbc_unExceptionL.insets = new Insets(0, 0, 2, 3);
		gbc_unExceptionL.anchor = GridBagConstraints.SOUTHEAST;
		gbc_unExceptionL.gridx = 0;
		gbc_unExceptionL.gridy = 0;
		logoP.add(unExceptionL, gbc_unExceptionL);

		JLabel chatL = new JLabel("Chat", SwingConstants.LEADING);
		GridBagConstraints gbc_chatL = new GridBagConstraints();
		gbc_chatL.anchor = GridBagConstraints.SOUTHWEST;
		gbc_chatL.insets = new Insets(0, 0, 2, 250);
		gbc_chatL.gridx = 1;
		gbc_chatL.gridy = 0;
		logoP.add(chatL, gbc_chatL);

		GridBagConstraints gbc_logoImg = new GridBagConstraints();
		gbc_logoImg.anchor = GridBagConstraints.SOUTHWEST;
		gbc_logoImg.gridx = 2;
		gbc_logoImg.gridy = 0;
		logoP.add(logoImg, gbc_logoImg);

		GridBagConstraints gbc_centerP = new GridBagConstraints();
		gbc_centerP.gridheight = 3;
		gbc_centerP.insets = new Insets(0, 0, 0, 0);
		gbc_centerP.fill = GridBagConstraints.BOTH;
		gbc_centerP.gridx = 2;
		gbc_centerP.gridy = 1;
		this.add(centerP, gbc_centerP);

		GridBagConstraints gbc_chatP = new GridBagConstraints();
		gbc_chatP.gridheight = 2;
		gbc_chatP.insets = new Insets(0, 0, 5, 0);
		gbc_chatP.fill = GridBagConstraints.BOTH;
		gbc_chatP.gridx = 3;
		gbc_chatP.gridy = 1;
		this.add(chatP, gbc_chatP);

		GridBagLayout gbl_chatP = new GridBagLayout();
		gbl_chatP.columnWidths = new int[] { 0, 0 };
		gbl_chatP.rowHeights = new int[] { 0, 0 };
		gbl_chatP.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_chatP.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		chatP.setLayout(gbl_chatP);

		GridBagConstraints gbc_messageTaSp = new GridBagConstraints();
		gbc_messageTaSp.insets = new Insets(10, 10, 10, 10);
		gbc_messageTaSp.fill = GridBagConstraints.BOTH;
		gbc_messageTaSp.gridx = 0;
		gbc_messageTaSp.gridy = 0;
		chatP.add(messageTaSp, gbc_messageTaSp);

		GridBagConstraints gbc_userListP = new GridBagConstraints();
		gbc_userListP.gridheight = 2;
		gbc_userListP.insets = new Insets(0, 0, 5, 0);
		gbc_userListP.fill = GridBagConstraints.BOTH;
		gbc_userListP.gridx = 4;
		gbc_userListP.gridy = 1;
		this.add(userListP, gbc_userListP);

		GridBagLayout gbl_userListP = new GridBagLayout();
		gbl_userListP.columnWidths = new int[] { 150, 0 };
		gbl_userListP.rowHeights = new int[] { 0, 0 };
		gbl_userListP.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
		gbl_userListP.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		userListP.setLayout(gbl_userListP);

		GridBagConstraints gbc_userListSp = new GridBagConstraints();
		gbc_userListSp.insets = new Insets(0, 10, 0, 0);
		gbc_userListSp.fill = GridBagConstraints.BOTH;
		gbc_userListSp.gridheight = 2;
		gbc_userListSp.gridwidth = 2;
		gbc_userListSp.gridx = 0;
		gbc_userListSp.gridy = 0;
		userListP.add(userListSp, gbc_userListSp);

		GridBagConstraints gbc_tableP = new GridBagConstraints();
		gbc_tableP.insets = new Insets(0, 0, 0, 0);
		gbc_tableP.fill = GridBagConstraints.BOTH;
		gbc_tableP.gridx = 1;
		gbc_tableP.gridy = 2;
		this.add(tableP, gbc_tableP);

		GridBagLayout gbl_tableP = new GridBagLayout();
		gbl_tableP.columnWidths = new int[] { 0, 0 };
		gbl_tableP.rowHeights = new int[] { 0, 0 };
		gbl_tableP.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_tableP.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		tableP.setLayout(gbl_tableP);

		GridBagConstraints gbc_tableSp = new GridBagConstraints();
		gbc_tableSp.insets = new Insets(0, 0, 10, 0);
		gbc_tableSp.fill = GridBagConstraints.BOTH;
		gbc_tableSp.gridx = 0;
		gbc_tableSp.gridy = 0;
		tableP.add(tableSp, gbc_tableSp);

		GridBagConstraints gbc_buttonP = new GridBagConstraints();
		gbc_buttonP.fill = GridBagConstraints.BOTH;
		gbc_buttonP.gridx = 1;
		gbc_buttonP.gridy = 3;
		this.add(buttonP, gbc_buttonP);

		GridBagLayout gbl_buttonP = new GridBagLayout();
		gbl_buttonP.columnWidths = new int[] { 89, 89, 107, 75, 0 };
		gbl_buttonP.rowHeights = new int[] { 20 };
		gbl_buttonP.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		gbl_buttonP.rowWeights = new double[] { 0.0 };
		buttonP.setLayout(gbl_buttonP);

		GridBagConstraints gbc_positionB = new GridBagConstraints();
		gbc_positionB.fill = GridBagConstraints.BOTH;
		gbc_positionB.insets = new Insets(0, 2, 0, 5);
		gbc_positionB.gridx = 0;
		gbc_positionB.gridy = 0;
		buttonP.add(positionB, gbc_positionB);

		GridBagConstraints gbc_creationB = new GridBagConstraints();
		gbc_creationB.insets = new Insets(0, 0, 0, 5);
		gbc_creationB.gridx = 1;
		gbc_creationB.gridy = 0;
		buttonP.add(creationB, gbc_creationB);

		GridBagConstraints gbc_informationB = new GridBagConstraints();
		gbc_informationB.insets = new Insets(0, 0, 0, 5);
		gbc_informationB.gridx = 2;
		gbc_informationB.gridy = 0;
		buttonP.add(informationB, gbc_informationB);

		GridBagConstraints gbc_logoutB = new GridBagConstraints();
		gbc_logoutB.gridx = 3;
		gbc_logoutB.gridy = 0;
		buttonP.add(logoutB, gbc_logoutB);

		GridBagConstraints gbc_inputMessageP = new GridBagConstraints();
		gbc_inputMessageP.gridwidth = 2;
		gbc_inputMessageP.fill = GridBagConstraints.BOTH;
		gbc_inputMessageP.gridx = 3;
		gbc_inputMessageP.gridy = 3;
		this.add(inputMessageP, gbc_inputMessageP);

		GridBagLayout gbl_inputMessageP = new GridBagLayout();
		gbl_inputMessageP.columnWidths = new int[] { 0, 0, 0 };
		gbl_inputMessageP.rowHeights = new int[] { 0, 0 };
		gbl_inputMessageP.columnWeights = new double[] { 1.0, 0.0,
				Double.MIN_VALUE };
		gbl_inputMessageP.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		inputMessageP.setLayout(gbl_inputMessageP);

		GridBagConstraints gbc_messageTF = new GridBagConstraints();
		gbc_messageTF.insets = new Insets(6, 10, 0, 10);
		gbc_messageTF.fill = GridBagConstraints.HORIZONTAL;
		gbc_messageTF.gridx = 0;
		gbc_messageTF.gridy = 0;
		inputMessageP.add(messageTF, gbc_messageTF);
		messageTF.setColumns(10);

		GridBagConstraints gbc_sendB = new GridBagConstraints();
		gbc_sendB.insets = new Insets(7, 0, 0, 5);
		gbc_sendB.gridx = 1;
		gbc_sendB.gridy = 0;
		inputMessageP.add(sendB, gbc_sendB);

		GridBagConstraints gbc_southNullP = new GridBagConstraints();
		gbc_southNullP.insets = new Insets(0, 0, 0, 0);
		gbc_southNullP.gridwidth = 4;
		gbc_southNullP.fill = GridBagConstraints.BOTH;
		gbc_southNullP.gridx = 1;
		gbc_southNullP.gridy = 4;
		this.add(southNullP, gbc_southNullP);

		GridBagLayout gbl_southNullP = new GridBagLayout();
		gbl_southNullP.columnWidths = new int[] { 780, 0 };
		gbl_southNullP.rowHeights = new int[] { 0, 0 };
		gbl_southNullP.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
		gbl_southNullP.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		southNullP.setLayout(gbl_southNullP);

		GridBagConstraints gbc_teamNameL = new GridBagConstraints();
		gbc_teamNameL.insets = new Insets(0, 0, 0, 20);
		gbc_teamNameL.anchor = GridBagConstraints.EAST;
		gbc_teamNameL.gridx = 0;
		gbc_teamNameL.gridy = 0;
		southNullP.add(teamNameL, gbc_teamNameL);
	}

	public void eventRegist() {

		userList.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				if (!e.getValueIsAdjusting()) {
					String selection = (String) userList.getSelectedValue();
					String message = Message.REQUEST_PERSONINFOR
							+ Message.DELIMETER + selection;
					ui.uisendMessage(message);

				}
			}
		});

		messageTF.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ui.sendChatMessage();

			}
		});

		creationB.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				CreateRoomDisplay createRoom = new CreateRoomDisplay(ui);

			}
		});

		sendB.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				ui.sendChatMessage();
			}
		});

		logoutB.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				ui.change("¼±¹Î");
				ui.setSize(400, 600);
				ui.sendLogoutMessage();
				messageTA.setText("");
				messageTF.setText("");
				// ui.closeResource();
			}
		});

		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int row = -1;
				row = table.getSelectedRow();
				if (row == -1) {
					return;
				}
				roomOwner = model.getOwner(row, 1);
				isRock = model.getisRock(row, 4);

			}
		});

		positionB.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (isRock == "°ø°³") {
					String message = Message.REQUEST_JOINROOM
							+ Message.DELIMETER + "UNROCK" + Message.DELIMETER
							+ roomOwner;
					ui.sendJoinRoom(message);
					ui.setSize(400, 600);
					ui.change("¹Î¼ö");
				} else if (isRock == "ºñ°ø°³") {

					String pw = JOptionPane.showInputDialog("ºñ¹Ð¹øÈ£¸¦ ÀÔ·ÂÇÏ¼¼¿ä");
					String message = Message.REQUEST_JOINROOM
							+ Message.DELIMETER + "ROCK" + Message.DELIMETER
							+ roomOwner + Message.DELIMETER + pw;
					ui.sendJoinRoom(message);
					ui.setSize(400, 600);
					ui.change("¹Î¼ö");
				} else {

				}
			}
		});

		informationB.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (roomOwner != null) {
					String message = Message.REQUEST_DETAILROOM
							+ Message.DELIMETER + roomOwner;
					ui.uisendMessage(message);
				}
			}
		});
		

	}

	private void add(Component c, int x, int y, int w, int h) {

		constraints.gridx = x;
		constraints.gridy = y;
		constraints.gridwidth = w;
		constraints.gridheight = h;
		gridBagLayout.setConstraints(c, constraints);
		this.add(c);
	}

	class MultiLineToolTip extends JToolTip {
		public MultiLineToolTip() {
			setUI(new MultiLineToolTipUI());
		}
	}

	class MultiLineToolTipUI extends MetalToolTipUI {
		private String[] strs;

		private int maxWidth = 0;

		public void paint(Graphics g, JComponent c) {
			FontMetrics metrics = Toolkit.getDefaultToolkit().getFontMetrics(
					g.getFont());
			Dimension size = c.getSize();
			g.setColor(c.getBackground());
			g.fillRect(0, 0, size.width, size.height);
			g.setColor(c.getForeground());
			if (strs != null) {
				for (int i = 0; i < strs.length; i++) {
					g.drawString(strs[i], 3, (metrics.getHeight()) * (i + 1));
				}
			}
		}

		public Dimension getPreferredSize(JComponent c) {
			FontMetrics metrics = Toolkit.getDefaultToolkit().getFontMetrics(
					c.getFont());
			String tipText = ((JToolTip) c).getTipText();
			if (tipText == null) {
				tipText = "";
			}
			BufferedReader br = new BufferedReader(new StringReader(tipText));
			String line;
			int maxWidth = 0;
			Vector v = new Vector();
			try {
				while ((line = br.readLine()) != null) {
					int width = SwingUtilities
							.computeStringWidth(metrics, line);
					maxWidth = (maxWidth < width) ? width : maxWidth;
					v.addElement(line);
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			int lines = v.size();
			if (lines < 1) {
				strs = null;
				lines = 1;
			} else {
				strs = new String[lines];
				int i = 0;
				for (Enumeration e = v.elements(); e.hasMoreElements(); i++) {
					strs[i] = (String) e.nextElement();
				}
			}
			int height = metrics.getHeight() * lines;
			this.maxWidth = maxWidth;
			return new Dimension(maxWidth + 6, height + 4);
		}
	}
}