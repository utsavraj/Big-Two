/**
 * This class creates a deck that will be used for BigTwoGame, a subclass of Deck
 * 
 * @author utsav
 *
 */


public class BigTwoDeck extends Deck {
		
		private static final long serialVersionUID = 1L;
		
/**
 * This methods overrides to create a BigTwo Deck 
 * 
 */
		public void initialize() 
		{
			removeAllCards();
			for (int i = 0; i < 4; i++) 
			{
				for (int j = 0; j < 13; j++) 
				{
					BigTwoCard bigtwocard = new BigTwoCard(i, j);
					this.addCard(bigtwocard);
				}
			}
			
		}

	}

