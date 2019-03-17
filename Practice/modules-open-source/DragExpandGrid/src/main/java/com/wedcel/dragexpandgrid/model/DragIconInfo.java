package com.wedcel.dragexpandgrid.model;

public class DragIconInfo {

    /**
     * 可展开的
     **/
    public static final int CATEGORY_EXPAND = 100;

    /**
     * 不可展开的
     **/
    public static final int CATEGORY_ONLY = 300;

    private int id;
    private String name;
    private int resIconId;
    /**
     * 类型
     **/
    private int category;


    public DragIconInfo(int id, String name, int resIconId, int category) {
        super();
        this.id = id;
        this.name = name;
        this.resIconId = resIconId;
        this.category = category;
    }


    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public int getResIconId() {
        return resIconId;
    }


    public void setResIconId(int resIconId) {
        this.resIconId = resIconId;
    }


    public int getCategory() {
        return category;
    }


    public void setCategory(int category) {
        this.category = category;
    }

}
