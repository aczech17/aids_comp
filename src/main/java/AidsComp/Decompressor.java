package AidsComp;

import IOUtil.BitReader;
import Structures.AssociativeArray.AssociativeArray;
import Structures.BitVector;
import Structures.HuffmanTree;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Decompressor
{
    private static Byte getByteFromEncoding(AssociativeArray<Byte, BitVector> bytesEncoding, BitVector bitVector)
    {
        for (byte B: bytesEncoding)
        {
            if (bytesEncoding.get(B).equals(bitVector))
                return B;
        }
        return null;
    }

    private static byte getNextByte(AssociativeArray<Byte, BitVector> bytesEncoding, BitReader reader) throws IOException
    {
        BitVector nextCodeWord = new BitVector();

        while (true)
        {
            nextCodeWord.pushBit(reader.readBit());
            Byte byteFound = getByteFromEncoding(bytesEncoding, nextCodeWord);
            if (byteFound != null)
                return byteFound;
        }
    }

    public static void decompress(String inputFilename, String outputFilename) throws IOException
    {
        BitReader reader = new BitReader(inputFilename);
        if (!reader.endOfFile()) // file is not empty
        {
            reader.setPaddingSize();
        }

        HuffmanTree tree = new HuffmanTree(reader);
        AssociativeArray<Byte, BitVector> bytesEncoding = tree.getBytesEncoding();
        RandomAccessFile output = new RandomAccessFile(outputFilename, "rw");

        while (!reader.endOfFile() && !reader.paddingReached())
        {
            byte nextByte = getNextByte(bytesEncoding, reader);
            output.writeByte(nextByte);
        }
    }
}
