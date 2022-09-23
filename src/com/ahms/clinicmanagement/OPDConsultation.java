package com.ahms.clinicmanagement;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JTabbedPane;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import com.ahms.api.ClinicManagementInterface;
import com.ahms.api.HospitalManagementInterface;
import com.ahms.clientinterface.InterfaceGenerator;
import com.ahms.clinicmgt.entities.BookedProcedure;
import com.ahms.clinicmgt.entities.ClinicBill;
import com.ahms.clinicmgt.entities.ConsultationNote;
import com.ahms.clinicmgt.entities.DischargeSummary;
import com.ahms.clinicmgt.entities.DiseeaseIndex;
import com.ahms.clinicmgt.entities.NursingNote;
import com.ahms.clinicmgt.entities.PatientVisit;
import com.ahms.clinicmgt.entities.PhysicalExamNote;
import com.ahms.clinicmgt.entities.PrescriptionOrderItem;
import com.ahms.clinicmgt.entities.SystemReviewNote;
import com.ahms.clinicmgt.entities.VitalSignEntry;
import com.ahms.hmgt.entities.HospitalCharge;
import com.ahms.hospitalmanagement.NewClinicCharges;
import com.ahms.labmgt.entities.TestOrderItem;
import com.ahms.laboratorymanagement.TestOrderEntry;
import com.ahms.laboratorymanagement.TestResultsViewing;

import javax.swing.UIManager;
import java.awt.Color;
import java.awt.Font;
import java.awt.CardLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.border.LineBorder;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Date;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.swing.border.MatteBorder;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class OPDConsultation extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField invoiceField;
	private JTextField hospNoField;
	private JTextField nameField;
	private JTextField AgeField;
	private JTextField sexField;
	private JTextField docField;
	private JTextField dateTimeField;
	private JTextArea complaintArea; 
	private JTextArea treatmentPlanArea;
	private JTextArea historyTextArea;
	private JTextArea physicalExamArea;
	
	private JButton btnSave;
	private JButton btnNewDiagnosis;
	
	
	private JPanel emrCardHolder;
	private CardLayout emrLayout = new CardLayout();
	public static JTable opd_diagnosis_table;
	
	
	//the content of each consultation, investigation, prescription or bill panel is uploaded here from the database and also for viewing
	public ArrayList<String> complaints;
	public ArrayList<TestOrderItem> testOrders;
	private JTextField visitStatusField;
	private ClinicManagementInterface cm_interface ;
	
	public static ArrayList<HospitalCharge> charge_list;
	static HospitalManagementInterface hmi;
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OPDConsultation frame = new OPDConsultation(new PatientVisit());
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	/**
	 * Create the frame.
	 * @throws Exception 
	 */
	public OPDConsultation(PatientVisit pVisit) throws Exception{
		setAlwaysOnTop(true);
		setTitle("Elite HMS - Consultation Screen         ");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(300, 100, 829, 621);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		cm_interface = InterfaceGenerator.getClinicManagementInterface();
		hmi =  InterfaceGenerator.getHospitalManagementInterface();
		charge_list = hmi.getAllCharges(); 
		
			
		JLabel label = new JLabel("Invoice No.");
		label.setBounds(471, 50, 65, 19);
		contentPane.add(label);
		
		invoiceField = new JTextField();
		invoiceField.setEditable(false);		
		
		invoiceField.setColumns(10);		
		invoiceField.setBounds(543, 49, 90, 19);
		contentPane.add(invoiceField);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 172, 793, 365);
		contentPane.add(tabbedPane);
		
				
	
		
		
		JPanel consultation_tab = new JPanel();
		tabbedPane.addTab("Consultation ", null, consultation_tab, null);
		consultation_tab.setLayout(null);
		
		
		
		emrCardHolder = new JPanel();
		emrCardHolder.setBorder(new LineBorder(new Color(0, 0, 0)));
		emrCardHolder.setBounds(210, 11, 572, 309);
		consultation_tab.add(emrCardHolder);
		emrCardHolder.setLayout(emrLayout);
		
		JPanel complaints_panel = new JPanel();
		emrCardHolder.add(complaints_panel, "compt");
		complaints_panel.setLayout(null);
		
		JLabel lblPresentingComplaints = new JLabel("Presenting Complaints");
		lblPresentingComplaints.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblPresentingComplaints.setBounds(10, 0, 454, 31);
		complaints_panel.add(lblPresentingComplaints);
		
		complaintArea = new JTextArea();
		complaintArea.setWrapStyleWord(true);
		complaintArea.setLineWrap(true);
		complaintArea.setBounds(10, 36, 550, 260);
		complaints_panel.add(complaintArea);
		
		JPanel patient_history_panel = new JPanel();
		emrCardHolder.add(patient_history_panel, "hist");
		patient_history_panel.setLayout(null);
		
		historyTextArea = new JTextArea();
		historyTextArea.setWrapStyleWord(true);
		historyTextArea.setLineWrap(true);
		historyTextArea.setBounds(10, 34, 550, 262);
		patient_history_panel.add(historyTextArea);
		
		JLabel lblPatientPastHistory = new JLabel("Patient Past History");
		lblPatientPastHistory.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblPatientPastHistory.setBounds(10, 0, 353, 31);
		patient_history_panel.add(lblPatientPastHistory);
		
		JPanel physical_exam_panel = new JPanel();
		emrCardHolder.add(physical_exam_panel, "phyex");
		physical_exam_panel.setLayout(null);
		
		JLabel lblPhysicalExamination = new JLabel("Physical Examination / Review of Systems");
		lblPhysicalExamination.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblPhysicalExamination.setBounds(10, 0, 550, 36);
		physical_exam_panel.add(lblPhysicalExamination);
		
		physicalExamArea = new JTextArea();
		physicalExamArea.setWrapStyleWord(true);
		physicalExamArea.setLineWrap(true);
		physicalExamArea.setBounds(10, 47, 550, 249);
		physical_exam_panel.add(physicalExamArea);
		
		JPanel diagnosis_panel = new JPanel();
		emrCardHolder.add(diagnosis_panel, "diag");
		diagnosis_panel.setLayout(null);
		
		JLabel lblDiagnosis = new JLabel("Diagnosis");
		lblDiagnosis.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblDiagnosis.setBounds(10, 0, 520, 36);
		diagnosis_panel.add(lblDiagnosis);
		
		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(10, 47, 550, 193);
		diagnosis_panel.add(scrollPane_3);
		
		opd_diagnosis_table = new JTable();
		opd_diagnosis_table.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				
				try {
					updateTable3(cm_interface.getAllDiagnosis(invoiceField.getText()));
				} catch (RemoteException e) {	e.printStackTrace();	}
			}
		});
		scrollPane_3.setViewportView(opd_diagnosis_table);
		
		btnNewDiagnosis = new JButton("Add Diagnosis");
		btnNewDiagnosis.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnNewDiagnosis.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					Diagnosis dg = new Diagnosis(invoiceField.getText(), "", "", "opd");
					dg.setVisible(true);
				} catch (RemoteException e) {	e.printStackTrace();	}
				
			}
		});
		btnNewDiagnosis.setBounds(10, 262, 159, 23);
		diagnosis_panel.add(btnNewDiagnosis);
		
		JPanel physician_remark_panel = new JPanel();
		emrCardHolder.add(physician_remark_panel, "rems");
		physician_remark_panel.setLayout(null);
		
		JLabel lblRemarks = new JLabel("Treatment Plan ");
		lblRemarks.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblRemarks.setBounds(10, 0, 530, 37);
		physician_remark_panel.add(lblRemarks);
		
		treatmentPlanArea = new JTextArea();
		treatmentPlanArea.setWrapStyleWord(true);
		treatmentPlanArea.setLineWrap(true);
		treatmentPlanArea.setBounds(10, 48, 550, 248);
		physician_remark_panel.add(treatmentPlanArea);
		
		JButton btnPresentingComplaints = new JButton("Presenting Complaints");
		btnPresentingComplaints.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				emrLayout.show(emrCardHolder, "compt");
			}
		});
		btnPresentingComplaints.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnPresentingComplaints.setBounds(22, 38, 150, 23);
		consultation_tab.add(btnPresentingComplaints);
		
		JButton btnPastHistory = new JButton("Patient Past History");
		btnPastHistory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				emrLayout.show(emrCardHolder, "hist");
			}
		});
		btnPastHistory.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnPastHistory.setBounds(22, 72, 150, 23);
		consultation_tab.add(btnPastHistory);
		
		JButton btnPhysicalExam = new JButton("Physical Examination");
		btnPhysicalExam.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				emrLayout.show(emrCardHolder, "phyex");
			}
		});
		btnPhysicalExam.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnPhysicalExam.setBounds(22, 106, 150, 23);
		consultation_tab.add(btnPhysicalExam);
		
		JButton btnDiagnosis = new JButton("Diagnosis");
		btnDiagnosis.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				emrLayout.show(emrCardHolder, "diag");
			}
		});
		btnDiagnosis.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnDiagnosis.setBounds(22, 140, 150, 23);
		consultation_tab.add(btnDiagnosis);
		
		JButton btnTreatmentPlan = new JButton("Treatment Plan");
		btnTreatmentPlan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				emrLayout.show(emrCardHolder, "rems");
			}
		});
		btnTreatmentPlan.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnTreatmentPlan.setBounds(22, 174, 150, 23);
		consultation_tab.add(btnTreatmentPlan);
		for(int i=0; i<charge_list.size(); i++){
			//clinicChargesBox.addItem(charge_list.get(i).getChargeName());
		}
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-YYYY");
		String datee = sdf.format(new Date());
		
		JLabel lblPatientName = new JLabel("Patient Name");
		lblPatientName.setBounds(10, 81, 80, 19);
		contentPane.add(lblPatientName);
		lblPatientName.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		nameField = new JTextField();
		nameField.setBounds(100, 80, 241, 19);
		contentPane.add(nameField);
		nameField.setEditable(false);
		nameField.setColumns(10);
		
				
		JLabel lblFileNumber = new JLabel("Hospital Number");
		lblFileNumber.setBounds(10, 50, 90, 19);
		contentPane.add(lblFileNumber);
		lblFileNumber.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		hospNoField = new JTextField();
		hospNoField.setBounds(100, 50, 126, 20);
		contentPane.add(hospNoField);
		hospNoField.setEditable(false);
		hospNoField.setColumns(10);
		
		JLabel lblSex = new JLabel("Gender");
		lblSex.setBounds(10, 112, 80, 19);
		contentPane.add(lblSex);
		lblSex.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblSex.setHorizontalAlignment(SwingConstants.LEFT);
		
		sexField = new JTextField();
		sexField.setBounds(100, 110, 57, 19);
		contentPane.add(sexField);
		sexField.setEditable(false);
		sexField.setColumns(10);
		
		JLabel lblAge = new JLabel("Age");
		lblAge.setBounds(10, 142, 55, 19);
		contentPane.add(lblAge);
		lblAge.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblAge.setHorizontalAlignment(SwingConstants.LEFT);
		
		AgeField = new JTextField();
		AgeField.setBounds(100, 141, 65, 19);
		contentPane.add(AgeField);
		AgeField.setEditable(false);
		AgeField.setColumns(10);
		try {
			Date dob = new SimpleDateFormat("yyyy-MM-dd").parse(pVisit.getDob());
			AgeField.setText(calculateAge(dob));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		JLabel lblDoctor = new JLabel("Doctor");
		lblDoctor.setBounds(471, 112, 80, 19);
		contentPane.add(lblDoctor);
		lblDoctor.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		docField = new JTextField();
		docField.setBounds(543, 111, 173, 19);
		contentPane.add(docField);
		docField.setEditable(false);
		docField.setColumns(10);
		
		JLabel lblDatetime = new JLabel("Date");
		lblDatetime.setBounds(471, 81, 80, 19);
		contentPane.add(lblDatetime);
		lblDatetime.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		dateTimeField = new JTextField();
		dateTimeField.setBounds(543, 81, 126, 20);
		contentPane.add(dateTimeField);
		dateTimeField.setEditable(false);
		dateTimeField.setColumns(10);
		
		JLabel lblVisitTy = new JLabel("Visit Status");
		lblVisitTy.setBounds(471, 142, 65, 19);
		contentPane.add(lblVisitTy);
		lblVisitTy.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		visitStatusField = new JTextField();
		visitStatusField.setBounds(543, 141, 90, 19);
		contentPane.add(visitStatusField);
		visitStatusField.setEditable(false);
		visitStatusField.setColumns(10);
		
		JButton button_3 = new JButton("Close");
		button_3.setBounds(677, 548, 96, 23);
		contentPane.add(button_3);
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				OPDConsultation.opd_diagnosis_table.requestFocus();
			}
		});
		button_3.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ConsultationNote cNote = new ConsultationNote();
					cNote.setInvoiceNo(pVisit.getInvoiceNo());
					cNote.setComplaints(complaintArea.getText());
					cNote.setPatientHistory(historyTextArea.getText());
					cNote.setPhysicalExam(physicalExamArea.getText());
					cNote.setTreatmentPlan(treatmentPlanArea.getText());
					
					cm_interface.updateConsultationNote(cNote);
					showMessage("Success!");
					
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		btnSave.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnSave.setBounds(479, 548, 89, 23);
		contentPane.add(btnSave);
		
		JButton btnPreview = new JButton("Preview");
		btnPreview.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnPreview.setBounds(578, 548, 89, 23);
		contentPane.add(btnPreview);	
		
		JLabel lblNewLabel = new JLabel("Consultation");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel.setBounds(10, 11, 793, 32);
		contentPane.add(lblNewLabel);
		
		updateScreen(pVisit);
		
		if (pVisit.getEmrSratus().equalsIgnoreCase("closed")) {
			btnSave.setEnabled(false);
			btnNewDiagnosis.setEnabled(false);
		}
	
	}
	
	//client method to update the opd diagnosis table
	public void updateTable3(ArrayList<DiseeaseIndex> list){
			Object[][] data = new Object[list.size()][4];
			for(int i = 0; i<list.size(); i++){
				data[i][0] = (i+1);
				data[i][1] = list.get(i).getDiseaseCode();
				data[i][2] = list.get(i).getDiseaseCondition();
				data[i][3] = list.get(i).getDiagnosisType();
			}
			
			Object[] columnNames = {"S/No", "Disease Code", "Disease Condtion", "Diagnosis Type"};
			DefaultTableModel model = new DefaultTableModel(data, columnNames);
			opd_diagnosis_table.setModel(model);
			opd_diagnosis_table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			opd_diagnosis_table.getColumnModel().getColumn(0).setPreferredWidth(60);
			opd_diagnosis_table.getColumnModel().getColumn(1).setPreferredWidth(100);
			opd_diagnosis_table.getColumnModel().getColumn(2).setPreferredWidth(500);
			opd_diagnosis_table.getColumnModel().getColumn(3).setPreferredWidth(120);

		}
			
	
	//client method to update procedure table  
	public void updateProcedureTable(ArrayList<BookedProcedure> list){
		Object[][] data = new Object[list.size()][8];
		for(int i = 0; i<list.size(); i++){
			data[i][0] = (i+1);
			data[i][1] = list.get(i).getProcedure();
			data[i][2] = list.get(i).getCptCode();
			data[i][3] = list.get(i).getTheatre();
			data[i][4] = list.get(i).getDate();
			data[i][5] = list.get(i).getTime();
			data[i][6] = list.get(i).getBookedBy();
			data[i][7] = list.get(i).getProcedureState();
			
		}
		
		Object[] columnNames = {"S/No", "Procedure", "CPT Code", "Theatre", "Date", "Time", "Booked by", "State of surgery"};
		DefaultTableModel model = new DefaultTableModel(data, columnNames);
	}
	
	
	//client method to update the Screen with information
		public void updateScreen(PatientVisit pVisit){
			try{
				ConsultationNote note = cm_interface.getConsultationNote(pVisit.getInvoiceNo());
				
					invoiceField.setEditable(false);
					invoiceField.setText(pVisit.getInvoiceNo());				
					hospNoField.setText(pVisit.getHospitalNo());
					nameField.setText(pVisit.getSurname()+" " + pVisit.getOthernames());
				
					sexField.setText(pVisit.getGender());
				
					
					docField.setText(pVisit.getDoctor());
					
					visitStatusField.setText(pVisit.getStatus());
					dateTimeField.setText(pVisit.getDate());
					
					if(pVisit.getStatus().equalsIgnoreCase("discharged")){
						
						}
			
					complaintArea.setText(note.getComplaints());
					treatmentPlanArea.setText(note.getTreatmentPlan());
					historyTextArea.setText(note.getPatientHistory());
					physicalExamArea.setText(note.getPhysicalExam());
					
					updateTable3(cm_interface.getAllDiagnosis(invoiceField.getText()));					
									
					updateProcedureTable(cm_interface.getBookedProcedures(invoiceField.getText()));					
					
			
			}catch(Exception ex){ ex.printStackTrace(); }
		}
		
		
		

	//a standard client method for displaying pop-up messages on frame that is setAlwaysOnTop(true)
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
