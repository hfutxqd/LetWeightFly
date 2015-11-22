package cf.imxqd.LetWeightFly.Activities;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.AnticipateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import cf.imxqd.LetWeightFly.R;
import cf.imxqd.LetWeightFly.Utils.Tool;

/**
 * Created by Henry on 2015/6/12.
 */
public class WelcomeActivity extends Activity implements Animator.AnimatorListener{

    TextView text;
    ImageView image;
    boolean notStarted = true;
    boolean done = false;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        setContentView(R.layout.welcome);
        text = (TextView) findViewById(R.id.wel_text);
        text.setVisibility(TextView.INVISIBLE);
        image = (ImageView) findViewById(R.id.wel_img);
        notStarted = true;
        done = false;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        done = false;
        if (notStarted) {
            int imageW = image.getWidth();

            ObjectAnimator obanimator2 = new ObjectAnimator();
            obanimator2.setTarget(image);
            obanimator2.setPropertyName("X");
            obanimator2.setFloatValues(-image.getWidth(), getScreenWidth() / 2 - imageW / 2);
            obanimator2.setDuration(1000);
            obanimator2.setInterpolator(new AnticipateInterpolator());
            obanimator2.addListener(this);

            obanimator2.start();
            notStarted = false;
        }
    }


    private int getScreenWidth()
    {
        return this.getWindowManager().getDefaultDisplay().getWidth();
    }


    @Override
    public void onBackPressed()
    {
    }

    @Override
    public void onAnimationStart(Animator animation) {

    }

    @Override
    public void onAnimationEnd(Animator animation) {
        if(!notStarted) {
            text.setVisibility(TextView.VISIBLE);
            ObjectAnimator obanimator = new ObjectAnimator();
            obanimator.setTarget(text);
            obanimator.setPropertyName("TextSize");
            obanimator.setFloatValues(20, 80);
            obanimator.setDuration(1000);
            obanimator.setInterpolator(new AnticipateInterpolator());

            ObjectAnimator obanimator2 = new ObjectAnimator();
            obanimator2.setTarget(text);
            obanimator2.setPropertyName("Alpha");
            obanimator2.setFloatValues(1, 0.1f);
            obanimator2.setDuration(1000);
            obanimator2.setInterpolator(new AnticipateInterpolator());
            obanimator.start();
            obanimator2.start();
            final Intent intent = new Intent();
            intent.setClass(this,ConfigUserInfo.class);
            obanimator2.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    startActivity(intent);
                    overridePendingTransition(R.anim.translate_in, R.anim.translate_out);
                    finish();
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        }
    }

    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }
}
