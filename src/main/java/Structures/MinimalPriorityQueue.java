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

    private void heapify(int i)
    {
        int l = left(i);
        int r = right(i);
        int smallest;
        if (l < arraySize && array[l].compareTo(array[i]) < 0)
            smallest = l;
        else
            smallest = i;

        if (r < arraySize && array[r].compareTo(array[smallest]) < 0)
            smallest = r;

        if (smallest != i)
        {
            swapElements(i, smallest);
            heapify(smallest);
        }
    }

    private void swapElements(int i, int j)
    {
        K tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
    }

    public void insert(K object)
    {
        incrementArraySize();
        int i = arraySize - 1;
        while (i > 0 && array[parent(i)].compareTo(object) > 0)
        {
            array[i] = array[parent(i)];
            i = parent(i);
        }
        array[i] = object;
    }

    public int size()
    {
        return arraySize;
    }

    public K extractMin()
    {
        if (arraySize == 0)
            return null;

        K minimum = array[0];

        array[0] = array[arraySize - 1];
        arraySize--;

        heapify(0);

        return minimum;
    }
}
