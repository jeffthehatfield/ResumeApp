package jeffhatfieldapps.resumeapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

public class SplashActivity extends AppCompatActivity {

    private ImageView backgroundImageView;
    private CircleImageView myPhotoImageView;
    private TextView topText;
    private TextView bottomText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        backgroundImageView = (ImageView) findViewById(R.id.main_background);
        myPhotoImageView = (CircleImageView) findViewById(R.id.profile_image);
        topText = (TextView) findViewById(R.id.top_text);
        bottomText = (TextView) findViewById(R.id.bottom_text);

        myPhotoImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainTransitionAnimation();
            }
        });
    }

    private void mainTransitionAnimation(){
        ObjectAnimator fadeBackgroundOutAnim = Animations.fadeOutAnimation(backgroundImageView, 1000, 0);
        fadeBackgroundOutAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(SplashActivity.this,
                                myPhotoImageView,
                                ViewCompat.getTransitionName(myPhotoImageView));
                startActivity(intent, options.toBundle());
            }
        });
        fadeBackgroundOutAnim.start();
    }
}
