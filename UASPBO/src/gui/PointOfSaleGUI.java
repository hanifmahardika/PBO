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
public class PointOfSaleGUI extends JInternalFrame {
	//table
	private DefaultTableModel dataModel = new DefaultTableModel();
    private JTable table = new JTable(dataModel);

    private JPanel judulPanel = new JPanel();
	private JPanel kontenPanel = new JPanel();

	private JTextField txtID=new JTextField();
	private JTextField txtNama=new JTextField();
	private JTextField txtHarga=new JTextField();
	private JTextField txtJml=new JTextField();
	private JLabel lblTot=new JLabel("<html><h3><font color='yellow'>0,-</font></h3></html>",SwingConstants.RIGHT);

	private JButton btnCari=new JButton("Cari");
	private JButton btnTambah=new JButton("Tambah");
	private JButton btnHapus=new JButton("Hapus");
	private JButton btnBatal=new JButton("Batal");
	private JButton btnExec=new JButton("Bayar");
	
	private CariBarang jd=new CariBarang(this,"Cari Barang",ModalityType.TOOLKIT_MODAL);

    private ProductTable pt=new ProductTable();
    private TransaksiTable tt=new TransaksiTable();
    
    private String usrLog;

	public PointOfSaleGUI(String judul, int posX, int posY, int width,
			int height, String usrLog) {
		// TODO Auto-generated constructor stub
		super(judul);
		
		setResizable(true);
		setMaximizable(true);
		setClosable(true);
		setIconifiable(true);

		this.usrLog=usrLog;
		
		super.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

		super.setMinimumSize(new Dimension(width, height));
		super.setBounds(posX, posY, width, height);
		
		JLabel lblJudul = new JLabel("<html><h2><font color='white'>Point of Sale</font></h2></html>");
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
				String id=txtID.getText();
				int jml=0;
				try {
					jml=Integer.parseInt(txtJml.getText());
				} catch (NumberFormatException eN) {
					// TODO: handle exception
					jml=0;
				}
				int stok=pt.cariById(id).getStok();
				int sisa=stok-jml;
				if(!txtID.getText().equals("") && jml!=0 && sisa>-1){
					isiTable(pt.cariById(txtID.getText()), txtJml.getText());
					reset();
					lblTot.setText(String.valueOf("<html><h3><font color='yellow'>"+getTotal()+",-</font></h3></html>"));
				}else{
					if(sisa<0){
						JOptionPane.showMessageDialog(null, "Stok tidak mencukupi", "Toko Obat", JOptionPane.ERROR_MESSAGE);
					}else{
						JOptionPane.showMessageDialog(null, "Isian tidak boleh kosong", "Toko Obat", JOptionPane.ERROR_MESSAGE);
					}
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
					lblTot.setText(String.valueOf("<html><h3><font color='yellow'>"+getTotal()+",-</font></h3></html>"));
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
			
			@SuppressWarnings("static-access")
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int cRow=dataModel.getRowCount();
				JOptionPane jop=new JOptionPane();
				if(cRow!=0){
					String jml=jop.showInputDialog(null, "Jumlah yang dibayarkan:", "Toko Obat", jop.QUESTION_MESSAGE);
					int iJml=0;
					try {
						iJml=Integer.parseInt(jml);
					} catch (NumberFormatException eN) {
						// TODO: handle exception
						iJml=0;
					}
					
					int kembali=iJml - getTotal();

					if(iJml>0 && kembali>-1){
						Date now= new Date();
						SimpleDateFormat fYear=new SimpleDateFormat("yy");
						SimpleDateFormat fDate=new SimpleDateFormat("yyyy-MM-dd");
						SimpleDateFormat fTime=new SimpleDateFormat("HH:mm:ss");
	
						String thn=fYear.format(now);
						String tanggal=fDate.format(now);
						String waktu=fTime.format(now);
	
						String idTrx=tt.nextId(thn);
						Transaksi trx=new Transaksi(idTrx, tanggal, waktu, usrLog);
						
						for(int i=0;i<cRow;i++){
							String namaB=dataModel.getValueAt(i, 1).toString();
							String idB=pt.cariByNama(namaB).getId();
							int harga=Integer.parseInt(dataModel.getValueAt(i, 2).toString());
							int jumlah=Integer.parseInt(dataModel.getValueAt(i, 3).toString());
	
							if(jumlah!=0){
								trx.tambahdTransaksi(new DetilTransaksi(idB, harga, jumlah));
							}
						}
						
						tt.insert(trx);
						JOptionPane.showMessageDialog(null, "Kembalian Rp"+kembali+",-", "Toko Obat", JOptionPane.INFORMATION_MESSAGE);
						reset();
						clearTable();
					}
				}
			}
		});
	}
	
	public void setTxtID(String isi){
		txtID.setText(isi);
	}

	public void setTxtNama(String isi){
		txtNama.setText(isi);
	}

	public void setTxtHarga(int isi){
		txtHarga.setText(String.valueOf(isi));
	}
	
	private void templateContent(){

		GridBagConstraints c = new GridBagConstraints();
		kontenPanel.setLayout(new GridBagLayout());

		c.fill=GridBagConstraints.HORIZONTAL;
		c.insets=new Insets(10, 10, 10, 10);
		c.gridx=0;
		c.gridy=0;
		c.weightx=0.0;
		kontenPanel.add(new JLabel("ID"),c);

		c.insets=new Insets(10, 0, 10, 10);
		c.gridx=1;
		c.gridy=0;
		c.weightx=1;
		c.weightx=0.1;
		kontenPanel.add(txtID,c);

		c.gridx=2;
		c.gridy=0;
		c.weightx=0.0;
		kontenPanel.add(btnCari,c);

//////////////////////////////////////////////////////////////////////////////////////
		JLabel lblRp=new JLabel("<html><h3><font color='yellow'>Rp</font></h3></html>");
		JPanel totalPanel=new JPanel(new GridBagLayout());

		c.insets=new Insets(0, 10, 0, 0);
		c.gridx=0;
		c.gridy=0;
		totalPanel.add(lblRp, c);

		c.insets=new Insets(0, 0, 0, 10);
		c.gridx=1;
		c.gridy=0;
		c.gridwidth=3;
		c.weightx=0.1;
		totalPanel.add(lblTot, c);
		totalPanel.setBackground(Color.BLACK);
///////////////////////////////////////////////////////////////////////////////////////
		
		c.insets=new Insets(10, 0, 10, 10);
		c.gridx=3;
		c.gridy=0;
		c.gridwidth=4;
		c.weightx=0.0;
		kontenPanel.add(totalPanel,c);

		c.insets=new Insets(0, 10, 10, 10);
		c.gridx=0;
		c.gridy=1;
		c.gridwidth=1;
		kontenPanel.add(new JLabel("Nama"),c);

		c.insets=new Insets(0, 0, 0, 10);
		c.gridx=1;
		c.gridy=1;
		c.weightx=1;
		c.gridwidth=2;
		kontenPanel.add(txtNama,c);

		c.gridx=3;
		c.gridy=1;
		c.gridwidth=1;
		c.weightx=0.0;
		kontenPanel.add(new JLabel("Harga"),c);
		
		c.gridx=4;
		c.gridy=1;
		c.weightx=1;
		kontenPanel.add(txtHarga,c);

		c.gridx=5;
		c.gridy=1;
		c.weightx=0.0;
		kontenPanel.add(new JLabel("Jumlah"),c);
		
		c.gridx=6;
		c.gridy=1;
		c.weightx=1;
		kontenPanel.add(txtJml,c);

		c.insets=new Insets(0, 10, 10, 10);
		c.gridx=0;
		c.gridy=2;
		c.weightx=1.5;
		c.gridwidth=2;
		kontenPanel.add(btnTambah,c);

		c.insets=new Insets(0, 0, 10, 10);
		c.gridx=3;
		c.gridy=2;
		c.gridwidth=2;
		c.weightx=0.0;
		kontenPanel.add(btnHapus,c);

		c.gridx=5;
		c.gridy=2;
		c.weightx=0.0;
		kontenPanel.add(btnBatal,c);

		c.insets=new Insets(0, 10, 10, 10);
		c.fill=GridBagConstraints.BOTH;
		c.gridx=0;
		c.gridy=3;
		c.gridwidth=7;
		c.weightx=0.0;
		c.weighty=0.1;
		kontenPanel.add(new JScrollPane(table),c);

		c.insets=new Insets(0, 10, 10, 10);
		c.fill=GridBagConstraints.HORIZONTAL;
		c.gridx=5;
		c.gridy=4;
		c.gridwidth=2;
		c.weightx=0.5;
		c.weighty=0.0;
		kontenPanel.add(btnExec,c);
		txtID.setEnabled(false);
		txtNama.setEnabled(false);
		txtHarga.setEnabled(false);
	}

	private void initTable(){
		dataModel.addColumn("No");
		dataModel.addColumn("Nama Produk");
		dataModel.addColumn("Harga");
		dataModel.addColumn("Jumlah");
		dataModel.addColumn("Sub Total");
		
		table.getColumnModel().getColumn(0).setPreferredWidth(50);
		table.getColumnModel().getColumn(1).setPreferredWidth(500);
		table.getColumnModel().getColumn(2).setPreferredWidth(100);
		table.getColumnModel().getColumn(3).setPreferredWidth(70);
		table.getColumnModel().getColumn(4).setPreferredWidth(100);
	}
	
	private void isiTable(Products p, String jml){
		int idx=-1;
		for(int i=dataModel.getRowCount()-1; i>-1;i--){
			if(dataModel.getValueAt(i, 1).equals(p.getNama())){
				idx=i;
				break;
			}
		}
		
		if(idx==-1){
			int iJml=Integer.parseInt(jml);

			if(iJml<0){
				iJml=0;
			}
			int sTot=iJml*p.getHarga();
			int cRow=dataModel.getRowCount();
			dataModel.addRow(new Object[]{cRow+1
					,p.getNama()
					,p.getHarga()
					,iJml
					,sTot});
		}else{
			int jmlL=Integer.parseInt(dataModel.getValueAt(idx, 3).toString());
			int hsl=jmlL+Integer.parseInt(jml);

			if(hsl<0){
				hsl=0;
			}
			int sTot=hsl*p.getHarga();
			dataModel.setValueAt(idx+1, idx, 0);
			dataModel.setValueAt(p.getNama(), idx, 1);
			dataModel.setValueAt(p.getHarga(), idx, 2);
			dataModel.setValueAt(hsl, idx, 3);
			dataModel.setValueAt(sTot, idx, 4);
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
		txtNama.setText(null);
		txtHarga.setText(null);
		txtJml.setText(null);
		lblTot.setText(String.valueOf("<html><h3><font color='yellow'>"+getTotal()+",-</font></h3></html>"));
	}
	
	private int getTotal(){
		int total=0;
		for(int i=0;i<dataModel.getRowCount();i++){
			total+=Integer.parseInt(dataModel.getValueAt(i, 4).toString());
		}
		return total;
	}
}