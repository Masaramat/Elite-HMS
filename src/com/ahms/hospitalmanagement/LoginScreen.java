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
import javax.swing.JDialog;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.awt.event.ActionEvent;

public class LoginScreen extends JFrame {

	
	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JTextField usernameField;
	private JPasswordField passwordField;
	public static JButton btnLogiin;
	
	HospitalManagementInterface hm_interface;
	
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginScreen frame = new LoginScreen();
					frame.setVisible(true);
					frame.getRootPane().setDefaultButton(btnLogiin);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	@SuppressWarnings({ })
	public LoginScreen() {
		setTitle("Elite HMS - Welcome");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(420, 170, 505, 271);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("User Log In Page");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("Tahoma", Font.BOLD, 14));
		label.setBounds(10, 11, 398, 33);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("Username: ");
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		label_1.setBounds(77, 76, 89, 24);
		contentPane.add(label_1);
		
		usernameField = new JTextField();
		usernameField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		usernameField.setColumns(10);
		usernameField.setBounds(180, 75, 177, 25);
		EventQueue.invokeLater( new Runnable() {
			@Override
			public void run() {
				usernameField.requestFocusInWindow();
			}
		} );
		contentPane.add(usernameField);
		
		JLabel label_2 = new JLabel("Password:");
		label_2.setFont(new Font("Tahoma", Font.PLAIN, 13));
		label_2.setBounds(77, 111, 89, 22);
		contentPane.add(label_2);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(180, 111, 177, 25);
		contentPane.add(passwordField);
		passwordField.setEchoChar('*');
		
		btnLogiin = new JButton("Login");
		btnLogiin.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {
				if(usernameField.getText().length()<6){
					//do nothing
				}else if(passwordField.getText().length()<4){
					//do nothing
				}else{
										
					try {
						hm_interface = InterfaceGenerator.getHospitalManagementInterface();
						UserCard ux = new UserCard();
						ux.setFullNames("");
						ux.setUsername(usernameField.getText());
						ux.setPassword(passwordField.getText());
						
						ux.setStatus("");
					
						UserCard user = hm_interface.getUserCard(ux);
						if(user.getUsername()==null){
							showMessage("Invalid username/password");
						}else if(!(user.getStatus().equalsIgnoreCase("active"))){
							showMessage("User account is not active.");
						}else {
							
							EHMSMainPage epFrame = new EHMSMainPage(user);					
							epFrame.setVisible(true);							
							dispose();										
						}
						
					} catch (RemoteException e) {	showMessage("Unable to connect to server");
					e.printStackTrace();
					} 
					
				}
			}
		});
		btnLogiin.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnLogiin.setBounds(121, 198, 89, 23);
		contentPane.add(btnLogiin);
		
		JButton button_1 = new JButton("Reset");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				usernameField.setText("");
				passwordField.setText("");;
			
			}
		});
		button_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		button_1.setBounds(220, 198, 89, 23);
		contentPane.add(button_1);
		
		JButton button_2 = new JButton("Exit");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		button_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		button_2.setBounds(319, 198, 89, 23);
		contentPane.add(button_2);
	}
	
	//a standard client method for displaying popup messages on frame that is setAlwaysOnTop(true)
	public static void showMessage(String message){
		final JDialog dialog = new JDialog();
		dialog.setAlwaysOnTop(true);
		JOptionPane.showMessageDialog(dialog, message);
	}
	
}
