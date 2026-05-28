
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
    
    private Input userInput;
    private Road road;

    public Window(Road road){
        System.out.println("Creating window");
        userInput = new Input();
        this.road = road;
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
        /* create a new road */
        if(e.getActionCommand().equals("new road")){
           int speedLimit = userInput.integerRequest(this, "Enter speed limit", 5, 100);
           int lanes = userInput.integerRequest(this, "lanes", 1, 10);
           road.extendRoad(speedLimit, lanes);
        }

        /* quit the program */
        if(e.getActionCommand().equals("quit")){
            System.exit(0);
        }
    }

    public class Panel extends JPanel{
        public Panel(){

        }

        public void setBackgroundColor(Color color){
            setBackground(color);
        }

        public void PaintComponent(Graphics g){
            /* Draw the graphics onto the screen */
            super.paintComponent(g);
            

        }
    }
}
