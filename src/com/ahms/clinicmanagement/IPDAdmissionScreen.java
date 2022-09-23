package com.ahms.clinicmanagement;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.FlowLayout;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Point;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import com.ahms.api.ClinicManagementInterface;
import com.ahms.clientinterface.InterfaceGenerator;
import com.ahms.clinicmgt.entities.InpatientAdmission;
import com.ahms.clinicmgt.entities.PatientVisit;
import com.ahms.hmgt.entities.Bed;
import com.ahms.hmgt.entities.Doctor;
import com.ahms.hmgt.entities.Room;
import com.toedter.calendar.JDateChooser;
import java.awt.Dimension;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;
import javax.swing.DefaultComboBoxModel;
import javax.swing.border.MatteBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.swing.JTextArea;
import javax.swing.UIManager;

public class IPDAdmissionScreen extends JFrame {
	
	private JTextField invoiceField;
	private JTextField hospNoField;
	private JTextField patientNameField;
	private JTextField genderField;
	private JTextField contactRelField;
	private JTextField phoneField;
	private JTable table;
	private JDateChooser adm_dateChooser;
	private JComboBox wardBox;
	private ButtonGroup btgrp;
	private JButton btnSavePrint;  
	
	private JComboBox departmentBox;
	private JComboBox doctorBox;
	
	
	
	private ClinicManagementInterface cm_interface;
	private Connection conn;
	private ArrayList<Room> wardlist;
	private ArrayList<Integer> bedCount;
	ArrayList<String> errorList = new ArrayList<>();
	ArrayList<Bed> bedlist;
	PatientVisit pvisit;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					IPDAdmissionScreen frame = new IPDAdmissionScreen("");
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
	public IPDAdmissionScreen(String invoice_no) throws RemoteException {
		setTitle("Elite HMS - Inpatient Admission Screen");
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(400, 120, 615, 572);
		
		cm_interface = InterfaceGenerator.getClinicManagementInterface();
		
		
		btgrp = new ButtonGroup();
		
		wardlist = cm_interface.getAllWards();
		bedCount = cm_interface.getBedCount();
		getContentPane().setLayout(null);
		Date date = new Date();
		
		ArrayList<Doctor> doclist = cm_interface.getDoctorList();		
		
		
		JLabel lblPatientName = new JLabel("Patient Name");
		lblPatientName.setBounds(20, 55, 66, 20);
		getContentPane().add(lblPatientName);
		lblPatientName.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPatientName.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		JLabel lblGender_1 = new JLabel("Gender");
		lblGender_1.setBounds(20, 86, 63, 20);
		getContentPane().add(lblGender_1);
		lblGender_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		patientNameField = new JTextField();
		patientNameField.setBounds(92, 55, 179, 20);
		getContentPane().add(patientNameField);
		patientNameField.setEditable(false);
		patientNameField.setColumns(10);
		
		genderField = new JTextField();
		genderField.setBounds(92, 86, 59, 20);
		getContentPane().add(genderField);
		genderField.setEditable(false);
		genderField.setColumns(10);
		
		JLabel lblHospitalNo = new JLabel("Hospital No.");
		lblHospitalNo.setBounds(355, 55, 73, 20);
		getContentPane().add(lblHospitalNo);
		lblHospitalNo.setHorizontalAlignment(SwingConstants.RIGHT);
		lblHospitalNo.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		hospNoField = new JTextField();
		hospNoField.setBounds(440, 55, 99, 20);
		getContentPane().add(hospNoField);
		hospNoField.setEditable(false);
		hospNoField.setColumns(10);
		
		JLabel lblInvoiceNo = new JLabel("Invoice No.");
		lblInvoiceNo.setBounds(355, 83, 73, 20);
		getContentPane().add(lblInvoiceNo);
		lblInvoiceNo.setHorizontalAlignment(SwingConstants.RIGHT);
		lblInvoiceNo.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		
		invoiceField = new JTextField(); 
		invoiceField.setEditable(false);
		invoiceField.setBounds(438, 83, 99, 20);
		getContentPane().add(invoiceField);
		invoiceField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(invoiceField.getText().length()<=0){
					//JOptionPane.showMessageDialog(null, "Invoice number field is empty.");
					System.out.println("Invoice number field is empty.");
				}else{
					try{
						pvisit = cm_interface.getPatientVisit(invoiceField.getText());
						if(pvisit.getHospitalNo()==null){
							showMessage("No Record Found");
						}else if(pvisit.getClinic().equalsIgnoreCase("ipd")){
							showMessage("Patient has been admitted");							
						}else{
							updateScreen(invoiceField.getText());																		
						}
					}catch(Exception ex){}
					
				}
			}
		});
		invoiceField.setColumns(10);
		
		JLabel lblContactRelation = new JLabel("Patient Relative");
		lblContactRelation.setBounds(20, 139, 103, 20);
		getContentPane().add(lblContactRelation);
		lblContactRelation.setHorizontalAlignment(SwingConstants.LEFT);
		lblContactRelation.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		contactRelField = new JTextField();
		contactRelField.setBounds(133, 139, 194, 20);
		getContentPane().add(contactRelField);
		contactRelField.setColumns(10);
		
		JLabel lblContactPhone = new JLabel("Contact Phone");
		lblContactPhone.setBounds(20, 170, 83, 20);
		getContentPane().add(lblContactPhone);
		lblContactPhone.setHorizontalAlignment(SwingConstants.LEFT);
		lblContactPhone.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		phoneField = new JTextField();
		phoneField.setBounds(133, 170, 111, 20);
		getContentPane().add(phoneField);
		phoneField.setColumns(10);
		
		adm_dateChooser = new JDateChooser();
		adm_dateChooser.setBounds(133, 233, 103, 21);
		getContentPane().add(adm_dateChooser);
		adm_dateChooser.setPreferredSize(new Dimension(40, 20));
		adm_dateChooser.setDateFormatString("yyyy-MM-dd");
		adm_dateChooser.setDate(date);
		
		JLabel lblAdmittingDoctor = new JLabel("Admitting Doctor");
		lblAdmittingDoctor.setBounds(20, 201, 87, 20);
		getContentPane().add(lblAdmittingDoctor);
		lblAdmittingDoctor.setHorizontalAlignment(SwingConstants.LEFT);
		lblAdmittingDoctor.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		
		doctorBox = new JComboBox();
		doctorBox.setBounds(133, 201, 236, 21);
		getContentPane().add(doctorBox);
		
		JLabel lblAdmDate = new JLabel("Admission Date");
		lblAdmDate.setBounds(20, 233, 90, 20);
		getContentPane().add(lblAdmDate);
		lblAdmDate.setHorizontalAlignment(SwingConstants.LEFT);
		lblAdmDate.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		
		JLabel lblDepartment = new JLabel("Clinic");
		lblDepartment.setBounds(20, 264, 100, 20);
		getContentPane().add(lblDepartment);
		lblDepartment.setHorizontalAlignment(SwingConstants.LEFT);
		lblDepartment.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		departmentBox = new JComboBox();
		departmentBox.setBounds(133, 264, 179, 21);
		getContentPane().add(departmentBox);
		departmentBox.setModel(new DefaultComboBoxModel(new String[] {"--Select", "General Surgery", "General Consultation", "General Medicine", "Antenatal & Gynae", "ENT", "Laparocopic Surgery", "Orthopedic Services", "Dental Surgery", "Plastic Surgery", "Peadratrics", "Ultrasound Scan/X-ray", "Laboratory Services", "NHIS"}));
		
		JLabel lblWard = new JLabel("Room");
		lblWard.setBounds(20, 294, 68, 20);
		getContentPane().add(lblWard);
		lblWard.setHorizontalAlignment(SwingConstants.LEFT);
		lblWard.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
			
			wardBox = new JComboBox();
			wardBox.setBounds(133, 294, 235, 20);
			getContentPane().add(wardBox);
			
			JLabel lblSelectedBed = new JLabel("Bed");
			lblSelectedBed.setBounds(20, 325, 46, 20);
			getContentPane().add(lblSelectedBed);
			lblSelectedBed.setHorizontalAlignment(SwingConstants.LEFT);
			lblSelectedBed.setFont(new Font("Tahoma", Font.PLAIN, 11));
			
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setBounds(133, 327, 334, 119);
			getContentPane().add(scrollPane);
			
			table = new JTable();
			table.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					
					int row = table.getSelectedRow();
					String value = table.getModel().getValueAt(row, 1).toString();
					String value2 = table.getModel().getValueAt(row, 2).toString();
				
				}
			});
			scrollPane.setViewportView(table);
			
				
			btnSavePrint = new JButton("Admit Patient");
			btnSavePrint.setFont(new Font("Tahoma", Font.PLAIN, 11));
			btnSavePrint.setBounds(341, 494, 133, 23);
			getContentPane().add(btnSavePrint);
			btnSavePrint.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					errorList.clear();
					validateForm();
					if(errorList.size() >0){
						System.out.println(errorList.get(0));
					}else{	
						try{
							//this is where we save the data
							InpatientAdmission admmm = bundleAdmissionData();
							InpatientAdmission adms = cm_interface.saveAdmission(admmm);
							showMessage("Successful!");
							OutpatientVisitScreen.updateOPDList();
							dispose();
						
							btnSavePrint.setEnabled(false);
						}catch(Exception ex){ex.printStackTrace();}
						
					}
				}
			});
			btnSavePrint.setEnabled(false);
			
			
			
			JButton btnClose = new JButton("Close");
			btnClose.setFont(new Font("Tahoma", Font.PLAIN, 11));
			btnClose.setBounds(484, 494, 89, 23);
			getContentPane().add(btnClose);
			
			JLabel lblNewLabel = new JLabel("Patient Admission Form");
			lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
			lblNewLabel.setBounds(10, 11, 579, 23);
			getContentPane().add(lblNewLabel);
			
			btnClose.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
				dispose();
				}
			});
		wardBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int xx = wardBox.getSelectedIndex();
				if(xx >= 0){
					try{
						//categoryField.setText(wardlist.get(xx).getWardName());
						bedlist = cm_interface.getFreeBeds(wardlist.get(xx).getRoomName());
						updateBedsTable(bedlist);	
					}catch(Exception ex){ ex.printStackTrace();}
				}else{
					//do nothing
				}
			}
		});
		
		for(int i=0; i<doclist.size(); i++){
			doctorBox.addItem(doclist.get(i).getDoctorName());
		}
		for(int i=0; i<wardlist.size(); i++){
			wardBox.addItem(wardlist.get(i).getRoomName());
		}
		
		
		if(invoice_no.length()<1){
			
		}else{
			
			updateScreen(invoice_no);
		}
		
				
	}
	
	
	
	
	//client method to update the tables
	public void updateBedsTable(ArrayList<Bed> list){
		Object[][] data = new Object[list.size()][3];
		for(int i=0; i<list.size(); i++){ 
			data[i][0] = list.get(i).getBedNo();
			data[i][1] = list.get(i).getBedDetails();
			data[i][2] = list.get(i).getBedCharge();
		}
		
		Object[] columnNames = { "Bed Code", "Bed Description", "Bed Charge"};
		DefaultTableModel model = new DefaultTableModel(data, columnNames);
		table.setModel(model);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getColumnModel().getColumn(0).setPreferredWidth(100);
		table.getColumnModel().getColumn(1).setPreferredWidth(248);
		table.getColumnModel().getColumn(2).setPreferredWidth(100);
	}
	
	//client method to validate Form
	public void validateForm(){
		try{
		
		if(invoiceField.getText().length()<1){
			errorList.add("Invalid invoice number.");
		}else if(hospNoField.getText().length()<1){
			errorList.add("Invalid hopsital number.");		
		}else if(departmentBox.getSelectedItem().toString().equalsIgnoreCase("--Select")){
			errorList.add("Invalid department selection.");
		}
		}catch(Exception ex){ex.printStackTrace();}
		
	}
	
	//client method to bundle admission data
	public InpatientAdmission bundleAdmissionData(){
		InpatientAdmission adm = new InpatientAdmission();
		int row = table.getSelectedRow();
		String bed_code = table.getModel().getValueAt(row, 0).toString();
		double charge = Double.parseDouble(table.getModel().getValueAt(row, 2).toString());
		
		adm.setInvoiceNo(invoiceField.getText());		
		adm.setAdmissionDate(((JTextField)adm_dateChooser.getDateEditor().getUiComponent()).getText());
		adm.setContactRelation(contactRelField.getText());
		adm.setContactPhone(phoneField.getText());
		adm.setDepartment(departmentBox.getSelectedItem().toString());
		adm.setAdmittingDoctor(doctorBox.getSelectedItem().toString());
		adm.setBedCharge(charge);
			
		adm.setBedCode(bed_code);
				
		return adm;
	}
	
	public void updateScreen(String invoice){
		try {
			pvisit = cm_interface.getPatientVisit(invoice);
			
			invoiceField.setEditable(false);	
			invoiceField.setText(invoice);
			hospNoField.setText(pvisit.getHospitalNo());
			patientNameField.setText(pvisit.getSurname()+" " + pvisit.getOthernames());						
			genderField.setText(pvisit.getGender());							
			btnSavePrint.setEnabled(true); 
			//btnSavePreview.setEnabled(true);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public static void showMessage(String message){
		final JDialog dialog = new JDialog();
		dialog.setAlwaysOnTop(true);
		JOptionPane.showMessageDialog(dialog, message);
	}
	
	
	
}
