package com.wedcel.dragexpandgrid.model;

public class DragIconInfo {
    public int id;
    public String name;
    public int resIconId;
    public int index = -1;

    public DragIconInfo(int id, String name, int resIconId) {
        super();
        this.id = id;
        this.name = name;
        this.resIconId = resIconId;
    }
}
