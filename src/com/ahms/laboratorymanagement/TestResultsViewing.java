package com.ahms.laboratorymanagement;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Font;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import com.ahms.api.ClinicManagementInterface;
import com.ahms.api.LabManagementInterface;
import com.ahms.clientinterface.InterfaceGenerator;
import com.ahms.clinicmgt.entities.PatientVisit;
import com.ahms.labmgt.entities.ResultLine;
import com.ahms.labmgt.entities.TestOrderItem;

import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

public class TestResultsViewing extends JFrame {

	private JPanel contentPane;
	private JTextField invoiceField;
	private JTextField patientNameField;
	private JTextField genderField;
	private JTable lab_result_table;
	
	private ArrayList<TestOrderItem> itemList = null;
	ArrayList<ResultLine> result_lines;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestResultsViewing frame = new TestResultsViewing("");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws RemoteException 
	 */
	public TestResultsViewing(String invoice_no) throws RemoteException {
		setTitle("Elite HMS - View Test Result");
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(400, 150, 618, 573);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		ClinicManagementInterface cm_interface = InterfaceGenerator.getClinicManagementInterface();
		LabManagementInterface lm_interface = InterfaceGenerator.getLabManagementInterface();
		
		PatientVisit pv = cm_interface.getPatientVisit(invoice_no);
		
		JLabel lblNewLabel = new JLabel("Invoice No");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel.setBounds(419, 51, 67, 20);
		contentPane.add(lblNewLabel);
		
		invoiceField = new JTextField();
		invoiceField.setEditable(false);
		invoiceField.setBounds(480, 51, 97, 20);
		invoiceField.setText(invoice_no);
		contentPane.add(invoiceField);
		invoiceField.setColumns(10);
		
		JLabel lblPatientName = new JLabel("Patient Name");
		lblPatientName.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblPatientName.setBounds(23, 51, 77, 20);
		contentPane.add(lblPatientName);
		
		patientNameField = new JTextField();
		patientNameField.setEditable(false);
		patientNameField.setBounds(101, 51, 213, 20);
		patientNameField.setText(pv.getSurname() +" "+ pv.getOthernames());
		contentPane.add(patientNameField);
		patientNameField.setColumns(10);
		
		JLabel lblGender = new JLabel("Gender");
		lblGender.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblGender.setBounds(23, 82, 67, 20);
		contentPane.add(lblGender);
		
		genderField = new JTextField();
		genderField.setEditable(false);
		genderField.setBounds(101, 82, 67, 20);
		genderField.setText(pv.getGender());
		contentPane.add(genderField);
		genderField.setColumns(10);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Laboratory Test Result", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 124, 580, 352);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 71, 560, 263);
		panel.add(scrollPane);
		
		lab_result_table = new JTable();
		scrollPane.setViewportView(lab_result_table);
		
		JLabel lblSelectTest = new JLabel("Select Test");
		lblSelectTest.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblSelectTest.setBounds(10, 32, 90, 20);
		panel.add(lblSelectTest);
		
		JComboBox comboBox = new JComboBox();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int xy = comboBox.getSelectedIndex();
				if(xy>=0){
					String test_id = itemList.get(xy).getTest_id();
					String invoice_no = invoiceField.getText();
					
					try {
						result_lines = lm_interface.getTestResult(invoice_no, test_id );
						updateTable(result_lines);
					} catch (RemoteException e) {	e.printStackTrace();	}				
				
				}
			}
		});
		
		itemList = lm_interface.getDeliveredResult(invoice_no);
		
		comboBox.removeAllItems();
		for(int i=0; i<itemList.size(); i++){
			comboBox.addItem(itemList.get(i).getTest_name());
		}
		AutoCompleteDecorator.decorate(comboBox);
		comboBox.setBounds(86, 32, 276, 20);
		panel.add(comboBox);
		
		JLabel lblNewLabel_2 = new JLabel("No. of items");
		lblNewLabel_2.setBounds(426, 32, 98, 20);
		panel.add(lblNewLabel_2);
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setText(itemList.size()+ "");
		textField.setBounds(509, 32, 46, 20);
		panel.add(textField);
		textField.setColumns(10);
		
		
		
		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				System.gc();
			}
		});
		btnClose.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnClose.setBounds(480, 500, 110, 23);
		contentPane.add(btnClose);
		
		JButton btnPrint = new JButton("Print");
		btnPrint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(invoiceField.getText().length()<1){
					//show error
				}else{
					try {
						LabResultPPrintout frame = new LabResultPPrintout(invoiceField.getText());
						frame.setVisible(true);
						dispose();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
		btnPrint.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnPrint.setBounds(360, 500, 110, 23);
		contentPane.add(btnPrint);
		
		JLabel lblNewLabel_1 = new JLabel("View Test Result");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_1.setBounds(10, 11, 580, 29);
		contentPane.add(lblNewLabel_1);
	}
	
	
	
	
	//client method to update table
			public void updateTable(ArrayList<ResultLine> list){
				//ArrayList<ResultLine> resultItems = getTestResult(inv, test);
				
				Object[][] data = new Object[list.size()][4];
					for(int i=0; i<list.size(); i++){ 
					data[i][0] = list.get(i).getTest_parameter();
					data[i][1] = list.get(i).getResult();
					data[i][2] = list.get(i).getUnit();
					data[i][3] = list.get(i).getNormal_range();
					
					}
				
				Object[] columnNames = {"Test Parameter", "Result", "Unit", "Normal Range" };
				
				DefaultTableModel model = new DefaultTableModel(data, columnNames);
				lab_result_table.setModel(model);
				lab_result_table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
				lab_result_table.getColumnModel().getColumn(0).setPreferredWidth(220);
				lab_result_table.getColumnModel().getColumn(1).setPreferredWidth(120);
				lab_result_table.getColumnModel().getColumn(2).setPreferredWidth(120);
				lab_result_table.getColumnModel().getColumn(2).setPreferredWidth(150);
				
				repaint();
				revalidate();
				
			}
}
