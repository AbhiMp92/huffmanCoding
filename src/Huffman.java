


import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Huffman {

    public static void main(String[] args) throws FileNotFoundException {

        Scanner in = new Scanner(System.in);

        //creating 2 queues
        Queue<BinaryTree<Pair>> S = new LinkedList<>();
        Queue<BinaryTree<Pair>> T = new LinkedList<>();
        boolean t = false;

        //taking input of file name
        System.out.println("Enter the name of the file with letters and probability:");
        String file = in.nextLine();
        File f1 = new File(file);

        Scanner kb = new Scanner(f1);

        Scanner sc = new Scanner(System.in);
        //taking input of line which we need to encode
        System.out.println("Building the Huffman tree ….\n" + "Huffman coding completed.");
        System.out.println();
        System.out.print("Enter a line of text (uppercase letters only): ");
        String line = sc.nextLine();

        //if there is any lower case letter in our input printing error message
        for (int i = 0; i < line.length(); i++) {
            Character c = line.charAt(i);
            if (Character.isLowerCase(c)) {
                t = true;
                System.out.println("Please enter all letters in UPPERCASE");
                break;
            }


        }

        //creating binary tree of pair object to store every input from input file
        BinaryTree<Pair> curr ;
        Pair p;


        while (kb.hasNext()) {

            curr = new BinaryTree<>();
            //getting character and its probability and storing it into pair object
            String st = kb.next();
            double probability = kb.nextDouble();
            p = new Pair(st.charAt(0), probability);
            curr.makeRoot(p);
            //adding binary tree into queue
            S.add(curr);
        }

        in.close();
        //calling huffManTree method to create huffManTree
        huffManTree(S, T);
        //calling findEncoding method to get binaryString output
        String[] binaryString = findEncoding(T.peek());




        //Storing A to Z character in array of character
        Character[] ch = new Character[26];
        Character c = 'A';
        for (int i = 0; i < 26; i++) {

            ch[i] = c;
            c++;
        }

        //creating hashmap to store character and its corresponding string output
        HashMap<Character, String> map = new HashMap<Character, String>();

        //storing characters from A to Z and its corresponding String value into the hashmap
        for (int i = 0; i < ch.length; i++) {
            map.put(ch[i], binaryString[i]);
        }


        //if input entered by user is in upperCase printing output
        if (!t) {

            //call to encode method
            String[] answer = encode(map, line);

            System.out.print("Here’s the encoded line: ");
            for (int i = 0; i < answer.length; i++) {
                System.out.print(answer[i]);
            }

            System.out.println();

            //call to decode method
            String[] decode = decode(map, answer);
            System.out.print("The decoded line is: ");
            for (int i = 0; i < decode.length; i++) {
                System.out.print(decode[i]);
            }

        }
    }

    /**
     *
     * @param map hashmap which has all characters as key and corresponding string value as value
     * @param line input entered by user
     * @return array of String which represents encoded String according to input entered by user
     * this method compares string entered by user character by character and stores output in array of string
     * and return that array
     */
    public static String[] encode(HashMap<Character,String> map, String line){

        String[] answer = new String[line.length()];

        //for loop to compare string character by character and if it matches it will store corresponding
        //string value by accessing value in hashmap
        for (int i = 0; i < line.length(); i++) {

            char check = 'A';
            for (int j = 0; j <= 26; j++) {
                if(line.charAt(i) == check){
                    answer[i] = map.get(check);
                }
                //if there is white space in the input entered by user storing white space in the output
                if(line.charAt(i) == ' '){
                    answer[i] = " ";
                    break;
                }
                check++;
            }
        }

        return answer;

    }

    /**
     *
     * @param map hashmap which has all characters as key and corresponding string value as value
     * @param line return value of encode method (encoded string of the input entered by user)
     * @return  decoded value of String (should be same as the string entered by user)
     *
     */
    public static String[] decode(HashMap<Character,String> map , String[] line){
        String[] answer = new String[line.length];

        for (int i = 0; i < line.length; i++) {

            char check = 'A';
            for (int j = 0; j <= 26; j++) {
                if(line[i].equals(map.get(check))){
                    answer[i] = check + "";
                }
                if(line[i].equals(" ")){
                    answer[i] = " ";
                }
                check++;
            }
        }

        return answer;

    }

    /**
     *
     * @param S queue filled with BinaryTree<Pair> object
     * @param T empty queue
     * this method creates huffManTree
     */

    public static void huffManTree(Queue<BinaryTree<Pair>> S, Queue<BinaryTree<Pair>> T ){


        BinaryTree<Pair> A = new BinaryTree<>();
        BinaryTree<Pair> B = new BinaryTree<>();

        //while loop that iterates until S is empty
        while(!S.isEmpty()){
            //if T is empty remove first two element from S and store into A and B respectively
            if(T.isEmpty()){
                A = S.remove();
                B = S.remove();
            }

            if (!T.isEmpty()){
               //if first element of s is smaller than the first element of T, A = first element of S
               if(S.peek().getData().getProb()<=T.peek().getData().getProb()){
                   A = S.remove();
               }

               //if first element of T is bigger , A = first element of T
               else if(S.peek().getData().getProb()>T.peek().getData().getProb()){
                   A = T.remove();
               }

               //if there is no element in T, B = first element of S
               if(T.isEmpty() && !S.isEmpty()){
                   B=S.remove();
               }

               //if there is no element in S , B = first element of T
               else if(!T.isEmpty() && S.isEmpty()){
                   B=T.remove();
               }


               else{
                  //if first element of S is smaller, B = first element of S
                   if(S.peek().getData().getProb()<=T.peek().getData().getProb()){
                       B = S.remove();
                   }
                  //if first element of S is bigger, B = first element of T
                   else if(S.peek().getData().getProb()>T.peek().getData().getProb()){
                       B = T.remove();
                   }

               }

            }
            //creating new binary tree and amaking pair object with the probability equal to probability of A +
            //probability of B
            BinaryTree<Pair> P = new BinaryTree<>();
            Pair pair = new Pair('0',A.getData().getProb()+B.getData().getProb());
            P.makeRoot(pair);
            //attaching A to the left of the tree
            P.attachLeft(A);
            //attaching B to the right of the tree
            P.attachRight(B);

            //adding tree to the queue T
            T.add(P);

        }

        while(T.size() > 1){

            BinaryTree<Pair> P = new BinaryTree<>();

            //removing first two elements from T and making new pair object same as above
            A = T.remove();
            B = T.remove();
            Pair pair = new Pair('0',A.getData().getProb()+B.getData().getProb());

            P.makeRoot(pair);
            P.attachLeft(A);
            P.attachRight(B);

            T.add(P);
        }

    }

    private static String[] findEncoding(BinaryTree<Pair> bt){
        String[] result = new String[26];
        findEncoding(bt, result, "");
        return result;
    }


    private static void findEncoding(BinaryTree<Pair> bt, String[] a, String prefix){
        // test is node/tree is a leaf
        if (bt.getLeft()==null && bt.getRight()==null){
            a[bt.getData().getValue() - 65] = prefix;
        }
        // recursive calls
        else{
            findEncoding(bt.getLeft(), a, prefix+"0");
            findEncoding(bt.getRight(), a, prefix+"1");
        }
    }



}

