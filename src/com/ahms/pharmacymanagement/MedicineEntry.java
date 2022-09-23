package com.ahms.pharmacymanagement;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import com.ahms.api.PharmacyManagementInterface;
import com.ahms.clientinterface.InterfaceGenerator;
import com.ahms.pharmarcymgt.entities.Medicine;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.SwingConstants;

public class MedicineEntry extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField medCodeField;
	private JTextField puField;
	private JTextArea medDescArea;
	@SuppressWarnings("rawtypes")
	private JComboBox formBox;
	@SuppressWarnings("rawtypes")
	private JComboBox countingBox;
	private JButton btnUpdate;
	private JTable table;
	private JButton btnSave;
	private JLabel lblNewLabel_1;
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MedicineEntry frame = new MedicineEntry();
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
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public MedicineEntry() {
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("Elite HMS -  Medicine Entry Form");
		setBounds(300, 100, 833, 523);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		PharmacyManagementInterface ph_interface = InterfaceGenerator.getPharmManagementInterface();
		
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Medicine Details", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(10, 50, 339, 429);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Medicine Code");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel.setBounds(10, 44, 78, 20);
		panel.add(lblNewLabel);
		
		JLabel lblMedicineDescription = new JLabel("Drug Name");
		lblMedicineDescription.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblMedicineDescription.setBounds(10, 75, 104, 20);
		panel.add(lblMedicineDescription);
		
		JLabel lblMedicineForm = new JLabel("Medicine Form");
		lblMedicineForm.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblMedicineForm.setBounds(10, 167, 78, 20);
		panel.add(lblMedicineForm);
		
		medCodeField = new JTextField(); 
		medCodeField.setEditable(false);
		medCodeField.setBounds(98, 44, 86, 20);
		panel.add(medCodeField);
		medCodeField.setColumns(10);
		
		medDescArea = new JTextArea();  
		medDescArea.setWrapStyleWord(true);
		medDescArea.setLineWrap(true);
		medDescArea.setBounds(10, 106, 317, 46);
		panel.add(medDescArea);
		
		formBox = new JComboBox();
		formBox.setModel(new DefaultComboBoxModel(new String[] {"--Select", "Capsule", "Cream", "Drops", "Infusion", "Injection", "Suppository", "Syrup", "Tablet"}));
		formBox.setBounds(98, 167, 165, 20);
		panel.add(formBox);
		
		puField = new JTextField();
		puField.setBounds(98, 252, 86, 20);
		//panel.add(puField);
		puField.setColumns(10);
		
		JLabel lblCountingType = new JLabel("Counting Type");
		lblCountingType.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblCountingType.setBounds(10, 205, 78, 20);
		panel.add(lblCountingType);
		
		countingBox = new JComboBox();
		countingBox.setModel(new DefaultComboBoxModel(new String[] {"--Select", "Countable", "Non-countable"}));
		countingBox.setBounds(98, 205, 165, 20);
		panel.add(countingBox);
		
		btnSave = new JButton("Save ");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(medDescArea.getText().length()<1){
					showMessage("Enter valid text for medicine name.");
				}else if(formBox.getSelectedItem().toString().equalsIgnoreCase("--Select")){
					showMessage("Select medicine form.");
				}else if(countingBox.getSelectedItem().toString().equalsIgnoreCase("--Select")){
					showMessage("Select counting type.");
				}else{
					try{

						Medicine med = new Medicine();
					
						med.setMedicineDescription(medDescArea.getText());
						med.setMedicineForm(formBox.getSelectedItem().toString());
						med.setPurchaseUnit(puField.getText());
						med.setCountingState(countingBox.getSelectedItem().toString());
						
						Medicine vv = ph_interface.saveNewMedicine(med);
						showMessage("Success!");
						medCodeField.setText(vv.getMedicineCode());
						btnSave.setEnabled(false);
						updateTable(ph_interface.getAllMedicines());
						
						
						
					}catch(Exception ex){ ex.printStackTrace(); }
					
				}
			}
		});
		btnSave.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnSave.setBounds(91, 395, 113, 23);
		panel.add(btnSave);
		
		JButton btnNewMedicine = new JButton("Clear");
		btnNewMedicine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				refreshForm();
			}
		});
		btnNewMedicine.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnNewMedicine.setBounds(214, 395, 113, 23);
		panel.add(btnNewMedicine);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(359, 50, 441, 360);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
			int xy = table.getSelectedRow();
			
			String medCode = table.getValueAt(xy, 1).toString();
			String drugname = table.getValueAt(xy, 2).toString();
			String medForm = table.getValueAt(xy, 3).toString();
			String count = table.getValueAt(xy, 4).toString();
			
			medCodeField.setText(medCode);
			medDescArea.setText(drugname); 
			formBox.setSelectedItem(medForm);
			puField.setText("");
			countingBox.setSelectedItem(count);
			
			btnUpdate.setEnabled(true);
			btnSave.setEnabled(false);
			
			
			}
		});
		scrollPane.setViewportView(table);
		
		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				MedicinePurchase.updateMedsCombo();
				
			}
		});
		btnClose.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnClose.setBounds(709, 440, 89, 23);
		contentPane.add(btnClose);
		
		btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Medicine md = new Medicine();
				md.setMedicineCode(medCodeField.getText());
				md.setMedicineDescription(medDescArea.getText());
				md.setMedicineForm(formBox.getSelectedItem().toString());
				md.setCountingState(countingBox.getSelectedItem().toString());
				md.setPurchaseUnit("");
				
				try {
					ph_interface.updateMedicne(md);					
					showMessage("Update Successful!");
					updateTable(ph_interface.getAllMedicines());
					refreshForm();
				} catch (RemoteException e) {				
					e.printStackTrace();
				}
			}
		});
		btnUpdate.setEnabled(false);
		btnUpdate.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnUpdate.setBounds(610, 440, 89, 23);
		contentPane.add(btnUpdate);
		
		lblNewLabel_1 = new JLabel("Elite HMS - Medicine Entry");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_1.setBounds(10, 11, 797, 28);
		contentPane.add(lblNewLabel_1);
		
		try {
			updateTable(ph_interface.getAllMedicines());
		} catch (RemoteException e) {		
			e.printStackTrace();
		}
		
	}
	
	//clent method to reset the form
	public void refreshForm(){
		medCodeField.setText("");
		medDescArea.setText("");
		formBox.setSelectedIndex(0); 
		puField.setText("");	
		countingBox.setSelectedIndex(0);
		
		btnUpdate.setEnabled(false);
		btnSave.setEnabled(true);
	}
	
	
	
	//client method to update the medicines table
	public void updateTable(ArrayList<Medicine> medlist){
		Object[][] data = new Object[medlist.size()][5];
		for(int i=0; i<medlist.size(); i++){ 
			data[i][0] = (i+1);
			data[i][1] = medlist.get(i).getMedicineCode();
			data[i][2] = medlist.get(i).getMedicineDescription();
			data[i][3] = medlist.get(i).getMedicineForm();
			data[i][4] = medlist.get(i).getCountingState();
			
		}
		Object[] columnNames = {"S/No", "Drug Code", "Drug Name", "Medicine Form", "Counting Type" };
		
		DefaultTableModel model = new DefaultTableModel(data, columnNames);
		table.setModel(model);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getColumnModel().getColumn(0).setPreferredWidth(60);
		table.getColumnModel().getColumn(1).setPreferredWidth(100);
		table.getColumnModel().getColumn(2).setPreferredWidth(300);
		table.getColumnModel().getColumn(3).setPreferredWidth(120);
		table.getColumnModel().getColumn(3).setPreferredWidth(100);
		
	}


	public static void showMessage(String message){
		final JDialog dialog = new JDialog();
		dialog.setAlwaysOnTop(true);
		JOptionPane.showMessageDialog(dialog, message);
	}

}
