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
import com.ahms.clinicmgt.entities.InpatientAdmission;
import com.toedter.calendar.JDateChooser;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.RemoteException;
import java.awt.event.ActionEvent;

public class InpatientAdmissionReport extends JFrame {

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
					InpatientAdmissionReport frame = new InpatientAdmissionReport();
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
	public InpatientAdmissionReport() {
		setTitle("Inpatient Admission Report");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(480, 150, 397, 389);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		final ReportsInterface rp_interface = InterfaceGenerator.getReportsInterface();
		
		JLabel lblNewLabel_1 = new JLabel("Inpatient Admission Report");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_1.setBounds(0, 11, 381, 36);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel = new JLabel("From Date");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel.setBounds(28, 68, 100, 21);
		contentPane.add(lblNewLabel);
		
		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setDateFormatString("dd-MM-yyyy");
		dateChooser.setDate(new Date());
		Date date = DateUtils.addYears(new Date(), -1);
		dateChooser.setMinSelectableDate(date);
		dateChooser.setMaxSelectableDate(new Date());
		dateChooser.setBounds(138, 69, 120, 20);
		contentPane.add(dateChooser);
		
		JLabel lblToDate = new JLabel("To Date");
		lblToDate.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		lblToDate.setBounds(28, 100, 100, 21);
		contentPane.add(lblToDate);
		
		JDateChooser dateChooser_1 = new JDateChooser();
		dateChooser_1.setDateFormatString("dd-MM-yyyy");
		dateChooser_1.setDate(new Date());
		Date date2 = DateUtils.addYears(new Date(), -1);
		dateChooser_1.setMinSelectableDate(date2);
		dateChooser_1.setMaxSelectableDate(new Date());
		dateChooser_1.setBounds(138, 101, 120, 20);
		contentPane.add(dateChooser_1);
		
		JComboBox userBox = new JComboBox();
		userBox.setModel(new DefaultComboBoxModel(new String[] {"All Doctors"}));
		userBox.setBounds(138, 132, 190, 22);
		contentPane.add(userBox);
		
		JLabel lblDoctor = new JLabel("Doctor");
		lblDoctor.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblDoctor.setBounds(28, 132, 100, 21);
		contentPane.add(lblDoctor);
		
		JLabel lblApplicationStatus = new JLabel("Admission Status");
		lblApplicationStatus.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblApplicationStatus.setBounds(28, 164, 100, 21);
		contentPane.add(lblApplicationStatus);
		
		JComboBox statusBox = new JComboBox();
		statusBox.setModel(new DefaultComboBoxModel(new String[] {"All", "Active", "Discharged"}));
		statusBox.setBounds(137, 165, 150, 22);
		contentPane.add(statusBox);
		
		JLabel lblConsStatus = new JLabel("Cons. Status");
		lblConsStatus.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblConsStatus.setBounds(28, 196, 100, 21);
		contentPane.add(lblConsStatus);
		
		JComboBox statusBox_1 = new JComboBox();
		statusBox_1.setModel(new DefaultComboBoxModel(new String[] {"All", "Open", "Close"}));
		statusBox_1.setBounds(138, 198, 150, 22);
		contentPane.add(statusBox_1);
		
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
					column_list.add("admission_status");
					data_list.add(statusBox.getSelectedItem().toString().toLowerCase());
				}
				if(!(statusBox_1.getSelectedItem().toString().equalsIgnoreCase("All"))) {
					column_list.add("emr_status");
					data_list.add(statusBox_1.getSelectedItem().toString().toLowerCase());
				}
				
				
				try {
					ArrayList<InpatientAdmission> listt = rp_interface.getInpatientAdmissionReport(column_list, data_list, sqlfromDate, sqltoDate);
					if(listt.size() == 0) {
						JFrame jf = new JFrame();
						jf.setAlwaysOnTop(true);
						JOptionPane.showMessageDialog(jf, "No results found!");
					}else {
						ShowReportsFrame frame = new ShowReportsFrame("inpatient_admission", column_list, data_list, sqlfromDate, sqltoDate);
						frame.setVisible(true);
					}
					
				} catch (RemoteException e1) {
					
					e1.printStackTrace();
				}
			}
		});
		btnPreview.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnPreview.setBounds(148, 281, 100, 23);
		contentPane.add(btnPreview);
		
		JButton btnCancel = new JButton("Close");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				System.gc();
			}
		});
		btnCancel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnCancel.setBounds(258, 281, 100, 23);
		contentPane.add(btnCancel);
		
		addWindowListener(new WindowAdapter() {
			  public void windowClosing(WindowEvent e) {
			    System.gc();
			  }
			});
	}
	
	
}
