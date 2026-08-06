import java.util.ArrayList;

/**
 * Store the data for a single tile of the road
 *
 * @author James Howard
 * @version 21/5/25
 */
public class RoadTile {

    private int speedLimit;
    private int lanes;
    private int roadLength;
    private RoadTile nextRoad;
    private ArrayList<ArrayList<Car>> cars = new ArrayList<ArrayList<Car>>();

    private final double stopDuration = 800;
    private final double goDuration = 400;
    private boolean canGo = false;
    private double currentDuration;

    public RoadTile(int speedLimit, int lanes, int length) {
        this.speedLimit = speedLimit;
        this.lanes = lanes;
        this.roadLength = length;
        for (int i = 0; i < lanes; i++)
            this.cars.add(new ArrayList<Car>());
    }

    public void setNextRoad(RoadTile nextRoad) {
        this.nextRoad = nextRoad;
    }

    public int getSpeedLimit() {
        return this.speedLimit;
    }

    public int getLanes() {
        return this.lanes;
    }

    public int getLength() {
        return this.roadLength;
    }

    public RoadTile getNextRoad() {
        return this.nextRoad;
    }

    public void addCar(Car car) {
        /* Find the lane with the fewest car and add one to it */
        int index = 0;
        int currentMin = cars.get(index).size();
        for (int i = 1; i < cars.size(); i++) {
            if (cars.get(i).size() < currentMin) {
                index = i;
                currentMin = cars.get(i).size();
            }
        }

        if (index < cars.size()) {
            cars.get(index).add(car);
            car.resetPosition();
        }
    }

    public void moveCars() {
        /*
         * loop through each lane and each car in the lane,
         * move the car along the road,
         * if it reaches then move it to the next road
         * if there is no further road then remove it
         */

        int carsAtLight = 0;
        int maxCarsAtLight = -1;
        boolean lastRoad = true;
        if(nextRoad!=null){
            maxCarsAtLight = nextRoad.getLanes();
            lastRoad = false;
        }
        
        System.out.println(canGo);
        for (ArrayList<Car> lane : cars) {
            for (int i = 0; i < lane.size(); i++) {
                boolean frontCar = true;
                double breakPos = roadLength;
                if (i >= 1) {
                    breakPos = lane.get(i - 1).getPosition();
                    frontCar = false;
                }
                
                boolean mustBrake = false;
                double predictedPos = lane.get(i).predictPosition();
                if(predictedPos+Car.carSpacingDist>=breakPos){
                    mustBrake = true;
                    if(frontCar && canGo){
                        if(lastRoad||carsAtLight<maxCarsAtLight){
                            System.out.println("gooo");
                            mustBrake = false;
                            carsAtLight++;
                        }
                    }
                }
                lane.get(i).move(speedLimit, canGo, mustBrake);

                if(nextRoad !=null) 
                    maxCarsAtLight = nextRoad.getLanes();

                if (lane.get(i).getPosition() >= roadLength) {
                    if (nextRoad != null)
                        nextRoad.addCar(lane.get(i));
                    if (!canGo){
                        System.out.println("Ran red light " + Road.redLightsRan);
                        Road.redLightsRan++;
                    }
                    lane.remove(i);
                    i--;
                }
            }
        }
    }

    public void updateLights(double totalTime) {
        currentDuration -= totalTime;
        if (currentDuration <= 0) {
            canGo = !canGo;
            if (canGo)
                currentDuration = goDuration;
            else
                currentDuration = stopDuration;
        }
    }

    public boolean canGo() {
        return this.canGo;
    }

    public ArrayList<ArrayList<Car>> getCars() {
        return this.cars;
    }
}