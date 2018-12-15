
/**
 * PROGRAM : CIS 162 - PROJECT 4 - FarkleTest
 * DESCRIPTION: The testing of a Farkle game
 * @author Xue Hua
 * @version 3 Dec 2018
 */
public class FarkleTest
{

    public static void main(String [] args){
        Farkle game = new Farkle(); 
        int pass = 0 , fail = 0 , totalTest = 0 ; 

        System.out.println("\n Testing Begins");

        System.out.println("\n\n------------------------ FARKLE TEST ------------------------ "); 
        totalTest++; // Test farkle (1/2)
        game.passDice();       
        game.setAllDice(new int[] {2,2,3,4,6,6}); 
        System.out.println("\nDice: {2,2,3,4,6,6} ");
        for(int i =1; i < 7 ; i++){
            game.selectDie(i);
        }
        if(game.playerFarkled()){
            pass++;
            System.out.println("PASSED --> Farkled");
        }else{
            fail++;

            System.out.println("FAILED");
        }

        totalTest++;
        game.passDice();    //Test farkle (2/2)
        game.setAllDice(new int[] {1,2,3,4,5,6}); 
        System.out.println("\nDice: {1,2,3,4,5,6}");
        for(int i =1; i < 7 ; i++){
            game.selectDie(i);
        }
        if(game.playerFarkled()){
            fail++ ; 
            System.out.println("FAILED ");
        }else{
            pass++; 
            System.out.println("PASSED --> Does not farkle");
        }

        System.out.println("\n\n------------------------ STRAIGHT TEST ------------------------ ");

        totalTest++;
        game.passDice();    //Test straight (1/)
        game.setAllDice(new int[] {1,2,3,4,5,6}); 
        System.out.println("\nDice: {1,2,3,4,5,6}");

        for(int i =1; i < 7 ; i++){
            game.selectDie(i);
        }
        game.scoreSelectedDice();

        if(game.getActivePlayer().getSubtotal() == 1000){
            pass++; 
            System.out.println("PASSED --->" + game.getActivePlayer().getSubtotal()); 
        }else{
            fail++;
            System.out.println("FAILED --->" + game.getActivePlayer().getSubtotal());
        }
        
        totalTest++;
        game.passDice();  
        game.setAllDice(new int[] {2,3,1,4,6,5}); 
        System.out.println("\nDice: {2,3,1,4,6,5}");

        for(int i =1; i < 7 ; i++){
            game.selectDie(i);
        }
        game.scoreSelectedDice();

        if(game.getActivePlayer().getSubtotal() == 1000){
            pass++; 
            System.out.println("PASSED --->" + game.getActivePlayer().getSubtotal()); 
        }else{
            fail++;
            System.out.println("FAILED --->" + game.getActivePlayer().getSubtotal());
        }

        System.out.println("\n\n------------------------ THREE UNIQUE PAIRS TEST ------------------------ "); 

        totalTest++;
        game.passDice();    //Test three unique pairs 
        game.setAllDice(new int[] {1,1,2,2,3,3}); 
        System.out.println("\n\nDice: {1,1,2,2,3,3}");
        for(int i =1; i < 7 ; i++){
            game.selectDie(i);
        }
        game.scoreSelectedDice(); 
        if(game.getActivePlayer().getSubtotal() == 1000){
            pass++; 
            System.out.println("PASSED --->" + game.getActivePlayer().getSubtotal()); 
        }else{
            fail++;
            System.out.println("FAILED --->" + game.getActivePlayer().getSubtotal());
        }

        totalTest++;
        game.passDice();  
        game.setAllDice(new int[] {5,2,3,3,2,5}); 
        System.out.println("\n\nDice: {5,2,3,3,2,5}");
        for(int i =1; i < 7 ; i++){
            game.selectDie(i);
        }
        game.scoreSelectedDice(); 
        if(game.getActivePlayer().getSubtotal() == 1000){
            pass++; 
            System.out.println("PASSED --->" + game.getActivePlayer().getSubtotal()); 
        }else{
            fail++;
            System.out.println("FAILED --->" + game.getActivePlayer().getSubtotal());
        }
        System.out.println("\n\n------------------------ OF A KIND TEST ------------------------ "); 

        totalTest++;
        game.passDice();    
        game.setAllDice(new int[] {1,1,1,2,2,6}); 
        System.out.println("\nDice: {1,1,1,2,2,6}");
        for(int i =1; i < 7 ; i++){
            game.selectDie(i);
        }
        game.scoreSelectedDice(); 
        if(game.getActivePlayer().getSubtotal() == 1000){
            pass++; 
            System.out.println("PASSED --->" + game.getActivePlayer().getSubtotal()); 
        }else{
            fail++;
            System.out.println("FAILED --->" + game.getActivePlayer().getSubtotal());
        }

        totalTest++;
        game.passDice();    
        game.setAllDice(new int[] {1,1,1,1,2,6}); 
        System.out.println("\nDice: {1,1,1,1,2,6}");
        for(int i =1; i < 7 ; i++){
            game.selectDie(i);
        }
        game.scoreSelectedDice(); 
        if(game.getActivePlayer().getSubtotal() == 2000){
            pass++; 
            System.out.println("PASSED --->" + game.getActivePlayer().getSubtotal()); 
        }else{
            fail++;
            System.out.println("FAILED --->" + game.getActivePlayer().getSubtotal());
        }

        totalTest++;
        game.passDice();   
        game.setAllDice(new int[] {1,1,1,1,1,6}); 
        System.out.println("\nDice: {1,1,1,1,1,6}");
        for(int i =1; i < 7 ; i++){
            game.selectDie(i);
        }
        game.scoreSelectedDice(); 
        if(game.getActivePlayer().getSubtotal() == 3000){
            pass++; 
            System.out.println("PASSED --->" + game.getActivePlayer().getSubtotal()); 
        }else{
            fail++;
            System.out.println("FAILED --->" + game.getActivePlayer().getSubtotal());
        }

        totalTest++;
        game.passDice();    
        game.setAllDice(new int[] {5,5,5,5,5,5}); 
        System.out.println("\nDice: {5,5,5,5,5,5}");
        for(int i =1; i < 7 ; i++){
            game.selectDie(i);
        }
        game.scoreSelectedDice(); 
        if(game.getActivePlayer().getSubtotal() == 2000){
            pass++; 
            System.out.println("PASSED --->" + game.getActivePlayer().getSubtotal()); 
        }else{
            fail++;
            System.out.println("FAILED --->" + game.getActivePlayer().getSubtotal());
        }

                totalTest++;
        game.passDice();    
        game.setAllDice(new int[] {4,4,2,4,5,1}); 
        System.out.println("\nDice: {4,4,2,4,5,1}");
        for(int i =1; i < 7 ; i++){
            game.selectDie(i);
        }
        game.scoreSelectedDice(); 
        if(game.getActivePlayer().getSubtotal() == 550){
            pass++; 
            System.out.println("PASSED --->" + game.getActivePlayer().getSubtotal()); 
        }else{
            fail++;
            System.out.println("FAILED --->" + game.getActivePlayer().getSubtotal());
        }
        System.out.println("\n\n------------------------ FIVES TEST ------------------------ "); 

        totalTest++;
        game.passDice();    
        game.setAllDice(new int[] {1,2,2,4,4,6}); 
        System.out.println("\nDice: {1,2,2,4,4,6}");
        for(int i =1; i < 7 ; i++){
            game.selectDie(i);
        }
        game.scoreSelectedDice(); 
        if(game.getActivePlayer().getSubtotal() == 100){
            pass++; 
            System.out.println("PASSED  --->" + game.getActivePlayer().getSubtotal()); 
        }else{
            fail++;
            System.out.println("FAILED --->" + game.getActivePlayer().getSubtotal());
        }

        totalTest++;
        game.passDice();    
        game.setAllDice(new int[] {5,5,2,2,1,4}); 
        System.out.println("\nDice: {5,5,2,2,1,4}");
        for(int i =1; i < 7 ; i++){
            game.selectDie(i);
        }
        game.scoreSelectedDice(); 
        if(game.getActivePlayer().getSubtotal() == 200){
            pass++; 
            System.out.println("PASSED  --->" + game.getActivePlayer().getSubtotal()); 
        }else{
            fail++;
            System.out.println("FAILED --->" + game.getActivePlayer().getSubtotal());
        }
        
        System.out.println("\n\n------------------------ ONES TEST ------------------------ "); 

        totalTest++;
        game.passDice();  
        game.setAllDice(new int[] {2,2,3,3,5,6}); 
        System.out.println("\nDice: {1,2,2,4,4,6}");
        for(int i =1; i < 7 ; i++){
            game.selectDie(i);
        }
        game.scoreSelectedDice(); 
        if(game.getActivePlayer().getSubtotal() == 50){
            pass++; 
            System.out.println("PASSED --->" + game.getActivePlayer().getSubtotal()); 
        }else{
            fail++;
            System.out.println("FAILED --->" + game.getActivePlayer().getSubtotal());
        }

        totalTest++;
        game.passDice();  
        game.setAllDice(new int[] {6,2,2,1,3,1}); 
        System.out.println("\nDice: {6,2,2,1,3,1}");
        for(int i =1; i < 7 ; i++){
            game.selectDie(i);
        }
        game.scoreSelectedDice(); 
        if(game.getActivePlayer().getSubtotal() == 200){
            pass++; 
            System.out.println("PASSED --->" + game.getActivePlayer().getSubtotal()); 
        }else{
            fail++;
            System.out.println("FAILED --->" + game.getActivePlayer().getSubtotal());
        }
        
       
        
        System.out.println("\n\n------------------------ RESULTS ------------------------ "); 
        System.out.print("Passed: " + pass + " / " + totalTest);
        System.out.print("\t\tFailed: " + fail + " / " + totalTest);
        System.out.println("\n\nTesting complete");

    }
}
