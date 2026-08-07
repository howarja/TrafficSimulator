/**
 *  Store the data for a single car, 
 *  handle movement(acceleration, deceleration and displacement) of the car
 *
 * @author James Howard
 * @version 7/8/25
 */ 

public class Car{

    private double position = 0;
    private double accel = 0;
    private double deccel = 0;
    private double vel = 0;
    public static double carSpacingDist = 55;

    public Car(){
        this.position = 0;
        this.accel = 1*0.2;
        this.deccel = 1;
    }

    /** Increase/decrease velocity toward the target speed
        Decrease velocity to avoid crashing into the car ahead
        Move the car forward by its velocity 
        
        @param targetSpeed the speed that the car is trying to reach, speed limit passed from roadTile
        @param carAheadPosition the position of the car infront of this one
        @param canGo wether the stop lights are active
        @param roadLength the length of the full roadTile that this car is on
    */
    public void move(double targetSpeed, boolean canGo, boolean brake){
        if(brake){
            this.vel = Math.max(0, this.vel - this.deccel); 
        }
        else if(targetSpeed>vel){
            this.vel = Math.min(targetSpeed, this.vel + this.accel);
        }
        else if(targetSpeed<vel){
            this.vel = Math.max(targetSpeed, this.vel - this.deccel);
        }

        /* Move the car forward */
        this.position+=vel;
    }
    
    /**
     * Calculate were the car will be if it brakes right now 
    */
    public double predictPosition(){
        
        double predictedPos = this.position;
        double predictedVel = this.vel;
        while (predictedVel>0) {
            predictedVel = Math.max(0, predictedVel - this.deccel); 
            predictedPos+=predictedVel;
        }
        return predictedPos;
    }

    /** 
     * Reset the position of the road to zero, for when this car is moved to a new road segment
     */
    public void resetPosition(){
        this.position = 0;
    }

    /**
     * @param newStoppingDistance The new distance that cars aim to space themselves
     * Set the target stopping distance for cars between each other and stop lights
     */
    public static void setStoppingDistance(double newStoppingDistance){
        carSpacingDist = newStoppingDistance;
    }

    public double getPosition(){
        return this.position;
    }
}
