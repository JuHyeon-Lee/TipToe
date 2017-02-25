package teamnova.myapplication.woongbi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import teamnova.myapplication.MusicMainScreenActivity;
import teamnova.myapplication.MusicServiceManager;
import teamnova.myapplication.R;


public class activity_main extends Activity {
    FrameLayout mHeader = null;
    ListView listView = null;
    ArrayList<String> arrayList = null;
    View fakeView,fakeView2;
    int button_hight =700;
    boolean down = true, once = true;
    MapView mapView;
    MapPOIItem marker;
    static public ArrayList<Data> data_list;
    static public Handler handler;
    int nowPlay;
    List_Adapter list_adapter;
    ImageButton down_image_I;
    TextView down_title_T;
    TextView down_artist_T;
    ImageButton down_image_btn;
    MusicServiceManager serviceManager;
    boolean start_btn_condition = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.woongbi_activity_main);

        New_class(); // 선언
        Get_Map(); // 맵 가져오기
        Listener(); // 리스너

        fakeView = getLayoutInflater().inflate(R.layout.woongbi_activity_main_item2, listView, false);
        fakeView2 = getLayoutInflater().inflate(R.layout.woongbi_activity_main_item3, listView, false);


        // 리스트뷰 데이터 입력
        list_adapter = new List_Adapter(getApplicationContext());
//        for(int i = 0; i < 30; i++)
//            arrayList.add("abc"+i);

        for(int i = 0; i < 20; i++){
            boolean alpha = true;
            if(i > 2)
                alpha = false;

            Data data = new Data(R.drawable.elbum_ex_001, "노래"+i, "사람"+i, true, i, false,R.raw.background, alpha);
            data_list.add(data);
        }

        listView.setAdapter(list_adapter);
        listView.addHeaderView(fakeView);





        nowPlay = 3;
        data_list.get(nowPlay).now_play = true;
//                Data data = new Data(data_list.get(i-1).image, data_list.get(i-1).title, data_list.get(i-1).artist, data_list.get(i-1).hart,data_list.get(i-1).hartcount, true, data_list.get(i-1).sound, data_list.get(i-1).alpha);

        list_adapter.notifyDataSetChanged();
//                Intent intent = new Intent(getApplicationContext(), MusicMainScreenActivity.class);
//                Log.d("main_position", ""+(i-1));
//                intent.putExtra("position",i-1);
//                startActivity(intent);



        down_image_I.setImageResource(data_list.get(nowPlay).image);
        down_title_T.setText(data_list.get(nowPlay).title.toString());
        down_artist_T.setText(data_list.get(nowPlay).artist);
        down_image_btn.setImageResource(R.drawable.btn_pause);


        serviceManager = new MusicServiceManager(activity_main.this, data_list.get(nowPlay).sound);
        serviceManager.start();

        start_btn_condition = false; // 일시정지 버튼





    }

    class Data {
        int image;
        String title;
        String artist;
        boolean hart;
        int hartcount;
        boolean now_play;
        int sound;
        boolean alpha;
        Data(int image, String title, String artist, boolean hart, int hartcount, boolean now_play, int sound, boolean alpha){
            this.image = image;
            this.title = title;
            this.artist = artist;
            this.hart = hart;
            this.hartcount = hartcount;
            this.now_play = now_play;
            this.sound = sound;
            this.alpha = alpha;
        }
    }


    class List_Adapter extends BaseAdapter{
        Context context = null;

        List_Adapter(Context context){
            this.context = context;
        }

        @Override
        public int getCount() {
            return data_list.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if(view == null){
                LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.woongbi_activity_main_item, viewGroup, false);
            }

//            TextView textView = (TextView)view.findViewById(R.id.tv_num_of_like);
//            textView.setText(arrayList.get(i));
            ImageView image_I = (ImageView) view.findViewById(R.id.image_I);
            TextView title_T = (TextView)view.findViewById(R.id.title_T);
            TextView artist_T = (TextView)view.findViewById(R.id.artist_T);
            ImageView item_hart = (ImageView)view.findViewById(R.id.item_hart);
            TextView item_hart_count = (TextView)view.findViewById(R.id.item_hart_count);

            image_I.setImageResource(data_list.get(i).image);
            title_T.setText(data_list.get(i).title);
            artist_T.setText(data_list.get(i).artist);
            item_hart_count.setText(data_list.get(i).hartcount+"");


            Log.d("list_nowplay", data_list.get(i).now_play+"");
            if(data_list.get(i).now_play){
                title_T.setTextColor(Color.parseColor("#F75B3B"));
                artist_T.setTextColor(Color.parseColor("#F75B3B"));
                artist_T.setAlpha(0.6f);
            }else{
                title_T.setTextColor(Color.parseColor("#000000"));
                artist_T.setTextColor(Color.parseColor("#000000"));
                artist_T.setAlpha(0.6f);
            }

            if(data_list.get(i).alpha){
                ((LinearLayout)view.findViewById(R.id.linear)).setAlpha(0.3f);
            }


            return view;
        }
    }

    void Listener(){
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
                int scrollY = getScrollY();
                //sticky actionbar
                Log.d("scrollY", scrollY+"");
                if(down)
                    mHeader.setTranslationY(Math.max(-scrollY, -scrollY));


                float ratio = clamp(mHeader.getTranslationY() / 0, 0.0f, 1.0f);
                //actionbar title alpha
//                ((Button)findViewById(R.id.up)).setAlpha(clamp(5.0F * ratio - 4.0F, 0.0F, 1.0F));
            }
        });

        ((Button)findViewById(R.id.up)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listView.removeHeaderView(fakeView2);
                listView.addHeaderView(fakeView);
                ((Button)findViewById(R.id.up)).setVisibility(View.GONE);
                mHeader.setTranslationY(Math.max(0, 0));
                listView.setSelection(0);
                once = true;
                down = true;
            }
        });

        ((Button)findViewById(R.id.mapButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(serviceManager != null)
                    serviceManager.stop();

                Log.d("nowplay", nowPlay+"");
                data_list.get(nowPlay).now_play = false;
                nowPlay = i-1;
                data_list.get(nowPlay).now_play = true;
//                Data data = new Data(data_list.get(i-1).image, data_list.get(i-1).title, data_list.get(i-1).artist, data_list.get(i-1).hart,data_list.get(i-1).hartcount, true, data_list.get(i-1).sound, data_list.get(i-1).alpha);

                list_adapter.notifyDataSetChanged();
//                Intent intent = new Intent(getApplicationContext(), MusicMainScreenActivity.class);
//                Log.d("main_position", ""+(i-1));
//                intent.putExtra("position",i-1);
//                startActivity(intent);



                down_image_I.setImageResource(data_list.get(nowPlay).image);
                down_title_T.setText(data_list.get(nowPlay).title.toString());
                down_artist_T.setText(data_list.get(nowPlay).artist);
                down_image_btn.setImageResource(R.drawable.btn_pause);


                serviceManager = new MusicServiceManager(activity_main.this, data_list.get(nowPlay).sound);
                serviceManager.start();

                start_btn_condition = false; // 일시정지 버튼

            }
        });

        down_image_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(start_btn_condition){
                    down_image_btn.setImageResource(R.drawable.btn_pause);
                    serviceManager.restart();
                    start_btn_condition = false;
                }else{
                    down_image_btn.setImageResource(R.drawable.btn_play);
                    serviceManager.pause();
                    start_btn_condition = true;
                }
            }
        });

        ((RelativeLayout)findViewById(R.id.relative)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MusicMainScreenActivity.class);
                Log.d("nowplay", nowPlay+"");
                intent.putExtra("position", nowPlay);
                startActivity(intent);
            }
        });
    }

    public static float clamp(float value, float max, float min) {
        return Math.max(Math.min(value, min), max);
    }

    void New_class(){
        mHeader = (FrameLayout)findViewById(R.id.header);
        listView = (ListView)findViewById(R.id.listview);
        arrayList = new ArrayList<>();
        data_list = new ArrayList<>();
        handler = new Handler(this);

        down_image_I = (ImageButton)findViewById(R.id.down_image_btn);
        down_title_T = (TextView)findViewById(R.id.title_T);
        down_artist_T = (TextView)findViewById(R.id.artist_T);
        down_image_btn = (ImageButton)findViewById(R.id.image_btn);
    }



    void Get_Map(){
        mapView = new MapView(this);
        mapView.setDaumMapApiKey("83464058cb1b1d7ee53c7ee5f0b99170");

        ViewGroup viewGroup = (ViewGroup)findViewById(R.id.mapView);
        viewGroup.addView(mapView);


//        marker = new MapPOIItem();
//        marker.setItemName("나");
//        marker.setTag(0);
//        marker.setMarkerType(MapPOIItem.MarkerType.CustomImage); // 기본으로 제공하는 BluePin 마커 모양.
//        marker.setCustomImageResourceId(R.drawable.marker01);
//        marker.setSelectedMarkerType(MapPOIItem.MarkerType.CustomImage); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
//        marker.setCustomSelectedImageResourceId(R.drawable.marker01);
//
//        marker.moveWithAnimation(MapPoint.mapPointWithGeoCoord(37.5649932, 126.9872227), true);
//        mapView.addPOIItem(marker);
////        if (gotomelocation) {
//            mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(37.5649932, 126.9872227), true);
////            gotomelocation = false;
//            mapView.setZoomLevel(0, true);
////        }

    }


    public int getScrollY() {
        View c = listView.getChildAt(0);
        if (c == null) {
            return 0;
        }

        int firstVisiblePosition = listView.getFirstVisiblePosition();
        int top = c.getTop();

        int headerHeight = 0;
        if (firstVisiblePosition >= 1) {
            headerHeight = mHeader.getHeight(); // 수정
        }

        int result = -top + firstVisiblePosition * c.getHeight() + headerHeight;
        if(result > button_hight) {
            down = false;
            result = button_hight;
            if(once) {
                listView.removeHeaderView(fakeView);
                listView.addHeaderView(fakeView2);
                listView.setSelection(0);
                ((Button)findViewById(R.id.up)).setVisibility(View.VISIBLE);
//                DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics();
//                int height = dm.heightPixels;


//                ViewGroup.LayoutParams params = listView.getLayoutParams();
//                params.height = height-button_hight+40;
//                listView.setLayoutParams(params);

                once = false;
            }
        }else{

        }

        return result;
    }


    public void handleMessage(Message msg) {
        Bundle bundle = msg.getData();
        int position = (int) bundle.get("position");

        data_list.get(nowPlay).now_play = false;
        nowPlay = position;
        data_list.get(nowPlay).now_play = true;
        list_adapter.notifyDataSetChanged();
    }


    class Handler extends android.os.Handler {
        WeakReference<activity_main> activity;

        Handler(activity_main main){
            activity = new WeakReference<activity_main>(main);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            activity_main activity = this.activity.get();
            if(activity != null){
                activity.handleMessage(msg);
            }

        }
    }
}
