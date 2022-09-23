package com.ahms.clinicmanagement;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import com.ahms.api.ClinicManagementInterface;
import com.ahms.clientinterface.InterfaceGenerator;
import com.ahms.clinicmgt.entities.Drug;
import com.ahms.clinicmgt.entities.OPDPrescription;
import com.ahms.clinicmgt.entities.PatientVisit;
import com.ahms.clinicmgt.entities.PrescriptionOrderItem;
import com.ahms.pharmarcymgt.entities.MedicineStockItem;
import com.ahms.reports.PrescriptionShowFrame;

import java.awt.Color;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.rmi.RemoteException;
import javax.swing.border.TitledBorder;

public class PrescriptionOrderEntry extends JFrame {

	private JPanel contentPane;
	private JTextField invoiceNoField;
	private JTextField dosageField;
	private JTextField durationField;
	private JComboBox drugBox;
	
	
	
	ArrayList<OPDPrescription> presList = new ArrayList<OPDPrescription>();
	private JTextField drugFormField;
	private JTextField frequencyField;
	private JTextField dispenseQtyField;
	private JTable table;
	
	ArrayList<MedicineStockItem> stocklist;
	ArrayList<PrescriptionOrderItem> prescriptionlist = new ArrayList<PrescriptionOrderItem>();
	private JTextField availableQtyField;
	private JTextField genderField;
	private JTextField patientName;
	private JTextField hospNoField;

	private PatientVisit pvs;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PrescriptionOrderEntry frame = new PrescriptionOrderEntry("","","");
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
	public PrescriptionOrderEntry(String invoice_no, String emrStatus, String source) throws RemoteException {
		setTitle("Elite HMS - Prescription Order Entry \r\n");
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(320, 100, 731, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		ClinicManagementInterface cm_interface = InterfaceGenerator.getClinicManagementInterface();
		stocklist =  cm_interface.getAvailableDrugs();
		pvs = cm_interface.getPatientVisit(invoice_no);
		
		JLabel lblInvoiceNo = new JLabel("Invoice No");
		lblInvoiceNo.setBounds(462, 88, 76, 20);
		contentPane.add(lblInvoiceNo);
		
		invoiceNoField = new JTextField();
		invoiceNoField.setEditable(false);
		invoiceNoField.setBounds(548, 88, 114, 20);
		invoiceNoField.setColumns(10);
		invoiceNoField.setText(invoice_no);
		contentPane.add(invoiceNoField);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Add Prescription", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(25, 127, 665, 183);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblDispenseQty = new JLabel("Dispense Qty ");
		lblDispenseQty.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblDispenseQty.setBounds(468, 133, 72, 20);
		panel.add(lblDispenseQty);
		
		dispenseQtyField = new JTextField();
		dispenseQtyField.setBounds(550, 133, 72, 20);
		panel.add(dispenseQtyField);
		dispenseQtyField.setColumns(10);
		
		drugFormField = new JTextField();
		drugFormField.setEditable(false);
		drugFormField.setBounds(548, 72, 86, 20);
		panel.add(drugFormField);
		drugFormField.setColumns(10);
		
		JLabel lblAvailableQty = new JLabel("Qty on Hand");
		lblAvailableQty.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblAvailableQty.setBounds(468, 103, 72, 20);
		panel.add(lblAvailableQty);
		
		availableQtyField = new JTextField();
		availableQtyField.setEditable(false);
		availableQtyField.setBounds(548, 103, 60, 20);
		panel.add(availableQtyField);
		availableQtyField.setColumns(10);
		
		JLabel lblDrugName = new JLabel("Drug name");
		lblDrugName.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblDrugName.setBounds(22, 27, 60, 20);
		panel.add(lblDrugName);
		
		drugBox = new JComboBox();
		drugBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				drugFormField.setText(stocklist.get(drugBox.getSelectedIndex()).getMedicineForm());
				availableQtyField.setText(stocklist.get(drugBox.getSelectedIndex()).getAvailableQty()+"");
				if(stocklist.get(drugBox.getSelectedIndex()).getCountState().equalsIgnoreCase("countable")){
					dispenseQtyField.setEditable(false);
				}else{
					dispenseQtyField.setEditable(true);
				}
				
			}
		});
		drugBox.setFont(new Font("Tahoma", Font.BOLD, 11));
		drugBox.setBounds(92, 27, 366, 20);
		for(int i =0; i<stocklist.size(); i++){
			drugBox.addItem(stocklist.get(i).getDrugName());
		}
		AutoCompleteDecorator.decorate(drugBox);
		panel.add(drugBox);
		
		JLabel lblType = new JLabel("Drug Form");
		lblType.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblType.setBounds(468, 72, 60, 20);
		panel.add(lblType);
		
		JLabel lblDosage = new JLabel("Dose");
		lblDosage.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblDosage.setBounds(22, 71, 60, 20);
		panel.add(lblDosage);
		
		JLabel lblRegimen = new JLabel("Frequency");
		lblRegimen.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblRegimen.setBounds(22, 102, 60, 20);
		panel.add(lblRegimen);
		
		JLabel lblDuration = new JLabel("Duration");
		lblDuration.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblDuration.setBounds(22, 133, 60, 20);
		panel.add(lblDuration);
		
		dosageField = new JTextField(); 
		dosageField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				try{
				if(dosageField.getText().length()<1 || durationField.getText().length()<1 || frequencyField.getText().length()<1 
						|| stocklist.get(drugBox.getSelectedIndex()).getCountState().equalsIgnoreCase("non-countable")){
					dispenseQtyField.setText("");
				}else{
					int one = Integer.parseInt(dosageField.getText());
					int two = Integer.parseInt(durationField.getText());
					int three = Integer.parseInt(frequencyField.getText());
					
					dispenseQtyField.setText((one*two*three)+"");
				}
				}catch(Exception ex){ex.printStackTrace();}
			}
		});
		dosageField.setHorizontalAlignment(SwingConstants.RIGHT);
		dosageField.setBounds(88, 71, 54, 20);
		panel.add(dosageField);
		dosageField.setColumns(10);
		
		durationField = new JTextField();
		durationField.setHorizontalAlignment(SwingConstants.RIGHT);
		durationField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				try{
					if(dosageField.getText().length()<1 || durationField.getText().length()<1 || frequencyField.getText().length()<1 
							|| stocklist.get(drugBox.getSelectedIndex()).getCountState().equalsIgnoreCase("non-countable")){
						dispenseQtyField.setText("");
					}else{
						int one = Integer.parseInt(dosageField.getText());
						int two = Integer.parseInt(durationField.getText());
						int three = Integer.parseInt(frequencyField.getText());
						
						dispenseQtyField.setText((one*two*three)+"");
					}
					}catch(Exception ex){ex.printStackTrace();}
			}
		});
		durationField.setBounds(88, 133, 54, 20);
		panel.add(durationField);
		durationField.setColumns(10);
		
		frequencyField = new JTextField(); 
		frequencyField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				try{
					if(dosageField.getText().length()<1 || durationField.getText().length()<1 || frequencyField.getText().length()<1 
							|| stocklist.get(drugBox.getSelectedIndex()).getCountState().equalsIgnoreCase("non-countable")){
						dispenseQtyField.setText("");
					}else{
						int one = Integer.parseInt(dosageField.getText());
						int two = Integer.parseInt(durationField.getText());
						int three = Integer.parseInt(frequencyField.getText());
						
						dispenseQtyField.setText((one*two*three)+"");
					}
					}catch(Exception ex){ex.printStackTrace();}
			}
		});
		frequencyField.setHorizontalAlignment(SwingConstants.RIGHT);
		frequencyField.setBounds(88, 102, 54, 20);
		panel.add(frequencyField);
		frequencyField.setColumns(10);
		
		JLabel lblDays = new JLabel(" (days)");
		lblDays.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblDays.setBounds(142, 136, 60, 14);
		panel.add(lblDays);
		
		
		
		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {	dispose();	}
		});
		btnClose.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnClose.setBounds(577, 527, 113, 23);
		contentPane.add(btnClose);
		
		JButton btnSavePrint = new JButton("Preview");
		btnSavePrint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PrescriptionShowFrame frame = new PrescriptionShowFrame(invoice_no);
				System.out.println("Show");
				frame.setVisible(true);
			}
		});
		btnSavePrint.setBounds(454, 527, 113, 23);
		contentPane.add(btnSavePrint);
		btnSavePrint.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(25, 321, 667, 183);
		contentPane.add(scrollPane_1);
		
		table = new JTable();
		scrollPane_1.setViewportView(table);
		
		genderField = new JTextField();
		genderField.setEditable(false);
		genderField.setText(pvs.getGender());
		genderField.setColumns(10);
		genderField.setBounds(106, 88, 74, 20);
		contentPane.add(genderField);
		
		JLabel label_1 = new JLabel("Gender");
		label_1.setHorizontalAlignment(SwingConstants.LEFT);
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label_1.setBounds(26, 88, 70, 20);
		contentPane.add(label_1);
		
		patientName = new JTextField();
		patientName.setEditable(false);
		patientName.setText(pvs.getSurname() + " " +pvs.getOthernames());
		patientName.setColumns(10);
		patientName.setBounds(106, 57, 247, 20);
		contentPane.add(patientName);
		
		JLabel label_2 = new JLabel("Patient Name");
		label_2.setHorizontalAlignment(SwingConstants.LEFT);
		label_2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label_2.setBounds(26, 56, 70, 22);
		contentPane.add(label_2);
		
		hospNoField = new JTextField();
		hospNoField.setEditable(false);
		hospNoField.setText(pvs.getHospitalNo());
		hospNoField.setColumns(10);
		hospNoField.setBounds(548, 57, 114, 20);
		contentPane.add(hospNoField);
		
		JLabel lblHospitalNo = new JLabel("Hospital No");
		lblHospitalNo.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblHospitalNo.setBounds(462, 57, 70, 20);
		contentPane.add(lblHospitalNo);
		
		JButton btnAdd = new JButton("Save Prescription");
		btnAdd.setBounds(323, 527, 121, 23);
		contentPane.add(btnAdd);
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			if(invoice_no.length()<1){
				showMessage("Invalid Invoice Number ");
			}else if(dosageField.getText().length()<1){
				showMessage("Invalid Dosage ");
			}else if(durationField.getText().length()<1){
				showMessage("Invalid Duration ");
			}else if(frequencyField.getText().length()<1 ){	
				showMessage("Invalid Frequency ");
			}else if(dispenseQtyField.getText().length()<1){	
				showMessage("Enter Dispense Quantity ");
			}else if(Integer.parseInt(dispenseQtyField.getText()) > Integer.parseInt(availableQtyField.getText() )){	
				showMessage("Medicine is out of stock. ");
			}else{
				
				PrescriptionOrderItem order = new PrescriptionOrderItem();
				order.setInvoiceNumber(invoiceNoField.getText());
				order.setMedicineCode(stocklist.get(drugBox.getSelectedIndex()).getMedicineCode());
				order.setMedicineName(drugBox.getSelectedItem().toString());
				order.setDose(Integer.parseInt(dosageField.getText()));
				order.setFrequency(Integer.parseInt(frequencyField.getText()));
				order.setDuration(Integer.parseInt(durationField.getText()));
				order.setDispenseQauntity(Integer.parseInt(dispenseQtyField.getText()));
				order.setOrderState("pending");
				
				try {
					cm_interface.savePrescriptionEntry(order);
					showMessage("Success");
					updateTable(cm_interface.getPrescriptions(invoice_no));
					resetForm();
				} catch (RemoteException e) {					
					e.printStackTrace();
				}
				
								
			}
			
			}
		});
		btnAdd.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		JLabel lblNewLabel = new JLabel("Prescription Form");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel.setBounds(10, 11, 695, 23);
		contentPane.add(lblNewLabel);
		
		if (emrStatus.equalsIgnoreCase("closed")) {
			btnAdd.setEnabled(false);
		}
	
		updateTable(cm_interface.getPrescriptions(invoice_no));
		
		
	}
	
	
	//client method to reset Form
	public void resetForm(){
		drugBox.setSelectedIndex(0);
		dosageField.setText(""); 
		frequencyField.setText("");
		durationField.setText("");
		dispenseQtyField.setText("");
		
	}
	
	public void updateTable(ArrayList<PrescriptionOrderItem> list){
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
		
		Object[] columnNames = { "S/No","Drug name", "Dose", "Frequency", "Duration", "Dispense Qty", "Order state"};
		DefaultTableModel model = new DefaultTableModel(data, columnNames);
		table.setModel(model);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getColumnModel().getColumn(0).setPreferredWidth(50);
		table.getColumnModel().getColumn(1).setPreferredWidth(375);
		table.getColumnModel().getColumn(2).setPreferredWidth(100);
		table.getColumnModel().getColumn(3).setPreferredWidth(100);
		table.getColumnModel().getColumn(4).setPreferredWidth(100);
		table.getColumnModel().getColumn(5).setPreferredWidth(100);
		table.getColumnModel().getColumn(6).setPreferredWidth(120);
		
	}

	public void showMessage(String message){
		JFrame jf = new JFrame();
		jf.setAlwaysOnTop(true);
		
		JOptionPane.showMessageDialog(jf, message);
	}
}
