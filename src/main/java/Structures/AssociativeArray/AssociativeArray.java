package Structures.AssociativeArray;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

public class AssociativeArray<K extends Comparable<K>, V> implements Iterable<K>
{
    class Pair
    {
        K key;
        V value;

        public K getKey()
        {
            return key;
        }
    }

    private ArrayList<Pair> array;
    public AssociativeArray()
    {
        array = new ArrayList<>();
    }

    private Pair getPair(K key)
    {
        for (Pair pair: array)
        {
            if (pair.key.equals(key))
                return pair;
        }

        return null;
    }

    public V get(K key)
    {
        Pair pair = getPair(key);
        if (pair == null)
            return null;

        return pair.value;
    }

    public void put(K key, V value)
    {
        Pair pairFound = getPair(key);
        if (pairFound == null)
        {
            Pair newPair = new Pair();
            newPair.key = key;
            newPair.value = value;

            array.add(newPair);

            array.sort(Comparator.comparing(Pair::getKey));
        }
        else
        {
            pairFound.value = value;
        }
    }

    public int size()
    {
        return array.size();
    }

    Pair getIthPair(int index)
    {
        return array.get(index);
    }

    @Override
    public Iterator<K> iterator()
    {
        return new KeyIterator<K>(this);
    }
}
