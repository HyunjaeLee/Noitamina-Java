import java.util.*;

/**
 * Created by Hyunjae on 9/17/16.
 */
public class Get {

    public static int bigList(Map<String, String> bigList) {

        String text = Util.getContents(Strings.BASE_URL);

        Deque<String> href = new ArrayDeque<>();
        Deque<String> title = new ArrayDeque<>();
        Util.parse(text , "((https?://ani.today/list/)+\\d{2,}+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)" , href );
        Util.parse(text , "<a href=\"https?://ani.today/list/+\\d{2,}+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*\">(.*?)</a>" , title);

        while(!href.isEmpty() && !title.isEmpty()) {
            bigList.put(title.removeFirst() , href.removeFirst());
        }

        return bigList.size();

    }

    public static int smallList(Map<String, String> smallList, String url) {

        StringBuilder sb = new StringBuilder();
        boolean hasBoardListItem = true;
        int pageNum = 1;
        String textTemp;
        while(hasBoardListItem){
            textTemp = Util.getContents(url + "/" + pageNum);
            if (textTemp.contains("<div class=\"board-list-item\">")){
                sb.append(textTemp);
                pageNum++;
            } else {
                hasBoardListItem = false;
            }
        }

        String text = sb.toString();

        Deque<String> href = new ArrayDeque<>();
        Deque<String> title = new ArrayDeque<>();
        Util.parse (text , "<div class=\"board-list-item\"><a href=\"(.*?)\" title=" , href);
        Util.parse (text , "<span class=\"text\">(.*?)</span>" , title);

        while(!href.isEmpty() && !title.isEmpty()) {
            smallList.put(title.removeFirst() , href.removeFirst());
        }

        return smallList.size();

    }

    public static String videoURL(String url){
        Set<String> set = new HashSet<>();
        return (Util.parse(Util.getContents(url), "(https?://[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]+.mp4)", set) == 1 )
                ? set.iterator().next() : null ;
    }

}
