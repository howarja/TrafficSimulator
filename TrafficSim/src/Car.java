public class Car{

    private double position = 0;
    private double accel = 0;
    private double deccel = 0;
    private double vel = 0;

    public Car(){
        this.position = 0;
        this.accel = 1*0.2;
        this.deccel = 5;
    }

    public void move(double target){
        if(target>vel)
            this.vel = Math.min(target, this.vel + this.accel); 
        else if(target<vel)
            this.vel = Math.max(target, this.vel - this.deccel); 
        this.position+=vel;
    }

    public void resetPosition(){
        this.position = 0;
    }

    public double getPosition(){
        return this.position;
    }

}