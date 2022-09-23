package com.ahms.reports;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.ahms.api.ClinicManagementInterface;
import com.ahms.clientinterface.InterfaceGenerator;
import com.ahms.clinicmgt.entities.PrescriptionOrderItem;
import com.ahms.labmgt.entities.ResultLine;
import com.ahms.laboratorymanagement.LaboratoryPrintFactory;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.swing.JRViewer;

public class PrescriptionShowFrame extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PrescriptionShowFrame frame = new PrescriptionShowFrame("");
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
	public PrescriptionShowFrame(String invoice_no) {
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		showReport(invoice_no);
		
		
	}
	
	public void showReport(String invoice_no){
		ClinicManagementInterface clmi = InterfaceGenerator.getClinicManagementInterface();
		String sourceName = "/com/ahms/reports/PrescriptionOrder.jrxml";
		ArrayList<PrescriptionOrderItem> prescription_list = null;
		
		try{
			JRDataSource jrdatasource = null;
				prescription_list = clmi.getPrescriptions(invoice_no);
				jrdatasource = new JRBeanCollectionDataSource(prescription_list);				
			
				JasperReport report = JasperCompileManager.compileReport(getClass().getResourceAsStream(sourceName));			
				JasperPrint filledReport = JasperFillManager.fillReport(report, null, jrdatasource);			
				this.getContentPane().add(new JRViewer(filledReport), BorderLayout.CENTER);
				this.pack();
			
		}catch(Exception ex){ ex.printStackTrace();	}
		
		
	}

}
