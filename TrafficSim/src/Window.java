
/**
 * Create a GUI window for the program
 *
 * @author James Howard
 * @version 21/5/25
 */

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

public class Window extends JFrame implements ActionListener {
    JMenuBar programBar;
    JMenu programMenu;
    JMenu simulationMenu;
    JMenuItem newRoadMenuItem;
    JMenuItem carSpawnMenuItem;
    JMenuItem carSpacingDistanceMenuItem;
    JMenuItem resetMenuItem;
    JMenuItem quitMenuItem;

    private BufferedImage carImage;
    private final String carPath = "car.png";

    private Input userInput;
    private Road road;
    private Panel panel;
    private final int WINDOWWIDTH = 1400;
    private final int WINDOWHEIGHT = 900;

    private final int CARHEIGHT = 10;
    private final int CARWIDTH = 20;

    private final int ROADHEIGHT = 60;

    private final Font font;

    private final int CAR_FREQUENCY_MIN=1;
    private final int CAR_FREQUENCY_MAX=5;

    private final int CAR_SPACING_MIN = 20;
    private final int CAR_SPACING_MAX = 75;

    private final int ROAD_LENGTH_MIN=50;
    private final int ROAD_LENTH_MAX=500;

    private final int LANES_MIN=1;
    private final int LANES_MAX=5;

    private final int SPEED_LIMIT_MIN=5;
    private final int SPEED_LIMIT_MAX=100;

    public static final String timeUnit = "(seconds)";
    public static final String speedUnit = "(m/s)";
    public static final String lengthUnit = "(meters)";

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

        programBar = new JMenuBar();
        this.setJMenuBar(programBar);

        programMenu = new JMenu("Program");
        programBar.add(programMenu);

        simulationMenu = new JMenu("Simulation");
        programMenu.add(simulationMenu);

        newRoadMenuItem = new JMenuItem("new road");
        newRoadMenuItem.addActionListener(this);
        programMenu.add(newRoadMenuItem);

        carSpawnMenuItem = new JMenuItem("car frequency");
        carSpawnMenuItem.addActionListener(this);
        simulationMenu.add(carSpawnMenuItem);

        carSpacingDistanceMenuItem = new JMenuItem("car spacing");
        carSpacingDistanceMenuItem.addActionListener(this);
        simulationMenu.add(carSpacingDistanceMenuItem);

        resetMenuItem = new JMenuItem("reset road");
        resetMenuItem.addActionListener(this);
        programMenu.add(resetMenuItem);

        quitMenuItem = new JMenuItem("quit");
        quitMenuItem.addActionListener(this);
        programMenu.add(quitMenuItem);

        this.setVisible(true);
        this.pack();
        this.toFront();

        /* Setup larger font */
        font = new Font("serif", Font.BOLD, 40);

        /* setup images */
        try {   
            carImage = ImageIO.read(new File(carPath));
        } catch (Exception e) {
            System.out.println("null image");
        }
    }

    public void actionPerformed(ActionEvent e) {
        /* create a new road tile */
        if (e.getActionCommand().equals("new road")) {
            int speedLimit = userInput.integerRequest(this, "Enter speed limit", SPEED_LIMIT_MIN, SPEED_LIMIT_MAX);
            int lanes = userInput.integerRequest(this, "pick lanes", LANES_MIN, LANES_MAX);
            int length = userInput.integerRequest(this, "pick length", ROAD_LENGTH_MIN, ROAD_LENTH_MAX);
            road.extendRoad(speedLimit, lanes, length);
            panel.repaint();
        }

        /* set car spawn time */
        if (e.getActionCommand().equals("car frequency")) {
            int period = userInput.integerRequest(this, "Enter new period between spawns", CAR_FREQUENCY_MIN, CAR_FREQUENCY_MAX);
            ProgramLoop.setCarSpawnTime(period);
            panel.repaint();
        }

        /* set car spacing */
        if (e.getActionCommand().equals("car spacing")) {
            int spacing = userInput.integerRequest(this, "Enter new spacing distance", CAR_SPACING_MIN, CAR_SPACING_MAX);
            Car.setStoppingDistance(spacing);
            panel.repaint();
        }
        
        /* reset road program */
        if (e.getActionCommand().equals("reset road")) {
            road.resetRoad();
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
            setBackground(Color.BLACK);
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
            
            /* Draw crashes */
            g2.setFont(font);
            g2.setColor(Color.white);
            g2.drawString("Crashes: " + Road.carCrashes, 50, 50);
            g2.drawString("Red lights ran: " + Road.redLightsRan, 50, 90);
            
            /* Loop through the road tiles, draw road and each car on it */
            g2.setColor(Color.GRAY);
            RoadTile currentRoad = road.getFirsTile();
            int totalLength = 0;
            while (currentRoad != null) {
                /* Draw the road */
                g2.setColor(Color.gray);
                int width = currentRoad.getLength();
                int height = ROADHEIGHT*currentRoad.getLanes();
                int y = WINDOWHEIGHT/2 - height/2;
                g2.fillRect(totalLength, y,width, /*ROADHEIGHT +*/ height);
                
                /* Draw the lines between the lanes */
                for(int i = 0; i < currentRoad.getLanes()+1; i++){
                    g2.drawLine(totalLength, y+i*ROADHEIGHT, totalLength+currentRoad.getLength(), y+i*ROADHEIGHT);
                }
                
                /* Draw the speed limit */
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
                        g2.drawImage(carImage, pos + totalLength, y+(laneIndex*ROADHEIGHT), CARWIDTH, CARHEIGHT,null);
                    }
                    laneIndex++;
                }
                currentRoad = currentRoad.getNextRoad();
                totalLength += width;
            }
        }
    }
}