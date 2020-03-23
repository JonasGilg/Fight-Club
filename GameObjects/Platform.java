package GameObjects;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;

import FileManagement.ImageManagement;
import Scenes.Level;

public final class Platform extends Area{
	
	private static final String imagePath = ""; //Pfad zur Bilddatei f�r Platformen
	
	public static Color drawColor = Color.BLACK;
	
	public Platform(Point pPosition, int breite, Level lvl){
		super(pPosition,new Dimension(breite,1),ImageManagement.importImage(imagePath), lvl);
	}
}
