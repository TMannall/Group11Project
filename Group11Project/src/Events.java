import org.jsfml.graphics.Font;
import org.jsfml.graphics.RenderWindow;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Random;

public abstract class Events extends FSMState {
    protected FSM stateMachine;
    protected GameDriver driver;
    protected RenderWindow window;
    protected Textures textures;
    protected Random randGenerator;
    protected EventGenerator eventGenerator;
    protected SoundFX sound;

    protected static String JavaVersion = Runtime.class.getPackage().getImplementationVersion();
    protected static String JdkFontPath = "textures/";
    protected static String JreFontPath = "textures/";
    protected static int titleFontSize = 50;
    protected static int buttonFontSize = 32;
    protected static String FontFile = "vinque.ttf";
    protected String FontPath;
    protected Font fontStyle;

    public Events(FSM stateMachine, GameDriver driver, RenderWindow window, Textures textures, Random randGenerator, EventGenerator eventGenerator, SoundFX sound){
        this.stateMachine = stateMachine;
        this.driver = driver;
        this.window = window;
        this.textures = textures;
        this.randGenerator = randGenerator;
        this.eventGenerator = eventGenerator;
        this.sound = sound;

        if ((new File(JreFontPath)).exists()) FontPath = JreFontPath;
        else FontPath = JdkFontPath;
        fontStyle = new Font();
        try {
            fontStyle.loadFromFile(
                    Paths.get(FontPath + FontFile));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public abstract void execute();
}
