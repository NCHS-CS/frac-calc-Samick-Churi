// Samick Churi
// Period 6
// Fraction Calculator Project

import java.util.*;

// TODO: Description of what this program does goes here.
public class FracCalc {

   // It is best if we have only one console object for input
   public static Scanner console = new Scanner(System.in);
   
   // This main method will loop through user input and then call the
   // correct method to execute the user's request for help, test, or
   // the mathematical operation on fractions. or, quit.
   // DO NOT CHANGE THIS METHOD!!
   public static void main(String[] args) {
   
      // initialize to false so that we start our loop
      boolean done = false;
      
      // When the user types in "quit", we are done.
      while (!done) {
         // prompt the user for input
         String input = getInput(console);
         
         // special case the "quit" command
         if (input.equalsIgnoreCase("quit")) {
            done = true;
         } else if (!UnitTestRunner.processCommand(input, FracCalc::processCommand)) {
        	   // We allowed the UnitTestRunner to handle the command first.
            // If the UnitTestRunner didn't handled the command, process normally.
            String result = processCommand(input);
            
            // print the result of processing the command
            System.out.println(result);
         }
      }
      
      System.out.println("Goodbye!");
      console.close();
   }

   // Prompt the user with a simple, "Enter: " and get the line of input.
   // Return the full line that the user typed in.
   public static String getInput(Scanner console) {
      // TODO: Implement this method
      System.out.print("Enter: ");
      String input = console.nextLine();
       return input;

   }
   
   // processCommand will process every user command except for "quit".
   // It will return the String that should be printed to the console.
   // This method won't print anything.
   // DO NOT CHANGE THIS METHOD!!!
   public static String processCommand(String input) {

      if (input.equalsIgnoreCase("help")) {
         return provideHelp();
      }
      
      // if the command is not "help", it should be an expression.
      // Of course, this is only if the user is being nice.
      return processExpression(input);
   }
   
   // Lots work for this project is handled in here.
   // Of course, this method will call LOTS of helper methods
   // so that this method can be shorter.
   // This will calculate the expression and RETURN the answer.
   // This will NOT print anything!
   // Input: an expression to be evaluated
   //    Examples: 
   //        1/2 + 1/2
   //        2_1/4 - 0_1/8
   //        1_1/8 * 2
   // Return: the fully reduced mathematical result of the expression
   //    Value is returned as a String. Results using above examples:
   //        1
   //        2 1/8
   //        2 1/4

   public static String processExpression(String input) {
      //Extracts the first number string, operator, and second number string
      String first = numberOne(input); //Takes the first operand string
      String op = Operator(input);
      String second = numberTwo(input);
      //Converts the 2 numbers to strings which may or may not be mixed or whole numbers
      //Converts it into improper
      String fraction1 = improperFrac(first);
      String fraction2 = improperFrac(second);
      //Takes the numerators from the improper fraction strings 
      int improper1 = Numerator(fraction1); 
      int improper2 = Numerator(fraction2);
      //Takes the denominators from the improper fraction strings
      int den1 = getDenom(fraction1);
      int den2 = getDenom(fraction2);
      //Calculates based on the operator
      String answer = operatingSystems(improper1,improper2,den1,den2,op);
      //calls the operatingSystems method to return value

      //takes the numerator and denominators of the final fractions
      int finalnumerator = Numerator(answer);
      int finaldenominator = Denominator(answer);
      //Formats the final fraction properly into a mixed number and decreases it
      return prettier(finalnumerator,finaldenominator);
   }

      //Returns whole number
      //Parses fraction string to find the whole number.
      public static int returnWholenumber(String fraction2){
          //If the string has an underscore it's read as a mixed number
         if (fraction2.indexOf("_")!=-1)
            //Parses the substring before the underscore as a whole number
            return Integer.parseInt(fraction2.substring(0,fraction2.indexOf("_")));
            //If the string has a slash but no underscore, it's a normal fraction
      else if (fraction2.indexOf("/")!=-1){
         return 0; // Whole number is equal to 0

      }
      // returns a whole number
      else{
         return Integer.parseInt(fraction2);
      }
      }

//Parses a fractions string to find the numerator portion
// 
      public static int getNum(String fraction2){
         //If it;s  a mixed numer the numerator is between '_' and '/'
         if(fraction2.indexOf("_")!=-1){
            return Integer.parseInt(fraction2.substring(fraction2.indexOf("_")+1, fraction2.indexOf("/")));
         }
         //If its a normal fraction containing '/' and not a mixed number then the numerator is between '/' and '_'
         else if(fraction2.indexOf("/")!=-1){
               return Integer.parseInt(fraction2.substring(0,fraction2.indexOf("/")));
            }
            //If it's a whole number the numerator is then 0
         else{
            return 0;
         }
      }
            // parses a fraction string to find the denominator portion
            public static int getDenom(String fraction2){
               //If it contains a slash, the denominator is after that slash mark '/'
               if(fraction2.indexOf("/")!=-1){
                  return Integer.parseInt(fraction2.substring(fraction2.indexOf("/")+1));
               }
               // If it's a whole number the denominator is then '1'
               else{
                  return 1;
               }
      }

      //Find the GCF between the 2 fractions to be used to reduce the final fraction
      //GCF is used to give a common base
      public static int greatestCommonfactor(int a, int b){
         int greatest = 1; //equals 1 for the GCF
         //Goes from 1 up to the smalled of the 2 numbers
         for (int i = 1; i<=Math.min(a,b); i++){
            //check if 'i' can divide both of the numbers evenly and nice
            if(a%i == 0 && b%i == 0){
               greatest = i; //Updates GCF
            }
         }
         return greatest;
         }
         
      //Takes a fractions string and converts it into an equivalant improper fraction
      //Handles the whole number as well
      public static String improperFrac(String output){
         //3 components of the number
         int whole = returnWholenumber(output);
         int num = getNum(output);
         int denom = getDenom(output);
         //If the  denominator is 0 then we return a string for an erro
         if(denom == 0){
            return "0/0";
         }
         //Calculates the amount of the improper numerator
         int improperNum = Math.abs(whole) * denom + num;
         //Changes the sign of it if the original number was negative then the numerator is now negative, we are just changing the location of the negative
         if (whole < 0){
            improperNum *= -1;
         }
         //Returns the last improper fraction
         return improperNum + "/"  + denom;
      }
      //Does the calculation for the specific operation for the components of the 2 improper fractions
      public static String operatingSystems(int n1, int n2, int d1, int d2, String Operator){
         int n; //Resulting numerator
         int d; //Resulting denominator

         if(Operator.equals("+")){
            //Addition
            n = n1 * d2 + n2 * d1;
            d = d1 * d2;
         } else if (Operator.equals("-")){
            //Subtraction
            n = n1 * d2 - n2 * d1;
            d = d1* d2;
         } 
         else if(Operator.equals("*")){
            //Multiplication
            n = n1 * n2;
            d = d1 * d2;

         }
         else{
            //Divison by 0 gets checked and if the second numbers divsior is 0 then we're doing divison by 0
            if(n2==0){
               return "0/0";
            }
            else {
               //Executes Keep Change Flip
               n = n1 * d2;
               d = d1 * n2;
            }
         }
         ///Returns the numerator and denominator as a string
         return n + "/" + d; 
         }

         //Takes the operator string from the expression
         //Works around tokens surrounding input
         public static String Operator(String input){
            int space1 = input.indexOf(" ");
            // The operator is assumed at the index(space1 +1)
            return input.substring(space1 + 1, space1 + 2);
         }

         //Takes the first Operand string from the expression
         public static String numberOne(String input){
            int space1 = input.indexOf(" ");
            //The first number is a substring from the start up to the first space
            return input.substring(0,space1);
         }
         //Takes the second operand string from the expression
         public static String numberTwo(String input){
            int space1 = input.indexOf(" ");
            //The second number starts after the operator and the following token
            return input.substring(space1 + 3);
         }
         //Takes the numerator as an integer from the standard fraction string
         public static int Numerator(String frac){
            //finds the slash and then takes the substring in front of it
            return Integer.parseInt(frac.substring(0,frac.indexOf("/")));
         }
         //Takes the denominator as an integer from the standard fraction string
         public static int Denominator(String frac){
            return Integer.parseInt(frac.substring(frac.indexOf("/")+1));
         }
         //Formatting method - handles erros, reduces the fraction
         //converts the improper fraction into a mixed and it's parts
         public static String prettier(int finalnumerator, int finaldenominator){
            //Divison by 0
            if(finaldenominator == 0){
               return "ERROR: Division by zero";
            }
            //Result is 0
            if(finalnumerator == 0){
               return "0";
            }
            //Normaliing by making sure the denominator is a postivie value by moving the sign to the numerator
            if (finaldenominator < 0) {
               finaldenominator *= -1;
               finalnumerator *= -1;
            }
            //Reduces, calculates the GCF using the absolute value
            int greatest = greatestCommonfactor(Math.abs(finalnumerator),Math.abs (finaldenominator));
               finalnumerator /= greatest;
               finaldenominator /= greatest;
               //Calculates mized number parts 
               int whole = finalnumerator / finaldenominator; //Signed whole number
               int remainder = Math.abs(finalnumerator) % finaldenominator; // non negative

               String output = expections(whole, remainder, finaldenominator, finalnumerator);
               return output;

         }
         //Handles formatting issues and details, can be improper or proper fraction, 
         public static String expections(int whole, int remainder, int finaldenominator, int finalnumerator){
            if(remainder == 0){
               //If it's a Perfect whole number
               return whole + "";
            }

            // If the proper fraction result(Whole number is = to 0) 
            if(whole == 0){
               if(finalnumerator < 0){
                  //Use the sign of the final numerator to decide if a '-' symbol is needed
                  return "-" + remainder + "/" + finaldenominator; //ex. 1/2
               }else{
                  //If theres a mixed number result like a non whole or a non 0 remainder item
                  //Format uses a space between the whole number and the fraction
                  return remainder + "/" + finaldenominator;
               }
               }

               return whole + " " + remainder + "/" + finaldenominator; 
            }



      // TODO: implement this method!
        /*int first = input.indexOf(" ");
         String part = input.substring(first + 1);
         int second = part.indexOf(" ");
   
         String op = input.substring(first + 1, first +2);
         String fractionChunk = part.substring(second + 1);

         String wholePart;
         String numerator;
         String denominator;


         int underScore = fractionChunk.indexOf("_");
         int slash = fractionChunk.indexOf("/");
         if(underScore != -1){
            wholePart = fractionChunk.substring(0,underScore);
            numerator = fractionChunk.substring(underScore+1,slash);
            denominator = fractionChunk.substring(slash + 1);
         }
         else if (slash != -1){
            wholePart = "0";
            numerator = fractionChunk.substring(0,slash);
            denominator = fractionChunk.substring(slash + 1);
         }
         else{
            wholePart = fractionChunk;
            numerator = "0";
            denominator = "1";
         }
         if(Integer.parseInt(numerator) < 0 && Integer.parseInt(denominator)<0){
            numerator = numerator.substring(1);
            denominator = denominator.substring(1);
         }
         else if (Integer.parseInt(denominator)<0){
            numerator = "-" + numerator;
            denominator = "-" + denominator;
         }


        return "Op:" + op + " Whole:" + wholePart + " Num:" + numerator +" Den:" + denominator;
/* */
   
   // Returns a string that is helpful to the user about how
   // to use the program. These are instructions to the user.
   public static String provideHelp() {
      // TODO: Update this help text!
     
      //Provides the user with help if they request for it
      //Tells the user how to format items such as impropers and standards.
      String help = "Hi, seems like you need help!\n";
      help += "Enter 2 numbers seperated by an operator and put a space between each number.\nFor example 2 / 3 + 6 / 7\nIf it's an improper fraction then state it like this: 1_1/3.\nFollow the format whole number_numerator/denominator";
      
      return help;
   }
}