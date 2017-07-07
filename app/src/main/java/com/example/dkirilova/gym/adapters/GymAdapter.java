package com.example.dkirilova.gym.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.dkirilova.gym.R;
import com.example.dkirilova.gym.dialog_fragments.EditOrDeleteGymFragment;

import java.io.File;
import java.util.List;

import model.gyms.Gym;

/**
 * Created by dkirilova on 7/5/2017.
 */

public class GymAdapter extends RecyclerView.Adapter<GymAdapter.ViewHolder> {

    //todo get activity from parent
    private AppCompatActivity activity;
    private List<Gym> gyms;

    public GymAdapter(AppCompatActivity activity, List<Gym> gyms) {
        this.activity = activity;
        this.gyms = gyms;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View row = layoutInflater.inflate(R.layout.row_gym, parent, false);
        return new ViewHolder(row);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final Gym gym = gyms.get(position);

        holder.tvGymName.setText(gym.getName());
        holder.tvGymAddress.setText(gym.getAddress());
        setImage(holder.ivImage, gym.getImage());


        holder.layout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                // todo put the selected gym in bundle

                Bundle bundle = new Bundle();
                bundle.putSerializable("gym", gym);
                EditOrDeleteGymFragment editOrDeleteGymFragment = new EditOrDeleteGymFragment();
                editOrDeleteGymFragment.setArguments(bundle);
                editOrDeleteGymFragment.show(activity.getSupportFragmentManager(), "editOrDeleteGymFragment");

                return true;
            }
        });

        holder.chbFavouriteGym.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                gym.setIsFavourite(isChecked);
                if (isChecked) {
                    // todo change the icon
                } else {
                    // todo change the icon
                }
            }
        });

        if (gym.isFavourite()) {
            // todo change the icon
        } else {
            // todo change the icon
        }
    }

    @Override
    public int getItemCount() {
        return gyms.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvGymName;
        private TextView tvGymAddress;
        private ImageView ivImage;
        private LinearLayout layout;
        private CheckBox chbFavouriteGym;

        public ViewHolder(View itemView) {
            super(itemView);

            tvGymName = (TextView) itemView.findViewById(R.id.tvGymName);
            tvGymAddress = (TextView) itemView.findViewById(R.id.tvGymAddress);
            ivImage = (ImageView) itemView.findViewById(R.id.ivGymImage);
            layout = (LinearLayout) itemView.findViewById(R.id.llRowGym);
            chbFavouriteGym = (CheckBox) itemView.findViewById(R.id.chbFavouriteGym);
        }
    }

    private void setImage(ImageView image, String uriStr) {
        if (image == null) {
            return;
        }
        if (uriStr == null) {
            image.setBackgroundResource(R.drawable.gym);
            return;
        }

        Uri uri = Uri.parse(uriStr);
        File imgFile = new File(uri.getPath());

        if (imgFile.exists()) {
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            image.setImageBitmap(myBitmap);
        }
    }
}