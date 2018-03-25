package cse4006.util;

/**
 * Pair ADT
 *
 * @author Prev (0soo.2@prev.kr)
 */
public class Pair<L,R> {
    /**
     * Left item of pair
     */
    public final L left;

    /**
     * Right item of pair
     */
    public final R right;

    /**
     * Constructor of pair
     * @param left: left item of pair
     * @param right: right item of pair
     */
    public Pair(L left, R right) {
        this.left = left;
        this.right = right;
    }
}