#include <iostream>
using namespace std;

void child(int **a_ptr) {
    int a = 10;
    *a_ptr = &a;
}


void parent() {
    int a = 10;
    int* b = &a;
    int** c = new int*;
    child(c);
    cout << "parent: " << b << endl;
    cout << "child: " << *c << endl;

    if (b > *c) {
        cout << "stack grows down" << endl;
    } else {
        cout << "stack grows up" << endl;
    }

    delete c;
}


int main() {
    parent();
    return 0;
}