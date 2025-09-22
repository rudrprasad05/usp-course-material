#include <iostream>
using namespace std;

int factorial(int n) {
    if (n == 0 || n == 1) return 1; // base case
    return n * factorial(n - 1);    // recursive case
}

int main() {
    cout << "Factorial of 5: " << factorial(5) << endl; // 120
    return 0;
}
