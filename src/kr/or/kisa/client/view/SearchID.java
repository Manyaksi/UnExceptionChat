package kr.or.kisa.client.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

public class SearchID extends JPanel {
	SearchFrame parents;

	JLabel UnExceptionL, ChatL, searchIDL, searchPasswdL, nameL, birthL, yearL,
			monthL, dayL, emailL;
	JTextField nameTF, emailTF;
	JButton searchB, cancelB;
	JPanel selectSearchP, southFucnP, logoP;

	GridBagLayout gridBag;
	GridBagConstraints constraints;

	JComboBox<String> yearType, monthType, dayType;
	String[] yearTypeComboBox = new String[100];
	String[] monthTypeComboBox = new String[12];
	String[] dayTypeComboBox = new String[31];

	public SearchID(SearchFrame parents) {
		this.parents = parents;

		selectSearchP = new JPanel();
		selectSearchP.setBorder(new EmptyBorder(0, 0, 0, 0));
		selectSearchP.setBackground(Color.WHITE);

		searchIDL = new JLabel("IDÃ£±â");
		searchIDL.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				searchIDL.setForeground(Color.DARK_GRAY);
			}

			public void mouseClicked(java.awt.event.MouseEvent evt) {
				searchIDL.setForeground(Color.GRAY);

				// JOptionPane.showMessageDialog(null, "ÁØºñÁßÀÔ´Ï´Ù...");

			}

			public void mouseExited(java.awt.event.MouseEvent evt) {
				searchIDL.setForeground(Color.GRAY);
			}
		});

		searchPasswdL = new JLabel("ºñ¹Ð¹øÈ£Ã£±â");
		searchPasswdL.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				searchPasswdL.setForeground(Color.DARK_GRAY);
				searchIDL.setForeground(Color.LIGHT_GRAY);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				searchPasswdL.setForeground(Color.LIGHT_GRAY);
				searchIDL.setForeground(Color.DARK_GRAY);
			}

			public void mouseClicked(java.awt.event.MouseEvent evt) {
				parents.change("ºñ¹ø");
				// JOptionPane.showMessageDialog(null, "ÁØºñÁßÀÔ´Ï´Ù...");

			}
		});
		searchPasswdL.setBackground(Color.WHITE);
		searchPasswdL.setForeground(Color.LIGHT_GRAY);
		searchPasswdL.setFont(new Font("³ª´®°íµñ", Font.PLAIN, 20));

		searchIDL.setBorder(new EmptyBorder(0, 0, 0, 0));
		searchIDL.setForeground(Color.DARK_GRAY);
		searchIDL.setBackground(Color.WHITE);
		searchIDL.setFont(new Font("³ª´®°íµñ", Font.PLAIN, 20));

		nameL = new JLabel("ÀÌ¸§");
		nameL.setFont(new Font("³ª´®°íµñ", Font.BOLD, 10));

		nameTF = new JTextField();
		nameTF.setPreferredSize(new Dimension(14, 30));
		nameTF.setMinimumSize(new Dimension(14, 30));
		nameTF.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		nameTF.setColumns(10);

		birthL = new JLabel("»ý³â¿ùÀÏ");
		birthL.setFont(new Font("³ª´®°íµñ", Font.BOLD, 10));

		// ³â
		for (int i = 0; i < 100; i++) {
			yearTypeComboBox[i] = String.valueOf(1916 + i);
		}
		for (int i = 0; i < yearTypeComboBox.length; i++) {
			// System.out.println(yearTypeComboBox[i]);
		}
		yearType = new JComboBox<String>(yearTypeComboBox);
		// yearType.setEnabled(false);
		yearType.setPreferredSize(new Dimension(90, 30));
		yearType.setMinimumSize(new Dimension(90, 30));
		yearType.setBorder(new EmptyBorder(0, 0, 0, 0));
		yearType.setOpaque(true);
		yearType.setBackground(Color.WHITE);

		// ¿ù
		for (int i = 0; i < 12; i++) {
			monthTypeComboBox[i] = String.valueOf(i + 1);
		}
		for (int i = 0; i < monthTypeComboBox.length; i++) {
			// System.out.println(monthTypeComboBox[i]);
		}
		monthType = new JComboBox<String>(monthTypeComboBox);
		// monthType.setEnabled(false);
		monthType.setBorder(new EmptyBorder(0, 0, 0, 0));
		monthType.setAlignmentY(0.0f);
		monthType.setAlignmentX(0.0f);
		monthType.setPreferredSize(new Dimension(70, 30));
		monthType.setMinimumSize(new Dimension(70, 30));

		// ÀÏ
		for (int i = 0; i < 31; i++) {
			dayTypeComboBox[i] = String.valueOf(i + 1);
		}
		for (int i = 0; i < dayTypeComboBox.length; i++) {
			// System.out.println(dayTypeComboBox[i]);
		}
		dayType = new JComboBox<String>(dayTypeComboBox);
		// dayType.setEnabled(false);
		dayType.setBorder(new EmptyBorder(0, 0, 0, 0));
		dayType.setAlignmentY(0.0f);
		dayType.setAlignmentX(0.0f);
		dayType.setBackground(Color.WHITE);
		dayType.setPreferredSize(new Dimension(70, 30));
		dayType.setMinimumSize(new Dimension(70, 30));

		yearL = new JLabel("³â");
		yearL.setAlignmentY(0.0f);
		yearL.setPreferredSize(new Dimension(11, 11));
		yearL.setMaximumSize(new Dimension(11, 11));
		yearL.setFont(new Font("³ª´®°íµñ", Font.PLAIN, 10));

		monthL = new JLabel("¿ù");
		monthL.setFont(new Font("³ª´®°íµñ", Font.PLAIN, 10));

		dayType.setPreferredSize(new Dimension(60, 30));
		dayType.setMinimumSize(new Dimension(60, 30));

		dayL = new JLabel("ÀÏ");
		dayL.setFont(new Font("³ª´®°íµñ", Font.PLAIN, 10));

		emailL = new JLabel("ÀÌ¸ÞÀÏ");
		emailL.setAlignmentY(0.0f);
		emailL.setFont(new Font("³ª´®°íµñ", Font.BOLD, 10));
		emailL.setHorizontalAlignment(SwingConstants.RIGHT);

		emailTF = new JTextField();
		emailTF.setPreferredSize(new Dimension(14, 30));
		emailTF.setMinimumSize(new Dimension(15, 30));
		emailTF.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		emailTF.setColumns(10);

		southFucnP = new JPanel();
		southFucnP.setBorder(new EmptyBorder(0, 0, 0, 0));
		southFucnP.setBackground(Color.WHITE);

		searchB = new JButton("Ã£±â");
		searchB.setBackground(Color.WHITE);
		searchB.setAlignmentY(0.0f);
		searchB.setFont(new Font("³ª´®°íµñ", Font.BOLD, 10));
		searchB.setForeground(Color.WHITE);
		searchB.setBackground(Color.BLACK);
		searchB.setOpaque(true);
		searchB.setBorderPainted(false);
		searchB.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				searchB.setBackground(Color.lightGray);
				searchB.setForeground(Color.white);
			}

			public void mouseExited(java.awt.event.MouseEvent evt) {
				searchB.setForeground(Color.white);
				searchB.setBackground(Color.BLACK);
			}
		});

		cancelB = new JButton("");
		cancelB.setBackground(Color.WHITE);
		cancelB.setAlignmentY(0.0f);
		cancelB.setFont(new Font("³ª´®°íµñ", Font.BOLD, 10));
		cancelB.setForeground(Color.WHITE);
		cancelB.setBackground(Color.WHITE);
		cancelB.setOpaque(true);
		cancelB.setBorderPainted(false);
		cancelB.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				cancelB.setBackground(Color.WHITE);
				cancelB.setForeground(Color.white);
			}

			public void mouseExited(java.awt.event.MouseEvent evt) {
				cancelB.setForeground(Color.WHITE);
				cancelB.setBackground(Color.WHITE);
			}
		});

		logoP = new JPanel();
		logoP.setBorder(new EmptyBorder(0, 0, 0, 0));
		logoP.setBackground(Color.WHITE);

		UnExceptionL = new JLabel("UnException");
		UnExceptionL.setFont(new Font("³ª´®°íµñ", Font.PLAIN, 13));

		ChatL = new JLabel("Chat");
		ChatL.setHorizontalAlignment(SwingConstants.RIGHT);
		ChatL.setFont(new Font("³ª´®°íµñ", Font.BOLD, 13));
	}

	public void setContents() {

		this.setBounds(100, 100, 400, 300);
		this.setAlignmentX(0.0f);
		this.setAlignmentY(0.0f);
		this.setBackground(Color.WHITE);
		this.setBorder(new EmptyBorder(5, 5, 5, 5));

		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 30, 0, 75, 10, 30, -38, 30,
				0, 30, 0 };
		gbl_contentPane.rowHeights = new int[] { 0, 0, 0, 0, 0, 19, 0 };
		gbl_contentPane.columnWeights = new double[] { 1.0, 0.0, 1.0, 1.0, 0.0,
				1.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 1.0, 0.0, 0.0, 0.0, 0.0,
				1.0, Double.MIN_VALUE };
		this.setLayout(gbl_contentPane);

		GridBagConstraints gbc_selectSearchP = new GridBagConstraints();
		gbc_selectSearchP.insets = new Insets(30, 0, 15, 0);
		gbc_selectSearchP.gridwidth = 9;
		gbc_selectSearchP.fill = GridBagConstraints.BOTH;
		gbc_selectSearchP.gridx = 0;
		gbc_selectSearchP.gridy = 0;
		this.add(selectSearchP, gbc_selectSearchP);

		GridBagLayout gbl_selectSearchP = new GridBagLayout();
		gbl_selectSearchP.columnWidths = new int[] { 30, 0, 0, 0 };
		gbl_selectSearchP.rowHeights = new int[] { 0, 0 };
		gbl_selectSearchP.columnWeights = new double[] { 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		gbl_selectSearchP.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		selectSearchP.setLayout(gbl_selectSearchP);

		GridBagConstraints gbc_searchIDL = new GridBagConstraints();
		gbc_searchIDL.insets = new Insets(0, 20, 0, 5);
		gbc_searchIDL.gridx = 1;
		gbc_searchIDL.gridy = 0;
		selectSearchP.add(searchIDL, gbc_searchIDL);
		searchPasswdL.setForeground(new Color(192, 192, 192));
		searchPasswdL.setFont(new Font("³ª´®°íµñ", Font.PLAIN, 20));

		GridBagConstraints gbc_searchPasswdL = new GridBagConstraints();
		gbc_searchPasswdL.anchor = GridBagConstraints.WEST;
		gbc_searchPasswdL.gridx = 2;
		gbc_searchPasswdL.gridy = 0;
		selectSearchP.add(searchPasswdL, gbc_searchPasswdL);

		GridBagConstraints gbc_nameL = new GridBagConstraints();
		gbc_nameL.insets = new Insets(0, 0, 5, 5);
		gbc_nameL.anchor = GridBagConstraints.WEST;
		gbc_nameL.gridx = 1;
		gbc_nameL.gridy = 1;
		this.add(nameL, gbc_nameL);

		GridBagConstraints gbc_nameTF = new GridBagConstraints();
		gbc_nameTF.gridwidth = 6;
		gbc_nameTF.insets = new Insets(0, 0, 10, 5);
		gbc_nameTF.fill = GridBagConstraints.HORIZONTAL;
		gbc_nameTF.gridx = 2;
		gbc_nameTF.gridy = 1;
		this.add(nameTF, gbc_nameTF);

		GridBagConstraints gbc_birthL = new GridBagConstraints();
		gbc_birthL.insets = new Insets(0, 0, 5, 5);
		gbc_birthL.anchor = GridBagConstraints.WEST;
		gbc_birthL.gridx = 1;
		gbc_birthL.gridy = 2;
		this.add(birthL, gbc_birthL);

		GridBagConstraints gbc_yearType = new GridBagConstraints();
		gbc_yearType.insets = new Insets(0, 0, 5, 5);
		gbc_yearType.fill = GridBagConstraints.HORIZONTAL;
		gbc_yearType.gridx = 2;
		gbc_yearType.gridy = 2;
		this.add(yearType, gbc_yearType);

		GridBagConstraints gbc_yearL = new GridBagConstraints();
		gbc_yearL.insets = new Insets(0, 0, 5, 5);
		gbc_yearL.gridx = 3;
		gbc_yearL.gridy = 2;
		this.add(yearL, gbc_yearL);

		GridBagConstraints gbc_monthType = new GridBagConstraints();
		gbc_monthType.insets = new Insets(0, 0, 5, 5);
		gbc_monthType.gridx = 4;
		gbc_monthType.gridy = 2;
		this.add(monthType, gbc_monthType);

		GridBagConstraints gbc_monthL = new GridBagConstraints();
		gbc_monthL.insets = new Insets(0, 0, 5, 5);
		gbc_monthL.gridx = 5;
		gbc_monthL.gridy = 2;
		this.add(monthL, gbc_monthL);

		GridBagConstraints gbc_dayType = new GridBagConstraints();
		gbc_dayType.insets = new Insets(0, 0, 5, 5);
		gbc_dayType.fill = GridBagConstraints.HORIZONTAL;
		gbc_dayType.gridx = 6;
		gbc_dayType.gridy = 2;
		this.add(dayType, gbc_dayType);

		GridBagConstraints gbc_dayL = new GridBagConstraints();
		gbc_dayL.insets = new Insets(0, 0, 5, 5);
		gbc_dayL.gridx = 7;
		gbc_dayL.gridy = 2;
		this.add(dayL, gbc_dayL);

		GridBagConstraints gbc_emailL = new GridBagConstraints();
		gbc_emailL.insets = new Insets(0, 0, 5, 5);
		gbc_emailL.anchor = GridBagConstraints.WEST;
		gbc_emailL.gridx = 1;
		gbc_emailL.gridy = 3;
		this.add(emailL, gbc_emailL);

		GridBagConstraints gbc_emailTF = new GridBagConstraints();
		gbc_emailTF.insets = new Insets(0, 0, 5, 5);
		gbc_emailTF.gridwidth = 6;
		gbc_emailTF.fill = GridBagConstraints.HORIZONTAL;
		gbc_emailTF.gridx = 2;
		gbc_emailTF.gridy = 3;
		this.add(emailTF, gbc_emailTF);
		emailTF.setColumns(10);

		GridBagConstraints gbc_southFucnP = new GridBagConstraints();
		gbc_southFucnP.insets = new Insets(10, 0, 20, 0);
		gbc_southFucnP.gridwidth = 9;
		gbc_southFucnP.fill = GridBagConstraints.BOTH;
		gbc_southFucnP.gridx = 0;
		gbc_southFucnP.gridy = 4;
		this.add(southFucnP, gbc_southFucnP);

		GridBagLayout gbl_southFucnP = new GridBagLayout();
		gbl_southFucnP.columnWidths = new int[] { 120, 0, 0, 120, 0 };
		gbl_southFucnP.rowHeights = new int[] { 0, 0 };
		gbl_southFucnP.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		gbl_southFucnP.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		southFucnP.setLayout(gbl_southFucnP);

		GridBagConstraints gbc_searchB = new GridBagConstraints();
		gbc_searchB.insets = new Insets(0, 0, 0, 5);
		gbc_searchB.gridx = 1;
		gbc_searchB.gridy = 0;
		southFucnP.add(searchB, gbc_searchB);

		GridBagConstraints gbc_cancelB = new GridBagConstraints();
		gbc_cancelB.insets = new Insets(0, 0, 0, 5);
		gbc_cancelB.gridx = 2;
		gbc_cancelB.gridy = 0;
		southFucnP.add(cancelB, gbc_cancelB);

		GridBagConstraints gbc_logoP = new GridBagConstraints();
		gbc_logoP.gridwidth = 3;
		gbc_logoP.fill = GridBagConstraints.BOTH;
		gbc_logoP.gridx = 6;
		gbc_logoP.gridy = 5;
		this.add(logoP, gbc_logoP);

		GridBagLayout gbl_logoP = new GridBagLayout();
		gbl_logoP.columnWidths = new int[] { 0, 0, 0 };
		gbl_logoP.rowHeights = new int[] { 0, 0 };
		gbl_logoP.columnWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		gbl_logoP.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		logoP.setLayout(gbl_logoP);

		GridBagConstraints gbc_UnExceptionL = new GridBagConstraints();
		gbc_UnExceptionL.anchor = GridBagConstraints.EAST;
		gbc_UnExceptionL.insets = new Insets(0, 0, 0, 2);
		gbc_UnExceptionL.gridx = 0;
		gbc_UnExceptionL.gridy = 0;
		logoP.add(UnExceptionL, gbc_UnExceptionL);

		GridBagConstraints gbc_ChatL = new GridBagConstraints();
		gbc_ChatL.anchor = GridBagConstraints.EAST;
		gbc_ChatL.gridx = 1;
		gbc_ChatL.gridy = 0;
		logoP.add(ChatL, gbc_ChatL);

	}

	public void exit() {
		setVisible(false);
	}

	public void eventRegist() {
		searchB.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				parents.sendSearchID();

			}
		});
		cancelB.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				exit();
			}
		});
	}

	private void add(Component c, int x, int y, int w, int h) {

		constraints.gridx = x;
		constraints.gridy = y;
		constraints.gridwidth = w;
		constraints.gridheight = h;
		gridBag.setConstraints(c, constraints);
		this.add(c);
	}

	/*
	 * public static void main(String[] args) { JFrame jf = new JFrame();
	 * SearchID s = new SearchID(); s.setContents(); jf.add(s);
	 * jf.setVisible(true); jf.pack(); }
	 */
}
