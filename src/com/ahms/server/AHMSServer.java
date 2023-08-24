package com.ahms.server;

import java.awt.EventQueue;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import com.ahms.api.ClinicManagementInterface;

import com.ahms.api.HospitalManagementInterface;
import com.ahms.api.LabManagementInterface;

import com.ahms.api.PharmacyManagementInterface;
import com.ahms.api.ReportsInterface;


public class AHMSServer {	
	
	public static HospitalManagementImplementation hmgt_ipml = new HospitalManagementImplementation(); 
	public static ClinicManagementImplementation clinic_mgt_ipml = new ClinicManagementImplementation(); 
	public static LabManagementImplementation labmgt_ipml = new LabManagementImplementation(); 
	public static PharmacyManagementImplementation pharm_mgt_impl = new PharmacyManagementImplementation();
	public static ReportsImplementation reports_impl = new ReportsImplementation();	

	private static Registry registry = null;
	private static Registry registry2 = null;
	private static Registry registry3 = null;
	private static Registry registry4 = null;
	private static Registry registry5 = null;		

	public AHMSServer() {}
	
	public static void main(String[] args) throws RemoteException{
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				startServer();				
			}
			
		});
		
	}
	
	
	public static void startServer(){
		 
		try {			
			
			//System.setProperty("java.rmi.server.hostname", "localhost");
			System.setProperty("java.rmi.server.hostname", "192.168.1.113");
			
			registry = LocateRegistry.createRegistry(2234);					
			ReportsInterface rp_interface = (ReportsInterface) UnicastRemoteObject.exportObject(reports_impl, 0);
			registry.rebind("rp_interface", rp_interface);
				
			registry2 = LocateRegistry.createRegistry(2235);					
			HospitalManagementInterface hm_interface = (HospitalManagementInterface) UnicastRemoteObject.exportObject(hmgt_ipml, 0);
			registry2.rebind("hm_interface", hm_interface);
			
			registry3 = LocateRegistry.createRegistry(2236);					
			ClinicManagementInterface cm_interface = (ClinicManagementInterface) UnicastRemoteObject.exportObject(clinic_mgt_ipml, 0);		
			registry3.rebind("cm_interface", cm_interface);
			
			registry4 = LocateRegistry.createRegistry(2237);					
			LabManagementInterface lm_interface = (LabManagementInterface) UnicastRemoteObject.exportObject(labmgt_ipml, 0);		
			registry4.rebind("lm_interface", lm_interface);
			
			registry5 = LocateRegistry.createRegistry(2238);					
			PharmacyManagementInterface phm_interface = (PharmacyManagementInterface) UnicastRemoteObject.exportObject(pharm_mgt_impl, 0);		
			registry5.rebind("phm_interface", phm_interface);
			
			
			System.out.println("AHMS Server is running... ");
		
		} catch (Exception e) {
			e.printStackTrace();
		}	
		
	} 
	
	public static void stopServer(){
		try {			
			registry.unbind("rp_interface");				
			registry2.unbind("hm_interface");			
			registry3.unbind("cm_interface");			
			registry4.unbind("lm_interface");						
			registry5.unbind("phm_interface");
			
					
			System.out.println("AHMS Server has stopped... ");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
		

}


