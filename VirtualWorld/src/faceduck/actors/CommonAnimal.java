package faceduck.actors;

import faceduck.skeleton.interfaces.*;
import faceduck.skeleton.util.Direction;
import faceduck.skeleton.util.Location;

/**
 * Common Animal of VirtualWorld.
 * It has energy, and it is used to act in the world; every time an Animal acts, it loses one unit of energy
 * Animal can eat something, move to adjacent place, or breed child.
 *
 * This class implements utils acts of animal(eat, move, breed).
 */
public abstract class CommonAnimal implements Animal {


	// Common value of consuming energy per each step
	private static final int CONSUMING_ENERGY = 1;


	// Current energy of this animal
	protected int energy;


	/**
	 * Returns possibility of eating this kind of object.
	 *
	 * @param prey
	 * 			   object to eat.
	 * @return true if animal can eat prey, false otherwise.
	 *
	 */
	abstract public boolean edibleWith(Edible prey);


	/**
	 * Act step with custom AI. Call and execute AI with consuming energy.
	 *    Finally check current energy and execute dying if energy is less than 0.
	 *
	 * @param world
	 * 			  The world that the actor is currently in.
	 * @param ai
	 * 			  AI instance to make decision. This function calls and execute AI instance
	 *
	 * @throws NullPointerException
	 *             If world is null.
	 */
	protected void actWithAI(World world, AI ai) {
		if (world == null) {
			throw new NullPointerException("World must not be null.");
		}

		Command cmd = ai.act(world, this);

		if (cmd != null)
			cmd.execute(world, this);

		this.setEnergy(
				world,
				this.getEnergy() - this.getConsumingEnergy()
		);
	}

	/**
	 * Get consuming energy of each step.
	 *
	 * @return Value of consuming energy of each step
	 */
	protected int getConsumingEnergy() {
		return CONSUMING_ENERGY;
	}


	/**
	 * Get current energy.
	 *
	 * @return int value
	 */
	@Override
	public int getEnergy() {
		return this.energy;
	}


	/**
	 * Set energy with exeption handling.
	 *
	 * @param world
	 * 			  The world that the actor is currently in.
	 *
	 * @param energy
	 * 			  Value of new energy.
	 */
	protected void setEnergy(World world, int energy) {
		this.energy = energy;

		if (this.energy > this.getMaxEnergy())
			// Guarantee max energy
			this.energy = this.getMaxEnergy();

		if (this.energy < 0)
			// Die if energy is less than 0
			world.remove(this);

	}


	/**
	 * Consumes an Actor located at the space adjacent to this Animal in the
	 * specified direction.
	 *
	 * @param world
	 *            The world containing this actor.
	 * @param dir
	 *            The direction of the Actor to eat.
	 *
	 * @throws NullPointerException
	 *             If world or dir are null.
	 *
	 * @throws IllegalArgumentException
	 *             If dir is illegal (Object not exists in that place or animal cannot eat that object)
	 */
	@Override
	public void eat(World world, Direction dir) {
		if (world == null) {
			throw new NullPointerException("World cannot be null");
		}
		if (dir == null) {
			throw new NullPointerException("Direction cannot be null");
		}

		Location nextLocation = new Location(world.getLocation(this), dir);
		Object preyCandidate = world.getThing(nextLocation);


		if (preyCandidate == null)
			throw new IllegalArgumentException("Can not eat object: Object do not exists in that place");

		else if (!(preyCandidate instanceof Edible))
			throw new IllegalArgumentException("Can not eat object: Object is not instance of Edible");

		else if (!this.edibleWith((Edible) preyCandidate))
			throw new IllegalArgumentException("Can not eat object: animal cannot eat that kind of object");

		else {
			Edible prey = (Edible) preyCandidate;

			// Remove prey
			world.remove(prey);

			// Move object
			world.remove(this);
			world.add(this, nextLocation);

			// Gain energy
			this.setEnergy(world, this.energy + prey.getEnergyValue());
		}
	}

	/**
	 * Moves the Animal one space in the world in the specified direction.
	 *
	 * @param world
	 *            The world containing this actor.
	 * @param dir
	 *            The direction to move in.
	 *
	 * @throws NullPointerException
	 *             If world or dir are null.
	 *
	 */
	@Override
	public void move(World world, Direction dir) {
		if (world == null) {
			throw new NullPointerException("World cannot be null");
		}
		if (dir == null) {
			throw new NullPointerException("Direction cannot be null");
		}

		Location nextLocation = new Location(world.getLocation(this), dir);

		if (world.getThing(nextLocation) != null)
			// Ignore if already exists in that place
			return;

		else {
			world.remove(this);
			world.add(this, nextLocation);
		}
	}


	/**
	 * Breeds a new animal of the exact type as the current Animal. The new
	 * Animal will spawn in the square adjacent to this Animal in the specified
	 * direction. When an animal breeds, it transfers 50% of its energy into its
	 * offspring's energy.
	 *
	 * @param world
	 *            The world containing this actor.
	 * @param dir
	 *            The direction in which the new Animal will spawn.
	 *
	 * @throws NullPointerException
	 *             If world or dir are null.
	 *
	 * @throws IllegalArgumentException
	 *             If dir is illegal (Some object exists in that place)
	 */
	@Override
	public void breed(World world, Direction dir) {
		if (world == null) {
			throw new NullPointerException("World cannot be null");
		}
		if (dir == null) {
			throw new NullPointerException("Direction cannot be null");
		}

		Location newLocation = new Location(world.getLocation(this), dir);

		if (world.getThing(newLocation) != null)
			throw new IllegalArgumentException("Object already exists in that place");

		else {
			// Make clone of current instance
			CommonAnimal clone = null;
			try {
				clone = this.getClass().newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}

			this.setEnergy(world, this.energy / 2);
			clone.energy = this.energy;

			world.add(clone, newLocation);
		}
	}

}
