package com.ahms.clinicmanagement;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.ahms.api.ClinicManagementInterface;
import com.ahms.clientinterface.InterfaceGenerator;
import com.ahms.clinicmgt.entities.ClinicProcedure;
import com.ahms.clinicmgt.entities.ProgressNote;
import com.ahms.hmgt.entities.ProcedureItem;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.ListSelectionModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ProcedureEntry extends JFrame {

	private JPanel contentPane;
	private JTextField idField;
	private JTextField nameField;
	private JTable table;
	private JButton btnSave;
	private ClinicManagementInterface cm_interface;
	
	private  ArrayList<ClinicProcedure> procedure_list;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProcedureEntry frame = new ProcedureEntry("");
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
	public ProcedureEntry(String source) {
		
		setTitle("Elite HMS - Surgical Procedures");
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(450, 150, 506, 494);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		cm_interface = InterfaceGenerator.getClinicManagementInterface();
		
		JLabel lblProcedureId = new JLabel("Procedure ID");
		lblProcedureId.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblProcedureId.setBounds(10, 57, 112, 20);
		contentPane.add(lblProcedureId);
		
		JLabel lblProcedureName = new JLabel("Procedure Name");
		lblProcedureName.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblProcedureName.setBounds(10, 88, 112, 20);
		contentPane.add(lblProcedureName);
		
		idField = new JTextField(); 
		idField.setEditable(false);
		idField.setBounds(118, 57, 86, 20);
		contentPane.add(idField);
		idField.setColumns(10);
		
		nameField = new JTextField();
		nameField.setBounds(118, 88, 259, 20);
		contentPane.add(nameField);
		nameField.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 135, 470, 245);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = table.getSelectedRow();
				idField.setText(procedure_list.get(row).getId());
				nameField.setText(procedure_list.get(row).getProcedure());
				btnSave.setEnabled(false);
			}
		});
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(table);
		
		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(source.equalsIgnoreCase("visit")) {
					ProcedureBooking.updateProcedureBox();
					dispose();
					System.gc();
				}else {
					dispose();
				}
				
				
			}
		});
		btnClose.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnClose.setBounds(379, 411, 89, 23);
		contentPane.add(btnClose);
		
		btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(nameField.getText().length() < 1){
					showMessage("Please enter procedure name.");
					
				}else{					
					try {
						ProcedureItem pi = new ProcedureItem();
						pi.setProcedureDesc(nameField.getText());
						String test = cm_interface.createProcedure(pi);
						showMessage("Success!");
						idField.setText("");
						nameField.setText("");
						procedure_list = cm_interface.getAllProcedures();
						updateProcedureTable(procedure_list);
						//btnSave.setEnabled(false);
						
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		btnSave.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnSave.setBounds(180, 411, 89, 23);
		contentPane.add(btnSave);
		
		JLabel lblNewLabel = new JLabel("Surgical Procedures");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(10, 11, 499, 23);
		contentPane.add(lblNewLabel);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRow()<0) {
					showMessage("No item selected.");
				}else {
					
				try {
					cm_interface.updateClinicProcedure(idField.getText(), nameField.getText());
					showMessage("Updated successfully!");
					procedure_list = cm_interface.getAllProcedures();
					updateProcedureTable(procedure_list);
					btnSave.setEnabled(true);
					idField.setText("");
					nameField.setText("");
					table.clearSelection();
					
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}	
					
				}
			}
		});
		btnUpdate.setBounds(280, 411, 89, 23);
		contentPane.add(btnUpdate);
		
		try {
			procedure_list = cm_interface.getAllProcedures();
			updateProcedureTable(procedure_list);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				if(source.equalsIgnoreCase("visit")) {
					ProcedureBooking.updateProcedureBox();
					dispose();
					System.gc();
				}else {
					dispose();
				}
			}
		});
		
	}
	
	

	public void updateProcedureTable(ArrayList<ClinicProcedure> list){
		Object[][] data = new Object[list.size()][3];
		for(int i = 0; i<list.size(); i++){
			data[i][0] = (i+1);
			data[i][1] = list.get(i).getId();
			data[i][2] = list.get(i).getProcedure();			
							
		}
		
		Object[] columnNames = {"S/No", "Procedure ID", "Procedure Description"};
		DefaultTableModel model = new DefaultTableModel(data, columnNames);
		table.setModel(model);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getColumnModel().getColumn(0).setPreferredWidth(70);
		table.getColumnModel().getColumn(1).setPreferredWidth(100);
		table.getColumnModel().getColumn(2).setPreferredWidth(300);
		
		
	}
	
	public static void showMessage(String message){
		final JDialog dialog = new JDialog();
		dialog.setAlwaysOnTop(true);
		JOptionPane.showMessageDialog(dialog, message);
	}
}
