package com.ahms.pharmacymanagement;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.ahms.api.PharmacyManagementInterface;
import com.ahms.clientinterface.InterfaceGenerator;
import com.ahms.pharmarcymgt.entities.Medicine;
import com.ahms.pharmarcymgt.entities.MedicineStockItem;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class CurrentStock extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTable table;
	ArrayList<MedicineStockItem> current_stock = new ArrayList<MedicineStockItem>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CurrentStock frame = new CurrentStock();
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
	public CurrentStock() {
		setTitle("Elite HMS - Current Pharmacy Stock");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 550, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

	
		PharmacyManagementInterface pm_interface = InterfaceGenerator.getPharmManagementInterface();
		
		JLabel lblNewLabel = new JLabel("Current Pharmacy Stock");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(0, 0, 508, 30);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Search Medicine");
		lblNewLabel_1.setBounds(10, 41, 103, 20);
		contentPane.add(lblNewLabel_1);
		
		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				try {
					current_stock = pm_interface.getCurrentStockItems(textField.getText());
					updateTable(current_stock);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		textField.setBounds(123, 41, 221, 23);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 84, 514, 332);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JButton btnNewButton = new JButton("Close");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnNewButton.setBounds(409, 427, 89, 23);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Refresh");
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					current_stock = pm_interface.getCurrentStockItems();
					updateTable(current_stock);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		btnNewButton_1.setBounds(310, 427, 89, 23);
		contentPane.add(btnNewButton_1);
		
		try {
			current_stock = pm_interface.getCurrentStockItems();
			updateTable(current_stock);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	//client method to update the medicines table
		public void updateTable(ArrayList<MedicineStockItem> medlist){
			Object[][] data = new Object[medlist.size()][5];
			for(int i=0; i<medlist.size(); i++){ 
				data[i][0] = (i+1);
				data[i][1] = medlist.get(i).getDrugName();
				data[i][3] = medlist.get(i).getAvailableQty();
				data[i][2] = medlist.get(i).getMedicineForm();
				
				
			}
			Object[] columnNames = {"S/No",  "Drug Name", "Drug Form", "Qty in Stock"};
			
			DefaultTableModel model = new DefaultTableModel(data, columnNames);
			table.setModel(model);
			table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			table.getColumnModel().getColumn(0).setPreferredWidth(60);
			table.getColumnModel().getColumn(1).setPreferredWidth(300);
			table.getColumnModel().getColumn(2).setPreferredWidth(100);
			table.getColumnModel().getColumn(3).setPreferredWidth(100);
			
			
		}
}
