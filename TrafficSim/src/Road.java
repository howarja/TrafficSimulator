

public class Road{

    private RoadTile firstTile;
    private RoadTile lastTile;
    

    public Road(){

    }

    public void extendRoad(int speedLimit, int lanes, int length){
        /* Add a new tile to the road with given parameters */
        RoadTile newTile = new RoadTile(speedLimit, lanes, length);
        if(firstTile!=null){
            lastTile.setNextRoad(newTile);
            lastTile = newTile;
        }else{
            lastTile = newTile;
            firstTile = newTile;
        }

        printRoad();
    }
    
    public void printRoad(){
        /* Print out ever tile in the road */
        RoadTile currentTile = firstTile;
        while(currentTile!=null){
            System.out.println(currentTile);
            currentTile = currentTile.getNextRoad();
        }
    }

    public RoadTile getFirsTile(){
        return this.firstTile;
    }

    public void addCar(){
        /* Add a car to the first tile of the road */
        if(this.firstTile!=null)
            this.firstTile.addCar(new Car());
    }

    public void update(){
        /* Move the cars on every tile of the road */
        RoadTile currentTile = firstTile;
        while(currentTile!=null){
            currentTile.moveCars();
            currentTile = currentTile.getNextRoad();
        }
    }
}