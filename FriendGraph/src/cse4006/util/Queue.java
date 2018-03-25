package cse4006.util;

/**
 * Queue ADT
 *   Implemented with linked-list
 *   Item type is defined as generic (template)
 *
 * @author Prev (0soo.2@prev.kr)
 */
public class Queue<T> {

    private class Node {
        T data;
        Node nextNode;
        Node(T data) {
            this.data = data;
            this.nextNode = null;
        }
    }
    private Node _front;
    private Node _rear;

    /**
     * Constructor of Queue
     */
    public Queue() {
        this._front = null;
        this._rear = null;
    }

    /**
     * Return whether queue is empty
     * @return true if queue is empty
     *         false if queue is not empty
     */
    public boolean empty() {
        return (_front == null);
    }

    /**
     * Add some item to queue
     * @param item: Type of queue
     */
    public void enqueue(T item) {
        Node node = new Node(item);
        node.nextNode = null;

        if (empty()) {
            this._rear = node;
            this._front = node;
        } else {
            this._rear.nextNode = node;
            this._rear = node;
        }
    }

    /**
     * Get front data of queue
     * @return item: Type of queue
     */
    public T peek() {
        if (empty()) throw new ArrayIndexOutOfBoundsException();
        return this._front.data;
    }

    /**
     * Get front data of queue and remove it
     * @return item: Type of queue
     */
    public T dequeue() {
        T item = peek();
        this._front = this._front.nextNode;

        if (this._front == null) {
            this._rear = null;
        }
        return item;
    }
}