package com.ahms.clinicmanagement;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;

import com.ahms.api.ClinicManagementInterface;
import com.ahms.clientinterface.InterfaceGenerator;
import com.ahms.clinicmgt.entities.PatientVisit;
import com.ahms.clinicmgt.entities.ProgressNote;
import com.ahms.hmgt.entities.UserCard;
import com.toedter.calendar.JDateChooser;
import java.awt.Dimension;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JTable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ProgressNoteEntry extends JFrame {

	private JPanel contentPane;
	private JTextField invoiceField;
	
	private Connection conn;
	private JTextField physicianField;
	private JTextArea noteArea;
	private JTable table;
	private JTextField hospNoField;
	private JTextField nameField;
	private JTextField genderField;
	private JButton btnLock;
	private JButton btnSave;
	private JButton btnUpdate;
	
	PatientVisit pv = null;
	ArrayList<ProgressNote> pnlist =null;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProgressNoteEntry frame = new ProgressNoteEntry(new UserCard(), "", "");
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
	public ProgressNoteEntry(UserCard user, String invoice_no, String emrStatus) {
		setTitle("Elite HMS - Clinic progress note entry");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(350, 100, 666, 595);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		ClinicManagementInterface cm_interface = InterfaceGenerator.getClinicManagementInterface();
		
		try {
			pv = cm_interface.getPatientVisit(invoice_no);
			pnlist = cm_interface.getProgressNotes(invoice_no);
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}
		
		JLabel lblInvoiceNo = new JLabel("Invoice No");
		lblInvoiceNo.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblInvoiceNo.setHorizontalAlignment(SwingConstants.LEFT);
		lblInvoiceNo.setBounds(401, 83, 78, 23);
		contentPane.add(lblInvoiceNo);
		
		
		invoiceField = new JTextField();
		invoiceField.setText(invoice_no);
		invoiceField.setEditable(false);
		invoiceField.setBounds(488, 84, 126, 20);
		contentPane.add(invoiceField);
		invoiceField.setColumns(10);
		Date ta = new Date();
		
		JLabel lblProgressNote = new JLabel("Progress Note");
		lblProgressNote.setBounds(20, 249, 113, 23);
		contentPane.add(lblProgressNote);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 283, 617, 186);
		contentPane.add(scrollPane);
		
		noteArea = new JTextArea();
		scrollPane.setViewportView(noteArea);
		noteArea.setWrapStyleWord(true);
		noteArea.setLineWrap(true);
		
		JButton btnClose = new JButton("Close");
		btnClose.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				
			}
		});
		btnClose.setBounds(885, 527, 89, 23);
		contentPane.add(btnClose);
		
			
		btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ProgressNote note;
				int xy = table.getSelectedRow();
				if(xy < 0) {
					showMessage("No item selected.");
				}else {
					note = pnlist.get(xy);
					note.setNote(noteArea.getText());					
					try {
						if(note.getNoteStatus().equalsIgnoreCase("locked")) {
							showMessage("This progress note is locked.");
						}else {
							cm_interface.updateProgressNote(note);
							showMessage("Success!");
							pnlist = cm_interface.getProgressNotes(invoice_no);
							updateProgressNoteTable(pnlist);
						}
						
						
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
				
				
				
			}
		});
		btnUpdate.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnUpdate.setBounds(228, 522, 89, 23);
		contentPane.add(btnUpdate);
		
		btnSave = new JButton("Save");
		btnSave.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(invoiceField.getText().length()<1){
					showMessage("Invalid invoice number");
				}else if(noteArea.getText().length()<2){
					showMessage("Enter valid text for progress note.");
				}else{
					ProgressNote pnote = new ProgressNote();
					pnote.setInvoice_no(invoiceField.getText());
					pnote.setNote(noteArea.getText());
					pnote.setPhysician(physicianField.getText());
					
					try {
						cm_interface.saveProgressNote(pnote);
						showMessage("Success!");
						pnlist = cm_interface.getProgressNotes(invoice_no);
						updateProgressNoteTable(pnlist);
						btnSave.setEnabled(false);
					} catch (RemoteException e) {
						e.printStackTrace();
					}
					
				}
			}
		});
		btnSave.setBounds(129, 522, 89, 23);
		contentPane.add(btnSave);
		
		
		
		JLabel lblDocumentingPhysician = new JLabel("Physician");
		lblDocumentingPhysician.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblDocumentingPhysician.setBounds(20, 480, 78, 23);
		contentPane.add(lblDocumentingPhysician);
		
		physicianField = new JTextField();
		physicianField.setEditable(false);
		physicianField.setBounds(82, 481, 209, 20);
		contentPane.add(physicianField);
		physicianField.setColumns(10);
		physicianField.setText(user.getFullNames());
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(20, 122, 617, 116);
		contentPane.add(scrollPane_1);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int xy = table.getSelectedRow();
				btnSave.setEnabled(false);
				if(pnlist.get(xy).getNoteStatus().equalsIgnoreCase("locked") || emrStatus.equalsIgnoreCase("closed")){
					
				}else{
					btnUpdate.setEnabled(true);
					btnLock.setEnabled(true);
					
					noteArea.setText(pnlist.get(xy).getNote());
				}
			}
		});
		scrollPane_1.setViewportView(table);
		
		JLabel lblHospitalNo = new JLabel("Hospital No");
		lblHospitalNo.setHorizontalAlignment(SwingConstants.LEFT);
		lblHospitalNo.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblHospitalNo.setBounds(403, 53, 78, 19);
		contentPane.add(lblHospitalNo);
		
		hospNoField = new JTextField();
		hospNoField.setText(pv.getHospitalNo());
		hospNoField.setEditable(false);
		hospNoField.setColumns(10);
		hospNoField.setBounds(488, 52, 126, 20);
		contentPane.add(hospNoField);
		
		JLabel label_1 = new JLabel("Patient Name");
		label_1.setHorizontalAlignment(SwingConstants.LEFT);
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label_1.setBounds(20, 52, 76, 19);
		contentPane.add(label_1);
		
		nameField = new JTextField();
		nameField.setText(pv.getSurname()+" "+pv.getOthernames());
		nameField.setEditable(false);
		nameField.setColumns(10);
		nameField.setBounds(99, 51, 182, 19);
		contentPane.add(nameField);
		
		JLabel label_2 = new JLabel("Gender");
		label_2.setHorizontalAlignment(SwingConstants.LEFT);
		label_2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label_2.setBounds(20, 83, 55, 19);
		contentPane.add(label_2);
		
		genderField = new JTextField();
		genderField.setText(pv.getGender());
		genderField.setEditable(false);
		genderField.setColumns(10);
		genderField.setBounds(99, 82, 55, 19);
		contentPane.add(genderField);
		
		btnLock = new JButton("Lock");
		btnLock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ProgressNote note = pnlist.get(table.getSelectedRow());
				JFrame jFrame = new JFrame();
				jFrame.setAlwaysOnTop(true);
				int confirmed = JOptionPane.showConfirmDialog(jFrame, 
				        "Are you sure you want to lock this progress note?", "Confirm Progress Note Locking",
				        JOptionPane.YES_NO_OPTION);

				    if (confirmed == JOptionPane.YES_OPTION) {
				    	try {
							cm_interface.lockProgressNote(note);
							showMessage("Successful!");
							pnlist = cm_interface.getProgressNotes(invoice_no);
							updateProgressNoteTable(pnlist);
							btnUpdate.setEnabled(false);
							btnLock.setEnabled(false);
						} catch (Exception e2) {
							e2.printStackTrace();
						}
				    }
				
				
				 
			}
		});
		btnLock.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnLock.setBounds(334, 522, 89, 23);
		contentPane.add(btnLock);
		
		JButton btnClear = new JButton("Clear");
		btnClear.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				noteArea.setText("");
				table.clearSelection();
				btnUpdate.setEnabled(false);
				btnLock.setEnabled(false);
				btnSave.setEnabled(true);
			}
		});
		btnClear.setBounds(433, 522, 89, 23);
		contentPane.add(btnClear);
		
		JLabel lblNewLabel = new JLabel("Progress Note");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel.setBounds(10, 11, 630, 28);
		contentPane.add(lblNewLabel);
		
		JButton btnNewButton_1 = new JButton("Close");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnNewButton_1.setBounds(532, 522, 89, 23);
		contentPane.add(btnNewButton_1);
		
		
		try {
			updateProgressNoteTable(pnlist);
		} catch (Exception e) {
			e.printStackTrace();
		}
		btnUpdate.setEnabled(false);
		btnLock.setEnabled(false);
		btnSave.setEnabled(true);
		
		if (emrStatus.equalsIgnoreCase("closed")) {
			btnUpdate.setEnabled(false);
			btnLock.setEnabled(false);
			btnSave.setEnabled(false);
		}

		
	}
	
	
	public void updateProgressNoteTable(ArrayList<ProgressNote> list){
		Object[][] data = new Object[list.size()][6];
		for(int i = 0; i<list.size(); i++){
			data[i][0] = (i+1);
			data[i][2] = list.get(i).getPhysician();
			data[i][3] = list.get(i).getDate();
			data[i][4] = list.get(i).getTime();
			data[i][1] = list.get(i).getNote();
			data[i][5] = list.get(i).getNoteStatus();
							
		}
		
		Object[] columnNames = {"S/No", "Progress Note", "Physician", "Date", "Time", "Status"};
		DefaultTableModel model = new DefaultTableModel(data, columnNames);
		table.setModel(model);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getColumnModel().getColumn(0).setPreferredWidth(60);
		table.getColumnModel().getColumn(1).setPreferredWidth(550);
		table.getColumnModel().getColumn(2).setPreferredWidth(100);
		table.getColumnModel().getColumn(3).setPreferredWidth(100);
		table.getColumnModel().getColumn(4).setPreferredWidth(100);
		table.getColumnModel().getColumn(0).setPreferredWidth(60);
		
	}
	
	
	public void showMessage(String message){
		JFrame jf = new JFrame();
		jf.setAlwaysOnTop(true);
		JOptionPane.showMessageDialog(jf, message);
	}
}
