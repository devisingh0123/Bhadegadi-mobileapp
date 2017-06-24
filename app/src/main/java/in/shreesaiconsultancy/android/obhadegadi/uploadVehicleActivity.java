package in.shreesaiconsultancy.android.obhadegadi;

import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
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
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class uploadVehicleActivity extends AppCompatActivity {

    // Nav bar
    sessionManager session;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle navToggle;
    private Toolbar toolbar;
    Bitmap yourSelectedImage;

    // Activity
    Button drivingLicense, vehicleImage, vehicleRc, InsuranceImage;
    int vehicleId;
    String artifactType;

    ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String s= getIntent().getExtras().getString("vehicleId");


        session = new sessionManager(this);
        vehicleId = Integer.parseInt(s);


                /******** Uploads **********/
        drivingLicense = (Button) findViewById(R.id.drivingLicense);
        vehicleImage = (Button) findViewById(R.id.vehicleImage);
        vehicleRc = (Button) findViewById(R.id.vehicleRc);
        InsuranceImage = (Button) findViewById(R.id.insuranceImage);





        /********** Notification Bar ***********/

//        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
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
                InsuranceImage = (Button) findViewById(R.id.insuranceImage);

                if(artifactType.equals("DL")) {
                    drivingLicense.setText("Uploaded");
                }
                if(artifactType.equals("VI")) {
                    vehicleImage.setText("Uploaded");
                }
                if(artifactType.equals("RC")) {
                    vehicleRc.setText("Uploaded");
                }
                if(artifactType.equals("II")) {
                    InsuranceImage.setText("Uploaded");
                }

                if(drivingLicense.getText().equals("Uploaded") && vehicleImage.getText().equals("Uploaded") && vehicleRc.getText().equals("Uploaded") && InsuranceImage.getText().equals("Uploaded")) {

                    Intent intent = new Intent(uploadVehicleActivity.this, showVehiclesActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                }




            }


            Log.d("status Handler", status);

        }
    };



    /*  Button Click Events */
    public void uploadDrivingLicense(View view) {

        artifactType = "DL";
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.setType("image/jpeg");
        startActivityForResult(i, 10);



    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        
        try {
            if (requestCode == 10 && resultCode == RESULT_OK) {


                final File file;

                        Uri uri = data.getData();
//                final String path = getRealPathFromURI(uri);
                final String path;

                path = getPath(this, uri);
                if (path == null) {
//                    filename = FilenameUtils.getName(uri.toString());
                    Toast.makeText(this, "Error selecting file.", Toast.LENGTH_SHORT).show();
                } else {
                   File file2 = new File(path);
                    file = file2;
                }

                pd = new ProgressDialog(this);
                pd.setMessage("Please Wait");
                pd.setTitle("Uploading Document");
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
        } 
        catch (NullPointerException e) {
            Toast.makeText(this, "There was an error selecting image, please try using photos", Toast.LENGTH_SHORT).show();
        }
    }

//    public String getRealPathFromURI(Uri contentUri) {
//        String[] proj = { MediaStore.Images.Media.DATA };
//
//
//
//
//        android.content.CursorLoader cursorLoader = new android.content.CursorLoader(
//                this,
//                contentUri, proj, null, null, null);
//        Cursor cursor = cursorLoader.loadInBackground();
//
//        int column_index =
//                cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//        cursor.moveToFirst();
//        return cursor.getString(column_index);
//    }



    public static String getPath(Context context, Uri uri) {
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else
            if (isMediaDocument(uri)) {
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
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();
            return getDataColumn(context, uri, null, null);
        }
        // File
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

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
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
                .build();



        okhttp3.Request request = new okhttp3.Request.Builder()
                .url("http://ec2-35-167-97-234.us-west-2.compute.amazonaws.com/api/uploadArtifact")
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
                    b.putString("what", "Upload Successful"); // for example
                    msg.setData(b);
                    handler.sendMessage(msg);
                } else {
                    Message msg = new Message();
                    Bundle b = new Bundle();
                    b.putString("what", "Upload Failed! Large File or slow network"); // for example
                    msg.setData(b);
                    handler.sendMessage(msg);

                }
                pd.dismiss();


            } catch (JSONException e) {
                e.printStackTrace();
                pd.dismiss();
                Message msg = new Message();
                Bundle b = new Bundle();
                b.putString("what", "Upload Failed! Try Again"); // for example
                msg.setData(b);
                handler.sendMessage(msg);

            }

        } catch (IOException e) {
            pd.dismiss();
            e.printStackTrace();
            Message msg = new Message();
            Bundle b = new Bundle();
            b.putString("what", "Upload Failed! Try Again"); // for example
            msg.setData(b);

        }

    }















    /*  Button Click Events */
    public void uploadVehicleImage(View view) {
        artifactType = "VI";
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.setType("image/jpeg");
        startActivityForResult(i, 10);

    }


    /*  Button Click Events */
    public void uploadVehicleRC(View view) {
        artifactType = "RC";
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.setType("image/jpeg");
        startActivityForResult(i, 10);
    }


    /*  Button Click Events */
    public void uploadInsurance(View view) {
        artifactType = "II";
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.setType("image/jpeg");
        startActivityForResult(i, 10);

    }



    /////////// Navigation functions //////////////////

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(navToggle.onOptionsItemSelected(item)) { return true; }

        return super.onOptionsItemSelected(item);
    }


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

    public void history(MenuItem item){
        Intent intent = new Intent(this, ownerHistoryActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

}
