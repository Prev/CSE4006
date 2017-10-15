package faceduck.actors;

import static faceduck.skeleton.util.Direction.EAST;
import static faceduck.skeleton.util.Direction.NORTH;
import static faceduck.skeleton.util.Direction.SOUTH;
import static faceduck.skeleton.util.Direction.WEST;
import faceduck.skeleton.interfaces.Actor;
import faceduck.skeleton.interfaces.World;
import faceduck.skeleton.util.Location;
import faceduck.skeleton.util.Util;

/**
 * A gardener is an {@link Actor} that doesn't move, eat or breed. A gardener
 * acts by placing grass in the world at random locations.
 */
public class Gardener implements Actor {

	private static final int GRASS_TO_ADD = 15;
	private static final double WORM_POLLUTION_PROBABILITY = 0.1;

	@Override
	public void act(World world) {
		if (world == null) {
			throw new NullPointerException("World must not be null.");
		}

		for (int i = 0; i < GRASS_TO_ADD; i++) {
			Location loc = Util.randomEmptyLoc(world);
			if (loc == null) {
				return; // world is full!
			}
			if (countGrassAround(world, loc) > 3) {
				return; // too crowded here
			}

			if (Math.random() <= WORM_POLLUTION_PROBABILITY)
				world.add(new WormWithGrass(), loc);
			else
				world.add(new Grass(), loc);
		}
	}

	@Override
	public int getViewRange() {
		return 0;
	}

	@Override
	public int getCoolDown() {
		return 0;
	}

	private static int countGrassAround(World world, Location loc) {
		return countGrassEastWest(world, loc)
				+ countGrassEastWest(world, new Location(loc, NORTH))
				+ countGrassEastWest(world, new Location(loc, SOUTH));
	}

	private static int countGrassEastWest(World world, Location loc) {
		return countGrass(world, loc)
				+ countGrass(world, new Location(loc, EAST))
				+ countGrass(world, new Location(loc, WEST));
	}

	private static int countGrass(World world, Location loc) {
		if (world.isValidLocation(loc) && world.getThing(loc) instanceof Grass) {
			return 1;
		}
		return 0;
	}
}
