package GameObjects;

import java.awt.Rectangle;

import Scenes.Level;

public abstract class PhysicsObject extends GameObject {
	protected boolean movedLeft = false;
	protected double recoil = 10;
	protected boolean recoiling = false;
		
	public PhysicsObject(Rectangle hitbox, Level level) {
		super(hitbox, level);
	}
	public final double getRecoil() {
		return recoil;
	}
	public final void setRecoil(int rec) {
		this.recoil = rec;
	}

	public final void multiRecoil(double factor) {
		double tmp = Math.round(factor * recoil);
		recoil = (int) tmp;
		this.recoil *= factor;
	}

}
