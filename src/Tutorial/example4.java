import java.util.Scanner;

public class example4 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the title of the book: ");
        String title = sc.nextLine();

        System.out.println("Enter the price of the book: ");
        double price = sc.nextDouble();

        System.out.println("Title is: " + title + " and price is: " + price);
    }
}
