public class Car{

    private double position;
    private double speed = 1;

    public Car(){
        this.position = 0;
        this.speed = 2;
    }

    public void move(){
        this.position+=speed;
        System.out.println(position);
    }

    public void resetPosition(){
        this.position = 0;
    }

    public double getPosition(){
        return this.position;
    }

}