package com.ahms.hospitalmanagement;



import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.CardLayout;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;

import com.ahms.api.HospitalManagementInterface;
import com.ahms.clientinterface.InterfaceGenerator;
import com.ahms.hmgt.entities.FamilyCard;
import com.ahms.hmgt.entities.PatientBiodata;
import com.ahms.hmgt.entities.State;
import com.ahms.hmgt.entities.UserCard;
import com.toedter.calendar.JDateChooser;
import java.awt.Dimension;
import javax.swing.JRadioButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;

import javax.swing.JCheckBox;
import javax.swing.border.TitledBorder;
@SuppressWarnings("rawtypes")
public class MasterPatientIndex extends JFrame {

	
	private static final long serialVersionUID = 1225991635119174140L;
	private JPanel contentPane;
	private JTextField hospNoField;
	private JTextField surnameField;
	private JTextField occupationField;
	private JTextField phone1Field;
	private JTextField firstnameField;
	private JTextField emailField;
	private JTextField nokSurnameField;
	private JTextField nokRelationship;
	private JTextField nokMobile;
	private JTextField regOfficerField;
	private JTextField amountField;
	private ButtonGroup gender;
	private JTextArea addressTextArea;
	private JCheckBox chckbxFamilyCard;
	private JDateChooser dobChooser;
	private JRadioButton radioMale;
	private JRadioButton radioFemale;
	private JComboBox<Object> stateBox;
	private JComboBox<Object> religionBox;
	private JComboBox<Object> maritalBox;
	
	
	private JComboBox bloodGroupBox;
	private JComboBox genotypeBox;
	private JComboBox nationalityBox;
	private JTextArea nokAddressArea;
	//buttons
	private JButton btnSave;
	private JButton btnUpdate;
	private JButton btnSearchButton;
	
	private CardLayout cLayout = new CardLayout();
	
	
	private ArrayList<State> statelist = new ArrayList<>();

	private ArrayList<FamilyCard> family_list; 
	
	
	
	ArrayList<PatientBiodata> patlist;
	private ArrayList<String> errorlist = new ArrayList<>();
	private static JTextField familyNoField;
	private static JTextField familyNameField;
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MasterPatientIndex frame = new MasterPatientIndex(new UserCard(), "", new PatientBiodata());
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
	@SuppressWarnings("unchecked")
	public MasterPatientIndex(UserCard user, String source, PatientBiodata biodata) {
		setTitle("Elite HMS");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(350, 70, 743, 661);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		final HospitalManagementInterface hm_interface = InterfaceGenerator.getHospitalManagementInterface();
		try {
			family_list = hm_interface.getAllFamilyCards();
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}
		contentPane.setLayout(null);
		
		gender = new ButtonGroup();
		
		
		
		JPanel switch_panel = new JPanel();
		switch_panel.setBounds(0, 0, 714, 611);
		switch_panel.setLayout(cLayout);
		contentPane.add(switch_panel);
		
		JPanel panel_one = new JPanel();
		switch_panel.add(panel_one, "p_one");
		panel_one.setLayout(null);
		
		JLabel lblL = new JLabel("Fields marked ");
		lblL.setBounds(20, 53, 72, 14);
		panel_one.add(lblL);
		lblL.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblL.setHorizontalAlignment(SwingConstants.LEFT);
		
		chckbxFamilyCard = new JCheckBox("Use Family Card");
		chckbxFamilyCard.setBounds(20, 74, 109, 23);
		panel_one.add(chckbxFamilyCard);
		chckbxFamilyCard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(chckbxFamilyCard.isSelected()){
					btnSearchButton.setEnabled(true);
					amountField.setText("0.00");
					amountField.setEnabled(false);
				
				}else{
					btnSearchButton.setEnabled(false);
					familyNameField.setText("");
					familyNoField.setText("");
					amountField.setEnabled(true);
				}
				
			}
		});
		chckbxFamilyCard.setHorizontalAlignment(SwingConstants.LEFT);
		chckbxFamilyCard.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		JLabel label_34 = new JLabel("*");
		label_34.setBounds(91, 53, 15, 14);
		panel_one.add(label_34);
		label_34.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label_34.setForeground(Color.RED);
		
		JLabel lblAreCompulsory = new JLabel("are compulsory");
		lblAreCompulsory.setBounds(106, 53, 109, 14);
		panel_one.add(lblAreCompulsory);
		lblAreCompulsory.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		JLabel lblFamilyName = new JLabel("Family Name / No.");
		lblFamilyName.setBounds(20, 104, 97, 20);
		panel_one.add(lblFamilyName);
		lblFamilyName.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		familyNoField = new JTextField();
		familyNoField.setBounds(391, 104, 83, 20);
		panel_one.add(familyNoField);
		familyNoField.setEditable(false);
		familyNoField.setColumns(10);
		
		JLabel label = new JLabel("Hospital No: ");
		label.setBounds(27, 147, 83, 23);
		panel_one.add(label);
		label.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		JLabel lblLastName = new JLabel("Last Name");
		lblLastName.setBounds(25, 181, 85, 22);
		panel_one.add(lblLastName);
		lblLastName.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		JLabel lblOtherNames = new JLabel("Other Names");
		lblOtherNames.setBounds(27, 214, 79, 22);
		panel_one.add(lblOtherNames);
		lblOtherNames.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		JLabel label_5 = new JLabel("D.O.B");
		label_5.setBounds(27, 242, 72, 23);
		panel_one.add(label_5);
		label_5.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		JLabel label_6 = new JLabel("Gender");
		label_6.setBounds(27, 276, 83, 20);
		panel_one.add(label_6);
		label_6.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		JLabel lblNationality = new JLabel("Nationality");
		lblNationality.setBounds(27, 304, 83, 22);
		panel_one.add(lblNationality);
		lblNationality.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		JLabel lblStateOfOrigin = new JLabel("State of Origin");
		lblStateOfOrigin.setBounds(25, 336, 85, 21);
		panel_one.add(lblStateOfOrigin);
		lblStateOfOrigin.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		JLabel lblMaritalStatus = new JLabel("Marital Status");
		lblMaritalStatus.setBounds(27, 369, 83, 17);
		panel_one.add(lblMaritalStatus);
		lblMaritalStatus.setHorizontalAlignment(SwingConstants.LEFT);
		lblMaritalStatus.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
				
		maritalBox = new JComboBox<Object>();
		maritalBox.setBounds(120, 368, 168, 20);
		panel_one.add(maritalBox);
		maritalBox.setModel(new DefaultComboBoxModel(new String[] {"Select", "Single", "Married", "Seperated", "Divorced", "Widowed", "n/a"}));
		
		stateBox = new JComboBox<Object>();
		stateBox.setBounds(120, 337, 168, 20);
		panel_one.add(stateBox);
		
		JLabel label_27 = new JLabel("*");
		label_27.setBounds(274, 369, 15, 14);
		panel_one.add(label_27);
		label_27.setForeground(Color.RED);
		
		radioMale = new JRadioButton("Male");
		radioMale.setBounds(120, 276, 64, 23);
		panel_one.add(radioMale);
		gender.add(radioMale);
		
		radioFemale = new JRadioButton("Female");
		radioFemale.setBounds(186, 276, 72, 23);
		panel_one.add(radioFemale);
		gender.add(radioFemale);
		
		JLabel label_28 = new JLabel("*");
		label_28.setBounds(264, 276, 15, 14);
		panel_one.add(label_28);
		label_28.setForeground(Color.RED);
		
		dobChooser = new JDateChooser();
		dobChooser.setBounds(120, 245, 138, 20);
		dobChooser.setPreferredSize(new Dimension(40, 20));
		dobChooser.setDateFormatString("yyyy-MM-dd");
		dobChooser.setMaxSelectableDate(new Date());
		dobChooser.setDate(new Date());
		panel_one.add(dobChooser);
		
		
		firstnameField = new JTextField();
		firstnameField.setBounds(120, 214, 168, 20);
		panel_one.add(firstnameField);
		firstnameField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
			}
		});
		firstnameField.setColumns(10);
		
		surnameField = new JTextField();
		surnameField.setBounds(120, 180, 168, 21);
		panel_one.add(surnameField);
		surnameField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
			}
		});
		surnameField.setColumns(10);
		
		hospNoField = new JTextField();
		hospNoField.setBounds(120, 149, 168, 20);
		panel_one.add(hospNoField);
		hospNoField.setEditable(false);
		hospNoField.setColumns(10);
		
		JLabel label_23 = new JLabel("*");
		label_23.setBounds(274, 179, 15, 14);
		panel_one.add(label_23);
		label_23.setForeground(Color.RED);
		
		JLabel label_21 = new JLabel("*");
		label_21.setBounds(274, 212, 15, 19);
		panel_one.add(label_21);
		label_21.setForeground(Color.RED);
		
		JLabel label_24 = new JLabel("*");
		label_24.setBounds(264, 247, 15, 14);
		panel_one.add(label_24);
		label_24.setForeground(Color.RED);
		
		nationalityBox = new JComboBox();
		nationalityBox.setBounds(120, 306, 168, 20);
		panel_one.add(nationalityBox);
		nationalityBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					statelist = hm_interface.getAllStates();
					if(nationalityBox.getSelectedItem().toString().equalsIgnoreCase("Nigerian")){
						for(int i=0; i<statelist.size(); i++){
				            stateBox.addItem(statelist.get(i).getName());
				            
				        }
					}else{	
						stateBox.removeAllItems();
						
						}
					
				} catch (RemoteException e) {
					e.printStackTrace();
				}
				
			}
		});
		nationalityBox.setModel(new DefaultComboBoxModel(new String[] {"--Select", "Nigerian", "Other"}));
		
		JLabel label_9 = new JLabel("Religion");
		label_9.setBounds(26, 403, 83, 17);
		panel_one.add(label_9);
		label_9.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		religionBox = new JComboBox<Object>();
		religionBox.setBounds(120, 400, 168, 19);
		panel_one.add(religionBox);
		religionBox.setModel(new DefaultComboBoxModel(new String[] {"N/A", "Christianity", "Islam", "Traditionalist", "Other"}));
		
		JLabel lblBloodGroup = new JLabel("Blood Group");
		lblBloodGroup.setBounds(27, 431, 76, 17);
		panel_one.add(lblBloodGroup);
		lblBloodGroup.setHorizontalAlignment(SwingConstants.LEFT);
		lblBloodGroup.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		bloodGroupBox = new JComboBox();
		bloodGroupBox.setBounds(120, 430, 83, 21);
		panel_one.add(bloodGroupBox);
		bloodGroupBox.setMaximumRowCount(6);
		bloodGroupBox.setModel(new DefaultComboBoxModel(new String[] {"N/A", "A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"}));
		
		JLabel lblGender = new JLabel("Genotype");
		lblGender.setBounds(28, 459, 83, 22);
		panel_one.add(lblGender);
		lblGender.setHorizontalAlignment(SwingConstants.LEFT);
		lblGender.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		genotypeBox = new JComboBox();
		genotypeBox.setBounds(120, 462, 83, 19);
		panel_one.add(genotypeBox);
		genotypeBox.setModel(new DefaultComboBoxModel(new String[] {"N/A", "AA", "AS", "SS"}));
		
		JLabel lblOccupation = new JLabel("Occupation");
		lblOccupation.setBounds(20, 493, 83, 17);
		panel_one.add(lblOccupation);
		lblOccupation.setHorizontalAlignment(SwingConstants.LEFT);
		lblOccupation.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		occupationField = new JTextField();
		occupationField.setBounds(120, 492, 167, 20);
		panel_one.add(occupationField);
		occupationField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
			}
		});
		occupationField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Patient Registration Form");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(10, 11, 694, 23);
		panel_one.add(lblNewLabel);
		
		JButton btnClose_1 = new JButton("Close");
		btnClose_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnClose_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnClose_1.setBounds(615, 577, 89, 23);
		panel_one.add(btnClose_1);
		
		JButton btnNext = new JButton("Next >");
		btnNext.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				errorlist.clear();
				validatePageOne();
				
				if(errorlist.size()>0){
					showMessage(errorlist.size() + " errors on the registration form. \n "+errorlist.get(0));
					
				}else{
				
				cLayout.show(switch_panel, "p_two");
				}
			}
		});
		btnNext.setBounds(516, 577, 89, 23);
		panel_one.add(btnNext);
		
		JLabel label_24_1 = new JLabel("*");
		label_24_1.setForeground(Color.RED);
		label_24_1.setBounds(298, 181, 15, 14);
		panel_one.add(label_24_1);
		
		JLabel label_24_2 = new JLabel("*");
		label_24_2.setForeground(Color.RED);
		label_24_2.setBounds(298, 309, 15, 14);
		panel_one.add(label_24_2);
		
		JLabel label_24_3 = new JLabel("*");
		label_24_3.setForeground(Color.RED);
		label_24_3.setBounds(298, 336, 15, 14);
		panel_one.add(label_24_3);
		
		familyNameField = new JTextField();
		familyNameField.setEditable(false);
		familyNameField.setBounds(120, 104, 261, 20);
		panel_one.add(familyNameField);
		familyNameField.setColumns(10);
		
		btnSearchButton = new JButton("Search");
		btnSearchButton.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnSearchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FamilyCardSearchFrame fx = new FamilyCardSearchFrame();
				fx.setVisible(true);
			}
		});
		btnSearchButton.setEnabled(false);
		btnSearchButton.setBounds(488, 103, 72, 23);
		panel_one.add(btnSearchButton);
		
		JPanel panel_two = new JPanel();
		switch_panel.add(panel_two, "p_two");
		panel_two.setLayout(null);
		
		JLabel lblAmount = new JLabel("Reg. Fee");
		lblAmount.setBounds(23, 424, 97, 20);
		panel_two.add(lblAmount);
		lblAmount.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		amountField = new JTextField();
		amountField.setBounds(130, 425, 86, 20);
		panel_two.add(amountField);
		amountField.setHorizontalAlignment(SwingConstants.RIGHT);
		amountField.setColumns(10);
		
		JLabel lblRegOfficer = new JLabel("Reg. Officer: ");
		lblRegOfficer.setBounds(23, 455, 97, 20);
		panel_two.add(lblRegOfficer);
		lblRegOfficer.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		regOfficerField = new JTextField();
		regOfficerField.setText(user.getFullNames());
		regOfficerField.setEditable(false);
		regOfficerField.setBounds(130, 456, 253, 20);
		panel_two.add(regOfficerField);
		regOfficerField.setColumns(10);
		
		
		btnSave = new JButton("Save");
		btnSave.setBounds(508, 543, 93, 23);
		panel_two.add(btnSave);
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				errorlist.clear();
				validatePageTwo();
				
				if(errorlist.size()>0){
					showMessage(errorlist.size() + " errors on the registration form. \n "+errorlist.get(0));
					
				}else{
					
					try {
						PatientBiodata pbd = submitPatientBiodata();
						PatientBiodata patientInfo = hm_interface.saveNewRegistration(pbd);
						JOptionPane.showMessageDialog(null, "New Patient Resgitration SuccessFul. Hospital No. is " + patientInfo.getHospital_no());
						hospNoField.setText(patientInfo.getHospital_no());
						btnSave.setEnabled(false);
						btnUpdate.setEnabled(true);
						cLayout.show(switch_panel, "p_one");
					
					} catch (RemoteException e) {						
						e.printStackTrace();
					}
					
					
				}
								
			}
		});
		btnSave.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		JButton btnPrint = new JButton("Preview");
		btnPrint.setBounds(405, 577, 93, 23);
		panel_two.add(btnPrint);
		btnPrint.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		btnUpdate = new JButton("Update");
		btnUpdate.setBounds(611, 543, 93, 23);
		panel_two.add(btnUpdate);
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				PatientBiodata pbd = submitPatientBiodata();
				try {
					hm_interface.updatePatientBiodata(pbd);
					showMessage("Update successful!");
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
		});
		btnUpdate.setEnabled(false);
		btnUpdate.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		JButton btnNewRegistration = new JButton("Clear");
		btnNewRegistration.setBounds(508, 577, 93, 23);
		panel_two.add(btnNewRegistration);
		btnNewRegistration.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				MasterPatientIndex mpi = new MasterPatientIndex(user, "", new PatientBiodata());
				mpi.setVisible(true);
			}
		});
		btnNewRegistration.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		JButton btnClose = new JButton("Close");
		btnClose.setBounds(611, 577, 93, 23);
		panel_two.add(btnClose);
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			dispose();
			}
		});
		btnClose.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		JButton btnBack = new JButton("< Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cLayout.show(switch_panel, "p_one");
			}
		});
		btnBack.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnBack.setBounds(405, 543, 93, 23);
		panel_two.add(btnBack);
		
		JPanel nok_panel = new JPanel();
		nok_panel.setBorder(new TitledBorder(null, "Next Of Kin", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		nok_panel.setBounds(10, 192, 694, 204);
		panel_two.add(nok_panel);
		nok_panel.setLayout(null);
		
		JLabel lblFullName = new JLabel("Full Name");
		lblFullName.setBounds(20, 27, 97, 20);
		nok_panel.add(lblFullName);
		lblFullName.setHorizontalAlignment(SwingConstants.LEFT);
		lblFullName.setFont(new Font("Tahoma", Font.PLAIN, 13));
		
		nokSurnameField = new JTextField();
		nokSurnameField.setBounds(127, 28, 311, 20);
		nok_panel.add(nokSurnameField);
		nokSurnameField.setColumns(10);
		
		JLabel label_29 = new JLabel("*");
		label_29.setBounds(448, 31, 15, 14);
		nok_panel.add(label_29);
		label_29.setForeground(Color.RED);
		
		JLabel lblAddress = new JLabel("Address");
		lblAddress.setBounds(20, 58, 97, 22);
		nok_panel.add(lblAddress);
		lblAddress.setHorizontalAlignment(SwingConstants.LEFT);
		lblAddress.setFont(new Font("Verdana", Font.PLAIN, 12));
		
		nokAddressArea = new JTextArea();
		nokAddressArea.setBounds(127, 59, 311, 47);
		nok_panel.add(nokAddressArea);
		nokAddressArea.setLineWrap(true);
		nokAddressArea.setWrapStyleWord(true);
		
		JLabel label_31 = new JLabel("*");
		label_31.setBounds(448, 63, 15, 14);
		nok_panel.add(label_31);
		label_31.setForeground(Color.RED);
		
		JLabel label_16 = new JLabel("Relationship");
		label_16.setBounds(20, 116, 82, 20);
		nok_panel.add(label_16);
		label_16.setHorizontalAlignment(SwingConstants.LEFT);
		label_16.setFont(new Font("Verdana", Font.PLAIN, 12));
		
		nokRelationship = new JTextField();
		nokRelationship.setBounds(127, 117, 178, 20);
		nok_panel.add(nokRelationship);
		nokRelationship.setColumns(10);
		
		JLabel label_30 = new JLabel("*");
		label_30.setBounds(315, 117, 15, 14);
		nok_panel.add(label_30);
		label_30.setForeground(Color.RED);
		
		JLabel label_17 = new JLabel("Mobile");
		label_17.setBounds(20, 147, 82, 20);
		nok_panel.add(label_17);
		label_17.setFont(new Font("Verdana", Font.PLAIN, 12));
		
		nokMobile = new JTextField();
		nokMobile.setBounds(127, 148, 144, 19);
		nok_panel.add(nokMobile);
		nokMobile.setColumns(10);
		
		JPanel contact_panel = new JPanel();
		contact_panel.setBorder(new TitledBorder(null, "Contact Info", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contact_panel.setBounds(10, 24, 694, 158);
		panel_two.add(contact_panel);
		contact_panel.setLayout(null);
		
		JLabel lblAddress_1 = new JLabel("Address");
		lblAddress_1.setBounds(20, 26, 93, 22);
		contact_panel.add(lblAddress_1);
		lblAddress_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblAddress_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		
		addressTextArea = new JTextArea();
		addressTextArea.setBounds(123, 26, 384, 50);
		contact_panel.add(addressTextArea);
		addressTextArea.setWrapStyleWord(true);
		addressTextArea.setLineWrap(true);
		
		JLabel label_4 = new JLabel("*");
		label_4.setBounds(529, 26, 15, 14);
		contact_panel.add(label_4);
		label_4.setForeground(Color.RED);
		
		JLabel lblOffice = new JLabel("Phone ");
		lblOffice.setBounds(20, 84, 93, 19);
		contact_panel.add(lblOffice);
		lblOffice.setHorizontalAlignment(SwingConstants.LEFT);
		lblOffice.setFont(new Font("Tahoma", Font.PLAIN, 13));
		
		phone1Field = new JTextField();
		phone1Field.setBounds(123, 84, 167, 20);
		contact_panel.add(phone1Field);
		phone1Field.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
			}
		});
		phone1Field.setColumns(10);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setBounds(20, 112, 93, 19);
		contact_panel.add(lblEmail);
		lblEmail.setHorizontalAlignment(SwingConstants.LEFT);
		lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 13));
		
		emailField = new JTextField();
		emailField.setBounds(123, 114, 264, 20);
		contact_panel.add(emailField);
		emailField.setColumns(10);
		btnPrint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				PatientBiodataPrintFrame view = new PatientBiodataPrintFrame(hospNoField.getText());
				view.setVisible(true);
			
			}
		});
		
		for(int i= 0; i<statelist.size(); i++){
			stateBox.addItem(statelist.get(i).getName());
		}
		
		if (source.equalsIgnoreCase("edit")) {
			fillPatientForm(biodata);
		}
		
					
	}
		
					
		
	public void validatePageOne() {
		try {
			String dob = ((JTextField)dobChooser.getDateEditor().getUiComponent()).getText();
			 				
			if(dob.length()<5){errorlist.add("Invalid date of birth ");}
			if(surnameField.getText().length() < 1){errorlist.add("Invalid surname");	}
			if(firstnameField.getText().length() < 1){		errorlist.add("Invalid firstname");		}
			if(!radioMale.isSelected() && !radioFemale.isSelected()){errorlist.add("Gender is not selected");} 
			if(nationalityBox.getSelectedItem().toString().equalsIgnoreCase("--Select")){	errorlist.add("Invalid nationality");} 
			if(stateBox.getSelectedIndex()<0){	errorlist.add("Invalid state selection");	}
		} catch (Exception e) {
			e.printStackTrace();
			errorlist.add("inavlid date format");
		}
	}
	
		//client method to validate reg form 
		public void validatePageTwo(){
			try{				
				if(addressTextArea.getText().length() < 1){	errorlist.add("Invalid address");	}								
				if(nokSurnameField.getText().length() < 1){ errorlist.add("Invalid next of kin name");	}				
				if(nokRelationship.getText().length() < 1){  errorlist.add("Invalid next of kin relationship"); }
				if(nokAddressArea.getText().length() < 1){	errorlist.add("Invalid next of kin address");	}
				
			}catch(Exception ex){
				ex.printStackTrace();
				
			}
		}
		
		
		// client method to package data for registration, calls server method to save a new patient record
		private PatientBiodata submitPatientBiodata(){ 					
			String opendate = "1901-01-01";
			String dob = ((JTextField)dobChooser.getDateEditor().getUiComponent()).getText();
			
			String gender = "";
			if(radioMale.isSelected()){
				gender = "M";
			}else if(radioFemale.isSelected()){
				gender = "F";
			}else{
				showMessage("Please select a gender.");
			}
						
			//String regOffr = regOfficerField.getText();
			double charge = Double.parseDouble(amountField.getText());
			
			//now set the attributes of the object
			PatientBiodata pbd = new PatientBiodata();
			pbd.setHospital_no(hospNoField.getText());
			
			pbd.setSurname(surnameField.getText());
			pbd.setFirstname(firstnameField.getText());
			pbd.setAddress1(addressTextArea.getText());
		
			pbd.setRegDate(opendate);
			pbd.setDob(dob);
			
			pbd.setGender(gender);
			
			pbd.setBloodGroup(bloodGroupBox.getSelectedItem().toString());
			pbd.setGenotype(genotypeBox.getSelectedItem().toString());
			
			pbd.setNationality(nationalityBox.getSelectedItem().toString());
			pbd.setState(stateBox.getSelectedItem().toString());
	
			pbd.setMaritalStatus(maritalBox.getSelectedItem().toString());
			pbd.setReligion(religionBox.getSelectedItem().toString());
			pbd.setOccupation(occupationField.getText());
			
			pbd.setPhoneMobile(phone1Field.getText());			
			
			pbd.setEmail(emailField.getText());
			
			pbd.setNok_surname(nokSurnameField.getText());			
			pbd.setNok_address(nokAddressArea.getText());
			pbd.setNok_relationship(nokRelationship.getText());
			pbd.setNok_mobile(nokMobile.getText());			
			
			pbd.setRegistrationOfficer(regOfficerField.getText());	
			
			
			if(chckbxFamilyCard.isSelected()){
				pbd.setFamilyNo(familyNoField.getText());
			}else{
				pbd.setFamilyNo("");
			}
			
			pbd.setCharge(charge);
			
			return pbd;
		}

		public static void showMessage(String message){
			final JFrame dialog = new JFrame();
			dialog.setAlwaysOnTop(true);
			JOptionPane.showMessageDialog(dialog, message);
		}
		
		public static void updateFamilyCardInfo(FamilyCard familyCard) {
			familyNameField.setText(familyCard.getFamilyName());
			familyNoField.setText(familyCard.getFamilyNo());
			
		}
		
		public void fillPatientForm(PatientBiodata biodata) {
						
			try {
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				dobChooser.setDate(sdf.parse(biodata.getDob()));
				
				if (biodata.getGender().equalsIgnoreCase("M")) {
					radioMale.setSelected(true);
				}else if (biodata.getGender().equalsIgnoreCase("F")) {
					radioFemale.setSelected(true);
				}
				
				amountField.setText(biodata.getCharge()+"");							
				regOfficerField.setText(biodata.getRegistrationOfficer());
				
				hospNoField.setText(biodata.getHospital_no());
				surnameField.setText(biodata.getSurname());
				firstnameField.setText(biodata.getFirstname());
				addressTextArea.setText(biodata.getAddress1());
				
				bloodGroupBox.setSelectedItem(biodata.getBloodGroup());
				genotypeBox.setSelectedItem(biodata.getGenotype());
				nationalityBox.setSelectedItem(biodata.getNationality());
				stateBox.setSelectedItem(biodata.getState());
				
				maritalBox.setSelectedItem(biodata.getMaritalStatus());
				religionBox.setSelectedItem(biodata.getReligion());
				occupationField.setText(biodata.getOccupation());
				phone1Field.setText(biodata.getPhoneMobile());
				emailField.setText(biodata.getEmail());
				
				nokSurnameField.setText(biodata.getNok_surname());
				nokAddressArea.setText(biodata.getNok_address());
				nokRelationship.setText(biodata.getNok_relationship());
				nokMobile.setText(biodata.getNok_mobile());
				
				chckbxFamilyCard.setEnabled(false);	
				
				btnUpdate.setEnabled(true);
				btnSave.setEnabled(false);
								
			
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
}
