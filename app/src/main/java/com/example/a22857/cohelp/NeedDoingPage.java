package com.example.a22857.cohelp;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.a22857.cohelp.adapter.ImagePickerAdapter;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ImagePreviewDelActivity;
import com.lzy.imagepicker.view.CropImageView;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Entity.ItemNeed;
import Entity.User;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NeedDoingPage extends AppCompatActivity implements ImagePickerAdapter.OnRecyclerViewItemClickListener{
    public static final int IMAGE_ITEM_ADD = -1;
    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_PREVIEW = 101;
    private ImagePickerAdapter adapter;
    private ArrayList<ImageItem> selImageList; //当前选择的所有图片
    private int maxImgCount = 8;//允许选择图片最大数
    private HttpUtil httpUtil;
    private Button submitview;
    private TextView title;
    private TextView name;
    private TextView time;
    private TextView textview;
    private EditText editText;
    private TextView reward;
    private Integer needid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.needdoingpage);
        httpUtil=new HttpUtil();
        submitview=(Button)findViewById(R.id.needsoingpagesubmit);
        title=(TextView)findViewById(R.id.needdoingpagetitle);
        name=(TextView)findViewById(R.id.needdoingpagename);
        time=(TextView)findViewById(R.id.needdoingpagetime);
        textview=(TextView)findViewById(R.id.needdoingpagetext);
        editText=(EditText)findViewById(R.id.needdoingpageedittext);
        reward=(TextView)findViewById(R.id.needdoingpagereward);
        submitview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage(selImageList,editText.getText().toString());
                Intent intent=new Intent(NeedDoingPage.this,MainPage.class);
                startActivityForResult(intent,0);
            }
        });
        Intent intent=getIntent();
        needid=intent.getIntExtra("needid",0);
        Log.d("初始化","传过来的的needID为"+needid);


        initImagePicker();
        initWidget();
    }
    private void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);                      //显示拍照按钮
        imagePicker.setCrop(true);                            //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true);                   //是否按矩形区域保存
        imagePicker.setSelectLimit(maxImgCount);              //选中数量限制
        imagePicker.setMultiMode(false);                      //多选
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);                       //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);                      //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);                         //保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);                         //保存文件的高度。单位像素
    }

    private void initWidget() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.needdoingpagerecylerview);
        selImageList = new ArrayList<>();
        adapter = new ImagePickerAdapter(this, selImageList, maxImgCount);
        adapter.setOnItemClickListener(NeedDoingPage.this);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        SharedPreferences sharedPreferences=getSharedPreferences("local_user",MODE_PRIVATE);
        String user=sharedPreferences.getString("user_id","");
        QueryNeedById queryNeedById=new QueryNeedById();
        Integer userid=Integer.parseInt(user);
        Log.d("初始化","本地获取的的userIDID为"+userid);
        queryNeedById.execute(needid,userid);
    }

    private SelectDialog showDialog(SelectDialog.SelectDialogListener listener, List<String> names) {
        SelectDialog dialog = new SelectDialog(this, R.style.transparentFrameWindowStyle, listener, names);
        if (!this.isFinishing()) {
            dialog.show();
        }
        return dialog;
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (position) {
            case IMAGE_ITEM_ADD:
                List<String> names = new ArrayList<>();
                names.add("拍照");
                names.add("相册");
                showDialog(new SelectDialog.SelectDialogListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        switch (position) {
                            case 0: // 直接调起相机
                                //打开选择,本次允许选择的数量
                                ImagePicker.getInstance().setSelectLimit(maxImgCount - selImageList.size());
                                Intent intent = new Intent(NeedDoingPage.this, ImageGridActivity.class);
                                intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS,true); // 是否是直接打开相机
                                startActivityForResult(intent, REQUEST_CODE_SELECT);
                                break;
                            case 1:
                                //打开选择,本次允许选择的数量
                                ImagePicker.getInstance().setSelectLimit(maxImgCount - selImageList.size());
                                Intent intent1 = new Intent(NeedDoingPage.this, ImageGridActivity.class);
                                startActivityForResult(intent1, REQUEST_CODE_SELECT);
                                break;
                            default:
                                break;
                        }
                    }
                }, names);
                break;
            default:
                //打开预览
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
            //添加图片返回
            if (data != null && requestCode == REQUEST_CODE_SELECT) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (images != null){
                    selImageList.addAll(images);
                    adapter.setImages(selImageList);
                }
            }
        } else if (resultCode == ImagePicker.RESULT_CODE_BACK) {
            //预览图片返回
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
    private void uploadImage(ArrayList<ImageItem> pathList,String answer) {
        byte[][] pictures=new byte[pathList.size()][];
        List<String> strings=new ArrayList<>();
        int i=0;
        for(ImageItem a:pathList)
        {
            pictures[i]=ConvertImage(a);
            strings.add(Base64.encodeToString((pictures[i]),Base64.DEFAULT));
            i++;
        }
        String url = "http://10.0.2.2:8080/result/add";
        OkHttpClient client = new OkHttpClient();
        SharedPreferences sharedPreferences=getSharedPreferences("local_user",MODE_PRIVATE);
        String user=sharedPreferences.getString("user_id","");
        Integer userid=Integer.parseInt(user);
        Log.d("初始化","本地获取的的userIDID为"+userid);
        Log.d("----获取的参数为","needid:"+needid+"   userid:"+userid+"   text:"+editText.getText().toString()+"    picture"+pictures);
        FormBody.Builder requestBuild = new FormBody.Builder();
        RequestBody requestBody = requestBuild
                .add("needid",needid+"")
                .add("userid",userid+"")
                .add("text",editText.getText().toString())
                .add("picture",strings+"")
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("addresult","连接失败"+e.getLocalizedMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String  result = response.body().string();
                Log.d("addRsult成功",result);

            }
        });
        //Log.d("----获取的图片为",pathList+"");
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
    class QueryNeedById extends AsyncTask<Integer, Integer, String> {
        Interface inter = new Interface();
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(Integer... params) {
            Integer needid=params[0];
            HashMap map=new HashMap();
            map.put("needid",needid+"");
            String result=inter.doGet("http://10.0.2.2:8080/need/querybyneedid",map);

            return result;
        }
        @Override
        protected void onProgressUpdate(Integer... progress) {}

        @Override
        protected void onPostExecute(String text) {
            ItemNeed a= JSON.parseObject(text, ItemNeed.class);
            Log.d("----result","接受到的need为"+a);
            name.setText(a.getUserName());
            time.setText(a.getNeed().getTime().substring(5));
            title.setText(a.getNeed().getTitle());
            textview.setText(a.getNeed().getText());
            reward.setText(a.getNeed().getReward()+"积分");
            Log.d("----result","接受到的needstate为"+a.getState());
//            if(!a.getNeed().getState().equals("none"))
//            {
//                buttonview.setText("已被接受");
//                buttonview.setEnabled(false);
//            }
        }
    }

}
