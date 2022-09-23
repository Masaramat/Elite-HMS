package com.ahms.api;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;

import com.ahms.clinicmgt.entities.BookedProcedure;
import com.ahms.clinicmgt.entities.ClinicBill;
import com.ahms.clinicmgt.entities.ClinicProcedure;
import com.ahms.clinicmgt.entities.ConsultationNote;
import com.ahms.clinicmgt.entities.DischargeSummary;
import com.ahms.clinicmgt.entities.DiseaseCategory;
import com.ahms.clinicmgt.entities.DiseaseCondition;
import com.ahms.clinicmgt.entities.DiseaseSubcategory;
import com.ahms.clinicmgt.entities.DiseeaseIndex;
import com.ahms.clinicmgt.entities.ExaminationDetail;
import com.ahms.clinicmgt.entities.ExaminationGroup;
import com.ahms.clinicmgt.entities.ExaminationSection;
import com.ahms.clinicmgt.entities.InpatientAdmission;
import com.ahms.clinicmgt.entities.InpatientObservation;
import com.ahms.clinicmgt.entities.NursingNote;
import com.ahms.clinicmgt.entities.OperationNote;
import com.ahms.clinicmgt.entities.PatientVisit;
import com.ahms.clinicmgt.entities.PhysicalExamNote;
import com.ahms.clinicmgt.entities.PrescriptionOrderItem;
import com.ahms.clinicmgt.entities.ProgressNote;
import com.ahms.clinicmgt.entities.Symptom;
import com.ahms.clinicmgt.entities.SymptomCategory;
import com.ahms.clinicmgt.entities.SystemReviewNote;
import com.ahms.clinicmgt.entities.VitalSignEntry;
import com.ahms.hmgt.entities.Appointment;
import com.ahms.hmgt.entities.Bed;
import com.ahms.hmgt.entities.Doctor;
import com.ahms.hmgt.entities.ProcedureItem;
import com.ahms.hmgt.entities.Room;
import com.ahms.labmgt.entities.TestOrderItem;
import com.ahms.pharmarcymgt.entities.MedicineStockItem;


public interface ClinicManagementInterface extends Remote{
	
	// The following methods belong to the patient visit entry class
	public ArrayList<Appointment> searchWaitingAppointment(java.sql.Date date1, java.sql.Date date2) throws RemoteException; 
	
	public String getPatientName(String hosp_no) throws RemoteException;
	
	public ArrayList<Doctor> getDoctorList() throws RemoteException;
	
	public ArrayList<String> generateInvoice(PatientVisit pvisit) throws RemoteException; 
	
	public String leftPad(String str, int xy) throws RemoteException; 
	
	public ArrayList<PatientVisit> searchPatientVisit(String text, String searchby) throws RemoteException;
	
	public ArrayList<PatientVisit> searchPatientVisit(String clinic, java.util.Date date1, java.util.Date date2) throws RemoteException;
	
	public ArrayList<PatientVisit> searchPatientVisit(String status) throws RemoteException;
	
	public ArrayList<PatientVisit> searchPatientVisit(String text, String searchby, String status) throws RemoteException;
	
	public ArrayList<PatientVisit> getOutPatientVisit(String visit_status, java.util.Date date1, java.util.Date date2) throws RemoteException;
	
	
	//The following methods belong to the IPD Admission screen class
	public ArrayList<Room> getAllWards() throws RemoteException;
	
	public ArrayList<Bed> getFreeBeds(String room_id) throws RemoteException;
	
	public PatientVisit getPatientVisit(String invoice_no) throws RemoteException;
	
	public InpatientAdmission saveAdmission(InpatientAdmission ipadm) throws RemoteException;
	
	public ArrayList<InpatientAdmission> searchInpatientAdmission(String status, Date date1, Date date2) throws RemoteException;
	
	public ArrayList<InpatientAdmission> searchInpatientAdmission(String status) throws RemoteException;
	
	public ArrayList<Integer> getBedCount() throws RemoteException;
	
	//The following methods belong to OPD consultation class
	public String getHistoryOfIllness(String invoice_no) throws RemoteException;
	
	public ArrayList<String> getComplaints(String invoice_no) throws RemoteException;
	
	public ArrayList<DiseeaseIndex> getAllDiagnosis(String case_no) throws RemoteException;
	
	public ArrayList<PrescriptionOrderItem> getPrescriptions(String invoice_no) throws RemoteException;
	
	public ArrayList<VitalSignEntry> getVitalSignEntries(String invoice_no) throws RemoteException;
	
	public ArrayList<SystemReviewNote> getSystemReviewNotes(String invoice_no) throws RemoteException;
	
	public ArrayList<PhysicalExamNote> getPhysicalExamNote(String invoice_no) throws RemoteException;
	
	public ArrayList<ClinicBill> getClinicBills(String invoice) throws RemoteException;
	
	public ArrayList<BookedProcedure> getBookedProcedures(String invoice_no) throws RemoteException;
	
	public void saveClinicCharge(String invoice, String service, double amount) throws RemoteException;
	
	public ArrayList<PatientVisit> getPatientVisitArray(String invoiceNo) throws RemoteException;
	
	
	//vital signs entry
	public void saveVSEntry(VitalSignEntry vs) throws RemoteException;
	
	public void updateVSEntry(VitalSignEntry vs) throws RemoteException;
	
	//chief complaints
	public ArrayList<Symptom> getAllSymptoms() throws RemoteException;
	
	public void saveComplaints(String invoice_no, String complaints) throws RemoteException;

	
	//review of systems
	public ArrayList<SymptomCategory> getSymptomCategories() throws RemoteException;
	
	public ArrayList<Symptom> getSymptomsByCategory(String categoryCode) throws RemoteException;
	
	public void saveReviewNotes(ArrayList<SystemReviewNote> list) throws RemoteException;
	
	//physical examination
	public ArrayList<ExaminationSection> getAllSections() throws RemoteException;
	
	public ArrayList<ExaminationGroup> getExamGroups(String sectionCode) throws RemoteException;
	
	public ArrayList<ExaminationDetail> getExamDetails(String group_code) throws RemoteException;
	
	public void savePhysicalExamNotes(String inovice_no, ArrayList<PhysicalExamNote> list) throws RemoteException;
	
	//diagnosis
	public ArrayList<DiseaseCategory> getAllDiseaseCategories() throws RemoteException;
	
	public ArrayList<DiseaseSubcategory> getDiseaseSubcategories(String text) throws RemoteException;
	
	public ArrayList<DiseaseCondition> getDiseaseCondtions(String text) throws RemoteException;
	
	public ArrayList<DiseaseCondition> searchDiseaseCondtions(String text) throws RemoteException;
	
	public void saveDiagnosis(DiseeaseIndex dindex) throws RemoteException;
	
	//procedure booking
	public ArrayList<ClinicProcedure> getAllProcedures() throws RemoteException;
	
	public void saveBookedProcedure(BookedProcedure bp) throws RemoteException;
	
	public void updateClinicProcedure(String procedure_id, String procedure_name) throws RemoteException;
	
	//prescription order entry
	public ArrayList<MedicineStockItem> getAvailableDrugs() throws RemoteException;
	
	public void savePrescriptionEntry(PrescriptionOrderItem item) throws RemoteException;
	
	//IPD Consultation
	public InpatientAdmission getAdmissionSlip(String invoice_no) throws RemoteException;
	
	public ArrayList<Date> getAdmissionDates(String invoice_no) throws RemoteException;
	
	public ArrayList<InpatientObservation> getInpatientObservations(String invoice_no, Date date) throws RemoteException;
	
	public void dischargePatient(String invoice_no, String bed_code, DischargeSummary ds) throws RemoteException;
	
	//inpatient observation
	public void saveInpatientObservation(InpatientObservation io) throws RemoteException;
	
	//progress note
	public void saveProgressNote(ProgressNote pn) throws RemoteException;
	
	public void lockProgressNote(ProgressNote note) throws RemoteException;
	
	public ArrayList<ProgressNote> getProgressNotes(String invoice_no) throws RemoteException;
	
	public void updateProgressNote(ProgressNote note) throws RemoteException;
	
	
	//admission bed history
	public int getOccupiedBeds() throws RemoteException;
	
	public int getFreeBeds() throws RemoteException;
	
	public int getPendingBeds() throws RemoteException;
	
	public int getAllBedsCount() throws RemoteException;
	
	public int getAdmissionsCount() throws RemoteException;
	
	public ArrayList<InpatientAdmission> searchInpatientAdmission(Date date1, Date date2) throws RemoteException;
	
	public ArrayList<InpatientAdmission> searchInpatientAdmission(String text, String Searchby) throws RemoteException;
	
	
	public void updateProcedureState(String invoice_no, String cpt_code) throws RemoteException;
	
	public void updateOperationNotes(String invoice_no, String cpt_code, String a, String b, String c, String d, String e, String f) throws RemoteException;
	
	//consultation notes
	public void updateConsultationNote(ConsultationNote cNote) throws RemoteException;
	
	public ConsultationNote getConsultationNote(String invoice_no)throws RemoteException;
	
	//nursing notes
	public void saveNursingNote(String invoice_no, String text, String nurse) throws RemoteException;
	
	public void updateNursingNote(NursingNote note) throws RemoteException;
	
	public void updateNursingNoteStatus(NursingNote note) throws RemoteException;
	
	public ArrayList<NursingNote> getNursingNotes(String invoice_no) throws RemoteException;
	
	//operation notes
	public void saveOperationNotes(OperationNote opn) throws RemoteException;
	
	public OperationNote getOperationNote(String invoice_no, String cpt_code) throws RemoteException;
	
	//discharge outpatient
	public void dischargeOutpatient(String invoice_no, DischargeSummary ds) throws RemoteException;
	
	public DischargeSummary getDishargeSummary(String invoice_no) throws RemoteException;
	
	
	//method to search for active patient 
	public int searchActivePatient(String hosp_no) throws RemoteException;
	
	//procedure
	public String createProcedure(ProcedureItem pitem) throws RemoteException;
	
	public void updateProcedure(ProcedureItem pitem) throws RemoteException;
	
	public ArrayList<ProcedureItem> getAllProcedureItems() throws RemoteException;
	
	public void endConsultation(String invoice_no) throws RemoteException;
	
	public void updatePatientProcedure(BookedProcedure procedure) throws RemoteException;
	
	
	
	


}
