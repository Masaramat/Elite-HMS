package com.ahms.hospitalmanagement;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.ahms.hmgt.entities.OPInvoice;
import com.ahms.hmgt.entities.PatientBiodata;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.swing.JRViewer;

public class OPDInvoiceReportFrame extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OPDInvoiceReportFrame frame = new OPDInvoiceReportFrame("");
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
	public OPDInvoiceReportFrame(String invoice_no) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		showReport(invoice_no);
	}
	
	public void showReport(String invoice_no){
		String reportSource = "/com/ahms/hospitalmanagement/OPDInvoice.jrxml";
		PatienBiodataBeanFactory pbbf = new PatienBiodataBeanFactory();
		
		
		try{
			ArrayList<OPInvoice> pb = pbbf.getOPDInvoice(invoice_no);				
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			
			JRDataSource jrdatasource = new JRBeanCollectionDataSource(pb);	
			JasperReport report = JasperCompileManager.compileReport(getClass().getResourceAsStream(reportSource));
			
			JasperPrint filledReport = JasperFillManager.fillReport(report, parameters, jrdatasource);
			//System.out.println("filled report"); 
			this.getContentPane().add(new JRViewer(filledReport), BorderLayout.CENTER);
			this.pack();
			
		}catch(Exception ex){ ex.printStackTrace();	}
		
		
	}
	

}
