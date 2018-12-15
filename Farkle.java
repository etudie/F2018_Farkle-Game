import java.util.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
/**
 * PROGRAM : CIS 162 - PROJECT 4
 * DESCRIPTION: The backend of a Farkle game
 * @author Xue Hua
 * @version 3 Dec 2018
 */
public class Farkle
{
    /* INSTANCE VARIABLES */
    private Player player ;                             
    private Player bestPlayer;
    private Player[] players;
    private ArrayList<GVdie> theDice ;

    private int tally[] ; 
    private final int NUM_DICE = 6 ; 
    private final int ONES_SCORE = 100 ; 
    private final int FIVES_SCORE = 50 ; 
    private final int STRAIGHT_SCORE = 1000; 
    private final int THREE_PAIR_SCORE = 1000; 
    private final int WINNING_SCORE = 10000;

    private boolean okToRoll, okToPass, initialRoll;

    /* CONSTRUCTOR */
    public Farkle()
    {
        // initialise instance variables
        theDice = new ArrayList<GVdie>(); 

        for(int i = 0 ; i < NUM_DICE ; i++){
            theDice.add(new GVdie());
        }

        tally = new int[7]; //tally[0] is unused, only use tally[1] - tally[6] . They serve as a counter for the dice face shown. 

        players = new Player[3]; 
        players[0] = new Player("Player 1"); 
        players[1] = new Player("Player 2");
        players[2] = new Player("Player 3");
        player = players[0];

        okToRoll = true; 
        okToPass = false;

        for(GVdie d: theDice){
            d.setBlank();
        }
        bestPlayer = new Player("Best Player"); // Temporary name
        loadBestPlayer();                       // load current best player on file
        initialRoll = true; 
    }

    /*** SETUP ****
     * getActivePlayer(): Returns active player from the Player class 
     * setActivePlayer(): Set the current active player (used in updates, or generators)
     * ArrayList<GVdie> getDice(): render theDice;
     */
    public Player getActivePlayer(){    
        return player;
    }

    public void setActivePlayer(int id){
        player = players[id-1];
    }

    public boolean gameOver(){          
        if( player.getScore() >= WINNING_SCORE ){
            okToRoll = false ; 
            okToPass = false ;
            checkBestScore();                   //when game is over, immediately check the score with current bestPlayer
            return true ;
        }else{
            return false ; 
        }
    }

    public ArrayList<GVdie> getDice(){
        return theDice; 
    }

    /*** SCOREKEEPING ****
     * resetTally(): runs through tallies and sets them to zero. Is used throughout program. Especially Farkle & Next Turn
     * tallySelectedDice(): counts how many dice scores what. Resets the tally each time. 
     * tallyUnscoredDice(): counts how many dice are selected but unscored. Reset tally each time. 
     */

    private void resetTally(){              //Helper method to reset tally[1] - tally[6];
        for(int i = 1; i < tally.length ; i++){ 
            tally[i] = 0;
        }
    }

    private void tallySelectedDice(){
        resetTally();
        for(GVdie d: theDice){          // tally each die score. e.g. if player selected 3 of a kind for 5. tally[5] = 3
            if(d.isSelected()){
                int val = d.getValue(); 
                tally[val]++;           //counter
            }
        }
    }

    private void tallyUnscoredDice(){
        resetTally();
        for(GVdie d: theDice){          // tally how many of the dice is unscored. These are unscored dice. Will use later to check on Farkle.
            if(!d.isScored()){
                int val = d.getValue(); 
                tally[val]++;
            }
        }
    }

    /*** SCORING ****
     * hasStraight(): boolean if tally[1] to [6] equals to one
     * hasThreePairs(): counts if there are 3 pairs of any tally
     * hasOnes() & hasFives(): counts if tally[1] and [5] more than zero
     * hasOfKind(): counts if there are any dice in groups of three and up
     * ofKindHelper(): handles detailed scoring system of hasOfKind(); 
     */
    private boolean hasStraight(){
        boolean output = false; 
        for(int i = 1 ;  i < tally.length  ; i++){
            if(tally[i] == 1){
                output = true ; 
            }else{              //loop will break when tally[i] !=1, meaning it is NOT a straight and does not check the remaining values.
                output = false; 
                break;
            }
        }
        return output ;  
    }

    private boolean hasThreePairs(){
        boolean output = false ; 
        int counter = 0; 
        for(int i = 1 ;  i < tally.length ; i++){
            if(tally[i] == 2){
                counter++;
            }
        }
        if(counter == 3){
            output = true ; 
        }
        return output ;  
    }

    private boolean hasOnes(){
        if(tally[1] > 0){
            return true; 
        }
        else{
            return false;
        }
    }

    private boolean hasFives(){
        if (tally[5] > 0){
            return true; 
        }
        else{
            return false;
        }
    }

    private boolean hasOfKind(){
        boolean output = false;
        for(int i = 1 ; i < tally.length ; i++){
            if(tally[i] > 2){       //if tally[i] > 2, means it could have 'of a kind' ranging from 3-6
                output = true; 
                break;              // breaks so no need to go through rest after TRUE
            }
        }
        return output ; 
    }

    private int ofKindHelper(){
        int output = 0 ; 
        for(int i = 1 ; i < tally.length ; i++){
            if(tally[i] == 3){
                output += (100 * i ); 
            }
            else if(tally[i] == 4){
                output += ( 200 * i );
            }
            else if(tally[i] == 5){
                output += ( 300 * i );
            }
            else if(tally[i] == 6){
                output += ( 400 * i ); 
            }
            if( i == 1){
                output *= 10; 
            }
        }
        return output ; 
    }

    /*** GAMEPLAY ****
     * nextTurn(): sets all dice to blank, unscored and unselected
     * resetGame(): simulates a "next" turn. Resets scores. 
     * scoreSelectedDice(): if dice is selected, add score to subtotal when rollDice()
     * noDiceSelected(): checks if there is at least one dice selected
     * rollDice(): yikes. Easily the most complicated method. Look below for more info. TL;DR: Rolls dice.
     * okToPass() & okToRoll(): for GUI to use when greying out buttons
     * playerFarkled(): check if player farkled
     */
    private void nextTurn(){
        for(GVdie d : theDice){
            d.setScored(false);
            d.setSelected(false);
            d.setBlank();
        }
        initialRoll = true;
        okToRoll = true; 
        okToPass = false;
    }

    public void resetGame(){
        for(Player p : players){
            p.newGame(); 
        }
        nextTurn();
    }

    public void scoreSelectedDice(){
        tallySelectedDice(); 
        if( hasThreePairs() ){
            player.addToSubtotal( THREE_PAIR_SCORE );
        }
        else if( hasStraight() ){
            player.addToSubtotal( STRAIGHT_SCORE );
        }else if( hasOfKind() ){
            player.addToSubtotal( ofKindHelper() );
        }
        if( (hasOnes() || hasFives()) && !hasThreePairs() && !hasStraight() ){
            if(tally[1] < 3){                       // less than three because three or more is 'of a kind'
                player.addToSubtotal( tally[1] * ONES_SCORE );
            }if(tally[5] < 3 ){
                player.addToSubtotal( tally[5] * FIVES_SCORE );
            }
        }
        for(GVdie d:theDice){
            if(d.isSelected() && !d.isScored()){
                d.setScored(true);
            }
        }
    }

    private boolean noDiceSelected(){
        boolean output = true;
        for(GVdie d: theDice){
            if(d.isSelected()){
                output = false ; 
                break ; 
            }
        }
        return output ; 
    }

    public void rollDice(){
        int counter = 0 ; 
        if(initialRoll){
            initialRoll = false ; 
            okToPass = true ; 
            for(GVdie d: theDice){
                d.roll();
            }
        }else{
            if(!noDiceSelected()){
                scoreSelectedDice();            // scores the selected dice
                for(GVdie d: theDice){      //check to see if all dice is scored. If counter == 6 and not farkled, roll all. 

                    if(d.isScored()){
                        counter++; 
                    }
                }
                if(counter == 6){
                    for(GVdie d: theDice){      // rolls all dice
                        d.setSelected(false); 
                        d.setScored(false);
                        d.roll();
                    }
                }else{
                    for(GVdie d: theDice){      // rolls unscored dice
                        if(!d.isScored()){
                            d.roll();
                        }
                    }
                }
            }
        }

    }

    public void passDice(){
        scoreSelectedDice(); 
        player.updateScore(); 
        nextTurn();
    }

    public boolean okToRoll(){
        return okToRoll;
    }

    public boolean okToPass(){
        return okToPass ; 
    }

    public boolean playerFarkled(){
        tallyUnscoredDice(); 
        if(!hasThreePairs() && !hasStraight() && !hasOfKind() && !hasOnes() && !hasFives()){
            okToRoll = false ; 
            player.setSubtotal(0);
            return true ;
        }
        else{
            return false;
        }
    }

    /*** BEST PLAYER ****
     * getBestPlayer(): for GUI use, to retrieve the bestPlayer
     * checkBestScore(): used in gameOver() to check if any high scores are broken
     * setBestPlayer(): if checkBestPlayer() is true, use setBestPlayer to... set... the... best ... player 
     * saveBestPlayer(): saves player info into bestplayer.txt for future use
     * loadBestPlauyer(): loads player info from bestplayer.txt each start up
     */
    public Player getBestPlayer(){
        return bestPlayer;
    }

    public void checkBestScore(){
        if(player.getTurns() < bestPlayer.getTurns()){
            setBestPlayer(player);
        }
    }

    public void setBestPlayer(Player p){
        bestPlayer = p;
        saveBestPlayer();
    }

    public void saveBestPlayer(){
        FileWriter filew ;
        BufferedWriter writer;

        try{
            filew = new FileWriter ("bestplayer.txt");
            writer = new BufferedWriter(filew);

            writer.write(bestPlayer.getName());
            writer.newLine();
            writer.write(""+bestPlayer.getScore());     // convert to string because somehow it stored in emojis/unicode 
            writer.newLine();
            writer.write(""+bestPlayer.getTurns());
            writer.close();
        }catch(Exception e){
            System.out.println("Error");

        }
    }

    public void loadBestPlayer(){
        FileReader filer; 
        BufferedReader reader;

        try{
            filer = new FileReader("bestplayer.txt");
            reader = new BufferedReader(filer);

            bestPlayer.setName(reader.readLine());
            bestPlayer.setScore(Integer.parseInt(reader.readLine()));
            bestPlayer.setTurns(Integer.parseInt(reader.readLine()));

            filer.close();

        }catch(Exception e){
            System.out.println("Error");
        }            
    }

    public void setAllDice(int[] values){
        int i =0 ;
        for(int v : values){
            if(v < 1 && v> 6 ){
                v = 1; 
            }
        }
        for(GVdie d : theDice){
            d.roll();
            while(d.getValue() != values[i]){
                d.roll();
            }
            i++;
        }
    }

    public void selectDie(int id){
        theDice.get(id-1).setSelected(true);
    }

}

