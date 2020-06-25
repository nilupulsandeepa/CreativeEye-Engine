import com.techlounge.creativeeye.Engine;

public class CreativeEyeApplication {

    private Engine gameEngine;

    public static void main(String[] args) {
        CreativeEyeApplication creativeEyeApplication = new CreativeEyeApplication();
        creativeEyeApplication.startGameEngine();
    }

    public void startGameEngine() {
        this.gameEngine = new Engine();
        this.gameEngine.start();
    }
}
