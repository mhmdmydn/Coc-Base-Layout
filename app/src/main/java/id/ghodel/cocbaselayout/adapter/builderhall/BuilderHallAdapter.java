package id.ghodel.cocbaselayout.adapter.builderhall;

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
import id.ghodel.cocbaselayout.model.builderhall.Basis;

import static id.ghodel.cocbaselayout.activity.MainActivity.BASE_STRENGTH;
import static id.ghodel.cocbaselayout.activity.MainActivity.CC_TROOPS;
import static id.ghodel.cocbaselayout.activity.MainActivity.DOWNLOAD;
import static id.ghodel.cocbaselayout.activity.MainActivity.HALL;
import static id.ghodel.cocbaselayout.activity.MainActivity.ID;
import static id.ghodel.cocbaselayout.activity.MainActivity.IMAGE;
import static id.ghodel.cocbaselayout.activity.MainActivity.LAYOUT_LINK;
import static id.ghodel.cocbaselayout.activity.MainActivity.LIKE;
import static id.ghodel.cocbaselayout.activity.MainActivity.TYPE;
import static id.ghodel.cocbaselayout.activity.MainActivity.VIEW;

/**
 * Created by Muhammad Mayudin on 05-Jul-21.
 */
public class BuilderHallAdapter extends RecyclerView.Adapter<BuilderHallAdapter.BuilderHallViewHolder> implements Filterable {

    private Context context;
    private List<Basis> builderBases;
    private List<Basis> builderBaseQuerySearch;

    public BuilderHallAdapter(Context context, List<Basis> builderBases){
        this.context = context;
        this.builderBases =  builderBases;
        this.builderBaseQuerySearch = new ArrayList<>(builderBases);
    }

    @NonNull
    @Override
    public BuilderHallViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_base, parent, false);
        return new BuilderHallViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BuilderHallViewHolder holder, int position) {
        final Basis Basis = builderBases.get(position);

        String pathImage = BuildConfig.BUILDER_BASE_IMAGE_URL +"hall"+ Basis.getBuilderBase().getHall()  +"/"+ Basis.getBuilderBase().getImage();
        String linkOpenCoc =  Basis.getBuilderBase().getLayoutLink();

        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(context);
        circularProgressDrawable.setStrokeWidth(5f);
        circularProgressDrawable.setCenterRadius(50f);
        circularProgressDrawable.start();

        Picasso.get().load(pathImage).placeholder(circularProgressDrawable).error(R.drawable.ic_placeholder_image_error).into(holder.imgThumb);

        holder.tvTitle.setText(context.getString(R.string.bh) + " " + Basis.getBuilderBase().getHall());

        if(TextUtils.isEmpty(linkOpenCoc)){
            holder.tvWithLink.setText("Without Link");
            holder.tvWithLink.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.bg_text_rounded_red));
        } else {
            holder.tvWithLink.setText("With Link");
        }

        holder.tvType.setVisibility(View.GONE);

        holder.imgThumb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Intent.ACTION_VIEW);
                in.putExtra("town_hall", "BH");
                in.putExtra(ID, Basis.getBuilderBase().getBaseId());
                in.putExtra(IMAGE,"hall"+ Basis.getBuilderBase().getHall() +"/"+ Basis.getBuilderBase().getImage());
                in.putExtra(LAYOUT_LINK, Basis.getBuilderBase().getLayoutLink());
                in.putExtra(HALL, Basis.getBuilderBase().getHall());
                in.putExtra("title", Basis.getBuilderBase().getTitle());
                in.putExtra("description", Basis.getBuilderBase().getDescription());
                in.putExtra(DOWNLOAD, Basis.getBuilderBase().getDownload());
                in.putExtra(VIEW, Basis.getBuilderBase().getView());
                in.putExtra(LIKE, Basis.getBuilderBase().getLike());
                in.putExtra("created", Basis.getBuilderBase().getCreated());
                in.putExtra("modified", Basis.getBuilderBase().getModified());
                in.setClass(context, PreviewActivity.class);
                context.startActivity(in);
            }
        });
    }

    @Override
    public int getItemCount() {
        return builderBases == null ? 0 : builderBases.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }
    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Basis> filterList = new ArrayList<>();

            if(constraint == null || constraint.length() == 0){
                filterList.addAll(builderBaseQuerySearch);

            } else {
                String query = constraint.toString().toLowerCase().trim();

                for (Basis Basis : builderBaseQuerySearch) {
                    String q = Basis.getBuilderBase().getHall();
                    if(q.contains(query)){
                        filterList.add(Basis);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filterList;

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            builderBases.clear();
            builderBases.addAll((Collection<? extends Basis>) results.values);
            notifyDataSetChanged();
        }
    };



    public class BuilderHallViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView tvTitle, tvWithLink, tvType;
        AppCompatImageView imgThumb;

        public BuilderHallViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tv_title);
            tvWithLink = itemView.findViewById(R.id.tv_with_link);
            tvType = itemView.findViewById(R.id.tv_type);
            imgThumb = itemView.findViewById(R.id.image_thumb);

        }

    }
}
