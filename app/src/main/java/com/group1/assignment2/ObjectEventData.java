package com.group1.assignment2;

public class ObjectEventData {

    private Integer id;
    private long timestamp;
    private float x;
    private float y;
    private float z;

    public ObjectEventData(){}

/*    public ObjectEventData(Integer id, long time, float x, float y, float z) {
        super();
        this.id = id;
        this.timestamp = time;
        this.x = x;
        this.y = y;
        this.z = z;
    } */

    // setters
    public void setId(Integer id) {
        this.id = id;
    }

    public void setTimeStamp(long time) {
        this.timestamp = time;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setZ(float z) {
        this.z = z;
    }

    // getters
    public Integer getId() {
        return this.id;
    }

    public long getTimeStamp() {
        return this.timestamp;
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public float getZ() {
        return this.z;
    }
}