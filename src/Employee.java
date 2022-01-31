import java.util.ArrayList;

/**
 * This is an Employee class that will be used to create the employee and then add and subtract
 * points from them. There is not setter method for amount of points, as it is set within the 
 * constructor to zero, so the only time they will change is with the alterAmountOfPoints method.
 * Therefore, the setter method is not needed.
 * @author Steven Orsini
 * @version October 24th, 2021
 */
public class Employee {

	private String fullName;
	private String firstName;
	private String lastName;
	private double points;
	private int solidHoles;
	private int plankHoles;
	private int sawHoles;
	private int callOuts;
	
	public Employee() {
		
	}
	
	/**
	 * This is the constructor method. It will set the employees first and last name, and
	 * as well set the points of the employee equal to zero to start.
	 * @param firstName - the employees first name 
	 * @param lastName - the employees last name
	 */
	public Employee(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.setFullName(firstName + " " + lastName);
		points = 0;
	}
	
	/**
	 * This is a accessor method that will return the amount of points the current Employee
	 * has.
	 * @return amount of points
	 */
	public double getPoints() {
		return points;
	}
	
	/**
	 * This is a accessor method that will return the first name of the current Employee.
	 * @return the first name of the employee
	 */
	public String getFirstName() {
		return firstName;
	}
	
	/**
	 * This is a mutator method that will change the first name of the Employee.
	 * @param firstName - employees first name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	/**
	 * This is a accessor method that will return the last name of the current Employee.
	 * @return employees last name
	 */
	public String getLastName() {
		return lastName;
	}
	
	/**
	 * This is a mutator method that will set the last name of the Employee.
	 * @param lastName - employees last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	/**
	 * @return the solidHoles
	 */
	public int getSolidHoles() {
		return solidHoles;
	}

	/**
	 * @param solidHoles the solidHoles to set
	 */
	public void setSolidHoles(int solidHoles) {
		this.solidHoles = solidHoles;
		this.alterAmountOfPoints();
	}

	/**
	 * @return the plankHoles
	 */
	public int getPlankHoles() {
		return plankHoles;
	}

	/**
	 * @param plankHoles the plankHoles to set
	 */
	public void setPlankHoles(int plankHoles) {
		this.plankHoles = plankHoles;
		this.alterAmountOfPoints();
	}

	/**
	 * @return the sawHoles
	 */
	public int getSawHoles() {
		return sawHoles;
	}

	/**
	 * @param sawHoles the sawHoles to set
	 */
	public void setSawHoles(int sawHoles) {
		this.sawHoles = sawHoles;
		this.alterAmountOfPoints();
	}

	/**
	 * @return the callOuts
	 */
	public int getCallOuts() {
		return callOuts;
	}

	/**
	 * @param callOuts the callOuts to set
	 */
	public void setCallOuts(int callOuts) {
		this.callOuts = callOuts;
		this.alterAmountOfPoints();
	}

	/**
	 * @return the fullName
	 */
	public String getFullName() {
		return fullName;
	}

	/**
	 * @param fullName the fullName to set
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	/**
	 * This is a mutator method that will change the value of points for the current Employee.
	 */
	public void alterAmountOfPoints() {
		this.points = this.getSolidHoles() + (this.getPlankHoles() * .5) + this.getSawHoles() + (this.getCallOuts() * -5);
	}
	
	/**
	 * This method will get all the stats and the full name for the employee and put it within an
	 * array list for parsing in the main class.
	 * @return array list of all stats
	 */
	public ArrayList<String> getAllStats(){
		ArrayList<String> stats = new ArrayList<String>();
		
		stats.add(this.toString());
		stats.add(String.valueOf(this.getSolidHoles()));
		stats.add(String.valueOf(this.getPlankHoles()));
		stats.add(String.valueOf(this.getSawHoles()));
		stats.add(String.valueOf(this.getCallOuts()));
		stats.add(String.valueOf(this.getPoints()));
		
		return stats;
	}
	
	@Override
	/**
	 * {@inheritDoc}
	 */
	public String toString() {
		return getFirstName() + " " + getLastName();
	}
}
