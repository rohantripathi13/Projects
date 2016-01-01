/* Mastermind PROJECT <Main.java>
 * by  <Rohan Tripathi>
 */
package project6;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Main extends Application {

	public static Group root;
	public static Scene scene;
	public static GridPane gridpane;
	public static Stage stage;
	public static Stage stageFin;
	static Scanner input;
	public static Stage welcome, setSpecs;
	public Button bt1;	//Human vs. Computer Button
	public Button bt2;	//Computer vs. Human Button
	public static Button bt3; //Close Button for End Window (Win Version)
	static Scene scene01;
	private static char[] pegs;	//pegs guess combination
	private char[] colors = { 'B', 'G', 'O', 'Y', 'R', 'P' };
	public static char[] userPegs;	//User input peg combination
	private static int attempts;	//Number of attempts allowed
	Hashtable<Integer, Boolean> htb = new Hashtable<Integer, Boolean>();	//Used to calculate black pegs
	Hashtable<Integer, Boolean> htw = new Hashtable<Integer, Boolean>();	//Used to calculate white pegs
	private static Set<String> posout = new HashSet<String>();	//Set of all possible string combinations for peg
	static Random rand = new Random();
	static ArrayList<Game> history = new ArrayList<Game>();	//Holds a history of all string combinations used
	private static Group root3;
	private static Button bt4;	//Close Button for End Window (Lose Version)
	private static Group root4;
	public static Stage lost;
	public int b, w;	//Black pegs and white pegs, respectively
	private char[] compChoice;	//Computer's peg guess
	private int size = 4, num = 12;	//Size of pegs, number of guesses. Initialized to default values
	private int guessnum = 1, row, col;	//Guess label, row and column of GUI Images
	private static ArrayList<String> guessList;	//List of all peg guesses stored as Strings
	private static HashMap<Integer, Integer> totalPegsMap;	//Mapping of Peg combinations and total result values
	private boolean gameOvr = false, loop3 = false;	//gameOvr indicates whether to End the Game, loop3 is a mailbox loop

	/**
	 * The actual main method of our program. First brings up a window to select between Human vs. Computer
	 * gameplay or Computer vs. Human gameplay. Gameplay of the following options then vary depending
	 * on the option selected. Human vs. Computer gameplay involves a person playing aganist the computer
	 * to solve the peg combination. Computer vs. Human gameplay involves the computer trying to solve
	 * the person's peg combination while receiving feedback from the person.
	 * 
	 * @author Rohan T. and Bryan V.
	 * 
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			stage = primaryStage;	//Setting up GUI stages and scenes
			root = new Group();
			ScrollPane rootPane = new ScrollPane();
			gridpane = new GridPane();
			gridpane.setPadding(new Insets(5, 20, 0, 0));
			gridpane.setHgap(30);
			gridpane.setVgap(20);
			Label head = new Label("Pegs' Statistics");
			head.setFont(Font.font("Times New Roman", FontWeight.BOLD, 12));
			gridpane.add(head, 1, 0);
			rootPane.setHbarPolicy(ScrollBarPolicy.ALWAYS);
			rootPane.setVbarPolicy(ScrollBarPolicy.ALWAYS);
			rootPane.setContent(gridpane);
			gridpane.setAlignment(Pos.TOP_LEFT);
			gridpane.setPrefWidth(700.0);
			scene = new Scene(rootPane, 700, 700);
			stage.setScene(scene);
			stage.setTitle("Mastermind Game");

			Group root3 = new Group();
			setSpecs = new Stage();
			setSpecs.setTitle(" Choose your specs");
			Scene scene3 = new Scene(root3, 600, 400, Color.GREY);
			setSpecs.setScene(scene3);
			GridPane gd = new GridPane();	//Gridpane for Set Specifications Window
			gd.setVgap(20);
			gd.setHgap(20);
			Label l3 = new Label(" Please select the number of attempts. [Default=12] ");
			gd.add(l3, 3, 2);
			TextField txt1 = new TextField();
			gd.add(txt1, 4, 2);
			Button pd = new Button(" Done ");	//Done button for attempts
			gd.add(pd, 4, 3);
			Label l4 = new Label(" Please select the length of the guesses. [Default=4]");
			gd.add(l4, 3, 5);
			TextField txt2 = new TextField();
			gd.add(txt2, 4, 5);
			Button el = new Button(" Done ");	//Done button for guesses
			gd.add(el, 4, 6);
			Button sg = new Button(" Start the Game ");	//Start button of setSpecs window
			gd.add(sg, 4, 8);
			Label la = new Label();
			gd.add(la, 3, 1);
			root3.getChildren().add(gd);

			Group root2 = new Group();
			welcome = new Stage();
			Scene scene2 = new Scene(root2, 380, 100, Color.LIGHTSALMON);
			welcome.setScene(scene2);
			BorderPane brdpane = new BorderPane();	//BorderPane for the Opening Window
			brdpane.setPadding(new Insets(10, 0, 5, 0));
			brdpane.setCenter(addLabel());
			brdpane.setBottom(addHbox());
			root2.getChildren().add(brdpane);
			welcome.show();
			bt1.setOnAction(new EventHandler<ActionEvent>() {	//Human vs. Computer Button
				public void handle(ActionEvent event) {
					welcome.close();
					setSpecs.show();
					usersTurn();	//Human vs. Computer gameplay setup method
				}
				
				/**
				 * Handles button push interactions for the setSpecs window. Once the "Start the Game"
				 * button is pushed, the actual Human vs. Computer gameplay will begin
				 * 
				 * @author Rohan T. and Bryan V.
				 */
				private void usersTurn() {
					pd.setOnAction(new EventHandler<ActionEvent>() {
						public void handle(ActionEvent event) {
							try {
								num = Integer.parseInt(txt1.getText());	//Read attempt value input
								la.setText("");
							} catch (NumberFormatException e) {	//Display error message
								la.setTextFill(Color.WHITESMOKE);
								la.setText("Invalid Input.Try again.");
								usersTurn();
							}
						}
					});
					el.setOnAction(new EventHandler<ActionEvent>() {
						public void handle(ActionEvent event) {
							try {
								size = Integer.parseInt(txt2.getText());	//Read guess value input
								la.setText("");
							} catch (NumberFormatException e) {	//Display error message
								la.setTextFill(Color.WHITESMOKE);
								la.setText("Invalid Input.Try again.");
								usersTurn();
							}

						}
					});
					sg.setOnAction(new EventHandler<ActionEvent>() {
						public void handle(ActionEvent event) {	//Set up specifications
							attempts = num;	//Initialize attempts
							pegs = new char[size];	//Initialize pegs[]
							setSpecs.close();	//Close setSpecs window
							for (int i = 0; i < pegs.length; i++){	//Generate peg combination
								pegs[i] = generateRandompegs();
							}
							userInput();	//Start Human vs. Computer gameplay
						}
					});

				}
			});
			bt2.setOnAction(new EventHandler<ActionEvent>() {	//Computer vs. Human button
				public void handle(ActionEvent event) {
					welcome.close();	//Close welcome window
					setSpecs.show();	//Open setSpecs window
					compTurn();	//Computer vs. Human gameplay setup method
				}
				
				/**
				 * Handles button push interactions for the setSpecs window. Once the "Start the Game"
				 * button is pushed, the actual Computer vs. Human gameplay will begin.
				 * 
				 * @author Rohan T. and Bryan V.
				 */
				private void compTurn() {
					pd.setOnAction(new EventHandler<ActionEvent>() {
						public void handle(ActionEvent event) {
							try {
								num = Integer.parseInt(txt1.getText());	//Read attempt value input
								attempts = num;	//Initialize attempt
								la.setText("");
							} catch (NumberFormatException e) {	//Error message
								la.setTextFill(Color.WHITESMOKE);
								la.setText("Invalid Input.Try again.");
								compTurn();
							}
						}
					});
					el.setOnAction(new EventHandler<ActionEvent>() {
						public void handle(ActionEvent event) {
							try {
								size = Integer.parseInt(txt2.getText());	//Read guesses value input
								la.setText("");
								generatePermutations(colors, size);
							} catch (NumberFormatException e) {	//Error message
								la.setTextFill(Color.WHITESMOKE);
								la.setText("Invalid Input.Try again.");
								compTurn();
							}
						}
						
						/**
						 * Calls the generatePermutationsRec method to add the permutations of 
						 * the pegs to posout.
						 * 
						 * @author Rohan T. and Bryan V.
						 * @param color
						 * @param len
						 */
						private void generatePermutations(char[] color, int len) {
							int n = color.length;
							generatePermutationsRec(color, "", n, len);
						}

						/** 
						 * The main recursive method to padd all possible strings of length len to posout
						 * 
						 * @author Rohan T. and Bryan V.
						 * @param color1
						 * @param prefix
						 * @param n
						 * @param len
						 */
						void generatePermutationsRec(char color1[], String prefix, int n, int len) {
							// Base case: len is 0, print prefix
							if (len == 0) {
								posout.add(prefix);
								return;
							}
							// One by one add all characters from set and recursively call for len
							// equals to len-1
							for (int i = 0; i < n; ++i) { // Next character of input added
								String newPrefix = prefix + color1[i];
								// k is decreased, because we have added a new character
								generatePermutationsRec(color1, newPrefix, n, len - 1);
							}
						}
					});
					sg.setOnAction(new EventHandler<ActionEvent>() {
						public void handle(ActionEvent event) {	//Set up specifications
							compChoice = new char[size];	//Initialize compChoice
							userPegs = new char[size];	//Initialize userPegs
							attempts = num;	//Set attempts
							setSpecs.close();	//Close setSpecs window
							setMyPegs();	//Set up the peg combination to test the computer
							compInput();	//Start Computer vs. Human game
						}
					});

				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Computer vs. Human main method. Directs the operation of the game until all the attempts have
	 * been used up or if the Human was caught cheating.
	 * 
	 * @author Rohan T. and Bryan V.
	 */
	public void compInput() {
		row = 1;	//Initialize row to 1
		guessList = new ArrayList<String>();
		totalPegsMap = new HashMap<Integer, Integer>();
		while ((attempts > 0) && (!gameOvr)) {	//Game ends either when there are no more avaliable attempts
			loop3 = false;	//or when gameOvr is set to true
			for (int i = 0; i < size; i++){	//Generate the Computer's peg combination guess
				compChoice[i] = generateRandompegs();
			}
			if (!history.isEmpty()) {	//If the computer guesses a combination that it has already guessed
				for (int i = 0; i < history.size(); i++) {	//then it guesses again
					if (history.get(i).s.equals(new String(compChoice))) {
						loop3 = true;
						break;
					}
				}
				if(loop3){	//When true, allows program the cycle back to the while loop
					continue;
				}
			}
			posout.remove(new String(compChoice)); //Remove used guess from posout
			char ans = compGuess();	//Retrieve Human feedback of correct or wrong answer
			if (ans == 'Y') {
				generateCompWin();	//Computer has won
				break;
			} else if (ans == 'N') {	//Computer is wrong
				evaluateChoice();	//Evaluate the feedback and guesses
				row++;
				attempts--;
			}
		}
		if (attempts == 0) {	//When out of attempts. Either computer wins if all black pegs match
			if (b == size) {	//or computer loses.
				generateCompWin();
			} else {
				generateCompLost();
			}
		}
	}

	/**
	 * Generates the GUI window if the computer loses the Computer vs. Human game
	 * 
	 * @author Rohan T. and Bryan V.
	 */
	private void generateCompLost() {
		generateCorrectAns();	//Generate actual peg combination onto the GUI
		stageFin = new Stage();
		stageFin.setTitle("Result of the Game");
		root3 = new Group();
		Scene scene3 = new Scene(root3, 300, 180, Color.GAINSBORO);
		stageFin.setScene(scene3);
		BorderPane brp = new BorderPane();
		brp.setPadding(new Insets(30, 30, 30, 30));
		Label l1 = new Label("COMPUTER LOST THE GAME !!");
		bt3 = new Button("Close");
		bt3.setPrefSize(100, 20);
		brp.setCenter(l1);
		bt3.setAlignment(Pos.BOTTOM_CENTER);
		brp.setBottom(bt3);
		root3.getChildren().add(brp);
		stage.show();
		stageFin.show();
		bt3.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				stageFin.close();
				stage.close();
			}
		});
		System.out.println("COMPUTER LOST!!");
	}

	/**
	 * Returns the Y/N feedback requested by the computer.
	 * 
	 * @author Rohan T. and Bryan V.
	 * @return "Y" or "N" depending on whether the peg guess is correct or not
	 */
	private char compGuess() {
		System.out.println("The computer has " + attempts + " guesses left.");
		System.out.print("The Computer's guess is: ");
		for (int i = 0; i < compChoice.length; i++) {
			System.out.print(compChoice[i] + " ");
		}
		String guessString = new String(compChoice);
		guessList.add(guessString);	//Add the peg guess to the guess list
		System.out.println("\nDid the computer win the Game? Y/N");
		@SuppressWarnings("resource")
		Scanner kb = new Scanner(System.in);
		char c = kb.next().toUpperCase().charAt(0);
		if (c != 'Y' && c != 'N') {
			System.out.println("Enter a valid answer. The allowed responses are 'Y' or 'N' ");
			compGuess();
		} else if (c == 'Y') {
			return 'Y';
		} else if (c == 'N') {
			return 'N';
		}
		return 'N';
	}

	/**
	 * Human determines the feedback of the computer's peg guess. If the human is caught cheating,
	 * then the game ends immediately and the computer wins.
	 * 
	 * @author Rohan T. and Bryan V.
	 */
	public void evaluateChoice() {
		String reply; //Holds Scanner input
		String[] replySplit;	//Holds array of Scanner inputs
		boolean loop = true, loop2 = true;	//Binary semaphores for various loops in the method
		int guessNumber, guessHash, curGuessInd, lo, hi;
		String guessString = new String(compChoice);	//String of compChoice[]
		String[] compare1, compare2;	//Used for comparison of total pegs guesses between pegs in the pegsMap and current peg guess
		Integer pastGuessHash;	//Hash value of one of the past guesses in the pegsMap
		@SuppressWarnings("resource")
		Scanner kb1 = new Scanner(System.in);
		while (loop) {
			loop = false;	//Initialize loop and loop2
			loop2 = true;
			System.out.println("How many black pegs did I get?: ");	//Enter black peg feedback
			reply = kb1.nextLine();
			replySplit = reply.split("\\s+");
			if (replySplit.length != 1) {
				System.out.println("Invalid input. Try again.");	//Error causes a loop to beginning
				loop = true;
			} else {
				try {
					b = Integer.parseInt(replySplit[0]); // # of black Pegs
					if (b < 0) {
						throw new NumberFormatException();	//Try entering again if input is negative
					} else if (b == size) {	//Computer wins if all pegs feedback are black pegs
						System.out.println("The Computer got " + b + " black pegs.");
						generateCompWin();	//Generate computer win GUI display
					} else {
						while (loop2) {
							loop2 = false; // Reset loop2
							System.out.println("How many white pegs did I get?: ");	//Enter white peg feedback
							reply = kb1.nextLine();
							replySplit = reply.split("\\s+");
							if (replySplit.length != 1) {
								System.out.println("Invalid input. Try again.");	//Error causes a loop to beginning of loop2
								loop2 = true;
							} else {
								try {
									w = Integer.parseInt(replySplit[0]); // # of white pegs
									if (w < 0) {
										throw new NumberFormatException();	//Try entering again if input is negative
									} else {
										guessHash = HashString(guessString);	//Calculate hash value
										guessNumber = b + w;	//Determine total pegs received
										if (guessNumber > size) {	//Case 1: More pegs than possible
											System.out.println("Nice try. But I know your lying. Game Over. You Lose");
											gameOvr = true;
											break;
										}
										if (totalPegsMap.containsKey(guessHash)) {	//Case 2: Different total pegs for Strings with same characters
											if (totalPegsMap.get(guessHash) != guessNumber) {	//"Restricted to the current 6 colors" for security
												System.out.println(
														"Nice try. But I know your lying. Game Over. You Lose");
												gameOvr = true;
												break;
											}
										}
										totalPegsMap.put(guessHash, guessNumber);
										curGuessInd = totalPegsMap.size() - 1;
										compare1 = guessString.split("");
										// Use past values to compute current truths
										// Check all the mappings in totalPegsMap through the use of the
										// ArrayList<String> Used to hold the string
										// If 1 peg differs, total pegs should be +/- 1 of total pegs
										// Take abs(# of different pegs) subtracted and added, and compare to
										// the mapping value
										// If not within bounds, then the feedback is a lie
										for (int a = 0, absChange = 0, pastResult; a < curGuessInd; a++, absChange = 0) {
											compare2 = guessList.get(a).split("");
											for (int b = 0; b < size; b++) { // Next find displaced copies
												for (int c = 0; c < size; c++) {
													if (compare2[c] != null) { // * placeholder for black pegs
														if (compare2[c].equals(compare1[b])) {
															compare2[c] = null; // null place holder for white pegs
															break;
														}
													}
												}
											}
											for (int b = 0; b < size; b++) { // Find number of displacements possible
												if (compare2[b] != null) {
													absChange++;	//Determine total different colors
												}
											}
											pastGuessHash = HashString(guessList.get(a)); // Retrieve pastguessHash
											pastResult = totalPegsMap.get(pastGuessHash); // Retrieve pastResult of past guess
											if (pastResult == size) { //Case 3: If pastResult is at max bound and a change results in max bound
												if ((absChange > 0) && (guessNumber == size)) {
													System.out.println(
															"Nice try. But I know your lying. Game Over. You Lose");
													gameOvr = true;
													break;
												}
											}
											if (pastResult + absChange >= size) { // Set upper bound of change
												hi = size;	//Upper bound of possible total pegs
											} else {
												hi = pastResult + absChange;
											}
											if (pastResult - absChange <= 0) { // Set lower bound of change
												lo = 0;	//Lower bound of possible total pegs
											} else {
												lo = pastResult - absChange;
											}
											if (!((lo <= guessNumber) && (guessNumber <= hi))) { 
											//Case 4: If guessNumber is not within the bounds, then you lie
												System.out.println("Nice try. But I know your lying. Game Over.");
												gameOvr = true;
												break;
											}
										}
									}
								} catch (NumberFormatException e) {	//Error causes loop2 to repeat
									System.out.println("Invalid input. Try again");
									loop2 = true;
								}
							}
						}
					}
				} catch (NumberFormatException e) {	//Error causes loop to repeat
					System.out.println("Invalid input. Try again.");
					loop = true;
				}
			}
		}
		history.add(new Game(new String(compChoice), b, w));	//Update history list
		row++;
		gridpane.add(new Label("Guess - " + guessnum), 1, row);	//Draw pegs on GUI
		guessnum++;	//GUI is updated at the end of the overall method
		gridpane.add(new Label(b + " Black " + w + " White "), 2, row);
		col = 4;
		String comp1 = new String(compChoice);
		for (int i = 0; i < comp1.length(); i++) {
			switch (comp1.charAt(i)) {
			case 'B':
				Circle circle01 = new Circle(20);
				circle01.setFill(Color.BLUE);
				circle01.setStroke(Color.BLACK);
				gridpane.add(circle01, col, row);
				col++;
				break;// blue
			case 'G':
				Circle circle02 = new Circle(20);
				circle02.setStroke(Color.BLACK);
				circle02.setFill(Color.GREEN);
				gridpane.add(circle02, col, row);
				col++;
				break; // green
			case 'O':
				Circle circle03 = new Circle(20);
				circle03.setFill(Color.ORANGE);
				circle03.setStroke(Color.BLACK);
				gridpane.add(circle03, col, row);
				col++;
				break; // orange
			case 'P':
				Circle circle04 = new Circle(20);
				circle04.setFill(Color.PURPLE);
				circle04.setStroke(Color.BLACK);
				gridpane.add(circle04, col, row);
				col++;
				break; // purple
			case 'R':
				Circle circle05 = new Circle(20);
				circle05.setFill(Color.RED);
				circle05.setStroke(Color.BLACK);
				gridpane.add(circle05, col, row);
				col++;
				break; // red
			case 'Y':
				Circle circle06 = new Circle(20);
				circle06.setFill(Color.YELLOW);
				circle06.setStroke(Color.BLACK);
				gridpane.add(circle06, col, row);
				col++;
				break; // yellow
			default:
				System.err.println("Error: no such colour!");
			}
		}
		if(gameOvr){
			generateCompWin();	//If Game Over, the generate on GUI correct peg combination
			generateCheatMsg();	//Opens a Cheat Message Window on the GUI
		}
	}

	/**
	 * Generates a Cheat Message Window. Clicking Exit immediately terminates the program
	 * 
	 * @author Rohan T. and Bryan V.
	 */
	private void generateCheatMsg() {
		Stage cc = new Stage();
		cc.setTitle(" CAUGHT CHEATING !");
		Group cheat = new Group();
		Scene sc = new Scene(cheat, 300, 190, Color.GOLD);
		GridPane gc = new GridPane();
		gc.setStyle("-fx-background-color: gold;");
		gc.setHgap(15);
		gc.setVgap(15);
		gc.add(new Label("You Cheated. The computer won by default."), 3, 4);
		Button bc = new Button("EXIT");
		bc.setTextFill(Color.FIREBRICK);
		bc.setPrefSize(120, 30);
		bc.setAlignment(Pos.BASELINE_CENTER);
		gc.add(bc, 3, 7);
		cheat.getChildren().add(gc);
		cc.setScene(sc);
		cc.show();
		bc.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				System.exit(0);
			}
		});
	}

	/**
	 * Generates GUI representation of correct pegs combination.
	 * 
	 * @author Rohan T. and Bryan V.
	 */
	private void generateCorrectAns() {
		row++;
		gridpane.add(new Label("Correct answer: "), 1, row);
		gridpane.add(new Label("" ), 2, row);
		col = 4;
		String comp1 = new String(userPegs);
		for (int i = 0; i < comp1.length(); i++) {
			switch (comp1.charAt(i)) {
			case 'B':
				Circle circle01 = new Circle(20);
				circle01.setFill(Color.BLUE);
				circle01.setStroke(Color.BLACK);
				gridpane.add(circle01, col, row);
				col++;
				break;// blue
			case 'G':
				Circle circle02 = new Circle(20);
				circle02.setStroke(Color.BLACK);
				circle02.setFill(Color.GREEN);
				gridpane.add(circle02, col, row);
				col++;
				break; // green
			case 'O':
				Circle circle03 = new Circle(20);
				circle03.setFill(Color.ORANGE);
				circle03.setStroke(Color.BLACK);
				gridpane.add(circle03, col, row);
				col++;
				break; // orange
			case 'P':
				Circle circle04 = new Circle(20);
				circle04.setFill(Color.PURPLE);
				circle04.setStroke(Color.BLACK);
				gridpane.add(circle04, col, row);
				col++;
				break; // purple
			case 'R':
				Circle circle05 = new Circle(20);
				circle05.setFill(Color.RED);
				circle05.setStroke(Color.BLACK);
				gridpane.add(circle05, col, row);
				col++;
				break; // red
			case 'Y':
				Circle circle06 = new Circle(20);
				circle06.setFill(Color.YELLOW);
				circle06.setStroke(Color.BLACK);
				gridpane.add(circle06, col, row);
				col++;
				break; // yellow
			default:
				System.err.println("Error: no such colour!");
			}
		}

	}

	/**
	 * Generates computer winning window as well as generation of correct answer of pegs combination on GUI
	 * 
	 * @author Rohan T. and Bryan V.
	 */
	private void generateCompWin() {
		generateCorrectAns();	//Generates GUI representation of correct peg combination
		stageFin = new Stage();
		stageFin.setTitle("Result of the Game");
		root3 = new Group();
		Scene scene3 = new Scene(root3, 300, 180, Color.TOMATO);
		stageFin.setScene(scene3);
		BorderPane brp = new BorderPane();
		brp.setPadding(new Insets(30, 30, 30, 30));
		Label l1 = new Label("COMPUTER WON THE GAME !!");
		bt3 = new Button("Close");
		bt3.setPrefSize(100, 20);
		brp.setCenter(l1);
		bt3.setAlignment(Pos.BOTTOM_CENTER);
		brp.setBottom(bt3);
		root3.getChildren().add(brp);
		stage.show();
		stageFin.show();
		bt3.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {	//Closes the program
				stageFin.close();
				stage.close();
			}
		});
		System.out.println("COMPUTER WON!!");
	}

	/**
	 * Sets the peg combination for the human to guess.
	 * 
	 * @author Rohan T. and Bryan V.
	 */
	public void setMyPegs() {
		System.out.println("Welcome to the world of Mastermind !!");
		System.out.println("The computer will try to crack your secret code in this turn. You should select exactly "
				+ size + " colors from the choices given below:");
		System.out.println(
				"Type B for Blue\nType G for Green\nType O for Orange\nType P for Purple\nType R for Red\nType Y for Yellow");
		System.out.println("Enter your " + size + " lettered secret code");
		input = new Scanner(System.in);
		String str = input.next().toUpperCase();
		if (str.length() != size) {
			System.out.println("Invalid Input. The length of the code should be " + size + ". Try again ->");
			setMyPegs();
		}
		for (int i = 0; i < size; i++) {
			if (str.charAt(i) != 'B' && str.charAt(i) != 'G' && str.charAt(i) != 'O' && str.charAt(i) != 'P'
					&& str.charAt(i) != 'R' && str.charAt(i) != 'Y') {
				System.out.println("Invalid Input. Please Choose only from the options given above. Try Again ->");
				setMyPegs();
			} else {
				userPegs[i] = str.charAt(i);
			}
		}
	}

	/**
	 * Adds a hBox to the BorderPane
	 * 
	 * @author Rohan T. and Bryan V.
	 * @return hBox, which is added to the BorderPane
	 */
	private Node addHbox() {
		HBox hb = new HBox();
		hb.setPadding(new Insets(20));
		hb.setSpacing(20);
		hb.setStyle("-fx-background-color: #336699;");
		bt1 = new Button("Human v/s Computer");
		bt2 = new Button("Computer v/s Human");
		bt1.setPrefSize(160, 20);
		bt2.setPrefSize(160, 20);
		hb.getChildren().addAll(bt1, bt2);
		return hb;
	}

	/**
	 * Creates a label "How would you like to Play this Game?"
	 * @return The label created
	 */
	private Node addLabel() {
		Label l = new Label("How would you like to Play this Game?");
		return l;
	}

	/**
	 * Selects random Pegs
	 * 
	 * @author Rohan T. and Bryan V.
	 * @return Random peg selected
	 */
	public static char generateRandompegs() {
		switch (rand.nextInt(6)) {
		case 0:
			return 'B'; // blue
		case 1:
			return 'G'; // green
		case 2:
			return 'O'; // orange
		case 3:
			return 'P'; // purple
		case 4:
			return 'R'; // red
		case 5:
			return 'Y'; // yellow
		default:
			System.err.println("Error: no such colour!");
		}
		return 0;
	}

	/**
	 * Calculates the number of black pegs in the guess
	 * 
	 * @author Rohan T. and Bryan V.
	 * @param array1
	 * @param guess
	 * @return The number of black pegs in the guess
	 */
	public int calcblackpegs(char array1[], String guess) {
		int numofblack = 0;

		for (int i = 0; i < size; i++) {
			if (array1[i] == guess.charAt(i)) {
				numofblack++;
				htb.put(i, true);
				htw.put(i, true);
			}
		}
		return numofblack;
	}

	/**
	 * Calculates the number of white pegs in the guess
	 * 
	 * @author Rohan T. and Bryan V.
	 * @param array1
	 * @param guess
	 * @return The number of white pegs in the guess
	 */
	public int calcwhitepegs(char array1[], String guess) {
		int numofwhite = 0;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++)
				if (!htw.containsKey(i) && !htb.containsKey(j)) {
					if (guess.charAt(i) == array1[j]) {
						htw.put(i, true);
						htb.put(j, true);
						numofwhite++;
					}
				}
		}
		return numofwhite;
	}

	/**
	 * Human plays aganist the computer to guess the correct peg combination in order to win the game
	 * The GUI is updated at the end of the method
	 * 
	 * @author Rohan T. and Bryan V.
	 */
	public void userInput() {
		String comp;	//comp is the String guess that you give
		int col, row = 0;
		System.out.println("Welcome to the World of Master Mind !!");
		System.out.println("The Computer has chosen its pegs.");
		System.out.println("It is your turn to play now. Best of Luck !!\n");

		while (attempts > 0) {
			input = new Scanner(System.in);
			row++;
			col = 4;
			System.out.println("\nYou have " + attempts + " attempts left");
			System.out.println("What is your next guess?");
			System.out.println("TYPE in the characters for your next guess and press ENTER");
			System.out.println("Length of your input should be " + size);
			comp = input.next().toUpperCase();
			if (comp.length() != size) {
				System.out.println("Invalid Input");
				continue;
			}
			b = calcblackpegs(pegs, comp);	//Calculate number of black pegs
			w = calcwhitepegs(pegs, comp);	//Calculate number of white pegs
			htw.clear();	//Clear the hashtable for both black pegs and white pegs
			htb.clear();
			if (b == size) {	//If the number of black pegs equals the total number of pegs, then create GUI for winning
				stageFin = new Stage();
				root3 = new Group();
				Scene scene3 = new Scene(root3, 300, 180, Color.TOMATO);
				stageFin.setScene(scene3);
				BorderPane brp = new BorderPane();
				brp.setPadding(new Insets(30, 30, 30, 30));
				Label l1 = new Label("CONGRATULATIONS !! YOU WON !!");
				bt3 = new Button("Close");
				bt3.setPrefSize(100, 20);
				brp.setCenter(l1);
				bt3.setAlignment(Pos.BOTTOM_CENTER);
				brp.setBottom(bt3);
				root3.getChildren().add(brp);
				stage.show();
				stageFin.show();
				bt3.setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent event) {	//Closes the Game
						stageFin.close();
						stage.close();
					}
				});
				System.out.println("You Won!!");
				break;
			}
			System.out.println("Result -> " + b + " Black Pegs   " + w + " White Pegs");	//Printing out the GUI
			gridpane.add(new Label("Guess - " + guessnum), 1, row);	//GUI is updated at the end of the method
			guessnum++;
			gridpane.add(new Label(b + " Black " + w + " White "), 2, row);
			for (int i = 0; i < comp.length(); i++) {
				switch (comp.charAt(i)) {
				case 'B':
					Circle circle01 = new Circle(20);
					circle01.setFill(Color.BLUE);
					circle01.setStroke(Color.BLACK);
					gridpane.add(circle01, col, row);
					col++;
					break;// blue
				case 'G':
					Circle circle02 = new Circle(20);
					circle02.setStroke(Color.BLACK);
					circle02.setFill(Color.GREEN);
					gridpane.add(circle02, col, row);
					col++;
					break; // green
				case 'O':
					Circle circle03 = new Circle(20);
					circle03.setFill(Color.ORANGE);
					circle03.setStroke(Color.BLACK);
					gridpane.add(circle03, col, row);
					col++;
					break; // orange
				case 'P':
					Circle circle04 = new Circle(20);
					circle04.setFill(Color.PURPLE);
					circle04.setStroke(Color.BLACK);
					gridpane.add(circle04, col, row);
					col++;
					break; // purple
				case 'R':
					Circle circle05 = new Circle(20);
					circle05.setFill(Color.RED);
					circle05.setStroke(Color.BLACK);
					gridpane.add(circle05, col, row);
					col++;
					break; // red
				case 'Y':
					Circle circle06 = new Circle(20);
					circle06.setFill(Color.YELLOW);
					circle06.setStroke(Color.BLACK);
					gridpane.add(circle06, col, row);
					col++;
					break; // yellow
				default:
					System.err.println("Error: no such colour!");
				}
			}
			attempts--;	//Update number of attempts left
		}
		row++;
		gridpane.add(new Label("Correct Answer"), 1, row);
		guessnum++;	//Update guess attempt made
		gridpane.add(new Label(), 2, row);
		col = 4;
		String comp1 = new String(pegs);
		for (int i = 0; i < comp1.length(); i++) {	//Print out GUI
			switch (comp1.charAt(i)) {
			case 'B':
				Circle circle01 = new Circle(20);
				circle01.setFill(Color.BLUE);
				circle01.setStroke(Color.BLACK);
				gridpane.add(circle01, col, row);
				col++;
				break;// blue
			case 'G':
				Circle circle02 = new Circle(20);
				circle02.setStroke(Color.BLACK);
				circle02.setFill(Color.GREEN);
				gridpane.add(circle02, col, row);
				col++;
				break; // green
			case 'O':
				Circle circle03 = new Circle(20);
				circle03.setFill(Color.ORANGE);
				circle03.setStroke(Color.BLACK);
				gridpane.add(circle03, col, row);
				col++;
				break; // orange
			case 'P':
				Circle circle04 = new Circle(20);
				circle04.setFill(Color.PURPLE);
				circle04.setStroke(Color.BLACK);
				gridpane.add(circle04, col, row);
				col++;
				break; // purple
			case 'R':
				Circle circle05 = new Circle(20);
				circle05.setFill(Color.RED);
				circle05.setStroke(Color.BLACK);
				gridpane.add(circle05, col, row);
				col++;
				break; // red
			case 'Y':
				Circle circle06 = new Circle(20);
				circle06.setFill(Color.YELLOW);
				circle06.setStroke(Color.BLACK);
				gridpane.add(circle06, col, row);
				col++;
				break; // yellow
			default:
				System.err.println("Error: no such colour!");
			}
		}
		if (attempts == 0) {	//If out of attempts, then you lose. Print out GUI for losing
			lost = new Stage();
			root4 = new Group();
			Scene scene4 = new Scene(root4, 380, 200, Color.DARKORANGE);
			lost.setScene(scene4);
			BorderPane br = new BorderPane();
			br.setPadding(new Insets(30, 10, 10, 10));
			Label l2 = new Label("SORRY ! YOU LOST !");
			bt4 = new Button("Close");
			br.setCenter(l2);
			bt4.setAlignment(Pos.CENTER);
			br.setBottom(bt4);
			root4.getChildren().add(br);
			stage.show();
			lost.show();
			bt4.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent event) {	//Close game
					lost.close();
					stage.close();
				}
			});
			System.out.println("You Lost!!");
		}
		System.out.print(" Computer's choice was: ");	//Reveal peg combination answer
		for (int a = 0; a < pegs.length; a++) {
			System.out.print(" " + pegs[a]);
		}
		input.close();
	}

	/**
	 * Returns the hashcode of a String, based on the characters used
	 * 
	 * @author Rohan T. and Bryan V.
	 * @param str
	 * @return
	 */
	static int HashString(String str) {
		int hashCode = 0;
		for (int length = str.length(); length > 0; length--) {
			hashCode += str.charAt(length - 1);
		}
		return hashCode;
	}

	/**
	 * Starts the JavaFX program
	 * 
	 * @author Rohan T. and Bryan V.
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
}

