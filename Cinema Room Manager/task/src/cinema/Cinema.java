package cinema;

import java.util.Scanner;

class CinemaGrid {

    private static final int SMALL_GRID_LIMIT = 60;
    private static final int FRONT_ROW_PRICE = 10;
    private static final int BACK_ROW_PRICE = 8;
    private int rows;
    private int columns;
    private String[][] seats;
    private int seatCount;

    public CinemaGrid(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.seatCount = rows * columns;
        this.seats = new String[rows][columns];
        initializeTiles();
    }

    /**
     * Initialize starting state of each seat
     */
    private void initializeTiles() {
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.columns; j++) {
                this.seats[i][j] = "S";
            }
        }
    }

    public boolean isSeatValid(int row, int column) {
        return row <= this.rows && column <= this.columns;
    }

    /**
     * Check if seat is already purchased
     * @param row
     * @param column
     * @return
     */
    public boolean isSeatTaken(int row, int column) {
        return this.seats[row - 1][column - 1] == "B";
    }

    /**
     * Reserve seat
     * @param row
     * @param column
     */
    public void purchaseSeat(int row, int column) {
        this.seats[row - 1][column - 1] = "B";
    }

    /**
     * Get seat price
     * @param row
     * @param column
     * @return
     */
    public int getSeatPrice(int row, int column) {
        int price;
        int seatsCount = this.rows * this.columns;
        if (seatsCount <= SMALL_GRID_LIMIT) {
            price = FRONT_ROW_PRICE;
        } else {
            if (row <= this.rows / 2) {
                price = FRONT_ROW_PRICE;
            } else {
                price = BACK_ROW_PRICE;
            }
        }
        return price;
    }

    /**
     * Get number of seats in cinema
     * @return
     */
    public int getSeatCount() {
        return this.seatCount;
    }

    /**
     * Get maximum possible income if cinema is fully booked
     * @return
     */
    public int getMaxIncome() {
        int price;
        if (this.seatCount <= SMALL_GRID_LIMIT) {
            price = this.seatCount * FRONT_ROW_PRICE;
        } else {
            int frontSeatCount = (this.rows / 2) * columns;
            int backSeatCount = (this.rows - this.rows / 2) * columns;
            price = (frontSeatCount * FRONT_ROW_PRICE) + (backSeatCount * BACK_ROW_PRICE);
        }
        return price;
    }

    /**
     * Print grid status to screen
     */
    public void printGrid() {
        System.out.println("Cinema:");
        for (int i = 0; i <= this.rows; i++) {
            for (int j = 0; j <= this.columns; j++) {
                if (i == 0 && j == 0) {
                    System.out.print(" ");
                } else if (i == 0) {
                    System.out.print(j);
                } else if (j == 0) {
                    System.out.print(i);
                } else {
                    System.out.print(this.seats[i - 1][j - 1]);
                }
                if (j < columns) {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }
}

class CinemaManager {
    enum State {SETUP, MENU, BUY}
    private CinemaGrid grid;
    private State state;
    private boolean running;

    // Statistics properties
    private int ticketsSold = 0;
    private float ticketsSoldPercent = 0;
    private int currentIncome = 0;
    private int totalIncome;

    private Scanner scanner;

    public CinemaManager() {
        this.running = false;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Run infinite loop, changing actions based on state the app is in
     */
    public void run() {
        this.running = true;
        this.state = State.SETUP;

        while (this.running) {
            switch (this.state) {
                case SETUP:
                    this.handleSetupState();
                    this.state = State.MENU;
                    break;
                case MENU:
                    this.handleMenuState();
                    break;
                case BUY:
                    this.handleBuyState();
                    this.state = State.MENU;
                    break;
                default:

            }
            System.out.println();
        }
    }

    /**
     * Handle setup state
     */
    private void handleSetupState() {
        System.out.println("Enter the number of rows:");
        int rows = scanner.nextInt();
        System.out.println("Enter the number of seats in each row:");
        int seatsPerRow = scanner.nextInt();
        this.grid = new CinemaGrid(rows, seatsPerRow);
        this.totalIncome = grid.getMaxIncome();
    }

    /**
     * Handle menu state
     */
    private void handleMenuState() {
        System.out.println("1. Show the seats");
        System.out.println("2. Buy a ticket");
        System.out.println("3. Statistics");
        System.out.println("0. Exit");

        int selection = scanner.nextInt();
        switch (selection) {
            case 0:
                this.running = false;
                break;
            case 1:
                System.out.println();
                this.grid.printGrid();
                break;
            case 2:
                this.state = State.BUY;
                break;
            case 3:
                System.out.println();
                this.printStatistics();
                break;
        }
    }

    /**
     * Handle buy menu selection
     */
    private void handleBuyState() {
        while (true) {
            // Print buy menu to user
            System.out.println("Enter a row number:");
            int row = scanner.nextInt();
            System.out.println("Enter a seat number in that row:");
            int column = scanner.nextInt();

            if (!this.grid.isSeatValid(row, column)) {
                System.out.println();
                System.out.println("Wrong input!");
                System.out.println();
            } else if (this.grid.isSeatTaken(row, column)) {
                // Print warning that seat is taken
                System.out.println();
                System.out.println("That ticket has already been purchased!");
                System.out.println();
            } else {
                // Purchase seat
                this.grid.purchaseSeat(row, column);

                // Update statistics
                this.ticketsSold++;
                this.ticketsSoldPercent = (float) this.ticketsSold / this.grid.getSeatCount() * 100;
                int seatPrice = this.grid.getSeatPrice(row, column);
                this.currentIncome += seatPrice;

                // Print price to user
                System.out.println();
                System.out.println("Ticket price: $" + seatPrice);

                break;
            }
        }
    }

    /**
     * Print cinema statistics
     */
    private void printStatistics() {
        System.out.printf("Number of purchased tickets: %d\n", this.ticketsSold);
        System.out.printf("Percentage: %.2f%%\n", this.ticketsSoldPercent);
        System.out.printf("Current income: $%d\n", this.currentIncome);
        System.out.printf("Total income: $%d\n", this.totalIncome);
    }

}

public class Cinema {
    public static void main(String[] args) {
        // Instantiate new cinema manager & run it
        CinemaManager manager = new CinemaManager();
        manager.run();
    }
}