import java.util.Scanner;
import java.util.Arrays;

class Main {
    // implement me
    private static void rotate(int[] arr, int steps) {
        // Save non-shuffled values for reference
        int[] temp = arr.clone();
        // Calculate offset of resulting array
        int offset = steps % arr.length;

        // Offset each index
        for (int i = 0; i < arr.length; i++) {
            int newIndex = i + offset;
            if (newIndex >= arr.length) {
                newIndex -= arr.length;
            }
            arr[newIndex] = temp[i];
        }
    }

    // do not change code below
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[] arr = Arrays.stream(scanner.nextLine().split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();

        int steps = Integer.parseInt(scanner.nextLine());

        rotate(arr, steps);

        for (int i : arr) {
            System.out.print(i + " ");
        }
    }
}