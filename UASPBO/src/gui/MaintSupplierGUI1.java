package gui;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;

public class MaintSupplierGUI1 extends JInternalFrame {
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MaintSupplierGUI1 frame = new MaintSupplierGUI1();
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
	public MaintSupplierGUI1() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Id Supplier :");
		lblNewLabel.setBounds(20, 82, 64, 14);
		getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Nama Supplier :");
		lblNewLabel_1.setBounds(217, 82, 88, 14);
		getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Maintenance Supplier");
		lblNewLabel_2.setFont(new Font("Tempus Sans ITC", Font.PLAIN, 15));
		lblNewLabel_2.setBounds(132, 34, 146, 14);
		getContentPane().add(lblNewLabel_2);
		
		textField = new JTextField();
		textField.setBounds(94, 79, 113, 20);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(294, 79, 114, 20);
		getContentPane().add(textField_1);
		
		JButton btnSave = new JButton("Save");
		btnSave.setBounds(215, 113, 89, 23);
		getContentPane().add(btnSave);
		
		JButton btnBatal = new JButton("Batal");
		btnBatal.setBounds(319, 113, 89, 23);
		getContentPane().add(btnBatal);

	}

}
