package com.ahms.pharmacymanagement;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;

import com.ahms.api.PharmacyManagementInterface;
import com.ahms.clientinterface.InterfaceGenerator;
import com.ahms.pharmarcymgt.entities.Medicine;
import com.ahms.pharmarcymgt.entities.MedicineStockItem;
import com.ahms.pharmarcymgt.entities.MedicineWithdrawalItem;
import com.toedter.calendar.JDateChooser;
import java.awt.Dimension;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import java.awt.Color;
import javax.swing.SwingConstants;

public class MedicineWithdrawal extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField qtyField;
	private JTable table;
	private JComboBox comboBox;
	
	private ArrayList<MedicineStockItem> medlist;
	ArrayList<MedicineWithdrawalItem> mwiList = new ArrayList<>();
	private JTextField stockQtyField;
	
	int xyyyy = 0;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MedicineWithdrawal frame = new MedicineWithdrawal();
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
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public MedicineWithdrawal() throws RemoteException {
		setAlwaysOnTop(true);
		setTitle("Elite HMS - Pharmacy Medicine Withrawal");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(400, 100, 514, 529);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		PharmacyManagementInterface ph_interface = InterfaceGenerator.getPharmManagementInterface();
		medlist = ph_interface.getCurrentStockItems();
		
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(9, 234, 478, 194);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JButton button = new JButton("New button");
		button.setBounds(469, 516, -68, -11);
		contentPane.add(button);
		
		
		JButton btnClearAll = new JButton("Clear List");
		btnClearAll.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnClearAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mwiList.clear();
				updateTable(mwiList);
			}
		});
		btnClearAll.setBounds(277, 456, 100, 23);
		contentPane.add(btnClearAll);
		
		JButton btnClose = new JButton("Close");
		btnClose.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btnClose.setBounds(387, 456, 100, 23);
		contentPane.add(btnClose);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Medicine Details", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(9, 51, 478, 172);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JButton btnAddToList = new JButton("Withdraw Stock");
		btnAddToList.setBounds(330, 139, 132, 22);
		panel.add(btnAddToList);
		btnAddToList.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		JLabel label = new JLabel("Date");
		label.setBounds(21, 26, 110, 20);
		panel.add(label);
		label.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setBounds(141, 26, 110, 21);
		panel.add(dateChooser);
		dateChooser.getCalendarButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		dateChooser.setPreferredSize(new Dimension(40, 20));
		dateChooser.setDateFormatString("yyyy-MM-dd");
		dateChooser.setDate(new Date());
		
		JLabel lblSelectDrug = new JLabel("Select Drug");
		lblSelectDrug.setBounds(21, 57, 110, 20);
		panel.add(lblSelectDrug);
		lblSelectDrug.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		comboBox = new JComboBox();
		for(int i=0; i<medlist.size(); i++){
			comboBox.addItem(medlist.get(i).getDrugName());
		}
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				xyyyy = comboBox.getSelectedIndex();
				stockQtyField.setText(medlist.get(xyyyy).getAvailableQty()+"");
			}
		});
		comboBox.setBounds(141, 57, 301, 20);
		panel.add(comboBox);
		comboBox.requestFocus();
		AutoCompleteDecorator.decorate(comboBox);
		comboBox.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		
		
		JLabel lblQuantity = new JLabel("Withdraw Quantity ");
		lblQuantity.setBounds(21, 119, 110, 20);
		panel.add(lblQuantity);
		lblQuantity.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		qtyField = new JTextField();
		qtyField.setBounds(141, 119, 66, 20);
		panel.add(qtyField);
		qtyField.setColumns(10);
		
		stockQtyField = new JTextField();
		stockQtyField.setText(medlist.get(xyyyy).getAvailableQty()+"");
		stockQtyField.setEditable(false);
		stockQtyField.setColumns(10);
		stockQtyField.setBounds(141, 88, 66, 20);
		panel.add(stockQtyField);
		
		JLabel lblQtyInStock = new JLabel("Quantity in Stock");
		lblQtyInStock.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblQtyInStock.setBounds(21, 88, 110, 20);
		panel.add(lblQtyInStock);
		
		JLabel lblNewLabel = new JLabel("Pharmacy Medicine Withdrawal");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel.setBounds(9, 11, 479, 29);
		contentPane.add(lblNewLabel);
		
		btnAddToList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					int qty = Integer.parseInt(qtyField.getText());
					int stockQty = Integer.parseInt(stockQtyField.getText());
					
					if(qty <= 0) {
						showMessage("Please enter a valid withdrawal quatity (not less than 1). ");
						qtyField.setText("");
						
					}else if (stockQty <= 0) {
						showMessage("Medicine is out of stock");
						qtyField.setText("");
					}else {
						MedicineWithdrawalItem mwi = new MedicineWithdrawalItem();
						mwi.setDrugName(medlist.get(comboBox.getSelectedIndex()).getDrugName());
						mwi.setMedicineCode(medlist.get(comboBox.getSelectedIndex()).getMedicineCode());
						mwi.setQuantity(qty);
						
						ph_interface.saveMedicineWithdrawal(mwi);
						showMessage("Success!");
						
						mwiList.add(mwi);
						updateTable(mwiList);
						
						medlist = ph_interface.getCurrentStockItems();
						stockQtyField.setText(medlist.get(xyyyy).getAvailableQty()+"");
						
						comboBox.setSelectedIndex(0);
						qtyField.setText("");
					}				
					
				}catch(Exception e){
					showMessage("Please enter a valid withdrawal quatity (not less than 1). ");
					e.printStackTrace();
				}
			}
		});
		
		updateTable(mwiList);
	}
	
	
	//client method to update the table
		public void updateTable(ArrayList<MedicineWithdrawalItem> itemlist){
			Object[][] data = new Object[itemlist.size()][3];
			for(int i=0; i<itemlist.size(); i++){ 
				data[i][0] = (i+1);				
				data[i][1] = itemlist.get(i).getDrugName();
				data[i][2] = itemlist.get(i).getQuantity();
				
			}
			Object[] columnNames = {"S/No", "Drug Name", "Quanitity" };
			
			DefaultTableModel model = new DefaultTableModel(data, columnNames);
			table.setModel(model);
			table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			table.getColumnModel().getColumn(0).setPreferredWidth(60);
			table.getColumnModel().getColumn(1).setPreferredWidth(320);
			table.getColumnModel().getColumn(2).setPreferredWidth(120);		
			
		}
		
	public void showMessage(String message){
		JFrame jf = new JFrame();
		jf.setAlwaysOnTop(true);
		JOptionPane.showMessageDialog(jf, message);
	}	
}
