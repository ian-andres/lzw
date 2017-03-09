import java.util.Arrays;
import java.util.List;


/**
 * Created by ianandresmclaughlin on 2/5/17.
 */
public class LZWTest {
    LZW lzw = new LZW();

    @org.junit.Test
    public void testCompression() throws Exception {
        //Assume each charter is represented by a byte, 8 bits
        //Length of output is number of bits compressed to
        List<String> inputs = getStrings();
        for(String input: inputs) {
            String output = lzw.encode(input);
            if(input.length()>100){
                System.out.println("Input "+input.substring(0,100) + "... of length "+input.length()+
                        " was compressed by " + (input.length() * 8 - output.length()) + " bits");
            }else{
                System.out.println("Input "+input + "... of length "+input.length()+  " was compressed by "
                        + (input.length() * 8 - output.length()) + " bits");
            }
            assert (input.length() * 8 > output.length());
        }
    }

    @org.junit.Test
    public void testDecode() throws Exception {
        List<String> inputs = getStrings();

        for(String input: inputs) {
            String output = lzw.encode(input);
            output = lzw.decode(output);
            if(output.length()>100){
                System.out.println(output.substring(0,100) + "... should equal " + output.substring(0,100)+ "...");
            }else{
                System.out.println(output + " should equal " + output);
            }
            assert (output.equals(input));
        }

    }

    @org.junit.Test(expected=IllegalArgumentException.class)
    public void testIllegalArgument(){
        System.out.println("Attempting to encode illegal text (lowercase)");
        lzw.encode("cantdoit");
    }

    private List<String> getStrings() {
        StringBuffer stringBuffer = new StringBuffer();
        for(String character:lzw.CHARACTERS){
            for(int i = 0; i <100000; i++){
                stringBuffer.append(character);
            }
        }
        return Arrays.asList("FOOFOOFOO","MISSISSIPPI","BANANA","BANANANA",stringBuffer.toString());
    }

}