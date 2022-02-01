import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.io.FileOutputStream;
import java.io.IOException;
import java.awt.Desktop;
import java.sql.Timestamp;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * This graphical user interface (GUI) will allow the owner of ScanTech to
 * login, and track their employees performance through a system. This system
 * will hold all current employees, and will allow for deletion of employees as
 * well to mitigate unneeded data allocation.
 * 
 * @author Steven Orsini
 * @version 9/4/2021
 */
public class GUI extends Application {
	private MenuBar menubar = new MenuBar();
	private Menu menuAlterEmployees = new Menu("Alter Employees");
	private Menu menuChangeEmpStats = new Menu("Change Employee Stats");
	private Menu menuSettings = new Menu("Settings");
	private MenuItem changeUsername = new MenuItem("Change Admin Username");
	private MenuItem changePassword = new MenuItem("Change Admin Password");
	private MenuItem deleteEmployee = new MenuItem("Delete Employees");
	private MenuItem changeEmpName = new MenuItem("Change Employee Name");
	private MenuItem changeHoleInSolid = new MenuItem("Change Holes in Solid");
	private MenuItem changeHoleInPlank = new MenuItem("Change Holes in Plank");
	private MenuItem changeSawCut = new MenuItem("Change Saw Cuts");
	private MenuItem changeCallOuts = new MenuItem("Change Call Outs");
		
	/**
	 * This method will be used to launch the application.
	 * @param args - any args for the main method
	 */
	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public void start(Stage primaryStage) throws Exception {
		BorderPane startPane = new BorderPane();
		loginPage(startPane);
		Scene scene = new Scene(startPane);
		setStage(primaryStage, scene);
	}

	/**
	 * This method will set the stage for the GUI. It will create the sizing, and
	 * set the titles.
	 * @param primaryStage - the stage for the GUI
	 * @param scene - the scene for the GUI
	 */
	public void setStage(Stage primaryStage, Scene scene) {
		primaryStage.setWidth(750);
		primaryStage.setHeight(725);
		primaryStage.setMinWidth(750);
		primaryStage.setMinHeight(725);
		primaryStage.setMaxWidth(750);
		primaryStage.setMaxHeight(725);
		primaryStage.setTitle("Scan Tech Corp. - Employee Review");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/**
	 * This method will create the login page, which is the starting page for the
	 * GUI.
	 * @param startPane - the pane for the GUI
	 */
	public void loginPage(BorderPane startPane) {		
		// Clearing and then setting color to the start pane
		startPane.getChildren().clear();
		startPane.setStyle("-fx-background-color: AQUAMARINE");

		// Adding everything to the menus
		menuAlterEmployees.getItems().addAll(menuChangeEmpStats, changeEmpName, deleteEmployee);
		menuChangeEmpStats.getItems().addAll(changeHoleInSolid, changeHoleInPlank, changeSawCut, changeCallOuts);
		menuSettings.getItems().addAll(changeUsername, changePassword);
		menubar.getMenus().addAll(menuAlterEmployees, menuSettings);
		menuAlterEmployees.setDisable(true);
		menuSettings.setDisable(true);
		
		// Creating the buttons for the GUI
		Button exitButton = new Button("Exit");
		Button submitButton = new Button("Submit");
		
		// Setting submit button as default and fixing to look like the original buttons
		submitButton.setDefaultButton(true);
		submitButton.setStyle("-fx-base: #ececec");
		
		// Creating all objects for start pane
		TextField username = new TextField();
		PasswordField password = new PasswordField();
		Label usernameLabel = new Label("Username:");
		Label passwordLabel = new Label("Password:");
		Label prompt = new Label("Enter your credentials");
		
		// Creating all boxes for objects to reside
		HBox buttonsHBox = new HBox(submitButton, exitButton);
		HBox hbox1 = new HBox();
		VBox top = new VBox();
		VBox labelVBox = new VBox(usernameLabel, passwordLabel);
		VBox entryVBox = new VBox(username, password);
		VBox vboxEverything = new VBox(prompt, hbox1);

		Image myImage = new Image("logo.png");
		ImageView logo = new ImageView(myImage);

		logo.setFitHeight(85);
		logo.setFitWidth(200);
		logo.setX(225);
		logo.setY(200);
		
		top.getChildren().addAll(menubar, logo);
		
		// Setting prompt text for entries
		username.setPromptText("Enter username");
		password.setPromptText("Enter password");

		// Adding the two vboxs with elements in them to the hbox
		hbox1.getChildren().addAll(labelVBox, entryVBox);

		// Setting padding, spacing, alignments, sizing, and fonts
		buttonsHBox.setPadding(new Insets(20));
		buttonsHBox.setSpacing(600);
		labelVBox.setPadding(new Insets(15));
		labelVBox.setSpacing(42);
		entryVBox.setPadding(new Insets(15));
		entryVBox.setSpacing(35);
		vboxEverything.setSpacing(20);
		top.setSpacing(35);
		
		BorderPane.setAlignment(vboxEverything, Pos.CENTER);
		BorderPane.setAlignment(top, Pos.CENTER);
		vboxEverything.setAlignment(Pos.CENTER);
		hbox1.setAlignment(Pos.CENTER);
		top.setAlignment(Pos.CENTER);
		startPane.setCenter(vboxEverything);
		startPane.setBottom(buttonsHBox);
		startPane.setTop(top);
		startPane.setMaxWidth(600);
		startPane.setMaxHeight(600);
		
		username.setMaxWidth(190);
		password.setMaxWidth(190);
		usernameLabel.setFont(new Font("Comic Sans MS", 15));
		passwordLabel.setFont(new Font("Comic Sans MS", 15));
		submitButton.setFont(new Font("Comic Sans MS", 13));
		exitButton.setFont(new Font("Comic Sans MS", 13));
		prompt.setFont(new Font("Comic Sans MS", 18));
		
		// Creating actions for the buttons being used
		exitButton.setOnAction(e -> {
			System.exit(0);
		});
		
		submitButton.setOnAction(e -> {
			if(!username.getText().equals("") && !password.getText().equals("")) {
				verifyLogin(startPane, username, password);
			} else {
				Alert notFinished = new Alert(AlertType.ERROR, "You have not filled out the form completely.");
				notFinished.showAndWait();
			}
		});
	}

	/**
	 * This method will take in the TextField and PasswordFields entered from the user
	 * and will compare them against the username and password for the admin account.
	 * @param startPane - the border pane for the GUI
	 * @param username - the username entered 
	 * @param password - the password entered
	 */
	public void verifyLogin(BorderPane startPane, TextField username, PasswordField password) {
		if(MongoUsage.verifyUsername(username.getText()) && MongoUsage.verifyPassword(username.getText(), password.getText())) {
			startPane.getChildren().clear();
			chooseActivity(startPane);
		} 
		else {
			Alert incorrectCreds = new Alert(AlertType.ERROR, "You have entered the incorrect username or password.");
			incorrectCreds.showAndWait();
		}
	}

	/**
	 * This method will create a GUI with four buttons, prompting the user to choose
	 * one of them for the GUI and it will go from there on which activity it goes to.
	 * @param startPane - the borderpane for the GUI
	 */
	public void chooseActivity(BorderPane startPane) {
		// Setting disable settings for the menubar for current page 
		disableCurrPage("");
		
		// Clearing and then setting color to the start pane
		startPane.getChildren().clear();
		startPane.setStyle("-fx-background-color: AQUAMARINE");
		
		// Undisabling everything in menubar
		menuAlterEmployees.setDisable(false);
		menuSettings.setDisable(false);
		
		// Creating buttons
		Button newStats = new Button("Enter stats for an employee");
		Button newEmployee = new Button("Enter a new employee");
		Button viewEmployee = new Button("View stats for an employee");
		Button viewAllEmployees = new Button("View stats for all employees");
		Button exitButton = new Button("Exit");
		
		Image myImage = new Image("logo.png");
		ImageView logo = new ImageView(myImage);

		logo.setFitHeight(250);
		logo.setFitWidth(400);
		logo.setX(225);
		logo.setY(200);
		
		// Creating page title
		Label pageLabel = new Label("ScanTech - Employee Performance");
		VBox topVbox = new VBox(menubar, pageLabel);
		topVbox.setSpacing(50);
		pageLabel.setFont(new Font("Comic Sans MS", 15));
		
		// Creating circular button variables
		final double CIRCLE_RADIUS = 45.0;
		Circle c = new Circle(CIRCLE_RADIUS);
		
		// Creating buttons sizes and shapes
		newStats.setShape(c);
		newStats.setPrefSize(2.0 * CIRCLE_RADIUS, 2.0 * CIRCLE_RADIUS);
		newStats.setWrapText(true);
		newEmployee.setShape(c);
		newEmployee.setPrefSize(2.0 * CIRCLE_RADIUS, 2.0 * CIRCLE_RADIUS);
		newEmployee.setWrapText(true);
		viewEmployee.setShape(c);
		viewEmployee.setPrefSize(2.0 * CIRCLE_RADIUS, 2.0 * CIRCLE_RADIUS);
		viewEmployee.setWrapText(true);
		viewAllEmployees.setShape(c);
		viewAllEmployees.setPrefSize(2.0 * CIRCLE_RADIUS, 2.0 * CIRCLE_RADIUS);
		viewAllEmployees.setWrapText(true);
		exitButton.setShape(c);
		exitButton.setPrefSize(2.0 * CIRCLE_RADIUS, 2.0 * CIRCLE_RADIUS);
		exitButton.setWrapText(true);
		
		// Setting fonts for buttons
		newStats.setFont(new Font("Comic Sans MS", 11));
		newEmployee.setFont(new Font("Comic Sans MS", 11));
		viewEmployee.setFont(new Font("Comic Sans MS", 11));
		viewAllEmployees.setFont(new Font("Comic Sans MS", 11));
		exitButton.setFont(new Font("Comic Sans MS", 11));

		// Creating the hboxes for the buttons
		HBox buttons = new HBox(newStats, newEmployee, viewEmployee, viewAllEmployees, exitButton);

		// Setting padding and spacing
		buttons.setPadding(new Insets(10));
		buttons.setSpacing(70);

		// Placing the buttons on the pane
		topVbox.setAlignment(Pos.CENTER);
		startPane.setCenter(logo);
		startPane.setTop(topVbox);
		startPane.setBottom(buttons);
	
		// Setting actions on buttons
		newEmployee.setOnAction(e -> {
			addNewEmployee(startPane);
		});
		
		newStats.setOnAction(e -> {
			newStats(startPane);
		});	
		
		viewEmployee.setOnAction(e -> {
			viewEmployee(startPane);
		});
		
		viewAllEmployees.setOnAction(e -> {
			try {
				viewAllEmployees();
			} 
			catch(IOException e1) {
				String stackTrace = ExceptionUtils.getStackTrace(e1);
				LogUtil.writeToLog(stackTrace);
			}
		});
		
		exitButton.setOnAction(e -> {
			System.exit(0);
		});
		
		deleteEmployee.setOnAction(e -> {
			deleteEmployee(startPane);
		});
		
		changeEmpName.setOnAction(e -> {
			changeEmployeeName(startPane);
		});
		
		changeSawCut.setOnAction(e -> {
			changeSawCuts(startPane);
		});
		
		changeHoleInSolid.setOnAction(e -> {
			changeHolesInSolid(startPane);
		});
		
		changeHoleInPlank.setOnAction(e -> {
			changeHolesInPlank(startPane);
		});
		
		changeCallOuts.setOnAction(e -> {
			changeCallOuts(startPane);
		});
		
		changeUsername.setOnAction(e -> {
			changeAdminUsername(startPane);
		});
		
		changePassword.setOnAction(e -> {
			changeAdminPassword(startPane);
		});
	}
	
	/**
	 * This method will go to the add new employee screen. This screen will allow the user to
	 * add a new employee to the employee database.
	 * @param startPane - the borderpane for the GUI
	 */
	public void addNewEmployee(BorderPane startPane) {
		// Setting disable settings for the menubar for current page 
		disableCurrPage("");
		
		// Clearing and then setting color to the start pane
		startPane.getChildren().clear();
		startPane.setStyle("-fx-background-color: AQUAMARINE");
		
		// Undisabling everything in menubar
		menuAlterEmployees.setDisable(false);
		menuSettings.setDisable(false);		
		
		// Making buttons 
		Button exitButton = new Button("Exit");
		Button submitButton = new Button("Submit");
		Button backButton = new Button("Back");
		
		// Setting submit button as default and fixing to look like the original buttons
		submitButton.setDefaultButton(true);
		submitButton.setStyle("-fx-base: #ececec");
		
		// Creating circular button variables
		final double CIRCLE_RADIUS = 28.0;
		Circle c = new Circle(CIRCLE_RADIUS);
		
		// Creating buttons sizes and shapes
		exitButton.setShape(c);
		exitButton.setPrefSize(2.0 * CIRCLE_RADIUS, 2.0 * CIRCLE_RADIUS);
		submitButton.setShape(c);
		submitButton.setPrefSize(2.0 * CIRCLE_RADIUS, 2.0 * CIRCLE_RADIUS);
		backButton.setShape(c);
		backButton.setPrefSize(2.0 * CIRCLE_RADIUS, 2.0 * CIRCLE_RADIUS);
		
		// Creating fields
		Label header = new Label("Add a new employee");
		Label lFirstName = new Label("Enter first name: ");
		Label lLastName = new Label("Enter last name: ");
		
		TextField empFirstName = new TextField();
		TextField empLastName = new TextField();
		
		VBox labels = new VBox(lFirstName, lLastName);
		VBox userEntered = new VBox(empFirstName, empLastName);
		VBox topVbox = new VBox(menubar, header);
		
		HBox buttons = new HBox(submitButton, backButton, exitButton);
		HBox hbox = new HBox(labels, userEntered);
		
		empFirstName.setPromptText("Enter first name");
		empLastName.setPromptText("Enter last name");
		
		// Setting button actions
		exitButton.setOnAction(e -> {
			System.exit(0);
		});

		backButton.setOnAction(e -> {
			chooseActivity(startPane);
		});
		
		submitButton.setOnAction(e -> {
			boolean isDone = true;
			
			// Checking if each field is entered
			if(empFirstName.getText().isEmpty()) {
				lFirstName.setTextFill(Color.RED);
				isDone = false;
			}
			else {
				lFirstName.setTextFill(Color.BLACK);
			}

			if(empLastName.getText().isEmpty()) {
				lLastName.setTextFill(Color.RED);
				isDone = false;
			}
			else {
				lLastName.setTextFill(Color.BLACK);
			}
			
			// If all fields are entered parse the results
			if(isDone) {
				MongoUsage.addEmployeeToDB(new Employee(empFirstName.getText(), empLastName.getText()));
				
				// Log addition of employee to file
				LogUtil.writeDBChangeToLog(new Employee(empFirstName.getText(), empLastName.getText()), false);
				
				Alert confirm = new Alert(AlertType.CONFIRMATION, "Successfully added a new employee.", ButtonType.OK);
				confirm.show();
				
				chooseActivity(startPane);
			}
		});
		
		deleteEmployee.setOnAction(e ->{
			deleteEmployee(startPane);
		});
		
		// Setting spacing, sizes, fonts, and padding
		header.setPadding(new Insets(30));
		labels.setPadding(new Insets(20));
		userEntered.setPadding(new Insets(20));
		buttons.setPadding(new Insets(20));
		labels.setSpacing(60);
		userEntered.setSpacing(50);
		buttons.setSpacing(275);
		submitButton.setFont(new Font("Comic Sans MS", 11));
		backButton.setFont(new Font("Comic Sans MS", 11));
		exitButton.setFont(new Font("Comic Sans MS", 11));
		lFirstName.setFont(new Font("Comic Sans MS", 14));
		lLastName.setFont(new Font("Comic Sans MS", 14));
		header.setFont(new Font("Comic Sans MS", 18));
		
		// Setting alignments for everything
		BorderPane.setAlignment(header, Pos.CENTER);
		BorderPane.setAlignment(hbox, Pos.CENTER);
		hbox.setAlignment(Pos.CENTER);
		topVbox.setAlignment(Pos.CENTER);
		startPane.setCenter(hbox);
		startPane.setTop(topVbox);
		startPane.setBottom(buttons);
	}
	
	/**
	 * This function sets up the page for adding stats to the employee, and it will prompt the user
	 * that these stats are what they want to add. All stats do NOT have to be filled out in order to
	 * process the form. A blank stat will be treated as a zero, and it will have no affect on the
	 * employees performance points.
	 * @param startPane - the borderpane for the GUI
	 */
	public void newStats(BorderPane startPane) throws NumberFormatException {
		// Setting disable settings for the menubar for current page 
		disableCurrPage("");
		
		// Undisabling everything in menubar
		menuAlterEmployees.setDisable(false);
		menuSettings.setDisable(false);
		
		// Clearing and then setting color to the start pane
		startPane.getChildren().clear();
		startPane.setStyle("-fx-background-color: AQUAMARINE");
		
		// Creating circular button variables
		final double CIRCLE_RADIUS = 28.0;
		Circle c = new Circle(CIRCLE_RADIUS);
		
		// Creating buttons
		Button exitButton = new Button("Exit");
		Button submitButton = new Button("Submit");
		Button backButton = new Button("Back");	
		
		// Setting submit button as default and fixing to look like the original buttons
		submitButton.setDefaultButton(true);
		submitButton.setStyle("-fx-base: #ececec");
		
		// Creating buttons sizes and shapes
		exitButton.setShape(c);
		exitButton.setPrefSize(2.0 * CIRCLE_RADIUS, 2.0 * CIRCLE_RADIUS);
		submitButton.setShape(c);
		submitButton.setPrefSize(2.0 * CIRCLE_RADIUS, 2.0 * CIRCLE_RADIUS);
		backButton.setShape(c);
		backButton.setPrefSize(2.0 * CIRCLE_RADIUS, 2.0 * CIRCLE_RADIUS);
		
		// Creating label for employee stats
		Label employeeToEdit = new Label();
		Label holesInSolid = new Label("Holes in Solid: ");
		Label holesInPlank = new Label("Holes in Plank: ");
		Label holesInSaw = new Label("Holes in Saw: ");
		Label callOuts = new Label("Call Outs: ");

		// Creating user entered fields
		TextField ansSolid = new TextField();
		TextField ansPlank = new TextField();
		TextField ansSaw = new TextField();
		TextField ansCallOuts = new TextField();
		
		// Setting prompt text for fields
		ansSolid.setPromptText("Enter a number");
		ansPlank.setPromptText("Enter a number");
		ansSaw.setPromptText("Enter a number");
		ansCallOuts.setPromptText("Enter a number");
		
		// Creating page title
		Label pageLabel = new Label("Enter stats for an employee");
		pageLabel.setFont(new Font("Comic Sans MS", 15));
		
		// For the buttons and fields
		HBox buttons = new HBox(submitButton, backButton, exitButton);
		HBox solid = new HBox(holesInSolid, ansSolid);
		HBox plank = new HBox(holesInPlank, ansPlank);
		HBox saw = new HBox(holesInSaw, ansSaw);
		HBox outs = new HBox(callOuts, ansCallOuts);
		VBox topVbox = new VBox(menubar, pageLabel);
		topVbox.setSpacing(50);

		// Creating the array list and setting equal to the employees names
		ArrayList<String> employeesArr = new ArrayList<String>();
		employeesArr = MongoUsage.getListOfEmployees();
		
		// Creating combobox
		final ComboBox<String> employees = populateComboBox(employeesArr);
		employees.setPromptText("Choose an employee");

		VBox vboxForm = new VBox(employees, solid, plank, saw, outs);
		
		// Making button actions 
		submitButton.setOnAction( new EventHandler<ActionEvent>() {			
			@Override
			public void handle(ActionEvent event) {
				Alert unansweredFields = new Alert(AlertType.ERROR, "Please fill out all fields before submitting.", 
						ButtonType.CLOSE, ButtonType.OK);		
				
				startPane.setCursor(Cursor.WAIT);
				
				if(ansSolid.getText().isEmpty()) {
					holesInSolid.setTextFill(Color.RED);
					unansweredFields.show();
					return;
				}
				else {
					holesInSolid.setTextFill(Color.BLACK);
				}

				if(ansPlank.getText().isEmpty()) {
					holesInPlank.setTextFill(Color.RED);
					unansweredFields.show();
					return;
				}
				else {
					holesInPlank.setTextFill(Color.BLACK);
				}

				if(ansSaw.getText().isEmpty()) {
					unansweredFields.show();
					holesInSaw.setTextFill(Color.RED);
					return;
				}
				else {
					holesInSaw.setTextFill(Color.BLACK);
				}

				if(ansCallOuts.getText().isEmpty()) {
					unansweredFields.show();
					callOuts.setTextFill(Color.RED);
					return;
				}
				else {
					callOuts.setTextFill(Color.BLACK);
				}	
				
				if(employees.getSelectionModel().isEmpty()) {
					unansweredFields.show();
					return;
				}
			
				try {
					int amtSolidHoles = Integer.parseInt(ansSolid.getText());
					int amtPlankHoles = Integer.parseInt(ansPlank.getText());
					int amtSawHoles = Integer.parseInt(ansSaw.getText());
					int amtCallOuts = Integer.parseInt(ansCallOuts.getText());
					HashMap<String, Integer> toChange = new HashMap<String, Integer>();
					ArrayList<Integer> arrToChange = new ArrayList<Integer>();
					ArrayList<String> names = new ArrayList<String>();
					
					names.add("holes in plank");
					names.add("holes in solid");
					names.add("saw cuts");
					names.add("call outs");
					
					arrToChange.add(amtPlankHoles);				
					arrToChange.add(amtSolidHoles);
					arrToChange.add(amtSawHoles);
					arrToChange.add(amtCallOuts);
					
					for(int i = 0; i < arrToChange.size(); i++) {
						if(arrToChange.get(i) != 0) {
							toChange.put(names.get(i), arrToChange.get(i));
						}
					}
						
					// Getting the employee for logging, and original vals
					Employee emp = MongoUsage.findEmployee(employees);
					int plankHoles = emp.getPlankHoles();
					int solidHoles = emp.getSolidHoles();
					int sawCuts = emp.getSawHoles();
					int callOut = emp.getCallOuts();
					
					// Adding the employee stats to the DB
					MongoUsage.addToEmployeeStats(employees, toChange);
	
					// Getting the new employee to send to logging method
					Employee newEmp = MongoUsage.findEmployee(employees);
					
					// Iterating over hashmap with keys of changed measures and values for logging
					toChange.forEach((key, val) -> {
						switch(key) {
							case "holes in plank":
								LogUtil.writeDBChangeToLog(String.valueOf(plankHoles), key, newEmp);
								break;
							case "holes in solid":
								LogUtil.writeDBChangeToLog(String.valueOf(solidHoles), key, newEmp);
								break;
							case "saw cuts":
								LogUtil.writeDBChangeToLog(String.valueOf(sawCuts), key, newEmp);
								break;
							case "call outs":
								LogUtil.writeDBChangeToLog(String.valueOf(callOut), key, newEmp);
								break;
						}
					});				
				}
				catch(NumberFormatException e1) {
					String stackTrace = ExceptionUtils.getStackTrace(e1);
					LogUtil.writeToLog(stackTrace);
					
					Alert invalidResponse = new Alert(AlertType.ERROR, "Invalid response."
							+ "You did not enter a correctly formatted number. Please press"
							+ " \"OK\" to try again, otherwise close to go back.", 
							ButtonType.CLOSE, ButtonType.OK);
					
					invalidResponse.setTitle("Invalid entrant.");
					
					invalidResponse.showAndWait();
					
					if(invalidResponse.getResult() == ButtonType.OK) {
						return;
					}
					else {
						startPane.getChildren().clear();
						loginPage(startPane);
					}
				}
				chooseActivity(startPane);
			}
		});	
		
		backButton.setOnAction(e -> {
			chooseActivity(startPane);
		});
		
		exitButton.setOnAction(e -> {
			System.exit(0);
		});
				
		// Vbox for buttons and fields
		VBox vbox = new VBox(vboxForm, buttons);
		
		// Set spacing, fonts, and padding
		buttons.setPadding(new Insets(20));
		buttons.setSpacing(275);
		submitButton.setFont(new Font("Comic Sans MS", 11));
		backButton.setFont(new Font("Comic Sans MS", 11));
		exitButton.setFont(new Font("Comic Sans MS", 11));
		employeeToEdit.setFont(new Font("Comic Sans MS", 18));
		holesInSolid.setFont(new Font("Comic Sans MS", 18));
		holesInPlank.setFont(new Font("Comic Sans MS", 18));
		holesInSaw.setFont(new Font("Comic Sans MS", 18));
		callOuts.setFont(new Font("Comic Sans MS", 18));
		vboxForm.setSpacing(70);
		plank.setSpacing(35);
		solid.setSpacing(35);
		saw.setSpacing(42);
		outs.setSpacing(69);
		
		// Setting alignment
		BorderPane.setAlignment(solid, Pos.CENTER);
		BorderPane.setAlignment(plank, Pos.CENTER);
		BorderPane.setAlignment(saw, Pos.CENTER);
		BorderPane.setAlignment(outs, Pos.CENTER);
		BorderPane.setAlignment(vboxForm, Pos.CENTER);
		BorderPane.setAlignment(vbox, Pos.CENTER);
		solid.setAlignment(Pos.CENTER);
		plank.setAlignment(Pos.CENTER);
		saw.setAlignment(Pos.CENTER);
		outs.setAlignment(Pos.CENTER);
		vboxForm.setAlignment(Pos.CENTER);
		vbox.setAlignment(Pos.CENTER);
		topVbox.setAlignment(Pos.CENTER);
		startPane.setCenter(vboxForm);
		startPane.setBottom(vbox);
		startPane.setTop(topVbox);
	}

	/**
	 * This method will call the writesoleEmployeeToFile method once an employee is chosen from the 
	 * combobox that is populated with the getEmployees method.
	 * @param startPane - the borderpane for the GUI
	 */
	public void viewEmployee(BorderPane startPane) {
		// Setting disable settings for the menubar for current page 
		disableCurrPage("");
		
		// Clearing and then setting color to the start pane
		startPane.getChildren().clear();
		startPane.setStyle("-fx-background-color: AQUAMARINE");
		
		// Undisabling everything in menubar
		menuAlterEmployees.setDisable(false);
		menuSettings.setDisable(false);
		
		// Creating circular button variables
		final double CIRCLE_RADIUS = 28.0;
		Circle c = new Circle(CIRCLE_RADIUS);
		
		// Creating buttons
		Button exitButton = new Button("Exit");
		Button submitButton = new Button("Submit");
		Button backButton = new Button("Back");
		
		// Setting submit button as default and fixing to look like the original buttons
		submitButton.setDefaultButton(true);
		submitButton.setStyle("-fx-base: #ececec");
		
		// Creating buttons sizes and shapes
		exitButton.setShape(c);
		exitButton.setPrefSize(2.0 * CIRCLE_RADIUS, 2.0 * CIRCLE_RADIUS);
		submitButton.setShape(c);
		submitButton.setPrefSize(2.0 * CIRCLE_RADIUS, 2.0 * CIRCLE_RADIUS);
		backButton.setShape(c);
		backButton.setPrefSize(2.0 * CIRCLE_RADIUS, 2.0 * CIRCLE_RADIUS);
		
		// Creating label for employee stats
		Label pageLabel = new Label("View a single employee");
		pageLabel.setFont(new Font("Comic Sans MS", 15));
		
		// For the buttons
		HBox buttons = new HBox(submitButton, backButton, exitButton);
		HBox hbox1 = new HBox();
		
		// Vbox for stats and buttons
		VBox vbox = new VBox(buttons);
		VBox topVbox = new VBox(menubar, pageLabel);
		topVbox.setSpacing(50);

		// Creating the array list and setting equal to the employees names
		ArrayList<String> employeesArr = new ArrayList<String>();
		employeesArr = MongoUsage.getListOfEmployees();

		// Creating combobox
		final ComboBox<String> employees = populateComboBox(employeesArr);
		employees.setPromptText("Choose an employee");
		
		// Making button actions 
		submitButton.setOnAction(e -> {
			// Make this to fire on submit button
			Employee employee = MongoUsage.findEmployee(employees);
			String fileName;
			try {
				fileName = writeSoleEmployeeToFile(employee);
				openFile(fileName);
			} 
			catch(IOException e1) {
				String stackTrace = ExceptionUtils.getStackTrace(e1);
				LogUtil.writeToLog(stackTrace);
			}
		});	
		
		backButton.setOnAction(e -> {
			chooseActivity(startPane);
		});
		
		exitButton.setOnAction(e -> {
			System.exit(0);
		});
				
		// Set spacing, fonts, and padding
		buttons.setPadding(new Insets(20));
		buttons.setSpacing(275);
		submitButton.setFont(new Font("Comic Sans MS", 11));
		backButton.setFont(new Font("Comic Sans MS", 11));
		exitButton.setFont(new Font("Comic Sans MS", 11));
		
		// Setting alignment
		BorderPane.setAlignment(hbox1, Pos.CENTER);
		hbox1.setAlignment(Pos.CENTER);
		BorderPane.setAlignment(vbox, Pos.CENTER);
		vbox.setAlignment(Pos.CENTER);
		topVbox.setAlignment(Pos.CENTER);
		startPane.setCenter(employees);
		startPane.setBottom(vbox);
		startPane.setTop(topVbox);
	}
	
	/**
	 * This method will write all employee stats to an xlsx file. The employee stats are stored within
	 * an array that is processed within the MongoUsage class. This array contains all documents within
	 * the Employee collection, and they are each removed from the temporary array so the next index
	 * that is to be written will always be the first element in the array.
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public String writeAllEmployeesToFile() throws FileNotFoundException, IOException {
		String sheetName = "All Employee Stats";
		int currRow = 1;
		
		// Setting workbook variables for Excel
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet(sheetName);
		Row header = sheet.createRow(0);

		CellStyle headerStyle = workbook.createCellStyle();
		headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
		headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		// Setting fonts for Excel workbook
		XSSFFont font = ((XSSFWorkbook) workbook).createFont();
		font.setFontName("Arial");
		font.setFontHeightInPoints((short) 16);
		font.setBold(true);
		headerStyle.setFont(font);
		
		// Adding in the header rows
		Cell headerCell = header.createCell(0);
		headerCell.setCellValue("Name");
		headerCell.setCellStyle(headerStyle);

		headerCell = header.createCell(1);
		headerCell.setCellValue("Holes in Solid");
		headerCell.setCellStyle(headerStyle);
		
		headerCell = header.createCell(2);
		headerCell.setCellValue("Holes in Plank");
		headerCell.setCellStyle(headerStyle);
		
		headerCell = header.createCell(3);
		headerCell.setCellValue("Holes in Saw");
		headerCell.setCellStyle(headerStyle);
		
		headerCell = header.createCell(4);
		headerCell.setCellValue("Call outs");
		headerCell.setCellStyle(headerStyle);
		
		headerCell = header.createCell(5);
		headerCell.setCellValue("Total Points");
		headerCell.setCellStyle(headerStyle);
		
		CellStyle style = workbook.createCellStyle();
		style.setWrapText(true);

		// Printing out current employee, then iterating to next row, then printing out next
		ArrayList<String> employees = MongoUsage.getListOfEmployees();
		int size = employees.size();
		
		// Iterating through the employee objects
		for(int i = 0; i < size; i++) {
			currRow = i + 1;
			Row row = sheet.createRow(currRow);
			
			// Getting the next employee in the array and getting that objects stats
			Employee currEmp = MongoUsage.findEmployee(employees.get(i)); 
			ArrayList<String> employeeStats = currEmp.getAllStats();
			int statSize = employeeStats.size();
			
			// Printing the name to the excel sheet 
			Cell name = row.createCell(0);
			name.setCellValue(employeeStats.get(0));
			name.setCellStyle(style);
			
			// Printing out stats for the current employee object
			for(int j = 1; j < statSize; j++) {
				Cell cell = row.createCell(j);
				cell.setCellValue(Double.valueOf(employeeStats.get(j)));
				cell.setCellStyle(style);
			}
		}
		
		sheet.setAutoFilter(new CellRangeAddress(0, currRow, 0, 5));
		
		String fileName = writeAndCloseWorkbook(workbook, sheetName);
		return fileName;
	}
	
	/**
	 * This method will write the stats of the chosen employee to the xlsx file. It will then call
	 * methods within this class to open and save the file down to the working directory. 
	 * @param employee - the current employee to write from
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public String writeSoleEmployeeToFile(Employee employee) throws FileNotFoundException, IOException {
		ArrayList<String> stats = employee.getAllStats();
		int size = stats.size();
		
		String employeeName = stats.get(0);
		String sheetName = "Stats for " + employeeName;
		
		// Setting workbook variables for Excel
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet(sheetName);
		Row header = sheet.createRow(0);

		CellStyle headerStyle = workbook.createCellStyle();
		headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
		headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		// Creating fonts for Excel
		XSSFFont font = ((XSSFWorkbook) workbook).createFont();
		font.setFontName("Arial");
		font.setFontHeightInPoints((short) 16);
		font.setBold(true);
		headerStyle.setFont(font);
		
		// Adding in the header rows
		Cell headerCell = header.createCell(0);
		headerCell.setCellValue("Name");
		headerCell.setCellStyle(headerStyle);

		headerCell = header.createCell(1);
		headerCell.setCellValue("Holes in Solid");
		headerCell.setCellStyle(headerStyle);
		
		headerCell = header.createCell(2);
		headerCell.setCellValue("Holes in Plank");
		headerCell.setCellStyle(headerStyle);
		
		headerCell = header.createCell(3);
		headerCell.setCellValue("Holes in Saw");
		headerCell.setCellStyle(headerStyle);
		
		headerCell = header.createCell(4);
		headerCell.setCellValue("Call outs");
		headerCell.setCellStyle(headerStyle);
		
		headerCell = header.createCell(5);
		headerCell.setCellValue("Total Points");
		headerCell.setCellStyle(headerStyle);
		
		CellStyle style = workbook.createCellStyle();
		style.setWrapText(true);

		// Printing out the name because it isn't a double
		Row row = sheet.createRow(1);
		Cell name = row.createCell(0);
		name.setCellValue(employeeName);
		name.setCellStyle(style);

		// Going through the stats minus the name
		for(int i = 1; i < size; i++) {
			Cell cell = row.createCell(i);
			cell.setCellValue(Double.valueOf(stats.get(i)));
			cell.setCellStyle(style);
		}
				
		String fileName = writeAndCloseWorkbook(workbook, sheetName, stats.get(0));
		return fileName;
	}
	
	/**
	 * This method will write the contents of the workbook in the current directory.
	 * The file will then be closed on the backend once it is finished generating and opened on the
	 * users OS.
	 * @param workbook - the xlsx workbook being written to
	 * @param sheetName - the name of the sheet being written to within the xlsx workbook
	 * @param employeeName - the full name of the employee
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public String writeAndCloseWorkbook(Workbook workbook, String sheetName, String employeeName) throws FileNotFoundException, IOException {
		// Setting file path values for saving the file to the system
		String fileName, fileExtension = ".xlsx";
        Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
		int space = 0;
        
        // Getting first and last name
        for(int i = 0; i < employeeName.length(); i++) {
        	if(employeeName.charAt(i) == ' ') {
        		space = i;
        		break;
        	}
        }
        
		// Making the file name with timestamp of date/time along with FIRST_LAST or ALL_EMPLOYEES
		fileName = timeStamp + "_ScanTech_" + employeeName.substring(0, space).toUpperCase() + "_" + employeeName.substring((space + 1), employeeName.length()).toUpperCase() + fileExtension;
		
		// Setting more file path values
		File currDir = new File(".");
		String path = currDir.getAbsolutePath();
		String fileLocation = path.substring(0, path.length() - 1) + fileName;
		FileOutputStream outputStream;
		
		// Getting the worksheet and setting a fixed column width
		Sheet wks = workbook.getSheet(sheetName);
		wks.setDefaultColumnWidth(23);
		
		// Writing the file to the file location
		try {
			outputStream = new FileOutputStream(fileLocation);
			workbook.write(outputStream);
			workbook.close();
		} 
		catch(FileNotFoundException e) {
			String stackTrace = ExceptionUtils.getStackTrace(e);
			LogUtil.writeToLog(stackTrace);
		}
		catch(IOException e1) {
			String stackTrace = ExceptionUtils.getStackTrace(e1);
			LogUtil.writeToLog(stackTrace);
		}
		return fileName;
	}
	
	/**
	 * This method will write the contents to a workbook in the current directory.
	 * The file will then be closed on the backend once it is finished generating and opened on the
	 * users OS.
	 * @param workbook - the xlsx workbook being written to
	 * @param sheetName - the name of the sheet being written to within the xlsx workbook
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public String writeAndCloseWorkbook(Workbook workbook, String sheetName) throws FileNotFoundException, IOException {
		// Setting file path values for saving the file to the system
		String fileName, fileExtension = ".xlsx";
        Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
        
		// Making the file name with timestamp of date/time along with FIRST_LAST or ALL_EMPLOYEES
		fileName = timeStamp + "_ScanTech_ALL_EMPLOYEES" + fileExtension;
		
		// Setting more file path values
		File currDir = new File(".");
		String path = currDir.getAbsolutePath();
		String fileLocation = path.substring(0, path.length() - 1) + fileName;
		FileOutputStream outputStream;
		
		// Getting the worksheet and setting a fixed column width
		Sheet wks = workbook.getSheet(sheetName);
		wks.setDefaultColumnWidth(23);
		
		// Writing the file to the file location
		try {
			outputStream = new FileOutputStream(fileLocation);
			workbook.write(outputStream);
			workbook.close();
		} 
		catch(FileNotFoundException e) {
			String stackTrace = ExceptionUtils.getStackTrace(e);
			LogUtil.writeToLog(stackTrace);	
		}
		catch(IOException e1) {
			String stackTrace = ExceptionUtils.getStackTrace(e1);
			LogUtil.writeToLog(stackTrace);
		}
		return fileName;
	}
	
	/**
	 * This method opens the file once it is found, for simplicity on the users end.
	 * @throws IOException
	 */
	public void openFile(String fileName) throws IOException{
		try {
			File fileToOpen = new File(fileName);
			Desktop desktop = Desktop.getDesktop();
			desktop.open(fileToOpen);
		}
		catch(IOException e) {
			String stackTrace = ExceptionUtils.getStackTrace(e);
			LogUtil.writeToLog(stackTrace);
		}
	}
	
	/**
	 * This method will use an API and helper method to write to an excel file with all employee
	 * stats. The file will be automatically opened when it is finished generating.
	 * @throws IOException
	 */
	public void viewAllEmployees() throws IOException {
		String fileName;
		try {
			fileName = writeAllEmployeesToFile();
			openFile(fileName);
		} 
		catch(IOException e) {
			String stackTrace = ExceptionUtils.getStackTrace(e);
			LogUtil.writeToLog(stackTrace);
		}
	}
	
	/**
	 * This method will allow for deletion of an employee from the Mongo DB. Employees are populated
	 * in a combobox and then chosen from the list to be deleted.
	 * @param startPane - the startpane for javafx
	 */
	public void deleteEmployee(BorderPane startPane) {
		disableCurrPage("Delete");
		
		// Undisabling everything in menubar
		menuAlterEmployees.setDisable(false);
		menuSettings.setDisable(false);
		
		// Clearing and then setting color to the start pane
		startPane.getChildren().clear();
		startPane.setStyle("-fx-background-color: AQUAMARINE");
		
		// Creating circular button variables
		final double CIRCLE_RADIUS = 28.0;
		Circle c = new Circle(CIRCLE_RADIUS);
		
		// Creating buttons
		Button exitButton = new Button("Exit");
		Button submitButton = new Button("Submit");
		Button backButton = new Button("Back");
		
		// Setting submit button as default and fixing to look like the original buttons
		submitButton.setDefaultButton(true);
		submitButton.setStyle("-fx-base: #ececec");
		
		// Creating buttons sizes and shapes
		exitButton.setShape(c);
		exitButton.setPrefSize(2.0 * CIRCLE_RADIUS, 2.0 * CIRCLE_RADIUS);
		submitButton.setShape(c);
		submitButton.setPrefSize(2.0 * CIRCLE_RADIUS, 2.0 * CIRCLE_RADIUS);
		backButton.setShape(c);
		backButton.setPrefSize(2.0 * CIRCLE_RADIUS, 2.0 * CIRCLE_RADIUS);
		
		// Creating page title
		Label pageLabel = new Label("Delete an employee");
		pageLabel.setFont(new Font("Comic Sans MS", 15));
		
		// For the buttons
		HBox buttons = new HBox(submitButton, backButton, exitButton);
		HBox hbox1 = new HBox();
		
		// Vbox for stats and buttons
		VBox vbox = new VBox(buttons);
		VBox topVbox = new VBox(menubar, pageLabel);
		topVbox.setSpacing(50);

		// Creating the array list and setting equal to the employees names
		ArrayList<String> employeesArr = new ArrayList<String>();
		employeesArr = MongoUsage.getListOfEmployees();
		
		// Creating combobox
		final ComboBox<String> employees = populateComboBox(employeesArr);
		employees.setPromptText("Choose an employee");
		
		// Making button actions 
		submitButton.setOnAction(e -> {
			boolean isDone = true;
			
			Alert unansweredFields = new Alert(AlertType.ERROR, "Please fill out all fields before submitting.", 
					ButtonType.CLOSE, ButtonType.OK);		
			
			startPane.setCursor(Cursor.WAIT);
			
			if(employees.getSelectionModel().isEmpty()) {
				unansweredFields.show();
				isDone = false;
				return;
			}
			
			if(isDone) {
				// Make this to fire on submit button
				String name = employees.getValue();
				Employee employee = MongoUsage.findEmployee(name);
				
				Alert confirm = new Alert(AlertType.CONFIRMATION, "Are you sure you want to delete the employee " + employee.toString() + " ?", ButtonType.YES, ButtonType.CANCEL);
				confirm.showAndWait();
	
				if(confirm.getResult() == ButtonType.YES) {
					MongoUsage.removeEmployee(employee);
					
					// Log the deletion of the employee to deletion file
					LogUtil.writeDBChangeToLog(employee, true);
					
					Alert deleted = new Alert(AlertType.CONFIRMATION, "Employee successfully deleted.", ButtonType.OK);
					deleted.show();
				}
				else {
					Alert cancelled = new Alert(AlertType.WARNING, "Cancelled deletion of employee " + employee.toString() + ".", ButtonType.OK);
					cancelled.showAndWait();
				}
				
				startPane.setBottom(hbox1);
				chooseActivity(startPane);
			}
		});	
		
		backButton.setOnAction(e -> {
			chooseActivity(startPane);
		});
		
		exitButton.setOnAction(e -> {
			System.exit(0);
		});
				
		// Set spacing, fonts, and padding
		buttons.setPadding(new Insets(20));
		buttons.setSpacing(275);
		submitButton.setFont(new Font("Comic Sans MS", 11));
		backButton.setFont(new Font("Comic Sans MS", 11));
		exitButton.setFont(new Font("Comic Sans MS", 11));
		
		// Setting alignment
		BorderPane.setAlignment(hbox1, Pos.CENTER);
		hbox1.setAlignment(Pos.CENTER);
		BorderPane.setAlignment(vbox, Pos.CENTER);
		vbox.setAlignment(Pos.CENTER);
		topVbox.setAlignment(Pos.CENTER);
		startPane.setCenter(employees);
		startPane.setBottom(vbox);
		startPane.setTop(topVbox);
	}
	
	/**
	 * This method will change the specified employees name to the new user entered name.
	 * @param startPane - the startpane for javafx
	 */
	public void changeEmployeeName(BorderPane startPane) {
		disableCurrPage("Emp Name");
		
		// Undisabling everything in menubar
		menuAlterEmployees.setDisable(false);
		menuSettings.setDisable(false);
		
		// Clearing and then setting color to the start pane
		startPane.getChildren().clear();
		startPane.setStyle("-fx-background-color: AQUAMARINE");
		
		// Creating circular button variables
		final double CIRCLE_RADIUS = 28.0;
		Circle c = new Circle(CIRCLE_RADIUS);
		
		// Creating buttons
		Button exitButton = new Button("Exit");
		Button submitButton = new Button("Submit");
		Button backButton = new Button("Back");
		
		// Setting submit button as default and fixing to look like the original buttons
		submitButton.setDefaultButton(true);
		submitButton.setStyle("-fx-base: #ececec");
		
		// Creating buttons sizes and shapes
		exitButton.setShape(c);
		exitButton.setPrefSize(2.0 * CIRCLE_RADIUS, 2.0 * CIRCLE_RADIUS);
		submitButton.setShape(c);
		submitButton.setPrefSize(2.0 * CIRCLE_RADIUS, 2.0 * CIRCLE_RADIUS);
		backButton.setShape(c);
		backButton.setPrefSize(2.0 * CIRCLE_RADIUS, 2.0 * CIRCLE_RADIUS);

		// New name entry for employee
		TextField newName = new TextField();
		newName.setMaxWidth(320);
		newName.setPromptText("Employees new name");
		
		// Creating page title
		Label pageLabel = new Label("Change employee name");
		pageLabel.setFont(new Font("Comic Sans MS", 15));
		
		// For the buttons
		HBox buttons = new HBox(submitButton, backButton, exitButton);
		HBox hbox1 = new HBox();
		VBox topVbox = new VBox(menubar, pageLabel);
		topVbox.setSpacing(50);
		
		// Creating the array list and setting equal to the employees names
		ArrayList<String> employeesArr = new ArrayList<String>();
		employeesArr = MongoUsage.getListOfEmployees();
		
		// Creating combobox
		final ComboBox<String> employees = populateComboBox(employeesArr);
		employees.setPromptText("Choose an employee");
		
		// Vbox for stats and buttons
		VBox vboxOne = new VBox(employees, newName);
		vboxOne.setSpacing(40);
		VBox vbox = new VBox(vboxOne, buttons);
		
		// Making button actions 
		submitButton.setOnAction(e -> {
			boolean isDone = true;
			
			Alert unansweredFields = new Alert(AlertType.ERROR, "Please fill out all fields before submitting.", 
					ButtonType.CLOSE, ButtonType.OK);		
			
			startPane.setCursor(Cursor.WAIT);
			
			if(employees.getSelectionModel().isEmpty()) {
				unansweredFields.show();
				isDone = false;
				return;
			}
			
			if(newName.getText().isEmpty()) {
				unansweredFields.show();
				isDone = false;
				return;
			}
			
			if(isDone) {
				Employee emp = MongoUsage.findEmployee(employees);
				
				String enteredName = newName.getText();
				String firstName = "";
				String lastName = "";
				int whitespace = 0;
				int length = enteredName.length();
				
				// Finding the space in the full name to split the string
				for(int i = 0; i < length; i++) {
					char currLetter = enteredName.charAt(i);
					if(currLetter != ' ') {
						whitespace++;
					}
					else {
						break;
					}
				}
				
				// Getting name with whitespace as separator
				firstName = enteredName.substring(0, whitespace);
				lastName = enteredName.substring(whitespace + 1, length);
				
				Alert confirm = new Alert(AlertType.CONFIRMATION, "Are you sure you want to change the employee name " + emp.toString() + " to " + firstName + " " + lastName + "? \nThis CANNOT be undone.", ButtonType.YES, ButtonType.CANCEL);
				confirm.showAndWait();
	
				// Getting original amount of measure for logging changes
				String originalAmt = emp.toString();
				
				if(confirm.getResult() == ButtonType.YES) {
					// Removing the old employee
					MongoUsage.removeEmployee(emp);
								
					// Setting the objects first and last name / full name for new insertion
					emp.setFirstName(firstName);
					emp.setLastName(lastName);
					emp.setFullName(firstName + " " + lastName);
					
					// Inserting the new employee
					MongoUsage.addEmployeeToDB(emp);
					
					// Logging change to DB write file
					LogUtil.writeDBChangeToLog(originalAmt, "name", emp);
				}
				else {
					Alert cancelled = new Alert(AlertType.WARNING, "Cancelled changing of employee name " + emp.toString() + ".", ButtonType.OK);
					cancelled.showAndWait();
				}
				
				startPane.setBottom(hbox1);
				chooseActivity(startPane);
			}
		});	
		
		backButton.setOnAction(e -> {
			chooseActivity(startPane);
		});
		
		exitButton.setOnAction(e -> {
			System.exit(0);
		});
				
		// Set spacing, fonts, and padding
		buttons.setPadding(new Insets(20));
		buttons.setSpacing(275);
		submitButton.setFont(new Font("Comic Sans MS", 11));
		backButton.setFont(new Font("Comic Sans MS", 11));
		exitButton.setFont(new Font("Comic Sans MS", 11));
		newName.setFont(new Font("Comic Sans MS", 18));
		
		// Setting alignment
		BorderPane.setAlignment(hbox1, Pos.CENTER);
		hbox1.setAlignment(Pos.CENTER);
		BorderPane.setAlignment(vbox, Pos.CENTER);
		vbox.setAlignment(Pos.CENTER);
		BorderPane.setAlignment(vboxOne, Pos.CENTER);
		vboxOne.setAlignment(Pos.CENTER);
		topVbox.setAlignment(Pos.CENTER);
		startPane.setCenter(vboxOne);
		startPane.setBottom(vbox);
		startPane.setTop(topVbox);
	}
	
	/**
	 * This method will change the specified employees holes in solid to the new user entered 
	 * holes in solid.
	 * @param startPane - the startpane for javafx
	 */
	public void changeHolesInSolid(BorderPane startPane) {
		disableCurrPage("Solid");
		
		// Undisabling everything in menubar
		menuAlterEmployees.setDisable(false);
		menuSettings.setDisable(false);
		
		// Clearing and then setting color to the start pane
		startPane.getChildren().clear();
		startPane.setStyle("-fx-background-color: AQUAMARINE");
		
		// Creating circular button variables
		final double CIRCLE_RADIUS = 28.0;
		Circle c = new Circle(CIRCLE_RADIUS);
		
		// Creating buttons
		Button exitButton = new Button("Exit");
		Button submitButton = new Button("Submit");
		Button backButton = new Button("Back");	
		
		// Setting submit button as default and fixing to look like the original buttons
		submitButton.setDefaultButton(true);
		submitButton.setStyle("-fx-base: #ececec");
		
		// Creating buttons sizes and shapes
		exitButton.setShape(c);
		exitButton.setPrefSize(2.0 * CIRCLE_RADIUS, 2.0 * CIRCLE_RADIUS);
		submitButton.setShape(c);
		submitButton.setPrefSize(2.0 * CIRCLE_RADIUS, 2.0 * CIRCLE_RADIUS);
		backButton.setShape(c);
		backButton.setPrefSize(2.0 * CIRCLE_RADIUS, 2.0 * CIRCLE_RADIUS);

		// Creating page title
		Label pageLabel = new Label("Change holes in solid for an employee");
		pageLabel.setFont(new Font("Comic Sans MS", 15));

		// New name entry for employee
		TextField newSolid = new TextField();
		newSolid.setMaxWidth(320);
		newSolid.setPromptText("Amount of holes in solid");
		
		// For the buttons
		HBox buttons = new HBox(submitButton, backButton, exitButton);
		HBox hbox1 = new HBox();
		
		// Creating the array list and setting equal to the employees names
		ArrayList<String> employeesArr = new ArrayList<String>();
		employeesArr = MongoUsage.getListOfEmployees();
		
		// Creating combobox
		final ComboBox<String> employees = populateComboBox(employeesArr);
		employees.setPromptText("Choose an employee");
		
		// Vbox for stats and buttons
		VBox vboxOne = new VBox(employees, newSolid);
		vboxOne.setSpacing(40);
		VBox vbox = new VBox(vboxOne, buttons);
		VBox topVbox = new VBox(menubar, pageLabel);
		topVbox.setSpacing(50);

		// Making button actions 
		submitButton.setOnAction(e -> {
			boolean isDone = true;
			
			Alert unansweredFields = new Alert(AlertType.ERROR, "Please fill out all fields before submitting.", 
					ButtonType.CLOSE, ButtonType.OK);		
			
			startPane.setCursor(Cursor.WAIT);
			
			if(employees.getSelectionModel().isEmpty()) {
				unansweredFields.show();
				isDone = false;
				return;
			}
			
			if(newSolid.getText().isEmpty()) {
				unansweredFields.show();
				isDone = false;
				return;
			}
			
			if(isDone) {
				Employee emp = MongoUsage.findEmployee(employees);
		
				int newSolidAmt = Integer.parseInt(newSolid.getText());
				
				Alert confirm = new Alert(AlertType.CONFIRMATION, "Are you sure you want to change " + emp.toString() + "\'s holes in solid from the original amount of " + emp.getSolidHoles() + " to " + newSolidAmt + "? \nThis CANNOT be undone.", ButtonType.YES, ButtonType.CANCEL);
				confirm.showAndWait();
				
				// Getting original amount of measure for logging changes
				String originalAmt = String.valueOf(emp.getSolidHoles());
	
				if(confirm.getResult() == ButtonType.YES) {
					// Removing the old employee
					MongoUsage.removeEmployee(emp);
								
					// Setting the objects new amt of holes in solid
					emp.setSolidHoles(newSolidAmt);
					
					// Inserting the new employee
					MongoUsage.addEmployeeToDB(emp);
					
					// Logging change to DB write file
					LogUtil.writeDBChangeToLog(originalAmt, "holes in solid", emp);
				}
				else {
					Alert cancelled = new Alert(AlertType.WARNING, "Cancelled changing of employee name " + emp.toString() + ".", ButtonType.OK);
					cancelled.showAndWait();
				}
				
				startPane.setBottom(hbox1);
				chooseActivity(startPane);
			}
		});	
		
		backButton.setOnAction(e -> {
			chooseActivity(startPane);
		});
		
		exitButton.setOnAction(e -> {
			System.exit(0);
		});
				
		// Set spacing, fonts, and padding
		buttons.setPadding(new Insets(20));
		buttons.setSpacing(275);
		submitButton.setFont(new Font("Comic Sans MS", 11));
		backButton.setFont(new Font("Comic Sans MS", 11));
		exitButton.setFont(new Font("Comic Sans MS", 11));
		newSolid.setFont(new Font("Comic Sans MS", 18));
		
		// Setting alignment
		BorderPane.setAlignment(hbox1, Pos.CENTER);
		hbox1.setAlignment(Pos.CENTER);
		BorderPane.setAlignment(vbox, Pos.CENTER);
		vbox.setAlignment(Pos.CENTER);
		BorderPane.setAlignment(vboxOne, Pos.CENTER);
		vboxOne.setAlignment(Pos.CENTER);
		topVbox.setAlignment(Pos.CENTER);
		startPane.setCenter(vboxOne);
		startPane.setBottom(vbox);
		startPane.setTop(topVbox);
	}
	
	/**
	 * This method will change the specified employees holes in plank to the new user entered 
	 * holes in plank.
	 * @param startPane - the startpane for javafx
	 */
	public void changeHolesInPlank(BorderPane startPane) {
		disableCurrPage("Plank");
		
		// Undisabling everything in menubar
		menuAlterEmployees.setDisable(false);
		menuSettings.setDisable(false);
		
		// Clearing and then setting color to the start pane
		startPane.getChildren().clear();
		startPane.setStyle("-fx-background-color: AQUAMARINE");
		
		// Creating circular button variables
		final double CIRCLE_RADIUS = 28.0;
		Circle c = new Circle(CIRCLE_RADIUS);
		
		// Creating buttons
		Button exitButton = new Button("Exit");
		Button submitButton = new Button("Submit");
		Button backButton = new Button("Back");	
		
		// Setting submit button as default and fixing to look like the original buttons
		submitButton.setDefaultButton(true);
		submitButton.setStyle("-fx-base: #ececec");
		
		// Creating buttons sizes and shapes
		exitButton.setShape(c);
		exitButton.setPrefSize(2.0 * CIRCLE_RADIUS, 2.0 * CIRCLE_RADIUS);
		submitButton.setShape(c);
		submitButton.setPrefSize(2.0 * CIRCLE_RADIUS, 2.0 * CIRCLE_RADIUS);
		backButton.setShape(c);
		backButton.setPrefSize(2.0 * CIRCLE_RADIUS, 2.0 * CIRCLE_RADIUS);

		// New name entry for employee
		TextField newPlank = new TextField();
		newPlank.setMaxWidth(320);
		newPlank.setPromptText("Amount of holes in plank");
		
		// Creating page title
		Label pageLabel = new Label("Change holes in plank for an employee");
		pageLabel.setFont(new Font("Comic Sans MS", 15));
		
		// For the buttons
		HBox buttons = new HBox(submitButton, backButton, exitButton);
		HBox hbox1 = new HBox();
		VBox topVbox = new VBox(menubar, pageLabel);
		topVbox.setSpacing(50);

		// Creating the array list and setting equal to the employees names
		ArrayList<String> employeesArr = new ArrayList<String>();
		employeesArr = MongoUsage.getListOfEmployees();
		
		// Creating combobox
		final ComboBox<String> employees = populateComboBox(employeesArr);
		employees.setPromptText("Choose an employee");
		
		// Vbox for stats and buttons
		VBox vboxOne = new VBox(employees, newPlank);
		vboxOne.setSpacing(40);
		VBox vbox = new VBox(vboxOne, buttons);
		
		// Making button actions 
		submitButton.setOnAction(e -> {
			boolean isDone = true;
			
			Alert unansweredFields = new Alert(AlertType.ERROR, "Please fill out all fields before submitting.", 
					ButtonType.CLOSE, ButtonType.OK);		
			
			startPane.setCursor(Cursor.WAIT);
			
			if(employees.getSelectionModel().isEmpty()) {
				unansweredFields.show();
				isDone = false;
				return;
			}
			
			if(newPlank.getText().isEmpty()) {
				unansweredFields.show();
				isDone = false;
				return;
			}
			
			if(isDone) {
				Employee emp = MongoUsage.findEmployee(employees);
		
				int newPlankAmt = Integer.parseInt(newPlank.getText());
				
				Alert confirm = new Alert(AlertType.CONFIRMATION, "Are you sure you want to change " + emp.toString() + "\'s holes in plank from the original amount of " + emp.getPlankHoles() + " to " + newPlankAmt + "? \nThis CANNOT be undone.", ButtonType.YES, ButtonType.CANCEL);
				confirm.showAndWait();
				
				// Getting original amount of measure for logging changes
				String originalAmt = String.valueOf(emp.getPlankHoles());
	
				if(confirm.getResult() == ButtonType.YES) {
					// Removing the old employee
					MongoUsage.removeEmployee(emp);
								
					// Setting the objects new amt of holes in solid
					emp.setPlankHoles(newPlankAmt);
					
					// Inserting the new employee
					MongoUsage.addEmployeeToDB(emp);
					
					// Logging change to DB write file
					LogUtil.writeDBChangeToLog(originalAmt, "holes in plank", emp);
				}
				else {
					Alert cancelled = new Alert(AlertType.WARNING, "Cancelled changing of employee name " + emp.toString() + ".", ButtonType.OK);
					cancelled.showAndWait();
				}
				
				startPane.setBottom(hbox1);
				chooseActivity(startPane);
			}
		});	
		
		backButton.setOnAction(e -> {
			chooseActivity(startPane);
		});
		
		exitButton.setOnAction(e -> {
			System.exit(0);
		});
				
		// Set spacing, fonts, and padding
		buttons.setPadding(new Insets(20));
		buttons.setSpacing(275);
		submitButton.setFont(new Font("Comic Sans MS", 11));
		backButton.setFont(new Font("Comic Sans MS", 11));
		exitButton.setFont(new Font("Comic Sans MS", 11));
		newPlank.setFont(new Font("Comic Sans MS", 18));
		
		// Setting alignment
		BorderPane.setAlignment(hbox1, Pos.CENTER);
		hbox1.setAlignment(Pos.CENTER);
		BorderPane.setAlignment(vbox, Pos.CENTER);
		vbox.setAlignment(Pos.CENTER);
		BorderPane.setAlignment(vboxOne, Pos.CENTER);
		vboxOne.setAlignment(Pos.CENTER);
		topVbox.setAlignment(Pos.CENTER);
		startPane.setCenter(vboxOne);
		startPane.setBottom(vbox);
		startPane.setTop(topVbox);
	}
	
	/**
	 * This method will change the specified employees holes in saw to the new user entered 
	 * holes in saw.
	 * @param startPane - the startpane for javafx
	 */
	public void changeSawCuts(BorderPane startPane) {
		disableCurrPage("Saw");
		
		// Undisabling everything in menubar
		menuAlterEmployees.setDisable(false);
		menuSettings.setDisable(false);
		
		// Clearing and then setting color to the start pane
		startPane.getChildren().clear();
		startPane.setStyle("-fx-background-color: AQUAMARINE");
		
		// Creating circular button variables
		final double CIRCLE_RADIUS = 28.0;
		Circle c = new Circle(CIRCLE_RADIUS);
		
		// Creating buttons
		Button exitButton = new Button("Exit");
		Button submitButton = new Button("Submit");
		Button backButton = new Button("Back");
		
		// Setting submit button as default and fixing to look like the original buttons
		submitButton.setDefaultButton(true);
		submitButton.setStyle("-fx-base: #ececec");
		
		// Creating buttons sizes and shapes
		exitButton.setShape(c);
		exitButton.setPrefSize(2.0 * CIRCLE_RADIUS, 2.0 * CIRCLE_RADIUS);
		submitButton.setShape(c);
		submitButton.setPrefSize(2.0 * CIRCLE_RADIUS, 2.0 * CIRCLE_RADIUS);
		backButton.setShape(c);
		backButton.setPrefSize(2.0 * CIRCLE_RADIUS, 2.0 * CIRCLE_RADIUS);
		
		// New name entry for employee
		TextField newSaw = new TextField();
		newSaw.setMaxWidth(320);
		newSaw.setPromptText("Amount of saw cuts");
		
		// Creating page title
		Label pageLabel = new Label("Change saw cuts for an employee");
		pageLabel.setFont(new Font("Comic Sans MS", 15));
		
		// For the buttons
		HBox buttons = new HBox(submitButton, backButton, exitButton);
		HBox hbox1 = new HBox();
		VBox topVbox = new VBox(menubar, pageLabel);
		topVbox.setSpacing(50);

		// Creating the array list and setting equal to the employees names
		ArrayList<String> employeesArr = new ArrayList<String>();
		employeesArr = MongoUsage.getListOfEmployees();
		
		// Creating combobox
		final ComboBox<String> employees = populateComboBox(employeesArr);
		employees.setPromptText("Choose an employee");
		
		// Vbox for stats and buttons
		VBox vboxOne = new VBox(employees, newSaw);
		vboxOne.setSpacing(40);
		VBox vbox = new VBox(vboxOne, buttons);
		
		// Making button actions 
		submitButton.setOnAction(e -> {
			boolean isDone = true;
			
			Alert unansweredFields = new Alert(AlertType.ERROR, "Please fill out all fields before submitting.", 
					ButtonType.CLOSE, ButtonType.OK);		
			
			startPane.setCursor(Cursor.WAIT);
			
			if(employees.getSelectionModel().isEmpty()) {
				unansweredFields.show();
				isDone = false;
				return;
			}
			
			if(newSaw.getText().isEmpty()) {
				unansweredFields.show();
				isDone = false;
				return;
			}
			
			if(isDone) {
				Employee emp = MongoUsage.findEmployee(employees);
		
				int newSawAmt = Integer.parseInt(newSaw.getText());
				
				Alert confirm = new Alert(AlertType.CONFIRMATION, "Are you sure you want to change " + emp.toString() + "\'s saw cuts from the original amount of " + emp.getSawHoles() + " to " + newSawAmt + "? \nThis CANNOT be undone.", ButtonType.YES, ButtonType.CANCEL);
				confirm.showAndWait();
	
				// Getting original amount of measure for logging changes
				String originalAmt = String.valueOf(emp.getSawHoles());
				
				if(confirm.getResult() == ButtonType.YES) {
					// Removing the old employee
					MongoUsage.removeEmployee(emp);
								
					// Setting the objects new amt of holes in solid
					emp.setSawHoles(newSawAmt);
					
					// Inserting the new employee
					MongoUsage.addEmployeeToDB(emp);
					
					// Logging change to DB write file
					LogUtil.writeDBChangeToLog(originalAmt, "saw cuts", emp);
				}
				else {
					Alert cancelled = new Alert(AlertType.WARNING, "Cancelled changing of employee name " + emp.toString() + ".", ButtonType.OK);
					cancelled.showAndWait();
				}
				
				startPane.setBottom(hbox1);
				chooseActivity(startPane);
			}
		});	
		
		backButton.setOnAction(e -> {
			chooseActivity(startPane);
		});
		
		exitButton.setOnAction(e -> {
			System.exit(0);
		});
				
		// Set spacing, fonts, and padding
		buttons.setPadding(new Insets(20));
		buttons.setSpacing(275);
		submitButton.setFont(new Font("Comic Sans MS", 11));
		backButton.setFont(new Font("Comic Sans MS", 11));
		exitButton.setFont(new Font("Comic Sans MS", 11));
		newSaw.setFont(new Font("Comic Sans MS", 18));
		
		// Setting alignment
		BorderPane.setAlignment(hbox1, Pos.CENTER);
		hbox1.setAlignment(Pos.CENTER);
		BorderPane.setAlignment(vbox, Pos.CENTER);
		vbox.setAlignment(Pos.CENTER);
		BorderPane.setAlignment(vboxOne, Pos.CENTER);
		vboxOne.setAlignment(Pos.CENTER);
		topVbox.setAlignment(Pos.CENTER);
		startPane.setCenter(vboxOne);
		startPane.setBottom(vbox);
		startPane.setTop(topVbox);
	}
	
	/**
	 * This method will change the specified employees call outs to the new user entered call outs.
	 * @param startPane - the startpane for javafx
	 */
	public void changeCallOuts(BorderPane startPane) {
		disableCurrPage("Call");
		
		// Undisabling everything in menubar
		menuAlterEmployees.setDisable(false);
		menuSettings.setDisable(false);
		
		// Clearing and then setting color to the start pane
		startPane.getChildren().clear();
		startPane.setStyle("-fx-background-color: AQUAMARINE");
		
		// Creating circular button variables
		final double CIRCLE_RADIUS = 28.0;
		Circle c = new Circle(CIRCLE_RADIUS);
		
		// Creating buttons
		Button exitButton = new Button("Exit");
		Button submitButton = new Button("Submit");
		Button backButton = new Button("Back");		
		
		// Setting submit button as default and fixing to look like the original buttons
		submitButton.setDefaultButton(true);
		submitButton.setStyle("-fx-base: #ececec");
		
		// Creating buttons sizes and shapes
		exitButton.setShape(c);
		exitButton.setPrefSize(2.0 * CIRCLE_RADIUS, 2.0 * CIRCLE_RADIUS);
		submitButton.setShape(c);
		submitButton.setPrefSize(2.0 * CIRCLE_RADIUS, 2.0 * CIRCLE_RADIUS);
		backButton.setShape(c);
		backButton.setPrefSize(2.0 * CIRCLE_RADIUS, 2.0 * CIRCLE_RADIUS);

		// New name entry for employee
		TextField newCallOuts = new TextField();
		newCallOuts.setMaxWidth(320);
		newCallOuts.setPromptText("Amount of call outs");
		
		// Creating page title
		Label pageLabel = new Label("Change call outs for an employee");
		pageLabel.setFont(new Font("Comic Sans MS", 15));
		
		// For the buttons
		HBox buttons = new HBox(submitButton, backButton, exitButton);
		HBox hbox1 = new HBox();
		VBox topVbox = new VBox(menubar, pageLabel);
		topVbox.setSpacing(50);

		// Creating the array list and setting equal to the employees names
		ArrayList<String> employeesArr = new ArrayList<String>();
		employeesArr = MongoUsage.getListOfEmployees();
		
		// Creating combobox
		final ComboBox<String> employees = populateComboBox(employeesArr);
		employees.setPromptText("Choose an employee");
		
		// Vbox for stats and buttons
		VBox vboxOne = new VBox(employees, newCallOuts);
		vboxOne.setSpacing(40);
		VBox vbox = new VBox(vboxOne, buttons);
		
		// Making button actions 
		submitButton.setOnAction(e -> {
			boolean isDone = true;
			
			Alert unansweredFields = new Alert(AlertType.ERROR, "Please fill out all fields before submitting.", 
					ButtonType.CLOSE, ButtonType.OK);		
			
			startPane.setCursor(Cursor.WAIT);
			
			if(employees.getSelectionModel().isEmpty()) {
				unansweredFields.show();
				isDone = false;
				return;
			}
			
			if(newCallOuts.getText().isEmpty()) {
				unansweredFields.show();
				isDone = false;
				return;
			}
			
			if(isDone) {
				Employee emp = MongoUsage.findEmployee(employees);
		
				int newCallOutsAmt = Integer.parseInt(newCallOuts.getText());
				
				Alert confirm = new Alert(AlertType.CONFIRMATION, "Are you sure you want to change " + emp.toString() + "\'s call outs from the original amount of " + emp.getCallOuts() + " to " + newCallOutsAmt + "? \nThis CANNOT be undone.", ButtonType.YES, ButtonType.CANCEL);
				confirm.showAndWait();
				
				// Getting original amount of measure for logging changes
				String originalAmt = String.valueOf(emp.getCallOuts());
	
				if(confirm.getResult() == ButtonType.YES) {
					// Removing the old employee
					MongoUsage.removeEmployee(emp);
								
					// Setting the objects new amt of holes in solid
					emp.setCallOuts(newCallOutsAmt);
					
					// Inserting the new employee
					MongoUsage.addEmployeeToDB(emp);
					
					// Logging change to DB write file
					LogUtil.writeDBChangeToLog(originalAmt, "call outs", emp);
				}
				else {
					Alert cancelled = new Alert(AlertType.WARNING, "Cancelled changing of employee name " + emp.toString() + ".", ButtonType.OK);
					cancelled.showAndWait();
				}
				
				startPane.setBottom(hbox1);
				chooseActivity(startPane);
			}
		});	
		
		backButton.setOnAction(e -> {
			chooseActivity(startPane);
		});
		
		exitButton.setOnAction(e -> {
			System.exit(0);
		});
				
		// Set spacing, fonts, and padding
		buttons.setPadding(new Insets(20));
		buttons.setSpacing(275);
		submitButton.setFont(new Font("Comic Sans MS", 11));
		backButton.setFont(new Font("Comic Sans MS", 11));
		exitButton.setFont(new Font("Comic Sans MS", 11));
		newCallOuts.setFont(new Font("Comic Sans MS", 18));
		
		// Setting alignment
		BorderPane.setAlignment(hbox1, Pos.CENTER);
		hbox1.setAlignment(Pos.CENTER);
		BorderPane.setAlignment(vbox, Pos.CENTER);
		vbox.setAlignment(Pos.CENTER);
		BorderPane.setAlignment(vboxOne, Pos.CENTER);
		vboxOne.setAlignment(Pos.CENTER);
		topVbox.setAlignment(Pos.CENTER);
		startPane.setCenter(vboxOne);
		startPane.setBottom(vbox);
		startPane.setTop(topVbox);
	}
	
	/**
	 * This method will allow the admin of the system to change their username.
	 * @param startPane - the startpane for javafx
	 */
	public void changeAdminUsername(BorderPane startPane) {
		disableCurrPage("Username");
		
		// Undisabling everything in menubar
		menuAlterEmployees.setDisable(false);
		menuSettings.setDisable(false);
		
		// Clearing and then setting color to the start pane
		startPane.getChildren().clear();
		startPane.setStyle("-fx-background-color: AQUAMARINE");
		
		// Creating circular button variables
		final double CIRCLE_RADIUS = 28.0;
		Circle c = new Circle(CIRCLE_RADIUS);
		
		// Creating buttons
		Button exitButton = new Button("Exit");
		Button submitButton = new Button("Submit");
		Button backButton = new Button("Back");		
		
		// Setting submit button as default and fixing to look like the original buttons
		submitButton.setDefaultButton(true);
		submitButton.setStyle("-fx-base: #ececec");
		
		// Creating buttons sizes and shapes
		exitButton.setShape(c);
		exitButton.setPrefSize(2.0 * CIRCLE_RADIUS, 2.0 * CIRCLE_RADIUS);
		submitButton.setShape(c);
		submitButton.setPrefSize(2.0 * CIRCLE_RADIUS, 2.0 * CIRCLE_RADIUS);
		backButton.setShape(c);
		backButton.setPrefSize(2.0 * CIRCLE_RADIUS, 2.0 * CIRCLE_RADIUS);
		
		// New username
		TextField newUsername = new TextField();
		newUsername.setMaxWidth(320);
		newUsername.setPromptText("Enter a new username");
		
		// Creating page title
		Label pageLabel = new Label("Change admin username");
		pageLabel.setFont(new Font("Comic Sans MS", 15));
		
		// For the buttons
		HBox buttons = new HBox(submitButton, backButton, exitButton);
		HBox hbox1 = new HBox();
		VBox topVbox = new VBox(menubar, pageLabel);
		topVbox.setSpacing(50);
		
		// Creating the array list and setting equal to the admin names
		ArrayList<String> adminArr = new ArrayList<String>();
		adminArr = MongoUsage.getListOfAdminUsernames();
		
		// Creating combobox
		final ComboBox<String> admins = populateComboBox(adminArr);
		admins.setPromptText("Choose an admin");
		
		// Vbox for stats and buttons
		VBox vboxOne = new VBox(admins, newUsername);
		vboxOne.setSpacing(40);
		VBox vbox = new VBox(vboxOne, buttons);
		
		// Making button actions 
		submitButton.setOnAction(e -> {
			boolean isDone = true;
			
			Alert unansweredFields = new Alert(AlertType.ERROR, "Please fill out all fields before submitting.", 
					ButtonType.CLOSE, ButtonType.OK);		
			
			startPane.setCursor(Cursor.WAIT);
			
			if(admins.getSelectionModel().isEmpty()) {
				unansweredFields.show();
				isDone = false;
				return;
			}
			
			if(newUsername.getText().isEmpty()) {
				unansweredFields.show();
				isDone = false;
				return;
			}
			
			if(isDone) {
				Admin admin = MongoUsage.findAdmin(admins.getValue());
					
				Alert confirm = new Alert(AlertType.CONFIRMATION, "Are you sure you want to change " + admin.getUsername() + "\'s username from the original username " + admin.getUsername() + " to " + newUsername.getText() + "? \nThis CANNOT be undone.", ButtonType.YES, ButtonType.CANCEL);
				confirm.showAndWait();
				
				// Getting original amount of measure for logging changes
				String originalName = String.valueOf(admin.getUsername());
	
				if(confirm.getResult() == ButtonType.YES) {
					System.out.println(admin.getUsername());
	
					// Removing the old admin
					MongoUsage.removeAdmin(admin);
								
					// Setting the objects new username
					admin.setUsername(newUsername.getText());
					
					// Inserting the new admin
					MongoUsage.addAdminToDB(admin);
					
					// Logging change to DB write file
					LogUtil.writeDBChangeToLog(originalName, "username", admin);
				}
				else {
					Alert cancelled = new Alert(AlertType.WARNING, "Cancelled changing of admin username " + admin.getUsername() + ".", ButtonType.OK);
					cancelled.showAndWait();
				}
				
				startPane.setBottom(hbox1);
				chooseActivity(startPane);
			}
		});	
		
		backButton.setOnAction(e -> {
			chooseActivity(startPane);
		});
		
		exitButton.setOnAction(e -> {
			System.exit(0);
		});
				
		// Set spacing, fonts, and padding
		buttons.setPadding(new Insets(20));
		buttons.setSpacing(275);
		submitButton.setFont(new Font("Comic Sans MS", 11));
		backButton.setFont(new Font("Comic Sans MS", 11));
		exitButton.setFont(new Font("Comic Sans MS", 11));
		newUsername.setFont(new Font("Comic Sans MS", 18));
		
		// Setting alignment
		BorderPane.setAlignment(hbox1, Pos.CENTER);
		hbox1.setAlignment(Pos.CENTER);
		BorderPane.setAlignment(vbox, Pos.CENTER);
		vbox.setAlignment(Pos.CENTER);
		BorderPane.setAlignment(vboxOne, Pos.CENTER);
		vboxOne.setAlignment(Pos.CENTER);
		topVbox.setAlignment(Pos.CENTER);
		startPane.setCenter(vboxOne);
		startPane.setBottom(vbox);
		startPane.setTop(topVbox);
	}
	
	/**
	 * This method will allow the admin of the system to change their password.
	 * @param startPane - the startpane for javafx
	 */
	public void changeAdminPassword(BorderPane startPane) {
		disableCurrPage("Password");
		
		// Undisabling everything in menubar
		menuAlterEmployees.setDisable(false);
		menuSettings.setDisable(false);
		
		// Clearing and then setting color to the start pane
		startPane.getChildren().clear();
		startPane.setStyle("-fx-background-color: AQUAMARINE");
		
		// Creating circular button variables
		final double CIRCLE_RADIUS = 28.0;
		Circle c = new Circle(CIRCLE_RADIUS);
		
		// Creating buttons
		Button exitButton = new Button("Exit");
		Button submitButton = new Button("Submit");
		Button backButton = new Button("Back");	
		
		// Setting submit button as default and fixing to look like the original buttons
		submitButton.setDefaultButton(true);
		submitButton.setStyle("-fx-base: #ececec");
		
		// Creating buttons sizes and shapes
		exitButton.setShape(c);
		exitButton.setPrefSize(2.0 * CIRCLE_RADIUS, 2.0 * CIRCLE_RADIUS);
		submitButton.setShape(c);
		submitButton.setPrefSize(2.0 * CIRCLE_RADIUS, 2.0 * CIRCLE_RADIUS);
		backButton.setShape(c);
		backButton.setPrefSize(2.0 * CIRCLE_RADIUS, 2.0 * CIRCLE_RADIUS);

		// New password entry for admin
		PasswordField newPassword = new PasswordField();
		PasswordField enteredAgain = new PasswordField();
		newPassword.setMaxWidth(320);
		newPassword.setPromptText("Enter a new password");
		enteredAgain.setMaxWidth(320);
		enteredAgain.setPromptText("Enter the password again");
		
		// Creating page title
		Label pageLabel = new Label("Change admin password");
		pageLabel.setFont(new Font("Comic Sans MS", 15));
		
		// For the buttons
		HBox buttons = new HBox(submitButton, backButton, exitButton);
		HBox hbox1 = new HBox();
		VBox topVbox = new VBox(menubar);
		topVbox.setSpacing(50);
		
		// Creating the array list and setting equal to the admin names
		ArrayList<String> adminArr = new ArrayList<String>();
		adminArr = MongoUsage.getListOfAdminUsernames();
		
		// Creating combobox
		final ComboBox<String> admins = populateComboBox(adminArr);
		admins.setPromptText("Choose an admin");
		
		// Vbox for stats and buttons
		VBox vboxOne = new VBox(admins, newPassword, enteredAgain);
		vboxOne.setSpacing(40);
		VBox vbox = new VBox(vboxOne, buttons);
		
		// Making button actions 
		submitButton.setOnAction(e -> {
			boolean isDone = true;
			
			Alert unansweredFields = new Alert(AlertType.ERROR, "Please fill out all fields before submitting.", 
					ButtonType.CLOSE, ButtonType.OK);		
			
			startPane.setCursor(Cursor.WAIT);
			
			if(admins.getSelectionModel().isEmpty()) {
				unansweredFields.show();
				isDone = false;
				return;
			}
			
			if(newPassword.getText().isEmpty()) {
				unansweredFields.show();
				isDone = false;
				return;
			}
			
			if(enteredAgain.getText().isEmpty()) {
				unansweredFields.show();
				isDone = false;
				return;
			}
			
			if(isDone) {
				Admin admin = MongoUsage.findAdmin(admins.getValue());				
				
				Alert confirm = new Alert(AlertType.CONFIRMATION, "Are you sure you want to change " + admin.getUsername() + "\'s password?", ButtonType.YES, ButtonType.CANCEL);
				confirm.showAndWait();
				
				// Getting original amount of measure for logging changes
				String originalPass = String.valueOf(admin.getPassword());
				
				if(confirm.getResult() == ButtonType.YES) {
					String firstPass = newPassword.getText();
					String secondPass = enteredAgain.getText();
					
					if(confirmPasswords(firstPass, secondPass)) {
						// Removing the old admin
						MongoUsage.removeAdmin(admin);
									
						// Setting the objects new password
						admin.setPassword(newPassword.getText());
						
						// Inserting the new admin
						MongoUsage.addAdminToDB(admin);
						
						// Logging change to DB write file
						LogUtil.writeDBChangeToLog(originalPass, "password", admin);					
					}
					else {
						Alert passNotMatching = new Alert(AlertType.WARNING, "Your passwords did not match. Please try again.", ButtonType.OK);
						passNotMatching.show();
					}
				}
				else {
					Alert cancelled = new Alert(AlertType.WARNING, "Cancelled changing of admin password " + admin.getPassword() + ".", ButtonType.OK);
					cancelled.show();
				}
				
				Alert success = new Alert(AlertType.CONFIRMATION, "Changed the password.", ButtonType.OK);
				success.show();
				startPane.setBottom(hbox1);
				chooseActivity(startPane);
			}
		});	
		
		backButton.setOnAction(e -> {
			chooseActivity(startPane);
		});
		
		exitButton.setOnAction(e -> {
			System.exit(0);
		});
				
		// Set spacing, fonts, and padding
		buttons.setPadding(new Insets(20));
		buttons.setSpacing(275);
		submitButton.setFont(new Font("Comic Sans MS", 11));
		backButton.setFont(new Font("Comic Sans MS", 11));
		exitButton.setFont(new Font("Comic Sans MS", 11));
		newPassword.setFont(new Font("Comic Sans MS", 18));
		enteredAgain.setFont(new Font("Comic Sans MS", 18));
		
		// Setting alignment
		BorderPane.setAlignment(hbox1, Pos.CENTER);
		hbox1.setAlignment(Pos.CENTER);
		BorderPane.setAlignment(vbox, Pos.CENTER);
		vbox.setAlignment(Pos.CENTER);
		BorderPane.setAlignment(vboxOne, Pos.CENTER);
		vboxOne.setAlignment(Pos.CENTER);
		topVbox.setAlignment(Pos.CENTER);
		startPane.setCenter(vboxOne);
		startPane.setBottom(vbox);
		startPane.setTop(topVbox);
	}
	
	/**
	 * This method will check the two user entered passwords, and return whether they were the same
	 * or not.
	 * @param first - the password originally entered
	 * @param second - the password entered again
	 * @return whether or not the passwords are the same
	 */
	public boolean confirmPasswords(String first, String second) {
		boolean areSame = false;
		if(first.equals(second)) {
			areSame = true;
		}
		return areSame;
	}
	
	/**
	 * This method will iterate over an array with the names, and add them to a combobox
	 * one by one.
	 * @param array - an array of the employee names
	 * @return populated ComboBox with employee names
	 */
	public ComboBox<String> populateComboBox(ArrayList<String> array) {
		ComboBox<String> comboBox = new ComboBox<String>();
		
		// Sorting the array alphabetically
		Collections.sort(array);
		
		// Iterating through the employees array and adding each name to the combobox
		for(int i = 0; i < array.size(); i++) {
			comboBox.getItems().add(array.get(i));
		}
		return comboBox;
	}
	
	/**
	 * This method will switch through the string param and base that off which menuitem
	 * to disable within the menubar of the current page.
	 * @param currPage - hardcoded value of the current page
	 */
	public void disableCurrPage(String currPage) {
		switch(currPage) {
			case "Delete":
				deleteEmployee.setDisable(true);
				changeSawCut.setDisable(false);
				changeCallOuts.setDisable(false);
				changeHoleInPlank.setDisable(false);
				changeHoleInSolid.setDisable(false);
				changePassword.setDisable(false);
				changeUsername.setDisable(false);
				changeEmpName.setDisable(false);
				break;
			case "Solid":
				changeHoleInSolid.setDisable(true);
				changeSawCut.setDisable(false);
				changeCallOuts.setDisable(false);
				changeHoleInPlank.setDisable(false);
				deleteEmployee.setDisable(false);
				changePassword.setDisable(false);
				changeUsername.setDisable(false);
				changeEmpName.setDisable(false);
				break;
			case "Plank":
				changeHoleInPlank.setDisable(true);
				changeSawCut.setDisable(false);
				changeCallOuts.setDisable(false);
				changeHoleInSolid.setDisable(false);
				deleteEmployee.setDisable(false);
				changePassword.setDisable(false);
				changeUsername.setDisable(false);
				changeEmpName.setDisable(false);
				break;
			case "Saw":
				changeSawCut.setDisable(true);
				changeCallOuts.setDisable(false);
				changeHoleInPlank.setDisable(false);
				changeHoleInSolid.setDisable(false);
				deleteEmployee.setDisable(false);
				changePassword.setDisable(false);
				changeUsername.setDisable(false);
				changeEmpName.setDisable(false);
				break;
			case "Call":
				changeCallOuts.setDisable(true);
				changeSawCut.setDisable(false);
				changeHoleInPlank.setDisable(false);
				changeHoleInSolid.setDisable(false);
				deleteEmployee.setDisable(false);
				changePassword.setDisable(false);
				changeUsername.setDisable(false);
				changeEmpName.setDisable(false);
				break;
			case "Username":
				changeUsername.setDisable(true);
				changeCallOuts.setDisable(false);
				changeSawCut.setDisable(false);
				changeHoleInPlank.setDisable(false);
				changeHoleInSolid.setDisable(false);
				deleteEmployee.setDisable(false);
				changePassword.setDisable(false);	
				changeEmpName.setDisable(false);
				break;
			case "Password":
				changePassword.setDisable(true);
				changeCallOuts.setDisable(false);
				changeSawCut.setDisable(false);
				changeHoleInPlank.setDisable(false);
				changeHoleInSolid.setDisable(false);
				deleteEmployee.setDisable(false);
				changeUsername.setDisable(false);
				changeEmpName.setDisable(false);
				break;
			case "Emp Name":
				changeEmpName.setDisable(true);
				changeCallOuts.setDisable(false);
				changeSawCut.setDisable(false);
				changeHoleInPlank.setDisable(false);
				changeHoleInSolid.setDisable(false);
				deleteEmployee.setDisable(false);
				changeUsername.setDisable(false);
				changePassword.setDisable(false);
			default:
				changeCallOuts.setDisable(false);
				changeSawCut.setDisable(false);
				changeHoleInPlank.setDisable(false);
				changeHoleInSolid.setDisable(false);
				deleteEmployee.setDisable(false);
				changePassword.setDisable(false);
				changeUsername.setDisable(false);
				changeEmpName.setDisable(false);
				break;
		}
	}
}
