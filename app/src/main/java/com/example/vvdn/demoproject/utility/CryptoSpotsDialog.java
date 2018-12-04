//package com.example.vvdn.demoproject.utility;
//
//import android.animation.Animator;
//import android.animation.AnimatorListenerAdapter;
//import android.animation.AnimatorSet;
//import android.animation.ObjectAnimator;
//import android.app.Dialog;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.os.Bundle;
//import android.view.KeyEvent;
//import android.view.View;
//import android.view.animation.Interpolator;
//import android.widget.TextView;
//
//import com.example.vvdn.demoproject.R;
//
//public class CryptoSpotsDialog extends Dialog {
//    private AnimatorPlayer animator;
//    private CharSequence message;
//    private int size;
//    private AnimatedView[] spots;
//
//    /* renamed from: com.megogrid.megowallet.slave.dialog.CryptoSpotsDialog$1 */
//    static class C36921 implements OnCancelListener {
//        C36921() {
//        }
//
//        public void onCancel(DialogInterface dialog) {
//            dialog.cancel();
//            System.out.println("<<lm dialog cancel");
//        }
//    }
//
//    static class AnimatedView extends View {
//        private int target;
//
//        public AnimatedView(Context context) {
//            super(context);
//        }
//
//        public void setXFactor(float xFactor) {
//            setX(((float) this.target) * xFactor);
//        }
//
//        public void setTarget(int target) {
//            this.target = target;
//        }
//    }
//
//    static class AnimatorPlayer extends AnimatorListenerAdapter {
//        private Animator[] animators;
//        private boolean interrupted = false;
//
//        public AnimatorPlayer(Animator[] animators) {
//            this.animators = animators;
//        }
//
//        public void onAnimationEnd(Animator animation) {
//            if (!this.interrupted) {
//                animate();
//            }
//        }
//
//        public void play() {
//            animate();
//        }
//
//        public void stop() {
//            this.interrupted = true;
//        }
//
//        private void animate() {
//            AnimatorSet set = new AnimatorSet();
//            set.playTogether(this.animators);
//            set.addListener(this);
//            set.start();
//        }
//    }
//
//    static class HesitateInterpolator implements Interpolator {
//        private final double POW = 0.5d;
//
//        HesitateInterpolator() {
//        }
//
//        public float getInterpolation(float input) {
//            if (((double) input) < 0.5d) {
//                return ((float) Math.pow((double) (input * 2.0f), 0.5d)) * 0.5f;
//            }
//            return (((float) Math.pow((double) ((1.0f - input) * 2.0f), 0.5d)) * -0.5f) + 1.0f;
//        }
//    }
//
//    public CryptoSpotsDialog(Context context) {
//        this(context, R.style.mecartSpotsDialogDefault);
//    }
//
//    public CryptoSpotsDialog(Context context, CharSequence message) {
//        this(context);
//        this.message = message;
//    }
//
//    public CryptoSpotsDialog(Context context, int theme) {
//        super(context, theme);
//    }
//
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        requestWindowFeature(1);
//        setContentView(R.layout.waiting_dialog);
//        initMessage();
//        initProgress();
//    }
//
//    protected void onStart() {
//        super.onStart();
//        this.animator = new AnimatorPlayer(createAnimations());
//        this.animator.play();
//    }
//
//    protected void onStop() {
//        super.onStop();
//        this.animator.stop();
//    }
//
//    private void initMessage() {
//        if (this.message != null && this.message.length() > 0) {
//            ((TextView) findViewById(R.id.dmax_spots_title)).setText(this.message);
//        }
//    }
//
//    private void initProgress() {
//        ProgressLayout progress = (ProgressLayout) findViewById(R.id.dmax_spots_progress);
//        this.size = progress.getSpotsCount();
//        this.spots = new AnimatedView[this.size];
//        int size = getContext().getResources().getDimensionPixelSize(R.dimen.spot_size);
//        int progressWidth = getContext().getResources().getDimensionPixelSize(R.dimen.progress_width);
//        for (int i = 0; i < this.spots.length; i++) {
//            AnimatedView v = new AnimatedView(getContext());
//            v.setBackgroundResource(R.drawable.crypto_spots_spot);
//            v.setTarget(progressWidth);
//            v.setXFactor(-1.0f);
//            progress.addView(v, size, size);
//            this.spots[i] = v;
//        }
//    }
//
//    private Animator[] createAnimations() {
//        Animator[] animators = new Animator[this.size];
//        for (int i = 0; i < this.spots.length; i++) {
////            Animator move = ObjectAnimator.ofFloat(this.spots[i], "xFactor", new float[]{BitmapDescriptorFactory.HUE_RED, 1.0f});
//            Animator move = ObjectAnimator.ofFloat(this.spots[i], "xFactor", new float[]{0.0f, 1.0f});
//
//            move.setDuration(1500);
//            move.setInterpolator(new HesitateInterpolator());
////            move.setStartDelay((long) (i * CartButton.WIDTH_DIPS));
//            move.setStartDelay((long) (i * 20));
//
//            animators[i] = move;
//        }
//        return animators;
//    }
//
//    public static CryptoSpotsDialog startProgressDialog(final Context context, String tittle) {
//        CryptoSpotsDialog dialog = new CryptoSpotsDialog(context, (CharSequence) tittle);
//        dialog.setCancelable(true);
//        dialog.setCanceledOnTouchOutside(false);
//        dialog.setOnCancelListener(new C36921());
//        dialog.setOnKeyListener(new OnKeyListener() {
//            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
//                if (keyCode != 4) {
//                    return false;
//                }
//                dialog.cancel();
////                if (context instanceof MainActivity) {
////                    ((MainActivity) context).finish();
////                }
//                System.out.println("<<lm dialog cancel key listenser");
//                return true;
//            }
//        });
//        dialog.show();
//        return dialog;
//    }
//
//    public void onBackPressed() {
//    }
//}
