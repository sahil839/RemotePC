import java.awt.Robot;
import java.awt.event.InputEvent;

public class mouseControl {
	static Robot robot;
    public mouseControl() {
        try {
            robot = new Robot();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
    public static void leftClick() {
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }
    public static void mouseMove(int x, int y) {
        robot.mouseMove(x, y);
    }
}