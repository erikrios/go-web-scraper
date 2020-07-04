import java.util.*;

public class Solution {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int t = in.nextInt();

        Map<Integer, List<Integer>> numbers = new HashMap<>();

        for (int i = 0; i < t; i++) {
            int a = in.nextInt();
            int b = in.nextInt();
            int n = in.nextInt();

            List<Integer> numberInputs = new ArrayList<>();
            numberInputs.add(a);
            numberInputs.add(b);
            numberInputs.add(n);

            numbers.put(i, numberInputs);
        }

        in.close();

        Map<Integer, List<Long>> results = calculate(numbers);

        // Print the results
        for (int i = 0; i < results.size(); i++) {
            List<Long> numberResults = results.get(i);
            for (long numberResult : numberResults) {
                System.out.print(numberResult + " ");
            }
            System.out.println();
        }
    }

    private static Map<Integer, List<Long>> calculate(Map<Integer, List<Integer>> numbers) {
        Map<Integer, List<Long>> results = new HashMap<>();

        for (int i = 0; i < numbers.size(); i++) {
            int a = numbers.get(i).get(0);
            int b = numbers.get(i).get(1);
            int n = numbers.get(i).get(2);

            List<Long> numberResults = new ArrayList<>();
            long total = a;

            for (int j = 0; j < n; j++) {
                total += (long) Math.pow(2, j) * b;
                numberResults.add(total);
            }
            results.put(i, numberResults);
        }

        return results;
    }
}
