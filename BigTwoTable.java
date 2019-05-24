import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;
import javax.swing.text.DefaultCaret;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class BigTwoTable implements CardGameTable {

	private BigTwoClient game; 
	private boolean[] selected;
	private int activePlayer;
	private JFrame frame;
	private JPanel bigTwoPanel; 
	private JButton playButton; 
	private JButton passButton; 
	private JTextArea msgArea;
	private JTextArea msgArea1; 
	private Image[][] cardImages; 
	private Image cardBackImage;
	private Image[] avatars; 
	private boolean[] presence;
	private JTextField message_box;
	
	
	public BigTwoTable(BigTwoClient game) 
	{
		this.game = game;
		this.setup();
		frame.setSize(1250, 925);
		frame.setVisible(true);
	}
	
	public void setup()
	{
		setActivePlayer(game.getPlayerID()); 
		selected = new boolean[13];
		resetSelected();
		bigTwoPanel = new BigTwoPanel();
	
		avatars = new Image[4];
		cardImages = new Image [4][13];
		
		avatars[0] = new ImageIcon("src/avatars/WW.png").getImage();
		avatars[1] = new ImageIcon("src/avatars/Captain.png").getImage();
		avatars[2] = new ImageIcon("src/avatars/Batman.png").getImage();
		avatars[3] = new ImageIcon("src/avatars/Ironman.png").getImage();
		cardBackImage = new ImageIcon("src/cards/b.png").getImage();
		
		char[] suit = {'d','c','h','s'};
		char[] rank = {'a', '2', '3', '4', '5', '6', '7', '8', '9', 't', 'j', 'q', 'k'};
		
		String fileLocation = new String();
		for (int i = 0; i < 4; i++)
		    {
				for(int j = 0 ; j < 13;j++)
				{
					fileLocation = "src/cards/" + rank[j] + suit[i] + ".png";
			        cardImages[i][j] = new ImageIcon(fileLocation).getImage();
				}
		        
		    }
		
		
		presence = new boolean[4];
		for (int i = 0; i < 4; i++)
			presence[i] = false;
		
		
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.setTitle("Big Two");
		frame.getContentPane().setBackground(new Color(25, 115, 25));
		final JSplitPane left_pane = new JSplitPane();
		left_pane.setDividerLocation(800);
		left_pane.setDividerSize(5);
		frame.add(left_pane, BorderLayout.CENTER);
		
		
		JMenuBar Menu_Bar = new JMenuBar();
		JMenu Game = new JMenu("Game");
		JMenu Message = new JMenu("Message");
		JMenuItem Connect = new JMenuItem("Connect");
		Connect.addActionListener(new ConnectMenuItemListener());
		JMenuItem Quit = new JMenuItem("Quit");
		Quit.addActionListener(new QuitMenuItemListener());
		JMenuItem Clear_Information = new JMenuItem("Clear Infomation Board");
		Clear_Information.addActionListener(new ClearMenuItemListener());
		JMenuItem Clear_ChatBoard = new JMenuItem("Clear Chat Board");
		Clear_ChatBoard.addActionListener(new ClearChatMenuItemListener());
		Game.add(Connect);
		Game.add(Quit);
		Message.add(Clear_Information);
		Message.add(Clear_ChatBoard);
		Menu_Bar.add(Game);
		Menu_Bar.add(Message);
		frame.setJMenuBar(Menu_Bar);
		
		
		msgArea = new JTextArea(100, 30);
		DefaultCaret caret = (DefaultCaret)msgArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		msgArea.append("Message Board\n");
		msgArea.setEditable(false);
		
		JScrollPane scroll_1 = new JScrollPane(msgArea);   
		msgArea.setLineWrap(true); 
		scroll_1.setVerticalScrollBarPolicy( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);    
		
		
		JPanel message = new JPanel();
		message.setLayout(new BoxLayout(message, BoxLayout.Y_AXIS));
		msgArea1 = new JTextArea(100, 30);
		DefaultCaret caret1 = (DefaultCaret)msgArea1.getCaret();
		caret1.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		msgArea1.append("This is the chat area!\n\n");
		msgArea1.setEditable(false);
		
		JScrollPane scroll_2 = new JScrollPane(msgArea1);   
		msgArea1.setLineWrap(true); 
		scroll_2.setVerticalScrollBarPolicy( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		JLabel label = new JLabel("Message: ");
		message_box = new MyTextField(30);
		message_box.setMinimumSize(new Dimension(30, 10));
		JPanel message_input = new JPanel();
		message_input.setLayout(new FlowLayout(FlowLayout.LEFT));
		message_input.add(label);
		message_input.add(message_box);
		
		message.add(scroll_1);
		message.add(scroll_2);
		message.add(message_input);
		
		left_pane.setRightComponent(message);
		left_pane.getRightComponent().setMinimumSize(new Dimension(100, 60));
		
		JPanel button_area = new JPanel();
		button_area.setLayout(new FlowLayout());
		playButton = new JButton(" Play ");
		Font newButtonFont=new Font(playButton.getFont().getName(),Font.BOLD,playButton.getFont().getSize());
		playButton.setFont(newButtonFont);
		playButton.setIcon(new ImageIcon("src/Buttons/Play.png"));
		playButton.addActionListener(new PlayButtonListener());
		
		
		passButton = new JButton(" Pass ");
		passButton.setFont(newButtonFont);
		passButton.setIcon(new ImageIcon("src/Buttons/Pass.png"));
		passButton.addActionListener(new PassButtonListener());
		button_area.add(playButton);
		button_area.add(passButton);
		button_area.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		button_area.setSize(900, 35);
		playButton = new JButton("PLAY");
		



		if (game.getCurrentIdx() != activePlayer)
		{
			button_area.setEnabled(false);
			playButton.setEnabled(false);
			passButton.setEnabled(false);
		}
		else
		{
			button_area.setEnabled(true);
			playButton.setEnabled(true);
			passButton.setEnabled(true);
		} 
		
		final JSplitPane right_pane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		right_pane.setDividerSize(5);
		right_pane.setDividerLocation(800);;
		right_pane.setTopComponent(bigTwoPanel);
		right_pane.setBackground(Color.green.darker().darker());
		right_pane.setBottomComponent(button_area);
		right_pane.getBottomComponent().setMinimumSize(new Dimension(800, 35));
		
		left_pane.setLeftComponent(right_pane);
	}

	class BigTwoPanel extends JPanel implements MouseListener {
		
		
		private static final long serialVersionUID = 1L;

		public BigTwoPanel() 
		{
			this.addMouseListener(this);
		}
		
		
		public void paintComponent(Graphics g) 
		{
			this.setOpaque(true);
			Graphics2D g2 = (Graphics2D) g;
			
	        if (presence[0]) 
	        {
		        if (game.getCurrentIdx() == 0 && game.getPlayerList().get(0).getNumOfCards() != 0)
		        {
		        		g.setColor(Color.BLUE);
		        }
		        		
		        if (activePlayer == 0)
		        {
		        		g.drawString("You", 10, 20);
		        }
		        		
		        else
		        {
		        		g.drawString(game.getPlayerList().get(0).getName(), 10, 20);
		        }
		        
		        g.setColor(Color.BLACK);
				g.drawImage(avatars[0], 10, 20, this);
			}
	        
	        g2.drawLine(0, 160, 1600, 160);
		    
	        if (activePlayer == 0) 
	        {
		        	for (int i = 0; i < game.getPlayerList().get(0).getNumOfCards(); i++) 
		        	{
			    		if (!selected[i])
			    		{
			    			g.drawImage(cardImages[game.getPlayerList().get(0).getCardsInHand().getCard(i).getSuit()][game.getPlayerList().get(0).getCardsInHand().getCard(i).getRank()], 155+40*i, 43, this);
			    		}
			    			
			    		else
			    		{
			    			g.drawImage(cardImages[game.getPlayerList().get(0).getCardsInHand().getCard(i).getSuit()][game.getPlayerList().get(0).getCardsInHand().getCard(i).getRank()], 155+40*i, 43-20, this);
			    		}
			    			
			    	}
	        }
	        
	        else
	        {
		    
	        		for (int i = 0; i < game.getPlayerList().get(0).getCardsInHand().size(); i++)
	        		{
	        			g.drawImage(cardBackImage, 155 + 40*i, 43, this);
	        		}
		    }
		    
		    if (presence[1])
		    {
			    if (game.getCurrentIdx() == 1 && game.getPlayerList().get(1).getNumOfCards() != 0)
			    {
			    		g.setColor(Color.BLUE);
			    }
			    		
		        	if (activePlayer == 1)
		        	{
		        		g.drawString("You", 10, 180);
		        	}
		        		
		        else
		        {
		        		g.drawString(game.getPlayerList().get(1).getName(), 10, 180);
		        }
		        	
		        	g.setColor(Color.BLACK);
        			g.drawImage(avatars[1], 10, 180, this);
		        		
			}
		    
		    g2.drawLine(0, 320, 1600, 320);
		    
		    if (activePlayer == 1) 
		    {
			    	for (int i = 0; i < game.getPlayerList().get(1).getNumOfCards(); i++)
			    	{
			    		if (!selected[i])
			    		{
			    			g.drawImage(cardImages[game.getPlayerList().get(1).getCardsInHand().getCard(i).getSuit()][game.getPlayerList().get(1).getCardsInHand().getCard(i).getRank()], 155+40*i, 203, this);
			    		}
			    		
			    		else
			    		{
			    			g.drawImage(cardImages[game.getPlayerList().get(1).getCardsInHand().getCard(i).getSuit()][game.getPlayerList().get(1).getCardsInHand().getCard(i).getRank()], 155+40*i, 203-20, this);
			    		}
			    	}
		    } 
		    
		    else
		    {
		    		for (int i = 0; i < game.getPlayerList().get(1).getCardsInHand().size(); i++) 
		    		{
		    			g.drawImage(cardBackImage, 155 + 40*i, 203, this);
		    		}
		    }
		    
		    if (presence[2])
		    {
			    if (game.getCurrentIdx() == 2 && game.getPlayerList().get(2).getNumOfCards() != 0)
			    {
			       	g.setColor(Color.BLUE);
			    }
		 
		        if (activePlayer == 2)
		        {
		        		g.drawString("You", 10, 340);
		        }
		        
		        else
		        {
		        		g.drawString(game.getPlayerList().get(2).getName(), 10, 340);
		        }
		        
		        g.setColor(Color.BLACK);
			    g.drawImage(avatars[2], 10, 340, this);
			}
		    
		    g2.drawLine(0, 480, 1600, 480);
		    
		    if (activePlayer == 2) 
		    {
			    	for (int i = 0; i < game.getPlayerList().get(2).getNumOfCards(); i++) 
			    	{
			    		if (!selected[i])
			    		{
			    			g.drawImage(cardImages[game.getPlayerList().get(2).getCardsInHand().getCard(i).getSuit()][game.getPlayerList().get(2).getCardsInHand().getCard(i).getRank()], 155+40*i, 363, this);

			    		}
			    		else
			    		{
			    			g.drawImage(cardImages[game.getPlayerList().get(2).getCardsInHand().getCard(i).getSuit()][game.getPlayerList().get(2).getCardsInHand().getCard(i).getRank()], 155+40*i, 363-20, this);

			    		}
			    	}
		    }
		    
		    else
		    {
			    	for (int i = 0; i < game.getPlayerList().get(2).getCardsInHand().size(); i++)
			    	{
			    		g.drawImage(cardBackImage, 155 + 40*i, 363, this);
			    	}
		    }
		    
		    if (presence[3])
		    {
			    if (game.getCurrentIdx() == 3 && game.getPlayerList().get(3).getNumOfCards() != 0)
			    {
			    		g.setColor(Color.BLUE);
			    }
		        	
		        if (activePlayer == 3)
		        {
		          	g.drawString("You", 10, 500);
		        }
		      
		        else
		        {
		        		g.drawString(game.getPlayerList().get(3).getName(), 10, 500);
		        }
		        
		        g.setColor(Color.BLACK);
			    g.drawImage(avatars[3], 10, 500, this);
		        	
			}
		    
		    g2.drawLine(0, 640, 1600, 640);
		    
		    if (activePlayer == 3)
		    {
			    	for (int i = 0; i < game.getPlayerList().get(3).getNumOfCards(); i++)
			    	{
			    		if (!selected[i])
			    		{
			    			g.drawImage(cardImages[game.getPlayerList().get(3).getCardsInHand().getCard(i).getSuit()][game.getPlayerList().get(3).getCardsInHand().getCard(i).getRank()], 155+40*i, 523, this);

			    		}
			    		
			    		else
			    		{
			    			g.drawImage(cardImages[game.getPlayerList().get(3).getCardsInHand().getCard(i).getSuit()][game.getPlayerList().get(3).getCardsInHand().getCard(i).getRank()], 155+40*i, 523-20, this);
			    		}
			    	}
		    } 
		    
		    else
		    {
			    	for (int i = 0; i < game.getPlayerList().get(3).getCardsInHand().size(); i++)
			    	{
			    		g.drawImage(cardBackImage, 155 + 40*i, 523, this);
			    	}
		    }
		    
		    g.drawString("Current Hand on Table", 10, 660);
		    
		    if (game.getHandsOnTable().size() == 0)
		    {
		     	g.drawString("No Hand on Table.", 10, 690);
		    }
		   
		    else
		    {
		    		Hand handOnTable = game.getHandsOnTable().get(game.getHandsOnTable().size()-1);
		    		g.drawString("Hand Type:\n ", 10, 720);
			    
		    		if (game.getPlayerList().get(game.getCurrentIdx()) != game.getHandsOnTable().get(game.getHandsOnTable().size()-1).getPlayer()) 
			    	{
			    		g.drawString(game.getHandsOnTable().get(game.getHandsOnTable().size()-1).getPlayer().getName(), 10, 745);
			    		
			    		for (int i = 0; i < handOnTable.size(); i++)
			    		{
			    			g.drawImage(cardImages[handOnTable.getCard(i).getSuit()][handOnTable.getCard(i).getRank()], 160 + 40*i, 690, this);
			    		}
			    			
			    	}
		    }
		    
		    g2.drawLine(0, 800, 1600, 800);
		}
		
		public void mouseClicked(MouseEvent e)
		{
			boolean flag = false; 
			int starting_point = game.getPlayerList().get(activePlayer).getNumOfCards()-1;
		
		
			if (e.getX() >= (155+starting_point*40) && e.getX() <= (155+starting_point*40+73)) 
			{
				if(selected[starting_point] == false && e.getY() >= 43 + 160 * activePlayer && e.getY() <= 43 + 160 * activePlayer+97) 
				{
					selected[starting_point] = true;
					flag = true;
			
				} 
				
				else if (selected[starting_point] == true && e.getY() >= 43 + 160 * activePlayer-20 && e.getY() <= 43 + 160 * activePlayer+97-20)
				{
					selected[starting_point] = false;
					flag = true;
				}
			}
			
			for (starting_point = game.getPlayerList().get(activePlayer).getNumOfCards()-2; starting_point >= 0 && !flag; starting_point--) 
			{
				if (e.getX() >= (155+starting_point*40) && e.getX() <= (155+starting_point*40+40)) 
				{
					if(selected[starting_point] == false && e.getY() >= 43 + 160 * activePlayer && e.getY() <= 43 + 160 * activePlayer+97) 
					{
						selected[starting_point] = true;
						flag = true;
					} 
					
					else if (selected[starting_point] == true && e.getY() >= 43 + 160 * activePlayer-20 && e.getY() <= 43 + 160 * activePlayer+97-20)
					{
						selected[starting_point] = false;
						flag = true;
					}
				}
				
				else if (e.getX() >= (155+starting_point*40+40) && e.getX() <= (155+starting_point*40+73) && e.getY() >= 43 + 160 * activePlayer && e.getY() <= 43 + 160 * activePlayer+97) 
				{
					if (selected[starting_point+1] == true && selected[starting_point] == false) 
					{
						selected[starting_point] = true;
						flag = true;
					}
				}
				
				else if (e.getX() >= (155+starting_point*40+40) && e.getX() <= (155+starting_point*40+73) && e.getY() >= 43 + 160 * activePlayer-20 && e.getY() <= 43 + 160 * activePlayer+97-20)
				{
					if (selected[starting_point+1] == false && selected[starting_point] == true)
					{
						selected[starting_point] = false;
						flag = true;
					}
				}
			}
		
		frame.repaint();
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
	}
	
	
	class MyTextField extends JTextField implements ActionListener
	{

		
		private static final long serialVersionUID = 1L;

		public MyTextField(int i)
		{
			super(i);
			addActionListener(this);
		}

		
		public void actionPerformed(ActionEvent event)
		{
			String input = getText();
			
			if (input != null && input.trim().isEmpty() == false) 
			{
				CardGameMessage message = new CardGameMessage(7, activePlayer, input);
				game.sendMessage(message);
			}
			
			this.setText("");
		}
		
	}
	
	
	class PlayButtonListener implements ActionListener
	{
		
		@Override
		public void actionPerformed(ActionEvent event)
		{
			
			
			if (game.getCurrentIdx() == activePlayer)
			{ 
				if (getSelected().length == 0) 
				{	
					int [] cardIdx = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
					game.makeMove(activePlayer, cardIdx);
				}
				
				else
				{
					game.makeMove(activePlayer, getSelected());
				}
					
				resetSelected();
				repaint();
			}
			
			else 
			{ 
				printMsg("It is not your turn\n");
				resetSelected();
				repaint();
			}
		}
	}
	
	
	class PassButtonListener implements ActionListener
	 	{
		
	
		public void actionPerformed(ActionEvent e) {
			if (game.getCurrentIdx() == activePlayer)
			{ 
				int[] cardIdx = null;
				game.makeMove(activePlayer, cardIdx);
				resetSelected();
				repaint();
			} 
			
			else 
			{
				printMsg("Not your turn!\n");
				resetSelected();
				repaint();
			}
		}
	}
	
	
	class ConnectMenuItemListener implements ActionListener
	{
		
		public void actionPerformed(ActionEvent e) 
		{
			if (game.getPlayerID() == -1) 
			{
				game.makeConnection();
			} 
			
			else if (game.getPlayerID() >= 0 && game.getPlayerID() <= 3)
			{
				printMsg("Connection already established!\n");
			}
				
		}
	}
	
	
	class QuitMenuItemListener implements ActionListener
	{
		
		@Override
		public void actionPerformed(ActionEvent e)
		{
			printMsg("Game Ended by the User!\n");
			System.exit(0);
		}
	}
	
	class ClearMenuItemListener implements ActionListener
	{
		
		@Override
		public void actionPerformed(ActionEvent e)
		{
			clearMsgArea();
		}
	}
	
	
	class ClearChatMenuItemListener implements ActionListener
	{
		
		@Override
		public void actionPerformed(ActionEvent e)
		{
			clearChatMsgArea();
		}
	}
	
	public void setNotExistence(int playerID)
	{
		presence[playerID] = false;
	}
	
	
	public void setActivePlayer(int activePlayer)
	{
		this.activePlayer = activePlayer;
	}

	
	public int[] getSelected()
	{
		int ct = 0;
		
		for (int i = 0; i < 13; i++)
		{
			if (selected[i])
			{
				ct++;
			}
		}
		
		int[] user_input = new int[ct];
		int counter = 0;
		
		for (int i = 0; i < 13; i++)
		{
			if (selected[i])
			{
				user_input[counter] = i;
				counter++;
			}
		}
		
		return user_input; 
	}

	
	public void resetSelected()
	{
		for (int i = 0; i < 13; i++)
			selected[i] = false;
	}

	public void repaint()
	{
		frame.repaint();
	}

	
	public void printMsg(String msg)
	{
		msgArea.append(msg);
	}
	
	public void printEndGameMsg()
	{
		String endOfGame="";
		
		for(int i=0; i<game.getNumOfPlayers(); ++i)
		{
			endOfGame+=game.getPlayerList().get(i).getName()+": ";
			
			if(game.getPlayerList().get(i).getNumOfCards()!=0)
			{
				for(int j=0; j<game.getPlayerList().get(i).getNumOfCards(); ++j)
				{
					endOfGame+=" ["+game.getPlayerList().get(i).getCardsInHand().getCard(j).toString()+"]";
				}
				endOfGame+="\n";
			}
			
			else
			{
				endOfGame+=" wins\n";
				//playSound("Player " + i + " Wins!");
			}
		}
		
		JOptionPane.showMessageDialog(null, "GameOver!!\n"+endOfGame);
	}
	
	
	public void printChatMsg(String msg) 
	{
		msgArea1.append(msg+"\n");
	}

	
	public void clearMsgArea()
	{
		msgArea.setText("");
	}
	

	public void clearChatMsgArea() 
	{
		this.msgArea1.setText("");
	}
	
	public void setExistence(int playerID)
	{
		presence[playerID] = true;
	}

	public void reset()
	{
		frame.setVisible(false);
		BigTwoDeck deck = new BigTwoDeck();
		deck.shuffle();
		game.start(deck);
		printMsg("Game Restarted by User!");
	}

	
	public void enable()
	{
		bigTwoPanel.setEnabled(true);
		playButton.setEnabled(true);
		passButton.setEnabled(true);
	}

	
	public void disable()
	{
		bigTwoPanel.setEnabled(false);
		playButton.setEnabled(false);
		passButton.setEnabled(false);
	}
	
	public void quit() 
	{
		System.exit(0);
	}
	
	public void playSound(String filename)
	 {
	       try{
	    	   	 String fileLocation = new String();
	    	     fileLocation = "src/Sounds/" + filename + ".wav";
	    	     AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(fileLocation));
	    	     Clip clip = AudioSystem.getClip();
	    	     clip.open(audioInputStream);
	    	     clip.start( );
	    	     
	    	    }
	    	   catch(Exception ex)
	    	   {  
	    		   
	    	   }
	

}

}