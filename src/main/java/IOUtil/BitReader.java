package IOUtil;

import java.io.*;

public class BitReader
{
    private byte currentByte;
    private final BufferedInputStream input;
    private int paddingSize;

    private final long fileSize;
    private long bitNumber;

    private final int BUFFER_CAPACITY = 64 * 1024 * 1024;
    private final byte[] buffer;
    private int bufferSize;
    private int bytesReadFromBuffer;

    public BitReader(String filename) throws IOException
    {
        currentByte = 0;
        fileSize = new RandomAccessFile(filename, "r").length() * 8;
        paddingSize = 0;
        bitNumber = 0;

        input = new BufferedInputStream(new FileInputStream(filename));

        buffer = new byte[BUFFER_CAPACITY];
        bufferSize = 0;
        bytesReadFromBuffer = 0;
    }

    public int readBit() throws IOException
    {
        long byteOffset = bitNumber % 8;
        if (byteOffset == 0)
            currentByte = getNextByte();

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

        return buffer[bytesReadFromBuffer++];
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
        paddingSize = (readBit() << 2) | (readBit() << 1) | readBit();
    }

    public boolean paddingReached()
    {
        return bitNumber > (fileSize - 1 - paddingSize);
    }
}
