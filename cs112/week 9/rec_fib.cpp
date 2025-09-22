#include <iostream>
using namespace std;

int fibonacci(int n) {
    if (n == 0) return 0;   // base case
    if (n == 1) return 1;   // base case
    return fibonacci(n-1) + fibonacci(n-2); // recursive case
}

int main() {
    for(int i=0; i<10; i++)
        cout << fibonacci(i) << " ";
    cout << endl;
    return 0;
}
