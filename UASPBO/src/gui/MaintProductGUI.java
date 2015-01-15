package gui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.table.*;

import transaksi.*;
import database.*;

@SuppressWarnings("serial")
public class MaintProductGUI extends JInternalFrame {
	//button
	private JButton btnAdd = new JButton("Tambah");
	private JButton btnEdit = new JButton("Ubah");
	private JButton btnDelete = new JButton("Hapus");

	//table
	private DefaultTableModel dataModel = new DefaultTableModel();
    private JTable table = new JTable(dataModel);

    //class_database
    private ProductTable pt=new ProductTable();
	private SupplierTable st=new SupplierTable();

	//panel_template
	private JPanel addPanel = new JPanel();
    private JPanel editPanel = new JPanel();
    private JPanel deletePanel = new JPanel();
	private JPanel judulPanel = new JPanel();
	private JPanel menuPanel = new JPanel(new GridLayout(3, 1));
	private JPanel kontenPanel = new JPanel();

	//variable
	private int width,height;

	//Komponen_Add
	private JTextField txtIDAdd = new JTextField();
	private JTextField txtNamaAdd = new JTextField();
	private JTextField txtHargaAdd = new JTextField();
	private JComboBox<String> cboSuppAdd;
	private JButton btnAddSpn=new JButton("Simpan");
	private JButton btnAddBtl=new JButton("Batal");

	//Komponen_Edit
	private JTextField txtIDEdit= new JTextField();
	private JTextField txtNamaEdit= new JTextField();
	private JTextField txtHargaEdit = new JTextField();
	private JTextField txtSuppEdit = new JTextField();
	private JButton btnEditUbh=new JButton("Ubah");
	private JButton btnEditBtl=new JButton("Batal");

	//Komponen_delete
	private JTextField txtIDDelete= new JTextField();
	private JTextField txtNamaDelete= new JTextField();
	private JTextField txtHargaDelete = new JTextField();
	private JTextField txtSuppDelete = new JTextField();
	private JButton btnDeleteHps=new JButton("Hapus");
	private JButton btnDeleteBtl=new JButton("Batal");

	public MaintProductGUI(String judul, int posX, int posY, int width,
			int height) {
		// TODO Auto-generated constructor stub		
		super(judul);

		setResizable(true);
		setClosable(true);
		setIconifiable(true);

		this.width=width;
		this.height=height;
		
		super.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		super.setMinimumSize(new Dimension(width, height));
		super.setBounds(posX, posY, width, height);
		
		createTemplate();
		
		createContent();

		super.getContentPane().add(judulPanel, BorderLayout.NORTH);
		super.getContentPane().add(menuPanel, BorderLayout.WEST);
		super.getContentPane().add(kontenPanel, BorderLayout.CENTER);

		menuKlik();
		updateTable();

		super.setVisible(true);
	}
	
	private void createTemplate(){
		JLabel lblJudul = new JLabel("<html><h2><font color='white'>MAINTENANCE PRODUCT</font></h2></html>");
		judulPanel.setBackground(Color.GREEN);
		judulPanel.add(lblJudul);

		menuPanel.add(btnAdd);
		menuPanel.add(btnEdit);
		menuPanel.add(btnDelete);
		btnAdd.setBackground(Color.CYAN);
		
		kontenPanel.setLayout(new GridBagLayout());
	}
	
	private void createContent(){
		GridBagConstraints c = new GridBagConstraints();
	    contentAdd();
	    contentEdit();
	    contentDelete();
		editPanel.setVisible(false);
		deletePanel.setVisible(false);
		JPanel opsiPanel=new JPanel(new CardLayout());

		c.fill=GridBagConstraints.HORIZONTAL;
		c.gridx=0;
		c.gridy=0;
		c.weightx=width;
	    opsiPanel.add(addPanel);
	    opsiPanel.add(editPanel);
	    opsiPanel.add(deletePanel);
	    kontenPanel.add(opsiPanel,c);

		initTable();
	    c.fill=GridBagConstraints.BOTH;
		c.weightx=width;
		c.weighty=height;
		c.gridx=0;
		c.gridy=1;
	    kontenPanel.add(new JScrollPane(table),c);
	}

	private void contentAdd(){
		JLabel lblID = new JLabel("ID Produk");
		JLabel lblNama = new JLabel("Nama Produk");
		JLabel lblHarga = new JLabel("Harga Produk");
		
		txtIDAdd.setEnabled(true);

		Vector<String> isiCboAdd=new Vector<String>(st.viewAllName());
		isiCboAdd.add(0,"-Nama Supplier-");
		cboSuppAdd = new JComboBox<String>(isiCboAdd);
		
		addPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		c.fill=GridBagConstraints.HORIZONTAL;
		c.insets=new Insets(10, 10, 10, 10);
		c.gridx = 0;
		c.gridy = 0;
		addPanel.add(lblID, c);

		c.gridx = 1;
		c.gridy = 0;
		c.weightx=0.5;
		addPanel.add(txtIDAdd, c);

		c.gridx = 2;
		c.gridy = 0;
		c.gridwidth=2;
		c.weightx=0.0;
		addPanel.add(cboSuppAdd, c);

		c.gridx = 0;
		c.gridy = 1;
		c.insets=new Insets(0, 10, 10, 10);
		addPanel.add(lblNama, c);

		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth=3;
		c.weightx=0.5;
		addPanel.add(txtNamaAdd, c);
		
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth=1;
		c.weightx=0.0;
		addPanel.add(lblHarga, c);

		c.gridx = 1;
		c.gridy = 2;
		addPanel.add(txtHargaAdd, c);

		c.gridx = 2;
		c.gridy = 2;
		c.weightx=0.1;
		addPanel.add(btnAddSpn, c);

		c.gridx = 3;
		c.gridy = 2;
		addPanel.add(btnAddBtl, c);
		
		addAction();
	}
	
	private void addAction(){
		cboSuppAdd.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(cboSuppAdd.getSelectedIndex()!=0){
					String idSup=st.cariByNama(cboSuppAdd.getSelectedItem().toString()).getId();
					String idProd=pt.nextId(idSup);
					txtIDAdd.setText(idProd);
				}else{
					txtIDAdd.setText(null);
				}
			}
		});
		
		btnAddSpn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(!txtIDAdd.getText().equals("") && 
					!txtNamaAdd.getText().equals("") &&
					!txtHargaAdd.getText().equals("")){
					String id=txtIDAdd.getText();
					String nama=txtNamaAdd.getText();
					int harga=Integer.parseInt(txtHargaAdd.getText());
					String idSup=st.cariByNama(cboSuppAdd.getSelectedItem().toString()).getId();
					Products baru=new Products(id, nama, idSup, harga,0);
					pt.insert(baru);
					JOptionPane.showMessageDialog(null, "Produk ditambahkan", "Toko Obat", JOptionPane.INFORMATION_MESSAGE);
					resetAdd();
					updateTable();
				}else{
					JOptionPane.showMessageDialog(null, "Isian Tidak Boleh Kosong", "Toko Obat", JOptionPane.ERROR_MESSAGE);
					if(txtIDAdd.getText().equals("")){
						cboSuppAdd.requestFocus();
					}else if(txtNamaAdd.getText().equals("")){
						txtNamaAdd.requestFocus();
					}else if(txtHargaAdd.getText().equals("")){
						txtHargaAdd.requestFocus();
					}
				}
			}
		});
		
		btnAddBtl.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				resetAdd();
			}
		});
	}

	private void resetAdd(){
		txtIDAdd.setText(null);
		txtNamaAdd.setText(null);
		txtHargaAdd.setText(null);
		cboSuppAdd.setSelectedIndex(0);
	}
	
	private void contentEdit(){
		JLabel lblID = new JLabel("ID Produk");
		JLabel lblNama = new JLabel("Nama Produk");
		JLabel lblHarga = new JLabel("Harga Produk");
		
		txtIDEdit.setEnabled(false);
		txtSuppEdit.setEnabled(false);
		
		editPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		c.fill=GridBagConstraints.HORIZONTAL;
		c.insets=new Insets(10, 10, 10, 10);
		c.gridx = 0;
		c.gridy = 0;
		editPanel.add(lblID, c);

		c.gridx = 1;
		c.gridy = 0;
		c.weightx=0.5;
		editPanel.add(txtIDEdit, c);

		c.gridx = 2;
		c.gridy = 0;
		c.gridwidth=2;
		c.weightx=0.0;
		editPanel.add(txtSuppEdit, c);

		c.gridx = 0;
		c.gridy = 1;
		c.insets=new Insets(0, 10, 10, 10);
		editPanel.add(lblNama, c);

		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth=3;
		c.weightx=0.5;
		editPanel.add(txtNamaEdit, c);
		
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth=1;
		c.weightx=0.0;
		editPanel.add(lblHarga, c);

		c.gridx = 1;
		c.gridy = 2;
		editPanel.add(txtHargaEdit, c);

		c.gridx = 2;
		c.gridy = 2;
		c.weightx=0.1;
		editPanel.add(btnEditUbh, c);

		c.gridx = 3;
		c.gridy = 2;
		editPanel.add(btnEditBtl, c);
		
		editAction();
	}

	private void editAction(){
		table.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				if(editPanel.isVisible()){
					JTable x=(JTable)e.getSource();
					txtIDEdit.setText(x.getValueAt(x.getSelectedRow(), 0).toString());
					txtNamaEdit.setText(x.getValueAt(x.getSelectedRow(), 1).toString());
					txtSuppEdit.setText(x.getValueAt(x.getSelectedRow(), 2).toString());
					txtHargaEdit.setText(x.getValueAt(x.getSelectedRow(), 3).toString());
				}
			}
		});
		
		btnEditUbh.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(!txtIDEdit.getText().equals("") && 
					!txtNamaEdit.getText().equals("") &&
					!txtHargaEdit.getText().equals("")){
					String id=txtIDEdit.getText();
					String nama=txtNamaEdit.getText();
					int harga=Integer.parseInt(txtHargaEdit.getText());
					String idSup=txtSuppEdit.getText();
					//st.cariByNama(txtSuppEdit.getText()).getId();
					Products lama=new Products(id, nama, idSup,harga,pt.cariById(id).getStok());
					pt.update(id,lama);
					JOptionPane.showMessageDialog(null, "Produk diubah", "Toko Obat", JOptionPane.INFORMATION_MESSAGE);
					resetEdit();
					updateTable();
				}else{
					if(txtIDEdit.getText().equals("")){
						JOptionPane.showMessageDialog(null, "Silakan pilih barang yang akan diubah", "Toko Obat", JOptionPane.ERROR_MESSAGE);
					}else if(txtNamaEdit.getText().equals("")){
						JOptionPane.showMessageDialog(null, "Isian tidak boleh kosong", "Toko Obat", JOptionPane.ERROR_MESSAGE);
						txtNamaEdit.requestFocus();
					}else if(txtHargaEdit.getText().equals("")){
						JOptionPane.showMessageDialog(null, "Isian tidak boleh kosong", "Toko Obat", JOptionPane.ERROR_MESSAGE);
						txtHargaEdit.requestFocus();
					}
				}
			}
		});
		
		btnEditBtl.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				resetEdit();
			}
		});
	}

	private void resetEdit(){
		txtIDEdit.setText(null);
		txtNamaEdit.setText(null);
		txtHargaEdit.setText(null);
		txtSuppEdit.setText(null);
	}

	private void contentDelete(){
		JLabel lblID = new JLabel("ID Produk");
		JLabel lblNama = new JLabel("Nama Produk");
		JLabel lblHarga = new JLabel("Harga Produk");
		
		txtIDDelete.setEnabled(false);
		txtSuppDelete.setEnabled(false);
		txtNamaDelete.setEnabled(false);
		txtHargaDelete.setEnabled(false);
		
		deletePanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		c.fill=GridBagConstraints.HORIZONTAL;
		c.insets=new Insets(10, 10, 10, 10);
		c.gridx = 0;
		c.gridy = 0;
		deletePanel.add(lblID, c);

		c.gridx = 1;
		c.gridy = 0;
		c.weightx=0.5;
		deletePanel.add(txtIDDelete, c);

		c.gridx = 2;
		c.gridy = 0;
		c.gridwidth=2;
		c.weightx=0.0;
		deletePanel.add(txtSuppDelete, c);

		c.gridx = 0;
		c.gridy = 1;
		c.insets=new Insets(0, 10, 10, 10);
		deletePanel.add(lblNama, c);

		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth=3;
		c.weightx=0.5;
		deletePanel.add(txtNamaDelete, c);
		
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth=1;
		c.weightx=0.0;
		deletePanel.add(lblHarga, c);

		c.gridx = 1;
		c.gridy = 2;
		deletePanel.add(txtHargaDelete, c);

		c.gridx = 2;
		c.gridy = 2;
		c.weightx=0.1;
		deletePanel.add(btnDeleteHps, c);

		c.gridx = 3;
		c.gridy = 2;
		deletePanel.add(btnDeleteBtl, c);
		
		deleteAction();
	}

	private void deleteAction(){
		table.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				if(deletePanel.isVisible()){
					JTable x=(JTable)e.getSource();
					txtIDDelete.setText(x.getValueAt(x.getSelectedRow(), 0).toString());
					txtNamaDelete.setText(x.getValueAt(x.getSelectedRow(), 1).toString());
					txtSuppDelete.setText(x.getValueAt(x.getSelectedRow(), 2).toString());
					txtHargaDelete.setText(x.getValueAt(x.getSelectedRow(), 3).toString());
				}
			}
		});
		
		btnDeleteHps.addActionListener(new ActionListener() {
			
			@SuppressWarnings("static-access")
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JOptionPane jop=new JOptionPane();
				if(!txtIDDelete.getText().equals("") && 
					!txtNamaDelete.getText().equals("") &&
					!txtHargaDelete.getText().equals("")){
					String id=txtIDDelete.getText();
					String nama=txtNamaDelete.getText();
					int x = jop.showConfirmDialog(null, "Hapus produk \""+nama+"\"?", "Toko Obat", jop.YES_NO_OPTION, jop.QUESTION_MESSAGE);
					if(x==jop.YES_OPTION){
						pt.delete(id);
						jop.showMessageDialog(null, "Produk dihapus", "Toko Obat", jop.INFORMATION_MESSAGE);
						resetDelete();
						updateTable();
					}
				}else{
					jop.showMessageDialog(null, "Silakan pilih produk yang akan dihapus", "Toko Obat", jop.ERROR_MESSAGE);
				}
			}
		});
		
		btnDeleteBtl.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				resetDelete();
			}
		});
	}

	private void resetDelete(){
		txtIDDelete.setText(null);
		txtNamaDelete.setText(null);
		txtHargaDelete.setText(null);
		txtSuppDelete.setText(null);
	}

	private void initTable(){
		dataModel.addColumn("ID");
		dataModel.addColumn("Nama Produk");
		dataModel.addColumn("Supplier");
		dataModel.addColumn("Harga");
	    table.getColumnModel().getColumn(1).setPreferredWidth(400);
	    table.getColumnModel().getColumn(2).setPreferredWidth(100);
	    table.getColumnModel().getColumn(3).setPreferredWidth(100);
	}
	
	private void menuKlik() {
		btnAdd.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				btnAdd.setBackground(Color.CYAN);
				btnEdit.setBackground(null);
				btnDelete.setBackground(null);
				addPanel.setVisible(true);
				editPanel.setVisible(false);
				deletePanel.setVisible(false);
				resetAll();
				updateTable();
			}
		});

		btnEdit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				btnAdd.setBackground(null);
				btnEdit.setBackground(Color.CYAN);
				btnDelete.setBackground(null);
				addPanel.setVisible(false);
				editPanel.setVisible(true);
				deletePanel.setVisible(false);
				resetAll();
				updateTable();
			}
		});

		btnDelete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				btnAdd.setBackground(null);
				btnEdit.setBackground(null);
				btnDelete.setBackground(Color.CYAN);
				addPanel.setVisible(false);
				editPanel.setVisible(false);
				deletePanel.setVisible(true);
				resetAll();
				updateTable();
			}
		});
	}
	
	private void resetAll(){
		resetAdd();
		resetEdit();
		resetDelete();
	}
	private void updateTable(){
		for(int i=dataModel.getRowCount()-1; i>-1;i--){
			dataModel.removeRow(i);
		}		
		Vector<Products> vProd =pt.viewAll();
		for(int i=0; i<vProd.size();i++){
			dataModel.addRow(new Object[]{vProd.elementAt(i).getId()
										,vProd.elementAt(i).getNama()
									,vProd.elementAt(i).getIdSup()
										,vProd.elementAt(i).getHarga()});
			//,vProd.elementAt(i).getStok()
										
		}
		table.setModel(dataModel);
	}
	private void updateTable1(){
		for(int i=dataModel.getRowCount()-1; i>-1;i--){
			dataModel.removeRow(i);
		}
		
		Vector<Products> vProd =pt.viewAll();
		for(int i=0; i<vProd.size();i++){
			dataModel.addRow(new Object[]{vProd.elementAt(i).getId()
										,vProd.elementAt(i).getNama()
										,st.cariById(vProd.elementAt(i).getIdSup()).getNama()
										,vProd.elementAt(i).getHarga()});
		}
		table.setModel(dataModel);
	}
}
