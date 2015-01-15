package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPasswordField;

import database.UserTable;
import transaksi.Users;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class LoginGUI1 extends JInternalFrame {
	private JTextField txtUser;
	private JPasswordField txtPass;
	private UserTable ut = new UserTable();

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 * @param mainGUI 
	 * @param l 
	 * @param k 
	 * @param j 
	 * @param i 
	 * @param string 
	 */
	public LoginGUI1(String judul, int posX, int posY, int width,
			int height, JFrame parent) {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		
		JLabel label = new JLabel("");
		label.setBounds(41, 60, 46, 14);
		getContentPane().add(label);
		
		JLabel lblNewLabel = new JLabel("ID :");
		lblNewLabel.setBounds(51, 125, 46, 14);
		getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Password :");
		lblNewLabel_1.setBounds(51, 164, 60, 14);
		getContentPane().add(lblNewLabel_1);
		
		txtUser = new JTextField();
		txtUser.setBounds(168, 122, 86, 20);
		getContentPane().add(txtUser);
		txtUser.setColumns(10);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String usr=txtUser.getText();
				//String pass=Users.generateMD5(txtPass.getText());
				//String pDef=Users.generateMD5("lh091011");
				String pass=(txtPass.getText());
				String pDef=("lh091011");
				
				int jml=ut.viewAll().size();
				Users lg=ut.cariById(txtUser.getText());
				if(jml==0 && usr.equals("adm") && pass.equals(pDef)){
					((MainGUI)getParent()).setLogin("adm","A");
					dispose();
				}else if(usr.equals(lg.getId()) && pass.equals(lg.getPass())){
					((MainGUI)getParent()).setLogin(lg.getId(),lg.getJenis());
					dispose();
				}else{
					JOptionPane.showMessageDialog(null, "Username dan Password salah", "Toko Obat", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnLogin.setBounds(55, 207, 89, 23);
		getContentPane().add(btnLogin);
		
		JButton btnExit = new JButton("Exit");
		btnExit.setBounds(165, 207, 89, 23);
		getContentPane().add(btnExit);
		
		txtPass = new JPasswordField();
		txtPass.setBounds(168, 161, 86, 20);
		getContentPane().add(txtPass);
		
		JLabel lblSilahkanLoginDisini = new JLabel("Silahkan Login Disini");
		lblSilahkanLoginDisini.setFont(new Font("Sylfaen", Font.PLAIN, 13));
		lblSilahkanLoginDisini.setBounds(103, 85, 126, 14);
		getContentPane().add(lblSilahkanLoginDisini);
		
		JLabel lblNewLabel_2 = new JLabel("Aplikasi Penjualan Toko Obat Herbal");
		lblNewLabel_2.setFont(new Font("Traditional Arabic", Font.PLAIN, 15));
		lblNewLabel_2.setBounds(41, 60, 237, 14);
		getContentPane().add(lblNewLabel_2);

	}
}
