package org.citybugs.citybugs_new;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

class Comments_Controller extends RecyclerView.Adapter<Comments_Controller.MyViewHolder> {


    Context context;
    ArrayList<Comments_Model> modelArrayList = new ArrayList<>();
    RequestManager glide ;

    public Comments_Controller(Context context, ArrayList<Comments_Model> modelArrayList) {
        this.context = context;
        this.modelArrayList = modelArrayList;
        glide = Glide.with(context);
    }

    @NonNull
    @Override
    public Comments_Controller.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_content,parent,false);
        Comments_Controller.MyViewHolder viewHolder = new Comments_Controller.MyViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder( Comments_Controller.MyViewHolder holder, int position) {
        final Comments_Model model = modelArrayList.get(position);

        holder.com_name.setText(model.getCom_Name());
        holder.com_title.setText(model.getDate());
        holder.com_context.setText(model.getComment());
try {
    glide.load(model.getImageUrl()).into(holder.com_image);
}catch (Exception ex){

}
glide.load(model.getUser_image())
                .apply(new RequestOptions().override(100, 100)).into(holder.com_userImg);


    }

    @Override
    public int getItemCount() {
        return modelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView com_name,com_title,com_context;
        ImageView com_image;
        ImageView com_userImg;


        public MyViewHolder(View itemView) {
            super(itemView);
            com_userImg = (ImageView)itemView.findViewById(R.id.com_user_img);
            com_image = (ImageView)itemView.findViewById(R.id.comm_img);
            com_name  = (TextView)itemView.findViewById(R.id.com_name);
            com_title  = (TextView)itemView.findViewById(R.id.com_title);
            com_context = (TextView)itemView.findViewById(R.id.com_context);
        }
    }
}
