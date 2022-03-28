package model;

public class Task {
    private int id;
    public int arrivingTime;
    public int servingTime;

    public Task (int id, int arrivingTime, int servingTime ) {
        this.id = id;
        this.arrivingTime = arrivingTime;
        this.servingTime = servingTime;
    }

    public Task (){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getArrivingTime() {
        return arrivingTime;
    }

    public void setArrivingTime(int arrivingTime) {
        this.arrivingTime = arrivingTime;
    }

    public int getServingTime() {
        return servingTime;
    }

    public void setServingTime(int servingTime) {
        this.servingTime = servingTime;
    }
}