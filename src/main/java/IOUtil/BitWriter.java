package IOUtil;

import Structures.BitVector;

import java.io.IOException;
import java.io.RandomAccessFile;

public class BitWriter
{
    private RandomAccessFile file;
    private byte currentByte;
    private int byteOffset;

    public BitWriter(String filename) throws IOException
    {
        file = new RandomAccessFile(filename, "rw"); // w?
        currentByte = 0;
        byteOffset = 0;
    }

    public void writeBit(int bit) throws IOException
    {
        currentByte |= (byte) ((byte)bit << (7 - byteOffset));

        byteOffset++;
        if (byteOffset == 8)
        {
            file.writeByte((int)currentByte);
            currentByte = 0;
            byteOffset = 0;
        }
    }

    public void writeTheRest() throws IOException
    {
        if (byteOffset > 0)
        {
            file.writeByte((int)currentByte);
        }
    }

    public void writeBitVector(BitVector vector) throws IOException
    {
        for (int i = 0; i < vector.size(); i++)
        {
            int bit = vector.getBit(i);
            writeBit(bit);
        }
    }

    public void writePadding() throws IOException
    {
        file.seek(0);
        byte zeroByte = file.readByte();

        int padding = (8 - byteOffset) % 8;
        zeroByte |= (byte)(padding << 5);

        file.seek(0);
        file.writeByte((int)zeroByte);
    }
}
