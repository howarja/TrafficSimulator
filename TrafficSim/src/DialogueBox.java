/**
 * Present a dialogue for user input
 *
 * @James Howard
 * @18/3/25
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;// listener
public class DialogueBox extends JDialog
{
    String answer;

    /* Create a small window with a text field the user to type in their input and a confirm button */
    public DialogueBox(String prompt){
        super (new JFrame(prompt), prompt);
        this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        
        int boxWidth = prompt.length()*10;
        this.setMinimumSize(new Dimension(boxWidth, 100));

        JTextField input = new JTextField();
        JButton confirm = new JButton();
        confirm.setText("Confirm");
        confirm.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e){
                    answer = input.getText();
                    close();
                }
            });
        /* finilise dialog window */
        this.setLayout(new GridLayout(2,1,5,5));
        this.add(input);
        this.add(confirm);
        this.pack();
        setModal(true);
    }
    
    public void close(){
        this.dispose();
    }

    public String getResponse(){
        return answer;
    }
}
