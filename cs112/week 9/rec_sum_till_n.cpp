#include <iostream>
using namespace std;

int recursive_sum(int n) {
    if (n == 1) return 1;          // base case
    return n + recursive_sum(n-1); // recursive case
}

int recursive_sum_range(int start, int end) {
    if (start > end) return 0;     // base case
    return start + recursive_sum_range(start + 1, end);
}

int main() {
    cout << "Sum(10): " << recursive_sum(10) << endl;
    cout << "Sum(25): " << recursive_sum(25) << endl;
    cout << "Sum(11 to 25): " << recursive_sum_range(11, 25) << endl;

    // Example with negative numbers
    cout << "Sum(-5 to 0): " << recursive_sum_range(-5, 0) << endl;

    return 0;
}
