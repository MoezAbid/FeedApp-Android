package feedup.housetargaryen.com.feedup;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import feedup.housetargaryen.com.feedup.Connection.Model.Application;

/**
 * Created by ASUS on 31/10/2017.
 */

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.Holder> implements Filterable{

    private ArrayList<Application> items;
    private ArrayList<Application> mFilteredList;
    public Application object;


    public MyRecyclerAdapter(ArrayList<Application> items) {
        this.items = items;
        this.mFilteredList = items;
    }


    public static class Holder extends RecyclerView.ViewHolder {
        public ImageView imageViewAPP;
        public TextView NomAPP, DateAPP;
        public RatingBar ratingbar;
        private TextView feedBackPoints;


        public Holder(final View itemView) {
            super(itemView);
            imageViewAPP = (ImageView) itemView.findViewById(R.id.Logo_app);
            NomAPP = (TextView) itemView.findViewById(R.id.Name_app);
            //DateAPP = (TextView) itemView.findViewById(R.id.Date);
            ratingbar = (RatingBar) itemView.findViewById(R.id.ratingBar);
            feedBackPoints = (TextView) itemView.findViewById(R.id.NbrPoints);
        }
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleview_item, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

        object = items.get(position);

        final Application application = items.get(holder.getAdapterPosition());

        String Name_APP = object.getNomAPP();
        Picasso.with(holder.itemView.getContext()).load(object.getLogoUri()).into(holder.imageViewAPP);
        float rate = object.getRateAPP();
        holder.NomAPP.setText(Name_APP);
        holder.ratingbar.setRating(rate);
        holder.feedBackPoints.setText(Integer.toString(object.getNbrPoints()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Description.class);
                intent.putExtra(Application.TAG, application);
                intent.putExtra("cf", "liste");
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    mFilteredList = items;
                } else {

                    ArrayList<Application> filteredList = new ArrayList<>();

                    for (Application androidApp : items) {

                        if (androidApp.getNomAPP().toLowerCase().contains(charString) || androidApp.getNomAPP().toLowerCase().contains(charString) ) {

                            filteredList.add(androidApp);
                        }
                    }

                    mFilteredList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilteredList = (ArrayList<Application>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public void setFilter(ArrayList<Application> newList){
        items = new ArrayList<>();
        items.addAll(newList);
        notifyDataSetChanged();
    }
}
