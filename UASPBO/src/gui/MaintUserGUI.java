package gui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.table.*;

import transaksi.*;
import database.*;

@SuppressWarnings("serial")
public class MaintUserGUI extends JInternalFrame {
	//button
	private JButton btnAdd = new JButton("Tambah");
	private JButton btnEdit = new JButton("Ubah");
	private JButton btnDelete = new JButton("Hapus");

	//table
	private DefaultTableModel dataModel = new DefaultTableModel();
    private JTable table = new JTable(dataModel);

    //class_database
    private UserTable ut=new UserTable();

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
	private JPasswordField txtPassAdd = new JPasswordField();
	private JComboBox<String> cboJenisAdd;
	private JButton btnAddSpn=new JButton("Simpan");
	private JButton btnAddBtl=new JButton("Batal");

	//Komponen_Edit
	private JTextField txtIDEdit= new JTextField();
	private JTextField txtNamaEdit= new JTextField();
	private JPasswordField txtPassEdit = new JPasswordField();
	private JComboBox<String> cboJenisEdit;
	private JButton btnEditUbh=new JButton("Ubah");
	private JButton btnEditBtl=new JButton("Batal");

	//Komponen_Edit
	private JTextField txtIDDelete= new JTextField();
	private JTextField txtNamaDelete= new JTextField();
	private JTextField txtJenisDelete = new JTextField();
	private JButton btnDeleteHps=new JButton("Hapus");
	private JButton btnDeleteBtl=new JButton("Batal");
	
	private JFrame parent;

	public MaintUserGUI(String judul, int posX, int posY, int width,
			int height, JFrame parent) {
		// TODO Auto-generated constructor stub		
		super(judul);
		
		this.parent=parent;

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
		JLabel lblJudul = new JLabel("<html><h2><font color='white'>MAINTENANCE USER</font></h2></html>");
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
		JLabel lblID = new JLabel("ID Pengguna");
		JLabel lblNama = new JLabel("Nama Pengguna");
		JLabel lblPass = new JLabel("Password");
		
		Vector<String> isiCboAdd=new Vector<String>();
		isiCboAdd.add("-Jenis Pengguna-");
		if(!((MainGUI)parent).getUsrLog().equals("adm")){
			btnEdit.setEnabled(true);
			btnDelete.setEnabled(true);
			isiCboAdd.add("Owner");
			isiCboAdd.add("Admin");
			isiCboAdd.add("Kasir");
		}else{
			btnEdit.setEnabled(false);
			btnDelete.setEnabled(false);
			isiCboAdd.add("Admin");
		}
		cboJenisAdd = new JComboBox<String>(isiCboAdd);
		
		addPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		c.fill=GridBagConstraints.HORIZONTAL;
		c.insets=new Insets(10, 10, 10, 10);
		c.gridx = 0;
		c.gridy = 0;
		c.weightx=0.1;
		addPanel.add(lblID, c);

		c.gridx = 1;
		c.gridy = 0;
		c.weightx=0.5;
		addPanel.add(txtIDAdd, c);

		c.gridx = 2;
		c.gridy = 0;
		c.gridwidth=2;
		c.weightx=0.0;
		addPanel.add(cboJenisAdd, c);

		c.gridx = 0;
		c.gridy = 1;
		c.insets=new Insets(0, 10, 10, 10);
		c.weightx=0.1;
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
		addPanel.add(lblPass, c);

		c.gridx = 1;
		c.gridy = 2;
		addPanel.add(txtPassAdd, c);

		c.gridx = 2;
		c.gridy = 2;
		addPanel.add(btnAddSpn, c);

		c.gridx = 3;
		c.gridy = 2;
		addPanel.add(btnAddBtl, c);
		
		addAction();
	}
	
	private void addAction(){
		btnAddSpn.addActionListener(new ActionListener() {
			
			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(!txtIDAdd.getText().equals("") && 
					!txtNamaAdd.getText().equals("") &&
					cboJenisAdd.getSelectedIndex()!= 0 &&
					!txtPassAdd.getText().equals("")){
					String id=txtIDAdd.getText();
					String nama=txtNamaAdd.getText();
					String pass=(txtPassAdd.getText());
					String jenis=cboJenisAdd.getSelectedItem().toString();
					if(jenis.equals("Owner")){
						jenis="O";
					}else if(jenis.equals("Admin")){
						jenis="A";
					}else if(jenis.equals("Kasir")){
						jenis="K";
					}
					Users baru=new Users(id, nama, pass, jenis);
					ut.insert(baru);
					JOptionPane.showMessageDialog(null, "Pengguna ditambahkan", "Toko Obat", JOptionPane.INFORMATION_MESSAGE);
					resetAdd();
					updateTable();

					if(((MainGUI)parent).getUsrLog().equals("adm")){
						((MainGUI)parent).setLogout();
					}
				}else{
					JOptionPane.showMessageDialog(null, "Isian Tidak Boleh Kosong", "Toko Obat", JOptionPane.ERROR_MESSAGE);
					if(txtIDAdd.getText().equals("")){
						cboJenisAdd.requestFocus();
					}else if(txtNamaAdd.getText().equals("")){
						txtNamaAdd.requestFocus();
					}else if(txtPassAdd.getText().equals("")){
						txtPassAdd.requestFocus();
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
		txtPassAdd.setText(null);
		cboJenisAdd.setSelectedIndex(0);
	}
	
	private void contentEdit(){
		JLabel lblID = new JLabel("ID Pengguna");
		JLabel lblNama = new JLabel("Nama Pengguna");
		JLabel lblPass = new JLabel("Password");
		
		txtIDEdit.setEnabled(false);

		Vector<String> isiCboAdd=new Vector<String>();
		isiCboAdd.add("-Jenis Pengguna-");
		isiCboAdd.add("Owner");
		isiCboAdd.add("Admin");
		isiCboAdd.add("Kasir");
		cboJenisEdit = new JComboBox<String>(isiCboAdd);

		editPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		c.fill=GridBagConstraints.HORIZONTAL;
		c.insets=new Insets(10, 10, 10, 10);
		c.gridx = 0;
		c.gridy = 0;
		c.weightx=0.1;
		editPanel.add(lblID, c);

		c.gridx = 1;
		c.gridy = 0;
		c.weightx=0.5;
		editPanel.add(txtIDEdit, c);

		c.gridx = 2;
		c.gridy = 0;
		c.gridwidth=2;
		c.weightx=0.0;
		editPanel.add(cboJenisEdit, c);

		c.gridx = 0;
		c.gridy = 1;
		c.insets=new Insets(0, 10, 10, 10);
		c.weightx=0.1;
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
		editPanel.add(lblPass, c);

		c.gridx = 1;
		c.gridy = 2;
		editPanel.add(txtPassEdit, c);

		c.gridx = 2;
		c.gridy = 2;
		editPanel.add(btnEditUbh, c);

		c.gridx = 3;
		c.gridy = 2;
		editPanel.add(btnEditBtl, c);
		
		editAction();
	}

	private void editAction(){
		table.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				if(editPanel.isVisible()){
					JTable x=(JTable)e.getSource();
					txtIDEdit.setText(x.getValueAt(x.getSelectedRow(), 0).toString());
					txtNamaEdit.setText(x.getValueAt(x.getSelectedRow(), 1).toString());
					txtPassEdit.setText(null);
					cboJenisEdit.setSelectedItem(x.getValueAt(x.getSelectedRow(), 2).toString());
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
		
		btnEditUbh.addActionListener(new ActionListener() {
			
			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(!txtIDEdit.getText().equals("") && 
					!txtNamaEdit.getText().equals("") &&
					cboJenisEdit.getSelectedIndex()!= 0){
					String id=txtIDEdit.getText();
					String nama=txtNamaEdit.getText();
					String jenis=cboJenisEdit.getSelectedItem().toString();
					if(jenis.equals("Owner")){
						jenis="O";
					}else if(jenis.equals("Admin")){
						jenis="A";
					}else if(jenis.equals("Kasir")){
						jenis="K";
					}
					
					String pass=ut.cariById(id).getPass();
					if(!txtPassEdit.getText().equals("")){
						pass=txtPassEdit.getText();
					}
					
					Users lama=new Users(id, nama, pass, jenis);
					ut.update(id,lama);
					JOptionPane.showMessageDialog(null, "Pengguna diubah", "Toko Obat", JOptionPane.INFORMATION_MESSAGE);
					resetEdit();
					updateTable();
				}else{
					if(txtIDEdit.getText().equals("")){
						JOptionPane.showMessageDialog(null, "Silakan pilih pengguna yang akan diubah", "Toko Obat", JOptionPane.ERROR_MESSAGE);
					}else if(txtNamaEdit.getText().equals("")){
						JOptionPane.showMessageDialog(null, "Isian tidak boleh kosong", "Toko Obat", JOptionPane.ERROR_MESSAGE);
						txtNamaEdit.requestFocus();
					}else if(txtPassEdit.getText().equals("")){
						JOptionPane.showMessageDialog(null, "Isian tidak boleh kosong", "Toko Obat", JOptionPane.ERROR_MESSAGE);
						txtPassEdit.requestFocus();
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
		txtPassEdit.setText(null);
		cboJenisEdit.setSelectedIndex(0);
	}

	private void contentDelete(){
		JLabel lblID = new JLabel("ID Pengguna");
		JLabel lblNama = new JLabel("Nama Pengguna");
		JLabel lblJenis = new JLabel("Jenis Pengguna");
		
		txtIDDelete.setEnabled(false);
		txtJenisDelete.setEnabled(false);
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
		deletePanel.add(lblJenis, c);

		c.gridx = 3;
		c.gridy = 0;
		c.weightx=0.5;
		deletePanel.add(txtJenisDelete, c);

		c.gridx = 0;
		c.gridy = 1;
		c.insets=new Insets(0, 10, 10, 10);
		c.weightx=0.0;
		deletePanel.add(lblNama, c);

		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth=3;
		c.weightx=0.5;
		deletePanel.add(txtNamaDelete, c);
		
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth=2;
		c.weightx=0.0;
		deletePanel.add(btnDeleteHps, c);

		c.gridx = 2;
		c.gridy = 2;
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
					txtJenisDelete.setText(x.getValueAt(x.getSelectedRow(), 2).toString());
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
					int x = jop.showConfirmDialog(null, "Hapus pengguna \""+nama+"\"?", "Toko Obat", jop.YES_NO_OPTION, jop.QUESTION_MESSAGE);
					if(x==jop.YES_OPTION){
						ut.delete(id);
						jop.showMessageDialog(null, "Pengguna dihapus", "Toko Obat", jop.INFORMATION_MESSAGE);
						resetDelete();
						updateTable();
					}
				}else{
					jop.showMessageDialog(null, "Silakan pilih pengguna yang akan dihapus", "Toko Obat", jop.ERROR_MESSAGE);
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
		txtJenisDelete.setText(null);
	}

	private void initTable(){
		dataModel.addColumn("ID");
		dataModel.addColumn("Nama Pengguna");
		dataModel.addColumn("Jenis Pengguna");
	    table.getColumnModel().getColumn(1).setPreferredWidth(400);
	    table.getColumnModel().getColumn(2).setPreferredWidth(200);
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
		
		Vector<Users> vProd =ut.viewAll();
		for(int i=0; i<vProd.size();i++){
			if(!vProd.elementAt(i).getId().equals(((MainGUI)parent).getUsrLog())){
				String jenis=null;
				if(vProd.elementAt(i).getJenis().equals("O")){
					jenis="Owner";
				}else if(vProd.elementAt(i).getJenis().equals("A")){
					jenis="Admin";
				}else if(vProd.elementAt(i).getJenis().equals("K")){
					jenis="Kasir";
				}
				dataModel.addRow(new Object[]{vProd.elementAt(i).getId()
											,vProd.elementAt(i).getNama()
											,jenis});
			}
		}
		table.setModel(dataModel);
	}
}
