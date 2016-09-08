import java.util.Comparator;

/**
 * Created by Hyunjae on 9/9/16.
 */

public class AlphanumComparator implements Comparator<String> {

    @Override
    public int compare(String o1,String o2){

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

}