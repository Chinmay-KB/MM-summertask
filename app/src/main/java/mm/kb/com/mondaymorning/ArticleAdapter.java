package mm.kb.com.mondaymorning;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter{

    private ArrayList<ArticleStore> dataSet;
    private Context ct;
    private int total_types;

    public ArticleAdapter(ArrayList<ArticleStore> dataSet, Context ct, int total_types) {
        this.dataSet = dataSet;
        this.ct = ct;
        this.total_types = total_types;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == ArticleStore.TEXT){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_text_layout,parent,false);
            return new TextHolder(view);
        }
        else if (viewType==ArticleStore.IMAGE){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_image_layout, parent, false);
            return new ImageHolder(view);
        }
        else if(viewType==ArticleStore.TITLE)
        {
            view=LayoutInflater.from(parent.getContext()).inflate(R.layout.article_title_layout,parent,false);
            return new TitleHolder(view);
        }
        else{
            view=LayoutInflater.from(parent.getContext()).inflate(R.layout.article_category_layout,parent,false);
            return new CategoryHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ArticleStore object = dataSet.get(position);
        if(object!=null){
            switch (object.type){
                case ArticleStore.TEXT:
                    ((TextHolder) holder).textView.setText(Html.fromHtml(object.getText()));
                    break;
                case ArticleStore.IMAGE:
                    Glide.with(ct).load(object.getText())
                            .thumbnail(0.25f)
                            .apply(new RequestOptions().centerCrop())
                            .into(((ImageHolder) holder).imageView);
                    break;
                case ArticleStore.TITLE:
                    ((TitleHolder)holder).textView.setText(object.getText());
                    break;
                case ArticleStore.CATEGORY:
                    ((CategoryHolder)holder).textView.setText(object.getText());
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public static class TextHolder extends RecyclerView.ViewHolder{

        TextView textView;
        public TextHolder(View itemView) {
            super(itemView);
            this.textView = itemView.findViewById(R.id.article_text_container);
        }
    }
    public static class ImageHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        public ImageHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.article_image_container);
        }
    }
    public static class TitleHolder extends RecyclerView.ViewHolder{
        TextView textView;
        public TitleHolder(View itemView){
            super(itemView);
            this.textView=itemView.findViewById(R.id.article_title_container);
        }
    }
    public static class CategoryHolder extends RecyclerView.ViewHolder{
        TextView textView;
        public CategoryHolder(View itemView){
            super(itemView);
            this.textView=itemView.findViewById(R.id.category_container);
        }
    }

    @Override
    public int getItemViewType(int position) {
        switch (dataSet.get(position).type){
            case 0:
                return ArticleStore.TEXT;
            case 1:
                return ArticleStore.IMAGE;
            case 10:
                return ArticleStore.TITLE;
            case 11:
                return ArticleStore.CATEGORY;
            default:
                return -1;
        }
    }
}