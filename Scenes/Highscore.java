package Scenes;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import main.CreateScene;
import main.Main;
import Data.Scoredata;

@SuppressWarnings("serial")
public final class Highscore extends Scene {

	private final Rectangle returnToMenuBounds = new Rectangle(Main.WIDTH - 220, 10, 220, 35);

	public Highscore(String soundFile, String imageFile) {
		super(soundFile, imageFile, "Other/BLOODY.ttf");

		MouseAdapter mouseHandler = new MouseAdapter() {
			@Override
			public final void mouseMoved(MouseEvent e) {
				if (getButtonBounds(returnToMenuBounds).contains(e.getPoint())) {
					selectedBounds = getButtonBounds(returnToMenuBounds);
				} else {
					selectedBounds = null;
				}
			}

			@Override
			public final void mouseClicked(MouseEvent e) {
				if (getButtonBounds(returnToMenuBounds).contains(e.getPoint())) {
					Main.MAIN.changeScene(CreateScene.createMenu());
				}
			}
		};
		addMouseListener(mouseHandler);
		addMouseMotionListener(mouseHandler);
	}

	public final void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g.create();
		try {
			File bloodyFile = new File(fontFile);

			URL fontURL = bloodyFile.toURI().toURL();

			Font bloodyFont = Font.createFont(Font.TRUETYPE_FONT, fontURL.openStream());

			g2d.setFont(new Font("Bloody", bloodyFont.getStyle(), (int) (Main.rescaleConstant * 25)));
		} catch (FontFormatException | IOException e) {
			// e.printStackTrace();
		}

		g2d.setColor(Color.RED);

		for (int i = 0; i < 5; i++) {
			g2d.drawString(i + 1 + ".", 400, (Main.HEIGHT / 7) * (i + 2));
			String s = (new Integer(Scoredata.scores[i]).toString());
			g2d.drawString(s, 600, (Main.HEIGHT / 7) * (i + 2));
			g2d.drawString(Scoredata.names[i], 850, (Main.HEIGHT / 7) * (i + 2));
		}

		drawTextButton(g2d, "return to menu", getButtonBounds(returnToMenuBounds));

		g2d.dispose();
	}

}
