package com.cadrac.hap_passenger.responses;

public class RouteListResponse {

    String status;
    RouteListResponse.data[] data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }



    public RouteListResponse.data[] getData() {
        return data;
    }

    public void setData(RouteListResponse.data[] data) {
        this.data = data;
    }


    public class data {

        String source;
        String agentname;
        String agentnumber;

        public String getAgentid() {
            return agentid;
        }

        public void setAgentid(String agentid) {
            this.agentid = agentid;
        }

        String agentid;



       String  lattitude;

        String longitude;



        public String getAgentname() {
            return agentname;
        }

        public void setAgentname(String agentname) {
            this.agentname = agentname;
        }

        public String getAgentnumber() {
            return agentnumber;
        }

        public void setAgentnumber(String agentnumber) {
            this.agentnumber = agentnumber;
        }

        public String getLattitude() {
            return lattitude;
        }

        public void setLattitude(String lattitude) {
            this.lattitude = lattitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }






    }

}
