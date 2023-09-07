package learn.models;

import java.util.Objects;

public class Host {

    private int id;
    private String email;
    private String firstName;
    private String lastName;
    private Location location;


    public Host(){

    }

    public Host(int id, String email, String firstName, String lastName, Location location){
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Host host = (Host) o;
//        return id == host.id && Objects.equals(email, host.email) && Objects.equals(firstName, host.firstName) && Objects.equals(lastName, host.lastName);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(id, email, firstName, lastName);
//    }
}
