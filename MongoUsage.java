import com.mongodb.MongoClient;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import javafx.scene.control.ComboBox;

import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.mongodb.client.model.Filters.*;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

/**
 * This class is used to interact with the MongoDB collection, and will handle anything that
 * has to do with the collection. It is an Employee class collection within the database, and it
 * works hand in hand with the GUI.java main file.
 * @author Steven Orsini
 * @version 11/14/2021
 */
public class MongoUsage {
	
	static CodecRegistry pojoCodecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(), fromProviders(PojoCodecProvider.builder().automatic(true).build()));
	static MongoClientSettings settings = MongoClientSettings.builder().codecRegistry(pojoCodecRegistry).build();
	static com.mongodb.client.MongoClient client = MongoClients.create(settings);
	static MongoCollection<Employee> col = setUpDB();
	static MongoCollection<Admin> adminCol = setUpAdmin();

	/**
	 * This function is used to set the warning level for the logger, and set up the database. It
	 * checks to make sure the static variable MongoClient is not equal to null, ensuring a proper
	 * connection, and then it connects to the proper database and collection, IDS, and employees
	 * respectively. It then logs in the console that the database has been properly configured. 
	 */
	public static MongoCollection<Employee> setUpDB() {
        Logger.getLogger("org.mongodb.driver").setLevel(Level.WARNING);
		
        // Connect to the server
		if(!client.equals(null)) {
			LogUtil.writeToLog("Successfully connected to database.");
		}
		else {
			LogUtil.writeToLog("Failed to connect to database.");
		}
		
		MongoDatabase db = client.getDatabase("ScanTech");
		col = db.getCollection("Employees", Employee.class);
		
		
		LogUtil.writeToLog("Successfully retrieved Employee collection.");
		
		return col;
	}
	
	/**
	 * This function is used to set the warning level for the logger, and set up the database. It
	 * checks to make sure the static variable MongoClient is not equal to null, ensuring a proper
	 * connection, and then it connects to the proper database and collection, IDS, and employees
	 * respectively. It then logs in the console that the database has been properly configured. 
	 */
	public static MongoCollection<Admin> setUpAdmin() {
        Logger.getLogger("org.mongodb.driver").setLevel(Level.WARNING);
		
        // Connect to the server
		if(!client.equals(null)) {
			LogUtil.writeToLog("Successfully connected to database.");
		}
		else {
			LogUtil.writeToLog("Failed to connect to database.");
		}
		
		MongoDatabase db = client.getDatabase("ScanTech");
		adminCol = db.getCollection("Admin", Admin.class);
		
		
		LogUtil.writeToLog("Successfully retrieved Admin collecion.");
		
		return adminCol;
	}
	
	/**
	 * This method will insert the employees first and last name into the collection as a document,
	 * without any populated statistics.
	 * @param emp - employee object
	 */
	public static void addEmployeeToDB(Employee emp) {
		col.insertOne(emp);
	}
	
	/**
	 * This method will remove the employee document from the collection.
	 * @param emp - employee object
	 */
	public static void removeEmployee(Employee emp) {
		col.deleteOne(eq("fullName", emp.toString()));
	}
	
	/**
	 * This method will iterate through each document in the Employees collection and add the 
	 * full name (first and last name concatenated) to the array list.
	 * @return a list of employee names
	 */
	public static ArrayList<String> getListOfEmployees(){
		ArrayList<String> employees = new ArrayList<String>();
		FindIterable<Employee> iterableDoc = col.find();
		Iterator<Employee> myIt = iterableDoc.iterator();
		Employee currentDoc;
		
		while(myIt.hasNext()) {
			currentDoc = myIt.next();
			String fullName = currentDoc.toString();
			employees.add(fullName);
		}
		
		return employees;
	}
	
	/**
	 * This method iterates over the mongo collection and finds the employee within the DB
	 * @param employee - combobox of all employees
	 * @return the employee found as a mongo document
	 */
	public static Employee findEmployee(ComboBox<String> employee) {
		String choice = employee.getValue();
		FindIterable<Employee> iterableDoc = col.find();
		Iterator<Employee> myIt = iterableDoc.iterator();
		Employee currentDoc = null;
		boolean scanning = true;
		
		while(myIt.hasNext() && scanning) {
			currentDoc = myIt.next();
			String currName = currentDoc.toString();
			if(currName.equals(choice)) {
				scanning = false;
			}
		}
		return currentDoc;
	}
	
	/**
	 * This method iterates over the mongo collection and finds the employee within the DB
	 * @param employee - combobox of all employees
	 * @return the employee found as a mongo document
	 */
	public static Employee findEmployee(String employee) {
		FindIterable<Employee> iterableDoc = col.find();
		Iterator<Employee> myIt = iterableDoc.iterator();
		Employee currentDoc = null;
		boolean scanning = true;
		
		while(myIt.hasNext() && scanning) {
			currentDoc = myIt.next();
			String currName = currentDoc.toString();
			if(currName.equals(employee)) {
				scanning = false;
			}
		}
		return currentDoc;
	}
	
	/**
	 * This method iterates through the hashmap of new values to add/subtract to the employee fields
	 * of this employee, and will use accessor/mutator methods to alter those values accordingly within
	 * the database.
	 * @param employee - the employee chosen
	 * @param toChange - a hashmap of key (the measure) and value (amount) to set in the employee obj
	 */
	public static void addToEmployeeStats(ComboBox<String> employee, HashMap<String, Integer> toChange) {
		// Grabbing the employee from the collection
		Employee currEmp = findEmployee(employee);
		
		// Adding the measures to the db
		toChange.forEach((key, val) -> {
			addToPerformanceMeasure(currEmp, key, val);
		});
		
		// Readding the new employee with its new stats
		removeEmployee(currEmp);
		addEmployeeToDB(currEmp);
	}
	
	/**
	 * This method will add the new amount to the current amount for the employee within the 
	 * database.
	 * @param currEmp - the current employee obj
	 * @param measure - the measure we are adding to
	 * @param amtToAdd - the amount we are adding
	 * @return the new amount for the measure
	 */
	public static void addToPerformanceMeasure(Employee currEmp, String measure, int amtToAdd) {
		switch(measure) {
			case "holes in plank":
				currEmp.setPlankHoles(currEmp.getPlankHoles() + amtToAdd);
				break;
			case "holes in solid":
				currEmp.setSolidHoles(currEmp.getSolidHoles() + amtToAdd);
				break;
			case "saw cuts":
				currEmp.setSawHoles(currEmp.getSawHoles() + amtToAdd);
				break;
			case "call outs":
				currEmp.setCallOuts(currEmp.getCallOuts() + amtToAdd);
				break;
			default:
				LogUtil.writeToLog("Unexcepted behavior in MongoUsage.addToPerformanceMeasure");
				break;
		}
	}
	
	/**
	 * This method will insert the admin object into the collection.
	 * @param a - admin object
	 */
	public static void addAdminToDB(Admin a) {
		adminCol.insertOne(a);
	}
	
	/**
	 * This method will remove the admin document from the collection.
	 * @param a - admin object
	 */
	public static void removeAdmin(Admin a) {
		adminCol.deleteOne(eq("username", a.getUsername()));
	}
	
	/**
	 * This method will iterate over the admin collection looking for the username within the 
	 * collection that the user had entered.
	 * @param username - the username entered from the GUI
	 * @return true if the username is found, false otherwise
	 */
	public static boolean verifyUsername(String username) {
		FindIterable<Admin> iterableDoc = adminCol.find();
		Iterator<Admin> myIt = iterableDoc.iterator();
		Admin currentDoc = null;
		boolean isFound = false;
		
		while(myIt.hasNext() && !isFound) {
			currentDoc = myIt.next();
			String currName = currentDoc.getUsername();
			if(currName.equals(username)) {
				isFound = true;
				break;
			}
		}
		
		return isFound;
	}
	
	/**
	 * This method will iterate over the admin collection looking for the password within the 
	 * collection that the user had entered.
	 * @param password - the username entered from the GUI
	 * @return true if the password is found, false otherwise
	 */
	public static boolean verifyPassword(String password) {
		FindIterable<Admin> iterableDoc = adminCol.find();
		Iterator<Admin> myIt = iterableDoc.iterator();
		Admin currentDoc = null;
		boolean isFound = false;
		
		while(myIt.hasNext() && !isFound) {
			currentDoc = myIt.next();
			String currPass = currentDoc.getPassword();
			if(currPass.equals(password)) {
				isFound = true;
				break;
			}
		}
		
		return isFound;
	}
	
	/**
	 * This method will iterate through each document in the Admin collection and add the 
	 * username to the array list.
	 * @return a list of admin usernames
	 */
	public static ArrayList<String> getListOfAdminUsernames(){
		ArrayList<String> employees = new ArrayList<String>();
		FindIterable<Admin> iterableDoc = adminCol.find();
		Iterator<Admin> myIt = iterableDoc.iterator();
		Admin currentDoc;
		
		while(myIt.hasNext()) {
			currentDoc = myIt.next();
			String username = currentDoc.getUsername();
			employees.add(username);
		}
		
		return employees;
	}
	
	/**
	 * This method iterates over the mongo collection and finds the admin within the DB
	 * @param admin - combobox of all admins
	 * @return the admin found as a mongo document
	 */
	public static Admin findAdmin(String admin) {
		FindIterable<Admin> iterableDoc = adminCol.find();
		Iterator<Admin> myIt = iterableDoc.iterator();
		Admin currentDoc = null;
		boolean scanning = true;
		
		while(myIt.hasNext() && scanning) {
			currentDoc = myIt.next();
			String currName = currentDoc.getUsername();
			if(currName.equals(admin)) {
				scanning = false;
			}
		}
		return currentDoc;
	}
}
