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
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolTip;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.metal.MetalToolTipUI;

import kr.or.kisa.server.Message;

public class chatRoomPanel extends JPanel {
	ChatUI ui;
	GridBagLayout gridBagLayout;
	GridBagConstraints gridC;

	JLabel unExceptionL, chatL, tip, kisaL, sendFileL;
	JTextArea chatTA;
	JList userList;
	// JComboBox<String> chatType;
	JTextField messageTF;
	JButton exitB, sendFileB, tipB, sendB;
	JScrollPane chatTaSp, chatAreaSp, chatTfTaSp, userListSp;
	JPanel southNullP, northNullP, westNullP, eastNullP, upFuctionP, chatP,
			userListP, inputMessageP;

	Shape shape;

	JScrollPane jp;

	public chatRoomPanel(ChatUI ui) {
		this.ui = ui;
		this.ui = ui;

		northNullP = new JPanel();
		northNullP.setBackground(Color.WHITE);

		westNullP = new JPanel();
		westNullP.setBackground(Color.WHITE);

		eastNullP = new JPanel();
		eastNullP.setBackground(Color.WHITE);

		upFuctionP = new JPanel();
		upFuctionP.setBackground(Color.WHITE);

		unExceptionL = new JLabel("UnException");
		unExceptionL.setFont(new Font("나눔고딕", Font.PLAIN, 13));

		chatL = new JLabel("Chat");
		chatL.setFont(new Font("나눔고딕", Font.BOLD, 13));

		tipB = new JButton("");
		tipB.setBorderPainted(false);
		tipB.setBackground(Color.WHITE);
		tipB.setBorder(new EmptyBorder(0, 0, 0, 0));
		tipB.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(
				getClass().getResource("/images/tip.png"))));

		sendFileB = new JButton("");
		sendFileB.setSize(new Dimension(10, 10));
		sendFileB.setBackground(Color.WHITE);
		sendFileB.setBounds(new Rectangle(0, 0, 10, 10));
		sendFileB.setMaximumSize(new Dimension(10, 10));
		sendFileB.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(
				getClass().getResource("/images/fileIcon.png"))));
		sendFileB.setBorderPainted(false);
		sendFileB.setBorder(new EmptyBorder(0, 0, 0, 0));

		exitB = new JButton("");
		exitB.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(
				getClass().getResource("/images/exit.png"))));
		exitB.setBackground(Color.WHITE);
		exitB.setSize(new Dimension(10, 10));
		exitB.setMaximumSize(new Dimension(10, 10));
		exitB.setBounds(new Rectangle(0, 0, 10, 10));
		exitB.setBorderPainted(false);
		exitB.setBorder(new EmptyBorder(0, 0, 0, 0));

		chatP = new JPanel();
		chatP.setBorder(new LineBorder(new Color(0, 0, 0)));
		chatP.setBackground(Color.WHITE);

		chatTaSp = new JScrollPane();
		chatTaSp.setBorder(new EmptyBorder(0, 0, 0, 0));

		
		chatTA = new JTextArea();
		chatTA.setBorder(new EmptyBorder(0, 0, 0, 0));
		chatTaSp.setViewportView(chatTA);

		userListP = new JPanel();
		userListP.setBorder(new EmptyBorder(0, 0, 0, 0));
		userListP.setFont(new Font("나눔고딕", Font.PLAIN, 10));
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
				(Color) new Color(0, 0, 0)), "\uC811\uC18D\uC790\uBAA9\uB85D",
				TitledBorder.LEADING, TitledBorder.TOP, null,
				new Color(0, 0, 0)));
		userListSp.setViewportView(userList);

		inputMessageP = new JPanel();
		inputMessageP.setBackground(Color.WHITE);
		inputMessageP.setBackground(Color.WHITE);
		inputMessageP.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));

		messageTF = new JTextField();
		messageTF.setBorder(new EmptyBorder(0, 0, 0, 0));
		messageTF.setFont(new Font("나눔고딕", Font.PLAIN, 23));

		sendB = new JButton("전송");
		sendB.setMinimumSize(new Dimension(50, 50));
		sendB.setMaximumSize(new Dimension(50, 50));
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
		sendB.setFont(new Font("나눔고딕", Font.BOLD, 10));
		sendB.setForeground(Color.WHITE);
		sendB.setBackground(Color.BLACK);
		sendB.setOpaque(true);
		sendB.setBorderPainted(false);

		southNullP = new JPanel();
		southNullP.setBackground(Color.WHITE);

	}

	public void setContents() {
		GridBagLayout gbl_this = new GridBagLayout();
		gbl_this.columnWidths = new int[] { 10, 250, 100, 10, 0 };
		gbl_this.rowHeights = new int[] { 10, 0, 400, 50, 10, 0 };
		gbl_this.columnWeights = new double[] { 1.0, 1.0, 1.0, 0.0,
				Double.MIN_VALUE };
		gbl_this.rowWeights = new double[] { 1.0, 1.0, 1.0, 1.0, 1.0,
				Double.MIN_VALUE };
		this.setLayout(gbl_this);
		this.setBounds(100, 100, 400, 600);
		this.setBackground(Color.WHITE);
		this.setBorder(new EmptyBorder(0, 0, 0, 0));

		GridBagConstraints gbc_northNullP = new GridBagConstraints();
		gbc_northNullP.gridwidth = 2;
		gbc_northNullP.insets = new Insets(0, 0, 5, 5);
		gbc_northNullP.fill = GridBagConstraints.BOTH;
		gbc_northNullP.gridx = 1;
		gbc_northNullP.gridy = 0;
		this.add(northNullP, gbc_northNullP);

		GridBagLayout gbl_northNullP = new GridBagLayout();
		gbl_northNullP.columnWidths = new int[] { 0 };
		gbl_northNullP.rowHeights = new int[] { 0 };
		gbl_northNullP.columnWeights = new double[] { Double.MIN_VALUE };
		gbl_northNullP.rowWeights = new double[] { Double.MIN_VALUE };
		northNullP.setLayout(gbl_northNullP);

		GridBagConstraints gbc_westNullP = new GridBagConstraints();
		gbc_westNullP.gridheight = 5;
		gbc_westNullP.insets = new Insets(0, 0, 0, 5);
		gbc_westNullP.fill = GridBagConstraints.BOTH;
		gbc_westNullP.gridx = 0;
		gbc_westNullP.gridy = 0;
		this.add(westNullP, gbc_westNullP);

		GridBagLayout gbl_westNullP = new GridBagLayout();
		gbl_westNullP.columnWidths = new int[] { 0 };
		gbl_westNullP.rowHeights = new int[] { 0 };
		gbl_westNullP.columnWeights = new double[] { Double.MIN_VALUE };
		gbl_westNullP.rowWeights = new double[] { Double.MIN_VALUE };
		westNullP.setLayout(gbl_westNullP);

		GridBagConstraints gbc_eastNullP = new GridBagConstraints();
		gbc_eastNullP.gridheight = 5;
		gbc_eastNullP.fill = GridBagConstraints.BOTH;
		gbc_eastNullP.gridx = 3;
		gbc_eastNullP.gridy = 0;
		this.add(eastNullP, gbc_eastNullP);

		GridBagLayout gbl_eastNullP = new GridBagLayout();
		gbl_eastNullP.columnWidths = new int[] { 0 };
		gbl_eastNullP.rowHeights = new int[] { 0 };
		gbl_eastNullP.columnWeights = new double[] { Double.MIN_VALUE };
		gbl_eastNullP.rowWeights = new double[] { Double.MIN_VALUE };
		eastNullP.setLayout(gbl_eastNullP);

		GridBagConstraints gbc_upFuctionP = new GridBagConstraints();
		gbc_upFuctionP.gridwidth = 2;
		gbc_upFuctionP.fill = GridBagConstraints.BOTH;
		gbc_upFuctionP.gridx = 1;
		gbc_upFuctionP.gridy = 1;
		this.add(upFuctionP, gbc_upFuctionP);

		GridBagLayout gbl_upFuctionP = new GridBagLayout();
		gbl_upFuctionP.columnWidths = new int[] { 0, 0, 0, 0, 0, 0 };
		gbl_upFuctionP.rowHeights = new int[] { 0, 0 };
		gbl_upFuctionP.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		gbl_upFuctionP.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		upFuctionP.setLayout(gbl_upFuctionP);

		GridBagConstraints gbc_unExceptionL = new GridBagConstraints();
		gbc_unExceptionL.anchor = GridBagConstraints.SOUTH;
		gbc_unExceptionL.insets = new Insets(0, 10, 5, 2);
		gbc_unExceptionL.gridx = 0;
		gbc_unExceptionL.gridy = 0;
		upFuctionP.add(unExceptionL, gbc_unExceptionL);

		GridBagConstraints gbc_chatL = new GridBagConstraints();
		gbc_chatL.anchor = GridBagConstraints.SOUTH;
		gbc_chatL.insets = new Insets(0, 0, 5, 180);
		gbc_chatL.gridx = 1;
		gbc_chatL.gridy = 0;
		upFuctionP.add(chatL, gbc_chatL);

		GridBagConstraints gbc_tipB = new GridBagConstraints();
		gbc_tipB.insets = new Insets(0, 0, 0, 5);
		gbc_tipB.gridx = 2;
		gbc_tipB.gridy = 0;
		upFuctionP.add(tipB, gbc_tipB);

		GridBagConstraints gbc_sendFileB = new GridBagConstraints();
		gbc_sendFileB.insets = new Insets(0, 0, 0, 5);
		gbc_sendFileB.gridx = 3;
		gbc_sendFileB.gridy = 0;
		upFuctionP.add(sendFileB, gbc_sendFileB);

		GridBagConstraints gbc_exitB = new GridBagConstraints();
		gbc_exitB.gridx = 4;
		gbc_exitB.gridy = 0;
		upFuctionP.add(exitB, gbc_exitB);

		GridBagConstraints gbc_chatP = new GridBagConstraints();
		gbc_chatP.insets = new Insets(0, 1, 5, 1);
		gbc_chatP.fill = GridBagConstraints.BOTH;
		gbc_chatP.gridx = 1;
		gbc_chatP.gridy = 2;
		this.add(chatP, gbc_chatP);

		GridBagLayout gbl_chatP = new GridBagLayout();
		gbl_chatP.columnWidths = new int[] { 0, 0 };
		gbl_chatP.rowHeights = new int[] { 0, 0 };
		gbl_chatP.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_chatP.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		chatP.setLayout(gbl_chatP);

		GridBagConstraints gbc_chatTaSp = new GridBagConstraints();
		gbc_chatTaSp.insets = new Insets(15, 15, 15, 15);
		gbc_chatTaSp.fill = GridBagConstraints.BOTH;
		gbc_chatTaSp.gridx = 0;
		gbc_chatTaSp.gridy = 0;
		chatP.add(chatTaSp, gbc_chatTaSp);

		GridBagConstraints gbc_userListP = new GridBagConstraints();
		gbc_userListP.insets = new Insets(0, 5, 5, 0);
		gbc_userListP.fill = GridBagConstraints.BOTH;
		gbc_userListP.gridx = 2;
		gbc_userListP.gridy = 2;
		this.add(userListP, gbc_userListP);

		GridBagLayout gbl_userListP = new GridBagLayout();
		gbl_userListP.columnWidths = new int[] { 0, 0 };
		gbl_userListP.rowHeights = new int[] { 0, 0 };
		gbl_userListP.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_userListP.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		userListP.setLayout(gbl_userListP);

		GridBagConstraints gbc_userListSp = new GridBagConstraints();
		gbc_userListSp.insets = new Insets(0, 0, 0, 3);
		gbc_userListSp.fill = GridBagConstraints.BOTH;
		gbc_userListSp.gridx = 0;
		gbc_userListSp.gridy = 0;
		userListP.add(userListSp, gbc_userListSp);

		GridBagConstraints gbc_inputMessageP = new GridBagConstraints();
		gbc_inputMessageP.gridwidth = 2;
		gbc_inputMessageP.insets = new Insets(0, 0, 5, 5);
		gbc_inputMessageP.fill = GridBagConstraints.BOTH;
		gbc_inputMessageP.gridx = 1;
		gbc_inputMessageP.gridy = 3;
		this.add(inputMessageP, gbc_inputMessageP);

		GridBagLayout gbl_inputMessageP = new GridBagLayout();
		gbl_inputMessageP.columnWidths = new int[] { 0, 0, 0 };
		gbl_inputMessageP.rowHeights = new int[] { 62, 0 };
		gbl_inputMessageP.columnWeights = new double[] { 1.0, 0.0,
				Double.MIN_VALUE };
		gbl_inputMessageP.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		inputMessageP.setLayout(gbl_inputMessageP);

		GridBagConstraints gbc_messageTF = new GridBagConstraints();
		gbc_messageTF.insets = new Insets(0, 10, 0, 10);
		gbc_messageTF.fill = GridBagConstraints.HORIZONTAL;
		gbc_messageTF.gridx = 0;
		gbc_messageTF.gridy = 0;
		inputMessageP.add(messageTF, gbc_messageTF);
		messageTF.setColumns(10);

		GridBagConstraints gbc_sendB = new GridBagConstraints();
		gbc_sendB.fill = GridBagConstraints.BOTH;
		gbc_sendB.weighty = 0.5;
		gbc_sendB.insets = new Insets(10, 0, 10, 10);
		gbc_sendB.gridx = 1;
		gbc_sendB.gridy = 0;
		inputMessageP.add(sendB, gbc_sendB);

		GridBagConstraints gbc_southNullP = new GridBagConstraints();
		gbc_southNullP.gridwidth = 2;
		gbc_southNullP.insets = new Insets(0, 0, 0, 5);
		gbc_southNullP.fill = GridBagConstraints.BOTH;
		gbc_southNullP.gridx = 1;
		gbc_southNullP.gridy = 4;
		this.add(southNullP, gbc_southNullP);

		GridBagLayout gbl_southNullP = new GridBagLayout();
		gbl_southNullP.columnWidths = new int[] { 0 };
		gbl_southNullP.rowHeights = new int[] { 0 };
		gbl_southNullP.columnWeights = new double[] { Double.MIN_VALUE };
		gbl_southNullP.rowWeights = new double[] { Double.MIN_VALUE };
		southNullP.setLayout(gbl_southNullP);

	}

	public void eventRegist() {
		userList.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				if (!e.getValueIsAdjusting()) {
					String selection = (String) userList.getSelectedValue();
					String message = Message.REQUEST_ROOM_PERSONINFOR
							+ Message.DELIMETER + selection;
					ui.uisendMessage(message);
				}
			}
		});
		exitB.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String message = Message.REQUEST_CHATROOMOUT
						+ Message.DELIMETER;
				ui.sendRoomOutMessage(message);
				setSize(800, 600);
				messageTF.setText("");
				chatTA.setText("");
			}
		});

		messageTF.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ui.sendChatMessageRoom();

			}
		});
		sendB.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				ui.sendChatMessageRoom();

			}
		});
		sendFileB.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String idFileTo;
				if ((idFileTo = JOptionPane
						.showInputDialog("누구에게 파일을 보내시겠습니까?")) != null) {
					ui.sendFileMessage(idFileTo);
					JOptionPane.showMessageDialog(null, "서비스 점검중입니다.", "점검중",
							JOptionPane.WARNING_MESSAGE);
				}

			}
		});
		tipB.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane
						.showMessageDialog(null, "<귓속말 보내는 방법>" + "\n"
								+ "/w 귓속말대상 할말", "사용법",
								JOptionPane.INFORMATION_MESSAGE);
			}
		});

	}

	private void add(Component com, int gridx, int gridy, int gridwidth,
			int gridheight) {
		gridC.gridx = gridx;
		gridC.gridy = gridy;
		gridC.gridwidth = gridwidth;
		gridC.gridheight = gridheight;
		gridBagLayout.setConstraints(com, gridC);
		add(com);

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
