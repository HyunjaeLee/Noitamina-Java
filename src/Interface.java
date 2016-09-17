import java.io.File;
import java.util.Map;

abstract class Interface {

    public abstract int face(String[] in, Map<String, String> map); // exit: -1 | continue: -2 | else

    Map<String, String> find(String string, Map<String, String> map){

        Map<String, String> result = Util.find(string, map);

        if (result.size() != 0) {
            result.keySet().forEach(System.out::println);
        } else {
            System.out.println("No Result");
        }

        return result;

    }

    void show(Map<String, String> map) {
        map.keySet().forEach(System.out::println);
    }

    abstract void downloadHelper (String string, Map<String, String> smallList);

    void download(Map<String, String> smallList) {

        smallList.forEach((title, href) -> {

            String file = Strings.DOWNLOAD_PATH + title + Strings.MP4;
            File f = new File(Strings.DOWNLOAD_PATH + title + Strings.MP4);

            if (f.exists()) {
                f.delete();
            }

            Download.downloadSet.add(new Download(Get.videoURL(href), file));
            Download.downloadSet.last().setName(title);
            Download.downloadSet.last().start();

        });

    }

    void thread() {
        Download.downloadSet.forEach(d -> System.out.printf("%s : %.1f%%%n" , d.getName() ,d.getProgress()));
    }

    void url(String string, Map<String, String> map) {
        Map<String, String> result = find(string, map);
        if(result.size() == 1) {
            System.out.println(map.values().iterator().next());
        }
    }

    static int con() {
        return -2;
    }

    int exit() {
        return -1;
    }

}
