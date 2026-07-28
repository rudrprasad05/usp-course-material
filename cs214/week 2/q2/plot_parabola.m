% CS214 Week 2 - Question 2B
% Draw a graph for y = x^2.

x = -10:0.1:10;
y = x .^ 2;

figure;
plot(x, y, 'b-', 'LineWidth', 2);
grid on;
xlabel('x');
ylabel('y');
title('Graph of y = x^2');
