import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

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

    public static void main(String[] args)
    {
        if (args.length < 2)
        {
            final String usage = "aids_comp [input filename] [output filename]";
            System.out.println(usage);
            return;
        }

        String inputFilename = args[0];
        String outputFilename = args[1];

        if (fileExists(outputFilename))
        {
            System.out.println("File " + outputFilename + " already exists.");
            return;
        }

        RandomAccessFile input = null;
        try
        {
            input = new RandomAccessFile(inputFilename, "r");
        }
        catch (FileNotFoundException exception)
        {
            System.out.println("File " + inputFilename + " does not exist.");
            return;
        }

        BitWriter output = null;
        try
        {
            output = new BitWriter(outputFilename);
        }
        catch (IOException e)
        {
            System.out.print("Could not open file " + outputFilename + ". ");
            System.out.println(e.getMessage());
            return;
        }

        try
        {
            Compressor.compress(input, output);
        }
        catch (IOException exception)
        {
            System.out.print("Could not compress file. ");
            System.out.println(exception.getMessage());
        }
    }
}
