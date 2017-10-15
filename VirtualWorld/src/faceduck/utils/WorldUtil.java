package faceduck.utils;

import faceduck.skeleton.interfaces.Actor;
import faceduck.skeleton.interfaces.World;
import faceduck.skeleton.util.Direction;
import faceduck.skeleton.util.Location;

import java.util.ArrayList;

/**
 * Utils of virtual world
 */
public class WorldUtil {

	/**
	 * Get visible objects of actor with `viewRange`
	 *
	 * @param world
	 *            The world to inspect.
	 * @param actor
	 *            The actor to consider.
	 *
	 * @return 2d array of object with k x k size. (k is viewRange)
	 */
	public static Object[][] getVisibleObjects(World world, Actor actor) {
		Location currentLoc = world.getLocation(actor);
		int k = actor.getViewRange();

		// Init 2d array to return
		Object[][] ret = new Object[k * 2 + 1][k * 2 + 1];

		// Square defined by the corners
		// 		(i − k, j − k)
		// 		(i + k, j − k)
		// 		(i + k, j + k)
		// 		(i − k, j + k)
		for (int i = -k; i <= k; i++) {
			for (int j = -k; j <= k; j++) {
				// Assign new variable fo new location
				Location newLoc = new Location(currentLoc.getX() + i, currentLoc.getY() + j);

				if (world.isValidLocation(newLoc))
					// if location is valid, add to `ret` variable.
					ret[i + k][j + k] = world.getThing(newLoc);
			}
		}

		return ret;
	}

	/**
	 * Get adjacent objects of actor
	 *
	 * @param world
	 *            The world to inspect.
	 * @param actor
	 *            The actor to consider.
	 *
	 * @return Array of Object {Northern, Southern, Eastern, Western}.
	 */
	public static Object[] getAdjacentObjects(World world, Actor actor) {
		Location loc = world.getLocation(actor);
		Location[] adjacentLocations = LocationUtil.adjacentLocations(loc);

		Object[] ret = new Object[4];

		// Check 4 locations and getObject in world
		for (int i = 0; i < 4; i++) {
			if (world.isValidLocation(adjacentLocations[i]))
				// if location is valid, add to `ret` variable.
				ret[i] = world.getThing(adjacentLocations[i]);
		}

		return ret;
	}

	/**
	 * Get list of adjacent objects which is not null
	 * @param world
	 *            The world to inspect.
	 * @param actor
	 *            The actor to consider.
	 *
	 * @return ArrayList of Object which is not null
	 */
	public static ArrayList<Object> getAdjacentObjectList(World world, Actor actor) {
		// Using `getAdjacentObjects` function to get objects, and manufacturing it.
		Object[] objects = getAdjacentObjects(world, actor);
		ArrayList<Object> ret = new ArrayList<>();

		for (Object o: objects)
			if (o != null)
				ret.add(o);

		return ret;
	}

}
