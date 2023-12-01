import AidsComp.AidsComp;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
//        RandomAccessFile file1 = null;
//        RandomAccessFile file2 = null;
//        try
//        {
//            file1 = new RandomAccessFile(path1, "r");
//            file2 = new RandomAccessFile(path2, "r");
//        }
//        catch (FileNotFoundException exception)
//        {
//            System.out.println("nie ma pliku");
//            return false;
//        }
//
//        if (file1.length() != file2.length())
//            return false;

        try (RandomAccessFile file1 = new RandomAccessFile(path1, "r");
             RandomAccessFile file2 = new RandomAccessFile(path2, "r"))
        {

            if (file1.length() != file2.length())
                return false;

            for (; ; )
            {
                try
                {
                    byte b1 = file1.readByte();
                    byte b2 = file2.readByte();
                    if (b1 != b2)
                    {
                        System.out.println(b1 + " " + b2);
                        return false;
                    }
                } catch (EOFException EOF)
                {
                    return true;
                }
            }
        }
    }
    private boolean performTest(String inputFilename)
    {
        String[] compressArgs = {"-c", inputFilename, compressedFileName};
        AidsComp.main(compressArgs);


        String[] decompressArgs = {"-d", compressedFileName, decompressedFilename};
        AidsComp.main(decompressArgs);

        boolean success = false;
        try
        {
            success = filesAreEqual(inputFilename, decompressedFilename);
        }
        catch (IOException e)
        {
            return false;
        }

        return success;
    }

    @Test
    public void emptyTest()
    {
        assertTrue(performTest("test_data/empty.txt"));
    }
    @Test
    public void niemanieTest()
    {
        assertTrue(performTest("test_data/niemanie.txt"));
    }

    @AfterEach
    public void cleanup()
    {
        try {
            Thread.sleep(1000); // Opóźnienie 1 sekundy
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            Files.delete(Paths.get(compressedFileName));
            Files.delete(Paths.get(decompressedFilename));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
