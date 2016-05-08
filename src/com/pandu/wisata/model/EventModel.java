package com.pandu.wisata.model;

import java.util.Date;

import android.os.Parcel;
import android.os.Parcelable;

public class EventModel implements Parcelable {

	private String	mId;				// Event Id
	private String	mName;				// Event Name or Title
	private String	mEventDate;			// Event Date in String
	private Date	mStartDate;			// Event Start
	private Date	mEndDate;			// Event End
	private String	mPicture;			// Event Picture
    private Date	mPostDate;			// Event Posting Date
    private String	mHost;				// Event creator
    private int		mView;				// View Count
    private int		mReply;				// Reply Count
	private int		mParticipantCount;
	private int		mCommunity;			// Forum Commity

    // Constructor
    public EventModel() {
    	this.mId				= "";
    	this.mName				= "";
    	this.mEventDate			= "";
        this.mStartDate			= new Date();
        this.mEndDate			= new Date();
        this.mParticipantCount	= 0;
        this.mPicture			= "";
        this.mCommunity			= 0;
        this.mView				= 0;
        this.mReply				= 0;
        this.mPostDate			= new Date();
        this.mHost				= "Anonymous";
    }

    public String getId() {
    	return mId;
    }
    
    public void setId(String id) {
    	mId = id;
    }
    
    public String getName() {
    	return mName;
    }
    
    public void setName(String name) {
    	mName = name;
    }
    
    public void setEventDate(String date) {
    	mEventDate = date;
    }

    public String getEventDate() {
    	return mEventDate;
    }

    public Date getStartDate() {
    	return mStartDate;
    }
    
    public void setStartDate(Date start) {
    	mStartDate = start;
    }

    public Date getEndDate() {
    	return mEndDate;
    }
    
    public void setEndDate(Date end) {
    	mEndDate = end;
    }

    public int getParticipant() {
    	return mParticipantCount;
    }
    
    public void setParticipant(int participant) {
    	mParticipantCount = participant;
    }

    public String getPicture() {
    	return mPicture;
    }

    public void setPicture(String picture) {
    	mPicture = picture;
    }

    public void setCommunity(int community) {
    	mCommunity = community;
    }

    public int getCommunity() {
    	return mCommunity;
    }

    public void setViewCount(int view) {
    	mView = view;
    }
    
    public int getViewCount() {
    	return mView;
    }
    
    public void setCommentCount(int comment) {
    	mReply = comment;
    }

    public int getCommentCount() {
    	return mReply;
    }
    
    public void setPostDate(Date postDate) {
    	mPostDate = postDate;
    }

    public Date getPostDate() {
    	return mPostDate;
    }

    public void setAuthor(String author) {
    	mHost = author;
    }

    public String getAuthor() {
    	return mHost;
    }

    // Parcelling
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(mId);
		dest.writeString(mName);
		dest.writeString(mEventDate);
		dest.writeLong(mStartDate.getTime());
		dest.writeLong(mEndDate.getTime());
		dest.writeInt(mParticipantCount);
		dest.writeString(mPicture);
		dest.writeInt(mCommunity);
	    dest.writeInt(mView);
	    dest.writeInt(mReply);
	    dest.writeLong(mPostDate.getTime());
	    dest.writeString(mHost);
	}

    public EventModel(final Parcel in) {
    	mId = in.readString();
    	mName = in.readString();
    	mEventDate = in.readString();
    	mStartDate = new Date(in.readLong());
    	mEndDate = new Date(in.readLong());
    	mParticipantCount = in.readInt();
    	mPicture = in.readString();
    	mCommunity = in.readInt();
        mView = in.readInt();
        mReply = in.readInt();
        mPostDate = new Date(in.readLong());
        mHost = in.readString();
	}

	public static final Parcelable.Creator<EventModel> CREATOR = new Parcelable.Creator<EventModel>() {

		@Override
		public EventModel createFromParcel(Parcel source) {
			return new EventModel(source);
		}

		@Override
		public EventModel[] newArray(int size) {
			return new EventModel[size];
		}
	};
}
