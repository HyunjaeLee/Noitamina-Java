import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {

    public static String getContents (String url) {

        StringBuilder contents = new StringBuilder();

        try {

            URLConnection uc = new URL(url).openConnection();
            uc.addRequestProperty("User-Agent", "Mozilla/5.0");
            BufferedReader input=new BufferedReader(new InputStreamReader(uc.getInputStream(), "UTF-8"));
            String line;
            while ((line=input.readLine())!=null) {
                contents.append(line);
            }

            input.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return contents.toString();

    }

    public static int parse(String text, String regex, int group, Collection<String> collection){

        if (text == null || regex == null || collection == null) {
            return -1;
        }

        collection.clear();

        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            collection.add(matcher.group(group));
        }

        return collection.size();

    }

    public static int parse(String text, String regex, Collection<String> collection){

        return parse(text, regex, 1, collection);

    }

    public static Map<String, String> find(String word, Map<String, String> map) {

        Map<String, String> result = new HashMap<>();

        if(!word.equals("")){ // runs for-loop if 'word' is not empty
            for(String k : map.keySet()) {

                if (k.equalsIgnoreCase(word.trim())) { //EQUALS
                    result.clear();
                    result.put(k, map.get(k));
                } else if (k.toUpperCase().contains(word.trim().toUpperCase())) { //CONTAINS
                    result.put(k, map.get(k));
                }

            }
        }

        return result;

    }

}
