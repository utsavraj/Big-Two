
/**
 * The class implements Pair (and returns it and topcard if valid), a sub-class of Hands
 * 
 * @author utsav
 *
 */

public class Pair extends Hand implements Abstract_Interface {

	private static final long serialVersionUID = 1L;
	
	/**
	 * This constructor helps creating a Pair hand object of specific player and cards played by that player
	 * @param player - The current player the cards belong to eg. Player 1
	 * 		
	 * @param card - list of cards (2 as pair) played by the player eg. Player 1 players spade and diamond 2s.
	 * 		
	 */
	
	
	public Pair(CardGamePlayer player, CardList card)
	{
		super(player,card);
	}
	

	
	public Card getTopCard()
	{
		//Only need to compare the suit as rank are the same
		
		if(this.getCard(0).suit > this.getCard(1).suit) //Card 1 has higher suit than Card 2
		{
			return this.getCard(0);
		}
		
		return this.getCard(1); //vice versa
		
	}
	

	
	public boolean isValid()
	{
		if(this.size() != 2) //Needs to be 2 cards
		{
			return false;
		}
		
		if(this.getCard(0).rank == this.getCard(1).rank) //Needs to have Same rank
		{
			return true;
			
		}
		
		return false;	
		
	}
	

	
	
	
	
	public String getType()
	{
		
		
		return new String("Pair"); //Tell its a pair
	}
}