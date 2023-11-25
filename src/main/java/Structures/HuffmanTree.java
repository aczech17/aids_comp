package Structures;
import java.util.HashMap;

public class HuffmanTree
{
    private class Node implements Comparable<Node>
    {
        Byte value;
        int frequency;

        Node left, right;

        public Node(Byte value, int frequency)
        {
            this.value = value;
            this.frequency = frequency;
            this.left = null;
            this.right = null;
        }
        public Byte getValue()
        {
            return value;
        }
        public int getFrequency()
        {
            return frequency;
        }

        @Override
        public int compareTo(Node o)
        {
            return this.frequency - o.frequency;
        }
    }

    Node root;

    private Node joinNodes(Node leftNode, Node rightNode)
    {
        Node newNode = new Node(null, leftNode.getFrequency() + rightNode.getFrequency());
        newNode.left = leftNode;
        newNode.right = rightNode;

        return newNode;
    }

    public HuffmanTree(HashMap<Byte, Integer> byteFrequencies)
    {
        MinimalPriorityQueue<Node> queue = new MinimalPriorityQueue<Node>();

        for (byte byteValue: byteFrequencies.keySet())
        {
            int frequency = byteFrequencies.get(byteValue);
            Node node = new Node(byteValue, frequency);
            queue.insert(node);
        }

        while (queue.size() > 1)
        {
            Node leftNode = queue.extractMin();
            Node rightNode = queue.extractMin();

            Node newNode = joinNodes(leftNode, rightNode);
            queue.insert(newNode);
        }

        root = queue.extractMin();
    }

    public boolean isEmpty()
    {
        return root == null;
    }

    public HashMap<Byte, BitVector> getBytesEncoding()
    {
        if (root == null)
            return null;

        HashMap<Byte, BitVector> map = new HashMap<>();

        if (root.left == null) // only one node
        {
            BitVector zeroBit = new BitVector();
            zeroBit.pushBit(0);

            map.put(root.getValue(), zeroBit);
            return map;
        }

        BitVector code = new BitVector();
        getBytesEncodingRecursive(root, code, map);

        return map;
    }

    private void getBytesEncodingRecursive(Node node, BitVector code, HashMap<Byte, BitVector> codes)
    {
        if (node.left != null)
        {
            code.pushBit(0);
            getBytesEncodingRecursive(node.left, code, codes);
        }
        if (node.right != null)
        {
            code.pushBit(1);
            getBytesEncodingRecursive(node.right, code, codes);
        }
        else // is a leaf
        {
            codes.put(node.getValue(), code.clone());
        }
        code.popBit();
    }

    public BitVector getTreeEncoding()
    {
        BitVector encoding = new BitVector();
        getTreeEncodingRecursive(root, encoding);

        return encoding;
    }

    private void getTreeEncodingRecursive(Node node, BitVector vector)
    {
        if (node.left == null) // is a leaf
        {
            vector.pushBit(1);
            vector.pushByte(node.getValue());
        }
        else
        {
            vector.pushBit(0);
            getTreeEncodingRecursive(node.left, vector);
            getTreeEncodingRecursive(node.right, vector);
        }
    }

    // **** DEBUG ****
    public void printInOrder()
    {
        if (isEmpty())
            return;
        printInOrderFromNode(root);
    }

    private void printInOrderFromNode(Node node)
    {
        if (node.left != null)
            printInOrderFromNode(node.left);

        if (node.getValue() != null)
            System.out.print((char) node.getValue().byteValue() + " ");
        System.out.println(node.getFrequency());

        if (node.right != null)
            printInOrderFromNode(node.right);
    }

    // **** ****
}
