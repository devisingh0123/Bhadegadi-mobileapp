
import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Message;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class uploadVehicleActivity extends AppCompatActivity {

    sessionManager session;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle navToggle;
    private Toolbar toolbar;
    Bitmap yourSelectedImage;
    boolean isKitKat = false;
    String userId;
    String realPath_1;
    private Uri cameraPictureUrl;

    Button drivingLicense, vehicleImage, vehicleRc, InsuranceImage;
    int vehicleId;
    String artifactType;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String s= getIntent().getExtras().getString("vehicleId");
        session = new sessionManager(this);
        vehicleId = Integer.parseInt(s);
        userId = session.getUserId();
        /******** Uploads **********/
        drivingLicense = (Button) findViewById(R.id.drivingLicense);
        vehicleImage = (Button) findViewById(R.id.vehicleImage);
        vehicleRc = (Button) findViewById(R.id.vehicleRc);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_vehicle);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        navToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);

        drawerLayout.addDrawerListener(navToggle);
        navToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav);
        View headerView = navigationView.getHeaderView(0);
        TextView app_name = (TextView) headerView.findViewById(R.id.menu_name);

        Typeface comfortaa = Typeface.createFromAsset(getAssets(), "fonts/Comfortaa-Bold.ttf");
        app_name.setTypeface(comfortaa);
    }

    private android.os.Handler handler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            String status = msg.getData().getString("what");
            Toast.makeText(uploadVehicleActivity.this, status, Toast.LENGTH_LONG).show();
            if(status.equals("Upload Successful")) {
                drivingLicense = (Button) findViewById(R.id.drivingLicense);
                vehicleImage = (Button) findViewById(R.id.vehicleImage);
                vehicleRc = (Button) findViewById(R.id.vehicleRc);
                if(artifactType.equals("DL")) {
                    drivingLicense.setText("Uploaded");
                }
                if(artifactType.equals("VI")) {
                    vehicleImage.setText("Uploaded");
                    Button disabled = (Button) findViewById(R.id.disabledButton);
                    Button doneBtn = (Button) findViewById(R.id.doneButton);
                    disabled.setVisibility(View.INVISIBLE);
                    doneBtn.setVisibility(View.VISIBLE);
                }
                if(artifactType.equals("RC")) {
                    vehicleRc.setText("Uploaded");

                }

                if(drivingLicense.getText().equals("Uploaded") && vehicleImage.getText().equals("Uploaded") && vehicleRc.getText().equals("Uploaded")) {
                    Intent intent = new Intent(uploadVehicleActivity.this, showVehiclesActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }
        }
    };

    public void done(View view) {
        Intent intent = new Intent(uploadVehicleActivity.this, HomePageActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void uploadDrivingLicense(View view) {

        artifactType = "DL";
            final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Vehicle Image!");
            builder.setItems(options, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (options[which].equals("Take Photo")) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                        cameraPictureUrl = createImageUri();
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, cameraPictureUrl);
                        startActivityForResult(intent, 11);
                    } else if (options[which].equals("Choose from Gallery")) {
                        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                        i.setType("image/jpeg");
                        startActivityForResult(i, 10);
                    } else if (options[which].equals("Cancel")) {
                        dialog.dismiss();
                    }
                }

            });
            builder.show();
    }


    private Uri createImageUri() {
    	ContentResolver contentResolver = getContentResolver();
    	ContentValues cv = new ContentValues();
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        cv.put(MediaStore.Images.Media.TITLE, "temp");
              return contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, cv);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {

        try {
            if (requestCode == 10 && resultCode == RESULT_OK) {

                final File file;

                Uri uri = data.getData();
                final String path;

                path = getPath(this, uri);
                if (path == null) {
                    Toast.makeText(this, "Error selecting file.", Toast.LENGTH_SHORT).show();
                } else {
                    File file2 = new File(path);
                    file = file2;
                }

                pd = new ProgressDialog(this);
                pd.setMessage("Please Wait");
                pd.setTitle("Uploading Document");
                pd.setCancelable(false);
                pd.setCanceledOnTouchOutside(false);
                pd.show();

                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        File file2 = new File(path);

                        File imageFile = file2;
                        if (imageFile != null) {
                            fileupload(imageFile);

                        } else {
                            Log.d("File", "is Empty");
                        }
                    }
                }).start();
            }

            if(requestCode == 11 && resultCode == RESULT_OK) {
                boolean found = false;
                pd = new ProgressDialog(this);
                pd.setMessage("Please Wait");
                pd.setTitle("Uploading Document");
                pd.setCancelable(false);
                pd.setCanceledOnTouchOutside(false);
                pd.show();

                File f = new File(Environment.getExternalStorageDirectory().toString());
                for (File temp : f.listFiles()) {
                    Log.d("file", temp.toString());

                    if (temp.getName().equals("temp.jpg")) {
                        f = temp;
                        found = true;
                        final File image = temp;
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                if (image != null) {
                                    fileupload(image);
                                } else {
                                    Log.d("File", "is Empty");
                                }
                            }
                        }).start();
                        break;
                    }
                }

                if(!found) {
                    pd.dismiss();
                    Toast.makeText(this, "Unable to select image, please use gallery", Toast.LENGTH_LONG).show();
                }
            }
        }
        catch (NullPointerException e) {
            Toast.makeText(this, "There was an error selecting image, please try using photos", Toast.LENGTH_SHORT).show();
            pd.dismiss();
        }
    }

    public static String getPath(Context context, Uri uri) {
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);
            }
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                final String selection = "_id=?";
                final String[] selectionArgs = new String[] {split[1]};
                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }

        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();
            return getDataColumn(context, uri, null, null);
        }
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = { column };
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    private void fileupload(File f) {

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .readTimeout(11500, TimeUnit.SECONDS)
                .build();

        client.dispatcher().executorService().shutdown();

        RequestBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("artifact", f.getName(),
                        RequestBody.create(MediaType.parse("image/jpeg"),f))
                .addFormDataPart("artifacttype", artifactType)
                .addFormDataPart("vehicleId", Integer.toString(vehicleId))
                .addFormDataPart("userId", userId)
                .build();

        okhttp3.Request request = new okhttp3.Request.Builder()
                .url("http://ec2-35-167-97-234.us-west-2.compute.amazonaws.com:8080/api/uploadArtifact")
                .post(body)
                .build();

        try {
            okhttp3.Response response =  client.newCall(request).execute();

            if(!response.isSuccessful()) {
                throw new IOException("Error"+response);
            }

            String jsonData = response.body().string();
            try {

                JSONObject Jobject = new JSONObject(jsonData);

                String resultStatus = Jobject.getString("requstStatus");

                if (resultStatus.equals("OK")) {
                    Message msg = new Message();
                    Bundle b = new Bundle();
                    b.putString("what", "Upload Successful");
                    msg.setData(b);
                    handler.sendMessage(msg);
                } else {
                    Message msg = new Message();
                    Bundle b = new Bundle();
                    b.putString("what", "Upload Failed! Large File or slow network");
                    msg.setData(b);
                    handler.sendMessage(msg);

                }
                pd.dismiss();


            } catch (JSONException e) {
                e.printStackTrace();
                pd.dismiss();
                Message msg = new Message();
                Bundle b = new Bundle();
                b.putString("what", "Upload Failed! Try Again");
                msg.setData(b);
                handler.sendMessage(msg);

            }

        } catch (IOException e) {
            pd.dismiss();
            e.printStackTrace();
            Message msg = new Message();
            Bundle b = new Bundle();
            b.putString("what", "Upload Failed! Try Again");
            msg.setData(b);

        } catch (Exception e) {
            pd.dismiss();
            e.printStackTrace();
            Message msg = new Message();
            Bundle b = new Bundle();
            b.putString("what", "Upload Failed! Try Again");
            msg.setData(b);
        }

    }















    /*  Button Click Events */
    public void uploadVehicleImage(View view) {
        artifactType = "VI";
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Vehicle Image!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (options[which].equals("Take Photo"))
                {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    cameraPictureUrl = createImageUri();
                    File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, cameraPictureUrl);
                    startActivityForResult(intent, 11);
                }
                else if (options[which].equals("Choose from Gallery"))
                {
                    Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                    i.setType("image/jpeg");
                    startActivityForResult(i, 10);

                }
                else if (options[which].equals("Cancel")) {
                    dialog.dismiss();
                }

            }

        });


        builder.show();


    }


    /*  Button Click Events */
    public void uploadVehicleRC(View view) {
        artifactType = "RC";
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Vehicle Image!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (options[which].equals("Take Photo"))
                {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, cameraPictureUrl);
//                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    startActivityForResult(intent, 11);
                }
                else if (options[which].equals("Choose from Gallery"))
                {
                    Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                    i.setType("image/jpeg");
                    startActivityForResult(i, 10);

                }
                else if (options[which].equals("Cancel")) {
                    dialog.dismiss();
                }

            }

        });


        builder.show();

    }


//    /*  Button Click Events */
//    public void uploadInsurance(View view) {
//        artifactType = "II";
//        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Driving Licence!");
//        builder.setItems(options, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//                if (options[which].equals("Take Photo"))
//                {
//                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                    File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
//                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
//                    startActivityForResult(intent, 11);
//                }
//                else if (options[which].equals("Choose from Gallery"))
//                {
//                    Intent i = new Intent(Intent.ACTION_GET_CONTENT);
//                    i.setType("image/jpeg");
//                    startActivityForResult(i, 10);
//
//                }
//                else if (options[which].equals("Cancel")) {
//                    dialog.dismiss();
//                }
//
//            }
//
//        });
//
//
//        builder.show();
//
//
//    }



    /////////// Navigation functions //////////////////

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(navToggle.onOptionsItemSelected(item)) { return true; }

        return super.onOptionsItemSelected(item);
    }


            /* Menu onClick Functions */


    /* Menu onClick Functions */
    public void logout(MenuItem item){
        session = new sessionManager(this);
        session.setFirstTimeLaunch(true);
        Intent intent = new Intent(this, ownerLoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void vehicles(MenuItem item){
        Intent intent = new Intent(this, showVehiclesActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void go_home(MenuItem item) {
        Intent intent = new Intent(this, HomePageActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }


    public void go_search(MenuItem item) {
        Intent intent = new Intent(this, searchVehicleActivity.class);
        startActivity(intent);
    }

    public void go_support(MenuItem item) {
        Intent intent = new Intent(this, supportActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void go_about(MenuItem item) {
        Intent intent = new Intent(this, aboutActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }




    public void history(MenuItem item){
        Intent intent = new Intent(this, ownerHistoryActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

}
