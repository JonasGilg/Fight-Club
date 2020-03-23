package GameObjects;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import main.Main;
import FileManagement.ImageManagement;
import FileManagement.SoundManagement;
import Scenes.Level;
import Scenes.Settings;

public abstract class Charakter extends PhysicsObject {
	protected final ArrayList<BufferedImage> g = new ArrayList<BufferedImage>();
	protected ArrayList<BufferedImage> running;
	protected static final SoundManagement sounds[] = { new SoundManagement("/Sounds/Actions/run/01.wav"), new SoundManagement("/Sounds/Actions/run/02.wav"), new SoundManagement("/Sounds/Actions/punch/punch1_hit.wav"), new SoundManagement("/Sounds/Actions/punch/punch2_miss.wav"), new SoundManagement("/Sounds/Actions/punch/punch1_hit.wav"), new SoundManagement("/Sounds/Actions/punch/punch2_miss.wav") };

	protected int lives = 3;
	protected boolean alive = true;
	protected int utSleep = 2;
	protected final int msDivider = 1;
	protected final int sleepThread = 2;
	protected int sleepKick = 100;
	protected int sleepPunch = 80;
	protected int sleepBlock = 100;
	protected int kickDamage = 20;
	protected int kickRange = 40;
	protected int punchDamage = 30;
	protected int punchRange = 30;

	protected final int bounceDistance = 1;
	protected int hp = 100;
	protected int maxHP;
	public String name;
	protected int moveSpeed = 1;
	protected int jumpHeight = 150;

	protected int feetModifier = 0;
	protected Area kollisionsArea;
	protected int jumpCounter = 0;
	protected int jumpSpeed = 1;
	protected boolean jumping = false;
	protected boolean jumped = false;
	protected boolean doubleJump = false;
	protected boolean doubleJumped = false;
	protected boolean ducked = false;
	protected boolean noGravity = false;

	protected Rectangle savedHitbox;
	protected Rectangle mainHitbox;
	protected Rectangle bodyHitbox;
	protected Rectangle feetHitbox;
	protected Rectangle headHitbox;
	protected Rectangle topHitbox;

	protected Rectangle armHitbox;
	protected Rectangle legHitbox;
	protected Rectangle leftHitbox = new Rectangle(-10, -10, 1, 1);
	protected Rectangle rightHitbox = new Rectangle(-10, -10, 1, 1);
	protected Rectangle drawBox = new Rectangle();
	protected int playerNumber = 0;
	protected Thread ut;
	protected Thread tt;

	protected Rectangle arm = null;
	protected Rectangle bein = null;

	protected BufferedImage currentImage;
	protected int leftRun = 0;
	protected int rightRun = 9;
	// protected int duckedLeftRun= indexOf(duckedLeftRun.0);
	// --------------------------------------------------------------------------<<<<<<<<<<<<<<<<
	// protected int duckedRightRun= indexOf(duckedRightRun.0);
	// --------------------------------------------------------------------------<<<<<<<<<<<<<<<<
	protected Color playerColor;
	protected Item holdedItem;
	protected boolean canShoot = false;
	protected int shootDmg = 0;

	protected int enemyDmg;
	protected PhysicsObject enemy;
	int time = 0;

	public Charakter(Rectangle hitbox, String name, int hp, Level lvl) {
		super(hitbox, lvl);
		mainHitbox = new Rectangle(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
		topHitbox = new Rectangle(hitbox.x, hitbox.y, hitbox.width, (hitbox.height / 2) - 1);
		feetHitbox = new Rectangle(mainHitbox.x + feetModifier, mainHitbox.y + (mainHitbox.height - 1), mainHitbox.width - feetModifier, 1);
		bodyHitbox = new Rectangle(mainHitbox.x, mainHitbox.y + 1, mainHitbox.width, mainHitbox.height - 2);
		headHitbox = new Rectangle(mainHitbox.x + feetModifier, mainHitbox.y, mainHitbox.width - feetModifier, 1);
		leftHitbox = new Rectangle(mainHitbox.x, mainHitbox.y, (mainHitbox.width / 2), mainHitbox.height);
		rightHitbox = new Rectangle(mainHitbox.x + (mainHitbox.width / 2), mainHitbox.y, mainHitbox.width / 2, mainHitbox.height);
		armHitbox = new Rectangle(-1, -1, 1, 1);
		legHitbox = new Rectangle(-2, -1, 1, 1);
		setFoot();
		setHand();
		this.hp = hp;
		this.maxHP = hp;
		this.name = name;
		this.holdedItem = new LeerItem(level);
		class TimerThread extends Thread {

			@Override
			public void run() {
				while (alive && level.levelAlive) {
					try {

						sleep(100);
					} catch (Exception e) {
						e.printStackTrace();
					}
					time++;
					if (time > 65530)
						time = 0;
				}

			}
		}
		;
		tt = new TimerThread();
		tt.setName("Timer Thread");

		class AudioThread extends Thread {
			@Override
			public void run() {
				while (alive && level.levelAlive) {
					if ((left || right) && !jumped && noGravity && !kick && !punch && !ducked) {
						sounds[0].play();
						sounds[0].play();
					}
					try {
						sleep(200);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		;
		AudioThread at = new AudioThread();
		at.setName("Audio Thread");
		at.start();
		class UpdateThread extends Thread {
			Charakter thisC;

			public UpdateThread(Charakter ch) {
				thisC = ch;
			}

			public void freeze(int ms) {
				long startT = System.currentTimeMillis();
				while (System.currentTimeMillis() < startT + ms) {

				}

			}

			@Override
			public void run() {
				while (alive && level.levelAlive) {
					synchronized (this) {
						if (ducked) {

							for (Obstacle o : level.obstacles) {
								if (topToObstacleCollision(o)) {
									standUpPossible = false;
									break;
								} else {
									standUpPossible = true;

								}
							}
						}
						for (Obstacle o : level.obstacles) {
							if (headToObstacleCollision(o)) {
								jump = false;
								jumpCounter = jumpHeight;
								jumped = false;
								doubleJumped = false;

							}
							if (bodyToObstacleCollision(o)) {
								stopAllSideMovement();
								bounceOffWall();
							}

						}

						if (!recoiling) {

							if (left && !kick && !punch) {
								if (ducked)
									movedLeft = true;
								else
									moveLeft();

							}

							else if (right && !kick && !punch) {
								if (ducked)
									movedLeft = false;
								else
									moveRight();

							}

							else if (kick) {

								for (Charakter c : level.charakters) {
									if (kickCollision(c) && !thisC.equals(c) && !c.block) {
										c.takeDmg(kickDamage, thisC);

										hit = true;
										c.setRecoiling(true);
										break;
									}
								}
								if (hit) {
									sounds[4].play();
								} else {
									sounds[5].play();
								}
								hit = false;
								freeze(sleepKick);
								kick = false;
								// kickFree=true;
							}

							else if (punch) {

								// freeze(sleepPunch / 2);
								if (canShoot) {
									freeze(sleepPunch);

									shoot();
									freeze(sleepPunch);
								} else {
									for (Charakter c : level.charakters) {
										if (punchCollision(c) && !thisC.equals(c) && !c.block) {
											c.takeDmg(punchDamage, thisC);

											hit = true;
											c.setRecoiling(true);
											break;

										}
									}
									if (hit) {
										sounds[2].play();
									} else {
										sounds[3].play();
									}
									hit = false;
								}
								freeze(sleepPunch);
								punch = false;

								// punchFree=true;
							}

							else if (block) {

								pickUpItem();
								if (!left && !right) {
									for (int i = 0; i < sleepBlock * 2; i++) {
										for (Charakter c : level.charakters) {
											if (!thisC.equals(c) && blockCollision(c)) {
												c.getBlocked(thisC);
											}
										}

										freeze(1);
									}
								}
								freeze(50);
								block = false;

							}

							if (duck) {

								duck();

							}
							if (standUpPossible && standUp) {
								standUp();
								standUp = false;
								duck = false;
							}
							if (jump) {
								jump();

							}

							if (fall) {

								fall();
								fall = false;
							}

							if (jumpCounter >= jumpHeight) {
								jumping = false;
								jump = false;
							}
						} else {
							if (!block || (block && (thisC.movedLeft == enemy.movedLeft))) {
								int maxRecoil = (int) enemy.getRecoil() * 4;

								if (enemy.movedLeft) {
									for (Obstacle obst : level.obstacles) {
										if ((((obst.hitbox.y + obst.hitbox.height) > thisC.getHeadHitbox().y) && (obst.hitbox.y < (thisC.getFeetHitbox().y + 1)))) {
											if ((obst.hitbox.x + obst.hitbox.width < thisC.getBodyHitbox().x) && ((thisC.getBodyHitbox().x - maxRecoil) < (obst.hitbox.x + obst.hitbox.width + 1))) {												maxRecoil = thisC.getBodyHitbox().x - (obst.hitbox.x + obst.hitbox.width);
											}
										}
									}
								} else {
									for (Obstacle obst : level.obstacles) {
										if ((((obst.hitbox.y + obst.hitbox.height) > thisC.getHeadHitbox().y) && (obst.hitbox.y < (thisC.getFeetHitbox().y + 1)))) {
											if ((obst.hitbox.x > thisC.getBodyHitbox().x + thisC.getBodyHitbox().width) && ((thisC.getBodyHitbox().x + thisC.getBodyHitbox().width + maxRecoil) > (obst.hitbox.x - 1))) {
												maxRecoil = obst.hitbox.x - (thisC.getBodyHitbox().x + thisC.getBodyHitbox().width);
											}
										}

									}
								}

								for (int i = 1; i < maxRecoil; i++) {

									// if (headToObstacleCollision(o)) {
									// jump = false;
									// jumpCounter = jumpHeight;
									// jumped = false;
									// doubleJumped = false;
									//
									// }

									if (enemy.movedLeft) {
										moveObjectLeft(1);
									} else {

										moveObjectRight(1);
									}
									setMainHitbox();
									freeze(2);
								}

							}
							recoiling = false;
						}
						gravitation(level.areas);

						try {
							sleep(utSleep);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

				}
			}
		}
		;

		ut = new UpdateThread(this);
		ut.setName("Update Thread");

	}

	// HITBOXEN
	// Setter
	public final void setMainHitbox() {
		if (ducked)
			mainHitbox.setBounds(hitbox.x, hitbox.y + 50, 50, 50);
		else
			mainHitbox.setBounds(hitbox.x, hitbox.y, 50, 100);

		setFeet();
	}

	public final void setTopHitbox() {
		topHitbox.setBounds(hitbox.x, hitbox.y, hitbox.width, (hitbox.height / 2) - 1);
	}

	public final void setRightHitbox() {
		rightHitbox.setBounds(mainHitbox.x + (mainHitbox.width / 2), mainHitbox.y, mainHitbox.width / 2, mainHitbox.height);
	}

	public final void setLeftHitbox() {
		leftHitbox.setBounds(mainHitbox.x, mainHitbox.y, (mainHitbox.width / 2), mainHitbox.height);
	}

	public final void setFeet() {
		feetHitbox.setLocation(mainHitbox.x + feetModifier, (mainHitbox.y + (mainHitbox.height - 1)));
		setBodyHitbox();
		setHeadHitbox();
		setLeftHitbox();
		setRightHitbox();
		setTopHitbox();
		setFoot();
		setHand();
	}

	public final void setHeadHitbox() {
		headHitbox.setLocation(mainHitbox.x + feetModifier, mainHitbox.y);
	}

	public final void setBodyHitbox() {
		bodyHitbox = new Rectangle(mainHitbox.x, mainHitbox.y + 1, mainHitbox.width, mainHitbox.height - 2);
	}

	public final void setHand() {
		if (movedLeft) {
			armHitbox = new Rectangle(mainHitbox.x - punchRange, (mainHitbox.y + (mainHitbox.height / 6)), punchRange, 10);
		} else {
			armHitbox = new Rectangle((mainHitbox.x + mainHitbox.width), (mainHitbox.y + (mainHitbox.height / 6)), punchRange, 10);
		}
	}

	public final void setFoot() {
		if (movedLeft) {
			legHitbox.setBounds(mainHitbox.x - kickRange, (mainHitbox.y + ((mainHitbox.height / 2) - 16)), kickRange, 15);
		} else {
			legHitbox.setBounds(mainHitbox.x + mainHitbox.width, (mainHitbox.y + ((mainHitbox.height / 2) - 16)), kickRange, 15);
		}
	}

	// Getter
	public final int getMaxHP() {
		return maxHP;
	}

	public final Rectangle getHeadHitbox() {
		return headHitbox;
	}

	public final Rectangle getMainHitbox() {
		return mainHitbox;
	}

	public final Rectangle getTopbox() {
		return topHitbox;
	}

	public final Rectangle getBodyHitbox() {
		return bodyHitbox;
	}

	public final Rectangle getFeetHitbox() {
		return feetHitbox;
	}

	// Game Attribut Methoden
	public int getLives() {
		return lives;
	}

	public final void setLeben(int lives) {
		this.lives = lives;
	}

	public final void loseLife() {
		lives--;
		if (lives == 0) {
			level.deleteCharakter(this);
		}
	}

	public final String getCharakterName() {
		return this.name;
	}

	public final void addMovespeed(int speed) {
		moveSpeed += speed;
	}

	public final void addJumpHeight(int height) {
		jumpHeight += height;
	}

	public final void multiJumpHeight(double factor) {

		double tmp = Math.round(factor * jumpHeight);

		jumpHeight = (int) tmp;

	}

	public final void takeDmg(int dmg, PhysicsObject source) {
		enemyDmg = dmg;
		enemy = source;
		hp -= dmg;
	}

	/*
	 * if (!block || (block && (this.movedLeft == source.movedLeft))) { this.hp
	 * -= dmg; recoiling = true; for (int i = 0; i < source.getRecoil(); i++) {
	 * if (source.movedLeft){ moveObjectLeft((int) source.getRecoil()); } else{
	 * 
	 * 
	 * 
	 * moveObjectRight((int) source.getRecoil()); } setMainHitbox();
	 * 
	 * } recoiling = false; }
	 * 
	 * }
	 */

	public final void heal(int leben) {
		if (hp + leben > maxHP) {
			hp = maxHP;
		} else {
			this.hp += leben;
		}
	}

	public final int getHP() {
		return hp;
	}

	public final int getPlayerNumber() {
		return playerNumber;
	}

	public final void setPlayerNumber(int playerNumber) {
		this.playerNumber = playerNumber;

	}

	public final Color getPlayerColor() {
		return playerColor;
	}

	public final void setPlayerColor(Color playerColor) {
		this.playerColor = playerColor;
	}

	public final void getBlocked(Charakter source) {
		recoiling = true;
		for (int i = 0; i < source.getRecoil() / 5; i++) {
			if (source.movedLeft)
				moveObjectLeft((int) source.getRecoil());
			if (!source.movedLeft)
				moveObjectRight((int) source.getRecoil());
			setMainHitbox();
			// freeze(20);
		}
		recoiling = false;
	}

	public final void pickUpItem() {
		if (holdedItem instanceof LeerItem) {
			for (Item it : level.items) {
				if (this.mainHitbox.intersects(it.hitbox)) {
					it.giveStats(this);
					holdedItem = it;
					it.setAufgehoben();
					it.moveObject(-100, 0);
					break;
				}
			}
		}
	}

	public final void removeItem(Item item) {
		holdedItem = new LeerItem(level);
	}

	public final Item getHoldedItem() {
		return holdedItem;
	}

	public final void setShoot(boolean status) {
		canShoot = status;
	}

	public final void setShootDmg(int dmg) {
		shootDmg = dmg;
	}

	// Movement Methoden
	public final void bounceOffWall() {
		if (movedLeft)
			moveObjectRight(bounceDistance);
		if (!movedLeft)
			moveObjectLeft(bounceDistance);
		setMainHitbox();
	}

	public final void moveLeft() {
		movedLeft = true;
		moveObjectLeft(moveSpeed);
		setMainHitbox();
	}

	public final void moveRight() {
		movedLeft = false;
		moveObjectRight(moveSpeed);
		setMainHitbox();
	}

	public final void moveUp() {
		moveObjectUp(moveSpeed);
		setMainHitbox();
	}

	public final void moveDown() {
		moveObjectDown(moveSpeed);
		setMainHitbox();
	}

	public final void fall() {
		if (enableFall) {
			enableFall = false;
			moveObjectDown(3);
			setMainHitbox();
			enableFall = false;
		}
	}

	public final void jump() {
		jumping = true;
		moveObjectUp(jumpSpeed);
		jumpCounter += jumpSpeed;
		setMainHitbox();
		if (jumpCounter > 0) {
			jumped = true;
		}

	}

	public final void resetJumpCounter() {
		// setJumped(true);
		jumpCounter = 0;
	}

	public final void duck() {
		ducked = true;
		setMainHitbox();
	}

	public final void standUp() {
		if (ducked) {

			ducked = false;
			setMainHitbox();
		}
	}

	public final void stopAllSideMovement() {
		left = false;
		right = false;
	}

	public final void gravity(int gravitynumber) {
		if (!jumping) {
			noGravity = false;
			moveObjectDown(gravitynumber);
			setMainHitbox();
		}

	}

	public final void gravitation(ArrayList<Area> areas) {
		boolean ground = false;
		for (Area a : areas) {
			if (feetToObstacleCollision(a)) {
				ground = true;
				setKollisionsArea(a);
				if (a instanceof Platform) {
					enableFall = true;
				} else {
					enableFall = false;
				}
				jumped = false;
				doubleJumped = false;
				break;
			}
		}
		noGravity = true;
		if (!ground || bodyToObstacleCollision(getKollisionsArea())) {
			enableFall = false;
			gravity(Settings.gravityNumber);
		}
	}

	// Action Methoden
	public final void shoot() {
		((Bazooka) holdedItem).shoot(movedLeft, this.level, this);

	}

	// Kollisions Methoden
	public final Area getKollisionsArea() {
		return kollisionsArea;
	}

	public final void setKollisionsArea(Area kollisionsArea) {
		this.kollisionsArea = kollisionsArea;
	}

	@Override
	public final boolean feetToObstacleCollision(GameObject obj) {
		return !((((feetHitbox.x + feetHitbox.width) < obj.getHitbox().x) || ((obj.getHitbox().x + obj.getHitbox().width) < feetHitbox.x)) || (((feetHitbox.y + feetHitbox.height) < obj.getHitbox().y || (obj.getHitbox().y + obj.getHitbox().height) < feetHitbox.y)));
		// true = kollision
	}

	public final boolean headToObstacleCollision(GameObject obj) {
		if (obj instanceof Obstacle) {
			return !(((((headHitbox.x + headHitbox.width) < obj.getHitbox().x) || ((obj.getHitbox().x + obj.getHitbox().width) < headHitbox.x)) || (((headHitbox.y + headHitbox.height) < obj.getHitbox().y || (obj.getHitbox().y + obj.getHitbox().height) < headHitbox.y))));
		}
		return false;
	}

	public final boolean bodyToObstacleCollision(GameObject obj) {
		if (obj instanceof Obstacle) {
			return !((((bodyHitbox.x + bodyHitbox.width) < obj.getHitbox().x) || ((obj.getHitbox().x + obj.getHitbox().width) < bodyHitbox.x)) || (((bodyHitbox.y + bodyHitbox.height) < obj.getHitbox().y || (obj.getHitbox().y + obj.getHitbox().height) < bodyHitbox.y)));
		}
		return false;
	}

	public final boolean topToObstacleCollision(Obstacle o) {
		return !((((topHitbox.x + topHitbox.width) < o.getHitbox().x) || ((o.getHitbox().x + o.getHitbox().width) < topHitbox.x)) || (((topHitbox.y + topHitbox.height) < o.getHitbox().y || (o.getHitbox().y + o.getHitbox().height) < topHitbox.y)));
	}

	public final boolean punchCollision(GameObject obj) { // MUSS DRINGEND
															// GE�NDERT
		// WERDEN!
		if (obj instanceof Charakter) {
			Charakter c = (Charakter) obj;
			return !((((armHitbox.x + armHitbox.width) < c.mainHitbox.x) || ((c.mainHitbox.x + c.mainHitbox.width) < armHitbox.x)) || (((armHitbox.y + armHitbox.height) < c.mainHitbox.y || (c.mainHitbox.y + c.mainHitbox.height) < armHitbox.y)));
		}
		return false;
	}

	public final boolean kickCollision(GameObject obj) { // MUSS DRINGEND
															// GE�NDERT
		// WERDEN!
		if (obj instanceof Charakter) {
			Charakter c = (Charakter) obj;
			return !((((legHitbox.x + legHitbox.width) < c.mainHitbox.x) || ((c.mainHitbox.x + c.mainHitbox.width) < legHitbox.x)) || (((legHitbox.y + legHitbox.height) < c.mainHitbox.y || (c.mainHitbox.y + c.mainHitbox.height) < legHitbox.y)));
		}
		return false;
	}

	public final boolean blockCollision(GameObject obj) {
		if (obj instanceof Charakter) {
			if (movedLeft)
				return leftHitbox.intersects(((Charakter) obj).mainHitbox);
			else
				return rightHitbox.intersects(((Charakter) obj).mainHitbox);
		}

		else if ((obj instanceof PhysicsObject)) {
			if (movedLeft)
				return leftHitbox.intersects(obj.hitbox);
			else
				return rightHitbox.intersects(obj.hitbox);
		}
		return false;
	}

	// BOOLEANS FOR PRESSED KEYS
	// MOVEMENTS
	protected boolean left = false;
	protected boolean right = false;
	protected boolean jump = false;
	protected boolean fall = false;
	protected boolean enableFall = false;
	protected boolean standUp = false;
	protected boolean standUpPossible = true;
	// ACTIONS
	protected boolean kick = false;
	protected boolean punch = false;
	protected boolean block = false;
	protected boolean duck = false;
	protected boolean punchFree = true;
	protected boolean kickFree = true;
	protected boolean blockFree = true;
	protected boolean hit = false;

	public void setHit(boolean status) {
		this.hit = status;
	}

	public void setRecoiling(boolean status) {
		recoiling = status;
	}

	@Override
	public void run() {
		tt.start();
		ut.start();
		while (lives > 0 && level.levelAlive) {
			if (hp <= 0 && lives > 1) {
				lives--;
				if (this instanceof Player) {
					moveObject(level.spawnFromBorder + (playerNumber * 200), 200);
				} else {
					moveObject(1920 - level.spawnFromBorder, 200);
				}
				hp = maxHP;
			}
			if (hp <= 0 && lives == 1) {
				lives = 0;
			}

			try {
				sleep(sleepThread);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			findLastWaypoint();
		}
		alive = false;
		synchronized (this) {
			try {
				level.deleteCharakter(this);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	@SuppressWarnings("deprecation")
	public final void draw(Graphics2D g2d) { // FOR IMAGES SEE:
		// main.CreateScene.createPlayerGraphics()
		// Line 220 =: index=0

		if (alive && (level == null || level.levelAlive)) {
			drawBox = new Rectangle(hitbox);
			// standing
			if (!duck) {
				if (movedLeft) {
					currentImage = g.get(18);

				} else {
					currentImage = g.get(19);
				}
				if (left && !right && !kick && !punch) {
					currentImage = g.get(leftRun);
					if (leftRun == 8) {
						leftRun = 0;
					} else {
						if (!jumped) {
							leftRun++;
						} else {
							// currentImage=g.get(indexofLeftJump);
						}
					}

				} else if (right && !left && !kick && !punch) {
					currentImage = g.get(rightRun);
					if (rightRun == 17) {
						rightRun = 9;
					} else {
						if (!jumped) {
							rightRun++;
						} else {
							// currentImage=g.get(indexofRightJump);
						}
					}

				}
				if (movedLeft) {

					if (punch) {
						if (!canShoot) {
							// g2d.setColor(Color.red);
							// g2d.fill(new Rectangle((int) (armHitbox.x *
							// Main.rescaleConstant), (int) (armHitbox.y *
							// Main.rescaleConstant), (int) (armHitbox.width *
							// Main.rescaleConstant), (int) (armHitbox.height *
							// Main.rescaleConstant)));
							currentImage = g.get(24);
							drawBox = new Rectangle(drawBox.x, drawBox.y, currentImage.getWidth(), drawBox.height);
						} else {
							currentImage = g.get(26);
						}
					} else if (kick) {
						// g2d.setColor(Color.black);
						// g2d.fill(new Rectangle((int) (legHitbox.x *
						// Main.rescaleConstant), (int) (legHitbox.y *
						// Main.rescaleConstant), (int) (legHitbox.width *
						// Main.rescaleConstant), (int) (legHitbox.height *
						// Main.rescaleConstant)));
						currentImage = g.get(22);
						drawBox = new Rectangle(drawBox.x, drawBox.y, currentImage.getWidth(), drawBox.height);
					} else if (block && !left && !right) {
						currentImage = g.get(36);
					}
				} else {

					if (punch) {
						if (!canShoot) {
							// g2d.setColor(Color.red);
							// g2d.fill(new Rectangle((int) (armHitbox.x *
							// Main.rescaleConstant), (int) (armHitbox.y *
							// Main.rescaleConstant), (int) (armHitbox.width *
							// Main.rescaleConstant), (int) (armHitbox.height *
							// Main.rescaleConstant)));
							currentImage = g.get(25);
							drawBox = new Rectangle(drawBox.x - punchRange, drawBox.y, currentImage.getWidth(), drawBox.height);
						} else {
							currentImage = g.get(28);
						}
					} else if (kick) {
						// g2d.setColor(Color.black);
						// g2d.fill(new Rectangle((int) (legHitbox.x *
						// Main.rescaleConstant), (int) (legHitbox.y *
						// Main.rescaleConstant), (int) (legHitbox.width *
						// Main.rescaleConstant), (int) (legHitbox.height *
						// Main.rescaleConstant)));
						currentImage = g.get(23);
						drawBox = new Rectangle(drawBox.x - kickRange, drawBox.y, currentImage.getWidth(), drawBox.height);
					} else if (block && !left && !right) {
						currentImage = g.get(37);
					}
				}

			}
			// ducked
			else {
				if (movedLeft) {
					currentImage = g.get(20);
				} else {
					currentImage = g.get(21);
				}
				/*
				 * if (left && !right && !kick && !punch) { currentImage =
				 * g.get(leftRun); // leftRun to change into // "int
				 * duckedLeftRun = // indexOf(duckedLeftRun.0) if (leftRun == 8)
				 * { leftRun = 0; } else { if (!jumped) { leftRun++; // -//- }
				 * else { // currentImage=g.get(indexofDuckedLeftJump); } }
				 * 
				 * } else if (right && !left && !kick && !punch) { currentImage
				 * = g.get(rightRun); // rightRun to change into // "int
				 * duckedRightRun = // indexOf(duckedRightRun.0) if (rightRun ==
				 * 17) { rightRun = 9; } else { if (!jumped) { rightRun++; //
				 * -//- } else { // currentImage=g.get(indexofDuckedRightJump);
				 * } }
				 * 
				 * }
				 */
				if (movedLeft) {

					if (punch) {

						if (!canShoot) {
							currentImage = g.get(30);
						} else {
							currentImage = g.get(27);
						}
					} else if (kick) {
						currentImage = g.get(32);
					} else if (block) {
						currentImage = g.get(34);
					}
				} else {

					if (punch) {

						if (!canShoot) {
							currentImage = g.get(31);
						} else {
							currentImage = g.get(29);
						}
					} else if (kick) {
						currentImage = g.get(33);
					} else if (block) {
						currentImage = g.get(35);
					}
				}

			}
			g2d.drawImage(ImageManagement.rescaleImage(currentImage, new Dimension((int) (drawBox.width * Main.rescaleConstant), (int) (drawBox.height * Main.rescaleConstant))), (int) (drawBox.x * Main.rescaleConstant), (int) (drawBox.y * Main.rescaleConstant), null);
		}

	}

	@Override
	public final String toString() {
		return name;
	}

	public ArrayList<BufferedImage> getGraphics() {
		return g;
	}
}
