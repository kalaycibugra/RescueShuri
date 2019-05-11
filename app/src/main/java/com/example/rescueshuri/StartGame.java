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

import java.util.Random;

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
        Bitmap[] walk_array,shot_array,level_array;
        boolean isMonsterDead=false;
        Bitmap background1,background2,background3,background4,background5,idle_player,walking0,walking1,walking2,walking3,walking4,walking5,shotting0,shotting1,shotting2,shotting3,shotting4,shotting5,ground,ground1,monster;
        Bitmap princess,fire,block,gate,levels1,levels2,levels3,levels4,levels5,boss,spike,enemy,killer;
        Display display;
        Rect rect;
        int speed=10;
        int bulletcoordX,bulletcoordY;
        boolean isShot=false;
        int firespeed=40;
        int ManX,ManY;
        int level=1;
        Random rand = new Random();
        int groundX,groundY,ground1X,ground1Y;
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
            boss=BitmapFactory.decodeResource(getResources(),R.drawable.boss);
            boss=Bitmap.createScaledBitmap(boss,300,400,true);
            killer=BitmapFactory.decodeResource(getResources(),R.drawable.eme);
            killer=Bitmap.createScaledBitmap(killer,300,400,true);

            spike=BitmapFactory.decodeResource(getResources(),R.drawable.spike);
            spike=Bitmap.createScaledBitmap(spike,300,400,true);
            enemy=BitmapFactory.decodeResource(getResources(),R.drawable.enemy);
            enemy=Bitmap.createScaledBitmap(enemy,300,400,true);
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
            block=BitmapFactory.decodeResource(getResources(),R.drawable.ground2);
            block=Bitmap.createScaledBitmap(block, 60, 60, true);
            gate=BitmapFactory.decodeResource(getResources(),R.drawable.gate);
            gate=Bitmap.createScaledBitmap(gate, 200, 100, true);
            level_array=new Bitmap[5];

            levels1=BitmapFactory.decodeResource(getResources(),R.drawable.level1);
            levels1=Bitmap.createScaledBitmap(levels1, 200, 100, true);
            level_array[0]=levels1;
            levels2=BitmapFactory.decodeResource(getResources(),R.drawable.level2);
            levels2=Bitmap.createScaledBitmap(levels2, 200, 100, true);
            level_array[1]=levels2;
            levels3=BitmapFactory.decodeResource(getResources(),R.drawable.level3);
            levels3=Bitmap.createScaledBitmap(levels3, 200, 100, true);
            level_array[2]=levels3;
            levels4=BitmapFactory.decodeResource(getResources(),R.drawable.level4);
            levels4=Bitmap.createScaledBitmap(levels4, 200, 100, true);
            level_array[3]=levels4;
            levels5=BitmapFactory.decodeResource(getResources(),R.drawable.level5);
            levels5=Bitmap.createScaledBitmap(levels5, 200, 100, true);
            level_array[4]=levels5;


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
            ground=BitmapFactory.decodeResource(getResources(),R.drawable.ground0);
            ground=Bitmap.createScaledBitmap(ground, 350, 150, true);
            ground1=BitmapFactory.decodeResource(getResources(),R.drawable.ground0);
            ground1=Bitmap.createScaledBitmap(ground, 350, 150, true);




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
//            new Random().nextInt(5 + 1)  + 10 // [0...5]  + 10 = [10...15]
            groundX=new Random().nextInt(dWidth/2+1)+300;
            groundY=new Random().nextInt(dHeight/2+1)+300;
        }
        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

//            canvas.drawBitmap(background1,null,rect,null);

            if(level==1){
                canvas.drawBitmap(background1,null,rect,null);
//                canvas.drawBitmap(levels1,10,10,null);
                canvas.drawBitmap(gate,dWidth-150,dHeight-400,null);
            }
            else if(level==2){
                canvas.drawBitmap(background2,null,rect,null);
                canvas.drawBitmap(gate,dWidth-150,dHeight-400,null);
                monster=enemy;

            }
            else if(level==3){
                canvas.drawBitmap(background3,null,rect,null);
                canvas.drawBitmap(gate,dWidth-150,dHeight-400,null);
                monster=killer;
            }
            else if(level==4){
                canvas.drawBitmap(background4,null,rect,null);
                canvas.drawBitmap(gate,dWidth-150,dHeight-400,null);
                monster=spike;

            }
            else if(level==5){
                canvas.drawBitmap(background5,null,rect,null);
                monster=boss;

            }
            canvas.drawBitmap(level_array[level-1],10,10,null);
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
                if(moveLeft) {
                    if (ManX > 10)
                        ManX = ManX - speed;
                }
                else if (moveRight) {
                    if (ManX < dWidth)
                        ManX = ManX + speed;
                }
                if(jumping){
                    if(leftJump){
                        if(ManX>10&&ManY>10)
                        ManX=ManX-20;
                    }
                    else if(rightJump){
                        if(ManX<dWidth&&ManY>10)
                        ManX=ManX+20;
                    }
                    ManY=ManY-50;
                }

                if(ManY<dHeight-2*idle_player.getHeight()+idle_player.getHeight()/2+idle_player.getHeight()/4){
                    if((ManX>groundX||(ManX+walking0.getWidth()>groundX))&&((ManX<groundX+ground.getWidth()))){
                        if(ManY+walking0.getHeight()>=groundY&&ManY+walking0.getHeight()<groundY+10){
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

                    if(!isShot){
                        bulletcoordX=ManX+walking0.getWidth()/2;
                        bulletcoordY=ManY+walking0.getHeight()/2;
                        isShot=true;
                    }
                }
            }
            else{
                if(ManY<dHeight-2*idle_player.getHeight()+idle_player.getHeight()/2+idle_player.getHeight()/4){
                    if((ManX>groundX||(ManX+walking0.getWidth()>groundX))&&((ManX<groundX+ground.getWidth()))){
                        if(ManY+walking0.getHeight()>=groundY&&ManY+walking0.getHeight()<groundY+10){
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


            canvas.drawBitmap(ground,groundX,groundY,null);

            if(isShot){
                if(!isMonsterDead){
                    if(bulletcoordX>dWidth){
                        isShot=false;
                    }
                    if(bulletcoordY<dHeight-monster.getHeight()-monster.getHeight()/4 ||bulletcoordY>dHeight-monster.getHeight()/4){
                        canvas.drawBitmap(monster, dWidth - monster.getWidth()-300, dHeight - monster.getHeight() - monster.getHeight() / 4, null);
                        canvas.drawBitmap(fire,bulletcoordX,bulletcoordY,null);
                        bulletcoordX=firespeed+bulletcoordX;

                    }
                    else{
                        if(bulletcoordX+fire.getWidth()<dWidth-monster.getWidth()) {
                            canvas.drawBitmap(monster, dWidth - monster.getWidth()-300, dHeight - monster.getHeight() - monster.getHeight() / 4, null);
                            canvas.drawBitmap(fire,bulletcoordX,bulletcoordY,null);
                            bulletcoordX=firespeed+bulletcoordX;
                        }
                        else{
                            isMonsterDead=true;
                            isShot=!isShot;
                        }
                    }
                }
                else{
                    if(bulletcoordX>dWidth){
                        isShot=false;

                    }
                    else{
                        canvas.drawBitmap(fire,bulletcoordX,bulletcoordY,null);
                        bulletcoordX=firespeed+bulletcoordX;

                    }
                }
            }else{
                if(!isMonsterDead)
                    canvas.drawBitmap(monster, dWidth - monster.getWidth()-300, dHeight - monster.getHeight() - monster.getHeight() / 4, null);

            }
            if(ManX+walking0.getWidth()/2>dWidth-15&&ManY>dHeight-2*idle_player.getHeight()+idle_player.getHeight()/2+idle_player.getHeight()/4-10){
                if(level!=5){
                level=level+1;
                ManX=0;
                ManY=dHeight-2*idle_player.getHeight()+idle_player.getHeight()/2+idle_player.getHeight()/4;
                isMonsterDead=false;
                    groundX=new Random().nextInt(dWidth/2+1)+300;
                    groundY=new Random().nextInt(dHeight/2+1)+300;}
            }
            if(level==5){
                canvas.drawBitmap(block,dWidth-150-block.getWidth(),dHeight-2*block.getHeight(),null);
                canvas.drawBitmap(block,dWidth-150-block.getWidth(),dHeight-3*block.getHeight(),null);
                canvas.drawBitmap(block,dWidth-150-block.getWidth(),dHeight-4*block.getHeight(),null);
                canvas.drawBitmap(block,dWidth-150-block.getWidth(),dHeight-5*block.getHeight(),null);
                canvas.drawBitmap(block,dWidth-150-block.getWidth(),dHeight-6*block.getHeight(),null);
                canvas.drawBitmap(block,dWidth-150-block.getWidth()+block.getWidth(),dHeight-6*block.getHeight(),null);
                canvas.drawBitmap(block,dWidth-150-block.getWidth()+2*block.getWidth(),dHeight-6*block.getHeight(),null);
                canvas.drawBitmap(block,dWidth-150-block.getWidth()+3*block.getWidth(),dHeight-6*block.getHeight(),null);


//                canvas.drawBitmap(block,dWidth-150-block.getWidth(),dHeight-2*block.getHeight()+block.getHeight()/2+block.getHeight()/4-block.getHeight(),null);

                canvas.drawBitmap(princess,dWidth-150,dHeight-2*princess.getHeight()+princess.getHeight()/2+princess.getHeight()/4,null);

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
