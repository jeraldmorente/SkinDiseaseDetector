FileData = load('dataset.mat'); 
csvwrite('dataset.csv', FileData.dataset);
dataTable = readtable('dataset.csv');
dataTable.Properties.VariableNames{1} = 'en1';
dataTable.Properties.VariableNames{2} = 'meanval';
dataTable.Properties.VariableNames{3} = 'meabs';
dataTable.Properties.VariableNames{4} = 'medabd';
dataTable.Properties.VariableNames{5} = 'ener1';
dataTable.Properties.VariableNames{6} = 'stdA';
dataTable.Properties.VariableNames{7} = 'L1norm';
dataTable.Properties.VariableNames{8} = 'L2norm';
dataTable.Properties.VariableNames{9} = 'k';
dataTable.Properties.VariableNames{10} = 's';
dataTable.Properties.VariableNames{11} = 'cor1';
dataTable.Properties.VariableNames{12} = 'm';
dataTable.Properties.VariableNames{13} = 'en12';
dataTable.Properties.VariableNames{14} = 'ener12';
dataTable.Properties.VariableNames{15} = 'cor12';
dataTable.Properties.VariableNames{16} = 'conts';
dataTable.Properties.VariableNames{17} = 'ener';
dataTable.Properties.VariableNames{18} = 'homo';
dataTable.Properties.VariableNames{19} = 'corre';
dataTable.Var20{1,1} = 'Impetigo';

for i=1:200; dataTable.Var20{i,1} = 'Impetigo'; end
for i=201:400; dataTable.Var20{i,1} = 'Eczema'; end
for i=401:600; dataTable.Var20{i,1} = 'Melanoma'; end
for i=601:800; dataTable.Var20{i,1} = 'Keratosis'; end
for i=801:1000; dataTable.Var20{i,1} = 'Boils'; end
for i=1001:1200; dataTable.Var20{i,1} = 'NoDisease'; end
for i=1201:1239; dataTable.Var20{i,1} = 'Psoriasis'; end
for i=1240:1283; dataTable.Var20{i,1} = 'Ringworm'; end

