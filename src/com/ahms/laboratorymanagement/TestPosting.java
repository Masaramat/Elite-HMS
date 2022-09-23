package com.ahms.laboratorymanagement;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import com.ahms.api.LabManagementInterface;
import com.ahms.clientinterface.InterfaceGenerator;
import com.ahms.hmgt.entities.UserCard;
import com.ahms.labmgt.entities.LabRequest;
import com.ahms.labmgt.entities.ResultLine;
import com.ahms.labmgt.entities.TestOrderItem;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TestPosting extends JFrame {

	private JPanel contentPane;
	private JTextField invoiceField;
	private JTable table;
	private JTextField textField_5;
	private JComboBox comboBox;
	
	private ArrayList<TestOrderItem> itemList;
	ArrayList<ResultLine> result_lines;
	
	LabManagementInterface lm_interface;
	private JTextField itemsField;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestPosting frame = new TestPosting(new UserCard(), "");
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
	public TestPosting(UserCard user, String invoice_no) {
		setAlwaysOnTop(true);
		setTitle("Elite HMS - Test Results Posting");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(420, 150, 602, 548);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lm_interface = InterfaceGenerator.getLabManagementInterface();
		
		JLabel lblInvoiceNo = new JLabel("Invoice No");
		lblInvoiceNo.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblInvoiceNo.setBounds(22, 62, 70, 21);
		contentPane.add(lblInvoiceNo);
		
		invoiceField = new JTextField();
		invoiceField.setText(invoice_no);		
		invoiceField.setBounds(111, 62, 145, 21);
		contentPane.add(invoiceField);
		invoiceField.setColumns(10);
		invoiceField.setEditable(false);
		
		JLabel lblSelectTest = new JLabel("Select Test Item");
		lblSelectTest.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblSelectTest.setBounds(22, 94, 80, 21);
		contentPane.add(lblSelectTest);
		
		comboBox = new JComboBox();
		comboBox.setFont(new Font("Tahoma", Font.BOLD, 11));
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
		comboBox.setBounds(111, 94, 288, 20);
		//for(int i=0; i<itemList.size(); i++){
			//comboBox.addItem(itemList.get(i).getTest_name());
		//}
		AutoCompleteDecorator.decorate(comboBox);
		contentPane.add(comboBox);
		 
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 126, 543, 268);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				LabRequestFrame.testItemTable.requestFocus();
				dispose();
			}
		});
		btnClose.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnClose.setBounds(435, 473, 123, 23);
		contentPane.add(btnClose);
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			  public void actionPerformed(ActionEvent arg0) {
				ArrayList<String> resultlist = new ArrayList<String>();
				String data = "";				
				
				for(int i=0; i<table.getRowCount(); i++){
					try{
					data = table.getValueAt(i, 1).toString();
					}catch(Exception e){
						data = " "; 
						}
					
					resultlist.add(data);					
					}
												
				int xy = comboBox.getSelectedIndex();
				if(xy>=0){
				String test_id = itemList.get(xy).getTest_id();
				String invoice_no = invoiceField.getText();
				
				try {
					lm_interface.updateResults(resultlist, test_id, invoice_no);
					showMessage("Success!");
					
				} catch (RemoteException e) {	e.printStackTrace();	}				
				
				}// end if
			}//action pefomed
		});
		btnSave.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnSave.setBounds(302, 473, 123, 23);
		contentPane.add(btnSave);
		
		JLabel lblReportBy = new JLabel("Report by:");
		lblReportBy.setBounds(10, 422, 65, 21);
		contentPane.add(lblReportBy);
		
		textField_5 = new JTextField();
		textField_5.setText(user.getFullNames());
		textField_5.setEditable(false);
		textField_5.setBounds(79, 422, 294, 20);
		contentPane.add(textField_5);
		textField_5.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Post Test Result");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel.setBounds(10, 11, 566, 28);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("No. Of Items");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_1.setBounds(435, 97, 70, 18);
		contentPane.add(lblNewLabel_1);
		
		itemsField = new JTextField();
		itemsField.setEditable(false);
		itemsField.setBounds(512, 94, 51, 20);
		contentPane.add(itemsField);
		itemsField.setColumns(10);
		
		try {
			updateComboBox(invoice_no);
			//updatePendingTable(lm_interface.getConfirmedRequests());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
			table.setModel(model);
			table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			table.getColumnModel().getColumn(0).setPreferredWidth(150);
			table.getColumnModel().getColumn(1).setPreferredWidth(150);
			table.getColumnModel().getColumn(2).setPreferredWidth(80);
			table.getColumnModel().getColumn(2).setPreferredWidth(150);
			
			repaint();
			revalidate();
			
		}
		
		
	//client method calls server method
	public void updateComboBox(String invoice_no) throws RemoteException{
		itemList = lm_interface.getConfirmedTest(invoice_no);
		itemsField.setText(itemList.size()+"");
		
		comboBox.removeAllItems();
		for(int i=0; i<itemList.size(); i++){
			comboBox.addItem(itemList.get(i).getTest_name());
		}
		}
	
	
	
		public static void showMessage(String message){
			final JDialog dialog = new JDialog();
			dialog.setAlwaysOnTop(true);
			JOptionPane.showMessageDialog(dialog, message);
		}
}
