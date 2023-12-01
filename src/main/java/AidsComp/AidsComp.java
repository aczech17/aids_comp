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
