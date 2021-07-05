package id.ghodel.cocbaselayout.adapter.townhall;

/**
 * Created by Muhammad Mayudin on 06-Jul-21.
 */


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
import id.ghodel.cocbaselayout.model.townhall.HybridModel;
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


public class HybridAdapter extends RecyclerView.Adapter<HybridAdapter.CocViewHolder> implements Filterable {

    private Context context;
    private List<HybridModel> listHybridModel;
    private List<HybridModel> hybridQuerySearch;

    public HybridAdapter(Context context, List<HybridModel> listHybridModel){
        this.context = context;
        this.listHybridModel = listHybridModel;
        this.hybridQuerySearch = new ArrayList<>(listHybridModel);
    }

    @NonNull
    @Override
    public CocViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.list_base, parent, false);

        return new CocViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HybridAdapter.CocViewHolder holder, int position) {
        final HybridModel hybridModel = listHybridModel.get(position);

        String pathImage = BuildConfig.BASE_IMAGE_URL +"hall"+ hybridModel.getLayout().getHall() + "/" + hybridModel.getLayout().getType() +"/"+ hybridModel.getLayout().getImage();
        String linkOpenCoc =  hybridModel.getLayout().getLayoutLink();

        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(context);
        circularProgressDrawable.setStrokeWidth(5f);
        circularProgressDrawable.setCenterRadius(50f);
        circularProgressDrawable.start();

        Picasso.get().load(pathImage).placeholder(circularProgressDrawable).error(R.drawable.ic_placeholder_image_error).into(holder.imgThumb);

        holder.tvTitle.setText(context.getString(R.string.town_hall) + " " + hybridModel.getLayout().getHall());

        if(TextUtils.isEmpty(linkOpenCoc)){
            holder.tvWithLink.setText("Without Link");
            holder.tvWithLink.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.bg_text_rounded_red));
        } else {
            holder.tvWithLink.setText("With Link");
        }

        holder.tvType.setText(hybridModel.getLayout().getType().toUpperCase());

        holder.imgThumb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(Intent.ACTION_VIEW);
                in.putExtra("town_hall", "TH");
                in.putExtra(ID, hybridModel.getLayout().getId());
                in.putExtra(IMAGE,"hall"+ hybridModel.getLayout().getHall() + "/" + hybridModel.getLayout().getType() +"/"+ hybridModel.getLayout().getImage());
                in.putExtra(LAYOUT_LINK, hybridModel.getLayout().getLayoutLink());
                in.putExtra(TYPE, hybridModel.getLayout().getType());
                in.putExtra(HALL, hybridModel.getLayout().getHall());
                in.putExtra(DOWNLOAD, hybridModel.getLayout().getDownload());
                in.putExtra(VIEW, hybridModel.getLayout().getView());
                in.putExtra(LIKE, hybridModel.getLayout().getLike());
                in.putExtra(BASE_STRENGTH, hybridModel.getLayout().getBaseStrength());
                in.putExtra(CC_TROOPS, hybridModel.getLayout().getCcTroops());
                in.setClass(context, PreviewActivity.class);
                context.startActivity(in);
            }
        });

    }

    @Override
    public int getItemCount() {
        return  listHybridModel == null ? 0 : listHybridModel.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }
    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<HybridModel> filterList = new ArrayList<>();

            if(constraint == null || constraint.length() == 0){
                filterList.addAll(hybridQuerySearch);

            } else {
                String query = constraint.toString().toLowerCase().trim();

                for (HybridModel hybridModelObj : hybridQuerySearch) {
                    String q = hybridModelObj.getLayout().getHall();
                    if(q.contains(query)){
                        filterList.add(hybridModelObj);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filterList;

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            listHybridModel.clear();
            listHybridModel.addAll((Collection<? extends HybridModel>) results.values);
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

