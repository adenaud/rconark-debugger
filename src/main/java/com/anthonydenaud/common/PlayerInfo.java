package com.anthonydenaud.common;

public class PlayerInfo {

    private int index;
    private String name;
    private float duration;

    public PlayerInfo() {
    }

    public PlayerInfo(int index, String name, float duration) {
        this.index = index;
        this.name = name;
        this.duration = duration;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getDuration() {
        return duration;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }
}
