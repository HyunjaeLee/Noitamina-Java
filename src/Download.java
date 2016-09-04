import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

/**
 * Created by Hyunjae on 9/5/16
 */

class Download extends Thread{

    private String url;
    private String file;

    public Download(String url, String file){
        this.url = url;
        this.file = file;
    }

    public void run() {
        try {
            URLConnection connection = new URL(this.url).openConnection();
            connection.addRequestProperty("User-Agent", "Mozilla/5.0");
            ReadableByteChannel rbc = Channels.newChannel(connection.getInputStream());
            FileOutputStream fOutStream = new FileOutputStream(new File(this.file));
            fOutStream.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            fOutStream.close();
            rbc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
