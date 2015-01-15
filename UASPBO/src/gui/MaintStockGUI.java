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
public class MaintStockGUI extends JInternalFrame {
	//table
	private DefaultTableModel dataModel = new DefaultTableModel();
    private JTable table = new JTable(dataModel);

    private JPanel judulPanel = new JPanel();
	private JPanel kontenPanel = new JPanel();

	private JTextField txtID=new JTextField();
	private JTextField txtJml=new JTextField();

	private JButton btnCari=new JButton("Cari");
	private JButton btnTambah=new JButton("Tambah");
	private JButton btnHapus=new JButton("Hapus");
	private JButton btnBatal=new JButton("Batal");
	private JButton btnExec=new JButton("Proses");
	
	private CariBarang jd=new CariBarang(this,"TES",ModalityType.TOOLKIT_MODAL);

    private ProductTable pt=new ProductTable();
    
    private String usrLog;

	public MaintStockGUI(String judul, int posX, int posY, int width,
			int height, String usrLog) {
		// TODO Auto-generated constructor stub
		super(judul);
		
		setResizable(true);
		setClosable(true);
		setIconifiable(true);

		this.usrLog=usrLog;
		
		super.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

		super.setMinimumSize(new Dimension(width, height));
		super.setBounds(posX, posY, width, height);
		
		JLabel lblJudul = new JLabel("<html><h2><font color='white'>MAINTENANCE STOCK</font></h2></html>");
		judulPanel.setBackground(Color.GRAY);
		judulPanel.add(lblJudul);

		super.getContentPane().add(judulPanel, BorderLayout.NORTH);
		super.getContentPane().add(kontenPanel, BorderLayout.CENTER);
		
		templateContent();
		initTable();
		klik();

		super.setVisible(true);
	}
	
	private void klik(){
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
		
		btnTambah.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(!txtID.getText().equals("") && !txtJml.getText().equals("")){
					isiTable(pt.cariById(txtID.getText()), txtJml.getText());
					reset();
				}else{
					JOptionPane.showMessageDialog(null, "Isian tidak boleh kosong", "Toko Obat", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		btnHapus.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int selRow=table.getSelectedRow();
				if(selRow!=-1){
					dataModel.removeRow(selRow);
				}else{
					JOptionPane.showMessageDialog(null, "Tidak ada data terpilih", "Toko Obat", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		btnBatal.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				reset();
				clearTable();
			}
		});
		
		addInternalFrameListener(new InternalFrameAdapter() {
			
			@Override
			public void internalFrameClosing(InternalFrameEvent e) {
				// TODO Auto-generated method stub
				int cRow=dataModel.getRowCount();
				if(cRow!=0){
					int x= JOptionPane.showConfirmDialog(null, "Data belum diproses. Tetap ingin keluar?", "Toko Obat", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if(x==JOptionPane.YES_OPTION){
						dispose();
					}
				}else{
					dispose();
				}
			}
		});
		
		btnExec.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int cRow=dataModel.getRowCount();
				if(cRow!=0){
					for(int i=0;i<cRow;i++){
						StockTable stt=new StockTable();
						String idB=dataModel.getValueAt(i, 0).toString();
						int jumlah=Integer.parseInt(dataModel.getValueAt(i, 2).toString());
						int stok=pt.cariById(idB).getStok()+jumlah;

						Date now= new Date();
						SimpleDateFormat fDate=new SimpleDateFormat("yyyy-MM-dd");
						SimpleDateFormat fTime=new SimpleDateFormat("HH:mm:ss");

						String tanggal=fDate.format(now);
						String waktu=fTime.format(now);
						Stocks baru=new Stocks(idB, usrLog, tanggal, waktu, jumlah, stok);
						if(baru.getJumlah()!=0){
							stt.insert(baru);
						}
					}
					JOptionPane.showMessageDialog(null, "Data stok tersimpan", "Toko Obat", JOptionPane.INFORMATION_MESSAGE);
					reset();
					clearTable();
				}
			}
		});
	}
	
	public void setTxtID(String isi){
		txtID.setText(isi);
	}
	
	private void templateContent(){
		JLabel lblID=new JLabel("ID");
		JLabel lblJml=new JLabel("Jumlah");

		GridBagConstraints c = new GridBagConstraints();
		kontenPanel.setLayout(new GridBagLayout());

		c.fill=GridBagConstraints.HORIZONTAL;
		c.insets=new Insets(10, 10, 10, 10);
		c.gridx=0;
		c.gridy=0;
		kontenPanel.add(lblID,c);

		c.insets=new Insets(10, 0, 10, 10);
		c.gridx=1;
		c.gridy=0;
		c.weightx=0.5;
		kontenPanel.add(txtID,c);

		c.gridx=2;
		c.gridy=0;
		c.weightx=0.0;
		kontenPanel.add(btnCari,c);

		c.gridx=3;
		c.gridy=0;
		c.weightx=0.0;
		kontenPanel.add(lblJml,c);

		c.gridx=4;
		c.gridy=0;
		kontenPanel.add(txtJml,c);

		c.insets=new Insets(0, 10, 10, 10);
		c.gridx=0;
		c.gridy=1;
		c.gridwidth=2;
		c.weightx=0.1;
		kontenPanel.add(btnTambah,c);

		c.insets=new Insets(0, 0, 10, 10);
		c.gridx=2;
		c.gridy=1;
		c.gridwidth=2;
		c.weightx=0.0;
		kontenPanel.add(btnHapus,c);

		c.gridx=4;
		c.gridy=1;
		c.gridwidth=1;
		kontenPanel.add(btnBatal,c);

		c.insets=new Insets(0, 10, 10, 10);
		c.fill=GridBagConstraints.BOTH;
		c.gridx=0;
		c.gridy=2;
		c.gridwidth=5;
		c.weightx=0.5;
		c.weighty=0.1;
		kontenPanel.add(new JScrollPane(table),c);

		c.insets=new Insets(0, 10, 10, 10);
		c.fill=GridBagConstraints.HORIZONTAL;
		c.gridx=4;
		c.gridy=3;
		c.gridwidth=1;
		c.weightx=0.5;
		c.weighty=0.0;
		kontenPanel.add(btnExec,c);
		txtID.setEnabled(false);
	}

	private void initTable(){
		dataModel.addColumn("ID Produk");
		dataModel.addColumn("Nama Produk");
		dataModel.addColumn("Jumlah");
		
		table.getColumnModel().getColumn(0).setPreferredWidth(110);
		table.getColumnModel().getColumn(1).setPreferredWidth(500);
		table.getColumnModel().getColumn(2).setPreferredWidth(70);
	}
	
	private void isiTable(Products p, String jml){
		int idx=-1;
		for(int i=dataModel.getRowCount()-1; i>-1;i--){
			if(dataModel.getValueAt(i, 0).equals(p.getId())){
				idx=i;
				break;
			}
		}
		
		if(idx==-1){
			int hsl=Integer.parseInt(jml);

			if(hsl<0){
				hsl=0;
			}
			
			dataModel.addRow(new Object[]{p.getId()
					,p.getNama()
					,hsl});
		}else{
			int jmlL=Integer.parseInt(dataModel.getValueAt(idx, 2).toString());
			int hsl=jmlL+Integer.parseInt(jml);

			if(hsl<0){
				hsl=0;
			}
			
			dataModel.setValueAt(p.getId(), idx, 0);
			dataModel.setValueAt(p.getNama(), idx, 1);
			dataModel.setValueAt(hsl, idx, 2);
		}
		
		table.setModel(dataModel);
	}

	private void clearTable(){
		for(int i=dataModel.getRowCount()-1; i>-1;i--){
			dataModel.removeRow(i);
		}
		table.setModel(dataModel);
	}

	private void reset(){
		txtID.setText(null);
		txtJml.setText(null);
	}
}