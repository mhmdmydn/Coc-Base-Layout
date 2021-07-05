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


public class WarAdapter extends RecyclerView.Adapter<WarAdapter.CocViewHolder> implements Filterable {

    private Context context;
    private List<WarModel> layout;
    private List<WarModel> warQuerySearch;

    public WarAdapter(Context context, List<WarModel> layout){
        this.context = context;
        this.layout = layout;
        this.warQuerySearch = new ArrayList<>(layout);
    }

    @NonNull
    @Override
    public CocViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.list_base, parent, false);

        return new CocViewHolder(view);
    }

    @Override
    public void onBindViewHolder(WarAdapter.CocViewHolder holder, int position) {
        final WarModel war = layout.get(position);

        String pathImage = BuildConfig.BASE_IMAGE_URL +"hall"+ war.getLayout().getHall() + "/" + war.getLayout().getType() +"/"+ war.getLayout().getImage();
        String linkOpenCoc =  war.getLayout().getLayoutLink();

        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(context);
        circularProgressDrawable.setStrokeWidth(5f);
        circularProgressDrawable.setCenterRadius(50f);
        circularProgressDrawable.start();

        Picasso.get().load(pathImage).placeholder(circularProgressDrawable).error(R.drawable.ic_placeholder_image_error).into(holder.imgThumb);

        holder.tvTitle.setText(context.getString(R.string.town_hall) + " " + war.getLayout().getHall());

        if(TextUtils.isEmpty(linkOpenCoc)){
            holder.tvWithLink.setText("Without Link");
            holder.tvWithLink.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.bg_text_rounded_red));
        } else {
            holder.tvWithLink.setText("With Link");
        }

        holder.tvType.setText(war.getLayout().getType().toUpperCase());

        holder.imgThumb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(Intent.ACTION_VIEW);
                in.putExtra("town_hall", "TH");
                in.putExtra(ID, war.getLayout().getId());
                in.putExtra(IMAGE,"hall"+ war.getLayout().getHall() + "/" + war.getLayout().getType() +"/"+ war.getLayout().getImage());
                in.putExtra(LAYOUT_LINK, war.getLayout().getLayoutLink());
                in.putExtra(TYPE, war.getLayout().getType());
                in.putExtra(HALL, war.getLayout().getHall());
                in.putExtra(DOWNLOAD, war.getLayout().getDownload());
                in.putExtra(VIEW, war.getLayout().getView());
                in.putExtra(LIKE, war.getLayout().getLike());
                in.putExtra(BASE_STRENGTH, war.getLayout().getBaseStrength());
                in.putExtra(CC_TROOPS, war.getLayout().getCcTroops());
                in.setClass(context, PreviewActivity.class);
                context.startActivity(in);
            }
        });

    }

    @Override
    public int getItemCount() {
        return  layout == null ? 0 : layout.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }
    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<WarModel> filterList = new ArrayList<>();

            if(constraint == null || constraint.length() == 0){
                filterList.addAll(warQuerySearch);

            } else {
                String query = constraint.toString().toLowerCase().trim();

                for (WarModel warObject : warQuerySearch) {
                    String q = warObject.getLayout().getHall();
                    if(q.contains(query)){
                        filterList.add(warObject);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filterList;

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            layout.clear();
            layout.addAll((Collection<? extends WarModel>) results.values);
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
