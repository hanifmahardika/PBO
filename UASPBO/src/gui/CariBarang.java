package gui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.table.*;

import transaksi.*;
import database.*;

@SuppressWarnings("serial")
public class CariBarang extends JDialog{
	private JComboBox<String> cboSupp;

	//table
	private DefaultTableModel dataModel = new DefaultTableModel();
    private JTable table = new JTable(dataModel);

    private ProductTable pt=new ProductTable();
	private SupplierTable st=new SupplierTable();
	
	private JInternalFrame owner;
	
	private String s="";
	
	public CariBarang(JInternalFrame owner, String title, ModalityType modal){
		super((JFrame)owner.getParent(), title, modal);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		Vector<String> isiCboAdd=new Vector<String>(st.viewAllName());
		isiCboAdd.add(0,"Semua Supplier");
		cboSupp = new JComboBox<String>(isiCboAdd);
		
		setLayout(new GridBagLayout());
		GridBagConstraints c =new GridBagConstraints();
		c.fill=GridBagConstraints.HORIZONTAL;
		c.insets=new Insets(10, 10, 10, 10);
		c.gridx=0;
		c.gridy=0;
		c.weightx=0.1;
		add(cboSupp,c);

		c.fill=GridBagConstraints.BOTH;
		c.insets=new Insets(0, 10, 10, 10);
		c.gridx=0;
		c.gridy=1;
		c.weighty=0.1;
		add(new JScrollPane(table),c);
		
		this.owner=owner;
		
		initTable();
		updateTable();
		klikTable();
		klikCbo();
	}
	
	private void klikCbo(){
		cboSupp.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String s="";
				if(cboSupp.getSelectedIndex()!=0){
					s=cboSupp.getSelectedItem().toString();
				}
				setSup(s);
			}
		});
	}

	public void setMinSize(int width,int height){
		setMinimumSize(new Dimension(width, height));
	}
	
	public void resetTable(){
		cboSupp.setSelectedIndex(0);
		setSup("");
	}

	private void setSup(String s){
		this.s=s;
		updateTable();
	}

	private void initTable(){
		dataModel.addColumn("ID");
		dataModel.addColumn("Nama Produk");
		dataModel.addColumn("Supplier");
		dataModel.addColumn("Stock");
		
		table.getColumnModel().getColumn(0).setPreferredWidth(90);
		table.getColumnModel().getColumn(1).setPreferredWidth(500);
		table.getColumnModel().getColumn(2).setPreferredWidth(200);
		table.getColumnModel().getColumn(3).setPreferredWidth(70);
	}

	private void updateTable(){
		for(int i=dataModel.getRowCount()-1; i>-1;i--){
			dataModel.removeRow(i);
		}
		
		Vector<Products> vProd=pt.viewAll();

		if(!s.equals("")){
			vProd =pt.cariBySupp(s);
		}
		
		for(int i=0; i<vProd.size();i++){
			dataModel.addRow(new Object[]{vProd.elementAt(i).getId()
										,vProd.elementAt(i).getNama()
										,vProd.elementAt(i).getIdSup()
										});
			//,vProd.elementAt(i).getStok()
		}
		table.setModel(dataModel);
	}
	
	private void klikTable(){
		table.addKeyListener(new KeyAdapter() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				if(table.isEditing()){
					table.getCellEditor().cancelCellEditing();
				}
			}
		});
		
		table.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				if(e.getClickCount()==2){
					int selRow=table.getSelectedRow();
					if(owner.getClass().equals(ViewLaporanGUI.class)){
						((ViewLaporanGUI) owner).setTxtID(table.getValueAt(selRow, 0).toString());
						dispose();
					}else if(owner.getClass().getName().equals(MaintStockGUI.class.getName())){
						((MaintStockGUI) owner).setTxtID(table.getValueAt(selRow, 0).toString());
						dispose();
					}else if(owner.getClass().getName().equals(PointOfSaleGUI.class.getName())){
						Products prod=pt.cariById(table.getValueAt(selRow, 0).toString());
						int stock=Integer.parseInt(table.getValueAt(selRow, 3).toString());
						if(stock!=0){
							((PointOfSaleGUI) owner).setTxtID(prod.getId());
							((PointOfSaleGUI) owner).setTxtNama(prod.getNama());
							((PointOfSaleGUI) owner).setTxtHarga(prod.getHarga());
							dispose();
						}else{
							JTable x = (JTable)e.getSource();
							JOptionPane.showMessageDialog(x.getParent(), "Stok Habis", "Toko Obat", JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			}
		});
		
		table.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
				if(table.isEditing()){
					table.getCellEditor().cancelCellEditing();
				}
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				if(table.isEditing()){
					table.getCellEditor().cancelCellEditing();
				}
			}
		});
	}
}