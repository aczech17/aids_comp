package IOUtil;

import java.io.*;

public class BitReader
{
    private byte currentByte;
    ///private int currentByteNumber;
    //private int byteOffset;
    private BufferedReader input;
    private int paddingSize;

    private final long fileSize;
    private long bitNumber = 0;

    private int BUFFER_CAPACITY = 2;
    private char[] buffer;
    private int bufferSize = 0;
    private int bytesReadFromBuffer = 0;

    public BitReader(String filename) throws IOException
    {
        currentByte = 0;
        // currentByteNumber = -1;
        // byteOffset = 8; // So that the first read of bit reads new byte.

        fileSize = new RandomAccessFile(filename, "r").length() * 8;
        paddingSize = 0;

        input = new BufferedReader(new FileReader(filename));

        buffer = new char[BUFFER_CAPACITY];
    }

    public int readBit() throws IOException
    {
        long byteOffset = bitNumber % 8;
        if (byteOffset == 0)
        {
            currentByte = getNextByte();
            //byteOffset = 0;
        }
        int bit = (currentByte >> (7 - byteOffset)) & 1;
        bitNumber++;

        return bit;
    }

    private byte getNextByte() throws IOException
    {
        if (bytesReadFromBuffer == bufferSize)
        {
            bufferSize = input.read(buffer);
            bytesReadFromBuffer = 0;
        }

        return (byte) buffer[bytesReadFromBuffer++];
    }

    public byte readByte() throws IOException
    {
        if (bitNumber % 8 == 0)
        {
            byte nextByte = getNextByte();
            bitNumber += 8;
            return nextByte;
        }

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
        return bitNumber == fileSize;
    }

    public void setPaddingSize() throws IOException
    {
        paddingSize = (readBit() << 2) | (readBit() << 1) | readBit(); // ??? operator precedence
    }
}
