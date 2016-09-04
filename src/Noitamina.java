import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

public class Noitamina {
	
	public static void main(String[] args){

        Scanner sc = new Scanner(System.in);

        Map<String, String> bigList = bigList();
        bigList.keySet().forEach(k -> System.out.println(k));
        String bigURL = bigList.get(search(bigList, sc));

        Map<String, String> smallList = smallList(bigURL);
        smallList.keySet().forEach(k -> System.out.println(k));
        String key = search(smallList, sc);
        String smallURL = smallList.get(key);

        String videoURL = videoURL(smallURL);

        String file = key + ".mp4";
        Download download = new Download(videoURL, file);
        download.start();

        sc.close();

	}

    public static Map<String, String> bigList() {


        String text = Util.getContents("http://ani.today");

        Set<String> href = new LinkedHashSet<>();
        Util.parse(text , "((https?://ani.today/list/)+\\d{2,}+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)" , href );

        Set<String> title = new LinkedHashSet<>();
        Util.parse(text , "<a href=\"https?://ani.today/list/+\\d{2,}+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*\">(.*?)</a>" , title);

        //<Key: Title, Value: href>
        Map<String, String> map = new TreeMap<>();

        Iterator<String> hrefIterator = href.iterator();
        Iterator<String> titleIterator = title.iterator();
        while(hrefIterator.hasNext() && titleIterator.hasNext()) {
            map.put(titleIterator.next(), hrefIterator.next());
        }

        return map;

    }

    public static Map<String, String> smallList(String url) {

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

        Set<String> href = new LinkedHashSet<>();
        Util.parse (text , "<div class=\"board-list-item\"><a href=\"(.*?)\" title=" , href);

        Set<String> title = new LinkedHashSet<>();
        Util.parse (text , "<span class=\"text\">(.*?)</span>" , title);

        Map<String, String> map = new TreeMap<>(new Comparator<String>()
        {
            public int compare(String o1, String o2) {

                String o1StringPart = o1.replaceAll("\\d", "");
                String o2StringPart = o2.replaceAll("\\d", "");
                if(o1StringPart.equalsIgnoreCase(o2StringPart))
                {
                    return extractInt(o1) - extractInt(o2);
                }
                return o1.compareTo(o2);
            }

            int extractInt(String s) {
                String num = s.replaceAll("\\D", "");
                return num.isEmpty() ? 0 : Integer.parseInt(num);
            }
        });

        Iterator<String> hrefIterator = href.iterator();
        Iterator<String> titleIterator = title.iterator();
        while(hrefIterator.hasNext() && titleIterator.hasNext()) {
            map.put(titleIterator.next(), hrefIterator.next());
        }

        return map;
    }

    public static String videoURL(String url){
        Set<String> set = new HashSet<>();
        return (Util.parse(Util.getContents(url), "(https?://[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]+.mp4)", set) == 1 )
                ? set.iterator().next() : null ;
    }

    public static String search(Map<String, String> map, Scanner sc) {
        String word = "";
        Map<String, String> result = new HashMap<>();
        while(result.size() != 1) {
            result.clear();
            word = sc.nextLine();
            result = Util.search(word, map);
            if(result.size() != 0)
                result.keySet().forEach(k -> System.out.println(k));
            else
                System.out.println("No Result");
        }
        return result.keySet().iterator().next();
    }

}

