/**
 * Call updates on the program on a timer and consitent intervals
 *
 * @author James Howard
 * @version 7/8/25
 */ 

public class ProgramLoop {

    private final int TARGET_FPS = 60;
    private final long TARGET_NS_PER_FRAME = 1000000000 / TARGET_FPS;// get the required nanoseconds between each frame
                                                                     // for the target fps

    public static long time;

    private boolean running;
    private static double carSpawnTime = 2500;
    private double currentCarSpawnTime;
    private static final int milliToSecond = 1000;

    private double carUpdateTime = 30;
    private double currentUpdateTime;

    private double lastMilliSecondTime = 0;

    public ProgramLoop(Window window) {
        /* Set up window, panel and frame loop */
        processLoop(window);
    }

    private void processLoop(Window window) {
        /*
         * Perform calcuations required for the frame
         * wait the remaining amount of time for the frame
         */

        long elapsedTime;// actual time that the program has taken
        long updateTime;// time to wait for the target FPS accounting for the elapsed time

        var startTime = System.currentTimeMillis();
        long timeOffset = 0;
        running = true;
        while (running) {

            /*-------------FIND CURRENT TIME----------- */
            var previousTime = System.nanoTime();
            time = (System.currentTimeMillis() - startTime) + timeOffset;

            /*-------------FRAME CALCULATIONS----------- */
            window.repaint();

            /*-------------FIND REMAINING FRAME TIME----------- */
            elapsedTime = System.nanoTime() - previousTime;// time taken for the frame
            updateTime = (TARGET_NS_PER_FRAME - elapsedTime) / 1000000;// remaining time untill next frame(in
                                                                       // microseconds for Thread.sleep)

            double timerDiff = System.currentTimeMillis()-lastMilliSecondTime;
            // spawn cars
            currentCarSpawnTime-=timerDiff;
            if(currentCarSpawnTime<=0){
                window.addCar();
                currentCarSpawnTime = carSpawnTime;
            }

            // update cars
            currentUpdateTime-=timerDiff;
            if(currentUpdateTime<=0){
                window.updateCars();
                window.updateRoadLights(timerDiff);
                currentUpdateTime = carUpdateTime;
            }

            /*-------------WAIT TIME----------- */
            lastMilliSecondTime = System.currentTimeMillis();
            try {
                Thread.sleep(Math.max(updateTime, 0));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void setCarSpawnTime(double newSpawnTime){
        carSpawnTime = newSpawnTime*milliToSecond;
    }
}
