package mm.kb.com.mondaymorning;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Intent;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;


public class RVAdapter extends RecyclerView.Adapter<RVAdapter.PersonViewHolder> {
    Context context;
    private String[] mTitleArray;
    private List<recyclerData> list;

    public RVAdapter(Context contxt, List<recyclerData>listItems) {
        context=contxt;
        list=listItems;
    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView subjectCode;
        TextView authors;
        TextView date;
        TextView postId;
        public ImageView article_card_image;

        PersonViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.card_view);
            subjectCode = (TextView)itemView.findViewById(R.id.tv_text);
            authors=(TextView)itemView.findViewById(R.id.tv_blah);
            date=(TextView)itemView.findViewById(R.id.tv_blah2);
            article_card_image=(ImageView)itemView.findViewById((R.id.iv_image));
            postId=(TextView)itemView.findViewById(R.id.page_id_stored);
        }
    }
    String title;
    List<String> feature;
    List<String> authorList;
    List<String> dateList;
    List<String> postIdList;

  //  RVAdapter(ArticleActivity interimActivity, List<String> feature){
    //    this.feature = feature;
    //}

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


    @Override
    public PersonViewHolder onCreateViewHolder(final ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view, viewGroup, false);
        final PersonViewHolder pvh = new PersonViewHolder(v);

        v.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int index=pvh.getAdapterPosition();
                Intent in=new Intent(viewGroup.getContext(),ArticleActivity.class);
                TextView postId=(TextView)v.findViewById(R.id.page_id_stored);
                in.putExtra("post_id",postId.getText().toString());
               viewGroup.getContext().startActivity(in);

            }
        });
        return pvh;
    }

    @Override
    public void onBindViewHolder(PersonViewHolder personViewHolder, int i) {
        recyclerData data=list.get(i);
        personViewHolder.subjectCode.setText(data.getTitle());
        personViewHolder.authors.setText(data.getByLine());
        personViewHolder.date.setText(data.getDateLine());
        personViewHolder.postId.setText(data.getPostId());
        Glide.with(context).load(data.getImg_url())
                .thumbnail(0.25f)
                .apply(new RequestOptions().centerCrop())
                .into(personViewHolder.article_card_image);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }
}