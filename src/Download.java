import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

class Download extends Thread{

    public static Set<Download> downloadList = new LinkedHashSet<>();

    public long downloadSize = 0;
    public long completeSize = 0;

    private int state = 0; // else -> canceled

    private String urlString;
    private String fileString;

    private File file;
    private URL url;

    private InputStream in;
    private FileOutputStream fos;

    public Download(String url, String file, String name) {
        downloadList.add(this);
        this.urlString = url;
        this.fileString = file;
        this.setName(name);
        this.start();
        System.out.println("Starting Download: " + this.getName());
    }

    public void run() {

        try {

            url = new URL(urlString);
            URLConnection connection = url.openConnection();
            connection.addRequestProperty("User-Agent", "Mozilla/5.0");
            in = connection.getInputStream();

            file = new File(fileString);
            fos = new FileOutputStream(file);

            completeSize = connection.getContentLengthLong();

            int len = -1;
            byte[] buf = new byte[1024];

            while((len = in.read(buf)) > -1){
                fos.write(buf , 0 , len);
                downloadSize += len;
            }

            fos.close();
            in.close();

        } catch (IOException e) {
            if(state!=0)
                System.out.println("Download Canceled: " + this.getName());
            else
                e.printStackTrace();
        }

    }

    public void cancel() {

        state = -1; // state: canceled

        // close streams
        try {
            if(fos != null) // NULL CHECK
                fos.close();
            if(in != null) // NULL CHECK
                in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // delete file
        if(file.exists()) {
            file.delete();
        }

        // kill thread
        if(this.isAlive()) {
            this.interrupt();
        }

        // remove from downloadSet
        if(downloadList.contains(this)) {
          downloadList.remove(this);
        }

    }

    //STATIC
    public static void cancelAll() {
        Iterator<Download> iterator = Download.downloadList.iterator();
        while(iterator.hasNext()) {
            Download temp = iterator.next();
            iterator.remove();
            temp.cancel();
        }
    }

    public float getProgress() {
        return ((float) downloadSize / completeSize) * 100;
    }

}
