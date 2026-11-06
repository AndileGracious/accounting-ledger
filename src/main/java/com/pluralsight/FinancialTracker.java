package com.pluralsight;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

//






public class FinancialTracker {

    /* ------------------------------------------------------------------
        data and formatters
       ------------------------------------------------------------------ */
    private static final ArrayList<Transaction> transactions = new ArrayList<>();
    private static final String FILE_NAME = "transactions.csv";

    private static final String DATE_PATTERN = "yyyy-MM-dd";
    private static final String TIME_PATTERN = "HH:mm:ss";
    private static final String DATETIME_PATTERN = DATE_PATTERN + " " + TIME_PATTERN;

    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern(DATE_PATTERN);
    private static final DateTimeFormatter TIME_FMT = DateTimeFormatter.ofPattern(TIME_PATTERN);
    private static final DateTimeFormatter DATETIME_FMT = DateTimeFormatter.ofPattern(DATETIME_PATTERN);

    /* ------------------------------------------------------------------
       Main menu

       ------------------------------------------------------------------ */
    public static void main(String[] args) {
        loadTransactions(FILE_NAME);
        sortTransactions();

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("Welcome to TransactionApp");
            System.out.println("Choose an option:");
            System.out.println("D) Add Deposit");
            System.out.println("P) Make Payment (Debit)");
            System.out.println("L) Ledger");
            System.out.println("X) Exit");


            String input = scanner.nextLine().trim();

            switch (input.toUpperCase()) {
                case "D" -> addDeposit(scanner);
                case "P" -> addPayment(scanner);
                case "L" -> ledgerMenu(scanner);
                case "X" -> running = false;
                default -> System.out.println("Invalid option");
            }
        }
        scanner.close();
    }
       //create sort method to sort transactions in order

        public static void sortTransactions(){
        transactions.sort(Comparator.comparing(Transaction::getDate).thenComparing(Transaction::getTime).reversed());
}
    /* ------------------------------------------------------------------
       File I/O
       ------------------------------------------------------------------ */


    public static void loadTransactions(String fileName) {
        // use both FileReader and BufferedReader
        // split and break line into 5 fields by \\|
        // convert String to LocalDate, LocalTime
        // use while loop to read every line until readLine returns null


        try {
            FileReader fileReader = new FileReader(FILE_NAME);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split("\\|");
                LocalDate date = LocalDate.parse(parts[0]);
                LocalTime time = LocalTime.parse(parts[1]);
                String description = parts[2];
                String vendor = parts[3];
                double amount = Double.parseDouble(parts[4]);

                transactions.add(new Transaction(date, time, description, vendor, amount));
            }
            bufferedReader.close();

        } catch (Exception e) {
            System.out.println("An error has occurred while reading the file");
        }


    }

    /* ------------------------------------------------------------------
       Add new transactions
       ------------------------------------------------------------------ */

    // prompt user for date and time in the String format and then parse to LocalDate| LocalTime using imported formatter.
    // add transaction using append, true to be able to just add the new transaction without deleting the old ones
    // use try catch to handle errors without crashing app
    // call on the sort method to make sure transactions are sorted newest first

    private static void addDeposit(Scanner scanner) {
        // TODO

        System.out.println("Enter date and time in the following format yyyy-MM-dd HH:mm:ss : ");
        String date = scanner.nextLine();
        System.out.println("Enter the Amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();
        System.out.println("Enter the description of the item: ");
        String description = scanner.nextLine();
        System.out.println("Enter the name of the Vendor: ");
        String vendor = scanner.nextLine();

        LocalDateTime dateTime = LocalDateTime.parse(date, DATETIME_FMT);
        LocalDate parsedDate = dateTime.toLocalDate();
        LocalTime parsedTime = dateTime.toLocalTime();

        try {
            Transaction transaction = new Transaction(parsedDate, parsedTime, description, vendor, amount);
            transactions.add(transaction);
            FileWriter fileWriter = new FileWriter(FILE_NAME, true);
            String dateString = parsedDate.format(DATE_FMT);
            String timeString = parsedTime.format(TIME_FMT);

            String line = dateString + "|" + timeString + "|" + description + "|" + vendor + "|" + amount;
            BufferedWriter writer = new BufferedWriter(fileWriter);
            writer.write(line);
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            System.out.println("Error detected");
            ;
        }
           sortTransactions();

    }

    // use scanner.nextLine after prompting for amount to clear scanner bug
    // make sure user types in a positive amount and then save it as negative (amount * -1)
    // same logic as in addDeposit prompt for date in String format parse it to LocalDate, LocalTime



    private static void addPayment(Scanner scanner) {
        // TODO
        System.out.println("Please enter date in the following format yyyy-MM-dd HH:mm:ss : ");
        String date = scanner.nextLine();
        System.out.println("Enter the payment amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();
        System.out.println("Enter the description of the item: ");
        String description = scanner.nextLine();
        System.out.println("Enter the name of the vendor: ");
        String vendor = scanner.nextLine();

        LocalDateTime dateTime = LocalDateTime.parse(date, DATETIME_FMT);
        LocalDate parsedDate = dateTime.toLocalDate();
        LocalTime parsedTime = dateTime.toLocalTime();

        if (amount > 0) {
            amount = amount * -1;

            try {

                Transaction transaction = new Transaction(parsedDate, parsedTime, description, vendor, amount);
                transactions.add(transaction);
                FileWriter fileWriter = new FileWriter(FILE_NAME, true);
                String dateString = parsedDate.format(DATE_FMT);
                String timeString = parsedTime.format(TIME_FMT);

                String line = dateString + "|" + timeString + "|" + description + "|" + vendor + "|" + amount;
                BufferedWriter writer = new BufferedWriter(fileWriter);
                writer.write(line);
                writer.newLine();
                writer.close();


            } catch (Exception e) {
                System.out.println("An error has been detected");
                ;
            }
            sortTransactions();


        }


    }

    /* ------------------------------------------------------------------
       Ledger menu
       ------------------------------------------------------------------ */
    private static void ledgerMenu(Scanner scanner) {
        boolean running = true;

        while (running) {
            System.out.println("Ledger");
            System.out.println("Choose an option:");
            System.out.println("A) All");
            System.out.println("D) Deposits");
            System.out.println("P) Payments");
            System.out.println("R) Reports");
            System.out.println("H) Home");

            String input = scanner.nextLine().trim();

            switch (input.toUpperCase()) {
                case "A" -> displayLedger();
                case "D" -> displayDeposits();
                case "P" -> displayPayments();
                case "R" -> reportsMenu(scanner);
                case "H" -> running = false;
                default -> System.out.println("Invalid option");
            }
        }
    }




    private static void displayLedger() {
        // show all transactions newest first
        // use for each loop to loop through the transactions list

        for (Transaction transaction : transactions) {
            System.out.println(transaction);

        }
    }

    private static void displayDeposits() {
        // use if statement inside the for each loop to filter only transactions with a positive amount

        for (Transaction transaction : transactions) {
            if (transaction.getAmount() > 0){
                System.out.println(transaction);


        }
        }


    }

    private static void displayPayments() {
        // use if statement inside the for each loop to filter only payments with a negative amount
        for (Transaction transaction : transactions) {
            if (transaction.getAmount() < 0) {
                System.out.println(transaction);
            }

        }
    }

    /* ------------------------------------------------------------------
       Reports menu
       ------------------------------------------------------------------ */
    private static void reportsMenu(Scanner scanner) {
        boolean running = true;
        while (running) {
            System.out.println("Reports");
            System.out.println("Choose an option:");
            System.out.println("1) Month To Date");
            System.out.println("2) Previous Month");
            System.out.println("3) Year To Date");
            System.out.println("4) Previous Year");
            System.out.println("5) Search by Vendor");
            System.out.println("6) Custom Search");
            System.out.println("0) Back");

            String input = scanner.nextLine().trim();

            switch (input) {
                case "1" -> {
                    // month to date is from the first day of this month to today
                    LocalDate today = LocalDate.now();
                    LocalDate start = today.withDayOfMonth(1);
                    filterTransactionsByDate(start, today);
                }
                case "2" -> {
                    // previous month is from first day of last month to last day of last month
                    LocalDate today = LocalDate.now();
                    LocalDate previous = today.minusMonths(1);
                    LocalDate start = previous.withDayOfMonth(1);
                    LocalDate end = previous.withDayOfMonth(previous.lengthOfMonth());
                    filterTransactionsByDate(start, end);

                }
                case "3" -> {
                    // year to date is from first day of the year to today
                    LocalDate today = LocalDate.now();
                    LocalDate start = today.withDayOfYear(1);
                    filterTransactionsByDate(start, today);
                }
                case "4" -> {
                    // previous year is from first day of last year to last day of last year
                    LocalDate today = LocalDate.now();
                    LocalDate previous = today.minusYears(1);
                    LocalDate start = previous.withDayOfYear(1);
                    LocalDate end  = previous.withDayOfYear(previous.lengthOfYear());
                    filterTransactionsByDate(start, end);
                }
                case "5" -> {
                }
                case "6" -> customSearch(scanner);
                case "0" -> running = false;
                default -> System.out.println("Invalid option");
            }
        }
    }

    /* ------------------------------------------------------------------
       Reporting helpers
       ------------------------------------------------------------------ */
    private static void filterTransactionsByDate(LocalDate start, LocalDate end) {
        // use for each loop to filter by date range, check if transaction date is not before start and not after end
        for (Transaction transaction : transactions) {
            if(!transaction.getDate().isBefore(start) && !transaction.getDate().isAfter(end)){
                System.out.println(transaction);
            }

        }



        }


    private static void filterTransactionsByVendor(String vendor) {
        // display vendors whose name match the ones searched for, use equals.IgnoreCase to compare names
        for (Transaction transaction : transactions) {
            if (transaction.getVendor().equalsIgnoreCase(vendor)){
                System.out.println(transaction);
            }

        }
    }

    private static void customSearch(Scanner scanner) {

    }

    /* ------------------------------------------------------------------
       Utility parsers (you can reuse in many places)
       ------------------------------------------------------------------ */
    private static LocalDate parseDate(String s) {

        return null;
    }

    private static Double parseDouble(String s) {
        return null;
    }
}

