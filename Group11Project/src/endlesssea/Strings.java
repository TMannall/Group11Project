package endlesssea;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.Font;
import org.jsfml.graphics.Text;

import java.io.IOException;
import java.nio.file.Paths;

public class Strings {
    // main menu
    public static String title = "Endless Sea";
    public static String newGame2 = "New Game";
    public static String loadGame = "Load Game";
    public static String credits = "Credits";
    public static String quit = "Exit";

    // map & trading
    public static String returnToMenu = "Return To Menu";
    public static String store = "Trading Post";
    public static String fort = "Fortress";
    public static String trade = "Start Trading";
    public static String buy = "Buy";

    // variable prefixes
    public static String funds = "Gold: ";
    public static String resources = "Resources: ";
    public static String shipHealth = "Hull Strength: ";
    public static String mastHealth = "Mast Strength: ";

    // combat
    public static String ammoTypeCanister = "Canister Shot";
    public static String ammoTypeSolid = "Round Shot";
    public static String ammoTypeChain = "Chain SHot";


    // if we end up having enough of these, they can be moved to their own class
    // they're just here for now becuase it's easier
    public Text menuFont(String string) {
        Font menuFont = new Font(); // using a temporary font for now... we can change this later
        try {
            menuFont.loadFromFile(Paths.get("LucidaSansRegular.ttf"));
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }

        Text text = new Text(string, menuFont, 18);
        text.setColor(Color.BLUE);
        text.setStyle(Text.BOLD | Text.ITALIC);

        return text;
    }

    public void gameFont(String string) {
        Font menuFont = new Font(); // using a temporary font for now... we can change this later
        try {
            menuFont.loadFromFile(Paths.get("LucidaSansRegular.ttf"));
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }

        Text text = new Text(string, menuFont, 12);
    }
}
