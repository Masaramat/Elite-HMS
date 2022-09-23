package com.ahms.pharmacymanagement;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JTable;

import com.ahms.api.PharmacyManagementInterface;
import com.ahms.clientinterface.InterfaceGenerator;
import com.ahms.pharmarcymgt.entities.PrescriptionInvoice;
import com.toedter.calendar.JDateChooser;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import javax.swing.SwingConstants;

public class PrescriptionOrder extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	
	ArrayList<PrescriptionInvoice> prescriptionList; 
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PrescriptionOrder frame = new PrescriptionOrder();
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
	public PrescriptionOrder() {
		setTitle("Elite HMS - Prescription Orders");
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(400, 150, 652, 490);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		PharmacyManagementInterface ph_interface = InterfaceGenerator.getPharmManagementInterface();
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 53, 614, 319);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				
			}
		});
		scrollPane.setViewportView(table);
		
		JButton btnClose = new JButton("Close");
		btnClose.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnClose.setBounds(535, 407, 89, 23);
		contentPane.add(btnClose);
		
		JButton btnDispense = new JButton("Go to dispensary");
		btnDispense.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnDispense.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int xy = table.getSelectedRow();
				if(xy<0){
					//do nothing
				}else{
					String invoice_no = prescriptionList.get(xy).getInvoiceNo();
					//System.out.println(invoice_no);
					try {
						DrugsDispensary ddp = new DrugsDispensary(invoice_no);
						ddp.setVisible(true);
						dispose();
					} catch (RemoteException e) {	e.printStackTrace();	}
					
				}
			}
		});
		btnDispense.setBounds(396, 407, 129, 23);
		contentPane.add(btnDispense);
		
		JLabel lblNewLabel = new JLabel("Prescription Orders");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel.setBounds(10, 11, 614, 29);
		contentPane.add(lblNewLabel);
		
		try {
			prescriptionList = ph_interface.getPrescriptionOrders("pending");
			updateTable(prescriptionList);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//prescriptionList = getPrescriptionOrders();		
	}
	
		
	//client method to update table
	public void updateTable(ArrayList<PrescriptionInvoice> list){
		Object[][] data = new Object[list.size()][7];
		for(int i=0; i<list.size(); i++){ 
			data[i][0] = (i+1);
			data[i][1] = list.get(i).getInvoiceNo();
			data[i][2] = list.get(i).getPatientSurname();			
			data[i][3] = list.get(i).getPatientOthernames();
			data[i][4] = list.get(i).getInvoiceDate();
			data[i][5] = list.get(i).getInvoiceStatus();
			data[i][6] = list.get(i).getNoOfItems();
			
		}
		
		Object[] columnNames = {"S/No", "Invoice No", "Surname ", "Othernames", "Date",  "Invoice status", "No of items"};
		
		DefaultTableModel model = new DefaultTableModel(data, columnNames);
		table.setModel(model);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getColumnModel().getColumn(0).setPreferredWidth(50);
		table.getColumnModel().getColumn(1).setPreferredWidth(100);
		table.getColumnModel().getColumn(2).setPreferredWidth(150);
		table.getColumnModel().getColumn(3).setPreferredWidth(250);
		table.getColumnModel().getColumn(4).setPreferredWidth(100);
		table.getColumnModel().getColumn(5).setPreferredWidth(100);
		table.getColumnModel().getColumn(6).setPreferredWidth(120);
		
		repaint();
		revalidate();
		
	}
}










	