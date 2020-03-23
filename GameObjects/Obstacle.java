package GameObjects;

import java.awt.Dimension;
import java.awt.Point;

import FileManagement.ImageManagement;
import Scenes.Level;

public final class Obstacle extends Area {
	
	
	public Obstacle(Point pPosition, Dimension pDimension, Level level,String imagepath) {
		super(pPosition,pDimension,ImageManagement.importImage(imagepath), level);
		graphic.add(0, ImageManagement.scaleImage(graphic.get(0), getHitbox()));
		
	}
}
