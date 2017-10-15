package faceduck.skeleton.interfaces;

/**
 * A command represents an action to be executed on an {@link Actor} in the
 * world.
 */
public interface Command {

	/**
	 * Executes an action on this {@link Actor} inside the specified world.
	 *
	 * @param world
	 *            The world containing the actor.
	 * @param actor
	 *            The actor on which the command is executed.
	 *
	 * @throws NullPointerException
	 *             If actor or world are null.
	 */
	public void execute(World world, Actor actor);
}
