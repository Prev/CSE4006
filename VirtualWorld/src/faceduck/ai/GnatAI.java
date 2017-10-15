package faceduck.ai;

import faceduck.commands.MoveCommand;
import faceduck.skeleton.interfaces.AI;
import faceduck.skeleton.interfaces.Actor;
import faceduck.skeleton.interfaces.Command;
import faceduck.skeleton.interfaces.World;
import faceduck.skeleton.util.Direction;
import faceduck.skeleton.util.Location;

import java.util.Random;

/**
 * The AI for a Gnat. This AI will pick a random direction and then return a
 * command which moves in that direction.
 */
public class GnatAI implements AI {

	/**
	 * GnatAI is dumb. It disregards its surroundings and simply tells the Gnat
	 * to move in a random direction.
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
		if (actor == null)
			throw new NullPointerException("Actor cannot be null");
		else if (world == null)
			throw new NullPointerException("World cannot be null");


		Random rand = new Random();
		Direction[] directions = { Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST };

		Direction newDir;
		Location newLoc;

		do {
			newDir = directions[rand.nextInt(4)];
			newLoc = new Location(world.getLocation(actor), newDir);
		} while (!world.isValidLocation(newLoc));

		return new MoveCommand(newDir);
	}
}
