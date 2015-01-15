package gui;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;

public class MaintStockGUI1 extends JInternalFrame {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MaintStockGUI1 frame = new MaintStockGUI1();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MaintStockGUI1() {
		setBounds(100, 100, 450, 300);

	}

}
