package id.ghodel.cocbaselayout.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import id.ghodel.cocbaselayout.R;
import id.ghodel.cocbaselayout.model.Layout;
import id.ghodel.cocbaselayout.model.WarModel;

/**
 * Created by Muhammad Mayudin on 03-Jul-21.
 */
public class CocAdapter extends RecyclerView.Adapter<CocAdapter.CocViewHolder> {

    private Context context;
    private List<WarModel> layout;

    public CocAdapter(Context context, List<WarModel> layout){
        this.context = context;
        this.layout = layout;
    }

    @Override
    public CocViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.list_base, parent, false);

        return new CocViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CocAdapter.CocViewHolder holder, int position) {
        final WarModel War = layout.get(position);

        String pathImage = "http://coc.vnhow.vn/img/uploads/layouts/large/"+"hall"+ War.getLayout().getHall() + "/" + War.getLayout().getType() +"/"+ War.getLayout().getImage();

        Picasso.get().load(pathImage).into(holder.imgThumb);

        holder.tvTitle.setText(pathImage);

    }

    @Override
    public int getItemCount() {
        return layout.size();
    }

    public class CocViewHolder extends RecyclerView.ViewHolder{
        AppCompatTextView tvTitle;
        AppCompatImageView imgThumb;

        public CocViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            imgThumb = itemView.findViewById(R.id.image_thumb);
        }
    }
}
