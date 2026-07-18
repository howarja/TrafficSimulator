public class ProgramLoop {

    private final int TARGET_FPS = 60;
    private final long TARGET_NS_PER_FRAME = 1000000000 / TARGET_FPS;// get the required nanoseconds between each frame
                                                                     // for the target fps

    public static long time;

    private boolean running;
    private double carSpawnTime = 10;
    private double currentCarSpawnTime;

    private double carUpdateTime = 0.3;
    private double currentUpdateTime;

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
            double totalTime = (double)(updateTime)/100;
            // spawn cars
            currentCarSpawnTime-=totalTime;
            if(currentCarSpawnTime<=0){
                window.addCar();
                currentCarSpawnTime = carSpawnTime;
            }

            // update cars
            currentUpdateTime-=totalTime;
            if(currentUpdateTime<=0){
                window.updateCars();
                window.updateRoadLights(totalTime);
                currentUpdateTime = carUpdateTime;
            }

            /*-------------WAIT TIME----------- */
            try {
                Thread.sleep(Math.max(updateTime, 0));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}