package com.example.picturebrowser;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import static android.R.attr.onClick;

public class MainActivity extends AppCompatActivity {

    //定义一个访问图片的数组
    int [] images= new int[]{
            R.drawable.java,
            R.drawable.javaee,
            R.drawable.swift,
            R.drawable.ajax,
            R.drawable.html
    };

    int currentImg = 2;

    private int alpha=255;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button plus= (Button) findViewById(R.id.plus);
        final Button minus = (Button) findViewById(R.id.minus);
        final ImageView image1= (ImageView) findViewById(R.id.image1);
        final ImageView image2= (ImageView) findViewById(R.id.image2);
        final Button next = (Button) findViewById(R.id.next);
        //定义一个监听器
        next.setOnClickListener (new View.OnClickListener(){
            @Override
            public void onClick(View v){
                image1.setImageResource(images[ ++currentImg % images.length]);
            }
        });

        //定义改变透明度的方法
        View.OnClickListener listener=new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(v == plus){
                    alpha += 20;
                }
                if(v==minus){
                    alpha -= 20;
                }
                if(alpha >= 255){
                    alpha = 255;
                }
                if(alpha <= 0){
                    alpha = 0;
                }
                image1.setImageAlpha(alpha);
            }
        };
        //两个Button添加监听
        plus.setOnClickListener(listener);
        minus.setOnClickListener(listener);
        image1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                BitmapDrawable bitmapDrawable= (BitmapDrawable) image1.getDrawable();
                //获取第一个图片显示框中的位图
                Bitmap bitmap=bitmapDrawable.getBitmap();
                //bitmap图片实际大小与第一个Image View的缩放比列
                double scale =1.0*bitmap.getHeight()/image1.getHeight();
                //获取要显示图片的开始点
                int x= (int) (event.getX()*scale);
                int y= (int) (event.getY()*scale);
                if(x+120>bitmap.getWidth()){
                    x=bitmap.getWidth()-120;
                }
                if(y+120>bitmap.getWidth()){
                    y=bitmap.getHeight()-120;
                }
                //显示图片的指定区域
                image2.setImageBitmap(Bitmap.createBitmap(bitmap,x,y,120,120));
                image2.setImageAlpha(alpha);
                return  false;
            }
        });
    }
}
