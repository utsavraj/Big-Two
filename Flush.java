
import java.util.Arrays;

/**
 * The class implements Flush (and returns it and topcard if valid), a sub-class of Hands
 * 
 * @author utsav
 *
 */

public class Flush extends Hand implements Abstract_Interface {

	
	private static final long serialVersionUID = 1L; //declares as the class calls for it - not necessary as I think a warning.

	/**
	 * This constructor helps creating a Flush hand object of specific player and cards played by that player
	 * @param player - The current player the cards belong to eg. Player 1
	 * 		
	 * @param card - list of cards played by the player 
	 * 		
	 */
	
	public Flush(CardGamePlayer player, CardList card)
	{
		super(player,card);
	}
	

	
	
	
	
	public Card getTopCard()
	{
		int [] num_rank = {this.getCard(0).rank, this.getCard(1).rank, this.getCard(2).rank, this.getCard(3).rank, this.getCard(4).rank}; // store the ranks of each card inside an num_rank array
		int index = 0;
		
		//In flush, only need to look at rank as suit (+ all 4) are all the same.
		
		
		for(int i = 0; i < 5;i++)
		{
			if(num_rank[i] == 0) //Increase rank of A so it only makes it with K
			{
				num_rank[i] = 13; 
			}
			
			if(num_rank[i] == 1) //Increase rank of 2 so it only makes it with K
			{
				num_rank[i] = 14; 
			}
		}
		
		
		
		Arrays.sort(num_rank); //For easier checking 
		
		
		
		
		
		for(int i = 0; i < num_rank.length;i++)
		{
			if(this.getCard(i).rank == num_rank[4])
			{
				index = i; //get the card with highest rank as the suit are same
			}
		}
		
		return this.getCard(index);
	}
	

	public boolean isValid()
	{
		if(this.size() != 5) //needs to have 5 cards
		{
			return false;
		}
		
		if(this.getCard(0).suit == this.getCard(1).suit && this.getCard(1).suit == this.getCard(2).suit && this.getCard(2).suit == this.getCard(3).suit && this.getCard(3).suit == this.getCard(4).suit) 
		{ //All suit needs to be the same.
			return true;
		}
		
		return false;
	}
	

	
	
	
	
	
	public String getType()
	{
		return "Flush"; //Tell its a Flush
	}
	

}
