package main;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

import javax.swing.JFrame;

import Scenes.Scene;

@SuppressWarnings("serial")
public final class Main extends JFrame {

	public static double rescaleConstant;

	private static final int REFRESH_RATE = 1000 / 60; // ~60 FPS
	public static final int WIDTH = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getWidth();
	public static final int HEIGHT = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getHeight();
	public static final Main MAIN = new Main();
	public static final Dimension NATIVE_RESOLUTION = new Dimension(1920, 1080);

	public Scene currentScene;

	public Main() {

		// CreateScene.createGraphics();
		currentScene = CreateScene.createStartscreen();
		add(currentScene);
		refresh();
	}

	public final void changeScene(Scene newScene) {
		currentScene.backgroundSound.close();
		remove(currentScene);
		add(newScene);
		currentScene = newScene;
		setVisible(true);
	}

	protected final void refresh() {
		class RefreshThread extends Thread {

			@Override
			public void run() {
				while (true) {
					try {
						sleep(REFRESH_RATE);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					currentScene.repaint();
				}
			}
		}

		RefreshThread refresh = new RefreshThread();
		refresh.start();
	}

	public final void removeAllListeners() {
		KeyListener[] kl = this.getKeyListeners();
		for (int i = 0; i < kl.length; i++) {
			removeKeyListener(kl[i]);
		}

		MouseListener[] ml = this.getMouseListeners();
		for (int i = 0; i < ml.length; i++) {
			removeMouseListener(ml[i]);
		}
	}

	public static final void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				rescaleConstant = WIDTH / NATIVE_RESOLUTION.getWidth();

				MAIN.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				MAIN.setUndecorated(true);
				MAIN.setResizable(false);
				MAIN.setSize(new Dimension(WIDTH, HEIGHT));
				if(System.getProperty("os.name").equals("Linux")) {
					MAIN.setLocation(0, 25);
				}
				MAIN.setVisible(true);
			}
		});
	}
}
