# Question 2 - MATLAB Solution

This directory answers the Week 2 MATLAB question.

## Part A

The unit prices are stored as a row vector and the customer purchases are stored as a
matrix. Each customer's sale is calculated with matrix multiplication:

```matlab
customerTotals = itemsPurchased * unitPrices';
totalSale = sum(customerTotals);
```

The total sale is:

```text
667.40
```

## Part B

The toy graph for `y = x^2` is implemented in `plot_parabola.m`.

## Run In MATLAB

```matlab
q2_solution
plot_parabola
```
