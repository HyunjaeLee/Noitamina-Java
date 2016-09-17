import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Hyunjae on 9/17/16.
 */
public class BigInterface extends Interface{


    @Override
    public int face(String[] in, Map<String, String> map) {

        return face(in, map, null);

    }

    public int face (String[] in, Map<String, String> map, Map<String, String> smallList) {

        int con = 0;

        switch (in[0].toUpperCase()) {
            case "FIND":
                Map<String, String> small = find(in[1], map);
                if(small.size() == 1) {
                    Get.smallList(smallList, small.values().iterator().next());
                    con = -2;
                }
                break;

            case "SHOW":
                show(map);
                break;

            case "DOWNLOAD":
                downloadHelper(in[1], map);
                break;

            case "THREADS":
                thread();
                break;

            case "URL" :
                url(in[1], map);
                break;

            case "EXIT":
                con = exit();
                break;

            default:
                System.out.println("No Result");
                break;

        }

        return con;

    }

    @Override
    protected void downloadHelper(String string, Map<String, String> map) {

        Map<String, String> result = find(string, map);
        if(result.size() == 1) {
            Map<String, String> downloads = new TreeMap<>(new AlphanumComparator());
            Get.smallList(downloads, result.values().iterator().next());
            download(downloads);
        }

    }

}
