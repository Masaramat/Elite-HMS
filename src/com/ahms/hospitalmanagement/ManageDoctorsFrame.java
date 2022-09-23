package com.ahms.hospitalmanagement;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.ahms.api.HospitalManagementInterface;
import com.ahms.clientinterface.InterfaceGenerator;
import com.ahms.hmgt.entities.Doctor;
import com.ahms.hmgt.entities.IPInvoice;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.ListSelectionModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ManageDoctorsFrame extends JFrame {

	private JPanel contentPane;
	private JTextField nameField;
	private JTextField qualificationField;
	private JTextField specialtyField;
	private JTable table;
	private JTextField idField;
	private JButton btnUpdate;
	private JButton btnSave;
	
	private JRadioButton rdbtnActive;
	private JRadioButton rdbtnInactive;
	
	private ButtonGroup buttonGroup = new ButtonGroup();
	
	private ArrayList<Doctor> doctor_list = new ArrayList<>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ManageDoctorsFrame frame = new ManageDoctorsFrame();
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
	public ManageDoctorsFrame() {
		setTitle("Elite HMS - Manage Doctors");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(400, 120, 560, 475);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		HospitalManagementInterface hManagementInterface = InterfaceGenerator.getHospitalManagementInterface();
		
		JLabel lblNewLabel = new JLabel("Manage Doctors");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel.setBounds(0, 11, 487, 23);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Doctor Name");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_1.setBounds(10, 80, 70, 23);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Qualification");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_1_1.setBounds(10, 115, 70, 23);
		contentPane.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("Specialty");
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_1_2.setBounds(10, 149, 70, 23);
		contentPane.add(lblNewLabel_1_2);
		
		JLabel lblNewLabel_2 = new JLabel("Status");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_2.setBounds(10, 183, 70, 23);
		contentPane.add(lblNewLabel_2);
		
		nameField = new JTextField();
		nameField.setBounds(90, 80, 254, 23);
		contentPane.add(nameField);
		nameField.setColumns(10);
		
		qualificationField = new JTextField();
		qualificationField.setColumns(10);
		qualificationField.setBounds(90, 116, 254, 23);
		contentPane.add(qualificationField);
		
		specialtyField = new JTextField();
		specialtyField.setColumns(10);
		specialtyField.setBounds(90, 150, 254, 23);
		contentPane.add(specialtyField);
		
		rdbtnActive = new JRadioButton("Active");
		rdbtnActive.setFont(new Font("Tahoma", Font.PLAIN, 11));
		rdbtnActive.setBounds(90, 183, 89, 23);
		contentPane.add(rdbtnActive);
		rdbtnActive.setSelected(true);
		
		rdbtnInactive = new JRadioButton("Inactive");
		rdbtnInactive.setFont(new Font("Tahoma", Font.PLAIN, 11));
		rdbtnInactive.setBounds(181, 183, 109, 23);
		contentPane.add(rdbtnInactive);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 227, 524, 150);
		contentPane.add(scrollPane);
		
		buttonGroup.add(rdbtnActive);
		buttonGroup.add(rdbtnInactive);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int xy = table.getSelectedRow();
				btnUpdate.setEnabled(true);
				btnSave.setEnabled(false);;
				
				nameField.setText(doctor_list.get(xy).getDoctorName());
				idField.setText(doctor_list.get(xy).getDoctorId());
				qualificationField.setText(doctor_list.get(xy).getQualifications());
				specialtyField.setText(doctor_list.get(xy).getSpecialty());
				if (doctor_list.get(xy).getStatus().equalsIgnoreCase("active")) {
					rdbtnActive.setSelected(true);
				}else if (doctor_list.get(xy).getStatus().equalsIgnoreCase("inactive")) {
					rdbtnInactive.setSelected(true);
				}
				
			}
		});
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(table);
		
		JLabel lblNewLabel_1_3 = new JLabel("Doctor ID");
		lblNewLabel_1_3.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_1_3.setBounds(10, 45, 70, 23);
		contentPane.add(lblNewLabel_1_3);
		
		idField = new JTextField();
		idField.setEditable(false);
		idField.setColumns(10);
		idField.setBounds(90, 45, 129, 23);
		contentPane.add(idField);
		
		JButton btnClose = new JButton("Close");
		btnClose.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnClose.setBounds(445, 402, 89, 23);
		contentPane.add(btnClose);
		
		btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(nameField.getText().length()<1) {
					showMessage("Enter a valid name.");
				}else if(specialtyField.getText().length()<1){
					showMessage("Enter a valid specialty.");
				}else if(qualificationField.getText().length()<1) {
					showMessage("Enter a valid qualification.");
				}else {
					
					try {
						String status = "";
						if(rdbtnActive.isSelected()) {
							status = "active";
						}else if (rdbtnInactive.isSelected()) {
							status = "inactive";
						}
						Doctor doctor = new Doctor();
						doctor.setDoctorId(idField.getText());
						doctor.setDoctorName(nameField.getText());
						doctor.setQualifications(qualificationField.getText());
						doctor.setSpecialty(specialtyField.getText());
						doctor.setStatus(status);
						
						hManagementInterface.updateDoctor(doctor);
						showMessage("Update Successful!");
						doctor_list = hManagementInterface.getAllDoctors();
						updateDoctorsTable(doctor_list);
						clearForm();
						
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
			}
		});
		btnUpdate.setEnabled(false);
		btnUpdate.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnUpdate.setBounds(244, 402, 89, 23);
		contentPane.add(btnUpdate);
		
		btnSave = new JButton("Save");
		btnSave.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(nameField.getText().length()<1) {
					showMessage("Enter a valid name.");
				}else if(specialtyField.getText().length()<1){
					showMessage("Enter a valid specialty.");
				}else if(qualificationField.getText().length()<1) {
					showMessage("Enter a valid qualification.");
				}else {
					
					try {
						String status = "";
						if(rdbtnActive.isSelected()) {
							status = "active";
						}else if (rdbtnInactive.isSelected()) {
							status = "inactive";
						}
						Doctor doctor = new Doctor();
						doctor.setDoctorName(nameField.getText());
						doctor.setQualifications(qualificationField.getText());
						doctor.setSpecialty(specialtyField.getText());
						doctor.setStatus(status);
						
						hManagementInterface.saveDoctor(doctor);
						showMessage("Success!");
						doctor_list = hManagementInterface.getAllDoctors();
						updateDoctorsTable(doctor_list);
						clearForm();
						
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
			}
		});
		btnSave.setBounds(145, 402, 89, 23);
		contentPane.add(btnSave);
		
		JButton btnNewButton = new JButton("Clear");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearForm();
			}
		});
		btnNewButton.setBounds(346, 402, 89, 23);
		contentPane.add(btnNewButton);
		try {
			doctor_list = hManagementInterface.getAllDoctors();
			updateDoctorsTable(doctor_list);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void clearForm() {
		idField.setText("");
		nameField.setText("");
		qualificationField.setText("");
		specialtyField.setText("");
		rdbtnActive.setSelected(true);
		
		btnSave.setEnabled(true);
		btnUpdate.setEnabled(false);
	}
	
	public static void showMessage(String message){
		final JFrame dialog = new JFrame();
		dialog.setAlwaysOnTop(true);
		JOptionPane.showMessageDialog(dialog, message);
	}
	
	//client method to display opd invoice table
	public void updateDoctorsTable(ArrayList<Doctor> list){
		
		Object[][] data = new Object[list.size()][5];
		for(int i=0; i<list.size(); i++){ 
			data[i][0] = (i+1);
			data[i][1] = list.get(i).getDoctorName();
			data[i][2] = list.get(i).getQualifications();
			data[i][3] = list.get(i).getSpecialty();
			data[i][4] = list.get(i).getStatus();					
							
		}
		
		Object[] columnNames = {"S/No", "Doctor Name", "Qualification", "Specialty", "Status"};
		
		DefaultTableModel model = new DefaultTableModel(data, columnNames);
		table.setModel(model);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getColumnModel().getColumn(0).setPreferredWidth(40);
		table.getColumnModel().getColumn(1).setPreferredWidth(180);
		table.getColumnModel().getColumn(2).setPreferredWidth(100);
		table.getColumnModel().getColumn(3).setPreferredWidth(150);
		table.getColumnModel().getColumn(4).setPreferredWidth(60);
						
		
		repaint();
		revalidate();
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
