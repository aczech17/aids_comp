package AidsComp;

import IOUtil.BitWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Objects;

public class AidsComp
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

    private static void compress(String inputFilename, String outputFilename) throws IOException
    {
        if (fileExists(outputFilename))
        {
            String message = "File " + outputFilename + " already exists.";
            throw new IOException(message);
        }

        RandomAccessFile input = new RandomAccessFile(inputFilename, "r");
        BitWriter output = new BitWriter(outputFilename);

        Compressor.compress(input, output);
    }

    private static void decompress(String inputFilename, String outputFilename) throws IOException
    {
        Decompressor decompressor = new Decompressor(inputFilename, outputFilename);
        decompressor.decompress();
    }

    public static void main(String[] args)
    {
        final String usage = "aids_comp -[c or d] [input filename] [output filename]";
        if (args.length < 3)
        {
            System.out.println(usage);
            return;
        }

        String option = args[0];
        String inputFilename = args[1];
        String outputFilename = args[2];

        switch (option)
        {
            case "-c":
            {
                try {compress(inputFilename, outputFilename);}
                catch (IOException exception) {System.out.println(exception.getMessage());}
                break;
            }
            case "-d":
            {
                try {decompress(inputFilename, outputFilename);}
                catch (IOException exception) {System.out.println(exception.getMessage());}
                break;
            }
            default:
            {
                System.out.println("Unknown option " + option + ".");
                System.out.println("Usage: " + usage);
            }
        }
    }
}
