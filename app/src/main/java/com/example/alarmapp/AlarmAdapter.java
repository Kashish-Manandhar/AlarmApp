package com.example.alarmapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.AlarmViewHolder>
{
    Context context;
    List<AlarmDB> alarmDBList;
    public interface onClickListener{
        void setonCLick(int position,View view);
        void setChecked(int position,boolean isChecked);
    }

    onClickListener clickListener;
    public AlarmAdapter(Context context, List<AlarmDB> alarmDBList) {
        this.context = context;
        this.alarmDBList = alarmDBList;
        this.clickListener=(onClickListener) context;

    }

    @NonNull
    @Override
    public AlarmAdapter.AlarmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row,parent,false);
        return new AlarmViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final AlarmAdapter.AlarmViewHolder holder, int position) {
        AlarmDB alarmDB=alarmDBList.get(position);
        holder.name.setText(alarmDB.getName());
        int hur=alarmDB.getHour();
        String tod;
        if(hur<12)
        {

            tod="AM";
        }
        else
        {
            hur=hur-12;
            tod="PM";
        }

        if(alarmDB.getMinute()<10) {
            holder.time.setText(hur + ":" +"0"+ alarmDB.getMinute()+" "+tod);
        }
        else
        {
            holder.time.setText(hur+ ":" + alarmDB.getMinute()+" "+tod);
        }
        if(alarmDB.isStatus())
        {
            holder.alarm_switch.setChecked(true);

        }
        else
        {
            holder.alarm_switch.setChecked(false);
//            holder.cardView.setCardBackgroundColor(R.color.disabled);

        }

        if(!alarmDB.isRepeat())
        {
            holder.layout.setVisibility(View.GONE);
        }

        if(!alarmDB.isSun())
        {
            holder.s.setVisibility(View.GONE);
        }
        if(!alarmDB.isMon())
        {
            holder.m.setVisibility(View.GONE);
        }
        if(!alarmDB.isTues())
        {
            holder.t.setVisibility(View.GONE);
        }
        if(!alarmDB.isWed())
        {
            holder.w.setVisibility(View.GONE);
        }
        if(!alarmDB.isThurs())
        {
            holder.th.setVisibility(View.GONE);
        }
        if(!alarmDB.isFri())
        {
            holder.fr.setVisibility(View.GONE);
        }
        if(!alarmDB.isSat())
        {
            holder.sat.setVisibility(View.GONE);
        }

        if(alarmDB.isStatus())
        {
            holder.cardView.setCardBackgroundColor(Color.parseColor("#1a2228"));
        }
        else
        {
            holder.cardView.setCardBackgroundColor(Color.parseColor("#414654"));

        }


    }

    @Override
    public int getItemCount() {
        return alarmDBList.size();
    }

    public class AlarmViewHolder extends RecyclerView.ViewHolder{
        TextView name,time,s,m,t,w,th,fr,sat;
        SwitchCompat alarm_switch;
        CardView cardView;
        LinearLayout layout;
        public AlarmViewHolder(@NonNull final View itemView) {
            super(itemView);
            cardView=itemView.findViewById(R.id.card);
            name=itemView.findViewById(R.id.list_name);
            time=itemView.findViewById(R.id.list_time);
            alarm_switch=itemView.findViewById(R.id.status);
            layout=itemView.findViewById(R.id.day_v);
            s=itemView.findViewById(R.id.s);
            m=itemView.findViewById(R.id.m);
            t=itemView.findViewById(R.id.tu);
            w=itemView.findViewById(R.id.we);
            th=itemView.findViewById(R.id.th);
            fr=itemView.findViewById(R.id.fr);
            sat=itemView.findViewById(R.id.sa);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.setonCLick(getAdapterPosition(),v);
                }
            });
            alarm_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    clickListener.setChecked(getAdapterPosition(),isChecked);
                    if(isChecked)
                    {
                        cardView.setCardBackgroundColor(Color.parseColor("#1a2228"));
                    }
                    else
                    {
                        cardView.setCardBackgroundColor(Color.parseColor("#414654"));
                    }
                }
            });
        }
    }
}
