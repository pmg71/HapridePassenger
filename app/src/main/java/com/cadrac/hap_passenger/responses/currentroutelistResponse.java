package com.cadrac.hap_passenger.responses;

public class currentroutelistResponse {

    public String getStatus() {
        return status;
    }

    String status;

    public Data[] getData() {
        return data;
    }

    Data[] data;
    public class Data{

        public String getSource() {
            return source;
        }

        public String getDestination() {
            return destination;
        }

        public String getFare() {
            return fare;
        }

        public String getSeats() {
            return seats;
        }

        public String getVehicletype() {
            return vehicletype;
        }

        String source, destination, fare, seats, vehicletype;

    }

}
