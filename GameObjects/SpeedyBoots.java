package GameObjects;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Scenes.Level;

public final class SpeedyBoots extends Item {

	private int msBonus = 1;
	public static final ArrayList<BufferedImage> images = new ArrayList<>();

	public SpeedyBoots(Point pCoord, Level lvl, int activeTime) {
		super(new Rectangle(pCoord, hitBoxSize), images, activeTime, lvl);

	}

	public final void setMsBonus(int bonusMs) {
		this.msBonus = bonusMs;
	}

	public final int getMsBonus() {
		return msBonus;
	}

	@Override
	public final void giveStats(Charakter c) {
		myCharakter = c;
		c.addMovespeed(msBonus);
	}

	@Override
	public final void removeStats() {
		try {
			myCharakter.removeItem(this);
			myCharakter.addMovespeed(-msBonus);
		} catch (Exception e) {
			if(level != null) {
				e.printStackTrace();
			}
		}
	}

}
