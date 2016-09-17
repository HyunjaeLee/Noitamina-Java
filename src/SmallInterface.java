import java.util.Map;

public class SmallInterface extends Interface {

    @Override
    public int face(String[] in, Map<String, String> map){

        int con = 0;

        switch (in[0].toUpperCase()) {

            case "SHOW":
                show(map);
                break;

            case "DOWNLOAD" :
                downloadHelper(in[1], map);
                break;

            case "URL" :
                url(in[1], map);
                break;

            case "THREADS" :
                thread();
                break;

            case "BACK":
                con = back();
                break;

            case "EXIT" :
                con = exit();
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
            download(result);
        }
    }

    static int back() {
        return -2;
    }

    @Override
    void help() {
        System.out.print(Strings.SMALL_HELP);
    }

}
