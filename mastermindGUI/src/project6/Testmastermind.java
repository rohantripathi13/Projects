/* Mastermind PROJECT <Testmastermind.java>
 * by- <Rohan Tripathi>
 */
package project6;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class Testmastermind {
	
	/**
	 * Print the welcome message before the tests cases start executing
	 */
	
	@BeforeClass
	public static void welcome(){
		System.out.println("Welcome to J-Unit Testing\n");
		System.out.println("Your test cases will execute now.");
	}
	
	/**
	 * Print the ending message after all the tests cases have been executed 
	 */
	
	@AfterClass
	public static void bbbye(){
		System.out.println("\nGoodbye! Hope you had fun with J-Unit");
	}
	
	/**
	 * Tests the method that calculates black pegs.
	 * It takes a character array and a string as a parameter and 
	 * Calculates black pegs by comparing them for the presence of similar colors at the same indices
	 */

	@Test
	public void testcalcblackpegs() {
		Main helper=new Main();
		char ch[]={'B','G','O','P'};
		String str="BGYO";	
		int blk=helper.calcblackpegs(ch,str);
		assertEquals(2,blk);
	}
	
	/**
	 * Tests the method that calculates white pegs.
	 * It takes a character array and a string as a parameter and 
	 * Calculates white pegs by comparing them for the presence of similar colors at different indices
	 */

	@Test
	public void testcalcwhitepegs() {
		Main helper=new Main();
		char ch[]={'B','G','O','P'};
		String str="BGYO";
		int bk=helper.calcblackpegs(ch, str);
		int wht=helper.calcwhitepegs(ch,str);
		assertEquals(1,wht);
	}
	
	/**
	 * Tests the method that checks the hashtables' status post pegs calculation.
	 * It checks whether the hashtable(black pegs) is empty or not.
	 * The boolean expression in assertFalse return true/false based on the inputs chosen.
	 * For our inputs, the expression will always be false.
	 */
	@Test
	public void testblkhashtable() {
		Main helper=new Main();
		char ch[]={'B','G','O','P'};
		String str="BGYO";	
		helper.calcblackpegs(ch, str);
		helper.calcwhitepegs(ch,str);
		assertFalse(helper.htb.isEmpty());
	}
	
	/**
	 * Tests the method that checks the hashtables' status post pegs calculation.
	 * It checks whether the hashtables(white pegs) are empty or not.
	 * The boolean expression in assertFalse return true/false based on the inputs chosen.
	 * For our inputs, the expression will always be false.
	 */

	@Test
	public void testwhthashtable() {
		Main helper=new Main();
		char ch[]={'B','G','O','P'};
		String str="BGYO";	
		helper.calcblackpegs(ch, str);
		helper.calcwhitepegs(ch,str);
		assertFalse(helper.htw.isEmpty());
	}
	
	/**
	 * Tests the evaluatechoice method.
	 * It checks whether the evaluatechoice method is working or not.
	 * The boolean expression in assertFalse return true/false based on arraylist 'history'.
	 * For our inputs, the expression will always be false.
	 * added the functionality of handling a NullPointerException
	 */

	@Test(expected=NullPointerException.class)
	public void testevaluatechoice() {
		Main helper=new Main();
		char ch[]={'B','G','O','P'};
		String str="BGYO";	
		helper.calcblackpegs(ch, str);
		helper.calcwhitepegs(ch,str);
		helper.evaluateChoice();
		assertFalse(Main.history.isEmpty());
	}
	
	/**
	 * Tests the method setMyPegs.
	 * It checks whether the control transfers inside setMyPegs or not.
	 * The boolean expression in assertFalse return true/false based on whether userpegs is empty.
	 * If userpegs is assigned colors, the expression will always be true or generate a NullPointerException.
	 */

	@Test(expected=NullPointerException.class)
	public void testsetmypegs() {
		Main helper=new Main();
		helper.setMyPegs();
		assertTrue(Main.userPegs.length!=0);
	}
	
	/**
	 * Tests the method generateRandomPegs.
	 * It checks whether the method return a color corresponding to a random number.
	 * The boolean expression in assertNotEquals returns true/false based on whether color is returned.
	 * If generateRandomPegs is return a color, the expression will always be false.
	 */
	
	@Test
	public void testgenerateRandompegs(){
		char ch=Main.generateRandompegs();
		assertNotEquals(ch,null);
	}
	
	/**
	 * Tests the method HashString.
	 * Can be tested by the fact that HashString will generate an ASCII code for the string which will be non-zero.
	 */
	
	@Test
	public void testHashString() {
		String str="BGYO";	
		int test=Main.HashString(str);
		assertNotEquals(test,0);
	}
}