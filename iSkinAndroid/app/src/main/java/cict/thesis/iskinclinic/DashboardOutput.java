package cict.thesis.iskinclinic;

public class DashboardOutput {
    private int outputID;
    private String output;

    public DashboardOutput(int outputID, String output)
    {
        this.outputID = outputID;
        this.output = output;
    }
    public int getOutputID() {
        return outputID;
    }
    public String getOutput() {
        return output;
    }


}
