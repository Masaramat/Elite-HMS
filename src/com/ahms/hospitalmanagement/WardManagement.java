package com.ahms.hospitalmanagement;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.ahms.api.HospitalManagementInterface;
import com.ahms.clientinterface.InterfaceGenerator;
import com.ahms.hmgt.entities.Bed;
import com.ahms.hmgt.entities.Room;
import com.ahms.hmgt.entities.Ward;

import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.DefaultComboBoxModel;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class WardManagement extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8840323552499379368L;
	private JPanel contentPane;

	
	private JTextField roomNoField;
	private JTextField roomNameField;
	private JTable rooms_table;
	private JComboBox wardSelectionBox;
	private JComboBox roomSelectionBox;
	
	private ArrayList<Ward> wardList;
	private ArrayList<Room> roomList;
	private ArrayList<Bed> bedList;
	
	private JTextField bedNoField;
	private JTextField bedDetailsField;
	private JTextField bedChargeField;
	private JTable beds_table;
	private JButton btnSaveRoom;
	private JButton btnUpdateRoom;
	
	private JButton btnSaveBed;
	private JButton btnUpdateBed;
	
	private HospitalManagementInterface hm_interface = InterfaceGenerator.getHospitalManagementInterface();
	
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WardManagement frame = new WardManagement();
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
	public WardManagement() throws RemoteException {
		setAlwaysOnTop(true);
		setTitle("Elite HMS - Ward Management");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(430, 120, 582, 587);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		wardList = hm_interface.getAllWards();
		roomList = hm_interface.getAllRooms();
		bedList = hm_interface.getAllBeds();
		
				
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 11, 549, 537);
		contentPane.add(tabbedPane);
				
		
		JPanel wardPanel = new JPanel();
		tabbedPane.addTab("Rooms   ", null, wardPanel, null);
		wardPanel.setLayout(null);
				
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 215, 524, 210);
		wardPanel.add(scrollPane_1);
		
		rooms_table = new JTable();
		rooms_table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int xy = rooms_table.getSelectedRow();
				roomNoField.setText(roomList.get(xy).getRoomId());
				roomNameField.setText(roomList.get(xy).getRoomName());
				wardSelectionBox.setSelectedItem(roomList.get(xy).getWardName());
				btnSaveRoom.setEnabled(false);
				btnUpdateRoom.setEnabled(true);
				
			}
		});
		scrollPane_1.setViewportView(rooms_table);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Create/Edit Room", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_1.setBounds(10, 30, 524, 174);
		wardPanel.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblRoomNo = new JLabel("Room No.");
		lblRoomNo.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblRoomNo.setBounds(10, 36, 111, 20);
		panel_1.add(lblRoomNo); 
		
		roomNoField = new JTextField();
		roomNoField.setBounds(131, 36, 86, 20);
		panel_1.add(roomNoField);
		roomNoField.setEditable(false);
		roomNoField.setColumns(10);
		
		JLabel lblCategory = new JLabel("Ward");
		lblCategory.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblCategory.setBounds(10, 67, 111, 20);
		panel_1.add(lblCategory);
		
		wardSelectionBox = new JComboBox();
		wardSelectionBox.setBounds(131, 67, 240, 20);
		for(int i=0; i<wardList.size(); i++){
			wardSelectionBox.addItem(wardList.get(i).getWardName());
		}
		panel_1.add(wardSelectionBox);
		
		JLabel lblRoomwardName = new JLabel("Room name");
		lblRoomwardName.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblRoomwardName.setBounds(10, 98, 111, 20);
		panel_1.add(lblRoomwardName);
		
		roomNameField = new JTextField();
		roomNameField.setBounds(131, 98, 240, 20);
		panel_1.add(roomNameField);
		roomNameField.setColumns(10);
		
		btnSaveRoom = new JButton("Save");
		btnSaveRoom.setBounds(73, 475, 114, 23);
		wardPanel.add(btnSaveRoom);
		
		btnUpdateRoom = new JButton("Update");
		btnUpdateRoom.setBounds(197, 475, 114, 23);
		wardPanel.add(btnUpdateRoom);
		btnUpdateRoom.setEnabled(false);
		
		JButton btnClear = new JButton("Clear");
		btnClear.setBounds(321, 475, 89, 23);
		wardPanel.add(btnClear);
		
		JButton btnClose_1 = new JButton("Close");
		btnClose_1.setBounds(420, 475, 114, 23);
		wardPanel.add(btnClose_1);
		btnClose_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				roomNoField.setText("");
				wardSelectionBox.setSelectedIndex(0);
				roomNameField.setText("");
				btnSaveRoom.setEnabled(true);
			}
		});
		btnUpdateRoom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(roomNoField.getText().length()<1 || roomNameField.getText().length()<2){
					showMessage("Please enter a valid room name");
				}else{
					String ward_id = wardList.get(wardSelectionBox.getSelectedIndex()).getWardId();
					try {
						hm_interface.updateRoom(roomNoField.getText(), ward_id, roomNameField.getText(), "");
						roomList = hm_interface.getAllRooms();
						updateRoomsTable(roomList);	
						showMessage("Success!");
						btnSaveRoom.setEnabled(true);
						btnUpdateRoom.setEnabled(false);
						
						roomNoField.setText("");
						wardSelectionBox.setSelectedIndex(0);
						roomNameField.setText("");
					} catch (RemoteException e) { e.printStackTrace();		}					
				}				
			}
		});
		btnSaveRoom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(wardSelectionBox.getSelectedItem().toString().length()<2 || roomNameField.getText().length()<2){
					//do nothing
				}else{					
					 try {
						 String ward_id = wardList.get(wardSelectionBox.getSelectedIndex()).getWardId();
						String text = hm_interface.createRoom(ward_id, roomNameField.getText(), "");
						roomNoField.setText(text);
						btnSaveRoom.setEnabled(false);
						btnUpdateRoom.setEnabled(true);
						updateRoomCombo();
						roomList = hm_interface.getAllRooms();
						updateRoomsTable(roomList);
						
						
					} catch (RemoteException e) {	e.printStackTrace();	}					
				}
				
			}
		});
		
		
		JPanel bedPanel = new JPanel();
		tabbedPane.addTab("Bed     ", null, bedPanel, null);
		bedPanel.setLayout(null);
		
		
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(10, 208, 502, 240);
		bedPanel.add(scrollPane_2);
		
		beds_table = new JTable();
		beds_table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
			int xy = beds_table.getSelectedRow();
			bedNoField.setText(bedList.get(xy).getBedNo());
			roomSelectionBox.setSelectedItem(bedList.get(xy).getWardName());
			bedDetailsField.setText(bedList.get(xy).getBedDetails());
			bedChargeField.setText(bedList.get(xy).getBedCharge()+ "");
			
			btnSaveBed.setEnabled(false);
			btnUpdateBed.setEnabled(true);
			
			}
		});
		scrollPane_2.setViewportView(beds_table);
		
		
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Create/Edit Bed Details", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_2.setBounds(10, 36, 502, 161);
		bedPanel.add(panel_2);
		panel_2.setLayout(null);
		
		JLabel lblBedNo = new JLabel("Bed No");
		lblBedNo.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblBedNo.setBounds(10, 26, 76, 23);
		panel_2.add(lblBedNo);		
		
		bedNoField = new JTextField();      
		bedNoField.setBounds(80, 27, 86, 20);
		panel_2.add(bedNoField); 
		bedNoField.setEditable(false);
		bedNoField.setColumns(10);
		
		JLabel lblWardroom = new JLabel("Room");
		lblWardroom.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblWardroom.setBounds(10, 60, 76, 23);
		panel_2.add(lblWardroom);
		
		roomSelectionBox = new JComboBox();
		roomSelectionBox.setBounds(80, 60, 233, 22);
		updateRoomCombo();
		panel_2.add(roomSelectionBox);
			
		
		JLabel lblBedDetails = new JLabel("Bed Name");
		lblBedDetails.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblBedDetails.setBounds(10, 94, 76, 23);
		panel_2.add(lblBedDetails);
		
		bedDetailsField = new JTextField();
		bedDetailsField.setBounds(80, 94, 233, 22);
		panel_2.add(bedDetailsField);
		bedDetailsField.setColumns(10);
		
		JLabel lblBedCharge = new JLabel("Bed Charge");
		lblBedCharge.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblBedCharge.setBounds(10, 128, 76, 23);
		panel_2.add(lblBedCharge);
		
		bedChargeField = new JTextField();
		bedChargeField.setBounds(80, 128, 86, 23);
		panel_2.add(bedChargeField);
		bedChargeField.setHorizontalAlignment(SwingConstants.RIGHT);
		bedChargeField.setColumns(10);
		
			
		
		JButton btnUpdateBed = new JButton("Update");
		btnUpdateBed.setEnabled(false);
		btnUpdateBed.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnUpdateBed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			try {
				hm_interface.updateBed(new Bed(bedNoField.getText(), bedDetailsField.getText(),"","", Double.parseDouble(bedChargeField.getText())));
				showMessage("Success!");
				btnSaveBed.setEnabled(true);
				btnUpdateBed.setEnabled(false);
				bedList = hm_interface.getAllBeds();
				updateBedsTable(bedList);
				clearBedForm();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			}
		});
		btnUpdateBed.setBounds(225, 475, 89, 23);
		bedPanel.add(btnUpdateBed);
		
		btnSaveBed = new JButton("Save");
		btnSaveBed.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnSaveBed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int xxx = roomSelectionBox.getSelectedIndex();
				if(xxx >= 0){
					if(bedDetailsField.getText().length()<1){
						
					}else if(bedChargeField.getText().length()<2){
						
					}else{
						try{
							Bed bed = new Bed();
							bed.setBedDetails(bedDetailsField.getText());
							bed.setBedCharge(Double.parseDouble(bedChargeField.getText()));
							bed.setRoomId(roomList.get(roomSelectionBox.getSelectedIndex()).getRoomId());
							
							String xxy = hm_interface.createBed(bed.getRoomId(), bed);
							showMessage("Success!");
							bedNoField.setText(xxy);
							bedList = hm_interface.getAllBeds();
							updateBedsTable(bedList);
							
							 bedNoField.setText(""); 
							 roomSelectionBox.setSelectedIndex(0);
							 bedDetailsField.setText("");
							 bedChargeField.setText("0.00");
							 btnSaveBed.setEnabled(true);
							 btnUpdateBed.setEnabled(false);
							
						}catch(Exception ex){ ex.printStackTrace();}
						
					}								
				}
			}
		});
		btnSaveBed.setBounds(118, 475, 97, 23);
		bedPanel.add(btnSaveBed);
		
		JButton btnClearBedForm = new JButton("Clear");
		btnClearBedForm.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnClearBedForm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				 bedNoField.setText(""); 
				 roomSelectionBox.setSelectedIndex(0);
				 bedDetailsField.setText("");
				 bedChargeField.setText("0.00");
				 btnSaveBed.setEnabled(true);
				 btnUpdateBed.setEnabled(false);
			}
		});
		btnClearBedForm.setBounds(324, 475, 89, 23);
		bedPanel.add(btnClearBedForm);
		
		JButton btnCloseBed = new JButton("Close");
		btnCloseBed.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnCloseBed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			dispose();
			}
		});
		btnCloseBed.setBounds(423, 475, 89, 23);
		bedPanel.add(btnCloseBed);
		
		
		//updateWardsTable(hm_interface.getAllWards());
		
		updateRoomsTable(roomList);
		updateBedsTable(bedList);
		
		
	}
	
	
	
	public void updateRoomCombo() {
		for(int i=0; i<roomList.size(); i++){
			try {
				roomList = hm_interface.getAllRooms();
				roomSelectionBox.addItem(roomList.get(i).getRoomName());
			}catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}	
		
	
	//client method to clear the ward form	
	public void clearCategoryForm(){
		//wardIdField.setText("");
		//wardNameField.setText("");
	}

		
	//method in client to clear room form
	public void clearWardForm(){
		roomNoField .setText("");
		roomNameField.setText(""); 
		//roomDescField.setText(""); 
		wardSelectionBox.setSelectedIndex(0);
	}

	//method to update rooms table
	public void updateRoomsTable(ArrayList<Room> list){
				
		Object[][] data = new Object[list.size()][3];
		for(int i=0; i<list.size(); i++){ 
			data[i][0] = (i+1);			
			data[i][2] = list.get(i).getRoomName();
			data[i][1] = list.get(i).getWardName();
		}
		
		Object[] columnNames = {"S/No", "Ward", "Room Name" };
		
		DefaultTableModel model = new DefaultTableModel(data, columnNames);
		rooms_table.setModel(model);
		rooms_table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		rooms_table.getColumnModel().getColumn(0).setPreferredWidth(30);
		rooms_table.getColumnModel().getColumn(1).setPreferredWidth(120);
		rooms_table.getColumnModel().getColumn(2).setPreferredWidth(120);
		
		repaint();
		revalidate();
	}
			
	//client method clear bed entry form
	public void clearBedForm(){
		bedNoField.setText(""); 
		bedDetailsField.setText(""); 		
		bedChargeField.setText("0.00");		
		roomSelectionBox.setSelectedIndex(0);
	}

	//client method to update table. calls server
	public void updateBedsTable(ArrayList<Bed> bedlist){
				
		Object[][] data = new Object[bedlist.size()][4];
		for(int i=0; i<bedlist.size(); i++){ 
			data[i][0] = (i+1);
			data[i][2] = bedlist.get(i).getBedDetails();
			data[i][1] = bedlist.get(i).getWardName();			
			data[i][3] = bedlist.get(i).getBedCharge();
			
		}
		
		Object[] columnNames = {"S/No", "Room", "Bed Name", "Charge" };
		
		DefaultTableModel model = new DefaultTableModel(data, columnNames);
		beds_table.setModel(model);
		beds_table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		beds_table.getColumnModel().getColumn(0).setPreferredWidth(50);
		beds_table.getColumnModel().getColumn(1).setPreferredWidth(150);
		beds_table.getColumnModel().getColumn(2).setPreferredWidth(200);		
		beds_table.getColumnModel().getColumn(3).setPreferredWidth(100);
			
		repaint();
		revalidate();	
		
	}
	
	
	public static void showMessage(String message){
		final JDialog dialog = new JDialog();
		dialog.setAlwaysOnTop(true);
		JOptionPane.showMessageDialog(dialog, message);
	}
	
	
	
	
}
