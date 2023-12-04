import AidsComp.AidsComp;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.*;

import java.nio.file.Files;
import java.nio.file.Paths;

public class CompDecompTest
{
    final String compressedFileName = "compressed.tmp";
    final String decompressedFilename = "decompressed.tmp";

    private boolean filesAreEqual(String path1, String path2) throws IOException
    {
        try (RandomAccessFile file1 = new RandomAccessFile(path1, "r");
             RandomAccessFile file2 = new RandomAccessFile(path2, "r"))
        {

            if (file1.length() != file2.length())
                return false;

            for (;;)
            {
                try
                {
                    byte b1 = file1.readByte();
                    byte b2 = file2.readByte();
                    if (b1 != b2)
                        return false;
                }
                catch (EOFException EOF)
                {
                    return true;
                }
            }
        }
    }
    private boolean performTest(String inputFilename) throws IOException
    {
        String[] compressArgs = {"-c", inputFilename, compressedFileName};
        AidsComp.main(compressArgs);

        String[] decompressArgs = {"-d", compressedFileName, decompressedFilename};
        AidsComp.main(decompressArgs);

        boolean result;
        try
        {
            result = filesAreEqual(inputFilename, decompressedFilename);
        }
        catch (IOException e)
        {
            result = false;
        }

        Files.delete(Paths.get(compressedFileName));
        Files.delete(Paths.get(decompressedFilename));

        return result;
    }

    @Test
    public void emptyTest() throws IOException
    {
        assertTrue(performTest("test_data/empty.txt"));
    }
    @Test
    public void niemanieTest() throws IOException
    {
        assertTrue(performTest("test_data/niemanie.txt"));
    }

    @Test
    public void panTadeuszTest() throws IOException
    {
        assertTrue(performTest("test_data/pan-tadeusz.txt"));
    }

    @Test
    public void jamnikTest() throws IOException
    {
        assertTrue(performTest("test_data/jamnik.png"));
    }
}
