import java.util.ArrayList;
import java.util.List;



 interface GameStateObserver {
    void updateGameState(String gameState);
}
/* */
 interface GameStateSubject {
    void registerObserver(GameStateObserver observer);
    void removeObserver(GameStateObserver observer);
    void notifyObservers(String gameState);
}

class GameStateManager implements GameStateSubject {
    private List<GameStateObserver> observers;

    public GameStateManager() {
        this.observers = new ArrayList<>();
    }

    @Override
    public void registerObserver(GameStateObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(GameStateObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(String gameState) {
        for (GameStateObserver observer : observers) {
            observer.updateGameState(gameState);
        }
    }
}
