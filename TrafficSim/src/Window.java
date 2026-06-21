
/**
 * Create a GUI window for the program
 *
 * @author James Howard
 * @version 21/5/25
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

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

    private final int ROADWIDTH = 200;
    private final int ROADHEIGHT = 20;
    private final int CARHEIGHT = 10;
    private final int CARWIDTH = 5;

    public Window(Road road){
        System.out.println("Creating window");
        this.road = road;

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

    public void addCar(){
        road.addCar();
    }

    public void updateCars(){
        road.update();
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

        @Override
        protected void paintComponent(Graphics g){
            /* Draw the graphics onto the screen */
            super.paintComponent(g);
           // System.out.println("repaint");
            Graphics2D g2 = (Graphics2D) g;

            int iteration = 0;
    
            g2.setColor(Color.GRAY);
            RoadTile currentRoad = road.getFirsTile();
            while (currentRoad!=null) {
                g2.setColor(Color.gray);
                g2.fillRect(ROADWIDTH*iteration, 0 , ROADWIDTH, ROADHEIGHT);

                g2.setColor(Color.yellow);
                ArrayList<Car> cars =currentRoad.getCars();
                for(Car car : cars){
                    int pos = (int)car.getPosition();
                    System.out.println(pos);
                    g2.fillRect(pos+ROADWIDTH*iteration, 0 , CARWIDTH, CARHEIGHT);
                }

                currentRoad = currentRoad.getNextRoad();
                iteration++;
            }
        }
    }
    
}
