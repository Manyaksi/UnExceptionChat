package kr.or.kisa.client.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import kr.or.kisa.client.model.ChatClient;

/**
 * 사용자 로그인화면 (클라이언트 초기화면) UI
 * 
 * @author 곽선민
 *
 */

public class LoginPanel extends JPanel {
	ChatUI ui;
	ChatClient chatClient;

	GridBagLayout gridBagLayout;// GridBagLayout
	GridBagConstraints constraints;
	JLabel idL, passwdL; // idL(아이디 라벨), passwdL(패스워드 라벨), logoL(로고라벨)
	JTextField idTF;
	JPasswordField passwdTF;
	JButton loginB, joinB, searchB; // loginB(로그인버튼), registB(회원가입버튼),
									// searchId(아이디찾기버튼)
	Image bgImg, btImg;

	public LoginPanel(ChatUI ui) {
		this.ui = ui;

		idL = new JLabel("아이디");
		idL.setFont(new Font("나눔고딕", Font.PLAIN, 10));

		idTF = new JTextField();
		idTF.setMinimumSize(new Dimension(10, 40));
		idTF.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		idTF.setOpaque(true);

		idTF.setFocusCycleRoot(true);
		idTF.setFocusTraversalKeysEnabled(false);
		idTF.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				idTF.setBackground(Color.WHITE);
			}
		});
		idTF.setMargin(new Insets(0, 5, 0, 0));
		idTF.setBackground(UIManager.getColor("CheckBox.background"));
		idTF.setPreferredSize(new Dimension(10, 40));
		idTF.setBorder(new MatteBorder(1, 1, 0, 1, (Color) new Color(128, 128,
				128)));
		idTF.setFont(new Font("나눔고딕", Font.PLAIN, 20));
		idTF.requestFocus(false);
		idTF.setColumns(10);

		loginB = new JButton("로그인");
		loginB.setBorderPainted(false);
		loginB.setForeground(Color.WHITE);
		loginB.setBackground(Color.BLACK);
		loginB.setOpaque(true);
		loginB.setMinimumSize(new Dimension(80, 70));
		loginB.setMaximumSize(new Dimension(70, 70));
		loginB.setPreferredSize(new Dimension(70, 70));
		loginB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});

		loginB.setFont(new Font("나눔고딕", Font.BOLD, 10));

		passwdL = new JLabel("비밀번호");
		passwdL.setFont(new Font("나눔고딕", Font.PLAIN, 10));

		passwdTF = new JPasswordField();
		passwdTF.setMinimumSize(new Dimension(10, 40));
		passwdTF.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		passwdTF.setOpaque(true);
		passwdTF.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				passwdTF.setBackground(Color.WHITE);
			}
		});
		passwdTF.setBackground(UIManager.getColor("CheckBox.background"));
		passwdTF.setPreferredSize(new Dimension(10, 40));
		passwdTF.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.GRAY));
		passwdTF.setFont(new Font("나눔고딕", Font.PLAIN, 20));
		passwdTF.setColumns(10);

		joinB = new JButton("회원이 아니세요? 여기를 눌러주세요!");
		joinB.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				joinB.setForeground(Color.LIGHT_GRAY);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				joinB.setForeground(Color.DARK_GRAY);
			}
		});
		joinB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		joinB.setMinimumSize(new Dimension(230, 15));
		joinB.setMaximumSize(new Dimension(230, 15));
		joinB.setFont(new Font("나눔고딕", Font.PLAIN, 10));
		joinB.setBorderPainted(false);
		joinB.setBackground(Color.WHITE);
		joinB.setBorder(new EmptyBorder(0, 0, 0, 0));

		searchB = new JButton("로그인 정보를 잊으셨나요?");
		searchB.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				searchB.setForeground(Color.LIGHT_GRAY);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				searchB.setForeground(Color.DARK_GRAY);
			}
		});
		searchB.setPreferredSize(new Dimension(178, 15));
		searchB.setMaximumSize(new Dimension(178, 15));
		searchB.setMinimumSize(new Dimension(178, 15));
		searchB.setFont(new Font("나눔고딕", Font.PLAIN, 10));
		searchB.setBackground(Color.WHITE);
		searchB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		searchB.setBorderPainted(false);
		searchB.setBorder(new EmptyBorder(0, 0, 0, 0));

		bgImg = Toolkit.getDefaultToolkit().getImage(
				getClass().getResource("/images/bg.png"));

	}

	// 유효성 검사
	public boolean isNull() {
		if (idTF.getText() == null || idTF.getText().trim().length() == 0
				|| new String(passwdTF.getPassword()) == null
				|| new String(passwdTF.getPassword()).trim().length() == 0) {
			return false;
		}
		return true;
	}

	public void setContents() {
		GridBagLayout gbl_this = new GridBagLayout();
		gbl_this.columnWidths = new int[] { 0, 0, 0, 0 };
		gbl_this.rowHeights = new int[] { 0, 0, 0, 0, 0 };
		gbl_this.columnWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gbl_this.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };

		this.setLayout(gbl_this);
		this.setBounds(100, 100, 400, 600);
		this.setBackground(Color.WHITE);
		this.setBorder(new EmptyBorder(5, 5, 5, 5));

		GridBagConstraints grid_idL = new GridBagConstraints();
		grid_idL.anchor = GridBagConstraints.EAST;
		grid_idL.insets = new Insets(315, 70, 5, 0);
		grid_idL.gridx = 0;
		grid_idL.gridy = 0;
		this.add(idL, grid_idL);

		GridBagConstraints gbc_idTF = new GridBagConstraints();
		gbc_idTF.insets = new Insets(315, 3, 0, 3);
		gbc_idTF.fill = GridBagConstraints.HORIZONTAL;
		gbc_idTF.gridx = 1;
		gbc_idTF.gridy = 0;
		this.add(idTF, gbc_idTF);

		GridBagConstraints gbc_idL = new GridBagConstraints();
		gbc_idL.fill = GridBagConstraints.BOTH;
		gbc_idL.gridheight = 2;
		gbc_idL.insets = new Insets(315, 0, 5, 70);
		gbc_idL.gridx = 2;
		gbc_idL.gridy = 0;
		this.add(loginB, gbc_idL);

		GridBagConstraints gbc_passwdL = new GridBagConstraints();
		gbc_passwdL.insets = new Insets(0, 70, 5, 0);
		gbc_passwdL.anchor = GridBagConstraints.EAST;
		gbc_passwdL.gridx = 0;
		gbc_passwdL.gridy = 1;
		this.add(passwdL, gbc_passwdL);

		GridBagConstraints gbc_passwdTF = new GridBagConstraints();
		gbc_passwdTF.insets = new Insets(0, 3, 5, 3);
		gbc_passwdTF.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwdTF.gridx = 1;
		gbc_passwdTF.gridy = 1;
		this.add(passwdTF, gbc_passwdTF);

		GridBagConstraints gbc_joinB = new GridBagConstraints();
		gbc_joinB.insets = new Insets(10, 0, 0, 0);
		gbc_joinB.gridwidth = 3;
		gbc_joinB.gridx = 0;
		gbc_joinB.gridy = 2;
		this.add(joinB, gbc_joinB);

		GridBagConstraints gbc_searchB = new GridBagConstraints();
		gbc_searchB.insets = new Insets(5, 0, 0, 0);
		gbc_searchB.gridwidth = 3;
		gbc_searchB.gridx = 0;
		gbc_searchB.gridy = 3;
		this.add(searchB, gbc_searchB);

	}

	public void eventRegist() {
		loginB.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ui.login();
				idTF.setText("");
				passwdTF.setText("");

			}
		});
		joinB.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ui.change("소라");
				ui.setSize(400, 600);
				idTF.setText("");
				passwdTF.setText("");
			}
		});

		searchB.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				SearchFrame sf = new SearchFrame("찾기", ui);
				sf.setContents();
				sf.setVisible(true);
			}
		});

	}

	// GridBagLayout 적용 메소드
	private void add(Component com, int gridx, int gridy, int gridwidth,
			int gridheight) {
		constraints.gridx = gridx;
		constraints.gridy = gridy;
		constraints.gridwidth = gridwidth;
		constraints.gridheight = gridheight;
		gridBagLayout.setConstraints(com, constraints);
		add(com);
	}

	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(bgImg, 0, 0, getWidth(), getHeight(), this);
		// g.drawLine(x1, y1, x2, y2);
	}

	/*
	 * public static void main(String[] args) { LoginPanel frame = new
	 * LoginPanel(); frame.setContents(); frame.setBackground(new Color(254,235,
	 * 52)); frame.setSize(400, 600); frame.setVisible(true); }
	 */

}
