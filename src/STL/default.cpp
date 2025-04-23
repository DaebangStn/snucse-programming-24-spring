#include <iostream>

int main() {
    #if defined(__cplusplus)
    std::cout << "C++ standard version: " << __cplusplus << std::endl;
    #else
    std::cout << "C++ standard version not defined." << std::endl;
    #endif
    return 0;
}
