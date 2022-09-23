package com.ahms.clinicmanagement;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.EventQueue;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;
import javax.swing.JRadioButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import com.ahms.api.ClinicManagementInterface;
import com.ahms.clientinterface.InterfaceGenerator;
import com.ahms.clinicmgt.entities.PatientVisit;
import com.ahms.hmgt.entities.UserCard;
import com.ahms.hospitalmanagement.GeneratePatientBill;
import com.ahms.laboratorymanagement.TestOrderEntry;
import com.toedter.calendar.JDateChooser;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import org.apache.commons.collections.functors.IfClosure;

import java.awt.Color;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class OutpatientVisitScreen extends JFrame {

	private JPanel contentPane;
	private static JTextField textField;
	
	private ButtonGroup bgrp = new ButtonGroup(); 
	private static JTable table;
	
	private JButton searchButton;
	private JButton btnBookAdmission;
	private JDateChooser dateChooser;
	private JDateChooser dateChooser_1;
	private static JComboBox comboBox;
	private static ClinicManagementInterface cm_interface = InterfaceGenerator.getClinicManagementInterface();
	
	private CardLayout cLayout = new CardLayout();
	
	private static ArrayList<PatientVisit> visitList = new ArrayList<>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OutpatientVisitScreen frame = new OutpatientVisitScreen(new UserCard());
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
	public OutpatientVisitScreen(UserCard user) {
		setTitle("Elite HMS - Outpatient Register ");
		setResizable(false);
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(250, 100, 922, 639);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		ClinicManagementInterface cm_interface = InterfaceGenerator.getClinicManagementInterface();
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.setBounds(10, 45, 874, 105);
		contentPane.add(panel);
		
		textField = new JTextField();
		textField.setBounds(351, 42, 188, 20);
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				try {
					if(textField.getText().length()<1) {
						visitList.clear();
						updateVisitTable(visitList);
					}else {
						visitList = cm_interface.searchPatientVisit(textField.getText(), comboBox.getSelectedItem().toString(), "discharged");
						updateVisitTable(visitList);
					}					
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		panel.setLayout(null);
		panel.add(textField);
		textField.setColumns(10);
		
		JLabel label_1 = new JLabel("Enter Text");
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label_1.setBounds(287, 42, 61, 20);
		panel.add(label_1);
		
		comboBox = new JComboBox();
		comboBox.setFont(new Font("Tahoma", Font.PLAIN, 11));
		comboBox.setBounds(97, 42, 129, 20);
		panel.add(comboBox);
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Patient Name", "Hospital Number", "Invoice Number"}));
		
		
		
		JLabel label = new JLabel("Search By");
		label.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label.setBounds(16, 42, 71, 20);
		panel.add(label);
		
		JRadioButton rdbtnDischarged = new JRadioButton("Discharged Patients");
		rdbtnDischarged.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				toggleSearchComponents(true);
				visitList.clear();
				updateVisitTable(visitList);
			}
		});
		rdbtnDischarged.setBounds(147, 10, 156, 23);
		panel.add(rdbtnDischarged);
		bgrp.add(rdbtnDischarged);
		
		JRadioButton rdbtnActive = new JRadioButton("Active Patients");
		rdbtnActive.setSelected(true);
		rdbtnActive.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				toggleSearchComponents(false);
				
				try {
					visitList = cm_interface.searchPatientVisit("active");
					updateVisitTable(visitList);
				} catch (RemoteException ex) {	ex.printStackTrace();	}
			}
		});
		rdbtnActive.setBounds(16, 10, 129, 23);
		panel.add(rdbtnActive);
		bgrp.add(rdbtnActive);
		
		dateChooser = new JDateChooser();
		dateChooser.setBounds(97, 73, 107, 20);
		panel.add(dateChooser);
		dateChooser.getCalendarButton().setText(" ");
		dateChooser.setPreferredSize(new Dimension(40, 20));
		dateChooser.setDate(new Date());		
		dateChooser.setDateFormatString("yyyy-MM-dd");
		
		JLabel label_2 = new JLabel("From Date");
		label_2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label_2.setBounds(16, 73, 61, 20);
		panel.add(label_2);
		
		JLabel label_3 = new JLabel("To Date");
		label_3.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label_3.setBounds(287, 73, 57, 20);
		panel.add(label_3);
		
		dateChooser_1 = new JDateChooser();
		dateChooser_1.setBounds(351, 73, 107, 20);
		panel.add(dateChooser_1);
		dateChooser_1.setPreferredSize(new Dimension(40, 20));
		dateChooser_1.setDate(new Date());
		dateChooser_1.setDateFormatString("yyyy-MM-dd");
		
		searchButton = new JButton(">>>");
		searchButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		searchButton.setBounds(541, 73, 61, 20);
		panel.add(searchButton);
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String clinic = "";
				Date date1 = null;
				Date date2 = null;
				try{
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					date1 = sdf.parse(((JTextField)dateChooser.getDateEditor().getUiComponent()).getText());
					date2 = sdf.parse(((JTextField)dateChooser_1.getDateEditor().getUiComponent()).getText());		
				}catch(Exception ex){}				
				
				if(rdbtnActive.isSelected()){
					clinic = "active";
					
					
				}else if(rdbtnDischarged.isSelected()){
					clinic = "discharged";
				}
								
				try {
					visitList = cm_interface.getOutPatientVisit(clinic, date1, date2);
					updateVisitTable(visitList);
				} catch (RemoteException e) {	e.printStackTrace();	}
				
			}
		});
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 161, 885, 249);
		contentPane.add(scrollPane);
		
				
		JPopupMenu popupMenu = new JPopupMenu();
		
		JMenuItem mnItemNursing = new JMenuItem("Nursing Station ");
		mnItemNursing.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int row = table.getSelectedRow();								
				try {
					NursesFrame frame = new NursesFrame(user, visitList.get(row));
					frame.setVisible(true);
					//dispose();
				} catch (Exception e) {		e.printStackTrace();	}
			}
			});
		popupMenu.add(mnItemNursing);
		
		JMenuItem mnItem1 = new JMenuItem("Consultation Note ");
		mnItem1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int row = table.getSelectedRow();
							
				try {
					OPDConsultation frame = new OPDConsultation(visitList.get(row));
					frame.setVisible(true);
					//dispose();
				} catch (Exception e) {		e.printStackTrace();	}
			}
			});
		
		
		JMenuItem mnItem2 = new JMenuItem("Laboratory Investigation");
		mnItem2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int row = table.getSelectedRow();
							
				try {
					TestOrderEntry frame = new TestOrderEntry(visitList.get(row).getInvoiceNo(), visitList.get(row).getEmrSratus(), "opd");
					frame.setVisible(true);
					//dispose();
				} catch (Exception e) {		e.printStackTrace();	}
			}
			});
		popupMenu.add(mnItem2);
		
		JMenuItem mnItem3 = new JMenuItem("Procedure");
		mnItem3 .addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int row = table.getSelectedRow();
						
				try {
					ProcedureBooking frame = new ProcedureBooking(user, visitList.get(row).getInvoiceNo(), "");
					frame.setVisible(true);
					//dispose();
				} catch (Exception e) {		e.printStackTrace();	}
			}
			});
		
		
		JMenuItem mnItem4 = new JMenuItem("Prescription");
		mnItem4 .addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int row = table.getSelectedRow();
						
				try {
					PrescriptionOrderEntry frame = new PrescriptionOrderEntry(visitList.get(row).getInvoiceNo(), visitList.get(row).getEmrSratus(), "");
					frame.setVisible(true);
					//dispose();
				} catch (Exception e) {		e.printStackTrace();	}
			}
			});
		popupMenu.add(mnItem4);
		JMenuItem mnItem5 = new JMenuItem("Hospital Bill");
		mnItem5 .addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int row = table.getSelectedRow();		
				String nameString = (visitList.get(row).getSurname() +" " + (visitList.get(row).getOthernames()));
				try {
					GeneratePatientBill frame = new GeneratePatientBill("", visitList.get(row).getInvoiceNo(), nameString, visitList.get(row).getEmrSratus(), "");
					frame.setVisible(true);
					//dispose();
				} catch (Exception e) {		e.printStackTrace();	}
			}
			});

		popupMenu.add(mnItem5);
		
		JMenuItem mnItem7 = new JMenuItem("Admit Patient");
		mnItem7 .addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int row = table.getSelectedRow();
				
				
				try {
					IPDAdmissionScreen frame = new IPDAdmissionScreen(visitList.get(row).getInvoiceNo());
					frame.setVisible(true);
					//dispose();
				} catch (Exception e) {		e.printStackTrace();	}
			}
			});
		
		
		JMenuItem mnItem6 = new JMenuItem("Discharge Summary");
		mnItem6 .addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int row = table.getSelectedRow();	
				
				try {
					DischargeFrame frame = new DischargeFrame(visitList.get(row).getInvoiceNo(), "", user);
					frame.setVisible(true);
					//dispose();
				} catch (Exception e) {		e.printStackTrace();	}
			}
			});
		
		
		if (user.getRole().equalsIgnoreCase("physician")) {
			//consultation
			popupMenu.add(mnItem1);
			//procedure
			popupMenu.add(mnItem3);
			//Admit patient
			popupMenu.add(mnItem7);
			//discharge
			popupMenu.add(mnItem6);
		}
		
		
		table = new JTable();
		
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent evt) {
				Point p = evt.getPoint();
				int currentRow = table.rowAtPoint(p);
				table.setRowSelectionInterval(currentRow, currentRow);
				
				if (visitList.get(table.getSelectedRow()).getEmrSratus().equalsIgnoreCase("closed") 
						|| visitList.get(table.getSelectedRow()).getStatus().equalsIgnoreCase("discharged")) {
					btnBookAdmission.setEnabled(false);
				}else {
					btnBookAdmission.setEnabled(true);
				}
			}
		});
		table.setComponentPopupMenu(popupMenu);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(table);
		
		JPanel switch_panel = new JPanel();
		switch_panel.setBounds(10, 472, 874, 75);
		contentPane.add(switch_panel);
		switch_panel.setLayout(cLayout);
		
		JPanel doc_panel = new JPanel();
		switch_panel.add(doc_panel, "p_doc");
		doc_panel.setLayout(null);
		
		JButton btnDischargeSummary = new JButton("Discharge Summary");
		btnDischargeSummary.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				if(row<0) {
					showMessage("No item selected!");
				}else {
					try {
						DischargeFrame frame = new DischargeFrame(visitList.get(row).getInvoiceNo(), "", user);
						frame.setVisible(true);
						//dispose();
					} catch (Exception ex) {		ex.printStackTrace();	}
				}
				
				
			}
		});
		btnDischargeSummary.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnDischargeSummary.setBounds(722, 45, 142, 23);
		
		doc_panel.add(btnDischargeSummary);
		
		JButton btnHospitalBill = new JButton("Hospital Bill");
		btnHospitalBill.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnHospitalBill.setBounds(570, 45, 142, 23);
		btnHospitalBill .addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int row = table.getSelectedRow();
				if(row<0) {
					showMessage("No item selected!");
				}else {
					String nameString = (visitList.get(row).getSurname() +" " + (visitList.get(row).getOthernames()));
					
					try {
						GeneratePatientBill frame = new GeneratePatientBill("", visitList.get(row).getInvoiceNo(),  nameString, visitList.get(row).getEmrSratus(), "");
						frame.setVisible(true);
						//dispose();
					} catch (Exception e) {		e.printStackTrace();	}
				}
						
				
			}
			});
		doc_panel.add(btnHospitalBill);
		
		btnBookAdmission = new JButton("Book Admission");
		btnBookAdmission.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnBookAdmission.setBounds(418, 45, 142, 23);
		btnBookAdmission .addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int row = table.getSelectedRow();
				if(row<0) {
					showMessage("No item selected!");
				}else {
					String value = visitList.get(row).getInvoiceNo();
					
					try {
						IPDAdmissionScreen frame = new IPDAdmissionScreen(value);
						frame.setVisible(true);
						//dispose();
					} catch (Exception e) {		e.printStackTrace();	}
				}
				
				
			}
			});
		doc_panel.add(btnBookAdmission);
		
		JButton btnPrescription = new JButton("Prescription");
		btnPrescription.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnPrescription.setBounds(266, 45, 142, 23);
		btnPrescription .addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int row = table.getSelectedRow();
				if(row<0) {
					showMessage("No item selected!");
				}else {
					String value = visitList.get(row).getInvoiceNo();
					
					try {
						PrescriptionOrderEntry frame = new PrescriptionOrderEntry(value, visitList.get(row).getEmrSratus(), "");
						frame.setVisible(true);
						//dispose();
					} catch (Exception e) {		e.printStackTrace();	}
				}
				
			}
			});
		doc_panel.add(btnPrescription);
		
		JButton btnProcedure = new JButton("Procedure");
		btnProcedure.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnProcedure.setBounds(722, 11, 142, 23);
		btnProcedure .addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int row = table.getSelectedRow();
				if(row<0) {
					showMessage("No item selected!");
				}else {
					String value = visitList.get(row).getInvoiceNo();				
					try {
						ProcedureBooking frame = new ProcedureBooking(user, value, "");
						frame.setVisible(true);
						//dispose();
					} catch (Exception e) {		e.printStackTrace();	}
				}
				
			}
			});
		doc_panel.add(btnProcedure);
		
		JButton btnLabInvestigation = new JButton("Lab Investigation");
		btnLabInvestigation.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnLabInvestigation.setBounds(570, 11, 142, 23);
		btnLabInvestigation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int row = table.getSelectedRow();
				if(row < 0) {
					showMessage("No item selected.");
				}else {
					String value = visitList.get(row).getInvoiceNo();				
					try {
						TestOrderEntry frame = new TestOrderEntry(value, visitList.get(row).getEmrSratus(),"opd");
						frame.setVisible(true);
						//dispose();
					} catch (Exception e) {		e.printStackTrace();	}
					
				}
				
			}
			});
		doc_panel.add(btnLabInvestigation);
		
		JButton btnConsultation = new JButton("Consultation");
		btnConsultation.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnConsultation.setBounds(418, 11, 142, 23);
		btnConsultation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int row = table.getSelectedRow();
				if (row < 0) {
					showMessage("No item selected.");
				}else {
					try {
						OPDConsultation frame = new OPDConsultation(visitList.get(row));
						frame.setVisible(true);
						//dispose();
					} catch (Exception e) {		e.printStackTrace();	}
				}
							
				
			}
			});
		doc_panel.add(btnConsultation);
		
		JButton btnNursing = new JButton("Nursing");
		btnNursing.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnNursing.setBounds(266, 11, 142, 23);
		btnNursing.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int row = table.getSelectedRow();
				if(row<0) {
					showMessage("No item selected!");
				}else {
					try {
						NursesFrame frame = new NursesFrame(user, visitList.get(row));
						frame.setVisible(true);
						//dispose();
					} catch (Exception e) {		e.printStackTrace();	}
				}
				
			}
			});
		doc_panel.add(btnNursing);
		
		JPanel nurse_panel = new JPanel();
		switch_panel.add(nurse_panel, "p_nurse");
		nurse_panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Outpatient Register");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel.setBounds(10, 11, 874, 23);
		contentPane.add(lblNewLabel);
		
		JButton btnCloses = new JButton("Close");
		btnCloses.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnCloses.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				System.gc();
			}
			});
		btnCloses.setBounds(779, 570, 116, 23);
		contentPane.add(btnCloses);
		
		toggleSearchComponents(false);
		
		try {
			visitList = cm_interface.searchPatientVisit("active");
			updateVisitTable(visitList);
		} catch (RemoteException e) {
			e.printStackTrace();	
			}
		
		addWindowListener(new WindowAdapter() {
			  public void windowClosing(WindowEvent e) {			   
			    	System.gc();			     
			  }
			});
		
		if(user.getRole().equalsIgnoreCase("nurse")) {
			btnBookAdmission.setEnabled(false);
			btnConsultation.setEnabled(false);
			btnDischargeSummary.setEnabled(false);
			btnProcedure.setEnabled(false);
		}
		
		if (user.getRole().equalsIgnoreCase("physician") || user.getRole().equalsIgnoreCase("super administrator")) {
			popupMenu.add(mnItem1);
			popupMenu.add(mnItem3);
			popupMenu.add(mnItem7);
			popupMenu.add(mnItem6);
		}
	}
	
	
	
	
	
	
	public void toggleSearchComponents(boolean state) {
		textField.setEditable(state);
		searchButton.setEnabled(state);
		dateChooser.setEnabled(state);
		dateChooser_1.setEnabled(state); 
		comboBox.setEnabled(state);
	}
	
	public static void updateOPDList() {
		try {
			visitList = cm_interface.searchPatientVisit("active");
			updateVisitTable(visitList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	//client method to update the visit table
		public static void updateVisitTable(ArrayList<PatientVisit> patdata){
					
			Object[][] data = new Object[patdata.size()][9];
			for(int i=0; i<patdata.size(); i++){ 
				data[i][0] = (i+1);
				data[i][7] = patdata.get(i).getDate();
				data[i][1] = patdata.get(i).getInvoiceNo();
				data[i][2] = patdata.get(i).getHospitalNo();
				data[i][3] = patdata.get(i).getSurname();
				data[i][4] = patdata.get(i).getOthernames();			
				data[i][5] = patdata.get(i).getGender();			
				data[i][6] = patdata.get(i).getDoctor();			
				data[i][8] = patdata.get(i).getEmrSratus();
							
			}
			
			Object[] columnNames = {"S/No", "Invoice No", "Hospital No", "Surname", "Othernames",  "Gender",  "Doctor", "Date ", "Cons. Status" };
			
			DefaultTableModel model = new DefaultTableModel(data, columnNames);
			table.setModel(model);
			table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			table.getColumnModel().getColumn(0).setPreferredWidth(40);
			table.getColumnModel().getColumn(1).setPreferredWidth(90);
			table.getColumnModel().getColumn(2).setPreferredWidth(90);
			table.getColumnModel().getColumn(3).setPreferredWidth(130);
			table.getColumnModel().getColumn(4).setPreferredWidth(180);
			table.getColumnModel().getColumn(5).setPreferredWidth(60);
			table.getColumnModel().getColumn(6).setPreferredWidth(120);
			table.getColumnModel().getColumn(7).setPreferredWidth(90);
			table.getColumnModel().getColumn(8).setPreferredWidth(100);
			
		}
		
		//a standard client method for displaying popup messages on frame that is setAlwaysOnTop(true)
		public static void showMessage(String message){
			final JDialog dialog = new JDialog();
			dialog.setAlwaysOnTop(true);
			JOptionPane.showMessageDialog(dialog, message);
		}
				
		
		
}
