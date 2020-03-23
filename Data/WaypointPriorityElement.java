package Data;


public final class WaypointPriorityElement {
	
	public final Waypoint wp;
	public WaypointPriorityElement prevWaypoint;
	public double c;
	public double g;
	public double f;

	public WaypointPriorityElement(Waypoint wp, double c, double g, double f) {
		this.wp = wp;
		this.c = c;
		this.g = g;
		this.f = f;
	}
	
	@Override
	public final boolean equals(Object other) {
		if(other instanceof WaypointPriorityElement) {
			WaypointPriorityElement wpe = (WaypointPriorityElement) other;
			if(this.wp.equals(wpe.wp)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public final String toString() {
		return wp.toString();
	}
	
}
