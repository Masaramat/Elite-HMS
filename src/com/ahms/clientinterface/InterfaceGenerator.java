package com.ahms.clientinterface;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import com.ahms.api.ClinicManagementInterface;
import com.ahms.api.HospitalManagementInterface;
import com.ahms.api.LabManagementInterface;
import com.ahms.api.PharmacyManagementInterface;
import com.ahms.api.ReportsInterface;

public class InterfaceGenerator {
	
	//private static String serverIP = "127.0.0.1";
	private static String serverIP = "192.168.1.113";

	public InterfaceGenerator() {}
	
	
	//method to connect to server and get  login interface 
	public static ReportsInterface getReportsInterface() {		
		try {
			Registry registry = LocateRegistry.getRegistry(serverIP, 2234);
			ReportsInterface rp_interface = (ReportsInterface) registry.lookup("rp_interface");
			return rp_interface;
		} catch (RemoteException | NotBoundException e) { e.printStackTrace();	}
			return null;		
	}
	
	
	//method to connect to server and get  hospital management interface 
		public static HospitalManagementInterface getHospitalManagementInterface() {		
			try {
				Registry registry = LocateRegistry.getRegistry(serverIP, 2235);
				HospitalManagementInterface hm_interface = (HospitalManagementInterface) registry.lookup("hm_interface");
				return hm_interface;
			} catch (RemoteException | NotBoundException e) { e.printStackTrace();	}
				return null;		
		}
	
	//method to connect to server and get clinic interface 
	public static ClinicManagementInterface getClinicManagementInterface() {		
		try {
			Registry registry = LocateRegistry.getRegistry(serverIP, 2236);
			ClinicManagementInterface cm_interface = (ClinicManagementInterface) registry.lookup("cm_interface");
			return cm_interface;
		} catch (RemoteException | NotBoundException e) { System.out.println("Unable to connect to AHMS Server");	}
			return null;		
	}
					
	//method to connect to server and get  lab mgt. interface 
	public static LabManagementInterface getLabManagementInterface() {		
		try {
			Registry registry = LocateRegistry.getRegistry(serverIP, 2237);
			LabManagementInterface lm_interface = (LabManagementInterface) registry.lookup("lm_interface");
			return lm_interface;
		} catch (RemoteException | NotBoundException e) { e.printStackTrace();	}
			return null;		
	}
	
	//method to connect to server and get  pharmacy interface 
	public static PharmacyManagementInterface getPharmManagementInterface() {		
		try {
			Registry registry = LocateRegistry.getRegistry(serverIP, 2238);
			PharmacyManagementInterface phm_interface = (PharmacyManagementInterface) registry.lookup("phm_interface");
			return phm_interface;
		} catch (RemoteException | NotBoundException e) { e.printStackTrace();	}
			return null;		
	}
		
		
		
	
		
}