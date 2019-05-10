package com.example.rescueshuri;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class StartGame extends AppCompatActivity {
    GameView gameView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameView=new GameView(this);
        setContentView(gameView);

    }
    class GameView extends View {

        Handler handler;
        Button jump;

        boolean moveLeft,moveRight,jumping,rightJump,leftJump,shot;
        Runnable runnable;
        Boolean ismoving=false;
//        LinearLayout board1 =(LinearLayout) findViewById(R.id.relativeLayout);
        Bitmap[] walk_array,shot_array;

        Bitmap background1,background2,background3,background4,background5,idle_player,walking0,walking1,walking2,walking3,walking4,walking5,shotting0,shotting1,shotting2,shotting3,shotting4,shotting5,ground,monster;
        Bitmap princess,fire;
        Display display;
        Rect rect;
        int speed=10;
        int bulletcoordX,bulletcoordY;
        boolean isShot=false;
        int firespeed=40;
        int ManX,ManY;
        int level=5;
        int manFrame = 0;
        int gravity=10;
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
            background1= BitmapFactory.decodeResource(getResources(),R.drawable.back);
            background2= BitmapFactory.decodeResource(getResources(),R.drawable.battleback8);
            background3= BitmapFactory.decodeResource(getResources(),R.drawable.flat);
            background4= BitmapFactory.decodeResource(getResources(),R.drawable.cyber);
            background5= BitmapFactory.decodeResource(getResources(),R.drawable.space);
            idle_player=BitmapFactory.decodeResource(getResources(),R.drawable.idle_000);
            idle_player=Bitmap.createScaledBitmap(idle_player, 140, 200, true);
            walking0=BitmapFactory.decodeResource(getResources(),R.drawable.walk_000);
            walking0=Bitmap.createScaledBitmap(walking0, 140, 200, true);
            walking1=BitmapFactory.decodeResource(getResources(),R.drawable.walk_001);
            walking1=Bitmap.createScaledBitmap(walking1, 140, 200, true);
            walking2=BitmapFactory.decodeResource(getResources(),R.drawable.walk_002);
            walking2=Bitmap.createScaledBitmap(walking2, 140, 200, true);
            walking3=BitmapFactory.decodeResource(getResources(),R.drawable.walk_003);
            walking3=Bitmap.createScaledBitmap(walking3, 140, 200, true);
            walking4=BitmapFactory.decodeResource(getResources(),R.drawable.walk_004);
            walking4=Bitmap.createScaledBitmap(walking4, 140, 200, true);
            walking5=BitmapFactory.decodeResource(getResources(),R.drawable.walk_005);
            walking5=Bitmap.createScaledBitmap(walking5, 140, 200, true);
            monster=BitmapFactory.decodeResource(getResources(),R.drawable.mum1);
            monster=Bitmap.createScaledBitmap(monster, 140, 200, true);
            princess=BitmapFactory.decodeResource(getResources(),R.drawable.med);
            princess=Bitmap.createScaledBitmap(princess, 140, 200, true);
            fire=BitmapFactory.decodeResource(getResources(),R.drawable.fireblast1);
            fire=Bitmap.createScaledBitmap(fire, 40, 40, true);
            /////////////////////////////
            shotting0=BitmapFactory.decodeResource(getResources(),R.drawable.shot1_000);
            shotting0=Bitmap.createScaledBitmap(shotting0, 140, 200, true);
            shotting1=BitmapFactory.decodeResource(getResources(),R.drawable.shot1_001);
            shotting1=Bitmap.createScaledBitmap(shotting1, 140, 200, true);
            shotting2=BitmapFactory.decodeResource(getResources(),R.drawable.shot1_002);
            shotting2=Bitmap.createScaledBitmap(shotting2, 140, 200, true);
            shotting3=BitmapFactory.decodeResource(getResources(),R.drawable.shot1_003);
            shotting3=Bitmap.createScaledBitmap(shotting3, 140, 200, true);
            shotting4=BitmapFactory.decodeResource(getResources(),R.drawable.shot1_004);
            shotting4=Bitmap.createScaledBitmap(shotting4, 140, 200, true);
            shotting5=BitmapFactory.decodeResource(getResources(),R.drawable.shot1_005);
            shotting5=Bitmap.createScaledBitmap(shotting5, 140, 200, true);
            ///////////
            ground =BitmapFactory.decodeResource(getResources(),R.drawable.ground0);
            ground=Bitmap.createScaledBitmap(ground, 350, 150, true);

            /////////////////////////////
            walk_array=new Bitmap[6];
            walk_array[0]=walking0;
            walk_array[1]=walking1;
            walk_array[2]=walking2;
            walk_array[3]=walking3;
            walk_array[4]=walking4;
            walk_array[5]=walking5;
            shot_array=new Bitmap[6];
            shot_array[0]=shotting0;
            shot_array[1]=shotting1;
            shot_array[2]=shotting2;
            shot_array[3]=shotting3;
            shot_array[4]=shotting4;
            shot_array[5]=shotting5;
//        *********************************************************


            display=((Activity)getContext()).getWindowManager().getDefaultDisplay();
            point=new Point();
            display.getSize(point);
            jump=new Button(context);
            jump.setText("Jump");
            dHeight=point.y;
            dWidth=point.x;
            rect = new Rect(0,0,dWidth,dHeight);
            ManX=0;
            ManY=dHeight-2*idle_player.getHeight()+idle_player.getHeight()/2+idle_player.getHeight()/4;
//            board1.setOnTouchListener(new OnSwipeTouchListener(gameView.this));
            moveLeft=false;
            moveRight=false;
            jumping=false;
            rightJump=false;
            leftJump=false;

        }
        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

//            canvas.drawBitmap(background1,null,rect,null);
            if(level==1){
                canvas.drawBitmap(background1,null,rect,null);
            }
            else if(level==2){
                canvas.drawBitmap(background2,null,rect,null);
            }
            else if(level==3){
                canvas.drawBitmap(background3,null,rect,null);
            }
            else if(level==4){
                canvas.drawBitmap(background4,null,rect,null);
            }
            else if(level==5){
                canvas.drawBitmap(background5,null,rect,null);

            }
            canvas.drawBitmap(fire,100,200,null);
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
                if(moveLeft)
                    ManX=ManX-speed;
                else if (moveRight)
                    ManX=ManX+speed;

                if(jumping){
                    if(leftJump){
                        ManX=ManX-20;
                    }
                    else if(rightJump){
                        ManX=ManX+20;
                    }
                    ManY=ManY-50;
                }

                if(ManY<dHeight-2*idle_player.getHeight()+idle_player.getHeight()/2+idle_player.getHeight()/4){
                    if((ManX>500||(ManX+walking0.getWidth()>500))&&((ManX<850))){
                        if(ManY+walking0.getHeight()>=500&&ManY+walking0.getHeight()<510){
                            canvas.drawBitmap(walk_array[manFrame], ManX, ManY, null);
                        }
                        else{
                            ManY=ManY+gravity;
                        }
                    }
                    else
                        ManY=ManY+gravity;


                }
                if(!shot) {
                    canvas.drawBitmap(walk_array[manFrame], ManX, ManY, null);
                }
                else {
                    canvas.drawBitmap(shot_array[manFrame], ManX, ManY, null);
                    bulletcoordX=ManX+walking0.getWidth()/2;
                    bulletcoordY=ManY+walking0.getHeight()/2;
                    isShot=true;
                }
            }
            else{
                if(ManY<dHeight-2*idle_player.getHeight()+idle_player.getHeight()/2+idle_player.getHeight()/4){
                    if((ManX>500||(ManX+walking0.getWidth()>500))&&((ManX<850))){
                        if(ManY+walking0.getHeight()>=500&&ManY+walking0.getHeight()<510){
                           canvas.drawBitmap(idle_player, ManX, ManY, null);
                        }
                        else{
                            ManY=ManY+gravity;
                        }
                    }
                    else{
                    ManY=ManY+gravity;}
//                    ManY=ManY+gravity;
                }

                canvas.drawBitmap(idle_player, ManX, ManY, null);
            }


            canvas.drawBitmap(ground,500,500,null);

            if(isShot){
                if(bulletcoordY<dHeight-monster.getHeight()-monster.getHeight()/4 ||bulletcoordY>dHeight-monster.getHeight()/4){
                    canvas.drawBitmap(monster, dWidth - monster.getWidth(), dHeight - monster.getHeight() - monster.getHeight() / 4, null);
                    canvas.drawBitmap(fire,bulletcoordX,bulletcoordY,null);
                    bulletcoordX=firespeed+bulletcoordX;

                }
                else{
                    if(bulletcoordX+fire.getWidth()<dWidth-monster.getWidth()) {
                        canvas.drawBitmap(monster, dWidth - monster.getWidth(), dHeight - monster.getHeight() - monster.getHeight() / 4, null);
                        canvas.drawBitmap(fire,bulletcoordX,bulletcoordY,null);
                        bulletcoordX=firespeed+bulletcoordX;
                    }
                }

            }else{
                canvas.drawBitmap(monster, dWidth - monster.getWidth(), dHeight - monster.getHeight() - monster.getHeight() / 4, null);

            }



            //canvas.drawBitmap(princess,dWidth-200,dHeight-princess.getHeight()-princess.getHeight()/4,null);
            handler.postDelayed(runnable,UPDATE_MILLIS);
        }

        public boolean onTouchEvent(MotionEvent event){
            int action = event.getAction();
            int x = (int)event.getX();
            int y = (int)event.getY();
            if(action==MotionEvent.ACTION_DOWN){
//          ManX=ManX+speed;
                if(y>dHeight*0.60){
                    if(x<dWidth/4){
                        moveLeft=true;
                    }
                    else if(x>dWidth*0.75){
                        moveRight=true;
                    }
                    else{
                        shot=true;
                    }

                }
                else{
                    if(x<dWidth/4){
                        leftJump=true;
                    }
                    else if(x>dWidth*0.75){
                        rightJump=true;
                    }
                    jumping=true;
                }
                ismoving=!ismoving;
            }
            else if (action==MotionEvent.ACTION_UP){
                ismoving=!ismoving;
                moveRight=false;
                moveLeft=false;
                jumping=false;
                leftJump=false;
                rightJump=false;
                shot=false;
            }
            return true;
        }
    }

}
