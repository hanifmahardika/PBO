package gui;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import java.awt.Font;
import javax.swing.JButton;

public class MaintProductGUI1 extends JInternalFrame {
	private JTextField txtProduct;
	private JTextField txtNamaProduct;
	private JTextField txtHargaProduct;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MaintProductGUI1 frame = new MaintProductGUI1();
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
	public MaintProductGUI1() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Maintenance Product");
		lblNewLabel.setFont(new Font("Tw Cen MT", Font.PLAIN, 15));
		lblNewLabel.setBounds(142, 31, 140, 14);
		getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Id Product :");
		lblNewLabel_1.setBounds(10, 77, 68, 14);
		getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Nama Product :");
		lblNewLabel_2.setBounds(10, 123, 83, 14);
		getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Harga Product :");
		lblNewLabel_3.setBounds(10, 168, 83, 14);
		getContentPane().add(lblNewLabel_3);
		
		txtProduct = new JTextField();
		txtProduct.setBounds(109, 74, 86, 20);
		getContentPane().add(txtProduct);
		txtProduct.setColumns(10);
		
		txtNamaProduct = new JTextField();
		txtNamaProduct.setBounds(109, 120, 245, 20);
		getContentPane().add(txtNamaProduct);
		txtNamaProduct.setColumns(10);
		
		txtHargaProduct = new JTextField();
		txtHargaProduct.setBounds(109, 165, 86, 20);
		getContentPane().add(txtHargaProduct);
		txtHargaProduct.setColumns(10);
		
		JComboBox cmbSupp = new JComboBox();
		cmbSupp.setBounds(205, 74, 149, 20);
		getContentPane().add(cmbSupp);
		
		JButton btnNewButton = new JButton("Insert");
		btnNewButton.setBounds(4, 204, 89, 23);
		getContentPane().add(btnNewButton);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.setBounds(106, 204, 89, 23);
		getContentPane().add(btnUpdate);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.setBounds(213, 204, 89, 23);
		getContentPane().add(btnDelete);
		
		JButton btnSave = new JButton("Save");
		btnSave.setBounds(213, 164, 89, 23);
		getContentPane().add(btnSave);
		
		JButton btnClear = new JButton("Clear");
		btnClear.setBounds(319, 164, 89, 23);
		getContentPane().add(btnClear);

	}
}
