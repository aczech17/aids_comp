package AidsComp;

import IOUtil.BitReader;
import IOUtil.BitWriter;
import Structures.AssociativeArray.AssociativeArray;
import Structures.BitVector;
import Structures.HuffmanTree;
import java.io.IOException;

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
        BitReader input = new BitReader(inputFilename);
        if (!input.endOfFile()) // file is not empty
        {
            input.setPaddingSize();
        }

        HuffmanTree tree = new HuffmanTree(input);
        AssociativeArray<Byte, BitVector> bytesEncoding = tree.getBytesEncoding();
        BitWriter output = new BitWriter(outputFilename);

        while (!input.endOfFile() && !input.paddingReached())
        {
            byte nextByte = getNextByte(bytesEncoding, input);
            output.writeByte(nextByte);
        }

        input.finish();
        output.finish();
    }
}
