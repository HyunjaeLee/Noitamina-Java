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

	private static String[] getInput() { // GET COMMAND -OPTION TARGET RETURN COMMAND TARGET OPTION
        System.out.print(Strings.INPUT);
        String[] str = new String[3]; // 0: COMMAND 1: TARGET 2: OPTION
        String[] in = sc.nextLine().split(" ", 2);
        str[0] = in[0]; // no exception -> split returns : Array.length >= 1
        if(in.length==2){
            if(in[1].startsWith("-")){ // with option
                in = in[1].split(" ", 2);
                str[2] = in[0]; // option
                if(in.length==2){ // with target
                    str[1] = in[1];
                } else {
                    str [1] = ""; // no target
                }
            } else {
                str[1]=in[1]; // no option
                str[2]="";
            }
        } else {
            str[1]=""; // command only
            str[2]="";
        }
        return str;
    }
}