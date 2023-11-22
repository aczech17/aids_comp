import static org.junit.jupiter.api.Assertions.assertEquals;
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

        vector.pushBit(0);
        vector.pushBit(1);

        vector.popBit();

        vector.pushBit(0);

        assertEquals(0, vector.getBit(-1));
    }
}
