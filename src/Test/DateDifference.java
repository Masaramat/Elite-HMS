package Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.util.Date;


public class DateDifference {

	public static void main(String[] args) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date da = sdf.parse( "2021-11-01");
		Date db = sdf.parse( "2021-11-08");
		
		
		
		
		System.out.println(calculateAge(da));

	}
	
	
	
	public static String calculateAge(Date dob) {		
		
		try {
			LocalDate today = LocalDate.now();//Today's date			
			LocalDate localDate = LocalDate.parse( new SimpleDateFormat("yyyy-MM-dd").format(dob) );
			LocalDate birthday = LocalDate.of(localDate.getYear(), localDate.getMonth(), localDate.getDayOfMonth());  //Birth date			 
			Period p = Period.between(birthday, today);
			
			
			if (p.getYears()>0) {
				return p.getYears() + " Year(s)";
			}else if(p.getMonths()>0){
				return p.getMonths() + " month(s)";
			}else {
				return p.getDays() + " day(s)";
			}
			
		} catch (Exception e) {
			return "error";
		}
		
		
	}
	

	
	

}
