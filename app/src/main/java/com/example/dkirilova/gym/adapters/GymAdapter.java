package com.example.dkirilova.gym.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dkirilova.gym.R;

import java.io.File;
import java.util.List;

import model.gyms.Gym;
import model.singleton.FitnessManager;

/**
 * Created by dkirilova on 7/5/2017.
 */

public class GymAdapter extends RecyclerView.Adapter<GymAdapter.ViewHolder> {

    private IGymAdapterController adapterController;
    private List<Gym> gyms;

    public GymAdapter(IGymAdapterController adapterController, List<Gym> gyms) {
        this.adapterController = adapterController;
        this.gyms = gyms;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View row = layoutInflater.inflate(R.layout.row_gym, parent, false);
        return new ViewHolder(row);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final Gym gym = gyms.get(position);

        holder.tvGymName.setText(gym.getName());
        holder.tvGymAddress.setText(gym.getAddress());
        setImage(holder.ivImage, gym.getImage());


        holder.layout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                adapterController.editOrDelete(gym);
                return true;
            }
        });

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterController.openDetails(gym);
            }
        });

        holder.chbFavouriteGym.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    holder.chbFavouriteGym.setButtonDrawable(R.drawable.ic_favorite_black_24dp);
                    FitnessManager.getInstance().delete(gym);
                    gym.setIsFavourite(true);
                    FitnessManager.getInstance().add(gym);
                } else {
                    holder.chbFavouriteGym.setButtonDrawable(R.drawable.ic_favorite_border_black_24dp);
                    FitnessManager.getInstance().delete(gym);
                    gym.setIsFavourite(false);
                    FitnessManager.getInstance().add(gym);
                }
            }
        });

        if (gym.isFavourite()) {
            holder.chbFavouriteGym.setButtonDrawable(R.drawable.ic_favorite_black_24dp);
        } else {
            holder.chbFavouriteGym.setButtonDrawable(R.drawable.ic_favorite_border_black_24dp);
        }
    }

    @Override
    public int getItemCount() {
        return (gyms != null ? gyms.size() : 0);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvGymName;
        private TextView tvGymAddress;
        private ImageView ivImage;
        private LinearLayout layout;
        private CheckBox chbFavouriteGym;

        ViewHolder(View itemView) {
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

    public interface IGymAdapterController {
        void editOrDelete(Gym gym);
        void openDetails(Gym gym);
    }
}