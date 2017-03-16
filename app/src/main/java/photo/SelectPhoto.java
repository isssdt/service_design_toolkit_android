package photo;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;

import addTouchpoint.AddNewTouchpoint;
import common.utils.Utils;
import poc.servicedesigntoolkit.getpost.R;
import touchpoint.activity.TouchPointDetailsActivity;
import touchpoint.dto.TouchPointFieldResearcherDTO;

public class SelectPhoto extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_TAKE_PHOTO = 1;
    Uri filePath, picUri;
    Bitmap myBitmap;
    private Button buttonCamera;
    private Button buttonGallery;
    private Button buttonUpload;
    private ImageView imageView;
    private static final int SELECT_IMAGE = 2;
    private static final int TAKE_IMAGE = 2;
    private static final int ACTION_TAKE_PHOTO_S = 3;



    private AlbumStorageDirFactory mAlbumStorageDirFactory = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_photo);

        buttonCamera = (Button) findViewById(R.id.camera);
        buttonGallery = (Button) findViewById(R.id.gallery);
        buttonUpload = (Button) findViewById(R.id.buttonUpload);
        imageView = (ImageView) findViewById(R.id.imageView);


        buttonCamera.setOnClickListener(this);
        buttonGallery.setOnClickListener(this);
        buttonUpload.setOnClickListener(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
            mAlbumStorageDirFactory = new FroyoAlbumDirFactory();
        } else {
            mAlbumStorageDirFactory = new BaseAlbumDirFactory();
        }

    }

    private void uploadImage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            TouchPointFieldResearcherDTO touchPointFieldResearcherDTO = (TouchPointFieldResearcherDTO) getIntent().getExtras().get(TouchPointFieldResearcherDTO.class.toString());
            if (null != picUri) {
                touchPointFieldResearcherDTO.setPhotoLocation(ImageFilePath.getPath(getApplicationContext(), picUri));
            }

            String activity = getIntent().getExtras().getString(Activity.class.toString());
            if (TouchPointDetailsActivity.class.toString().equals(activity)) {
                Utils.forwardToScreen(this, TouchPointDetailsActivity.class, TouchPointFieldResearcherDTO.class.toString(), touchPointFieldResearcherDTO);
            }
            if (AddNewTouchpoint.class.toString().equals(activity)) {
                Utils.forwardToScreen(this, AddNewTouchpoint.class, TouchPointFieldResearcherDTO.class.toString(), touchPointFieldResearcherDTO);
            }
        }
    }


    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_IMAGE);
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {

            picUri = getPickImageResultUri(data);

            try {
                myBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), picUri);
                myBitmap = getResizedBitmap(myBitmap, 500);

                imageView.setImageBitmap(myBitmap);

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
            case ACTION_TAKE_PHOTO_S: {
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

    private void takePhoto() {
        try {
            dispatchTakePictureIntent();
        } catch (IOException e) {
        }
    }




    private void dispatchTakePictureIntent() throws IOException {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        String fileName = "temp.jpg";
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, fileName);
        filePath = getContentResolver()
                .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        values);
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, filePath);
        startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
    }
    @Override
    public void onBackPressed() {

    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 0) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }


    /**
     * Get the URI of the selected image from
     * <p>
     * Will return the correct URI for camera and gallery image.
     *
     * @param data the returned data of the activity result
     */
    public Uri getPickImageResultUri(Intent data) {
        boolean isCamera = true;
        if (data != null) {
            String action = data.getAction();
            isCamera = action != null && action.equals(MediaStore.ACTION_IMAGE_CAPTURE);
        }

//        return isCamera ? getCaptureImageOutputUri() : data.getData();
        return isCamera ? filePath : data.getData();
    }
}