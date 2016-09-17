import java.io.File;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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

            new Download(Get.videoURL(href), file, title);

        });

    }

    void thread() {
        Download.downloadList.forEach(d -> System.out.printf("%s : %.1f%%%n" , d.getName() ,d.getProgress()));
    }

    void cancel(String name) {

        Set<Download> match = Download
                .downloadList
                .stream()
                .filter(d -> d.getName().equalsIgnoreCase(name))
                .collect(Collectors.toSet());
        if(match.size() == 1)
            match.iterator().next().cancel();
        else
            System.out.println("No matching thread");

    }

    void cancelAll() {
        Download.cancelAll();
    }

    void url(String string, Map<String, String> map) {
        Map<String, String> result = find(string, map);
        if(result.size() == 1) {
            System.out.println(map.values().iterator().next());
        }
    }

    int exit() {
        return Strings.EXIT; // -1
    }

    abstract void help();

}
