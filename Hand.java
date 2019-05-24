/**
 * This class is a subclass of Cardlist and models the card in hand of player, hands (eg.pair) played are valid and its top card.
 * 
 * @author utsav
 */

public class Hand extends CardList implements Abstract_Interface{
	
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor for the card played as hand by a specific player
	 * @param player - A specific player
	 * 		
	 * @param cards - Cards played as hand (eg. Pair of 2s)
	 * 
	 */
	
	public Hand(CardGamePlayer player, CardList cards)
	{
		this.player = player;
		
		for(int i = 0; i < cards.size();i++)
		{
			this.addCard(cards.getCard(i));
		}
	}
	

	private CardGamePlayer player;
	
	
	
	
	/**
	 * A getter method to retrieve the player of the active hand
	 *  
	 * @return the player described above.
	 * 
	 */
	
	public CardGamePlayer getPlayer()
	{
		return this.player;
		
	}
	
	/**
	 * A dummy getter method to retrieve the top card of the current hand - Overridden by specific hand eg. Flush.
	 *  
	 * @return null - Somethings needs to be returned
	 * 
	 */
	
	public Card getTopCard()
	{
		return null;
		
	}
	
	/**
	 * Boolean method that tells if the current hand beats the previous hand
	 * 
	 * @param Current hand 
	 * 
	 * @return true only if the current hand beats the previous hand
	 * 
	 */
	
	public boolean beats(Hand hand)
	{
		if(hand.size() == 1) //Single
		{
			if(this.size() == hand.size() && this.isValid() && this.getTopCard().compareTo(hand.getTopCard()) == 1)
			{
				return true;
			}
		}
		
		if(hand.size() == 2) //Pair
		{
			
			
			if(this.size() == hand.size() && this.isValid() && this.getTopCard().compareTo(hand.getTopCard()) == 1)
			{
				return true;
			}
		}
		
		if(hand.size() == 3) //Triple
		{
			
			if(this.size() == hand.size() && this.isValid() && this.getTopCard().compareTo(hand.getTopCard()) == 1)
			{
				return true;
			}
		}
		
		
		
		
		
		if(hand.size() == 5) //Flush, StraightFlush, Quad, Straight & FullHouse
		{
			if(this instanceof StraightFlush) 
			{
				if(this.size() == hand.size())
				{
					if(this.getType() == hand.getType() && this.getTopCard().compareTo(hand.getTopCard()) == 1)
					{
						return true;
					}
					
					else if(this.getType() == hand.getType() && this.getTopCard().compareTo(hand.getTopCard()) == -1)
					{
						return false;
					}
					
					else
					{
						return true;
					}
					
				}
			}
			
			if(this instanceof Straight)
			{
				if(this.size() == hand.size())
				{
					if(this.getType() == hand.getType() && this.getTopCard().compareTo(hand.getTopCard()) == 1)
					{
						return true;
					}
					
					else if(this.getType() == hand.getType() && this.getTopCard().compareTo(hand.getTopCard()) != 1)
					{
						return false;
					}
					
					else
					{
						return false;
					}
				}	
			}
			
			if(this instanceof Flush)
			{
				if(this.size() == hand.size())
				{
					if(this.getType() == hand.getType() && this.getTopCard().compareTo(hand.getTopCard()) == 1)
					{
						return true;
					}
					
					else if(this.getType() == hand.getType() && this.getTopCard().compareTo(hand.getTopCard()) != 1)
					{
						return false;
					}
					
					else
					{
						if(hand.getType() == "Straight")
						{
							return true;
						}
					}
				}	
			}
			
			
			
			
			if(this instanceof Quad)
			{
				if(this.size() == hand.size())
				{
					if(this.getType() == hand.getType() && this.getTopCard().compareTo(hand.getTopCard()) == 1)
					{
						return true;
					}
					
					else if(this.getType() == hand.getType() && this.getTopCard().compareTo(hand.getTopCard()) != 1)
					{
						return false;
					}
					
					
					else
					{
						if(hand.getType() != "StraightFlush")
						{
							return true;
						}
					}
				}
			}
			
			if(this instanceof FullHouse)
			{
				if(this.getType() == hand.getType() && this.getTopCard().compareTo(hand.getTopCard()) == 1)
				{
					return true;
				}
				
				else if(this.getType() == hand.getType() && this.getTopCard().compareTo(hand.getTopCard()) != 1)
				{
					return false;
				}
				
				
				
				else
				{
					if(hand.getType() == "Straight" || hand.getType() == "Flush")
					{
						return true;
					}
				}
			}		
		}
		
		return false;
	}
	
	
	
	
	
	
	
	public boolean isValid()
	{
		return false;
	}
	
	
	public String getType()
	{
		return null;
	}

}
