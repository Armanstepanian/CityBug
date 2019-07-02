package org.citybugs.citybugs_new;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import org.citybugs.citybugs_new.R;

import java.sql.SQLOutput;
import java.util.ArrayList;

import static com.facebook.FacebookSdk.getApplicationContext;

public class Controller extends RecyclerView.Adapter<Controller.MyViewHolder> {

    Context context;
    ArrayList<Model> modelArrayList = new ArrayList<>();
    RequestManager glide ;
    public Controller(Context context, ArrayList<Model> model) {
        this.context = context;
        this.modelArrayList = model;
        glide = Glide.with(context);
    }
    @Override
    public MyViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_scrolling,parent,false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder( MyViewHolder holder, int position) {
        final Model model = modelArrayList.get(position);

        holder.name.setText(model.getName());
        holder.title.setText(model.getTitl());
        holder.context.setText(model.getContext());
     try {
         holder.Scroll_text_id.setText(model.getText_id());
     }catch (Exception ex ){
         System.out.println(ex);

     }
        glide.load(model.getPicture()).into(holder.Picture);
        glide.load(model.getProfilePick())
                .apply(new RequestOptions().override(100, 100)).into(holder.profilePick);



    }

    @Override
    public int getItemCount() {
        return modelArrayList.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name,title,context,Scroll_text_id;
        ImageButton like,share,comment;
        ImageView profilePick,Picture;

        public MyViewHolder( View itemView) {

            super(itemView);

            profilePick = (ImageView)itemView.findViewById(R.id.img);
            Picture = (ImageView)itemView.findViewById(R.id.profPic);

            name = (TextView)itemView.findViewById(R.id.name);
            Scroll_text_id = (TextView)itemView.findViewById(R.id.id_Scroll);
            title = (TextView)itemView.findViewById(R.id.title);
            context = (TextView)itemView.findViewById(R.id.Status);
            like = (ImageButton)itemView.findViewById(R.id.likeButton);
            share = (ImageButton)itemView.findViewById(R.id.shareButton);
            comment = (ImageButton)itemView.findViewById(R.id.commentButton);


            like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
            share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            comment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println(getApplicationContext());

                    System.out.println(Scroll_text_id.getText());

                }
            });
        }
    }
}
