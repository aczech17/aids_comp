package AidsComp;

import Structures.AssociativeArray.AssociativeArray;
import Structures.BitVector;
import Structures.HuffmanTree;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Decompressor
{
    BitReader reader;
    AssociativeArray<Byte, BitVector> bytesEncoding;
    RandomAccessFile output;

    public Decompressor(String inputFilename, String outputFilename) throws IOException
    {
        reader = new BitReader(inputFilename);
        if (!reader.endOfFile()) // file is not empty
        {
            reader.setPaddingSize();
        }

        HuffmanTree tree = new HuffmanTree(reader);
        bytesEncoding = tree.getBytesEncoding();
        output = new RandomAccessFile(outputFilename, "rw");
    }

    private Byte getByteFromEncoding(BitVector bitVector)
    {
        for (byte B: bytesEncoding)
        {
            if (bytesEncoding.get(B).equals(bitVector))
                return B;
        }
        return null;
    }

    private byte getNextByte() throws IOException
    {
        BitVector nextCodeWord = new BitVector();

        while (true)
        {
            nextCodeWord.pushBit(reader.readBit());
            Byte byteFound = getByteFromEncoding(nextCodeWord);
            if (byteFound != null)
                return byteFound;
        }
    }

    public void decompress() throws IOException
    {
        while (!reader.endOfFile())
        {
            byte nextByte = getNextByte();
            output.writeByte((int)nextByte);
        }
    }
}
