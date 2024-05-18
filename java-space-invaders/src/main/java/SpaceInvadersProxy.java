public class SpaceInvadersProxy implements Commons {
    private SpaceInvaders realSubject;

    public SpaceInvadersProxy() {
        realSubject = null;
    }

    public void startGame() {
        if (realSubject == null) {
            realSubject = new SpaceInvaders();
        }
        realSubject.showMainMenu();
    }
}
