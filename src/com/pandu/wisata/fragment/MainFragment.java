package com.pandu.wisata.fragment;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.pandu.wisata.R;
import com.pandu.wisata.adapter.RecyclerViewAdapter;
import com.pandu.wisata.adapter.RecyclerViewAdapter.ItemHolder;
import com.pandu.wisata.model.EventModel;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class MainFragment extends Fragment {

	private RecyclerView		myRecyclerView;
    private RecyclerViewAdapter	myRecyclerViewAdapter;

    @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
    }
    
	@Override
	public void onActivityCreated(Bundle savedState) {
	    super.onActivityCreated(savedState);
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.activity_main_fragment, container, false);

		// Create new RecyclerView
		myRecyclerView = (RecyclerView) rootView.findViewById(R.id.cardList);
		
		// Set RecyclerView size
		myRecyclerView.setHasFixedSize(true);

		// Set RecylerView Layout
		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
		linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
		myRecyclerView.setLayoutManager(linearLayoutManager);

		// Create adapter and handle on click
        myRecyclerViewAdapter = new RecyclerViewAdapter(getActivity());
        myRecyclerViewAdapter.setOnItemClickListener(new RecyclerItemClickListener());

        // Set RecyclerView adapter
        myRecyclerView.setAdapter(myRecyclerViewAdapter);

        // Dummy Data
        // Today date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        for (int counter=0; counter<5; counter++) {
        	sdf.setCalendar(cal);
        	String postDate = sdf.format(cal.getTime());

        	EventModel dataItem = new EventModel();
        	dataItem.setCommunity(1);
           	dataItem.setId("id"+(counter+1));
        	dataItem.setName("Event Title Number #" + (counter + 1));
        	dataItem.setEventDate("10 Juni 2016");
        	dataItem.setPostDate(CreateDate(postDate));
        	dataItem.setParticipant(5);
        	dataItem.setViewCount(8);
        	dataItem.setCommentCount(7);
    		//if (by!=null)
    		//	event.setAuthor(by);
    		//event.setPicture("http://3.bp.blogspot.com/-1VG4qXIpaEk/VHiChNIoP5I/AAAAAAAABEQ/ucJz9j4r9-w/s1600/pemandangan1.png");
        	myRecyclerViewAdapter.addEvent(0, dataItem);

        	cal.add(Calendar.DATE, -1);
        }

		return rootView;
	}

	// Convert String date to Date
	private Date CreateDate(String someDate) {
		//SimpleDateFormat simpledateformat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
		//SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
		ParsePosition pos = new ParsePosition(0);
		Date newDate = simpledateformat.parse(someDate, pos);
		return newDate;
	}

	private class RecyclerItemClickListener implements RecyclerViewAdapter.OnItemClickListener {

		@Override
		public void onItemClick(ItemHolder item, int position) {
			// TODO Auto-generated method stub
	        Toast.makeText(getActivity(),
	                position + " : " + item.getTitle(),
	                Toast.LENGTH_SHORT).show();
		}
		
	}
	

}
