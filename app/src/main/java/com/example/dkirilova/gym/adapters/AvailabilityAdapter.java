package com.example.dkirilova.gym.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dkirilova.gym.R;

import java.util.List;

import model.gyms.Availability;

public class AvailabilityAdapter extends RecyclerView.Adapter<AvailabilityAdapter.ViewHolder>{

    private List<Availability> availabilities;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View row = layoutInflater.inflate(R.layout.row_availability, parent, false);
        return new ViewHolder(row);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Availability availability = availabilities.get(position);

        holder.tvDayName.setText(availability.getDayOfWeek());
        holder.tvDuration.setText(String.valueOf(availability.getDuration()));
        holder.tvStartTime.setText(String.valueOf(availability.getStartTime()));
    }

    @Override
    public int getItemCount() {
        return (availabilities != null? availabilities.size(): 0);
    }

    public void setAvailabilities(List<Availability> availabilities) {
        this.availabilities = availabilities;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvDayName;
        TextView tvStartTime;
        TextView tvDuration;

        public ViewHolder(View itemView) {
            super(itemView);

            tvDayName = (TextView) itemView.findViewById(R.id.tvARDayName);
            tvStartTime= (TextView) itemView.findViewById(R.id.tvARStartTime);
            tvDuration = (TextView) itemView.findViewById(R.id.tvARDuartion);
        }
    }
}
