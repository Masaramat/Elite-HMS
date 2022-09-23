package com.ahms.pharmacymanagement;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Point;

import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import com.ahms.api.PharmacyManagementInterface;
import com.ahms.clientinterface.InterfaceGenerator;
import com.ahms.clinicmanagement.OPDConsultation;
import com.ahms.pharmarcymgt.entities.PrescriptionInvoice;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class PharmacyManagement extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	public static JTable main_table;

	ArrayList<PrescriptionInvoice> prescriptionList; 
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PharmacyManagement frame = new PharmacyManagement();
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
	public PharmacyManagement() {
		setTitle("Absolute Hospital Management System(AHMS)                                                               Pharmacy Management Module");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(50, 10, 1300, 730);
		
		PharmacyManagementInterface ph_interface = InterfaceGenerator.getPharmManagementInterface();
		
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(new Color(0, 128, 128));
		setJMenuBar(menuBar);
		
		JMenu mnMain = new JMenu("  Main  ");
		mnMain.setForeground(new Color(255, 255, 255));
		menuBar.add(mnMain);
		
		JMenuItem mntmOpcPrescriptionOrder = new JMenuItem("Open Prescription Orders");
		mntmOpcPrescriptionOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				PrescriptionOrder frame = new PrescriptionOrder();
				frame.setVisible(true);
			}
		});
		mnMain.add(mntmOpcPrescriptionOrder);
		
		JMenuItem mntmMedicinePurchase = new JMenuItem("Add Medicine Purchase");
		mntmMedicinePurchase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			try{
				 MedicinePurchase frame = new MedicinePurchase(); 
				 frame.setAlwaysOnTop(true);
				 frame.setVisible(true);
			}catch(Exception ex){ex.printStackTrace();}
			
			}
		});
		
		JMenuItem mntmMedicineMaster = new JMenuItem("New Medicine Name");
		mnMain.add(mntmMedicineMaster);
		mntmMedicineMaster.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			try{
				MedicineEntry frame = new MedicineEntry();
				frame.setAlwaysOnTop(true);
				frame.setVisible(true);
			}catch(Exception ex){ex.printStackTrace();}
			}
		});
		mnMain.add(mntmMedicinePurchase);
		
		JMenuItem mntmPharmacyStockWithdrawal = new JMenuItem("Withdraw Medicine from Stock");
		mnMain.add(mntmPharmacyStockWithdrawal);
		
		JMenuItem mntmPharmacyCurrentStock = new JMenuItem("Open Pharmacy Stock Control");
		mnMain.add(mntmPharmacyCurrentStock);
		
		JMenu mnReports = new JMenu("  Reports  ");
		mnReports.setForeground(new Color(255, 255, 255));
		menuBar.add(mnReports);
		
		JMenuItem mntmOutpatientDispensationReport = new JMenuItem("Dispensed Drugs Report");
		mnReports.add(mntmOutpatientDispensationReport);
		
		JMenuItem mntmMedicinePurchaseReport = new JMenuItem("Drugs Purchase Report");
		mnReports.add(mntmMedicinePurchaseReport);
		
		JMenuItem mntmPharmacyStockWithdrawal_1 = new JMenuItem("Drugs Withdrawal Report");
		mnReports.add(mntmPharmacyStockWithdrawal_1);
		
		JMenu mnSetupUtility = new JMenu("  Setup");
		mnSetupUtility.setForeground(new Color(255, 255, 255));
		menuBar.add(mnSetupUtility);
		
		JMenuItem mntmUserProfile = new JMenuItem("Change Password");
		mnSetupUtility.add(mntmUserProfile);
		
		JMenuItem mntmLogout = new JMenuItem("Logout");
		mnSetupUtility.add(mntmLogout);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(0, 132, 1284, 30);
		panel_1.setLayout(null);
		panel_1.setBackground(new Color(0, 139, 139));
		contentPane.add(panel_1);
		
		JLabel label_8 = new JLabel();
		label_8.setForeground(Color.WHITE);
		label_8.setFont(new Font("Tahoma", Font.BOLD, 12));
		label_8.setBounds(36, 3, 508, 22);
		panel_1.add(label_8);
		
		JButton button = new JButton("   Logout    ");
		button.setForeground(Color.YELLOW);
		button.setFont(new Font("Tahoma", Font.BOLD, 12));
		button.setBackground(new Color(0, 139, 139));
		button.setBounds(1157, 2, 117, 23);
		panel_1.add(button);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 1284, 130);
		panel.setLayout(null);
		panel.setBackground(new Color(245, 245, 245));
		contentPane.add(panel);
		
		JLabel lblSurgicareHospitalLtd = new JLabel("Surgicare Hospital Ltd. \r\n");
		lblSurgicareHospitalLtd.setHorizontalAlignment(SwingConstants.LEFT);
		lblSurgicareHospitalLtd.setForeground(new Color(0, 128, 128));
		lblSurgicareHospitalLtd.setFont(new Font("Sitka Heading", Font.BOLD, 26));
		lblSurgicareHospitalLtd.setBounds(158, 11, 609, 46);
		panel.add(lblSurgicareHospitalLtd);
		
		JLabel lblPlots = new JLabel("Plot 850, 1(S) Road, Federal Housing Avenue, Lugbe, Abuja.   ");
		lblPlots.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblPlots.setBounds(158, 59, 437, 17);
		panel.add(lblPlots);
		
		JLabel label_2 = new JLabel("www.surgicarehospital.org");
		label_2.setForeground(Color.BLUE);
		label_2.setFont(new Font("Tahoma", Font.ITALIC, 13));
		label_2.setBounds(158, 78, 244, 17);
		panel.add(label_2);
		
		JLabel label_3 = new JLabel(" 07030914926, 09074006043");
		label_3.setFont(new Font("Tahoma", Font.PLAIN, 13));
		label_3.setBounds(158, 102, 244, 17);
		panel.add(label_3);
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setColumns(10);
		textField.setBounds(10, 8, 138, 111);
		panel.add(textField);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(10, 173, 271, 486);
		panel_2.setBorder(new LineBorder(new Color(0, 0, 0)));
		contentPane.add(panel_2);
		panel_2.setLayout(null);
		
		JLabel lblPanseOptimalComputers = new JLabel("Panse Optimal Computers Ltd\r\n");
		lblPanseOptimalComputers.setForeground(Color.BLACK);
		lblPanseOptimalComputers.setFont(new Font("Footlight MT Light", Font.BOLD | Font.ITALIC, 13));
		lblPanseOptimalComputers.setBounds(10, 406, 232, 22);
		panel_2.add(lblPanseOptimalComputers);
		
		JLabel lblSupportabsolutehmsgmailcom = new JLabel("support.absolutehms@gmail.com   ");
		lblSupportabsolutehmsgmailcom.setForeground(Color.BLACK);
		lblSupportabsolutehmsgmailcom.setFont(new Font("Footlight MT Light", Font.ITALIC, 13));
		lblSupportabsolutehmsgmailcom.setBounds(10, 427, 232, 22);
		panel_2.add(lblSupportabsolutehmsgmailcom);
		
		JLabel label_9 = new JLabel("08097469346");
		label_9.setFont(new Font("Footlight MT Light", Font.BOLD | Font.ITALIC, 13));
		label_9.setBounds(10, 449, 232, 26);
		panel_2.add(label_9);
		
		JLabel lblPoweredBy = new JLabel("Powered by ");
		lblPoweredBy.setFont(new Font("Footlight MT Light", Font.BOLD | Font.ITALIC, 13));
		lblPoweredBy.setBounds(10, 378, 198, 22);
		panel_2.add(lblPoweredBy);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(291, 203, 983, 330);
		contentPane.add(scrollPane);
		
		JPopupMenu popupMenu = new JPopupMenu();
		
		JMenuItem mnItem1 = new JMenuItem("Go to Dispensary   ");
		mnItem1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int row = main_table.getSelectedRow();
				String value = main_table.getModel().getValueAt(row, 1).toString();
				
				try {
					DrugsDispensary frame = new DrugsDispensary(value);
					frame.setVisible(true);
										
				} catch (Exception e) {		e.printStackTrace();	}
			}
			});
		popupMenu.add(mnItem1);
		
		main_table = new JTable();
		main_table.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				try {
					prescriptionList = ph_interface.getPrescriptionOrders("pending");
					updateTable(prescriptionList);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
		main_table.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent evt) {
				Point p = evt.getPoint();
				int currentRow = main_table.rowAtPoint(p);
				main_table.setRowSelectionInterval(currentRow, currentRow);
			}
		});
		main_table.setComponentPopupMenu(popupMenu);
		main_table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(main_table);
		
		
		try {
			prescriptionList = ph_interface.getPrescriptionOrders("pending");
			updateTable(prescriptionList);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
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
			main_table.setModel(model);
			main_table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			main_table.getColumnModel().getColumn(0).setPreferredWidth(80);
			main_table.getColumnModel().getColumn(1).setPreferredWidth(150);
			main_table.getColumnModel().getColumn(2).setPreferredWidth(190);
			main_table.getColumnModel().getColumn(3).setPreferredWidth(290);
			main_table.getColumnModel().getColumn(4).setPreferredWidth(120);
			main_table.getColumnModel().getColumn(5).setPreferredWidth(120);
			main_table.getColumnModel().getColumn(6).setPreferredWidth(120);
			
			repaint();
			revalidate();
			
		}
		
	
	
	
	
	
	
	
	
	

}
