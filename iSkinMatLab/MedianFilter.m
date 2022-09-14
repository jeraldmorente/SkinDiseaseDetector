function [K]=MedianFilter(im);

J = imnoise(im,'salt & pepper',0.02);
K = medfilt2(J);

