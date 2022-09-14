function varargout = iSkinClinicHome(varargin)

gui_Singleton = 1;
gui_State = struct('gui_Name', mfilename, ...
'gui_Singleton', gui_Singleton, ...
'gui_OpeningFcn', @iSkinClinicHome_OpeningFcn, ...
'gui_OutputFcn', @iSkinClinicHome_OutputFcn, ...
'gui_LayoutFcn', [] , ...
'gui_Callback', []);
if nargin && ischar(varargin{1})
gui_State.gui_Callback = str2func(varargin{1});
end
if nargout
[varargout{1:nargout}] = gui_mainfcn(gui_State, varargin{:});
else
gui_mainfcn(gui_State, varargin{:});
end
% End initialization code - DO NOT EDIT
% --- Executes just before HOME_ is made visible.
function iSkinClinicHome_OpeningFcn(hObject, eventdata, handles, varargin)
handles.output = hObject;
guidata(hObject, handles);

set(handles.startprocess,'Enable','on')
set(handles.stopprocess,'Enable','off')



function varargout =  iSkinClinicHome_OutputFcn(hObject, eventdata, handles)
varargout{1} = handles.output;

a3=imresize(imread('head.png'),[400 400]);
axes(handles.axes3);
imshow(a3);

function startprocess_Callback(hObject, eventdata, handles)
javaaddpath('mysql-connector-java-5.1.6-bin.jar')
conn = database('matlabskin','root','','Vendor','MySQL','Server','localhost');   
myFolder = 'C:\xampp\htdocs\iskinclinic\uploads';
olddir   = dir(myFolder);
global im im2
scfolder ='SCANNING FOLDER';
set(handles.scanstatus, 'String', scfolder);
set(handles.startprocess,'Enable','off')
set(handles.stopprocess,'userdata',0);
set(handles.stopprocess,'Enable','on');
while true
  pause(5)
  newdir = dir(myFolder);
  drawnow
  if ~isequal(newdir, olddir)
    scfolder1 ='NEW IMAGE FOUND';
    %-------------
    set(handles.text6, 'String', '');
    set(handles.text14, 'String', ''); 
    set(handles.text15, 'String', ''); 
    set(handles.text16, 'String', '');
    set(handles.text17, 'String', '');
    set(handles.text13, 'String', '');  
    set(handles.text18, 'String', '');
    
    
    set(handles.scanstatus, 'String', scfolder1);
    fprintf('NEW IMAGE FOUND\n');
    olddir = newdir;
   % jpgfiles = dir(fullfile(myFolder,'\*.jpg*'))
    filePattern = fullfile(myFolder, '*.jpg');
    jpegFiles = dir(filePattern);
    n=length(jpegFiles);
    disp(n)
    st=int2str(n);
    str=strcat(st,'.jpg');
   % im= imread(fullfile(myFolder,str), 'jpg'); 
    
    path = fullfile(myFolder,str), 'jpg';
    im2 = imread(path);
    set(handles.text1, 'String', path);
    axes(handles.axes1);
    imshow(im2);
    im2 = rgb2gray(im2);
    K=MedianFilter(im2);
    imshow(K);
    thresh = multithresh(K,2);
    seg_I = imquantize(K,thresh);
    RGB = label2rgb(seg_I); 	 
    L=RGB;
    K=rgb2gray(L);
    axes(handles.axes2);
    imshow(K);
    im=K;
%-----------------FEATURE EXTRACTION----------------
    F=FeatureStatistical(K); 
        load dataset1;
        F=[F];
        dataset1=[dataset1;F];
        save dataset1.mat dataset1
        FileData1 = load('dataset1.mat');
        csvwrite('dataset1.csv', FileData1.dataset1);
        dataTable1 = readtable('dataset1.csv');
        dataTable1.Properties.VariableNames{1} = 'en1';
        dataTable1.Properties.VariableNames{2} = 'meanval';
        dataTable1.Properties.VariableNames{3} = 'meabs';
        dataTable1.Properties.VariableNames{4} = 'medabd';
        dataTable1.Properties.VariableNames{5} = 'ener1';
        dataTable1.Properties.VariableNames{6} = 'stdA';
        dataTable1.Properties.VariableNames{7} = 'L1norm';
        dataTable1.Properties.VariableNames{8} = 'L2norm';
        dataTable1.Properties.VariableNames{9} = 'k';
        dataTable1.Properties.VariableNames{10} = 's';
        dataTable1.Properties.VariableNames{11} = 'cor1';
        dataTable1.Properties.VariableNames{12} = 'm';
        dataTable1.Properties.VariableNames{13} = 'en12';
        dataTable1.Properties.VariableNames{14} = 'ener12';
        dataTable1.Properties.VariableNames{15} = 'cor12';
        dataTable1.Properties.VariableNames{16} = 'conts';
        dataTable1.Properties.VariableNames{17} = 'ener';
        dataTable1.Properties.VariableNames{18} = 'homo';
        dataTable1.Properties.VariableNames{19} = 'corre';
        load('trainedFinal.mat')
        yfit = trainedFinal.predictFcn(dataTable1);
        row1 = yfit([end]);     
          
        %SKEWNESS
        sk=num2str(F(1,10));
        set(handles.text14, 'String', sk); 
        %KURTOSIS
        krts=num2str(F(1,9));
        set(handles.text15, 'String', krts); 
        %MEAN
        mn=num2str(F(1,2));
        set(handles.text16, 'String', mn);
        %ENTROPHY
        entropy1=num2str(F(1,1));
        set(handles.text17, 'String', entropy1);
        %ENERGY
        energy3=num2str(F(1,17));
        set(handles.text13, 'String', energy3);  
        %HOMOEGENITY
        homo2=num2str(F(1,18));
        set(handles.text18, 'String', homo2);
%------------------ DISEASE TYPE-----------------------
        disp(row1);
        b=char(row1);
        sqlCommand =  sprintf('insert into result(output) values (''%s'')',b); 
        r=exec(conn, sqlCommand); 
        set(handles.text6, 'String', b);
            elseif get(handles.stopprocess, 'userdata')
            set(handles.startprocess,'Enable','on')
            set(handles.stopprocess,'Enable','off')
            scfolder3 ='PROCESS STOPED';
            set(handles.scanstatus, 'String', scfolder3);
            break;     
     else
        fprintf('NO NEW UPLOAD\n')
        scfolder2 ='NO NEW UPLOAD';
        set(handles.scanstatus, 'String', scfolder2);
    end
    end



function aboutuser_Callback(hObject, eventdata, handles)
close(gcf);
start1



function Exit_Callback(hObject, eventdata, handles)
conf=questdlg('Are you sure you want to Exit','Exit Image','Yes','No','No');
switch conf
case 'Yes'
close(gcf)
case 'No'
return
end
% --------------------------------------------------------------------


% --- Executes on button press in stopprocess.
function stopprocess_Callback(hObject, eventdata, handles)
set(handles.stopprocess, 'userdata',1);
%SKEWNESS
set(handles.text14, 'String', ''); 
%KURTOSIS
set(handles.text15, 'String', ''); 
%MEAN'
set(handles.text16, 'String', '');
%ENTROPHY'
set(handles.text17, 'String', '');
%ENERGY'
set(handles.text13, 'String', '');  
%HOMOEGENITY'
set(handles.text18, 'String', '');
set(handles.text6, 'String', '');


% --------------------------------------------------------------------
function xit_Callback(hObject, eventdata, handles)
conf=questdlg('Are you sure you want to Exit','Exit Image','Yes','No','No');
switch conf
case 'Yes'
close(gcf)
case 'No'
return
end
