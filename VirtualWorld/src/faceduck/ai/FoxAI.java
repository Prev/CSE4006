package faceduck.ai;

import faceduck.commands.MoveCommand;
import faceduck.skeleton.interfaces.*;
import faceduck.skeleton.util.Location;
import faceduck.skeleton.util.Util;


/**
 * The AI for a Fox. This AI seek out rabbits to eat.
 * If there are no visible rabbits, it moves randomly. If fox has enough energy, it sometime breed.
 */
public class FoxAI extends CommonAnimalAI {

	private static final double BREED_PROBABILITY = 0.05;

	private Location destination;


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
	 * Act function of Fox.
	 * Basely use common animal AI, but in last case, it move randomly instead of doing nothing.
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

		// If CommonAnimalAI's decision value is to do nothing, move to destination
		if (this.destination == null) {
			this.destination = Util.randomEmptyLoc(world);
		}

		if (this.destination.distanceTo(world.getLocation(actor)) == 0) {
			// Arrived at destination
			this.destination = null;
			return null;
		}

		return new MoveCommand(world.getLocation(actor).dirTo(this.destination));
	}
}
