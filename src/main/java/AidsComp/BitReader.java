package AidsComp;

import java.io.IOException;
import java.io.RandomAccessFile;

public class BitReader
{
    private byte currentByte;
    private int currentByteNumber;
    private int byteOffset;
    private final RandomAccessFile input;
    private int paddingSize;
    private boolean endOfFile;

    public BitReader(String filename) throws IOException
    {
        currentByte = 0;
        currentByteNumber = 0;
        byteOffset = 0;

        input = new RandomAccessFile(filename, "r");

        paddingSize = (readBit() << 2) | (readBit() << 1) | readBit(); // ??? operator precedence
        endOfFile = false;
    }

    public int readBit() throws IOException
    {
        int bit = (currentByte >> (7 - byteOffset)) & 1;
        if (!lastBitReached())
        {
            byteOffset++;
            if (byteOffset == 8)
            {
                currentByte = input.readByte();
                currentByteNumber++;
                byteOffset = 0;
            }
        }
        else
            endOfFile = true;

        return bit;
    }

    private boolean lastBitReached() throws IOException
    {
        return (currentByteNumber == input.length() - 1) && (byteOffset == (7 - paddingSize));
    }

    public byte readByte() throws IOException
    {
        byte B = 0;
        for (int i = 0; i < 8; i++)
        {
            int bit = readBit();
            B |= (byte) (bit << (7 - i));
        }
        return B;
    }

    public boolean endOfFile()
    {
        return endOfFile;
    }
}
