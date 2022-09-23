package com.ahms.laboratorymanagement;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.ahms.api.LabManagementInterface;
import com.ahms.clientinterface.InterfaceGenerator;
import com.ahms.hmgt.entities.PatientBiodata;
import com.ahms.labmgt.entities.LabRequest;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JTextField;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;

import com.toedter.calendar.JDateChooser;

public class DeliveredRequestFrame extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTable table;
	
	private ArrayList<LabRequest> lab_list = new ArrayList<>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DeliveredRequestFrame frame = new DeliveredRequestFrame();
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
	public DeliveredRequestFrame() {
		setTitle("Elite HMS - Delivered Lab Request");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(400, 150, 639, 531);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		LabManagementInterface lmi = InterfaceGenerator.getLabManagementInterface();
		
		
		JLabel lblNewLabel = new JLabel("Laboratory Request - Delivered ");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel.setBounds(20, 11, 510, 28);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Search using:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_1.setBounds(20, 50, 71, 22);
		contentPane.add(lblNewLabel_1);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Patient Name", "Invoice Number"}));
		comboBox.setBounds(98, 50, 124, 22);
		contentPane.add(comboBox);
		
		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent ke) {
				if (textField.getText().length()<1) {
					lab_list.clear();
					updateTestTable(lab_list);
				}else {
					try {
						lab_list = lmi.getLabRequestSummary(comboBox.getSelectedItem().toString(), textField.getText());
						updateTestTable(lab_list);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				
			}
		});
		textField.setBounds(232, 50, 206, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 134, 603, 281);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JButton btnNewButton = new JButton("Close");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				System.gc();
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnNewButton.setBounds(524, 458, 89, 23);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("View Result");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			if (table.getSelectedRow() < 0) {
				showMessage("No item selected");
			}else {				
				try {
					String invoice_no = lab_list.get(table.getSelectedRow()).getInvoiceNo();
					TestResultsViewing frame = new TestResultsViewing(invoice_no);
					frame.setVisible(true);
					dispose();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
			}
		});
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnNewButton_1.setBounds(425, 458, 89, 23);
		contentPane.add(btnNewButton_1);
		
		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setDateFormatString("dd-MM-yyyy");
		dateChooser.setDate(new Date());
		dateChooser.setBounds(98, 92, 110, 20);
		contentPane.add(dateChooser);
		
		JDateChooser dateChooser_1 = new JDateChooser();
		dateChooser_1.setDateFormatString("dd-MM-yyyy");
		dateChooser_1.setDate(new Date());
		dateChooser_1.setBounds(296, 92, 110, 20);
		contentPane.add(dateChooser_1);
		
		JLabel lblNewLabel_2 = new JLabel("From Date:");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_2.setBounds(20, 92, 58, 20);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_2_1 = new JLabel("To Date:");
		lblNewLabel_2_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_2_1.setBounds(228, 92, 58, 20);
		contentPane.add(lblNewLabel_2_1);
		
		JButton btnNewButton_2 = new JButton("Search");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				try {
					//parse selected date to sql format					
					String from_date = ((JTextField)dateChooser.getDateEditor().getUiComponent()).getText();
					SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
					Date date = sdf.parse(from_date);
					java.sql.Date sql_from_date = new java.sql.Date(date.getTime());//var1
									
					String to_date = ((JTextField)dateChooser_1.getDateEditor().getUiComponent()).getText();
					SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy");
					Date date2 = sdf2.parse(to_date);
					java.sql.Date sql_to_date = new java.sql.Date(date2.getTime());//var2
					
					lab_list = lmi.getLabRequestSummary(sql_from_date, sql_to_date);
					updateTestTable(lab_list);
					
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		btnNewButton_2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnNewButton_2.setBounds(425, 92, 89, 20);
		contentPane.add(btnNewButton_2);
		
		updateTestTable(lab_list);
	}
	
	
	
	//client method to update table
		public void updateTestTable(ArrayList<LabRequest> list){
			
			Object[][] data = new Object[list.size()][5];
			for(int i=0; i<list.size(); i++){
				data[i][0] = (i+1);
				data[i][1] = list.get(i).getInvoiceNo();
				data[i][2] = list.get(i).getSurname()+ " " +list.get(i).getOthernames();
				data[i][3] = "delivered";
				data[i][4] = list.get(i).getNoOfTests();
							
			}
			
			Object[] columnNames = {"S/No", "Invoice. No", "Patient Name", "Status", "No. of Items"};
			
			DefaultTableModel model = new DefaultTableModel(data, columnNames);
			table.setModel(model);
			table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			table.getColumnModel().getColumn(0).setPreferredWidth(60);
			table.getColumnModel().getColumn(1).setPreferredWidth(120);
			table.getColumnModel().getColumn(2).setPreferredWidth(220);
			table.getColumnModel().getColumn(3).setPreferredWidth(120);
			table.getColumnModel().getColumn(4).setPreferredWidth(120);
			
			repaint();
			revalidate();
		}
				
		public static void showMessage(String message){
			final JDialog dialog = new JDialog();
			dialog.setAlwaysOnTop(true);
			JOptionPane.showMessageDialog(dialog, message);
		}
}
