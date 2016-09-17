import java.util.Map;

public class SmallInterface extends Interface {

    @Override
    public int face(String[] in, Map<String, String> map){

        int con = Strings.DEFAULT; //0

        switch (in[0].toUpperCase()) {

            case "SHOW":
                show(map);
                break;

            case "URL" :
                url(in[1], map);
                break;

            case "DOWNLOAD" :
                downloadHelper(in[1], map);
                break;

            case "THREADS" :
                thread();
                break;

            case "CANCEL":
                cancel(in[1]);
                break;

            case "CANCELALL":
                cancelAll();
                break;

            case "BACK":
                con = back();
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
            download(result);
        }
    }

    static int back() {
        return Strings.CONTINUE;
    }

    @Override
    void help() {
        System.out.print(Strings.SMALL_HELP);
    }

}
