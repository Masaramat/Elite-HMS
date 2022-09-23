package com.ahms.hospitalmanagement;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.ahms.api.HospitalManagementInterface;
import com.ahms.clientinterface.InterfaceGenerator;
import com.ahms.hmgt.entities.FamilyCard;


import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FamilyCardSearchFrame extends JFrame {

	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private JTextField textField;
	
	private ArrayList<FamilyCard> family_list = new ArrayList<>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FamilyCardSearchFrame frame = new FamilyCardSearchFrame();
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
	public FamilyCardSearchFrame() {
		setAlwaysOnTop(true);
		setTitle("Elite HMS - Search Family Card ");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(400, 150, 540, 380);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		final HospitalManagementInterface hm_interface = InterfaceGenerator.getHospitalManagementInterface();
		
		JLabel lblNewLabel = new JLabel("Search Family Card");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel.setBounds(10, 11, 504, 23);
		contentPane.add(lblNewLabel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 45, 504, 198);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MasterPatientIndex.updateFamilyCardInfo(family_list.get(table.getSelectedRow()));
				dispose();
			}
		});
		scrollPane.setViewportView(table);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Family Name", "Family Number"}));
		comboBox.setBounds(86, 282, 163, 18);
		contentPane.add(comboBox);
		
		JLabel lblNewLabel_1 = new JLabel("Search By:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_1.setBounds(10, 284, 66, 14);
		contentPane.add(lblNewLabel_1);
		
		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {				
				try {
					if(textField.getText().length()<1) {
						family_list.clear();
						updateFamilyCardTable(family_list);
					}else {
						family_list = hm_interface.getFamilyCards(comboBox.getSelectedItem().toString(), textField.getText());
						updateFamilyCardTable(family_list);
					}
					
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		
		textField.setBounds(269, 282, 195, 18);
		contentPane.add(textField);
		textField.setColumns(10);
		
		updateFamilyCardTable(family_list);
	}
	
	// client methods to update user  table
		public void updateFamilyCardTable(ArrayList<FamilyCard> list) {
			Object[][] data = new Object[list.size()][4];
					
			for(int i=0; i<list.size(); i++) {
				data[i][0] = (i+1);
				data[i][1] = list.get(i).getFamilyNo();
				data[i][2] = list.get(i).getFamilyName();
				data[i][3] = list.get(i).getDate();						
			}
			
			Object[] columnNames = {"S/No", "Family No.", "Family Name", "Reg. Date"};
			
			DefaultTableModel model = new DefaultTableModel(data, columnNames);
			table.setModel(model);
		
			table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			table.getColumnModel().getColumn(0).setPreferredWidth(60);
			table.getColumnModel().getColumn(1).setPreferredWidth(100);
			table.getColumnModel().getColumn(2).setPreferredWidth(220);
			table.getColumnModel().getColumn(3).setPreferredWidth(140);
					
		}
	
		public static void showMessage(String message){
			final JFrame dialog = new JFrame();
			dialog.setAlwaysOnTop(true);
			JOptionPane.showMessageDialog(dialog, message);
		}
	
	
}
