package com.ahms.hospitalmanagement;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import java.awt.Font;
import java.awt.Point;

import com.ahms.api.ClinicManagementInterface;
import com.ahms.api.HospitalManagementInterface;
import com.ahms.clientinterface.InterfaceGenerator;
import com.ahms.clinicmgt.entities.PatientVisit;
import com.ahms.hmgt.entities.IPInvoice;
import com.toedter.calendar.JDateChooser;
import java.awt.Dimension;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import javax.swing.SwingConstants;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class IPDInvoice extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTable table_1;
	
	public static String returned_status = "";
	
	private JButton button;
	private JButton btnAddBill;
	private JDateChooser dateChooser;
	private JDateChooser dateChooser_1;
	
	private ArrayList<IPInvoice> invoice_list;

	ButtonGroup bg = new ButtonGroup();
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					IPDInvoice frame = new IPDInvoice();
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
	public IPDInvoice() {
		setTitle("Elite HMS - Inpatient Invoice");
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(400, 100, 649, 529 );
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		final HospitalManagementInterface hm_interface = InterfaceGenerator.getHospitalManagementInterface();
		final ClinicManagementInterface cm_interface = InterfaceGenerator.getClinicManagementInterface();
		
		JLabel label_6 = new JLabel("From Date");
		label_6.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label_6.setBounds(17, 80, 69, 21);
		contentPane.add(label_6);
		
		dateChooser = new JDateChooser();
		dateChooser.setPreferredSize(new Dimension(40, 20));
		dateChooser.setDateFormatString("yyyy-MM-dd");
		dateChooser.setDate(new Date());
		dateChooser.setBounds(86, 80, 109, 20);
		contentPane.add(dateChooser);
		
		JLabel label_7 = new JLabel("To Date");
		label_7.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label_7.setBounds(233, 80, 61, 21);
		contentPane.add(label_7);
		
		dateChooser_1 = new JDateChooser();
		dateChooser_1.setPreferredSize(new Dimension(40, 20));
		dateChooser_1.setDateFormatString("yyyy-MM-dd");
		dateChooser_1.setDate(new Date());
		dateChooser_1.setBounds(283, 80, 104, 20);
		contentPane.add(dateChooser_1);
		
		JRadioButton rdbtnActive = new JRadioButton("Active");
		rdbtnActive.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				toggleButtons(false);
				
				try {
					invoice_list = hm_interface.getIPDInvoice("active");
					updateInvoiceTable(invoice_list);
				} catch (RemoteException e1) {					
					e1.printStackTrace();
				}
			}
		});
		rdbtnActive.setSelected(true);
		rdbtnActive.setFont(new Font("Tahoma", Font.PLAIN, 11));
		rdbtnActive.setBounds(17, 51, 83, 21);
		contentPane.add(rdbtnActive);
		
		JRadioButton rdbtnDischarged = new JRadioButton("Discharged");
		rdbtnDischarged.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				toggleButtons(true);
			
				try {
					invoice_list.clear();
					updateInvoiceTable(invoice_list);
				} catch (Exception e1) {					
					e1.printStackTrace();
				}
			}
		});
		rdbtnDischarged.setFont(new Font("Tahoma", Font.PLAIN, 11));
		rdbtnDischarged.setBounds(102, 50, 109, 23);
		contentPane.add(rdbtnDischarged);
		bg.add(rdbtnActive);
		bg.add(rdbtnDischarged);
		
		button = new JButton(">>>");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try{
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					String status = "";
					if(rdbtnActive.isSelected()){
						status = "active";
					}else if(rdbtnDischarged.isSelected()){
						status = "discharged";
					}
					
					java.util.Date date1 = sdf.parse(((JTextField)dateChooser.getDateEditor().getUiComponent()).getText());
					java.util.Date date2 = sdf.parse(((JTextField)dateChooser_1.getDateEditor().getUiComponent()).getText());
					
					invoice_list = hm_interface.getIPDInvoice(status, date1, date2);
					updateInvoiceTable(invoice_list);
					
					}catch(Exception ex){ ex.printStackTrace();}
			}
		});
		button.setFont(new Font("Tahoma", Font.PLAIN, 11));
		button.setBounds(420, 78, 69, 23);
		contentPane.add(button);
		
		
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(17, 125, 606, 278);
		contentPane.add(scrollPane);
		
		table = new JTable();
		JPopupMenu popupMenu = new JPopupMenu();
		
		JMenuItem mnItem1 = new JMenuItem("Add  Hospital Bill      ");
		mnItem1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int row = table.getSelectedRow();
				String value = invoice_list.get(row).getInvoiceNo();
				String value2 = invoice_list.get(row).getSurname() +" "+ invoice_list.get(row).getOthernames();
				
				GeneratePatientBill pb;
				try {
					pb = new  GeneratePatientBill("in", value, value2, "", invoice_list.get(row).getAdmissionStatus());
					pb.setVisible(true);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			});
		popupMenu.add(mnItem1);
		

		JMenuItem mnItem2 = new JMenuItem("Make Payment      ");
		mnItem2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int row = table.getSelectedRow();
							
				try {
					IPDBusinessOffice	pb = new  IPDBusinessOffice(invoice_list.get(row));
					pb.setVisible(true);
				} catch (Exception e) {
					
					e.printStackTrace();
				}
				
			}
			});
		popupMenu.add(mnItem2);
		
		
		
		
		table_1 = new JTable();
		table_1.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				
				try {
					if (returned_status.equalsIgnoreCase("active")) {
						try {
							invoice_list = hm_interface.getIPDInvoice("active");
							updateInvoiceTable(invoice_list);
						} catch (RemoteException e1) {					
							e1.printStackTrace();
						}
					}else if (returned_status.equalsIgnoreCase("discharged")) {
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						java.util.Date date1 = sdf.parse(((JTextField)dateChooser.getDateEditor().getUiComponent()).getText());
						java.util.Date date2 = sdf.parse(((JTextField)dateChooser_1.getDateEditor().getUiComponent()).getText());
						
						invoice_list = hm_interface.getIPDInvoice(returned_status, date1, date2);
						updateInvoiceTable(invoice_list);
					}
					
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				
			}
		});
		table_1.setComponentPopupMenu(popupMenu);
		table_1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent evt) {
				Point p = evt.getPoint();
				int currentRow = table_1.rowAtPoint(p);
				table_1.setRowSelectionInterval(currentRow, currentRow);
			}
		});
		
		table_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int xy = table_1.getSelectedRow();
				if(xy < 0){
					showMessage("No item selected");
				}else{
					try {
						PatientVisit pVisit = cm_interface.getPatientVisit(invoice_list.get(xy).getInvoiceNo());
						if(pVisit.getEmrSratus().equalsIgnoreCase("closed")) {
							btnAddBill.setEnabled(false);
						}else {
							btnAddBill.setEnabled(true);
						}
						
												
						double admDue = Double.parseDouble(invoice_list.get(xy).getAdmissionDues());
						double clinicDue = Double.parseDouble(invoice_list.get(xy).getClinicDues());
						double procedureDue = Double.parseDouble(invoice_list.get(xy).getProcedureDues());
						double LabDue = Double.parseDouble(invoice_list.get(xy).getLaboratoryDues());
						double pharmacyDue = Double.parseDouble(invoice_list.get(xy).getPharmacyDues());
						
						double totalDue = clinicDue + procedureDue + LabDue + pharmacyDue + admDue;						
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
		scrollPane.setViewportView(table_1);
		
		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			dispose();
			}
		});
		btnClose.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnClose.setBounds(519, 455, 104, 23);
		contentPane.add(btnClose);
		
		JButton btnPrint = new JButton("Print");
		btnPrint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int xy = table_1.getSelectedRow();
				if(xy<0){
					//no item selected
				}else{
					String invoice_no = (String) table_1.getValueAt(xy, 0);
					IPDInvoiceReportFrame view = new IPDInvoiceReportFrame(invoice_no);
					view.setVisible(true);
					//System.out.println(invoice_no);
				}
			}
		});
		btnPrint.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnPrint.setBounds(420, 455, 89, 23);
		contentPane.add(btnPrint);
		
		btnAddBill = new JButton("Add Bill");
		btnAddBill.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				int xy = table_1.getSelectedRow();
				if(xy < 0){
					showMessage("No item selected.");
				}else{
					int row = table_1.getSelectedRow();
					String value = invoice_list.get(row).getInvoiceNo();
					String value2 = invoice_list.get(row).getSurname() +" "+ invoice_list.get(row).getOthernames();
					
					try {
						GeneratePatientBill pb = new  GeneratePatientBill("in", value, value2, "", invoice_list.get(row).getAdmissionStatus());
						pb.setVisible(true);
						dispose();
						System.gc();
					} catch (RemoteException e) {
						
						e.printStackTrace();
					}
				}
			}
		});
		btnAddBill.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnAddBill.setBounds(205, 455, 89, 23);
		contentPane.add(btnAddBill);
		
		JButton btnMakePayment = new JButton("Make Payment");
		btnMakePayment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				int xy = table_1.getSelectedRow();
				if(xy < 0){
					showMessage("No item selected.");
				}else{
					int row = table_1.getSelectedRow();								 
					try {
						IPDBusinessOffice pb = new  IPDBusinessOffice(invoice_list.get(row));
						pb.setVisible(true);
						dispose();
						System.gc();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		btnMakePayment.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnMakePayment.setBounds(304, 455, 106, 23);
		contentPane.add(btnMakePayment);
		
		JLabel lblInpatientInvoice = new JLabel("Inpatient Invoice");
		lblInpatientInvoice.setHorizontalAlignment(SwingConstants.CENTER);
		lblInpatientInvoice.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblInpatientInvoice.setBounds(17, 11, 606, 26);
		contentPane.add(lblInpatientInvoice);
		
		try {
			invoice_list = hm_interface.getIPDInvoice("active");
			updateInvoiceTable(invoice_list);
		} catch (RemoteException e1) {					
			e1.printStackTrace();
		}
		
		toggleButtons(false);
		
	}
	
	
	
	public void toggleButtons(boolean status) {
		button.setEnabled(status);
		dateChooser.setEnabled(status);
		dateChooser_1.setEnabled(status);
	}
	
	
	//client method to display opd invoice table
		public void updateInvoiceTable(ArrayList<IPInvoice> list){
			
			Object[][] data = new Object[list.size()][14];
			for(int i=0; i<list.size(); i++){ 
				data[i][0] = (i+1);
				data[i][1] = list.get(i).getInvoiceNo();
				data[i][12] = list.get(i).getAdmissionDate();
				data[i][2] = list.get(i).getSurname() + " " + list.get(i).getOthernames();
				data[i][3] = list.get(i).getGender();
				data[i][4] = list.get(i).getClinicDues();
				data[i][5] = list.get(i).getProcedureDues();
				data[i][6] = list.get(i).getLaboratoryDues();
				data[i][7] = list.get(i).getPharmacyDues();
				data[i][8] = list.get(i).getAdmissionDues();
				data[i][9] = list.get(i).getTotalReceivable();
				data[i][10] = list.get(i).getTotalDeposit();
				data[i][11] = list.get(i).getTotalDue();
				data[i][13] = list.get(i).getAdmissionStatus();					
			}
			
			Object[] columnNames = {"S/No", "Invoice No", "Patient Name", "Gender", "Clinic Fee", "Procedure", "Laboratory Fee","Pharmacy Fee", "Admission Fee", "Total Due", "Amount Paid", "Balance", "Adm Date", "Adm Status" };
			
			DefaultTableModel model = new DefaultTableModel(data, columnNames);
			table_1.setModel(model);
			table_1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			table_1.getColumnModel().getColumn(0).setPreferredWidth(40);
			table_1.getColumnModel().getColumn(1).setPreferredWidth(70);
			table_1.getColumnModel().getColumn(2).setPreferredWidth(150);
			table_1.getColumnModel().getColumn(3).setPreferredWidth(60);
			table_1.getColumnModel().getColumn(4).setPreferredWidth(80);
			table_1.getColumnModel().getColumn(5).setPreferredWidth(80);
			table_1.getColumnModel().getColumn(6).setPreferredWidth(80);
			table_1.getColumnModel().getColumn(7).setPreferredWidth(80);
			table_1.getColumnModel().getColumn(8).setPreferredWidth(80);
			table_1.getColumnModel().getColumn(9).setPreferredWidth(80);
			table_1.getColumnModel().getColumn(10).setPreferredWidth(80);
			table_1.getColumnModel().getColumn(11).setPreferredWidth(80);
			table_1.getColumnModel().getColumn(12).setPreferredWidth(80);
			table_1.getColumnModel().getColumn(13).setPreferredWidth(80);
			
			repaint();
			revalidate();
			
		}
	
		public static void showMessage(String message){
			final JFrame dialog = new JFrame();
			dialog.setAlwaysOnTop(true);
			JOptionPane.showMessageDialog(dialog, message);
		}
	
}
