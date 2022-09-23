package com.ahms.laboratorymanagement;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import com.ahms.api.LabManagementInterface;
import com.ahms.clientinterface.InterfaceGenerator;
import com.ahms.labmgt.entities.TestGroup;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.UIManager;
import java.awt.Color;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.border.EtchedBorder;
import javax.swing.SwingConstants;

public class TestGroupEntry extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTable table;
	
	

	
	private Connection conn;
	private ArrayList<TestGroup> list;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestGroupEntry frame = new TestGroupEntry();
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
	public TestGroupEntry() throws RemoteException{
		setAlwaysOnTop(true);
		setTitle("Elite HMS - Test Group Entry");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 509, 525);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		LabManagementInterface lm_interface = InterfaceGenerator.getLabManagementInterface();
		
		list = lm_interface.getTestGroups();
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Add / Edit", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(10, 47, 447, 97);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblTestGroupId = new JLabel("Group ID");
		lblTestGroupId.setBounds(29, 27, 63, 23);
		panel.add(lblTestGroupId);
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setBounds(108, 27, 109, 23);
		panel.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Group Name");
		lblNewLabel_1.setBounds(29, 61, 64, 23);
		panel.add(lblNewLabel_1);
		
		textField_1 = new JTextField();
		textField_1.setBounds(107, 63, 251, 23);
		panel.add(textField_1);
		textField_1.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 156, 447, 274);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
				
		
		
		JButton btnExit = new JButton("Close");
		btnExit.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnExit.setBounds(383, 452, 100, 23);
		contentPane.add(btnExit);
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		
		
		JButton btnNew = new JButton("Save");
		btnNew.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnNew.setBounds(53, 452, 100, 23);		
		btnNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(textField_1.getText().length()<1){
					showMessage("Enter a Valid Name for Test Group");
					}else{
						try {							
							textField.setText(lm_interface.createTestGroup(textField_1.getText()));
							showMessage("Success!");
							updateTable(lm_interface.getTestGroups());
							btnNew.setEnabled(false);
							
							
						} catch (RemoteException e) {		e.printStackTrace();		}
					}
					
			}
		});
		contentPane.add(btnNew);
		
		JButton btnSave = new JButton("Update");
		btnSave.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnSave.setBounds(163, 452, 100, 23);
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String groupid = textField.getText();				
				String groupname = textField_1.getText();
				
				try {
					lm_interface.saveTestGroup(groupid, "", groupname);
					showMessage("Success!");
					updateTable(lm_interface.getTestGroups());
					
				} catch (RemoteException e) {		e.printStackTrace();	}
				
				
			}
		});
		contentPane.add(btnSave);
		
		JButton btnClear = new JButton("Clear");
		btnClear.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnClear.setBounds(273, 452, 100, 23);
		contentPane.add(btnClear);
		
		JLabel lblNewLabel = new JLabel("Test Group Entry");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel.setBounds(10, 11, 473, 30);
		contentPane.add(lblNewLabel);
		
		updateTable(list);
				
	}
	
	
	//client method to refresh the form
	public void refreshForm(){
		textField.setText("");
		textField_1.setText("");
		//comboBox.setSelectedItem("");
		
		updateTable(list);
		repaint();
		revalidate();
	}
 
	//client method to update table model
	public void updateTable( ArrayList<TestGroup> list){
		Object[][] data = new Object[list.size()][2];
		for(int i=0; i<list.size(); i++){ 
			data[i][0] = list.get(i).getGroup_id();
			data[i][1] = list.get(i).getGroup_name();
			
		}
		
		Object[] columnNames = {"Group ID", "Group Description"};
		DefaultTableModel model = new DefaultTableModel(data, columnNames);
		table.setModel(model);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getColumnModel().getColumn(0).setPreferredWidth(100);
		table.getColumnModel().getColumn(1).setPreferredWidth(250);
		
		
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
