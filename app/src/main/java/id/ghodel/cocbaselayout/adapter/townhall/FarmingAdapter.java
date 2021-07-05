package id.ghodel.cocbaselayout.adapter.townhall;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import id.ghodel.cocbaselayout.BuildConfig;
import id.ghodel.cocbaselayout.R;
import id.ghodel.cocbaselayout.activity.PreviewActivity;
import id.ghodel.cocbaselayout.model.townhall.FarmingModel;
import id.ghodel.cocbaselayout.model.townhall.WarModel;

import static id.ghodel.cocbaselayout.activity.MainActivity.ID;
import static id.ghodel.cocbaselayout.activity.MainActivity.IMAGE;
import static id.ghodel.cocbaselayout.activity.MainActivity.LAYOUT_LINK;
import static id.ghodel.cocbaselayout.activity.MainActivity.BASE_STRENGTH;
import static id.ghodel.cocbaselayout.activity.MainActivity.HALL;
import static id.ghodel.cocbaselayout.activity.MainActivity.TYPE;
import static id.ghodel.cocbaselayout.activity.MainActivity.DOWNLOAD;
import static id.ghodel.cocbaselayout.activity.MainActivity.LIKE;
import static id.ghodel.cocbaselayout.activity.MainActivity.VIEW;
import static id.ghodel.cocbaselayout.activity.MainActivity.CC_TROOPS;


public class FarmingAdapter extends RecyclerView.Adapter<FarmingAdapter.CocViewHolder> implements Filterable {

    private Context context;
    private List<FarmingModel> listFarmingModel;
    private List<FarmingModel> farmingQuerySearch;

    public FarmingAdapter(Context context, List<FarmingModel> listFarmingModel){
        this.context = context;
        this.listFarmingModel = listFarmingModel;
        this.farmingQuerySearch = new ArrayList<>(listFarmingModel);
    }

    @NonNull
    @Override
    public CocViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.list_base, parent, false);

        return new CocViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FarmingAdapter.CocViewHolder holder, int position) {
        final FarmingModel farmingModel = listFarmingModel.get(position);

        String pathImage = BuildConfig.BASE_IMAGE_URL +"hall"+ farmingModel.getLayout().getHall() + "/" + farmingModel.getLayout().getType() +"/"+ farmingModel.getLayout().getImage();
        String linkOpenCoc =  farmingModel.getLayout().getLayoutLink();

        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(context);
        circularProgressDrawable.setStrokeWidth(5f);
        circularProgressDrawable.setCenterRadius(50f);
        circularProgressDrawable.start();

        Picasso.get().load(pathImage).placeholder(circularProgressDrawable).error(R.drawable.ic_placeholder_image_error).into(holder.imgThumb);

        holder.tvTitle.setText(context.getString(R.string.town_hall) + " " + farmingModel.getLayout().getHall());

        if(TextUtils.isEmpty(linkOpenCoc)){
            holder.tvWithLink.setText("Without Link");
            holder.tvWithLink.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.bg_text_rounded_red));
        } else {
            holder.tvWithLink.setText("With Link");
        }

        holder.tvType.setText(farmingModel.getLayout().getType().toUpperCase());

        holder.imgThumb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(Intent.ACTION_VIEW);
                in.putExtra("town_hall", "TH");
                in.putExtra(ID, farmingModel.getLayout().getId());
                in.putExtra(IMAGE,"hall"+ farmingModel.getLayout().getHall() + "/" + farmingModel.getLayout().getType() +"/"+ farmingModel.getLayout().getImage());
                in.putExtra(LAYOUT_LINK, farmingModel.getLayout().getLayoutLink());
                in.putExtra(TYPE, farmingModel.getLayout().getType());
                in.putExtra(HALL, farmingModel.getLayout().getHall());
                in.putExtra(DOWNLOAD, farmingModel.getLayout().getDownload());
                in.putExtra(VIEW, farmingModel.getLayout().getView());
                in.putExtra(LIKE, farmingModel.getLayout().getLike());
                in.putExtra(BASE_STRENGTH, farmingModel.getLayout().getBaseStrength());
                in.putExtra(CC_TROOPS, farmingModel.getLayout().getCcTroops());
                in.setClass(context, PreviewActivity.class);
                context.startActivity(in);
            }
        });

    }

    @Override
    public int getItemCount() {
        return  listFarmingModel == null ? 0 : listFarmingModel.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }
    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<FarmingModel> filterList = new ArrayList<>();

            if(constraint == null || constraint.length() == 0){
                filterList.addAll(farmingQuerySearch);

            } else {
                String query = constraint.toString().toLowerCase().trim();

                for (FarmingModel farmingModelObj : farmingQuerySearch) {
                    String q = farmingModelObj.getLayout().getHall();
                    if(q.contains(query)){
                        filterList.add(farmingModelObj);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filterList;

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            listFarmingModel.clear();
            listFarmingModel.addAll((Collection<? extends FarmingModel>) results.values);
            notifyDataSetChanged();
        }
    };


    public class CocViewHolder extends RecyclerView.ViewHolder{
        AppCompatTextView tvTitle, tvWithLink, tvType;
        AppCompatImageView imgThumb;

        public CocViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvWithLink = itemView.findViewById(R.id.tv_with_link);
            tvType = itemView.findViewById(R.id.tv_type);
            imgThumb = itemView.findViewById(R.id.image_thumb);
        }
    }


}

