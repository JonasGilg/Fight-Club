package GameObjects;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import main.Main;

import Scenes.Level;
import Scenes.Settings;

public final class Player extends Charakter {
	KeyListener klLeftRight;
	KeyListener klActions;
	KeyListener klUpDown;

	private static Dimension playerHitbox = new Dimension(50, 100);

	private static int hp = 500;

	public Player(Point pPosition, String name, Level level) {
		super(new Rectangle(pPosition, playerHitbox), name, hp, level);
		

		klLeftRight = new KeyAdapter() {
			@Override
			public final void keyPressed(KeyEvent e) {

				if (e.getKeyCode() == Settings.keys[playerNumber][0]) {
					left = true;
				}
				if (e.getKeyCode() == Settings.keys[playerNumber][1]) {
					right = true;
				}
			}

			@Override
			public final void keyReleased(KeyEvent e) {

				if (e.getKeyCode() == Settings.keys[playerNumber][0]) {
					left = false;
				}
				if (e.getKeyCode() == Settings.keys[playerNumber][1]) {
					right = false;
				}
			}
		};

		klUpDown = new KeyAdapter() {

			@Override
			public final void keyPressed(KeyEvent e) {

				if ((e.getKeyCode() == Settings.keys[playerNumber][2])
						&& (jumped == false || doubleJumped == false)) {
					if (jumped) {
						doubleJumped = true;
					}
					resetJumpCounter();
					jump = true;

				}

				if (e.getKeyCode() == Settings.keys[playerNumber][3]) {
					fall = true;
				}
				if (e.getKeyCode() == Settings.keys[playerNumber][6]) { // &&
																		// !duck
					duck = true;
				}
			}

			@Override
			public final void keyReleased(KeyEvent e) {

				if (e.getKeyCode() == Settings.keys[playerNumber][2]) {

				}
				if (e.getKeyCode() == Settings.keys[playerNumber][3]) {

				}
				if (e.getKeyCode() == Settings.keys[playerNumber][6]) { // &&
																		// duck
					standUp = true;

				}
			}
		};

		klActions = new KeyAdapter() {

			@Override
			public final void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == Settings.keys[playerNumber][7]
						&& kickFree) {
					kickFree = false;
					kick = true;

				}
				if (e.getKeyCode() == Settings.keys[playerNumber][4]
						&& punchFree) {
					punchFree = false;
					punch = true;

				}
				if (e.getKeyCode() == Settings.keys[playerNumber][5]
						&& blockFree) {
					blockFree = false;
					block = true;
				}
			}

			@Override
			public final void keyReleased(KeyEvent e) {

				if (e.getKeyCode() == Settings.keys[playerNumber][5]) {
					blockFree = true;

				}
				if (e.getKeyCode() == Settings.keys[playerNumber][4]) {
					punchFree = true;
				}
				if (e.getKeyCode() == Settings.keys[playerNumber][7]) {
					kickFree = true;
				}

			}

		};
	}

	@Override
	public final void run() {
		Main.MAIN.addKeyListener(klLeftRight);
		Main.MAIN.addKeyListener(klUpDown);
		Main.MAIN.addKeyListener(klActions);
		currentImage = g.get(18);
		super.run();
		setName("Player Thread");

	}

}
