package com.ahms.laboratorymanagement;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import com.ahms.api.ClinicManagementInterface;
import com.ahms.api.LabManagementInterface;
import com.ahms.clientinterface.InterfaceGenerator;
import com.ahms.clinicmgt.entities.PatientVisit;
import com.ahms.labmgt.entities.TestItem;
import com.ahms.labmgt.entities.TestOrderItem;

import java.awt.Font;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.border.TitledBorder;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.rmi.RemoteException;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class TestOrderEntry extends JFrame {

	private JPanel contentPane; 
	private JTextField invoiceNoField; 
	private JTextField hospNoField;
	private JTextField fullNameField;
	private JTextField testCodeField;
	private JTextField testFeeField;
	public static JComboBox<String> testNameComboBox;

	
	private PatientVisit pvs;
	private ArrayList<TestItem> allTestsList;
	private ArrayList<TestOrderItem> selectTest = new ArrayList<>();
	private JTable table;
	private JTextField genderField;
	
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestOrderEntry frame = new TestOrderEntry("","", "");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	//3013725243
	/**
	 * Create the frame.
	 * @throws RemoteException 
	 */
	public TestOrderEntry(String invoice_no, String emrStatus, String source) throws RemoteException {
		setAlwaysOnTop(true);
		setTitle("Absolute HMS:  Laboratory Test Order Entry   ");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(400, 150, 604, 546);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.control);
		contentPane.setBorder(null);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		LabManagementInterface lm_interface = InterfaceGenerator.getLabManagementInterface();
		ClinicManagementInterface cm_interface = InterfaceGenerator.getClinicManagementInterface();
		
		allTestsList = lm_interface.getTestItems();
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Add Laboratory Request", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_3.setBounds(20, 130, 547, 132);
		contentPane.add(panel_3);
		panel_3.setLayout(null);
		
		JLabel lblTestId = new JLabel("Test Code");
		lblTestId.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblTestId.setBounds(10, 57, 87, 20);
		panel_3.add(lblTestId);
		
		testCodeField = new JTextField();
		testCodeField.setEditable(false);
		testCodeField.setBounds(107, 57, 64, 20);
		panel_3.add(testCodeField);
		testCodeField.setColumns(10);
		
		JLabel lblTestName = new JLabel("Laboratory Test ");
		lblTestName.setHorizontalAlignment(SwingConstants.LEFT);
		lblTestName.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblTestName.setBounds(10, 26, 87, 20);
		panel_3.add(lblTestName);
		
		
		testFeeField = new JTextField(); 						
		testFeeField.setEditable(false);
		testFeeField.setHorizontalAlignment(SwingConstants.RIGHT);
		testFeeField.setBounds(107, 88, 53, 20);
		panel_3.add(testFeeField);
		testFeeField.setColumns(10);
		
		JLabel lblRate = new JLabel("Fee");
		lblRate.setHorizontalAlignment(SwingConstants.LEFT);
		lblRate.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblRate.setBounds(10, 88, 87, 20);
		panel_3.add(lblRate);
		
		testNameComboBox = new JComboBox<String>();
		testNameComboBox.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				
				try {
					allTestsList = lm_interface.getTestItems();
					for(int i=0; i<allTestsList.size(); i++){
						testNameComboBox.addItem(allTestsList.get(i).getTestName());
						AutoCompleteDecorator.decorate(testNameComboBox);
						
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				
			}
		});
		testNameComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//get the selected index
				int nsss = testNameComboBox.getSelectedIndex();
				if(nsss >= 0){
				
				testFeeField.setText("" + allTestsList.get(nsss).getPrice());				
					testCodeField.setText(allTestsList.get(nsss).getTestId());
					
			
				}
			}
		});
		testNameComboBox.setFont(new Font("Tahoma", Font.PLAIN, 11));
		testNameComboBox.setBounds(107, 26, 295, 20);
		for(int i=0; i<allTestsList.size(); i++){
			testNameComboBox.addItem(allTestsList.get(i).getTestName());
			AutoCompleteDecorator.decorate(testNameComboBox);
			
		}
		panel_3.add(testNameComboBox);
		
		JButton btnNewButton_1 = new JButton("Add Test");
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {					
					TestNameEntry frame = new TestNameEntry();
					frame.setVisible(true);
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
				
			}
		});
		btnNewButton_1.setBounds(431, 25, 106, 23);
		panel_3.add(btnNewButton_1);
		
		
		
 		
		fullNameField = new JTextField();
		fullNameField.setEditable(false);
		fullNameField.setBounds(100, 56, 214, 20);
		contentPane.add(fullNameField);
		fullNameField.setColumns(10);
		
		hospNoField = new JTextField();
		hospNoField.setBounds(464, 55, 92, 20);
		contentPane.add(hospNoField);
		hospNoField.setEditable(false);
		hospNoField.setColumns(10);
		
		JLabel lblPatientId = new JLabel("Hospital No");
		lblPatientId.setBounds(384, 56, 70, 20);
		contentPane.add(lblPatientId);
		lblPatientId.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		JLabel lblPatientName = new JLabel("Patient Name");
		lblPatientName.setHorizontalAlignment(SwingConstants.LEFT);
		lblPatientName.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblPatientName.setBounds(20, 55, 70, 22);
		contentPane.add(lblPatientName);
		
		invoiceNoField = new JTextField();
		invoiceNoField.setEditable(false);
		invoiceNoField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(invoiceNoField.getText().length()>=5){
					try {
						pvs = lm_interface.getPatientVisitView(invoiceNoField.getText());
						loadInvoiceDetails(pvs);
					} catch (RemoteException e) {	e.printStackTrace();			}
					
					}else{}
			}
		});
		invoiceNoField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				
			}
		});
		invoiceNoField.setBounds(464, 86, 92, 20);
		contentPane.add(invoiceNoField);
		invoiceNoField.setColumns(10);
		
		
		JLabel lblInvoiceNo = new JLabel("Invoice No");
		lblInvoiceNo.setBounds(384, 86, 70, 22);
		contentPane.add(lblInvoiceNo);
		lblInvoiceNo.setFont(new Font("Tahoma", Font.PLAIN, 11));
		new SimpleDateFormat("dd-MMM-yyyy");
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 273, 547, 178);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JButton btnClose = new JButton("Close");
		btnClose.setBounds(475, 473, 92, 23);
		contentPane.add(btnClose);
		btnClose.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		JLabel lblGender = new JLabel("Gender");
		lblGender.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblGender.setBounds(20, 88, 70, 20);
		contentPane.add(lblGender);
		
		genderField = new JTextField();
		genderField.setEditable(false);
		
		genderField.setBounds(100, 87, 74, 20);
		contentPane.add(genderField);
		genderField.setColumns(10);
		
		JButton btnViewResult = new JButton("View Result");
		btnViewResult.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					TestResultsViewing trv = new TestResultsViewing(invoice_no);
					trv.setVisible(true);
					dispose();
				} catch (RemoteException e2) {
					e2.printStackTrace();
				}
			}
		});
		btnViewResult.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnViewResult.setBounds(366, 473, 99, 23);
		contentPane.add(btnViewResult);
		
		JButton btnSaveRequest = new JButton("Save");
		btnSaveRequest.setBounds(257, 473, 99, 23);
		contentPane.add(btnSaveRequest);
		btnSaveRequest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				double pp = 0.00;
				try {
					pp = Double.parseDouble(testFeeField.getText());					
				} catch (Exception e) {			showMessage("Invalid Price");			}
				
				
				if(invoiceNoField.getText().length()<2){
					showMessage("Invalid invoice number!");
				}else if(testCodeField.getText().length()<2){
					showMessage("Invalid invoice number!");
				}else{
					try {
						lm_interface.saveTestOrder(invoiceNoField.getText(), testCodeField.getText(), pp);
						showMessage("Success!");
						
						testCodeField.setText("");
						testFeeField.setText("");
						testNameComboBox.setSelectedIndex(0);
						
						updateTestTable(lm_interface.getOrderItems(invoice_no, "all"));
						
					} catch (RemoteException e) {
					
						e.printStackTrace();
					}
					
					
				}
				
			}
		});
		btnSaveRequest.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		JLabel lblNewLabel = new JLabel("Laboratory Request Form");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel.setBounds(10, 11, 569, 23);
		contentPane.add(lblNewLabel);
		
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		
		
		
			invoiceNoField.setEditable(false);
			invoiceNoField.setText(invoice_no);
			try {
				pvs = cm_interface.getPatientVisit(invoice_no);
				loadInvoiceDetails(pvs);
				selectTest = lm_interface.getOrderItems(invoice_no, "all");
				updateTestTable(selectTest);
			} catch (RemoteException e) {	e.printStackTrace();			}
			
		
			if (emrStatus.equalsIgnoreCase("closed")) {
				btnSaveRequest.setEnabled(false);
			}
							
	}
	


	//client method to update the frame 
	public void loadInvoiceDetails(PatientVisit pssv){
		invoiceNoField.setText(pssv.getInvoiceNo());
		hospNoField.setText(pssv.getHospitalNo());
		fullNameField.setText(pssv.getSurname()+" " +pssv.getOthernames());
		genderField.setText(pssv.getGender());
		//docIdField.setText(pssv.getDoctor()); 
		//docNameField.setText(pssv.getDoctor());
					
	}

	//client method to update investigations table
	public void updateTestTable(ArrayList<TestOrderItem> itemlist){
		Object[][] data = new Object[itemlist.size()][4];
		for(int i = 0; i<itemlist.size(); i++){
			data[i][0] = (i+1);		
			data[i][1] = itemlist.get(i).getTest_name();
			data[i][2] = itemlist.get(i).getPrice();
			data[i][3] = itemlist.get(i).getStatus();
		}
		
		Object[] columnNames = {"S/No", "Test Description", "Price", "Status"};
		DefaultTableModel model = new DefaultTableModel(data, columnNames);
		table.setModel(model);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getColumnModel().getColumn(0).setPreferredWidth(60);
		table.getColumnModel().getColumn(1).setPreferredWidth(350);
		table.getColumnModel().getColumn(2).setPreferredWidth(100);
		table .getColumnModel().getColumn(3).setPreferredWidth(130);
		
		
				
		
	}

	
	public void showMessage(String message){
		JFrame jf = new JFrame();
		jf.setAlwaysOnTop(true);
		
		JOptionPane.showMessageDialog(jf, message);
	}
}
