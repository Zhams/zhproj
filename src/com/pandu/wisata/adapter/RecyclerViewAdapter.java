package com.pandu.wisata.adapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.pandu.wisata.R;
import com.pandu.wisata.model.EventModel;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ItemHolder> {

	public interface OnItemClickListener{
        public void onItemClick(ItemHolder item, int position);
    }

	private final ArrayList<EventModel> mArrayItemList;
    private OnItemClickListener			onItemClickListener;
    private LayoutInflater				layoutInflater;

    // Default Constructor
    public RecyclerViewAdapter(Context context){
        layoutInflater = LayoutInflater.from(context);
        mArrayItemList = new ArrayList<EventModel>();
    }

    @Override
    public RecyclerViewAdapter.ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.layout_item, parent, false);
        return new ItemHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.ItemHolder holder, int position) {
		EventModel dataModel = mArrayItemList.get(position);

		holder.setTitle(dataModel.getName());
        holder.setDateStart(dataModel.getEventDate());

        if (dataModel.getCommunity()==1) {
        	if ((position % 2) == 0)
        		holder.setImage(R.drawable.ethnic);
        	else
        		holder.setImage(R.drawable.place);
        } else if (dataModel.getCommunity()==2) {
        	holder.setImage(R.drawable.ethnic);
        } else if (dataModel.getCommunity()==3) {
        	holder.setImage(R.drawable.place);
        } else if (dataModel.getCommunity()==4) {
        	holder.setListIcon(R.drawable.ic_place_list);
        } else if (dataModel.getCommunity()==5) {
        	holder.setListIcon(R.drawable.ic_hotel_list);
        } else if (dataModel.getCommunity()==6) {
        	holder.setListIcon(R.drawable.ic_food_list);
        } else if (dataModel.getCommunity()==7) {
        	holder.setListIcon(R.drawable.ic_guide_list);
        } else if (dataModel.getCommunity()==8) {
        	holder.setListIcon(R.drawable.ic_car_list);
        }

		Date today = new Date();
		Date postDate = dataModel.getPostDate();
		long dateDiff = daysBetween(postDate, today);
		String myFormat = "dd MMM yyyy";
		SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
		String strPostDate = sdf.format(postDate);
		if (dateDiff==0)
			strPostDate = "Posted today (" + strPostDate + ")";
		else if (dateDiff==1)
			strPostDate = "Posted yesterday (" + strPostDate + ")";
		else
			strPostDate = "Posted " + strPostDate;
        holder.setCreateDate(strPostDate);

        holder.setParticipant(dataModel.getParticipant() + " participant");
        holder.setView(dataModel.getViewCount() + " view");
        holder.setComment(dataModel.getCommentCount() + " comment");
    }

    @Override
    public int getItemCount() {
        return mArrayItemList.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        onItemClickListener = listener;
    }

    public OnItemClickListener getOnItemClickListener(){
        return onItemClickListener;
    }

    public void add(int location, String iName){
        //itemsName.add(location, iName);
        notifyItemInserted(location);
    }

    public void addEvent(int position, EventModel event) {
    	mArrayItemList.add(event);
    	notifyItemInserted(position);
    }

    public void remove(int location){
        //if(location >= itemsName.size())
        //    return;

        //itemsName.remove(location);
        notifyItemRemoved(location);
    }

	private static Calendar getDatePart(Date date) {
	    Calendar cal = Calendar.getInstance();       // get calendar instance
	    cal.setTime(date);      
	    cal.set(Calendar.HOUR_OF_DAY, 0);            // set hour to midnight
	    cal.set(Calendar.MINUTE, 0);                 // set minute in hour
	    cal.set(Calendar.SECOND, 0);                 // set second in minute
	    cal.set(Calendar.MILLISECOND, 0);            // set millisecond in second

	    return cal;                                  // return the date part
	}

	private static long daysBetween(Date startDate, Date endDate) {
		Calendar sDate = getDatePart(startDate);
		Calendar eDate = getDatePart(endDate);

		long daysBetween = 0;
		while (sDate.before(eDate)) {
			sDate.add(Calendar.DAY_OF_MONTH, 1);
			daysBetween++;
			}

		return daysBetween;
	}
    
    // Class
    public static class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private RecyclerViewAdapter	parent;

        private ImageView			mListIcon;
        private	TextView			mTitle;
        private TextView			mStart;
        private ImageView			mImage;
        private TextView			mCreated;
        private TextView			mParticipant;
        private TextView			mView;
        private TextView			mComment;

        public ItemHolder(View itemView, RecyclerViewAdapter parent) {
            super(itemView);
            itemView.setOnClickListener(this);
            this.parent = parent;

            mListIcon		= (ImageView) itemView.findViewById(R.id.list_image);
            mTitle			= (TextView) itemView.findViewById(R.id.title);
    		mStart			= (TextView) itemView.findViewById(R.id.start_date);
            mImage			= (ImageView) itemView.findViewById(R.id.event_image);
            mCreated		= (TextView) itemView.findViewById(R.id.created_date);
            mParticipant	= (TextView) itemView.findViewById(R.id.participant);
            mView			= (TextView) itemView.findViewById(R.id.view);
            mComment		= (TextView) itemView.findViewById(R.id.comment);
        }

        public void setListIcon(int resId) {
        	mListIcon.setImageResource(resId);
        }
        
        public void setTitle(CharSequence title) {
            mTitle.setText(title);
        }

        public CharSequence getTitle() {
            return mTitle.getText();
        }

        public void setDateStart(CharSequence date) {
        	mStart.setText(date);
        }
        
        public CharSequence getDateStart() {
        	return mStart.getText();
        }

        public void setImage(int resourceId) {
        	mImage.setImageResource(resourceId);
        }
        
        public void setCreateDate(CharSequence date)  {
        	mCreated.setText(date);
        }
        
        public CharSequence getCreateDate() {
        	return mCreated.getText();
        }

        public void setParticipant(CharSequence participant) {
        	mParticipant.setText(participant);
        }
        
        public CharSequence getParticipant() {
        	return mParticipant.getText();
        }
        
        public void setView(CharSequence view) {
        	mView.setText(view);
        }
        
        public CharSequence getView() {
        	return mView.getText();
        }
        
        public void setComment(CharSequence comment) {
        	mComment.setText(comment);
        }
        
        public CharSequence getComment() {
        	return mComment.getText();
        }

        @Override
        public void onClick(View v) {
            final OnItemClickListener listener = parent.getOnItemClickListener();
            if (listener != null) {
                listener.onItemClick(this, getPosition());
            }
        }
    }
    
}
