package com.ahms.hospitalmanagement;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JSpinner;
import javax.swing.JButton;

import com.ahms.api.HospitalManagementInterface;
import com.ahms.clientinterface.InterfaceGenerator;
import com.ahms.clinicmanagement.OPDVisit;
import com.ahms.hmgt.entities.Appointment;
import com.ahms.hmgt.entities.Doctor;
import com.ahms.hmgt.entities.PatientBiodata;
import com.ahms.hmgt.entities.UserCard;
import com.toedter.calendar.JDateChooser;
import java.awt.Dimension;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringJoiner;
import java.awt.event.ActionEvent;
import javax.swing.DefaultComboBoxModel;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class OPDAppointment extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static JTextField hospNoField;
	private static JTextField patNameField;
	
	private JSpinner timeSpinner;
	private JTextField searchField;
	private JTable table;
	private JComboBox comboBox;
	private JComboBox comboBox_1;
	private JDateChooser dateChooser;
	private JButton btnSave;
	private JButton btnEdit;
	private JDateChooser dateChooser_1;
	private JDateChooser dateChooser_2;
	private JComboBox comboBox_2;
	
	
	private ArrayList<Appointment> apptList;
	private ArrayList<Doctor> doctorList;
	private ArrayList<String> errorlist = new ArrayList<>();
	
	private Connection conn;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OPDAppointment frame = new OPDAppointment(new UserCard(), new PatientBiodata(), "new");
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
	public OPDAppointment(UserCard user, PatientBiodata pBiodata, String source) throws RemoteException {
		setTitle("Elite HMS - Appointment / Schedule");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(380, 130, 675, 555);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		HospitalManagementInterface hm_interface = InterfaceGenerator.getHospitalManagementInterface();		
		doctorList = hm_interface.getAllDoctors();
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 11, 639, 492);
		contentPane.add(tabbedPane);
		
		JPanel panel1 = new JPanel();
		tabbedPane.addTab("Appointment Entry", null, panel1, null);
		panel1.setLayout(null);
		
		JLabel lblHospitalNo = new JLabel("Hospital No: ");
		lblHospitalNo.setHorizontalAlignment(SwingConstants.LEFT);
		lblHospitalNo.setBounds(57, 65, 99, 20);
		panel1.add(lblHospitalNo);
		
		JLabel lblPatientName = new JLabel("Patient Name: ");
		lblPatientName.setHorizontalAlignment(SwingConstants.LEFT);
		lblPatientName.setBounds(57, 96, 99, 20);
		panel1.add(lblPatientName);
		
		JLabel lblSelectDoctor = new JLabel("Select Doctor: ");
		lblSelectDoctor.setHorizontalAlignment(SwingConstants.LEFT);
		lblSelectDoctor.setBounds(57, 127, 99, 20);
		panel1.add(lblSelectDoctor);
		
		JLabel lblDate = new JLabel("Date: ");
		lblDate.setHorizontalAlignment(SwingConstants.LEFT);
		lblDate.setBounds(57, 158, 99, 20);
		panel1.add(lblDate);
		
		JLabel lblTime = new JLabel("Time: ");
		lblTime.setHorizontalAlignment(SwingConstants.LEFT);
		lblTime.setBounds(57, 189, 99, 20);
		panel1.add(lblTime);
		
		JLabel lblAppointmentState = new JLabel("Appointment State: ");
		lblAppointmentState.setHorizontalAlignment(SwingConstants.LEFT);
		lblAppointmentState.setBounds(57, 220, 99, 20);
		panel1.add(lblAppointmentState);
		
		patNameField = new JTextField();
		patNameField.setEditable(false);
		patNameField.setBounds(166, 96, 244, 20);
		panel1.add(patNameField);
		patNameField.setColumns(10);
		
		hospNoField = new JTextField();
		hospNoField.setEditable(false);
		hospNoField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String text;
				try {
					text = hm_interface.getPatientName(hospNoField.getText());
					if(text.length()>0){
						patNameField.setText(text);
						hospNoField.setEditable(false);
						}else{		patNameField.setText(text); 	}
				} catch (RemoteException e) {e.printStackTrace();		}
				
			}
		});
		hospNoField.setBounds(166, 65, 128, 20);
		panel1.add(hospNoField);
		hospNoField.setColumns(10);
		
		comboBox = new JComboBox();
			
		comboBox.setBounds(166, 127, 193, 20);
		for(int i=0; i<doctorList.size(); i++){
			comboBox.addItem("Dr. "+doctorList.get(i).getDoctorName());
		}
		panel1.add(comboBox);
		
		
		
		timeSpinner = new JSpinner(new SpinnerDateModel());
		timeSpinner.setBounds(166, 189, 90, 20);
		panel1.add(timeSpinner);
		JSpinner.DateEditor de_timeSpinner = new JSpinner.DateEditor(timeSpinner, "hh:mm a");
		timeSpinner.setEditor(de_timeSpinner);
		
		comboBox_1 = new JComboBox();
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"Booked", "Arrived"}));
		comboBox_1.setBounds(166, 220, 109, 20);
		panel1.add(comboBox_1);
		
		dateChooser = new JDateChooser();
		dateChooser.setPreferredSize(new Dimension(40, 20));
		dateChooser.setDateFormatString("yyyy-MM-dd");
		dateChooser.setDate(new Date());
		dateChooser.setMaxSelectableDate(new Date());
		dateChooser.setBounds(166, 158, 128, 21);
		panel1.add(dateChooser);
		
		btnSave = new JButton("Save");
		btnSave.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				errorlist.clear();
				//check for missing values and fill up error list
				if(hospNoField.getText().length() <= 0){
					errorlist.add("Hospital No");
				}	
				
				//if any error exists
				if(errorlist.size()>0){
					StringJoiner join = new StringJoiner(",");
					for(int i=0; i<errorlist.size(); i++){	join.add(errorlist.get(i));	}
					showMessage("Field(s) "+join.toString()+" Cannot be Empty ");
					
				//theres no error. check date and proceed to insert record
				}else{
					//set all five variables
					try{
					String hosp_no = hospNoField.getText();//var1
					
					//parse selected date to sql format					
					String appt_date = ((JTextField)dateChooser.getDateEditor().getUiComponent()).getText();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					Date date = sdf.parse(appt_date);
					java.sql.Date sqldate = new java.sql.Date(date.getTime());//var3
					
					Date text = (Date) timeSpinner.getValue();
					SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm a");
					String appt_time = 	sdf2.format(text).toString();//var4
					
					String appt_state = comboBox_1.getSelectedItem().toString();//var5
								
					hm_interface.saveAppointment(hosp_no, doctorList.get(comboBox.getSelectedIndex()).getDoctorId(), sqldate, appt_time, appt_state);
					}catch(Exception e){showMessage("Invalid Date Entry");}
					
					showMessage("Success!");
					btnSave.setEnabled(false);
					btnEdit.setEnabled(true);
					
									
				}
				
			}
		});
		btnSave.setBounds(331, 418, 90, 23);
		panel1.add(btnSave);
		
		btnEdit = new JButton("Update");
		btnEdit.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnEdit.setEnabled(false);
		btnEdit.setBounds(431, 418, 93, 23);
		panel1.add(btnEdit);
		
		JButton btnClose = new JButton("Close");
		btnClose.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btnClose.setBounds(534, 418, 90, 23);
		panel1.add(btnClose);
		
		JLabel lblNewLabel_1 = new JLabel("Book Patient Appointment");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_1.setBounds(10, 11, 579, 23);
		panel1.add(lblNewLabel_1);
		
		JButton btnSearchButton = new JButton(">>>");
		btnSearchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SearchPatientFile pFile = new SearchPatientFile(user, "appt");
				pFile.setVisible(true);
			}
		});
		btnSearchButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnSearchButton.setBounds(304, 64, 68, 23);
		panel1.add(btnSearchButton);
		
		JPanel panel2 = new JPanel();
		tabbedPane.addTab("Search & View", null, panel2, null);
		panel2.setLayout(null);
		
		JLabel lblEnterText = new JLabel("Search By:");
		lblEnterText.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblEnterText.setHorizontalAlignment(SwingConstants.LEFT);
		lblEnterText.setBounds(22, 56, 70, 21);
		panel2.add(lblEnterText);
		
		searchField = new JTextField();
		searchField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (searchField.getText().length()<1) {
					apptList.clear();
					updateTable(apptList);
				}else {
					try {
						apptList = hm_interface.searchAppointment(comboBox_2.getSelectedItem().toString(), searchField.getText());
						updateTable(apptList);
					} catch (RemoteException e2) {						
						e2.printStackTrace();
					}
				}
			}
		});
		
		searchField.setBounds(255, 56, 140, 21);
		panel2.add(searchField);
		searchField.setColumns(10);
		
		JLabel lblFrom = new JLabel("From");
		lblFrom.setHorizontalAlignment(SwingConstants.LEFT);
		lblFrom.setBounds(22, 88, 41, 21);
		panel2.add(lblFrom);
		
		JLabel lblTo = new JLabel("To");
		lblTo.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTo.setBounds(239, 88, 30, 21);
		panel2.add(lblTo);
		
		dateChooser_1 = new JDateChooser();
		dateChooser_1.setPreferredSize(new Dimension(40, 20));
		dateChooser_1.setDateFormatString("yyyy-MM-dd");
		dateChooser_1.setDate(new Date());
		dateChooser_1.setBounds(85, 88, 124, 20);
		panel2.add(dateChooser_1);
		
		dateChooser_2 = new JDateChooser();
		dateChooser_2.setPreferredSize(new Dimension(40, 20));
		dateChooser_2.setDateFormatString("yyyy-MM-dd");
		dateChooser_2.setDate(new Date());		
		dateChooser_2.setBounds(279, 88, 124, 20);
		panel2.add(dateChooser_2);
		
		JButton button_1 = new JButton(">>>");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					Date dateOne = sdf.parse(((JTextField)dateChooser_1.getDateEditor().getUiComponent()).getText());
					Date dateTwo = sdf.parse(((JTextField)dateChooser_2.getDateEditor().getUiComponent()).getText());
					
					java.sql.Date d1 = new java.sql.Date(dateOne.getTime());
					java.sql.Date d2 = new java.sql.Date(dateTwo.getTime());
					
					apptList = hm_interface.searchAppointment(d1, d2);
					updateTable(apptList);
					
					searchField.setText("");
					
				}catch(Exception e){e.printStackTrace();}
			}
		});
		button_1.setBounds(441, 87, 70, 23);
		panel2.add(button_1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 134, 614, 270);
		panel2.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		comboBox_2 = new JComboBox();
		comboBox_2.setModel(new DefaultComboBoxModel(new String[] {"Patient Name", "Hospital Number"}));
		comboBox_2.setBounds(85, 56, 140, 21);
		panel2.add(comboBox_2);
		
		JLabel lblNewLabel_2 = new JLabel("Search Patient Appointment");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setBounds(10, 11, 579, 20);
		panel2.add(lblNewLabel_2);
		
		JButton btnClose_1 = new JButton("Close");
		btnClose_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				System.gc();
			}
		});
		btnClose_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnClose_1.setBounds(534, 417, 90, 23);
		panel2.add(btnClose_1);
		
		JButton newVisitButton = new JButton("New Visit");
		newVisitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (table.getSelectedRow()<0) {
						showMessage("Select patient from table.");
					}else {
							PatientBiodata pBiodata  = hm_interface.getPatientBiodata(apptList.get(table.getSelectedRow()).getHospitalNno());
							
							OPDVisit frame = new OPDVisit(user, pBiodata, "search");
							frame.setVisible(true);
							dispose();
							System.gc();
						
						}
						
					
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		newVisitButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		newVisitButton.setBounds(435, 417, 89, 23);
		panel2.add(newVisitButton);
		
		if (source.equalsIgnoreCase("search")) {
			updatePatientInfo(pBiodata);
		}
	}
	
	
	public static void updatePatientInfo(PatientBiodata pBiodata) {
		hospNoField.setText(pBiodata.getHospital_no());
		patNameField.setText(pBiodata.getSurname() +", "+pBiodata.getFirstname());
	}
	
	//client method to display 
	public void updateTable(ArrayList<Appointment> apptlist){
		Object[][] data = new Object[apptlist.size()][7];
		for(int i=0; i<apptlist.size(); i++){
			data[i][0] = (i+1);
			data[i][1] = apptlist.get(i).getHospitalNno();
			data[i][2] = apptlist.get(i).getSurname() +" "+ apptlist.get(i).getOthernames(); 
			data[i][3] = apptlist.get(i).getDoctorName();
			data[i][4] = apptlist.get(i).getDate();
			data[i][5] = apptlist.get(i).getTime();	
			data[i][6] = apptlist.get(i).getState();
		}
		Object[] columnNames = {"S/No", "Hosp. No", "Patient Name", "Doctor", "Date", "Time", "State" };
		
		DefaultTableModel model = new DefaultTableModel(data, columnNames);
		table.setModel(model);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getColumnModel().getColumn(0).setPreferredWidth(60);
		table.getColumnModel().getColumn(1).setPreferredWidth(100);
		table.getColumnModel().getColumn(2).setPreferredWidth(180);
		table.getColumnModel().getColumn(3).setPreferredWidth(120);
		table.getColumnModel().getColumn(4).setPreferredWidth(100);
		table.getColumnModel().getColumn(5).setPreferredWidth(100);
		table.getColumnModel().getColumn(6).setPreferredWidth(100);
		
	}
	
	public static void showMessage(String message){
		final JDialog dialog = new JDialog();
		dialog.setAlwaysOnTop(true);
		JOptionPane.showMessageDialog(dialog, message);
	}
}
