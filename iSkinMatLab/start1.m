function varargout = start1(varargin)
gui_Singleton = 1;
gui_State = struct('gui_Name', mfilename, ...
'gui_Singleton', gui_Singleton, ...
'gui_OpeningFcn', @start1_OpeningFcn, ...
'gui_OutputFcn', @start1_OutputFcn, ...
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

function start1_OpeningFcn(hObject, eventdata, handles, varargin)
handles.output = hObject;
guidata(hObject, handles);


a3=imresize(imread('head.png'),[350 340]);
axes(handles.axes1);
imshow(a3);

function varargout = start1_OutputFcn(hObject, eventdata, handles)
varargout{1} = handles.output;
% --- Executes on button press in startbutton.
function startbutton_Callback(hObject, eventdata, handles)
close(gcf)
iSkinClinicHome

function exitbutton_Callback(hObject, eventdata, handles)
conf=questdlg('Are you sure you want to Exit','Exit Image','Yes','No','No');
switch conf
case 'Yes'
close(gcf)
case 'No'
return
end
% --------------------------------------------------------------------
function menu_Callback(hObject, eventdata, handles)
function help_Callback(hObject, eventdata, handles)
close(gcf)
help_guide
% --------------------------------------------------------------------
function howtoused_Callback(hObject, eventdata, handles)

function userguide_Callback(hObject, eventdata, handles)

open user_manual.pdf
% --------------------------------------------------------------------

% --------------------------------------------------------------------
function softwareinstall_Callback(hObject, eventdata, handles)
open softwareInstallationGuide.pdf
