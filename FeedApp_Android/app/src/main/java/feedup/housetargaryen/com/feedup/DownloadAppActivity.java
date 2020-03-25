package feedup.housetargaryen.com.feedup;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import feedup.housetargaryen.com.feedup.Connection.Model.Application;

public class DownloadAppActivity extends AppCompatActivity implements View.OnClickListener {

    Button buttonDownload;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_app);
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
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }
}
