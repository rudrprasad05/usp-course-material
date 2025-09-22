#include <iostream>

using namespace std;

int sum(int n){
    if(n == 1) return n;
    return n + sum(n-1);
}

int sum(int min, int max) {
    if (min > max) return 0;    // handle invalid range
    if (min == max) return min; // base case
    return min + sum(min + 1, max); // recursive case
}

int main(){
    int a = sum(5);
    cout << a << endl;

    int b = sum(5, 10);
    cout << b << endl;
}