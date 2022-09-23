package com.ahms.clinicmanagement;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import com.ahms.api.ClinicManagementInterface;
import com.ahms.api.HospitalManagementInterface;
import com.ahms.clientinterface.InterfaceGenerator;
import com.ahms.clinicmgt.entities.PatientVisit;
import com.ahms.hmgt.entities.Appointment;
import com.ahms.hmgt.entities.Doctor;
import com.ahms.hmgt.entities.PatientBiodata;
import com.ahms.hmgt.entities.UserCard;
import com.ahms.hospitalmanagement.SearchPatientFile;
import com.ahms.laboratorymanagement.TestOrderEntry;
import com.toedter.calendar.JDateChooser;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerDateModel;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import java.awt.Color;
import javax.swing.border.TitledBorder;
import java.awt.Font;
import java.awt.Point;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JRadioButton;
import javax.swing.JCheckBox;
import javax.swing.border.MatteBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class OPDVisit extends JFrame {
	private JDateChooser dateChooser_2;
	
	private JPanel contentPane;
	private Connection conn;
	private JTextField invoiceNoField;
	private static JTextField hospNoField;
	private JTextField statusField;
	private static JTextField patNameField;
	
	private JComboBox doctorBox;
	private JComboBox comboBox_1;
	private JComboBox comboBox_2;
	
	private ButtonGroup bg = new ButtonGroup();;
	
	public ArrayList<Doctor> doctorList ;
	private ArrayList<Appointment> waitingList;
	private ArrayList<String> errorList = new ArrayList<String>(); 
	
	
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OPDVisit frame = new OPDVisit(new UserCard(), new PatientBiodata(), "new");
					frame.setVisible(true);
				} catch (Exception e) {		e.printStackTrace();	}
			}
		});
	}

		
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public OPDVisit(UserCard user, PatientBiodata pb, String source)  {
		setAlwaysOnTop(true);
		
		setTitle("Elite HMS - New Patient Visit ");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(400, 150, 555, 509);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
					

		ClinicManagementInterface cm_interface = InterfaceGenerator.getClinicManagementInterface();
		
		
		JPanel panel_4 = new JPanel();
		panel_4.setBounds(10, 11, 520, 448);
		contentPane.add(panel_4);
		panel_4.setBorder(null);
		panel_4.setLayout(null);
		
		JLabel lblInvoiceNo = new JLabel("Invoice No.");
		lblInvoiceNo.setBounds(38, 54, 76, 19);
		panel_4.add(lblInvoiceNo);
		lblInvoiceNo.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		JLabel lblPatientFileNo = new JLabel("Hospital No.");
		lblPatientFileNo.setBounds(38, 84, 76, 19);
		panel_4.add(lblPatientFileNo);
		lblPatientFileNo.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		JLabel lblPatientName = new JLabel("Patient Name");
		lblPatientName.setBounds(38, 114, 76, 19);
		panel_4.add(lblPatientName);
		lblPatientName.setFont(new Font("Tahoma", Font.PLAIN, 11));		
		
		
		JLabel lblVisitType = new JLabel("Visit Type");
		lblVisitType.setBounds(38, 204, 76, 19);
		panel_4.add(lblVisitType);
		lblVisitType.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		JLabel lblDoctor = new JLabel("Doctor");
		lblDoctor.setBounds(38, 144, 76, 19);
		panel_4.add(lblDoctor);
		lblDoctor.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		JLabel lblDate = new JLabel("Date");
		lblDate.setBounds(38, 234, 76, 19);
		panel_4.add(lblDate);
		lblDate.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		JLabel lblClinic = new JLabel("Clinic");
		lblClinic.setBounds(38, 174, 76, 19);
		panel_4.add(lblClinic);
		lblClinic.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		JLabel lblVisitStatus = new JLabel("Visit Status");
		lblVisitStatus.setBounds(38, 294, 76, 19);
		panel_4.add(lblVisitStatus);
		lblVisitStatus.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		JSpinner spinner = new JSpinner(new SpinnerDateModel());
		spinner.setFont(new Font("Tahoma", Font.PLAIN, 11));
		spinner.setBounds(124, 263, 86, 20);
		panel_4.add(spinner);
		JSpinner.DateEditor de_timeSpinner = new JSpinner.DateEditor(spinner, "hh:mm a");
		spinner.setEditor(de_timeSpinner);
		
		JLabel lblTime = new JLabel("Time");
		lblTime.setBounds(38, 264, 76, 19);
		panel_4.add(lblTime);
		lblTime.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		dateChooser_2 = new JDateChooser();
		dateChooser_2.setBounds(124, 234, 106, 19);
		dateChooser_2.setPreferredSize(new Dimension(40, 20));
		dateChooser_2.setDateFormatString("yyyy-MM-dd");
		dateChooser_2.setDate(new Date());
		panel_4.add(dateChooser_2);
		
		
		invoiceNoField = new JTextField();
		invoiceNoField.setBounds(124, 53, 126, 19);
		panel_4.add(invoiceNoField);
		invoiceNoField.setEditable(false);
		invoiceNoField.setColumns(10);
		
		hospNoField = new JTextField();
		hospNoField.setEditable(false);
		hospNoField.setBounds(124, 83, 126, 20);
		panel_4.add(hospNoField);
		hospNoField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				
				
			}
		});
		hospNoField.setColumns(10);
		
		patNameField = new JTextField();
		patNameField.setBounds(124, 113, 202, 20);
		panel_4.add(patNameField);
		patNameField.setEditable(false);
		patNameField.setColumns(10);
		
		comboBox_1 = new JComboBox();
		comboBox_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		comboBox_1.setBounds(124, 203, 86, 20);
		panel_4.add(comboBox_1);
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"--Select", "New", "Old"}));
		
		
		doctorBox = new JComboBox();
		doctorBox.setFont(new Font("Tahoma", Font.PLAIN, 11));
		doctorBox.setBounds(124, 144, 171, 19);
		panel_4.add(doctorBox);
		
		try {			
			for(int i=0; i< cm_interface.getDoctorList().size(); i++){
				doctorBox.addItem("Dr. "+cm_interface.getDoctorList().get(i).getDoctorName());
			}			
		} catch (RemoteException e1) {	e1.printStackTrace();	}
		
		comboBox_2 = new JComboBox();
		comboBox_2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		comboBox_2.setBounds(124, 174, 86, 19);
		panel_4.add(comboBox_2);
		comboBox_2.setModel(new DefaultComboBoxModel(new String[] {"OPD"}));
		
		statusField = new JTextField();
		statusField.setBounds(124, 293, 59, 20);
		panel_4.add(statusField);
		statusField.setForeground(new Color(0, 100, 0));
		statusField.setText("active");
		statusField.setEditable(false);
		statusField.setColumns(10);
		
		JButton btnClose = new JButton("Close");
		btnClose.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnClose.setBounds(421, 414, 89, 23);
		panel_4.add(btnClose);
		
		JButton btnNew = new JButton("Clear");
		btnNew.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnNew.setBounds(322, 414, 89, 23);
		panel_4.add(btnNew);
		
		JButton btnGenerateInvoice = new JButton("Generate Invoice");
		btnGenerateInvoice.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnGenerateInvoice.setBounds(186, 414, 126, 23);
		panel_4.add(btnGenerateInvoice);
		
		JLabel lblNewLabel = new JLabel("Patient Visit Form");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel.setBounds(10, 0, 500, 23);
		panel_4.add(lblNewLabel);
		
		JButton searchButton = new JButton(">>>");
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SearchPatientFile frameFile = new SearchPatientFile(user, "visit");
				frameFile.setVisible(true);
			}
		});
		searchButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		searchButton.setBounds(260, 82, 59, 23);
		panel_4.add(searchButton);
		btnGenerateInvoice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(hospNoField.getText().length() <= 0 || patNameField.getText().length() <= 0){
					errorList.add("No patient selected.");
				}else if(comboBox_1.getSelectedItem().toString().equalsIgnoreCase("--Select")){
					errorList.add("Invalid Visit Type.");
				}				
				try{
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						Date date  = sdf.parse(((JTextField)dateChooser_2.getDateEditor().getUiComponent()).getText());
					}catch(Exception ex){
						errorList.add("Invalid Date Entry.");						
					}
				
				if(errorList.size()>0){
				showMessage(errorList.get(0));
				errorList.clear();
				}else{
					
					try {
						int xy = cm_interface.searchActivePatient(hospNoField.getText());
						if(xy>0){
							showMessage("Patient has an Active Invoice!");
							//hospNoField.setText("");
						}else{
							String hospitalNo, clinic, doctor, date, time, type, status;
							
							hospitalNo = hospNoField.getText();
							doctor = doctorBox.getSelectedItem().toString();
							type = comboBox_1.getSelectedItem().toString();
							clinic = comboBox_2.getSelectedItem().toString();
							date = ((JTextField)dateChooser_2.getDateEditor().getUiComponent()).getText();
							
							Date text = (Date) spinner.getValue();
							SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
							time = sdf.format(text);
							status = statusField.getText();
							
							PatientVisit pv = new PatientVisit();
							pv.setHospitalNo(hospitalNo);
							pv.setDoctor(doctor);
							pv.setType(type);
							pv.setClinic(clinic);
							pv.setDate(date);
							pv.setTime(time);
							pv.setStatus(status);
							
							ArrayList<String> list;
							list = cm_interface.generateInvoice(pv);
							invoiceNoField.setText(list.get(0)); 
							showMessage("Success!");
							btnGenerateInvoice.setEnabled(false);							
						}
											
					} catch (RemoteException e) {	e.printStackTrace();	}
										
				}
 								
			}
		});
		btnNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				hospNoField.setText("");
				invoiceNoField.setText("");
				patNameField.setText("");
				btnGenerateInvoice.setEnabled(true);
				comboBox_1.setSelectedIndex(0);
			}
		});
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		
		
		if (source.equalsIgnoreCase("search")) {
			updatePatientInfo(pb);
		}
	
	}
	
	
					
	public static void updatePatientInfo(PatientBiodata pBiodata) {
		hospNoField.setText(pBiodata.getHospital_no());
		patNameField.setText(pBiodata.getSurname() +", "+pBiodata.getFirstname());
	}
	
	//a standard client method for displaying popup messages on frame that is setAlwaysOnTop(true)
	public static void showMessage(String message){
		final JDialog dialog = new JDialog();
		dialog.setAlwaysOnTop(true);
		JOptionPane.showMessageDialog(dialog, message);
	}
}
