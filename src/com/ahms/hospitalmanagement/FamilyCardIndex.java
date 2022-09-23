package com.ahms.hospitalmanagement;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.SwingConstants;

import com.ahms.api.HospitalManagementInterface;
import com.ahms.clientinterface.InterfaceGenerator;
import com.ahms.hmgt.entities.FamilyCard;
import com.ahms.hmgt.entities.UserCard;
import com.toedter.calendar.JDateChooser;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.rmi.RemoteException;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Color;

public class FamilyCardIndex extends JFrame {

	private JPanel contentPane;
	private JTextField famNoField;
	private JTextField famNameField;
	private JTextField regOfficerField;
	private JTextField regFeeField;
	private JLabel bvnLbl;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FamilyCardIndex frame = new FamilyCardIndex(new UserCard());
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
	public FamilyCardIndex(UserCard user) {
		setResizable(false);
		setTitle("Elite HMS - New Family Card");
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(400, 150, 493, 412);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		final HospitalManagementInterface hm_interface = InterfaceGenerator.getHospitalManagementInterface();
		
		JLabel lblNewLabel = new JLabel("Family Number");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel.setBounds(38, 62, 107, 20);
		contentPane.add(lblNewLabel);
		
		famNoField = new JTextField();
		famNoField.setEditable(false);
		famNoField.setBounds(161, 62, 107, 20);
		contentPane.add(famNoField);
		famNoField.setColumns(10);
		
		JLabel lblFamilyName = new JLabel("Family Name");
		lblFamilyName.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblFamilyName.setBounds(38, 93, 107, 20);
		contentPane.add(lblFamilyName);
		
		famNameField = new JTextField();
		famNameField.setBounds(161, 93, 249, 20);
		contentPane.add(famNameField);
		famNameField.setColumns(10);
		
				
		JLabel lblFileOpenDate = new JLabel("File Open Date");
		lblFileOpenDate.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblFileOpenDate.setBounds(38, 124, 107, 20);
		contentPane.add(lblFileOpenDate);
		
		JLabel lblRegistrationOfficer = new JLabel("Registration Officer");
		lblRegistrationOfficer.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblRegistrationOfficer.setBounds(38, 186, 107, 20);
		contentPane.add(lblRegistrationOfficer);
		
		JLabel lblRegistrationFee = new JLabel("Registration Fee");
		lblRegistrationFee.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblRegistrationFee.setBounds(38, 155, 86, 20);
		contentPane.add(lblRegistrationFee);
		
		regOfficerField = new JTextField();
		regOfficerField.setText(user.getFullNames());
		regOfficerField.setEditable(false);
		regOfficerField.setBounds(161, 186, 218, 20);
		contentPane.add(regOfficerField);
		regOfficerField.setColumns(10);
		
		regFeeField = new JTextField();
		regFeeField.addKeyListener(new KeyAdapter() {
			String xy = "";
			@Override
			public void keyReleased(KeyEvent e) {
				if(regFeeField.getText().length()<1) {
					bvnLbl.setText("");
				}else {
									
						bvnLbl.setText("");					
					try {
						@SuppressWarnings("unused")
						double number = Double.parseDouble(regFeeField.getText());
						
						}catch(Exception ex) {							
							regFeeField.setText("");
							bvnLbl.setText("Only Numbers Allowed.");
						}
				}			
			}
		});
		regFeeField.setHorizontalAlignment(SwingConstants.RIGHT);
		regFeeField.setBounds(161, 155, 86, 20);
		contentPane.add(regFeeField);
		regFeeField.setColumns(10);
		
		JButton btnSave = new JButton("Save");
		btnSave.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(famNameField.getText().length()<1) {
					showMessage("Please enter a family name.");
				}else if(regFeeField.getText().length()<1){
					showMessage("Please enter registration fee.");
				}else{
					try{
					FamilyCard famz = new FamilyCard();
					
					famz.setFamilyName(famNameField.getText());
					famz.setRegOfficer(regOfficerField.getText());
					famz.setRegFee(Double.parseDouble(regFeeField.getText()));
					
					FamilyCard fm =hm_interface.createFamilyCard(famz);
					
					if(fm.getFamilyNo() == null){
						showMessage("Unable to create family card.");
					}else{
						showMessage("Success!");
						famNoField.setText(fm.getFamilyNo());
						famNoField.setEditable(false);
						btnSave.setEnabled(false);
					}					
					}catch(NumberFormatException es){
						//show valid message
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		btnSave.setBounds(163, 337, 89, 23);
		contentPane.add(btnSave);
		
		JButton btnClear = new JButton("Clear");
		btnClear.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				famNoField.setText("");
				famNameField.setText("");
				regOfficerField.setText("");
				regFeeField.setText("");
				btnSave.setEnabled(true);
			}
		});
		btnClear.setBounds(262, 337, 89, 23);
		contentPane.add(btnClear);
		
		JButton btnClose = new JButton("Close");
		btnClose.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			dispose();
			}
		});
		btnClose.setBounds(361, 337, 89, 23);
		contentPane.add(btnClose);
		
		JDateChooser dateChooser = new JDateChooser();
		dateChooser.getCalendarButton().setEnabled(false);
		dateChooser.setPreferredSize(new Dimension(40, 20));
		dateChooser.setDateFormatString("yyyy-MM-dd");
		dateChooser.setDate(new Date());
		dateChooser.setBounds(161, 124, 107, 19);
		contentPane.add(dateChooser);
		
		JLabel lblNewFamilyCard = new JLabel("New Family Card");
		lblNewFamilyCard.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewFamilyCard.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewFamilyCard.setBounds(10, 11, 467, 28);
		contentPane.add(lblNewFamilyCard);
		
		bvnLbl = new JLabel("");
		bvnLbl.setFont(new Font("Tahoma", Font.PLAIN, 10));
		bvnLbl.setForeground(Color.RED);
		bvnLbl.setBounds(257, 158, 122, 14);
		contentPane.add(bvnLbl);
	}
	
	
	public static void showMessage(String message){
		final JDialog dialog = new JDialog();
		dialog.setAlwaysOnTop(true);
		JOptionPane.showMessageDialog(dialog, message);
	}
}
