package com.ahms.clinicmanagement;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;

import com.ahms.api.ClinicManagementInterface;
import com.ahms.clientinterface.InterfaceGenerator;
import com.ahms.clinicmgt.entities.OperationNote;
import com.ahms.clinicmgt.entities.PatientVisit;
import com.toedter.calendar.JDateChooser;
import java.awt.Dimension;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.UIManager;
import java.awt.Color;
import javax.swing.border.LineBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class OperationNoteEntry extends JFrame {

	private JPanel contentPane;
	private JTextField invoiceField;
	private JTextField drainField;
	private JTextField procedureNameField;
	private JTextField cptField;
	
	JTextArea findingsArea;
	private JTextField hospNoField;
	private JTextField patNameField;
	private JTextField genderField;
	private JTextField surgeonField;
	private JTextField asstSurgeonField;
	private JTextField anaesthetist;
	private JTextField scrubNurseField;
	
	CardLayout cl = new CardLayout();
	JPanel switchPanel;
	PatientVisit pvs = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OperationNoteEntry frame = new OperationNoteEntry("","","","");
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
	public OperationNoteEntry(String invoice_no, String emrStatus, String name_of_procedure, String procedure_code) {
		setResizable(false);
		setTitle("Elite HMS - Operation Note ");
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(310, 70, 746, 656);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		ClinicManagementInterface cm_interface = InterfaceGenerator.getClinicManagementInterface();
		try {
			pvs =cm_interface.getPatientVisit(invoice_no);
		} catch (RemoteException e2) {
			e2.printStackTrace();
		}
		
		JLabel lblInvoiceNo = new JLabel(" Invoice No");
		lblInvoiceNo.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblInvoiceNo.setBounds(20, 59, 90, 20);
		contentPane.add(lblInvoiceNo);
		
		invoiceField = new JTextField();
		invoiceField.setEditable(false);
		invoiceField.setText(invoice_no);
		invoiceField.setColumns(10);
		invoiceField.setBounds(120, 59, 117, 20);
		contentPane.add(invoiceField);
		
		cptField = new JTextField();
		cptField.setEditable(false);
		cptField.setBounds(521, 105, 86, 20);
		cptField.setText(procedure_code);
		contentPane.add(cptField);
		cptField.setColumns(10);
		
		JLabel lblProcedure = new JLabel("Name of Procedure");
		lblProcedure.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblProcedure.setBounds(10, 105, 121, 20);
		contentPane.add(lblProcedure);
		
		procedureNameField = new JTextField();
		procedureNameField.setEditable(false);
		procedureNameField.setBounds(141, 105, 357, 20);
		procedureNameField.setText(name_of_procedure);
		contentPane.add(procedureNameField);
		procedureNameField.setColumns(10);
		
		 JLabel label_2 = new JLabel("Hospital Number");
		 label_2.setHorizontalAlignment(SwingConstants.LEFT);
		 label_2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		 label_2.setBounds(20, 29, 90, 19);
		 contentPane.add(label_2);
		 
		 hospNoField = new JTextField();
		 hospNoField.setText((String) null);
		 hospNoField.setEditable(false);
		 hospNoField.setText(pvs.getHospitalNo());
		 hospNoField.setColumns(10);
		 hospNoField.setBounds(120, 28, 117, 20);
		 contentPane.add(hospNoField);
		 
		 JLabel label_3 = new JLabel("Patient Name");
		 label_3.setHorizontalAlignment(SwingConstants.LEFT);
		 label_3.setFont(new Font("Tahoma", Font.PLAIN, 11));
		 label_3.setBounds(296, 29, 76, 19);
		 contentPane.add(label_3);
		 
		 patNameField = new JTextField();
		 patNameField.setText(pvs.getSurname()+" "+pvs.getOthernames());
		 patNameField.setEditable(false);
		 patNameField.setColumns(10);
		 patNameField.setBounds(382, 28, 226, 19);
		 contentPane.add(patNameField);
		 
		 JPanel panel = new JPanel();
		 panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		 panel.setBounds(10, 136, 711, 83);
		 contentPane.add(panel);
		 panel.setLayout(null);
		 
		 JLabel lblSurgeon = new JLabel("Surgeon");
		 lblSurgeon.setFont(new Font("Tahoma", Font.PLAIN, 11));
		 lblSurgeon.setBounds(10, 14, 85, 20);
		 panel.add(lblSurgeon);
		 
		 JLabel lblAssistantSurgeon = new JLabel("Assistant Surgeon");
		 lblAssistantSurgeon.setFont(new Font("Tahoma", Font.PLAIN, 11));
		 lblAssistantSurgeon.setBounds(363, 14, 95, 20);
		 panel.add(lblAssistantSurgeon);
		 
		 surgeonField = new JTextField();
		 surgeonField.setBounds(89, 14, 211, 20);
		 panel.add(surgeonField);
		 surgeonField.setColumns(10);
		 
		 asstSurgeonField = new JTextField();
		 asstSurgeonField.setColumns(10);
		 asstSurgeonField.setBounds(469, 14, 211, 20);
		 panel.add(asstSurgeonField);
		 
		 anaesthetist = new JTextField();
		 anaesthetist.setColumns(10);
		 anaesthetist.setBounds(89, 44, 211, 20);
		 panel.add(anaesthetist);
		 
		 scrubNurseField = new JTextField();
		 scrubNurseField.setColumns(10);
		 scrubNurseField.setBounds(469, 45, 211, 20);
		 panel.add(scrubNurseField);
		 
		 JLabel lblAnaesthetist = new JLabel("Anaesthetist");
		 lblAnaesthetist.setFont(new Font("Tahoma", Font.PLAIN, 11));
		 lblAnaesthetist.setBounds(10, 45, 85, 20);
		 panel.add(lblAnaesthetist);
		 
		 JLabel lblScrubNurse = new JLabel("Scrub Nurse");
		 lblScrubNurse.setFont(new Font("Tahoma", Font.PLAIN, 11));
		 lblScrubNurse.setBounds(363, 45, 85, 20);
		 panel.add(lblScrubNurse);
		 
		 switchPanel = new JPanel();
		 switchPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		 switchPanel.setBounds(10, 230, 710, 393);
		 switchPanel.setLayout(cl);
		 contentPane.add(switchPanel);
		 
		 JPanel panel_1 = new JPanel();
		 switchPanel.add(panel_1, "p1");
		 panel_1.setLayout(null);
		 
		 JPanel panel_2 = new JPanel();
		 switchPanel.add(panel_2, "p2");
		 panel_2.setLayout(null);
		 
		 JButton btnNext = new JButton("Next    >>");
		 btnNext.setFont(new Font("Tahoma", Font.PLAIN, 11));
		 btnNext.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent arg0) {
		 		cl.show(switchPanel, "p2");
		 	}
		 });
		 btnNext.setBounds(448, 357, 116, 23);
		 panel_1.add(btnNext);
		 
		 JLabel lblDiagnosis = new JLabel("DIAGNOSIS");
		 lblDiagnosis.setFont(new Font("Tahoma", Font.BOLD, 12));
		 lblDiagnosis.setBounds(10, 11, 85, 20);
		 panel_1.add(lblDiagnosis);
		 
		 JLabel lblPreop = new JLabel("Pre-Op");
		 lblPreop.setFont(new Font("Tahoma", Font.BOLD, 11));
		 lblPreop.setBounds(34, 42, 85, 20);
		 panel_1.add(lblPreop);
		 
		 JTextArea preOp_textArea = new JTextArea(); 
		 preOp_textArea.setWrapStyleWord(true);
		 preOp_textArea.setLineWrap(true);
		 preOp_textArea.setBounds(87, 40, 547, 41);
		 panel_1.add(preOp_textArea);
		 
		 JTextArea postOp_textArea = new JTextArea();
		 postOp_textArea.setWrapStyleWord(true);
		 postOp_textArea.setLineWrap(true);
		 postOp_textArea.setBounds(87, 92, 547, 41);
		 panel_1.add(postOp_textArea);
		 
		 JLabel lblPostop = new JLabel("Post-Op");
		 lblPostop.setFont(new Font("Tahoma", Font.BOLD, 11));
		 lblPostop.setBounds(34, 97, 85, 20);
		 panel_1.add(lblPostop);
		 
		 JLabel lblFindings = new JLabel("FINDINGS");
		 lblFindings.setBounds(10, 156, 195, 20);
		 panel_1.add(lblFindings);
		 lblFindings.setFont(new Font("Tahoma", Font.BOLD, 12));
		 
		 JScrollPane scrollPane_1 = new JScrollPane();
		 scrollPane_1.setBounds(87, 155, 608, 79);
		 panel_1.add(scrollPane_1);
		 
		 JTextArea findingsArea_1 = new JTextArea();  
		 scrollPane_1.setViewportView(findingsArea_1);
		 findingsArea_1.setWrapStyleWord(true);
		 findingsArea_1.setLineWrap(true);
		 
		 		 
		
		
		
		 
		 JLabel lblPostOperationOrderstreatment = new JLabel("POST OPERATION ORDERS/TREATMENT");
		 lblPostOperationOrderstreatment.setFont(new Font("Tahoma", Font.BOLD, 12));
		 lblPostOperationOrderstreatment.setBounds(10, 11, 286, 20);
		 panel_2.add(lblPostOperationOrderstreatment);
		 
		 JTextArea poTreatment_textArea = new JTextArea();
		 poTreatment_textArea.setWrapStyleWord(true);
		 poTreatment_textArea.setLineWrap(true);
		 poTreatment_textArea.setBounds(105, 42, 585, 83);
		 panel_2.add(poTreatment_textArea);
		 
		 JLabel lblSpecialNtes = new JLabel("SPECIAL NOTES");
		 lblSpecialNtes.setFont(new Font("Tahoma", Font.BOLD, 12));
		 lblSpecialNtes.setBounds(10, 136, 286, 20);
		 panel_2.add(lblSpecialNtes);
		 
		 JLabel label_6 = new JLabel("Drain");
		 label_6.setBounds(10, 167, 63, 20);
		 panel_2.add(label_6);
		 label_6.setFont(new Font("Tahoma", Font.PLAIN, 11));
		 
		 drainField = new JTextField();
		 drainField.setBounds(102, 167, 492, 20);
		 panel_2.add(drainField);
		 drainField.setColumns(10);
		 
		 
		 JLabel lblDetailSpecimen = new JLabel("Detail of Specimen");
		 lblDetailSpecimen.setBounds(10, 200, 100, 20);
		 panel_2.add(lblDetailSpecimen);
		 lblDetailSpecimen.setFont(new Font("Tahoma", Font.PLAIN, 11));
		 
		 JTextArea specimenDetailField = new JTextArea();
		 specimenDetailField.setWrapStyleWord(true);
		 specimenDetailField.setBounds(105, 198, 585, 38);
		 panel_2.add(specimenDetailField);
		 specimenDetailField.setLineWrap(true);
		
		 
		 JLabel lblHistologySent = new JLabel("Histology Sent ");
		 lblHistologySent.setBounds(10, 249, 85, 20);
		 panel_2.add(lblHistologySent);
		 lblHistologySent.setFont(new Font("Tahoma", Font.PLAIN, 11));
		 
		 JTextArea histologyField = new JTextArea();
		 histologyField.setWrapStyleWord(true);
		 histologyField.setBounds(105, 247, 585, 38);
		 panel_2.add(histologyField);
		 histologyField.setLineWrap(true);
		 							
		 
		 JLabel label_7 = new JLabel("Remarks");
		 label_7.setBounds(10, 298, 85, 20);
		 panel_2.add(label_7);
		 label_7.setFont(new Font("Tahoma", Font.PLAIN, 11));
		 
		 JTextArea remarksField = new JTextArea();
		 remarksField.setWrapStyleWord(true);
		 remarksField.setBounds(104, 296, 586, 43);
		 panel_2.add(remarksField);
		 remarksField.setLineWrap(true);
		 
		 
		 JLabel lblProcedures = new JLabel("PROCEDURES");
		 lblProcedures.setBounds(10, 259, 85, 20);
		 panel_1.add(lblProcedures);
		 lblProcedures.setFont(new Font("Tahoma", Font.BOLD, 12));
		 
		 JScrollPane scrollPane = new JScrollPane();
		 scrollPane.setBounds(100, 258, 595, 79);
		 panel_1.add(scrollPane);
		 
		 JTextArea procedureArea = new JTextArea();
		 scrollPane.setViewportView(procedureArea);
		 procedureArea.setWrapStyleWord(true);
		 procedureArea.setLineWrap(true);			
		 
		 
		 JPanel panel_3 = new JPanel();
		 panel_3.setBounds(11, 11, 710, 83);
		 contentPane.add(panel_3);
		 panel_3.setLayout(null);
		 
		 JLabel label_4 = new JLabel("Gender");
		 label_4.setBounds(284, 46, 35, 14);
		 panel_3.add(label_4);
		 label_4.setHorizontalAlignment(SwingConstants.LEFT);
		 label_4.setFont(new Font("Tahoma", Font.PLAIN, 11));
		 
		 genderField = new JTextField();
		 genderField.setBounds(372, 43, 43, 19);
		 panel_3.add(genderField);
		 genderField.setText(pvs.getGender());
		 genderField.setEditable(false);
		 genderField.setColumns(10);
		
		
		JButton buttonSave = new JButton("Save");
		buttonSave.setBounds(322, 357, 116, 23);
		panel_2.add(buttonSave);
		buttonSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(invoiceField.getText().length()<1){
					showMessage("Enter a valid invoice number!");
				} else{					
					
					try {
						String a = findingsArea_1.getText();						
						String b = histologyField.getText();								
						String c = procedureArea.getText(); 
						
						String d = drainField.getText(); 
						String e = remarksField.getText();
						String f = specimenDetailField.getText();
						String g = surgeonField.getText();
						String h = asstSurgeonField.getText();
						String i = anaesthetist.getText();
						String j = scrubNurseField.getText(); 
						String k = preOp_textArea.getText();
						String l = postOp_textArea.getText();
						String m = poTreatment_textArea.getText();
						
						OperationNote opn = new OperationNote();
						opn.setInvoiceNo(invoiceField.getText());
						opn.setCPTCode(cptField.getText());
						opn.setProcedureName(procedureNameField.getText());
						opn.setFindings(a);
						opn.setHistologySent(b);
						opn.setDetailsOfSpecimen(f);
						opn.setProcedure(c);
						opn.setDrain(d);
						opn.setRemarks(e);							
						opn.setSurgeon(g);
						opn.setAsstSurgeon(h);
						opn.setAnaesthetist(i);
						opn.setScrubNurse(j);
						opn.setPreOpDiagnosis(k);
						opn.setPostOpDiagnosis(l);
						opn.setPostOpTreatment(m);
						
						
						cm_interface.saveOperationNotes(opn);						
						showMessage("Saved Succesfully!");
						
						buttonSave.setEnabled(false);
						
					} catch (RemoteException e1) {		e1.printStackTrace();			}
					
				}
			}
		});
		buttonSave.setFont(new Font("Tahoma", Font.PLAIN, 11));	
		
		 JButton btnPrint = new JButton("Print");
		 btnPrint.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
		 		ArrayList<String> params = new ArrayList<String>();
		 		params.add(patNameField.getText());
		 		params.add(genderField.getText());
		 		params.add(hospNoField.getText());
		 		ProcedureNoteShowFrame frame = new ProcedureNoteShowFrame(invoice_no, procedure_code, params);
		 		frame.setVisible(true);
		 	}
		 });
		 
		 btnPrint.setFont(new Font("Tahoma", Font.PLAIN, 11));
		 btnPrint.setBounds(448, 357, 116, 23);
		 panel_2.add(btnPrint);
		
		JButton button_3 = new JButton("Close");
		button_3.setBounds(574, 357, 116, 23);
		panel_2.add(button_3);
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		button_3.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		JButton button_1 = new JButton("<<    Back");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cl.show(switchPanel, "p1");
			}
		});
		button_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		button_1.setBounds(181, 357, 116, 23);
		panel_2.add(button_1);
		
		
		try {			
			 OperationNote on = cm_interface.getOperationNote(invoiceField.getText(), cptField.getText());
			 drainField.setText(on.getDrain());
			 specimenDetailField.setText(on.getDetailsOfSpecimen());
			 histologyField.setText(on.getHistologySent());	
			 remarksField.setText(on.getRemarks());			
			 
			 surgeonField.setText(on.getSurgeon());
			 asstSurgeonField.setText(on.getAsstSurgeon());
			 anaesthetist.setText(on.getAnaesthetist());
			 scrubNurseField.setText(on.getScrubNurse()); 
			 preOp_textArea.setText(on.getPreOpDiagnosis());
			 postOp_textArea.setText(on.getPostOpDiagnosis());
			 procedureArea.setText(on.getProcedure());
			 findingsArea_1.setText(on.getFindings());
			 
			 JButton btnCancel = new JButton("Cancel");
			 btnCancel.addActionListener(new ActionListener() {
			 	public void actionPerformed(ActionEvent arg0) {
			 		dispose();
			 	}
			 });
			 btnCancel.setFont(new Font("Tahoma", Font.PLAIN, 11));
			 btnCancel.setBounds(574, 357, 107, 23);
			 panel_1.add(btnCancel);
			 poTreatment_textArea.setText(on.getPostOpTreatment());			 
			
		} catch (RemoteException e) {	e.printStackTrace();}
		
		if (emrStatus.equalsIgnoreCase("closed")) {
			buttonSave.setEnabled(false);
		}
		
		
	}
	
	
	
	//a standard client method for displaying popup messages on frame that is setAlwaysOnTop(true)
	public static void showMessage(String message){
		final JDialog dialog = new JDialog();
		dialog.setAlwaysOnTop(true);
		JOptionPane.showMessageDialog(dialog, message);
	}
}
