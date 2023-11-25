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
            writeCurrentByte();
        }
    }

    public void writeCurrentByte() throws IOException
    {
        file.writeByte((int)currentByte); // why int?
        currentByte = 0;
        byteOffset = 0;
    }

    public void writeTheRest() throws IOException
    {
        if (byteOffset > 0)
        {
            writeCurrentByte();
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

//    public int getByteOffset()
//    {
//        return byteOffset;
//    }

    public void writePadding() throws IOException
    {
        file.seek(0);
        byte zeroByte = file.readByte();

        zeroByte |= (byte)(byteOffset << 5);

        file.seek(0);
        file.writeByte((int)zeroByte);
    }
}
