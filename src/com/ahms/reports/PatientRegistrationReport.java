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
import com.ahms.hmgt.entities.PatientBiodata;
import com.ahms.labmgt.entities.TestOrderItem;
import com.toedter.calendar.JDateChooser;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.awt.event.ActionEvent;

public class PatientRegistrationReport extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	private  ArrayList<String> column_list = new ArrayList<String>();
	private  ArrayList<String> data_list = new ArrayList<String>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PatientRegistrationReport frame = new PatientRegistrationReport();
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
	public PatientRegistrationReport() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(450, 150, 421, 372);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		final ReportsInterface rp_interface = InterfaceGenerator.getReportsInterface();
		
		JLabel lblPatientRegistrationReport = new JLabel("Patient Registration Report");
		lblPatientRegistrationReport.setHorizontalAlignment(SwingConstants.CENTER);
		lblPatientRegistrationReport.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblPatientRegistrationReport.setBounds(10, 11, 385, 25);
		contentPane.add(lblPatientRegistrationReport);
		
		JLabel label_1 = new JLabel("From Date");
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label_1.setBounds(35, 56, 100, 21);
		contentPane.add(label_1);
		
		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setDateFormatString("dd-MM-yyyy");
		dateChooser.setDate(new Date());
		Date date = DateUtils.addYears(new Date(), -1);
		dateChooser.setMinSelectableDate(date);
		dateChooser.setMaxSelectableDate(new Date());
		dateChooser.setBounds(145, 57, 120, 20);
		contentPane.add(dateChooser);
		
		JLabel label_2 = new JLabel("To Date");
		label_2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label_2.setBounds(35, 88, 100, 21);
		contentPane.add(label_2);
		
		JDateChooser dateChooser_1 = new JDateChooser();
		dateChooser_1.setDateFormatString("dd-MM-yyyy");
		dateChooser_1.setDate(new Date());
		Date date2 = DateUtils.addYears(new Date(), -1);
		dateChooser_1.setMinSelectableDate(date2);
		dateChooser_1.setMaxSelectableDate(new Date());
		dateChooser_1.setBounds(145, 89, 120, 20);
		contentPane.add(dateChooser_1);
		
		JLabel lblRegOfficer = new JLabel("Reg. Officer");
		lblRegOfficer.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblRegOfficer.setBounds(35, 120, 100, 21);
		contentPane.add(lblRegOfficer);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"All Users"}));
		comboBox.setBounds(145, 120, 150, 22);
		contentPane.add(comboBox);
		
		JButton button = new JButton("Preview");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				column_list.clear();
				data_list.clear();
				
				Date fromDate = dateChooser.getDate();
				java.sql.Date sqlfromDate = new java.sql.Date(fromDate.getTime());
				
				Date toDate = dateChooser_1.getDate();
				java.sql.Date sqltoDate = new java.sql.Date(toDate.getTime());
				
				
				if(!(comboBox.getSelectedItem().toString().equalsIgnoreCase("All users"))) {
					column_list.add("reg_offr");
					data_list.add(comboBox.getSelectedItem().toString().toLowerCase());
				}
				
				
				try {
					ArrayList<PatientBiodata> listt = rp_interface.getPatientRegistrationReport(column_list, data_list, sqlfromDate, sqltoDate);
					if(listt.size() == 0) {
						JFrame jf = new JFrame();
						jf.setAlwaysOnTop(true);
						JOptionPane.showMessageDialog(jf, "No results found!");
					}else {
						ShowReportsFrame frame = new ShowReportsFrame("patient_reg_report", column_list, data_list, sqlfromDate, sqltoDate);
						frame.setVisible(true);
					}
					
				} catch (RemoteException e1) {				
					e1.printStackTrace();
				}
			}
		});
		button.setFont(new Font("Tahoma", Font.PLAIN, 11));
		button.setBounds(169, 296, 100, 23);
		contentPane.add(button);
		
		JButton button_1 = new JButton("Close");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				System.gc();
			}
		});
		button_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		button_1.setBounds(279, 296, 100, 23);
		contentPane.add(button_1);
	}
}
