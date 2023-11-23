import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class FileReader
{
    static HashMap<Byte, Integer> getByteFrequencies(String filename) throws FileNotFoundException
    {
        HashMap<Byte, Integer> frequencies = new HashMap<>();

        File file = new File(filename);
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextByte())
        {
            byte byteRead = scanner.nextByte();
            frequencies.merge(byteRead, 1, Integer::sum);
        }

        return frequencies;
    }
}
