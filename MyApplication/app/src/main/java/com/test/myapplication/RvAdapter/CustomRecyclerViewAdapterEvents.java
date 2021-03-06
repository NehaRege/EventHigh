package com.test.myapplication.RvAdapter;

import android.content.Context;
import android.location.Address;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.squareup.picasso.Picasso;
import com.test.myapplication.Models.FreeEventsModel.Event;
import com.test.myapplication.R;

import java.util.ArrayList;

/**
 * Created by NehaRege on 8/16/16.
 */
public class CustomRecyclerViewAdapterEvents extends RecyclerView.Adapter<CustomRecyclerViewAdapterEvents.ViewHolder> {


    private static final String TAG= "CustomRvAdapter";
    //TODO: define arraylist for the data
    private ArrayList<Event> allEventsdataList;

    private Context context;


    private OnRecyclerViewItemClickListener onItemClickListener;

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(int position);
    }




    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textViewTitle;
        TextView textViewDate;
        TextView textViewVenue;

//        Button button;

        public ViewHolder(View itemView) {
            super(itemView);



            imageView = (ImageView) itemView.findViewById(R.id.card_layout_event_image);
            textViewTitle = (TextView) itemView.findViewById(R.id.card_layout_event_title);
//            textViewDate = (TextView) itemView.findViewById(R.id.card_layout_event_date);
            textViewVenue = (TextView) itemView.findViewById(R.id.card_layout_event_venue);
//            button = (Button) itemView.findViewById(R.id.card_layout_event_button_share);

//            button.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    //TODO: handle the click here for share feature using share intents
//                }
//            });


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Log.i(TAG, "onClick:  item clicked at position "+getLayoutPosition());

                    onItemClickListener.onItemClick(getLayoutPosition());
                }
            });

        }
    }

    public CustomRecyclerViewAdapterEvents(Context context,
                                           ArrayList<Event> allEventsData,
                                           OnRecyclerViewItemClickListener listener) {
        this.context = context;

        if(listener != null) {
            this.onItemClickListener = listener;

        }

        if(allEventsData != null){
            this.allEventsdataList = allEventsData;
        } else {
            this.allEventsdataList = new ArrayList<>();
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();

        View cardLayout = LayoutInflater.from(context).inflate(R.layout.rv_card_layout_events,parent,false);

        ViewHolder viewHolder = new ViewHolder(cardLayout);

        return viewHolder;
    }

    //// TODO: 8/17/16
//    String dataItem = data.get(position);
//
//    /**
//     * Pull out the inflated TextView/ImageView references out of our SampleViewHolder
//     * instance.
//     *
//     * Look at the constructor of SampleViewHolder() and note that variable fields
//     * 'imageView' and 'textView' are both public ( which is why we don't need a
//     * getter ).
//     */
//    TextView textView = holder.textView;
//    ImageView imageView = holder.imageView;
//
//    // put our dataItem string as text into the text view
//    textView.setText(dataItem);
//
//    // set the launcher icon as our image resource
//    imageView.setImageResource(R.mipmap.ic_launcher);

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        ImageView imageView = holder.imageView;
        TextView textViewTitle = holder.textViewTitle;
        TextView textViewDate = holder.textViewDate;
        TextView textViewVenue = holder.textViewVenue;
//        Button button = holder.button;

//        imageView.setImageResource(R.drawable.dog);
//        textViewTitle.setText("EVENT TITLE");
//        textViewDate.setText("AUG 26, 2016");

        if(allEventsdataList.size()>0) {
            Event event = allEventsdataList.get(position);

            textViewTitle.setText(event.getName().getText());
            textViewVenue.setText(event.getVenue().getAddress().getAddress1());

            if (event.getLogo()!=null) {
                Picasso.with(context).load(event.getLogo().getUrl()).into(imageView);
            }

//            if(value.getImage()!=null){
//                Picasso.with(mContext).load(value.getImage().getUrl()).into(holder.rvImageView);
//            }

//            Picasso.with(mContext).load(value.getImage().getUrl()).into(holder.rvImageView);

        }


    }

    @Override
    public int getItemCount() {
        return allEventsdataList.size();
    }
}
