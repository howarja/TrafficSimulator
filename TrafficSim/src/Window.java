
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
    JMenuItem menuItem;
    
    public Window(){
        System.out.println("Creating window");
        setTitle("Traffic simulator");
        this.getContentPane().setPreferredSize(new Dimension(400, 600));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        bar = new JMenuBar();
        this.setJMenuBar(bar);
        
        menu = new JMenu("Menu");
        bar.add(menu);
        menuItem = new JMenuItem("cool button");
        menu.add(menuItem);
        
        this.pack();
        this.toFront();
        this.setVisible(true);
    }
    
    public void actionPerformed(ActionEvent e){

    }
}
