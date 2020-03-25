package feedup.housetargaryen.com.feedup.Connection.Model;

/**
 * Created by Hamza on 21/11/2017.
 */

public class Payement {
    private String UIDUser;
    private String Date;
    private String EtatPayement;
    private int Montant;
    private String RIB;


    public Payement() {
    }

    public Payement(String date, String etatPayement, int montant, String RIB, String UIDUser) {
        this.UIDUser=UIDUser;
        Date = date;
        EtatPayement = etatPayement;
        Montant = montant;
        this.RIB = RIB;
    }

    public String getUIDUser() {
        return UIDUser;
    }

    public void setUIDUser(String UIDUser) {
        this.UIDUser = UIDUser;
    }

    public String getDate() {
        return Date;
    }

    public String getEtatPayement() {
        return EtatPayement;
    }

    public int getMontant() {
        return Montant;
    }

    public String getRIB() {
        return RIB;
    }

    public void setDate(String date) {
        Date = date;
    }

    public void setEtatPayement(String etatPayement) {
        EtatPayement = etatPayement;
    }

    public void setMontant(int montant) {
        Montant = montant;
    }

    public void setRIB(String RIB) {
        this.RIB = RIB;
    }

    @Override
    public String toString() {
        return "Payement{" +
                "UIDUser='" + UIDUser + '\'' +
                ", Date='" + Date + '\'' +
                ", EtatPayement='" + EtatPayement + '\'' +
                ", Montant=" + Montant +
                ", RIB='" + RIB + '\'' +
                '}';
    }
}
