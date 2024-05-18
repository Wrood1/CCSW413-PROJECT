import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.Serial;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JPanel;


public class Board extends JPanel implements Runnable, Commons {
    @Serial
    private static final long serialVersionUID = 1L;
    private static Board instance;
    private Dimension d;
    private ArrayList<Alien> aliens;
    private Player player;
    private Shot shot;
    private ArrayList<Shot> shots; // Use an ArrayList to manage multiple shots
    private GameOver gameend = new GameOver();

    private Won vunnet;
    private ArrayList<Bomb> bombs;
    private GameStateManager gameStateManager;


    private int alienX = 150;
    private int alienY = 25;
    private int direction = -1;
    private int deaths = 0;

    private boolean ingame = true;
    private boolean havewon = true;
    private final String expl = "/img/explosion.png";
    private final String alienpix = "/img/alien.png";
    private String message = "Your planet belongs to us now...";

    private Thread animator;
    private Shot shotPrototype;

    // Private constructor for Singleton pattern
    private Board() {
        initBoard();
        shotPrototype = new Shot(); // Initialize shotPrototype

        gameStateManager = new GameStateManager(); // Initialize GameStateManager
        GameOverDisplay gameOverDisplay = new GameOverDisplay(); // Create GameOverDisplay observer
        gameStateManager.registerObserver(gameOverDisplay); // Register observer

    }

    private void initBoard() {
        addKeyListener(new TAdapter());
        setFocusable(true);
        d = new Dimension(BOARD_WIDTH, BOARD_HEIGTH);
        setBackground(Color.black);

        gameInit();
        setDoubleBuffered(true);
    }

    public static synchronized Board getInstance() {
        if (instance == null) {
            instance = new Board();
        }
        return instance;
    }


    private void gameInit() {
        shot = new Shot(); // Initialize the shot object
        aliens = new ArrayList<>();
        shots = new ArrayList<>(); // Initialize the list of shots
        player = new Player(); // Create a Player object
        bombs = new ArrayList<>(); // Initialize the list of bombs

        ImageIcon ii = new ImageIcon(this.getClass().getResource(alienpix));
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 6; j++) {
                Alien alien = new Alien(alienX + 18 * j, alienY + 18 * i);
                alien.setImage(ii.getImage());
                aliens.add(alien);
            }
        }
        if (animator == null || !ingame) {
            animator = new Thread(this);
            animator.start();
        }
    }

    // Add a new shot to the board
    public void addShot(Shot shot) {
        shots.add(shot);
        System.out.println("Shot added: " + shot); // Print the shot being added just for check

    }

    public void drawAliens(Graphics g) {
        Iterator<Alien> it = aliens.iterator();

        while (it.hasNext()) {
            Alien alien = it.next();
            if (alien.isVisible()) {
                g.drawImage(alien.getImage(), alien.getX(), alien.getY(), this);
            }
            if (alien.isDying()) {
                alien.die();
            }
        }
    }
    public void drawPlayer(Graphics g) {
        if (player.isVisible()) {
            g.drawImage(player.getImage(), player.getX(), player.getY(), this);
        }

        if (player.isDying()) {
            player.die();
            havewon = false;
            ingame = false;
            notifyGameOver(); // Notify observers on game over

        }
    }
    public void drawGameEnd(Graphics g) {
        g.drawImage(gameend.getImage(), 0, 0, this);
    }
    public void drawShot(Graphics g) {
        for (Shot shot : shots) {
            if (shot.isVisible()) {
                g.drawImage(shot.getImage(), shot.getX(), shot.getY(), this);
            }
        }
    }
    public void drawBombing(Graphics g) {
        Iterator<Bomb> i3 = bombs.iterator();

        while (i3.hasNext()) {
            Bomb b = i3.next();

            if (!b.isDestroyed()) {
                g.drawImage(b.getImage(), b.getX(), b.getY(), this);
            }
        }
    }

    public void paint(Graphics g) {
        super.paint(g);

        g.setColor(Color.black);
        g.fillRect(0, 0, d.width, d.height);
        g.setColor(Color.green);

        if (ingame) {

            g.drawLine(0, GROUND, BOARD_WIDTH, GROUND);
            drawAliens(g);
            drawPlayer(g);
            drawBombing(g);
            drawShot(g);
        }
        else {
            drawGameEnd(g);
        }

        Toolkit.getDefaultToolkit().sync();
        g.dispose();

    }

    public void gameOver() {
        Graphics g = this.getGraphics();
        gameend = new GameOver();
        vunnet = new Won();
        // g.setColor(Color.black);
        g.fillRect(0, 0, BOARD_WIDTH, BOARD_HEIGTH);
        if (havewon == true) {
            g.drawImage(vunnet.getImage(), 0, 0, this);
        } else {
            g.drawImage(gameend.getImage(), 0, 0, this);
        }
        g.setColor(new Color(0, 32, 48));
        g.fillRect(50, BOARD_WIDTH / 2 - 30, BOARD_WIDTH - 100, 50);
        g.setColor(Color.white);
        g.drawRect(50, BOARD_WIDTH / 2 - 30, BOARD_WIDTH - 100, 50);
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = this.getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(message, (BOARD_WIDTH - metr.stringWidth(message)) / 2,
                BOARD_WIDTH / 2);
    }

    private void notifyGameOver() {
        gameStateManager.notifyObservers("GAME_OVER");
    }

    public void animationCycle() {
        for (Shot shot : shots) {
            if (shot.isVisible()) {
                int y = shot.getY();
                y -= SHOT_SPEED; // Adjust SHOT_SPEED to control the speed of the shot
                shot.setY(y);
                if (y < 0) {
                    shot.setVisible(false); // Hide the shot when it goes off-screen
                }
                Iterator<Alien> it = aliens.iterator();
                int shotX = shot.getX();
                int shotY = shot.getY();
                while (it.hasNext()) {
                    Alien alien = it.next();
                    int alienX = alien.getX();
                    int alienY = alien.getY();

                    if (alien.isVisible() && shot.isVisible()) {
                        if (shotX >= (alienX) && shotX <= (alienX + ALIEN_WIDTH)
                                && shotY >= (alienY)
                                && shotY <= (alienY + ALIEN_HEIGHT)) {
                            ImageIcon ii = new ImageIcon(getClass().getResource(expl));
                            alien.setImage(ii.getImage());
                            alien.setDying(true);
                            deaths++;
                            shot.die();
                        }
                    }
                }
                y = shot.getY();
                y -= 8;
                if (y < 0)
                    shot.die();
                else
                    shot.setY(y);
            }
        }
        if (deaths == NUMBER_OF_ALIENS_TO_DESTROY) {
            ingame = false;
            message = "Congratulations! You saved the galaxy!";
        }

        // player
        player.act();

        // shot
        if (shot.isVisible()) {
            Iterator<Alien> it = aliens.iterator();
            int shotX = shot.getX();
            int shotY = shot.getY();
            while (it.hasNext()) {
                Alien alien = it.next();
                int alienX = alien.getX();
                int alienY = alien.getY();
                if (alien.isVisible() && shot.isVisible()) {
                    if (shotX >= (alienX) && shotX <= (alienX + ALIEN_WIDTH)
                            && shotY >= (alienY)
                            && shotY <= (alienY + ALIEN_HEIGHT)) {
                        ImageIcon ii = new ImageIcon(getClass().getResource(expl));
                        alien.setImage(ii.getImage());
                        alien.setDying(true);
                        deaths++;
                        shot.die();
                    }
                }
            }
            int y = shot.getY();
            y -= 8;
            if (y < 0)
                shot.die();
            else
                shot.setY(y);
        }

        // aliens
        Iterator<Alien> it1 = aliens.iterator();
        while (it1.hasNext()) {
            Alien a1 = it1.next();
            int x = a1.getX();

            if (x >= BOARD_WIDTH - BORDER_RIGHT && direction != -1) {
                direction = -1;
                Iterator<Alien> i1 = aliens.iterator();
                while (i1.hasNext()) {
                    Alien a2 = i1.next();
                    a2.setY(a2.getY() + GO_DOWN);
                }
            }
            if (x <= BORDER_LEFT && direction != 1) {
                direction = 1;

                Iterator<Alien> i2 = aliens.iterator();
                while (i2.hasNext()) {
                    Alien a = i2.next();
                    a.setY(a.getY() + GO_DOWN);
                }
            }
        }

        Iterator<Alien> it = aliens.iterator();
        while (it.hasNext()) {
            Alien alien = it.next();
            if (alien.isVisible()) {
                int y = alien.getY();
                if (y > GROUND - ALIEN_HEIGHT) {
                    havewon = false;
                    ingame = false;
                    message = "Aliens are invading the galaxy!";
                    notifyGameOver(); // Notify observers on game over
                }
                alien.act(direction);

            }
        }

        // bombs
        Iterator<Alien> i3 = aliens.iterator();
        Random generator = new Random();
        while (i3.hasNext()) {
            int shot = generator.nextInt(15);
            Alien a = i3.next();
            Bomb b = a.getBomb();
            if (shot == CHANCE && a.isVisible() && b.isDestroyed()) {
                b.setDestroyed(false);
                b.setX(a.getX());
                b.setY(a.getY());
            }
            int bombX = b.getX();
            int bombY = b.getY();
            int playerX = player.getX();
            int playerY = player.getY();

            if (player.isVisible() && !b.isDestroyed()) {
                if (bombX >= (playerX) && bombX <= (playerX + PLAYER_WIDTH)
                        && bombY >= (playerY)
                        && bombY <= (playerY + PLAYER_HEIGHT)) {
                    ImageIcon ii = new ImageIcon(this.getClass().getResource(expl));
                    player.setImage(ii.getImage());
                    player.setDying(true);
                    b.setDestroyed(true);
                    notifyGameOver(); // Notify observers on game over

                }
            }

            if (!b.isDestroyed()) {
                b.setY(b.getY() + 1);
                if (b.getY() >= GROUND - BOMB_HEIGHT) {
                    b.setDestroyed(true);
                }
            }
        }
    }


    public void run() {
        long beforeTime, timeDiff, sleep;

        beforeTime = System.currentTimeMillis();

        while (ingame) {
            repaint();
            animationCycle();

            timeDiff = System.currentTimeMillis() - beforeTime;
            sleep = DELAY - timeDiff;

            if (sleep < 0)
                sleep = 1;
            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                System.out.println("interrupted");
            }
            beforeTime = System.currentTimeMillis();
        }
        gameOver();
    }

    private class TAdapter extends KeyAdapter {
        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode();
            if (ingame && key == KeyEvent.VK_SPACE) {
                Shot newShot = shotPrototype.clone();
                newShot.setX(player.getX()); // Set initial position
                newShot.setY(player.getY());
                newShot.setVisible(true); // Ensure shot is visible when fired
                addShot(newShot); // Add the new shot to the board
            }
            player.keyPressed(e); // Move player even if space is pressed

        }
    }
}
