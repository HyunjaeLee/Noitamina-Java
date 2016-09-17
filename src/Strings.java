import java.io.File;

public class Strings {

    //TEXT
    public static final String BASE_URL = "http://ani.today";
    public static final String DOWNLOAD_PATH = System.getProperty("user.home") + File.separator + "Downloads" + File.separator;
    public static final String MP4 = ".mp4";
    public static final String BIG_HELP = "help\nshow\nfind <title>\nurl <title>\ndownload <title>\nthreads\ncancel <download>\ncancelAll\nexit\n";
    public static final String SMALL_HELP = "help\nshow\nurl <title>\ndownload <title>\nthreads\ncancel <download>\ncancelAll\nexit\nback\n";
    public static final String INPUT = "> ";

    //STATUS
    public static final int DEFAULT = 0;
    public static final int EXIT = -1;
    public static final int CONTINUE = -2;

}
