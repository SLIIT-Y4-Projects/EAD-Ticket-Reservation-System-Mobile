package com.example.ead;

public class Reservation {
    private String id;
    private String reservationDate;
    private String status;
    private String passengerId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(String reservationDate) {
        this.reservationDate = reservationDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(String passengerId) {
        this.passengerId = passengerId;
    }

    public String getTrainId() {
        return trainId;
    }

    public void setTrainId(String trainId) {
        this.trainId = trainId;
    }

    private String trainId;

    public Reservation(){}
    public Reservation(String id, String reservationDate, String status, String passengerId, String trainId) {
        this.id = id;
        this.reservationDate = reservationDate;
        this.status = status;
        this.passengerId = passengerId;
        this.trainId = trainId;
    }
}
