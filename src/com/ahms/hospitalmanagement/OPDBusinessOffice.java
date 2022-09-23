package com.ahms.hospitalmanagement;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import java.awt.Font;

import com.ahms.api.HospitalManagementInterface;
import com.ahms.clientinterface.InterfaceGenerator;
import com.ahms.hmgt.entities.Deposit;
import com.ahms.hmgt.entities.OPInvoice;
import com.toedter.calendar.JDateChooser;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;

public class OPDBusinessOffice extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table_1;
	private JTextField grandTotalField;
	private JTextField amountField;
	private JTextField totalReceivedField;
	private JTextField totalDueField;
	
	private ButtonGroup btnGroup = new ButtonGroup();
	private ArrayList<OPInvoice> invoice_list;
	private JTextField invoiceNoField;
	private JTextField nameField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OPDBusinessOffice frame = new OPDBusinessOffice(new OPInvoice());
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
	public OPDBusinessOffice(OPInvoice invoice) {
		setTitle("Outpatient Business Office");
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(400, 120, 607, 597);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		final HospitalManagementInterface hm_interface = InterfaceGenerator.getHospitalManagementInterface();
		
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 93, 565, 407);
		contentPane.add(tabbedPane);
		
		JPanel deposit_tab = new JPanel();
		tabbedPane.addTab("Deposit", null, deposit_tab, null);
		deposit_tab.setLayout(null);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 110, 540, 176);
		deposit_tab.add(scrollPane_1);
		
		table_1 = new JTable();
		scrollPane_1.setViewportView(table_1);
		
		JLabel lblPayMode = new JLabel("Mode of Payment"); 
		lblPayMode.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblPayMode.setBounds(10, 11, 87, 22);
		deposit_tab.add(lblPayMode);
		
		JLabel lblBank = new JLabel("Bank");
		lblBank.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblBank.setBounds(10, 44, 77, 22);
		deposit_tab.add(lblBank);
		
				
		JComboBox bankBox = new JComboBox();
		bankBox.setEnabled(false);
		bankBox.setModel(new DefaultComboBoxModel(new String[] {"Select", "Access ", "UBA"}));
		bankBox.setBounds(107, 44, 119, 22);
		deposit_tab.add(bankBox);
		
		JComboBox payModeBox = new JComboBox();
		payModeBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(payModeBox.getSelectedItem().toString().equalsIgnoreCase("transfer") || payModeBox.getSelectedItem().toString().equalsIgnoreCase("pos")){
					bankBox.setEnabled(true);
				}else{
					bankBox.setEnabled(false);
				}
			}
		});
		payModeBox.setModel(new DefaultComboBoxModel(new String[] {"Select", "Cash", "Transfer", "POS"}));
		payModeBox.setBounds(107, 12, 119, 21);
		deposit_tab.add(payModeBox);
		
		JLabel lblAmount = new JLabel("Amount");
		lblAmount.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblAmount.setBounds(10, 77, 64, 22);
		deposit_tab.add(lblAmount);
		
		amountField = new JTextField();
		amountField.setBounds(107, 77, 86, 20);
		deposit_tab.add(amountField);
		amountField.setColumns(10);
		
		JLabel lblTotalReceived = new JLabel("Total Received");
		lblTotalReceived.setBounds(266, 314, 94, 16);
		deposit_tab.add(lblTotalReceived);
		lblTotalReceived.setHorizontalAlignment(SwingConstants.LEFT);
		lblTotalReceived.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		totalReceivedField = new JTextField();
		totalReceivedField.setBounds(358, 312, 87, 20);
		deposit_tab.add(totalReceivedField);
		totalReceivedField.setEditable(false);
		totalReceivedField.setColumns(10);
		
		JLabel lblTotalDue = new JLabel("Balance\r\n");
		lblTotalDue.setBounds(10, 344, 87, 16);
		deposit_tab.add(lblTotalDue);
		lblTotalDue.setForeground(Color.RED);
		lblTotalDue.setHorizontalAlignment(SwingConstants.LEFT);
		lblTotalDue.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		totalDueField = new JTextField();
		totalDueField.setBounds(114, 342, 86, 20);
		deposit_tab.add(totalDueField);
		totalDueField.setEditable(false);
		totalDueField.setColumns(10);
		
		JLabel lblInvoiceNo = new JLabel("Invoice No");
		lblInvoiceNo.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblInvoiceNo.setBounds(412, 50, 63, 23);
		contentPane.add(lblInvoiceNo);
		
		invoiceNoField = new JTextField();
		invoiceNoField.setEditable(false);
		invoiceNoField.setBounds(485, 51, 90, 20);
		contentPane.add(invoiceNoField);
		invoiceNoField.setColumns(10);
		
		JLabel lblPatientName = new JLabel("Patient Name");
		lblPatientName.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblPatientName.setBounds(10, 51, 77, 20);
		contentPane.add(lblPatientName);
		
		nameField = new JTextField();
		nameField.setEditable(false);
		nameField.setBounds(87, 50, 221, 22);
		contentPane.add(nameField);
		nameField.setColumns(10);
		
		JLabel lblGrandTotalDue = new JLabel("Total Due");
		lblGrandTotalDue.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblGrandTotalDue.setBounds(10, 311, 87, 22);
		deposit_tab.add(lblGrandTotalDue);
		lblGrandTotalDue.setForeground(Color.RED);
		
		grandTotalField = new JTextField();
		grandTotalField.setBounds(114, 311, 86, 20);
		deposit_tab.add(grandTotalField);
		grandTotalField.setEditable(false);
		grandTotalField.setColumns(10);
		
		JButton btnPreview = new JButton("Preview Reciept");
		btnPreview.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PaymentReceiptShowFrame frame = new PaymentReceiptShowFrame(invoiceNoField.getText());
				frame.setVisible(true);
			}
		});
		btnPreview.setEnabled(true);
		btnPreview.setBounds(340, 521, 119, 23);
		contentPane.add(btnPreview);
		btnPreview.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		JButton btnClose = new JButton("Close");
		btnClose.setBounds(469, 521, 106, 23);
		contentPane.add(btnClose);
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			dispose();
			}
		});
		btnClose.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		
		JButton btnSavepayment = new JButton("Save Payment");
		btnSavepayment.setBounds(211, 521, 119, 23);
		contentPane.add(btnSavepayment);
		btnSavepayment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(invoiceNoField.getText().length()<1){
					showMessage("Select Invoice Number!");
				}else if(payModeBox.getSelectedItem().toString().equalsIgnoreCase("select")){
					showMessage("Select Mode of Payment!");
				}else if(bankBox.getSelectedItem().toString().equalsIgnoreCase("select") && bankBox.isEnabled()){
					showMessage("Select Bank!");
					} else if(amountField.getText().length()<1){
						showMessage("Enter valid amount!");
				} else{
					String invoice_no = invoiceNoField.getText();
					String paymode = payModeBox.getSelectedItem().toString();
					String bank = bankBox.getSelectedItem().toString();
					double amount = Double.parseDouble(amountField.getText());
					
					try {
						hm_interface.saveDeposit(invoice_no, paymode, bank, amount);
						showMessage("Success!");
						OPInvoice op = hm_interface.getOneOPDinvoice(invoice_no);
						updateScreen(op);
						updateDeposit(hm_interface.getAllDeposits(invoice_no));
						amountField.setText("");
						payModeBox.setSelectedIndex(0);
						bankBox.setSelectedIndex(0);
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
				}
			}
		});
		btnSavepayment.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		JLabel lblNewLabel = new JLabel("Outpatient Hospital Bill Payment");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(10, 11, 637, 28);
		contentPane.add(lblNewLabel);
		
		
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
		table_1.setModel(model);
		table_1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table_1.getColumnModel().getColumn(0).setPreferredWidth(60);
		table_1.getColumnModel().getColumn(1).setPreferredWidth(170);
		table_1.getColumnModel().getColumn(2).setPreferredWidth(170);
		table_1.getColumnModel().getColumn(3).setPreferredWidth(120);
				
		repaint();
		revalidate();
	}
	

	public static void showMessage(String message){
		final JDialog dialog = new JDialog();
		dialog.setAlwaysOnTop(true);
		JOptionPane.showMessageDialog(dialog, message);
	}


	public void updateScreen(OPInvoice opi){
		invoiceNoField.setText(opi.getInvoiceNo());
		nameField.setText(opi.getSurname()+", " + opi.getOthernames());
		double clinicDue = Double.parseDouble(opi.getClinicDue());
		double procedureDue = Double.parseDouble(opi.getProcedureDue());
		double LabDue = Double.parseDouble(opi.getLaboratoryDue());
		double pharmacyDue = Double.parseDouble(opi.getPharmacyDue());
		
		double totalDue = clinicDue + procedureDue + LabDue + pharmacyDue;
		grandTotalField.setText(totalDue+"");					
		totalReceivedField.setText(opi.getTotalDeposit());
		totalDueField.setText(opi.getTotalDue());
	}
}
