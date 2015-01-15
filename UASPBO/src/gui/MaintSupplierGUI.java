package gui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.table.*;

import transaksi.*;
import database.*;

@SuppressWarnings("serial")
public class MaintSupplierGUI extends JInternalFrame {
	//button
	private JButton btnAdd = new JButton("Tambah");
	private JButton btnEdit = new JButton("Ubah");
	private JButton btnDelete = new JButton("Hapus");

	//table
	private DefaultTableModel dataModel = new DefaultTableModel();
    private JTable table = new JTable(dataModel);

    //class_database
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
	private JButton btnAddSpn=new JButton("Simpan");
	private JButton btnAddBtl=new JButton("Batal");

	//Komponen_Edit
	private JTextField txtIDEdit= new JTextField();
	private JTextField txtNamaEdit= new JTextField();
	private JButton btnEditUbh=new JButton("Ubah");
	private JButton btnEditBtl=new JButton("Batal");

	//Komponen_Edit
	private JTextField txtIDDelete= new JTextField();
	private JTextField txtNamaDelete= new JTextField();
	private JButton btnDeleteHps=new JButton("Hapus");
	private JButton btnDeleteBtl=new JButton("Batal");

	public MaintSupplierGUI(String judul, int posX, int posY, int width,
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
		JLabel lblJudul = new JLabel("<html><h2><font color='white'>MAINTENANCE SUPPLIER</font></h2></html>");
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
		JLabel lblID = new JLabel("ID Supplier");
		JLabel lblNama = new JLabel("Nama Supplier");
		
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
		c.weightx=0.0;
		addPanel.add(lblNama, c);

		c.gridx = 3;
		c.gridy = 0;
		c.weightx=0.5;
		addPanel.add(txtNamaAdd, c);

		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth=2;
		c.weightx=0.0;
		addPanel.add(btnAddSpn, c);

		c.gridx = 2;
		c.gridy = 1;
		c.gridwidth=2;
		addPanel.add(btnAddBtl, c);
		
		addAction();
	}
	
	private void addAction(){
		btnAddSpn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(!txtIDAdd.getText().equals("") && 
					!txtNamaAdd.getText().equals("")){
					String id=txtIDAdd.getText();
					String nama=txtNamaAdd.getText();
					Suppliers baru=new Suppliers(id, nama);
					st.insert(baru);
					JOptionPane.showMessageDialog(null, "Supplier ditambahkan", "Toko Obat", JOptionPane.INFORMATION_MESSAGE);
					resetAdd();
					updateTable();
				}else{
					JOptionPane.showMessageDialog(null, "Isian Tidak Boleh Kosong", "Toko Obat", JOptionPane.ERROR_MESSAGE);
					if(txtIDAdd.getText().equals("")){
						txtIDAdd.requestFocus();
					}else if(txtNamaAdd.getText().equals("")){
						txtNamaAdd.requestFocus();
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
	}
	
	private void contentEdit(){
		JLabel lblID = new JLabel("ID Supplier");
		JLabel lblNama = new JLabel("Nama Supplier");
		
		txtIDEdit.setEnabled(false);
		
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
		c.weightx=0.0;
		editPanel.add(lblNama, c);

		c.gridx = 3;
		c.gridy = 0;
		c.weightx=0.5;
		editPanel.add(txtNamaEdit, c);

		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth=2;
		c.weightx=0.0;
		editPanel.add(btnEditUbh, c);

		c.gridx = 2;
		c.gridy = 1;
		c.gridwidth=2;
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
				}
			}
		});
		
		btnEditUbh.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(!txtIDEdit.getText().equals("") && 
					!txtNamaEdit.getText().equals("")){
					String id=txtIDEdit.getText();
					String nama=txtNamaEdit.getText();
					Suppliers lama=new Suppliers(id, nama);
					st.update(id,lama);
					JOptionPane.showMessageDialog(null, "Supplier diubah", "Toko Obat", JOptionPane.INFORMATION_MESSAGE);
					resetEdit();
					updateTable();
				}else{
					if(txtIDEdit.getText().equals("")){
						JOptionPane.showMessageDialog(null, "Silakan pilih supplier yang akan diubah", "Toko Obat", JOptionPane.ERROR_MESSAGE);
					}else if(txtNamaEdit.getText().equals("")){
						JOptionPane.showMessageDialog(null, "Isian tidak boleh kosong", "Toko Obat", JOptionPane.ERROR_MESSAGE);
						txtNamaEdit.requestFocus();
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
	}

	private void contentDelete(){
		JLabel lblID = new JLabel("ID Supplier");
		JLabel lblNama = new JLabel("Nama Supplier");
		
		txtIDDelete.setEnabled(false);
		txtNamaDelete.setEnabled(false);
		
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
		c.weightx=0.0;
		deletePanel.add(lblNama, c);

		c.gridx = 3;
		c.gridy = 0;
		c.weightx=0.5;
		deletePanel.add(txtNamaDelete, c);

		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth=2;
		c.weightx=0.0;
		deletePanel.add(btnDeleteHps, c);

		c.gridx = 2;
		c.gridwidth=2;
		c.gridy = 1;
		deletePanel.add(btnDeleteBtl, c);
		
		deleteAction();
	}

	private void deleteAction(){
		table.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				if(deletePanel.isVisible()){
					JTable x=(JTable)e.getSource();
					txtIDDelete.setText(x.getValueAt(x.getSelectedRow(), 0).toString());
					txtNamaDelete.setText(x.getValueAt(x.getSelectedRow(), 1).toString());
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		btnDeleteHps.addActionListener(new ActionListener() {
			
			@SuppressWarnings("static-access")
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JOptionPane jop=new JOptionPane();
				if(!txtIDDelete.getText().equals("") && 
					!txtNamaDelete.getText().equals("")){
					String id=txtIDDelete.getText();
					String nama=txtNamaDelete.getText();
					int x = jop.showConfirmDialog(null, "Hapus supplier \""+nama+"\"?", "Toko Obat", jop.YES_NO_OPTION, jop.QUESTION_MESSAGE);
					if(x==jop.YES_OPTION){
						st.delete(id);
						jop.showMessageDialog(null, "Supplier dihapus", "Toko Obat", jop.INFORMATION_MESSAGE);
						resetDelete();
						updateTable();
					}
				}else{
					jop.showMessageDialog(null, "Silakan pilih supplier yang akan dihapus", "Toko Obat", jop.ERROR_MESSAGE);
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
	}

	private void initTable(){
		dataModel.addColumn("ID Supplier");
		dataModel.addColumn("Nama Supplier");
	    table.getColumnModel().getColumn(0).setPreferredWidth(100);
	    table.getColumnModel().getColumn(1).setPreferredWidth(400);
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
		
		Vector<Suppliers> vSupp =st.viewAll();
		for(int i=0; i<vSupp.size();i++){
			dataModel.addRow(new Object[]{vSupp.elementAt(i).getId()
										,vSupp.elementAt(i).getNama()});
		}
		table.setModel(dataModel);
	}
}
