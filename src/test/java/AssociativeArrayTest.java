import Structures.AssociativeArray.AssociativeArray;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class AssociativeArrayTest
{
    @Test
    public void setOneAndGetOne()
    {
        AssociativeArray<Integer, String> array = new AssociativeArray<>();

        array.put(21, "pope");

        assertEquals("pope", array.get(21));
    }

    @Test
    public void getFromEmpty()
    {
        AssociativeArray<Integer, String> array = new AssociativeArray<>();

        assertNull(array.get(37));
    }

    @Test
    public void getNonexistent()
    {
        AssociativeArray<Integer, String> array = new AssociativeArray<>();

        array.put(21, "pope");

        assertNull(array.get(37));
    }

    @Test
    public void setOneValueAndThenAnother()
    {
        AssociativeArray<Integer, String> array = new AssociativeArray<>();

        array.put(21, "pope");
        array.put(21, "bergoglio");

        assertEquals("bergoglio", array.get(21));
    }

    @Test
    public void setFewAndGetOne()
    {
        AssociativeArray<Integer, String> array = new AssociativeArray<>();

        array.put(21, "karol");
        array.put(37, "wojtyla");
        array.put(0xDEAD, "pope");

        assertEquals("pope", array.get(0xDEAD));
    }
}
