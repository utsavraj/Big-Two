
/**
 * The class implements Single (and returns it and topcard if valid), a sub-class of Hands
 * 
 * @author utsav
 *
 */

public class Single extends Hand implements Abstract_Interface{


	private static final long serialVersionUID = 1L;
	
	/**
	 * This constructor helps creating a single hand object of the specific player and cards played by that player
	 * @param player - The current player the cards belong to eg. Player 1
	 * 		
	 * @param card - list of cards played by the player 
	 * 		
	 */
	
	
	
	public Single(CardGamePlayer player, CardList card)
	{
		super(player,card);
	}
	

	
	
	
	public Card getTopCard()
	{
		return this.getCard(0); //The only card is top card
	}
	

	
	public boolean isValid()
	{
		if(this.size() == 1) //Only 1 card
			{ return true; }
		
		return false;
	}
	

	
	public String getType()
	{
		return new String("Single"); //Tell its a Single
	}
	
	

}