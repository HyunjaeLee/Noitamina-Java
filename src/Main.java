import java.util.Scanner;
import java.util.Map;
import java.util.TreeMap;

public class Main {

    private static Scanner sc = new Scanner(System.in);

	public static void main(String[] args){

        BigInterface bigInterface = new BigInterface();
        SmallInterface smallInterface = new SmallInterface();

        int con = Strings.DEFAULT; // 0

        while(con != Strings.EXIT) { // -1

            Map<String, String> bigList = new TreeMap<>(new AlphanumComparator());
            Map<String, String> smallList = new TreeMap<>(new AlphanumComparator());
            Get.bigList(bigList);
            bigList.keySet().forEach(System.out::println);

            if(con == Strings.CONTINUE){ // -2
                con = Strings.DEFAULT; // 0
            }

            while ((con != Strings.EXIT) && (con != Strings.CONTINUE)) { // -1 -2
                con = bigInterface.face(getInput(), bigList, smallList);
            }

            if(con == Strings.CONTINUE){ // -2
                con = Strings.DEFAULT; // 0
            }

            smallList.keySet().forEach(System.out::println);

            while ((con != Strings.EXIT) && (con != Strings.CONTINUE)) { // -1 -2
                con = smallInterface.face(getInput(), smallList);
            }

        }

        sc.close();

	}

	private static String[] getInput() {

        System.out.print(Strings.INPUT);

        String[] str = new String[2];
        String[] in = sc.nextLine().split(" ", 2); //limit : 2

        try{
            str[0] = in[0];
        } catch(ArrayIndexOutOfBoundsException e) {
            str[0] = "";
        }

        try{
            str[1] = in[1];
        } catch(ArrayIndexOutOfBoundsException e) {
            str[1] = "";
        }

        return str;

    }

}