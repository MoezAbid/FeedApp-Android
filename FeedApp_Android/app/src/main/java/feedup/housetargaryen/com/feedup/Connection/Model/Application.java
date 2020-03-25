package feedup.housetargaryen.com.feedup.Connection.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ASUS on 31/10/2017.
 */

public class Application implements Parcelable {

    public static final String TAG = "application";

    private String ApkUri;
    private int NbrPoints;
    private String NomAPP;
    private String NomPackage;
    private String PubliciteUri;
    private int Ram;
    private float RateAPP;
    private String VersionApp;
    private String dateAjoutApp;
    private String description;
    private String logoUri;
    private int nombreFeedBack;
    private float taille;


    public Application() {
    }

    public Application(String ApkUri, int NbrPoints, String NomAPP, String NomPackage, String PubliciteUri, int Ram, float RateAPP, String VersionApp, String dateAjoutApp, String description, String logoUri, int nombreFeedBack, float taille) {
        this.ApkUri = ApkUri;
        this.NbrPoints = NbrPoints;
        this.NomAPP = NomAPP;
        this.NomPackage = NomPackage;
        this.PubliciteUri = PubliciteUri;
        this.Ram = Ram;
        this.RateAPP = RateAPP;
        this.VersionApp = VersionApp;
        this.dateAjoutApp = dateAjoutApp;
        this.description = description;
        this.logoUri = logoUri;
        this.nombreFeedBack = nombreFeedBack;
        this.taille = taille;
    }

    protected Application(Parcel in) {
        ApkUri = in.readString();
        NbrPoints = in.readInt();
        NomAPP = in.readString();
        NomPackage = in.readString();
        PubliciteUri = in.readString();
        Ram = in.readInt();
        RateAPP = in.readFloat();
        VersionApp = in.readString();
        dateAjoutApp = in.readString();
        description = in.readString();
        logoUri = in.readString();
        nombreFeedBack = in.readInt();
        taille = in.readFloat();
    }

    public static final Creator<Application> CREATOR = new Creator<Application>() {
        @Override
        public Application createFromParcel(Parcel in) {
            return new Application(in);
        }

        @Override
        public Application[] newArray(int size) {
            return new Application[size];
        }
    };

    public String getApkUri() {
        return ApkUri;
    }

    public void setApkUri(String ApkUri) {
        this.ApkUri = ApkUri;
    }

    public int getNbrPoints() {
        return NbrPoints;
    }

    public void setNbrPoints(int NbrPoints) {
        this.NbrPoints = NbrPoints;
    }

    public String getNomAPP() {
        return NomAPP;
    }

    public void setNomAPP(String NomAPP) {
        this.NomAPP = NomAPP;
    }

    public String getNomPackage() {
        return NomPackage;
    }

    public void setNomPackage(String NomPackage) {
        this.NomPackage = NomPackage;
    }

    public String getPubliciteUri() {
        return PubliciteUri;
    }

    public void setPubliciteUri(String PubliciteUri) {
        this.PubliciteUri = PubliciteUri;
    }

    public int getRam() {
        return Ram;
    }

    public void setRam(int Ram) {
        this.Ram = Ram;
    }

    public float getRateAPP() {
        return RateAPP;
    }

    public void setRateAPP(float RateAPP) {
        this.RateAPP = RateAPP;
    }

    public String getVersionApp() {
        return VersionApp;
    }

    public void setVersionApp(String VersionApp) {
        this.VersionApp = VersionApp;
    }

    public String getDateAjoutApp() {
        return dateAjoutApp;
    }

    public void setDateAjoutApp(String dateAjoutApp) {
        this.dateAjoutApp = dateAjoutApp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLogoUri() {
        return logoUri;
    }

    public void setLogoUri(String logoUri) {
        this.logoUri = logoUri;
    }

    public int getNombreFeedBack() {
        return nombreFeedBack;
    }

    public void setNombreFeedBack(int nombreFeedBack) {
        this.nombreFeedBack = nombreFeedBack;
    }

    public float getTaille() {
        return taille;
    }

    public void setTaille(float taille) {
        this.taille = taille;
    }

    @Override
    public String toString() {
        return "Application{" +
                "ApkUri='" + ApkUri + '\'' +
                ", NbrPoints=" + NbrPoints +
                ", NomAPP='" + NomAPP + '\'' +
                ", NomPackage='" + NomPackage + '\'' +
                ", PubliciteUri='" + PubliciteUri + '\'' +
                ", Ram=" + Ram +
                ", RateAPP=" + RateAPP +
                ", VersionApp='" + VersionApp + '\'' +
                ", dateAjoutApp='" + dateAjoutApp + '\'' +
                ", description='" + description + '\'' +
                ", logoUri='" + logoUri + '\'' +
                ", nombreFeedBack=" + nombreFeedBack +
                ", taille=" + taille +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ApkUri);
        dest.writeInt(NbrPoints);
        dest.writeString(NomAPP);
        dest.writeString(NomPackage);
        dest.writeString(PubliciteUri);
        dest.writeInt(Ram);
        dest.writeFloat(RateAPP);
        dest.writeString(VersionApp);
        dest.writeString(dateAjoutApp);
        dest.writeString(description);
        dest.writeString(logoUri);
        dest.writeInt(nombreFeedBack);
        dest.writeFloat(taille);
    }
}
