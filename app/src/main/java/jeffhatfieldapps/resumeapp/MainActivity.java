package jeffhatfieldapps.resumeapp;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends Activity {

    private ImageView backgroundImageView;
    private CircleImageView myPhotoImageView;
    private TextView topText;
    private TextView bottomText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        backgroundImageView = findViewById(R.id.main_background);
        myPhotoImageView = findViewById(R.id.profile_image);
        topText = findViewById(R.id.top_text);
        bottomText = findViewById(R.id.bottom_text);

        myPhotoImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainTransitionAnimation();
            }
        });
    }

    private void mainTransitionAnimation(){
    }
}
