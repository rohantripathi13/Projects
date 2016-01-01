/*Critter World by-
 * <Rohan Tripathi>
 */

package main;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

/* see the PDF for descriptions of the methods and fields in this class
 * you may add fields, methods or inner classes to Critter ONLY if you make your additions private
 * no new public, protected or default-package code or data can be added to Critter
 */
public abstract class Critter {
	
	public enum CritterShape {
		CIRCLE,
		SQUARE,
		TRIANGLE,
		DIAMOND,
		STAR
	}
	
	/* the default color is white, which I hope makes critters invisible by default
	 * If you change the background color of your View component, then update the default
	 * color to be the same as you background 
	 * 
	 * critters must override at least one of the following three methods, it is not 
	 * proper for critters to remain invisible in the view
	 * 
	 * If a critter only overrides the outline color, then it will look like a non-filled 
	 * shape, at least, that's the intent. You can edit these default methods however you 
	 * need to, but please preserve that intent as you implement them. 
	 */
	public javafx.scene.paint.Color viewColor() { 
		return javafx.scene.paint.Color.WHITE; 
	}
	
	public javafx.scene.paint.Color viewOutlineColor() { return viewColor(); }
	public javafx.scene.paint.Color viewFillColor() { return viewColor(); }
	
	public abstract CritterShape viewShape(); 
		
	private static java.util.Random rand = new java.util.Random();

	public static int getRandomInt(int max) {	//Gives a random integer between 0 to max
		return rand.nextInt(max);
	}

	public static void setSeed(long new_seed) {	//Changes the sequence of random numbers based on the seed
		rand = new java.util.Random(new_seed);
	}

	/*
	 * a one-character long string that visually depicts your critter in the
	 * ASCII interface
	 */
	public String toString() {
		return "";
	}

	private int energy = 0;

	protected int getEnergy() {	//Energy getter for Algae
		return energy;
	}

	private int x_coord;
	private int y_coord;
	private final static int pitch=10;

	protected final void walk(int direction) { //Critter walk method
	   	 int old_x_coord = x_coord;    //Old record of coordinates, in case critter bumps into another critter
	   	 int old_y_coord = y_coord;
	   	 if (!hasMoved) {    //Critter's may only move once per time step
	   		 switch (direction) {
	   		 case 0: // Right
	   			 x_coord+=pitch;
	   			 break;
	   		 case 1: // Right, Up
	   			 x_coord+=pitch; 
	   			 y_coord-=pitch;
	   			 break;
	   		 case 2: // Up
	   			 y_coord-=pitch;
	   			 break;
	   		 case 3: // Left, Up
	   			 x_coord-=pitch;
	   			 y_coord-=pitch;
	   			 break;
	   		 case 4: // Left
	   			 x_coord-=pitch;
	   			 break;
	   		 case 5: // Left, Down
	   			 x_coord-=pitch;
	   			 y_coord+=pitch;
	   			 break;
	   		 case 6: // Down
	   			 y_coord+=pitch;
	   			 break;
	   		 case 7: // Right, Down
	   			 x_coord+=pitch;
	   			 y_coord+=pitch;
	   			 break;
	   		 }
	   		 x_coord %= (Params.world_width*pitch); // Wrap around
	   		 y_coord %= (Params.world_height*pitch);
	   		 if(x_coord < 0){    //Addresses java's negative dividend modulus effect
	   			 x_coord += (Params.world_width*pitch);
	   		 }
	   		 if(y_coord < 0){
	   			 y_coord += (Params.world_height*pitch);
	   		 }
	   		 if (encounter) {    //If another Critter is encountered, do not move if current Critter lands on the same coordinates
	   			 int oneCrit = 0;    //Must be one, or else there are additional critters in the coordinate    
	   			 for (int i = 0, listSize = population.size(); i < listSize; i++) {    //Check if critter is on the spot of another critter
	   						 Critter compareCrit = population.get(i);
	   						 if (!compareCrit.dead) {
	   							 if(x_coord == compareCrit.x_coord){
	   								 if(y_coord == compareCrit.y_coord){
	   									 oneCrit++;
	   								 }
	   							 }
	   						 }
	   					 }
	   			 if(oneCrit != 1){    //If there are more than one critter at the spot, then critter does not move
	   				 x_coord = old_x_coord;    //Revert back to old coordinates
	   				 y_coord = old_y_coord;
	   			 }
	   			 else{
	   				 avoidance = true;    //If no critters are at the spot, take it and set the avoidance flag true
	   			 }
	   		 }
	   	 }
	   	 energy -= Params.walk_energy_cost; // Update energy levels
	   	 if (energy <= 0) { // Update dead bug listing
	   		 dead = true;
	   	 }
	   	 hasMoved = true; // Critter can no longer move within time step
	    }

	    protected final void run(int direction) { //Critter run method
	   	 int old_x_coord = x_coord;    //Old record of coordinates, in case critter bumps into another critter
	   	 int old_y_coord = y_coord;
	   	 if (!hasMoved) {    //Critter's may only move once per time step
	   		 switch (direction) {
	   		 case 0: // Right
	   			 x_coord += (pitch*2);
	   			 break;
	   		 case 1: // Right, Up
	   			 x_coord += (pitch*2);
	   			 y_coord -= (pitch*2);
	   			 break;
	   		 case 2: // Up
	   			 y_coord -= (pitch*2);
	   			 break;
	   		 case 3: // Left, Up
	   			 x_coord -= (pitch*2);
	   			 y_coord -= (pitch*2);
	   			 break;
	   		 case 4: // Left
	   			 x_coord -= (pitch*2);
	   			 break;
	   		 case 5: // Left, Down
	   			 x_coord -= (pitch*2);
	   			 y_coord += (pitch*2);
	   			 break;
	   		 case 6: // Down
	   			 y_coord += (pitch*2);
	   			 break;
	   		 case 7: // Right, Down
	   			 x_coord += (pitch*2);
	   			 y_coord += (pitch*2);
	   			 break;
	   		 }
	   		 x_coord %= (Params.world_width*(2*pitch)); // Wrap around
	   		 y_coord %= (Params.world_height*(2*pitch));
	   		 if(x_coord < 0){    //Addresses java's negative dividend modulus effect
	   			 x_coord += (Params.world_height*(2*pitch));
	   		 }
	   		 if(y_coord < 0){
	   			 y_coord += (Params.world_height*(2*pitch));
	   		 }
	   		 if (encounter) {    //If another Critter is encountered, do not move if current Critter lands on the same coordinates
	   			 int oneCrit = 0;    //Must be one, or else there are additional critters in the coordinate    
	   			 for (int i = 0, listSize = population.size(); i < listSize; i++) {
	   						 Critter compareCrit = population.get(i);
	   						 if (!compareCrit.dead) {
	   							 if(x_coord == compareCrit.x_coord){
	   								 if(y_coord == compareCrit.y_coord){
	   									 oneCrit++;
	   								 }
	   							 }
	   						 }
	   					 }
	   			 if(oneCrit != 1){    //If more than one critter is in the spot, revert back to old coordinates
	   				 x_coord = old_x_coord;
	   				 y_coord = old_y_coord;
	   			 }
	   			 else{
	   				 avoidance = true;    //If only one critter is in the spot, set the avoidance flag true
	   			 }
	   		 }
	   	 }
	   	 energy -= Params.run_energy_cost; // Update energy levels
	   	 if (energy <= 0) { // Update dead bug listing
	   		 dead = true;
	   	 }
	   	 hasMoved = true; // Critter can no longer move within time step
	    }
	protected final void reproduce(Critter offspring, int direction) { //Critter reproduction method
		if (energy < Params.min_reproduce_energy) {	//Critter must have enough energy to reproduce
			return;
		} 
		else {
			if ((energy % 2) == 1) { // Odd case: Round child energy down, parent energy up
				energy /= 2;
				offspring.energy = energy;
				energy++;
			} else { // Even case: Both parent and child get half original energy
				energy /= 2;
				offspring.energy = energy;
			}
			offspring.x_coord = x_coord; // Give same position to child as parent
			offspring.y_coord = y_coord;
			offspring.walk(direction); // Set position of child adjacent to parent
			babies.add(offspring); // Add child into the babies list
		}
	}
	
	protected String look(int direction, boolean steps) { // Finds Critters 1 or
															// 2 steps away from
															// current position
		energy -= Params.look_energy_cost; // Update energy levels
		if (energy <= 0) { // Update dead bug listing
			dead = true;
		}
		int targetX = x_coord; // targetX will be the x-coordinates to look at
		int targetY = y_coord; // targetY will be the x-coordinates to look at
		if (steps) { // If steps is true, then look 2 steps ahead
			switch (direction) {
			case 0: // Right
				targetX += 2 * pitch;
				break;
			case 1: // Right, Up
				targetX += 2 * pitch;
				targetY -= 2 * pitch;
				break;
			case 2: // Up
				targetY -= 2 * pitch;
				break;
			case 3: // Left, Up
				targetX -= 2 * pitch;
				targetY -= 2 * pitch;
				break;
			case 4: // Left
				targetX -= 2 * pitch;
				break;
			case 5: // Left, Down
				targetX -= 2 * pitch;
				targetY += 2 * pitch;
				break;
			case 6: // Down
				targetY += 2 * pitch;
				break;
			case 7: // Right, Down
				targetX += 2 * pitch;
				targetY += 2 * pitch;
				break;
			}
		} else { // If steps is false, then look 1 step ahead
			switch (direction) {
			case 0: // Right
				targetX += pitch;
				break;
			case 1: // Right, Up
				targetX += pitch;
				targetY -= pitch;
				break;
			case 2: // Up
				targetY -= pitch;
				break;
			case 3: // Left, Up
				targetX -= pitch;
				targetY -= pitch;
				break;
			case 4: // Left
				targetX -= pitch;
				break;
			case 5: // Left, Down
				targetX -= pitch;
				targetY += pitch;
				break;
			case 6: // Down
				targetY += pitch;
				break;
			case 7: // Right, Down
				targetX += pitch;
				targetY += pitch;
				break;
			}
		}
		targetX %= Params.world_width * pitch; // Wrap around
		targetY %= Params.world_height * pitch;
		if (targetX < 0) { // Addresses java's negative dividend modulus effect
			targetX += Params.world_width * pitch;
		}
		if (targetY < 0) {
			targetY += Params.world_height * pitch;
		}
		Iterator<Critter> popList = population.iterator();
		while (popList.hasNext()) { // Find a Critter at (targetX,targetY)
			Critter targetCritter = popList.next();
			if (!targetCritter.dead) {
				if (targetCritter.x_coord == targetX) {
					if (targetCritter.y_coord == targetY) {
						return targetCritter.toString(); // Return the String of
															// Critter
					}
				}
			}
		}
		return null; // Return null
	}

	public abstract void doTimeStep();	//Each subclass of Critter has its own version of doTimeStep 

	public abstract boolean fight(String oponent);	//Each subclass of Critter varies in its fight-or-flight response

	/*
	 * create and initialize a Critter subclass critter_class_name must be the
	 * name of a concrete subclass of Critter, if not an InvalidCritterException
	 * must be thrown
	 */
	public static void makeCritter(String critter_class_name) throws InvalidCritterException {	//Create instances of the given critter subclass
		Class<?> myClass = null;
		try {
			if(!(scnames.contains(critter_class_name))){
			scnames.add(critter_class_name);
			}
			myClass = Class.forName(critter_class_name);
			Constructor<?> constructor = null;
			constructor = myClass.getConstructor();
			Critter instanceOfMyClass = (Critter) constructor.newInstance();
			instanceOfMyClass.energy = Params.start_energy;	//Initial energy
			instanceOfMyClass.x_coord = Critter.getRandomInt(Params.world_width*pitch)+pitch;	//Initial coordinates
			instanceOfMyClass.y_coord = Critter.getRandomInt(Params.world_height*pitch)+pitch;
			population.add(instanceOfMyClass);	//Add Critter into population list
		} catch (ClassNotFoundException e) {	//critter_class_name is not a valid Class
			// e.printStackTrace();
			throw new InvalidCritterException(critter_class_name);
		} catch (NoSuchMethodException e) {	//No method can be found for the myClass constructor
			// e.printStackTrace();
			throw new InvalidCritterException(critter_class_name);
		} catch (SecurityException e) {	//Security violation
			e.printStackTrace();
		} catch (InstantiationException e) {	//Specific class can not be instantiated
			// e.printStackTrace();
			throw new InvalidCritterException(critter_class_name);
		} catch (IllegalAccessException e) {	//Method does not have access to class, method, or constructor
			e.printStackTrace();
		} catch (IllegalArgumentException e) {	//Illegal argument passed in
			e.printStackTrace();
		} catch (InvocationTargetException e) {	//Wraps an exception thrown by an invoked method or constructor
			e.printStackTrace();
		} catch (ClassCastException e) {	//Thrown when given class is not a Critter class
			throw new InvalidCritterException(critter_class_name);
		}
	}

	public static List<Critter> getInstances(String critter_class_name) throws InvalidCritterException {	//Gets instances of the given critter class
		List<Critter> result = new java.util.ArrayList<Critter>();	//Holds the critters of the given critter class
		Class<?> myClass = null;
		try {
			myClass = Class.forName(critter_class_name);
			Constructor<?> constructor = null;
			constructor = myClass.getConstructor();
			Critter instanceOfClass = (Critter) constructor.newInstance();
			Iterator<Critter> popIterator = population.iterator();	//Iterate and store critters of the same type in the result list
			while(popIterator.hasNext()){
				Critter tempCritter = popIterator.next();
				if(tempCritter.getClass().isInstance(instanceOfClass)){	//Check if critter is the same class of the given critter class
					result.add(tempCritter);	//Add critter to the result list
				}
			}
		} catch (ClassNotFoundException e) {	//critter_class_name is not a valid Class
			//e.printStackTrace();
			throw new InvalidCritterException(critter_class_name);
		}
		catch (NoSuchMethodException e) {	//No method can be found for the myClass constructor
			e.printStackTrace();
		} catch (SecurityException e) {	//Security violation
			e.printStackTrace();
		}
		catch (InstantiationException e) {	//Specific class can not be instantiated
			//e.printStackTrace();
			throw new InvalidCritterException(critter_class_name);	
		} catch (IllegalAccessException e) {	//Method does not have access to class, method, or constructor
			e.printStackTrace();
		} catch (IllegalArgumentException e) {	//Illegal argument passed in
			e.printStackTrace();
		} catch (InvocationTargetException e) {	//Wraps an exception thrown by an invoked method or constructor
			e.printStackTrace();
		} catch (ClassCastException e){	//Thrown when given class is not a Critter class
			throw new InvalidCritterException(critter_class_name);
		}
		return result;
	}

	public static String runStats(List<Critter> critters) {	//Identifies the number of critters in the given critter list
		System.out.print("" + critters.size() + " critters as follows -- ");
		java.util.Map<String, Integer> critter_count = new java.util.HashMap<String, Integer>();
		for (Critter crit : critters) {
			String crit_string = crit.toString();
			Integer old_count = critter_count.get(crit_string);
			if (old_count == null) {
				critter_count.put(crit_string, 1);
			} else {
				critter_count.put(crit_string, old_count.intValue() + 1);
			}
		}
		String s=""+critters.size();
		return s;
	}

	/*
	 * the TestCritter class allows some critters to "cheat". If you want to
	 * create tests of your Critter model, you can create subclasses of this
	 * class and then use the setter functions contained here.
	 * 
	 * NOTE: you must make sure that the setter functions work with your
	 * implementation of Critter. That means, if you're recording the positions
	 * of your critters using some sort of external grid or some other data
	 * structure in addition to the x_coord and y_coord functions, then you MUST
	 * update these setter functions so that they correctly update your
	 * grid/data structure.
	 */
	static abstract class TestCritter extends Critter {
		protected void setEnergy(int new_energy_value) {
			super.energy = new_energy_value;
		}

		protected void setXCoord(int new_x_coord) {
			super.x_coord = new_x_coord;
		}

		protected void setYCoord(int new_y_coord) {
			super.y_coord = new_y_coord;
		}
	}
	
	public static ArrayList<String> scnames=new ArrayList<String>(); // Arraylist to store what Class names
	public static List<Critter> population = new java.util.ArrayList<Critter>();	//List holds the critter population
	public static List<Critter> babies = new java.util.ArrayList<Critter>();	//List holds the baby critter population
	public static String[][] printMatrix = new String[Params.world_height + 2][Params.world_width + 2];	//Matrix used to help draw world map
	private static boolean borders = false;	//Flag to allow the initial drawing of the world map borders
	private boolean dead = false;	//If true, then critter is dead
	private boolean hasMoved = false;	//If true, then critter has already moved for the time step
	private boolean encounter = false;	//If true, then critter has an encounter with another critter
	private boolean avoidance = false;	//If true, then critter has avoided death for the time step

	public static void worldTimeStep() {    //One time step of the whole critter world
	   	 ListIterator<Critter> critAccess = population.listIterator();    //List iterator for population
	   	 while (critAccess.hasNext()) { // Run doTimeStep() for entire critter population
	   		 Critter tempCritter = critAccess.next();
	   		 if (tempCritter.energy <= 0) { // Record pre-operation deaths
	   			 tempCritter.dead = true;
	   		 }
	   		 else { // Run only for living critters
	   			 tempCritter.doTimeStep();
	   		 }
	   	 }
	   	 for (int i = 0, listSize = population.size(); i < listSize; i++) {    //Work out encounters between critters
	   		 int currentDice, compareDice;
	   		 Critter currentCrit = population.get(i);
	   		 if (!currentCrit.dead) {    //Do not run if the current critter is already dead
	   			 for (int j = i + 1; j < listSize; j++) {
	   				 Critter compareCrit = population.get(j);//choose the next critter in the list to compare
	   				 if(currentCrit.dead){    //If currentCrit died within the inner iteration, leave the iteration
	   					 break;
	   				 }
	   				 if (!compareCrit.dead) {    //Do not run if the compareCrit is alreay dead
	   					 if (currentCrit.x_coord == compareCrit.x_coord) {    //Match both Critter coordinates
	   						 if (currentCrit.y_coord == compareCrit.y_coord) {
	   							 currentCrit.encounter = true;    //Both critters are encountering each other
	   							 compareCrit.encounter = true;
	   							 if (currentCrit.fight(compareCrit.toString())) {    //currentCrit fights compareCrit
	   								 if(currentCrit.dead){
	   									 break;    //Case where current Critter's die while walking, running, looking in fight
	   								 }
	   								 currentDice = getRandomInt(currentCrit.energy);    //currentCrit's Power Level
	   								 if (compareCrit.fight(currentCrit.toString())) {    //compareCrit fights currentCrit
	   									 if(compareCrit.dead){
	   										 continue;    //Case where compare Critter's die while walking, running, looking in fight
	   									 }
	   									 compareDice = getRandomInt(compareCrit.energy);    //compareCrit's Power Level
	   									 if (currentDice > compareDice) { //currentCrit kills compareCrit
	   										 currentCrit.energy += (compareCrit.energy / 2);    //Update critter energy
	   										 compareCrit.energy = -1;
	   										 compareCrit.dead = true;
	   									 } else if (currentDice < compareDice) {    //compareCrit kills currentCrit
	   										 compareCrit.energy += (currentCrit.energy / 2);    //Update critter energy
	   										 currentCrit.energy = -1;
	   										 currentCrit.dead = true;
	   									 }
	   									 else{    //RNG decides who lives
	   										 int tossIndex = getRandomInt(2);
	   										 if(tossIndex == 0){    //compareCrit kills currentCrit
	   											 compareCrit.energy += (currentCrit.energy / 2);    //Update critter energy
	   											 currentCrit.energy = -1;
	   											 currentCrit.dead = true;
	   										 }
	   										 else{    //currentCrit kills compareCrit
	   											 currentCrit.energy += (compareCrit.energy / 2);    //Update critter energy
	   											 compareCrit.energy = -1;
	   											 compareCrit.dead = true;
	   										 }
	   									 }
	   								 }
	   								 else {    //compareCrit will not fight
	   									 if(compareCrit.avoidance){    //If compareCrit avoids, then it survives for now
	   										 compareCrit.avoidance = false;    //Reset avoidance flag
	   									 }
	   									 else{     //If compareCrit cannot avoid, then it will die
	   										 if(!compareCrit.dead){
	   											 currentCrit.energy += (compareCrit.energy / 2);    //Update critter energy
	   										 }
	   										 compareCrit.energy = -1;
	   										 compareCrit.dead = true;
	   									 }
	   								 }
	   							 }
	   							 else {    //currentCrit will not fight
	   								 if(currentCrit.avoidance){    //If currentCrit avoids, then it will survive
	   									 currentCrit.avoidance = false;
	   								 }
	   								 else if (compareCrit.fight(currentCrit.toString())){    //If currentCrit cannot avoid, then it will die
	   									 if(compareCrit.dead){
	   										 continue;    //Case where compareCrit died in fight by walking, running, and looking
	   									 }
	   									 if(!currentCrit.dead){    //If currentCrit had died in fight by walking, running, and looking
	   									 compareCrit.energy += (currentCrit.energy / 2);    //Update critter energy
	   									 }
	   									 currentCrit.energy = -1;
	   									 currentCrit.dead = true;
	   								 }
	   								 compareCrit.avoidance = false;    //If compareCrit avoids, then currentCrit survives too
	   							 }
	   							 currentCrit.encounter = false;    //Reset encounter flags
	   							 compareCrit.encounter = false;
	   						 }
	   					 }
	   				 }
	   			 }
	   		 }
	   	 }
	   	 critAccess = population.listIterator();
	   	 while(critAccess.hasNext()){
	   		 Critter tempCritter = critAccess.next();
	   		 tempCritter.hasMoved = false;    //Reset hasMoved flags for all critters
	   		 tempCritter.energy -= Params.rest_energy_cost;    //Deduct rest energy costs for all critters
	   		 if(tempCritter.energy <= 0){    //Cull the dead critters from the population list
	   			 critAccess.remove();
	   		 }
	   	 }
	   	 for(int algaeCount = Params.refresh_algae_count; algaeCount > 0; algaeCount--){
	   		 Algae newAlgae = new Algae();    //Fill population with new algae
	   		 newAlgae.setEnergy(Params.photosynthesis_energy_amount);    //Initial algae energy
	   		 newAlgae.setXCoord(getRandomInt(Params.world_width*pitch));    //Initial algae coordinates
	   		 newAlgae.setYCoord(getRandomInt(Params.world_height*pitch));
	   		 population.add(newAlgae);
	   	 }
	   	if (!(scnames.contains("main.Algae"))) {
	   		 scnames.add("main.Algae");
	   	 }
	   	 population.addAll(babies);    //Include baby critters at the end of the world time step
	   	 babies.clear();    //Clear the babies list
	    }

	public static void displayWorld() { // Prints out the 2D world
		int i, j;
		int xCount, yCount;
		if (!borders) { // Fill up borders of Map if not already initialized
			xCount = Params.world_width;
			yCount = Params.world_height + 1;
			printMatrix[0][0] = "+";
			for (i = 1; i <= xCount; i++) { // First Row
				printMatrix[0][i] = "-";
			}
			printMatrix[0][i] = "+";
			printMatrix[yCount][0] = "+";
			for (i = 1; i <= xCount; i++) { // Last Row
				printMatrix[yCount][i] = "-";
			}
			printMatrix[yCount][i] = "+";
			yCount = Params.world_height;
			xCount = Params.world_width + 1;
			for (j = 1; j <= yCount; j++) { // First Column
				printMatrix[j][0] = "|";
			}
			for (j = 1; j <= yCount; j++) { // Last Column
				printMatrix[j][xCount] = "|";
			}
			borders = true;
		}
		xCount = Params.world_width;
		yCount = Params.world_height;
		for (j = 1; j <= yCount; j++) { // Reset and clean inside of map
			for (i = 1; i <= xCount; i++) {
				printMatrix[j][i] = " ";
			}
		}
		Iterator<Critter> critSymbols = population.iterator();
		while (critSymbols.hasNext()) { // Fill up inside of map with critter ID Strings
			Critter tempSymbol = critSymbols.next();
			printMatrix[tempSymbol.y_coord + 1][tempSymbol.x_coord + 1] = tempSymbol.toString();
		}
		xCount = Params.world_width + 2;
		yCount = Params.world_height + 2;
		for (j = 0; j < yCount; j++) { // Print out the Matrix
			for (i = 0; i < xCount; i++) {
				System.out.print(printMatrix[j][i]);
			}
			System.out.print("\n"); // Newline
		}
	}

	public static void displayOnPane() {
		Iterator<Critter> critSymbols = population.iterator();
		while (critSymbols.hasNext()) { // Fill up inside of map with critter ID
										// Strings
			Critter tempSymbol = critSymbols.next();
			double x = tempSymbol.x_coord; //Create variables containing the current position of the critters
			double y = tempSymbol.y_coord;// if the co-ordinates are beyond the screen then restart from the edge
			Node nodeToFind = mainController.scene02.lookup("#canvas"); //Find the canvas
			Pane found2 = ((Pane) nodeToFind);
			found2.setStyle("-fx-background-color: white;");

			switch (tempSymbol.viewShape()) { // Get the enum value assigned to each subclass of Critter

			case CIRCLE: //Create a circle if the choice is a circle
				Circle circle = new Circle(x, y, 6); 
				circle.setFill(tempSymbol.viewFillColor()); //set the outline color and fill color
				circle.setStroke(tempSymbol.viewOutlineColor());
				found2.getChildren().add(circle); //Add the circle to the pane
				break;

			case SQUARE:
				Rectangle rect = new Rectangle(x, y, 12, 12); //Create a square if the choice is a square
				rect.setFill(tempSymbol.viewFillColor()); //set the outline color and fill color
				rect.setStroke(tempSymbol.viewOutlineColor());
				found2.getChildren().add(rect);// add the square to the pane
				break;

			case TRIANGLE:
				Polygon triangle = new Polygon();//Create a triangle if the choice is a triangle
				triangle.getPoints().addAll(new Double[]{ //Draw the shape based on current co-ordinates of the Critter
						x,y,
						x+14,y,
						x+7,y-7});
				triangle.setFill(tempSymbol.viewFillColor()); //set the outline color and fill color
				triangle.setStroke(tempSymbol.viewOutlineColor());
				found2.getChildren().add(triangle); // Add the triangle to the pane
				break;

			case DIAMOND:
				Polygon polygon = new Polygon(); //Create a diamond if the choice is a Diamond
				polygon.getPoints().addAll(new Double[] { //Draw the shape based on current co-ordinates of the Critter
						x,y,
						x+7,y-7,
						x+14,y,
						x+7,y+7});
				polygon.setFill(tempSymbol.viewFillColor()); //set the outline color and fill color
				polygon.setStroke(tempSymbol.viewOutlineColor());
				found2.getChildren().add(polygon); //Add the diamond to the pane
				break;

			case STAR:
				Polygon star = new Polygon(); //Create a star if the choice is a star
				star.getPoints().addAll(new Double[] {	 //Draw the shape based on current co-ordinates of the Critter
						x,y,
						x+4.9,y,
						x+7,y-5.6,
						x+9.1,y,
						x+12.6,y,
						x+9.8,y+4.2,
						x+9.1,y+9.1,
						x+7,y+5.6,
						x+4.9,y+9.1,
						x+4.2,y+4.2});
				star.setFill(tempSymbol.viewFillColor()); //set the outline color and fill color
				star.setStroke(tempSymbol.viewOutlineColor());
				found2.getChildren().add(star); //Add star to the pane
				break;
			}
		}
	}
}
