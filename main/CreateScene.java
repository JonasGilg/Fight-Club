package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;

import Data.Waypoint;
import FileManagement.ImageManagement;
import GameObjects.Bazooka;
import GameObjects.BouncyBoots;
import GameObjects.Enemy;
import GameObjects.Healthpack;
import GameObjects.LeerItem;
import GameObjects.Obstacle;
import GameObjects.Platform;
import GameObjects.Player;
import GameObjects.PowerGlove;
import GameObjects.Rocket;
import GameObjects.SpeedyBoots;
import Scenes.Credits;
import Scenes.EndOfGame;
import Scenes.Highscore;
import Scenes.Level;
import Scenes.Menu;
import Scenes.Scene;
import Scenes.Settings;
import Scenes.Startscreen;
import Scenes.TransitionScreen;
import Scenes.Tutorial;

public final class CreateScene { // Lagert die Generierung von Scenen und Leveln
									// aus

	public static final Startscreen createStartscreen() { // Generiert
															// den
															// Startladebildschirm
		return new Startscreen("/Sounds/startsound.wav", "Images/Menu Background.png");
	}

	public static final Menu createMenu() { // Generiert das Men�
		return new Menu("/Sounds/FightClubMenu.wav", "Images/MainMenu.png");
	}

	public static final Tutorial createTutorial(Main main) { // Generiert das
																// Tutorial
		return new Tutorial("/Sounds/Griphop.wav", "Images/Fight_Club_Tutorial.png");
	}

	public static final Settings createSettings() { // Generiert die
		// Einstellungen
		return new Settings("/Sounds/Rollin_at_5.wav", "Images/gears.png");
	}

	public static final Highscore createHighscore() { // Generiert den
		// Highscorescreen
		return new Highscore("/Sounds/Pinball Spring.wav", "Images/Fight_Club_Highscore_Hannover.png");
	}

	public static final Credits createCredits() { // Generiert die
													// Credits
		return new Credits("/Sounds/Life_of_Riley.wav", "Images/Fight_Club_Credits_Hannover.png");
	}

	public static final Level createLevelOne() { // Generiert das
		// erste Level

		final class NextLevel implements SceneChanger {

			@Override
			public Scene nextScene() {
				return createLevelTwo();
			}
		}

		final class TransitionScene implements SceneChanger {

			@Override
			public Scene nextScene() {
				return new TransitionScreen("/Sounds/vicSound.wav", "Images/Fight_Club_Victory.jpg", new NextLevel());
			}
		}

		Level level1 = new Level("/Sounds/Kick_Shock.wav", "Images/sewer2.jpg", new TransitionScene(), new DefeatScene());
		
		Platform.drawColor = new Color(200, 200, 200);
		level1.gravityNumber = Settings.gravityNumber;

		level1.obstacles.add(new Obstacle(new Point(200, 1000), new Dimension(1600, 50), level1, "Images/hoer.png"));
		level1.platforms.add(new Platform(new Point(0, 800), 200, level1));
		level1.platforms.add(new Platform(new Point(0, 500), 200, level1));
		level1.platforms.add(new Platform(new Point(400, 650), 200, level1));
		level1.platforms.add(new Platform(new Point(400, 350), 200, level1));
		level1.platforms.add(new Platform(new Point(800, 500), 300, level1));

		level1.platforms.add(new Platform(new Point(1300, 650), 200, level1));
		level1.platforms.add(new Platform(new Point(1300, 350), 200, level1));
		level1.platforms.add(new Platform(new Point(1700, 800), 200, level1));
		level1.platforms.add(new Platform(new Point(1700, 500), 200, level1));
		
		Waypoint wp001 = new Waypoint(new Point(250, 950),  "001");
		Waypoint wp002 = new Waypoint(new Point(350, 950),  "002");
		Waypoint wp003 = new Waypoint(new Point(450, 950),  "003");
		Waypoint wp004 = new Waypoint(new Point(550, 950),  "004");
		Waypoint wp005 = new Waypoint(new Point(650, 950),  "005");
		Waypoint wp006 = new Waypoint(new Point(750, 950),  "006");
		Waypoint wp007 = new Waypoint(new Point(850, 950),  "007");
		Waypoint wp008 = new Waypoint(new Point(950, 950),  "008");
		Waypoint wp009 = new Waypoint(new Point(1050, 950),  "009");
		Waypoint wp010 = new Waypoint(new Point(1150, 950),  "010");
		Waypoint wp011 = new Waypoint(new Point(1250, 950),  "011");
		Waypoint wp012 = new Waypoint(new Point(1350, 950),  "012");
		Waypoint wp013 = new Waypoint(new Point(1450, 950),  "013");
		Waypoint wp014 = new Waypoint(new Point(1550, 950),  "014");
		Waypoint wp015 = new Waypoint(new Point(1650, 950),  "015");
		Waypoint wp016 = new Waypoint(new Point(1750, 950),  "016");
		
		level1.waypoints.add(wp001);
		level1.waypoints.add(wp002);
		level1.waypoints.add(wp003);
		level1.waypoints.add(wp004);
		level1.waypoints.add(wp005);
		level1.waypoints.add(wp006);
		level1.waypoints.add(wp007);
		level1.waypoints.add(wp008);
		level1.waypoints.add(wp009);
		level1.waypoints.add(wp010);
		level1.waypoints.add(wp011);
		level1.waypoints.add(wp012);
		level1.waypoints.add(wp013);
		level1.waypoints.add(wp014);
		level1.waypoints.add(wp015);
		level1.waypoints.add(wp016);
		
		for(int i = 0; i < level1.waypoints.size() - 1; i++) {
			level1.waypoints.get(i).connectedPoints.add(level1.waypoints.get(i + 1));
			level1.waypoints.get(i + 1).connectedPoints.add(level1.waypoints.get(i));
		}
		
		Waypoint wp101 = new Waypoint(new Point(50, 750),  "101");
		Waypoint wp102 = new Waypoint(new Point(150, 750),  "102");
		
		wp101.connectedPoints.add(wp102);
		wp102.connectedPoints.add(wp101);
		
		wp101.connectedPoints.add(wp001);
		wp001.connectedPoints.add(wp101);
		
		level1.waypoints.add(wp101);
		level1.waypoints.add(wp102);
		
		wp102.connectedPoints.add(wp002);
		wp002.connectedPoints.add(wp102);
		
		Waypoint wp103 = new Waypoint(new Point(1750, 750), "103");
		Waypoint wp104 = new Waypoint(new Point(1850, 750), "104");
		
		wp103.connectedPoints.add(wp104);
		wp104.connectedPoints.add(wp103);
		
		wp103.connectedPoints.add(wp015);
		wp015.connectedPoints.add(wp103);
		
		wp104.connectedPoints.add(wp016);
		wp016.connectedPoints.add(wp104);
		
		level1.waypoints.add(wp103);
		level1.waypoints.add(wp104);
		
		Waypoint wp301 = new Waypoint(new Point(50, 450),  "301");
		Waypoint wp302 = new Waypoint(new Point(150, 450),  "302");
		
		wp301.connectedPoints.add(wp302);
		wp302.connectedPoints.add(wp301);
		
		level1.waypoints.add(wp302);
		level1.waypoints.add(wp301);
		
		Waypoint wp306 = new Waypoint(new Point(1750, 450), "306");
		Waypoint wp307 = new Waypoint(new Point(1850, 450), "307");
		
		wp306.connectedPoints.add(wp307);
		wp307.connectedPoints.add(wp306);
		
		level1.waypoints.add(wp307);
		level1.waypoints.add(wp306);
		
		Waypoint wp201 = new Waypoint(new Point(450, 600), "201");
		Waypoint wp202 = new Waypoint(new Point(550, 600), "202");
		Waypoint wp203 = new Waypoint(new Point(1350, 600), "203");
		Waypoint wp204 = new Waypoint(new Point(1450, 600), "204");
		
		level1.waypoints.add(wp201);
		level1.waypoints.add(wp202);
		level1.waypoints.add(wp203);
		level1.waypoints.add(wp204);
		
		Waypoint wp303 = new Waypoint(new Point(850, 450), "303");
		Waypoint wp304 = new Waypoint(new Point(950, 450), "304");
		Waypoint wp305 = new Waypoint(new Point(1050, 450), "305");
		
		level1.waypoints.add(wp303);
		level1.waypoints.add(wp304);
		level1.waypoints.add(wp305);
		
		Waypoint wp401 = new Waypoint(new Point(450, 300), "401");
		Waypoint wp402 = new Waypoint(new Point(550, 300), "402");
		Waypoint wp403 = new Waypoint(new Point(1350, 300), "403");
		Waypoint wp404 = new Waypoint(new Point(1450, 300), "404");

		level1.waypoints.add(wp401);
		level1.waypoints.add(wp402);
		level1.waypoints.add(wp403);
		level1.waypoints.add(wp404);
		
		wp102.connectedPoints.add(wp201);
		wp201.connectedPoints.add(wp102);
		
		wp202.connectedPoints.add(wp201);
		wp201.connectedPoints.add(wp202);
		
		wp201.connectedPoints.add(wp003);
		wp202.connectedPoints.add(wp004);
		
		wp201.connectedPoints.add(wp302);
		wp302.connectedPoints.add(wp201);
		
		wp202.connectedPoints.add(wp303);
		wp303.connectedPoints.add(wp202);
		
		wp303.connectedPoints.add(wp304);
		wp304.connectedPoints.add(wp303);
		
		wp304.connectedPoints.add(wp305);
		wp305.connectedPoints.add(wp304);
		
		wp302.connectedPoints.add(wp401);
		wp401.connectedPoints.add(wp302);
		
		wp401.connectedPoints.add(wp402);
		wp402.connectedPoints.add(wp401);
		
		wp401.connectedPoints.add(wp201);
		wp402.connectedPoints.add(wp202);
		
		wp402.connectedPoints.add(wp303);
		wp303.connectedPoints.add(wp402);
		
		wp301.connectedPoints.add(wp101);
		wp302.connectedPoints.add(wp102);
		
		wp303.connectedPoints.add(wp007);
		wp304.connectedPoints.add(wp008);
		wp305.connectedPoints.add(wp009);
		
		wp403.connectedPoints.add(wp305);
		wp305.connectedPoints.add(wp403);
		
		wp203.connectedPoints.add(wp305);
		wp305.connectedPoints.add(wp203);
		
		wp404.connectedPoints.add(wp403);
		wp403.connectedPoints.add(wp404);
		
		wp203.connectedPoints.add(wp204);
		wp204.connectedPoints.add(wp203);
		
		wp403.connectedPoints.add(wp203);
		wp404.connectedPoints.add(wp204);
		
		wp402.connectedPoints.add(wp303);
		wp303.connectedPoints.add(wp402);
		
		wp306.connectedPoints.add(wp404);
		wp404.connectedPoints.add(wp306);

		wp204.connectedPoints.add(wp103);
		wp103.connectedPoints.add(wp204);
		
		wp306.connectedPoints.add(wp103);
		wp307.connectedPoints.add(wp104);
		
		wp204.connectedPoints.add(wp306);
		wp306.connectedPoints.add(wp204);
		
		wp203.connectedPoints.add(wp012);
		wp204.connectedPoints.add(wp013);
		
		
		
		
		

		level1.items.add(new Healthpack(new Point(-100, 1000), level1, 0));
		// echte Items und Spawnzeiten
		level1.items.add(new Healthpack(new Point(650, -100), level1));
		level1.items.add(new BouncyBoots(new Point(250, -100), level1, Settings.bouncyBootsActTime));
		level1.items.add(new SpeedyBoots(new Point(450, -100), level1, Settings.speedyBootsActTime));
		level1.items.add(new PowerGlove(new Point(550, -100), level1, Settings.powerGloveActTime));
		level1.items.add(new Bazooka(new Point(750, -100), Settings.pistolActiveTime, level1));
		level1.itemSpawnTimes.add(70);
		level1.itemSpawnTimes.add(50);
		level1.itemSpawnTimes.add(35);
		level1.itemSpawnTimes.add(20);
		level1.itemSpawnTimes.add(10);
		for (int i = 0; i < Settings.playerModebit + 1; i++) {
			level1.players.add(new Player(new Point(300 + (i * 300), 100), Settings.playerName[i], level1));

		}
		int i = 0;
		for (Player p : level1.players) {
			p.setPlayerNumber(i);
			i++;
		}
		
		if (Settings.playerModebit == 0) {
			level1.enemies.add(new Enemy(new Point(1810, 300), "Chuck", 500, level1));
		}
		
		
		createGraphics(level1);
		level1.buildLevel();
		return level1;
	}

	public static final Level createLevelTwo() { // Generiert das
													// zweite Level
		final class NextLevel implements SceneChanger {

			@Override
			public Scene nextScene() {
				return createLevelThree();
			}
		}

		final class TransitionScene implements SceneChanger {

			@Override
			public Scene nextScene() {
				return new TransitionScreen("/Sounds/vicSound.wav", "Images/Fight_Club_Victory.jpg", new NextLevel());
			}
		}

		Level level2 = new Level("/Sounds/Ishikari_Lore.wav", "Images/Dojo.jpg", new TransitionScene(), new DefeatScene());

		level2.gravityNumber = Settings.gravityNumber;
		Platform.drawColor = new Color(200, 200, 200);

		level2.obstacles.add(new Obstacle(new Point(0, 1055), new Dimension(1920, 250), level2, "Images/holz.jpg"));
		level2.obstacles.add(new Obstacle(new Point(0, 0), new Dimension(25, 1200), level2, "Images/holz.jpg"));
		level2.obstacles.add(new Obstacle(new Point(1895, 0), new Dimension(25, 1200), level2, "Images/holz.jpg"));
		level2.platforms.add(new Platform(new Point(25, 300), 200, level2));
		level2.platforms.add(new Platform(new Point(1695, 300), 200, level2));
		level2.platforms.add(new Platform(new Point(25, 550), 1870, level2));
		level2.platforms.add(new Platform(new Point(25, 800), 200, level2));
		level2.platforms.add(new Platform(new Point(1695, 800), 200, level2));

		Waypoint wp001 = new Waypoint(new Point((1870 / 19) * 1 - 25, 1005), "001");
		Waypoint wp002 = new Waypoint(new Point((1870 / 19) * 2 - 25, 1005), "002");
		Waypoint wp003 = new Waypoint(new Point((1870 / 19) * 3 - 25, 1005), "003");
		Waypoint wp004 = new Waypoint(new Point((1870 / 19) * 4 - 25, 1005), "004");
		Waypoint wp005 = new Waypoint(new Point((1870 / 19) * 5 - 25, 1005), "005");
		Waypoint wp006 = new Waypoint(new Point((1870 / 19) * 6 - 25, 1005), "006");
		Waypoint wp007 = new Waypoint(new Point((1870 / 19) * 7 - 25, 1005), "007");
		Waypoint wp008 = new Waypoint(new Point((1870 / 19) * 8 - 25, 1005), "008");
		Waypoint wp009 = new Waypoint(new Point((1870 / 19) * 9 - 25, 1005), "009");
		Waypoint wp010 = new Waypoint(new Point((1870 / 19) * 10 - 25, 1005), "010");
		Waypoint wp011 = new Waypoint(new Point((1870 / 19) * 11 - 25, 1005), "011");
		Waypoint wp012 = new Waypoint(new Point((1870 / 19) * 12 - 25, 1005), "012");
		Waypoint wp013 = new Waypoint(new Point((1870 / 19) * 13 - 25, 1005), "013");
		Waypoint wp014 = new Waypoint(new Point((1870 / 19) * 14 - 25, 1005), "014");
		Waypoint wp015 = new Waypoint(new Point((1870 / 19) * 15 - 25, 1005), "015");
		Waypoint wp016 = new Waypoint(new Point((1870 / 19) * 16 - 25, 1005), "016");
		Waypoint wp017 = new Waypoint(new Point((1870 / 19) * 17 - 25, 1005), "017");
		Waypoint wp018 = new Waypoint(new Point((1870 / 19) * 18 - 25, 1005), "018");
		Waypoint wp019 = new Waypoint(new Point((1870 / 19) * 19 - 25, 1005), "019");

		level2.waypoints.add(wp001);
		level2.waypoints.add(wp002);
		level2.waypoints.add(wp003);
		level2.waypoints.add(wp004);
		level2.waypoints.add(wp005);
		level2.waypoints.add(wp006);
		level2.waypoints.add(wp007);
		level2.waypoints.add(wp008);
		level2.waypoints.add(wp009);
		level2.waypoints.add(wp010);
		level2.waypoints.add(wp011);
		level2.waypoints.add(wp012);
		level2.waypoints.add(wp013);
		level2.waypoints.add(wp014);
		level2.waypoints.add(wp015);
		level2.waypoints.add(wp016);
		level2.waypoints.add(wp017);
		level2.waypoints.add(wp018);
		level2.waypoints.add(wp019);

		for (int i = 0; i < level2.waypoints.size() - 1; i++) {
			level2.waypoints.get(i).connectedPoints.add(level2.waypoints.get(i + 1));
			level2.waypoints.get(i + 1).connectedPoints.add(level2.waypoints.get(i));
		}

		Waypoint wp201 = new Waypoint(new Point((1870 / 19) * 1 - 25, 500), "201");
		Waypoint wp202 = new Waypoint(new Point((1870 / 19) * 2 - 25, 500), "202");
		Waypoint wp203 = new Waypoint(new Point((1870 / 19) * 3 - 25, 500), "203");
		Waypoint wp204 = new Waypoint(new Point((1870 / 19) * 4 - 25, 500), "204");
		Waypoint wp205 = new Waypoint(new Point((1870 / 19) * 5 - 25, 500), "205");
		Waypoint wp206 = new Waypoint(new Point((1870 / 19) * 6 - 25, 500), "206");
		Waypoint wp207 = new Waypoint(new Point((1870 / 19) * 7 - 25, 500), "207");
		Waypoint wp208 = new Waypoint(new Point((1870 / 19) * 8 - 25, 500), "208");
		Waypoint wp209 = new Waypoint(new Point((1870 / 19) * 9 - 25, 500), "209");
		Waypoint wp210 = new Waypoint(new Point((1870 / 19) * 10 - 25, 500), "210");
		Waypoint wp211 = new Waypoint(new Point((1870 / 19) * 11 - 25, 500), "211");
		Waypoint wp212 = new Waypoint(new Point((1870 / 19) * 12 - 25, 500), "212");
		Waypoint wp213 = new Waypoint(new Point((1870 / 19) * 13 - 25, 500), "213");
		Waypoint wp214 = new Waypoint(new Point((1870 / 19) * 14 - 25, 500), "214");
		Waypoint wp215 = new Waypoint(new Point((1870 / 19) * 15 - 25, 500), "215");
		Waypoint wp216 = new Waypoint(new Point((1870 / 19) * 16 - 25, 500), "216");
		Waypoint wp217 = new Waypoint(new Point((1870 / 19) * 17 - 25, 500), "217");
		Waypoint wp218 = new Waypoint(new Point((1870 / 19) * 18 - 25, 500), "218");
		Waypoint wp219 = new Waypoint(new Point((1870 / 19) * 19 - 25, 500), "219");

		level2.waypoints.add(wp201);
		level2.waypoints.add(wp202);
		level2.waypoints.add(wp203);
		level2.waypoints.add(wp204);
		level2.waypoints.add(wp205);
		level2.waypoints.add(wp206);
		level2.waypoints.add(wp207);
		level2.waypoints.add(wp208);
		level2.waypoints.add(wp209);
		level2.waypoints.add(wp210);
		level2.waypoints.add(wp211);
		level2.waypoints.add(wp212);
		level2.waypoints.add(wp213);
		level2.waypoints.add(wp214);
		level2.waypoints.add(wp215);
		level2.waypoints.add(wp216);
		level2.waypoints.add(wp217);
		level2.waypoints.add(wp218);
		level2.waypoints.add(wp219);

		for (int i = 19; i < level2.waypoints.size() - 1; i++) {
			level2.waypoints.get(i).connectedPoints.add(level2.waypoints.get(i + 1));
			level2.waypoints.get(i + 1).connectedPoints.add(level2.waypoints.get(i));
		}

		Waypoint wp101 = new Waypoint(new Point((1870 / 19) * 1 - 25, 750), "101");
		Waypoint wp102 = new Waypoint(new Point((1870 / 19) * 2 - 25, 750), "102");
		Waypoint wp118 = new Waypoint(new Point((1870 / 19) * 18 - 25, 750), "118");
		Waypoint wp119 = new Waypoint(new Point((1870 / 19) * 19 - 25, 750), "119");

		level2.waypoints.add(wp101);
		level2.waypoints.add(wp102);
		level2.waypoints.add(wp118);
		level2.waypoints.add(wp119);

		wp101.connectedPoints.add(wp102);
		wp101.connectedPoints.add(wp201);
		wp101.connectedPoints.add(wp001);

		wp001.connectedPoints.add(wp101);
		wp201.connectedPoints.add(wp101);

		wp102.connectedPoints.add(wp101);
		wp102.connectedPoints.add(wp202);
		wp102.connectedPoints.add(wp002);

		wp002.connectedPoints.add(wp102);
		wp202.connectedPoints.add(wp102);

		wp118.connectedPoints.add(wp119);
		wp118.connectedPoints.add(wp218);
		wp118.connectedPoints.add(wp018);

		wp018.connectedPoints.add(wp118);
		wp218.connectedPoints.add(wp118);

		wp119.connectedPoints.add(wp118);
		wp119.connectedPoints.add(wp219);
		wp119.connectedPoints.add(wp019);

		wp019.connectedPoints.add(wp119);
		wp219.connectedPoints.add(wp119);

		Waypoint wp301 = new Waypoint(new Point((1870 / 19) * 1 - 25, 250), "301");
		Waypoint wp302 = new Waypoint(new Point((1870 / 19) * 2 - 25, 250), "302");
		Waypoint wp318 = new Waypoint(new Point((1870 / 19) * 18 - 25, 250), "318");
		Waypoint wp319 = new Waypoint(new Point((1870 / 19) * 19 - 25, 250), "319");

		level2.waypoints.add(wp301);
		level2.waypoints.add(wp302);
		level2.waypoints.add(wp318);
		level2.waypoints.add(wp319);

		wp301.connectedPoints.add(wp302);
		wp301.connectedPoints.add(wp201);

		wp201.connectedPoints.add(wp301);

		wp302.connectedPoints.add(wp301);
		wp302.connectedPoints.add(wp202);

		wp202.connectedPoints.add(wp302);

		wp318.connectedPoints.add(wp319);
		wp318.connectedPoints.add(wp218);

		wp218.connectedPoints.add(wp318);

		wp319.connectedPoints.add(wp318);
		wp319.connectedPoints.add(wp219);

		wp219.connectedPoints.add(wp319);

		wp203.connectedPoints.add(wp003);
		wp204.connectedPoints.add(wp004);
		wp205.connectedPoints.add(wp005);
		wp206.connectedPoints.add(wp006);
		wp207.connectedPoints.add(wp007);
		wp208.connectedPoints.add(wp008);
		wp209.connectedPoints.add(wp009);
		wp210.connectedPoints.add(wp010);
		wp211.connectedPoints.add(wp011);
		wp212.connectedPoints.add(wp012);
		wp213.connectedPoints.add(wp013);
		wp214.connectedPoints.add(wp014);
		wp215.connectedPoints.add(wp015);
		wp216.connectedPoints.add(wp016);
		wp217.connectedPoints.add(wp017);

		wp102.connectedPoints.add(wp205);
		wp205.connectedPoints.add(wp102);

		wp102.connectedPoints.add(wp005);
		wp005.connectedPoints.add(wp102);

		wp118.connectedPoints.add(wp215);
		wp215.connectedPoints.add(wp118);

		wp118.connectedPoints.add(wp015);
		wp015.connectedPoints.add(wp118);

		wp318.connectedPoints.add(wp215);
		wp215.connectedPoints.add(wp318);
		wp205.connectedPoints.add(wp302);

		wp302.connectedPoints.add(wp205);
		wp205.connectedPoints.add(wp302);

		level2.items.add(new Healthpack(new Point(-100, 1000), level2, 0));

		level2.items.add(new Healthpack(new Point(650, -100), level2));
		level2.itemSpawnTimes.add(70);
		level2.items.add(new BouncyBoots(new Point(250, -100), level2, Settings.bouncyBootsActTime));
		level2.itemSpawnTimes.add(50);
		level2.items.add(new SpeedyBoots(new Point(450, -100), level2, Settings.speedyBootsActTime));
		level2.itemSpawnTimes.add(35);
		level2.items.add(new PowerGlove(new Point(550, -100), level2, Settings.powerGloveActTime));
		level2.itemSpawnTimes.add(20);
		level2.items.add(new Bazooka(new Point(750, -100), Settings.pistolActiveTime, level2));
		level2.itemSpawnTimes.add(10);
		for (int i = 0; i < Settings.playerModebit + 1; i++) {
			level2.players.add(new Player(new Point(150 + (i * 200), 100), Settings.playerName[i], level2));

		}
		int i = 0;
		for (Player p : level2.players) {
			p.setPlayerNumber(i);
			i++;
		}
		if (Settings.playerModebit == 0) {
			level2.enemies.add(new Enemy(new Point(1795, 200), "Chuck", 1500, level2));
		}

		createGraphics(level2);
		level2.buildLevel();
		return level2;
	}

	public static final Level createLevelThree() {
		// zweite Level
		final class NextLevel implements SceneChanger {

			@Override
			public Scene nextScene() {
				return new EndOfGame("/Sounds/vicSound.wav", "Images/Fight_Club_Victory.jpg");
			}
		}

		final class TransitionScene implements SceneChanger {

			@Override
			public Scene nextScene() {
				return new TransitionScreen("/Sounds/vicSound.wav", "Images/Fight_Club_Victory.jpg", new NextLevel());
			}
		}

		Level level3 = new Level("/Sounds/Summon_the_Rawk.wav", "Images/CageFight.jpg", new TransitionScene(), new DefeatScene());
		level3.setType("Endgegner");

		level3.gravityNumber = Settings.gravityNumber;
		Platform.drawColor = new Color(200, 200, 200);
		level3.spawnFromBorder = 800;

		level3.obstacles.add(new Obstacle(new Point(560, 600), new Dimension(800, 50), level3, "Images/wiretex.jpg"));
		level3.obstacles.add(new Obstacle(new Point(560, 200), new Dimension(50, 400), level3, "Images/wiretex.jpg"));
		level3.obstacles.add(new Obstacle(new Point(1310, 200), new Dimension(50, 400), level3, "Images/wiretex.jpg"));
		level3.obstacles.add(new Obstacle(new Point(560, 150), new Dimension(800, 50), level3, "Images/wiretex.jpg"));

		level3.platforms.add(new Platform(new Point(610, 400), 700, level3));

		Waypoint wp001 = new Waypoint(new Point(660, 550), "001");
		Waypoint wp002 = new Waypoint(new Point(760, 550), "002");
		Waypoint wp003 = new Waypoint(new Point(860, 550), "003");
		Waypoint wp004 = new Waypoint(new Point(960, 550), "004");
		Waypoint wp005 = new Waypoint(new Point(1060, 550), "005");
		Waypoint wp006 = new Waypoint(new Point(1160, 550), "006");
		Waypoint wp007 = new Waypoint(new Point(1260, 550), "007");

		level3.waypoints.add(wp001);
		level3.waypoints.add(wp002);
		level3.waypoints.add(wp003);
		level3.waypoints.add(wp004);
		level3.waypoints.add(wp005);
		level3.waypoints.add(wp006);
		level3.waypoints.add(wp007);

		for (int i = 0; i < level3.waypoints.size() - 1; i++) {
			level3.waypoints.get(i).connectedPoints.add(level3.waypoints.get(i + 1));
			level3.waypoints.get(i + 1).connectedPoints.add(level3.waypoints.get(i));
		}

		Waypoint wp101 = new Waypoint(new Point(660, 350), "101");
		Waypoint wp102 = new Waypoint(new Point(760, 350), "102");
		Waypoint wp103 = new Waypoint(new Point(860, 350), "103");
		Waypoint wp104 = new Waypoint(new Point(960, 350), "104");
		Waypoint wp105 = new Waypoint(new Point(1060, 350), "105");
		Waypoint wp106 = new Waypoint(new Point(1160, 350), "106");
		Waypoint wp107 = new Waypoint(new Point(1260, 350), "107");

		level3.waypoints.add(wp101);
		level3.waypoints.add(wp102);
		level3.waypoints.add(wp103);
		level3.waypoints.add(wp104);
		level3.waypoints.add(wp105);
		level3.waypoints.add(wp106);
		level3.waypoints.add(wp107);

		for (int i = level3.waypoints.size() / 2; i < level3.waypoints.size() - 1; i++) {
			level3.waypoints.get(i).connectedPoints.add(level3.waypoints.get(i + 1));
			level3.waypoints.get(i + 1).connectedPoints.add(level3.waypoints.get(i));
		}

		for (int i = 0; i < level3.waypoints.size() / 2; i++) {
			level3.waypoints.get(i).connectedPoints.add(level3.waypoints.get(i + level3.waypoints.size() / 2));
			level3.waypoints.get(i + level3.waypoints.size() / 2).connectedPoints.add(level3.waypoints.get(i));
		}

		wp001.connectedPoints.add(wp103);
		wp101.connectedPoints.add(wp003);

		wp002.connectedPoints.add(wp104);
		wp102.connectedPoints.add(wp004);

		wp003.connectedPoints.add(wp105);
		wp103.connectedPoints.add(wp005);
		wp003.connectedPoints.add(wp101);
		wp103.connectedPoints.add(wp001);

		wp004.connectedPoints.add(wp106);
		wp104.connectedPoints.add(wp006);
		wp004.connectedPoints.add(wp102);
		wp104.connectedPoints.add(wp002);

		wp005.connectedPoints.add(wp107);
		wp105.connectedPoints.add(wp007);
		wp005.connectedPoints.add(wp103);
		wp105.connectedPoints.add(wp003);

		wp006.connectedPoints.add(wp104);
		wp106.connectedPoints.add(wp004);

		wp007.connectedPoints.add(wp105);
		wp107.connectedPoints.add(wp005);

		level3.items.add(new Healthpack(new Point(-100, 1000), level3, 0));

		for (int i = 0; i < Settings.playerModebit + 1; i++) {
			level3.players.add(new Player(new Point(level3.spawnFromBorder + (i * 200), 100), Settings.playerName[i], level3));

		}
		int i = 0;
		for (Player p : level3.players) {
			p.setPlayerNumber(i);
			i++;
		}
		if (Settings.playerModebit == 0) {
			level3.enemies.add(new Enemy(new Point(1920 - level3.spawnFromBorder, 200), "Chuck", 5000, level3));
		}

		createGraphics(level3);
		level3.buildLevel();
		return level3;
	}

	public static final Level createMultiplayerLevel() {
		Level multilevel = new Level("/Sounds/Kick_Shock.wav", "Images/Level_1_Hintergrund.png", null, null);

		multilevel.gravityNumber = Settings.gravityNumber;

		multilevel.obstacles.add(new Obstacle(new Point(200, 1000), new Dimension(1600, 50), multilevel, "Images/wiretex.jpg"));
		multilevel.platforms.add(new Platform(new Point(0, 800), 200, multilevel));
		multilevel.platforms.add(new Platform(new Point(0, 500), 200, multilevel));
		multilevel.platforms.add(new Platform(new Point(400, 650), 200, multilevel));
		multilevel.platforms.add(new Platform(new Point(400, 350), 200, multilevel));
		multilevel.platforms.add(new Platform(new Point(800, 500), 300, multilevel));

		multilevel.platforms.add(new Platform(new Point(1300, 650), 200, multilevel));
		multilevel.platforms.add(new Platform(new Point(1300, 350), 200, multilevel));
		multilevel.platforms.add(new Platform(new Point(1700, 800), 200, multilevel));
		multilevel.platforms.add(new Platform(new Point(1700, 500), 200, multilevel));

		

		multilevel.items.add(new Healthpack(new Point(-100, 1000), multilevel, 0));
		// echte Items und Spawnzeiten
		multilevel.items.add(new Healthpack(new Point(650, -100), multilevel));
		multilevel.items.add(new BouncyBoots(new Point(250, -100), multilevel, Settings.bouncyBootsActTime));
		multilevel.items.add(new SpeedyBoots(new Point(450, -100), multilevel, Settings.speedyBootsActTime));
		multilevel.items.add(new PowerGlove(new Point(550, -100), multilevel, Settings.powerGloveActTime));
		multilevel.items.add(new Bazooka(new Point(750, -100), Settings.pistolActiveTime, multilevel));
		multilevel.itemSpawnTimes.add(20);
		multilevel.itemSpawnTimes.add(40);
		multilevel.itemSpawnTimes.add(41);
		multilevel.itemSpawnTimes.add(42);
		multilevel.itemSpawnTimes.add(43);
		for (int i = 0; i < Settings.playerModebit + 1; i++) {
			multilevel.players.add(new Player(new Point(300 + (i * 300), 100), Settings.playerName[i], multilevel));

		}
		int i = 0;
		for (Player p : multilevel.players) {
			p.setPlayerNumber(i);
			i++;
		}
		createGraphics(multilevel);
		multilevel.buildLevel();
		return multilevel;
	}

	public static final void createGraphics(Scenes.Level level) {
		createPlayerGraphics(level);
		createEnemyGraphics();
		createItemGraphics();
		createProjectileGraphics();
	}

	public static final void createPlayerGraphics(Scenes.Level level) {
		for (Player p : level.players) {

			p.getGraphics().add(ImageManagement.importImage("Images/charakter/" + Integer.toString(p.getPlayerNumber()) + "/llhc/01.png"));// links
																																			// laufen
																																			// //Stehen
			p.getGraphics().add(ImageManagement.importImage("Images/charakter/" + Integer.toString(p.getPlayerNumber()) + "/llhc/02.png"));
			p.getGraphics().add(ImageManagement.importImage("Images/charakter/" + Integer.toString(p.getPlayerNumber()) + "/llhc/03.png"));
			p.getGraphics().add(ImageManagement.importImage("Images/charakter/" + Integer.toString(p.getPlayerNumber()) + "/llhc/04.png"));
			p.getGraphics().add(ImageManagement.importImage("Images/charakter/" + Integer.toString(p.getPlayerNumber()) + "/llhc/05.png"));
			p.getGraphics().add(ImageManagement.importImage("Images/charakter/" + Integer.toString(p.getPlayerNumber()) + "/llhc/06.png"));
			p.getGraphics().add(ImageManagement.importImage("Images/charakter/" + Integer.toString(p.getPlayerNumber()) + "/llhc/07.png"));
			p.getGraphics().add(ImageManagement.importImage("Images/charakter/" + Integer.toString(p.getPlayerNumber()) + "/llhc/08.png"));
			p.getGraphics().add(ImageManagement.importImage("Images/charakter/" + Integer.toString(p.getPlayerNumber()) + "/llhc/09.png"));
			p.getGraphics().add(ImageManagement.importImage("Images/charakter/" + Integer.toString(p.getPlayerNumber()) + "/rlhc/01.png"));// rechts
																																			// laufen
			p.getGraphics().add(ImageManagement.importImage("Images/charakter/" + Integer.toString(p.getPlayerNumber()) + "/rlhc/02.png"));
			p.getGraphics().add(ImageManagement.importImage("Images/charakter/" + Integer.toString(p.getPlayerNumber()) + "/rlhc/03.png"));
			p.getGraphics().add(ImageManagement.importImage("Images/charakter/" + Integer.toString(p.getPlayerNumber()) + "/rlhc/04.png"));
			p.getGraphics().add(ImageManagement.importImage("Images/charakter/" + Integer.toString(p.getPlayerNumber()) + "/rlhc/05.png"));
			p.getGraphics().add(ImageManagement.importImage("Images/charakter/" + Integer.toString(p.getPlayerNumber()) + "/rlhc/06.png"));
			p.getGraphics().add(ImageManagement.importImage("Images/charakter/" + Integer.toString(p.getPlayerNumber()) + "/rlhc/07.png"));
			p.getGraphics().add(ImageManagement.importImage("Images/charakter/" + Integer.toString(p.getPlayerNumber()) + "/rlhc/08.png"));
			p.getGraphics().add(ImageManagement.importImage("Images/charakter/" + Integer.toString(p.getPlayerNumber()) + "/rlhc/09.png"));
			p.getGraphics().add(ImageManagement.importImage("Images/charakter/" + Integer.toString(p.getPlayerNumber()) + "/llhc/10.png"));// links
																																			// idle
			p.getGraphics().add(ImageManagement.importImage("Images/charakter/" + Integer.toString(p.getPlayerNumber()) + "/rlhc/10.png"));// rechts
																																			// idle
			p.getGraphics().add(ImageManagement.importImage("Images/charakter/" + Integer.toString(p.getPlayerNumber()) + "/dlhc/duck_idle.png"));// links
																																					// idle
																																					// //Ducken
			p.getGraphics().add(ImageManagement.importImage("Images/charakter/" + Integer.toString(p.getPlayerNumber()) + "/drhc/duck_idle.png"));// rechts
																																					// idle
			p.getGraphics().add(ImageManagement.importImage("Images/charakter/" + Integer.toString(p.getPlayerNumber()) + "/kick/left/kick_left.png"));
			p.getGraphics().add(ImageManagement.importImage("Images/charakter/" + Integer.toString(p.getPlayerNumber()) + "/kick/right/kick_right.png"));
			p.getGraphics().add(ImageManagement.importImage("Images/charakter/" + Integer.toString(p.getPlayerNumber()) + "/punch/left/Boxen.png"));
			p.getGraphics().add(ImageManagement.importImage("Images/charakter/" + Integer.toString(p.getPlayerNumber()) + "/punch/right/Boxen.png"));
			p.getGraphics().add(ImageManagement.importImage("Images/charakter/" + Integer.toString(p.getPlayerNumber()) + "/punch/left/bazooka.png"));
			p.getGraphics().add(ImageManagement.importImage("Images/charakter/" + Integer.toString(p.getPlayerNumber()) + "/punch/left/ducked_bazooka.png"));
			p.getGraphics().add(ImageManagement.importImage("Images/charakter/" + Integer.toString(p.getPlayerNumber()) + "/punch/right/bazooka.png"));
			p.getGraphics().add(ImageManagement.importImage("Images/charakter/" + Integer.toString(p.getPlayerNumber()) + "/punch/right/ducked_bazooka.png"));
			p.getGraphics().add(ImageManagement.importImage("Images/charakter/" + Integer.toString(p.getPlayerNumber()) + "/punch/left/ducked_punch_left.png")); // ducked_punch
			p.getGraphics().add(ImageManagement.importImage("Images/charakter/" + Integer.toString(p.getPlayerNumber()) + "/punch/right/ducked_punch_right.png")); // ducked_punch
			p.getGraphics().add(ImageManagement.importImage("Images/charakter/" + Integer.toString(p.getPlayerNumber()) + "/kick/left/ducked_kick_left.png"));
			p.getGraphics().add(ImageManagement.importImage("Images/charakter/" + Integer.toString(p.getPlayerNumber()) + "/kick/right/ducked_kick_right.png"));
			p.getGraphics().add(ImageManagement.importImage("Images/charakter/" + Integer.toString(p.getPlayerNumber()) + "/dlhc/duck_block_left.png"));// links
																																						// ducked
																																						// block
																																						// //Ducken
			p.getGraphics().add(ImageManagement.importImage("Images/charakter/" + Integer.toString(p.getPlayerNumber()) + "/drhc/duck_block_right.png"));// rechts
																																							// ducked
			p.getGraphics().add(ImageManagement.importImage("Images/charakter/" + Integer.toString(p.getPlayerNumber()) + "/llhc/Boxen_nach_oben.png"));
			p.getGraphics().add(ImageManagement.importImage("Images/charakter/" + Integer.toString(p.getPlayerNumber()) + "/rlhc/Boxen_nach_oben.png"));// block
			// and more

		}
		// Charakter.runSounds.add(SoundManagement.importSound("Sounds/Actions/run/01.wav"));
		// Charakter.runSounds.add(SoundManagement.importSound("Sounds/Actions/run/02.wav"));
	}

	public static final void createEnemyGraphics() {

		Enemy.images.add(ImageManagement.importImage("Images/charakter/enemy/llhc/01.png"));// links
																							// laufen
																							// //Stehen
		Enemy.images.add(ImageManagement.importImage("Images/charakter/enemy/llhc/02.png"));
		Enemy.images.add(ImageManagement.importImage("Images/charakter/enemy/llhc/03.png"));
		Enemy.images.add(ImageManagement.importImage("Images/charakter/enemy/llhc/04.png"));
		Enemy.images.add(ImageManagement.importImage("Images/charakter/enemy/llhc/05.png"));
		Enemy.images.add(ImageManagement.importImage("Images/charakter/enemy/llhc/06.png"));
		Enemy.images.add(ImageManagement.importImage("Images/charakter/enemy/llhc/07.png"));
		Enemy.images.add(ImageManagement.importImage("Images/charakter/enemy/llhc/08.png"));
		Enemy.images.add(ImageManagement.importImage("Images/charakter/enemy/llhc/09.png"));
		Enemy.images.add(ImageManagement.importImage("Images/charakter/enemy/rlhc/01.png"));// rechts
																							// laufen
		Enemy.images.add(ImageManagement.importImage("Images/charakter/enemy/rlhc/02.png"));
		Enemy.images.add(ImageManagement.importImage("Images/charakter/enemy/rlhc/03.png"));
		Enemy.images.add(ImageManagement.importImage("Images/charakter/enemy/rlhc/04.png"));
		Enemy.images.add(ImageManagement.importImage("Images/charakter/enemy/rlhc/05.png"));
		Enemy.images.add(ImageManagement.importImage("Images/charakter/enemy/rlhc/06.png"));
		Enemy.images.add(ImageManagement.importImage("Images/charakter/enemy/rlhc/07.png"));
		Enemy.images.add(ImageManagement.importImage("Images/charakter/enemy/rlhc/08.png"));
		Enemy.images.add(ImageManagement.importImage("Images/charakter/enemy/rlhc/09.png"));
		Enemy.images.add(ImageManagement.importImage("Images/charakter/enemy/llhc/10.png"));// links
																							// idle
		Enemy.images.add(ImageManagement.importImage("Images/charakter/enemy/rlhc/10.png"));// rechts
																							// idle
		Enemy.images.add(ImageManagement.importImage("Images/charakter/enemy/dlhc/duck_idle.png"));// links
																									// idle
																									// //Ducken
		Enemy.images.add(ImageManagement.importImage("Images/charakter/enemy/drhc/duck_idle.png"));// rechts
																									// idle
		Enemy.images.add(ImageManagement.importImage("Images/charakter/enemy/kick/left/kick_left.png"));
		Enemy.images.add(ImageManagement.importImage("Images/charakter/enemy/kick/right/kick_right.png"));
		Enemy.images.add(ImageManagement.importImage("Images/charakter/enemy/punch/left/Boxen.png"));
		Enemy.images.add(ImageManagement.importImage("Images/charakter/enemy/punch/right/Boxen.png"));
		Enemy.images.add(ImageManagement.importImage("Images/charakter/enemy/punch/left/bazooka.png"));
		Enemy.images.add(ImageManagement.importImage("Images/charakter/enemy/punch/left/ducked_bazooka.png"));
		Enemy.images.add(ImageManagement.importImage("Images/charakter/enemy/punch/right/bazooka.png"));
		Enemy.images.add(ImageManagement.importImage("Images/charakter/enemy/punch/right/ducked_bazooka.png"));
		Enemy.images.add(ImageManagement.importImage("Images/charakter/enemy/punch/left/ducked_punch_left.png"));
		Enemy.images.add(ImageManagement.importImage("Images/charakter/enemy/punch/right/ducked_punch_right.png"));
		Enemy.images.add(ImageManagement.importImage("Images/charakter/enemy/kick/left/ducked_kick_left.png"));
		Enemy.images.add(ImageManagement.importImage("Images/charakter/enemy/kick/right/ducked_kick_right.png"));
		Enemy.images.add(ImageManagement.importImage("Images/charakter/enemy/dlhc/duck_block_left.png"));
		Enemy.images.add(ImageManagement.importImage("Images/charakter/enemy/drhc/duck_block_right.png"));
		Enemy.images.add(ImageManagement.importImage("Images/charakter/enemy/llhc/Boxen_nach_oben.png"));
		Enemy.images.add(ImageManagement.importImage("Images/charakter/enemy/rlhc/Boxen_nach_oben.png"));
	}

	public static final void createItemGraphics() {
		PowerGlove.images.add(ImageManagement.importImage("Images/items/PowerGloves.png"));
		PowerGlove.images.add(ImageManagement.importImage("Images/items/PowerGloves_icon.png"));
		Healthpack.images.add(ImageManagement.importImage("Images/items/Healthpack.png"));
		Healthpack.images.add(ImageManagement.importImage("Images/items/Healthpack_icon.png"));
		LeerItem.images.add(ImageManagement.importImage("Images/items/LeerItem_icon.png"));
		LeerItem.images.add(ImageManagement.importImage("Images/items/LeerItem_icon.png"));
		BouncyBoots.images.add(ImageManagement.importImage("Images/items/BouncyBoots.png"));
		BouncyBoots.images.add(ImageManagement.importImage("Images/items/BouncyBoots_icon.png"));
		SpeedyBoots.images.add(ImageManagement.importImage("Images/items/SpeedyBoots.png"));
		SpeedyBoots.images.add(ImageManagement.importImage("Images/items/SpeedyBoots_icon.png"));
		Bazooka.images.add(ImageManagement.importImage("Images/items/Bazooka.png"));
		Bazooka.images.add(ImageManagement.importImage("Images/items/Bazooka_icon.png"));
	}

	public static final void createProjectileGraphics() {
		Rocket.images.add(ImageManagement.importImage("Images/items/Rocket/left.png"));
		Rocket.images.add(ImageManagement.importImage("Images/items/Rocket/right.png"));
		Rocket.images.add(ImageManagement.importImage("Images/items/Rocket/explosion.png"));
	}
}

final class DefeatScene implements SceneChanger {

	@Override
	public Scene nextScene() {
		return new EndOfGame("/Sounds/LoseSound.wav", "Images/Fight_Club_Game_Over.png");
	}
}

final class MenuScene implements SceneChanger {

	@Override
	public Scene nextScene() {
		return CreateScene.createHighscore();
	}

}

final class ToMenu implements SceneChanger {
	@Override
	public Scene nextScene() {
		return CreateScene.createMenu();
	}
}
