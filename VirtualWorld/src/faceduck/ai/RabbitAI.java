package faceduck.ai;

import faceduck.commands.MoveCommand;
import faceduck.skeleton.interfaces.*;
import faceduck.skeleton.util.Direction;
import faceduck.skeleton.util.Location;

import java.util.Random;


/**
 * The AI for a Rabbit. Rabbit has crazy desire of breeding.
 * If rabbit has enough energy to breed, it breed.
 * Otherwise, this AI seek out grass to eat.
 * If there are no visible grass, it do nothing.
 */
public class RabbitAI extends CommonAnimalAI {

	private static final double BREED_PROBABILITY = 0.1;

	/**
	 * Get breed probability.
	 *
	 * @return Value of Double
	 */
	@Override
	protected double getBreedProbability() {
		return BREED_PROBABILITY;
	}

	/**
	 * Act function of Rabbit. Same with CommonAI's logic
	 *
	 * @param world
	 *            The world to inspect.
	 * @param actor
	 *            The actor to consider.
	 *
	 * @return Command to execute.
	 */
	@Override
	public Command act(World world, Actor actor) {
		Command baseCommand = super.act(world, actor);

		if (baseCommand != null)
			return baseCommand;

		// If CommonAnimalAI's decision value is to do nothing, move randomly instead of it.
		Random rand = new Random();
		Direction newDir;
		Location newLoc;

		do {
			newDir = Direction.values()[rand.nextInt(4)];
			newLoc = new Location(world.getLocation(actor), newDir);
		} while (!world.isValidLocation(newLoc));

		return new MoveCommand(newDir);
	}
}


