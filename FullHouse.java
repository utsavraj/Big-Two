/**
 * The class implements FullHouse (and returns it and topcard if valid), a sub-class of Hands
 * 
 * @author utsav
 *
 */
public class FullHouse extends Hand implements Abstract_Interface {

	
	private static final long serialVersionUID = 1L;
	
	/**
	 * This constructor helps creating a FullHouse hand object of specific player and cards played by that player
	 * @param player - The current player the cards belong to eg. Player 1
	 * 		
	 * @param card - list of cards played by the player 
	 * 		
	 */
	
	public FullHouse(CardGamePlayer player, CardList card)
	{
		super(player,card);
	}
	

	
	
	public Card getTopCard()
	{
		
		// gets the rank and the suit of the 3 same rank card with highest suit
		this.sort();
		
		if(this.getCard(0).rank == this.getCard(2).rank) 
		{
			return this.getCard(2); // All the sorted first 3 cards have same rank means the 3rd card is the highest - top card
		}
		
		else
		{
			return this.getCard(4); // Odd 2 cards have lower rank, thus final card is the top card
		}
	}
	

	
	public boolean isValid()
	{
		if(this.size() != 5) //needs to be five cards
		{
			return false;
		}
		
		this.sort(); // Easier Checking
		
		if(this.getCard(0).rank == this.getCard(2).rank) //All 3 cards have smaller ranker than the pair of other card
		{
			if(this.getCard(0).rank == this.getCard(1).rank && this.getCard(0).rank == this.getCard(2).rank && this.getCard(3).rank == this.getCard(4).rank)
			{
				return true; 
			}
		}
		
		
		
		//Vice versa
		
		else if(this.getCard(2).rank == this.getCard(4).rank)
		{
			if(this.getCard(2).rank == this.getCard(3).rank && this.getCard(2).rank == this.getCard(4).rank && this.getCard(0).rank == this.getCard(1).rank)
			{
				return true;
			}
		}
		
		return false;
		
		
	}
	
	
	
	
	

	
	
	public String getType()
	{
		return "FullHouse"; //Tell its a FullHouse
	}
}