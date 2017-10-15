package faceduck.skeleton.util;

import static faceduck.skeleton.util.Direction.EAST;
import static faceduck.skeleton.util.Direction.NORTH;
import static faceduck.skeleton.util.Direction.SOUTH;
import static faceduck.skeleton.util.Direction.WEST;

/**
 * Represents an (x,y) location.
 */
public final class Location {

	private final int x;
	private final int y;

	public Location(int newX, int newY) {
		this.x = newX;
		this.y = newY;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	/**
	 * Creates and returns a {@link Location} one space away from the specified
	 * {@link Location} in the given {@link Direction}.
	 *
	 * @param loc
	 *            The specified location.
	 * @param dir
	 *            The specified direction.
	 * @throws NullPointerException
	 *             If loc or dir are null.
	 * @return A new Location object one space away from loc in the specified
	 *         direction.
	 */
	public Location(Location loc, Direction dir) {
		if (loc == null) {
			throw new NullPointerException("Location cannot be null");
		} else if (dir == null) {
			throw new NullPointerException("Direction cannot be null");
		}

		switch (dir) {
		case NORTH:
			this.x = loc.x;
			this.y = loc.y - 1;
			return;
		case SOUTH:
			this.x = loc.x;
			this.y = loc.y + 1;
			return;
		case EAST:
			this.x = loc.x + 1;
			this.y = loc.y;
			return;
		case WEST:
			this.x = loc.x - 1;
			this.y = loc.y;
			return;
		}

		throw new RuntimeException("Impossible to get here.");
	}

	/**
	 * Returns the distance from this location to another location, assuming you
	 * have to travel on the grid.
	 *
	 * @param loc
	 *            The destination location.
	 * @throws NullPointerException
	 *             If loc is null.
	 * @returns The distance between this location and loc.
	 */
	public int distanceTo(Location loc) {
		if (loc == null) {
			throw new NullPointerException("Location cannot be null.");
		}
		return Math.abs(this.x - loc.x) + Math.abs(this.y - loc.y);
	}

	/**
	 * Returns the Direction from the current Location towards another Location.
	 *
	 * @param loc
	 *            The specified location.
	 * @throws NullPointerException
	 *             If loc is null.
	 * @return The direction you must go in order to get from this location to
	 *         loc.
	 */
	public Direction dirTo(Location loc) {
		if (loc == null) {
			throw new NullPointerException("Location cannot be null.");
		}

		int deltaX = loc.x - this.x;
		int deltaY = loc.y - this.y;

		if (Math.abs(deltaX) > Math.abs(deltaY)) {
			if (deltaX > 0) {
				return EAST;
			} else {
				return WEST;
			}
		} else {
			if (deltaY > 0) {
				return SOUTH;
			} else {
				return NORTH;
			}
		}
	}
}
