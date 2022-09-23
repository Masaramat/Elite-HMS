package com.ahms.hospitalmanagement;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.ahms.api.ClinicManagementInterface;
import com.ahms.api.HospitalManagementInterface;
import com.ahms.clientinterface.InterfaceGenerator;
import com.ahms.clinicmgt.entities.PatientVisit;
import com.ahms.hmgt.entities.Deposit;
import com.ahms.hmgt.entities.IPInvoice;
import com.ahms.hmgt.entities.OPInvoice;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.swing.JRViewer;

public class PaymentReceiptShowFrame extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PaymentReceiptShowFrame frame = new PaymentReceiptShowFrame("");
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
	public PaymentReceiptShowFrame(String invoice_no) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 654, 398);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		showReport(invoice_no);
	}
	
	public void showReport(String invoice_no){
		
		ClinicManagementInterface cm_interface = InterfaceGenerator.getClinicManagementInterface();
		PatientVisit pVisit = new PatientVisit();
		String reportSource = "";
		try {
			pVisit = cm_interface.getPatientVisit(invoice_no);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (pVisit.getClinic().equalsIgnoreCase("ipd")) {
			reportSource = "/com/ahms/hospitalmanagement/PaymentReciept.jrxml";
		}else if (pVisit.getClinic().equalsIgnoreCase("opd")) {
			reportSource = "/com/ahms/hospitalmanagement/OutpatientPaymentReceipt.jrxml";
		}
		
		String subSourceName = "/com/ahms/hospitalmanagement/ReceiptSubreport.jrxml";	
		PatienBiodataBeanFactory pbbf = new PatienBiodataBeanFactory();
		
		NumberFormat nf = NumberFormat.getCurrencyInstance();	 
		DecimalFormatSymbols dfs = ((DecimalFormat) nf).getDecimalFormatSymbols();
		dfs.setCurrencySymbol("");
		((DecimalFormat) nf).setDecimalFormatSymbols(dfs);
		
		HospitalManagementInterface hm_interface = InterfaceGenerator.getHospitalManagementInterface();
		
		
				
		try{
			ArrayList<OPInvoice> opp = pbbf.getOPDInvoice(invoice_no);
			ArrayList<IPInvoice> pb = pbbf.getIPDInvoice(invoice_no);	
			ArrayList<Deposit> deposit_list = hm_interface.getAllDeposits(invoice_no);	
			
			JRDataSource jrdatasource = null;
			if (pVisit.getClinic().equalsIgnoreCase("ipd")) {
				jrdatasource = new JRBeanCollectionDataSource(pb);	
			}else if (pVisit.getClinic().equalsIgnoreCase("opd")) {
				jrdatasource = new JRBeanCollectionDataSource(opp);	
			}			
			
			JasperReport report = JasperCompileManager.compileReport(getClass().getResourceAsStream(reportSource));
			JasperReport jasperSubReport = JasperCompileManager.compileReport(getClass().getResourceAsStream(subSourceName));
			
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("paymentList", deposit_list);
			parameters.put("paymentSubreport", jasperSubReport);	
			
			JasperPrint filledReport = JasperFillManager.fillReport(report, parameters, jrdatasource);
			
			this.getContentPane().add(new JRViewer(filledReport), BorderLayout.CENTER);
			this.pack();
			
		}catch(Exception ex){ ex.printStackTrace();	}
		
		
	}
	
	

}
