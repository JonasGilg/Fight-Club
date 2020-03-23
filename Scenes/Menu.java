package Scenes;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import main.CreateScene;
import main.Main;

@SuppressWarnings("serial")
public final class Menu extends Scene {
	
	public GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
	public final int width = gd.getDisplayMode().getWidth();
	public final int height = gd.getDisplayMode().getHeight();
	
	
	//Aufl�sungsvariablen
	
	private final int differx=(int)(Main.NATIVE_RESOLUTION.getWidth()*0.022);
	private final int differy=(int)(Main.NATIVE_RESOLUTION.getHeight()*0.130);
	
	
	private final Rectangle startGameBounds = new Rectangle((int) (2 * differx * Main.rescaleConstant), (int) (differy * Main.rescaleConstant), (int) (262 * Main.rescaleConstant), (int) (85 * Main.rescaleConstant));
	private final Rectangle tutorialBounds = new Rectangle((int) (3 * differx * Main.rescaleConstant), (int) (2 * differy * Main.rescaleConstant), (int) (262 * Main.rescaleConstant), (int) (85 * Main.rescaleConstant));
	private final Rectangle settingsBounds = new Rectangle((int) (4 * differx * Main.rescaleConstant), (int) (3 * differy * Main.rescaleConstant), (int) (262 * Main.rescaleConstant), (int) (85 * Main.rescaleConstant));
	private final Rectangle highscoreBounds = new Rectangle((int) (5 * differx * Main.rescaleConstant), (int) (4 * differy * Main.rescaleConstant), (int) (262 * Main.rescaleConstant), (int) (85 * Main.rescaleConstant));
	private final Rectangle creditsBounds = new Rectangle((int) (6 * differx * Main.rescaleConstant), (int) (5 * differy * Main.rescaleConstant), (int) (262 * Main.rescaleConstant), (int) (85 * Main.rescaleConstant));
	private final Rectangle exitGameBounds = new Rectangle((int) (7 * differx * Main.rescaleConstant), (int) (6 * differy * Main.rescaleConstant), (int) (262 * Main.rescaleConstant), (int) (85 * Main.rescaleConstant));

	public Menu(String soundFile, String imageFile) {
		super(soundFile, imageFile, "Other/BLOODY.ttf");
		gd=null;
		
		
		MouseAdapter mouseHandler = new MouseAdapter() {
            @Override
            public final void mouseMoved(MouseEvent e) {
                if (getButtonBounds(startGameBounds).contains(e.getPoint())) {
                    selectedBounds = getButtonBounds(startGameBounds);
                } else if (getButtonBounds(tutorialBounds).contains(e.getPoint())) {
                        selectedBounds = getButtonBounds(tutorialBounds);
                } else if (getButtonBounds(settingsBounds).contains(e.getPoint())) {
                    selectedBounds = getButtonBounds(settingsBounds);
                } else if (getButtonBounds(highscoreBounds).contains(e.getPoint())) {
                	selectedBounds = getButtonBounds(highscoreBounds);
                } else if (getButtonBounds(creditsBounds).contains(e.getPoint())) {
                    selectedBounds = getButtonBounds(creditsBounds);
                } else if (getButtonBounds(exitGameBounds).contains(e.getPoint())) {
                    selectedBounds = getButtonBounds(exitGameBounds);
                } else {
                    selectedBounds = null;
                }
            }

			@Override
            public final void mouseClicked(MouseEvent e) {
                if (getButtonBounds(startGameBounds).contains(e.getPoint())) {
                	Main.MAIN.removeAllListeners();
                	if(Settings.playerModebit==0){
                	Main.MAIN.changeScene(CreateScene.createLevelOne());
                	}
                	else{
                		Main.MAIN.changeScene(CreateScene.createMultiplayerLevel());
                	}
                } else if (getButtonBounds(tutorialBounds).contains(e.getPoint())) {
                	Main.MAIN.removeAllListeners();
                	Main.MAIN.changeScene(CreateScene.createTutorial(Main.MAIN));
                } else if (getButtonBounds(settingsBounds).contains(e.getPoint())) {
                	Main.MAIN.removeAllListeners();
                	Main.MAIN.changeScene( CreateScene.createSettings());
                } else if (getButtonBounds(highscoreBounds).contains(e.getPoint())) {
                	Main.MAIN.removeAllListeners();
                	Main.MAIN.changeScene(CreateScene.createHighscore());
                } else if (getButtonBounds(creditsBounds).contains(e.getPoint())) {
                	Main.MAIN.removeAllListeners();
                	Main.MAIN.changeScene(CreateScene.createCredits());
                } else if (getButtonBounds(exitGameBounds).contains(e.getPoint())) {
                	Main.MAIN.removeAllListeners();
                    System.exit(0);
                }
            }
        };
        addMouseListener(mouseHandler);
        addMouseMotionListener(mouseHandler);
	}

	public final void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D) g.create();
		
		drawTextButton(g2d, "Start Game", getButtonBounds(startGameBounds));
		drawTextButton(g2d, "Tutorial", getButtonBounds(tutorialBounds));
		drawTextButton(g2d, "Settings", getButtonBounds(settingsBounds));
		drawTextButton(g2d, "Highscore", getButtonBounds(highscoreBounds));
		drawTextButton(g2d, "Credits", getButtonBounds(creditsBounds));
		drawTextButton(g2d, "Exit Game", getButtonBounds(exitGameBounds));
		
		g2d.dispose();
	}
}