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
    private RoadTile nextRoad;
    private ArrayList<Car> cars = new ArrayList<Car>();

    public RoadTile(int speedLimit, int lanes){
        this.speedLimit = speedLimit;
        this.lanes = lanes;
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

    public RoadTile getNextRoad(){
        return this.nextRoad;
    }

    public void addCar(Car car){
        cars.add(car);
    }

    public void moveCars(){
        if (cars.size()>0) {
            System.out.println("car exists");
            cars.get(0).move(5);
            if(cars.get(0).getPosition()>=50){
                System.out.println("moving car");

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