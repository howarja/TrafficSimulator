
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
    private Panel panel;
    private final int WIDTH = 300;
    private final int HEIGHT = 300;

    public Window(Road road){
        System.out.println("Creating window");
        this.road = road;
        road.extendRoad(5,5);

        panel = new Panel();
        this.add(panel);
        setPreferredSize(new Dimension(WIDTH,HEIGHT));

        userInput = new Input();
        
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


        this.setVisible(true);
        this.pack();
        this.toFront();

    }
    
    public void actionPerformed(ActionEvent e){
        /* create a new road */
        if(e.getActionCommand().equals("new road")){
           int speedLimit = userInput.integerRequest(this, "Enter speed limit", 5, 100);
           int lanes = userInput.integerRequest(this, "lanes", 1, 10);
           road.extendRoad(speedLimit, lanes);
           panel.repaint();
        }

        /* quit the program */
        if(e.getActionCommand().equals("quit")){
            System.exit(0);
        }
    }

    public class Panel extends JPanel{
        public Panel(){
            setPreferredSize(new Dimension(WIDTH, HEIGHT));
            setBackground(Color.BLUE);
            this.setVisible(true);
            System.out.println("Creating panel");
            repaint();
        }

        public void setBackgroundColor(Color color){
            setBackground(color);
        }

        public void PaintComponent(Graphics g){
            /* Draw the graphics onto the screen */
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            System.out.println("repaint");


            int iteration = 0;
            int roadWidth = 100;
            int roadHeight = 20;
            g2.setColor(Color.GRAY);
            g2.drawRect(30, 30 , roadWidth, roadHeight);
            RoadTile currentRoad = road.getFirsTile();
            while (currentRoad!=null) {
                g2.drawRect(roadWidth*iteration, 0 , roadWidth, roadHeight);
                currentRoad = currentRoad.getNextRoad();
                iteration++;
            }
        }
    }
}
