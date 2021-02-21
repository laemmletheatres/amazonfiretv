package com.amazon.android.tv.tenfoot.ui.epg;

import android.text.TextUtils;

import java.io.Serializable;

public class EPGEvent implements Serializable {

    private final long start;
    private final long end;
    private final String title;
    private final EPGChannel channel;
    private final String programUrl;
    //is this the current selected event?
    public boolean selected;
    private String desc;
    private EPGEvent previousEvent;
    private EPGEvent nextEvent;
    private String startDateTime;
    private String endDateTime;

    public EPGEvent(EPGChannel epgChannel, long start, long end, String title, String programUrl, String startDateTime, String endDateTime) {
        this.channel = epgChannel;
        this.start = start;
        this.end = end;
        this.title = title;
        this.programUrl = programUrl;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    public String getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(String startDateTime) {
        this.startDateTime = startDateTime;
    }

    public String getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(String endDateTime) {
        this.endDateTime = endDateTime;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public EPGChannel getChannel() {
        return channel;
    }

    public long getStart() {
        return start;
    }

    public long getEnd() {
        return end;
    }

    public String getTitle() {
        if (TextUtils.isEmpty(title))
            return "";

        return title;
    }

    public String getProgramUrl() {
        return programUrl;
    }

    public boolean isCurrent() {
        long now = System.currentTimeMillis();
        return now >= start && now <= end;
    }

    public boolean isSelected() {
        return this.selected;
    }

    public EPGEvent getNextEvent() {
        return nextEvent;
    }

    public void setNextEvent(EPGEvent nextEvent) {
        this.nextEvent = nextEvent;
    }

    public EPGEvent getPreviousEvent() {
        return previousEvent;
    }

    public void setPreviousEvent(EPGEvent previousEvent) {
        this.previousEvent = previousEvent;
    }
}
