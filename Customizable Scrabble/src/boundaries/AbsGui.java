package boundaries;

import java.awt.Color;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;

@SuppressWarnings("serial")
public abstract class AbsGui extends JFrame {
	
	protected int w;
	protected int h;
	protected JPanel pane;
	
	public AbsGui(int appX, int appY, int appWidth, int appHeight, Color color, Border border) {
		this.w = appWidth;
		this.h = appHeight;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(appX, appY, appWidth, appHeight);
		pane = new JPanel();
		pane.setBackground(color);
		pane.setForeground(color);
		pane.setBorder(border);
		pane.setLayout(null);
		setContentPane(pane);
	}
	
	public AbsGui(int appWidth, int appHeight, Color color, Border border) {
		this(((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() - appWidth) * 1/2,
				((int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() - appHeight) * 1/2,
				appWidth,
				appHeight,
				color,
				border);
	}

	protected void showComponents() {}

	public void openWindow() {
		setVisible(true);
	}
	
	public void closeWindow() {
		setVisible(false);
		dispose();
	}
	
	public void hideWindow() {
		setVisible(false);
	}
	
	public void refresh(Object object) {
		pane.removeAll();
		showComponents();
		pane.repaint();
		pane.validate();
	}
	
}