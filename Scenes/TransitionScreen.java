package Scenes;

import java.awt.Graphics;

import main.Main;
import main.SceneChanger;

@SuppressWarnings("serial")
public final class TransitionScreen extends Scene {

	public TransitionScreen(String soundFile, String imageFile, final SceneChanger nextScene) {
		super(soundFile, imageFile, "");

		class WaitThread extends Thread {
			@Override
			public void run() {
				try {
					sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Main.MAIN.changeScene(nextScene.nextScene());
			}
		}
		WaitThread wait = new WaitThread();
		wait.start();
	}

	public final void paintComponent(Graphics g) {
		super.paintComponent(g);
	}

}
