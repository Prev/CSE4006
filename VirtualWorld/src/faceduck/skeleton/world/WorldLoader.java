package faceduck.skeleton.world;

import faceduck.actors.*;
import faceduck.skeleton.interfaces.Actor;
import faceduck.skeleton.interfaces.World;
import faceduck.skeleton.util.Location;
import faceduck.skeleton.util.Util;

/**
 * This class is for initializing your world. Here you get to decide how many of
 * your objects you want to add to your world in the beginning.
 */
public class WorldLoader {

	private World world;
	private int numGrass;
	private int numGnats;
	private int numRabbits;
	private int numFoxes;

	private int numWormWithGrass;

	private static final double WORM_POLLUTION_PROBABILITY = 0.05;

	public WorldLoader(World w) {
		this.world = w;
		this.numGrass = world.getHeight() * world.getWidth() / 7;
		this.numGnats = numGrass / 4;
		this.numRabbits = numGrass / 4;
		this.numFoxes = numRabbits / 8;

		this.numWormWithGrass = (int)(this.numGrass * WORM_POLLUTION_PROBABILITY);
		this.numGrass = (int)(this.numGrass * (1 - WORM_POLLUTION_PROBABILITY));
	}

	/**
	 * Populates the world with the initial {@link Actor}s.
	 */
	public void initializeWorld() {
		addGrass();
		addGnats();
		addGardener();
		addRabbit();
		addFox();
		addWormWithGrass();
	}

	private void addGrass() {
		for (int i = 0; i < numGrass; i++) {
			Location loc = Util.randomEmptyLoc(world);
			world.add(new Grass(), loc);
		}
	}

	private void addWormWithGrass() {
		for (int i = 0; i < numWormWithGrass; i++) {
			Location loc = Util.randomEmptyLoc(world);
			world.add(new WormWithGrass(), loc);
		}
	}

	private void addGnats() {
		for (int i = 0; i < numGnats; i++) {
			Location loc = Util.randomEmptyLoc(world);
			world.add(new Gnat(), loc);
		}
	}

	private void addGardener() {
		Location loc = Util.randomEmptyLoc(world);
		world.add(new Gardener(), loc);
	}

	private void addFox() {
		for (int i = 0; i < numFoxes; i++) {
			Location loc = Util.randomEmptyLoc(world);
			if (loc != null) {
				// If the world isn't full
				world.add(new FoxImpl(), loc);
			}
		}
	}

	private void addRabbit() {
		for (int i = 0; i < numRabbits; i++) {
			Location loc = Util.randomEmptyLoc(world);
			if (loc != null) {
				// If the world isn't full
				world.add(new RabbitImpl(), loc);
			}
		}
	}
}
