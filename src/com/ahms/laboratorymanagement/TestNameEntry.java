package com.ahms.laboratorymanagement;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import com.ahms.api.LabManagementInterface;
import com.ahms.clientinterface.InterfaceGenerator;
import com.ahms.labmgt.entities.TestGroup;
import com.ahms.labmgt.entities.TestItem;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.JDialog;

import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.ScrollPaneConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.border.EtchedBorder;
import java.awt.Color;

public class TestNameEntry extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTextField testIdField;
	private JTextField testNameField;
	private JTextField priceField;
	private JComboBox comboBox;	
	
	
	private ArrayList<TestItem> testList;
	private ArrayList<TestGroup> testGroupList; 
	
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestNameEntry frame = new TestNameEntry();
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
	public TestNameEntry() throws RemoteException{
		setAlwaysOnTop(true);
		setTitle("Elite HMS - Test Name Entry");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(400, 100, 545, 595);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		LabManagementInterface lm_interface = InterfaceGenerator.getLabManagementInterface();
		
		testGroupList = lm_interface.getTestGroups();
		testList = lm_interface.getTestItems();
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Add New Test", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_1.setBounds(10, 55, 509, 162);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Test ID");
		lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel.setBounds(10, 29, 70, 23);
		panel_1.add(lblNewLabel);
		
		JLabel lblTestName = new JLabel("Test Name");
		lblTestName.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		lblTestName.setHorizontalAlignment(SwingConstants.LEFT);
		lblTestName.setBounds(10, 97, 72, 23);
		panel_1.add(lblTestName);
		
		testIdField = new JTextField();
		testIdField.setEditable(false);
		testIdField.setBounds(90, 30, 127, 20);
		panel_1.add(testIdField);
		testIdField.setColumns(10);
		
		testNameField = new JTextField();
		testNameField.setColumns(10);
		testNameField.setBounds(92, 98, 239, 20);
		panel_1.add(testNameField);
		
		JLabel lblTestGroupCode = new JLabel("Test Group");
		lblTestGroupCode.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		lblTestGroupCode.setHorizontalAlignment(SwingConstants.LEFT);
		lblTestGroupCode.setBounds(10, 63, 72, 23);
		panel_1.add(lblTestGroupCode);
		
		JLabel lblPrice = new JLabel("Price");
		lblPrice.setHorizontalAlignment(SwingConstants.LEFT);
		lblPrice.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		lblPrice.setBounds(10, 131, 72, 23);
		panel_1.add(lblPrice);
		
		priceField = new JTextField();
		priceField.setText("0.00");
		priceField.setHorizontalAlignment(SwingConstants.RIGHT);
		priceField.setBounds(90, 129, 86, 20);
		panel_1.add(priceField);
		priceField.setColumns(10);
		
		comboBox = new JComboBox();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
								
			}
		});
		comboBox.setEditable(true);
		comboBox.setBounds(90, 61, 239, 22);
		for(int i=0; i<testGroupList.size(); i++){
			comboBox.addItem(testGroupList.get(i).getGroup_name());
		}
		AutoCompleteDecorator.decorate(comboBox);
		panel_1.add(comboBox);
		
		JButton btnNewButton = new JButton("Configure Test");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					TestConfiguration frame = new TestConfiguration();
					frame.setVisible(true);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnNewButton.setBounds(374, 97, 112, 23);
		panel_1.add(btnNewButton);
		
		
		
		JButton btnSave = new JButton("Save");
		btnSave.setBounds(133, 521, 89, 23);
		contentPane.add(btnSave);
		btnSave.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		JButton btnDelete = new JButton("Update");
		btnDelete.setBounds(232, 521, 89, 23);
		contentPane.add(btnDelete);
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TestItem test = submitTest();
				if(testNameField.getText().length()<1){	
					showMessage("Enter a Valid Test Name ");
				}else{
					try {					
						lm_interface.saveTest(test);
						showMessage("Update Successful!");
						 updateTable(lm_interface.getTestItems());
						 testNameField.setText("");
						 priceField.setText("0.00");
						 
						 priceField.setText(""+0.00);
							testIdField.setText("");
							testNameField.setText("");
							
							btnDelete.setEnabled(false);
							btnSave.setEnabled(true);
						
					} catch (RemoteException e) {	e.printStackTrace();		}
				}		
			}
		});
		btnDelete.setEnabled(false);
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		JButton btnNew = new JButton("Clear");
		btnNew.setBounds(331, 521, 89, 23);
		contentPane.add(btnNew);
		btnNew.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		
		JButton btnExit = new JButton("Close");
		btnExit.setBounds(430, 521, 89, 23);
		contentPane.add(btnExit);
		btnExit.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 228, 509, 276);
		contentPane.add(scrollPane_1);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int xy = table.getSelectedRow();
				if(xy<0){
					
				}else{
					String test_id = table.getValueAt(xy, 1).toString();
					String test_name = table.getValueAt(xy, 2).toString();
					double price = Double.parseDouble(table.getValueAt(xy, 3).toString());
					priceField.setText(""+price);
					testIdField.setText(test_id);
					testNameField.setText(test_name);
					
					btnDelete.setEnabled(true);
					btnSave.setEnabled(false);
				}
				
			}
		});
		scrollPane_1.setViewportView(table);
		
		JLabel lblNewLabel_1 = new JLabel("Test Entry");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_1.setBounds(10, 11, 509, 33);
		contentPane.add(lblNewLabel_1);
		
		
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			TestOrderEntry.testNameComboBox.requestFocus();
			dispose();
			}
		});
		btnNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			TestItem test = submitTest();
			if(testNameField.getText().length()<1){	
				showMessage("Enter a Valid Test Name ");
			}else{
				try {					
					testIdField.setText(lm_interface.createTest(test));
					showMessage("Success");
					 updateTable(lm_interface.getTestItems());
					 testNameField.setText("");
					 priceField.setText("0.00");
					
				} catch (RemoteException e) {	e.printStackTrace();		}
			}
			}
		});
		
		
		updateTable(testList);
				
	}
			
	//client method to update table 
	public void updateTable(ArrayList<TestItem> testList){
			Object[][] data = new Object[testList.size()][4];
			for(int i=0; i<testList.size(); i++){ 
				data[i][0] = (i+1);
				data[i][1] = testList.get(i).getTestId();				
				data[i][2] = testList.get(i).getTestName();					
				data[i][3] = testList.get(i).getPrice();				
				
			}
			
			Object[] columnNames = {"S/No", "Test ID", "Test Name", "Price"};
			DefaultTableModel model = new DefaultTableModel(data, columnNames);
			table.setModel(model);
			table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
			table.getColumnModel().getColumn(0).setPreferredWidth(50);
			table.getColumnModel().getColumn(1).setPreferredWidth(100);
			table.getColumnModel().getColumn(2).setPreferredWidth(400);
			table.getColumnModel().getColumn(3).setPreferredWidth(200);
			
			
			repaint();
			revalidate();
		}
		
	//client method bundles frame values into object and submits to server
	public TestItem submitTest(){
		String testId, testName, testGroupId;
		double price;
		try{
		testId = testIdField.getText();		
		testName = testNameField.getText();		
		price = Double.parseDouble(priceField.getText());
		testGroupId = testGroupList.get(comboBox.getSelectedIndex()).getGroup_id();
		
		TestItem test = new TestItem();
		test.setTestId(testId);
		test.setTestCode("");
		test.setTestName(testName);
		test.setTestType("");
		test.setTestTitle("");
		test.setTestGroupCode(testGroupId);
		test.setPrice(price);
		test.setDiscount(0.00);
		test.setVat(0.00);
		
		//sending the test object to server for update
		
		return test;		
		}catch(Exception ex){ ex.printStackTrace(); }		
		return null;
	}
	
	public static void showMessage(String message){
		final JDialog dialog = new JDialog();
		dialog.setAlwaysOnTop(true);
		JOptionPane.showMessageDialog(dialog, message);
	}
}
