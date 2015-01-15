package gui;

import java.awt.*;
import java.awt.Dialog.*;
import java.awt.event.*;
import java.text.*;
import java.util.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

import database.*;
import transaksi.*;

@SuppressWarnings("serial")
public class ViewLaporanGUI extends JInternalFrame {
	private CariBarang jd=new CariBarang(this, "Cari Barang", ModalityType.TOOLKIT_MODAL);
	private JButton btnCari=new JButton("Cari");

	private JPanel judulPanel=new JPanel();
	private JPanel kontenPanel=new JPanel(new GridBagLayout());
	private JTabbedPane opsiPanel=new JTabbedPane();

	private JPanel trxPanel=new JPanel(new GridBagLayout());
	private JPanel tglPanel=new JPanel(new GridBagLayout());
	private JPanel userPanel=new JPanel(new GridBagLayout());
	private JPanel barangPanel=new JPanel(new GridBagLayout());
	
	private Vector<String> isiCbo;

	private JTextField txtIDTrx=new JTextField(10);
	private JComboBox<String> cboTgl;
	private JComboBox<String> cboBln;
	private JComboBox<String> cboThn;
	private JComboBox<String> cboUsr;
	private JTextField txtIDB=new JTextField(10);

	private JButton btnTrx=new JButton("Tampilkan Laporan");
	private JButton btnTgl=new JButton("Tampilkan Laporan");
	private JButton btnUsr=new JButton("Tampilkan Laporan");
	private JButton btnBrg=new JButton("Tampilkan Laporan");
		
	private DefaultTableModel dataModel=new DefaultTableModel();
	private JTable table= new JTable(dataModel);
	private JScrollPane tablePanel=new JScrollPane(table);
	
	private UserTable ut=new UserTable();
	private ProductTable pt=new ProductTable();
	private TransaksiTable tt=new TransaksiTable();
	
	@SuppressWarnings("deprecation")
	public ViewLaporanGUI(String judul, int posX, int posY, int width, int height) {
		super(judul);

		setMaximizable(true);
		setResizable(true);
		setClosable(true);
		setIconifiable(true);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		JLabel lblJudul=new JLabel("<html><h2><font color='white'>View Report</font></h2><html>");
		judulPanel.setBackground(Color.GRAY);
		judulPanel.add(lblJudul);
		add(judulPanel, BorderLayout.NORTH);
		
///////////////////////////////////////////////////////////////
		GridBagConstraints c = new GridBagConstraints();
		c.fill=GridBagConstraints.HORIZONTAL;
		c.insets=new Insets(0, 10, 10, 10);
		c.gridx=0;
		c.gridy=0;
		trxPanel.add(new JLabel("ID Transaksi"),c);

		c.gridx=1;
		c.gridy=0;
		trxPanel.add(txtIDTrx,c);

		c.insets=new Insets(0, 10, 0, 10);
		c.gridx=0;
		c.gridy=1;
		c.weightx=0.5;
		c.gridwidth=3;
		trxPanel.add(btnTrx,c);

		opsiPanel.addTab("Transaksi", trxPanel);
////////////////////////////////////////////////////////////////
		c = new GridBagConstraints();
		c.fill=GridBagConstraints.HORIZONTAL;
		c.insets=new Insets(5, 10, 10, 10);
		c.gridx=0;
		c.gridy=0;
		tglPanel.add(new JLabel("Tanggal Transaksi"),c);

		isiCbo=new Vector<String>();
		isiCbo.add("-Tgl-");
		for(int i=1;i<32;i++){
			String ang="0"+i;
			isiCbo.add(ang.substring(ang.length()-2, ang.length()));
		}
		cboTgl=new JComboBox<String>(isiCbo);

		isiCbo=new Vector<String>();
		isiCbo.add("-Bln-");
		for(int i=0;i<12;i++){
			isiCbo.add(new DateFormatSymbols().getMonths()[i]);
		}
		cboBln=new JComboBox<String>(isiCbo);

		isiCbo=new Vector<String>();
		isiCbo.add("-Thn-");
		for(int i=new Date().getYear()+1900;i>1899;i--){
			isiCbo.add(i+"");
		}
		cboThn=new JComboBox<String>(isiCbo);

		c.gridx=1;
		c.gridy=0;
		c.weightx=0.1;
		tglPanel.add(cboTgl,c);

		c.gridx=2;
		c.gridy=0;
		tglPanel.add(cboBln,c);

		c.gridx=3;
		c.gridy=0;
		tglPanel.add(cboThn,c);

		c.insets=new Insets(0, 10, 10, 10);
		c.gridx=0;
		c.gridy=1;
		c.weightx=0.5;
		c.gridwidth=4;
		tglPanel.add(btnTgl,c);

		opsiPanel.addTab("Tanggal", tglPanel);
///////////////////////////////////////////////////////////////
		c = new GridBagConstraints();
		c.fill=GridBagConstraints.HORIZONTAL;
		c.insets=new Insets(5, 10, 10, 10);
		c.gridx=0;
		c.gridy=0;
		userPanel.add(new JLabel("Nama Kasir"),c);

		Vector <Users> usr=new Vector<Users>(ut.viewAll());
		isiCbo=new Vector<String>();
		isiCbo.add("-Pilih-");
		for(int i=0;i<usr.size();i++){
			if(usr.elementAt(i).getJenis().equals("K")){
				isiCbo.add(usr.elementAt(i).getNama());
			}
		}
		cboUsr=new JComboBox<String>(isiCbo);

		c.gridx=1;
		c.gridy=0;
		userPanel.add(cboUsr,c);

		c.insets=new Insets(0, 10, 10, 10);
		c.gridx=0;
		c.gridy=1;
		c.weightx=0.5;
		c.gridwidth=3;
		userPanel.add(btnUsr,c);

		opsiPanel.addTab("Pengguna", userPanel);
///////////////////////////////////////////////////////////////
		c = new GridBagConstraints();
		c.fill=GridBagConstraints.HORIZONTAL;
		c.insets=new Insets(0, 10, 10, 10);
		c.gridx=0;
		c.gridy=0;
		barangPanel.add(new JLabel("ID Produk"),c);

		txtIDB.setEnabled(false);
		c.gridx=1;
		c.gridy=0;
		barangPanel.add(txtIDB,c);

		c.gridx=2;
		c.gridy=0;
		barangPanel.add(btnCari,c);

		c.insets=new Insets(0, 10, 0, 10);
		c.gridx=0;
		c.gridy=1;
		c.weightx=0.5;
		c.gridwidth=5;
		barangPanel.add(btnBrg,c);

		opsiPanel.addTab("Produk", barangPanel);
///////////////////////////////////////////////////////////////
		c = new GridBagConstraints();
		c.fill=GridBagConstraints.HORIZONTAL;
		c.insets=new Insets(10, 10, 0, 10);
		c.gridx=0;
		c.gridy=0;
		kontenPanel.add(opsiPanel, c);

		Vector<String> header=new Vector<String>();
		Vector<Integer> hWidth=new Vector<Integer>();
		header.add("No");
		header.add("Nama Barang");
		header.add("Jumlah");
		header.add("Harga");
		header.add("Total");

		hWidth.add(20);
		hWidth.add(300);
		hWidth.add(70);
		hWidth.add(70);
		hWidth.add(70);
		initTable(header,hWidth);
		
		c.fill=GridBagConstraints.BOTH;
		c.insets=new Insets(10, 10, 10, 10);
		c.gridx=0;
		c.gridy=1;
		c.weightx=10;
		c.weighty=10;
		kontenPanel.add(tablePanel, c);

		add(kontenPanel, BorderLayout.CENTER);
		
		klik();
		
		setBounds(posX, posY, width, height);
		setVisible(true);
	}
	
	private void initTable(Vector<String> header,Vector<Integer> hWidth){
		dataModel=new DefaultTableModel();
		for(int i=0;i<header.size();i++){
			String colName=header.elementAt(i);
			dataModel.addColumn(colName);
		}

		table.setModel(dataModel);
		for(int i=0;i<hWidth.size();i++){
			int colWidth=hWidth.elementAt(i);
			table.getColumnModel().getColumn(i).setPreferredWidth(colWidth);
		}
	}
	
	private void klik(){
		opsiPanel.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				JTabbedPane opt=(JTabbedPane) e.getSource();
				int tab=opt.getSelectedIndex();
				Vector<String> header=new Vector<String>();
				Vector<Integer> hWidth=new Vector<Integer>();
				if(tab==0){
					header.add("No");
					header.add("Nama Barang");
					header.add("Jumlah");
					header.add("Harga");
					header.add("Total");

					hWidth.add(20);
					hWidth.add(300);
					hWidth.add(70);
					hWidth.add(70);
					hWidth.add(70);
				}else if(tab==1){
					header.add("ID Trx");
					header.add("Waktu");
					header.add("Nama Kasir");
					header.add("Total");
					
					hWidth.add(70);
					hWidth.add(100);
					hWidth.add(250);
					hWidth.add(100);
				}else if(tab==2){
					header.add("ID Trx");
					header.add("Tanggal");
					header.add("Total");
					
					hWidth.add(60);
					hWidth.add(300);
					hWidth.add(100);
				}else if(tab==3){
					header.add("ID Trx");
					header.add("Tanggal");
					header.add("Nama Kasir");
					header.add("Jumlah");
					
					hWidth.add(60);
					hWidth.add(300);
					hWidth.add(200);
					hWidth.add(70);
				}
				initTable(header,hWidth);
			}
		});

		btnCari.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				double mX,mY;
				Point lok=MouseInfo.getPointerInfo().getLocation();
				mX=lok.getX();
				mY=lok.getY();

				jd.resetTable();
				jd.setSize(600, 300);
				jd.setLocation((int)mX, (int)mY);
				jd.setMinSize(400, 300);
				jd.setVisible(true);
			}
		});
		
		btnTrx.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(!txtIDTrx.getText().equals("")){
					String id=txtIDTrx.getText();
					Transaksi trx=tt.cariById(id);
					if(trx!=null){
						Vector<DetilTransaksi> vDetil= new Vector<DetilTransaksi>(trx.getdTransaksi());

						clearTable();
						for(int i=0;i<vDetil.size();i++){
							DetilTransaksi dt=vDetil.elementAt(i);
							Products prod=pt.cariById(dt.getId());
							dataModel.addRow(new Object[]{i+1,prod.getNama()
									,dt.getJumlah()
									,dt.getHarga()
									,dt.getSubTotal()});
						}
					}else{
						JOptionPane.showMessageDialog(null, "Transaksi tidak ditemukan", "Toko Obat", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		
		btnTgl.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(cboTgl.getSelectedIndex()!=0 &&
					cboBln.getSelectedIndex()!=0 &&
					cboThn.getSelectedIndex()!=0){

					String bln="0"+cboBln.getSelectedIndex();
					String tgl=cboThn.getSelectedItem().toString()+"-"+
								bln.substring(bln.length()-2, bln.length())+"-"+
								cboTgl.getSelectedItem().toString();
					Vector<Transaksi> vTrx= new Vector<Transaksi>(tt.cariByTanggal(tgl));
					if(vTrx.size()!=0){
						clearTable();
						for(int i=0;i<vTrx.size();i++){
							Transaksi trx=vTrx.elementAt(i);
							Users usr=trx.getUser();
							dataModel.addRow(new Object[]{trx.getId(),trx.getWkt()
									,usr.getNama()
									,trx.getTotal()});
						}
					}else{
						JOptionPane.showMessageDialog(null, "Transaksi tidak ditemukan", "Toko Obat", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		
		btnUsr.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(cboUsr.getSelectedIndex()!=0){
					String namaU=cboUsr.getSelectedItem().toString();
					Users usr=ut.cariByNama(namaU);
					String idU=usr.getId();
					Vector<Transaksi> vTrx= new Vector<Transaksi>(tt.cariByIdU(idU));
					if(vTrx.size()!=0){
						clearTable();
						for(int i=0;i<vTrx.size();i++){
							Transaksi trx=vTrx.elementAt(i);
							String tgl=trx.getTgl()+" "+trx.getWkt();
							String nTgl="";
							try {
								Date date=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(tgl);
								nTgl=new SimpleDateFormat("EEEE, dd MMMM yyyy (HH:mm:ss)").format(date);
							} catch (ParseException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							dataModel.addRow(new Object[]{trx.getId(),nTgl
									,trx.getTotal()});
						}
					}else{
						JOptionPane.showMessageDialog(null, "Transaksi tidak ditemukan", "Toko Obat", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		
		btnBrg.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(!txtIDB.getText().equals("")){
					String idB=txtIDB.getText();
					Vector<Transaksi> vTrx= new Vector<Transaksi>(tt.cariByIdB(idB));
					if(vTrx.size()!=0){
						clearTable();
						for(int i=0;i<vTrx.size();i++){
							Transaksi trx=vTrx.elementAt(i);
							Users usr=trx.getUser();
							String tgl=trx.getTgl()+" "+trx.getWkt();
							String nTgl="";
							try {
								Date date=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(tgl);
								nTgl=new SimpleDateFormat("EEEE, dd MMMM yyyy (HH:mm:ss)").format(date);
							} catch (ParseException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							dataModel.addRow(new Object[]{trx.getId(),nTgl
									,usr.getNama()
									,trx.getQtyProductByID(idB)});
						}
					}else{
						JOptionPane.showMessageDialog(null, "Transaksi tidak ditemukan", "Toko Obat", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
	}

	private void clearTable(){
		for(int i=dataModel.getRowCount()-1; i>-1;i--){
			dataModel.removeRow(i);
		}
		table.setModel(dataModel);
	}

	public void setTxtID(String isi){
		txtIDB.setText(isi);
	}
}