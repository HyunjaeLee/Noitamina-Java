import java.io.File;
import java.util.Map;

/**
 * Created by Hyunjae on 9/17/16.
 */

abstract class Interface {

    public abstract int face(String[] in, Map<String, String> map); // -1: exit

    protected final static Map<String, String> find(String string, Map<String, String> map){

        Map<String, String> result = Util.find(string, map);

        if (result.size() != 0) {
            result.keySet().forEach(System.out::println);
        } else {
            System.out.println("No Result");
        }

        return result;

    }

    protected final static void show(Map<String, String> map) {
        map.keySet().forEach(System.out::println);
    }

    protected abstract void downloadHelper (String string, Map<String, String> smallList);

    protected final static void download(Map<String, String> smallList) {

        smallList.forEach((title, href) -> {

            String file = Strings.DOWNLOAD_PATH + title + Strings.MP4;
            File f = new File(Strings.DOWNLOAD_PATH + title + Strings.MP4);

            if (f.exists()) {
                f.delete();
            }

            Download.set.add(new Download(Get.videoURL(href), file));
            Download.set.last().setName(title);
            Download.set.last().start();

        });

    }

    protected final static void thread() {
        Download.set.forEach(d -> System.out.printf("%s : %.1f%%%n" , d.getName() ,d.getProgress()));
    }

    protected static void url(String string, Map<String, String> map) {
        Map<String, String> result = find(string, map);
        if(result.size() == 1) {
            System.out.println(map.values().iterator().next());
        }
    }

    protected final static int con() {
        return -2;
    }

    protected final static int exit() {
        return -1;
    }

}
