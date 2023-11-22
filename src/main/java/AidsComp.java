import Structures.HuffmanTree;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

public class AidsComp
{
    static HashMap<Byte, Integer> getFrequencyMap(String filename) throws IOException
    {
        HashMap<Byte, Integer> map = new HashMap<>();
        try(FileInputStream file = new FileInputStream(filename))
        {
            while (true)
            {
                int readResult = file.read();
                if (readResult == -1)
                    break;

                byte byteRead = (byte) readResult;
                map.merge(byteRead, 1, Integer::sum);
            }
        }

        return map;
    }

    public static void main(String[] args) throws IOException
    {
        HashMap<Byte, Integer> byteFreqMap = getFrequencyMap("data/empty.txt");
        HuffmanTree tree = new HuffmanTree(byteFreqMap);

        tree.printInOrder();
    }
}
