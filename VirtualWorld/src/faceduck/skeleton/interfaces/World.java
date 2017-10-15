package faceduck.skeleton.interfaces;

import java.util.Set;

import faceduck.skeleton.util.Location;

public interface World {

	/**
	 * Returns the set of all objects in the world.
	 *
	 * @return A set of objects.
	 */
	public Set<Object> getAllObjects();

	/**
	 * Returns the location of a particular object in the world.
	 *
	 * @param thing
	 *            The object to locate in the world.
	 *
	 * @throws NullPointerException
	 *             If thing is null
	 *
	 * @return The Location object which contains thing, or null if thing is not
	 *         in the world.
	 */
	public Location getLocation(Object thing);

	/**
	 * Given a location, returns the object which is there or null if no object
	 * is at the location
	 *
	 * @param loc
	 *            The location to investigate
	 *
	 * @throws NullPointerException
	 *             Throws an exception if loc is null.
	 * @throws IndexOutOfBoundsExcpetion
	 *             Throws an exception if loc is out of bounds of the world.
	 *
	 * @return An Object, or null is the location is empty.
	 */
	public Object getThing(Location loc);

	/**
	 * Adds an object to a location in the world.
	 *
	 * @param newThing
	 *            The object to add
	 * @param loc
	 *            The Location to add the object at
	 *
	 * @throws IllegalArgumentException
	 *             If loc is not empty.
	 * @throws NullPointerException
	 *             If loc or newThing are null.
	 * @throws IndexOutOfBoundsException
	 *             If loc is out of bounds in the world.
	 */
	public void add(Object newThing, Location loc);

	/**
	 * Given an object in the world, this method will remove that object from
	 * the world.
	 *
	 * @param oldThing
	 *            The object to remove from the world.
	 *
	 * @throws NullPointerException
	 *             If oldThing is null.
	 * @throws IllegalArgumentException
	 *             If oldThing is not in the world.
	 */
	public void remove(Object oldThing);

	/**
	 * Returns the width of the world.
	 *
	 * @return An integer representing the width of the world.
	 */
	public int getWidth();

	/**
	 * Returns the height of the world.
	 *
	 * @return An integer representing the height of the world.
	 */
	public int getHeight();

	/**
	 * Performs a single step in the simulation.
	 *
	 * @return Returns false if there are no faceduck.actors to step, true otherwise.
	 */
	public boolean step();

	/**
	 * Returns true if loc is a valid location in the world, false otherwise.
	 *
	 * @param loc
	 *            the location to inspect
	 *
	 * @throws NullPointerException
	 *             If loc is null.
	 *
	 * @return true if loc is valid in the world, false otherwise.
	 */
	public boolean isValidLocation(Location loc);
}
