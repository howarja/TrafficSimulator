/**
 * Store the data for a single tile of the road
 *
 * @author James Howard
 * @version 21/5/25
 */

public class RoadTile{
    
    private int speedLimit;
    private int lanes;
    private RoadTile nextRoad;

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
}