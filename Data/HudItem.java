package Data;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

import main.Main;
import FileManagement.ImageManagement;
import GameObjects.Charakter;
import Scenes.Settings;

public final class HudItem {
	private Point position;
	private final Charakter c;
	private final double maxBreite;
	private final double maxHP;
	private double tmp=0;
	private static final BufferedImage lifeImg = ImageManagement.importImage("Images/items/Herz_icon.png");
	
	public HudItem(Point pos, Charakter c){
		position=pos;
		this.c=c;
		maxHP=c.getHP();
		if (Settings.playerModebit==0||Settings.playerModebit==1){
		maxBreite=((Main.MAIN.getBounds().width-100)/2);
		}else if (Settings.playerModebit==2){
			maxBreite=((Main.MAIN.getBounds().width-100)/3);
		}else 	maxBreite=((Main.MAIN.getBounds().width-100)/4);

		
		tmp = (maxBreite/maxHP)*c.getHP();
		
	}
	public final void setLocation(Point p){
		position= new Point(p);
	}
	public final void setLocation(int x, int y){
		position = new Point(x,y);
	}
	public final Point getLocation(){
		return position;
	}
	public final void draw(Graphics2D g2d){
		Color save = g2d.getColor();
		g2d.setColor(c.getPlayerColor());
		g2d.drawString(c.getCharakterName(),position.x,position.y+10);
		for(int i=0; i<c.getLives();i++){
		g2d.drawImage(lifeImg, (i*35)+position.x+80,position.y, null);
		}
		g2d.setColor(Color.red);
		g2d.fillRect(position.x, position.y+30,(int) maxBreite,15);
		if(c.getLives()>0){
		g2d.setColor(Color.green);
		tmp = Math.round((maxBreite/maxHP)*c.getHP());
		g2d.fillRect(position.x, position.y+30, (int)tmp ,15);
		g2d.drawImage(c.getHoldedItem().getGraphic().get(1),position.x,position.y+46,
				null);
		g2d.setColor(Color.white);
		g2d.drawString(c.getHoldedItem().getActiveTimeAsString(), position.x+c.getHoldedItem().getGraphic().get(1).getWidth(), position.y+65);
		}
		
		g2d.setColor(save);
	}
}
