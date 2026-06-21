

public class Road{

    private RoadTile firstTile;
    private RoadTile lastTile;
    

    public Road(){

    }

    public void extendRoad(int speedLimit, int lanes){
        RoadTile newTile = new RoadTile(speedLimit, lanes);
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
        if(this.firstTile!=null)
            this.firstTile.addCar(new Car());
    }

    public void update(){
        RoadTile currentTile = firstTile;
        while(currentTile!=null){
            currentTile.moveCars();
            currentTile = currentTile.getNextRoad();
        }
    }
}