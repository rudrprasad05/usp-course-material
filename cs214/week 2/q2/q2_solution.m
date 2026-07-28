% CS214 Week 2 - Question 2A
% Calculate the total sale for five products and six customers.

unitPrices = [2.50, 1.00, 33.90, 7.00, 200.00];

itemsPurchased = [
    3 0 0 1 1;
    0 1 1 0 0;
    5 0 0 0 0;
    3 4 2 1 0;
    0 0 0 0 1;
    7 0 3 0 0
];

customerTotals = itemsPurchased * unitPrices';
totalSale = sum(customerTotals);

disp('Customer totals:');
disp(customerTotals);

fprintf('Total sale: %.2f\n', totalSale);
