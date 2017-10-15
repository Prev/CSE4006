package faceduck.skeleton.interfaces;

/**
 * Represents an AI for an {@link Actor}.
 */
public interface AI {

	/**
	 * Looks at a view of the world local decides how to act. The
	 * {@link Command} should <i>not</i> be executed by the AI--that is the job
	 * of the client of the AI.
	 *
	 * @param world
	 *            The world to inspect.
	 * @param actor
	 *            The actor to consider.
	 *
	 * @throws NullPointerException
	 *             If actor or world are null.
	 *
	 * @return The command to be executed. The returned command may be null.
	 */
	public Command act(World world, Actor actor);
}
