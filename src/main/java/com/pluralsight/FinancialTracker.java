package com.pluralsight;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Comparator;
import java.util.Collections;


    public class FinancialTracker {

        private static final ArrayList<Transaction> transactions = new ArrayList<Transaction>();
        private static final String FILE_NAME = "transactions.csv";
        private static final String DATE_FORMAT = "yyyy-MM-dd";
        private static final String TIME_FORMAT = "HH:mm:ss";
        private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);
        private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(TIME_FORMAT);

        public static void main(String[] args) {
            loadTransactions(FILE_NAME);
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
                    case "D":
                        addDeposit(scanner);
                        break;
                    case "P":
                        addPayment(scanner);
                        break;
                    case "L":
                        ledgerMenu(scanner);
                        break;
                    case "X":
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid option");
                        break;
                }
            }

            scanner.close();
        }

        public static void loadTransactions(String fileName) {
            // This method should load transactions from a file with the given file name.
            // If the file does not exist, it should be created.
            // The transactions should be stored in the `transactions` ArrayList.
            // Each line of the file represents a single transaction in the following format:
            // <date>,<time>,<vendor>,<type>,<amount>
            // For example: 2023-04-29,13:45:00,Amazon,PAYMENT,29.99
            // After reading all the transactions, the file should be closed.
            // If any errors occur, an appropriate error message should be displayed.
            try {
                // Create a File object with the specified 'fileName'
                File myFile = new File(fileName);

                // Check if the file doesn't exist and create it if necessary
                if (myFile.createNewFile()) {
                    System.out.println("Inventory does not exist! Creating file...\n");
                }
            } catch (IOException e) {
                // Handle any IOException that may occur during file creation
                System.out.println("Error: Could not run file creation!");
                System.out.println("===========================");
            }

            try {
                // Create a BufferedReader to read from the specified file (assumed 'fileName' contains the file path)
                BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));

                // Skip the first line assuming it's a header or not needed
                bufferedReader.readLine();

                // Read the lines from the file until the end
                String input;
                while ((input = bufferedReader.readLine()) != null) {
                    // Split the input line using the "|" delimiter
                    String[] tokens = input.split("\\|");

                    // Extract and parse data from the split tokens
                    String date = tokens[0];
                    LocalDate realDate = LocalDate.parse(date);
                    String time = tokens[1];
                    LocalTime realTime = LocalTime.parse(time);
                    String description = tokens[2];
                    String vendor = tokens[3];
                    double price = Double.parseDouble(tokens[4]);

                    // Create a new Transaction object with the parsed data
                    Transaction loadTransactions = new Transaction(realDate, realTime, description, vendor, price);

                    // Add the Transaction object to the 'transactions' list
                    transactions.add(loadTransactions);
                }

                // Close the BufferedReader to free resources
                bufferedReader.close();

            } catch (Exception ex) {
                // Handle any exceptions that may occur during the file reading or parsing
                System.out.println("Error!");
                System.out.println("=================================");
            }
        }



        private static void addDeposit(Scanner scanner) {
            // This method should prompt the user to enter the date, time, vendor, and amount of a deposit.
            // The user should enter the date and time in the following format: yyyy-MM-dd HH:mm:ss
            // The amount should be a positive number.
            // After validating the input, a new `Deposit` object should be created with the entered values.
            // The new deposit should be added to the `transactions` ArrayList.
            try {
                // Create a BufferedWriter to write to the specified file (assumed FILE_NAME contains the file path)
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(FILE_NAME, true));


                // Ask the user to enter the date in the specified format and parse it
                System.out.println("Please enter the date in this format: (yyyy-MM-dd)");
                String inputD = scanner.nextLine();
                LocalDate realDate = LocalDate.parse(inputD,DATE_FORMATTER);
                System.out.println(realDate);

                // Ask the user to enter the time in the specified format and parse it
                System.out.println("Please enter the time of the deposit in this format: (HH:mm:ss)");
                String inputT = scanner.nextLine();
                LocalTime realTime = LocalTime.parse(inputT,TIME_FORMATTER);
                System.out.println(realTime);

                // Ask the user to enter the name of the vendor
                System.out.println("Please enter the name of the vendor: ");
                String vendor = scanner.nextLine();

                // Prompt the user to give a description of the item they are buying
                System.out.println("Please enter the Description of the item: ");
                String description = scanner.nextLine();


                // Prompt the user to enter the amount to deposit and validate it
                System.out.println("Please enter the amount you'd like to deposit:");
                double depositDouble = scanner.nextDouble();
                scanner.nextLine();

                // Check if the deposit amount is negative and handle the error
                if (depositDouble < 0) {
                    System.out.println("Error: You have entered an incorrect amount.");
                    System.out.println("=====================================");
                }

                // Create a Transaction object with the entered data
                Transaction deposit = new Transaction(realDate, realTime, description, vendor, depositDouble);

                // Add the Transaction object to the 'transactions' list
                transactions.add(deposit);

                // Format the transaction data for writing to the file
                String display = "\n" + deposit.getDate() + "|" + deposit.getTime() + "|" + deposit.getDescription() + "|"
                        + deposit.getVendor() + "|"
                        + deposit.getAmount();

                // Write the formatted data to the file
                bufferedWriter.write(display);

                // Close the BufferedWriter to save the changes to the file
                bufferedWriter.close();
                System.out.println("Your Deposit transaction has been added.");
            } catch (Exception ex) {
                // Handle any exceptions that may occur during input, writing, or parsing
                System.out.println("Error!");
                System.out.println("=====================================================");
            }
        }




        private static void addPayment(Scanner scanner){
            // This method should prompt the user to enter the date, time, vendor, and amount of a payment.
            // The user should enter the date and time in the following format: yyyy-MM-dd HH:mm:ss
            // The amount should be a positive number.
            // After validating the input, a new `Payment` object should be created with the entered values.
            // The new payment should be added to the `transactions` ArrayList.try {
            try {
                // Create a BufferedWriter to write to the specified file (assumed FILE_NAME contains the file path)
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(FILE_NAME, true));

                // Prompt the user to enter the date in the specified format and parse it
                System.out.println("Please enter the date of the payment in this format: (yyyy-MM-dd)");
                String inputD = scanner.nextLine();
                LocalDate realDate = LocalDate.parse(inputD,DATE_FORMATTER);
                System.out.println(realDate);

                // Prompt the user to enter the time in the specified format and parse it
                System.out.println("Please enter the time of the payment in this format: (HH:mm:ss)");
                String inputT = scanner.nextLine();
                LocalTime realTime = LocalTime.parse(inputT,TIME_FORMATTER);
                System.out.println(realTime);

                // Prompt the user to enter the name of the vendor
                System.out.println("Please enter the name of the vendor: ");
                String vendor = scanner.nextLine();

                // Prompt the user to give a description of the item they are buying
                System.out.println("Please enter the Description of the item: ");
                String description = scanner.nextLine();

                // Prompt the user to enter the amount to deposit and validate it
                System.out.println("Please enter the amount you'd like to pay (-): $");
                double paymentDouble = scanner.nextDouble();
                scanner.nextLine();

                // Check if the deposit amount is negative and handle the error
                if (paymentDouble > 0) {
                    System.out.println("Error: You have entered an incorrect amount.");
                    System.out.println("=================================");
                }

                // Create a Transaction object with the entered data
                Transaction payment = new Transaction(realDate, realTime, description,  vendor, paymentDouble);

                // Add the Transaction object to the 'transactions' list
                transactions.add(payment);

                // Format the transaction data for writing to the file
                String display = "\n" + payment.getDate() + "|" + payment.getTime() + "|" + payment.getDescription() + "|"
                        + payment.getVendor() + "|"
                        + "-" + payment.getAmount();

                // Write the formatted data to the file
                bufferedWriter.write(display);

                // Close the BufferedWriter to save the changes to the file
                bufferedWriter.close();
                System.out.println("Your Payment transaction has been added!");
            } catch (Exception ex) {
                // Handle any exceptions that may occur during input, writing, or parsing
                System.out.println("Error!");
                System.out.println("=====================================================");
            }
        }


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
                    case "A":
                        displayLedger();
                        break;
                    case "D":
                        displayDeposits();
                        break;
                    case "P":
                        displayPayments();
                        break;
                    case "R":
                        reportsMenu(scanner);
                        break;
                    case "H":
                        running = false;
                    default:
                        System.out.println("Invalid option");
                        break;
                }
            }
        }

        private static void displayLedger() {
            // Sort the transactions based on the transaction date in descending order
            // Use the Collections.sort method to sort the transactions list based on the transaction date.
            // The custom Comparator compares the dates in reverse order, ensuring that the newest transactions appear at the top.
            Collections.sort(transactions, new Comparator<Transaction>() {
                @Override
                public int compare(Transaction t1, Transaction t2) {
                    // Compare the dates in reverse order to get newest first
                    return t2.getDate().compareTo(t1.getDate());
                }
            });

            // Display the sorted transactions in a table
            System.out.println("[ ===== [All Transactions] ======] ");

            //Use \t to space the headers out.
            System.out.println("\t\t\t\tDate\t\t\t\tTime\t\t\t\tType\t\t\t\tVendor\t\t\t\tAmount");
            for (Transaction transaction : transactions) {
                System.out.println(transaction);
            }
        }





        private static void displayDeposits() {
            // This method should display a table of all deposits in the `transactions` ArrayList.
            // The table should have columns for date, time, vendor, and amount.
            ArrayList<Transaction> List = new ArrayList<>();
            //We use the Collections.sort method to sort the transactions list based on the transaction date.
            // The custom Comparator compares the dates in reverse order, ensuring that the newest transactions appear at the top.
            transactions.sort(new Comparator<Transaction>() {
                @Override
                public int compare(Transaction t1, Transaction t2) {
                    // Compare the dates in reverse order to get newest first
                    return t2.getDate().compareTo(t1.getDate());
                }
            });

        }

        private static void displayPayments() {
            // This method should display a table of all payments in the `transactions` ArrayList.
            // The table should have columns for date, time, vendor, and amount.
            ArrayList<Transaction> List = new ArrayList<>();
            System.out.println("[ ========== [All Payments] ==========]");

            //Headers and use \t to space the headers out
            System.out.println("\t\t\t\tDate\t\t\t\tTime\t\t\t\tType\t\t\t\tVendor\t\t\t\tAmount");

            //We use the Collections.sort method to sort the transactions list based on the transaction date.
            // The custom Comparator compares the dates in reverse order, ensuring that the newest transactions appear at the top.
            transactions.sort(new Comparator<Transaction>() {
                @Override
                public int compare(Transaction t1, Transaction t2) {
                    // Compare the dates in reverse order to get newest first
                    return t2.getDate().compareTo(t1.getDate());
                }
            });

            for (Transaction transaction : transactions) {
                if (transaction.getAmount() <= 0 ){
                    List.add(transaction);
                }
            }

            // Error Message
            if(List.isEmpty()){
                System.out.println("Error! There is no payments found!");
            }else{
                for(Transaction transaction : List){
                    System.out.println(transaction);
                }
            }

        }

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
                System.out.println("0) Back");

                String input = scanner.nextLine().trim();

                switch (input) {
                    case "1":
                        // Generate a report for all transactions within the current month,
                        // including the date, vendor, and amount for each transaction.
                        LocalDate thisMonth = LocalDate.now();
                        System.out.println("Displaying all the transactions for this month of " + thisMonth.getMonth() + ": ");
                        filterTransactionsByDate(thisMonth.withDayOfMonth(1), thisMonth);
                        System.out.println("=========================");
                        break;
                    case "2":
                        // Generate a report for all transactions within the previous month,
                        // including the date, vendor, and amount for each transaction.
                        LocalDate lastMonth = LocalDate.now().minusMonths(1);
                        System.out.println("Displaying all the transactions for this month of " + lastMonth.getMonth() + ": ");
                        filterTransactionsByDate(lastMonth.withDayOfMonth(1), lastMonth.withDayOfMonth(lastMonth.lengthOfMonth()));
                        System.out.println("=====================");
                        break;
                    case "3":
                        // Generate a report for all transactions within the current year,
                        // including the date, vendor, and amount for each transaction.
                        LocalDate thisYear = LocalDate.now();
                        System.out.println("Displaying all transactions for the year of " + thisYear.getYear() + " so far: ");
                        filterTransactionsByDate(thisYear.withDayOfYear(1), thisYear);
                        System.out.println("============================");
                        break;
                    case "4":
                        // Generate a report for all transactions within the previous year,
                        // including the date, vendor, and amount for each transaction.
                        LocalDate lastYear = LocalDate.now().minusYears(1);
                        System.out.println("Displaying all transactions for the year of " + lastYear.getYear() + ": ");
                        filterTransactionsByDate(lastYear.withMonth(1).withDayOfMonth(1), lastYear.withMonth(12).withDayOfMonth(31));
                        System.out.println("==========================");
                        break;
                    case "5":
                        // Prompt the user to enter a vendor name, then generate a report for all transactions
                        // with that vendor, including the date, vendor, and amount for each transaction.
                        System.out.print("Please type the name of the vendor you would like to check for: ");
                        String vendorFilter = scanner.nextLine().trim();
                        // Call the filterTransactionsByVendor function and store the result in a variable
                        ArrayList<Transaction> filteredTransactions = filterTransactionsByVendor(vendorFilter);
                        // Check if there are any matching transactions
                        if (filteredTransactions.isEmpty()) {
                            System.out.println("No transactions found for the specified vendor.");
                        } else {
                            // Print the filtered transactions
                            for (Transaction transaction : filteredTransactions) {
                                System.out.println(transaction); // You can adjust how you want to display the transactions
                            }
                        }
                        System.out.println("================================");
                    case "0":
                        running = false;

                    default:
                        System.out.println("Invalid option");
                        break;
                }
            }
        }


        private static void filterTransactionsByDate(LocalDate startDate, LocalDate endDate) {
            // This method filters the transactions by date and prints a report to the console.
            // It takes two parameters: startDate and endDate, which represent the range of dates to filter by.
            // The method loops through the transactions list and checks each transaction's date against the date range.
            // Transactions that fall within the date range are printed to the console.
            // If no transactions fall within the date range, the method prints a message indicating that there are no results.
            ArrayList<Transaction> found = new ArrayList<>();
            for (Transaction transaction : transactions) {
                LocalDate dateToCheck = transaction.getDate();
                if (dateToCheck.isAfter(startDate.minusDays(1)) && dateToCheck.isBefore(endDate.plusDays(1))) {
                    found.add(transaction);
                }
            }
            if (found.isEmpty()) {
                System.out.println("Error!");
            } else {
                for (Transaction transaction : found) {
                    System.out.println(transaction);
                }
            }
        }

        private static ArrayList<Transaction> filterTransactionsByVendor(String vendor) {
            // This method filters the transactions by vendor and prints a report to the console.
            // It takes one parameter: vendor, which represents the name of the vendor to filter by.
            // The method loops through the transactions list and checks each transaction's vendor name against the specified vendor name.
            // Transactions with a matching vendor name are printed to the console.
            // If no transactions match the specified vendor name, the method prints a message indicating that there are no results.
            ArrayList<Transaction> found = new ArrayList<>();
            for (Transaction transaction : transactions) {
                if (transaction.getVendor().equalsIgnoreCase(vendor)) {
                    found.add(transaction);
                }
            }

            // Error Message
                if (found.isEmpty()) {
                    System.out.println("Error!");
                } else {
                    for (Transaction transaction : found) {
                        System.out.println(transaction);
                    }
                }
                return found;
            }}


