package GameObjects;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public final class LeerItem extends Item {
	public static final ArrayList<BufferedImage> images = new ArrayList<>();	
	
	public LeerItem(Scenes.Level lvl) {
		super(new Rectangle(0,0,0,0), images, 0, lvl);
	}
	@Override
	public final String getActiveTimeAsString(){    //Output as String for HUDItem
	
		return"";
	}

}
