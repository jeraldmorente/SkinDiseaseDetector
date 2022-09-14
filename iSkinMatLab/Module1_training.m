%%Taking an Image
clc
clear all
close all
for i=1:1283
cd allsetfinal
st=int2str(i)
str=strcat(st,'.jpg');
im=imread(str);
cd C:\Users\MORENTE\Documents\MATLAB\iSkinClinic
%imshow(im);
%title('Input face');    
%c=input('Enter the class');
im=rgb2gray(im);
%%apply median Filter
K=MedianFilter(im);
imshow(K);

thresh = multithresh(K,2);
seg_I = imquantize(K,thresh);
RGB = label2rgb(seg_I); 	 
L=RGB;
K=rgb2gray(L);
imshow(K);

F=FeatureStatistical(K);
try
    load dataset;
    F=[F];
    dataset=[dataset;F];
    save dataset.mat dataset
catch
    db=[F];
    save dataset.mat dataset
end

end