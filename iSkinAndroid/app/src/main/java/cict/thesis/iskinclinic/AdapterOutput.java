package cict.thesis.iskinclinic;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;


public class AdapterOutput extends RecyclerView.Adapter<AdapterOutput.DashBoardMealHolder> {
    private Context mCtx;
    private List<DashboardOutput> dashboardOutputList;
    String getID;
    onClickInterface onClickInterface;


    public AdapterOutput(Context mCtx, List<DashboardOutput> dashboardOutputList, onClickInterface onClickInterface) {
        this.mCtx = mCtx;
        this.dashboardOutputList = dashboardOutputList;
        this.onClickInterface = onClickInterface;
    }

    @NonNull
    @Override
    public DashBoardMealHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.list_output, parent, false);
        return new DashBoardMealHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final DashBoardMealHolder holder, final int position) {
        DashboardOutput dashboardOutput = dashboardOutputList.get(position);
        String output = dashboardOutput.getOutput();

        if(output.equals("Impetigo")){
            holder.output.setText("Impetigo");
        }
        else if(output.equals("Melanoma")){
            holder.output.setText("Melanoma");
        }

        else if(output.equals("Eczema")){
            holder.output.setText("Eczema");
        }

        else if(output.equals("Boils")){
            holder.output.setText("Boils");
        }

        else if(output.equals("Keratosis")){
            holder.output.setText("Keratosis");
        }

        else if(output.equals("Ringworm")){
            holder.output.setText("Ringworm");
        }

        else if(output.equals("Psoriasis")){
            holder.output.setText("Psoriasis");
        }

        else if(output.equals("NoDisease")){
            output = "Not Found in Database";
            holder.output.setText(output);
            holder.resultview.setVisibility(View.GONE);
        }

        holder.resultview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mCtx, OutputInfo.class);
                intent.putExtra("outputID", dashboardOutputList.get(position).getOutputID());
                intent.putExtra("output", dashboardOutputList.get(position).getOutput());
                mCtx.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return dashboardOutputList.size();
    }

    class DashBoardMealHolder extends RecyclerView.ViewHolder {
        TextView output, resultview;

        LinearLayout linearLayout;

        public DashBoardMealHolder(View itemView) {
            super(itemView);
            output = itemView.findViewById(R.id.textOutput);
            resultview = itemView.findViewById(R.id.textViewOutput);
            linearLayout = itemView.findViewById(R.id.linear_dash);
        }
    }

}
