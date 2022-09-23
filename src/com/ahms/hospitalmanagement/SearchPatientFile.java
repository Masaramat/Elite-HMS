package com.ahms.hospitalmanagement;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JDialog;

import com.ahms.api.HospitalManagementInterface;
import com.ahms.clientinterface.InterfaceGenerator;
import com.ahms.clinicmanagement.OPDVisit;
import com.ahms.hmgt.entities.PatientBiodata;
import com.ahms.hmgt.entities.UserCard;
import com.toedter.calendar.JDateChooser;
import java.awt.Dimension;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class SearchPatientFile extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTable table;
	private JButton btnBookAppt;
	private JButton newVisitButton;

	ArrayList<PatientBiodata> patlist = new ArrayList<>();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SearchPatientFile frame = new SearchPatientFile(new UserCard(), "new");
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
	public SearchPatientFile(UserCard user, String source) {
		setAlwaysOnTop(true);
		setTitle("Elite HMS - Search Patient File ");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(400, 150, 640, 483);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		final HospitalManagementInterface hm_interface = InterfaceGenerator.getHospitalManagementInterface();
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.setBounds(10, 52, 604, 82);
		contentPane.add(panel);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Patient Name", "Hospital Number"}));
		comboBox.setBounds(79, 11, 181, 20);
		panel.add(comboBox);
		
		JLabel label = new JLabel("Search by: ");
		label.setBounds(10, 11, 71, 20);
		panel.add(label);
		
		textField = new JTextField();		
		textField.addKeyListener(new KeyAdapter() {
			
			@Override
			public void keyReleased(KeyEvent arg0) {
				try {
					if(textField.getText().length()<1) {
						patlist.clear();
						updateSearchTable(patlist);
					}else {
						patlist = hm_interface.getPatient(textField.getText(), comboBox.getSelectedItem().toString());
						updateSearchTable(patlist);
					}					
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
		});
		textField.setColumns(10);
		textField.setBounds(359, 11, 161, 20);
		panel.add(textField);
		
		
		
		JLabel label_1 = new JLabel("From");
		label_1.setBounds(10, 42, 36, 22);
		panel.add(label_1);
		
		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setPreferredSize(new Dimension(40, 20));
		dateChooser.setDateFormatString("yyyy-MM-dd");
		dateChooser.setBounds(79, 42, 124, 21);
		dateChooser.setDate(new Date());
		panel.add(dateChooser);
		
		JLabel label_2 = new JLabel("To");
		label_2.setHorizontalAlignment(SwingConstants.LEFT);
		label_2.setBounds(255, 42, 37, 21);
		panel.add(label_2);
		
		JDateChooser dateChooser_1 = new JDateChooser();
		dateChooser_1.setPreferredSize(new Dimension(40, 20));
		dateChooser_1.setDateFormatString("yyyy-MM-dd");
		dateChooser_1.setBounds(290, 43, 124, 21);
		dateChooser_1.setDate(new Date());
		panel.add(dateChooser_1);
		
		JLabel label_3 = new JLabel("Enter Text");
		label_3.setHorizontalAlignment(SwingConstants.LEFT);
		label_3.setBounds(290, 11, 59, 20);
		panel.add(label_3);
		
		JButton goBtn = new JButton(">>>");
		goBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				patlist.clear();
				try {
					
					java.util.Date date1 = sdf.parse(((JTextField)dateChooser.getDateEditor().getUiComponent()).getText());
					java.util.Date date2 = sdf.parse(((JTextField)dateChooser_1.getDateEditor().getUiComponent()).getText());
					
					patlist = hm_interface.getPatient(date1, date2);
					updateSearchTable(patlist);
					
					
				} catch (ParseException e) { 	e.printStackTrace(); 		
				} catch (RemoteException e) {
					
				}
			}
		});
		goBtn.setFont(new Font("Tahoma", Font.PLAIN, 11));
		goBtn.setBounds(461, 42, 59, 23);
		panel.add(goBtn);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 145, 604, 246);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JButton btnClose = new JButton("Close");
		btnClose.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				System.gc();
			}
		});
		btnClose.setBounds(525, 410, 89, 23);
		contentPane.add(btnClose);
		
		JButton btnPreview = new JButton("Preview");
		btnPreview.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnPreview.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int xy = table.getSelectedRow();
				if(xy<0){
					showMessage("No item selected.");
				}else{
					String hosp_no = (String) table.getValueAt(xy, 0);
					PatientBiodataPrintFrame view = new PatientBiodataPrintFrame(hosp_no);
					view.setVisible(true);
					
				}
			}
		});
		btnPreview.setBounds(426, 410, 89, 23);
		contentPane.add(btnPreview);
		
		btnBookAppt = new JButton("New Appointment");
		btnBookAppt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					if (table.getSelectedRow()<0) {
						showMessage("Select patient from table.");
					}else {
						if(source.equalsIgnoreCase("new")) {
							OPDAppointment frame = new OPDAppointment(user, patlist.get(table.getSelectedRow()), "search");
							frame.setVisible(true);
							dispose();
						}else {						
							OPDAppointment.updatePatientInfo(patlist.get(table.getSelectedRow()));
							dispose();
						}
						
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		btnBookAppt.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnBookAppt.setBounds(191, 410, 126, 23);
		contentPane.add(btnBookAppt);
		
		newVisitButton = new JButton("New Visit");
		newVisitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (table.getSelectedRow()<0) {
						showMessage("Select patient from table.");
					}else {
						if(source.equalsIgnoreCase("new")) {
							OPDVisit frame = new OPDVisit(user, patlist.get(table.getSelectedRow()), "search");
							frame.setVisible(true);
							dispose();
							System.gc();
						}else {						
							OPDVisit.updatePatientInfo(patlist.get(table.getSelectedRow()));
							dispose();
							System.gc();
						}
						
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		newVisitButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		newVisitButton.setBounds(327, 410, 89, 23);
		contentPane.add(newVisitButton);
		
		JLabel lblNewLabel = new JLabel("Search Patient File");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel.setBounds(0, 11, 624, 30);
		contentPane.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("Edit Biodata ");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					if (table.getSelectedRow()<0) {
						showMessage("Select patient from table.");
					}else {
						MasterPatientIndex mpx = new MasterPatientIndex(user, "edit", patlist.get(table.getSelectedRow()));
						mpx.setVisible(true);
						dispose();
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnNewButton.setBounds(79, 410, 102, 23);
		contentPane.add(btnNewButton);
		
		
		if (source.equalsIgnoreCase("appt")) {
			newVisitButton.setEnabled(false);
		}else if (source.equalsIgnoreCase("visit")) {
			btnBookAppt.setEnabled(false);
		}else {
			
		}
		
		
		
		addWindowListener(new WindowAdapter() {
			  public void windowClosing(WindowEvent e) {			   
			    	System.gc();			     
			  }
			});
		
	}
	
	//client method to update table
	public void updateSearchTable(ArrayList<PatientBiodata> patdata){
		
		Object[][] data = new Object[patdata.size()][10];
		for(int i=0; i<patdata.size(); i++){ 
			data[i][0] = patdata.get(i).getHospital_no();
			data[i][1] = patdata.get(i).getSurname();
			data[i][2] = patdata.get(i).getFirstname();
			data[i][3] = patdata.get(i).getAddress1();
			data[i][4] = patdata.get(i).getRegDate();				
			data[i][5] = patdata.get(i).getDob();
			data[i][6] = patdata.get(i).getGender();				
			data[i][7] = patdata.get(i).getPhoneMobile();
			data[i][8] = patdata.get(i).getNok_surname();					
			data[i][9] = patdata.get(i).getNok_relationship();
			
						
		}
		
		Object[] columnNames = {"Hosp. No", "Surname", "Othernames", "Home Address", "Reg. Date", "Date of Birth", "Gender", "Phone", "Next of Kin", "Relationship" };
		
		DefaultTableModel model = new DefaultTableModel(data, columnNames);
		table.setModel(model);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getColumnModel().getColumn(0).setPreferredWidth(100);
		table.getColumnModel().getColumn(1).setPreferredWidth(130);
		table.getColumnModel().getColumn(2).setPreferredWidth(180);
		table.getColumnModel().getColumn(3).setPreferredWidth(200);
		table.getColumnModel().getColumn(4).setPreferredWidth(100);
		table.getColumnModel().getColumn(5).setPreferredWidth(100);
		table.getColumnModel().getColumn(6).setPreferredWidth(70);
		table.getColumnModel().getColumn(7).setPreferredWidth(100);
		table.getColumnModel().getColumn(8).setPreferredWidth(100);
		table.getColumnModel().getColumn(9).setPreferredWidth(100);
		repaint();
		revalidate();
	}
			
	public static void showMessage(String message){
		final JDialog dialog = new JDialog();
		dialog.setAlwaysOnTop(true);
		JOptionPane.showMessageDialog(dialog, message);
	}
}
