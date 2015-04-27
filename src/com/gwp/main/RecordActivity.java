package com.gwp.main;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.gwp.course.R;
import com.gwp.util.Constants.Mode;
import com.gwp.util.Constants;
import com.gwp.view.DrawView;

public class RecordActivity extends Activity implements OnClickListener{
	DrawView drawview;
	ImageButton pen, eraser, camera, picture, hand;
	final private String filePath = Environment.getDataDirectory().getAbsolutePath() + File.pathSeparator + "temp.jpg";
	final private Uri uri = Uri.fromFile(new File(filePath));
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,  
				WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置成全屏模式  
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//横屏
		requestWindowFeature(Window.FEATURE_NO_TITLE);  //去标题
		setContentView(R.layout.activity_record);
		drawview = (DrawView)findViewById(R.id.draw_view);
		pen = (ImageButton)findViewById(R.id.pen);
		eraser = (ImageButton)findViewById(R.id.eraser);
		camera = (ImageButton)findViewById(R.id.camera);
		picture = (ImageButton)findViewById(R.id.picture);
		hand = (ImageButton)findViewById(R.id.hand);
		pen.setOnClickListener(this);
		eraser.setOnClickListener(this);
		camera.setOnClickListener(this);
		picture.setOnClickListener(this);
		hand.setOnClickListener(this);
		
	}
	@Override
	public void onClick(View view) {
		int id = view.getId();
		if(id == R.id.eraser){
			if(drawview.getMode() != Mode.ERASER)drawview.changeMode(Mode.ERASER);
			
		}else if(id ==R.id.pen){
			if(drawview.getMode() == Mode.PEN) drawview.changeColor();
			else drawview.changeMode(Mode.PEN);
		
		}else if(id == R.id.camera){
			callCamera();
		
		}else if(id == R.id.picture){
			callPicture();
	
		}else if(id == R.id.hand){
			drawview.changeMode(Mode.HAND);
		}
	}
	
	private void callPicture() {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);  
        intent.addCategory(Intent.CATEGORY_OPENABLE);  
        intent.setType("image/*");  
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent, Constants.PICTURE_CODE);
		
	}
	private void callCamera(){		 
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//调用android自带的照相机 
		intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
		startActivityForResult(intent, Constants.CAMERA_CODE); 
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch(requestCode){
		case Constants.CAMERA_CODE:
			if(resultCode == Activity.RESULT_OK){
				drawview.addBitmap(BitmapFactory.decodeFile(filePath));
			}
		case Constants.PICTURE_CODE:
			if(resultCode == Activity.RESULT_OK){
				drawview.addBitmap(BitmapFactory.decodeFile(filePath));
			}
		}
	}
	
/*	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		new Thread(){
			public void run(){
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				drawview.initView();
			}
		}.start();
	}*/
}