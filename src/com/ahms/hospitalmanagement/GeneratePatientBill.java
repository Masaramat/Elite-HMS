 package com.ahms.hospitalmanagement;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.ahms.api.ClinicManagementInterface;
import com.ahms.api.HospitalManagementInterface;
import com.ahms.clientinterface.InterfaceGenerator;
import com.ahms.clinicmgt.entities.ClinicBill;
import com.ahms.clinicmgt.entities.PatientVisit;
import com.ahms.hmgt.entities.HospitalCharge;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GeneratePatientBill extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField invoiceNoField;
	private JTextField amountField;
	private JTable table;
	private JTextField patNameField;
	private static JComboBox comboBox;
	
	private static HospitalManagementInterface hmi =  InterfaceGenerator.getHospitalManagementInterface();	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GeneratePatientBill frame = new GeneratePatientBill("", "", "", "", "");
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
	public GeneratePatientBill(String source, String invoice_no, String patient_name, String emrStatus, String visit_status) throws RemoteException {
		
		setTitle("Elite HMS - Hospital Bill ");
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(450, 150, 533, 526);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		ClinicManagementInterface cm_interface = InterfaceGenerator.getClinicManagementInterface();
		
		
		 
				
		JLabel lblNewLabel = new JLabel("Invoice No");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel.setBounds(328, 52, 69, 21);
		contentPane.add(lblNewLabel);
		
		invoiceNoField = new JTextField();
		invoiceNoField.setBounds(397, 52, 98, 20);
		invoiceNoField.setText(invoice_no);
		invoiceNoField.setEditable(false);
		contentPane.add(invoiceNoField);
		invoiceNoField.setColumns(10);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Add Hospital Bill", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 97, 485, 145);
		contentPane.add(panel);
		panel.setLayout(null);
		
		comboBox = new JComboBox();
		comboBox.setBounds(99, 38, 257, 21);
		panel.add(comboBox);
		
		updateBillCombo();
		
		
		
		JButton btnClose = new JButton("Close");
		btnClose.setBounds(768, 842, 106, 23);
		btnClose.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {			
			dispose();
			 }
		});
		contentPane.add(btnClose);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 253, 485, 175);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JButton btnClose_1 = new JButton("Close");
		btnClose_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(source.equalsIgnoreCase("out")) {
					OPDInvoice.returned_status =  visit_status;
					OPDInvoice.table.requestFocus();
					dispose();
				}else if(source.equalsIgnoreCase("in")) {
					dispose();
				}else {
					dispose();
				}
				
			}
		});
		btnClose_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnClose_1.setBounds(383, 450, 110, 23);
		contentPane.add(btnClose_1);
		
		JLabel label_1 = new JLabel("Patient Name");
		label_1.setHorizontalAlignment(SwingConstants.LEFT);
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label_1.setBounds(10, 53, 69, 19);
		contentPane.add(label_1);
		
		patNameField = new JTextField();
		patNameField.setText(patient_name);
		patNameField.setEditable(false);
		patNameField.setColumns(10);
		patNameField.setBounds(89, 52, 182, 19);
		contentPane.add(patNameField);
		
		
		
		JLabel lblAmount = new JLabel("Amount");
		lblAmount.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblAmount.setBounds(10, 70, 87, 21);
		panel.add(lblAmount);
		
		amountField = new JTextField();
		amountField.setBounds(99, 70, 86, 20);
		panel.add(amountField);
		amountField.setColumns(10);
		
		JLabel lblHospitalService = new JLabel("Hospital Service");
		lblHospitalService.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblHospitalService.setBounds(10, 38, 100, 21);
		panel.add(lblHospitalService);
		
	
		JButton btnNewService = new JButton("Add");
		btnNewService.setBounds(375, 37, 78, 23);
		panel.add(btnNewService);
		btnNewService.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewService.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					NewClinicCharges ncc = new NewClinicCharges("program");
					ncc.setVisible(true);
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
		});
		
		JLabel lblNewLabel_1 = new JLabel("Hospital Bill");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(0, 11, 507, 21);
		contentPane.add(lblNewLabel_1);
		
		JButton btnSave = new JButton("Save Bill");
		btnSave.setBounds(263, 450, 110, 23);
		contentPane.add(btnSave);
		btnSave.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(comboBox.getSelectedItem().toString().equalsIgnoreCase("") ){
					showMessage("Enter Correct Values");
				}else if(invoiceNoField.getText().length()<1){
					showMessage("Invalid Invoice Number");
				}else if(amountField.getText().length()<1){
					showMessage("Invalid amount");
				}else{
					try{
						double amount = Double.parseDouble(amountField.getText());
						cm_interface.saveClinicCharge(invoiceNoField.getText(), comboBox.getSelectedItem().toString(), amount);
						showMessage("Successful");
						updateClinicChargeTable(cm_interface.getClinicBills(invoice_no));
						amountField.setText("");
					
					}catch(Exception ex){ ex.printStackTrace(); }
				}
			
				
			}
		});
		
		if (emrStatus.equalsIgnoreCase("closed")) {
			btnSave.setEnabled(false);
		}
		
		updateClinicChargeTable(cm_interface.getClinicBills(invoice_no));
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				if(source.equalsIgnoreCase("out")) {
					OPDInvoice.returned_status =  visit_status;
					OPDInvoice.table.requestFocus();
					dispose();
				}else if(source.equalsIgnoreCase("in")) {
					dispose();
				}else {
					dispose();
				}
			}
		});
		
	}
	
	@SuppressWarnings("unchecked")
	public static void updateBillCombo() {
		try {
			comboBox.removeAllItems();
			ArrayList<HospitalCharge> charge_list = hmi.getAllCharges();
			for(int i=0; i<charge_list.size(); i++){
				comboBox.addItem(charge_list.get(i).getChargeName());
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	public static void showMessage(String message){
		final JFrame dialog = new JFrame();
		dialog.setAlwaysOnTop(true);
		JOptionPane.showMessageDialog(dialog, message);
	}	
	
	
	//client method to update clinic charge table 
		public void updateClinicChargeTable(ArrayList<ClinicBill> list){
			Object[][] data = new Object[list.size()][3];
			for(int i=0; i<list.size(); i++){ 
				data[i][0] = (i+1);
				data[i][1] = list.get(i).getServiceDescription();
				data[i][2] = list.get(i).getAmount();
			}
			
			Object[] columnNames = { "S/No", "Hospital Service", "Amount"};
			DefaultTableModel model = new DefaultTableModel(data, columnNames);
			table.setModel(model);
			table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			table.getColumnModel().getColumn(0).setPreferredWidth(60);
			table.getColumnModel().getColumn(1).setPreferredWidth(300);
			table.getColumnModel().getColumn(2).setPreferredWidth(120);
		}
}
