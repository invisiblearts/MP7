import java.util.Scanner;
import java.util.stream.IntStream;

public class Bazi {
    public static void main(String[] unused) {
        Scanner s = new Scanner(System.in);
        System.out.println(welcomeWords);
        System.out.println("Please enter the name of the first person.");
        String name1 = s.nextLine();
        System.out.println("Please enter the birthday of the first person, in format MMDDYYYY." +
                " eg: 01021987 for Jan 02 1987.");
        String bday1 = s.nextLine();
        System.out.println("Please enter the name of the second person.");
        String name2 = s.nextLine();
        System.out.println("Please enter the birthday of the second person, in format MMDDYYYY." +
                " eg: 01021987 for Jan 02 1987.");
        String bday2 = s.nextLine();

        int nameLen = Math.max(name1.length(), name2.length());
        int[] name1Arr = new int[nameLen], name2Arr = new int[nameLen];
        for (int i = 0; i < name1.length(); i++) {
            name1Arr[i] = (int) name1.charAt(i);
        }
        for (int i = 0; i < name2.length(); i++) {
            name2Arr[i] = (int) name2.charAt(i);
        }

        double nameRate = getNameRate(name1Arr, name2Arr, nameLen);

        int[] bday1Arr = new int[8], bday2Arr = new int[8];
        for (int i = 0; i < 7; i++){
            bday1Arr[i] = Integer.parseInt(Character.toString(bday1.charAt(i)));
            bday2Arr[i] = Integer.parseInt(Character.toString(bday2.charAt(i)));
        }

        double bdayRate = getBdayRate(bday1Arr, bday2Arr);

        printFinal(nameRate, bdayRate);
    }

    private static double getNameRate(int[] a, int[] b, int nameLen){
        int[] result = new int[2 * nameLen];
        for(int i = 0; 2 * i + 1 < result.length; i++) {
            result[2 * i] = a[i];
            result[2 * i + 1] = b[i];
        }
        return getNameRate(result);
    }

    private static double getBdayRate(int[] a, int[] b){
        double len1 = Math.sqrt((IntStream.of(a).map(x -> x*x).sum()));
        double len2 = Math.sqrt((IntStream.of(b).map(x -> x*x).sum()));
        double dotProduct = 0;
        for (int i = 0; i < 8; i++) {
            dotProduct += a[i] * b[i];
        }
        return (dotProduct / len1 / len2 + 1) / 2;
    }

    private static final String welcomeWords =
            "Welcome to System Bazi(八字)!!\n" +
            "This powerful system can calculate your \"match rate\" with your significant other\n" +
            "with on your names and birthdays, based on ancient and mysterious oriental metaphysics.\n" +
            "Now, let's begin.";

    private static double getNameRate(int[] arr) {
        if(arr.length == 2) {
            System.out.printf("Affection Rate:%d%d%%!\n",arr[0],arr[1]);
            return (10.0 * (arr[0] % 10 ) + (arr[1] % 10 )) / 100.0;
        }
        int[] next = new int[arr.length - 1];
        for(int i = 0; i < next.length; i++) {
            next[i] = (arr[i] + arr[i+1]) % 10;
        }
        return getNameRate(next);
    }
    /**
     *Cosine value of two vectors could be calculated by
     * Cosθ = dotProduct of A and B/ absolute value of A and B
     *int[] a and b have same length
     */
    private static double getBirthdayRate(int[] a, int[] b) {
        double dotProduct = 0.0;
        double absValA = 0.0;
        double absValB = 0.0;
        double theta = 0.0;
        for(int i = 0; i < a.length; i++) {
            dotProduct += a[i]*b[i];
            absValA += a[i]*a[i];
            absValB += b[i]*b[i];
        }
        absValA = Math.sqrt(absValA);
        absValB = Math.sqrt(absValB);
        theta = dotProduct / (absValA * absValB);
        return (theta+1) / 2;
    }
    private static void printFinal(double r1, double r2) {
        double result = 0.3 * r1 + 0.7 * r2;
        System.out.printf("Final Affection Rate: %d%%!\n",(int) (result * 100));
    }

}
