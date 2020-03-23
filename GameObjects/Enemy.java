package GameObjects;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

import Data.Waypoint;
import Data.WaypointPriorityElement;
import Scenes.Level;
import Scenes.Settings;

public final class Enemy extends Charakter {
	private static final Dimension playerHitbox = new Dimension(50, 100);
	public static final ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
	public int reactionTime;

	protected BufferedImage currentImage;
	private static int eLives = 3;
	private Waypoint targetWP;
	private Waypoint nextWP;
	private WaypointPriorityElement currentLastElement;

	private ArrayList<WaypointPriorityElement> openList;
	private ArrayList<WaypointPriorityElement> closedList;

	private ArrayList<Waypoint> waypointQueue;

	public Enemy(Point pPosition, String name, int hp, Level lvl) {
		super(new Rectangle(pPosition, playerHitbox), name, hp, lvl);
		if (Settings.difbyte == 0) {
			reactionTime = 100;
		} else if (Settings.difbyte == 1) {
			reactionTime = 75;
		} else {
			reactionTime = 1;
		}
		lives = eLives;
		class FindEnemyThread extends Thread {
			@Override
			public void run() {
				while (alive && level.levelAlive) {
					try {
						synchronized (this) {
							getToTarget();
						}
					} catch (Exception e) {
						// e.printStackTrace();
					}
				}
			}
		}
		FindEnemyThread fet = new FindEnemyThread();
		fet.setName("Find Enemy Thread");
		fet.start();

	}

	public final ArrayList<BufferedImage> getGraphics() {
		return images;
	}

	@Override
	public final void run() {
		g.addAll(Enemy.images);
		setName("Enemy Thread");
		super.run();
	}

	private final void getToTarget() {
		if (level.players.get(0).hitbox.getLocation().distance(hitbox.getLocation()) < 150) {
			if (level.players.get(0).hitbox.getY() + 50 < hitbox.getY()) {
				fall = true;
			} else if (level.players.get(0).hitbox.getY() > hitbox.getY() + 150 && (jumped == false || doubleJumped == false)) {
				timedJump();
			} else if (level.players.get(0).hitbox.getX() - hitbox.getX() < -50) {
				left = true;
				right = false;
			} else if (level.players.get(0).hitbox.getX() - hitbox.getX() > 50) {
				left = false;
				right = true;
			} else {
				stopAllSideMovement();
				Random rnd = new Random();
				if (level.players.get(0).kick || level.players.get(0).punch) {
					if (Settings.difbyte == 2) {
						block = true;
					} else {
						int x = rnd.nextInt(3);
						if (x == 0) {
							duck = true;
							freeze(100);
							standUp = true;
						} else if (x == 1) {
							block = true;
						}
					}
				} else {
					int x = rnd.nextInt(3);
					if (x == 0 && punchFree) {
						punch = true;
						punchFree = false;
						class PunchWaitThread extends Thread {
							public void run() {
								freeze(500);
								punchFree = true;
							}

						}
						;
						PunchWaitThread pwt = new PunchWaitThread();
						pwt.start();
					} else if (x == 1 && kickFree) {
						kick = true;
						kickFree = false;
						class kickWaitThread extends Thread {
							public void run() {
								freeze(500);
								kickFree = true;
							}

						}
						;
						kickWaitThread kwt = new kickWaitThread();
						kwt.start();
					} else if (x == 2 && kickFree) {
						duck = true;
						freeze(50);
						kick = true;
						kickFree = false;
						class dropKickWaitThread extends Thread {
							public void run() {
								freeze(500);
								kickFree = true;
								standUp = true;
							}
						}
						;
						dropKickWaitThread kwt = new dropKickWaitThread();
						kwt.start();
					}
				}
				freeze(reactionTime);
			}
		} else {
			targetWP = level.players.get(0).currentWP;
			if (currentWP != targetWP && targetWP != null) {
				findRoute();
				getToNext();
			}
		}
	}

	private final void getToNext() {
		if (!waypointQueue.isEmpty()) {
			nextWP = waypointQueue.get(0);
		}
		if (currentWP != nextWP) {
			if (isLeft(nextWP)) {
				left = true;
				right = false;
			} else if (isRight(nextWP)) {
				right = true;
				left = false;
			}
			if (isUp(nextWP) && (jumped == false || doubleJumped == false)) {
				timedJump();
			} else if (isDown(nextWP) && enableFall) {
				fall = true;
			}
		} else {
			stopAllSideMovement();
		}
	}

	private final synchronized void timedJump() {
		class JumpThread extends Thread {
			@Override
			public final synchronized void run() {
				jump();
				try {
					sleep(270);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				try {
					if (isUp(nextWP)) {
						jump();
					}
				} catch (NullPointerException e) {

				}
			}

			private final synchronized void jump() {
				if (jumped) {
					doubleJumped = true;
				}
				resetJumpCounter();
				jump = true;
			}
		}

		JumpThread jt = new JumpThread();
		jt.setName("Jump Thread");
		jt.start();
	}

	private final boolean isDown(Waypoint wp) {
		return hitbox.y + 50 < wp.p.y;
	}

	private final boolean isUp(Waypoint wp) {
		return hitbox.y + 50 > wp.p.y;
	}

	private final boolean isRight(Waypoint wp) {
		return hitbox.x + 25 < wp.p.x;
	}

	private final boolean isLeft(Waypoint wp) {
		return hitbox.x + 25 > wp.p.x;
	}

	private final synchronized void findRoute() {
		WaypointPriorityElement startKnoten = new WaypointPriorityElement(currentWP, 0, 0, currentWP.p.distance(targetWP.p));

		openList = new ArrayList<WaypointPriorityElement>();
		closedList = new ArrayList<WaypointPriorityElement>();

		openList.add(startKnoten);

		WaypointPriorityElement currentNode;

		do {
			currentNode = openList.remove(0);

			if (currentNode.wp == targetWP && currentLastElement == null) {
				currentLastElement = currentNode;
			}
			if (currentNode.wp.equals(targetWP) && currentNode.g <= currentLastElement.g) {
				currentLastElement = currentNode;
				waypointQueue = new ArrayList<Waypoint>();
				WaypointPriorityElement helper = currentNode;
				while (!helper.wp.equals(currentWP)) {
					waypointQueue.add(helper.wp);
					helper = helper.prevWaypoint;
				}
				Collections.reverse(waypointQueue);
				for (int i = 0; i < waypointQueue.size(); i++) {
					waypointQueue.get(i).color = Color.RED;
				}
			}

			closedList.add(currentNode);

			expandNode(currentNode);

		} while (!openList.isEmpty());
		currentLastElement = null;
	}

	private final synchronized void expandNode(WaypointPriorityElement currentNode) {
		ArrayList<WaypointPriorityElement> successorList = createSuccessorList(currentNode);
		for (int i = 0; i < successorList.size(); i++) {
			WaypointPriorityElement successor = successorList.get(i);
			if (closedList.contains(successor)) {
				continue;
			}

			double tentative_g = currentNode.g + successor.c;

			if (openList.contains(successor) && tentative_g >= successor.g) {
				continue;
			}

			if (openList.contains(successor)) {
				Collections.sort(openList, new Comparator<WaypointPriorityElement>() {
					@Override
					public int compare(WaypointPriorityElement o1, WaypointPriorityElement o2) {
						if (o1.f < o2.f) {
							return -1;
						} else if (o2.f < o2.f) {
							return 1;
						} else {
							return 0;
						}
					}
				});
			} else {
				openList.add(successor);
				Collections.sort(openList, new Comparator<WaypointPriorityElement>() {
					@Override
					public int compare(WaypointPriorityElement o1, WaypointPriorityElement o2) {
						if (o1.f < o2.f) {
							return -1;
						} else if (o2.f < o2.f) {
							return 1;
						} else {
							return 0;
						}
					}
				});
			}
		}
	}

	private final synchronized ArrayList<WaypointPriorityElement> createSuccessorList(WaypointPriorityElement currentNode) {
		ArrayList<WaypointPriorityElement> helper = new ArrayList<WaypointPriorityElement>();

		for (int i = 0; i < currentNode.wp.connectedPoints.size(); i++) {
			Waypoint curr = currentNode.wp.connectedPoints.get(i);
			double c = curr.p.distance(currentNode.wp.p);
			double g = c + currentNode.g;
			double f = g + curr.p.distance(targetWP.p);
			helper.add(new WaypointPriorityElement(curr, c, g, f));
			helper.get(i).prevWaypoint = currentNode;
		}

		Collections.sort(helper, new Comparator<WaypointPriorityElement>() {
			@Override
			public final int compare(WaypointPriorityElement o1, WaypointPriorityElement o2) {
				if (o1.f < o2.f) {
					return 1;
				} else if (o2.f < o2.f) {
					return -1;
				} else {
					return 0;
				}
			}
		});

		return helper;
	}
}
