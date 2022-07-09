import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // Get number of rows
        int rows = scanner.nextInt();
        // Get number of seats per row
        int seatsPerRow = scanner.nextInt();
        // Initialize two-dimensional array
        int[][] seatStatus = new int[rows][seatsPerRow];

        // Set status for all seats
        for (int i = 0; i < seatStatus.length; i++) {
            for (int j = 0; j < seatStatus[i].length; j++) {
                seatStatus[i][j] = scanner.nextInt();
            }
        }

        int selectedLength = scanner.nextInt();
        int consecutiveSeats = 0;
        for (int i = 0; i < seatStatus.length; i++) {
            consecutiveSeats = 0;
            for (int j = 0; j < seatStatus[i].length; j++) {
                if (seatStatus[i][j] == 0) {
                    consecutiveSeats++;
                } else {
                    consecutiveSeats = 0;
                }
                if (consecutiveSeats >= selectedLength) {
                    break;
                }
            }
            if (consecutiveSeats >= selectedLength) {
                System.out.println(i + 1);
                break;
            }
        }
        if (consecutiveSeats < selectedLength) {
            System.out.println(0);
        }
    }
}