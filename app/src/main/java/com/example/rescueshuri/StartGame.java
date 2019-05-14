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
import java.util.concurrent.TimeUnit;

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
        int rocketspeed=25;
        int rocketX,rocketY;
        boolean moveLeft,moveRight,jumping,rightJump,leftJump,shot,is_level_swap;
        Runnable runnable;
        Boolean ismoving=false;
//        LinearLayout board1 =(LinearLayout) findViewById(R.id.relativeLayout);
        Bitmap[] walk_array,shot_array,level_array;
        boolean isMonsterDead=false;
        Bitmap rocket,first_mons,back_over,finish_game,background1,background2,background3,background4,background5,idle_player,walking0,walking1,
                walking2,walking3,walking4,walking5,shotting0,shotting1,shotting2,shotting3,shotting4,shotting5,ground,ground1,monster;
        Bitmap princess,fire,enemy_fire,block,gate,levels1,levels2,levels3,levels4,levels5,boss,spike,enemy,killer,coin1,coin2,coin3,heart;
        Display display;
        Rect rect;
        int speed=10;
        int bulletcoordX,bulletcoordY,enemybulletX,enemybulletY,coin1_x,coin1_y,coin2_x,coin2_y,coin3_x,coin3_y;
        boolean isShot=false;
        boolean isHit=false;
        int firespeed=60;
        int ManX,ManY;
        int life=3;
        boolean level5_wall;
        int level=1;
        int monster_x,monster_y;
        boolean is_coin1,is_coin2,is_coin3;
        int monster_life=50;
        Random rand = new Random();
        int groundX,groundY,ground1X,ground1Y;
        int manFrame = 0;
        int gravity=10;
        int coin_count=0;
        boolean game_over=false;
        boolean game_finish=false;
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
            finish_game=BitmapFactory.decodeResource(getResources(),R.drawable.finish);
            background1= BitmapFactory.decodeResource(getResources(),R.drawable.back);
            background2= BitmapFactory.decodeResource(getResources(),R.drawable.battleback8);
            background3= BitmapFactory.decodeResource(getResources(),R.drawable.flat);
            background4= BitmapFactory.decodeResource(getResources(),R.drawable.cyber);
            background5= BitmapFactory.decodeResource(getResources(),R.drawable.space);
            back_over=BitmapFactory.decodeResource(getResources(),R.drawable.game_over);
            idle_player=BitmapFactory.decodeResource(getResources(),R.drawable.idle_000);

            idle_player=Bitmap.createScaledBitmap(idle_player, 140, 200, true);
            boss=BitmapFactory.decodeResource(getResources(),R.drawable.boss);
            boss=Bitmap.createScaledBitmap(boss,300,400,true);
            killer=BitmapFactory.decodeResource(getResources(),R.drawable.eme);
            killer=Bitmap.createScaledBitmap(killer,300,400,true);
            coin1=BitmapFactory.decodeResource(getResources(),R.drawable.coin_gold);
            coin1=Bitmap.createScaledBitmap(coin1,70,70,true);
            coin2=BitmapFactory.decodeResource(getResources(),R.drawable.coin_gold);
            coin2=Bitmap.createScaledBitmap(coin2,70,70,true);
            coin3=BitmapFactory.decodeResource(getResources(),R.drawable.coin_gold);
            coin3=Bitmap.createScaledBitmap(coin2,70,70,true);
            heart=BitmapFactory.decodeResource(getResources(),R.drawable.heart);
            heart=Bitmap.createScaledBitmap(heart,70,70,true);


            rocket=BitmapFactory.decodeResource(getResources(),R.drawable.rocket);
            rocket=Bitmap.createScaledBitmap(rocket,200,150,true);
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
            enemy_fire=BitmapFactory.decodeResource(getResources(),R.drawable.enemy_fire);
            enemy_fire=Bitmap.createScaledBitmap(enemy_fire, 40, 40, true);
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
            is_coin1=true;
            is_coin2=true;
            is_coin3=true;
            level5_wall=false;
            groundX=new Random().nextInt(dWidth/2+1)+300;
            groundY=new Random().nextInt(dHeight/2+1)+300;
            is_level_swap=true;
            monster_x=dWidth - monster.getWidth()-300;
            monster_y=dHeight - monster.getHeight() - monster.getHeight() / 4;
            enemybulletX=monster_x;
            enemybulletY=monster_y+monster.getHeight()/2;
            first_mons=monster;
            rocketX=dWidth;
            rocketY=250;
        }
        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            if(game_over){
                level=1;
                canvas.drawBitmap(back_over,null,rect,null);

            }
            else if(game_finish){
                level=1;
                canvas.drawBitmap(finish_game,null,rect,null);
            }
            else
                {
                if(level==1){
                    canvas.drawBitmap(background1,null,rect,null);
                    monster=first_mons;
                    monster_y=dHeight - monster.getHeight() - monster.getHeight() / 4;

                    //
                }
                else if(level==2){
                    canvas.drawBitmap(background2,null,rect,null);
                    monster=enemy;
                    monster_y=dHeight - monster.getHeight() - monster.getHeight() / 4;
                }
                else if(level==3){
                    canvas.drawBitmap(background3,null,rect,null);
                    monster=killer;
                    monster_y=dHeight - monster.getHeight() - monster.getHeight() / 4;
                }
                else if(level==4){
                    canvas.drawBitmap(background4,null,rect,null);
                    monster=spike;
                    monster_y=dHeight - monster.getHeight() - monster.getHeight() / 4;

                }
                else if(level==5){
                    canvas.drawBitmap(background5,null,rect,null);
                    monster=boss;
                    monster_y=dHeight - monster.getHeight() - monster.getHeight() / 4;
                }

                canvas.drawBitmap(level_array[level-1],10,10,null);
                canvas.drawBitmap(gate,dWidth-150,dHeight-450,null);
                if(is_level_swap){
                    groundX=new Random().nextInt(dWidth/2+1)+300;
                    groundY=new Random().nextInt(dHeight/2+1)+300;
                    coin1_x=new Random().nextInt(dWidth-100+1)+100;
                    coin1_y=new Random().nextInt(dHeight-100+1)+100;
                    coin2_x=new Random().nextInt(dWidth-100+1)+100;
                    coin2_y=new Random().nextInt(dHeight-100+1)+100;
                    coin3_x=new Random().nextInt(dWidth-100+1)+100;
                    coin3_y=new Random().nextInt(dHeight-100+1)+100;
                    is_level_swap=false;
                }

                for (int i=0;i<life;i++){
                    canvas.drawBitmap(heart,10+levels1.getWidth()+i*heart.getWidth(),10,null);

                }
                if(enemyCollusion(ManX,ManY,coin1_x,coin1_y,walking0,coin1)&&is_coin1) {
                    is_coin1 = false;
                    coin_count++;
    //                life++;
                }
                if(enemyCollusion(ManX,ManY,coin2_x,coin2_y,walking0,coin2)&&is_coin2) {
                    is_coin2 = false;
    //                life++;
                    coin_count++;
                }
                if(enemyCollusion(ManX,ManY,coin3_x,coin3_y,walking0,coin3)&&is_coin3) {
                    is_coin3 = false;
                    coin_count++;
    //                life++;
                }
                if(coin_count>=2){
                    life++;
                    coin_count=coin_count-2;
                }
                if(is_coin1)
                    canvas.drawBitmap(coin1,coin1_x,coin1_y,null);
                if(is_coin2)
                    canvas.drawBitmap(coin2,coin2_x,coin2_y,null);
                if(is_coin3)
                    canvas.drawBitmap(coin3,coin3_x,coin3_y,null);
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
                            ManY=ManY+gravity;
                        }
                    }

                    canvas.drawBitmap(idle_player, ManX, ManY, null);
                }


                canvas.drawBitmap(ground,groundX,groundY,null);

                if(isShot){
                    if(!isMonsterDead){
                        if(bulletcoordX>dWidth){
                            isShot=false;
                        }
                        if(enemyCollusion(monster_x,monster_y,bulletcoordX,bulletcoordY,monster,fire)){
                            isMonsterDead=true;
                            isShot=!isShot;
                            bulletcoordX=ManX+walking0.getWidth()/2;
                            bulletcoordY=ManY+walking0.getHeight()/2;

                        }
                        else{
                            canvas.drawBitmap(monster, monster_x,monster_y, null);
                            canvas.drawBitmap(fire,bulletcoordX,bulletcoordY,null);
                            bulletcoordX=firespeed+bulletcoordX;
                        }
    //
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
                }
                if(!isMonsterDead){
                    canvas.drawBitmap(monster, monster_x, monster_y, null);

                    if(enemybulletX<0){
                        enemybulletX=monster_x;
                    }
                    if(enemyCollusion(ManX,ManY,enemybulletX,enemybulletY,idle_player,enemy_fire)) {
                        System.out.println("hit");
                        System.out.println(ManX);
                        System.out.println(enemybulletX);
                        System.out.println(life);
                        System.out.println("asdasadsasdasdadsadsasdasas");
    //                    is_level_swap=true;
    //                    System.out.println(life);
                        if (life == 0){
                            is_coin1 = true;
                            game_over = true;
                            ManX = 0;
                            ManY = dHeight - 2 * idle_player.getHeight() + idle_player.getHeight() / 2 + idle_player.getHeight() / 4;
                            is_coin2 = true;
                            is_coin3 = true;
                            enemybulletX = monster_x;
                            life=3;
                        }

                        else{
    //                        enemybulletX = enemybulletX - firespeed;
                            enemybulletX = monster_x;

                            life--;
                        }
                    }
                    else {
                        canvas.drawBitmap(enemy_fire, enemybulletX, enemybulletY, null);
                        enemybulletX = enemybulletX - firespeed;
                    }
                }
                    if(enemyCollusion(ManX,ManY,rocketX,rocketY,idle_player,rocket)){
                        if (life == 0){
                            is_coin1 = true;
                            game_over = true;
                            ManX = 0;
                            ManY = dHeight - 2 * idle_player.getHeight() + idle_player.getHeight() / 2 + idle_player.getHeight() / 4;
                            is_coin2 = true;
                            is_coin3 = true;
                            enemybulletX = monster_x;
                            coin_count=0;
                            isMonsterDead=false;
                            life=3;
                        }
                        else{
                            rocketX=dWidth;
                            life--;
                        }
                    }
                if(ManX+walking0.getWidth()/2>dWidth-15&&ManY>dHeight-2*idle_player.getHeight()+idle_player.getHeight()/2+idle_player.getHeight()/4-10){
                    if(level!=5){
                        level=level+1;
                        ManX=0;
                        ManY=dHeight-2*idle_player.getHeight()+idle_player.getHeight()/2+idle_player.getHeight()/4;
                        isMonsterDead=false;

                        is_level_swap=true;
                        is_coin1=true;
                        is_coin2=true;
                        is_coin3=true;
                    }
                }
                if(level==5){
                    if(!level5_wall) {
                        canvas.drawBitmap(block, dWidth - 150 - block.getWidth(), dHeight - 2 * block.getHeight(), null);
                        canvas.drawBitmap(block, dWidth - 150 - block.getWidth(), dHeight - 3 * block.getHeight(), null);
                        canvas.drawBitmap(block, dWidth - 150 - block.getWidth(), dHeight - 4 * block.getHeight(), null);
                        canvas.drawBitmap(block, dWidth - 150 - block.getWidth(), dHeight - 5 * block.getHeight(), null);
                        canvas.drawBitmap(block, dWidth - 150 - block.getWidth(), dHeight - 6 * block.getHeight(), null);
                        canvas.drawBitmap(block, dWidth - 150 - block.getWidth() + block.getWidth(), dHeight - 6 * block.getHeight(), null);
                        canvas.drawBitmap(block, dWidth - 150 - block.getWidth() + 2 * block.getWidth(), dHeight - 6 * block.getHeight(), null);
                        canvas.drawBitmap(block, dWidth - 150 - block.getWidth() + 3 * block.getWidth(), dHeight - 6 * block.getHeight(), null);

                    }
    //                canvas.drawBitmap(block,dWidth-150-block.getWidth(),dHeight-2*block.getHeight()+block.getHeight()/2+block.getHeight()/4-block.getHeight(),null);

                    canvas.drawBitmap(princess,dWidth-150,dHeight-2*princess.getHeight()+princess.getHeight()/2+princess.getHeight()/4,null);

                        for(int i=2;i<7;i++){
                            if(level5_wall==true)
                                break;
                            else if(enemyCollusion(dWidth-150-block.getWidth(),dHeight-i*block.getHeight(),bulletcoordX,bulletcoordY,block,fire)){
                                level5_wall=true;
                            }
                        }
                    if(level5_wall){
                        game_finish=true;
                        is_coin1 = true;
//                        game_over = true;
                        ManX = 0;
                        isMonsterDead=false;
                        ManY = dHeight - 2 * idle_player.getHeight() + idle_player.getHeight() / 2 + idle_player.getHeight() / 4;
                        is_coin2 = true;
                        is_coin3 = true;
                        coin_count=0;
                        enemybulletX = monster_x;
                        life=3;
                    }

                }
                if(rocketX+rocket.getWidth()<0)
                    rocketX=dWidth;
                canvas.drawBitmap(rocket,rocketX,rocketY,null);
                rocketX=rocketX-rocketspeed;
                }
            //canvas.drawBitmap(princess,dWidth-200,dHeight-princess.getHeight()-princess.getHeight()/4,null);
            handler.postDelayed(runnable,UPDATE_MILLIS);
        }
        public boolean enemyCollusion(int main_x,int main_y,int enemy_x,int enemy_y,Bitmap main,Bitmap enemy){

            if (main_x<enemy_x+enemy.getWidth()&&main_x+main.getWidth()>enemy_x&&main_y<enemy_y+enemy.getHeight()&&main_y+main.getHeight()>enemy_y)
                return true;
            else
                return false;
        }


        public boolean onTouchEvent(MotionEvent event){
            int action = event.getAction();
            int x = (int)event.getX();
            int y = (int)event.getY();
            game_over=false;
            game_finish=false;
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
