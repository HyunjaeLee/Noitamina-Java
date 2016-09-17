import java.util.Scanner;
import java.util.Map;
import java.util.TreeMap;

public class Main {

	public static void main(String[] args){

        Scanner sc = new Scanner(System.in);

        BigInterface bigInterface = new BigInterface();
        SmallInterface smallInterface = new SmallInterface();

        int con = 0; // exit: -1 | continue: -2 | else

        while(con != -1) {

            Map<String, String> bigList = new TreeMap<>(new AlphanumComparator());
            Map<String, String> smallList = new TreeMap<>(new AlphanumComparator());
            Get.bigList(bigList);
            bigList.keySet().forEach(System.out::println);

            if(con == -2){
                con = 0;
            }

            while ((con != -1) && (con != -2)) {
                con = bigInterface.face(sc.nextLine().split(" "), bigList, smallList);
            }

            if(con == -2){
                con = 0;
            }

            smallList.keySet().forEach(System.out::println);

            while ((con != -1) && (con != -2)) {
                con = smallInterface.face(sc.nextLine().split(" "), smallList);
            }

        }

        sc.close();

	}

}