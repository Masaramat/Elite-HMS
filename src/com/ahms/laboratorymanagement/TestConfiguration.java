package com.ahms.laboratorymanagement;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import com.ahms.api.LabManagementInterface;
import com.ahms.clientinterface.InterfaceGenerator;
import com.ahms.labmgt.entities.TestGroup;
import com.ahms.labmgt.entities.TestItem;
import com.ahms.labmgt.entities.TestParameter;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.event.ActionEvent;
import java.awt.CardLayout;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TestConfiguration extends JFrame {

	private JPanel contentPane;
	private JTextField testParField;
	private JTextField unitField;
	private JTextField rangeField;
	private JTextField paramField;
	private JComboBox testComboBox;
	
	private Connection conn;
	private ArrayList<TestItem> testList = null;
	private ArrayList<TestGroup> testGroupList; 
	private ArrayList<TestParameter> parameterList;
	ArrayList<TestParameter> paramList;
	
	private JTable table;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestConfiguration frame = new TestConfiguration();
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
	public TestConfiguration() throws RemoteException{
		setAlwaysOnTop(true);
		setTitle("Elite HMS - Laboratory Test Configuration");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(300, 100, 701, 575);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		LabManagementInterface lm_interface = InterfaceGenerator.getLabManagementInterface();
		
		testList = lm_interface.getTestItems();
		
		JLabel lblSelectTest = new JLabel("Select Test");
		lblSelectTest.setHorizontalAlignment(SwingConstants.LEFT);
		lblSelectTest.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblSelectTest.setBounds(20, 56, 82, 21);
		contentPane.add(lblSelectTest);
		
		JPanel normalTest = new JPanel();
		normalTest.setBounds(10, 100, 655, 322);
		contentPane.add(normalTest);
		normalTest.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 630, 297);
		normalTest.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int xy = table.getSelectedRow();
				if (xy<0) {
					
				}else {
					testParField.setText(parameterList.get(xy).getParamDesc());
					unitField.setText(parameterList.get(xy).getUnit());
					
					rangeField.setText(parameterList.get(xy).getNormalRange());
					paramField.setText(parameterList.get(xy).getParamCode());
				}
			}
		});
		scrollPane.setViewportView(table);
		
		
		
		JLabel lblNameofTest = new JLabel("Name of Test Parameter");
		lblNameofTest.setBounds(127, 433, 148, 21);
		contentPane.add(lblNameofTest);
		
		JLabel lblUnit = new JLabel("Unit");
		lblUnit.setBounds(377, 433, 60, 21);
		contentPane.add(lblUnit);
		
		JLabel lblNormalRange = new JLabel("Normal Range");
		lblNormalRange.setBounds(490, 433, 99, 21);
		contentPane.add(lblNormalRange);
		
		testParField = new JTextField();
		testParField.setBounds(111, 459, 250, 20);
		contentPane.add(testParField);
		testParField.setColumns(10);
		
		unitField = new JTextField();
		unitField.setBounds(371, 459, 99, 20);
		contentPane.add(unitField);
		unitField.setColumns(10);
		
		rangeField = new JTextField();
		rangeField.setBounds(480, 459, 166, 20);
		contentPane.add(rangeField);
		rangeField.setColumns(10);
		
		
		
		JButton btnRemove = new JButton("Refresh");
		btnRemove.setEnabled(false);
		btnRemove.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				updateTable(parameterList);
			}
		});
		btnRemove.setBounds(477, 502, 89, 23);
		contentPane.add(btnRemove);
		
		JButton btnClose = new JButton("Close");
		btnClose.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			dispose();
			}
		});
		btnClose.setBounds(576, 502, 89, 23);
		contentPane.add(btnClose);
		
		JLabel lblParameterId = new JLabel("Parameter ID");
		lblParameterId.setBounds(10, 433, 79, 21);
		contentPane.add(lblParameterId);
		
		paramField = new JTextField();
		paramField.setEditable(false);
		paramField.setBounds(10, 459, 86, 20);
		contentPane.add(paramField);
		paramField.setColumns(10);
		
		testComboBox = new JComboBox();
		testComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int nox = testComboBox.getSelectedIndex();
				if(nox >= 0){
					try {
						parameterList = lm_interface.getAllParameters(testList.get(nox).getTestId());
						updateTable(parameterList );
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
					
					}else{}
				
			}
		});
		testComboBox.setBounds(112, 56, 314, 20);
		contentPane.add(testComboBox);
		
		for(int i=0; i<testList.size(); i++) {
			testComboBox.addItem(testList.get(i).getTestName());
		}
		
		JButton btnAdd = new JButton("Save");
		btnAdd.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
					String text = "";
				try{
					
					text = lm_interface.createTestParameter(testList.get(testComboBox.getSelectedIndex()).getTestId());
					paramField.setText(text);
					testParField.requestFocus();
					lm_interface.saveParameters(testParField.getText(),unitField.getText(),rangeField.getText(),paramField.getText());
					//JOptionPane.showMessageDialog(null, "Updated Successfully!");
					
					int nox = testComboBox.getSelectedIndex();
					if(nox<0){
						
					}else{
						updateTable(lm_interface.getAllParameters(testList.get(nox).getTestId()));
						clearForm();
					}
					
				}catch(Exception ex){ex.printStackTrace();}
				
				
					
			}
		});
		btnAdd.setBounds(381, 502, 89, 23);
		contentPane.add(btnAdd);
		
		JLabel lblNewLabel_1 = new JLabel("Test Configuration");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(10, 11, 665, 28);
		contentPane.add(lblNewLabel_1);
		
		
		
		
		
		
		updateTable(parameterList);
			
	}
	 
		
		//client method to clear form
		public void clearForm(){
			paramField.setText("");
			testParField.setText("");
			unitField.setText("");
			rangeField.setText("");
			//resultField.setText("");
		}

		//client method to update table
		public void updateTable(ArrayList<TestParameter> paramList){
				
			Object[][] data = new Object[paramList.size()][4];
			for(int i=0; i<paramList.size(); i++){ 
				data[i][0] = i+1;
				data[i][1] = paramList.get(i).getParamDesc();
				data[i][2] = paramList.get(i).getUnit();
				data[i][3] = paramList.get(i).getNormalRange();
				
			}
			
			Object[] columnNames = {"S/No", "Description", "Unit", "Normal Range" };
			
			DefaultTableModel model = new DefaultTableModel(data, columnNames);
			table.setModel(model);
			table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			table.getColumnModel().getColumn(0).setPreferredWidth(30);
			table.getColumnModel().getColumn(1).setPreferredWidth(300);
			table.getColumnModel().getColumn(2).setPreferredWidth(150);
			table.getColumnModel().getColumn(3).setPreferredWidth(150);
			
			
			repaint();
			revalidate();
			
			
		}
}
