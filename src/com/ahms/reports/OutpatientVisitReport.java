package com.ahms.reports;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.apache.commons.lang.time.DateUtils;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.SwingConstants;

import com.ahms.api.ReportsInterface;
import com.ahms.clientinterface.InterfaceGenerator;
import com.ahms.clinicmgt.entities.PatientVisit;
import com.ahms.labmgt.entities.TestOrderItem;
import com.toedter.calendar.JDateChooser;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.RemoteException;
import java.awt.event.ActionEvent;

public class OutpatientVisitReport extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JComboBox statusBox_1;
	
	private  ArrayList<String> column_list = new ArrayList<String>();
	private  ArrayList<String> data_list = new ArrayList<String>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OutpatientVisitReport frame = new OutpatientVisitReport();
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
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public OutpatientVisitReport() {
		setTitle("Elite HMS - Outpatient Visit Report");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(480, 150, 397, 389);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		final ReportsInterface rp_interface = InterfaceGenerator.getReportsInterface();
		
		JLabel lblNewLabel_1 = new JLabel("Outpatient Visit Report");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_1.setBounds(0, 0, 378, 36);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel = new JLabel("From Date");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel.setBounds(28, 57, 100, 21);
		contentPane.add(lblNewLabel);
		
		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setDateFormatString("dd-MM-yyyy");
		dateChooser.setDate(new Date());
		Date date = DateUtils.addYears(new Date(), -1);
		dateChooser.setMinSelectableDate(date);
		dateChooser.setMaxSelectableDate(new Date());
		dateChooser.setBounds(138, 58, 120, 20);
		contentPane.add(dateChooser);
		
		JDateChooser dateChooser_1 = new JDateChooser();
		dateChooser_1.setDateFormatString("dd-MM-yyyy");
		dateChooser_1.setDate(new Date());
		Date date2 = DateUtils.addYears(new Date(), -1);
		dateChooser_1.setMinSelectableDate(date2);
		dateChooser_1.setMaxSelectableDate(new Date());
		dateChooser_1.setBounds(138, 90, 120, 20);
		contentPane.add(dateChooser_1);
		
		JLabel lblToDate = new JLabel("To Date");
		lblToDate.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblToDate.setBounds(28, 89, 100, 21);
		contentPane.add(lblToDate);
		
		JLabel lblDoctor = new JLabel("Doctor");
		lblDoctor.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblDoctor.setBounds(28, 121, 100, 21);
		contentPane.add(lblDoctor);
		
		JComboBox userBox = new JComboBox();
		userBox.setModel(new DefaultComboBoxModel(new String[] {"All Doctors"}));
		userBox.setBounds(138, 121, 190, 22);
		contentPane.add(userBox);
		
		JComboBox statusBox = new JComboBox();
		statusBox.setModel(new DefaultComboBoxModel(new String[] {"All", "Active", "Discharged"}));
		statusBox.setBounds(137, 154, 150, 22);
		contentPane.add(statusBox);
		
		JLabel lblApplicationStatus = new JLabel("Visit Status");
		lblApplicationStatus.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblApplicationStatus.setBounds(28, 153, 100, 21);
		contentPane.add(lblApplicationStatus);
		
		JButton btnPreview = new JButton("Preview");
		btnPreview.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				column_list.clear();
				data_list.clear();
				
				Date fromDate = dateChooser.getDate();
				java.sql.Date sqlfromDate = new java.sql.Date(fromDate.getTime());
				
				Date toDate = dateChooser_1.getDate();
				java.sql.Date sqltoDate = new java.sql.Date(toDate.getTime());
				
				
				if(!(statusBox.getSelectedItem().toString().equalsIgnoreCase("All"))) {
					column_list.add("visit_status");
					data_list.add(statusBox.getSelectedItem().toString().toLowerCase());
				}
				if(!(statusBox_1.getSelectedItem().toString().equalsIgnoreCase("All"))) {
					column_list.add("emr_status");
					data_list.add(statusBox_1.getSelectedItem().toString().toLowerCase());
				}
				
				
				try {
					ArrayList<PatientVisit> listt = rp_interface.getOutpatientVisitReport(column_list, data_list, sqlfromDate, sqltoDate);
					if(listt.size() == 0) {
						JFrame jf = new JFrame();
						jf.setAlwaysOnTop(true);
						JOptionPane.showMessageDialog(jf, "No results found!");
					}else {
						ShowReportsFrame frame = new ShowReportsFrame("outpatient_visit", column_list, data_list, sqlfromDate, sqltoDate);
						frame.setVisible(true);
					}
					
				} catch (RemoteException e1) {
					
					e1.printStackTrace();
				}
			}
		});
		btnPreview.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnPreview.setBounds(148, 270, 100, 23);
		contentPane.add(btnPreview);
		
		JButton btnCancel = new JButton("Close");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				System.gc();
			}
		});
		btnCancel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnCancel.setBounds(258, 270, 100, 23);
		contentPane.add(btnCancel);
		
		JLabel lblConsStatus = new JLabel("Cons. Status");
		lblConsStatus.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblConsStatus.setBounds(28, 185, 100, 21);
		contentPane.add(lblConsStatus);
		
		statusBox_1 = new JComboBox();
		statusBox_1.setModel(new DefaultComboBoxModel(new String[] {"All", "Open", "Close"}));
		statusBox_1.setBounds(138, 187, 150, 22);
		contentPane.add(statusBox_1);
		
		addWindowListener(new WindowAdapter() {
			  public void windowClosing(WindowEvent e) {
			    System.gc();
			  }
			});
	}
}
