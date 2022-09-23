package com.ahms.hospitalmanagement;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.ahms.api.HospitalManagementInterface;
import com.ahms.clientinterface.InterfaceGenerator;
import com.ahms.hmgt.entities.UserCard;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ChangePasswordFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField oldPasswordField;
	private JPasswordField newPasswordField;
	private JPasswordField confirmPasswordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChangePasswordFrame frame = new ChangePasswordFrame(new UserCard());
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
	public ChangePasswordFrame(final UserCard user) {
		setTitle("Elite HMS - Change Password");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(450, 150, 450, 272);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		final HospitalManagementInterface hm_interface = InterfaceGenerator.getHospitalManagementInterface();
		
		
		JLabel lblNewLabel = new JLabel("Change Password");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel.setBounds(38, 11, 335, 21);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Username");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_1.setBounds(38, 43, 100, 21);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Old Password");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_1_1.setBounds(38, 75, 100, 21);
		contentPane.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("New Password");
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_1_2.setBounds(38, 107, 100, 21);
		contentPane.add(lblNewLabel_1_2);
		
		JLabel lblNewLabel_1_3 = new JLabel("Confirm Password");
		lblNewLabel_1_3.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_1_3.setBounds(38, 139, 100, 21);
		contentPane.add(lblNewLabel_1_3);
		
		textField = new JTextField();
		textField.setText(user.getUsername());
		textField.setEditable(false);
		textField.setBounds(161, 43, 160, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		oldPasswordField = new JPasswordField();
		oldPasswordField.setBounds(161, 75, 160, 21);
		contentPane.add(oldPasswordField);
		
		newPasswordField = new JPasswordField();
		newPasswordField.setBounds(160, 107, 161, 21);
		contentPane.add(newPasswordField);
		
		confirmPasswordField = new JPasswordField();
		confirmPasswordField.setBounds(161, 139, 161, 21);
		contentPane.add(confirmPasswordField);
		
		JButton saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				@SuppressWarnings("deprecation")
				String new_password = newPasswordField.getText();
				@SuppressWarnings("deprecation")
				String confirm_password = confirmPasswordField.getText();
				@SuppressWarnings("deprecation")
				String old_password = oldPasswordField.getText();
				
				if(!(new_password.equals(confirm_password))) {
					showPopUp("New password mismatch.");
					newPasswordField.setText("");
					textField.setText(user.getUsername());
					oldPasswordField.setText("");
					confirmPasswordField.setText("");
					
				}else if(!(old_password.equals(user.getPassword()))) {
					showPopUp("Old password is incorrect.");
					newPasswordField.setText("");
					textField.setText(user.getUsername());
					oldPasswordField.setText("");
					confirmPasswordField.setText("");
					System.out.println("Old password is: "+user.getPassword());
					System.out.println("New password is : " + old_password);
					
				}else {
					try {
						hm_interface.changePassword(new_password, user);
						showPopUp("Password changed successfully.");
						newPasswordField.setText("");
						textField.setText(user.getUsername());
						oldPasswordField.setText("");
						confirmPasswordField.setText("");
					} catch (Exception e2) {	e2.printStackTrace();			}
				}
					
				
			}
		});
		saveButton.setBounds(135, 201, 89, 23);
		contentPane.add(saveButton);
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		cancelButton.setBounds(333, 201, 89, 23);
		contentPane.add(cancelButton);
		
		JButton clearButton = new JButton("Clear");
		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				newPasswordField.setText("");
				textField.setText(user.getUsername());
				oldPasswordField.setText("");
				confirmPasswordField.setText("");
			}
		});
		clearButton.setBounds(234, 201, 89, 23);
		contentPane.add(clearButton);
	}
	
	
	public void showPopUp(String message) {
		JFrame jf = new JFrame();
		jf.setAlwaysOnTop(true);
		JOptionPane.showMessageDialog(jf, message);
	}
}
