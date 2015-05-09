package kr.or.kisa.client.view;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * GUI에서 사용할 공통 메소드들...
 * 
 * @author 김순재
 *
 */
public class GUIUtil {
	public static final String STYLE_METAL = "javax.swing.plaf.metal.MetalLookAndFeel"; // Default
																						// 값이다.
	public static final String STYLE_WINDOWS = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel"; // window
																										// 룩앤필이다.
	public static final String STYLE_MOTIF = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
	public static final String STYLE_NIMBUS = "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel";
	public static final String STYLE_OS = UIManager
			.getSystemLookAndFeelClassName();

	// 화면 중앙 배치
	public static void setCenterScreen(Container container) {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension dim = toolkit.getScreenSize();
		System.out.println(dim);
		int x = (dim.height - container.getSize().height) / 2;
		int y = (dim.width - container.getSize().width) / 2;
		container.setLocation(y, x);
	}

	// 화면 풀스크린 배치
	public static void setFullScreen(Container container) {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension dim = toolkit.getScreenSize();
		// container.setSize(dim.width, dim.height);
		container.setBounds(0, 0, dim.width, dim.height);
	}

	// 화면 룩앤필(테마) 설정
	public static void setLookNFeel(Container container, String lookNFeelName) {

		try {
			UIManager.setLookAndFeel(lookNFeelName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // 다중으로 해줄 필요 없이, Exception 하나로 설정해줄수가 있음.
		SwingUtilities.updateComponentTreeUI(container);

	}
}
