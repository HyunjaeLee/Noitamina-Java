import java.io.File;
import java.util.*;

public class Noitamina {

    public static final String BASEURL = "http://ani.today";
    public static final String DOWNLOADS = System.getProperty("user.home") + File.separator + "Downloads" + File.separator;
    public static final String MP4 = ".mp4";

    private static Map<String, String> bigList;
    private static Map<String, String> bigFindList = new HashMap<>();
    private static Map<String, String> smallList;
    private static Map<String, String> smallFindList = new HashMap<>();

    private static boolean bool = true;

    private static TreeSet<Download> downloadSet = new TreeSet<>(new Comparator<Download>() {

        String o1;
        String o2;

        @Override
        public int compare(Download d1,Download d2){
            o1 = d1.getName();
            o2 = d2.getName();
            String o1StringPart=o1.replaceAll("\\d","");
            String o2StringPart=o2.replaceAll("\\d","");
            if(o1StringPart.equalsIgnoreCase(o2StringPart)) {
                return extractInt(o1)-extractInt(o2);
            }
            return o1.compareTo(o2);
        }

        int extractInt(String s){
            String num=s.replaceAll("\\D","");
            return num.isEmpty()?0:Integer.parseInt(num);
        }
    });

	public static void main(String[] args){

        Scanner sc = new Scanner(System.in);

        bigList = bigList();
        bigList.keySet().forEach(k -> System.out.println(k));

        String input ;

        while(bigFindList.size() != 1) {
            input = sc.nextLine();
            bigCli(input);
        }

        smallList = smallList(bigFindList.values().iterator().next());
        smallList.keySet().forEach(k -> System.out.println(k));

        while(bool) {
            input = sc.nextLine();
            smallCli(input);
        }

        sc.close();

	}

    public static Map<String , String> bigList() {

        Map<String , String> map = new TreeMap<>(new AlphanumComparator());

        String text = Util.getContents(BASEURL);

        Deque<String> href = new ArrayDeque<>();
        Deque<String> title = new ArrayDeque<>();
        Util.parse(text , "((https?://ani.today/list/)+\\d{2,}+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)" , href );
        Util.parse(text , "<a href=\"https?://ani.today/list/+\\d{2,}+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*\">(.*?)</a>" , title);

        while(!href.isEmpty() && !title.isEmpty()) {
            map.put(title.removeFirst() , href.removeFirst());
        }

        return map;

    }

    public static Map<String, String> smallList(String url) {

        Map<String, String> map = new TreeMap<>(new AlphanumComparator());

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
            map.put(title.removeFirst() , href.removeFirst());
        }

        return map;

    }

    public static String videoURL(String url){
        Set<String> set = new HashSet<>();
        return (Util.parse(Util.getContents(url), "(https?://[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]+.mp4)", set) == 1 )
                ? set.iterator().next() : null ;
    }

    public static void bigCli(String input) {

        String[] inputs = input.split(" " , 2); // limit = 2

        switch (inputs[0].toUpperCase()) {

            case "FIND":

                bigFindList = Util.find(inputs[1], bigList);

                if(bigFindList.size() != 0) {
                    bigFindList.keySet().forEach(k -> System.out.println(k));
                } else {
                    System.out.println("No Result");
                }

                break;

            case "DOWNLOAD" :

                if((bigFindList = Util.find(inputs[1], bigList)).size() == 1) {

                    smallList(bigFindList.values().iterator().next()).forEach((k, v) -> {

                        String file = DOWNLOADS + k + MP4;
                        File f = new File(file);

                        if (f.exists()) {
                            f.delete();
                        }

                        downloadSet.add(new Download(videoURL(v), file));
                        downloadSet.last().setName(k);
                        downloadSet.last().start();

                    });

                } else {
                    if(bigFindList.size() != 0) {
                        bigFindList.keySet().forEach(k -> System.out.println(k));
                    } else {
                        System.out.println("No Result");
                    }
                }

                bigFindList.clear();

                break;

            case "DOWNLOADS" :
                downloadSet.forEach(download -> System.out.printf("%s : %.2f%%%n" , download.getName() ,download.getProgress()));
                break;

            default:
                System.out.println("No Result");
                break;

        }

    }

    public static void smallCli(String input) {

        String[] inputs = input.split(" " , 2); // limit = 2

        switch (inputs[0].toUpperCase()) {

            case "DOWNLOAD" :

                if((smallFindList = Util.find(inputs[1], smallList)).size() == 1) {

                    String smallKey = smallFindList.keySet().iterator().next();

                    String file = DOWNLOADS + smallKey + MP4;

                    if(new File(file).exists()) {
                        new File(file).delete();
                    }

                    downloadSet.add(new Download(videoURL(smallFindList.values().iterator().next()), file));
                    downloadSet.last().setName(smallKey);
                    downloadSet.last().start();

                } else {
                    if(smallFindList.size() != 0) {
                        smallFindList.keySet().forEach(k -> System.out.println(k));
                    } else {
                        System.out.println("No Result");
                    }
                }

                break;

            case "DOWNLOADS" :
                downloadSet.forEach(download -> System.out.printf("%s : %.2f%%%n" , download.getName() ,download.getProgress()));
                break;

            case "EXIT" :
                bool = false;
                break;

            default:
                System.out.println("No Result");
                break;

        }

    }

}
