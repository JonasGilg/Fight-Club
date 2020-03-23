package FileManagement;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public final class ImageManagement {
	
	public static final BufferedImage importImage(String imageFile) {
		try {
			return ImageIO.read(new File(imageFile));
		} catch(IOException e) {
			//e.printStackTrace();
			return null;
		}
	}
	
	public static final BufferedImage scaleImage(BufferedImage img, Rectangle r) {
		try{
			//if(img.getHeight()<r.getHeight()) r.height=img.getHeight();
			//if(img.getWidth()<r.getWidth()) r.width=img.getWidth();
		return toBufferedImage(img.getSubimage(0, 0, r.width, r.height));
		} catch (Exception e) {
			//e.printStackTrace();
			return rescaleImage(img, new Dimension(r.width, r.height));
		}
	}
	
	public static final BufferedImage rescaleImage(BufferedImage img, Dimension d) {
		try {
			return toBufferedImage(img.getScaledInstance(d.width, d.height, Image.SCALE_DEFAULT));
		} catch(Exception e) {
			e.printStackTrace();
			return img;
		}
	}
	
	private static final BufferedImage toBufferedImage(Image img) {
	    if (img instanceof BufferedImage) {
	        return (BufferedImage) img;
	    }

	    BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

	    Graphics2D bGr = bimage.createGraphics();
	    bGr.drawImage(img, 0, 0, null);
	    bGr.dispose();

	    return bimage;
	}
}
