package folaoyewole.look4mee.Model;

/**
 * Created by sp_developer on 11/7/16.
 */
public class AccountDetails {
    String accName;
    String phone;
    String employerID;

    public String getEmployerID() {
        return employerID;
    }

    public void setEmployerID(String employerID) {
        this.employerID = employerID;
    }

    public String getAccName() {
        return accName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAccName(String accName) {
        this.accName = accName;
    }

}
