import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 *
 * @author reema, wrood, fareeda, sara
 */

public class SpaceInvaders extends JFrame implements Commons {
	private static final long serialVersionUID = -4905230094675077405L;

	private JButton start, help;

	/* Intro */
	private static final String TOP_MESSAGE = "Space Invaders <br> Java Version";
	private static final String INITIAL_MESSAGE ="Help us, amazing captain!!"
			+ "<br>The aliens are trying to invade our planet."
			+ "<br><br><br>Your mission:"
			+ "<br><br>Kill all the invading aliens before they can invade planet Earth."
			+ "<br>And preferably, don't die during the battle!";
	/* HELP */
	private static final String HELP_TOP_MESSAGE = "HELP";
	private static final String HELP_MESSAGE = "Controles: "
			+"<br><br>Movement to the Left: <br>Left Arrow key"
			+ "<br><br>Movement to the Right: <br>Right Arrow key"
			+ "<br><br>Shoot: <br>Space bar";

	private static final int BOARD_HEIGHT = 600;

	private JFrame frame2, frame3;

	public SpaceInvaders() {
		initUI();
	}

	/*
	 * Constructor
	 */
	private void initUI() {
		frame2 = new JFrame("Space Invaders");
		frame3 = new JFrame("HELP");

		String topmessage = "<html><br><br>" + TOP_MESSAGE + "</html>";
		String message = "<html>" + INITIAL_MESSAGE + "</html>";

		start = new JButton("Start Mission");
		start.addActionListener(new ButtonListener());
		start.setBounds(800, 800, 200, 100);

		help = new JButton("HELP");
		help.addActionListener(new HelpButton());

		JLabel text = new JLabel(message, SwingConstants.CENTER);
		JLabel toptext = new JLabel(topmessage, SwingConstants.CENTER);

		Font font = new Font("Helvetica", Font.BOLD, 12);
		text.setFont(font);

		Font font2 = new Font("Helvetica", Font.BOLD, 20);
		toptext.setFont(font2);

		frame2.setTitle("Space Invaders");

		frame2.add(text);

		frame2.add(toptext, BorderLayout.PAGE_START);
		JPanel nedredel = new JPanel();
		nedredel.add(help);
		nedredel.add(start);

		frame2.add(nedredel, BorderLayout.PAGE_END);
		frame2.setSize(500, 500);
		frame2.setLocationRelativeTo(null);
		frame2.setVisible(true);
		frame2.setResizable(false);

	}

	public void showMainMenu() {
		frame2.setVisible(true);
	}

	public void startGame() {
		JFrame frame = new JFrame("Space Invaders");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(BOARD_WIDTH, BOARD_HEIGHT);

		frame.getContentPane().add(Board.getInstance());
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		closeIntro();
	}

	public void closeIntro() {
		frame2.dispose();
		frame3.dispose();
	}

	public void closeHelp() {
		frame3.dispose();
	}


	private class ButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			startGame();

		}
	}

	private class CloseHelp implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			closeHelp();
		}
	}

	private class HelpButton implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			JButton close = new JButton("CLOSE");
			close.addActionListener(new CloseHelp());

			String topmessage = "<html><br>" + HELP_TOP_MESSAGE + "</html>";
			String message = "<html>" + HELP_MESSAGE + "</html> ";
			JLabel text = new JLabel(message, SwingConstants.CENTER);
			JLabel toptext = new JLabel(topmessage, SwingConstants.CENTER);

			Font font = new Font("Helvetica", Font.BOLD, 12);
			text.setFont(font);

			Font font2 = new Font("Helvetica", Font.BOLD, 20);
			toptext.setFont(font2);

			frame3.add(text);

			frame3.add(toptext, BorderLayout.PAGE_START);

			frame3.add(close, BorderLayout.PAGE_END);
			frame3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame3.setSize(250, 290);
			frame3.setResizable(false);
			frame3.setLocationRelativeTo(null);
			frame3.setVisible(true);
		}
	}
	/*
	 * Main
	 */
	public static void main(String[] args) {
		SpaceInvadersProxy proxy = new SpaceInvadersProxy();
		proxy.startGame();
	}	}