package resources;

public enum Constants {

    locationPath("/locations"),
    locationDetailsPath("/location_details"),
    search("/search"),
    reviews("/reviews"),
    restaurant("/restaurant"),
    cuisines("/cuisines");
    private String resource;


    Constants(String resource) {
        this.resource = resource;
    }

    public String getResource(){
            return resource;
        }
    }


