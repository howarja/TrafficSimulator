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
        /* loop through each lane and each car in the lane, 
            move the car along the road, 
            if it reaches then move it to the next road
            if there is no further road then remove it */
        for (ArrayList<Car> lane : cars) {
            for (int i = 0; i < lane.size(); i++) {
                double nextCarPos = 10000;
                if (i > 1)
                    nextCarPos = lane.get(i - 1).getPosition();

                lane.get(i).move(speedLimit, nextCarPos);
                if (lane.get(i).getPosition() >= roadLength) {
                    if (nextRoad != null)
                        nextRoad.addCar(lane.get(i));
                    lane.remove(i);
                    i--;
                }
            }
        }
    }

    public ArrayList<ArrayList<Car>> getCars() {
        return this.cars;
    }
}