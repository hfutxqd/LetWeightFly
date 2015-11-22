package cf.imxqd.LetWeightFly.Fragments;

import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.db.chart.Tools;
import com.db.chart.model.LineSet;
import com.db.chart.view.AxisController;
import com.db.chart.view.LineChartView;
import com.db.chart.view.animation.Animation;


import cf.imxqd.LetWeightFly.R;


public class WeekFragment extends Fragment {


    /** First chart */
    private LineChartView mChartOne;
    private Button mPlayOne;
    private boolean mUpdateOne;
    private final String[] mLabelsOne= {"", "10-15", "", "15-20", "", "20-25", "", "25-30", "", "30-35", ""};
    private final float[][] mValuesOne = {{3.5f, 4.7f, 4.3f, 8f, 6.5f, 10f, 7f, 8.3f, 7.0f, 7.3f, 5f},
            {2.5f, 3.5f, 3.5f, 7f, 5.5f, 8.5f, 6f, 6.3f, 5.8f, 6.3f, 4.5f},
            {1.5f, 2.5f, 2.5f, 4f, 2.5f, 5.5f, 5f, 5.3f, 4.8f, 5.3f, 3f}};



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.fragment_week, container, false);

        // Init first chart
        mUpdateOne = true;
        mChartOne = (LineChartView) layout.findViewById(R.id.linechart);
        mPlayOne = (Button) layout.findViewById(R.id.play);
        mPlayOne.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mUpdateOne)
                    updateChart(0, mChartOne, mPlayOne);
                else
                    dismissChart(0, mChartOne, mPlayOne);
                mUpdateOne = !mUpdateOne;
            }
        });


        showChart(0, mChartOne, mPlayOne);
        return layout;
    }


    /**
     * Show a CardView chart
     * @param tag   Tag specifying which chart should be dismissed
     * @param chart   Chart view
     * @param btn    Play button
     */
    private void showChart(final int tag, final LineChartView chart, final Button btn){
        dismissPlay(btn);
        Runnable action =  new Runnable() {
            @Override
            public void run() {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        showPlay(btn);
                    }
                }, 500);
            }
        };

        switch(tag){
            case 0:
                produceOne(chart, action); break;
            default:
        }
    }


    /**
     * Update the values of a CardView chart
     * @param tag   Tag specifying which chart should be dismissed
     * @param chart   Chart view
     * @param btn    Play button
     */
    private void updateChart(final int tag, final LineChartView chart, Button btn){

        dismissPlay(btn);

        switch(tag){
            case 0:
                updateOne(chart); break;
            default:
        }
    }



    /**
     * Dismiss a CardView chart
     * @param tag   Tag specifying which chart should be dismissed
     * @param chart   Chart view
     * @param btn    Play button
     */
    private void dismissChart(final int tag, final LineChartView chart, final Button btn){

        dismissPlay(btn);

        Runnable action =  new Runnable() {
            @Override
            public void run() {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        showPlay(btn);
                        showChart(tag, chart, btn);
                    }
                }, 500);
            }
        };

        switch(tag){
            case 0:
                dismissOne(chart, action); break;
            default:
        }
    }


    /**
     * Show CardView play button
     * @param btn    Play button
     */
    private void showPlay(Button btn){
        btn.setEnabled(true);
        btn.animate().alpha(1).scaleX(1).scaleY(1);
    }


    /**
     * Dismiss CardView play button
     * @param btn    Play button
     */
    private void dismissPlay(Button btn){
        btn.setEnabled(false);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH)
            btn.animate().alpha(0).scaleX(0).scaleY(0);
        else
            btn.setVisibility(View.INVISIBLE);
    }



    /**
     *
     * Chart 1
     *
     */

    public void produceOne(LineChartView chart, Runnable action){

        LineSet dataset = new LineSet(mLabelsOne, mValuesOne[0]);
        dataset.setColor(Color.parseColor("#a34545"))
                .setFill(Color.parseColor("#a34545"))
                .setSmooth(true);
        chart.addData(dataset);

        dataset = new LineSet(mLabelsOne, mValuesOne[1]);
        dataset.setColor(Color.parseColor("#e08b36"))
                .setFill(Color.parseColor("#e08b36"))
                .setSmooth(true);
        chart.addData(dataset);

        dataset = new LineSet(mLabelsOne, mValuesOne[2]);
        dataset.setColor(Color.parseColor("#61263c"))
                .setFill(Color.parseColor("#61263c"))
                .setSmooth(true);
        chart.addData(dataset);

        chart.setTopSpacing(Tools.fromDpToPx(15))
                .setBorderSpacing(Tools.fromDpToPx(0))
                .setAxisBorderValues(0, 10, 1)
                .setXLabels(AxisController.LabelPosition.INSIDE)
                .setYLabels(AxisController.LabelPosition.NONE)
                .setLabelsColor(Color.parseColor("#e08b36"))
                .setXAxis(false)
                .setYAxis(false);

        Animation anim = new Animation().setStartPoint(-1, 1).setEndAction(action);

        chart.show(anim);
    }


    public void updateOne(LineChartView chart){
        float[][] newValues = {{3.5f, 4.7f, 4.3f, 8f, 6.5f, 10f, 7f, 8.3f, 7.0f, 7.3f, 5f},
                {1f, 2f, 2f, 3.5f, 2f, 5f, 4.5f, 4.8f, 4.3f, 4.8f, 2.5f}};
        chart.updateValues(1, newValues[0]);
        chart.updateValues(2, newValues[1]);
        chart.notifyDataUpdate();
    }


    public static void dismissOne(LineChartView chart, Runnable action){
        chart.dismiss(new Animation().setStartPoint(-1, 0).setEndAction(action));
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mChartOne.removeAllViews();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
