package feedup.housetargaryen.com.feedup.Connection.Model;

/**
 * Created by Moez on 13/10/2017.
 */

public class User {

    /*
    * Contains the User definition*/

    //TODO :  Effacer l'attirubut "verified Email."

    private String mail;
    private String pass;
    private String firstName;
    private String lastName;
    private int cin;
    private String rib;
    private String adresse;
    private int cumulDePoints;

    public User(String mail, String pass, String firstName, String lastName, int cin, String rib, String adresse, int cumulDePoints) {
        this.mail = mail;
        this.pass = pass;
        this.firstName = firstName;
        this.lastName = lastName;
        this.cin = cin;
        this.rib = rib;
        this.adresse = adresse;
        this.cumulDePoints = cumulDePoints;
    }

    public User() {
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
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

    public int getCin() {
        return cin;
    }

    public void setCin(int cin) {
        this.cin = cin;
    }

    public String getRib() {
        return rib;
    }

    public void setRib(String rib) {
        this.rib = rib;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public int getCumulDePoints() {
        return cumulDePoints;
    }

    public void setCumulDePoints(int cumulDePoints) {
        this.cumulDePoints = cumulDePoints;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (getCin() != user.getCin()) return false;
        if (!getMail().equals(user.getMail())) return false;
        if (getFirstName() != null ? !getFirstName().equals(user.getFirstName()) : user.getFirstName() != null)
            return false;
        return getLastName() != null ? getLastName().equals(user.getLastName()) : user.getLastName() == null;

    }

    @Override
    public int hashCode() {
        int result = getMail().hashCode();
        result = 31 * result + (getFirstName() != null ? getFirstName().hashCode() : 0);
        result = 31 * result + (getLastName() != null ? getLastName().hashCode() : 0);
        result = 31 * result + getCin();
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "mail='" + mail + '\'' +
                ", pass='" + pass + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", cin=" + cin +
                ", rib=" + rib +
                ", adresse='" + adresse + '\'' +
                ", cumulDePoints=" + cumulDePoints +
                '}';
        //
    }
}
