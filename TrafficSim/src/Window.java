
/**
 * Create a GUI window for the program
 *
 * @author James Howard
 * @version 21/5/25
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Window extends JFrame implements ActionListener
{
    JMenuBar bar;
    JMenu menu;
    JMenuItem newRoadMenuItem;
    JMenuItem quitMenuItem;
    
    public Window(){
        System.out.println("Creating window");
        setTitle("Traffic simulator");
        this.getContentPane().setPreferredSize(new Dimension(400, 600));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        bar = new JMenuBar();
        this.setJMenuBar(bar);
        
        menu = new JMenu("Menu");
        bar.add(menu);

        newRoadMenuItem = new JMenuItem("new road");
        newRoadMenuItem.addActionListener(this);    
        menu.add(newRoadMenuItem);

        quitMenuItem = new JMenuItem("quit");
        quitMenuItem.addActionListener(this);
        menu.add(quitMenuItem);

        this.pack();
        this.toFront();
        this.setVisible(true);
    }
    
    public void actionPerformed(ActionEvent e){
        /* quit the program */
        if(e.getActionCommand().equals("new road")){
            createDialogBox("new road time", "new text");
        }

        /* quit the program */
        if(e.getActionCommand().equals("quit")){
            System.exit(0);
        }
    }

    public DialogueBox createDialogBox(String title, String text){
        return new DialogueBox(title, text, this);
    }
}
