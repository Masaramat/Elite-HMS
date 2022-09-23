package com.ahms.pharmacymanagement;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import com.ahms.api.PharmacyManagementInterface;
import com.ahms.clientinterface.InterfaceGenerator;
import com.ahms.clinicmgt.entities.PrescriptionOrderItem;

import javax.swing.JButton;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.rmi.RemoteException;
import javax.swing.UIManager;
import java.awt.Color;
import javax.swing.SwingConstants;

public class DrugsDispensary extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField invoiceField;
	private JTable table;
	private JTextField priceField;
	private JTextField amtPayableField;
	
	private ArrayList<PrescriptionOrderItem>  poiList; 
	private ArrayList<Integer> selItemList = new ArrayList<>();
	PharmacyManagementInterface ph_interface = InterfaceGenerator.getPharmManagementInterface();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DrugsDispensary frame = new DrugsDispensary("");
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
	public DrugsDispensary(String invoice_no) throws RemoteException {
		setResizable(false);
		setTitle("Elite HMS - Drug Dispensary");
		setAlwaysOnTop(true);
		setBounds(400, 100, 647, 539);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnDispenseDrugs = new JButton("Dispense ");
		btnDispenseDrugs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(poiList.size()<=0){
					//do nothing
				}else if(amtPayableField.getText().length()<1){
					//do nothing
				}else{				
					try {
						dispensePrescription();
						poiList = ph_interface.getPrescriptions(invoiceField.getText());
						updatePendingPrescriptionTable(poiList);
						
						
						 priceField.setText("");
						// discountField.setText("");
						 amtPayableField.setText("");
						
						
					} catch (RemoteException e) {	e.printStackTrace();	}
					
				} 
				
				
				
			}
		});
		btnDispenseDrugs.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnDispenseDrugs.setBounds(367, 464, 132, 23);
		contentPane.add(btnDispenseDrugs);
		
		JLabel lblInvoiceNo = new JLabel("Invoice No");
		lblInvoiceNo.setBounds(10, 52, 83, 23);
		contentPane.add(lblInvoiceNo);
		
		invoiceField = new JTextField();
		invoiceField.setEditable(false);
		invoiceField.setBounds(103, 53, 132, 23);
		contentPane.add(invoiceField);
		invoiceField.setText(invoice_no);
		invoiceField.setColumns(10);
		
		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				dispose();
			}
		});
		btnClose.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnClose.setBounds(509, 464, 108, 23);
		contentPane.add(btnClose);
		
		JLabel lblTotalPrice = new JLabel("Price");
		lblTotalPrice.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblTotalPrice.setBounds(28, 415, 49, 20);
		contentPane.add(lblTotalPrice);
		
		priceField = new JTextField(); 
		priceField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				calculateAmtPayable();
			}
			
		});
		priceField.setBounds(72, 415, 83, 20);
		contentPane.add(priceField);
		priceField.setColumns(10);
		
		amtPayableField = new JTextField();
		amtPayableField.setColumns(10);
		amtPayableField.setEditable(false);
		amtPayableField.setBounds(335, 415, 68, 20);
		contentPane.add(amtPayableField);
		
		JLabel lblTotalPayable = new JLabel("Amount Payable");
		lblTotalPayable.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblTotalPayable.setBounds(237, 415, 95, 20);
		contentPane.add(lblTotalPayable);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 98, 607, 287);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		scrollPane.setViewportView(table);
		
		JLabel lblNewLabel = new JLabel("Drug Dispensary");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel.setBounds(10, 11, 611, 23);
		contentPane.add(lblNewLabel);
		
		poiList = ph_interface.getPrescriptions(invoiceField.getText());
		updatePendingPrescriptionTable(poiList);
		
		
	}
	

	//client method to update prescription table
	public void updatePendingPrescriptionTable(ArrayList<PrescriptionOrderItem> list){
		
		Object[][] data = new Object[list.size()][7];
		for(int i=0; i<list.size(); i++){ 
			data[i][0] = (i+1);			
			data[i][1] = list.get(i).getMedicineName();
			data[i][2] = list.get(i).getDose();
			data[i][3] = list.get(i).getFrequency();
			data[i][4] = list.get(i).getDuration();
			data[i][5] = list.get(i).getDispenseQauntity();
			data[i][6] = list.get(i).getOrderState();
			
		}
		
		Object[] columnNames = { "S/No", "Medicine Name", "Dose", "Freq.", "Duration", "Dispense Qty", "Status"};
		DefaultTableModel model = new DefaultTableModel(data, columnNames);
		table.setModel(model);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getColumnModel().getColumn(0).setPreferredWidth(50);	
		table.getColumnModel().getColumn(1).setPreferredWidth(300);
		table.getColumnModel().getColumn(2).setPreferredWidth(90);
		table.getColumnModel().getColumn(3).setPreferredWidth(90);
		table.getColumnModel().getColumn(4).setPreferredWidth(90);
		table.getColumnModel().getColumn(5).setPreferredWidth(100);
		table.getColumnModel().getColumn(6).setPreferredWidth(100);
	}
		
	//client method to package selected prescriptions. calls server method to update prescription_status
	public void dispensePrescription(){
		if(table.getRowCount()>0){
			selItemList.clear();
			if(table.getSelectedRowCount()>0){
				int selectrow[] = table.getSelectedRows();
				for (int i=0; i<selectrow.length; i++){
					selItemList.add(poiList.get(selectrow[i]).getSerialNo());
				}
				try{
					ph_interface.updatePrescriptionStatus(invoiceField.getText(), selItemList, Double.parseDouble(amtPayableField.getText()));
				showMessage("Dispensed Successfully!");
				}catch(Exception ex){
					showMessage("Please Enter a Valid Price");
				}
			}
			
		}
		
	}
	
	
		
	//a standard client method for displaying popup messages on frame that is setAlwaysOnTop(true)
	public static void showMessage(String message){
		final JDialog dialog = new JDialog();
		dialog.setAlwaysOnTop(true);
		JOptionPane.showMessageDialog(dialog, message);
	}

		
	//client method to calculate pay amount
	public void calculateAmtPayable(){
		try{
		double price = Double.parseDouble(priceField.getText());
		//double discount = Double.parseDouble(discountField.getText());
		
		//double amt_payable = (price - discount);
		amtPayableField.setText(price+"");
		}catch(Exception c){ 
			showMessage("Invalid Price value");
			amtPayableField.setText("");
			}
	}

}
