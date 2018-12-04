package com.example.vvdn.demoproject.animation;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vvdn.volleydemoproject.R;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button button1, button2, button3;

    /**
     * Vertices of pentagon
     */
    Point[] pentagonVertices;

    Button fab;


    /**
     * Buttons to be animated
     */
    Button[] buttons;

    LinearLayout[] linearLayouts;

    int height, width;

    int radius;

    int ANIMATION_DURATION = 1000;

    /**
     * Coordination of button
     */
    int startPositionX = 0;
    int startPositionY = 0;

    /**
     * To check which animation is to be played
     * O for enter animation
     * 1 for exit animation
     */
    int whichAnimation = 0;

    //Polygon
    int NUM_OF_SIDES = 3;
    int POSITION_CORRECTION = 11;

    //    int[] enterDelay = {80, 120, 160, 40, 0};
//    int[] exitDelay = {80, 40, 0, 120, 160};
    int[] enterDelay = {80, 80, 80, 40, 0};
    int[] exitDelay = {80, 40, 0, 120, 160};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main11);

        height = (int) getResources().getDimension(R.dimen.button_height);
        width = (int) getResources().getDimension(R.dimen.button_width);
        radius = (int) getResources().getDimension(R.dimen.radius);

        fab = (Button) findViewById(R.id.fab);

        fab.setOnClickListener(this);
        initViews();
        calculatePentagonVertices(radius, POSITION_CORRECTION);

    }

    private void initViews() {
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);

    }

    private void calculatePentagonVertices(int radius, int rotation) {

        pentagonVertices = new Point[NUM_OF_SIDES];

        /**
         q         * Calculating the center of pentagon
         */
        Display display = getWindowManager().getDefaultDisplay();
        int centerX = display.getWidth() / 2;
        int centerY = (int) (display.getHeight() / 1.4f);

        /**
         * Calculating the coordinates of vertices of pentagon
         */
        for (int i = 0; i < NUM_OF_SIDES; i++) {
            pentagonVertices[i] = new Point((int) (radius * Math.cos(rotation + i * 2 * Math.PI / NUM_OF_SIDES)) + centerX,
                    (i == 0 ? 1350 : ((int) (radius * Math.sin(rotation + i * 2 * Math.PI / NUM_OF_SIDES)) + centerY - 50))
                    //1490 is center button y position that change on 1 digit
            );
        }

        buttons = new Button[pentagonVertices.length];
        linearLayouts = new LinearLayout[pentagonVertices.length];

       /* buttons[0]=button1;
        buttons[1]=button2;
        buttons[2]=button3;*/


        for (int i = 0; i < buttons.length; i++) {
            //Adding button at (0,0) coordinates and setting their visibility to zero

            linearLayouts[i] = new LinearLayout(MainActivity.this);
            TextView textView = new TextView(this);
            textView.setText("Send");
            linearLayouts[i].setGravity(Gravity.CENTER);
            linearLayouts[i].addView(textView);
            linearLayouts[i].setLayoutParams(new RelativeLayout.LayoutParams(5, 5));
            linearLayouts[i].setX(0);
            linearLayouts[i].setY(0);
            linearLayouts[i].setTag(i);
            linearLayouts[i].setOnClickListener(this);
            linearLayouts[i].setVisibility(View.INVISIBLE);
            linearLayouts[i].setBackgroundResource(R.drawable.circular_background);
//            linearLayouts[i].setTextColor(Color.WHITE);
//            linearLayouts[i].setText(String.valueOf(i + 1));
//            linearLayouts[i].setTextSize(20);

//            buttons[i] = new Button(MainActivity.this);
//            buttons[i].setLayoutParams(new RelativeLayout.LayoutParams(5, 5));
//            buttons[i].setX(0);
//            buttons[i].setY(0);
//            buttons[i].setTag(i);
//            buttons[i].setOnClickListener(this);
//            buttons[i].setVisibility(View.INVISIBLE);
//            buttons[i].setBackgroundResource(R.drawable.circular_background);
//            buttons[i].setTextColor(Color.WHITE);
//            buttons[i].setText(String.valueOf(i + 1));
//            buttons[i].setTextSize(20);
            /**
             * Adding those buttons in acitvities layout*/

//            ((RelativeLayout) findViewById(R.id.activity_main)).addView(buttons[i]);
            ((RelativeLayout) findViewById(R.id.activity_main)).addView(linearLayouts[i]);


        }
    }

    @Override
    public void onClick(View view) {

        boolean isFabClicked = false;

        switch (view.getId()) {
            case R.id.fab:
                isFabClicked = true;
                if (whichAnimation == 0) {
                    /**
                     * Getting the center point of floating action button
                     *  to set start point of buttons
                     */
                    startPositionX = (int) view.getX() + 50;
                    startPositionY = (int) view.getY() + 50;

//                    for (Button button : buttons) {
//                        button.setX(startPositionX);
//                        button.setY(startPositionY);
//                        button.setVisibility(View.VISIBLE);
//                    }
//                    for (int i = 0; i < buttons.length; i++) {
//                        playEnterAnimation(buttons[i], i);
//                    }
                    for (LinearLayout linearLayout : linearLayouts) {
                        linearLayout.setX(startPositionX);
                        linearLayout.setY(startPositionY);
                        linearLayout.setVisibility(View.VISIBLE);
                    }
//                    for (int i = 0; i < buttons.length; i++) {
//                        playEnterAnimation(buttons[i], i);
//                    }

                    for (int i = 0; i < linearLayouts.length; i++) {
                        playEnterAnimation1(linearLayouts[i], i);
                    }
                    whichAnimation = 1;
                } else {
//                    for (int i = 0; i < buttons.length; i++) {
//                        playExitAnimation(buttons[i], i);
//                    }

                    for (int i = 0; i < linearLayouts.length; i++) {
                        playExitAnimation1(linearLayouts[i], i);
                    }
                    whichAnimation = 0;
                }
        }

        if (!isFabClicked) {
            switch ((int) view.getTag()) {
                case 0:
                    Toast.makeText(this, "Button 1 clicked", Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    Toast.makeText(this, "Button 2 clicked", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    Toast.makeText(this, "Button 3 clicked", Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    Toast.makeText(this, "Button 4 clicked", Toast.LENGTH_SHORT).show();
                    break;
                case 4:
                    Toast.makeText(this, "Button 5 clicked", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    private void playEnterAnimation1(final LinearLayout button, int position) {

        /**
         * Animator that animates buttons x and y position simultaneously with size
         */
        AnimatorSet buttonAnimator = new AnimatorSet();

        /**
         * ValueAnimator to update x position of a button
         */
        ValueAnimator buttonAnimatorX = ValueAnimator.ofFloat(startPositionX + button.getLayoutParams().width / 2,
                pentagonVertices[position].x);
        buttonAnimatorX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                button.setX((float) animation.getAnimatedValue() - button.getLayoutParams().width / 2);
                button.requestLayout();
            }
        });
        buttonAnimatorX.setDuration(ANIMATION_DURATION);

        /**
         * ValueAnimator to update y position of a button
         */
        ValueAnimator buttonAnimatorY = ValueAnimator.ofFloat(startPositionY + 5,
                pentagonVertices[position].y);
        buttonAnimatorY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                button.setY((float) animation.getAnimatedValue());
                button.requestLayout();
            }
        });
        buttonAnimatorY.setDuration(ANIMATION_DURATION);

        /* *
         * This will increase the size of button*/

        ValueAnimator buttonSizeAnimator = ValueAnimator.ofInt(5, width);
        buttonSizeAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                button.getLayoutParams().width = (int) animation.getAnimatedValue();
                button.getLayoutParams().height = (int) animation.getAnimatedValue();
                button.requestLayout();
            }
        });
        buttonSizeAnimator.setDuration(ANIMATION_DURATION);

        /**
         * Add both x and y position update animation in
         *  animator set
         */
        buttonAnimator.play(buttonAnimatorX).with(buttonAnimatorY).with(buttonSizeAnimator);
        buttonAnimator.setStartDelay(enterDelay[position]);
        buttonAnimator.start();
    }

    private void playExitAnimation1(final LinearLayout button, int position) {

        /**
         * Animator that animates buttons x and y position simultaneously with size
         */
        AnimatorSet buttonAnimator = new AnimatorSet();

        /**
         * ValueAnimator to update x position of a button
         */
        ValueAnimator buttonAnimatorX = ValueAnimator.ofFloat(pentagonVertices[position].x - button.getLayoutParams().width / 2,
                startPositionX);
        buttonAnimatorX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                button.setX((float) animation.getAnimatedValue());
                button.requestLayout();
            }
        });
        buttonAnimatorX.setDuration(ANIMATION_DURATION);

        /**
         * ValueAnimator to update y position of a button
         */
        ValueAnimator buttonAnimatorY = ValueAnimator.ofFloat(pentagonVertices[position].y,
                startPositionY + 5);
        buttonAnimatorY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                button.setY((float) animation.getAnimatedValue());
                button.requestLayout();
            }
        });
        buttonAnimatorY.setDuration(ANIMATION_DURATION);

        /* *
         * This will decrease the size of button
         */
        ValueAnimator buttonSizeAnimator = ValueAnimator.ofInt(width, 5);
        buttonSizeAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                button.getLayoutParams().width = (int) animation.getAnimatedValue();
                button.getLayoutParams().height = (int) animation.getAnimatedValue();
                button.requestLayout();
            }
        });
        buttonSizeAnimator.setDuration(ANIMATION_DURATION);

        /**
         * Add both x and y position update animation in
         *  animator set
         */
        buttonAnimator.play(buttonAnimatorX).with(buttonAnimatorY).with(buttonSizeAnimator);
        buttonAnimator.setStartDelay(exitDelay[position]);
        buttonAnimator.start();
    }

    private void playEnterAnimation(final Button button, int position) {

        /**
         * Animator that animates buttons x and y position simultaneously with size
         */
        AnimatorSet buttonAnimator = new AnimatorSet();

        /**
         * ValueAnimator to update x position of a button
         */
        ValueAnimator buttonAnimatorX = ValueAnimator.ofFloat(startPositionX + button.getLayoutParams().width / 2,
                pentagonVertices[position].x);
        buttonAnimatorX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                button.setX((float) animation.getAnimatedValue() - button.getLayoutParams().width / 2);
                button.requestLayout();
            }
        });
        buttonAnimatorX.setDuration(ANIMATION_DURATION);

        /**
         * ValueAnimator to update y position of a button
         */
        ValueAnimator buttonAnimatorY = ValueAnimator.ofFloat(startPositionY + 5,
                pentagonVertices[position].y);
        buttonAnimatorY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                button.setY((float) animation.getAnimatedValue());
                button.requestLayout();
            }
        });
        buttonAnimatorY.setDuration(ANIMATION_DURATION);

        /* *
         * This will increase the size of button*/

        ValueAnimator buttonSizeAnimator = ValueAnimator.ofInt(5, width);
        buttonSizeAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                button.getLayoutParams().width = (int) animation.getAnimatedValue();
                button.getLayoutParams().height = (int) animation.getAnimatedValue();
                button.requestLayout();
            }
        });
        buttonSizeAnimator.setDuration(ANIMATION_DURATION);

        /**
         * Add both x and y position update animation in
         *  animator set
         */
        buttonAnimator.play(buttonAnimatorX).with(buttonAnimatorY).with(buttonSizeAnimator);
        buttonAnimator.setStartDelay(enterDelay[position]);
        buttonAnimator.start();
    }

    private void playExitAnimation(final Button button, int position) {

        /**
         * Animator that animates buttons x and y position simultaneously with size
         */
        AnimatorSet buttonAnimator = new AnimatorSet();

        /**
         * ValueAnimator to update x position of a button
         */
        ValueAnimator buttonAnimatorX = ValueAnimator.ofFloat(pentagonVertices[position].x - button.getLayoutParams().width / 2,
                startPositionX);
        buttonAnimatorX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                button.setX((float) animation.getAnimatedValue());
                button.requestLayout();
            }
        });
        buttonAnimatorX.setDuration(ANIMATION_DURATION);

        /**
         * ValueAnimator to update y position of a button
         */
        ValueAnimator buttonAnimatorY = ValueAnimator.ofFloat(pentagonVertices[position].y,
                startPositionY + 5);
        buttonAnimatorY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                button.setY((float) animation.getAnimatedValue());
                button.requestLayout();
            }
        });
        buttonAnimatorY.setDuration(ANIMATION_DURATION);

        /* *
         * This will decrease the size of button
         */
        ValueAnimator buttonSizeAnimator = ValueAnimator.ofInt(width, 5);
        buttonSizeAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                button.getLayoutParams().width = (int) animation.getAnimatedValue();
                button.getLayoutParams().height = (int) animation.getAnimatedValue();
                button.requestLayout();
            }
        });
        buttonSizeAnimator.setDuration(ANIMATION_DURATION);

        /**
         * Add both x and y position update animation in
         *  animator set
         */
        buttonAnimator.play(buttonAnimatorX).with(buttonAnimatorY).with(buttonSizeAnimator);
        buttonAnimator.setStartDelay(exitDelay[position]);
        buttonAnimator.start();
    }
}
