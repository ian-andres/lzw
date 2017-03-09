import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ianandresmclaughlin on 3/8/17.
 */
public class LZWCompressor {


    public static void main(String[] args) throws Exception {
        System.out.println("Please enter file name");
        Scanner s = new Scanner(System.in);
        String filePattern = ".+\\.(txt|lzw)";
        String filePath = s.next(filePattern);
        Pattern p = Pattern.compile(filePattern);
        Matcher m = p.matcher(filePath);
        m.matches();
        String fileType = m.group(1);
        System.out.println(fileType);
        LZWCompressor compressor = new LZWCompressor();
        Action action = getAction(fileType);
        System.out.println(action + " " + m.group(0));

        if(action.equals(Action.COMPRESS)) {
            compressor.compress(filePath);
        } else {
            compressor.decompress(filePath);
        }

    }

    private static Action getAction(String fileType) {
        Action action;
        if(fileType.equals("txt")){
            action = Action.COMPRESS;
        } else {
            action = Action.DECOMPRESS;
        }
        return action;
    }

    private void decompress(String filePath) {
    }

    private void compress(String filePath) {
    }

    enum Action {
        COMPRESS("Compressing"),
        DECOMPRESS("Decompressing");
        String message;
        Action(String message){
            this.message = message;
        }
        @Override
        public String toString(){
            return this.message;
        }
    }
}