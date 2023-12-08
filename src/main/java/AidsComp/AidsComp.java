package AidsComp;

import static AidsComp.Compressor.compress;
import static AidsComp.Decompressor.decompress;

import java.io.IOException;

public class AidsComp
{
    public static void main(String[] args)
    {
        final String usage = "aids_comp -[c or d] [input filename] [output filename]";
        if (args.length < 3)
        {
            System.err.println(usage);
            return;
        }

        String option = args[0];
        String inputFilename = args[1];
        String outputFilename = args[2];

        if (inputFilename.equals(outputFilename))
        {
            System.err.println("Input filename cannot be same as output.");
            return;
        }

        switch (option)
        {
            case "-c":
            {
                try {compress(inputFilename, outputFilename);}
                catch (IOException exception) {System.err.println(exception.getMessage());}
                break;
            }
            case "-d":
            {
                try {decompress(inputFilename, outputFilename);}
                catch (IOException exception) {System.err.println(exception.getMessage());}
                break;
            }
            default:
            {
                System.err.println("Unknown option " + option + ".");
                System.err.println("Usage: " + usage);
            }
        }
    }
}
