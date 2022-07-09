import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);

        int rows = scanner.nextInt();
        int columns = scanner.nextInt();

        int[][] array = new int[columns][rows];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                array[j][rows - 1 - i] = scanner.nextInt();
            }
        }

        for (int[] nestedArray : array) {
            for (int value : nestedArray) {
                System.out.print(value + " ");
            }
            System.out.println();
        }
    }
}