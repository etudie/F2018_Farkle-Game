import java.awt.*;
import java.util.ArrayList;
import java.awt.event.*;
import javax.swing.*;
import java.text.*;
import javax.swing.AbstractButton;
/**
 * Farkle Graphical User Interface
 * FarkleGUI : A Special Kind of Madness 
 * 
 * This GUI uses several helper methods. It will serve as the userface for the Farkle Game, complete with 3 players, graphical dice 
 * and several other gameplay options
 *
 * @author SCOTT GRISSOM & XUE HUA
 * @version 08 November 2018
 */


public class FarkleGUI extends JFrame implements ActionListener
{
    /* INSTANCE VARIABLES*/
    private Farkle theGame; 
    private GridBagConstraints position;
    private Player player1, player2, player3, activePlayer,bestPlayer ; 
    // menu items
    JMenuItem quitItem, newGameItem, bestScoreItem; 
    // radio buttons for the 3 players
    private JRadioButton player1Radio, player2Radio, player3Radio; 
    // pass and roll buttons for the game
    private JButton rollBtn, passBtn;                 
    //JLabels
    private JLabel turn1Lbl, sub1Lbl, score1Lbl;
    private JLabel turn2Lbl, sub2Lbl, score2Lbl;
    private JLabel turn3Lbl, sub3Lbl, score3Lbl;

    public static void main(String[] args){             // Main Method
        FarkleGUI frame = new FarkleGUI();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    /*** FARKLEGUI CONSTRUCTOR***/
    public FarkleGUI()
    {
        /*** GUI SETUP ***/
        setupFarkleGUI();

        /*** SET UP PLAYER ***/
        setupPlayers();

        /*** ARRAY OF DICE ***/
        renderGVdie();

        /*** PLAYER COLUMNS ***/
        setupPlayerCols();

        /*** ROLL AND PASS BUTTON ***/
        setupRollPass();
    }

    @Override
    public void actionPerformed(ActionEvent event){  

        /* Roll Dice */
        if(event.getSource() == rollBtn){
            theGame.rollDice();
            updateStats();
            if(theGame.playerFarkled()){

                JOptionPane.showMessageDialog(null, "FARKLE !");
                passDiceHelper();
            }
            if(theGame.gameOver()){
                gameOver();
            }
        }

        /* Pass Dice */
        if(event.getSource() == passBtn){
            passDiceHelper();

            if(theGame.gameOver()){
                gameOver();
            }
        }

        /* Menu Bar */
        if(event.getSource() == quitItem){
            System.exit(0);
        }
        if(event.getSource() == newGameItem){
            theGame.resetGame();
            updateStats();
            activePlayer = player3 ; 
            switchTurn();
        }
        if(event.getSource() == bestScoreItem){
            bestPlayer = theGame.getBestPlayer();
            JOptionPane.showMessageDialog(null, "Name: " +bestPlayer.getName() +"\nScore: "+ bestPlayer.getScore() +"\nTurns: " +bestPlayer.getTurns());
        }

        /* Update Active Player */
        if (player1Radio.isSelected()){
            theGame.setActivePlayer(1);
            activePlayer = player1;
        }else if (player2Radio.isSelected()){
            theGame.setActivePlayer(2);
            activePlayer = player2;
        }else if (player3Radio.isSelected()){
            activePlayer = player3;
            theGame.setActivePlayer(3);
        }

        /* Check if buttons are okay to roll after each event */
        rollBtn.setEnabled(theGame.okToRoll()); 
        passBtn.setEnabled(theGame.okToPass());

        
    }

    /*** Update stats helper method. Should be executed after each action: Roll, Pass, Farkle, etc. ***/
    public void updateStats()
    {   

        turn1Lbl.setText("Turns : " + player1.getTurns());
        sub1Lbl.setText("Subtotal : " + player1.getSubtotal());
        score1Lbl.setText("Score : " + player1.getScore());

        turn2Lbl.setText("Turns : " + player2.getTurns());
        sub2Lbl.setText("Subtotal : " + player2.getSubtotal());
        score2Lbl.setText("Score : " + player2.getScore());

        turn3Lbl.setText("Turns : " + player3.getTurns());
        sub3Lbl.setText("Subtotal : " + player3.getSubtotal());
        score3Lbl.setText("Score : " + player3.getScore());

    }

    /*** switchTurn() : A method that helps with setEnabled() when one radio is selected ***/
    public void switchTurn(){

        if(activePlayer == player1){
            player2Radio.setSelected(true);
            player1Radio.setEnabled(false); 
            player2Radio.setEnabled(true);
            player3Radio.setEnabled(false); 

        }else if(activePlayer == player2){
            player3Radio.setSelected(true);
            player1Radio.setEnabled(false); 
            player2Radio.setEnabled(false);
            player3Radio.setEnabled(true);

        }else  if(activePlayer == player3){
            player1Radio.setSelected(true);
            player1Radio.setEnabled(true); 
            player2Radio.setEnabled(false);
            player3Radio.setEnabled(false);

        }
    }

    /*** gameOver() : displays the winner ***/
    public void gameOver(){
        rollBtn.setEnabled(false); 
        passBtn.setEnabled(false);
        Player winner;
        if(player1.getScore() > player2.getScore()){
            winner = player1;
        }else{
            winner = player2;
        }
        if(winner.getScore() < player3.getScore()){
            winner = player3;
        }
        JOptionPane.showMessageDialog(null, winner.getName() + " WINS! " );
        
    }

    /*** setupPlayers() : To set up the game, using theGame.setActivePlayer(); ***/
    public void setupPlayers()
    {
        activePlayer = player1 ; 

        theGame.setActivePlayer(1);
        player1 = theGame.getActivePlayer();

        theGame.setActivePlayer(2);
        player2 = theGame.getActivePlayer();

        theGame.setActivePlayer(3);
        player3 = theGame.getActivePlayer();

        theGame.setActivePlayer(1);

        player1Radio = new JRadioButton(player1.getName(), true);
        player2Radio = new JRadioButton(player2.getName());
        player3Radio = new JRadioButton(player3.getName());

        ButtonGroup playerGroup = new ButtonGroup();
        playerGroup.add(player1Radio);
        playerGroup.add(player2Radio);
        playerGroup.add(player3Radio);
    }

    /*** setupPlayerCols(): A method that sets up graphics such as Jbuttons and JLabels ***/
    public void setupPlayerCols()
    {
        /*** PLAYER COLUMNS ***/
        /* PLAYER ONE COL */

        position.gridy = 2;
        position.gridx = 3;

        add(player1Radio, position); //radio button

        position.gridy = 3;         // turns, subtotal and score
        turn1Lbl = new JLabel("Turns : " + player1.getTurns());
        add(turn1Lbl, position);
        position.gridy = 4;
        sub1Lbl = new JLabel("Subtotal : "+ player1.getSubtotal());
        add(sub1Lbl, position);
        position.gridy = 5;
        score1Lbl = new JLabel("Score : "+ player1.getScore());
        add(score1Lbl, position);

        /* PLAYER TWO COL */

        position.gridx = 4; 
        position.gridy = 2; 

        add(player2Radio, position);
        player2Radio.setEnabled(false); 
        position.gridy = 3;// turns, subtotal and score
        turn2Lbl = new JLabel("Turns : " + player2.getTurns());
        add(turn2Lbl, position);
        position.gridy = 4;
        sub2Lbl = new JLabel("Subtotal : "+ player2.getSubtotal());
        add(sub2Lbl, position);
        position.gridy = 5;
        score2Lbl = new JLabel("Score : "+ player2.getScore());
        add(score2Lbl, position);

        /* PLAYER THREE COL */

        position.gridx = 5;
        position.gridy = 2;
        add(player3Radio, position);  
        player3Radio.setEnabled(false); 

        position.gridy = 3; // turns, subtotal and score
        turn3Lbl = new JLabel("Turns : " + player3.getTurns());
        add(turn3Lbl, position);
        position.gridy = 4;
        sub3Lbl = new JLabel("Subtotal : "+ player3.getSubtotal());
        add(sub3Lbl, position);
        position.gridy = 5;
        score3Lbl = new JLabel("Score : "+ player3.getScore());
        add(score3Lbl, position);
    }

    /*** setupRollPass(): a setup method for roll and pass buttons of JButton type ***/
    public void setupRollPass()
    {
        /*** ROLL AND PASS BUTTON ***/
        rollBtn = new JButton("Roll");
        passBtn = new JButton("Pass");

        position.gridx = 1; 
        position.gridy = 3;

        add(rollBtn,position);
        rollBtn.addActionListener(this);

        position.gridx = 2; 
        add(passBtn,position);
        passBtn.addActionListener(this);
        passBtn.setEnabled(false);
    }

    /*** setupFarkleGUI() : to set up the basic parameters of the GUI. e.g Font, font size, insets, etc. Also instatiating the game ***/
    public void setupFarkleGUI()
    {
        setTitle("GVSU FARKLE");
        theGame = new Farkle();

        setupMenus();       // hide away details of creating menus
        setLayout(new GridBagLayout());         // set layout manager and fonts
        position = new GridBagConstraints();
        Font myFont = new Font("sans-serif", Font.BOLD, 12); 
        // make components left justified with padding on right
        position.anchor = GridBagConstraints.LINE_START;
        position.insets = new Insets(0,0,20,20);
    }

    /*** renderGVdie(): Renders the GV Die to subsequent cols ***/
    public void renderGVdie()
    {
        int i ; 
        position.gridx = 3;
        position.gridy = 1;
        ArrayList <GVdie> theDice ;
        theDice = theGame.getDice();
        for(i = 0 ; i < theDice.size() ;  i++){
            theDice.get(i);
            position.gridx = i;
            add(theDice.get(i),position);
        }
    }

    /*** passDiceHelper() : call when need to simulate passDice. Used also in Farkle ***/
    public void passDiceHelper(){
        theGame.passDice();
        updateStats();
        switchTurn();
    }

    /*** setupMenus() : to Determine the functions of the menu options ***/
    private void setupMenus (){

        // create and display the menu bar
        JMenuBar menusBar = new JMenuBar();
        setJMenuBar(menusBar);

        // create a menu and add to the menubar
        JMenu fileMenu = new JMenu("File");
        menusBar.add(fileMenu);

        // FIX ME: create a menu item for About (see below) 
        newGameItem = new JMenuItem("New Game");
        fileMenu.add(newGameItem);
        newGameItem.addActionListener(this); 

        bestScoreItem = new JMenuItem("Best Score");
        fileMenu.add(bestScoreItem);
        bestScoreItem.addActionListener(this); 

        // create a menu item for Quit
        quitItem = new JMenuItem("Quit");
        fileMenu.add(quitItem);
        quitItem.addActionListener(this);
    }
}
