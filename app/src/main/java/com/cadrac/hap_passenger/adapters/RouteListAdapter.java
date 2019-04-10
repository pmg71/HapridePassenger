package com.cadrac.hap_passenger.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Filter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cadrac.hap_passenger.MainActivity;
import com.cadrac.hap_passenger.R;
import com.cadrac.hap_passenger.activity.PassengerRequest;
import com.cadrac.hap_passenger.responses.RouteListResponse;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import static android.support.constraint.Constraints.TAG;

public class RouteListAdapter extends RecyclerView.Adapter<RouteListAdapter.EMSHolder>{

        int FADE_DURATION = 100;
        ArrayList<RouteListResponse.data> data;
        ArrayList<RouteListResponse.data> search;
        Context context;
        MainActivity Settlement;
        String charString;
        com.cadrac.hap_passenger.responses.RouteListResponse RouteListResponse;


public RouteListAdapter(ArrayList<RouteListResponse.data> data, MainActivity Settlement, Context context) {
        this.context=context;
        this.data = data;
        this.Settlement= Settlement;

        this.search= new ArrayList<RouteListResponse.data>();
        this.search.addAll(data);
        this.notifyDataSetChanged();
        }



@Override
public void onBindViewHolder(@NonNull final RouteListAdapter.EMSHolder holder, final int position) {
        setScaleAnimation(holder.itemView);


        Log.d("TAG", "onBindViewHolder: "+data.get(position).getSource());
        holder.source.setText(data.get(position).getSource());

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              String source=  data.get(position).getSource();
                Log.d(TAG, "onClick: "+ data.get(position).getLattitude());

                LatLng latLng = new LatLng(Double.parseDouble(data.get(position).getLattitude()),Double.parseDouble(data.get(position).getLongitude()));
                Settlement.AgentListDialog(source,latLng);
            }
        });


        }

private void setScaleAnimation(View view)
        {
        ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f,
        Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setDuration(FADE_DURATION);
        view.startAnimation(anim);
        }

public static class EMSHolder extends RecyclerView.ViewHolder
{

    TextView source;
    LinearLayout linearLayout;


    public EMSHolder(View v)
    {
        super(v);

        source=(TextView)v.findViewById(R.id.source);

        linearLayout=(LinearLayout)v.findViewById(R.id.ll);

    }
}

    public int getItemCount() {
        return  data.size();
    }

    public Object getItem(int i) {
        return i;
    }

    public Filter getFilter()
    {
        return new Filter()
        {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence)
            {

                data.clear();

                charString = charSequence.toString();

                if (charString.isEmpty())
                { Log.d("TAG", "onTextChanged: "+charString);
                    data.addAll(search);
                }
                else
                {
                    Log.d("TAG", "onTextChanged: "+charString);
                    //dataFilter.clear();
                    ArrayList<RouteListResponse.data> filteredList = new ArrayList<>();
                    for (RouteListResponse.data row : search)
                    {
                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match

                        if (row.getSource().toLowerCase().contains(charString.toLowerCase()))/*.contains(charSequence)*/
                        {
                            filteredList.add(row);
                        }
                    }

                    data = filteredList;
                }

                //System.out.println("Data Filter Size--"+data.size());
                FilterResults filterResults = new FilterResults();
                filterResults.values = data;
                return filterResults;
            }

            @Override
            protected void  publishResults(CharSequence charSequence, FilterResults filterResults)
            {
                data = (ArrayList<RouteListResponse.data>) filterResults.values;
                notifyDataSetChanged();

            }


        };
    }

    @NonNull
    @Override
    public RouteListAdapter.EMSHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.routelistadapter,
                parent, false);

        return new RouteListAdapter.EMSHolder(itemView);
    }
    public long getItemId(int i) {
        return i;
    }

}
