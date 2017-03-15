package photo;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;

import common.utils.Utils;
import poc.servicedesigntoolkit.getpost.R;
import touchpoint.activity.TouchPointDetailsActivity;
import touchpoint.dto.TouchPointFieldResearcherDTO;

public class SelectPhoto extends AppCompatActivity implements View.OnClickListener {

    static final int REQUEST_TAKE_PHOTO = 1;
    private static final int take_photo_request_code =1;
    private static final String TAG = "selectphoto";
    String newPath,sourcepath;
    Uri filePath;
    private Button buttonCamera;
    private Button buttonGallery;
    private Button buttonUpload;
    private ImageView imageView;
    private EditText editTextName;
    private Bitmap bitmap;
    private static final int SELECT_IMAGE = 1;
    private static final int TAKE_IMAGE = 2;
    private String Username,Touchpoint_name;
    String action,channel,imagefinalPath;
    String rating_intent,reaction_intent,comment_intent;
    String expected_unit,channel_desc, actual_time,actual_unit, JourneyName,id_String;
    Integer id,expected_time;
    private String mCurrentPhotoPath;
    private static final int ACTION_TAKE_PHOTO_B = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_photo);

        buttonCamera = (Button) findViewById(R.id.camera);
        buttonGallery = (Button) findViewById(R.id.gallery);
        buttonUpload = (Button) findViewById(R.id.buttonUpload);
        imageView = (ImageView) findViewById(R.id.imageView);

        Bundle extras = getIntent().getExtras();
        TouchPointFieldResearcherDTO touchPointFieldResearcherDTO = (TouchPointFieldResearcherDTO) extras.get(TouchPointFieldResearcherDTO.class.toString());
        Username = touchPointFieldResearcherDTO.getFieldResearcherDTO().getSdtUserDTO().getUsername();
        Touchpoint_name = touchPointFieldResearcherDTO.getTouchpointDTO().getTouchPointDesc();

        JourneyName = touchPointFieldResearcherDTO.getTouchpointDTO().getJourneyDTO().getJourneyName();
        action = touchPointFieldResearcherDTO.getTouchpointDTO().getAction();
        channel = touchPointFieldResearcherDTO.getTouchpointDTO().getChannelDTO().getChannelName();
        expected_time = touchPointFieldResearcherDTO.getTouchpointDTO().getDuration();
        expected_unit = touchPointFieldResearcherDTO.getTouchpointDTO().getMasterDataDTO().getDataValue();
        channel_desc = touchPointFieldResearcherDTO.getTouchpointDTO().getChannelDescription();
        id = touchPointFieldResearcherDTO.getTouchpointDTO().getId();
        id_String = id.toString();
        if (null != touchPointFieldResearcherDTO.getRatingDTO() && null != touchPointFieldResearcherDTO.getRatingDTO().getValue() &&
                !touchPointFieldResearcherDTO.getRatingDTO().getValue().isEmpty()) {
            rating_intent = touchPointFieldResearcherDTO.getRatingDTO().getValue();
            reaction_intent = touchPointFieldResearcherDTO.getReaction();
            comment_intent = touchPointFieldResearcherDTO.getComments();
            actual_time = touchPointFieldResearcherDTO.getDuration().toString();
            actual_unit = touchPointFieldResearcherDTO.getDurationUnitDTO().getDataValue();
        }

        buttonCamera.setOnClickListener(this);
        buttonGallery.setOnClickListener(this);
        buttonUpload.setOnClickListener(this);

    }

    private void uploadImage(){
        //String image = getStringImage(bitmap);

        String selectedImagePath;
        Log.d("VERSION", String.valueOf(Build.VERSION.SDK_INT));
        Log.d("---------------------------------------------------------------->filePath",filePath.toString());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            selectedImagePath = ImageFilePath.getPath(getApplicationContext(), filePath);
            //selectedImagePath = getPath(filePath);
            TouchPointFieldResearcherDTO touchPointFieldResearcherDTO = (TouchPointFieldResearcherDTO) getIntent().getExtras().get(TouchPointFieldResearcherDTO.class.toString());
            touchPointFieldResearcherDTO.setPhotoLocation(selectedImagePath);


            SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("Trial", Context.MODE_PRIVATE);
            Gson gson = new Gson();
            String list = sharedPref.getString("TouchPointFieldResearcherDTO", "");
            //touchPointFieldResearcherListDTO = gson.fromJson(list, TouchPointFieldResearcherListDTO.class);
            Log.d("TouchPointFieldResearcherDTO",list);


            SharedPreferences.Editor editor = sharedPref.edit();
            String json = gson.toJson(touchPointFieldResearcherDTO);
            editor.putString("TouchPointFieldResearcherDTO",Username);
            Log.d("JourneyFieldResearcherDTO", " EMPTY "+json);

            Utils.forwardToScreen(this, TouchPointDetailsActivity.class, TouchPointFieldResearcherDTO.class.toString(), touchPointFieldResearcherDTO);
        }
    }

    private String getPath(Uri uri) {

        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(uri, projection, null, null,null);

        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();

        return cursor.getString(column_index);
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_IMAGE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SELECT_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                sourcepath = ImageFilePath.getPath(getApplicationContext(), filePath);
                Log.d("sourcepath_SELECT_IMAGE",sourcepath);
                Log.d("filePath_SELECT_IMAGE",filePath.toString());
            }
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
                createImageFile();
                copyfile();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if (requestCode == TAKE_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {

            filePath = data.getData();
            Log.d("filePath_REQUEST_TAKE_PHOTO",filePath.toString());
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.gallery:
                if (ContextCompat.checkSelfPermission(SelectPhoto.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(SelectPhoto.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, SELECT_IMAGE);
                } else {
                    showFileChooser();
                }
                break;

            case R.id.camera:
                if (ContextCompat.checkSelfPermission(SelectPhoto.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(SelectPhoto.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, TAKE_IMAGE);
                } else {
                    takePhoto();
                }
                break;

            case R.id.buttonUpload:
                uploadImage();
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case TAKE_IMAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    takePhoto();
                }else {
                    Toast.makeText(SelectPhoto.this, "Permission denied to get your LOCATION", Toast.LENGTH_SHORT).show();
                }
                return;
            }
            case SELECT_IMAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showFileChooser();
                }else {
                    Toast.makeText(SelectPhoto.this, "Permission denied to get your LOCATION", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }
    private void copyfile(){
        try {
                File source= new File(sourcepath);
                File destination= new File(newPath);
                if (source.exists()) {
                    FileChannel src = new FileInputStream(source).getChannel();
                    FileChannel dst = new FileOutputStream(destination).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                }
            imagefinalPath = newPath;

        } catch (Exception e) {}
    }

    private void takePhoto() {
        handleBigCameraPhoto();
        dispatchTakePictureIntent(ACTION_TAKE_PHOTO_B);
    }
    private void handleBigCameraPhoto() {

        if (mCurrentPhotoPath != null) {
            setPic();
            galleryAddPic();
            mCurrentPhotoPath = null;
        }

    }

    private void setPic() {

		/* There isn't enough memory to open up more than a couple camera photos */
		/* So pre-scale the target bitmap into which the file is decoded */

		/* Get the size of the ImageView */
        int targetW = imageView.getWidth();
        int targetH = imageView.getHeight();

		/* Get the size of the image */
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

		/* Figure out which way needs to be reduced less */
        int scaleFactor = 1;
        if ((targetW > 0) || (targetH > 0)) {
            scaleFactor = Math.min(photoW/targetW, photoH/targetH);
        }

		/* Set bitmap options to scale the image decode target */
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

		/* Decode the JPEG file into a Bitmap */
        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);

		/* Associate the Bitmap to the ImageView */
        imageView.setImageBitmap(bitmap);
    }

    private File setUpPhotoFile() throws IOException {

        File f = createImageFile();
        mCurrentPhotoPath = f.getAbsolutePath();

        return f;
    }

    private void dispatchTakePictureIntent(int actionCode) {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        switch(actionCode) {
            case ACTION_TAKE_PHOTO_B:
                File f = null;

                try {
                    f = setUpPhotoFile();
                    mCurrentPhotoPath = f.getAbsolutePath();
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                            FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", f));
                } catch (IOException e) {
                    e.printStackTrace();
                    f = null;
                    mCurrentPhotoPath = null;
                }
                break;

            default:
                break;
        } // switch

        startActivityForResult(takePictureIntent, actionCode);
    }


    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = Username +"_"+timeStamp;
        //String storageDir = Environment.getExternalStorageDirectory() + "/ServiceDesignToolkit";
        String storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/ServiceDesignToolkit";
        File dir = new File(storageDir);
        if (!dir.exists())
            dir.mkdir();

        File image = new File(storageDir + "/" + imageFileName + ".jpg");

        // Save a file: path for use with ACTION_VIEW intents
        newPath = image.getAbsolutePath();
        Log.i(TAG, "photo path = " + newPath);
        return image;
    }
    @Override
    public void onBackPressed() {

    }
}