
import java.util.*;

/**
 * Created by ianandresmclaughlin on 2/6/17.
 *
 * Class that mimics LZW compression. Two public methods: encode,decode
 * encode takes string arg and outputs  string representation of compressed binary values.
 * Also stores additional number at beginning of String as an 8 bit value which value represents the compressed
 * character's bit length
 *
 * decode takes the outputted string of binary values and returns back to original input
 *
 * This implementation will not compress strings with 2 or less characters because of the bitLength value that
 * the encode method stores with the generated output. However, because the bit length adapts to the size of the
 * input it is able to compress very large inputs
 *
 * Encoding is done in O(n) time
 * Decoding is done in O(n) time
 * Memory Usage is O(n)
 */

public class LZW {

    static final HashSet<String> CHARACTERS = new HashSet<String>(Arrays.asList("A", "B", "C", "D", "E", "F", "G",
            "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"));


    public String encode(String input) throws  IllegalArgumentException{
        int maxLength = input.length() + CHARACTERS.size();
        int logLength = (int) Math.ceil(log2(maxLength));
        int size = (int) Math.pow(2,logLength);

        String binaryLength = toBinaryString(256 + logLength);

        Map<String,String> dictionary = createBlankEncodingDictionary(size);
        size+=dictionary.size();
        String current = "";
        StringBuffer output = new StringBuffer(binaryLength);
        for(int i = 0; i < input.length(); i++){
            String next = input.substring(i, i + 1);
            if(!CHARACTERS.contains(next))
                throw new IllegalArgumentException("Input can only contain characters in set"+CHARACTERS.toString());
            String key = current+next;
            if(dictionary.containsKey(key)){
                current=key;
            }else{
                dictionary.put(key,toBinaryString(size++));
                output.append(dictionary.get(current));
                current=next;
            }
        }
        output.append(dictionary.get(current));
        return output.toString();
    }

    public String decode(String encodedText){
        int bitLength = parseInt(encodedText.substring(0,8));
        encodedText = encodedText.substring(8);
        int size = (int) Math.pow(2,bitLength);

        Map<String,String> dictionary = createBlankDecodingDictionary(size);
        size+=dictionary.size();

        StringBuffer output = new StringBuffer();
        String previous = dictionary.get(encodedText.substring(0,bitLength)),encodedString,decodedString,val;
        output.append(previous);

        for(int i = 1; i< encodedText.length()/ bitLength; i ++){
            int beginIndex = i * bitLength;
            encodedString = encodedText.substring(beginIndex,beginIndex+bitLength);
            if(encodedString.equals(toBinaryString(size))){
                decodedString = previous+previous.substring(0,1);
            }else {
                decodedString = dictionary.get(encodedString);
            }
            output.append(decodedString);
            val = previous+decodedString.substring(0,1);
            dictionary.put(toBinaryString(size++),val);

            previous=decodedString;


        }

        return output.toString();
    }


    /*
     Need to create new blank dictionary when encoding and decoding.
   */
    private HashMap<String,String> createBlankEncodingDictionary(int start){
        HashMap<String, String> dictionary = new HashMap<String, String>();
        for(String character: CHARACTERS){
            dictionary.put(character,toBinaryString(start++));
        }
        return dictionary;
    }

    private HashMap<String,String> createBlankDecodingDictionary(int start){
        HashMap<String, String> dictionary = new HashMap<String, String>();
        for(String character: CHARACTERS){
            dictionary.put(toBinaryString(start++),character);
        }
        return dictionary;
    }

    /*Methods to convert integers to and from binary representation string */

    private String toBinaryString(int start) {
        return Integer.toBinaryString(start).substring(1);
    }

    private int parseInt(String binaryString) {
        char[] reversed = new StringBuffer(binaryString).reverse().toString().toCharArray();
        int val = 0,bin=0;
        for(int i = 0;i<reversed.length;i++){
            switch (reversed[i]){
                case '0':bin=0;
                    break;
                case '1':bin=1;
                    break;
            }
            val+=Math.pow(2,i)*bin;
        }
        return val;
    }

    private double log2(int size) {
        return  (Math.log(size)/Math.log(2));
    }
}
