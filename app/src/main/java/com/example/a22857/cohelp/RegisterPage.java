package com.example.a22857.cohelp;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.example.a22857.cohelp.adapter.ImagePickerAdapter;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ImagePreviewDelActivity;
import com.lzy.imagepicker.view.CropImageView;

import Entity.User;

/**
 * Created by 22857 on 2021/3/11.
 */

public class RegisterPage extends AppCompatActivity implements ImagePickerAdapter.OnRecyclerViewItemClickListener{
    private EditText name;
    private EditText password;
    private EditText repassword;
    private EditText account;
    private EditText descview;
    private Button sure;
    private Button cancal;
    private String nametext;
    private String passwordtext;
    private String repasswordtext;
    private String accounttext;
    private String desc;
    private Bitmap bitmap = null;
    private ArrayList<ImageItem> selImageList;
    private ImagePickerAdapter adapter;
    public static final int IMAGE_ITEM_ADD = -1;
    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_PREVIEW = 101;
    private int maxImgCount = 1;//???????????????????????????

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registerpage);
        name = (EditText) findViewById(R.id.registerPageName);
        password = (EditText) findViewById(R.id.registerPagePassword);
        repassword = (EditText) findViewById(R.id.registerPageRepassword);
        sure = (Button) findViewById(R.id.registerPageSure);
        cancal = (Button) findViewById(R.id.registerPageCancal);
        account = (EditText) findViewById(R.id.registerPageAccount);
        descview=(EditText)findViewById(R.id.registerPagedesc);
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nametext = name.getText().toString();
                passwordtext = password.getText().toString();
                repasswordtext = repassword.getText().toString();
                accounttext = account.getText().toString();
                desc=descview.getText().toString();
                if (accounttext.equals("") || nametext.equals("") || passwordtext.equals("") || repasswordtext.equals("")) {
                    AlertDialog alertDialog1 = new AlertDialog.Builder(RegisterPage.this)
                            .setTitle("??????")
                            .setMessage("??????????????????????????????")
                            .create();
                    alertDialog1.show();
                } else if (accounttext.length() > 6 || accounttext.length() < 4) {
                    AlertDialog alertDialog3 = new AlertDialog.Builder(RegisterPage.this)
                            .setTitle("??????")
                            .setMessage("???????????????4-6???")
                            .create();
                    alertDialog3.show();
                } else if (!passwordtext.equals(repasswordtext)) {
                    AlertDialog alertDialog2 = new AlertDialog.Builder(RegisterPage.this)
                            .setTitle("??????")
                            .setMessage("???????????????????????????")
                            .create();
                    alertDialog2.show();
                }else if(selImageList.size()==0)
                {
                    AlertDialog alertDialog2 = new AlertDialog.Builder(RegisterPage.this)
                            .setTitle("??????")
                            .setMessage("????????????????????????")
                            .create();
                    alertDialog2.show();
                } else {
                    String url = "http://10.0.2.2:8080/user/add";
                    OkHttpClient client = new OkHttpClient();
                    FormBody.Builder requestBuild = new FormBody.Builder();
                    RequestBody requestBody = requestBuild
                            .add("name", nametext)
                            .add("account", accounttext)
                            .add("password", passwordtext)
                            .add("description", desc)
                            .add("money", "1000")
                            .add("head",Base64.encodeToString((ConvertImage(selImageList.get(0))),Base64.DEFAULT))
                            .build();
                    Request request = new Request.Builder()
                            .url(url)
                            .post(requestBody)
                            .build();
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Log.d("MainActivityPost", "????????????" + e.getLocalizedMessage());
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String result = response.body().string();
                            Log.d("MainActivity", result);
                            if (response.body() != null) {
                                response.body().close();
                                Looper.prepare();
                                Toast.makeText(RegisterPage.this, "??????????????????????????????", Toast.LENGTH_SHORT).show();
                                Intent inter = new Intent(RegisterPage.this, MainActivity.class);
                                startActivityForResult(inter, 0);
                                Looper.loop();
                            }
                        }
                    });
                }

            }
        });
        cancal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterPage.this, MainActivity.class);
                startActivity(intent);
            }
        });
        initImagePicker();
        initWidget();
    }
    private void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //?????????????????????
        imagePicker.setShowCamera(true);                      //??????????????????
        imagePicker.setCrop(true);                            //?????????????????????????????????
        imagePicker.setSaveRectangle(true);                   //???????????????????????????
        imagePicker.setSelectLimit(maxImgCount);              //??????????????????
        imagePicker.setMultiMode(false);                      //??????
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //??????????????????
        imagePicker.setFocusWidth(800);                       //?????????????????????????????????????????????????????????????????????
        imagePicker.setFocusHeight(800);                      //?????????????????????????????????????????????????????????????????????
        imagePicker.setOutPutX(80);                         //????????????????????????????????????
        imagePicker.setOutPutY(80);                         //????????????????????????????????????
    }

    private void initWidget() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.registerPageHead);
        selImageList = new ArrayList<>();
        adapter = new ImagePickerAdapter(this, selImageList, maxImgCount);
        adapter.setOnItemClickListener(RegisterPage.this);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }
    @Override
    public void onItemClick(View view, int position) {
        switch (position) {
            case IMAGE_ITEM_ADD:
                List<String> names = new ArrayList<>();
                names.add("??????");
                names.add("??????");
                showDialog(new SelectDialog.SelectDialogListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        switch (position) {
                            case 0: // ??????????????????
                                //????????????,???????????????????????????
                                ImagePicker.getInstance().setSelectLimit(maxImgCount - selImageList.size());
                                Intent intent = new Intent(RegisterPage.this, ImageGridActivity.class);
                                intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS,true); // ???????????????????????????
                                startActivityForResult(intent, REQUEST_CODE_SELECT);
                                break;
                            case 1:
                                //????????????,???????????????????????????
                                ImagePicker.getInstance().setSelectLimit(maxImgCount - selImageList.size());
                                Intent intent1 = new Intent(RegisterPage.this, ImageGridActivity.class);
                                startActivityForResult(intent1, REQUEST_CODE_SELECT);
                                break;
                            default:
                                break;
                        }
                    }
                }, names);
                break;
            default:
                //????????????
                Intent intentPreview = new Intent(this, ImagePreviewDelActivity.class);
                intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, (ArrayList<ImageItem>) adapter.getImages());
                intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, position);
                intentPreview.putExtra(ImagePicker.EXTRA_FROM_ITEMS,true);
                startActivityForResult(intentPreview, REQUEST_CODE_PREVIEW);
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            //??????????????????
            if (data != null && requestCode == REQUEST_CODE_SELECT) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (images != null){
                    selImageList.addAll(images);
                    adapter.setImages(selImageList);
                }
            }
        } else if (resultCode == ImagePicker.RESULT_CODE_BACK) {
            //??????????????????
            if (data != null && requestCode == REQUEST_CODE_PREVIEW) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_ITEMS);
                if (images != null){
                    selImageList.clear();
                    selImageList.addAll(images);
                    adapter.setImages(selImageList);
                }
            }
        }
    }
    private SelectDialog showDialog(SelectDialog.SelectDialogListener listener, List<String> names) {
        SelectDialog dialog = new SelectDialog(this, R.style.transparentFrameWindowStyle, listener, names);
        if (!this.isFinishing()) {
            dialog.show();
        }
        return dialog;
    }
    public byte[] ConvertImage(ImageItem imageItem)
    {
        String url= imageItem.path;
        if(TextUtils.isEmpty(url))
        {
            return null;
        }
        InputStream is=null;
        byte [] data =null;
        try{
            is=new FileInputStream(url);
            data=new byte[is.available()];
            is.read(data);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            if(is!=null)
            {
                try{
                    is.close();
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
        return data;
    }

}