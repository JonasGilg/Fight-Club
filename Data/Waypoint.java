package Data;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

import main.Main;

public final class Waypoint {
	public final Point p;
	public final String name;
	public final ArrayList<Waypoint> connectedPoints;
	public Color color;
	
	public Waypoint(Point p, String name) {
		this.p = p;
		this.name = name;
		connectedPoints = new ArrayList<Waypoint>();
		color = Color.CYAN;
	}
	
	public final void draw(Graphics2D g2d) {
		g2d.setColor(color);
		g2d.draw(new Rectangle((int) ((p.x - 5) * Main.rescaleConstant), (int) ((p.y - 5) * Main.rescaleConstant), (int) (10 * Main.rescaleConstant), (int) (10 * Main.rescaleConstant)));
		g2d.drawString(name, (int) ((p.x + 6) * Main.rescaleConstant), (int) ((p.y - 6) * Main.rescaleConstant));
		for(int i = 0; i < connectedPoints.size(); i++) {
			g2d.drawLine((int) (p.x * Main.rescaleConstant), (int) (p.y * Main.rescaleConstant), (int) (connectedPoints.get(i).p.x * Main.rescaleConstant), (int) (connectedPoints.get(i).p.y * Main.rescaleConstant));
		}
		color = Color.CYAN;
	}
	
	@Override
	public final String toString() {
		return name;
	}
}
