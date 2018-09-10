package com.pisb.credenz18;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


class RegAdapter extends RecyclerView.Adapter<RegAdapter.ViewHolder> {

    Context context;

    RecepitData recepitData;
    NetWork netWork;


    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public TextView date;
        public TextView id;
        public TextView total;
        public TextView no;

        public ViewHolder(final View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.part_name);
            date=itemView.findViewById(R.id.date);
            id=itemView.findViewById(R.id.uniId);
            total=itemView.findViewById(R.id.totPrice);
            no=itemView.findViewById(R.id.totEvents);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i("click", "onClick: ");
                    Intent i = new Intent(context, Details.class);
                    i.putExtra("id",id.getText().toString());
                    context.startActivity(i);
                }
            });
        }
    }


    @Override
    public RegAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.receipt_rv, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    private ArrayList<PrevData> data;

    private List<RecepitData> recepitDataList;


    public RegAdapter(List<RecepitData> recepitData, Context context)
    {
        recepitDataList = recepitData;
        this.context =context;
    }


    @Override
    public void onBindViewHolder(final RegAdapter.ViewHolder viewHolder, final int position) {
        //final PrevData prevData=data.get(position);
        RecepitData recepitData = recepitDataList.get(position);
        viewHolder.name.setText(recepitData.getName1());
        viewHolder.date.setText(recepitData.getDate());
        viewHolder.id.setText(recepitData.getUniKey());

        netWork = new NetWork(context);
        recepitData = netWork.getDataKey(recepitData.getUniKey());

        viewHolder.total.setText("Total Price: " + Integer.toString(recepitData.getTotal()));
        viewHolder.no.setText("Total Events: "+ Integer.toString(getCount(recepitData)));

    }

    @Override
    public int getItemCount() {
        return recepitDataList.size();
    }


    public int getCount(RecepitData recepitData){
        return recepitData.getBPlan() + recepitData.getClash() + recepitData.getContraption() +
                recepitData.getCretronix() + recepitData.getDatawiz() + recepitData.getEnigma() +
                recepitData.getNTH() + recepitData.getPaperPresentation() + recepitData.getPixelate() +
                recepitData.getQuizB() + recepitData.getQuizG() + recepitData.getQuizM() + recepitData.getReverse_Coding() +
                recepitData.getRoboliga() + recepitData.getSoftware_Development() + recepitData.getWallStreet() +
                recepitData.getWebWeaver() + recepitData.getXodia();
    }

}



