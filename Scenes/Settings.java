package Scenes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

import javax.swing.JLabel;
import javax.swing.JPanel;

import main.CreateScene;
import main.Main;

@SuppressWarnings("serial")
public final class Settings extends Scene {
	public final static String playerName[] = { "Player 1", "Player 2", "Player 3", "Player 4" };
	public final static Color[] playerColor = { Color.blue, Color.red, Color.green, Color.yellow };
	private final String[] difficulty = { "No Skill", "Normal", "Impossible" };
	private final String[] playerMode = { "VS CPU", "2-Player", "3-Player", "4-Player" };

	public static int gravityNumber = 1;

	public static int playerModebit = 0;
	private static int changePlayer = 0;
	public static int difbyte = 1;

	private boolean waitForInput = false;
	private int keyCode;
	private JPanel keyBindFrame;
	private int toChangeKey;
	private int toChangePlayer;
	public static int bouncyBootsActTime = 15;
	public static int speedyBootsActTime = 15;
	public static int powerGloveActTime = 15;
	public static int pistolActiveTime = 15;
	// Aufl�sungsvariablen
	private final int differ1 = (int) (Main.HEIGHT * 0.078);
	private final int differ2 = (int) (Main.WIDTH * 0.022);
	private final int differ3 = (int) (Main.WIDTH * 0.1244);
	private final int differ4 = (int) (Main.HEIGHT * 0.182);
	private final int differ5 = (int) (Main.WIDTH * 0.23);
	private final int differ6 = (int) (Main.HEIGHT * 0.065);
	private final int differ7 = (int) (Main.WIDTH * 0.0878);
	private final int rektx = (int) (Main.WIDTH * 0.56);
	private final int rekty = (int) (Main.HEIGHT * 0.167);
	private final int rektwidth = (int) (Main.WIDTH * 0.22);
	private final int rektheigth = (int) (Main.HEIGHT * 0.05);

	// TASTEN
	// [player#][move#] 0links 1rechts 2jump 3fall 4punch 5block 6duck 7kick
	public static int[][] keys = { { 0x41, 0x44, 0x57, 0x53, 0x55, 0x48, 0x4a, 0x4b }, { 0x25, 0x27, 0x26, 0x28, 0x65, 0x61, 0x62, 0x63 }, { 0x43, 0x42, 0x46, 0x56, 0x51, 0x47, 0x45, 0x5a }, { 0x49, 0x4c, 0x4d, 0x4e, 0x4f, 0x50, 0x52, 0x54 } };

	// SCHALTFL�CHEN ERSTELLEN
	private final Rectangle difficultyBounds = new Rectangle(rektx, rekty, rektwidth, rektheigth);
	private final Rectangle PlayerModeBounds = new Rectangle(rektx, 2 * rekty, rektwidth, rektheigth);
	private final Rectangle SaveSettingsBounds = new Rectangle(rektx, 3 * rekty, rektwidth, rektheigth);
	private final Rectangle recoverBounds = new Rectangle(rektx, 4 * rekty, rektwidth, rektheigth);
	private final Rectangle returnToMenuBounds = new Rectangle(Main.WIDTH - rektwidth, 10, rektwidth, rektheigth);

	// PLAYERS
	private final Rectangle Player1Bounds = new Rectangle(differ2, differ1, 100, rektheigth);
	private final Rectangle Player2Bounds = new Rectangle(differ2 + 1 * differ3, differ1, 100, rektheigth);
	private final Rectangle Player3Bounds = new Rectangle(differ2 + 2 * differ3, differ1, 100, rektheigth);
	private final Rectangle Player4Bounds = new Rectangle(differ2 + 3 * differ3, differ1, 100, rektheigth);

	// KEYBINDINGS
	private final Rectangle jumpBounds = new Rectangle(differ2, differ4 + 0 * differ1, rektwidth, rektheigth);
	private final Rectangle punchBounds = new Rectangle(differ2, differ4 + 2 * differ1, rektwidth, rektheigth);
	private final Rectangle kickBounds = new Rectangle(differ2, differ4 + 4 * differ1, rektwidth, rektheigth);
	private final Rectangle blockBounds = new Rectangle(differ2, differ4 + 6 * differ1, rektwidth, rektheigth);
	private final Rectangle duckBounds = new Rectangle(differ2 + differ5, differ4 + 0 * differ1, rektwidth, rektheigth);
	private final Rectangle fallBounds = new Rectangle(differ2 + differ5, differ4 + 2 * differ1, rektwidth, rektheigth);
	private final Rectangle MoveLeftBounds = new Rectangle(differ2 + differ5, differ4 + 4 * differ1, rektwidth, rektheigth);
	private final Rectangle MoveRightBounds = new Rectangle(differ2 + differ5, differ4 + 6 * differ1, rektwidth, rektheigth);

	public Settings(String soundFile, String imageFile) {
		super(soundFile, imageFile, "Other/BLOODY.ttf");

		keyBindFrame = new JPanel();
		keyBindFrame.setBackground(Color.green);
		keyBindFrame.setBounds((Main.WIDTH - 300) / 2, ((Main.HEIGHT - 300) / 2), 300, 200);
		JLabel textField = new JLabel("Zum Zuweisen neue Taste dr�cken");
		keyBindFrame.add(textField);
		textField.setLocation(100, 100);
		textField.setSize(100, 20);
		keyBindFrame.setVisible(false);
		this.add(keyBindFrame);
		// SCHALTFL�CHEN+MAUS
		MouseAdapter mouseHandler = new MouseAdapter() {
			@Override
			public final void mouseMoved(MouseEvent e) {
				if (getButtonBounds(returnToMenuBounds).contains(e.getPoint())) {
					selectedBounds = getButtonBounds(returnToMenuBounds);
				}/*
				 * else if
				 * (getButtonBounds(SaveSettingsBounds).contains(e.getPoint()))
				 * { selectedBounds = getButtonBounds(SaveSettingsBounds); }
				 * else if
				 * (getButtonBounds(recoverBounds).contains(e.getPoint())) {
				 * selectedBounds = getButtonBounds(recoverBounds); } else if
				 * (getButtonBounds(difficultyBounds).contains(e.getPoint())) {
				 * selectedBounds = getButtonBounds(difficultyBounds); } else if
				 * (getButtonBounds(PlayerModeBounds).contains(e.getPoint())) {
				 * selectedBounds = getButtonBounds(PlayerModeBounds);
				 * 
				 * P1 } else if
				 * (getButtonBounds(P1jumpBounds).contains(e.getPoint())) {
				 * selectedBounds = getButtonBounds(P1jumpBounds); } else if
				 * (getButtonBounds(P1punchBounds).contains(e.getPoint())) {
				 * selectedBounds = getButtonBounds(P1punchBounds); } else if
				 * (getButtonBounds(P1kickBounds).contains(e.getPoint())) {
				 * selectedBounds = getButtonBounds(P1kickBounds); } else if
				 * (getButtonBounds(P1blockBounds).contains(e.getPoint())) {
				 * selectedBounds = getButtonBounds(P1blockBounds); } else if
				 * (getButtonBounds(P1duckBounds).contains(e.getPoint())) {
				 * selectedBounds = getButtonBounds(P1duckBounds); } else if
				 * (getButtonBounds(P1fallBounds).contains(e.getPoint())) {
				 * selectedBounds = getButtonBounds(P1fallBounds); } else if
				 * (getButtonBounds(P1MoveLeftBounds).contains(e.getPoint())) {
				 * selectedBounds = getButtonBounds(P1MoveLeftBounds); } else if
				 * (getButtonBounds(P1MoveRightBounds).contains(e.getPoint())) {
				 * selectedBounds = getButtonBounds(P1MoveRightBounds);
				 * 
				 * P2 } else if
				 * (getButtonBounds(P2jumpBounds).contains(e.getPoint())) {
				 * selectedBounds = getButtonBounds(P2jumpBounds); } else if
				 * (getButtonBounds(P2punchBounds).contains(e.getPoint())) {
				 * selectedBounds = getButtonBounds(P2punchBounds); } else if
				 * (getButtonBounds(P2kickBounds).contains(e.getPoint())) {
				 * selectedBounds = getButtonBounds(P2kickBounds); } else if
				 * (getButtonBounds(P2blockBounds).contains(e.getPoint())) {
				 * selectedBounds = getButtonBounds(P2blockBounds); } else if
				 * (getButtonBounds(P2duckBounds).contains(e.getPoint())) {
				 * selectedBounds = getButtonBounds(P2duckBounds); } else if
				 * (getButtonBounds(P2fallBounds).contains(e.getPoint())) {
				 * selectedBounds = getButtonBounds(P2fallBounds); } else if
				 * (getButtonBounds(P2MoveLeftBounds).contains(e.getPoint())) {
				 * selectedBounds = getButtonBounds(P2MoveLeftBounds); } else if
				 * (getButtonBounds(P2MoveRightBounds).contains(e.getPoint())) {
				 * selectedBounds = getButtonBounds(P2MoveRightBounds);
				 * 
				 * }
				 */else {
					selectedBounds = null;
				}
				repaint();
			}

			// SCHALTFL�CHEN-FUNKTIONEN
			@Override
			public final void mouseClicked(MouseEvent e) {
				if (getButtonBounds(returnToMenuBounds).contains(e.getPoint())) {
					Main.MAIN.removeAllListeners();
					Main.MAIN.changeScene(CreateScene.createMenu());
				} else if (getButtonBounds(SaveSettingsBounds).contains(e.getPoint())) {
					saveConfig();
				} else if (getButtonBounds(recoverBounds).contains(e.getPoint())) {
					loadConfig();

					// PLAYER-REITER
				} else if (getButtonBounds(Player1Bounds).contains(e.getPoint())) {
					changePlayer = 0;
				} else if (getButtonBounds(Player2Bounds).contains(e.getPoint())) {
					changePlayer = 1;
				} else if (getButtonBounds(Player3Bounds).contains(e.getPoint())) {
					changePlayer = 2;
				} else if (getButtonBounds(Player4Bounds).contains(e.getPoint())) {
					changePlayer = 3;

				} else if (getButtonBounds(difficultyBounds).contains(e.getPoint())) {
					if (difbyte < 2) {
						difbyte++;
					} else
						difbyte = 0;

				} else if (getButtonBounds(PlayerModeBounds).contains(e.getPoint())) {
					if (playerModebit < 3) {
						playerModebit++;
					} else
						playerModebit = 0;

					// CHANGE KEYS
				} else if (getButtonBounds(jumpBounds).contains(e.getPoint())) {
					setKey(changePlayer, 2);
				} else if (getButtonBounds(punchBounds).contains(e.getPoint())) {
					setKey(changePlayer, 4);
				} else if (getButtonBounds(kickBounds).contains(e.getPoint())) {
					setKey(changePlayer, 7);
				} else if (getButtonBounds(blockBounds).contains(e.getPoint())) {
					setKey(changePlayer, 5);
				} else if (getButtonBounds(duckBounds).contains(e.getPoint())) {
					setKey(changePlayer, 6);
				} else if (getButtonBounds(fallBounds).contains(e.getPoint())) {
					setKey(changePlayer, 3);
				} else if (getButtonBounds(MoveLeftBounds).contains(e.getPoint())) {
					setKey(changePlayer, 0);
				} else if (getButtonBounds(MoveRightBounds).contains(e.getPoint())) {
					setKey(changePlayer, 1);
				}
			}

		};

		addMouseListener(mouseHandler);
		addMouseMotionListener(mouseHandler);

		KeyListener klSettings = new KeyAdapter() {
			@Override
			public final void keyPressed(KeyEvent e) {
				if (waitForInput) {
					keyCode = e.getKeyCode();
					keys[toChangePlayer][toChangeKey] = keyCode;
					// System.out.println("KeyPressed: " + e.getKeyChar()
					// + " / KeyCode: " + keyCode);
					waitForInput = false;
				}
			}

			@Override
			public final void keyReleased(KeyEvent e) {
				keyBindFrame.setVisible(false);
			}

		};
		Main.MAIN.addKeyListener(klSettings);

	}

	public final void setKey(int pN, int kN) {
		keyBindFrame.setVisible(true);

		toChangePlayer = pN;
		toChangeKey = kN;
		waitForInput = true;
		// System.out.println("KeyToChange: " + keys[pN][kN]);
	}

	// SCHALTFL�CHEN ZEICHNEN
	public final void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g.create();
		// SettingButtons
		drawTextButton(g2d, "return to menu", getButtonBounds(returnToMenuBounds));
		drawTextButton(g2d, "Einstellungen speichern", getButtonBounds(SaveSettingsBounds));
		drawTextButton(g2d, "Standard wiederherstellen", getButtonBounds(recoverBounds));
		drawTextButton(g2d, "Difficulty: " + difficulty[difbyte], getButtonBounds(difficultyBounds));
		drawTextButton(g2d, "Game-Mode: " + playerMode[playerModebit], getButtonBounds(PlayerModeBounds));

		drawTextButton(g2d, "Player 1", getButtonBounds(Player1Bounds));
		drawTextButton(g2d, "Player 2", getButtonBounds(Player2Bounds));
		drawTextButton(g2d, "Player 3", getButtonBounds(Player3Bounds));
		drawTextButton(g2d, "Player 4", getButtonBounds(Player4Bounds));
		switch (changePlayer) {
		case 0:
			g2d.draw(new Rectangle(differ2 - 10, differ6, differ7, rektheigth + 20));
			break;
		case 1:
			g2d.draw(new Rectangle((int) (Main.WIDTH * 0.139), differ6, differ7, rektheigth + 20));
			break;
		case 2:
			g2d.draw(new Rectangle((int) (Main.WIDTH * 0.263), differ6, differ7, rektheigth + 20));
			break;
		case 3:
			g2d.draw(new Rectangle((int) (Main.WIDTH * 0.388), differ6, differ7, rektheigth + 20));
			break;
		}

		// KeyButtons
		drawTextButton(g2d, "Player Left: " + KeyEvent.getKeyText(keys[changePlayer][0]), getButtonBounds(MoveLeftBounds));
		drawTextButton(g2d, "Player Right: " + KeyEvent.getKeyText(keys[changePlayer][1]), getButtonBounds(MoveRightBounds));
		drawTextButton(g2d, "Player Jump: " + KeyEvent.getKeyText(keys[changePlayer][2]), getButtonBounds(jumpBounds));
		drawTextButton(g2d, "Player Fall: " + KeyEvent.getKeyText(keys[changePlayer][3]), getButtonBounds(fallBounds));
		drawTextButton(g2d, "Player Punch: " + KeyEvent.getKeyText(keys[changePlayer][4]), getButtonBounds(punchBounds));
		drawTextButton(g2d, "Player Block: " + KeyEvent.getKeyText(keys[changePlayer][5]), getButtonBounds(blockBounds));
		drawTextButton(g2d, "Player Duck: " + KeyEvent.getKeyText(keys[changePlayer][6]), getButtonBounds(duckBounds));
		drawTextButton(g2d, "Player Kick: " + KeyEvent.getKeyText(keys[changePlayer][7]), getButtonBounds(kickBounds));
		g2d.draw(new Rectangle(differ2 - 10, differ4 - 20, (int) (Main.WIDTH * 0.461), (int) (Main.HEIGHT * 0.573)));

		g2d.dispose();
	}

	// STDCONFIGDATEI EINLESEN
	public final static void loadConfig() {
		try {
			Scanner scan = new Scanner(new File("Data/stdconfig.txt"));
			String Line = "";
			Scanner help = null;
			int i = 0;
			for (int n = 0; n < 4; n++) {
				Line = scan.nextLine();
				help = new Scanner(Line);
				while (help.hasNextInt(16)) {
					keys[n][i] = help.nextInt(16);
					i++;
				}
				i = 0;
			}
			playerModebit = scan.nextByte();
			difbyte = scan.nextByte();
			scan.close();
			help.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Datei nicht gefunden!");
		}
	}

	// EINSTELLUNGEN SPEICHERN
	public final static void saveConfig() {
		try {
			PrintStream output = new PrintStream(new File("Data/config.txt"));
			for (int n = 0; n < 4; n++) {
				for (int i = 0; i < 8; i++) {
					output.print(Integer.toHexString(keys[n][i]) + " ");
				}
				output.println();
			}
			output.println(playerModebit);
			output.print(difbyte);
			output.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Datei wurde nicht gefunden!");
		}
	}

}
