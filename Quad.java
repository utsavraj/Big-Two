
/**
 * The class implements Quad (and returns it and topcard if valid), a sub-class of Hands
 * 
 * @author utsav
 *
 */

public class Quad extends Hand implements Abstract_Interface {
	

	private static final long serialVersionUID = 1L;


	/**
	 * This constructor helps creating a Quad hand object of specific player and cards played by that player
	 * @param player - The current player the cards belong to eg. Player 1
	 * 		
	 * @param card - list of cards played by the player 
	 * 		
	 */
	
	
	public Quad(CardGamePlayer player, CardList card)
	{
		super(player,card);
	}
	

	
	public Card getTopCard()
	{
		this.sort(); // to simplify implementation - with sort its easier to compare
		
		if(this.getCard(0).rank == this.getCard(1).rank) //If rank of first 2 cards are the same.
		{
			return this.getCard(3); //4 card would be the biggest.
		}
		
		else
		{
			return this.getCard(4); // if the ranks of 1st or 4th are the not same, it
			// means that the rank of the odd card is smaller,  and biggest card is sorted card.
		}
	}
	

	public boolean isValid()
	{
		if(this.size() != 5) //Needs to be five card
		{
			return false;
		}
		
		
		
		this.sort();
		
		if(this.getCard(0).rank == this.getCard(1).rank)
		{
			if(this.getCard(1).rank == this.getCard(2).rank && this.getCard(2).rank == this.getCard(3).rank)
			{
				return true; //4 cards with the same rank.
			}
		}
		
		else if(this.getCard(1).rank == this.getCard(2).rank)
		{
			if(this.getCard(2).rank == this.getCard(3).rank && this.getCard(2).rank == this.getCard(4).rank)
			{
				return true; //Odd card has smaller rank, thus all the next 4 has to be the same rank.
			}
		}
		
		return false;
	}
	
	

	
	
	
	public String getType()
	{
		return "Quad"; //Tell its a Quad
	}
}