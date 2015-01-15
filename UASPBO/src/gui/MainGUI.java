package gui;

import java.awt.*;
import java.awt.event.*;
import java.beans.*;

import javax.swing.*;

@SuppressWarnings("serial")
public class MainGUI extends JFrame {
	private String usrLog;
	private String usrAuth;
	private int width,height;
	
	private JDesktopPane mdiPane=new JDesktopPane();

	private JButton btn=new JButton("Laporan");
	private LoginGUI login;
	private MaintUserGUI mUser;
	private MaintProductGUI mProduct;
	private MaintStockGUI mStock;
	private MaintSupplierGUI mSupplier;
	private PointOfSaleGUI mPOS;
	private ViewLaporanGUI mReport;
	
	private JMenuBar menuBar=new JMenuBar();

	private JMenu menuUser=new JMenu();
	private JMenuItem itemLogout=new JMenuItem("Logout");

	private JMenu menuMaint=new JMenu("Menu");
	private JMenuItem itemMUser=new JMenuItem("Maintenance User");
	private JMenuItem itemMProduk=new JMenuItem("Maintenance Produk");
	private JMenuItem itemMStock=new JMenuItem("Maintenance Stok");
	private JMenuItem itemMSupp=new JMenuItem("Maintenance Supplier");

	private JMenu menuPenj=new JMenu("Penjualan");
	private JMenuItem itemPOS=new JMenuItem("Point Of Sale");

	private JMenu menuOwner=new JMenu("Laporan");
	private JMenuItem itemReport=new JMenuItem("View Report");
	
	public MainGUI(String judul, int posX, int posY, int width, int height) {
		// TODO Auto-generated constructor stub
		super(judul);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.width=width;
		this.height=height;

		menuUser.add(itemLogout);

		menuMaint.add(itemMUser);
		menuMaint.add(itemMSupp);
		menuMaint.add(itemMProduk);
		menuMaint.add(itemMStock);

		menuPenj.add(itemPOS);

		menuOwner.add(itemReport);

		menuBar.add(menuUser);
		menuBar.add(menuMaint);
		menuBar.add(menuPenj);
		menuBar.add(menuOwner);

		setJMenuBar(menuBar);
		menuBar.setVisible(false);
		
		add(btn,BorderLayout.NORTH);
		btn.setVisible(false);
		
		add(mdiPane, BorderLayout.CENTER);
		setBounds(posX, posY, width, height);
		setVisible(true);
		
		showLoginGUI();
		klik();
	}
	
	private boolean cekFrame(JInternalFrame frm){
		boolean hsl=true;
		JInternalFrame [] x=mdiPane.getAllFrames();
		for(int i=0;i<x.length;i++){
			if(frm!=null && x[i].getClass().equals(frm.getClass())){
				if(x[i].isIcon()){
					try {
						x[i].setIcon(false);
					} catch (PropertyVetoException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				hsl=false;
				break;
			}
		}
		return hsl;
	}

	private void disposeAllFrame(){
		JInternalFrame [] x=mdiPane.getAllFrames();
		for(int i=0;i<x.length;i++){
			x[i].dispose();
		}
	}
	
	private void klik(){
		itemLogout.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				setLogout();
			}
		});
		
		itemMUser.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				showMUserGUI();
			}
		});

		itemMProduk.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				showMProductGUI();
			}
		});

		itemMStock.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				showMStockGUI();
			}
		});

		itemMSupp.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				showMSupplierGUI();
			}
		});

		itemPOS.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				showPosGUI();
			}
		});

		itemReport.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				showReportGUI();
			}
		});
	}

	private void showLoginGUI(){
		login= new LoginGUI("Login - Toko Obat", (width/2)-(250/2), (height/2)-(120/2), 250, 150,this);
		mdiPane.add(login);
	}

	private void showMUserGUI(){
		if(cekFrame(mUser)){
			mUser=new MaintUserGUI("Maintenance User - Toko Obat",(width/2)-(600/2),(height/2)-(450/2),600,450,this);
			mdiPane.add(mUser);
		}
	}

	private void showMProductGUI(){
		if(cekFrame(mProduct)){
			mProduct=new MaintProductGUI("Maintenance Product - Toko Obat",(width/2)-(600/2),(height/2)-(450/2),600,450);
			mdiPane.add(mProduct);
		}
	}

	private void showMSupplierGUI(){
		if(cekFrame(mSupplier)){
			mSupplier= new MaintSupplierGUI("Maintenance Product - Toko Obat",(width/2)-(600/2),(height/2)-(450/2),600,450);
			mdiPane.add(mSupplier);
		}
	}

	private void showMStockGUI(){
		if(cekFrame(mStock)){
			mStock= new MaintStockGUI("Maintenance Stock - Toko Obat",(width/2)-(600/2),(height/2)-(450/2),600,450,usrLog);
			mdiPane.add(mStock);
		}
	}

	private void showPosGUI(){
		if(cekFrame(mPOS)){
			mPOS= new PointOfSaleGUI("Point of Sale - Toko Obat",(width/2)-(600/2),(height/2)-(450/2),600,450,usrLog);
			mdiPane.add(mPOS);
		}
	}

	private void showReportGUI(){
		if(cekFrame(mReport)){
			mReport= new ViewLaporanGUI("View Report - Toko Obat",(width/2)-(600/2),(height/2)-(450/2),600,450);
			mdiPane.add(mReport);
		}
	}
	
	public void setLogin(String usrLog, String usrAuth){
		this.usrLog=usrLog;
		this.usrAuth=usrAuth;
		usrPrev();
	}
	
	public String getUsrLog(){
		return usrLog;
	}

	private void usrPrev(){
		menuMaint.setVisible(false);
		menuOwner.setVisible(false);
		menuPenj.setVisible(false);
		
		itemMProduk.setVisible(true);
		itemMStock.setVisible(true);
		itemMSupp.setVisible(true);
		itemMUser.setVisible(true);

		if(usrAuth.equals("O")){
			menuMaint.setVisible(true);
			menuOwner.setVisible(true);
			menuPenj.setVisible(true);
		}else if(usrAuth.equals("A")){
			if(usrLog.equals("adm")){
				itemMProduk.setVisible(false);
				itemMStock.setVisible(false);
				itemMSupp.setVisible(false);
			}
			menuMaint.setVisible(true);
		}else if(usrAuth.equals("K")){
			menuPenj.setVisible(true);
		}
		
		menuUser.setText(usrLog);
		menuBar.setVisible(true);
	}
	
	public void setLogout(){
		disposeAllFrame();
		menuBar.setVisible(false);
		showLoginGUI();
	}
}
