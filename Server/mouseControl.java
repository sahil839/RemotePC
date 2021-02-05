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
    public static void mouseWheel(int wheelAmt) {
        robot.mouseWheel(wheelAmt);
    }
    public static void doType(int... keyCodes)
    {
        int length = keyCodes.length;
        for(int i = 0; i < length; i++) {
            robot.keyPress(keyCodes[i]);
        }
        robot.delay(10);
        for(int i = length - 1; i >= 0; i--) {
            robot.keyRelease(keyCodes[i]);
        }
    }
    public static void typeCharacter(char character){
        switch (character) {
            	case'A': doType(VK_SHIFT, VK_A); break;
		case'B': doType(VK_SHIFT, VK_B); break;
		case'C': doType(VK_SHIFT, VK_C); break;
		case'D': doType(VK_SHIFT, VK_D); break;
		case'E': doType(VK_SHIFT, VK_E); break;
		case'F': doType(VK_SHIFT, VK_F); break;
		case'G': doType(VK_SHIFT, VK_G); break;
		case'H': doType(VK_SHIFT, VK_H); break;
		case'I': doType(VK_SHIFT, VK_I); break;
		case'J': doType(VK_SHIFT, VK_J); break;
		case'K': doType(VK_SHIFT, VK_K); break;
		case'L': doType(VK_SHIFT, VK_L); break;
		case'M': doType(VK_SHIFT, VK_M); break;
		case'N': doType(VK_SHIFT, VK_N); break;
		case'O': doType(VK_SHIFT, VK_O); break;
		case'P': doType(VK_SHIFT, VK_P); break;
		case'Q': doType(VK_SHIFT, VK_Q); break;
		case'R': doType(VK_SHIFT, VK_R); break;
		case'S': doType(VK_SHIFT, VK_S); break;
		case'T': doType(VK_SHIFT, VK_T); break;
		case'U': doType(VK_SHIFT, VK_U); break;
		case'V': doType(VK_SHIFT, VK_V); break;
		case'W': doType(VK_SHIFT, VK_W); break;
		case'X': doType(VK_SHIFT, VK_X); break;
		case'Y': doType(VK_SHIFT, VK_Y); break;
		case'Z': doType(VK_SHIFT, VK_Z); break;
		case'a': doType(VK_A); break;
		case'b': doType(VK_B); break;
		case'c': doType(VK_C); break;
		case'd': doType(VK_D); break;
		case'e': doType(VK_E); break;
		case'f': doType(VK_F); break;
		case'g': doType(VK_G); break;
		case'h': doType(VK_H); break;
		case'i': doType(VK_I); break;
		case'j': doType(VK_J); break;
		case'k': doType(VK_K); break;
		case'l': doType(VK_L); break;
		case'm': doType(VK_M); break;
		case'n': doType(VK_N); break;
		case'o': doType(VK_O); break;
		case'p': doType(VK_P); break;
		case'q': doType(VK_Q); break;
		case'r': doType(VK_R); break;
		case's': doType(VK_S); break;
		case't': doType(VK_T); break;
		case'u': doType(VK_U); break;
		case'v': doType(VK_V); break;
		case'w': doType(VK_W); break;
		case'x': doType(VK_X); break;
		case'y': doType(VK_Y); break;
		case'z': doType(VK_Z); break;
            default:
                // Throw it error

        }
    }
}
