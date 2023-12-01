package AidsComp;

import Structures.AssociativeArray.AssociativeArray;
import Structures.BitVector;
import Structures.HuffmanTree;

import java.io.EOFException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Compressor
{
    private static AssociativeArray<Byte, Integer> getByteFrequencies(RandomAccessFile input) throws IOException
    {
        AssociativeArray<Byte, Integer> frequencies = new AssociativeArray<>();

        for (;;)
        {
            try
            {
                byte byteRead = input.readByte();
                Integer frequency = frequencies.get(byteRead);

                if (frequency == null)
                    frequencies.put(byteRead, 1);
                else
                    frequencies.put(byteRead, frequency + 1);
            }
            catch (EOFException EOF)
            {
                break;
            }
        }

        return frequencies;
    }

    public static void compress(RandomAccessFile input, BitWriter output) throws IOException
    {
        if (input.length() == 0)
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

        input.seek(0);

        for (;;) //read the input file
        {
            try
            {
                byte nextByte = input.readByte();
                BitVector code = bytesEncoding.get(nextByte);

                output.writeBitVector(code);
            }
            catch (EOFException EOF)
            {
                output.writeTheRest();
                output.writePadding();
                break;
            }
        } // reading loop
    }
}