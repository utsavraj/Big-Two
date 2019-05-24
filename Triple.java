/**
 * The class implements triple (and returns it and topcard if valid), a sub-class of Hands
 * 
 * @author utsav
 *
 */

public class Triple extends Hand implements Abstract_Interface {


	private static final long serialVersionUID = 1L;
	
	/**
	 * This constructor helps creating a triple hand object of the specific player and cards played by that player
	 * 
	 * @param player - The current player the cards belong to eg. Player 1
	 * 		
	 * @param card - list of cards played by the player 
	 * 		
	 */
	
	
	public Triple(CardGamePlayer player, CardList card)
	{
		super(player,card);
	}
	

	
	public Card getTopCard()
	{
		if(this.getCard(0).suit > this.getCard(1).suit) //Card 1 is bigger than Card 2, then card 1 needs to be compared with card 3
		{
			if(this.getCard(0).suit > this.getCard(2).suit)
			{
				return this.getCard(0); //As Card 1 has bigger suit
			}
			
			else
			{
				return this.getCard(2); //implies Card 2 has bigger suit
			}
		}
		
		else
		{
			if(this.getCard(1).suit > this.getCard(2).suit) //Similar idea as above.
			{
				return this.getCard(1);
			}
			
			else
			{
				return this.getCard(2);
			}
		}
		
	}
	

	
	public boolean isValid()
	{
		if(this.size()  != 3) //Needs to be 3 cards
		{
			return false;
		}
		
		if(this.getCard(0).rank == this.getCard(1).rank && this.getCard(0).rank == this.getCard(2).rank) //All ranks need to be same
		{
			return true;
		}
		
		return false;
	}
	
	
	
	
	

	
	public String getType()
	{
		return new String("Triple"); //Tell its a triple
	}

}
