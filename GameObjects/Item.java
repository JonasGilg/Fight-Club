package GameObjects;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Scenes.Level;
import Scenes.Settings;

public abstract class Item extends GameObject {
	protected int sleepThread = 5;
	protected static final Dimension hitBoxSize = new Dimension(50, 50);
	protected boolean nichtAufgehoben = true;
	protected int activeTime;
	protected boolean activated = false;
	protected Charakter myCharakter;
	private int maxActiveTime = 0;

	public Item(Rectangle rectangle, ArrayList<BufferedImage> pImg,
			int activeTime, Level level) {
		super(rectangle, level);
		super.setGraphic(pImg);
		this.activeTime = activeTime;
		this.maxActiveTime = activeTime;
	}

	public void giveStats(Charakter c) {

	}

	public final Dimension getHitBoxSize() {
		return hitBoxSize;
	}

	public final void setAufgehoben() {
		nichtAufgehoben = false;

	}

	public void removeStats() {

	}

	public final int getMaxActiveTime() {
		return maxActiveTime;
	}

	public final int getActiveTime() {
		return activeTime;
	}

	public String getActiveTimeAsString() { // Output as String for HUDItem
		if (activeTime > 0)
			return "" + activeTime;
		return "";
	}

	@Override
	public void run() {
		int time = 1;
		while (nichtAufgehoben && (level == null || level.levelAlive)) {
			freeze(sleepThread);
			update();
		}
		while (time <= maxActiveTime && (level == null || level.levelAlive)) {
			freeze(1000);
			time++;
			activeTime--;

		}
		if (level.levelAlive) {
			removeStats();
		}
		level.items.remove(this);
		setName("ItemThread");

	}

	public final void update() {
		boolean ground = false;
		for (Area a : level.areas) {
			if (hitbox.intersects(a.hitbox)) {
				ground = true;
				break;
			}
		}
		if (!ground) {
			moveObjectDown(Settings.gravityNumber);
		}
		findLastWaypoint();

	}

}
