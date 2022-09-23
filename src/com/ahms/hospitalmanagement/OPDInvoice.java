package com.ahms.hospitalmanagement;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JTable;

import com.ahms.api.ClinicManagementInterface;
import com.ahms.api.HospitalManagementInterface;
import com.ahms.clientinterface.InterfaceGenerator;
import com.ahms.clinicmgt.entities.PatientVisit;
import com.ahms.hmgt.entities.OPInvoice;
import com.toedter.calendar.JDateChooser;
import java.awt.Dimension;
import javax.swing.JScrollPane;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.awt.event.ActionEvent;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import org.eclipse.jdt.internal.compiler.ast.IfStatement;

import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import javax.swing.SwingConstants;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class OPDInvoice extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	public static JTable table;
	
	public static String returned_status = "";
	
	
	
	
	private JButton btnSearch;
	private JButton btnAddBill;
	private JDateChooser dateChooser;
	private JDateChooser dateChooser_1;
	private JRadioButton rdbtnNotDischarged;
	private JRadioButton rdbtnDischarged;
	
	private ButtonGroup btnGroup = new ButtonGroup();
	private ArrayList<OPInvoice> invoice_list =  new ArrayList<OPInvoice>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OPDInvoice frame = new OPDInvoice();
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
	public OPDInvoice() {		
		setTitle("Elite HMS - Outpatient Invoice ");
		setResizable(false);
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(350, 100, 742, 546);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblFromDate = new JLabel("From Date");
		lblFromDate.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblFromDate.setBounds(18, 86, 69, 21);
		contentPane.add(lblFromDate);
		
		JLabel lblToDate = new JLabel("To Date");
		lblToDate.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblToDate.setBounds(244, 86, 69, 21);
		contentPane.add(lblToDate);
		
		final HospitalManagementInterface hm_interface = InterfaceGenerator.getHospitalManagementInterface();
		final ClinicManagementInterface cm_interface = InterfaceGenerator.getClinicManagementInterface();
		
		
		dateChooser = new JDateChooser();
		dateChooser.setEnabled(false);
		dateChooser.setPreferredSize(new Dimension(40, 20));
		dateChooser.setDateFormatString("yyyy-MM-dd");
		dateChooser.setDate(new Date());
		dateChooser.setBounds(78, 87, 106, 20);
		contentPane.add(dateChooser);
		
		dateChooser_1 = new JDateChooser();
		dateChooser_1.setEnabled(false);
		dateChooser_1.setPreferredSize(new Dimension(40, 20));
		dateChooser_1.setDateFormatString("yyyy-MM-dd");
		dateChooser_1.setDate(new Date());
		dateChooser_1.setBounds(290, 87, 106, 20);
		contentPane.add(dateChooser_1);
		
		JButton btnClose = new JButton("Close");
		btnClose.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			dispose();
			}
		});
		btnClose.setBounds(637, 483, 89, 23);
		contentPane.add(btnClose);
		
		rdbtnNotDischarged = new JRadioButton("Active");
		rdbtnNotDischarged.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					
					toggleButtons(false);
					invoice_list = hm_interface.getOPDinvoice("active");
					updateInvoiceTable(invoice_list);
				} catch (RemoteException e1) {					
					e1.printStackTrace();
				}
				
			}
		});
		rdbtnNotDischarged.setSelected(true);
		rdbtnNotDischarged.setFont(new Font("Tahoma", Font.PLAIN, 11));
		rdbtnNotDischarged.setBounds(18, 52, 80, 20);
		contentPane.add(rdbtnNotDischarged);
		
		JRadioButton rdbtnDischarged = new JRadioButton("Discharged");
		rdbtnDischarged.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				toggleButtons(true);
				invoice_list.clear();
				updateInvoiceTable(invoice_list);
			}
		});
		rdbtnDischarged.setFont(new Font("Tahoma", Font.PLAIN, 11));
		rdbtnDischarged.setBounds(108, 52, 109, 20);
		contentPane.add(rdbtnDischarged);
		
		btnGroup.add(rdbtnDischarged);
		btnGroup.add(rdbtnNotDischarged);
		
		
		btnSearch = new JButton("Search");
		btnSearch.setEnabled(false);
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String status = "";
				if(rdbtnNotDischarged.isSelected()){
					status = "active";
				}else if(rdbtnDischarged.isSelected()){
					status = "discharged";
				}
				
				java.util.Date date1 = sdf.parse(((JTextField)dateChooser.getDateEditor().getUiComponent()).getText());
				java.util.Date date2 = sdf.parse(((JTextField)dateChooser_1.getDateEditor().getUiComponent()).getText());
				
				invoice_list = hm_interface.getOPDinvoice(status, date1, date2);
				
				updateInvoiceTable(invoice_list);
				
				}catch(Exception ex){ ex.printStackTrace();}
								
			}
		});
		btnSearch.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnSearch.setBounds(426, 86, 69, 20);
		contentPane.add(btnSearch);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(18, 131, 708, 315);
		contentPane.add(scrollPane);
		
		JPopupMenu popupMenu = new JPopupMenu();
		
		
		JMenuItem mnItem1 = new JMenuItem("Add  Hospital Bill      ");
		mnItem1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int row = table.getSelectedRow();
				String value = invoice_list.get(row).getInvoiceNo();
				String value2 = invoice_list.get(row).getSurname() +" "+ invoice_list.get(row).getOthernames();
				
				try {
					GeneratePatientBill pb = new  GeneratePatientBill("out", value, value2, "", invoice_list.get(row).getVisitStatus());
					pb.setVisible(true);
				} catch (RemoteException e) {					
					e.printStackTrace();
				}
				
			}
			});
		popupMenu.add(mnItem1);
		
		JMenuItem mnItem2 = new JMenuItem("Make Payment");
		mnItem2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int row = table.getSelectedRow();								 
				try {
					OPDBusinessOffice pb = new  OPDBusinessOffice(invoice_list.get(row));
					pb.setVisible(true);
				} catch (Exception e) {
					
					e.printStackTrace();
				}
				
			}
			});
		popupMenu.add(mnItem2);
		
		JMenuItem mnItem3 = new JMenuItem("Preview");
		mnItem3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int xy = table.getSelectedRow();
				if(xy<0){
					showMessage("No item selected.");
				}else{
					String invoice_no = invoice_list.get(table.getSelectedRow()).getInvoiceNo();
					OPDInvoiceReportFrame view = new OPDInvoiceReportFrame(invoice_no);
					view.setVisible(true);
					//System.out.println(invoice_no);
				}
				
			}
			});
		popupMenu.add(mnItem3);
		
		
		table = new JTable();
		table.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				try {
					if(rdbtnDischarged.isSelected()) {
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						java.util.Date date1 = sdf.parse(((JTextField)dateChooser.getDateEditor().getUiComponent()).getText());
						java.util.Date date2 = sdf.parse(((JTextField)dateChooser_1.getDateEditor().getUiComponent()).getText());
						
						invoice_list = hm_interface.getOPDinvoice("discharged", date1, date2);
						
						updateInvoiceTable(invoice_list);
					}else {
						invoice_list = hm_interface.getOPDinvoice("active");
						updateInvoiceTable(invoice_list);
					}
					
				} catch (RemoteException e1) {					
					e1.printStackTrace();
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		table.setComponentPopupMenu(popupMenu);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent evt) {
				Point p = evt.getPoint();
				int currentRow = table.rowAtPoint(p);
				table.setRowSelectionInterval(currentRow, currentRow);
			}
		});
		
		
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int xy = table.getSelectedRow();
				
					try {
						if(xy<0) {
							
						}else {
							PatientVisit pVisit = cm_interface.getPatientVisit(invoice_list.get(xy).getInvoiceNo());
							if(pVisit.getEmrSratus().equalsIgnoreCase("closed")) {
								btnAddBill.setEnabled(false);
							}else {
								btnAddBill.setEnabled(true);
							}
						}					
						
					} catch (Exception e) {
						e.printStackTrace();
					}				
				
			}
		});
		scrollPane.setViewportView(table);
		
		JButton btnPrint = new JButton("Preview");
		btnPrint.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnPrint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int xy = table.getSelectedRow();
				if(xy<0){
					showMessage("No item selected.");
				}else{
					String invoice_no = invoice_list.get(table.getSelectedRow()).getInvoiceNo();
					OPDInvoiceReportFrame view = new OPDInvoiceReportFrame(invoice_no);
					view.setVisible(true);
					//System.out.println(invoice_no);
				}
			}
		});
		btnPrint.setBounds(538, 483, 89, 23);
		contentPane.add(btnPrint);
		
		btnAddBill = new JButton("Add Bill");
		btnAddBill.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnAddBill.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				int xy = table.getSelectedRow();
				if(xy < 0){
					showMessage("No item selected.");
				}else{
					int row = table.getSelectedRow();
					String value = invoice_list.get(row).getInvoiceNo();
					String value2 = invoice_list.get(row).getSurname() +" "+ invoice_list.get(row).getOthernames();
					
					try {
						GeneratePatientBill pb = new  GeneratePatientBill("out", value, value2, "", invoice_list.get(row).getVisitStatus());
						pb.setVisible(true);
					} catch (RemoteException e) {
						
						e.printStackTrace();
					}
				}
			}
		});
		btnAddBill.setBounds(323, 483, 89, 23);
		contentPane.add(btnAddBill);
		
		JButton btnMakePayment = new JButton("Make Payment");
		btnMakePayment.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnMakePayment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				int xy = table.getSelectedRow();
				if(xy < 0){
					showMessage("No item selected.");
				}else{
					int row = table.getSelectedRow();								 
					try {
						OPDBusinessOffice pb = new  OPDBusinessOffice(invoice_list.get(row));
						pb.setVisible(true);
					} catch (Exception e) {
						
						e.printStackTrace();
					}
				}
			}
		});
		btnMakePayment.setBounds(422, 483, 106, 23);
		contentPane.add(btnMakePayment);
		
		JLabel lblNewLabel = new JLabel("Outpatient Invoice");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel.setBounds(18, 11, 643, 26);
		contentPane.add(lblNewLabel);
		
		try {
			invoice_list = hm_interface.getOPDinvoice("active");
			updateInvoiceTable(invoice_list);
		} catch (RemoteException e1) {					
			e1.printStackTrace();
		}
		
		
		addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				
				try {
					if(returned_status.equalsIgnoreCase("active")) {
						invoice_list = hm_interface.getOPDinvoice("active");
						updateInvoiceTable(invoice_list);
					}else if (returned_status.equalsIgnoreCase("discharged")) {
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						java.util.Date date1 = sdf.parse(((JTextField)dateChooser.getDateEditor().getUiComponent()).getText());
						java.util.Date date2 = sdf.parse(((JTextField)dateChooser_1.getDateEditor().getUiComponent()).getText());
						
						invoice_list = hm_interface.getOPDinvoice(returned_status, date1, date2);
						
						updateInvoiceTable(invoice_list);
					}
					
				} catch (RemoteException | ParseException e1) {					
					e1.printStackTrace();
				}
			}
		});
		
	}
	
	//client method to display opd invoice table
	public void updateInvoiceTable(ArrayList<OPInvoice> list){
		
		Object[][] data = new Object[list.size()][12];
		for(int i=0; i<list.size(); i++){ 
			data[i][0] = (i+1);
			data[i][1] = list.get(i).getInvoiceNo();			
			data[i][2] = list.get(i).getSurname()+" "+ list.get(i).getOthernames();	
			data[i][3] = list.get(i).getClinicDue();
			data[i][4] = list.get(i).getProcedureDue();
			data[i][5] = list.get(i).getLaboratoryDue();
			data[i][6] = list.get(i).getPharmacyDue();
			data[i][7] = list.get(i).getTotalReceivable();
			data[i][8] = list.get(i).getTotalDeposit();
			data[i][9] = list.get(i).getTotalDue();	
			data[i][10] = list.get(i).getInvoiceDate();
			data[i][11] = list.get(i).getVisitStatus();					
		}
		
		Object[] columnNames = {"S/No","Invoice No", "Patient Name", "Clinic", "Procedure", "Laboratory", "Pharmacy", "Total Due", "Amount Paid", "Balance", "Invoice Date", "Visit Status" };
		
		DefaultTableModel model = new DefaultTableModel(data, columnNames);
		table.setModel(model);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getColumnModel().getColumn(0).setPreferredWidth(30);
		table.getColumnModel().getColumn(1).setPreferredWidth(70);
		table.getColumnModel().getColumn(2).setPreferredWidth(150);
		table.getColumnModel().getColumn(3).setPreferredWidth(80);
		table.getColumnModel().getColumn(4).setPreferredWidth(80);
		table.getColumnModel().getColumn(5).setPreferredWidth(80);
		table.getColumnModel().getColumn(6).setPreferredWidth(80);
		table.getColumnModel().getColumn(7).setPreferredWidth(80);
		table.getColumnModel().getColumn(8).setPreferredWidth(80);
		table.getColumnModel().getColumn(9).setPreferredWidth(80);
		table.getColumnModel().getColumn(10).setPreferredWidth(80);
		table.getColumnModel().getColumn(11).setPreferredWidth(80);
		
		repaint();
		revalidate();
	}
	
	public void toggleButtons(boolean status) {
		btnSearch.setEnabled(status);
		dateChooser.setEnabled(status);
		dateChooser_1.setEnabled(status);
	}	
	
	
	public static void showMessage(String message){
		final JFrame dialog = new JFrame();
		dialog.setAlwaysOnTop(true);
		JOptionPane.showMessageDialog(dialog, message);
	}	
}
