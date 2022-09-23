package com.ahms.clinicmanagement;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.ahms.api.ClinicManagementInterface;
import com.ahms.clientinterface.InterfaceGenerator;
import com.ahms.clinicmgt.entities.DischargeSummary;
import com.ahms.clinicmgt.entities.InpatientAdmission;
import com.ahms.clinicmgt.entities.PatientVisit;
import com.ahms.hmgt.entities.UserCard;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class DischargeFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField hospNoField;
	private JTextField genderField;
	private JTextField patNameField;
	private JTextField invoiceNoField;
	private JTextField dateFIeld;
	private JTextField physicianField;
	private PatientVisit pv = null;
	private InpatientAdmission adms = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DischargeFrame frame = new DischargeFrame("", "", new UserCard());
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
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public DischargeFrame(String invoice_no, String bed_no, UserCard user) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				InpatientVisitScreen.updateIPList();
			}
		});
		setAlwaysOnTop(true);
		setTitle("Elite HMS - Patient Discharge Summary ");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(400, 130, 637, 550);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		ClinicManagementInterface cm_interface = InterfaceGenerator.getClinicManagementInterface();
		
		try {
			pv = cm_interface.getPatientVisit(invoice_no);
			adms = cm_interface.getAdmissionSlip(invoice_no);
			
			
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		JLabel label = new JLabel("Hospital Number");
		label.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label.setBounds(10, 54, 90, 19);
		contentPane.add(label);
		
		hospNoField = new JTextField();
		hospNoField.setEditable(false);
		hospNoField.setText(pv.getHospitalNo());
		hospNoField.setColumns(10);
		hospNoField.setBounds(96, 53, 126, 20);
		contentPane.add(hospNoField);
		
		JLabel label_1 = new JLabel("Gender");
		label_1.setHorizontalAlignment(SwingConstants.LEFT);
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label_1.setBounds(307, 83, 55, 19);
		contentPane.add(label_1);
		
		genderField = new JTextField();
		genderField.setEditable(false);
		genderField.setText(pv.getGender());
		genderField.setColumns(10);
		genderField.setBounds(393, 82, 61, 19);
		contentPane.add(genderField);
		
		JLabel label_2 = new JLabel("Patient Name");
		label_2.setHorizontalAlignment(SwingConstants.LEFT);
		label_2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label_2.setBounds(307, 53, 76, 19);
		contentPane.add(label_2);
		
		patNameField = new JTextField();
		patNameField.setEditable(false);
		patNameField.setText(pv.getSurname()+" "+pv.getOthernames());
		patNameField.setColumns(10);
		patNameField.setBounds(393, 52, 200, 19);
		contentPane.add(patNameField);
		
		JLabel label_3 = new JLabel("Invoice No.");
		label_3.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label_3.setBounds(10, 85, 76, 19);
		contentPane.add(label_3);
		
		invoiceNoField = new JTextField();
		invoiceNoField.setEditable(false);
		invoiceNoField.setColumns(10);
		invoiceNoField.setText(invoice_no);
		invoiceNoField.setBounds(96, 84, 126, 19);
		contentPane.add(invoiceNoField);
		
		JLabel lblDischargeDate = new JLabel("Discharge Date");
		lblDischargeDate.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblDischargeDate.setBounds(10, 130, 76, 19);
		contentPane.add(lblDischargeDate);
		
		dateFIeld = new JTextField();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
		dateFIeld.setText(sdf.format(new Date()));
		dateFIeld.setEditable(false);
		dateFIeld.setColumns(10);
		dateFIeld.setBounds(102, 129, 120, 20);
		contentPane.add(dateFIeld);
		
		JLabel label_5 = new JLabel("Discharge Mode");
		label_5.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label_5.setBounds(10, 160, 82, 19);
		contentPane.add(label_5);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"--Select", "Alive", "Dead"}));
		comboBox.setBounds(102, 160, 120, 19);
		contentPane.add(comboBox);
		
		JLabel label_6 = new JLabel("Discharge Note");
		label_6.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label_6.setBounds(10, 201, 81, 19);
		contentPane.add(label_6);
		
		JTextArea textArea = new JTextArea();
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		textArea.setBounds(105, 201, 498, 210);
		contentPane.add(textArea);
		
		JLabel lblDischargeBy = new JLabel("Discharge by");
		lblDischargeBy.setBounds(10, 435, 82, 19);
		contentPane.add(lblDischargeBy);
		
		physicianField = new JTextField();
		physicianField.setEditable(false);
		physicianField.setColumns(10);
		physicianField.setBounds(102, 434, 260, 20);
		physicianField.setText(user.getFullNames());
		contentPane.add(physicianField);
		
		JButton button = new JButton("Discharge Patient");
		button.setForeground(new Color(255, 255, 255));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (pv.getClinic().equalsIgnoreCase("IPD")) {
					if(adms.getAdmissionStatus().equalsIgnoreCase("discharged")){ 
						showMessage("Patient has been discharged already!");
					}else if(comboBox.getSelectedItem().toString().equalsIgnoreCase("select")){
						showMessage("Select Mode of Discharge!");
					}else if(textArea.getText().length()<0){
						showMessage("Enter Text For Discharge Note!");
					}else if(invoiceNoField.getText().length()<1){
						showMessage("Invalid Invoice Number!");
					}else {
						DischargeSummary ds = new DischargeSummary(comboBox.getSelectedItem().toString(), textArea.getText(),"","",physicianField.getText());
						try {
							JFrame jFrame = new JFrame();
							jFrame.setAlwaysOnTop(true);
							int confirmed = JOptionPane.showConfirmDialog(jFrame, 
							        "Are you sure you want to dischage this patient?", "Confirm Patient Discharge ",
							        JOptionPane.YES_NO_OPTION);

							    if (confirmed == JOptionPane.YES_OPTION) {
							    	
						    		cm_interface.dischargePatient(invoice_no, bed_no, ds);
						    		pv = cm_interface.getPatientVisit(invoice_no);
									adms = cm_interface.getAdmissionSlip(invoice_no);
									
									showMessage("Discharge Successful!"); 
									
									button.setEnabled(false);
									button.setBackground(Color.white);
									
							    }							
						
						}catch (RemoteException e) {						
							e.printStackTrace();
						}
						 
					}
				}else {
					DischargeSummary ds = new DischargeSummary(comboBox.getSelectedItem().toString(), textArea.getText(),"","",physicianField.getText());
					JFrame jFrame = new JFrame();
					jFrame.setAlwaysOnTop(true);
					int confirmed = JOptionPane.showConfirmDialog(jFrame, 
					        "Are you sure you want to dischage this patient?", "Confirm Patient Discharge ",
					        JOptionPane.YES_NO_OPTION);

				    if (confirmed == JOptionPane.YES_OPTION) {
				    	try {
							cm_interface.dischargeOutpatient(invoice_no, ds);
							showMessage("Discharge Successful!"); 
							button.setEnabled(false);
							button.setBackground(Color.white);
						
						} catch (RemoteException e) {						
							e.printStackTrace();
						}
				    	
				    }
										
				}
				
			}
		});
		button.setEnabled(true);
		button.setBackground(new Color(0, 100, 0));
		button.setBounds(182, 477, 152, 23);
		contentPane.add(button);
		
		
		JButton button_1 = new JButton("End Consultation");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(pv.getStatus().equalsIgnoreCase("active")) {
					showMessage("Patient visit is still active.");
				}else {
					JFrame jFrame = new JFrame();
					jFrame.setAlwaysOnTop(true);
					int confirmed = JOptionPane.showConfirmDialog(jFrame, 
					        "Proceed to end consultation?", "End Consultation ",
					        JOptionPane.YES_NO_OPTION);

				    if (confirmed == JOptionPane.YES_OPTION) {
				    	try {
							cm_interface.endConsultation(invoice_no);
							showMessage("Consultation closed successfully!"); 
							button_1.setEnabled(false);
							button_1.setBackground(Color.white);
						
						} catch (RemoteException e1) {						
							e1.printStackTrace();
						}
				    	
				    }
				}
				
			}
		});
		button_1.setForeground(new Color(255, 255, 255));
		button_1.setEnabled(true);
		button_1.setBackground(new Color(0, 100, 0));
		button_1.setBounds(344, 477, 153, 23);
		contentPane.add(button_1);
		
		JButton btnNewButton = new JButton("Close");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				dispose();
			}
		});
		btnNewButton.setBounds(507, 477, 89, 23);
		contentPane.add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("Discharge Patient");
		lblNewLabel.setBounds(0, 11, 621, 20);
		contentPane.add(lblNewLabel);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		if (pv.getEmrSratus().equalsIgnoreCase("closed")) {
			button_1.setEnabled(false);
			button_1.setBackground(Color.white);
			
		}
		if (!pv.getStatus().equalsIgnoreCase("active")) {
			
			button.setEnabled(false);
			button.setBackground(Color.white);
			try {
				DischargeSummary dSummary = cm_interface.getDishargeSummary(invoice_no);
				textArea.setText(dSummary.getDischargeNote());
				comboBox.setSelectedItem(dSummary.getDischargeMOde());
				physicianField.setText(dSummary.getPhysician());
				dateFIeld.setText(dSummary.getDate());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	
	//a standard client method for displaying popup messages on frame that is setAlwaysOnTop(true)
	public static void showMessage(String message){
		final JDialog dialog = new JDialog();
		dialog.setAlwaysOnTop(true);
		JOptionPane.showMessageDialog(dialog, message);
	}
}
