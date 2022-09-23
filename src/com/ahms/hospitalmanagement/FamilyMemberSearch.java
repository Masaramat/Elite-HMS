package com.ahms.hospitalmanagement;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.ahms.api.HospitalManagementInterface;
import com.ahms.clientinterface.InterfaceGenerator;
import com.ahms.clinicmanagement.OPDVisit;
import com.ahms.hmgt.entities.FamilyCard;
import com.ahms.hmgt.entities.PatientBiodata;
import com.ahms.hmgt.entities.UserCard;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.border.TitledBorder;
import javax.swing.JButton;

public class FamilyMemberSearch extends JFrame {

	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private JTextField textField;
	
	CardLayout cLayout = new CardLayout();
	
	private ArrayList<FamilyCard> family_list = new ArrayList<>();
	private ArrayList<PatientBiodata> family_members_list = new ArrayList<>();
	private JTable table_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FamilyMemberSearch frame = new FamilyMemberSearch(new UserCard(), "");
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
	public FamilyMemberSearch(UserCard user, String source) {
		setAlwaysOnTop(true);
		setTitle("Search Family Card - Elite HMS");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(400, 150, 570, 524);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		final HospitalManagementInterface hm_interface = InterfaceGenerator.getHospitalManagementInterface();
		
		JPanel switch_panel = new JPanel();
		switch_panel.setBounds(10, 11, 534, 463);
		contentPane.add(switch_panel);
		switch_panel.setLayout(cLayout);
		
		JPanel search_pane = new JPanel();
		switch_panel.add(search_pane, "panel_2");
		search_pane.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Search By:");
		lblNewLabel_1.setBounds(10, 63, 66, 14);
		search_pane.add(lblNewLabel_1);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(86, 61, 163, 18);
		search_pane.add(comboBox);
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Family Name", "Family Number"}));
		
		textField = new JTextField();
		textField.setBounds(269, 61, 195, 18);
		search_pane.add(textField);
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {				
				try {
					if(textField.getText().length()<1) {
						family_list.clear();
						updateFamilyCardTable(family_list);
						family_members_list.clear();
						updateFamilyMembersTable(family_members_list);
					}else {
						family_list = hm_interface.getFamilyCards(comboBox.getSelectedItem().toString(), textField.getText());
						updateFamilyCardTable(family_list);
					}
					
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		textField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Search Family Card");
		lblNewLabel.setBounds(10, 11, 454, 23);
		search_pane.add(lblNewLabel);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 102, 514, 296);
		search_pane.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//MasterPatientIndex.updateFamilyCardInfo(family_list.get(table.getSelectedRow()));
				
				
				
			}
		});
		scrollPane.setViewportView(table);
		
		JButton btnClose_1 = new JButton("Close");
		btnClose_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnClose_1.setBounds(435, 429, 89, 23);
		search_pane.add(btnClose_1);
		
		JButton btnViewMembers = new JButton("View Members");
		btnViewMembers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int xy = table.getSelectedRow();
				if (xy<0) {
					showMessage("No item selected.");
				}else {
					try {
						family_members_list = hm_interface.getPatient(family_list.get(xy).getFamilyNo());
						updateFamilyMembersTable(family_members_list);
						cLayout.show(switch_panel, "panel_1");
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
				
				
			}
		});
		btnViewMembers.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnViewMembers.setBounds(299, 429, 126, 23);
		search_pane.add(btnViewMembers);
		
		JPanel member_panel = new JPanel();
		switch_panel.add(member_panel, "panel_1");
		member_panel.setLayout(null);
		
		JButton btnBookAppt = new JButton("Book Appointment");
		btnBookAppt.setBounds(101, 429, 126, 23);
		member_panel.add(btnBookAppt);
		btnBookAppt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (table_1.getSelectedRow()<0) {
						showMessage("Select patient from table.");
					}else {
						if(source.equalsIgnoreCase("new")) {
							OPDAppointment frame = new OPDAppointment(user, family_members_list.get(table_1.getSelectedRow()), "search");
							frame.setVisible(true);
							dispose();
						}else {						
							OPDAppointment.updatePatientInfo(family_members_list.get(table_1.getSelectedRow()));
							dispose();
						}
						
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
			
		});
		btnBookAppt.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		JButton newVisitButton = new JButton("New Visit");
		newVisitButton.setBounds(237, 429, 89, 23);
		member_panel.add(newVisitButton);
		newVisitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (table_1.getSelectedRow()<0) {
						showMessage("Select patient from table.");
					}else {
						if(source.equalsIgnoreCase("new")) {
							OPDVisit frame = new OPDVisit(user, family_members_list.get(table_1.getSelectedRow()), "search");
							frame.setVisible(true);
							dispose();
							System.gc();
							}else {						
								OPDVisit.updatePatientInfo(family_members_list.get(table_1.getSelectedRow()));
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
		
		JButton btnPreview = new JButton("Preview");
		btnPreview.setBounds(336, 429, 89, 23);
		member_panel.add(btnPreview);
		btnPreview.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int xy = table.getSelectedRow();
				if(xy<0){
					showMessage("No item selected.");
				}else{
					String hosp_no = family_members_list.get(table_1.getSelectedRow()).getHospital_no();
					PatientBiodataPrintFrame view = new PatientBiodataPrintFrame(hosp_no);
					view.setVisible(true);
					
				}
			}
		});
		btnPreview.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		JButton btnClose = new JButton("Close");
		btnClose.setBounds(435, 429, 89, 23);
		member_panel.add(btnClose);
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				System.gc();
			}
		});
		btnClose.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 59, 514, 338);
		member_panel.add(scrollPane_1);
		
		table_1 = new JTable();
		scrollPane_1.setViewportView(table_1);
		
		JLabel lblNewLabel_2 = new JLabel("Family Members");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_2.setBounds(4, 11, 484, 23);
		member_panel.add(lblNewLabel_2);
		
		JButton btnBack = new JButton("Back");
		btnBack.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cLayout.show(switch_panel, "panel_2");
			}
		});
		btnBack.setBounds(10, 429, 81, 23);
		member_panel.add(btnBack);
		
		updateFamilyCardTable(family_list);
		updateFamilyMembersTable(family_members_list);
	}
	
	// client methods to update user  table
	public void updateFamilyCardTable(ArrayList<FamilyCard> list) {
		Object[][] data = new Object[list.size()][4];
				
		for(int i=0; i<list.size(); i++) {
			data[i][0] = (i+1);
			data[i][1] = list.get(i).getFamilyNo();
			data[i][2] = list.get(i).getFamilyName();
			data[i][3] = list.get(i).getDate();						
		}
		
		Object[] columnNames = {"S/No", "Family No.", "Family Name", "Reg. Date"};
		
		DefaultTableModel model = new DefaultTableModel(data, columnNames);
		table.setModel(model);
	
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getColumnModel().getColumn(0).setPreferredWidth(60);
		table.getColumnModel().getColumn(1).setPreferredWidth(100);
		table.getColumnModel().getColumn(2).setPreferredWidth(220);
		table.getColumnModel().getColumn(3).setPreferredWidth(140);
				
	}
	
	// client methods to update user  table
	public void updateFamilyMembersTable(ArrayList<PatientBiodata> list) {
		Object[][] data = new Object[list.size()][4];
				
		for(int i=0; i<list.size(); i++) {
			data[i][0] = (i+1);
			data[i][1] = list.get(i).getHospital_no();
			data[i][2] = list.get(i).getSurname() + " " + list.get(i).getFirstname();
			data[i][3] = list.get(i).getRegDate();						
		}
		
		Object[] columnNames = {"S/No", "Hospital No.", "Patient Name", "Reg. Date"};
		
		DefaultTableModel model = new DefaultTableModel(data, columnNames);
		table_1.setModel(model);
	
		table_1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table_1.getColumnModel().getColumn(0).setPreferredWidth(40);
		table_1.getColumnModel().getColumn(1).setPreferredWidth(100);
		table_1.getColumnModel().getColumn(2).setPreferredWidth(280);
		table_1.getColumnModel().getColumn(3).setPreferredWidth(100);
				
	}
		
		
	
		public static void showMessage(String message){
			final JFrame dialog = new JFrame();
			dialog.setAlwaysOnTop(true);
			JOptionPane.showMessageDialog(dialog, message);
		}
}
