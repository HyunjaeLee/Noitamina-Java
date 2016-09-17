import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Comparator;
import java.util.TreeSet;

class Download extends Thread{

    public static TreeSet<Download> downloadSet = new TreeSet<>(new Comparator<Download>() {

        String o1;
        String o2;

        @Override
        public int compare(Download d1,Download d2){
            o1 = d1.getName();
            o2 = d2.getName();
            String o1StringPart=o1.replaceAll("\\d","");
            String o2StringPart=o2.replaceAll("\\d","");
            if(o1StringPart.equalsIgnoreCase(o2StringPart)) {
                return extractInt(o1)-extractInt(o2);
            }
            return o1.compareTo(o2);
        }

        int extractInt(String s){
            String num=s.replaceAll("\\D","");
            return num.isEmpty()?0:Integer.parseInt(num);
        }
    });

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
