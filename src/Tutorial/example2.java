public class example2 {
    public String title;
    public double price;
    private int inStockQ;

    public double SalesPrice() {
        return price * 0.9;
    }

    public boolean isAvailable() {
        return inStockQ > 0;
    }
    public static void main(String[] args) {
        example2 a = new example2();
        a.title = "Java";
        a.price = 100;
        System.out.println(a.SalesPrice());
    }
}
