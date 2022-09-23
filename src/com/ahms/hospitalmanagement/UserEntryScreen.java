package com.ahms.hospitalmanagement;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.ahms.api.HospitalManagementInterface;
import com.ahms.clientinterface.InterfaceGenerator;
import com.ahms.hmgt.entities.PatientBiodata;
import com.ahms.hmgt.entities.UserCard;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.DefaultComboBoxModel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


@SuppressWarnings("rawtypes")
public class UserEntryScreen extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;
	private JTextField textField_1;
	private JTable table;
	
	ArrayList<UserCard> user_list = new ArrayList<UserCard>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserEntryScreen frame = new UserEntryScreen();
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
	@SuppressWarnings("unchecked")
	public UserEntryScreen() {
		setTitle("Elite HMS - User Management");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(430, 120, 530, 556);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		HospitalManagementInterface hai = InterfaceGenerator.getHospitalManagementInterface();
		
		ButtonGroup bg = new ButtonGroup();
		
		JLabel lblFullNames = new JLabel("Full Names");
		lblFullNames.setBounds(18, 49, 77, 20);
		contentPane.add(lblFullNames);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(18, 80, 69, 20);
		contentPane.add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(18, 111, 77, 20);
		contentPane.add(lblPassword);
		
		JLabel lblRole = new JLabel("Role");
		lblRole.setBounds(18, 142, 77, 20);
		contentPane.add(lblRole);
		
		JRadioButton rdbtnActive = new JRadioButton("Enable");
		rdbtnActive.setBounds(18, 180, 69, 23);
		rdbtnActive.setSelected(true);
		contentPane.add(rdbtnActive);
		
		JRadioButton rdbtnInactive = new JRadioButton("Disable");
		rdbtnInactive.setBounds(89, 180, 77, 23);
		contentPane.add(rdbtnInactive);
		
		bg.add(rdbtnActive);
		bg.add(rdbtnInactive);
		
		
		textField = new JTextField();
		textField.setBounds(89, 49, 258, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(89, 111, 139, 20);
		contentPane.add(passwordField);
		
		textField_1 = new JTextField();
		textField_1.setBounds(89, 80, 167, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Physician", "Nurse", "Receptionist", "Manager", "Lab Scientist", "Pharmacist", "Super Administrator" }));
		comboBox.setBounds(89, 142, 167, 20);
		contentPane.add(comboBox);
		
		JButton btnSave = new JButton("Save");
		btnSave.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnSave.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {
				if(textField.getText().length()<1){
					showMessage("Please enter full name.");
					
				}else if(textField_1.getText().length()<1){
					showMessage("Please enter username.");
					
				} else if(passwordField.getText().length()<3){
					showMessage("Please enter password.");
					
				}else{
					String role = comboBox.getSelectedItem().toString().toLowerCase();
					String status = "";
					
					if(rdbtnInactive.isSelected()){
						status = "inactive";
					}else if(rdbtnActive.isSelected()){
						status = "active";
					}
					
					UserCard ues = new UserCard(textField.getText(), textField_1.getText(), passwordField.getText(), role, status);
					try {
						hai.createUser(ues);
						showMessage("New User Added Successfully!");
						user_list = hai.getAllUsers();
						updateUserTable(user_list);
						btnSave.setEnabled(false);
					} catch (RemoteException e) {						
						e.printStackTrace();
					}
				}
			}
		});
		btnSave.setBounds(118, 483, 89, 23);
		contentPane.add(btnSave);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(textField.getText().length()<1){
					showMessage("Please enter full name.");
					
				}else if(passwordField.getText().length()<3){
					showMessage("Please enter password.");
					
				}else{
					String role = comboBox.getSelectedItem().toString().toLowerCase();
					String status = "";
					
					if(rdbtnInactive.isSelected()){
						status = "inactive";
					}else if(rdbtnActive.isSelected()){
						status = "active";
					}
					
					UserCard ues = new UserCard(textField.getText(), textField_1.getText(), passwordField.getText(), role, status);
					try {
						hai.updateUser(ues);
						showMessage("User updated successfully!");
						btnSave.setEnabled(true);
						textField.setText("");
						textField_1.setText("");
						passwordField.setText("");
						comboBox.setSelectedIndex(0);
						table.clearSelection();
						user_list = hai.getAllUsers();						
						updateUserTable(user_list);
						
					} catch (RemoteException e1) {						
						e1.printStackTrace();
					}
				}
				
			}
		});
		btnUpdate.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnUpdate.setBounds(217, 483, 89, 23);
		contentPane.add(btnUpdate);
		
		JButton btnClose = new JButton("Close");
		btnClose.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnClose.setBounds(415, 483, 89, 23);
		contentPane.add(btnClose);
		
		JLabel lblNewLabel = new JLabel("User Management");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(0, 11, 471, 26);
		contentPane.add(lblNewLabel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(18, 210, 486, 262);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnSave.setEnabled(false);				
				int xy = table.getSelectedRow();
				textField.setText(user_list.get(xy).getFullNames());
				textField_1.setText(user_list.get(xy).getUsername());
				comboBox.setSelectedItem(user_list.get(xy).getRole());
			}
		});
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(table);
		
		JButton btnNewButton = new JButton("Clear");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField.setText("");
				textField_1.setText("");
				passwordField.setText("");
				comboBox.setSelectedIndex(0);
				btnSave.setEnabled(true);
				table.clearSelection();
				
			}
		});
		btnNewButton.setBounds(316, 483, 89, 23);
		contentPane.add(btnNewButton);
		
		try {
			user_list = hai.getAllUsers();
			updateUserTable(user_list);
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	
	//client method to update table
		public void updateUserTable(ArrayList<UserCard> user_list){
			
			Object[][] data = new Object[user_list.size()][5];
			for(int i=0; i<user_list.size(); i++){ 
				data[i][0] = (i+1);
				data[i][1] = user_list.get(i).getFullNames();
				data[i][2] = user_list.get(i).getUsername();
				data[i][3] = user_list.get(i).getRole();
				data[i][4] = user_list.get(i).getStatus();				
				
							
			}
			
			Object[] columnNames = {"S/No.", "Full Name", "Username", "Role", "Status" };
			
			DefaultTableModel model = new DefaultTableModel(data, columnNames);
			table.setModel(model);
			table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			table.getColumnModel().getColumn(0).setPreferredWidth(60);
			table.getColumnModel().getColumn(1).setPreferredWidth(200);
			table.getColumnModel().getColumn(2).setPreferredWidth(130);
			table.getColumnModel().getColumn(3).setPreferredWidth(100);
			table.getColumnModel().getColumn(4).setPreferredWidth(80);
			repaint();
			revalidate();
		}
	
	
	//a standard client method for displaying popup messages on frame that is setAlwaysOnTop(true)
		public static void showMessage(String message){
			final JDialog dialog = new JDialog();
			dialog.setAlwaysOnTop(true);
			JOptionPane.showMessageDialog(dialog, message);
		}
}
