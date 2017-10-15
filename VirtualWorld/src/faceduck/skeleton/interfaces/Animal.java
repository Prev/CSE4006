package faceduck.skeleton.interfaces;

import faceduck.skeleton.util.Direction;

/**
 * An Animal is an {@link Actor} which can eat, move, and/or breed.
 */
public interface Animal extends Actor {

	/**
	 * Returns the amount of energy that the {@link Animal} has remaining.
	 *
	 * @return An integer greater than 0 if alive; an integer less than or equal
	 *         to 0 otherwise if dead.
	 */
	public int getEnergy();

	/**
	 * Returns the max amount of energy this {@link Animal} can have.
	 *
	 * @return An integer representing the max energy an {@link Animal} can
	 *         have.
	 */
	public int getMaxEnergy();

	/**
	 * Returns the threshold of energy required to be able to breed.
	 *
	 * @return An integer representing the amount of energy required to breed.
	 */
	public int getBreedLimit();

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
	 */
	public void eat(World world, Direction dir);

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
	 */
	public void move(World world, Direction dir);

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
	 */
	public void breed(World world, Direction dir);
}
