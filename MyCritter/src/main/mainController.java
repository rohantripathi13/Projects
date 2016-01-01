/*Critter World by-
 * <Rohan Tripathi>
 */
 
package main;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class mainController implements Initializable {

	@FXML
	private Label seedlabel; //List of labels created using Scene builder
	@FXML
	private Label makelabel;
	@FXML
	private Label crittername;
	@FXML
	private Label critternum;
	@FXML
	public static Label label1;
	@FXML
	public static Label label2;
	@FXML
	public static Label label3;
	@FXML
	public static Label label4;
	@FXML
	public static Label label5;
	@FXML
	public static Label label6;


	@FXML
	private TextField seedTxt; //List of text fields created
	@FXML
	private TextField nameTxt;
	@FXML
	private TextField numTxt;
	@FXML
	private TextField stepTxt;
	@FXML
	private static Text actionTarget;

	@FXML
	private static Text text1, text2, text3, text4, text5, text6; //List of Text blocks
	@FXML
	public static List<String> actionList = new ArrayList<String>(); //ArrayList to store the text block names as strings
	@FXML
	public static List<String> labelList= new ArrayList<String>(); // ArrayList to store the label names as strings 

	@FXML
	private static GridPane grid; // ID for Grid Pane


	@FXML
	private static Button seedbtn; // List of Buttons created
	@FXML
	private static Button makebtn;
	@FXML
	private static Button dispbtn;
	@FXML
	private static Button startbtn;
	@FXML
	private static Button stepbtn;
	@FXML
	private static Button stepbtn2;
	@FXML
	private static Button stopbtn;
	@FXML
	private static Button quitbtn;

	@FXML
	private static Pane canvas; // Reference ID for pane on scene 2

	public static Scene scene01; //Static references to the scenes
	public static Scene scene02;
	public static boolean exec = true;

	public void seedPressed(ActionEvent event) { // Method to execute when seed is pressed
		Node nodeToFind1 = scene02.lookup("#grid"); //Find the grid pane and set the background to orange
		Pane found1 = ((Pane) nodeToFind1);
		found1.setStyle("-fx-background-color: orange;");	
		int seedNum;
	   	 String seedStr=seedTxt.getText(); //get the input by user as a string
	   	 try{
	   		 seedNum=Integer.parseInt(seedStr);
	   	 }
	   	 catch(NumberFormatException e){    //Default seed is 1
	   		 seedNum = 1;
	   	 }

		Critter.setSeed(seedNum); // Call Critter's method setSeed
	}

	public void stepPressed(ActionEvent event) throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, InvalidCritterException { // Method to execute when step is pressed
		Node nodeToFind1 = scene02.lookup("#grid"); // Find the grid and set the background color to Orange
		Pane found1 = ((Pane) nodeToFind1);
		found1.setStyle("-fx-background-color: orange;");
		Node nodeToFind = mainController.scene02.lookup("#canvas"); // Find the Pane and clear it
		Pane found = ((Pane) nodeToFind);
		found.getChildren().clear();
		String stepStr = stepTxt.getText();    //Map requires a step text box !!!!!
		int stepNum;
		
	   	 try{
	   		 stepNum=Integer.parseInt(stepStr);
	   		 if(stepNum < 0){    //If step entered is negative, do not step
	   			 throw new NumberFormatException();
	   		 }
	   	 }
	   	 catch(NumberFormatException e){    //Default step is 1 unless text entered was negative or invalid
	   		 if(stepStr.equals("")){    
	   			 stepNum = 1;
	   		 }
	   		 else{
	   			 stepNum = 0;
	   		 }
	   	 }
	   	while(stepNum > 0){
	   		 Critter.worldTimeStep();
	   		 stepNum--;
	   	 }
	   	 found.setStyle("-fx-background-color: white;");   	// Find the pane and set the background color to white
	   	 Critter.displayOnPane();
	   	 try {
	   		 mainController.statsPressed(event); //Display stats for the different categories of the critters
	   	 } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
	   			 | InvocationTargetException | InvalidCritterException e) {
	   		 e.printStackTrace();
	   	 }
	}

	public void stepPressed2(ActionEvent event) throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, InvalidCritterException {
		Node nodeToFind1 = scene02.lookup("#grid"); //Look up the Grid Pane and set the background to Orange
		Pane found1 = ((Pane) nodeToFind1);
		found1.setStyle("-fx-background-color: orange;");	
		Critter.worldTimeStep(); //Call world time step
		Node nodeToFind = mainController.scene02.lookup("#canvas");
		Pane found = ((Pane) nodeToFind);
		found.getChildren().clear(); //Look up canvas and clear it
		found.setStyle("-fx-background-color: white;"); //Set the background white
		Critter.displayOnPane(); //Display the Critters on the screen
		mainController.statsPressed(event); // Display the stats for the Critters created
	}

	public static void statsPressed(ActionEvent event) throws NoSuchMethodException, SecurityException, InvalidCritterException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {// Method to display current stats of the critters
		int i = 0;
		while (i<Critter.scnames.size()) { // Loop through the list of label names
			try {
				String str = Critter.scnames.get(i);
				Class<?> myCrit01 = Class.forName(str); // Create instance of the subclass based on the string input
				List<Critter> arg01 = Critter.getInstances(str);
				Node nodeToFind = scene02.lookup(labelList.get(i)); // Look up the label with particular ID and set its text
				Label found = ((Label)nodeToFind);
				found.setText(str.substring(5));
				found.setTextFill(Color.BLACK); 
				Method method01 = myCrit01.getMethod("runStats", List.class); // Invoke run stats in Critter class
				Node nodeToFind1 = scene02.lookup(actionList.get(i));
				Text found1 = ((Text)nodeToFind1);
				found1.setFill(Color.FIREBRICK);//Look up text fields and set their color to FIREBRICK
				found1.setText((String) method01.invoke(null, arg01));
			} catch (Exception e) {
				e.printStackTrace();
			}
			i++;
		}
	}
	
	public void start2(ActionEvent event) { //Method to continuously execute time step and display the new Critter positions
		while (exec) {
			Node nodeToFind = mainController.scene02.lookup("#canvas");//Find canvas and continue to plot the objects
			Pane found = ((Pane) nodeToFind);
			found.getChildren().clear();
			found.setStyle("-fx-background-color: white;");
			Critter.displayOnPane();
		    try {
				mainController.statsPressed(event);
			} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | InvalidCritterException e) {
				e.printStackTrace();
			}
		}
	}

	public void stop2(ActionEvent event){ // Stop the continuous plotting by setting exec to false 
		exec = false;
	    try {
			start2(event);
		} catch (SecurityException | IllegalArgumentException e) {
			e.printStackTrace();
		}
	}
	
	public void makeCrit(ActionEvent event) { // Method to create Critters 
		Node nodeToFind1 = scene02.lookup("#grid");//Find the grid and set its background to orange
		Pane found1 = ((Pane) nodeToFind1);
		found1.setStyle("-fx-background-color: orange;");
		Node nodeToFind = scene02.lookup("#canvas"); //Find the canvas Pane
		Pane found = ((Pane) nodeToFind);
		found.setStyle("-fx-background-color: white;"); //Set background to white
		Node nodeToFind3 = scene01.lookup("#actionTarget");//Look up the text field
		Text found3 = ((Text) nodeToFind3);
		int makeCount;
	   	 String name = nameTxt.getText(); //Get the user inputs in the form of Strings
	   	 String numString = numTxt.getText();
	   	 try{
	   		 makeCount = Integer.parseInt(numString);    //Receive Integer String
	   	 }
	   	 catch(NumberFormatException e){    //Non-Integers will not be processed, but "" will be default 1
	   		 if(numString.equals("")){    
	   			 makeCount = 1;
	   		 }
	   		 else{
	   			 makeCount = -1;
	   		 }
	   	 }
	   	 while(makeCount > 0) {
	   		 try {
	   			 Critter.makeCritter("main." + name); // Call the make Critter method using name as the String
	   			 makeCount--;
	   		 } catch (InvalidCritterException e) {
	   			 makeCount = -1;
	   		 }
	   	 }
	   	 if(makeCount < 0){
	   		 found3.setFill(Color.FIREBRICK);
	   		 found3.setText("Invalid command");
	   	 }
	   	 else{
	   		 found3.setFill(Color.FIREBRICK);
	   		 found3.setText("" + numString + " " + name + " created and added");
	   	 }
	   	 Critter.displayOnPane(); //Display on the pane
		try {
			mainController.statsPressed(event); //Display the stats for the different categories of the Critter
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InvalidCritterException e) {
			e.printStackTrace();
		}
	}
	
	public void quit2(ActionEvent event){ //Method to close both the windows
    	Stage stage1 = (Stage) scene01.getWindow();
    	Stage stage2 = (Stage) scene02.getWindow();
    	stage1.close();
    	stage2.close();
     }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	}
}
