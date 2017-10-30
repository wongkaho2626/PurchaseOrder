package domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.mysql.jdbc.StringUtils;

public class DateValidator {

	public boolean checkDD(String d){
		String[] dd = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21"
				, "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
		String[] mm = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
		for(String i : dd){
			for(String j : mm){
				if(d.equals(j + "/" + i)){
					return true;
				}
			}
		}
		return false;
	}

	public boolean checkMM(String m){
		String[] mm = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
		for(String i : mm){
			if(m.equals(i)){
				return true;
			}
		}
		return false;
	}

	public Exception checkCompleteUnitDateValid(String input){
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");
		Exception exception = null;
		if(!StringUtils.isNullOrEmpty(input)){
			try {
				sdf.setLenient(false);
				java.util.Date date = sdf.parse(input);
				java.sql.Date sqlDate = new java.sql.Date(date.getTime());
				String ChangedDate = input.substring(6, 8) + "/" + input.substring(0, 2) + "/" + input.substring(3, 5);
			} catch (ParseException e1) {
				exception = e1;
			} catch (Exception e1){
				exception = e1;
			}
		}

		return exception;
	}
	
	public String ETDcalculation(String ShipToArriveDate){
		String ETD = ShipToArriveDate;
		String month = null;
		String day = null;
		int mm;
		int dd;
		int yy;
		try{
			mm = Integer.parseInt(ShipToArriveDate.substring(0, 2)) - 1;
			dd = Integer.parseInt(ShipToArriveDate.substring(3, 5));
			yy = Integer.parseInt(ShipToArriveDate.substring(6, 8)) + 2000;
						
			Calendar c1 = Calendar.getInstance();
			c1.set(yy, mm, dd);
			c1.add(Calendar.DAY_OF_MONTH, -42);
			Date sDate = c1.getTime();
			System.out.println(sDate);
			int year;
			year = c1.get(Calendar.YEAR) - 2000;
			if(sDate.getDate() < 10){
				day = "0" + Integer.toString(sDate.getDate());
			}else{
				day = Integer.toString(sDate.getDate());
			}
			if(sDate.getMonth()+1 < 10){
				month = "0" + Integer.toString(sDate.getMonth()+1);
			}else{
				month = Integer.toString(sDate.getMonth()+1);
			}
			ETD = month + "/" + day + "/" + year;
		}catch(Exception e1){
			return null;
		}
		
		return ETD;
	}
}
