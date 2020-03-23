package GameObjects;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Scenes.Level;

public final class BouncyBoots extends Item {

	private int jumpBonus = 2;
	public static final ArrayList<BufferedImage> images = new ArrayList<>();

	public BouncyBoots(Point pCoord, Level lvl, int activeTime) {

		super(new Rectangle(pCoord, hitBoxSize), images, activeTime, lvl);

	}

	public final void setJumpBonus(int bonus) {
		this.jumpBonus = bonus;
	}

	public final int getJumpBonus() {
		return this.jumpBonus;
	}

	@Override
	public final void giveStats(Charakter c) {
		myCharakter = c;
		c.multiJumpHeight(jumpBonus);
	}

	@Override
	public final void removeStats() {
		myCharakter.removeItem(this);
		myCharakter.multiJumpHeight((double) (jumpBonus * 0.25));
	}

}
