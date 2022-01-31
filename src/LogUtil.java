import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import org.apache.commons.lang3.exception.ExceptionUtils;

/**
 * This class will write to a log file with the current date. Each stack trace will be appended to 
 * the current dates log file .txt file, so all errors occured in that day will be logged for user
 * issues.
 * @author Steven Orsini
 * @version 12/19/2021
 */
public class LogUtil {
	
	/**
	 * This method will output the stack trace that occured during the time of the caught error
	 * @param stackTrace - the stack trace of the error 
	 * @throws FileNotFoundException
	 * @throws IOException 
	 */
	public static void writeToLog(String stackTrace) {
		// Setting file path values for saving the file to the system
		String fileName, fileExtension = ".txt";
        LocalDate currentDate = LocalDate.now();
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("kk:mm:ss");
		String militaryTime = formatter.format(date);
        
		// Making the file name with timestamp of date/time along with FIRST_LAST or ALL_EMPLOYEES
		fileName = currentDate + "_ScanTech_Error_Logs" + fileExtension;
		
		// Setting more file path values
		File currDir = new File(".");
		String path = currDir.getAbsolutePath();
		String fileLocation = path.substring(0, path.length() - 1) + fileName;
		
		// Writing the file to the file location
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(fileLocation, true));
			writer.write(militaryTime + "- " + stackTrace + "\n");
			writer.close();
		} 
		catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		catch(IOException e1) {
			e1.printStackTrace();
		}
	}
	
	/**
	 * This method will output the old and new measure changes to a log file.
	 * @param stackTrace - the stack trace of the error 
	 * @throws FileNotFoundException
	 * @throws IOException 
	 */
	public static void writeDBChangeToLog(String originalAmt, String measureChanged, Employee emp) {
		// Setting file path values for saving the file to the system
		String fileName, fileExtension = ".txt";
        LocalDate currentDate = LocalDate.now();
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("kk:mm:ss");
		String militaryTime = formatter.format(date);
        
		// Making the file name with timestamp of date/time along with FIRST_LAST or ALL_EMPLOYEES
		fileName = currentDate + "_ScanTech_Logs_DB_Measure_Changes_" + fileExtension;
		
		// Setting more file path values
		File currDir = new File(".");
		String path = currDir.getAbsolutePath();
		String fileLocation = path.substring(0, path.length() - 1) + fileName;
		
		String newMeasure = null;
		
		switch(measureChanged) {
			case "holes in plank":
				newMeasure = String.valueOf(emp.getPlankHoles());
				break;
			case "holes in solid":
				newMeasure = String.valueOf(emp.getSolidHoles());
				break;
			case "saw cuts":
				newMeasure = String.valueOf(emp.getSawHoles());
				break;
			case "call outs":
				newMeasure = String.valueOf(emp.getCallOuts());
				break;
			case "name":
				newMeasure = emp.toString();
				break;
		}
		
		// Writing the file to the file location
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(fileLocation, true));
			writer.write(militaryTime + "- The employee " + emp.toString() + " had their " + measureChanged + " changed from " + originalAmt + " to " + newMeasure + "\n");
			writer.close();
		} 
		catch(FileNotFoundException e) {
			String error = ExceptionUtils.getStackTrace(e);
			writeToLog(error);
		}
		catch(IOException e1) {
			String error = ExceptionUtils.getStackTrace(e1);
			writeToLog(error);
		}
	}
	
	/**
	 * This method will log the deletion and creation of an employee within the log file.
	 * @param stackTrace - the stack trace of the error 
	 * @throws FileNotFoundException
	 * @throws IOException 
	 */
	public static void writeDBChangeToLog(Employee emp, boolean wasDeletion) {
		// Setting file path values for saving the file to the system
		String fileName, fileExtension = ".txt";
        LocalDate currentDate = LocalDate.now();
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("kk:mm:ss");
		String militaryTime = formatter.format(date);
		
		// Making the file name with timestamp of date/time along with FIRST_LAST or ALL_EMPLOYEES
		fileName = currentDate + "_ScanTech_Logs_DB_Object_DeleteOrCreate_" + fileExtension;
		
		// Setting more file path values
		File currDir = new File(".");
		String path = currDir.getAbsolutePath();
		String fileLocation = path.substring(0, path.length() - 1) + fileName;
				
		// Writing the file to the file location
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(fileLocation, true));
			if(wasDeletion) {
				writer.write(militaryTime + "- The employee " + emp.toString() + " has been deleted from the database.\n");
			}
			else {
				writer.write(militaryTime + "- The employee " + emp.toString() + " has been added to the database.\n");
			}
			writer.close();
		} 
		catch(FileNotFoundException e) {
			String error = ExceptionUtils.getStackTrace(e);
			writeToLog(error);		
		}
		catch(IOException e1) {
			String error = ExceptionUtils.getStackTrace(e1);
			writeToLog(error);
		}
	}
	
	/**
	 * This method will output the old and new measure changes to a log file.
	 * @param stackTrace - the stack trace of the error 
	 * @throws FileNotFoundException
	 * @throws IOException 
	 */
	public static void writeDBChangeToLog(String originalAmt, String measureChanged, Admin admin) {
		// Setting file path values for saving the file to the system
		String fileName, fileExtension = ".txt";
        LocalDate currentDate = LocalDate.now();
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("kk:mm:ss");
		String militaryTime = formatter.format(date);
		
		// Making the file name with timestamp of date/time along with FIRST_LAST or ALL_EMPLOYEES
		fileName = currentDate + "_ScanTech_Logs_DB_Measure_Changes_" + fileExtension;
		
		// Setting more file path values
		File currDir = new File(".");
		String path = currDir.getAbsolutePath();
		String fileLocation = path.substring(0, path.length() - 1) + fileName;
		
		String newMeasure = null;
		
		if(measureChanged == "username") {
			newMeasure = admin.getUsername();
		}
		else {
			newMeasure = admin.getPassword();
		}
		
		// Writing the file to the file location
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(fileLocation, true));
			writer.write(militaryTime + "- The admin " + admin.getUsername() + " had their " + measureChanged + " changed from " + originalAmt + " to " + newMeasure + "\n");
			writer.close();
		} 
		catch(FileNotFoundException e) {
			String error = ExceptionUtils.getStackTrace(e);
			writeToLog(error);
		}
		catch(IOException e1) {
			String error = ExceptionUtils.getStackTrace(e1);
			writeToLog(error);
		}
	}
}
