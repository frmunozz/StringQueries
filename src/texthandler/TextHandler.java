package texthandler;

import java.nio.file.Paths;

public class TextHandler {
    private static final String path = Paths.get("").toAbsolutePath().toString();

    public static String preProcesing(String text){
        String forbiddenRegex = "[^0-9a-z \\-#%?]";
        text = text.toLowerCase();
        text = text.replaceAll(forbiddenRegex, " ");
        text = text.trim().replaceAll(" +", " ");
        return text;
    }

    public static String english(){
        return path + "/src/texthandler/data/english.50MB";
    }

//    public static void main(String[] args) {
//        String test = "dasasff\"fssdfdsf!sddads&&dsf         sdf";
//        System.out.println(test);
//        System.out.println(test.replaceAll("[^0-9a-z \\-#%&?]", ""));
//    }
}
