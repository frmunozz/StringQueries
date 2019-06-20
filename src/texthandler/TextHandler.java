package texthandler;

import java.nio.file.Paths;

public class TextHandler {
    private static final String path = Paths.get("").toAbsolutePath().toString();

    /**
     * define a preprocessing of the Text, passing all characters to lowercase,
     * replacing all forbidden characters by blank space and erasing all additional blank spaces
     * leaving only one blank space between two words.
     * @param text the original text
     * @return the cleared text
     */
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
}
