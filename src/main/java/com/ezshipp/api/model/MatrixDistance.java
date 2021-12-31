package com.ezshipp.api.model;

import lombok.Data;

@Data
public class MatrixDistance implements Comparable<MatrixDistance> {

    private double distance;
    private double duration;
    private int flag;

    public MatrixDistance(double distance, double duration) {
        this.distance = distance;
        this.duration = duration;
        this.flag = 1;
    }

    @Override
    public int compareTo(MatrixDistance o) {
        return (int)(this.distance - o.distance);
    }

}
