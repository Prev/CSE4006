package faceduck.skeleton.interfaces;

/**
 * A participant in the world.
 */
public interface Actor {

	/**
	 * Allows the actor to choose and execute an action affecting the world.
	 *
	 * @param world
	 *            The world that the actor is currently in.
	 *
	 * @throws NullPointerException
	 *             If world is null.
	 */
	public void act(World world);

	/**
	 * How far can this actor see?
	 *
	 * @return An integer which represents the number of cells the actor can see
	 *         around themselves.
	 */
	public int getViewRange();

	/**
	 * How frequently can this actor act?
	 *
	 * @return An integer representing the number of steps this actor must wait
	 *         before acting again.
	 */
	public int getCoolDown();
}
