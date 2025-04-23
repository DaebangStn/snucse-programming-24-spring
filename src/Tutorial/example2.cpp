#include <iostream>
#include <string>
#include <cstring>

#include <assert.h>
using namespace std;


class Str{
    public:
        Str(const char* s) {
            len = strlen(s);
            str = new char[len + 1];
            assert(str != 0);
            strcpy(str, s);
        }

        ~Str() {
            delete[] str;
        }

        void printStr() {
            cout << str << endl;
        }

    private:
        char* str;
        int len;
};

int main() {
    Str s = Str("Hello");
    s.printStr();
    return 0;
}