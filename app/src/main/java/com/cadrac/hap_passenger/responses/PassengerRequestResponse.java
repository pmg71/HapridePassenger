package com.cadrac.hap_passenger.responses;

public class PassengerRequestResponse {

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    String status;
    String seats;
    String cost;

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getSeats() {
        return seats;
    }

    public void setSeats(String seats) {
        this.seats = seats;
    }

    Data[] data;

    public Data[] getData() {
        return data;
    }

    public void setData(Data[] data) {
        this.data = data;
    }

    public class Data {

        String destination;
        String fare;
        String seats;
        String vehicletype;

        public String getCabcost() {
            return cabcost;
        }

        public void setCabcost(String cabcost) {
            this.cabcost = cabcost;
        }

        public String getAutocost() {
            return autocost;
        }

        public void setAutocost(String autocost) {
            this.autocost = autocost;
        }

        String cabcost;
        String autocost;





        public String getDestination() {
            return destination;
        }

        public void setDestination(String destination) {
            this.destination = destination;
        }

        public String getFare() {
            return fare;
        }

        public void setFare(String fare) {
            this.fare = fare;
        }

        public String getSeats() {
            return seats;
        }

        public void setSeats(String seats) {
            this.seats = seats;
        }

        public String getVehicletype() {
            return vehicletype;
        }

        public void setVehicletype(String vehicletype) {
            this.vehicletype = vehicletype;
        }


    }
   }

