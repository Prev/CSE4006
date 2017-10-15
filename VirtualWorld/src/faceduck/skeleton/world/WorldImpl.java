package faceduck.skeleton.world;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import faceduck.skeleton.interfaces.Actor;
import faceduck.skeleton.interfaces.World;
import faceduck.skeleton.util.Location;

/**
 * This is an implementation of the world which runs faceduck.actors. The world contains
 * two types of maps. One maps locations to objects in the world and the other
 * maps objects in the world to location.
 */
public class WorldImpl implements World {

	private int width;
	private int height;

	// Used for arena
	// private Map<Class, String> aiToName;
	// private Map<String, Integer> nameToCount;

	// this 2D array maps locations to objects
	protected Object[][] locToObj;

	// This map maps objects to locations
	protected Map<Object, Location> objToLoc;

	// This queue determines which actor to step next
	protected Queue<Actor> needToAct;
	// this queue has all faceduck.actors which have already acted
	protected Queue<Actor> hasActed;

	// This map maps faceduck.actors to the number of steps since their last move
	protected Map<Actor, Integer> actorToWait;

	/**
	 * Instantiates an instance of the world.
	 *
	 * @param width
	 *            The width of the world.
	 * @param height
	 *            The height of the world.
	 *
	 * @throws IllegalArgumentException
	 *             If width or height are less than 0.
	 */
	public WorldImpl(int width, int height) {
		if (width < 0 || height < 0) {
			throw new IllegalArgumentException("Parameters must be > 0");
		}

		this.width = width;
		this.height = height;

		locToObj = new Object[width][height];
		objToLoc = new HashMap<Object, Location>();

		needToAct = new LinkedList<Actor>();
		hasActed = new LinkedList<Actor>();

		actorToWait = new HashMap<Actor, Integer>();
		// this.nameToCount = new HashMap<String, Integer>();
	}

	// used for arena
	// public void setMap(Map<Class, String> m) {
	// this.aiToName = m;
	// Set<Class> s = aiToName.keySet();
	// for(Class c : s){
	// String name = aiToName.get(c);
	// nameToCount.put(name, 9);
	// }
	// }

	/**
	 * Returns the set of all objects in the world.
	 *
	 * @return A set of objects.
	 */
	@Override
	public Set<Object> getAllObjects() {
		// The set of all objects in the world is just the keyset of the
		// object-location map
		Set<Object> result;
		synchronized (WorldImpl.class) {
			result = objToLoc.keySet();
		}
		return result;
	}

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
	@Override
	public Location getLocation(Object thing) {
		if (thing == null) {
			throw new NullPointerException("Input cannot be null.");
		}
		Location loc;
		synchronized (WorldImpl.class) {
			loc = objToLoc.get(thing);
		}
		return loc;
	}

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
	@Override
	public Object getThing(Location loc) {
		if (loc == null) {
			throw new NullPointerException("Input cannot be null.");
		}
		int x = loc.getX();
		int y = loc.getY();
		if (x < 0 || x >= width || y < 0 || y >= height) {
			throw new IndexOutOfBoundsException(
					"Not a valid location in the world");
		}
		return locToObj[x][y];
	}

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
	@Override
	public void add(Object newThing, Location loc) {
		if (newThing == null) {
			throw new NullPointerException("Object cannot be null.");
		} else if (loc == null) {
			throw new NullPointerException("Location cannot be null.");
		}
		synchronized (WorldImpl.class) {
			if (objToLoc.containsKey(newThing)) {
				throw new IllegalArgumentException(
						"The object already exists in the world.");
			}
		}

		int x = loc.getX();
		int y = loc.getY();

		if (x >= width || y >= height || x < 0 || y < 0) {
			throw new IndexOutOfBoundsException(
					"Not a valid location in the world");
		}

		// check if the space is empty
		if (locToObj[x][y] != null) {
			throw new IllegalArgumentException("This space is not empty.");
		}

		// map the location to the object
		locToObj[x][y] = newThing;

		// map the object to the location
		synchronized (WorldImpl.class) {
			objToLoc.put(newThing, loc);
		}

		// if the new thing is an actor add it to the list
		if (newThing instanceof Actor) {
			// always add to hasActed - new objects need to wait for the next
			// step to act
			hasActed.add((Actor) newThing);
		}

		// used for arena
		// if((newThing instanceof FoxImpl) && (aiToName != null)) {
		// AI faceduck.ai = ((FoxImpl)newThing).getAI();
		// String s = this.aiToName.get(faceduck.ai.getClass());
		// this.nameToCount.put(s, this.nameToCount.get(s) + 1);
		// }
	}

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
	@Override
	public void remove(Object oldThing) {
		if (oldThing == null) {
			throw new NullPointerException("Object cannot be null.");
		}

		synchronized (WorldImpl.class) {
			if (!objToLoc.containsKey(oldThing)) {
				throw new IllegalArgumentException(
						"Object does not exist in the world.");
			}
		}

		// Removing the oldThing returns us the value it was mapped to - the
		// location it was at.
		Location loc = objToLoc.remove(oldThing);
		// now remove it from the other mapping
		locToObj[loc.getX()][loc.getY()] = null;

		// if the new thing is an actor add it to the list
		if (oldThing instanceof Actor) {
			// determine which queue to remove from
			if (needToAct.contains(oldThing)) {
				needToAct.remove((Actor) oldThing);
			} else {
				hasActed.remove((Actor) oldThing);
			}
		}

		// used for arena
		// if ((oldThing instanceof FoxImpl) && (aiToName != null)) {
		// AI faceduck.ai = ((FoxImpl) oldThing).getAI();
		// String s = this.aiToName.get(faceduck.ai.getClass());
		// this.nameToCount.put(s, this.nameToCount.get(s) - 1);
		// }
	}

	/**
	 * Returns the width of the world.
	 *
	 * @return An integer representing the width of the world.
	 */
	@Override
	public int getWidth() {
		return width;
	}

	/**
	 * Returns the height of the world.
	 *
	 * @return An integer representing the height of the world.
	 */
	@Override
	public int getHeight() {
		return height;
	}

	/**
	 * Performs a single step in the simulation.
	 *
	 * @return Returns false if there are no faceduck.actors to step, true otherwise.
	 */
	@Override
	public boolean step() {
		// If needToAct is empty but hasActed isn't, we just "move on" to the
		// next step.
		if (needToAct.isEmpty()) {
			if (hasActed.isEmpty()) {
				// If both queues are empty something is wrong.
				return false;
			} else {
				needToAct = hasActed;
			}
		}

		// Create a new empty queue for hasActed - clearing the queue
		hasActed = new LinkedList<Actor>();

		// act on each actor until everyone has acted
		while (!needToAct.isEmpty()) {
			// How long a particular actor has gone since acting
			Integer amountWaited;
			Actor a = needToAct.poll();
			if (actorToWait.containsKey(a)) {
				amountWaited = actorToWait.get(a);
				// If a has not met cooldown threshold, don't act
				if (amountWaited < a.getCoolDown()) {
					actorToWait.put(a, amountWaited + 1);
				} else {
					actorToWait.put(a, 0);
					a.act(this);
				}
			} else {
				actorToWait.put(a, 0);
				a.act(this);
			}

			// objToLoc may be manipulated from multiple threads
			if (!hasActed.contains(a)) {
				synchronized (WorldImpl.class) {
					if (objToLoc.containsKey(a)) {
						hasActed.add(a);
					}
				}
			}
		}

		// reset
		needToAct = hasActed;

		// used for arena
		// System.out.println(nameToCount);
		// for (String s: nameToCount.keySet()){
		// System.out.println("Name: " + s + "Count: " + nameToCount.get(s));
		// }

		return true;
	}

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
	@Override
	public boolean isValidLocation(Location loc) {
		if (loc == null) {
			throw new NullPointerException("Location cannot be null");
		}
		return 0 <= loc.getX() && 0 <= loc.getY() && loc.getX() < width
				&& loc.getY() < height;
	}
}