import com.techlounge.creativeeye.Engine;
import org.lwjgl.*;

public class CreativeEyeApplication {

    private Engine gameEngine;

    public static void main(String[] args) {
        System.out.println("CreativeEye Application Initialization");
        System.out.println((new EngineInfo()).toString());
        CreativeEyeApplication creativeEyeApplication = new CreativeEyeApplication();
        creativeEyeApplication.startGameEngine();
    }

    public void startGameEngine() {
        this.gameEngine = new Engine();
        this.gameEngine.start();
    }
}
