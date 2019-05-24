/**
 * This public class is a subclass of Card, models the cards used in BigTwo game. 
 * 
 * @author utsav
 *
 */

public class BigTwoCard extends Card {


	private static final long serialVersionUID = 1L;


	/**
	 * This constructor makes a card with a specific suit & rank
	 * @param suit - an int value between 0 and 3 representing the suit of a card:
	 *  0 = Diamond, 1 = Club, 2 = Heart, 3 = Spade
	 *            
	 * @param rank - an int value between 0 and 12 representing the rank of a card:
	 * 0 = 'A', 1 = '2', 2 = '3', ..., 8 = '9', 9 = '0', 10 = 'J'
	 * 11 = 'Q', 12 = 'K'
	 *            
	 */

	public BigTwoCard(int suit, int rank) {
		super(suit, rank);
		
	}
	
/**
 * Compares the rank and suit for the card
 * 
 */
	
	public int compareTo(Card card)
	{
		
		int rank = this.rank;
		int card_rank = card.rank;
		
		if(rank == 0)
		{
			rank = 13;
		}
		
		if(rank == 1)
		{
			rank = 14;
		}
		
		if(card_rank == 0)
		{
			card_rank = 13;
		}
		
		if(card_rank == 1)
		{
			card_rank = 14;
		}		
		
		if (rank > card_rank) {
			return 1;
		} 
		else if (rank < card_rank) {
			return -1;
		} 
		
		else if (this.suit > card.suit) {
			return 1;
		} 
		
		else if (this.suit < card.suit) {
			return -1;
		} 
		
		else {
			return 0;
		}
	}
	
}