package com.ahms.clinicmanagement;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.awt.event.ActionEvent;

import com.ahms.api.ClinicManagementInterface;
import com.ahms.clientinterface.InterfaceGenerator;
import com.ahms.clinicmgt.entities.BookedProcedure;
import com.ahms.clinicmgt.entities.ClinicProcedure;
import com.ahms.clinicmgt.entities.DiseeaseIndex;
import com.ahms.clinicmgt.entities.PatientVisit;
import com.ahms.hmgt.entities.UserCard;
import com.toedter.calendar.JDateChooser;
import java.awt.Dimension;
import javax.swing.SwingConstants;
import javax.swing.JSpinner;
import javax.swing.DefaultComboBoxModel;
import javax.swing.UIManager;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ProcedureBooking extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField invoiceField;
	private JTextField hospNoField;
	private JTextField nameField;
	private JTextField genderField;
	private JSpinner spinner;
	private JTextField copField;
	private JTextField coaField;
	private JTextField bookedbyField;
	private JDateChooser dateChooser;
	private JComboBox theatreBox;
	private static JComboBox procedureBox;
	private JComboBox anesthesiaBox;
	
	private JButton btnOperationNotes;
	private JButton btnUpdate;
	private JButton btnSave;
	private JButton btnConfirmProcedure;
	
	private int selected_procedure_sno;
	
	
	private Connection conn;
	private static ArrayList<ClinicProcedure> procedure_list;
	private ArrayList<BookedProcedure> patient_procedure_list;
	private JTable table;
	PatientVisit pvs = null;
	static ClinicManagementInterface cm_interface = null;
	private JButton btnClear;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProcedureBooking frame = new ProcedureBooking(new UserCard(), "","");
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
	public ProcedureBooking(UserCard user, String invoice_no, String source) throws RemoteException{
		setResizable(false);
		setTitle("Elite HMS - Book Surgical Procedure     ");
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(350, 100, 739, 628);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		cm_interface = InterfaceGenerator.getClinicManagementInterface();
		
		procedure_list = cm_interface.getAllProcedures();
		pvs = cm_interface.getPatientVisit(invoice_no);
		patient_procedure_list = cm_interface.getBookedProcedures(invoice_no);
		
		JLabel lblInvoiceNo = new JLabel("Invoice No");
		lblInvoiceNo.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblInvoiceNo.setBounds(437, 88, 76, 20);
		contentPane.add(lblInvoiceNo);
		
		invoiceField = new JTextField();
		invoiceField.setText(invoice_no);
		invoiceField.setEditable(false); 		
		invoiceField.setBounds(523, 88, 131, 20);
		contentPane.add(invoiceField);
		invoiceField.setColumns(10);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Book New Procedure", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_2.setBounds(10, 119, 689, 262);
		contentPane.add(panel_2);
		panel_2.setLayout(null);
		
		JLabel lblAnesthesia = new JLabel("Anesthesia");
		lblAnesthesia.setBounds(10, 59, 62, 20);
		panel_2.add(lblAnesthesia);
		lblAnesthesia.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		anesthesiaBox = new JComboBox();
		anesthesiaBox.setModel(new DefaultComboBoxModel(new String[] {"--Select", "Local Anesthesia", "General Anesthesia", "Spinal Anesthesia", "Sedative Anesthesia"}));
		anesthesiaBox.setBounds(120, 59, 178, 20);
		panel_2.add(anesthesiaBox);
		
		JLabel lblSurgeryDate = new JLabel("Date");
		lblSurgeryDate.setBounds(10, 183, 76, 20);
		panel_2.add(lblSurgeryDate);
		lblSurgeryDate.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		JLabel lblCostOfProcedure = new JLabel("Cost of Procedure");
		lblCostOfProcedure.setBounds(10, 90, 100, 20);
		panel_2.add(lblCostOfProcedure);
		lblCostOfProcedure.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		JLabel lblSurgeryTime = new JLabel("Time");
		lblSurgeryTime.setHorizontalAlignment(SwingConstants.LEFT);
		lblSurgeryTime.setBounds(10, 214, 76, 20);
		panel_2.add(lblSurgeryTime);
		lblSurgeryTime.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		JLabel lblCostOfAnesthesia = new JLabel("Cost of Anesthesia");
		lblCostOfAnesthesia.setHorizontalAlignment(SwingConstants.LEFT);
		lblCostOfAnesthesia.setBounds(10, 121, 100, 20);
		panel_2.add(lblCostOfAnesthesia);
		lblCostOfAnesthesia.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		JLabel lblTheatre = new JLabel("Theatre");
		lblTheatre.setBounds(10, 152, 62, 20);
		panel_2.add(lblTheatre);
		lblTheatre.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		theatreBox = new JComboBox(); 
		theatreBox.setModel(new DefaultComboBoxModel(new String[] {"--Select", "Theatre I", "Theatre II"}));
		theatreBox.setBounds(120, 152, 146, 20);
		panel_2.add(theatreBox);
		
		
		JLabel lblProcedure = new JLabel("Procedure");
		lblProcedure.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblProcedure.setBounds(10, 28, 76, 20);
		panel_2.add(lblProcedure);
		
		procedureBox = new JComboBox();  
		procedureBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int xy = procedureBox.getSelectedIndex();
				if(xy<0){
					
				}else{
					
				}
			}
		});
		procedureBox.setBounds(120, 28, 220, 20);
		updateProcedureBox();
		AutoCompleteDecorator.decorate(procedureBox);
		panel_2.add(procedureBox);
		
		
		
		copField = new JTextField(); 
		copField.setBounds(120, 90, 86, 20);
		panel_2.add(copField);
		copField.setColumns(10);
		
		coaField = new JTextField();
		coaField.setBounds(120, 121, 86, 20);
		panel_2.add(coaField);
		coaField.setColumns(10);
		
		JLabel lblBookedBy = new JLabel("Booked By");
		lblBookedBy.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblBookedBy.setBounds(376, 214, 62, 20);
		panel_2.add(lblBookedBy);
		
		bookedbyField = new JTextField();
		bookedbyField.setText(user.getFullNames());
		bookedbyField.setEditable(false);
		bookedbyField.setBounds(448, 214, 220, 20);
		panel_2.add(bookedbyField);
		bookedbyField.setColumns(10);
		
		dateChooser = new JDateChooser();
		dateChooser.setPreferredSize(new Dimension(40, 20));
		dateChooser.setDateFormatString("yyyy-MM-dd");
		dateChooser.setDate(new Date());
		dateChooser.setMinSelectableDate(new Date());
		dateChooser.setBounds(120, 183, 100, 20);
		panel_2.add(dateChooser);
		
		spinner = new JSpinner(new SpinnerDateModel());
		spinner.setFont(new Font("Tahoma", Font.PLAIN, 11));
		spinner.setBounds(120, 214, 100, 20);
		JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(spinner, "hh:mm a");
		spinner.setEditor(timeEditor);
		panel_2.add(spinner);
		
		JButton btnNewButton = new JButton("Add");
		btnNewButton.setBounds(350, 27, 88, 23);
		panel_2.add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				ProcedureEntry pe = new ProcedureEntry("visit");
				pe.setVisible(true);
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		
		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			dispose();
						
			}
		});
		btnClose.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnClose.setBounds(579, 543, 128, 23);
		contentPane.add(btnClose);
		
		JLabel lblHospitalNo = new JLabel("Hospital No");
		lblHospitalNo.setBounds(437, 57, 79, 20);
		contentPane.add(lblHospitalNo);
		lblHospitalNo.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		hospNoField = new JTextField();
		hospNoField.setBounds(104, 88, 110, 20);
		contentPane.add(hospNoField);
		hospNoField.setText(pvs.getHospitalNo());
		hospNoField.setEditable(false);
		hospNoField.setColumns(10);
		
		JLabel lblFullName = new JLabel("Patient Name");
		lblFullName.setBounds(10, 57, 84, 20);
		contentPane.add(lblFullName);
		lblFullName.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		nameField = new JTextField();
		nameField.setText(pvs.getSurname()+" "+pvs.getOthernames());
		nameField.setBounds(104, 57, 225, 20);
		contentPane.add(nameField);
		nameField.setEditable(false);
		nameField.setColumns(10);
		
		JLabel lblGender = new JLabel("Gender");
		lblGender.setBounds(10, 88, 59, 20);
		contentPane.add(lblGender);
		lblGender.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		genderField = new JTextField();
		genderField.setBounds(526, 57, 128, 20);
		contentPane.add(genderField);
		genderField.setText(pvs.getGender());
		genderField.setEditable(false);
		genderField.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 392, 689, 97);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				selected_procedure_sno = Integer.parseInt(patient_procedure_list.get(table.getSelectedRow()).getSno());
				if (patient_procedure_list.get(table.getSelectedRow()).getProcedureState().equalsIgnoreCase("confirmed")) {
					btnUpdate.setEnabled(false);
				}else {
					updateForm(patient_procedure_list.get(table.getSelectedRow()));
					System.out.println(patient_procedure_list.get(table.getSelectedRow()).getSno());
					btnUpdate.setEnabled(true);
				}
			}
		});
		scrollPane.setViewportView(table);
		
		btnOperationNotes = new JButton("Operation Notes");
		btnOperationNotes.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnOperationNotes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int xy = table.getSelectedRow();
				if(xy<0){
				showMessage("No Procedure Selected.");	
				}else if(!patient_procedure_list.get(xy).getProcedureState().equalsIgnoreCase("confirmed")){
					showMessage("Procedure has not been confirmed yet.");	
				}else{
					OperationNoteEntry opnote = new OperationNoteEntry(patient_procedure_list.get(xy).getInvoiceNo(), pvs.getEmrSratus(), patient_procedure_list.get(xy).getProcedure(), patient_procedure_list.get(xy).getCptCode());
					opnote.setVisible(true);
					dispose();
				}
				
				
			}
		});
		btnOperationNotes.setBounds(300, 543, 128, 23);
		contentPane.add(btnOperationNotes);
		
		btnConfirmProcedure = new JButton("Confirm Procedure");
		btnConfirmProcedure.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				final JFrame jFrame = new JFrame();
				jFrame.setAlwaysOnTop(true);
				
				int xy = table.getSelectedRow();
				if(xy<0){
					showMessage("No item selected.");
				}else{
					String status = patient_procedure_list.get(xy).getProcedureState();
					String cpt_code = patient_procedure_list.get(xy).getCptCode();
											
					if(status.equalsIgnoreCase("pending")){
						try {					
							
							int confirmed = JOptionPane.showConfirmDialog(jFrame, 
							        "Do you want to confirm this procedure?", "Confirm Procedure",
							        JOptionPane.YES_NO_OPTION);

							    if (confirmed == JOptionPane.YES_OPTION) {
							    	cm_interface.updateProcedureState(invoiceField.getText(), cpt_code);
									showMessage("Successful!");
									patient_procedure_list = cm_interface.getBookedProcedures(invoiceField.getText());
									updateProcedureTable(patient_procedure_list);
							    }
							
							
						} catch (RemoteException e) {
							e.printStackTrace();
						}
					
					}else{
						showMessage("Procedure is confirmed!");
					}
					
					}
			}
		});
		btnConfirmProcedure.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnConfirmProcedure.setBounds(579, 509, 128, 23);
		btnConfirmProcedure.setEnabled(false);
		contentPane.add(btnConfirmProcedure);
		
		JLabel lblNewLabel = new JLabel("Book Surgical Procedure");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel.setBounds(10, 11, 697, 31);
		contentPane.add(lblNewLabel);
		
		btnUpdate = new JButton("Update Booking");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {			
					
				try {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					Date date = sdf.parse(((JTextField)dateChooser.getDateEditor().getUiComponent()).getText());
					Double copr = Double.parseDouble(copField.getText());
					Double coan = Double.parseDouble(coaField.getText());
					Date textTime = (Date) spinner.getValue();
					SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm a");
					
					BookedProcedure bpcd = new BookedProcedure();
					bpcd.setInvoiceNo(invoiceField.getText());
					bpcd.setProcedure(procedureBox.getSelectedItem().toString());
					bpcd.setCptCode(procedure_list.get(procedureBox.getSelectedIndex()).getId());
					bpcd.setAnesthesia(anesthesiaBox.getSelectedItem().toString());
					bpcd.setTheatre(theatreBox.getSelectedItem().toString());
					bpcd.setDate(sdf.format(date).toString());
					bpcd.setTime(sdf2.format(textTime));
					bpcd.setRemarks("");									
					bpcd.setProcedureCost(copr);
					bpcd.setAnesthesiaCost(coan);
					bpcd.setSno(selected_procedure_sno + "");
					
					cm_interface.updatePatientProcedure(bpcd);
					showMessage("Procedure updated successfully.");
					patient_procedure_list = cm_interface.getBookedProcedures(invoice_no);
					updateProcedureTable(patient_procedure_list);
					
					clearForm();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				
			}
		});
		btnUpdate.setBounds(438, 509, 131, 23);
		contentPane.add(btnUpdate);
		btnUpdate.setEnabled(false);
		btnUpdate.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		btnSave = new JButton("Book Procedure");
		btnSave.setBounds(300, 509, 128, 23);
		contentPane.add(btnSave);
		btnSave.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		btnClear = new JButton("Clear Form");
		btnClear.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearForm();
			}
		});
		btnClear.setBounds(437, 543, 131, 23);
		contentPane.add(btnClear);
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(hospNoField.getText().length()<1){
					showMessage("Enter a valid invoice number");
				}else{
					if(anesthesiaBox.getSelectedItem().toString().equalsIgnoreCase("--select")){
						showMessage("Select type of anesthesia");
					}else if(theatreBox.getSelectedItem().toString().equalsIgnoreCase("--select")){
						showMessage("Select operation theatre");
					}else if(copField.getText().length()<1){
						showMessage("Enter a valid number for cost of procedure");
					}else if(coaField.getText().length()<1){
						showMessage("Enter a valid number for cost of anesthesia");
					}else if(bookedbyField.getText().length()<1){
						showMessage("Enter name of booking physician");
					}else{
						// the form is filled. use try/multiple catch to complete validation
						
						try {
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
							Date date = sdf.parse(((JTextField)dateChooser.getDateEditor().getUiComponent()).getText());
							Double copr = Double.parseDouble(copField.getText());
							Double coan = Double.parseDouble(coaField.getText());
							Date textTime = (Date) spinner.getValue();
							SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm a");
							
							BookedProcedure bpcd = new BookedProcedure();
							bpcd.setInvoiceNo(invoiceField.getText());
							bpcd.setProcedure(procedureBox.getSelectedItem().toString());
							bpcd.setCptCode(procedure_list.get(procedureBox.getSelectedIndex()).getId());
							bpcd.setAnesthesia(anesthesiaBox.getSelectedItem().toString());
							bpcd.setTheatre(theatreBox.getSelectedItem().toString());
							bpcd.setDate(sdf.format(date).toString());
							bpcd.setTime(sdf2.format(textTime));
							bpcd.setRemarks("");
							bpcd.setBookedBy(bookedbyField.getText());
							
							bpcd.setProcedureCost(copr);
							bpcd.setAnesthesiaCost(coan);
							
							cm_interface.saveBookedProcedure(bpcd);
							showMessage("Successful!");
							patient_procedure_list = cm_interface.getBookedProcedures(invoice_no);
							updateProcedureTable(patient_procedure_list);
							clearForm();
							
						}catch (ParseException e) { showMessage("Enter a valid date for surgery");		
						}catch(NumberFormatException ex){ showMessage("Enter a valid number for the cost of procedure/anesthesia"); 
						}catch (RemoteException e) {	e.printStackTrace();	}
						
					}
					
				} 
				
			}
		});
		
		
		if (pvs.getEmrSratus().equalsIgnoreCase("closed")) {
			btnUpdate.setEnabled(false);
			btnSave.setEnabled(false);
			btnConfirmProcedure.setEnabled(false);
		}
		
		updateProcedureTable(patient_procedure_list);	
		
		
	}
		
	
	
	public void clearForm(){
		anesthesiaBox.setSelectedItem("--Select");
		theatreBox.setSelectedIndex(0);
		procedureBox.setSelectedIndex(0);
		copField.setText("");
		coaField.setText("");
		bookedbyField.setText("");
		btnUpdate.setEnabled(false);		
	}
	
	public void updateForm(BookedProcedure procedure){
		anesthesiaBox.setSelectedItem(procedure.getAnesthesia());
		theatreBox.setSelectedItem(procedure.getTheatre());
		procedureBox.setSelectedItem(procedure.getProcedure());
		copField.setText(procedure.getProcedureCost() + "");
		coaField.setText(procedure.getAnesthesiaCost() + "");
		bookedbyField.setText(procedure.getBookedBy());
		
		
	}

	//a standard client method for displaying popup messages on frame that is setAlwaysOnTop(true)
	public static void showMessage(String message){
		final JFrame dialog = new JFrame();
		dialog.setAlwaysOnTop(true);
		JOptionPane.showMessageDialog(dialog, message);
	}
	
	
	//client method to update procedure table  
		public void updateProcedureTable(ArrayList<BookedProcedure> list){
			Object[][] data = new Object[list.size()][7];
			for(int i = 0; i<list.size(); i++){
				data[i][0] = (i+1);
				data[i][1] = list.get(i).getProcedure();
				data[i][2] = list.get(i).getTheatre();
				data[i][3] = list.get(i).getDate();
				data[i][4] = list.get(i).getTime();
				data[i][5] = list.get(i).getBookedBy();
				data[i][6] = list.get(i).getProcedureState();
				
			}
			
			Object[] columnNames = {"S/No", "Procedure", "Theatre", "Date", "Time", "Booked by", "Status"};
			DefaultTableModel model = new DefaultTableModel(data, columnNames);
			table.setModel(model);
			table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			table.getColumnModel().getColumn(0).setPreferredWidth(60);
			table.getColumnModel().getColumn(1).setPreferredWidth(250);
			table.getColumnModel().getColumn(2).setPreferredWidth(120);
			table.getColumnModel().getColumn(3).setPreferredWidth(90);
			table.getColumnModel().getColumn(4).setPreferredWidth(90);
			table.getColumnModel().getColumn(5).setPreferredWidth(130);
			table.getColumnModel().getColumn(6).setPreferredWidth(100);
		}
		
		
	public static void updateProcedureBox() {
		try {
			procedureBox.removeAllItems();
			procedure_list = cm_interface.getAllProcedures();
			for(int i=0; i<procedure_list.size(); i++){
				procedureBox.addItem(procedure_list.get(i).getProcedure());
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}	
		
		
		
		
}
