package IOUtil;

import Structures.BitVector;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

public class BitWriter
{
    String outputFilename;
    private final BufferedOutputStream output;
    private byte currentByte;
    private int byteOffset;

    final int BUFFER_CAPACITY = 64 * 1024 * 1024;
    byte[] buffer;
    int bufferSize;

    public BitWriter(String filename) throws IOException
    {
        outputFilename = filename;
        output = new BufferedOutputStream(new FileOutputStream(filename));
        currentByte = 0;
        byteOffset = 0;

        buffer = new byte[BUFFER_CAPACITY];
        bufferSize = 0;
    }

    public void writeBit(int bit) throws IOException
    {
        currentByte |= (byte) ((byte)bit << (7 - byteOffset));

        byteOffset++;
        if (byteOffset == 8)
        {
            writeByte(currentByte);
            currentByte = 0;
            byteOffset = 0;
        }
    }

    public void writeByte(byte B) throws IOException
    {
        if (bufferSize == BUFFER_CAPACITY)
        {
            output.write(buffer, 0, bufferSize);
            bufferSize = 0;
        }
        buffer[bufferSize++] = B;
    }

    public void finish() throws IOException
    {
        if (byteOffset > 0)
            writeByte(currentByte);

        if (bufferSize > 0)
            output.write(buffer, 0, bufferSize);

        output.flush();
        output.close();
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
        RandomAccessFile file = new RandomAccessFile(outputFilename, "rw");
        byte zeroByte = file.readByte();

        int padding = (8 - byteOffset) % 8;
        zeroByte |= (byte)(padding << 5);

        file.seek(0);
        file.writeByte((int)zeroByte);
    }
}
