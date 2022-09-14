package cict.thesis.iskinclinic;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;



public class AdapterSkinDiseasesList extends RecyclerView.Adapter<AdapterSkinDiseasesList.MyViewHolder> {

    private List<SkinAppDiseasesList> skinAppDiseasesLists;
    private Context context;
    onClickInterface onClickInterface;

    public AdapterSkinDiseasesList(List<SkinAppDiseasesList> skinAppDiseasesLists, Context context, onClickInterface onClickInterface) {
        this.skinAppDiseasesLists = skinAppDiseasesLists;
        this.context = context;
        this.onClickInterface = onClickInterface;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.skin_app_sdlist, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.sdname.setText(skinAppDiseasesLists.get(position).getSkinDiseaseName());

        holder.sdname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickInterface.setClick(position);
                Intent intent = new Intent(context, SkinDiseaseInfo.class);
                intent.putExtra("skinID", skinAppDiseasesLists.get(position).getSkinID());
                intent.putExtra("skinDiseaseName", skinAppDiseasesLists.get(position).getSkinDiseaseName());
                intent.putExtra("skinDiseaseImage", skinAppDiseasesLists.get(position).getSkinDiseaseImage());
                intent.putExtra("skinDiseaseDesc", skinAppDiseasesLists.get(position).getSkinDiseaseDesc());
                intent.putExtra("skinDiseaseCauses", skinAppDiseasesLists.get(position).getSkinDiseaseCauses());
                intent.putExtra("skinDiseaseSymptoms", skinAppDiseasesLists.get(position).getSkinDiseaseSymptoms());
                intent.putExtra("skinDiseasePrevention", skinAppDiseasesLists.get(position).getSkinDiseasePrevention());
                intent.putExtra("skinDiseaseTreatment", skinAppDiseasesLists.get(position).getSkinDiseaseTreatment());
                context.startActivity(intent);
            }
        });
        Glide.with(context)
                .load(skinAppDiseasesLists.get(position).getSkinDiseaseImage()).error(R.mipmap.ic_launcher)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(holder.sdpicture);
    }

    @Override
    public int getItemCount() {
        return skinAppDiseasesLists.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView sdname;
        ImageView sdpicture;
        public MyViewHolder(View itemView) {
            super(itemView);
            sdname = itemView.findViewById(R.id.textsdname);
            sdpicture = itemView.findViewById(R.id.dash_sdpicture);
        }
    }
}
