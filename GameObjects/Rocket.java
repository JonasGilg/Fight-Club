package GameObjects;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import main.Main;
import FileManagement.ImageManagement;
import Scenes.Level;

public final class Rocket extends Projectile {

	private static Dimension caliber = new Dimension(30, 10);
	private static int dmg = 30;
	private static int rocketSpeed = 3;
	public static final ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();

	public Rocket(Point coord, boolean movesLeft, Level lvl) {
		super(rocketSpeed, dmg, movesLeft, new Rectangle(coord, caliber), lvl);

		level = lvl;
	}

	@Override
	public final void run() {
		super.images = Rocket.images;
		super.run();
	}

	@Override
	public final void draw(Graphics2D g2d) {
		if (currentImage != null && !currentImage.equals(images.get(2))) {
			g2d.drawImage(ImageManagement.rescaleImage(currentImage,
					new Dimension((int) (hitbox.width * Main.rescaleConstant),
							(int) (hitbox.height * Main.rescaleConstant))),
					(int) (hitbox.x * Main.rescaleConstant),
					(int) (hitbox.y * Main.rescaleConstant), null);
		} else if (!outOfBounds && currentImage != null) {
			g2d.drawImage(
					ImageManagement
							.rescaleImage(
									currentImage,
									new Dimension(
											(int) (currentImage.getWidth() * Main.rescaleConstant),
											(int) (currentImage.getHeight() * Main.rescaleConstant))),
					(int) ((hitbox.x - 35) * Main.rescaleConstant),
					(int) ((hitbox.y - 45) * Main.rescaleConstant), null);

		}
	}

}
