import javax.swing.*;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class GameOver extends Sprite implements Commons, GameStateObserver {
    private final String gameOver = "/img/gameover.png";
    private int width;

    public GameOver() {
        ImageIcon ii = new ImageIcon(this.getClass().getResource(gameOver));
        setWidth(ii.getImage().getWidth(null));
        setImage(ii.getImage());
        setX(0);
        setY(0);
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public void updateGameState(String gameState) {
        if ("GAME_OVER".equals(gameState)) {
            System.out.println("Game Over! Displaying game over screen...");
            showGameOverScreen();
        }
    }

    private void showGameOverScreen() {
		// Run the dialog box in a separate thread
		SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(null, "Game Over! Try Again?"));
	}
	
}
