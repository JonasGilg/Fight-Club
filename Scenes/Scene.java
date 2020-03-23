package Scenes;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.RadialGradientPaint;
import java.awt.Rectangle;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.swing.JPanel;

import main.Main;
import FileManagement.ImageManagement;
import FileManagement.SoundManagement;

@SuppressWarnings("serial")
public class Scene extends JPanel {
	public final SoundManagement backgroundSound;
	protected BufferedImage backroundImage; // Hintergrundbild der Scene
	protected Rectangle selectedBounds; // der Button, auf dem die Maus gerade
										// ist
	protected String fontFile; // Link zur Datei einer Schriftart

	public Scene(String soundFile, String imageFile, String fontFile) {
		backgroundSound = new SoundManagement(soundFile);
		backgroundSound.loop();
		backroundImage = ImageManagement.importImage(imageFile);
		try {
			backroundImage = ImageManagement.rescaleImage(backroundImage, new Dimension(Main.WIDTH, Main.HEIGHT));
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.fontFile = fontFile;

		GraphicsEnvironment ge = GraphicsEnvironment
				.getLocalGraphicsEnvironment(); // sorgt daf�r, dass die
												// Schriftart benutzt werden
												// kann
		try {
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File(
					fontFile))); // "" "" "" ""
		} catch (FontFormatException | IOException e) {
			//e.printStackTrace();
		}
	}

	public void paintComponent(Graphics g) { // zeichnet den Hintergrund
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.drawImage(backroundImage, 0, 0, null);
		g2d.dispose();
	}

	protected final void drawTextButton(Graphics2D g2d, String text, Rectangle bounds) {
		try {
			File bloodyFile = new File(fontFile);
			
			URL fontURL = bloodyFile.toURI().toURL();
			
			Font bloodyFont = Font.createFont(Font.TRUETYPE_FONT, fontURL.openStream());
			
			g2d.setFont(new Font("Bloody", bloodyFont.getStyle(), (int) (Main.rescaleConstant * 25))); 
		} catch (FontFormatException | IOException e) {
			//e.printStackTrace();
		}
		FontMetrics fm = g2d.getFontMetrics();

		g2d.setColor(new Color(170, 0, 0));
		
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
		if (selectedBounds != null && bounds.contains(selectedBounds)) { 
			
			RadialGradientPaint rpg = new RadialGradientPaint(new Point(
					
					bounds.x + (bounds.width / 2), bounds.y
							+ (bounds.height / 2)), Math.min(bounds.width,
					bounds.height), new float[] { 0f, 0.5f }, new Color[] {
					new Color(200, 200, 200), new Color(0, 0, 0) });
			g2d.setPaint(rpg);
			RoundRectangle2D fill = new RoundRectangle2D.Float(bounds.x,
					bounds.y, bounds.width, bounds.height, 22, 22);
			g2d.fill(fill);
			g2d.setColor(Color.RED);
		}
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		g2d.drawString(
				text,
				bounds.x + ((bounds.width - fm.stringWidth(text)) / 2),
				bounds.y + ((bounds.height - fm.getHeight()) / 2)
						+ fm.getAscent());
	}

	protected final void drawText(Graphics2D g2d, String text, Rectangle bounds) {
		FontMetrics fm = g2d.getFontMetrics();
		g2d.setColor(new Color(170, 0, 0));
		g2d.drawString(
				text,
				bounds.x + ((bounds.width - fm.stringWidth(text)) / 2),
				bounds.y + ((bounds.height - fm.getHeight()) / 2)
						+ fm.getAscent());
	}

	protected final Rectangle getButtonBounds(Rectangle masterBounds) { // gibt die
																	// Grenzen
																	// eines
																	// Button
																	// zur�ck
		Rectangle bounds = new Rectangle(masterBounds);
		Point p = getImageOffset();
		bounds.translate(p.x, p.y);
		return bounds;
	}

	protected final Point getImageOffset() { // gibt den Punkt zur�ck, an dem ein
										// Bild gezeichnet werden soll, damit es
										// in der Mitte ist
		Point p = new Point();
		if (backroundImage != null) {
			p.x = (getWidth() - backroundImage.getWidth()) / 2;
			p.y = (getHeight() - backroundImage.getHeight()) / 2;
		}

		return p;
	}
}
