/* Mastermind PROJECT <Game.java>
 * by  <Rohan Tripathi>
 */
package project6;

/**
 * Class able of holding String of pegs and the number of black and white pegs used.
 * 
 *  @author Rohan Tripathi
 *
 */
public class Game{
	String s;
	int black,white;
	
	public Game(String s, int black,int white){
		this.s=s;
		this.black=black;
		this.white=white;
	}
}