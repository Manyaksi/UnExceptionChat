package kr.or.kisa.client.view;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * GUI���� ����� ���� �޼ҵ��...
 * 
 * @author �����
 *
 */
public class GUIUtil {
	public static final String STYLE_METAL = "javax.swing.plaf.metal.MetalLookAndFeel"; // Default
																						// ���̴�.
	public static final String STYLE_WINDOWS = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel"; // window
																										// ������̴�.
	public static final String STYLE_MOTIF = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
	public static final String STYLE_NIMBUS = "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel";
	public static final String STYLE_OS = UIManager
			.getSystemLookAndFeelClassName();

	// ȭ�� �߾� ��ġ
	public static void setCenterScreen(Container container) {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension dim = toolkit.getScreenSize();
		System.out.println(dim);
		int x = (dim.height - container.getSize().height) / 2;
		int y = (dim.width - container.getSize().width) / 2;
		container.setLocation(y, x);
	}

	// ȭ�� Ǯ��ũ�� ��ġ
	public static void setFullScreen(Container container) {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension dim = toolkit.getScreenSize();
		// container.setSize(dim.width, dim.height);
		container.setBounds(0, 0, dim.width, dim.height);
	}

	// ȭ�� �����(�׸�) ����
	public static void setLookNFeel(Container container, String lookNFeelName) {

		try {
			UIManager.setLookAndFeel(lookNFeelName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // �������� ���� �ʿ� ����, Exception �ϳ��� �������ټ��� ����.
		SwingUtilities.updateComponentTreeUI(container);

	}
}
