
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

public class Window extends JFrame implements ActionListener {
    JMenuBar bar;
    JMenu menu;
    JMenuItem newRoadMenuItem;
    JMenuItem quitMenuItem;

    private Input userInput;
    private Road road;
    private Panel panel;
    private final int WINDOWWIDTH = 900;
    private final int WINDOWHEIGHT = 900;

    private final int CARHEIGHT = 10;
    private final int CARWIDTH = 5;

    private final Font font;

    public Window(Road road) {
        System.out.println("Creating window");
        this.road = road;

        panel = new Panel();
        this.add(panel);
        setPreferredSize(new Dimension(WINDOWWIDTH, WINDOWHEIGHT));

        userInput = new Input();

        setTitle("Traffic simulator");
        this.getContentPane().setPreferredSize(new Dimension(WINDOWWIDTH, WINDOWHEIGHT));
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

        /* Setup larger font */
        font = new Font("serif", Font.BOLD, 40);
    }

    public void actionPerformed(ActionEvent e) {
        /* create a new road */
        if (e.getActionCommand().equals("new road")) {
            int speedLimit = userInput.integerRequest(this, "Enter speed limit", 5, 100);
            int lanes = userInput.integerRequest(this, "pick lanes", 1, 4);
            int length = userInput.integerRequest(this, "pick length", 100, 500);
            road.extendRoad(speedLimit, lanes, length);
            panel.repaint();
        }

        /* quit the program */
        if (e.getActionCommand().equals("quit")) {
            System.exit(0);
        }
    }

    public void addCar() {
        road.addCar();
    }

    public void updateCars() {
        road.updateCars();
    }

    public void updateRoadLights(double totalTime){
        road.updateRoadTileLights(totalTime);
    }

    public class Panel extends JPanel {
        public Panel() {
            setPreferredSize(new Dimension(WIDTH, HEIGHT));
            setBackground(Color.BLUE);
            this.setVisible(true);
            repaint();
        }

        public void setBackgroundColor(Color color) {
            setBackground(color);
        }

        @Override
        protected void paintComponent(Graphics g) {
            /* Draw the graphics onto the screen */
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            
            /* Loop through the road tiles, draw road and each car on it */
            g2.setColor(Color.GRAY);
            RoadTile currentRoad = road.getFirsTile();
            int totalLength = 0;
            while (currentRoad != null) {
                /* Draw the road */
                g2.setColor(Color.gray);
                int width = currentRoad.getLength();
                int height = CARHEIGHT*currentRoad.getLanes();
                int y = WINDOWHEIGHT/2 - height/2;
                g2.fillRect(totalLength, y,width, /*ROADHEIGHT +*/ height);
                
                /* Draw the speed limit */
                g2.setFont(font);
                g2.setColor(Color.red);
                g2.drawString(String.valueOf(currentRoad.getSpeedLimit()), totalLength+currentRoad.getLength()/2, y-30);

                /* draw the lights */
                if(currentRoad.canGo())
                    g2.setColor(Color.green);
                else
                    g2.setColor(Color.red);
                int circlSize = 35;
                g2.fillOval(totalLength+width-circlSize/2, y-30-circlSize/2, circlSize, circlSize);

                /* draw the cars */
                g2.setColor(Color.yellow);
                ArrayList<ArrayList<Car>> cars = currentRoad.getCars();
                int laneIndex = 0;
                for (ArrayList<Car> lane : cars) {
                    for (Car car : lane) {
                        int pos = (int) car.getPosition();
                        g2.fillRect(pos + totalLength, y+(laneIndex*CARHEIGHT), CARWIDTH, CARHEIGHT);
                    }
                    laneIndex++;
                }
                currentRoad = currentRoad.getNextRoad();
                totalLength += width;
            }
        }
    }
}