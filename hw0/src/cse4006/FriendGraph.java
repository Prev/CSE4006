package cse4006;

import cse4006.util.*;

/**
 * FriendGraph
 *
 * @author Prev (0soo.2@prev.kr)
 */
public class FriendGraph {

    private Person[] peopleList;
    private boolean[][] relation;

    private int maxPeopleCount;
    private int nowPeopleCount;

    /**
     * Constructor of FriendGraph
     * @param maxPeopleNumber: Max number of people in graph
     */
    public FriendGraph(int maxPeopleNumber) {
        this.maxPeopleCount = maxPeopleNumber;
        this.nowPeopleCount = 0;

        this.peopleList = new Person[maxPeopleNumber];
        this.relation = new boolean[maxPeopleNumber][maxPeopleNumber];
    }

    /**
     * Add person to graph
     * @param person: Person instance
     */
    public void addPerson(Person person) {
        if (this.nowPeopleCount == this.maxPeopleCount)
            throw new Error("People is full in graph");

        if (_findPerson(person.getName()) != null)
            throw new Error("There is already a person in graph with same name");

        this.peopleList[ this.nowPeopleCount++ ] = person;
    }

    private Person _findPerson(String name) {
        for (int i = 0; i < nowPeopleCount; i++) {
            if (peopleList[i].getName() == name)
                return peopleList[i];
        }
        return null;
    }

    private int _findId(String name) {
        for (int i = 0; i < nowPeopleCount; i++) {
            if (peopleList[i].getName() == name)
                return i;
        }
        return -1;
    }

    /**
     * Add friendship between person1 & person2
     * @param name1: Name of person1
     * @param name2: Name of person2
     */
    public void addFriendship(String name1, String name2) {
        int person1Id = _findId(name1);
        int person2Id = _findId(name2);

        this.relation[person1Id][person2Id] = true;
        this.relation[person2Id][person1Id] = true;
    }


    /**
     * Get distance between person1 & person2
     *      if person1 and person2 are same, there are two different value of this situation
     *          if that person is in any relation, return 0
     *          else, return -1
     * @param name1: Name of person1
     * @param name2: Name of person2
     * @return
     */
    public int getDistance(String name1, String name2) {
        boolean visited[] = new boolean[this.maxPeopleCount];
        Queue<Pair<Integer, Integer>> que = new Queue<>();

        int source = _findId(name1);
        int destination = _findId(name2);

        if (source == destination) {
            for (int i = 0; i < relation[source].length; i++)
                if (relation[source][i]) return 0;

            return -1;
        }

        que.enqueue(new Pair<>(source, 0));

        while (!que.empty()) {
            Pair<Integer, Integer> elm = que.dequeue();
            int id = elm.left;
            int dist = elm.right;

            if (id == destination)
                return dist;

            visited[id] = true;

            for (int j = 0; j < this.relation[id].length; j++) {
                if (this.relation[id][j] && !visited[j])
                    que.enqueue(new Pair<>(j, dist+1));
            }
        }

        return -1;
    }
}

