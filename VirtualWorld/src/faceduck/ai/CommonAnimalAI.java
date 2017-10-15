package faceduck.ai;

import faceduck.actors.CommonAnimal;
import faceduck.commands.BreedCommand;
import faceduck.commands.EatCommand;
import faceduck.commands.MoveCommand;
import faceduck.skeleton.interfaces.*;
import faceduck.skeleton.util.Direction;
import faceduck.skeleton.util.Location;
import faceduck.utils.WorldUtil;

import java.util.ArrayList;

/**
 * AI  of Common Animal.
 *  Firstly, if some conditions are satisfied, animal act breeding.
 *  Secondly, if there is edible object near with animal, it trying to eat it.
 *  Next, if there is edible object within view-range, move to it.
 *  Otherwise, do nothing.
 **/

public abstract class CommonAnimalAI implements AI {

	/**
	 * Get breed probability.
	 *
	 * @return Value of Double
	 */
	abstract protected double getBreedProbability();


	/**
	 * Act function of Common Animal.
	 *  Firstly, if some conditions are satisfied, animal act breeding.
	 *  Secondly, if there is edible object near with animal, it trying to eat it.
	 *  Next, if there is edible object within view-range, move to it.
	 *  Otherwise, do nothing.
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
	 *
	 * @throws UnsupportedOperationException
	 *             If actor is not CommonAnimal type.
	 */
	@Override
	public Command act(World world, Actor actor) {
		if (actor == null)
			throw new NullPointerException("Actor cannot be null");
		else if (world == null)
			throw new NullPointerException("World cannot be null");
		else if (!(actor instanceof CommonAnimal))
			throw new UnsupportedOperationException("Actor cannot be CommonAnimal");


		CommonAnimal instance = (CommonAnimal) actor;
		Location loc = world.getLocation(actor);

		// Visible objects of this animal
		Object[][] visibleObjects = WorldUtil.getVisibleObjects(world, actor);

		// Adjacent objects of this animal
		ArrayList<Object> adjacentObject = WorldUtil.getAdjacentObjectList(world, actor);


		// Breed requirements
		//   1. Energy should be more than breed limit.
		//   2. There are no objects near animal. (Animal is shy!!)
		//   3. Even if the above two conditions are met,
		//         the breeding is performed only at a certain probability.
		//   	   (Breeds only when it is in good mood!!!)

		if (instance.getEnergy() >= instance.getBreedLimit() && adjacentObject.size() == 0 && Math.random() < this.getBreedProbability()) {
			for (Direction dir: Direction.values()) {
				Location newLoc = new Location(loc, dir);

				if (world.isValidLocation(newLoc) && world.getThing(newLoc) == null)
					return new BreedCommand(dir);
			}
		}

		// If there is edible object, eat it!
		for (Object near: adjacentObject) {
			if (near != null && near instanceof Edible && instance.edibleWith((Edible) near)) {
				return new EatCommand( loc.dirTo(world.getLocation(near)) );
			}
		}

		// Go to edible object if any is visible
		for (Object visible: visibleObjects) {
			if (visible != null && visible instanceof Edible && instance.edibleWith((Edible) visible)) {
				return new MoveCommand( loc.dirTo(world.getLocation(visible)) );
			}
		}

		return null;
	}

}
