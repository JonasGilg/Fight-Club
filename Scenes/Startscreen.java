package Scenes;

import java.awt.Graphics;

import Data.Scoredata;

import main.CreateScene;
import main.Main;

@SuppressWarnings("serial")
public final class Startscreen extends Scene {

	public Startscreen(String soundFile, String imageFile) {
		super(soundFile, imageFile, "");
		
		final class WaitThread extends Thread {
			@Override
			public final void run() {
				Scoredata.laden();
				try {
					sleep(2500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Main.MAIN.changeScene(CreateScene.createMenu());
			}
		}
		WaitThread wait = new WaitThread();
		wait.start();
	}
	
	public final void paintComponent(Graphics g) {
		super.paintComponent(g);
	}
}
