package GameObjects;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Scenes.Level;

public final class Healthpack extends Item {

	private int hp = 100;

	public static final ArrayList<BufferedImage> images = new ArrayList<>();

	public Healthpack(Point pCoord, Level level) {
		super(new Rectangle(pCoord, hitBoxSize), images, 0, level);

	}

	public Healthpack(Point pCoord, Level lvl, int hp) {
		super(new Rectangle(pCoord, hitBoxSize), images, 0, lvl);
		setHP(hp);
	}

	public final void setHP(int hp) {
		this.hp = hp;
	}

	public final int getHP() {
		return this.hp;
	}

	@Override
	public final void giveStats(Charakter c) {
		myCharakter = c;
		c.heal(hp);
	}

	@Override
	public final void removeStats() {
		try {
			myCharakter.removeItem(this);
		} catch (Exception e) {
			if(level != null) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public final String getActiveTimeAsString() { // Output as String for HUDItem

		return "";
	}

}
