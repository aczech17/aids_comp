package AidsComp.IOUtil;

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
        currentByteNumber = -1;
        byteOffset = 8; // So that the first read of bit reads new byte.

        input = new RandomAccessFile(filename, "r");

        //paddingSize = (readBit() << 2) | (readBit() << 1) | readBit(); // ??? operator precedence
        paddingSize = 0;

        endOfFile = (input.length() == 0);
    }

    public int readBit() throws IOException
    {
        if (byteOffset == 8)
        {
            currentByte = input.readByte();
            currentByteNumber++;
            byteOffset = 0;
        }

        int bit = (currentByte >> (7 - byteOffset)) & 1;
        byteOffset++;


        if ((currentByteNumber == input.length() - 1) && (byteOffset > (7 - paddingSize)))
            endOfFile = true;

        return bit;
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

    public void setPaddingSize() throws IOException
    {
        paddingSize = (readBit() << 2) | (readBit() << 1) | readBit(); // ??? operator precedence
    }
}
