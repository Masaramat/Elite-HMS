package com.ahms.hospitalmanagement;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.ahms.clinicmanagement.InpatientVisitScreen;
import com.ahms.clinicmanagement.OPDVisit;
import com.ahms.clinicmanagement.OutpatientVisitScreen;
import com.ahms.clinicmanagement.ProcedureEntry;
import com.ahms.clinicmgt.entities.PatientVisit;
import com.ahms.hmgt.entities.PatientBiodata;
import com.ahms.hmgt.entities.UserCard;
import com.ahms.laboratorymanagement.DeliveredRequestFrame;
import com.ahms.laboratorymanagement.TestConfiguration;
import com.ahms.laboratorymanagement.TestNameEntry;
import com.ahms.laboratorymanagement.LabRequestFrame;
import com.ahms.pharmacymanagement.CurrentStock;
import com.ahms.pharmacymanagement.MedicineEntry;
import com.ahms.pharmacymanagement.MedicinePurchase;
import com.ahms.pharmacymanagement.MedicineWithdrawal;
import com.ahms.pharmacymanagement.PrescriptionOrder;
import com.ahms.reports.FamilyRegistrationReport;
import com.ahms.reports.GeneralDepositReport;
import com.ahms.reports.InpatientAdmissionReport;
import com.ahms.reports.LabTestReportFrame;
import com.ahms.reports.OutpatientVisitReport;
import com.ahms.reports.PatientRegistrationReport;
import com.ahms.reports.PharmacyPurchaseReport;
import com.ahms.reports.PharmacyWithdrawalReport;
import com.ahms.reports.ProcedureReportFrame;

import javax.swing.JMenuBar;
import java.awt.Color;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;

@SuppressWarnings("unused")
public class EHMSMainPage extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField usernameField;
	private JTextField roleField;
	
	static Timer timer_1 = new Timer();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EHMSMainPage frame = new EHMSMainPage(new UserCard());
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
	public EHMSMainPage(UserCard user) {
		setTitle("Elite Hospital Solutions ");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(10, 10, 1380, 730);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnPatient = new JMenu("Patient");
		menuBar.add(mnPatient);
		
		JMenuItem mntmNewFamilyCard = new JMenuItem("Open New Family File");
		mntmNewFamilyCard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FamilyCardIndex frame = new FamilyCardIndex(user);
				frame.setVisible(true);
			}
		});
		//mnPatient.add(mntmNewFamilyCard);
		
		JMenuItem mntmNewPatientFile = new JMenuItem("Open New Patient File");
		mntmNewPatientFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MasterPatientIndex frameIndex = new MasterPatientIndex(user, "", new PatientBiodata());
				frameIndex.setVisible(true);
			}
		});
		//mnPatient.add(mntmNewPatientFile);
		
		
		JMenuItem mntmSearchPatientFile = new JMenuItem("Search Patient File");
		mntmSearchPatientFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SearchPatientFile searchPatientFile = new SearchPatientFile(user, "new");
				searchPatientFile.setVisible(true);
			}
		});
		
		JMenuItem mntmSearchFamilyCard = new JMenuItem("Search Family File");
		mntmSearchFamilyCard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FamilyMemberSearch frame = new FamilyMemberSearch(user, "new");
				frame.setVisible(true);
			}
		});
		
		//mnPatient.add(mntmSearchFamilyCard);
		//mnPatient.add(mntmSearchPatientFile);
		
		
		JMenuItem mntmAppointment = new JMenuItem("Patient Appointment / Schedule");
		mntmAppointment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					OPDAppointment appointment = new OPDAppointment(user, new PatientBiodata(), "new");
					appointment.setVisible(true);
				} catch (RemoteException e1) {
					
					e1.printStackTrace();
				}
			}
		});
		
		//mnPatient.add(mntmAppointment);
		
		JMenuItem mntmNewClinicVisit = new JMenuItem(" Patient Visit");
		mntmNewClinicVisit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				OPDVisit visit = new OPDVisit(user, new PatientBiodata(), "new");
				visit.setVisible(true);
			}
		});
		
		//mnPatient.add(mntmNewClinicVisit);
		
		JMenu mnClinic = new JMenu("Clinic");
		menuBar.add(mnClinic);
		
		JMenuItem mntmOutpatientVisit = new JMenuItem("Outpatient Clinic");
		mntmOutpatientVisit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				OutpatientVisitScreen ovFrame = new OutpatientVisitScreen(user);
				ovFrame.setVisible(true);
			}
		});
		//mnClinic.add(mntmOutpatientVisit);
		
		JMenuItem mntmAdmissionsRegister = new JMenuItem("Inpatient Clinic");
		mntmAdmissionsRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InpatientVisitScreen ivFrame = new InpatientVisitScreen(user);
				ivFrame.setVisible(true);
			}
		});
		//mnClinic.add(mntmAdmissionsRegister);
		
		JMenu mnPharmacy = new JMenu("Pharmacy");
		menuBar.add(mnPharmacy);
		
		JMenuItem mntmPrescriptionOrder = new JMenuItem("Patient Prescription Order");
		mntmPrescriptionOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PrescriptionOrder poFrame = new PrescriptionOrder();
				poFrame.setVisible(true);
			}
		});
		//mnPharmacy.add(mntmPrescriptionOrder);
		
		JMenuItem mntmMedicinePurchase = new JMenuItem("Pharmacy Stock Purchase");
		mntmMedicinePurchase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					MedicinePurchase mpFrame = new MedicinePurchase();
					mpFrame.setVisible(true);
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
				
			}
			
		});
		
		//mnPharmacy.add(mntmMedicinePurchase);
		
		JMenuItem mntmMedicineWithdrawal = new JMenuItem("Pharmacy Stock Withdrawal");
		mntmMedicineWithdrawal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					MedicineWithdrawal mwFrame = new MedicineWithdrawal();
					mwFrame.setVisible(true);
				} catch (RemoteException e1) {					
					e1.printStackTrace();
				}
			}
		});		
		//mnPharmacy.add(mntmMedicineWithdrawal);
		
		JMenuItem mntmPharmStockControl = new JMenuItem("Pharmacy Stock Control");
		mntmPharmStockControl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CurrentStock frame = new CurrentStock();
				frame.setVisible(true);
			}
		});
		//mnPharmacy.add(mntmPharmStockControl);
		
		JMenu mnLaboratory = new JMenu("Laboratory");
		menuBar.add(mnLaboratory);
		
		JMenuItem mntmLabRequest = new JMenuItem("Laboratory Request");
		mntmLabRequest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LabRequestFrame toFrame = new LabRequestFrame(user, "");
				toFrame.setVisible(true);
			}
		});
		//mnLaboratory.add(mntmLabRequest);
		
		//mnLaboratory.add(mntmEnterTestResult);
		
		JMenuItem mntmDeliverTestResult = new JMenuItem("Search Delivered Test(s)");
		mntmDeliverTestResult.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DeliveredRequestFrame toFrame = new DeliveredRequestFrame();
				toFrame.setVisible(true);
			}
		});
		//mnLaboratory.add(mntmDeliverTestResult);
		
		
		
		JMenu mnInvoice = new JMenu("Billing");
		menuBar.add(mnInvoice);
		
		JMenuItem mntmOutpatientInvoice = new JMenuItem("Outpatient Invoice");
		mntmOutpatientInvoice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				OPDInvoice invoice = new OPDInvoice();
				invoice.setVisible(true);
			}
		});
		//mnInvoice.add(mntmOutpatientInvoice);
		
		JMenuItem mntmInpatientInvoice = new JMenuItem("Inpatient Invoice");
		mntmInpatientInvoice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				IPDInvoice invoice = new IPDInvoice();
				invoice.setVisible(true);
			}
		});
		//mnInvoice.add(mntmInpatientInvoice);
		
		JMenu mnAdmin = new JMenu("Admin");
		menuBar.add(mnAdmin);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Doctor Management");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ManageDoctorsFrame frame = new ManageDoctorsFrame();
				frame.setVisible(true);
			}
		});
		//mnAdmin.add(mntmNewMenuItem);
		
		JMenuItem mntmWardManagement = new JMenuItem("Ward Management");
		mntmWardManagement.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WardManagement frame;
				try {
					frame = new WardManagement();
					frame.setVisible(true);
				} catch (RemoteException e1) {
					
					e1.printStackTrace();
				}
							}
		});
		//mnAdmin.add(mntmWardManagement);
		
		JMenuItem mntmUserManagement = new JMenuItem("User Management");
		mntmUserManagement.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserEntryScreen frame;
				try {
					frame = new UserEntryScreen();
					frame.setVisible(true);
				} catch (Exception e1) {
					
					e1.printStackTrace();
				}
							}
		});
		//mnAdmin.add(mntmUserManagement);
		
		JMenu mnReport = new JMenu("Report");
		menuBar.add(mnReport);
		
		JMenuItem mntmFamilyRegReport = new JMenuItem("Family Registration Report");
		mntmFamilyRegReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FamilyRegistrationReport frame = new FamilyRegistrationReport();
				frame.setVisible(true);
			}
		});
		//mnReport.add(mntmFamilyRegReport);
		
		JMenuItem mntmPatientRegReport = new JMenuItem("Patient Registration Report");
		mntmPatientRegReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PatientRegistrationReport frame = new PatientRegistrationReport();
				frame.setVisible(true);
			}
		});
		//mnReport.add(mntmPatientRegReport);
		
		JMenuItem mntmOutpatienVisitReport = new JMenuItem("Outpatient Visit Report");
		mntmOutpatienVisitReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				OutpatientVisitReport frame = new OutpatientVisitReport();
				frame.setVisible(true);
			}
		});
		//mnReport.add(mntmOutpatienVisitReport);
		
		JMenuItem mntmInpatientAdmReport = new JMenuItem("Inpatient Admission Report");
		mntmInpatientAdmReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InpatientAdmissionReport frame = new InpatientAdmissionReport();
				frame.setVisible(true);
			}
		});
		//mnReport.add(mntmInpatientAdmReport);
		
		JMenuItem mntmProcedureReport = new JMenuItem("Procedures Report");
		mntmProcedureReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ProcedureReportFrame frame = new ProcedureReportFrame();
				frame.setVisible(true);
			}
		});
		//mnReport.add(mntmProcedureReport);
		
		JMenuItem mntmLabInvestigationReport = new JMenuItem("Lab Investigation Report");
		mntmLabInvestigationReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LabTestReportFrame frame = new LabTestReportFrame();
				frame.setVisible(true);
			}
		});
		//mnReport.add(mntmLabInvestigationReport);
		
		JMenuItem mntmPharmPurchaseReport = new JMenuItem("Pharmacy Purchase Report");
		mntmPharmPurchaseReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PharmacyPurchaseReport frame = new PharmacyPurchaseReport();
				frame.setVisible(true);
			}
		});
		//mnReport.add(mntmPharmPurchaseReport);
		
		JMenuItem mntmStockWithdrawalRep = new JMenuItem("Pharmacy Stock Withdrawal Report");
		mntmStockWithdrawalRep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PharmacyWithdrawalReport frame = new PharmacyWithdrawalReport();
				frame.setVisible(true);
			}
		});
		//mnReport.add(mntmStockWithdrawalRep);
		
		JMenuItem mntmCashRegister = new JMenuItem("Daily Cash Register");
		mntmCashRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GeneralDepositReport frame = new GeneralDepositReport();
				frame.setVisible(true);
			}
		});
		//mnReport.add(mntmCashRegister);
		
		JMenu mnSetup = new JMenu("Setup");
		menuBar.add(mnSetup);
		
		JMenuItem mntmNewHospitalCharge = new JMenuItem("Hospital Charges Setup");
		mntmNewHospitalCharge.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					NewClinicCharges frame  = new NewClinicCharges("");
					frame.setVisible(true);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		//mnSetup.add(mntmNewHospitalCharge);
		
		JMenuItem mntmNewMedecineName = new JMenuItem("Medicine Setup");
		mntmNewMedecineName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MedicineEntry frame  = new MedicineEntry();
				frame.setVisible(true);
			}
		});		
		//mnSetup.add(mntmNewMedecineName);
		
		JMenuItem mntmAddNewTest = new JMenuItem("Laboratory Test Setup");
		mntmAddNewTest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					TestNameEntry frame  = new TestNameEntry();
					frame.setVisible(true);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		//mnSetup.add(mntmAddNewTest);
		
		JMenuItem mntmConfigureTestParameters = new JMenuItem("Configure Laboratory Test");
		mntmConfigureTestParameters.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					TestConfiguration frame  = new TestConfiguration();
					frame.setVisible(true);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		//mnSetup.add(mntmConfigureTestParameters);
		
		JMenuItem mntmSurgicalProcedure = new JMenuItem("Surgical Procedure Setup");
		mntmSurgicalProcedure.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ProcedureEntry frame  = new ProcedureEntry("");
				frame.setVisible(true);
			}
		});
		//mnSetup.add(mntmSurgicalProcedure);
		
		JMenu mnUtility = new JMenu("Utility");
		menuBar.add(mnUtility);
		
		JMenuItem mntmChangePassword = new JMenuItem("Change Password");
		mntmChangePassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ChangePasswordFrame frame = new ChangePasswordFrame(user);
				frame.setVisible(true);
			}
		});
		mnUtility.add(mntmChangePassword);
		
		JMenuItem mntmLogout = new JMenuItem("Logout");
		mntmLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame jFrame = new JFrame();
				jFrame.setAlwaysOnTop(true);
				int confirmed = JOptionPane.showConfirmDialog(jFrame, 
				        "Are you sure you want to logout of the system?", "Confirm Logout",
				        JOptionPane.YES_NO_OPTION);

				    if (confirmed == JOptionPane.YES_OPTION) {
				    	
				      System.exit(0);
				    }
			}
		});
		mnUtility.add(mntmLogout);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		roleField = new JTextField();
		roleField.setEditable(false);
		roleField.setFont(new Font("Tahoma", Font.BOLD, 13));
		roleField.setForeground(new Color(79, 40, 157));
		roleField.setColumns(10);
		roleField.setBounds(128, 607, 256, 26);
		contentPane.add(roleField);
		
		roleField.setText(user.getRole());
		
		usernameField = new JTextField();
		usernameField.setEditable(false);
		usernameField.setFont(new Font("Tahoma", Font.BOLD, 13));
		usernameField.setForeground(new Color(79, 40, 157));
		usernameField.setBounds(128, 575, 256, 23);
		contentPane.add(usernameField);
		usernameField.setColumns(10);
		usernameField.setText(user.getUsername());
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Username:");
		lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_1_1_1.setBounds(50, 576, 78, 22);
		contentPane.add(lblNewLabel_1_1_1);
		
		JLabel lblNewLabel_1 = new JLabel("Role:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_1.setBounds(50, 609, 125, 22);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(EHMSMainPage.class.getResource("/resources/bk2.jpg")));
		lblNewLabel.setBounds(0, 54, 1364, 666);
		contentPane.add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(236, 244, 246));
		panel.setBounds(0, 0, 1364, 59);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JButton btnPatientReg = new JButton("");
		btnPatientReg.setBorder(null);
		btnPatientReg.setToolTipText("New Patient Registration");
		btnPatientReg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MasterPatientIndex frame = new MasterPatientIndex(user, "", new PatientBiodata());
				frame.setVisible(true);
			}
		});
		btnPatientReg.setIcon(new ImageIcon(EHMSMainPage.class.getResource("/resources/patient_1.png")));
		btnPatientReg.setBounds(20, 11, 47, 29);
		btnPatientReg.setBackground(null);
		panel.add(btnPatientReg);
		
		JButton btnFamilyReg = new JButton("");
		btnFamilyReg.setBorder(null);
		btnFamilyReg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FamilyCardIndex frm = new FamilyCardIndex(user);
				frm.setVisible(true);
			}
		});
		btnFamilyReg.setToolTipText("New Family Registration");
		btnFamilyReg.setIcon(new ImageIcon(EHMSMainPage.class.getResource("/resources/family_1.png")));
		btnFamilyReg.setBounds(100, 11, 47, 29);
		btnFamilyReg.setBackground(null);
		panel.add(btnFamilyReg);
		
		JButton btnAppointment = new JButton("");
		btnAppointment.setBorder(null);
		btnAppointment.setToolTipText("Patient Appointment");
		btnAppointment.setIcon(new ImageIcon(EHMSMainPage.class.getResource("/resources/Edit-icon_1.png")));
		btnAppointment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					OPDAppointment frm = new OPDAppointment(user, new PatientBiodata(), "");
					frm.setVisible(true);
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnAppointment.setBounds(180, 11, 47, 29);
		btnAppointment.setBackground(null);
		panel.add(btnAppointment);
		
		JButton btnVisit = new JButton("");
		btnVisit.setBackground(null);
		btnVisit.setBorder(null);
		btnVisit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				OPDVisit frm = new OPDVisit(user, new PatientBiodata(), "");
				frm.setVisible(true);
			}
		});
		btnVisit.setToolTipText("Patient Visit");
		btnVisit.setIcon(new ImageIcon(EHMSMainPage.class.getResource("/resources/visit_1.png")));
		btnVisit.setBounds(260, 11, 47, 29);
		panel.add(btnVisit);
		
		JButton btnOpd = new JButton("");
		btnOpd.setBackground(null);
		btnOpd.setBorder(null);
		btnOpd.setToolTipText("Outpatient Clinic");
		btnOpd.setIcon(new ImageIcon(EHMSMainPage.class.getResource("/resources/out_patient_1.png")));
		btnOpd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				OutpatientVisitScreen vs = new OutpatientVisitScreen(user);
				vs.setVisible(true);
			}
		});
		btnOpd.setBounds(340, 11, 47, 29);
		panel.add(btnOpd);
		
		JButton btnIpd = new JButton("");
		btnIpd.setBackground(null);
		btnIpd.setBorder(null);
		btnIpd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InpatientVisitScreen iv = new InpatientVisitScreen(user);
				iv.setVisible(true);
			}
		});
		btnIpd.setToolTipText("Inpatient Clinic");
		btnIpd.setIcon(new ImageIcon(EHMSMainPage.class.getResource("/resources/ipd_1.png")));
		btnIpd.setBounds(420, 11, 47, 29);
		panel.add(btnIpd);
		
		JButton btnInvoice = new JButton("");
		btnInvoice.setBackground(null);
		btnInvoice.setBorder(null);
		btnInvoice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		btnInvoice.setToolTipText("Invoice");
		btnInvoice.setIcon(new ImageIcon(EHMSMainPage.class.getResource("/resources/invoice_1.png")));
		btnInvoice.setBounds(500, 11, 47, 29);
		panel.add(btnInvoice);
		
		JButton btnSearchClient = new JButton("");
		btnSearchClient.setBackground(null);
		btnSearchClient.setBorder(null);
		btnSearchClient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SearchPatientFile pf = new SearchPatientFile(user, "");
				pf.setVisible(true);
			}
		});
		btnSearchClient.setToolTipText("Search Patient");
		btnSearchClient.setIcon(new ImageIcon(EHMSMainPage.class.getResource("/resources/browse_patient_1.png")));
		btnSearchClient.setBounds(580, 11, 47, 29);
		panel.add(btnSearchClient);
		
		JButton btnUserAdministration = new JButton("");
		btnUserAdministration.setBackground(null);
		btnUserAdministration.setBorder(null);
		btnUserAdministration.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserEntryScreen userEntryScreen = new UserEntryScreen();
				userEntryScreen.setVisible(true);
			}
		});
		btnUserAdministration.setToolTipText("User Management");
		btnUserAdministration.setIcon(new ImageIcon(EHMSMainPage.class.getResource("/resources/usermgt.png")));
		btnUserAdministration.setBounds(660, 11, 47, 29);
		panel.add(btnUserAdministration);
		
		JButton btnPrescription = new JButton("");
		btnPrescription.setBackground(null);
		btnPrescription.setBorder(null);
		btnPrescription.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PrescriptionOrder pOrder = new PrescriptionOrder();
				pOrder.setVisible(true);
			}
		});
		btnPrescription.setToolTipText("Prescription");
		btnPrescription.setIcon(new ImageIcon(EHMSMainPage.class.getResource("/resources/prescription_1.png")));
		btnPrescription.setBounds(740, 11, 47, 29);	
		
		panel.add(btnPrescription);
		
		JButton btnBackup = new JButton("");
		btnBackup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BackupFrame frame;
				try {
					frame = new BackupFrame();
					frame.setVisible(true);
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		btnBackup.setBackground(null);
		btnBackup.setBorder(null);
		btnBackup.setToolTipText("Backup");
		btnBackup.setIcon(new ImageIcon(EHMSMainPage.class.getResource("/resources/backup_1.png")));
		btnBackup.setBounds(820, 11, 53, 29);
		panel.add(btnBackup);
		
		JFrame jFrame = new JFrame();
		jFrame.setAlwaysOnTop(true);
		
		JButton btnLogout = new JButton("");
		btnLogout.setBackground(null);
		btnLogout.setBorder(null);
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int confirmed = JOptionPane.showConfirmDialog(jFrame, 
				        "Are you sure you want to logout of the system?", "Confirm Logout",
				        JOptionPane.YES_NO_OPTION);

				    if (confirmed == JOptionPane.YES_OPTION) {
				    	System.exit(0);
				    }
			}
		});
		btnLogout.setToolTipText("Logout");
		btnLogout.setIcon(new ImageIcon(EHMSMainPage.class.getResource("/resources/logout_2.png")));
		btnLogout.setBounds(900, 11, 53, 29);
		panel.add(btnLogout);
		
		addWindowListener(new WindowAdapter() {
			 public void windowClosing(WindowEvent evt) {
			        int resp = JOptionPane.showConfirmDialog(jFrame, "Are you sure you want to exit?",
			            "Exit?", JOptionPane.YES_NO_OPTION);

			        if (resp == JOptionPane.YES_OPTION) {
			        	System.exit(0);
			        } else {
			        	setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			        }
			    }
			});
		
		
		switch (user.getRole()) {
		case "physician":
			//patient module.
			mnPatient.add(mntmNewPatientFile);
			mnPatient.add(mntmNewFamilyCard);			
			mnPatient.add(mntmSearchPatientFile);
			mnPatient.add(mntmSearchFamilyCard);
			mnPatient.add(mntmAppointment);
			mnPatient.add(mntmNewClinicVisit);
			
			//clinic module
			mnClinic.add(mntmOutpatientVisit);
			mnClinic.add(mntmAdmissionsRegister);
			
			//invoice module
			mnInvoice.add(mntmOutpatientInvoice);
			mnInvoice.add(mntmInpatientInvoice);
			
			//report module
			mnReport.add(mntmFamilyRegReport);
			mnReport.add(mntmPatientRegReport);
			mnReport.add(mntmOutpatienVisitReport);
			mnReport.add(mntmInpatientAdmReport);
			mnReport.add(mntmProcedureReport);
			mnReport.add(mntmCashRegister);
			
			//setup module
			mnSetup.add(mntmNewHospitalCharge);
			mnSetup.add(mntmNewMedecineName);		
			mnSetup.add(mntmSurgicalProcedure);
			
			//menu icons
			btnUserAdministration.setEnabled(false);			
			btnBackup.setEnabled(false);
			
			
			
			break;
		case "nurse":
			//patient module.
			mnPatient.add(mntmNewPatientFile);
			mnPatient.add(mntmNewFamilyCard);			
			mnPatient.add(mntmSearchPatientFile);
			mnPatient.add(mntmSearchFamilyCard);
			mnPatient.add(mntmAppointment);
			mnPatient.add(mntmNewClinicVisit);
			
			//clinic module
			mnClinic.add(mntmOutpatientVisit);
			mnClinic.add(mntmAdmissionsRegister);
			
			//pharmacy module
			mnPharmacy.add(mntmPrescriptionOrder);
			
			//invoice module
			mnInvoice.add(mntmOutpatientInvoice);
			mnInvoice.add(mntmInpatientInvoice);
			
			//report module
			mnReport.add(mntmFamilyRegReport);
			mnReport.add(mntmPatientRegReport);
			mnReport.add(mntmOutpatienVisitReport);
			mnReport.add(mntmInpatientAdmReport);
			mnReport.add(mntmCashRegister);
			
			//setup module
			mnSetup.add(mntmNewHospitalCharge);
			
			//menu icons
			btnUserAdministration.setEnabled(false);			
			btnBackup.setEnabled(false);
			
			break;
		case "receptionist":
			//patient module.
			mnPatient.add(mntmNewPatientFile);
			mnPatient.add(mntmNewFamilyCard);			
			mnPatient.add(mntmSearchPatientFile);
			mnPatient.add(mntmSearchFamilyCard);
			mnPatient.add(mntmAppointment);
			mnPatient.add(mntmNewClinicVisit);
			
			//invoice module
			mnInvoice.add(mntmOutpatientInvoice);
			mnInvoice.add(mntmInpatientInvoice);
			
			//report module
			mnReport.add(mntmFamilyRegReport);
			mnReport.add(mntmPatientRegReport);
			mnReport.add(mntmOutpatienVisitReport);
			mnReport.add(mntmInpatientAdmReport);
			mnReport.add(mntmCashRegister);
			
			//setup module
			mnSetup.add(mntmNewHospitalCharge);
		
			btnOpd.setEnabled(false);
			btnIpd.setEnabled(false);
			btnUserAdministration.setEnabled(false);			
			btnBackup.setEnabled(false);
			btnPrescription.setEnabled(false);
			
			
			break;
		case "manager":
			//patient module.
			mnPatient.add(mntmNewPatientFile);
			mnPatient.add(mntmNewFamilyCard);			
			mnPatient.add(mntmSearchPatientFile);
			mnPatient.add(mntmSearchFamilyCard);
			mnPatient.add(mntmAppointment);
			mnPatient.add(mntmNewClinicVisit);
			
			//pharmacy module
			mnPharmacy.add(mntmPrescriptionOrder);
			mnPharmacy.add(mntmMedicinePurchase);
			mnPharmacy.add(mntmMedicineWithdrawal);
			mnPharmacy.add(mntmPharmStockControl);
			
			//invoice module
			mnInvoice.add(mntmOutpatientInvoice);
			mnInvoice.add(mntmInpatientInvoice);
			
			//admin module
			mnAdmin.add(mntmWardManagement);
			
			//report module
			mnReport.add(mntmFamilyRegReport);
			mnReport.add(mntmPatientRegReport);
			mnReport.add(mntmOutpatienVisitReport);
			mnReport.add(mntmInpatientAdmReport);
			mnReport.add(mntmProcedureReport);
			mnReport.add(mntmLabInvestigationReport);
			mnReport.add(mntmPharmPurchaseReport);
			mnReport.add(mntmStockWithdrawalRep);
			mnReport.add(mntmCashRegister);
			
			//setup module
			mnSetup.add(mntmNewHospitalCharge);
			mnSetup.add(mntmNewMedecineName);
			mnSetup.add(mntmSurgicalProcedure);
			
			//menu buttons
			btnUserAdministration.setEnabled(false);			
			btnBackup.setEnabled(false);
			
			
			break;
		case "lab scientist":
			
			//lab module
			mnLaboratory.add(mntmLabRequest);			
			mnLaboratory.add(mntmDeliverTestResult);			
			
			//report module
			mnReport.add(mntmFamilyRegReport);
			mnReport.add(mntmPatientRegReport);
			mnReport.add(mntmOutpatienVisitReport);
			mnReport.add(mntmInpatientAdmReport);
			
			mnReport.add(mntmLabInvestigationReport);
			
			mnReport.add(mntmCashRegister);
			
			//setup module			
			mnSetup.add(mntmAddNewTest);
			mnSetup.add(mntmConfigureTestParameters);
			
			//menu buttons
			btnPatientReg.setEnabled(false);
			btnFamilyReg.setEnabled(false);
			btnAppointment.setEnabled(false);
			btnVisit.setEnabled(false);
			btnOpd.setEnabled(false);
			btnIpd.setEnabled(false);
			btnInvoice.setEnabled(false);
			btnSearchClient.setEnabled(false);
			btnUserAdministration.setEnabled(false);			
			btnBackup.setEnabled(false);
			btnPrescription.setEnabled(false);
			
		
			
			break;
		case "pharmacist":
			//pharmacy module
			mnPharmacy.add(mntmPrescriptionOrder);
			mnPharmacy.add(mntmMedicinePurchase);
			mnPharmacy.add(mntmMedicineWithdrawal);
			mnPharmacy.add(mntmPharmStockControl);
			
			//report module
			mnReport.add(mntmFamilyRegReport);
			mnReport.add(mntmPatientRegReport);
			mnReport.add(mntmOutpatienVisitReport);
			mnReport.add(mntmInpatientAdmReport);
	
			mnReport.add(mntmPharmPurchaseReport);
			mnReport.add(mntmStockWithdrawalRep);
			mnReport.add(mntmCashRegister);
			
			//setup module
			mnSetup.add(mntmNewMedecineName);
			
			//menu buttons
			btnPatientReg.setEnabled(false);
			btnFamilyReg.setEnabled(false);
			btnAppointment.setEnabled(false);
			btnVisit.setEnabled(false);
			btnOpd.setEnabled(false);
			btnIpd.setEnabled(false);
			btnInvoice.setEnabled(false);
			btnSearchClient.setEnabled(false);
			btnUserAdministration.setEnabled(false);			
			btnBackup.setEnabled(false);
			
			
			
			break;
		case "super administrator":
			//patient module.
			mnPatient.add(mntmNewPatientFile);
			mnPatient.add(mntmNewFamilyCard);			
			mnPatient.add(mntmSearchPatientFile);
			mnPatient.add(mntmSearchFamilyCard);
			mnPatient.add(mntmAppointment);
			mnPatient.add(mntmNewClinicVisit);
			
			//clinic module
			mnClinic.add(mntmOutpatientVisit);
			mnClinic.add(mntmAdmissionsRegister);
			
			//pharmacy module
			mnPharmacy.add(mntmPrescriptionOrder);
			mnPharmacy.add(mntmMedicinePurchase);
			mnPharmacy.add(mntmMedicineWithdrawal);
			mnPharmacy.add(mntmPharmStockControl);
			
			//lab module
			mnLaboratory.add(mntmLabRequest);			
			mnLaboratory.add(mntmDeliverTestResult);
			
			
			//invoice module
			mnInvoice.add(mntmOutpatientInvoice);
			mnInvoice.add(mntmInpatientInvoice);
			
			//admin module
			mnAdmin.add(mntmWardManagement);
			mnAdmin.add(mntmNewMenuItem);
			mnAdmin.add(mntmUserManagement);
			
			
			//report module
			mnReport.add(mntmFamilyRegReport);
			mnReport.add(mntmPatientRegReport);
			mnReport.add(mntmOutpatienVisitReport);
			mnReport.add(mntmInpatientAdmReport);
			mnReport.add(mntmProcedureReport);
			mnReport.add(mntmLabInvestigationReport);
			mnReport.add(mntmPharmPurchaseReport);
			mnReport.add(mntmStockWithdrawalRep);
			mnReport.add(mntmCashRegister);
			
			//setup module
			mnSetup.add(mntmNewHospitalCharge);
			mnSetup.add(mntmNewMedecineName);
			mnSetup.add(mntmSurgicalProcedure);
			mnSetup.add(mntmAddNewTest);
			mnSetup.add(mntmConfigureTestParameters);
			
			break;
			

		default:
			break;
		}
		
		new GCTimTask().run();
		
	}
	
	static class GCTimTask extends TimerTask {
	    public void run() {
	        int delay = (7 * 60 * 1000);
	        timer_1.schedule(new GCTimTask(), delay);
	        System.gc();
	        
	    }
	}
}
