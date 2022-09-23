package Test;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.ahms.hmgt.entities.Bed;
import com.ahms.hmgt.entities.PatientBiodata;
import com.ahms.hospitalmanagement.PatienBiodataBeanFactory;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.swing.JRViewer;

public class GroupTestReport extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GroupTestReport frame = new GroupTestReport();
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
	public GroupTestReport() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		showReport();
	}
	
	
	
	public void showReport(){
		String reportSource = "GroupTestReport.jasper";
		TestFactory pbbf = new TestFactory();
		
		InputStream instr  = null;
				
		try{
			ArrayList<Bed> pb = pbbf.getAllBeds();
						
			instr = getClass().getResourceAsStream(reportSource);
						
			HashMap<String, Object> parameters = new HashMap<String, Object>();			
			
			JRDataSource jrdatasource = new JRBeanCollectionDataSource(pb);
			
			JasperPrint filledReport = JasperFillManager.fillReport(instr, parameters, jrdatasource);
			//System.out.println("filled report"); 
			this.getContentPane().add(new JRViewer(filledReport), BorderLayout.CENTER);
			this.pack();
			
		}catch(Exception ex){ ex.printStackTrace();	}
		
		
	}
	
	
	

}
