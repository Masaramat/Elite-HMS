package com.ahms.hospitalmanagement;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.ahms.api.HospitalManagementInterface;
import com.ahms.clientinterface.InterfaceGenerator;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.event.ActionEvent;
import javax.swing.JFileChooser;

public class BackupFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BackupFrame frame = new BackupFrame();
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
	public BackupFrame() throws RemoteException{
		setTitle("Database Backup");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(400, 150, 530, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		final HospitalManagementInterface hManagementInterface = InterfaceGenerator.getHospitalManagementInterface();
		String dt = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
		String backUpDir = "\"C:/Users/my pc/documents/Elite Hospital Solutions/Backups/"+dt+"\"";
		
		JLabel lblNewLabel = new JLabel("DATABASE BACKUP");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(10, 11, 414, 21);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("File Name");
		lblNewLabel_1.setBounds(10, 62, 76, 21);
		contentPane.add(lblNewLabel_1);
		
		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.BOLD, 11));
		textField.setBounds(77, 62, 347, 21);
		textField.setText(backUpDir);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnClose.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnClose.setBounds(318, 196, 89, 23);
		contentPane.add(btnClose);
		
		JButton btnNewButton = new JButton("OK");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String message =hManagementInterface.databaseBackup(textField.getText());
					showPopup(message);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnNewButton.setBounds(219, 196, 89, 23);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Choose");
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String dir = "";
				String selected = "";
				JFileChooser f = new JFileChooser();
		        f.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); 
		        f.showSaveDialog(null);
		        
		        dir = f.getCurrentDirectory().toString().replace("\\", "/");
		        selected = f.getSelectedFile().toString().replace("\\", "/");
		        String dateString =new SimpleDateFormat("dd-MM-yyyy").format(new Date());
		        textField.setText("\"" + selected + "/" + dateString + "\"");
		        
		        System.out.println(dir);
		        System.out.println(selected);
			}
		});
		btnNewButton_1.setBounds(434, 61, 70, 23);
		contentPane.add(btnNewButton_1);
		
		
	
	}
	
	public void showPopup(String message){
		JFrame frame = new JFrame();
		frame.setAlwaysOnTop(true);
		JOptionPane.showMessageDialog(frame, message);
		
	}
	
}
