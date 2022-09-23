package com.ahms.clinicmanagement;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import com.ahms.api.ClinicManagementInterface;
import com.ahms.clientinterface.InterfaceGenerator;
import com.ahms.clinicmgt.entities.DiseaseCategory;
import com.ahms.clinicmgt.entities.DiseaseCondition;
import com.ahms.clinicmgt.entities.DiseaseSubcategory;
import com.ahms.clinicmgt.entities.DiseeaseIndex;
import com.ahms.clinicmgt.entities.PatientVisit;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.RemoteException;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Diagnosis extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField invoiceField;
	private JTextField searchField;
	@SuppressWarnings("rawtypes")
	private JComboBox categoryBox;
	@SuppressWarnings("rawtypes")
	private JComboBox subcategoryBox;
	@SuppressWarnings("rawtypes")
	private JList conditionList;
	private JLabel lblDiseasesAndConditions;
	private JLabel lblCategories;
	private JLabel lblSubcategory;
	private JLabel lblDiseaseContion;
	private JButton btnAdd;
	private JScrollPane scrollPane;
	private JTable table;
	private JTextArea textArea;
	private JCheckBox chckbxUseFreeText;
	
	private ArrayList<DiseaseCategory> lisst;
	private ArrayList<DiseaseSubcategory> sublist = null;
	private ArrayList<DiseaseCondition> conlist = null;
	private ArrayList<DiseeaseIndex> dilist = null;
	private JTextField hospNoField;
	private JTextField nameField;
	private JTextField genderField;
	
	PatientVisit pv = null;
	private JPanel panel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Diagnosis frame = new Diagnosis("","","", "");
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
	public Diagnosis(String invoice_no, String two, String three, String source) throws RemoteException{
		setTitle("Elite HMS - Diagnosis      ");
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(300, 70, 756, 666);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		ClinicManagementInterface cm_interface = InterfaceGenerator.getClinicManagementInterface();
		pv = cm_interface.getPatientVisit(invoice_no);
		
		lisst = cm_interface.getAllDiseaseCategories();
		
		JLabel label = new JLabel("Invoice No");
		label.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label.setBounds(22, 52, 64, 21);
		contentPane.add(label);
		
		invoiceField = new JTextField();
		invoiceField.setEditable(false);
		invoiceField.setText(invoice_no);
		invoiceField.setColumns(10);
		invoiceField.setBounds(96, 51, 114, 23);
		contentPane.add(invoiceField);
		
		
		
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 463, 722, 113);
		contentPane.add(scrollPane_1);
		
		table = new JTable();
		scrollPane_1.setViewportView(table);
		
		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				 OPDConsultation.opd_diagnosis_table.requestFocus();				 
				 dispose();
				 System.gc();			
			}
		});
		btnClose.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnClose.setBounds(628, 593, 104, 23);
		contentPane.add(btnClose);
		
		JLabel label_1 = new JLabel("Hospital No");
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label_1.setBounds(23, 21, 63, 20);
		contentPane.add(label_1);
		
		hospNoField = new JTextField();
		hospNoField.setText(pv.getHospitalNo());
		hospNoField.setEditable(false);
		hospNoField.setColumns(10);
		hospNoField.setBounds(96, 21, 114, 20);
		contentPane.add(hospNoField);
		
		JLabel label_2 = new JLabel("Patient Name");
		label_2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label_2.setBounds(354, 21, 84, 20);
		contentPane.add(label_2);
		
		nameField = new JTextField();
		nameField.setText(pv.getSurname()+" "+pv.getOthernames());
		nameField.setEditable(false);
		nameField.setColumns(10);
		nameField.setBounds(448, 21, 273, 20);
		contentPane.add(nameField);
		
		JLabel label_3 = new JLabel("Gender");
		label_3.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label_3.setBounds(354, 52, 59, 20);
		contentPane.add(label_3);
		
		genderField = new JTextField();
		genderField.setText(pv.getGender());
		genderField.setEditable(false);
		genderField.setColumns(10);
		genderField.setBounds(448, 52, 64, 20);
		contentPane.add(genderField);
		
		
		btnAdd = new JButton("Save ");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(chckbxUseFreeText.isSelected() && textArea.getText().length()<1){
					showMessage("Enter valid text for disease condition.");
				
				}else if(chckbxUseFreeText.isSelected() && textArea.getText().length()>1){
					
					DiseeaseIndex dindex = new DiseeaseIndex();
					dindex.setInvoiceNo(invoiceField.getText());
					dindex.setCaseNo("");//
					dindex.setDiseaseCode("OTH");
					dindex.setDiagnosisType("");
					dindex.setDiseaseCondition(textArea.getText());					
					
					try {
						cm_interface.saveDiagnosis(dindex);
						showMessage("Successful!");
					
						dilist = cm_interface.getAllDiagnosis(invoice_no);
						updateTable(dilist);
					} catch (RemoteException e) {		e.printStackTrace();  }
				
				}else if(!chckbxUseFreeText.isSelected() && conditionList.getSelectedIndex() <0){
					showMessage("Select disease condition from list.");

				}else{
					
					DiseeaseIndex dindex = new DiseeaseIndex();
					dindex.setInvoiceNo(invoiceField.getText());
					dindex.setCaseNo("");//
					dindex.setDiseaseCode(conlist.get(conditionList.getSelectedIndex()).getConditionCode());
					dindex.setDiagnosisType("");
					dindex.setDiseaseCondition(conlist.get(conditionList.getSelectedIndex()).getConditionDesc());
					
					try {
						cm_interface.saveDiagnosis(dindex);
						showMessage("Successful!");
						
						dilist = cm_interface.getAllDiagnosis(invoice_no);
						updateTable(dilist);
						conditionList.clearSelection();
						
					} catch (RemoteException e) {		e.printStackTrace();  }
				}				
			}
		});
		btnAdd.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnAdd.setBounds(514, 593, 104, 23);
		contentPane.add(btnAdd);
		
		panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Add New Diagnosis", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 103, 722, 339);
		contentPane.add(panel);
		panel.setLayout(null);
		
		lblDiseasesAndConditions = new JLabel("Search");
		lblDiseasesAndConditions.setBounds(10, 31, 51, 21);
		panel.add(lblDiseasesAndConditions);
		lblDiseasesAndConditions.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		searchField = new JTextField();
		searchField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(searchField.getText().length()<1) {
					categoryBox.setEnabled(true);
					subcategoryBox.setEnabled(true);
				}else {
					categoryBox.setEnabled(false);
					subcategoryBox.setEnabled(false);
					try {
						conlist = cm_interface.searchDiseaseCondtions(searchField.getText());
						updateConditionList(conlist);
					} catch (RemoteException e2) {	e2.printStackTrace();		}
				}
			}
		});
		searchField.setBounds(122, 31, 386, 21);
		panel.add(searchField);
		searchField.setColumns(10);
		
		lblCategories = new JLabel("Category");
		lblCategories.setBounds(10, 63, 102, 21);
		panel.add(lblCategories);
		lblCategories.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		categoryBox = new JComboBox();
		categoryBox.setBounds(122, 63, 485, 20);
		panel.add(categoryBox);
		categoryBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				subcategoryBox.removeAllItems();
				int xx = categoryBox.getSelectedIndex();
				if(xx>=0){
					try{
						sublist = cm_interface.getDiseaseSubcategories(lisst.get(xx).getGroupId());
						for(int i = 0; i<sublist.size(); i++){
							subcategoryBox.addItem(sublist.get(i).getSubgroupCode()+"   " +sublist.get(i).getSubgroupDesc());
						}
					}catch(Exception e){e.printStackTrace();}
				
				}
			}
		});
		AutoCompleteDecorator.decorate(categoryBox);
		
		lblSubcategory = new JLabel("Sub-Category");
		lblSubcategory.setBounds(10, 95, 99, 21);
		panel.add(lblSubcategory);
		lblSubcategory.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		subcategoryBox = new JComboBox();
		subcategoryBox.setBounds(122, 95, 568, 20);
		panel.add(subcategoryBox);
		
		lblDiseaseContion = new JLabel("Disease Condition\r\n");
		lblDiseaseContion.setBounds(10, 137, 102, 21);
		panel.add(lblDiseaseContion);
		lblDiseaseContion.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(122, 138, 568, 108);
		panel.add(scrollPane);
		
		conditionList = new JList(); 
		scrollPane.setViewportView(conditionList);
		
		textArea = new JTextArea();
		textArea.setBounds(122, 271, 568, 51);
		panel.add(textArea);
		textArea.setEditable(false);
		
				
		chckbxUseFreeText = new JCheckBox("Use Free Text");
		chckbxUseFreeText.setBounds(2, 272, 114, 23);
		panel.add(chckbxUseFreeText);
		chckbxUseFreeText.setFont(new Font("Tahoma", Font.BOLD, 11));
		chckbxUseFreeText.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(chckbxUseFreeText.isSelected()){
					conditionList.setEnabled(false);
					subcategoryBox.setEnabled(false);
					categoryBox.setEnabled(false);
					searchField.setEnabled(false);
					textArea.setEditable(true);
				}else if(!chckbxUseFreeText.isSelected()){
					conditionList.setEnabled(true);
					subcategoryBox.setEnabled(true);
					categoryBox.setEnabled(true);
					searchField.setEnabled(true);
					textArea.setText("");
					textArea.setEditable(false);

				}
			}
		});
		subcategoryBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				conditionList.removeAll();
				int xx = subcategoryBox.getSelectedIndex();
				if(xx>=0){
				try {
					conlist = cm_interface.getDiseaseCondtions(sublist.get(xx).getSubgroupId());
					updateConditionList(conlist);
				} catch (RemoteException e) {	e.printStackTrace();		}
				
				}
			}
		});
		
		for(int i = 0; i < lisst.size(); i++){
			categoryBox.addItem(lisst.get(i).getGroupDesc());
		}
		
		
		dilist = cm_interface.getAllDiagnosis(invoice_no);
		updateTable(dilist);
		
		
		addWindowListener(new WindowAdapter() {
			  public void windowClosing(WindowEvent e) {				
				  OPDConsultation.opd_diagnosis_table.requestFocus();
				  System.gc();
				 
			  }
			});
		
		
		
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void updateConditionList(ArrayList<DiseaseCondition> list){
		DefaultListModel dml = new DefaultListModel();
		for(int i=0; i<list.size(); i++){
			dml.addElement(list.get(i).getConditionCode() +"    " +list.get(i).getConditionDesc());
		}
		conditionList.setModel(dml);
	}

	//client method to update the table
	public void updateTable(ArrayList<DiseeaseIndex> list){
		Object[][] data = new Object[list.size()][4];
		for(int i=0; i<list.size(); i++){ 
			data[i][0] = (i+1);
			data[i][1] = list.get(i).getDiseaseCode();
			data[i][2] = list.get(i).getDiseaseCondition();
			data[i][3] = list.get(i).getDiagnosisType();
		}
		
		Object[] columnNames = { "S/No ", "Disease Code", "Disease Condition", "Diagnosis Type"};
		DefaultTableModel model = new DefaultTableModel(data, columnNames);
		table.setModel(model);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getColumnModel().getColumn(0).setPreferredWidth(80);
		table.getColumnModel().getColumn(1).setPreferredWidth(120);
		table.getColumnModel().getColumn(2).setPreferredWidth(600);
		table.getColumnModel().getColumn(3).setPreferredWidth(150);
	}
	
	
	public void showMessage(String message){
		JFrame jf = new JFrame();
		jf.setAlwaysOnTop(true);
		JOptionPane.showMessageDialog(jf, message);
		
	}
	
	
	
	
}
