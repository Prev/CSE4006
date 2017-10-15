package faceduck.skeleton.util;

import static faceduck.skeleton.util.Direction.EAST;
import static faceduck.skeleton.util.Direction.NORTH;
import static faceduck.skeleton.util.Direction.SOUTH;
import static faceduck.skeleton.util.Direction.WEST;

import java.util.Random;

import faceduck.skeleton.interfaces.World;

/**
 * Useful utilities, currently focused on randomization. We use a fixed seed so
 * that simulations are repeatable.
 */
public class Util {

	private static final int NUM_DIRECTIONS = Direction.values().length;
	private static final Random rand = new Random(2013);

	/**
	 * Returns a random Direction.
	 */
	public static Direction randomDir() {
		switch (rand.nextInt(NUM_DIRECTIONS)) {
		case 0:
			return NORTH;
		case 1:
			return SOUTH;
		case 2:
			return EAST;
		case 3:
			return WEST;
		}
		throw new RuntimeException("Impossible to get here.");
	}

	/**
	 * Finds a random empty location in world.
	 *
	 * @param world
	 *            The world to search.
	 *
	 * @return A random location, or null if the world is full.
	 */
	public static Location randomEmptyLoc(World world) {
		return randomEmptyLoc(world, 0, 0, world.getWidth(), world.getHeight());
	}

	/**
	 * Finds a random empty location in the world within a bounding box.
	 *
	 * @return A random location, or null if the world is full.
	 */
	public static Location randomEmptyLoc(World w, int startW, int startH,
			int width, int height) {
		int x = rand.nextInt(width - startW) + startW;
		int y = rand.nextInt(height - startH) + startH;

		// first position is random, if full searches sequentially
		for (int i = startW; i < width; ++i) {
			x = ((x + 1) % (width - startW)) + startW;
			for (int j = startH; j < height; ++j) {
				y = ((y + 1) % (height - startH)) + startH;
				Location loc = new Location(x, y);
				if (w.getThing(loc) == null) {
					return loc;
				}
			}
		}
		// no position free
		return null;
	}
}
