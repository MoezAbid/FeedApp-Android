package feedup.housetargaryen.com.feedup;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import feedup.housetargaryen.com.feedup.Connection.Model.Application;

public class DownloadWithBrowserActivity extends AppCompatActivity implements View.OnClickListener {
    Button buttonDownload;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        initView();
        Application application = getIntent().getParcelableExtra(Application.TAG);
        url = application.getApkUri();
        Toast.makeText(this,application.getNomAPP(),Toast.LENGTH_LONG).show();
        Log.d("myApplication", application.getNomAPP());
        initListeners();

    }

    public void initView(){
        buttonDownload = (Button) findViewById(R.id.buttonDownload);
    }

    public void initListeners(){
        buttonDownload.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {


        /*downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        Long reference = downloadManager.enqueue(request);*/

       Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);


    }
}
