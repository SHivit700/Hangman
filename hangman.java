import java.awt.*;
import javax.swing.*;
import java.util.Scanner;
import java.io.*;


class hangman extends Canvas
{
    public static int gameChosen;

    public static String word = "hi";
    // public static String word = randomWord().toLowerCase();

    public static final int gameOver = 5;
    public static final int gameWon = 10;
    public static int incorrectAttempts = 0;
    public static char [] guessedCorrect = create(word);
    public static String guessedSoFar = "";
    public static String guessedCorrectString = "";

    public void paint(Graphics g) {  

        switch(incorrectAttempts)
        {
            // User won
            case 10 :
            g.drawString("You win!", 100, 100);
            break;

            // User lost
            case 5 : 
            g.drawString("L :(" , 100, 80);
            g.drawString("Word was: " + word , 100, 130);
            break;

            case 4 :
            // leg 2
            g.drawLine(150, 175, 160, 185);
            // leg 1
            g.drawLine(150, 175, 140, 185);
            case 3 :
            // hand 1
            g.drawLine(150, 152, 140, 150);
            // hand 2
            g.drawLine(150, 152, 160, 150);
            case 2 :
            //body
            g.drawLine(150, 140, 150, 175);
            case 1 :
            //face
            g.drawOval(142, 125, 15, 15);
            case 0 :
            //structure
            g.drawLine(100, 100, 100, 200);
            g.drawLine(100, 100, 150, 100);
            g.drawLine(150, 100, 150, 125);
            g.drawLine(75, 200, 125, 200);
            guessedCorrectString = new String(guessedCorrect);
            g.drawString("Guess: " + guessedCorrectString, 100, 300);
            g.drawString("Guessed So Far: " + guessedSoFar, 100, 350);
            g.drawString("Incorrect Guesses Remaining: " + (gameOver - incorrectAttempts), 180, 150);
        }
    }  
    public static void main(String[] args) 
    {  
        Scanner sc = new Scanner(System.in);
        hangman m=new hangman();  
        JFrame f=new JFrame();  
        f.setSize(400,500);
        f.add(m);  
        f.setVisible(true);    

        char guess;

        while (isGameOver() == false)
        {
            System.out.println("Guess a character");
            guess = sc.nextLine().charAt(0);
            System.out.println();
            
            // character has not been guessed before
            if(guessedSoFar.indexOf(guess) == -1)
            {
                // character is the right guess
                if(word.indexOf(guess) != -1)
                {
                    // add all occurances of guess 
                    for(int i = 0 ; i < word.length() ; i++)
                    {
                        if(word.charAt(i) == guess)
                        {
                            guessedCorrect[i] = guess;
                        }
                    }
                    guessedSoFar += Character.toString(guess);
                }
                // guess is incorrect
                else
                {
                    incorrectAttempts++;
                    guessedSoFar += Character.toString(guess);
                }
            }
            // character has been guessed already
            else 
            {
                System.out.println("Character has been guessed");
                System.out.println("Try Again");
            }

            String wordguessed = new String(guessedCorrect);

            if(wordguessed.equals(word))
            {
                incorrectAttempts = 10;
            }

            f.dispose();
            f.add(m);  
            f.setVisible(true);  
        }
    } 

    // for creating blank spaces '_' for word to be guessed and ' ' if two or more words exist
    public static char[] create (String n)
    {
        char [] wrds = new char[n.length()];
        for(int i = 0 ; i < n.length() ; i++)
        {
            if(n.charAt(i) == ' ')
            {
                wrds[i] = ' ';
            }
            else
            {
                wrds[i] = '_';
            }
        }
        return wrds;
    }

    // picked from wordList.txt
    public static String randomWord()
    {
        try {
            Scanner sc2 = new Scanner(System.in);
            System.out.println("Choose a mode (enter the number): ");
            System.out.println("1 : Dictionary");
            System.out.println("2 : Countries");
            gameChosen = sc2.nextInt();

            String game = "";

            switch(gameChosen)
            {
                case 1:
                game = "wordList.txt";
                break;

                case 2:
                game = "wordListCountries.txt";
                break;
            }

            File myObj = new File(game);
            
            int len = 0;

            LineNumberReader reader = null;
            try {
                reader = new LineNumberReader(new FileReader(game));
                while ((reader.readLine()) != null);
                len = reader.getLineNumber();
            } catch (Exception ex) {
                len = -1;
            } 

            String list [] = new String [len];

            Scanner myReader = new Scanner(myObj);
            int count = 0;
            while (myReader.hasNextLine()) {
              list[count] = myReader.nextLine();
              count++;
            }

            int rand = (int)(Math.random() * len);
            myReader.close();
            return list[rand];

          } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            return "";
          }
    }

    // checks if game is over
    public static boolean isGameOver()
    {
        if(incorrectAttempts == gameOver || incorrectAttempts == gameWon)
        {
            return true;
        }
        return false;
    }
}