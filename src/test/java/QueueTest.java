import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;

import Exceptions.EmptyQueue;
import Structures.MinimalPriorityQueue;
import org.junit.jupiter.api.Test;

public class QueueTest
{
    private int getMinimalValue(int[] input) throws EmptyQueue
    {
        MinimalPriorityQueue<Integer> queue = new MinimalPriorityQueue<>();

        for (int val: input)
        {
            queue.insert(val);
        }

        return queue.extractMin();
    }
    @Test
    public void oneValue() throws EmptyQueue
    {
       int min = getMinimalValue(new int[]{2});

        assertEquals(2, min);
    }

    @Test
    public void valuesAscending() throws EmptyQueue
    {
        int min = getMinimalValue(new int[]{1, 2, 3, 7});

        assertEquals(1, min);
    }

    @Test
    public void valuesDescending() throws EmptyQueue
    {
        int min = getMinimalValue(new int[]{7, 3, 2, 1});

        assertEquals(1, min);
    }

    @Test
    public void valuesRandom() throws EmptyQueue
    {
        int min = getMinimalValue(new int[]{2, 1, -3, 7, 14, 88 -2, 0, 5});

        assertEquals(-3, min);
    }

    @Test
    public void emptyQueueHasSizeZero()
    {
        MinimalPriorityQueue<Integer> queue = new MinimalPriorityQueue<>();

        assertEquals(0, queue.size());
    }

    @Test
    public void correctSizeAfterPuttingAndExtracting() throws EmptyQueue
    {
        MinimalPriorityQueue<Integer> queue = new MinimalPriorityQueue<>();

        queue.insert(21);
        queue.insert(37);
        queue.insert(14);
        queue.insert(88);

        queue.extractMin();
        queue.extractMin();

        assertEquals(2, queue.size());
    }

    @Test
    public void shouldBeEmptyAfterPuttingFewAndExtractingEverything() throws EmptyQueue
    {
        MinimalPriorityQueue<Integer> queue = new MinimalPriorityQueue<>();

        queue.insert(1);
        queue.insert(3);
        queue.insert(6);

        queue.extractMin();
        queue.extractMin();
        queue.extractMin();

        assertEquals(0, queue.size());
    }

    @Test
    public void shouldBeEmptyAfterPuttingOneAndExtractingOne() throws EmptyQueue
    {
        MinimalPriorityQueue<Integer> queue = new MinimalPriorityQueue<>();

        queue.insert(69);

        queue.extractMin();

        assertEquals(0, queue.size());
    }

    @Test
    public void megaTest() throws EmptyQueue
    {
        MinimalPriorityQueue<Integer> queue = new MinimalPriorityQueue<>();

        queue.insert(2);
        queue.insert(1);
        queue.insert(3);
        queue.insert(7);
        queue.insert(-20);
        queue.insert(40);

        int[] goodOrder = {-20, 1, 2, 3, 7, 40};
        int size = queue.size();

        for (int i = 0; i < size; i++)
        {
            int minimum = queue.extractMin();
            int expected = goodOrder[i];

            assertEquals(expected, minimum);
        }
    }
}
