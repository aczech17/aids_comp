import Exceptions.EmptyQueue;
import Structures.HuffmanTree;

import java.util.HashMap;

public class AidsComp
{
    public static void main(String[] args)
    {
        HashMap<Byte, Integer> byteFreqMap = new HashMap<>();
        
        byteFreqMap.put((byte) 'A', 1);
        byteFreqMap.put((byte) 'B', 2);
        byteFreqMap.put((byte) 'C', 3);
        byteFreqMap.put((byte) 'D', 4);
        byteFreqMap.put((byte) 'E', 5);

        try
        {
            HuffmanTree tree = new HuffmanTree(byteFreqMap);
            tree.printInOrder();
        }
        catch (EmptyQueue emptyQueue)
        {
            System.out.println(emptyQueue.getMessage());
        }
    }
}
