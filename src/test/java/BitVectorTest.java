import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import Structures.BitVector;
import org.junit.jupiter.api.Test;


public class BitVectorTest
{
    @Test
    public void putZero()
    {
        BitVector vector = new BitVector();
        vector.pushBit(0);

        int bit = vector.getBit(-1);

        assertEquals(0, bit);
    }

    @Test
    public void putOne()
    {
        BitVector vector = new BitVector();
        vector.pushBit(1);

        assertEquals(1, vector.getBit(-1));
    }

    @Test
    public void put3Assymetric()
    {
        BitVector vector = new BitVector();

        vector.pushBit(1);
        vector.pushBit(1);
        vector.pushBit(0);

        int[] expectedBits = {1, 1, 0};

        for (int i = 0; i < 3; i++)
            assertEquals(expectedBits[i], vector.getBit(i));
    }

    @Test
    public void put8BitsAndThan1()
    {
        BitVector vector = new BitVector();

        vector.pushBit(0);
        vector.pushBit(1);
        vector.pushBit(0);
        vector.pushBit(0);
        vector.pushBit(0);
        vector.pushBit(0);
        vector.pushBit(0);
        vector.pushBit(1);

        vector.pushBit(1);

        assertEquals(1, vector.getBit(-1));
    }

    @Test
    public void put8BitsAndThan0()
    {
        BitVector vector = new BitVector();

        vector.pushBit(0);
        vector.pushBit(1);
        vector.pushBit(0);
        vector.pushBit(0);
        vector.pushBit(0);
        vector.pushBit(0);
        vector.pushBit(0);
        vector.pushBit(1);

        vector.pushBit(0);

        assertEquals(0, vector.getBit(-1));
    }

    @Test
    public void push1PopItAndThenPush0()
    {
        BitVector vector = new BitVector();

        vector.pushBit(1);
        vector.popBit();
        vector.pushBit(0);

        assertEquals(0, vector.getBit(-1));
    }

    @Test
    public void push0PopItAndThenPush1()
    {
        BitVector vector = new BitVector();

        vector.pushBit(0);
        vector.popBit();
        vector.pushBit(1);

        assertEquals(1, vector.getBit(-1));
    }

    @Test
    public void compareTwoSame()
    {
        BitVector vector1 = new BitVector();
        BitVector vector2 = new BitVector();

        vector1.pushBit(0);
        vector2.pushBit(0);

        vector1.pushBit(1);
        vector2.pushBit(1);

        vector1.pushBit(0);
        vector2.pushBit(0);

        vector1.pushBit(0);
        vector2.pushBit(0);

        vector1.pushBit(1);
        vector2.pushBit(1);

        assertEquals(vector1, vector2);
    }

    @Test
    public void compareTwoOther()
    {
        BitVector vector1 = new BitVector();
        BitVector vector2 = new BitVector();

        vector1.pushBit(1);
        vector2.pushBit(1);

        vector1.pushBit(1);
        vector2.pushBit(0);

        vector1.pushBit(1);
        vector2.pushBit(1);

        assertNotEquals(vector1, vector2);
    }

    @Test
    public void compare3OnesAnd2Ones()
    {
        BitVector vector1 = new BitVector();
        BitVector vector2 = new BitVector();

        vector1.pushBit(1);
        vector1.pushBit(1);

        vector2.pushBit(1);
        vector2.pushBit(1);
        vector2.pushBit(1);

        assertNotEquals(vector1, vector2);
    }

    @Test
    public void cloneTest0()
    {
        BitVector vector1 = new BitVector();

        vector1.pushBit(1);
        vector1.pushBit(1);
        vector1.pushBit(1);

        var vector2 = vector1.clone();

        assertEquals(vector1, vector2);
    }

    @Test
    public void pushOneByte()
    {
        BitVector vector = new BitVector();
        vector.pushByte((byte)0b11100101);

        int[] expectedBits = {1, 1, 1, 0, 0, 1, 0, 1};

        for (int i = 0; i < 8; i++)
        {
            assertEquals(vector.getBit(i), expectedBits[i]);
        }
    }

    @Test
    public void pushTwoBitsAndOneByte()
    {
        BitVector vector = new BitVector();
        vector.pushBit(1);
        vector.pushBit(0);
        vector.pushByte((byte)0b11100101);

        int[] expectedBits = {1, 0, 1, 1, 1, 0, 0, 1, 0, 1};

        for (int i = 0; i < 10; i++)
        {
            assertEquals(vector.getBit(i), expectedBits[i]);
        }
    }
}
