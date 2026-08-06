 

/**
 * Get reliable input from the user for 
 * multi choices, 
 * double within range,
 * int within range,
 * string
 *
 * @author James Howard
 * @version 2/4/26
 */

public class Input
{
    /**
     *  Creatte a dialogue box, asking for a user input, return the result 
    */
     public String createDialogBox(Window window, String prompt){
      
        DialogueBox box = new DialogueBox(prompt);
        box.setLocationRelativeTo(window);
        box.setVisible(true);
        return box.getResponse();
    }

    /** 
     *    Request a string from the user
     *    Repeat request if user provides invalid input
     */
    public String stringRequest(Window window, String prompt){
        String result = createDialogBox(window, prompt);

        /* Request again if input was empty */
        if(result.equals("")){
            System.out.println("Invalid input, empty response.");
            return stringRequest(window, prompt);
        }
        return result;
    }

    /** 
     *    Request a double from the user, repeat request if input is invalid
     *    Repeat request if user provides invalid input
     */
    public double doubleRequest(Window window, String prompt, double min, double max){
        String stringResult = createDialogBox(window, prompt + " Input number between " + min + " and " + max);

        double result = -1;
        boolean validInput = true;
        /* Convert the string input to an double, try catch used in case string does not parse to double
         * if failed then input is invalid
         * if int input is not within requested range input is invalid */
        try{
            result = Double.parseDouble(stringResult);  // java parses string to doulbe when passed into double constructor
            /* Check wether input is within requested range */
            if(result<min||result>max){
                validInput = false;
                System.out.println("Invalid input - keep input within requested range");
            }
        }
        catch(Exception e){
            validInput = false;
            System.out.println("Invalid input - Input exclusively numerical answer ");
        }
        
        /* Repeat request if input is invalid */
        if(!validInput){
            return doubleRequest(window,prompt, min, max);
        }

        return result;
    }

    /** 
     *    Request a string from the user
     *    Repeat request if user provides invalid input
     */
    public int integerRequest(Window window, String prompt, int min, int max){
        /* Request a int from the user, repeat request if input is invalid */
        String stringResult = createDialogBox(window, prompt + " Input integer between " + min + " and " + max);

        int result = -1;
        boolean validInput = true;
        /* Convert the string input to an int, try catch used in case string does not parse to int
         * if failed then input is invalid
         * if int input is not within requested range input is invalid */
        try{
            result = Integer.parseInt(stringResult);
            if(result<min||result>max){
                validInput = false;
                System.out.println("Invalid input - keep input within requested range");
            }
        } catch(Exception e){
            validInput = false;
            System.out.println("Invalid input - Input exclusively numerical answer ");
        }

        /* Repeat request if input was invalid */
        if(!validInput){
            return integerRequest(window,prompt, min, max);
        }
        return result;
    }

    /** 
     *    Request input from user of choice out of multiple actions
     *    Repeat request if user provides invalid input
     */
    public int optionRequest(Window window, String[] actions){
        System.out.println("\nOptions: ");
        for(int i = 0; i < actions.length; i++)
            System.out.println((i+1) +". "+ actions[i]);

        /* return result if valid, otherwise request again */
        int result = integerRequest(window, "Choose action", 1, actions.length)-1;
        return result;
    }
}
