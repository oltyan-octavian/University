package ro.iss2024.laborator5iss.domain;

public class User extends Entity<Integer>{
    private String name;
    private String password;
    private String PNC;
    private String address;
    private String phoneNumber;
    private String type;

    public User(Integer integer, String name, String password, String PNC, String address, String phoneNumber, String type) {
        super(integer);
        this.name = name;
        this.password = password;
        this.PNC = PNC;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.type = type;
    }

    public User(String name, String password, String PNC, String address, String phoneNumber, String type) {
        super();
        this.name = name;
        this.password = password;
        this.PNC = PNC;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPNC() {
        return PNC;
    }

    public void setPNC(String PNC) {
        this.PNC = PNC;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", PNC='" + PNC + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
