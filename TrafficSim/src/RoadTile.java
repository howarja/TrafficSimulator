import java.util.ArrayList;

/**
 * Store the data for a single tile of the road
 *
 * @author James Howard
 * @version 21/5/25
 */

import java.util.ArrayList;
public class RoadTile{
    
    private int speedLimit;
    private int lanes;
    private int roadLength;
    private RoadTile nextRoad;
    private ArrayList<Car> cars = new ArrayList<Car>();

    public RoadTile(int speedLimit, int lanes, int length){
        this.speedLimit = speedLimit;
        this.lanes = lanes;
        this.roadLength = length;
    }

    public void setNextRoad(RoadTile nextRoad){
        this.nextRoad = nextRoad;
    }

    public int getSpeedLimit(){
        return this.speedLimit;
    }

    public int getLanes(){
        return this.lanes;
    }

    public int getLength(){
        return this.roadLength;
    }

    public RoadTile getNextRoad(){
        return this.nextRoad;
    }

    public void addCar(Car car){
        cars.add(car);
        car.resetPosition();
    }

    public void moveCars(){
        if (cars.size()>0) {
            cars.get(0).move(speedLimit);
            if(cars.get(0).getPosition()>=roadLength){
                if(nextRoad!=null)
                    nextRoad.addCar(cars.get(0));
                cars.remove(0);
            }
        }
    }

    public ArrayList<Car> getCars(){
        return this.cars;
    }
}