package com.ahms.server;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;

public class Testter extends JFrame {

	private JPanel contentPane;
	
	static Timer timer = new Timer();
	static Timer timer_1 = new Timer();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Testter frame = new Testter();
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
	public Testter() {
		setTitle("AHMS Server Control Panel");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 538, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		new GCTimTask().run();
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(192, 24, 279, 23);
		contentPane.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("Start Server\r\n");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AHMSServer.startServer();
				new TimTask().run();
				lblNewLabel.setText("Realtime HMS Server is running...");
				lblNewLabel.setForeground(Color.red);
				
			}
		});
		btnNewButton.setBounds(24, 24, 157, 23);
		contentPane.add(btnNewButton);
		
		JButton btnStopServer = new JButton("Stop Server");
		btnStopServer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				AHMSServer.stopServer();
				System.exit(0);
			}
		});
		btnStopServer.setBounds(24, 58, 157, 23);
		contentPane.add(btnStopServer);
		
	}
	
	
	public static void checkLicense() {		
		
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");		
		String exp = "2024-08-31";
		
		try {
			Date d1 = new Date();
			Date d2 = sdf.parse(exp);
			
			if(d1.compareTo(d2)>0){
				
				AHMSServer.stopServer();
				timer.cancel();
			}
			
		} catch (ParseException e) { 					
			e.printStackTrace();
		}
	}
	
	static class TimTask extends TimerTask {
	    public void run() {
	        int delay = (2 * 60 * 60 * 1000);
	        timer.schedule(new TimTask(), delay);
	        checkLicense();
	        System.gc();
	        
	    }
	}
	
	static class GCTimTask extends TimerTask {
	    public void run() {
	        int delay = (7 * 60 * 1000);
	        timer_1.schedule(new GCTimTask(), delay);
	        System.gc();
	       
	    }
	}
	
	
	
}
