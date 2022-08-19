package com.josh.java.training.collection.queue;

import java.util.NoSuchElementException;
import java.util.PriorityQueue;

public class QueueDemo {
	public static void main(String[] args) {
        //  FIFO (first in, first out)
        PriorityQueue<Integer> q1 = new PriorityQueue<Integer>(3);

        // Throws NoSuchElement exception --- no items in queue yet
        q1.add(10);
        q1.add(20);
        q1.add(30);

        System.out.println("Head of queue is: " + q1.element());

        try {
            q1.add(40);
        } catch (IllegalStateException e) {
            System.out.println("Tried to add too many items to the queue.");
        }

        for (Integer value : q1) {
            System.out.println("Queue value: " + value);
        }

        System.out.println("Removed from queue: " + q1.remove());
        System.out.println("Removed from queue: " + q1.remove());
        System.out.println("Removed from queue: " + q1.remove());

        try {
            System.out.println("Removed from queue: " + q1.remove());
        } 
        catch (NoSuchElementException e) {
            System.out.println("Tried to remove too many items from queue");
        }

        PriorityQueue<Integer> q2 = new PriorityQueue<>(2);

        System.out.println("Queue 2 peek: " + q2.peek());

        q2.offer(10);
        q2.offer(20);

        System.out.println("Queue 2 peek: " + q2.peek());

        if(q2.offer(30) == false) {
            System.out.println("Offer failed to add third item.");
        }

        for(Integer value: q2) {
            System.out.println("Queue 2 value: " + value);
        }

        System.out.println("Queue 2 poll: " + q2.poll());
        System.out.println("Queue 2 poll: " + q2.poll());
        System.out.println("Queue 2 poll: " + q2.poll());
    }
}
