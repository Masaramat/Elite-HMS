package com.ahms.pharmacymanagement;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import com.ahms.api.PharmacyManagementInterface;
import com.ahms.clientinterface.InterfaceGenerator;
import com.ahms.hmgt.entities.Vendor;
import com.ahms.pharmarcymgt.entities.Medicine;
import com.ahms.pharmarcymgt.entities.MedicinePurchaseItem;
import com.toedter.calendar.JDateChooser;
import java.awt.Dimension;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;

import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

public class MedicinePurchase extends JFrame {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel lblPulabel;
	private JPanel contentPane;
	private JTable table;
	private JTextField qtyField;
	private JTextField unitpriceField;
	@SuppressWarnings("rawtypes")
	private static JComboBox drugsBox;
	private JDateChooser dateChooser;
	
	
	private static ArrayList<Medicine> medlist;
	private ArrayList<Vendor> vendorlist;
	private ArrayList<MedicinePurchaseItem> purchased_items = new ArrayList<MedicinePurchaseItem>();
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MedicinePurchase frame = new MedicinePurchase();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws RemoteException 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public MedicinePurchase() throws RemoteException {
		setAlwaysOnTop(true);
		setTitle("Elite HMS :   Medicine Purchase ");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(450, 100, 546, 579);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		PharmacyManagementInterface ph_interface = InterfaceGenerator.getPharmManagementInterface();
		medlist = ph_interface.getAllMedicines();
		//vendorlist = ph_interface.getAllVendors();
		
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "New Purchase Entry", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 21, 509, 191);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblSelectDrug = new JLabel("Select Drug");
		lblSelectDrug.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblSelectDrug.setBounds(20, 58, 67, 20);
		panel.add(lblSelectDrug);
		
		JLabel lblQuantity = new JLabel("Quantity");
		lblQuantity.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblQuantity.setBounds(20, 89, 67, 20);
		panel.add(lblQuantity);
		
		JLabel lblUnitPrice = new JLabel("Total Price");
		lblUnitPrice.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblUnitPrice.setBounds(20, 120, 67, 20);
		panel.add(lblUnitPrice);
		
		lblPulabel = new JLabel("");
		lblPulabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblPulabel.setBounds(193, 89, 64, 20);
		panel.add(lblPulabel);
		
		
		
		qtyField = new JTextField();
		qtyField.setBounds(97, 89, 86, 20);
		panel.add(qtyField);
		qtyField.setColumns(10);
		
		unitpriceField = new JTextField();
		unitpriceField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				try{
				if(qtyField.getText().length()<1){
					
				}else{
					int one = Integer.parseInt(qtyField.getText());
					int two = Integer.parseInt(unitpriceField.getText());
					
					
					
				}
				}catch(Exception ex)
				{ex.printStackTrace();}			}
		});
		unitpriceField.setBounds(97, 120, 86, 20);
		panel.add(unitpriceField);
		unitpriceField.setColumns(10);
		
		
		JLabel lblDate = new JLabel("Date");
		lblDate.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblDate.setBounds(20, 27, 67, 20);
		panel.add(lblDate);
		
		dateChooser = new JDateChooser();
		dateChooser.setPreferredSize(new Dimension(40, 20));
		dateChooser.setDateFormatString("yyyy-MM-dd");
		Date date = new Date();
		dateChooser.setDate(date);
		dateChooser.setBounds(97, 27, 108, 21);
		panel.add(dateChooser);
		
		drugsBox = new JComboBox();  
		drugsBox.setFont(new Font("Tahoma", Font.BOLD, 11));
		drugsBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//totalField.setText(""); 
				qtyField.setText("");
				unitpriceField.setText("");
				
				
				//lblPulabel.setText(medlist.get(drugsBox.getSelectedIndex()).getPurchaseUnit());
				
			}
		});
		drugsBox.setBounds(97, 58, 377, 20);
		updateMedsCombo();
		
		drugsBox.requestFocus();
		AutoCompleteDecorator.decorate(drugsBox);
		panel.add(drugsBox);
	
		
		JButton btnNewEntry = new JButton("Clear");
		btnNewEntry.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnNewEntry.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				drugsBox.setSelectedIndex(0);
				qtyField.setText("");
				unitpriceField.setText("");

			}
		});
		btnNewEntry.setBounds(392, 155, 100, 23);
		panel.add(btnNewEntry);
		
		JButton btnAddToList = new JButton("Save Item");
		btnAddToList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
				if(drugsBox.getSelectedItem().toString().equalsIgnoreCase("--Select")){
					      
				}else if(qtyField.getText().length()<1){
					
				}else if(unitpriceField.getText().length()<1){
					
				}else{
					MedicinePurchaseItem mp = new MedicinePurchaseItem();
					mp.setMedicineCode(medlist.get(drugsBox.getSelectedIndex()).getMedicineCode());
					mp.setMedicimeName(medlist.get(drugsBox.getSelectedIndex()).getMedicineDescription());
					mp.setVendor("");
					mp.setDate("");
					mp.setQty(Integer.parseInt(qtyField.getText()));
					mp.setUnit_price(Integer.parseInt(unitpriceField.getText()));
					mp.setStaff("");
					
					ph_interface.saveMedicinePurchase(mp);
					
					showMessage("Success!");
					
					purchased_items.add(mp);
					updateTable(purchased_items);
					
					drugsBox.setSelectedIndex(0);
					qtyField.setText("");
					unitpriceField.setText("");
					
				}
					
				}catch(Exception ex){ex.printStackTrace();}
			}
		});
		btnAddToList.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnAddToList.setBounds(282, 155, 100, 23);
		panel.add(btnAddToList);
		
		JButton btnNewMeds = new JButton("New Medicine");
		btnNewMeds.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MedicineEntry meFrame = new MedicineEntry();
				meFrame.setVisible(true);
			}
		});
		btnNewMeds.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnNewMeds.setBounds(333, 26, 141, 23);
		panel.add(btnNewMeds);
		
		
		
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 223, 510, 256);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				table.getSelectedRow();
				
			}
		});
		scrollPane.setViewportView(table);
		
		
		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			dispose();
			}
		});
		btnClose.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnClose.setBounds(419, 506, 100, 23);
		contentPane.add(btnClose);
		
		JButton btnClearAll = new JButton("Clear List");
		btnClearAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			purchased_items.clear();
			updateTable(purchased_items);
			}
		});
		btnClearAll.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnClearAll.setBounds(309, 506, 100, 23);
		contentPane.add(btnClearAll);
		
		updateTable(purchased_items);
	}
	
	@SuppressWarnings("unchecked")
	public static void updateMedsCombo() {
		for(int i=0; i<medlist.size(); i++){
			drugsBox.addItem(medlist.get(i).getMedicineDescription());
		}
	}
	
	
	//client method to update the table
	public void updateTable(ArrayList<MedicinePurchaseItem> itemlist){
		Object[][] data = new Object[itemlist.size()][5];
		for(int i=0; i<itemlist.size(); i++){ 
			data[i][0] = (i+1);		 
			data[i][1] = itemlist.get(i).getMedicimeName();
			data[i][2] = itemlist.get(i).getQty();
			data[i][3] = itemlist.get(i).getUnit_price();	
			data[i][4] = itemlist.get(i).getDate();
			
			
		}
		Object[] columnNames = {"S/No", "Drug Name", "Quanitity", "Total Price", "Date" };
		
		DefaultTableModel model = new DefaultTableModel(data, columnNames);
		table.setModel(model);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getColumnModel().getColumn(0).setPreferredWidth(40);
		table.getColumnModel().getColumn(1).setPreferredWidth(300);
		table.getColumnModel().getColumn(2).setPreferredWidth(100);
		table.getColumnModel().getColumn(3).setPreferredWidth(100);
		table.getColumnModel().getColumn(4).setPreferredWidth(80);
		
		
	}
	
	
	public void showMessage(String message){
		JFrame jf = new JFrame();
		jf.setAlwaysOnTop(true);
		JOptionPane.showMessageDialog(jf, message);
	}
}
