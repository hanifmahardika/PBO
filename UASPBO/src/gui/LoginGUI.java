package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import transaksi.Users;
import database.UserTable;

@SuppressWarnings("serial")
public class LoginGUI extends JInternalFrame {
	
	
	private JPanel judulPanel = new JPanel();
	private JPanel kontenPanel = new JPanel();

	private JLabel lblUser=new JLabel("ID :");
	private JLabel lblPass=new JLabel("Password :");
	private JLabel lbljudul=new JLabel("Aplikasi Penjualan :");


	private JTextField txtUser=new JTextField();
	private JPasswordField txtPass=new JPasswordField();
	
	JButton btnOk=new JButton("Login");
	JButton btnBtl=new JButton("Exit");
	
	private UserTable ut=new UserTable();
	
	private JFrame parent;

	public LoginGUI(String judul, int posX, int posY, int width,
			int height, JFrame parent) {
		// TODO Auto-generated constructor stub
		super(judul);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		this.parent=parent;

		JLabel lblJudul = new JLabel("<html><h2><font color='white'>Silahkan Login Disini</font></h2></html>");
		judulPanel.setBackground(Color.GREEN);
		judulPanel.add(lblJudul);
		
				
		kontenPanel.setLayout(new GridLayout(3, 2));
		kontenPanel.add(lblUser);
		kontenPanel.add(txtUser);
		kontenPanel.add(lblPass);
		kontenPanel.add(txtPass);
		kontenPanel.add(btnOk);
		kontenPanel.add(btnBtl);
		
		add(judulPanel,BorderLayout.NORTH);
		add(kontenPanel,BorderLayout.CENTER);
		setBounds(posX, posY, width, height);
		
		setResizable(false);
		
		setVisible(true);
		
		klik();
	}
	
	private void klik(){
		btnOk.addActionListener(new ActionListener() {
			
			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String usr=txtUser.getText();
				//String pass=Users.generateMD5(txtPass.getText());
				//String pDef=Users.generateMD5("lh091011");
				String pass=(txtPass.getText());
				String pDef=("lh091011");
				
				int jml=ut.viewAll().size();
				Users lg=ut.cariById(txtUser.getText());
				if(jml==0 && usr.equals("adm") && pass.equals(pDef)){
					((MainGUI)parent).setLogin("adm","A");
					dispose();
				}else if(usr.equals(lg.getId()) && pass.equals(lg.getPass())){
					((MainGUI)parent).setLogin(lg.getId(),lg.getJenis());
					dispose();
				}else{
					JOptionPane.showMessageDialog(null, "Username dan Password salah", "Toko Obat", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		btnBtl.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
		});
	}
}
