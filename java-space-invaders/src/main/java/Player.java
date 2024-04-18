import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;

public class Player extends Sprite implements Commons {

	private final int START_Y = 400;
	private final int START_X = 270;
	private final String player = "/img/craft.png";
	private int width;
	private Shot shotPrototype;

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

		if (key == KeyEvent.VK_LEFT) {
			dx = -2;
		}

		if (key == KeyEvent.VK_RIGHT) {
			dx = 2;
		}
		if(key == KeyEvent.VK_SPACE){
			fire();
		}
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_LEFT) {
			dx = 0;
		}

		if (key == KeyEvent.VK_RIGHT) {
			dx = 0;
		}
	}

	private void fire() {
		Shot shot = shotPrototype.clone();
		shot.setX(this.x + width / 2);
		shot.setY(this.y);
		shot.setVisible(true); // Make the cloned shot visible
		Board.getInstance().addShot(shot);
	}
}
