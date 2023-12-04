package AidsComp;

import IOUtil.BitReader;
import IOUtil.BitWriter;
import Structures.AssociativeArray.AssociativeArray;
import Structures.BitVector;
import Structures.HuffmanTree;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Compressor
{
    private static boolean fileExists(String filename)
    {
        try
        {
            RandomAccessFile file = new RandomAccessFile(filename, "r");
        }
        catch (FileNotFoundException exception)
        {
            return false;
        }
        return true;
    }

    private static AssociativeArray<Byte, Integer> getByteFrequencies(BitReader input) throws IOException
    {
        AssociativeArray<Byte, Integer> frequencies = new AssociativeArray<>();

        while (!input.endOfFile())
        {
            byte byteRead = input.readByte();
            Integer frequency = frequencies.get(byteRead);

            if (frequency == null)
                frequencies.put(byteRead, 1);
            else
                frequencies.put(byteRead, frequency + 1);

        }

        return frequencies;
    }

    public static void compress(String inputFilename, String outputFilename) throws IOException
    {
        if (fileExists(outputFilename))
        {
            String message = "File " + outputFilename + " already exists.";
            throw new IOException(message);
        }

        BitReader input = new BitReader(inputFilename);
        BitWriter output = new BitWriter(outputFilename);

        if (input.endOfFile())
            return; // empty input => empty output

        var byteFrequencies = getByteFrequencies(input);
        HuffmanTree huffmanTree = new HuffmanTree(byteFrequencies);
        var bytesEncoding = huffmanTree.getBytesEncoding();
        BitVector treeEncoding = huffmanTree.getTreeEncoding();

        // write 3 bits for future padding writing
        output.writeBit(0);
        output.writeBit(0);
        output.writeBit(0);

        output.writeBitVector(treeEncoding);

        input = new BitReader(inputFilename); // reset input


        while (!input.endOfFile())
        {
            byte nextByte = input.readByte();
            BitVector code = bytesEncoding.get(nextByte);
            output.writeBitVector(code);
        }

        input.finish();

        output.finish();
        output.writePadding();
    }
}
