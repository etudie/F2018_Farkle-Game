import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
/**
 * Name: Player
 * Description: A class that contains several helper methods that set and get the player's parameters
 * @author SCOTT GRISSOM & XUE HUA
 * @version 08 NOV 2018
 */
public class Player
{

    private String name ; 
    private int score ;
    private int turns ;
    private int subtotal ; 

    /**
     * Constructor for objects of class Player
     */
    public Player(String pName)
    {
        // initialise instance variables
        name = pName ; 
        score = 0 ; 
        turns = 0 ; 
        subtotal = 0 ; 

    }

    public String getName(){
        return name ; 
    }

    public void setName (String n){
        name = n ; 
    }

    public int getScore(){
        return score ; 
    }

    public void setScore(int s) {
        score = s ; 
    }

    public int getSubtotal(){
        return subtotal ; 
    }

    public void setSubtotal(int s){
        subtotal = s ;
    }

    public int getTurns(){

        return turns ; 
    }

    public void setTurns(int t){
        turns = t ;
    }

    public void addToSubtotal (int amt){
        subtotal += amt;
    }

    public void updateScore(){
        score += subtotal ; 
        subtotal = 0 ; 
        turns += 1 ; 
    }

    public void newGame(){
        score = 0 ; 
        subtotal = 0 ;
        turns = 0 ; 
    }
}
