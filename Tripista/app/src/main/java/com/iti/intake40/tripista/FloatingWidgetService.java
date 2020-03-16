package com.iti.intake40.tripista;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FloatingWidgetService extends Service  {
    private WindowManager mWindowManager;
    private View mChatHeadView,mRemoveChatHeader;
    private  ImageView remove_image_view;
    private Point szWindow = new Point();
    private int x_init_cord, y_init_cord, x_init_margin, y_init_margin;
    private boolean isLeft = true;
    private View icon,expand;
    ArrayList<String> notes = new ArrayList<>();
    RecyclerView recyclerView;
    //RecycleAdapter adapter;
    private Context context;

    public FloatingWidgetService() {
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        //init WindowManager
        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        getWindowManagerDefaultDisplay();

        //Init LayoutInflater
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

        addRemoveView(inflater);
        addFloatingWidgetView(inflater);
        implementTouchListenerToFloatingWidgetView();
    }


    /*  Add Remove View to Window Manager  */
    private View addRemoveView(LayoutInflater inflater) {
        //Inflate the removing view layout we created
        mRemoveChatHeader = inflater.inflate(R.layout.close_layout, null);

        //Add the view to the window.
        WindowManager.LayoutParams paramRemove = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                PixelFormat.TRANSLUCENT);

        //Specify the view position
        paramRemove.gravity = Gravity.TOP | Gravity.LEFT;

        //Initially the Removing widget view is not visible, so set visibility to GONE
        mRemoveChatHeader.setVisibility(View.GONE);
        remove_image_view = mRemoveChatHeader.findViewById(R.id.remove_image);

        //Add the view to the window
        mWindowManager.addView(mRemoveChatHeader, paramRemove);
        return remove_image_view;
    }

    /*  Add Floating Widget View to Window Manager  */
    private void addFloatingWidgetView(LayoutInflater inflater) {
        //Inflate the floating view layout we created
        mChatHeadView = inflater.inflate(R.layout.head_chat, null);
        //Add the view to the window.
        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        //Specify the view position
        params.gravity = Gravity.TOP | Gravity.LEFT;

        //Initially view will be added to top-left corner, you change x-y coordinates according to your need
        params.x = 0;
        params.y = 100;

        //Add the view to the window
        mWindowManager.addView(mChatHeadView, params);

    }

    private void getWindowManagerDefaultDisplay() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2)
            mWindowManager.getDefaultDisplay().getSize(szWindow);
        else {
            int w = mWindowManager.getDefaultDisplay().getWidth();
            int h = mWindowManager.getDefaultDisplay().getHeight();
            szWindow.set(w, h);
        }
    }

    /*  Implement Touch Listener to Floating Widget Root View  */
    private void implementTouchListenerToFloatingWidgetView() {
        //Drag and move floating view using user's touch action.
        icon = mChatHeadView.findViewById(R.id.layoutCollapsed);
        expand = mChatHeadView.findViewById(R.id.layoutExpanded);
        recyclerView = mChatHeadView.findViewById(R.id.recycle);
        List<String> list = new ArrayList<>();
        list.add("bobos");
        list.add("saeed");
        list.add("bobos");
        list.add("saeed");
        list.add("bobos");
        list.add("saeed");
       // adapter =new RecycleAdapter(list,this);
       // recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));




        mChatHeadView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("clicked know !!!");

                expand.setVisibility(View.VISIBLE);
                icon.setVisibility(View.GONE);
            }
        });
        icon.setOnTouchListener(new View.OnTouchListener() {

            long time_start = 0, time_end = 0;

            boolean isLongClick = false;//variable to judge if user click long press
            boolean inBounded = false;//variable to judge if floating view is bounded to remove view
            int remove_img_width = 0, remove_img_height = 0;

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                //Get Floating widget view params
                WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) mChatHeadView.getLayoutParams();

                //get the touch location coordinates
                int x_cord = (int) event.getRawX();
                int y_cord = (int) event.getRawY();

                int x_cord_Destination, y_cord_Destination;

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        remove_img_width = remove_image_view.getLayoutParams().width;
                        remove_img_height = remove_image_view.getLayoutParams().height;

                        x_init_cord = x_cord;
                        y_init_cord = y_cord;

                        //remember the initial position.
                        x_init_margin = layoutParams.x;
                        y_init_margin = layoutParams.y;

                        return false;
                    case MotionEvent.ACTION_UP:
                        isLongClick = false;
                        mRemoveChatHeader.setVisibility(View.GONE);
                        remove_image_view.getLayoutParams().height = remove_img_height;
                        remove_image_view.getLayoutParams().width = remove_img_width;
                        //If user drag and drop the floating widget view into remove view then stop the service
                        if (inBounded) {
                            stopSelf();
                            inBounded = false;
                            break;
                        }


                        //Get the difference between initial coordinate and current coordinate
                        int x_diff = x_cord - x_init_cord;
                        int y_diff = y_cord - y_init_cord;

                        //The check for x_diff <5 && y_diff< 5 because sometime elements moves a little while clicking.
                        //So that is click event.
                        if (Math.abs(x_diff) < 5 && Math.abs(y_diff) < 5) {
                            time_end = System.currentTimeMillis();


                        }

                        y_cord_Destination = y_init_margin + y_diff;

                        int barHeight = getStatusBarHeight();
                        if (y_cord_Destination < 0) {
                            y_cord_Destination = 0;
                        } else if (y_cord_Destination + (mChatHeadView.getHeight() + barHeight) > szWindow.y) {
                            y_cord_Destination = szWindow.y - (mChatHeadView.getHeight() + barHeight);
                        }

                        layoutParams.y = y_cord_Destination;

                        inBounded = false;

                        //reset position if user drags the floating view
                        resetPosition(x_cord);

                        return true;
                    case MotionEvent.ACTION_MOVE:
                        isLongClick = true;
                        onFloatingWidgetLongClick();
                        mRemoveChatHeader.setVisibility(View.VISIBLE);
                        int x_diff_move = x_cord - x_init_cord;
                        int y_diff_move = y_cord - y_init_cord;

                        x_cord_Destination = x_init_margin + x_diff_move;
                        y_cord_Destination = y_init_margin + y_diff_move;

                        //If user long click the floating view, update remove view
                        if (isLongClick) {
                            int x_bound_left = szWindow.x / 2 - (int) (remove_img_width * 1.5);
                            int x_bound_right = szWindow.x / 2 + (int) (remove_img_width * 1.5);
                            int y_bound_top = szWindow.y - (int) (remove_img_height * 1.5);

                            //If Floating view comes under Remove View update Window Manager
                            if ((x_cord >= x_bound_left && x_cord <= x_bound_right) && y_cord >= y_bound_top) {
                                inBounded = true;

                                int x_cord_remove = (int) ((szWindow.x - (remove_img_height * 1.5)) / 2);
                                int y_cord_remove = (int) (szWindow.y - ((remove_img_width * 1.5) + getStatusBarHeight()));

                                if (remove_image_view.getLayoutParams().height == remove_img_height) {
                                    remove_image_view.getLayoutParams().height = (int) (remove_img_height * 1.5);
                                    remove_image_view.getLayoutParams().width = (int) (remove_img_width * 1.5);

                                    WindowManager.LayoutParams param_remove = (WindowManager.LayoutParams) mRemoveChatHeader.getLayoutParams();
                                    param_remove.x = x_cord_remove;
                                    param_remove.y = y_cord_remove;

                                    mWindowManager.updateViewLayout(mRemoveChatHeader, param_remove);
                                }

                                layoutParams.x = x_cord_remove + (Math.abs(mRemoveChatHeader.getWidth() - mChatHeadView.getWidth())) / 2;
                                layoutParams.y = y_cord_remove + (Math.abs(mRemoveChatHeader.getHeight() - mChatHeadView.getHeight())) / 2;

                                //Update the layout with new X & Y coordinate
                                mWindowManager.updateViewLayout(mChatHeadView, layoutParams);
                                break;
                            } else {
                                //If Floating window gets out of the Remove view update Remove view again
                                inBounded = false;
                                remove_image_view.getLayoutParams().height = remove_img_height;
                                remove_image_view.getLayoutParams().width = remove_img_width;
                            }

                        }


                        layoutParams.x = x_cord_Destination;
                        layoutParams.y = y_cord_Destination;

                        //Update the layout with new X & Y coordinate
                        mWindowManager.updateViewLayout(mChatHeadView, layoutParams);
                        return false;
                }
                return false;
            }
        });
    }



    /*  on Floating Widget Long Click, increase the size of remove view as it look like taking focus */
    private void onFloatingWidgetLongClick() {
        //Get remove Floating view params
        WindowManager.LayoutParams removeParams = (WindowManager.LayoutParams) mRemoveChatHeader.getLayoutParams();

        //get x and y coordinates of remove view
        int x_cord = (szWindow.x - mRemoveChatHeader.getWidth()) / 2;
        int y_cord = szWindow.y - (mRemoveChatHeader.getHeight() + getStatusBarHeight());


        removeParams.x = x_cord;
        removeParams.y = y_cord;

        //Update Remove view params
        mWindowManager.updateViewLayout(mRemoveChatHeader, removeParams);
    }

    /*  Reset position of Floating Widget view on dragging  */
    private void resetPosition(int x_cord_now) {
        if (x_cord_now <= szWindow.x / 2) {
            isLeft = true;
            moveToLeft(x_cord_now);
        } else {
            isLeft = false;
            moveToRight(x_cord_now);
        }

    }


    /*  Method to move the Floating widget view to Left  */
    private void moveToLeft(final int current_x_cord) {
        final int x = szWindow.x - current_x_cord;

        new CountDownTimer(500, 5) {
            //get params of Floating Widget view
            WindowManager.LayoutParams mParams = (WindowManager.LayoutParams) mChatHeadView.getLayoutParams();

            public void onTick(long t) {
                long step = (500 - t) / 5;

                mParams.x = 0 - (int) (current_x_cord * current_x_cord * step);

                mWindowManager.updateViewLayout(mChatHeadView, mParams);
            }

            public void onFinish() {
                mParams.x = 0;

                //Update window manager for Floating Widget
                mWindowManager.updateViewLayout(mChatHeadView, mParams);
            }
        }.start();
    }

    /*  Method to move the Floating widget view to Right  */
    private void moveToRight(final int current_x_cord) {

        new CountDownTimer(500, 5) {
            //get params of Floating Widget view
            WindowManager.LayoutParams mParams = (WindowManager.LayoutParams) mChatHeadView.getLayoutParams();

            public void onTick(long t) {
                long step = (500 - t) / 5;

                mParams.x = (int) (szWindow.x + (current_x_cord * current_x_cord * step) - mChatHeadView.getWidth());
                mWindowManager.updateViewLayout(mChatHeadView, mParams);
            }

            public void onFinish() {
                mParams.x = szWindow.x - mChatHeadView.getWidth();

                //Update window manager for Floating Widget
                mWindowManager.updateViewLayout(mChatHeadView, mParams);
            }
        }.start();
    }



    /*  return status bar height on basis of device display metrics  */
    private int getStatusBarHeight() {
        return (int) Math.ceil(25 * getApplicationContext().getResources().getDisplayMetrics().density);
    }


    /*  Update Floating Widget view coordinates on Configuration change  */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        getWindowManagerDefaultDisplay();

        WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) mChatHeadView.getLayoutParams();

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {


            if (layoutParams.y + (mChatHeadView.getHeight() + getStatusBarHeight()) > szWindow.y) {
                layoutParams.y = szWindow.y - (mChatHeadView.getHeight() + getStatusBarHeight());
                mWindowManager.updateViewLayout(mChatHeadView, layoutParams);
            }

            if (layoutParams.x != 0 && layoutParams.x < szWindow.x) {
                resetPosition(szWindow.x);
            }

        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {

            if (layoutParams.x > szWindow.x) {
                resetPosition(szWindow.x);
            }

        }

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mChatHeadView != null)
            mWindowManager.removeView(mChatHeadView);

        if (mRemoveChatHeader != null)
            mWindowManager.removeView(mRemoveChatHeader);

    }


}
