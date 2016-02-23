import org.jsfml.graphics.Color;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Text;

import java.util.Random;

/**
 * Boss State class used to manage the final boss level, which occurs at the last waypoint of the map.
 */
public class BossEvent extends CombatEvent {

    public BossEvent(FSM stateMachine, GameDriver driver, RenderWindow window, Textures textures, Random randGenerator, EventGenerator eventGenerator, SoundFX sound, PlayerShip playerShip) {
        super(stateMachine, driver, window, textures, randGenerator, eventGenerator, sound, playerShip);
        setup();
    }

    @Override
    public void chooseDifficulty() {
        difficulty = EnemyShip.Difficulty.BOSS;
    }

    public void setTitle(){
        title = new Text("Boss Battle", fontStyle, titleFontSize);
        title.setPosition(driver.getWinWidth() / 2, 300);
        title.setOrigin(title.getLocalBounds().width / 2, title.getLocalBounds().height / 2);
        title.setColor(Color.BLACK);
        title.setStyle(Text.BOLD);
    }

    // Boss will NEVER retreat
    public void actionAI() {
            enemyShip.attack(playerShip);
            if (playerShip.getHullHP() <= 0) {
                stateMachine.getStates().set(7, new GameOver(stateMachine, driver, window, textures, driver.leaderboardObj, GameOver.Reason.PLAYER_HULL_DESTROYED));
                stateMachine.setState(stateMachine.getStates().get(7));     // Game over, player hull destroyed
            }
    }

    public void checkWin() {
        if (enemyShip.getHullHP() <= 0) {
            playerShip.addPlayerScore(1500);
            stateMachine.getStates().set(7, new GameOver(stateMachine, driver, window, textures, driver.leaderboardObj, GameOver.Reason.BOSS_KILL));
            stateMachine.setState(stateMachine.getStates().get(7));         // Game over, enemy ship destroyed
        }
    }
}
