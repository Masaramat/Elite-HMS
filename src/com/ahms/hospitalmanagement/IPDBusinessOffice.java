package com.ahms.hospitalmanagement;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;

import com.ahms.api.HospitalManagementInterface;
import com.ahms.clientinterface.InterfaceGenerator;
import com.ahms.hmgt.entities.Deposit;
import com.ahms.hmgt.entities.IPInvoice;
import com.toedter.calendar.JDateChooser;
import java.awt.Dimension;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;

public class IPDBusinessOffice extends JFrame {

	private JPanel contentPane;
	private JTextField depositAmountField;
	private JTable table;
	private JTextField TotalReceivedField;
	private JTextField totalDueField;
	private ButtonGroup bgrp = new ButtonGroup();
	private JTextField grandTotalField;
	
	private ArrayList<IPInvoice> invoice_list;
	private JTextField invoiceField;
	private JTextField nameField;
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					IPDBusinessOffice frame = new IPDBusinessOffice(new IPInvoice());
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public IPDBusinessOffice(IPInvoice invoice) {
		setTitle("Inpatient Business Office");
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(400, 120, 626, 592);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		HospitalManagementInterface hm_interface = InterfaceGenerator.getHospitalManagementInterface();
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 91, 587, 411);
		contentPane.add(tabbedPane);
		
		JPanel deposit_tab = new JPanel();
		tabbedPane.addTab("Deposit", null, deposit_tab, null);
		deposit_tab.setLayout(null);
		
		JLabel label = new JLabel("Pay Mode");
		label.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label.setBounds(10, 21, 64, 22);
		deposit_tab.add(label);
		
		JComboBox depositBankBox = new JComboBox();
		depositBankBox.setModel(new DefaultComboBoxModel(new String[] {"Select ", "Access Bank", "UBA"}));
		depositBankBox.setEnabled(false);
		depositBankBox.setBounds(72, 54, 119, 22);
		deposit_tab.add(depositBankBox);
		
		JComboBox depositModeBox = new JComboBox();
		depositModeBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(depositModeBox.getSelectedItem().toString().equalsIgnoreCase("transfer") || depositModeBox.getSelectedItem().toString().equalsIgnoreCase("pos")){
					depositBankBox.setEnabled(true);
				}else{
					depositBankBox.setEnabled(false);
				}
			}
		});
		depositModeBox.setModel(new DefaultComboBoxModel(new String[] {"Select", "Cash", "Transfer", "POS"}));
		depositModeBox.setBounds(72, 22, 119, 21);
		deposit_tab.add(depositModeBox);
		
		JLabel label_1 = new JLabel("Bank");
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label_1.setBounds(10, 54, 77, 22);
		deposit_tab.add(label_1);
		
		depositAmountField = new JTextField();
		depositAmountField.setColumns(10);
		depositAmountField.setBounds(72, 87, 86, 20);
		deposit_tab.add(depositAmountField);
		

		JLabel label_3 = new JLabel("Invoice No");
		label_3.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label_3.setBounds(387, 57, 89, 23);
		contentPane.add(label_3);
		
		invoiceField = new JTextField();
		invoiceField.setEditable(false); 
		invoiceField.setColumns(10);
		invoiceField.setBounds(454, 58, 119, 20);
		contentPane.add(invoiceField);
		
		JLabel label_4 = new JLabel("Patient Name");
		label_4.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label_4.setBounds(10, 58, 111, 20);
		contentPane.add(label_4);
		
		nameField = new JTextField();	
		nameField.setEditable(false);
		nameField.setColumns(10);
		nameField.setBounds(97, 56, 190, 22);
		contentPane.add(nameField);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 136, 563, 157);
		deposit_tab.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		
		
		JLabel label_2 = new JLabel("Amount");
		label_2.setBounds(10, 87, 64, 22);
		deposit_tab.add(label_2);
		label_2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		JLabel label_8 = new JLabel("Total Received");
		label_8.setBounds(281, 316, 94, 16);
		deposit_tab.add(label_8);
		label_8.setHorizontalAlignment(SwingConstants.CENTER);
		label_8.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		TotalReceivedField = new JTextField();
		TotalReceivedField.setBounds(385, 314, 86, 20);
		deposit_tab.add(TotalReceivedField);
		TotalReceivedField.setEditable(false);
		TotalReceivedField.setColumns(10);
		
		JLabel lblBalance = new JLabel("Balance");
		lblBalance.setBounds(10, 347, 94, 16);
		deposit_tab.add(lblBalance);
		lblBalance.setHorizontalAlignment(SwingConstants.LEFT);
		lblBalance.setForeground(Color.RED);
		lblBalance.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		totalDueField = new JTextField();
		totalDueField.setBounds(114, 345, 86, 20);
		deposit_tab.add(totalDueField);
		totalDueField.setEditable(false);
		totalDueField.setColumns(10);
		
		JLabel lblGrandTotal = new JLabel("Total Due");
		lblGrandTotal.setBounds(10, 313, 94, 22);
		deposit_tab.add(lblGrandTotal);
		lblGrandTotal.setForeground(Color.RED);
		
		grandTotalField = new JTextField();
		grandTotalField.setBounds(114, 314, 86, 20);
		deposit_tab.add(grandTotalField);
		grandTotalField.setEditable(false);
		grandTotalField.setColumns(10);
		
		JButton btnPreviewReciept = new JButton("Preview Reciept");
		btnPreviewReciept.setBounds(305, 513, 136, 23);
		contentPane.add(btnPreviewReciept);
		btnPreviewReciept.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				PaymentReceiptShowFrame frame = new PaymentReceiptShowFrame(invoiceField.getText());
				frame.setVisible(true);
			}
		});
		btnPreviewReciept.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		JButton btnSavePayment = new JButton("Save Payment");
		btnSavePayment.setBounds(159, 513, 136, 23);
		contentPane.add(btnSavePayment);
		btnSavePayment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(invoiceField.getText().length()<1){
				showMessage("Select Invoice");	
				}else if(depositModeBox.getSelectedItem().toString().equalsIgnoreCase("select")){
					showMessage("Select mode of payment");
					}else if(depositBankBox.getSelectedItem().toString().equalsIgnoreCase("select") && depositBankBox.isEnabled()){
						showMessage("Select bank");
					} else if(depositAmountField.getText().length()<1){
						showMessage(" Enter amount");
					} else{
						String invoice_no = invoiceField.getText();
						String paymode = depositModeBox.getSelectedItem().toString();
						String bank = depositBankBox.getSelectedItem().toString();
						double amount = Double.parseDouble(depositAmountField.getText());
						
						try {
							hm_interface.saveDeposit(invoice_no, paymode, bank, amount);
							
							IPInvoice ip = hm_interface.getOneIPDInvoice(invoice_no);	
							showMessage("Success!");
							updateDeposit(hm_interface.getAllDeposits(invoice_no));
							updateScreen(ip);
							depositAmountField.setText("");
							depositModeBox.setSelectedIndex(0);
							depositBankBox.setSelectedIndex(0);
						} catch (RemoteException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						
					}
				}
			
		});
		btnSavePayment.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		JButton btnClose = new JButton("Close");
		btnClose.setBounds(451, 513, 122, 23);
		contentPane.add(btnClose);
		btnClose.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		JLabel lblNewLabel = new JLabel("Inpatient Hospital Bill Payment");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel.setBounds(10, 11, 587, 28);
		contentPane.add(lblNewLabel);
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		
		
		try {
			updateScreen(invoice);
			updateDeposit(hm_interface.getAllDeposits(invoice.getInvoiceNo()));
		} catch (RemoteException e) {
		
			e.printStackTrace();
		}
		
		
		
	}
	
	
		
	public void updateDeposit(ArrayList<Deposit> list){
		Object[][] data = new Object[list.size()][4];
		for(int i=0; i<list.size(); i++){ 
			data[i][0] = (i+1);
			data[i][1] = list.get(i).getPayment_mode();
			data[i][2] = list.get(i).getBank();
			data[i][3] = list.get(i).getAmount();
							
		}
		
		Object[] columnNames = {"S/No",  "Payment Mode", "Bank", "Amount"};
		
		DefaultTableModel model = new DefaultTableModel(data, columnNames);
		table.setModel(model);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getColumnModel().getColumn(0).setPreferredWidth(100);
		table.getColumnModel().getColumn(1).setPreferredWidth(150);
		table.getColumnModel().getColumn(2).setPreferredWidth(180);
		table.getColumnModel().getColumn(3).setPreferredWidth(200);
				
		repaint();
		revalidate();
	}
	
	
	
	public static void showMessage(String message){
		final JDialog dialog = new JDialog();
		dialog.setAlwaysOnTop(true);
		JOptionPane.showMessageDialog(dialog, message);
	}
	
	public void updateScreen(IPInvoice ipiv){
		
		TotalReceivedField.setText(ipiv.getTotalDeposit());
		totalDueField.setText(ipiv.getTotalDue());
		
		invoiceField.setText(ipiv.getInvoiceNo());
		nameField.setText(ipiv.getSurname()+", "+ipiv.getOthernames());
		
		double admDue = Double.parseDouble(ipiv.getAdmissionDues());
		double clinicDue = Double.parseDouble(ipiv.getClinicDues());
		double procedureDue = Double.parseDouble(ipiv.getProcedureDues());
		double LabDue = Double.parseDouble(ipiv.getLaboratoryDues());
		double pharmacyDue = Double.parseDouble(ipiv.getPharmacyDues());
		
		double totalDue = clinicDue + procedureDue + LabDue + pharmacyDue + admDue;
		grandTotalField.setText(totalDue+"");
		
	}
	
	
	
	

}
