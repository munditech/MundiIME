package tk.munditv.mundiime.advistor;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import tk.munditv.videoplayer.NiceVideoPlayer;
import tk.munditv.videoplayer.NiceVideoPlayerManager;
import tk.munditv.videoplayer.TxVideoPlayerController;

import tk.munditv.mundiime.R;

public class AdvistorWindow {

    private final static String TAG = "AdvistorWindow";

    private final static int ADVISTOR_WINDOW_X = -800;
    private final static int ADVISTOR_WINDOW_Y = 200;
    private final static int ADVISTOR_WINDOW_WIDTH = 410;
    private final static int ADVISTOR_WINDOW_HEIGHT = 512;
    private final static int ADVISTOR_WINDOW_GRAVITY = Gravity.TOP;
    private final static int ADVISTOR_WINDOW_FORNAT = PixelFormat.TRANSLUCENT;
    private final static int ADVISTOR_WINDOW_TYPE = WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY;

    private final static int TEXTBOX_WINDOW_X = -180;
    private final static int TEXTBOX_WINDOW_Y = 260;
    private final static int TEXTBOX_WINDOW_WIDTH = 918;
    private final static int TEXTBOX_WINDOW_HEIGHT = 604;
    private final static int TEXTBOX_WINDOW_GRAVITY = Gravity.TOP;
    private final static int TEXTBOX_WINDOW_FORNAT = PixelFormat.TRANSLUCENT;
    private final static int TEXTBOX_WINDOW_TYPE = WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY;

    private final static int VIDEO_WINDOW_X = 0;
    private final static int VIDEO_WINDOW_Y = 200;
    private final static int VIDEO_WINDOW_WIDTH = 960;
    private final static int VIDEO_WINDOW_HEIGHT = 540;
    private final static int VIDEO_WINDOW_GRAVITY = Gravity.TOP;
    private final static int VIDEO_WINDOW_FORNAT = PixelFormat.OPAQUE;
    private final static int VIDEO_WINDOW_TYPE = WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY;

    private Context                     mContext;
    private Context                     mAContext;
    private WindowManager               mWindowManager = null;
    private WindowManager.LayoutParams  mParams = null;
    private WindowManager.LayoutParams  mParams2 = null;
    private WindowManager.LayoutParams  mParams3 = null;
    private FrameLayout                 mADVISTORFrameLayout = null;
    private FrameLayout                 mTEXTBOXFrameLayout = null;
    private FrameLayout                 mVideoFrameLayout = null;
    private LayoutInflater              mLayoutinflater = null;
    private View                        mADVISTORView = null;
    private View                        mTEXTBOXView = null;
    private View                        mVideoView = null;
    private TextView                    mTextView = null;
    private ImageView                   mImageView = null;
    private NiceVideoPlayer             mVideoPlayer = null;
    private int                         steps = 0;
    private Handler                     mHandler = null;

    private int index = 1;

    public AdvistorWindow(Context acontext, Context context) {
        Log.d(TAG,"Constructor()");
        mContext = context;
        mAContext = acontext;
        mWindowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        mLayoutinflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        initialize();
    }

    private void initialize() {
        Log.d(TAG,"initialize()");
        initImage();
        initTextBox();
        //initVideo();
        switchContext();
        return;
    }

    private void initImage() {
        Log.d(TAG, "initImage");
        try {
            mADVISTORView = mLayoutinflater.inflate(R.layout.advistor,null);
            mADVISTORFrameLayout = new FrameLayout(mContext);
            mParams = new WindowManager.LayoutParams();
            mParams.width = ADVISTOR_WINDOW_WIDTH;
            mParams.height = ADVISTOR_WINDOW_HEIGHT;
            mParams.gravity = ADVISTOR_WINDOW_GRAVITY;
            mParams.x = ADVISTOR_WINDOW_X;
            mParams.y = ADVISTOR_WINDOW_Y;
            mParams.format = ADVISTOR_WINDOW_FORNAT;
            mParams.type = ADVISTOR_WINDOW_TYPE;
            mParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                    WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL |
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
            mParams.token = null;
            mADVISTORFrameLayout.setLayoutParams(mParams);
            mADVISTORFrameLayout.addView(mADVISTORView);
            mWindowManager.addView(mADVISTORFrameLayout, mParams);
            mImageView = (ImageView) mADVISTORView.findViewById(R.id.image);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return;
    }

    private void initTextBox() {
        Log.d(TAG, "initTextBox");
        try {
            mTEXTBOXView = mLayoutinflater.inflate(R.layout.textbox, null);
            mTEXTBOXFrameLayout = new FrameLayout(mContext);
            mParams2 = new WindowManager.LayoutParams();
            mParams2.width = TEXTBOX_WINDOW_WIDTH;
            mParams2.height = TEXTBOX_WINDOW_HEIGHT;
            mParams2.gravity = TEXTBOX_WINDOW_GRAVITY;
            mParams2.x = TEXTBOX_WINDOW_X;
            mParams2.y = TEXTBOX_WINDOW_Y;
            mParams2.format = TEXTBOX_WINDOW_FORNAT;
            mParams2.type = TEXTBOX_WINDOW_TYPE;
            mParams2.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                    WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL |
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
            mParams2.token = null;
            mTextView = (TextView) mTEXTBOXView.findViewById(R.id.textView);
            mTextView.setBackgroundResource(R.drawable.dialog);
            mTextView.setTextColor(Color.BLUE);
            mTextView.setText(null);
            mTEXTBOXFrameLayout.setLayoutParams(mParams2);
            mTEXTBOXFrameLayout.addView(mTEXTBOXView);
            mWindowManager.addView(mTEXTBOXFrameLayout, mParams2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return;
    }

    private void initVideo() {
        Log.d(TAG, "initVideo");
        try {
            mVideoFrameLayout = new FrameLayout(mContext);
            mVideoView = mLayoutinflater.inflate(R.layout.video, null);
            mParams3 = new WindowManager.LayoutParams();
            mParams3.width = VIDEO_WINDOW_WIDTH;
            mParams3.height = VIDEO_WINDOW_HEIGHT;
            mParams3.gravity = VIDEO_WINDOW_GRAVITY;
            mParams3.x = VIDEO_WINDOW_X;
            mParams3.y = VIDEO_WINDOW_Y;
            mParams3.format = VIDEO_WINDOW_FORNAT;
            mParams3.type = VIDEO_WINDOW_TYPE;
            mParams3.token = null;
            mVideoFrameLayout.setLayoutParams(mParams3);
            mVideoFrameLayout.addView(mVideoView);
            mWindowManager.addView(mVideoFrameLayout, mParams3);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startVideo() {
        initVideo();
        mVideoView.setVisibility(View.VISIBLE);
        mVideoPlayer = mVideoView.findViewById(R.id.nice_video_player);
        mVideoPlayer.setPlayerType(NiceVideoPlayer.TYPE_IJK);
        String videourl = mContext.getString(R.string.video_url);
        mVideoPlayer.setUp(videourl,null);
        TxVideoPlayerController controller = new TxVideoPlayerController(mContext);
        mVideoPlayer.setController(controller);
        mVideoPlayer.setEnabled(true);
        mVideoPlayer.start();

    }

    public void stopVideo() {
        Log.d(TAG, "stopVideo");
        if (mVideoFrameLayout != null) {
            NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
            mVideoView.setVisibility(View.GONE);
            mVideoFrameLayout.removeAllViews();
            mWindowManager.removeViewImmediate(mVideoFrameLayout);
            mVideoView = null;
            mVideoFrameLayout = null;
        }
    }

    public void Destroy() {
        Log.d(TAG,"Destroy()");
        try {
            mADVISTORFrameLayout.removeAllViews();
            mTEXTBOXFrameLayout.removeAllViews();
            mADVISTORView = null;
            mADVISTORFrameLayout = null;
            mLayoutinflater = null;
            mWindowManager = null;
            mContext = null;
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(mContext,"Destroy error!",Toast.LENGTH_SHORT);
        }
        return;
    }

    public void switchContext() {
        steps = steps % 7;
        Log.d(TAG, "switchContext() steps = " + steps);
        switch(steps) {
            case 0 :
                Animation animFadeOut = AnimationUtils
                        .loadAnimation(mAContext,R.anim.fade_out);
                mImageView.setAnimation(animFadeOut);
                mTextView.setAnimation(animFadeOut);
                mImageView.setVisibility(View.GONE);
                mTextView.setVisibility(View.GONE);
                break;
            case 1 :
                Animation animFadeIn = AnimationUtils
                        .loadAnimation(mAContext,R.anim.fade_in);
                mImageView.setAnimation(animFadeIn);
                mTextView.setAnimation(animFadeIn);
                mImageView.setVisibility(View.VISIBLE);
                mTextView.setVisibility(View.VISIBLE);
                //mTextView.setText(R.string.talk_content_1);
                mTextView.setBackgroundResource(R.drawable.dialog1);
                break;
            case 2 :
                mImageView.setVisibility(View.VISIBLE);
                mTextView.setVisibility(View.VISIBLE);
                //mTextView.setText(R.string.talk_content_2);
                mTextView.setBackgroundResource(R.drawable.dialog2);
                break;
            case 3 :
                mImageView.setVisibility(View.VISIBLE);
                mTextView.setVisibility(View.VISIBLE);
                //mTextView.setText(R.string.talk_content_3);
                mTextView.setBackgroundResource(R.drawable.dialog3);
                break;
            case 4 :
                mImageView.setVisibility(View.VISIBLE);
                mTextView.setVisibility(View.VISIBLE);
                //mTextView.setText(R.string.talk_content_4);
                mTextView.setBackgroundResource(R.drawable.dialog4);
                if(mHandler == null) {
                    mHandler = new Handler();
                }
                mHandler.postDelayed(searchResult, 1000);
                return;
            case 5 :
                mImageView.setVisibility(View.VISIBLE);
                mTextView.setVisibility(View.VISIBLE);
                //mTextView.setText(R.string.talk_content_5);
                mTextView.setBackgroundResource(R.drawable.dialog5);
                if(mHandler == null) {
                    mHandler = new Handler();
                }
                mHandler.postDelayed(playvideo, 10000);
                break;
            case 6 :
                if(mVideoFrameLayout != null) {
                    mHandler.removeCallbacks(checkvideo);
                    stopVideo();
                } else {
                    mHandler.removeCallbacks(playvideo);
                }
                mImageView.setVisibility(View.VISIBLE);
                mTextView.setVisibility(View.VISIBLE);
                //mTextView.setText(R.string.talk_content_6);
                mTextView.setBackgroundResource(R.drawable.dialog6);
                if(mHandler == null) {
                    mHandler = new Handler();
                }
                mHandler.postDelayed(hideall, 3000);
                return;
        }
        steps++;
    }

    private Runnable playvideo = new Runnable() {

        @Override
        public void run() {
            startVideo();
            mHandler.removeCallbacks(playvideo);
            mHandler.postDelayed(checkvideo, 1000);
        }
    };

    private Runnable checkvideo = new Runnable() {

        @Override
        public void run() {
            if (mVideoPlayer.isCompleted()) {
                stopVideo();
                switchContext();
                mHandler.removeCallbacks(checkvideo);
                return;
            }
            mHandler.removeCallbacks(checkvideo);
            mHandler.postDelayed(checkvideo,500);
        }
    };

    private Runnable hideall = new Runnable() {

        @Override
        public void run() {
            steps=0;
            switchContext();
            mHandler.removeCallbacks(hideall);
        }
    };

    private Runnable searchResult = new Runnable() {
        @Override
        public void run() {
            steps = 5;
            switchContext();
            mHandler.removeCallbacks(searchResult);
        }
    };

}
