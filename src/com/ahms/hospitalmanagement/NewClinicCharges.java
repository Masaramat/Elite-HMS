package com.ahms.hospitalmanagement;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.ahms.api.HospitalManagementInterface;
import com.ahms.clientinterface.InterfaceGenerator;
import com.ahms.clinicmanagement.OPDConsultation;
import com.ahms.hmgt.entities.HospitalCharge;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import java.awt.Color;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class NewClinicCharges extends JFrame {
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NewClinicCharges frame = new NewClinicCharges("");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws RemoteException 
	 */
	public NewClinicCharges(String source) throws RemoteException {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				GeneratePatientBill.updateBillCombo();
				dispose();
			}
		});
		setAlwaysOnTop(true);
		setTitle("Elite HMS - Hospital Service Charges  ");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(400, 120, 488, 416);
		getContentPane().setLayout(null);
		
		HospitalManagementInterface hm_interface = InterfaceGenerator.getHospitalManagementInterface();
		
				
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "New Hospital Service ", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(10, 11, 439, 130);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblHospitalService = new JLabel("Hospital Service");
		lblHospitalService.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblHospitalService.setBounds(10, 30, 118, 21);
		panel.add(lblHospitalService);
		
		JTextArea textArea = new JTextArea();
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		textArea.setBounds(104, 28, 306, 43);
		panel.add(textArea);
		
		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(source.equalsIgnoreCase("program")) {
					GeneratePatientBill.updateBillCombo();
					dispose();
				}else {
					dispose();
				}
				
			}
		});
		btnClose.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnClose.setBounds(318, 340, 128, 23);
		getContentPane().add(btnClose);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 152, 439, 158);
		getContentPane().add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int xy = table.getSelectedRow();
				textArea.setText((String) table.getValueAt(xy, 1));
				
			}
		});
		scrollPane.setViewportView(table);
		
		JButton btnSave = new JButton("Save");
		btnSave.setBounds(179, 340, 129, 23);
		getContentPane().add(btnSave);
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					if(textArea.getText().length()<2){
						showMessage("Enter Valid Name of Hospital Service!");
					
					}else{
						
						hm_interface.saveHospitalCharge(textArea.getText(), 0.00);
						updateTable(hm_interface.getAllCharges());
						textArea.setText("");
						
						btnSave.setEnabled(false);
					}
				}catch(Exception ex){ ex.printStackTrace(); }
			}
		});
		btnSave.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		
		//System.out.println((hm_interface.getAllCharges().size()));
		updateTable(hm_interface.getAllCharges());
	}
	
	public void updateTable(ArrayList<HospitalCharge> list){
		Object[][] data = new Object[list.size()][2];
		for(int i=0; i<list.size(); i++){ 
			data[i][0] = (i+1);
			data[i][1] = list.get(i).getChargeName();			
		}
		
		Object[] columnNames = { "S/No", "Hospital Service"};
		DefaultTableModel model = new DefaultTableModel(data, columnNames);
		table.setModel(model);	
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getColumnModel().getColumn(0).setPreferredWidth(70);
		table.getColumnModel().getColumn(1).setPreferredWidth(350);	
		
	}
	
	
	
	public static void showMessage(String message){
		final JDialog dialog = new JDialog();
		dialog.setAlwaysOnTop(true);
		JOptionPane.showMessageDialog(dialog, message);
	}
	
	
	
	
}
