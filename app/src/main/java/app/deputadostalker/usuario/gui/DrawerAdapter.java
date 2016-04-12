package app.deputadostalker.usuario.gui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import app.deputadostalker.R;
import app.deputadostalker.usuario.service.CircularNetworkImageView;
import app.deputadostalker.usuario.service.CustomVolleyRequest;
import app.deputadostalker.usuario.service.CircularNetworkImageView;
import app.deputadostalker.usuario.service.CustomVolleyRequest;

/**
 * Created by Matheus Uehara on 20/03/2016.
 */
public class DrawerAdapter extends RecyclerView.Adapter<DrawerAdapter.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private String mNavTitles[];
    private int mIcons[];
    private String name;
    private String profile;
    private String email;
    Context context;

    private ImageLoader imageLoader;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        int Holderid;

        TextView textView;
        ImageView imageView;
        CircularNetworkImageView profile;
        TextView Name;
        TextView email;
        Context contxt;


        public ViewHolder(View itemView,int ViewType,Context c) {
            super(itemView);
            contxt = c;
            itemView.setClickable(true);
            itemView.setOnClickListener(this);

            if(ViewType == TYPE_ITEM) {
                textView = (TextView) itemView.findViewById(R.id.rowText);
                imageView = (ImageView) itemView.findViewById(R.id.rowIcon);
                Holderid = 1;
            }
            else{
                Name = (TextView) itemView.findViewById(R.id.name);
                email = (TextView) itemView.findViewById(R.id.email);
                profile = ((CircularNetworkImageView) itemView.findViewById(R.id.circleView));
                Holderid = 0;
            }
        }
        @Override
        public void onClick(View v) {
            Toast.makeText(contxt,"The Item Clicked is: "+getAdapterPosition(),Toast.LENGTH_SHORT).show();

        }

    }

    DrawerAdapter(String Titles[],int Icons[],String Name,String Email, String Profile,Context passedContext){
        mNavTitles = Titles;
        mIcons = Icons;
        name = Name;
        email = Email;
        profile = Profile;
        this.context = passedContext;

    }

    @Override
    public DrawerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.nav_drawer_item_row,parent,false);
            ViewHolder vhItem = new ViewHolder(v,viewType,context);
            return vhItem;

        } else if (viewType == TYPE_HEADER) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.nav_drawer_header,parent,false);
            ViewHolder vhHeader = new ViewHolder(v,viewType,context);
            return vhHeader;
        }
        return null;

    }

    @Override
    public void onBindViewHolder(DrawerAdapter.ViewHolder holder, int position) {
        if(holder.Holderid ==1) {
            holder.textView.setText(mNavTitles[position - 1]);
            holder.imageView.setImageResource(mIcons[position -1]);
        }
        else{
            imageLoader = CustomVolleyRequest.getInstance(context.getApplicationContext()).getImageLoader();
            imageLoader.get(profile,
                    ImageLoader.getImageListener(holder.profile,
                            R.mipmap.ic_launcher,
                            R.mipmap.ic_launcher));
            holder.profile.setImageUrl(profile, imageLoader);
            holder.Name.setText(name);
            holder.email.setText(email);
        }
    }

    @Override
    public int getItemCount() {
        return mNavTitles.length+1;
    }


    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position))
            return TYPE_HEADER;

        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

}