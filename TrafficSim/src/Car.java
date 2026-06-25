public class Car{

    private double position = 0;
    private double speed = 1;

    public Car(){
        this.position = 0;
        this.speed = 2;
    }

    public void move(double movement){
        this.position+=movement;
        //System.out.println(this.position);
    }

    public void resetPosition(){
        this.position = 0;
    }

    public double getPosition(){
        return this.position;
    }

}