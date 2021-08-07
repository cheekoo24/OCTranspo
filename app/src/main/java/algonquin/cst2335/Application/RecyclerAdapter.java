package algonquin.cst2335.Application;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {
    private ArrayList<OCBusData> busDataList;
    private RecyclerViewClickListener listener;

    public RecyclerAdapter(ArrayList<OCBusData> busDataList, RecyclerViewClickListener listener) {
        this.busDataList = busDataList;
        this.listener = listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView numRoute;
        private TextView stringRoute;

        public MyViewHolder(final View view) {
            super(view);
            numRoute = view.findViewById(R.id.numRoute);
            stringRoute = view.findViewById(R.id.stringRoute);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onClick(view, getAdapterPosition());
        }
    }

    @Override
    public RecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.ocitemlist_layout, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.MyViewHolder holder, int position) {
        String routeNo = busDataList.get(position).getRouteNum();
        String routeName = busDataList.get(position).getRouteName();
        holder.numRoute.setText(routeNo);
        holder.stringRoute.setText(routeName);
    }

    @Override
    public int getItemCount() {
        return busDataList.size();
    }

    public interface RecyclerViewClickListener {
        void onClick(View v, int position);
    }
}
