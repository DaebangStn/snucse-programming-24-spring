#include <iostream>
#include <string>
using namespace std;


class Item{
    public:
        string title;
        double price;
        double SalePrice() {
            return price * 0.9;
        }
        double isAvailable() {
            return inStockQ > 0;
        }
    private:
        int inStockQ;
};

int main() {
    Item a;
    a.title = "Book";
    a.price = 2000;

    cout << "Title: " << a.title << endl;
    cout << "Price: " << a.SalePrice() << endl;
}