#include <iostream>
using namespace std;

void reverseNumber(int n) {
    if (n == 0) return;          // base case
    cout << n % 10;              // print last digit
    reverseNumber(n / 10);       // recursive call
}

int main() {
    int num = 12345;
    cout << "Reversed: ";
    reverseNumber(num);          // Output: 54321
    cout << endl;
    return 0;
}
