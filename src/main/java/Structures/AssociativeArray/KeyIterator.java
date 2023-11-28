package Structures.AssociativeArray;

import java.util.Iterator;

class KeyIterator<K extends Comparable<K>> implements Iterator<K>
{
    private AssociativeArray array;
    int index;

    public KeyIterator(AssociativeArray array)
    {
        this.array = array;
        index = -1;
    }

    @Override
    public boolean hasNext()
    {
        return index + 1 < array.size();
    }

    @Override
    public K next()
    {
        index++;
        AssociativeArray.Pair nextPair = array.getIthPair(index);

        return (K) nextPair.key;
    }
}
