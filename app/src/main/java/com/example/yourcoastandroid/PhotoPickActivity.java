package com.example.yourcoastandroid;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PhotoPickActivity extends AppCompatActivity
{
    // static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_TAKE_PHOTO = 1;
    static final int SELECT_A_PHOTO = 2;

    Button btn_take, btn_submit, btn_choose;
    TextView tv_message;
    ImageView iv_photo;
    // Name of photo file saved by camera
    String currentPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_pick);

        btn_take = findViewById(R.id.btn_take);
        btn_submit = findViewById(R.id.btn_submit);
        btn_choose = findViewById(R.id.btn_choose);
        tv_message = findViewById(R.id.tv_message);
        iv_photo = findViewById(R.id.iv_photo);

        btn_take.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                dispatchTakePictureIntent();
            }
        });

        btn_choose.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // Create an intent to choose a photo from the gallery (so many things to do here!)
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                // Start intent with a request code
                startActivityForResult(i, SELECT_A_PHOTO);
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
//                Intent i = new Intent(Intent.ACTION_SEND);
//                i.setType("text/plain");
//                i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"publicaccess@coastal.ca.gov"});
//                i.putExtra(Intent.EXTRA_SUBJECT, " Photo of ____ ");
//                i.putExtra(Intent.EXTRA_TEXT   , "????");
//                // must be a .png File
//                i.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(currentPhotoPath)));


                // Go straight to email to choose photo

                String url = "mailto:publicaccess@coastal.ca.gov?";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));

                //this line isn't working correctly.. trying to add photo to email
                // i.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(currentPhotoPath)));
                startActivity(i);
            }
        });
    }


//    private void dispatchTakePictureIntent()
//    {
//        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//
//        if (takePictureIntent.resolveActivity(getPackageManager()) != null)
//        {   // Enable camera
//            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
//        }
//    }

    private void dispatchTakePictureIntent()
    {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null)
        {
            // Create the File where the photo should go
            File photoFile = null;
            try
            {
                photoFile = createImageFile();
            } catch (IOException ex)
            {
                // Error occurred while creating the File
                Toast.makeText(this, "Something went wrong! Couldn't create file.", Toast.LENGTH_SHORT).show();
            }
            // Continue only if the File was successfully created
            if (photoFile != null)
            {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {

//        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK)
//        {   // Get/set bitmap for thumbnail
//            Bundle extras = data.getExtras();
//            Bitmap imageBitmap = (Bitmap) extras.get("data");
//
//            // Set photo to bitmap & return thumbnail
//            iv_photo.setImageBitmap(imageBitmap);
//        }

//        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK)
//        {
//            // Get bitmap from file
//            Bitmap takenImage = BitmapFactory.decodeFile(currentPhotoPath);
//
//            // Set image in image view
//            ImageView iv_photo = findViewById(R.id.iv_photo);
//            iv_photo.setImageBitmap(takenImage);
//
//            // Show file name in text view
//            TextView tv_message = findViewById(R.id.tv_message);
//            tv_message.setText(currentPhotoPath);
//        }

        ImageView iv_photo = findViewById(R.id.iv_photo);
        TextView tv_message = findViewById(R.id.tv_message);

        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK)
        {
            // Ensures an appropriate sized image is uploaded
            // to image view with as little memory as possible
            Glide.with(this).load(currentPhotoPath).into(iv_photo);

            // Show file name in text view
            //tv_message.setText(currentPhotoPath);
        }

        if (requestCode == SELECT_A_PHOTO && resultCode == RESULT_OK)
        {
            // Get photo from gallery
            Uri selectedPhoto = data.getData();

            // Ensures an appropriate sized image is uploaded
            // to image view with as little memory as possible
            Glide.with(this).load(selectedPhoto).into(iv_photo);

            // Show file name in text view
            //tv_message.setText(selectedPhoto.toString());
        }
    }

    private File createImageFile() throws IOException
    {
        // Create an image file name with date to differentiate photos with the same name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "png_" + timeStamp + "_";

        // Store image into new variable
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        // Then store into a new file
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".png",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path is used with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();

        return image;
    }


    //To send email?
//    Intent i = new Intent(Intent.ACTION_SEND);
//    i.setType("text/plain");
//    i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"publicaccess@coastal.ca.gov"});
//    i.putExtra(Intent.EXTRA_SUBJECT, " Photo of ____ ");
//    i.putExtra(Intent.EXTRA_TEXT   , "????");
      // must be a .png File
//    i.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(destinationFile));



//        startActivity(Intent.createChooser(i, "Send mail..."));
//    Intent intent = new Intent(getBaseContext(), DetailsActivity.class);
//    startActivity(intent);

}
