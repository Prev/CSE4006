package faceduck.utils;

import faceduck.skeleton.util.Direction;
import faceduck.skeleton.util.Location;

/**
 * Utils of Location class
 */
public class LocationUtil {

	/**
	 * Get adjacent locations of some location.
	 *
	 * @param loc
	 * 			Location of base.
	 *
	 * @return Array of Location {Northern, Southern, Eastern, Western}.
	 */
	public static Location[] adjacentLocations(Location loc) {
		return new Location[]{
				new Location(loc, Direction.NORTH),
				new Location(loc, Direction.SOUTH),
				new Location(loc, Direction.EAST),
				new Location(loc, Direction.WEST)
		};
	}

}
