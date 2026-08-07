 
/**
 * Create run the traffic simulation program
 *
 * @author James Howard
 * @version 7/8/25
 */

public class Main {
    public static void main(String[] args) throws Exception {
        Road road = new Road();
        Window window = new Window(road);
        ProgramLoop loop = new ProgramLoop(window);
    }
}
