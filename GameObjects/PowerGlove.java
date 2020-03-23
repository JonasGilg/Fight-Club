package GameObjects;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Scenes.Level;

public final class PowerGlove extends Item {
	private static int recoilBonus = 2;

	public static final ArrayList<BufferedImage> images = new ArrayList<>();

	public PowerGlove(Point pCoord, Level lvl, int activeTime) {
		super(new Rectangle(pCoord, hitBoxSize), images, activeTime, lvl);

	}

	@Override
	public final void giveStats(Charakter c) {
		myCharakter = c;
		c.multiRecoil(recoilBonus);
	}

	@Override
	public final void removeStats() {
		try {
			myCharakter.removeItem(this);
			myCharakter.multiRecoil((double) (recoilBonus * 0.25));
		} catch (Exception e) {
			if(level != null) {
				if(level != null) {
					e.printStackTrace();
				}
			}
		}
	}
}
