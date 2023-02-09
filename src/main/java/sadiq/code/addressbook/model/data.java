package sadiq.code.addressbook.model;

public class data {

    private String nameData;
    private String phoneData;

    public data(String name, String phone) {
        this.nameData = name;
        this.phoneData = phone;
    }

    public String getName() {
        return nameData;
    }

    public void setName(String name) {
        this.nameData = nameData;
    }

    public String getPhone() {
        return phoneData;
    }

    public void setPhone(String phone) {
        this.phoneData  = phoneData;
    }

    @Override
    public String toString() {
        return nameData;
    }
}
