import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class BigTwoClient implements CardGame, NetworkGame
{
	
	private int numOfPlayers;
	private Deck deck;
	private ArrayList<CardGamePlayer> playerList;
	private ArrayList<Hand> handsOnTable;
	private int playerID; 
	private String playerName; 
	private String serverIP; 
	private int serverPort; 
	private Socket sock;
	private ObjectOutputStream oos;
	private int currentIdx; 
	private BigTwoTable table; 
	private ObjectInputStream object_input;

	

	class ServerHandler implements Runnable
	{	
		public void run() 
		{
			CardGameMessage message = null;
			
			try
			{
				while ((message = (CardGameMessage) object_input.readObject()) != null)
				{
					parseMessage(message);
					System.out.println("Accepting messages Now");
				}
			} 
			
			catch (Exception exception) 
			{
				exception.printStackTrace();
			}
			
			table.repaint();
		}
	}
	
	public BigTwoClient()
	{
		playerList = new ArrayList<CardGamePlayer>();
		
		for (int i = 0; i < 4; i++)
		{
			playerList.add(new CardGamePlayer());
		}
			
		handsOnTable = new ArrayList<Hand>();
		table = new BigTwoTable(this);
		table.disable();
		
		playerName = (String) JOptionPane.showInputDialog("Please enter your name: ");
		
		if (playerName == null)
		{
			playerName = "Default_name";
		}
			
		makeConnection();
		table.repaint();
	}

	public int getPlayerID()
	{
		return playerID;
	}

	public void setPlayerID(int playerID)
	{
		this.playerID = playerID;
	}

	public String getPlayerName()
	{
		return playerName;
	}

	public void setPlayerName(String playerName)
	{
		playerList.get(playerID).setName(playerName);
	}

	public String getServerIP()
	{
		return serverIP;
	}

	public void setServerIP(String serverIP)
	{
		this.serverIP = serverIP;
	}

	public int getServerPort()
	{
		return serverPort;
	}
	
	public void setServerPort(int serverPort)
	{
		this.serverPort = serverPort;
	}

	public void makeConnection()
	{
		serverIP = "127.0.0.1";
		serverPort = 2396;
		
		try 
		{
			sock = new Socket(this.serverIP, this.serverPort);
		} 
		
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		
		try
		{
			oos = new ObjectOutputStream(sock.getOutputStream());
			object_input = new ObjectInputStream(sock.getInputStream());
		}
		
		catch (IOException exception)
		{
			exception.printStackTrace();
		}
		
		Runnable new_job = new ServerHandler();
		Thread new_thread = new Thread(new_job);
		new_thread.start();
		
		sendMessage(new CardGameMessage(1, -1, this.getPlayerName()));
		sendMessage(new CardGameMessage(4, -1, null));
		
		table.repaint();
	}

	public void parseMessage(GameMessage message)
	{
		
		if(message.getType() == CardGameMessage.PLAYER_LIST)
		{
			playerID = message.getPlayerID();
			table.setActivePlayer(playerID);
			
			for (int i = 0; i < 4; i++)
			{
				if (((String[])message.getData())[i] != null)
				{
					this.playerList.get(i).setName(((String[])message.getData())[i]);
					table.setExistence(i);
				}
			}
			
			table.repaint();
		}
		
		else if(message.getType() == CardGameMessage.JOIN)
		{
			playerList.get(message.getPlayerID()).setName((String)message.getData());
			table.setExistence(message.getPlayerID());
			table.repaint();
			table.printMsg("Player " + playerList.get(message.getPlayerID()).getName() + " has joined the game!\n");
		}
		
		else if(message.getType() == CardGameMessage.FULL)
		{
			playerID = -1;
			table.printMsg("The game is full!\n");
			table.repaint();
		}
		
		else if(message.getType() == CardGameMessage.QUIT)
		{
			table.printMsg("Player " + message.getPlayerID() + " " + playerList.get(message.getPlayerID()).getName() + " left the game.\n");
			playerList.get(message.getPlayerID()).setName("");
			table.setNotExistence(message.getPlayerID());
			if (this.endOfGame() == false)
			{
				table.disable(); 
				this.sendMessage(new CardGameMessage(4, -1, null));
				for (int i = 0; i < 4; i++)
				{
					playerList.get(i).removeAllCards();
				}
					
				table.repaint();
			}
			
			table.repaint();
		}
		
		else if(message.getType() == CardGameMessage.READY)
		{
			table.printMsg("Player " + message.getPlayerID() + " is ready now!\n");
			handsOnTable = new ArrayList<Hand>();
			table.repaint();
		}
		
		else if(message.getType() == CardGameMessage.START)
		{
			start((BigTwoDeck)message.getData());
			table.printMsg("Game has started!\n\n");
			table.enable();
			table.repaint();
		}
		
		else if(message.getType() == CardGameMessage.MOVE)
		{
			checkMove(message.getPlayerID(), (int[])message.getData());
			table.repaint();
		}
		
		else if(message.getType() == CardGameMessage.MSG)
		{
		table.printChatMsg((String)message.getData());
		}
		
		else
		{
			table.printMsg("Wrong message type: " + message.getType());
			table.repaint();
		}
		
	}

	
	public void sendMessage(GameMessage message)
	{
		try 
		{
			oos.writeObject(message);
		}
		
		catch (IOException exception)
		{
			exception.printStackTrace();
		}
	}


	public int getNumOfPlayers() 
	{
		return numOfPlayers;
	}

	
	public Deck getDeck()
	{
		return this.deck;
	}

	
	public ArrayList<CardGamePlayer> getPlayerList() 
	{
		return playerList;
	}

	public ArrayList<Hand> getHandsOnTable() 
	{
		return handsOnTable;
	}

	public int getCurrentIdx()
	{
		return currentIdx;
	}

	public void start(Deck deck) {
		
		this.deck = deck;
		
		for (int i = 0; i < 4; i++)
		{
			playerList.get(i).removeAllCards();
		}
		
		for (int i = 0; i < 4; i++)
		{
			for (int j = 0; j < 13; j++)
			{
				getPlayerList().get(i).addCard(this.deck.getCard(i*13+j));
			}
		}
		
		for (int i = 0; i < 4; i++)
		{
			getPlayerList().get(i).getCardsInHand().sort();
		}
		
		for (int i = 0; i < 4; i++)
		{
			if (playerList.get(i).getCardsInHand().contains(new BigTwoCard(0,2)))
			{
				currentIdx = i;
				break;
			}
		}
		
		table.repaint();
		table.setActivePlayer(playerID);
	}

	
	public void makeMove(int playerID, int[] cardIdx) 
	{
		
		CardGameMessage message = new CardGameMessage(6, playerID, cardIdx);
		sendMessage(message);
	}

	public void checkMove(int playerID, int[] cardIdx)
	{
		int numOfHandsPlayed=handsOnTable.size();
		
		if(cardIdx==null)
		{
			if(numOfHandsPlayed==0)
			{
				table.printMsg("Not a legal move!\n");
				table.playSound("Not a legal move!");
			}
			
			else if(handsOnTable.get(numOfHandsPlayed-1).getPlayer().getName()==playerList.get(currentIdx).getName())
			{
				table.printMsg("Not a legal move!\n");
				table.playSound("Not a legal move!");
			}
			
			else
			{
				table.printMsg(playerList.get(currentIdx).getName()+": "+"{Pass}\n");
				table.playSound("Pass");
				
				if (currentIdx!=3)
				{
					++currentIdx;
				}
				
				else
				{
					currentIdx=0;
				}
				table.printMsg(this.getPlayerList().get(currentIdx).getName()+" Please make a move! \n");
			}
		}
		
		else 
		{
			if(numOfHandsPlayed==0)
			{
				CardList playerSelectedCards =new CardList();
				Hand playerHand;
				
				for(int i=0; i<cardIdx.length; ++i)
				{
					playerSelectedCards.addCard(playerList.get(currentIdx).getCardsInHand().getCard(cardIdx[i]));	
				}
				
				playerHand=composeHand(playerList.get(currentIdx), playerSelectedCards);
				
				if(playerHand==null)
				{
					table.printMsg("Not a legal move!\n");
					table.playSound("Not a legal move!");
				}
				
				else
				{
					playerHand.sort();
					
					if(playerHand.getCard(0).getRank()!=2||playerHand.getCard(0).getSuit()!=0)
					{
						table.printMsg("Not a legal move!\n");
						table.playSound("Not a legal move!");
					}
					
					else
					{
						table.printMsg(playerList.get(currentIdx).getName()+": "+"{"+playerHand.getType()+"}");
						
						for (int j=0; j<playerHand.size(); ++j)
						{
							table.printMsg(" ["+playerHand.getCard(j).toString()+"]");
						}
						
						table.printMsg("\n");
						playerList.get(currentIdx).removeCards(playerHand);
						
						if (currentIdx!=3)
						{
							++currentIdx;
						}
						
						else
						{
							currentIdx=0;
						}
						
						handsOnTable.add(playerHand);
						table.printMsg(this.getPlayerList().get(currentIdx).getName()+" Please make a move! \n");
					}
				}
			}
			
			else
			{
				CardList playerSelectedCards =new CardList();
				Hand playerHand;
				
				for(int i=0; i<cardIdx.length; ++i)
				{
					playerSelectedCards.addCard(playerList.get(currentIdx).getCardsInHand().getCard(cardIdx[i]));
					
				}
				
				playerHand=composeHand(playerList.get(currentIdx), playerSelectedCards);
				
				if(handsOnTable.get(numOfHandsPlayed-1).getPlayer().getName()==playerList.get(currentIdx).getName())
				{
					if (playerHand==null)
					{
						table.printMsg("Not a legal move!\n");
						table.playSound("Not a legal move!");
					}
					
					else
					{
						playerHand.sort();
						table.printMsg(playerList.get(currentIdx).getName()+": "+"{"+playerHand.getType()+"}");
						
						for (int j=0; j<playerHand.size(); ++j)
						{
							table.printMsg(" ["+playerHand.getCard(j).toString()+"]");
						}
						
						table.printMsg("\n");
						playerList.get(currentIdx).removeCards(playerHand);
						
						if (currentIdx!=3)
						{
							++currentIdx;
						}
						
						else
						{
							currentIdx=0;
						}
						
						handsOnTable.add(playerHand);
						table.printMsg(this.getPlayerList().get(currentIdx).getName()+" Please make a move! \n");
					}
				}
				
				else
				{
					if(playerHand!=null)
					{
						if (playerHand.size()==handsOnTable.get(handsOnTable.size()-1).size())
						{
							if (handsOnTable.get(handsOnTable.size()-1).beats(playerHand)==true)
							{
								table.printMsg("Not a legal move!\n");
								table.playSound("Not a legal move!");
							}
							
							else if(playerHand!=null)
							{
								playerHand.sort();
								table.printMsg(playerList.get(currentIdx).getName()+": "+"{"+playerHand.getType()+"}");
								
								for (int j=0; j<playerHand.size(); ++j)
								{
									table.printMsg(" ["+playerHand.getCard(j).toString()+"]");
								}
								
								table.printMsg("\n");
								playerList.get(currentIdx).removeCards(playerHand);
								
								if (currentIdx!=3)
								{
									++currentIdx;
								}
								
								else
								{
									currentIdx=0;
								}
								
								handsOnTable.add(playerHand);
								table.printMsg(this.getPlayerList().get(currentIdx).getName()+" Please make a move!\n");
							}
						}
						
						else
						{
							table.printMsg("Not a legal move!\n");
							table.playSound("Not a legal move!");
						}
					}
					
					else
					{
						table.printMsg("Not a legal move!\n");
						table.playSound("Not a legal move!");
					}
				}
			}
		} 
		
		if(!endOfGame())
		{
			playerList.get(playerID).getCardsInHand().sort();
			table.resetSelected();
			
			if(this.playerID==currentIdx)
			{
				table.enable();
			}
			
			else
			{
				table.disable();
			}
			
			table.repaint();
		}
		
		else
		{
			
			table.repaint();
			table.printEndGameMsg();
			handsOnTable.clear();
			
			for (int i=0; i<4; ++i)
			{
				playerList.get(i).removeAllCards();
			}
			
			sendMessage(new CardGameMessage(CardGameMessage.READY, -1, null));
		} 
	}

	public boolean endOfGame() 
	{
		for (int i = 0; i < 4; i++)
		{
			if (this.getPlayerList().get(i).getNumOfCards() == 0)
			{
				return true;
			}
				
		}
			
		return false;
	}
	
	
	public static void main(String[] args)
	{
		BigTwoClient client = new BigTwoClient();
		
	}
	
	public static Hand composeHand(CardGamePlayer player, CardList cards)
	{
		Hand test;
		test = new Single(player, cards);
		
		if (test.isValid())
		{
			return test;
		}
			
		test = new Pair(player, cards);
		
		if (test.isValid())
		{
			return test;
		}
		
		test = new Triple(player, cards);
		
		if (test.isValid())
		{
			return test;
		}
		
		test = new StraightFlush(player, cards);
		
		if (test.isValid())
		{
			return test;
		}
			
		test = new Straight(player, cards);
		
		if (test.isValid())
		{
			return test;
		}
		
		test = new Flush(player, cards);
		
		if (test.isValid())
		{
			return test;
		}
			
		test = new FullHouse(player, cards);
		
		if (test.isValid())
		{
			return test;
		}

		test = new Quad(player, cards);
		
		if (test.isValid())
		{
			return test;
		}
			
		return null;
	}

}