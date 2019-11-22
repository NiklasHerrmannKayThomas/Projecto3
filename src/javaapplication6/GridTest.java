package javaapplication6;

import java.awt.GridLayout;
import javax.swing.*;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class GridTest extends JFrame
{
    private JPanel panel;
    private JLabel[][]labelneeded;
    private JButton[][]buttons;
    private int SIZE;
    private int SIZECol;
    private GridLayout experimentLayout;
    private int numberOfPlayer;
    private int playerOrder=0;
    private static int livingCellNumber=0;
    private int eventWinnerChecker=0;
    ImageIcon empty = new ImageIcon(getClass().getResource("WhiteSpace.png"));
    ImageIcon player1 = new ImageIcon(getClass().getResource("RedSpace.png"));
    ImageIcon player2 = new ImageIcon(getClass().getResource("BlueSpace.png"));
    ImageIcon player3 = new ImageIcon(getClass().getResource("YellowSPace.png"));
    private Cell gameBoard[][];
    public GridTest()
    {
        super("Connect 4");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800,800);
        setResizable(true);
        setLocationRelativeTo(null);
        
        playerNumberAndBoardSize();
        dynamicAllocation();
        experimentLayout =  new GridLayout(SIZE+1,SIZECol);
        panel = new JPanel();
        panel.setLayout(experimentLayout);

        buttons = new JButton[SIZE+1][SIZECol];
        labelneeded = new JLabel[SIZE+1][SIZECol];
        
        addButtons(panel);

        add(panel);
        setVisible(true);
    }
 
public void addButtons(JPanel panel) {
    for (int k = 0; k < SIZE; k++) {
       for (int j = 0; j < SIZECol; j++) {
               if(numberOfPlayer==2)
               {
                   buttons[k][j] = new JButton(empty);
                   buttons[k][j].addActionListener(new listenButtonTwoPlayers());
                   panel.add(buttons[k][j]);
               }
               if(numberOfPlayer==3)
               {
                   buttons[k][j] = new JButton(empty);
                   buttons[k][j].addActionListener(new listenButtonThreePlayers());
                   panel.add(buttons[k][j]);
               }
           }
       
      }
    buttons[SIZE][0] = new JButton("Mensaje");
    panel.add(buttons[SIZE][0]);
    buttons[SIZE][1] = new JButton("Salir");
    panel.add(buttons[SIZE][1]);
    labelneeded[SIZE][2] = new JLabel("Good luck brother, im counting on ya");
    panel.add(labelneeded[SIZE][2]);
}


 public void dynamicAllocation()
    {
      // Create dynamic Cell array to game board
        gameBoard = new Cell[getBoardSize()][getBoardSizeCol()];
        for (int i = 0; i <getBoardSize(); i++)
        {
            for (int j = 0; j <getBoardSizeCol(); j++)
            {
                gameBoard [i][j]=new Cell();
            }
        }
        livingCellNumber=0;
    }  
private class listenButtonTwoPlayers implements ActionListener
    {           
        @Override
        public void actionPerformed(ActionEvent e)
        {            
            try {
                int eventFlag = 0;
                int flagPlayerOrder=0;

                for(int i=getBoardSize()-1; i>=0; --i)
                {
                    for(int j=0; j<=getBoardSizeCol()-1; ++j)
                    {
                        if(eventFlag==0 && buttons[i][j]== e.getSource()) // Get the button component that was clicked
                        {  
                           if(flagPlayerOrder==0 && playerOrder%2==0) 
                           { 
                               // Player 1 Operations                           
                               // Fill the board from down to up
                                for(int k=0; k<=getBoardSize(); ++i)    
                                {
                                    if(gameBoard[i-k][j].getCellState()==0 && playerOrder%2==0)
                                    {
                                       if(i+1==getBoardSize() && gameBoard[i][j].getCellState()==0)
                                       {
                                       buttons[i-k][j].setIcon(player1);          // Set new icon to player 1 
                                       gameBoard[i-k][j].setAllPosition('X', i);  // Set cell parameters
                                       gameBoard[i-k][j].setCellState(1);
                                       ++livingCellNumber;  // Increase living cell number
                                       winnerPlayer(1);     // Check player 1 winning state
                                       flagPlayerOrder=1;   
                                       eventFlag=1;
                                       break; 
                                       }else{
                                           if(gameBoard[i+1][j].getCellState()!=0 && gameBoard[i][j].getCellState()!=2 && gameBoard[i][j].getCellState()!=1){
                                               buttons[i-k][j].setIcon(player1);            // Set new icon to player 2
                                               gameBoard[i-k][j].setAllPosition('X', i);    // Set cell parameters    
                                               gameBoard[i-k][j].setCellState(1);           // Set cell state
                                               ++livingCellNumber;
                                               winnerPlayer(1);
                                               flagPlayerOrder=1;
                                               eventFlag=1;
                                               break;
                                           }
                                       }
                                    } 
                                }

                                setUpperCellToEmpty(i,j);   // Set upper cell to empty 
                                System.out.println("... Player 1 played ... ");
                                ++playerOrder; // Change order from player 1 to player 2
                                break;
                            }

                            // Player 2 operations
                            if(flagPlayerOrder==0 && playerOrder%2==1) 
                            { 
                                for(int k=0; k<=getBoardSize(); ++i)
                                {
                                    if(gameBoard[i-k][j].getCellState()==0 && playerOrder%2==1);
                                    {
                                       if(i+1==getBoardSize() && gameBoard[i][j].getCellState()==0)
                                       {
                                       buttons[i-k][j].setIcon(player2);            // Set new icon to player 2
                                       gameBoard[i-k][j].setAllPosition('O', i);    // Set cell parameters    
                                       gameBoard[i-k][j].setCellState(2);           // Set cell state
                                       ++livingCellNumber;
                                       winnerPlayer(2);
                                       flagPlayerOrder=1;
                                       eventFlag=1;
                                       break;
                                       }else{
                                           if(gameBoard[i+1][j].getCellState()!=0 && gameBoard[i][j].getCellState()!=1 && gameBoard[i][j].getCellState()!=2){
                                               buttons[i-k][j].setIcon(player2);            // Set new icon to player 2
                                               gameBoard[i-k][j].setAllPosition('O', i);    // Set cell parameters    
                                               gameBoard[i-k][j].setCellState(2);           // Set cell state
                                               ++livingCellNumber;
                                               winnerPlayer(2);
                                               flagPlayerOrder=1;
                                               eventFlag=1;
                                               break;
                                           }
                                       }
                                    } 
                                }
                                setUpperCellToEmpty(i,j);
                                System.out.println("... Player 2 played ... ");
                                ++playerOrder;
                                break;
                            }
                        } // END EVENT SOURCE
                    } // END SECOND FOR LOOP         
                } // END FIRST FOR LOOP     
        }catch(Exception ex) 
        { 
            warningMessage(); 
        }     
       
        }
 }
private class listenButtonThreePlayers implements ActionListener
    {           
        @Override
        public void actionPerformed(ActionEvent e)
        {            
            try {
                int eventFlag = 0;
                int flagPlayerOrder=0;
                
                for(int i=getBoardSize()-1; i>=0; --i)
                {
                    for(int j=0; j<=getBoardSizeCol()-1; ++j)
                    {
                        if(eventFlag==0 && buttons[i][j]== e.getSource()) // Get the button component that was clicked
                        {  
                           if(flagPlayerOrder==0 && playerOrder==0) 
                           { 
                               // Player 1 Operations                           
                               // Fill the board from down to up
                                for(int k=0; k<=getBoardSize(); ++i)    
                                {
                                    if(gameBoard[i-k][j].getCellState()==0 && playerOrder==0)
                                    {
                                       if(i+1==getBoardSize() && gameBoard[i][j].getCellState()==0)
                                       {
                                       buttons[i-k][j].setIcon(player1);          // Set new icon to player 1 
                                       gameBoard[i-k][j].setAllPosition('X', i);  // Set cell parameters
                                       gameBoard[i-k][j].setCellState(1);
                                       ++livingCellNumber;  // Increase living cell number
                                       winnerPlayer(1);     // Check player 1 winning state
                                       flagPlayerOrder=1;   
                                       eventFlag=1;
                                       break; 
                                       }else{
                                           if(gameBoard[i+1][j].getCellState()!=0 && gameBoard[i][j].getCellState()!=2 && gameBoard[i][j].getCellState()!=1 && gameBoard[i][j].getCellState()!=3){
                                               buttons[i-k][j].setIcon(player1);            // Set new icon to player 2
                                               gameBoard[i-k][j].setAllPosition('X', i);    // Set cell parameters    
                                               gameBoard[i-k][j].setCellState(1);           // Set cell state
                                               ++livingCellNumber;
                                               winnerPlayer(1);
                                               flagPlayerOrder=1;
                                               eventFlag=1;
                                               break;
                                           }
                                       }
                                    } 
                                }

                                setUpperCellToEmpty(i,j);   // Set upper cell to empty 
                                System.out.println("... Player 1 played ... ");
                                ++playerOrder; // Change order from player 1 to player 2
                                break;
                            }

                            // Player 2 operations
                            if(flagPlayerOrder==0 && playerOrder==1) 
                            { 
                                for(int k=0; k<=getBoardSize(); ++i)
                                {
                                    if(gameBoard[i-k][j].getCellState()==0 && playerOrder==1)
                                    {
                                       if(i+1==getBoardSize() && gameBoard[i][j].getCellState()==0)
                                       {
                                       buttons[i-k][j].setIcon(player2);            // Set new icon to player 2
                                       gameBoard[i-k][j].setAllPosition('O', i);    // Set cell parameters    
                                       gameBoard[i-k][j].setCellState(2);           // Set cell state
                                       ++livingCellNumber;
                                       winnerPlayer(2);
                                       flagPlayerOrder=1;
                                       eventFlag=1;
                                       break;
                                       }else{
                                           if(gameBoard[i+1][j].getCellState()!=0 && gameBoard[i][j].getCellState()!=1 && gameBoard[i][j].getCellState()!=2 && gameBoard[i][j].getCellState()!=3){
                                               buttons[i-k][j].setIcon(player2);            // Set new icon to player 2
                                               gameBoard[i-k][j].setAllPosition('O', i);    // Set cell parameters    
                                               gameBoard[i-k][j].setCellState(2);           // Set cell state
                                               ++livingCellNumber;
                                               winnerPlayer(2);
                                               flagPlayerOrder=1;
                                               eventFlag=1;
                                               break;
                                           }
                                       }
                                    } 
                                }
                                setUpperCellToEmpty(i,j);
                                System.out.println("... Player 2 played ... ");
                                ++playerOrder;
                                break;
                            }
                            if(flagPlayerOrder==0 && playerOrder==2)
                            {
                               for (int k=0; k<=getBoardSize(); ++i)
                               {
                                   if (gameBoard[i-k][j].getCellState()==0 && playerOrder==2)
                                   {
                                       if(i+1==getBoardSize() && gameBoard[i][j].getCellState()==0)
                                       {
                                       buttons[i-k][j].setIcon(player3);            // Set new icon to player 3
                                       gameBoard[i-k][j].setAllPosition('T', i);    // Set cell parameters    
                                       gameBoard[i-k][j].setCellState(3);           // Set cell state
                                       ++livingCellNumber;
                                       winnerPlayer(3);
                                       flagPlayerOrder=1;
                                       eventFlag=1;
                                       break;
                                       }else{
                                           if(gameBoard[i+1][j].getCellState()!=0 && gameBoard[i][j].getCellState()!=1 && gameBoard[i][j].getCellState()!=2 && gameBoard[i][j].getCellState()!=3){
                                               buttons[i-k][j].setIcon(player3);            // Set new icon to player 3
                                               gameBoard[i-k][j].setAllPosition('T', i);    // Set cell parameters    
                                               gameBoard[i-k][j].setCellState(3);           // Set cell state
                                               ++livingCellNumber;
                                               winnerPlayer(3);
                                               flagPlayerOrder=1;
                                               eventFlag=1;
                                               break;
                                           }
                                       }
                                   }
                               }
                               setUpperCellToEmpty(i,j);
                               System.out.println("... Player 3 played ... ");
                               playerOrder=0;
                               break;
                            }
                        } // END EVENT SOURCE
                    } // END SECOND FOR LOOP         
                } // END FIRST FOR LOOP     
        }catch(Exception ex) 
        { 
            warningMessage(); 
        }     
       
        }
 }
public void warningMessage()
    {
        JFrame frameWarning = new JFrame();           
        JOptionPane.showMessageDialog(frameWarning,
        "Invalid Movement !!\nThe cell is not empty.", "Warning",
        JOptionPane.WARNING_MESSAGE);
    }
public void playerNumberAndBoardSize()
    {
        // User inputs from input dialogs
        String playerNumber = JOptionPane.showInputDialog( "Player Number (2 or 3)" );
        String boardSize = JOptionPane.showInputDialog( "Game Board Size(Vertical)" );
        String boardSizeCol = JOptionPane.showInputDialog( "Game Board Length(Horizontal)" );

        if (playerNumber.length()==0 || boardSize.length()==0 || boardSizeCol.length()==0)
        {
            JFrame frameInputError = new JFrame();
            JOptionPane.showMessageDialog(frameInputError,
                    "Blank spaces found, error !!",
                    "Insufficient Data to Start game",
                    JOptionPane.ERROR_MESSAGE);
                    System.exit(0);
        }
        // Get player number and size of board
        numberOfPlayer  = Integer.parseInt(playerNumber); 
        int sizeOfBoard = Integer.parseInt(boardSize);
        int lengthOfBoard = Integer.parseInt(boardSizeCol);

        // User input check to game board
        if(sizeOfBoard < 6)
        {
           JFrame frameInputError = new JFrame();
           JOptionPane.showMessageDialog(frameInputError,
           "Board size must be greater than 6  !!",
           "Board Size Error",
           JOptionPane.ERROR_MESSAGE);
           System.exit(0);
        }
        
        if (lengthOfBoard < 8)
        {
           JFrame frameInputError = new JFrame();
           JOptionPane.showMessageDialog(frameInputError,
           "Board Length must be greater than 8  !!",
           "Board Length Error",
           JOptionPane.ERROR_MESSAGE);
           System.exit(0);
        }
        
        setBoardSize(sizeOfBoard+1);
        setBoardSizeCol(lengthOfBoard);
    }
public void setBoardSize(int newSize)
    {
        SIZE = newSize;
    }
public int getBoardSize(){
    return SIZE;
}
public void setBoardSizeCol(int newSize)
    {
        SIZECol = newSize;
    }
public int getBoardSizeCol(){
    return SIZECol;
}
public void setUpperCellToEmpty(int rowPos, int columnPos)
    {
        try 
        {
            gameBoard[rowPos-1][columnPos].setCellState(0);    
        }   
        catch (Exception ex) 
        { }      
    } 
public void startAgain()
   {
       
        for(int i=0; i<getBoardSize(); ++i)
        {         
            for(int j=0; j<getBoardSizeCol(); ++j)
            {
                gameBoard[i][j].setCellState(-99);  // Initial Value
                buttons[i][j].setIcon(empty);// Put the empty cell icon               
            }
        }
        
        setVisible(false);                            // Unvisible previous game board
        GridTest newGame = new GridTest();           // New Game Object
   }

public void showResult(int winnerPlayer)
   {
       JFrame frameShowResult = new JFrame();       
       if(winnerPlayer==1 && eventWinnerChecker==0)
       {
            JOptionPane.showMessageDialog(frameShowResult,
            "\nWinner : Player 1\n\nThe new game will start.\n\n",
            "End Game",
            JOptionPane.INFORMATION_MESSAGE);
            winnerPlayer=0;
            ++eventWinnerChecker;
            startAgain(); 
       }
       if(winnerPlayer==3 && eventWinnerChecker==0)
       {
            JOptionPane.showMessageDialog(frameShowResult,
            "\nWinner : Player 3\n\nThe new game will start.\n\n",
            "End Game",
            JOptionPane.INFORMATION_MESSAGE);
            winnerPlayer=0;
            ++eventWinnerChecker;
            startAgain();
       }
       if(winnerPlayer==2 && eventWinnerChecker==0)
       {
            JOptionPane.showMessageDialog(frameShowResult,
            "\nWinner : Player 2\n\nThe new game will start.\n\n",
            "End Game",
            JOptionPane.INFORMATION_MESSAGE);
            winnerPlayer=0;
            ++eventWinnerChecker;
            startAgain();
       }
       if(winnerPlayer==4 && eventWinnerChecker==0){
           JOptionPane.showMessageDialog(frameShowResult,
            "\nDraw, nobody wins!\n\nThe new game will start.\n\n",
            "End Game", JOptionPane.INFORMATION_MESSAGE);
           winnerPlayer=0;
           ++eventWinnerChecker;
           startAgain();
       }
   }
public void winnerPlayer(int winner)
    {
        for(int i=0; i<getBoardSize(); ++i)
        {         
            for(int j=0; j<getBoardSizeCol(); ++j)
            {     
                if(gameBoard[i][j].getCellState() == winner)
                {
                    // CHECK UP TO DOWN POSITIONS
                    if(i+3<getBoardSize())
                    {
                        if(gameBoard[i+1][j].getCellState() == winner && gameBoard[i+2][j].getCellState() == winner && gameBoard[i+3][j].getCellState() == winner)  
                        {
                            eventWinnerChecker=0;
                            if(winner==1)
                                showResult(1);
                            if(winner==3)
                                showResult(3);
                            if(winner==2);
                                showResult(2);
                        }
                    }
                    // CHECK LEFT TO RIGHT POSITION
                    if(j + 3 <getBoardSizeCol())
                    {
                        if(gameBoard[i][j+1].getCellState() == winner && gameBoard[i][j+2].getCellState() == winner && gameBoard[i][j+3].getCellState() == winner)
                        { 
                            eventWinnerChecker=0;
                            if(winner==1)
                                showResult(1);
                            if(winner==3)
                                showResult(3);
                            if(winner==2);
                                showResult(2);
                                
                        }
                    }

                    // CHECK DIAGONAL LEFT TO RIGHT POSITION
                    if(i  < getBoardSize()- 3 && j<getBoardSizeCol()-3)
                    {
                        if(gameBoard[i+1][j+1].getCellState() == winner && gameBoard[i+2][j+2].getCellState() == winner && gameBoard[i+3][j+3].getCellState() == winner)
                        {
                            eventWinnerChecker=0;
                            if(winner==1)
                                showResult(1);
                            if(winner==3)
                                showResult(3);
                            if(winner==2);
                                showResult(2);
                        }   
                    }

                    // CHECK DIAGONAL RIGHT TO LEFT POSITION
                    if(i  < getBoardSize()- 3 && j - 3 >= 0 )
                    {
                        if(gameBoard[i+1][j-1].getCellState() == winner && gameBoard[i+2][j-2].getCellState() == winner && gameBoard[i+3][j-3].getCellState() == winner)
                        {
                            eventWinnerChecker=0;
                            if(winner==1)
                                showResult(1);
                            if(winner==3)
                                showResult(3);
                            if(winner==2);
                                showResult(2);
                        } 
                    }
                    System.out.println(SIZE*SIZECol);
                    System.out.println(livingCellNumber);
                    if(SIZE*SIZECol==livingCellNumber){
                        eventWinnerChecker=0;
                        showResult(4);
                    }
                }         
            }             
        } 
    }
}
