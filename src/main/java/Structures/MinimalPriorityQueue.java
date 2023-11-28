package Structures;
public class MinimalPriorityQueue<K extends Comparable<K>>
{
    private K[] array;
    private int arraySize;
    private final int INITIAL_CAPACITY = 2;

    public MinimalPriorityQueue()
    {
        arraySize = 0;
        array = (K[]) new Comparable[INITIAL_CAPACITY];
    }

    private void resizeArray()
    {
        int newCapacity = 2 * array.length;
        K[] newArray = (K[]) new Comparable[newCapacity];
        System.arraycopy(array, 0, newArray, 0, arraySize);
        array = newArray;
    }

    private void incrementArraySize()
    {
        if (arraySize + 1 > array.length)
            resizeArray();

        arraySize++;
    }

    private int left(int i)
    {
        return 2 * i + 1;
    }

    private int right(int i)
    {
        return 2 * i + 2;
    }

    private int parent(int i)
    {
        return i / 2;
    }

    private void swapElements(int i, int j)
    {
        K tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
    }

    private void heapDown(int i)
    {
        while (true)
        {
            int smallest = i;
            if (left(i) < arraySize && array[left(i)].compareTo(array[i]) < 0)
                smallest = left(i);
            if (right(i) < arraySize && array[right(i)].compareTo(array[smallest]) < 0)
                smallest = right(i);

            if (smallest != i)
            {
                swapElements(i, smallest);
                i = smallest;
            }
            else
                break;
        }
    }

    private void heapUp(int i)
    {
        while (i > 0 && array[parent(i)].compareTo(array[i]) > 0)
        {
            swapElements(i, parent(i));
            i = parent(i);
        }
    }

    public void insert(K object)
    {
        incrementArraySize();
        array[arraySize - 1] = object;
        heapUp(arraySize - 1);
    }

    public K extractMin()
    {
        if (arraySize == 0)
            return null;

        K minimum = array[0];

        array[0] = array[arraySize - 1];
        arraySize--;

        heapDown(0);

        return minimum;
    }

    public int size()
    {
        return arraySize;
    }
}
