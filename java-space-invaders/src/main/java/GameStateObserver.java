import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

 interface GameStateObserver {
    void updateGameState(String gameState);
}
 interface GameStateSubject {
    void registerObserver(GameStateObserver observer);
    void removeObserver(GameStateObserver observer);
    void notifyObservers(String gameState);
}

class GameStateManager implements GameStateSubject {
    //1
    private List<GameStateObserver> observers;
    public GameStateManager() {
        this.observers = new ArrayList<>();
    }

    //2
    @Override
    public void registerObserver(GameStateObserver observer) {
        observers.add(observer);
    }

    //
    @Override
    public void removeObserver(GameStateObserver observer) {
        observers.remove(observer);
    }

    //3
    @Override
    public void notifyObservers(String gameState) {
        for (GameStateObserver observer : observers) {
            observer.updateGameState(gameState);
        }
    }
}
class GameOverDisplay implements GameStateObserver {
    @Override
    public void updateGameState(String gameState) {
        if ("GAME_OVER".equals(gameState)) {
            System.out.println("Game Over! Displaying game over screen...");
            // Display game over message
            showGameOverScreen();
        }
    }
    
    private void showGameOverScreen() {
        // Implement the Game Over screen display logic here
        JOptionPane.showMessageDialog(null, "Game Over! Try Again?");
    }
}