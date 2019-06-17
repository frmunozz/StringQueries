package texthandler;

import java.nio.file.Paths;

public class TextHandler {
    private static final String path = Paths.get("").toAbsolutePath().toString();
    public static String preProcesing(String text){
        String forbiddenRegex = "[\\[\\]()\"{}.,;:'\n\t\r ]";
        return text.replaceAll(forbiddenRegex, "").toLowerCase();
    }

    public static String english(){
        return path + "/src/texthandler/data/english.50MB";
    }
}
