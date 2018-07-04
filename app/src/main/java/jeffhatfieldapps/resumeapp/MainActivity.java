package jeffhatfieldapps.resumeapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    private CircleImageView myPhotoImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myPhotoImageView = findViewById(R.id.profile_image);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
