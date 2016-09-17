import java.io.*;
import java.net.*;
import java.util.*;
import java.util.regex.*;

/**
 * Created by Hyunjae on 9/5/16.
 */

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

        for(String k : map.keySet()) {
            if (k.toUpperCase().equals(word.trim().toUpperCase())) {
                result.clear();
                result.put(k, map.get(k));
                break;
            } else if (k.toUpperCase().contains(word.trim().toUpperCase())) {
                result.put(k, map.get(k));
            }

        }

        return result;

    }

}
