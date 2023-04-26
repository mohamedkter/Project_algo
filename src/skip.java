import java.util.Random;


public class skip<K extends Comparable<? super K>, V> {
    private final int MAX_LEVEL = 32; // maximum level of the skip list
    private final double P = 0.5; // probability of increasing the level
    private int levelCount = 1; // current level of the skip list
    private final Node<K, V> head = new Node<>(null, null, MAX_LEVEL); // head node of the skip list
    
    // Node class for the skip list
    private static class Node<K extends Comparable<? super K>, V> {
        K key;
        V value;
        Node<K, V>[] next;
        
        Node(K key, V value, int level) {
            this.key = key;
            this.value = value;
            this.next = new Node[level];
        }
    }

    int randomLevel() {
        int lev;
        Random value = new Random();
        for (lev = 0; Math.abs(value.nextInt()) % 2 == 0; lev++) {
            // Do nothing
        }
        return lev; // returns a random level
    }
    // Insert a key-value pair into the skip list
    public void insert(KVPair<K, V> pair) {
        Node<K, V>[] update = new Node[MAX_LEVEL]; // array to store the update nodes
        Node<K, V> p = head; // start from the head node
        
        // Traverse the skip list to find the position to insert the new node
        for (int i = levelCount - 1; i >= 0; i--) {
            while (p.next[i] != null && p.next[i].key.compareTo(pair.getKey()) < 0) {
                p = p.next[i];
            }
            update[i] = p;
        }
        
        // If the key already exists, update the value
        if (p.next[0] != null && p.next[0].key.compareTo(pair.getKey()) == 0) {
            p.next[0].value = pair.getValue();
        } else {
            // Generate a random level for the new node
            int level = 1;
            while (randomLevel() < P && level < MAX_LEVEL) {
                level++;
            }
            
            // If the new level is higher than the current level of the skip list, update the update array
            if (level > levelCount) {
                for (int i = levelCount; i < level; i++) {
                    update[i] = head;
                }
                levelCount = level;
            }
            
            // Create a new node for the key-value pair and insert it into the skip list
            Node<K, V> newNode = new Node<>(pair.getKey(), pair.getValue(), level);
            for (int i = 0; i < level; i++) {
                newNode.next[i] = update[i].next[i];
                update[i].next[i] = newNode;
            }
        }
    }
    public void search(K key) {
        Node<K, V> p = head; // start from the head node
        
        // Traverse the skip list to find the node with the given key
        for (int i = levelCount - 1; i >= 0; i--) {
            while (p.next[i] != null && p.next[i].key.compareTo(key) < 0) {
                p = p.next[i];
            }
        }
        
        // If the node with the given key is found, return its value
        if (p.next[0] != null && p.next[0].key.compareTo(key) == 0) {
            System.out.println(p.next[0].value);
        } else {
           System.out.println("not fount");
        }
    }
 // Print all nodes in the skip list along with their depths
    public void dump() {
        for (int i = levelCount - 1; i >= 0; i--) {
            Node<K, V> p = head.next[i]; // start from the first node at this level
            
            System.out.print("Level " + i + ": ");
            while (p != null) {
                System.out.print("(" + p.key + ", " + p.value + ", " + i + ") ");
                p = p.next[i];
            }
            System.out.println();
        }
    }
    @SuppressWarnings("unchecked")
    public KVPair<K, V> remove(K key) {
        Node<K, V>[] update = new Node[MAX_LEVEL]; // array to store the update nodes
        Node<K, V> p = head; // start from the head node
        
        // Traverse the skip list to find the node with the given key
        for (int i = levelCount - 1; i >= 0; i--) {
            while (p.next[i] != null && p.next[i].key.compareTo(key) < 0) {
                p = p.next[i];
            }
            update[i] = p;
        }
        
        // If the node with the given key is found, remove it and return the key-value pair
        if (p.next[0] != null && p.next[0].key.compareTo(key) == 0) {
            Node<K, V> removedNode = p.next[0];
            for (int i = 0; i < levelCount; i++) {
                if (update[i].next[i] == removedNode) {
                    update[i].next[i] = removedNode.next[i];
                }
            }
            while (levelCount > 1 && head.next[levelCount - 1] == null) {
                levelCount--;
            }
            return new KVPair<>(removedNode.key, removedNode.value);
        } else {
            // If the node with the given key is not found, return null
            return null;
        }
    }
    public KVPair<K, V> removeByValue(V value) {
        Node<K, V>[] update = new Node[MAX_LEVEL]; // array to store the update nodes
        Node<K, V> p = head; // start from the head node
        KVPair<K, V> removedPair = null; // initialize the removed pair to null
        
        // Traverse the skip list to find the node with the given value
        for (int i = levelCount - 1; i >= 0; i--) {
            while (p.next[i] != null && p.next[i].value != null && p.next[i].value.equals(value)) {
                update[i] = p;
                p = p.next[i];
            }
        }
        
        // If the node with the given value is found, remove it and return its key-value pair
        if (p != null && p.value != null && p.value.equals(value)) {
            for (int i = 0; i < levelCount; i++) {
                if (update[i].next[i] != p) {
                    break;
                }
                update[i].next[i] = p.next[i];
            }
            while (levelCount > 1 && head.next[levelCount - 1] == null) {
                levelCount--;
            }
            removedPair = new KVPair<>(p.key, p.value);
        }
        
        return removedPair;
    }
    public int size() {
        int size = 0;
        Node<K, V> p = head.next[0]; // start from the first node at the bottom level
        while (p != null) {
            size++;
            p = p.next[0];
        }
        return size;
    }
    @SuppressWarnings("unchecked")
    public void adjustHead(int newLevel) {
        if (newLevel <= 0) {
            throw new IllegalArgumentException("newLevel must be > 0");
        }
        
        if (newLevel > MAX_LEVEL - levelCount) {
            throw new IllegalArgumentException("newLevel is too large");
        }
        
        Node<K, V>[] newNext = new Node[MAX_LEVEL];
        System.arraycopy(head.next, 0, newNext, 0, levelCount);
        
        for (int i = levelCount; i < levelCount + newLevel; i++) {
            newNext[i] = null;
        }
        
        head.next = newNext;
        levelCount += newLevel;
    }

}

