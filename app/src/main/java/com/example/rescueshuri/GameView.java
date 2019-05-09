package com.example.rescueshuri;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

public class GameView extends View {

    Handler handler;
    Runnable runnable;
    Boolean ismoving=false;
    Bitmap [] walk_array;
    Bitmap background,idle_player,walking0,walking1,walking2,walking3,walking4,walking5;
    Display display;
    Rect rect;
    int speed=10;
    int ManX,ManY;
    int manFrame = 0;
    Point point;
    final int UPDATE_MILLIS=50;

    int dWidth,dHeight; //device width and height
    public GameView(Context context) {
        super(context);
        handler=new Handler();
        runnable=new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        };

//        *********************************************************
        background= BitmapFactory.decodeResource(getResources(),R.drawable.back);
        idle_player=BitmapFactory.decodeResource(getResources(),R.drawable.idle_000);
        idle_player=Bitmap.createScaledBitmap(idle_player, 130, 200, true);
        walking0=BitmapFactory.decodeResource(getResources(),R.drawable.walk_000);
        walking0=Bitmap.createScaledBitmap(walking0, 130, 200, true);
        walking1=BitmapFactory.decodeResource(getResources(),R.drawable.walk_001);
        walking1=Bitmap.createScaledBitmap(walking1, 130, 200, true);
        walking2=BitmapFactory.decodeResource(getResources(),R.drawable.walk_002);
        walking2=Bitmap.createScaledBitmap(walking2, 130, 200, true);
        walking3=BitmapFactory.decodeResource(getResources(),R.drawable.walk_003);
        walking3=Bitmap.createScaledBitmap(walking3, 130, 200, true);
        walking4=BitmapFactory.decodeResource(getResources(),R.drawable.walk_004);
        walking4=Bitmap.createScaledBitmap(walking4, 130, 200, true);
        walking5=BitmapFactory.decodeResource(getResources(),R.drawable.walk_005);
        walking5=Bitmap.createScaledBitmap(walking5, 130, 200, true);

        walk_array=new Bitmap[6];
        walk_array[0]=walking0;
        walk_array[1]=walking1;
        walk_array[2]=walking2;
        walk_array[3]=walking3;
        walk_array[4]=walking4;
        walk_array[5]=walking5;
//        *********************************************************


        display=((Activity)getContext()).getWindowManager().getDefaultDisplay();
        point=new Point();
        display.getSize(point);
        dHeight=point.y;
        dWidth=point.x;
        rect = new Rect(0,0,dWidth,dHeight);
        ManX=0;
        ManY=dHeight/2;

    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(background,null,rect,null);

        if(manFrame==0){
            manFrame=1;
        }
        else if(manFrame==1){
            manFrame=2;
        }
        else if(manFrame==2){
            manFrame=3;
        }
        else if(manFrame==3){
            manFrame=4;
        }
        else if(manFrame==4){
            manFrame=5;
        }
        else{
            manFrame=0;
        }

        if(ismoving){
            ManX=ManX+speed;
            canvas.drawBitmap(walk_array[manFrame],ManX,ManY,null);
            }
        else{
            canvas.drawBitmap(idle_player, ManX, ManY, null);
        }
        handler.postDelayed(runnable,UPDATE_MILLIS);
    }

    public boolean onTouchEvent(MotionEvent event){
        int action = event.getAction();
        if(action==MotionEvent.ACTION_DOWN){
//          ManX=ManX+speed;
            ismoving=!ismoving;
        }
        else if (action==MotionEvent.ACTION_UP){
            ismoving=!ismoving;
        }
        return true;
    }
}
