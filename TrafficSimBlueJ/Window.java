 


/**
 * Create a GUI window for the program
 *
 * @author James Howard
 * @version 7/8/25
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
    private final int WINDOWHEIGHT = 700;
    private final int TOTAL_MAX_ROAD_LENGTH = 1200;

    public static final int CARHEIGHT = 70;
    public static final int CARWIDTH = 70;

    private final int ROADHEIGHT = 60;
    private final int OUTLINTE_THICKNESS = 5;

    private final Font font;
    private Stroke dashedLines;

    private final int CAR_FREQUENCY_MIN=1;
    private final int CAR_FREQUENCY_MAX=5;

    private final int CAR_SPACING_MIN = 5;
    private final int CAR_SPACING_MAX = 75;

    private final int ROAD_LENGTH_MIN=50;
    private final int ROAD_LENTH_MAX=500;

    private final int LANES_MIN=1;
    private final int LANES_MAX=5;

    private final int SPEED_LIMIT_MIN=5;
    private final int SPEED_LIMIT_MAX=100;

    public static final String TIME_UNIT = "(seconds)";
    public static final String SPEED_UNIT = "(m/s)";
    public static final String LENGTH_UNIT = "(meters)";

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

        this.setResizable(false);
        this.setVisible(true);
        this.pack();
        this.toFront();

        /* Setup larger font */
        font = new Font("serif", Font.BOLD, 40);

        /* Setup dash stroke for lane lines */
        dashedLines = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL,
                                    0, new float[]{9}, 0);
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
            int currentRoadLength = 0;
            RoadTile currentRoadTile = road.getFirsTile();
            while (currentRoadTile!=null) {
                currentRoadLength+=currentRoadTile.getLength();
                currentRoadTile = currentRoadTile.getNextRoad();
            }
            if(currentRoadLength>=TOTAL_MAX_ROAD_LENGTH-ROAD_LENGTH_MIN)
                return;

            int maxLength = Math.min(TOTAL_MAX_ROAD_LENGTH-currentRoadLength, ROAD_LENTH_MAX);

            int speedLimit = userInput.integerRequest(this, "Enter speed limit"+SPEED_UNIT, SPEED_LIMIT_MIN, SPEED_LIMIT_MAX);
            int lanes = userInput.integerRequest(this, "pick lanes", LANES_MIN, LANES_MAX);
            
            int length = userInput.integerRequest(this, "pick length"+LENGTH_UNIT, ROAD_LENGTH_MIN, maxLength);
            road.extendRoad(speedLimit, lanes, length);
            panel.repaint();
        }

        /* set car spawn time */
        if (e.getActionCommand().equals("car frequency")) {
            int period = userInput.integerRequest(this, "Enter new period between spawns"+TIME_UNIT, CAR_FREQUENCY_MIN, CAR_FREQUENCY_MAX);
            ProgramLoop.setCarSpawnTime(period);
            panel.repaint();
        }

        /* set car spacing */
        if (e.getActionCommand().equals("car spacing")) {
            int spacing = userInput.integerRequest(this, "Enter new spacing distance"+LENGTH_UNIT, CAR_SPACING_MIN, CAR_SPACING_MAX);
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
            setBackground(Color.WHITE);
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
            g2.setStroke(dashedLines);
            g2.setColor(Color.black);

            RoadTile currentRoad = road.getFirsTile();
            if(currentRoad!=null){
            g2.drawString("Crashes: " + Road.carCrashes, 50, 50);
            g2.drawString("Red lights ran: " + Road.redLightsRan, 50, 90);
            }else{
                g2.drawString("Use the program menu to create your first road", 50, 50);
            }

            /* Draw order is important for layering, road has to be looped through multiple times to ensure corect layering.
                First for outlines as they must be behind everything,
                Next for the road and lane lines
                Finally for the cars, traffic lights and speed limit text because they must be above everything */


            int totalLength = 0;
            g2.setColor(Color.black);
            while (currentRoad != null) {
                /* Draw the road */
                int width = currentRoad.getLength();
                int height = ROADHEIGHT*currentRoad.getLanes();
                int y = WINDOWHEIGHT/2 - height/2;

                g2.fillRect(totalLength-OUTLINTE_THICKNESS, y-OUTLINTE_THICKNESS,width+OUTLINTE_THICKNESS*2, /*ROADHEIGHT +*/ height+OUTLINTE_THICKNESS*2);

                currentRoad = currentRoad.getNextRoad();
                totalLength += width;
            }

            /* Draw the road and lane lines */
            currentRoad = road.getFirsTile();
            totalLength = 0;
            while(currentRoad!=null){
                /* Draw the road */
                int width = currentRoad.getLength();
                int height = ROADHEIGHT*currentRoad.getLanes();
                int y = WINDOWHEIGHT/2 - height/2;

                g2.setColor(Color.gray);
                g2.fillRect(totalLength, y,width, /*ROADHEIGHT +*/ height);

                /* Draw the lines between the lanes */
                g2.setColor(Color.white);
                for(int i = 1; i < currentRoad.getLanes(); i++){
                    g2.drawLine(totalLength, y+i*ROADHEIGHT, totalLength+currentRoad.getLength(), y+i*ROADHEIGHT);
                }

                currentRoad = currentRoad.getNextRoad();
                totalLength += width;
            }

            /* Draw the cars and UI overlays */
            currentRoad = road.getFirsTile();
            totalLength = 0;
            while(currentRoad!=null){
                ArrayList<ArrayList<Car>> cars = currentRoad.getCars();
                int laneIndex = 0;
                int height = ROADHEIGHT*currentRoad.getLanes();
                int y = WINDOWHEIGHT/2 - height/2;
                for (ArrayList<Car> lane : cars) {
                    for (Car car : lane) {
                        int xPos = (int) car.getPosition();
                        int yPos = y+(laneIndex*ROADHEIGHT)-CARHEIGHT/2+ROADHEIGHT/2;
                        g2.drawImage(carImage, xPos + totalLength, yPos, CARWIDTH, CARHEIGHT,null);
                    }
                    laneIndex++;
                }
                
                /* draw the lights */
                if(currentRoad.canGo())
                    g2.setColor(Color.green);
                else
                    g2.setColor(Color.red);
                int circlSize = 45;
                g2.fillOval(totalLength+currentRoad.getLength()-circlSize/2, y-circlSize*3, circlSize, circlSize);

                /* Draw the speed limit */
                g2.setColor(Color.black);
                g2.drawString(String.valueOf(currentRoad.getSpeedLimit())+SPEED_UNIT, totalLength+currentRoad.getLength()/2, y-30);

                totalLength += currentRoad.getLength();
                currentRoad = currentRoad.getNextRoad();
            }
        }
    }
}
