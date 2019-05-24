import java.util.Arrays;

/**
 * The class implements StraightFlush (and returns it and topcard if valid), a sub-class of Hands
 * 
 * @author utsav
 *
 */


public class StraightFlush extends Hand implements Abstract_Interface {

	private static final long serialVersionUID = 1L;
	
	/**
	 * This constructor helps creating a straight-flush hand object of the specific player and cards played by that player
	 * 
	 * @param player - The current player the cards belong to eg. Player 1
	 * 		
	 * @param card - list of cards played by the player 
	 * 		
	 */
	
	public StraightFlush(CardGamePlayer player, CardList card)
	{
		super(player,card);
	}
	

	
	public Card getTopCard()
	{
		int [] num_rank = {this.getCard(0).rank, this.getCard(1).rank, this.getCard(2).rank, this.getCard(3).rank, this.getCard(4).rank};
		int index = 0;
		
		
		//For the sake of simplicity, 2 and A can only form a straight flush with K but not with 3
		
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
		
		Arrays.sort(num_rank); //Easier Checking
		
		
		
		
		
		for(int i = 0; i < num_rank.length;i++)
		{
			if(this.getCard(i).rank == num_rank[4])
			{
				index = i; //Only need to worry about rank as same suit
			}
		}
		
		return this.getCard(index);
		
	}
	

	
	
	
	public boolean isValid()
	{
		if(this.size() != 5) //needs to be 5 cards
		{
			return false;
		}
		
		
		int [] num_rank = {this.getCard(0).rank, this.getCard(1).rank, this.getCard(2).rank, this.getCard(3).rank, this.getCard(4).rank};
		
		
		//For the sake of simplicity, 2 and A can only form a straight flush with K but not with 3
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
		boolean check = false;
		
		
		for (int i = 1; i < num_rank.length; i++) 
		{
			if(this.getCard(0).suit == this.getCard(1).suit && this.getCard(1).suit == this.getCard(2).suit && this.getCard(2).suit == this.getCard(3).suit && this.getCard(3).suit == this.getCard(4).suit)
			{//Same suit
				if (num_rank[i] == num_rank[i-1] + 1) //Consecutive rank
				{
					check = true;
				}
				else
				{
					return false;
				}
			}
		}
		
		return check;
	}
	

	
	
	
	public String getType()
	{
		return "StraightFlush"; //Tell its a Straight Flush
	}
	

}