import java.util.Map;
import java.util.TreeMap;
import java.util.HashMap;

public class BigInterface extends Interface{


    @Override
    public int face(String[] in, Map<String, String> map) {

        return face(in, map, new HashMap<>());

    }

    public int face (String[] in, Map<String, String> map, Map<String, String> smallList) {

        int con = Strings.DEFAULT; //0

        switch (in[0].toUpperCase()) {
            case "FIND":
                Map<String, String> small = find(in[1], map);
                if(small.size() == 1) { // 단일 데이터일 경우 continue
                    Get.smallList(smallList, small.values().iterator().next());
                    con = Strings.CONTINUE; //-2
                }
                break;

            case "SHOW":
                show(map);
                break;

            case "URL" :
                url(in[1], map);
                break;

            case "DOWNLOAD":
                downloadHelper(in[1], map);
                break;

            case "THREADS":
                thread();
                break;

            case "CANCEL":
                cancel(in[1]);
                break;

            case "CANCELALL":
                cancelAll();
                break;

            case "EXIT" :
                con = exit(); // -1
                break;

            case "HELP" :
            default :
                help();
                break;

        }

        return con;

    }

    @Override
    void downloadHelper(String string, Map<String, String> map) {

        Map<String, String> result = find(string, map);
        if(result.size() == 1) {
            Map<String, String> downloads = new TreeMap<>(new AlphanumComparator());
            Get.smallList(downloads, result.values().iterator().next());
            download(downloads);
        }

    }

    @Override
    void help() {
        System.out.print(Strings.BIG_HELP);
    }

}
