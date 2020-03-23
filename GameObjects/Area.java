package GameObjects;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import Scenes.Level;

public abstract class Area extends GameObject{

	public Area(Point pPosition, Dimension pDimension, BufferedImage pImg, Level level) {
		super(new Rectangle(pPosition,pDimension), level);
		super.graphic.add(pImg);
	}

}
