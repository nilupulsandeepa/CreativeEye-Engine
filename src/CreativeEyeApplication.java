import com.techlounge.creativeeye.CEEngine;

public class CreativeEyeApplication {

    private CEEngine gameEngine;

    public static void main(String[] args) {
        CreativeEyeApplication creativeEyeApplication = new CreativeEyeApplication();
        creativeEyeApplication.startGameEngine();
    }

    public void startGameEngine() {
        this.gameEngine = new CEEngine();
        this.gameEngine.start();
    }
}
