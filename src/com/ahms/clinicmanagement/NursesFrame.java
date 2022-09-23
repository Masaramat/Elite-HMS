package com.ahms.clinicmanagement;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.ahms.api.ClinicManagementInterface;
import com.ahms.api.HospitalManagementInterface;
import com.ahms.clientinterface.InterfaceGenerator;
import com.ahms.clinicmgt.entities.InpatientObservation;
import com.ahms.clinicmgt.entities.NursingNote;
import com.ahms.clinicmgt.entities.PatientVisit;
import com.ahms.clinicmgt.entities.VitalSignEntry;
import com.ahms.hmgt.entities.UserCard;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;

public class NursesFrame extends JFrame {

	private JPanel contentPane;
	private JTextField ageField;
	private JTextField genderField;
	private JTextField nameField;
	private JTextField dateField;
	private JTextField invoiceNoField;
	private JTextField hospitalNoField;
	private JTextField heightField;
	private JTextField weightField;
	private JTextField tempField;
	private JTextField bpSysField;
	private JTextField bpDiasField;
	private JTextField pulseField;
	private JTextField respField;
	private JTable vsTable;
	private JButton btnSave;
	private JTextArea noteArea;
	private JButton btnEditNote;
	private JButton btnAddNotes;
	private JButton btnUpdate;
	private JButton btnViewNote;
	private JButton btnSave_1;
	
	
	
	
	public ArrayList<Date> admDates;
	ArrayList<VitalSignEntry> vs_list = new ArrayList<>();
	private ArrayList<NursingNote> nn_list = new ArrayList<>();
	
	private ClinicManagementInterface cm_interface;
	private HospitalManagementInterface hm_interface;
	private JTable nursing_note_table;
	private JTextField userField;
	private JTextField obsTempField;
	private JTextField obsBPSysField;
	private JTextField obsBPDysField;
	private JTextField obsPulseField;
	private JTextField obsRespField;
	private JTable adm_date_table;
	private JTable obs_chart_table;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NursesFrame frame = new NursesFrame(new UserCard(), new PatientVisit());
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
	public NursesFrame(UserCard user, PatientVisit pVisit) {
		setAlwaysOnTop(true);
		setTitle("Elite HMS - Nursing Station ");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(300, 130, 811, 560);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		cm_interface = InterfaceGenerator.getClinicManagementInterface();
		
			
		JLabel lblNewLabel_3 = new JLabel("Nursing Station");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_3.setBounds(10, 11, 775, 36);
		contentPane.add(lblNewLabel_3);
		
		JLabel lblNewLabel_1 = new JLabel("Patient Name");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_1.setBounds(37, 61, 75, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Gender");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_2.setBounds(37, 92, 59, 14);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_2_1 = new JLabel("Age");
		lblNewLabel_2_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_2_1.setBounds(37, 120, 59, 14);
		contentPane.add(lblNewLabel_2_1);
		
		ageField = new JTextField();
		ageField.setEditable(false);
		ageField.setColumns(10);		
		
		try {
			Date dob = new SimpleDateFormat("yyyy-MM-dd").parse(pVisit.getDob());
			ageField.setText(calculateAge(dob));
		} catch (Exception e) {
			e.printStackTrace();
		}
		ageField.setBounds(122, 117, 85, 20);
		contentPane.add(ageField);
		
		
		
		genderField = new JTextField();
		genderField.setText(pVisit.getGender());
		genderField.setEditable(false);
		genderField.setColumns(10);
		genderField.setBounds(122, 89, 86, 20);
		contentPane.add(genderField);
		
		nameField = new JTextField();
		nameField.setText(pVisit.getSurname() + " " + pVisit.getOthernames());
		nameField.setEditable(false);
		nameField.setColumns(10);
		nameField.setBounds(122, 58, 157, 20);
		contentPane.add(nameField);
		
		JLabel lblNewLabel = new JLabel("Hospital No.");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel.setBounds(419, 61, 75, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblInvoiceNo = new JLabel("Invoice No.");
		lblInvoiceNo.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblInvoiceNo.setBounds(419, 89, 75, 14);
		contentPane.add(lblInvoiceNo);
		
		JLabel lblDate = new JLabel("Invoice Date");
		lblDate.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblDate.setBounds(419, 120, 75, 14);
		contentPane.add(lblDate);
		
		dateField = new JTextField();
		dateField.setText(pVisit.getDate());
		dateField.setEditable(false);
		dateField.setColumns(10);
		dateField.setBounds(504, 117, 96, 20);
		contentPane.add(dateField);
		
		invoiceNoField = new JTextField();
		invoiceNoField.setText(pVisit.getInvoiceNo());
		invoiceNoField.setEditable(false);
		invoiceNoField.setColumns(10);
		invoiceNoField.setBounds(504, 89, 96, 20);
		contentPane.add(invoiceNoField);
		
		hospitalNoField = new JTextField();
		hospitalNoField.setText(pVisit.getHospitalNo());
		hospitalNoField.setEditable(false);
		hospitalNoField.setColumns(10);
		hospitalNoField.setBounds(504, 58, 134, 20);
		contentPane.add(hospitalNoField);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 161, 775, 349);
		contentPane.add(tabbedPane);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("Vital Signs", null, panel, null);
		panel.setLayout(null);
		
		JLabel lblHeight = new JLabel("Height");
		lblHeight.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblHeight.setBounds(10, 26, 49, 19);
		panel.add(lblHeight);
		
		heightField = new JTextField();
		heightField.setColumns(10);
		heightField.setBounds(69, 25, 63, 20);
		panel.add(heightField);
		
		JLabel lblWeight = new JLabel("Weight");
		lblWeight.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblWeight.setBounds(205, 25, 57, 19);
		panel.add(lblWeight);
		
		weightField = new JTextField();
		weightField.setColumns(10);
		weightField.setBounds(272, 24, 63, 20);
		panel.add(weightField);
		
		JLabel lblTemperature = new JLabel("Temp.");
		lblTemperature.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblTemperature.setBounds(405, 25, 63, 19);
		panel.add(lblTemperature);
		
		tempField = new JTextField();
		tempField.setColumns(10);
		tempField.setBounds(482, 24, 63, 20);
		panel.add(tempField);
		
		JLabel lblBloodPressure = new JLabel("Blood Pres.");
		lblBloodPressure.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblBloodPressure.setBounds(10, 56, 63, 19);
		panel.add(lblBloodPressure);
		
		bpSysField = new JTextField();
		bpSysField.setColumns(10);
		bpSysField.setBounds(71, 56, 37, 20);
		panel.add(bpSysField);
		
		bpDiasField = new JTextField();
		bpDiasField.setColumns(10);
		bpDiasField.setBounds(117, 56, 37, 20);
		panel.add(bpDiasField);
		
		JLabel lblPulseRate = new JLabel("Pulse ");
		lblPulseRate.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblPulseRate.setBounds(205, 56, 57, 19);
		panel.add(lblPulseRate);
		
		pulseField = new JTextField();
		pulseField.setColumns(10);
		pulseField.setBounds(272, 55, 63, 20);
		panel.add(pulseField);
		
		JLabel lblRespirationRate = new JLabel("Resp. Rate");
		lblRespirationRate.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblRespirationRate.setBounds(405, 56, 63, 19);
		panel.add(lblRespirationRate);
		
		respField = new JTextField();
		respField.setColumns(10);
		respField.setBounds(482, 55, 63, 20);
		panel.add(respField);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 98, 588, 202);
		panel.add(scrollPane);
		
		vsTable = new JTable();
		vsTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int xy = vsTable.getSelectedRow();
				if(xy<0) {
					
				}else {
					updateForm(vs_list.get(xy));
				}
			}
		});
		vsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(vsTable);
		
		btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(invoiceNoField.getText().length()<1){
						showMessage("Invalid Invoice Number!");
					}else{
						VitalSignEntry vs = submitForm();
						
						cm_interface.saveVSEntry(vs);
						
						showMessage("success!");
						vs_list = cm_interface.getVitalSignEntries(invoiceNoField.getText());
						updateVSETable(vs_list);
						vsTable.requestFocus();
						clearForm();
						
						
					}
					
				} catch (RemoteException ae) {	ae.printStackTrace();		}
			}
		});
		btnSave.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnSave.setBounds(654, 175, 106, 23);
		panel.add(btnSave);
		
		btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(invoiceNoField.getText().length()<1){
						showMessage("Invalid Invoice Number!");
					}else{
						VitalSignEntry vs =submitForm();
						vs.setSerialNo(vs_list.get(vsTable.getSelectedRow()).getSerialNo());
						System.out.println(vs.getSerialNo() +", "+vs.getInvoiceNumber()+", "+vs.getHeight());
						cm_interface.updateVSEntry(vs);
						showMessage("Updated successfully!");
						vs_list = cm_interface.getVitalSignEntries(invoiceNoField.getText());
						updateVSETable(vs_list);
						vsTable.requestFocus();
						clearForm();
						
					}
					
				} catch (RemoteException e1) {	e1.printStackTrace();		}
			}
		});
		btnUpdate.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnUpdate.setBounds(654, 209, 106, 23);
		panel.add(btnUpdate);
		
		JButton btnClear = new JButton("Clear");
		btnClear.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnClear.setBounds(654, 243, 106, 23);
		panel.add(btnClear);
		
		JButton btnClose = new JButton("Close");
		btnClose.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnClose.setBounds(654, 277, 106, 23);
		panel.add(btnClose);
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Nursing Notes", null, panel_1, null);
		panel_1.setLayout(null);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 11, 615, 132);
		panel_1.add(scrollPane_1);
		
		nursing_note_table = new JTable();
		nursing_note_table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnAddNotes.setEnabled(false);
				btnEditNote.setEnabled(true);
				if (nn_list.get(nursing_note_table.getSelectedRow()).getStatus().equalsIgnoreCase("locked") || pVisit.getEmrSratus().equalsIgnoreCase("closed")) {
					btnEditNote.setEnabled(false);
					
					noteArea.setText(nn_list.get(nursing_note_table.getSelectedRow()).getNote());					
				}else {
					
					noteArea.setText(nn_list.get(nursing_note_table.getSelectedRow()).getNote());	
				}
			}
		});
		scrollPane_1.setViewportView(nursing_note_table);
		
		btnAddNotes = new JButton("Save");
		btnAddNotes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(invoiceNoField.getText().length()<1){
					 showMessage("Invalid Invoice Number.");
				}else if(noteArea.getText().length()<1){
					 showMessage("Enter Valid Text in Nursing Notes");
				}else{
					try {
						NursingNote note = new NursingNote();						
						note.setNote(noteArea.getText());
						note.setNurse(userField.getText());					
						
						cm_interface.saveNursingNote(pVisit.getInvoiceNo(), note.getNote(), note.getNurse());
						showMessage("Success!");
						nn_list = cm_interface.getNursingNotes(invoiceNoField.getText());			
						updateNursingNoteTable(nn_list);
						noteArea.setText("");
						btnSave.setEnabled(false);
						
											
					} catch (RemoteException e2) {		e2.printStackTrace();	}		
				}
			}
		});
		btnAddNotes.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnAddNotes.setBounds(670, 154, 90, 23);
		panel_1.add(btnAddNotes);
		
		btnEditNote = new JButton("Update");
		btnEditNote.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (nursing_note_table.getSelectedRow() <0) {
					showMessage("No item selected.");
				}else {
					try {
						NursingNote note = nn_list.get(nursing_note_table.getSelectedRow());					
						note.setNote(noteArea.getText());					
						cm_interface.updateNursingNote(note);
						showMessage("Update Successful!");
						nn_list = cm_interface.getNursingNotes(invoiceNoField.getText());			
						updateNursingNoteTable(nn_list);
						noteArea.setText("");
						nursing_note_table.clearSelection();
						btnAddNotes.setEnabled(true);
						btnEditNote.setEnabled(false);
						
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
				
			}
		});
		btnEditNote.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnEditNote.setEnabled(false);
		btnEditNote.setBounds(670, 188, 90, 23);
		panel_1.add(btnEditNote);
		
		btnViewNote = new JButton("Lock");
		btnViewNote.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame jFrame = new JFrame();
				jFrame.setAlwaysOnTop(true);
				
				if (nursing_note_table.getSelectedRow()<0) {
					showMessage("No item selected.");
				}else {
					NursingNote note = nn_list.get(nursing_note_table.getSelectedRow());
					
					if (note.getStatus().equalsIgnoreCase("locked")) {
						showMessage("This note is locked.");
					}else {
						int confirmed = JOptionPane.showConfirmDialog(jFrame,  "Do you want to lock this nursing note?",
								"Confirm Note Locking.",   JOptionPane.YES_NO_OPTION);

					    if (confirmed == JOptionPane.YES_OPTION) {
					    	try {
								cm_interface.updateNursingNoteStatus(note);
								nn_list = cm_interface.getNursingNotes(invoiceNoField.getText());
								updateNursingNoteTable(nn_list);
								showMessage("Successful!");
							} catch (Exception e2) {
								e2.printStackTrace();
							}
					    }
					}				
				}
			}
		});
		btnViewNote.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnViewNote.setBounds(670, 222, 89, 23);
		panel_1.add(btnViewNote);
		
		JButton btnCloseNote = new JButton("Close");
		btnCloseNote.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				System.gc();
			}
		});
		btnCloseNote.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnCloseNote.setBounds(670, 290, 90, 23);
		panel_1.add(btnCloseNote);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(10, 154, 615, 132);
		panel_1.add(scrollPane_2);
		
		noteArea = new JTextArea();
		scrollPane_2.setViewportView(noteArea);
		noteArea.setWrapStyleWord(true);
		noteArea.setLineWrap(true);
		
		userField = new JTextField();
		userField.setText(user.getFullNames());
		userField.setEditable(false);
		userField.setColumns(10);
		userField.setBounds(66, 297, 205, 20);
		panel_1.add(userField);
		
		JLabel lblNurse = new JLabel("User");
		lblNurse.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNurse.setBounds(10, 297, 70, 20);
		panel_1.add(lblNurse);
		
		JButton btnClear_1 = new JButton("Clear");
		btnClear_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				noteArea.setText("");
				nursing_note_table.clearSelection();
				btnAddNotes.setEnabled(true);
				btnEditNote.setEnabled(false);
			}
		});
		btnClear_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnClear_1.setBounds(670, 256, 90, 23);
		panel_1.add(btnClear_1);
		
		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("Observational Chart", null, panel_2, null);
		panel_2.setLayout(null);
		
		obsTempField = new JTextField();
		obsTempField.setColumns(10);
		obsTempField.setBounds(56, 21, 86, 22);
		panel_2.add(obsTempField); 
		
		JLabel lblTemperature_1 = new JLabel("Temp.");
		lblTemperature_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblTemperature_1.setBounds(10, 22, 47, 21);
		panel_2.add(lblTemperature_1);
		
		JLabel lblBloosPressure = new JLabel("B P");
		lblBloosPressure.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblBloosPressure.setBounds(191, 21, 47, 21);
		panel_2.add(lblBloosPressure);
		
		obsBPSysField = new JTextField();
		obsBPSysField.setColumns(10);
		obsBPSysField.setBounds(237, 21, 38, 22);
		panel_2.add(obsBPSysField);
		
		obsBPDysField = new JTextField();
		obsBPDysField.setColumns(10);
		obsBPDysField.setBounds(285, 21, 38, 21);
		panel_2.add(obsBPDysField);
		
		JLabel lblPulseRate_1 = new JLabel("Pulse");
		lblPulseRate_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblPulseRate_1.setBounds(418, 22, 75, 21);
		panel_2.add(lblPulseRate_1);
		
		obsPulseField = new JTextField();
		obsPulseField.setColumns(10);
		obsPulseField.setBounds(488, 21, 64, 22);
		panel_2.add(obsPulseField);
		
		JLabel lblRespirationRate_1 = new JLabel("Resp. Rate");
		lblRespirationRate_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblRespirationRate_1.setBounds(607, 22, 75, 21);
		panel_2.add(lblRespirationRate_1);
		
		obsRespField = new JTextField();
		obsRespField.setColumns(10);
		obsRespField.setBounds(677, 21, 64, 22);
		panel_2.add(obsRespField);
		
		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(10, 54, 184, 97);
		panel_2.add(scrollPane_3);
		
		adm_date_table = new JTable();
		adm_date_table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					admDates = cm_interface.getAdmissionDates(vs_list.get(adm_date_table.getSelectedRow()).getInvoiceNumber());
					if (adm_date_table.getSelectedRow()<0) {
						
					}else {
						updateObservationTable(cm_interface.getInpatientObservations(vs_list.get(adm_date_table.getSelectedRow()).getInvoiceNumber(), admDates.get(adm_date_table.getSelectedRow())));
					}
					
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		scrollPane_3.setViewportView(adm_date_table);
		
		JScrollPane scrollPane_4 = new JScrollPane();
		scrollPane_4.setBounds(10, 162, 617, 148);
		panel_2.add(scrollPane_4);
		
		obs_chart_table = new JTable();
		scrollPane_4.setViewportView(obs_chart_table);
		
		btnSave_1 = new JButton("Save");
		btnSave_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {     
				if(invoiceNoField.getText().length()<1 || obsTempField.getText().length()<1 || obsPulseField.getText().length()<1 || obsRespField.getText().length()<1){
					showMessage("Please enter value for all fields.");
				}else{
					InpatientObservation io = submitObservation();
					try {
						cm_interface.saveInpatientObservation(io);
						showMessage("Successful!");
						obsTempField.setText("");
						obsPulseField.setText("");
						obsRespField.setText("");
						obsBPDysField.setText("");
						obsBPSysField.setText("");
					
						admDates = cm_interface.getAdmissionDates(pVisit.getInvoiceNo());
						updateAdmDateTable(admDates);
						if(admDates.size()>0){
							updateObservationTable(cm_interface.getInpatientObservations(pVisit.getInvoiceNo(), admDates.get(admDates.size()-1)));
						}
					} catch (RemoteException e2) {
						e2.printStackTrace();
					}
					
				}
			}
		});
		btnSave_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnSave_1.setBounds(671, 219, 89, 23);
		panel_2.add(btnSave_1);
		
		JButton btnClear_2 = new JButton("Clear");
		btnClear_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				obsTempField.setText("");
				obsPulseField.setText("");
				obsRespField.setText("");
				obsBPDysField.setText("");
				obsBPSysField.setText("");
				obs_chart_table.clearSelection();
				adm_date_table.clearSelection();
				if (admDates.size()>0) {
					try {
						updateObservationTable(cm_interface.getInpatientObservations(invoiceNoField.getText(), admDates.get(admDates.size()-1)));
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
			}
		});
		btnClear_2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnClear_2.setBounds(671, 253, 89, 23);
		panel_2.add(btnClear_2);
		
		JButton btnClose_1 = new JButton("Close");
		btnClose_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnClose_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnClose_1.setBounds(671, 287, 89, 23);
		panel_2.add(btnClose_1);
		
		
		try {
			admDates = cm_interface.getAdmissionDates(invoiceNoField.getText());
			updateAdmDateTable(admDates);
			if (admDates.size()>0) {
				
			updateObservationTable(cm_interface.getInpatientObservations(invoiceNoField.getText(), admDates.get(admDates.size()-1)));
			}		
			
			vs_list = cm_interface.getVitalSignEntries(invoiceNoField.getText());
			updateVSETable(vs_list);
			
			nn_list = cm_interface.getNursingNotes(invoiceNoField.getText());			
			updateNursingNoteTable(nn_list);
		
		} catch (RemoteException e1) {
			e1.printStackTrace();
		
		}
		
		// deactivate the buttons when consultation is closed
		if (pVisit.getEmrSratus().equalsIgnoreCase("closed")) {
			btnSave.setEnabled(false);
			btnUpdate.setEnabled(false);
			btnAddNotes.setEnabled(false);
			btnEditNote.setEnabled(false);
			btnViewNote.setEnabled(false);
			btnSave_1.setEnabled(false);
		
			
		}
		
		
		
		
	}// end of constructor
	
	//client method to update form
	public void updateForm(VitalSignEntry vs){		
		 btnSave.setEnabled(false);
		 heightField.setText(vs.getHeight()+"");
		 tempField.setText(vs.getTemperature()+"");
		 respField.setText(vs.getRespirationRate()+"");
		 weightField.setText(vs.getWeight()+"");
		 bpSysField.setText(vs.getBpSystole()+"");
		 bpDiasField.setText(vs.getBpDiastole()+"");
		
		 pulseField.setText(vs.getPulseRate()+ "");
	}
	
	//client method to clear form
		public void clearForm(){
			vsTable.clearSelection();
			 btnSave.setEnabled(true);
			 heightField.setText("");
			 tempField.setText("");
			 respField.setText("");
			 weightField.setText("");
			 bpSysField.setText("");
			 bpDiasField.setText("");
			
			 pulseField.setText("");
		}
		
	public VitalSignEntry submitForm(){
		VitalSignEntry vs = new VitalSignEntry();
		try{
		
			vs.setInvoiceNumber(invoiceNoField.getText());
			
			int height = Integer.parseInt(heightField.getText());
			vs.setHeight(height);
			int weight = Integer.parseInt(weightField.getText());
			vs.setWeight(weight);					
			vs.setBmi(0.00);
			int bpsys = Integer.parseInt(bpSysField.getText());
			vs.setBpSystole(bpsys);
			int bpdias = Integer.parseInt(bpDiasField.getText());
			vs.setBpDiastole(bpdias);
			int pulse = Integer.parseInt(pulseField.getText());
			vs.setPulseRate(pulse);
			double temp = Double.parseDouble(tempField.getText());	
			vs.setTemperature(temp);
			int resp = Integer.parseInt(respField.getText());			
			vs.setRespirationRate(resp);		
			
			
		}catch(Exception ex){ex.printStackTrace();
		showMessage("Invalid Input in the Form!");
		return null;
		}
		return vs;
		
	}
	
	//client method to validate form for saving
	public InpatientObservation submitObservation(){
		try{
			InpatientObservation io = new InpatientObservation();
			
			io.setInvoiceNo(invoiceNoField.getText());
			io.setTemperature(Double.parseDouble(obsTempField.getText()));
			io.setBp_systole(Integer.parseInt(obsBPSysField.getText()));
			io.setBy_diastole(Integer.parseInt(obsBPDysField.getText()));
			io.setPulse(Integer.parseInt(obsPulseField.getText()));
			io.setRespiration(Integer.parseInt(obsRespField.getText()));
			
			return io;		
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
		
	}

	
	
	//client method to update vital sign table
	public void updateVSETable(ArrayList<VitalSignEntry> list){
		Object[][] data = new Object[list.size()][9];
		for(int i=0; i<list.size(); i++){ 
			data[i][0] = (i+1);
			
			data[i][1] = list.get(i).getHeight();
			data[i][2] = list.get(i).getWeight();		
			data[i][3] = list.get(i).getTemperature();
			data[i][4] = list.get(i).getBpSystole()+"/" +list.get(i).getBpDiastole();
			data[i][5] = list.get(i).getPulseRate();
			data[i][6] = list.get(i).getRespirationRate();
			data[i][7] = list.get(i).getDate();
			data[i][8] = list.get(i).getTime();
			
		}
		
		Object[] columnNames = { "S/No", "Height", "Weight", "Temp.", "BP", "Pulse", "Resp.","Date", "Time"};
		DefaultTableModel model = new DefaultTableModel(data, columnNames);
		vsTable.setModel(model);
		vsTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		vsTable.getColumnModel().getColumn(0).setPreferredWidth(60);
		vsTable.getColumnModel().getColumn(1).setPreferredWidth(80);
		vsTable.getColumnModel().getColumn(2).setPreferredWidth(80);
		vsTable.getColumnModel().getColumn(3).setPreferredWidth(80);
		vsTable.getColumnModel().getColumn(4).setPreferredWidth(80);
		vsTable.getColumnModel().getColumn(5).setPreferredWidth(80);
		vsTable.getColumnModel().getColumn(6).setPreferredWidth(80);
		vsTable.getColumnModel().getColumn(7).setPreferredWidth(80);
		vsTable.getColumnModel().getColumn(8).setPreferredWidth(80);
		
	}
	
	//client method to update Nursing notes table
	public void updateNursingNoteTable(ArrayList<NursingNote> list){
		Object[][] data = new Object[list.size()][6];
		for(int i=0; i<list.size(); i++){ 
			data[i][0] = (i+1);
			data[i][1] = list.get(i).getNote();
			data[i][2] = list.get(i).getNurse();
			data[i][3] = list.get(i).getDate();
			data[i][4] = list.get(i).getTime();
			data[i][5] = list.get(i).getStatus();
		}
		
		Object[] columnNames = { "S/No", "Nursing Note", "Nurse", "Date", "Time", "Status"};
		DefaultTableModel model = new DefaultTableModel(data, columnNames);
		nursing_note_table.setModel(model);
		nursing_note_table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		nursing_note_table.getColumnModel().getColumn(0).setPreferredWidth(40);
		nursing_note_table.getColumnModel().getColumn(1).setPreferredWidth(280);
		nursing_note_table.getColumnModel().getColumn(2).setPreferredWidth(100);
		nursing_note_table.getColumnModel().getColumn(3).setPreferredWidth(90);
		nursing_note_table.getColumnModel().getColumn(4).setPreferredWidth(90);
		nursing_note_table.getColumnModel().getColumn(5).setPreferredWidth(90);
	}
	
	//client method to update the admission date table
	public void updateAdmDateTable(ArrayList<Date> list){
		Object[][] data = new Object[list.size()][2];
		
		for(int i = 0; i<list.size(); i++){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String dt = sdf.format(list.get(i));
			data[i][0] = (i+1);
			data[i][1] = dt;
			}
		
		Object[] columnNames = {"Day","Admission Date"};
		
		DefaultTableModel model = new DefaultTableModel(data, columnNames);
		adm_date_table.setModel(model);
		adm_date_table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		adm_date_table.getColumnModel().getColumn(0).setPreferredWidth(54 );
		adm_date_table.getColumnModel().getColumn(1).setPreferredWidth(130);
		
	}
	
	//client method to update the observation table 
	public void updateObservationTable(ArrayList<InpatientObservation> list){
		Object[][] data = new Object[list.size()][6];
		for(int i = 0; i<list.size(); i++){
			data[i][0] = (i+1);
			data[i][2] = list.get(i).getTemperature();
			data[i][1] = list.get(i).getTime();
			data[i][3] = list.get(i).getBp_systole()+" / "+ list.get(i).getBy_diastole();
			data[i][4] = list.get(i).getPulse();
			data[i][5] = list.get(i).getRespiration();			
			
		}
		
		Object[] columnNames = {"S/No","Time", "Temperature", "Blood Pressure", "Pulse Rate", "Resp. Rate"};
		DefaultTableModel model = new DefaultTableModel(data, columnNames);
		obs_chart_table.setModel(model);
		obs_chart_table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		obs_chart_table.getColumnModel().getColumn(0).setPreferredWidth(50);
		obs_chart_table.getColumnModel().getColumn(1).setPreferredWidth(100);
		obs_chart_table.getColumnModel().getColumn(2).setPreferredWidth(110);
		obs_chart_table.getColumnModel().getColumn(3).setPreferredWidth(120);
		obs_chart_table.getColumnModel().getColumn(4).setPreferredWidth(110);
		obs_chart_table.getColumnModel().getColumn(5).setPreferredWidth(110);
		
	}
	
	//a standard client method for displaying popup messages on frame that is setAlwaysOnTop(true)
	public static void showMessage(String message){
		final JDialog dialog = new JDialog();
		dialog.setAlwaysOnTop(true);
		JOptionPane.showMessageDialog(dialog, message);
	}
	
	public static String calculateAge(Date dob) {		
		
		try {
			LocalDate today = LocalDate.now();//Today's date			
			LocalDate localDate = LocalDate.parse( new SimpleDateFormat("yyyy-MM-dd").format(dob) );
			LocalDate birthday = LocalDate.of(localDate.getYear(), localDate.getMonth(), localDate.getDayOfMonth());  //Birth date			 
			Period p = Period.between(birthday, today);
			
			
			if (p.getYears()>0) {
				return p.getYears() + " Year(s)";
			}else if(p.getMonths()>0){
				return p.getMonths() + " month(s)";
			}else {
				return p.getDays() + " day(s)";
			}
			
		} catch (Exception e) {
			return "error";
		}
		
		
	}
}
