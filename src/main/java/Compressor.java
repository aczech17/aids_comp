import Structures.HuffmanTree;

import java.io.FileNotFoundException;

public class Compressor
{
    static void compress(String inputFilename, String outputFilename) throws FileNotFoundException
    {
        var byteFrequencies = FileReader.getByteFrequencies(inputFilename);
        HuffmanTree huffmanTree = new HuffmanTree(byteFrequencies);
        var codes = huffmanTree.getCodes();
    }
}
