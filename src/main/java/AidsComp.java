import Structures.HuffmanTree;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;

public class AidsComp
{
    public static void main(String[] args)
    {
        RandomAccessFile input = null;
        try
        {
            input = new RandomAccessFile("data/empty.txt", "rw");
        }
        catch (FileNotFoundException exception)
        {
            System.out.println("file not found :/");
            return;
        }

        BitWriter output = null;
        try
        {
            output = new BitWriter("output");
        }
        catch (IOException e)
        {
            System.out.println("nie utworzono pliku output");
            return;
        }

        try
        {
            Compressor.compress(input, output);
        }
        catch (IOException exception)
        {
            System.out.println(exception.getMessage());
            return;
        }
    }
}
