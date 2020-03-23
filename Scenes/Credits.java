package Scenes;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import main.CreateScene;
import main.Main;

@SuppressWarnings("serial")
public final class Credits extends Scene {
	
	private final Rectangle returnToMenuBounds = new Rectangle((int) (765 * Main.rescaleConstant), (int) (10 * Main.rescaleConstant), 
			(int) (220 * Main.rescaleConstant), (int) (35 * Main.rescaleConstant));

	public Credits(String soundFile, String imageFile) {
		super(soundFile, imageFile, "Other/BLOODY.ttf");
		
		MouseAdapter mouseHandler = new MouseAdapter() {
            @Override
            public final void mouseMoved(MouseEvent e) {
                if (getButtonBounds(returnToMenuBounds).contains(e.getPoint())) {
                    selectedBounds = getButtonBounds(returnToMenuBounds);
                } else {
                    selectedBounds = null;
                }
                repaint();
            }
            
            @Override
            public final void mouseClicked(MouseEvent e) {
                if (getButtonBounds(returnToMenuBounds).contains(e.getPoint())) {
                    Main.MAIN.changeScene(CreateScene.createMenu());
                }
            }
        };
        addMouseListener(mouseHandler);
        addMouseMotionListener(mouseHandler);
	}
	
	public final void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D) g.create();
		
		drawTextButton(g2d, "return to menu", getButtonBounds(returnToMenuBounds));
		
		g2d.dispose();
	}

}
