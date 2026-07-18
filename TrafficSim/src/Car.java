public class Car{

    private double position = 0;
    private double accel = 0;
    private double deccel = 0;
    private double vel = 0;
    private boolean crashed;
    public static final double carSpacingDist = 55;

    public Car(){
        this.position = 0;
        this.accel = 1*0.2;
        this.deccel = 1;
        this.crashed = false;
    }

    /* Increase/decrease velocity toward the target speed
        Decrease velocity to avoid crashing into the car ahead
        Move the car forward by its velocity */
    public void move(double targetSpeed, double carAheadPosition){
        if(crashed)
            return;

        if(position>=carAheadPosition)
            crashed = true;

        if(position+carSpacingDist>=carAheadPosition)
            this.vel = Math.max(0, this.vel - this.deccel); 
        else if(targetSpeed>vel)
            this.vel = Math.min(targetSpeed, this.vel + this.accel); 
        else if(targetSpeed<vel)
            this.vel = Math.max(targetSpeed, this.vel - this.deccel); 
        this.position+=vel;
    }

    public void resetPosition(){
        this.position = 0;
    }

    public double getPosition(){
        return this.position;
    }
}