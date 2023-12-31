package Structures;

public class BitVector
{

    final int INITIAL_BYTES_ALLOCATED = 2;
    private byte[] array;
    private int size;

    public BitVector()
    {
        array = new byte[INITIAL_BYTES_ALLOCATED];
        size = 0;
    }

    private void resizeIfNeeded()
    {
        int bitsAllocated = array.length * 8;
        if (size == bitsAllocated)
        {
            byte[] newArray = new byte[2 * array.length];
            System.arraycopy(array, 0, newArray, 0, array.length);
            array = newArray;
        }
    }

    public void pushBit(int bit)
    {
        resizeIfNeeded();

        int byteIndex = size / 8;
        int bitIndex = size % 8;

        byte newByte;
        if (bitIndex == 0)
            newByte = (byte) (bit << 7);
        else
        {
            // SEVERE MUSHROOMS
            byte oldByte = array[byteIndex];

            int shift = (8 - bitIndex);
            byte mask = (byte) ((0xFFFFFFFF << shift) & 0xFF);
            newByte = (byte) ((oldByte & mask) // Zero the rightmost bits
                    | (bit << (shift - 1))); // and add the new bit.
        }

        array[byteIndex] = newByte;
        size++;
    }

    public void popBit()
    {
        if (size > 0)
        {
            size--;
        }
    }

    public int getBit(int index)
    {
        if (index < 0)
            index = size + index;

        int byteIndex = index / 8;
        int bitIndex = index % 8;

        byte B = array[byteIndex];

        byte mask = (byte) (1 << (7 - bitIndex));
        return (B & mask) == 0 ? 0 : 1;
    }

    public void pushByte(byte B)
    {
        for (int i = 0; i < 8; i++)
        {
            int bit = (B >> 7 - i) & 1;
            pushBit(bit);
        }
    }

    public int size()
    {
        return size;
    }

    @Override
    public boolean equals(Object o)
    {
        if (o == this)
            return true;
        if (!(o instanceof BitVector))
            return false;

        BitVector other = (BitVector)o;

        if (this.size != other.size)
            return false;

        for (int i = 0; i < this.size; i++)
        {
            if (this.getBit(i) != other.getBit(i))
                return false;
        }

        return true;
    }

    @Override
    public BitVector clone()
    {
        BitVector vector = new BitVector();

        for (int i = 0; i < this.size; i++)
            vector.pushBit(this.getBit(i));

        return vector;
    }
}
