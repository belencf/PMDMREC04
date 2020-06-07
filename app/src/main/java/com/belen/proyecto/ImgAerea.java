package com.belen.proyecto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class ImgAerea extends AppCompatActivity implements View.OnClickListener {
private AdminSQL baseDatos;
private ImageView vista;
public static final int REQUEST_CODE_TAKE_PHOTO = 0 /*1*/;
private String mCurrentPhotoPath;
private Uri photoURI;
private String provincia;
private String ruta;
private String imageFileName;
private File storageDir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_img_aerea);
        if (baseDatos==null) {   // Abrimos a base de datos para escritura
            baseDatos = AdminSQL.getInstance(getApplicationContext());
            baseDatos.abrirBD();
        }
        vista =(ImageView) findViewById(R.id.Aerea);
        TextView tfase= (TextView) findViewById(R.id.Fase);
        TextView tnormas= (TextView) findViewById(R.id.normas);
        Bundle datos = this.getIntent().getExtras();
        provincia = datos.getString("var");
        ArrayList<String> lfase=baseDatos.checkfase(provincia);
        String fase=lfase.get(0);
        tfase.setText(fase);
        ArrayList<String> lnormas=baseDatos.checknormas(fase);
        String normas=lnormas.get(0);
        tnormas.setText(normas);
        ArrayList<String> lruta=baseDatos.recuperaruta(provincia);
        if (lruta.isEmpty()){
            switch (provincia) {
                case "A Coruña":
                    vista.setImageResource(R.drawable.coru);
                    break;
                case "Lugo":
                    vista.setImageResource(R.drawable.lugo);
                    break;
                case "Pontevedra":
                    vista.setImageResource(R.drawable.ponte);
                    break;
                case "Ourense":
                    vista.setImageResource(R.drawable.orense);
                    break;
                default:
                    break;
            }
        }else{
            ruta=lruta.get(0);
            vista.setImageBitmap(BitmapFactory.decodeFile(ruta));
        }
        vista.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        if (v == vista) {

            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this,
                    Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                } else {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            225);
                }


                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.CAMERA)) {

                } else {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.CAMERA},
                            226);
                }
            } else {
                dispatchTakePictureIntent();
            }
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
            }
            if (photoFile != null) {

                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.TITLE, "mifoto");
                values.put(MediaStore.Images.Media.DESCRIPTION, "Foto hecha ");
                photoURI = getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_CODE_TAKE_PHOTO);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        imageFileName =provincia;
        storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_TAKE_PHOTO && resultCode == RESULT_OK) {

            Bitmap bitmap;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), photoURI);
                vista.setImageBitmap(bitmap);
                Save s=new Save();
                mCurrentPhotoPath=s.SaveImage(this,bitmap,storageDir,imageFileName);
                long ok=baseDatos.addfoto(provincia,mCurrentPhotoPath);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

}

