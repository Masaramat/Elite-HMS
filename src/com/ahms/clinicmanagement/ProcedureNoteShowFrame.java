package com.ahms.clinicmanagement;

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
import com.ahms.clinicmgt.entities.OperationNote;
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

public class ProcedureNoteShowFrame extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProcedureNoteShowFrame frame = new ProcedureNoteShowFrame("", "", new ArrayList<String>());
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
	public ProcedureNoteShowFrame(String invoice_no, String procedure_code, ArrayList<String> params) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		showReport(invoice_no, procedure_code, params);
	}
	
	public void showReport(String invoice_no, String procedure_code, ArrayList<String> params){
		ClinicManagementInterface clmi = InterfaceGenerator.getClinicManagementInterface();
		String sourceName = "/com/ahms/clinicmanagement/OperationNotePrintout.jrxml";
		ArrayList<OperationNote> opnote_list = new ArrayList<>();		
		OperationNote note = null;
		
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		
		try{
			JRDataSource jrdatasource = null;
				note = clmi.getOperationNote(invoice_no, procedure_code);
				opnote_list.add(note);
				jrdatasource = new JRBeanCollectionDataSource(opnote_list);	
				
				parameters.put("patientName", params.get(0));
				parameters.put("gender", params.get(1));
				parameters.put("hospitalNo", params.get(2));
			
				JasperReport report = JasperCompileManager.compileReport(getClass().getResourceAsStream(sourceName));			
				JasperPrint filledReport = JasperFillManager.fillReport(report, parameters, jrdatasource);			
				this.getContentPane().add(new JRViewer(filledReport), BorderLayout.CENTER);
				this.pack();
			
		}catch(Exception ex){ ex.printStackTrace();	}
		
		
	}

}
