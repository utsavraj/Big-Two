
import java.util.Arrays;
/**
 * The class implements Straight (and returns it and topcard if valid), a sub-class of Hands
 * 
 * @author utsav
 *
 */


public class Straight extends Hand implements Abstract_Interface{


	private static final long serialVersionUID = 1L;

	/**
	 * This constructor helps creating a straight hand object of the specific player and cards played by that player
	 * @param player - The current player the cards belong to eg. Player 1
	 * 		
	 * @param card - list of cards played by the player 
	 * 		
	 */
	
	public Straight(CardGamePlayer player, CardList card)
	{
		super(player,card);
	}
	

	
	public Card getTopCard()
	{
		int [] num_rank = {this.getCard(0).rank, this.getCard(1).rank, this.getCard(2).rank, this.getCard(3).rank, this.getCard(4).rank};
		int index = 0;
		
		
		// 2 and A can only form a straight with K but not with 3
		
		for(int i = 0; i < 5;i++)
		{
			if(num_rank[i] == 0) //
			{
				num_rank[i] = 13;
			}
			
			if(num_rank[i] == 1)
			{
				num_rank[i] = 14;
			}
		}
		
		Arrays.sort(num_rank); //Easier checking
		
		for(int i = 0; i < num_rank.length;i++)
		{
			if(this.getCard(i).rank == num_rank[4])
			{
				index = i; //gets the card with highest rank
			}
		}
		
		return this.getCard(index);
	}

	
	public boolean isValid()
	{
		if(this.size() != 5) //Needs to be 5
		{
			return false;
		}
		
		
		int [] num_rank = {this.getCard(0).rank, this.getCard(1).rank, this.getCard(2).rank, this.getCard(3).rank, this.getCard(4).rank};
		
		
		// 2 and A can only form a straight with K but not with 3
		
		for(int i = 0; i < 5;i++)
		{
			if(num_rank[i] == 0)
			{
				num_rank[i] = 13;
			}
			
			if(num_rank[i] == 1)
			{
				num_rank[i] = 14;
			}
		}
		
		
		Arrays.sort(num_rank); //Easier checking
		
		for (int i = 1; i < num_rank.length; i++) 
		{
		    if (num_rank[i] != num_rank[i-1] + 1)  //Current card's rank should be equal to previous card's rank + 1 as they are consecutive.
		    {	
		      return false;
		    }
		}
		
		
		return true;
	}
	

	
	
	
	
	
	
	public String getType()
	{
		return "Straight"; //Tell its a Straight
	}
}