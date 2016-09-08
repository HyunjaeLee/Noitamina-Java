import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.*;
//import java.nio.channels.Channels;
//import java.nio.channels.ReadableByteChannel;

/**
 * Created by Hyunjae on 9/5/16
 */

class Download extends Thread{

    private String url;
    private String file;

    private long downloadSize = 0;
    private long completeSize = -1;

    public Download(String url, String file){
        this.url = url;
        this.file = file;
    }

    public float getProgress() {
        return ((float) downloadSize / completeSize) * 100;
    }

    public void run() {
        try {

            URLConnection connection = new URL(this.url).openConnection();
            connection.addRequestProperty("User-Agent", "Mozilla/5.0");

            /*
            ReadableByteChannel rbc = Channels.newChannel(connection.getInputStream());
            FileOutputStream fos = new FileOutputStream(new File(this.file));
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            fos.close();
            rbc.close();
            */

            InputStream in = connection.getInputStream();
            FileOutputStream fos = new FileOutputStream(new File(this.file));

            completeSize = connection.getContentLengthLong();

            int len = -1;
            byte[] buf = new byte[1024];

            while((len = in.read(buf)) > -1){
                fos.write(buf , 0 , len);
                downloadSize += len;
            }

            fos.close();
            in.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
