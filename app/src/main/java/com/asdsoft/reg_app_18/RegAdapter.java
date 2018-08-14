package com.asdsoft.reg_app_18;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


class RegAdapter extends
        RecyclerView.Adapter<RegAdapter.ViewHolder> {


    Context c;


    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public TextView date;
        public TextView id;
        public TextView total;
        public TextView no;
        Prev prev;

        public ViewHolder(final View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.part_name);
            date=itemView.findViewById(R.id.date);
            id=itemView.findViewById(R.id.uniId);
            total=itemView.findViewById(R.id.totPrice);
            no=itemView.findViewById(R.id.totEvents);
            prev=new Prev();

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    int itemPosition = itemView.getChildLayoutPosition(view);
//                    prev.goToNext(id.getText().toString());
                    Log.i("click", "onClick: ");
                    Intent i = new Intent(c, Details.class);
                    i.putExtra("id",id.getText().toString());

                    c.startActivity(i);
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


    public RegAdapter(ArrayList<PrevData> registration, Context context)
    {
        data = registration;
        c=context;
    }


    @Override
    public void onBindViewHolder(final RegAdapter.ViewHolder viewHolder, final int position) {
        final PrevData prevData=data.get(position);
        viewHolder.name.setText(prevData.getRegName());
        viewHolder.date.setText(prevData.getRegDate());
        viewHolder.id.setText(prevData.getUniId());
        viewHolder.total.setText("Total: "+Integer.toString(prevData.gettotal()));
        viewHolder.no.setText("No. of Events: "+Integer.toString(prevData.getNoOfEvents()));

    }


    @Override
    public int getItemCount() {
        return data.size();
    }
}
