package com.example.dkirilova.gym.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dkirilova.gym.R;

import java.io.File;
import java.util.List;

import model.gyms.Exercise;

/**
 * Created by dkirilova on 7/6/2017.
 *
 *
 */

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ViewHolder> {

    private List<Exercise> exercises;
    private IExerciseAdapterController adapterController;

    public ExerciseAdapter(IExerciseAdapterController adapterController){
        this.adapterController = adapterController;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View row = layoutInflater.inflate(R.layout.row_exercise, parent, false);
        return new ViewHolder(row);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final Exercise exercise = exercises.get(position);
        int level = exercise.getLevel();
        holder.tvName.setText(exercise.getName());
        holder.tvExperienceLevel.setText(String.valueOf(level));
        setImage(holder.ivImage, exercise.getImage());

        if(level >= 1 && level <= 3){
            holder.layout.setBackgroundResource(R.color.green);
        } else if (level >= 4 && level <= 7){
            holder.layout.setBackgroundResource(R.color.yellow);
        }else if(level >= 8 && level <= 10){
            holder.layout.setBackgroundResource(R.color.red);
        }

        holder.layout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                adapterController.editOrDelete(exercise);
                return true;
            }
        });

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterController.openDetails(exercise);
            }
        });
    }

    @Override
    public int getItemCount() {

        return (exercises != null? exercises.size(): 0);
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView ivImage;
        private TextView tvName;
        private TextView tvExperienceLevel;
        private LinearLayout layout;

        ViewHolder(View itemView) {
            super(itemView);

            ivImage = (ImageView) itemView.findViewById(R.id.ivExerciseImage);
            tvName = (TextView) itemView.findViewById(R.id.tvExerciseName);
            tvExperienceLevel = (TextView) itemView.findViewById(R.id.tvExerciseLevel);
            layout = (LinearLayout) itemView.findViewById(R.id.llExerciseRow);
        }
    }

    private void setImage(ImageView image, String uriStr){
        if(image == null || uriStr == null){
            return;
        }

        Uri uri = Uri.parse(uriStr);
        File imgFile = new  File(uri.getPath());

        if(imgFile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            image.setImageBitmap(myBitmap);
        }
    }

    public interface IExerciseAdapterController{

        void editOrDelete(Exercise exercise);
        void openDetails(Exercise exercise);
    }
}
