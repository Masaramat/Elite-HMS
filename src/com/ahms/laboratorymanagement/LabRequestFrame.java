package com.ahms.laboratorymanagement;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.ahms.api.LabManagementInterface;
import com.ahms.clientinterface.InterfaceGenerator;
import com.ahms.hmgt.entities.UserCard;
import com.ahms.labmgt.entities.LabRequest;
import com.ahms.labmgt.entities.TestOrderItem;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.util.ArrayList;

import javax.swing.SwingConstants;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.awt.event.ActionEvent;
import javax.swing.ListSelectionModel;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JTextField;

public class LabRequestFrame extends JFrame {

	private JPanel contentPane;
	CardLayout cardLayout = new CardLayout();
	private JTable labOrderTable;
	public static JTable testItemTable;
	
	private ArrayList<String> selItemList = new ArrayList<String>();
	
	private ArrayList<LabRequest> lab_request_list;
	private ArrayList<TestOrderItem> request_items_list;
	private LabManagementInterface lm_interface;
	private JTextField nameField;
	private JTextField genderField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LabRequestFrame frame = new LabRequestFrame(new UserCard(), "");
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
	public LabRequestFrame(UserCard user, String source) {
		setTitle("Elite HMS - Laboratory Request(s)");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(400, 150, 639, 531);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lm_interface = InterfaceGenerator.getLabManagementInterface();
		
		JPanel switch_panel = new JPanel();
		switch_panel.setBounds(10, 45, 603, 436);
		switch_panel.setLayout(cardLayout);
		contentPane.add(switch_panel);
		
		JPanel request_panel = new JPanel();
		switch_panel.add(request_panel, "panel_1");
		request_panel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 583, 375);
		request_panel.add(scrollPane);
		
		labOrderTable = new JTable();
		scrollPane.setViewportView(labOrderTable);
		
		JButton btnClose = new JButton("Close");
		btnClose.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnClose.setBounds(504, 402, 89, 23);
		request_panel.add(btnClose);
		
		JButton btnNewButton = new JButton("Open Request");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					lab_request_list = lm_interface.getLabRequestSummary("active");
					int xy = labOrderTable.getSelectedRow();
					if (xy>=0) {
						request_items_list = lm_interface.getOrderItems(lab_request_list.get(xy).getInvoiceNo());
						nameField.setText(lab_request_list.get(xy).getSurname() + " " + lab_request_list.get(xy).getOthernames());
						
						
						updateTestItemTable(request_items_list);
						cardLayout.show(switch_panel, "panel_2");
					}
					
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnNewButton.setBounds(381, 402, 113, 23);
		request_panel.add(btnNewButton);
		
		JPanel test_items_panel = new JPanel();
		switch_panel.add(test_items_panel, "panel_2");
		test_items_panel.setLayout(null);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 45, 583, 334);
		test_items_panel.add(scrollPane_1);
		
		testItemTable = new JTable();
		testItemTable.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				try {					
					if(request_items_list.size()>0) {
						request_items_list = lm_interface.getOrderItems(request_items_list.get(0).getInvoice_no());						
						updateTestItemTable(request_items_list);
					}
					
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				
			}
		});
		testItemTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		scrollPane_1.setViewportView(testItemTable);
		
		JButton btnNewButton_1 = new JButton("Close");
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				System.gc();
			}
		});
		btnNewButton_1.setBounds(504, 402, 89, 23);
		test_items_panel.add(btnNewButton_1);
		
		JButton btnConfirmTest = new JButton("Confirm Test");
		btnConfirmTest.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnConfirmTest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame jFrame = new JFrame();
				jFrame.setAlwaysOnTop(true);
				if(testItemTable.getSelectedRow()<0) {
		    		showMessage("No item selected.");
		    	}else {
		    		try {
						int confirmed = JOptionPane.showConfirmDialog(jFrame, 
						        "Proceed to confirm laboratory test(s)?", "Laboratory Test Confirmation.",
						        JOptionPane.YES_NO_OPTION);

						    if (confirmed == JOptionPane.YES_OPTION) {					    	
						    	confirmTestOrder();					    	
						    }		
						
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}
		    	}
			}
		});
		btnConfirmTest.setBounds(181, 402, 102, 23);
		test_items_panel.add(btnConfirmTest);
		
		JButton btnPostResult = new JButton("Post Result");
		btnPostResult.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int xy = labOrderTable.getSelectedRow();
				if(xy>=0) {
					 
					try {
						TestPosting testPosting = new TestPosting(user, lab_request_list.get(xy).getInvoiceNo());
						testPosting.setVisible(true);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					
				}
			}
		});
		btnPostResult.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnPostResult.setBounds(293, 402, 89, 23);
		test_items_panel.add(btnPostResult);
		
		JButton btnDeliverTest = new JButton("Deliver Test");
		btnDeliverTest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int xy = testItemTable.getSelectedRow();
				if(xy>=0) {
					try{
						
						JFrame jFrame = new JFrame();
						jFrame.setAlwaysOnTop(true);
						
						
						int confirmed = JOptionPane.showConfirmDialog(jFrame, 
						        "Proceed to deliver laboratory test(s)?", "Laboratory Test Delivery.",
						        JOptionPane.YES_NO_OPTION);

						    if (confirmed == JOptionPane.YES_OPTION) {
						    	deliverResult();
								//showMessage("Delivery Successful!");
								
						    }		
							
						
						
								
						}catch(Exception ex) {ex.printStackTrace();}
					
				}else {
					showMessage("No item selected.");
				}
			}
		});
		btnDeliverTest.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnDeliverTest.setBounds(392, 402, 102, 23);
		test_items_panel.add(btnDeliverTest);
		
		JLabel lblNewLabel_1 = new JLabel("Patient name:");
		lblNewLabel_1.setBounds(10, 11, 80, 23);
		test_items_panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Gender:");
		lblNewLabel_2.setBounds(388, 13, 54, 19);
		test_items_panel.add(lblNewLabel_2);
		
		nameField = new JTextField();
		nameField.setBounds(100, 12, 248, 20);
		test_items_panel.add(nameField);
		nameField.setColumns(10);
		;
		
		genderField = new JTextField();
		genderField.setBounds(446, 12, 86, 20);
		test_items_panel.add(genderField);
		genderField.setColumns(10);
		
		JButton btnNewButton_2 = new JButton("Back");
		btnNewButton_2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(switch_panel, "panel_1");
			}
		});
		btnNewButton_2.setBounds(82, 402, 89, 23);
		test_items_panel.add(btnNewButton_2);
		
		
		JLabel lblNewLabel = new JLabel("Laboratory Request(s)");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel.setBounds(21, 11, 592, 26);
		contentPane.add(lblNewLabel);
		
		try {
			lab_request_list = lm_interface.getLabRequestSummary("active");
			updateOrderTable(lab_request_list);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//client method to confirm test orders. calls server method to update table
	public void confirmTestOrder() throws RemoteException{ 
		if(testItemTable.getRowCount()>0){
			selItemList.clear();
			if(testItemTable.getSelectedRowCount()>0){
				int selectrow[] = testItemTable.getSelectedRows();
				for (int i=0; i<selectrow.length; i++){
					selItemList.add(request_items_list.get(selectrow[i]).getTest_id());
				}
				
				lm_interface.updateOrderStatus(request_items_list.get(selectrow[0]).getInvoice_no(), selItemList);
				showMessage("Confirmed Successfully!");
				request_items_list = lm_interface.getOrderItems(request_items_list.get(selectrow[0]).getInvoice_no());
				updateTestItemTable(request_items_list);
				lab_request_list = lm_interface.getLabRequestSummary("active");
				updateOrderTable(lab_request_list);
				
			}
			
		}
		
		
		
	}
	
	//client method to deliver test results
	public void deliverResult(){
		if(testItemTable.getRowCount()>0){
			selItemList.clear();
			if(testItemTable.getSelectedRowCount()>0){
				int selectrow[] = testItemTable.getSelectedRows();	
				
				int xy = 0;
				
				for (int j=0; j<selectrow.length; j++){
					if(!(request_items_list.get(selectrow[j]).getStatus().equalsIgnoreCase("ready"))){
					xy += 1;
				}
				
				if (xy>0) {
					showMessage(xy + " selected test items are not ready.");
				}else {
					for (int i=0; i<selectrow.length; i++){
						selItemList.add(request_items_list.get(selectrow[i]).getTest_id());
					}
					
					try {
						lm_interface.updateToDelivery(request_items_list.get(0).getInvoice_no(), selItemList);
						request_items_list = lm_interface.getOrderItems(request_items_list.get(selectrow[0]).getInvoice_no());
						updateTestItemTable(request_items_list);
						lab_request_list = lm_interface.getLabRequestSummary("active");
						updateOrderTable(lab_request_list);
						showMessage("Delivery successful.");
						
					} catch (RemoteException e) {					
						e.printStackTrace();
					}
				}			
				
			}
			
		}
		}
	}
	
	//client method to update the table for all lab tests requests
	public void updateOrderTable(ArrayList<LabRequest> list){
		
		Object[][] data = new Object[list.size()][5];
		for(int i=0; i<list.size(); i++){ 
					
			 
			data[i][0] = ""+(i+1);
			data[i][1] = list.get(i).getInvoiceNo();
			data[i][2] = list.get(i).getSurname();
			data[i][3] = list.get(i).getOthernames();			
			data[i][4] = list.get(i).getNoOfTests();
			
		}
		
		Object[] columnNames = {"S/No","Invoice_no", "Surname", "Othernames", "No of Test(s)"};
		DefaultTableModel model = new DefaultTableModel(data, columnNames);
		labOrderTable.setModel(model);
		labOrderTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		labOrderTable.getColumnModel().getColumn(0).setPreferredWidth(40);
		labOrderTable.getColumnModel().getColumn(1).setPreferredWidth(100);
		labOrderTable.getColumnModel().getColumn(2).setPreferredWidth(150);
		labOrderTable .getColumnModel().getColumn(3).setPreferredWidth(250);
		labOrderTable .getColumnModel().getColumn(4).setPreferredWidth(80);
		labOrderTable.setModel(model); 
		repaint();
		revalidate();
	}
	
	//client method to update the table for test Items
	public void updateTestItemTable(ArrayList<TestOrderItem> list){
		
		Object[][] data = new Object[list.size()][6];
		for(int i=0; i<list.size(); i++){ 		
			 
			data[i][0] = ""+(i+1);
			data[i][1] = list.get(i).getInvoice_no();
			
			data[i][2] = list.get(i).getTest_name();
			data[i][3] = list.get(i).getDate();
			data[i][4] = list.get(i).getStatus();
			data[i][5] = list.get(i).getPrice();
			
		}
		
		Object[] columnNames = {"S/No","Invoice_no", "Test Name", "Date", "Status", "Price"};
		DefaultTableModel model = new DefaultTableModel(data, columnNames);
		testItemTable.setModel(model);
		testItemTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		testItemTable.getColumnModel().getColumn(0).setPreferredWidth(40);
		testItemTable.getColumnModel().getColumn(1).setPreferredWidth(90);
		testItemTable.getColumnModel().getColumn(2).setPreferredWidth(250);
		testItemTable .getColumnModel().getColumn(3).setPreferredWidth(80);
		testItemTable .getColumnModel().getColumn(4).setPreferredWidth(80);
		testItemTable .getColumnModel().getColumn(5).setPreferredWidth(80);
		
		testItemTable.setModel(model); 
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
