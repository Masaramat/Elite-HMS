package com.ahms.reports;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Date;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.ahms.api.ReportsInterface;
import com.ahms.clientinterface.InterfaceGenerator;
import com.ahms.clinicmgt.entities.BookedProcedure;
import com.ahms.clinicmgt.entities.InpatientAdmission;
import com.ahms.clinicmgt.entities.PatientVisit;
import com.ahms.hmgt.entities.FamilyCard;
import com.ahms.hmgt.entities.GeneralDeposit;
import com.ahms.hmgt.entities.PatientBiodata;
import com.ahms.labmgt.entities.TestOrderItem;
import com.ahms.pharmarcymgt.entities.MedicinePurchaseItem;
import com.ahms.pharmarcymgt.entities.MedicineWithdrawalItem;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.swing.JRViewer;


public class ShowReportsFrame extends JFrame {

	private JPanel contentPane;
	
	ReportsInterface rpi = InterfaceGenerator.getReportsInterface();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ShowReportsFrame frame = new ShowReportsFrame("", null, null, null, null);
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
	public ShowReportsFrame(String report_type, ArrayList<String> table_list, ArrayList<String> data_list, Date fromDate, Date toDate) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 550, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		showReport(report_type, table_list, data_list, fromDate, toDate);
		
	}
	
	
	public void showReport(String report_type, ArrayList<String> table_list, ArrayList<String> data_list, Date fromDate, Date toDate) {
		String sourceName = "";	
		
		
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		
		double total = 0;
		double total2 = 0;
		
		NumberFormat nf = NumberFormat.getCurrencyInstance();	 
		DecimalFormatSymbols dfs = ((DecimalFormat) nf).getDecimalFormatSymbols();
		dfs.setCurrencySymbol("");
		((DecimalFormat) nf).setDecimalFormatSymbols(dfs);
		
		String totalString = "";
		String totalString2 = "";
		String fromdate = fromDate.toString();
		String todate = toDate.toString();
		
		
		ArrayList<TestOrderItem> test_list = null;
		ArrayList<BookedProcedure> procedure_list = null;
		ArrayList<PatientVisit> visit_list = null;
		ArrayList<InpatientAdmission> admission_list = null;
		ArrayList<MedicinePurchaseItem> purchase_list = null; 
		ArrayList<MedicineWithdrawalItem> withdrawal_list = null;
		ArrayList<FamilyCard> family_list = null;
		ArrayList<GeneralDeposit> deposit_list = null;
		ArrayList<PatientBiodata> reg_list = null;
		
		try {
			JRDataSource jrdatasource = null;
			switch (report_type) {
			case "lab_test_report" :
				sourceName = "/com/ahms/reports/LabTestReport.jrxml";
				test_list = rpi.getLaboratoryReport(table_list, data_list, fromDate, toDate);
				jrdatasource = new JRBeanCollectionDataSource(test_list);
				
				parameters.clear();
				for(int i =0; i<test_list.size(); i++) {
					total = total + test_list.get(i).getPrice();
				}
				totalString = nf.format(total);
				
				parameters.put("totalPrice", totalString); 
				parameters.put("fromdate", fromdate); 
				parameters.put("todate", todate); 
				
				break;
			case "procedure_report" :
				sourceName = "/com/ahms/reports/ProceduresReport.jrxml";	
				procedure_list =rpi.getProcedureReport(table_list, data_list, fromDate, toDate);
				jrdatasource = new JRBeanCollectionDataSource(procedure_list);
				
				parameters.clear();
				for(int i =0; i<procedure_list.size(); i++) {
					total = total + procedure_list.get(i).getAnesthesiaCost(); 
					total2 = total2 + procedure_list.get(i).getProcedureCost();
				}
				totalString = nf.format(total);
				totalString2 = nf.format(total2);
				
				parameters.put("totalAnesthesia", totalString);
				parameters.put("totalProcedure", totalString2);
				parameters.put("fromdate", fromdate); 
				parameters.put("todate", todate); 
				
				break;
			case "inpatient_admission" :
				sourceName = "/com/ahms/reports/InpatientAdmissionReport.jrxml";	
				admission_list =rpi.getInpatientAdmissionReport(table_list, data_list, fromDate, toDate);
				jrdatasource = new JRBeanCollectionDataSource(admission_list);
				break;
			case "outpatient_visit" :
				sourceName = "/com/ahms/reports/OutpatientVisitReport.jrxml";	
				visit_list = rpi.getOutpatientVisitReport(table_list, data_list, fromDate, toDate);
				jrdatasource = new JRBeanCollectionDataSource(visit_list);
				break;
			case "pharmacy_purchase" :
				sourceName = "/com/ahms/reports/PharmacyPurchaseReport.jrxml";	
				purchase_list = rpi.getPharmacyPurchaseReport(fromDate, toDate);
				jrdatasource = new JRBeanCollectionDataSource(purchase_list);
				
				parameters.clear();
				for(int i =0; i<purchase_list.size(); i++) {
					total = total + purchase_list.get(i).getMedicinePrice();
				}
				totalString = nf.format(total);
				
				parameters.put("totalPrice", totalString); 
				parameters.put("fromdate", fromdate); 
				parameters.put("todate", todate); 
				
				break;
			case "pharmacy_withdrawal" :
				sourceName = "/com/ahms/reports/PharmacyWithdrawalReport.jrxml";	
				withdrawal_list = rpi.getPharmacyWithdrawalReport(fromDate, toDate);
				jrdatasource = new JRBeanCollectionDataSource(withdrawal_list);
				break;
			case "family_reg_report" :
				sourceName = "/com/ahms/reports/FamilyRegistrationReport.jrxml";	
				family_list =rpi.getFamilyRegistrationReport(table_list, data_list, fromDate, toDate);
				jrdatasource = new JRBeanCollectionDataSource(family_list);
				
				parameters.clear();
				for(int i =0; i<family_list.size(); i++) {
					total = total + family_list.get(i).getRegFee();
				}
				totalString = nf.format(total);
				
				parameters.put("totalPrice", totalString); 
				parameters.put("fromdate", fromdate); 
				parameters.put("todate", todate); 
				
				break;
				
			case "general_deposit" :
				sourceName = "/com/ahms/reports/GeneralDepositReport.jrxml";	
				deposit_list =rpi.getGeneralDepositReport(table_list, data_list, fromDate, toDate);
				jrdatasource = new JRBeanCollectionDataSource(deposit_list);
				
				parameters.clear();
				for(int i =0; i<deposit_list.size(); i++) {
					total = total + deposit_list.get(i).getAmount();
				}
				totalString = nf.format(total);
				
				parameters.put("totalDeposit", totalString); 
				parameters.put("fromdate", fromdate); 
				parameters.put("todate", todate); 
				break;
				
			case "patient_reg_report" :
				sourceName = "/com/ahms/reports/PatientRegistrationReport.jrxml";	
				reg_list =rpi.getPatientRegistrationReport(table_list, data_list, fromDate, toDate);
				jrdatasource = new JRBeanCollectionDataSource(reg_list); 
				
				parameters.clear();
				for(int i =0; i<reg_list.size(); i++) {
					total = total + reg_list.get(i).getCharge();
				}
				totalString = nf.format(total);
				
				parameters.put("totalPrice", totalString); 
				parameters.put("fromdate", fromdate); 
				parameters.put("todate", todate); 
				break;
				
			default:
				break;
			}		
			
			JasperReport report = JasperCompileManager.compileReport(getClass().getResourceAsStream(sourceName));			
			JasperPrint filledReport = JasperFillManager.fillReport(report, parameters, jrdatasource);			
			this.getContentPane().add(new JRViewer(filledReport), BorderLayout.CENTER);
			this.pack();			
			
			
		}catch(Exception ex){ ex.printStackTrace();	}
		
		
		
	}
	
	

}
