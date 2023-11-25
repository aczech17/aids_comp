import Structures.HuffmanTree;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;

public class AidsComp
{
    public static void main(String[] args) throws IOException
    {
        RandomAccessFile file = new RandomAccessFile("test.txt", "rw");

        file.writeByte((int)'A');
        file.writeByte((int)'B');
        file.writeByte((int)'C');

        file.seek(0);
        file.writeByte((int)'X');
    }
}
