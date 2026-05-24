public class Road{
    
    private int speedLimit;
    private int lanes;
    private Road nextRoad;

    public Road(int speedLimit, int lanes){
        this.speedLimit = speedLimit;
        this.lanes = lanes;
    }

    public void setNextRoad(Road nextRoad){
        this.nextRoad = nextRoad;
    }

    public int getSpeedLimit(){
        return this.speedLimit;
    }

    public int getLanes(){
        return this.lanes;
    }

    public Road getNextRoad(){
        return this.nextRoad;
    }
}