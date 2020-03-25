package feedup.housetargaryen.com.feedup.Connection.Model;

/**
 * Created by poste on 25/11/2017.
 */

public class FeedBack {

    public static final String TAG = "feedback";

    private String screenShotUri;
    private String commentaire;
    private String dateFeedback;
    private String idUser;
    private String idApplication;
    private int noteFeedback;

    public FeedBack() {
    }

    public FeedBack(String screenShotUri, String commentaire, String dateFeedback, String idUser, String idApplication, int noteFeedback) {
        this.screenShotUri = screenShotUri;
        this.commentaire = commentaire;
        this.dateFeedback = dateFeedback;
        this.idUser = idUser;
        this.idApplication = idApplication;
        this.noteFeedback = noteFeedback;
    }

    public static String getTAG() {
        return TAG;
    }

    public String getScreenShotUri() {
        return screenShotUri;
    }

    public void setScreenShotUri(String screenShotUri) {
        this.screenShotUri = screenShotUri;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public String getDateFeedback() {
        return dateFeedback;
    }

    public void setDateFeedback(String dateFeedback) {
        this.dateFeedback = dateFeedback;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getIdApplication() {
        return idApplication;
    }

    public void setIdApplication(String idApplication) {
        this.idApplication = idApplication;
    }

    public int getNoteFeedback() {
        return noteFeedback;
    }

    public void setNoteFeedback(int noteFeedback) {
        this.noteFeedback = noteFeedback;
    }

    @Override
    public String toString() {
        return "FeedBack{" +
                "screenShotUri='" + screenShotUri + '\'' +
                ", commentaire='" + commentaire + '\'' +
                ", dateFeedback='" + dateFeedback + '\'' +
                ", idUser='" + idUser + '\'' +
                ", idApplication='" + idApplication + '\'' +
                ", noteFeedback=" + noteFeedback +
                '}';
    }
}
