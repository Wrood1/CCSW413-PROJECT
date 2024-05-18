import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;

public class Player extends Sprite implements Commons {

	private final int START_Y = 400;
	private final int START_X = 270;
	private final String player = "/img/craft.png";
	private int width;
	private Shot shotPrototype;
	private CommandInvoker commandInvoker;


	/*
	 * Constructor
	 */
	public Player() {
		ImageIcon ii = new ImageIcon(this.getClass().getResource(player));

		width = ii.getImage().getWidth(null);

		setImage(ii.getImage());
		setX(START_X);
		setY(START_Y);
		shotPrototype = new Shot((START_X + width / 2 ), START_Y); // Initialize the shot prototype off-screen
		commandInvoker = new CommandInvoker();
		// Initialize commands map
		commandInvoker.registerCommand(KeyEvent.VK_LEFT, new MoveLeftCommand(this));
		commandInvoker.registerCommand(KeyEvent.VK_RIGHT, new MoveRightCommand(this));
		commandInvoker.registerCommand(KeyEvent.VK_SPACE, new ShootCommand(this));
	}

	public void act() {
		x += dx;
		if (x <= 2)
			x = 2;
		if (x >= BOARD_WIDTH - 2 * width)
			x = BOARD_WIDTH - 2 * width;
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		commandInvoker.executeCommand(key);
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		commandInvoker.stopCommand(key);
	}

	private void fire() {
		Shot shot = shotPrototype.clone();
		shot.setX(this.x + width / 2);
		shot.setY(this.y);
		shot.setVisible(true); // Make the cloned shot visible
		Board.getInstance().addShot(shot);
	}
}
class CommandInvoker {
    private Map<Integer, Command> commands;

    public CommandInvoker() {
        commands = new HashMap<>();
    }

    public void registerCommand(int keyCode, Command command) {
        commands.put(keyCode, command);
    }

    public void executeCommand(int keyCode) {
        if (commands.containsKey(keyCode)) {
            commands.get(keyCode).execute();
        }
    }

    public void stopCommand(int keyCode) {
        if (commands.containsKey(keyCode)) {
            commands.get(keyCode).stop();
        }
    }
}

interface Command {
    void execute();
    void stop();
}

class MoveLeftCommand implements Command {
    private Player player;

    public MoveLeftCommand(Player player) {
        this.player = player;
    }

    @Override
    public void execute() {
        System.out.println("Move Left Command Executed");
        player.dx = -2;
    }

    @Override
    public void stop() {
        player.dx = 0;
    }
}

class MoveRightCommand implements Command {
    private Player player;

    public MoveRightCommand(Player player) {
        this.player = player;
    }

    @Override
    public void execute() {
        System.out.println("Move Right Command Executed");
        player.dx = 2;
    }

    @Override
    public void stop() {
        player.dx = 0;
    }
}

class ShootCommand implements Command {
    private Player player;

    public ShootCommand(Player player) {
        this.player = player;
    }

    @Override
    public void execute() {
        System.out.println("Shoot Command Executed");
        player.fire();
    }

    @Override
    public void stop() {
    }
}
