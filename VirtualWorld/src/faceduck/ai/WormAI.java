package faceduck.ai;

import faceduck.actors.Grass;
import faceduck.commands.MoveCommand;
import faceduck.skeleton.interfaces.AI;
import faceduck.skeleton.interfaces.Actor;
import faceduck.skeleton.interfaces.Command;
import faceduck.skeleton.interfaces.World;
import faceduck.utils.WorldUtil;


/**
 * The AI for a Worm (in Grass)
 *
 */
public class WormAI implements AI {

	/**
	 * Worm always want to move another adjacent grass.
	 * If there is adjacent grass, AI commands to move it.
	 *
	 * @param world
	 *            The world to inspect.
	 * @param actor
	 *            The actor to consider.
	 *
	 * @return Command to execute.
	 *
	 * @throws NullPointerException
	 *             If world or actor are null.
	 */
	@Override
	public Command act(World world, Actor actor) {
		if (world == null) {
			throw new NullPointerException("World must not be null.");
		}
		if (actor == null) {
			throw new NullPointerException("Actor must not be null.");
		}

		for (Object o: WorldUtil.getAdjacentObjectList(world, actor)) {
			if (o instanceof Grass) {
				return new MoveCommand(world.getLocation(actor).dirTo(world.getLocation(o)));
			}
		}

		return null;
	}
}
