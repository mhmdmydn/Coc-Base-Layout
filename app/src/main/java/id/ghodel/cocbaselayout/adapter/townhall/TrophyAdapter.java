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
import id.ghodel.cocbaselayout.model.townhall.TrophyModel;
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


public class TrophyAdapter extends RecyclerView.Adapter<TrophyAdapter.CocViewHolder> implements Filterable {

    private Context context;
    private List<TrophyModel> listtrophyModel;
    private List<TrophyModel> trophyModelSearchQuery;

    public TrophyAdapter(Context context, List<TrophyModel> listtrophyModel){
        this.context = context;
        this.listtrophyModel = listtrophyModel;
        this.trophyModelSearchQuery = new ArrayList<>(listtrophyModel);
    }

    @NonNull
    @Override
    public CocViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.list_base, parent, false);

        return new CocViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TrophyAdapter.CocViewHolder holder, int position) {
        final TrophyModel trophyModel = listtrophyModel.get(position);

        String pathImage = BuildConfig.BASE_IMAGE_URL +"hall"+ trophyModel.getLayout().getHall() + "/" + trophyModel.getLayout().getType() +"/"+ trophyModel.getLayout().getImage();
        String linkOpenCoc =  trophyModel.getLayout().getLayoutLink();

        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(context);
        circularProgressDrawable.setStrokeWidth(5f);
        circularProgressDrawable.setCenterRadius(50f);
        circularProgressDrawable.start();

        Picasso.get().load(pathImage).placeholder(circularProgressDrawable).error(R.drawable.ic_placeholder_image_error).into(holder.imgThumb);

        holder.tvTitle.setText(context.getString(R.string.town_hall) + " " + trophyModel.getLayout().getHall());

        if(TextUtils.isEmpty(linkOpenCoc)){
            holder.tvWithLink.setText("Without Link");
            holder.tvWithLink.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.bg_text_rounded_red));
        } else {
            holder.tvWithLink.setText("With Link");
        }

        holder.tvType.setText(trophyModel.getLayout().getType().toUpperCase());

        holder.imgThumb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(Intent.ACTION_VIEW);
                in.putExtra("town_hall", "TH");
                in.putExtra(ID, trophyModel.getLayout().getId());
                in.putExtra(IMAGE,"hall"+ trophyModel.getLayout().getHall() + "/" + trophyModel.getLayout().getType() +"/"+ trophyModel.getLayout().getImage());
                in.putExtra(LAYOUT_LINK, trophyModel.getLayout().getLayoutLink());
                in.putExtra(TYPE, trophyModel.getLayout().getType());
                in.putExtra(HALL, trophyModel.getLayout().getHall());
                in.putExtra(DOWNLOAD, trophyModel.getLayout().getDownload());
                in.putExtra(VIEW, trophyModel.getLayout().getView());
                in.putExtra(LIKE, trophyModel.getLayout().getLike());
                in.putExtra(BASE_STRENGTH, trophyModel.getLayout().getBaseStrength());
                in.putExtra(CC_TROOPS, trophyModel.getLayout().getCcTroops());
                in.setClass(context, PreviewActivity.class);
                context.startActivity(in);
            }
        });

    }

    @Override
    public int getItemCount() {
        return  listtrophyModel == null ? 0 : listtrophyModel.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }
    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<TrophyModel> filterList = new ArrayList<>();

            if(constraint == null || constraint.length() == 0){
                filterList.addAll(trophyModelSearchQuery);

            } else {
                String query = constraint.toString().toLowerCase().trim();

                for (TrophyModel trophyModelObj : trophyModelSearchQuery) {
                    String q = trophyModelObj.getLayout().getHall();
                    if(q.contains(query)){
                        filterList.add(trophyModelObj);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filterList;

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            listtrophyModel.clear();
            listtrophyModel.addAll((Collection<? extends TrophyModel>) results.values);
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

