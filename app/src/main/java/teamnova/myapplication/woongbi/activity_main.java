package teamnova.myapplication.woongbi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import net.daum.mf.map.api.CameraUpdateFactory;
import net.daum.mf.map.api.MapCircle;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapPointBounds;
import net.daum.mf.map.api.MapView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import teamnova.myapplication.Data;
import teamnova.myapplication.MusicMainScreenActivity;
import teamnova.myapplication.MusicServiceManager;
import teamnova.myapplication.PopUpActivity;
import teamnova.myapplication.R;

public class activity_main extends Activity implements MapView.MapViewEventListener, MapView.POIItemEventListener {
    FrameLayout mHeader = null;
    SwipeMenuListView listView = null;
    ArrayList<String> arrayList = null;
    View fakeView, fakeView2;
    int button_hight = 790;
    boolean down = true, once = true;
    MapView mapView;
    MapPOIItem customMarker;
    MapPOIItem marker;

    static public ArrayList<Data> data_list = new ArrayList<Data>();

    static public Handler handler;
    int nowPlay;
    List_Adapter list_adapter;
    ImageButton down_image_I;
    TextView down_title_T;
    TextView down_artist_T;
    ImageButton down_image_btn;
    MusicServiceManager serviceManager;
    boolean start_btn_condition = true;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

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

        for (int i = 0; i < 20; i++) {
            boolean alpha = true;
            if (i > 2)
                alpha = false;
            Data data = new Data(R.drawable.elbum_ex_001, "노래" + i, "사람" + i, true, i, false, R.raw.background, alpha);
            data_list.add(data);
        }

        listView.setAdapter(list_adapter);
        listView.addHeaderView(fakeView);


        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {

                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                deleteItem.setTitle("삭제");
                deleteItem.setTitleSize(18);
                deleteItem.setTitleColor(Color.WHITE);
                deleteItem.setWidth(dp2px(80));
                menu.addMenuItem(deleteItem);
            }
        };
        listView.setMenuCreator(creator);
//        listView.setOnMenuStateChangeListener(new SwipeMenuListView.OnMenuStateChangeListener() {
//            @Override
//            public void onMenuOpen(int position) {
//                position = position - 1;
//                Toast.makeText(getApplicationContext(), ""+position, Toast.LENGTH_SHORT).show();
//                if(data_list.get(position).alpha){
//                    listView.setMenuCreator(creator_alpha);
//                    nowPlay--;
//                    data_list.remove(position);
//                    list_adapter.notifyDataSetChanged();
//                }else{
//
//                }
//            }
//
//            @Override
//            public void onMenuClose(int position) {
//
//            }
//        });
        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                nowPlay--;
                data_list.remove(position);
                list_adapter.notifyDataSetChanged();

                return false;
            }
        });
        listView.setSwipeDirection(SwipeMenuListView.DIRECTION_RIGHT);
        listView.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private int dp2px(int dp) {
        float scale = getResources().getDisplayMetrics().density;
        int pixels = (int) (dp * scale + 0.5f);
        return pixels;
    }

    @Override
    public void onMapViewInitialized(MapView mapView) {


        // 중심점 변경
        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(37.5649932, 126.9872227), true);

//        MapPOIItem marker = new MapPOIItem();
//        marker.setItemName("Default Marker");
//        marker.setTag(0);
//        marker.setMapPoint(MapPoint.mapPointWithGeoCoord(37.5649932,126.9872227));
//        marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
//        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
//
//        mapView.addPOIItem(marker);


        customMarker = new MapPOIItem();
        customMarker.setItemName("내 위치");
        customMarker.setTag(1);
        customMarker.setMapPoint(MapPoint.mapPointWithGeoCoord(37.565116, 126.987194));
        customMarker.setMarkerType(MapPOIItem.MarkerType.CustomImage); // 마커타입을 커스텀 마커로 지정.
        customMarker.setCustomImageResourceId(R.drawable.marker01); // 마커 이미지.
        customMarker.setCustomImageAutoscale(true); // hdpi, xhdpi 등 안드로이드 플랫폼의 스케일을 사용할 경우 지도 라이브러리의 스케일 기능을 꺼줌.
        customMarker.setCustomImageAnchor(0.5f, 1.0f); // 마커 이미지중 기준이 되는 위치(앵커포인트) 지정 - 마커 이미지 좌측 상단 기준 x(0.0f ~ 1.0f), y(0.0f ~ 1.0f) 값.

        mapView.addPOIItem(customMarker);


        customMarker = new MapPOIItem();
        customMarker.setItemName("위워크");
        customMarker.setTag(2);
        customMarker.setMapPoint(MapPoint.mapPointWithGeoCoord(37.5649952, 126.9872267));
        customMarker.setMarkerType(MapPOIItem.MarkerType.CustomImage); // 마커타입을 커스텀 마커로 지정.
        customMarker.setCustomImageResourceId(R.drawable.marker02); // 마커 이미지.
        customMarker.setCustomImageAutoscale(false); // hdpi, xhdpi 등 안드로이드 플랫폼의 스케일을 사용할 경우 지도 라이브러리의 스케일 기능을 꺼줌.
        customMarker.setCustomImageAnchor(0.5f, 1.0f); // 마커 이미지중 기준이 되는 위치(앵커포인트) 지정 - 마커 이미지 좌측 상단 기준 x(0.0f ~ 1.0f), y(0.0f ~ 1.0f) 값.

        mapView.addPOIItem(customMarker);

        customMarker = new MapPOIItem();
        customMarker.setItemName("향린교회");
        customMarker.setTag(3);
        customMarker.setMapPoint(MapPoint.mapPointWithGeoCoord(37.565618, 126.986686));
        customMarker.setMarkerType(MapPOIItem.MarkerType.CustomImage); // 마커타입을 커스텀 마커로 지정.
        customMarker.setCustomImageResourceId(R.drawable.marker03); // 마커 이미지.
        customMarker.setCustomImageAutoscale(false); // hdpi, xhdpi 등 안드로이드 플랫폼의 스케일을 사용할 경우 지도 라이브러리의 스케일 기능을 꺼줌.
        customMarker.setCustomImageAnchor(0.5f, 1.0f); // 마커 이미지중 기준이 되는 위치(앵커포인트) 지정 - 마커 이미지 좌측 상단 기준 x(0.0f ~ 1.0f), y(0.0f ~ 1.0f) 값.

        mapView.addPOIItem(customMarker);


        customMarker = new MapPOIItem();
        customMarker.setItemName("SK건설");
        customMarker.setTag(4);
        customMarker.setMapPoint(MapPoint.mapPointWithGeoCoord(37.565653, 126.987947));
        customMarker.setMarkerType(MapPOIItem.MarkerType.CustomImage); // 마커타입을 커스텀 마커로 지정.
        customMarker.setCustomImageResourceId(R.drawable.marker03); // 마커 이미지.
        customMarker.setCustomImageAutoscale(false); // hdpi, xhdpi 등 안드로이드 플랫폼의 스케일을 사용할 경우 지도 라이브러리의 스케일 기능을 꺼줌.
        customMarker.setCustomImageAnchor(0.5f, 1.0f); // 마커 이미지중 기준이 되는 위치(앵커포인트) 지정 - 마커 이미지 좌측 상단 기준 x(0.0f ~ 1.0f), y(0.0f ~ 1.0f) 값.

        mapView.addPOIItem(customMarker);


        MapCircle circle1 = new MapCircle(
                MapPoint.mapPointWithGeoCoord(37.5649932, 126.9872227), // center
                50, // radius
                Color.argb(100, 247, 91, 59), // strokeColor
                Color.argb(100, 247, 91, 59) // fillColor
        );
        circle1.setTag(1234);
        mapView.addCircle(circle1);

//
// 지도뷰의 중심좌표와 줌레벨을 Circle이 모두 나오도록 조정.
        MapPointBounds[] mapPointBoundsArray = {circle1.getBound()};
        MapPointBounds mapPointBounds = new MapPointBounds(mapPointBoundsArray);
        int padding = 50; // px
        mapView.moveCamera(CameraUpdateFactory.newMapPointBounds(mapPointBounds, padding));
    }

    @Override
    public void onMapViewCenterPointMoved(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewZoomLevelChanged(MapView mapView, int i) {

    }

    @Override
    public void onMapViewSingleTapped(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDoubleTapped(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewLongPressed(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDragStarted(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDragEnded(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewMoveFinished(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onPOIItemSelected(MapView mapView, MapPOIItem mapPOIItem) {

        switch (mapPOIItem.getTag()) {
            case 1: //내위치
//                Intent intent = new Intent(activity_main.this , PopUpActivity.class);
//                intent.putExtra("kind",1);
//                startActivity(intent);
                break;
            case 2: //워크
                Intent intent2 = new Intent(activity_main.this, PopUpActivity.class);
                intent2.putExtra("kind", 2);
                startActivity(intent2);
                break;
//            case 1234: //워크
//                Intent intent5 = new Intent(activity_main.this, PopUpActivity.class);
//                intent5.putExtra("kind", 2);
//                startActivity(intent5);
//                break;
            case 3: //교회
                Intent intent3 = new Intent(activity_main.this, PopUpActivity.class);
                intent3.putExtra("kind", 3);

                startActivity(intent3);
                break;
            case 4: //건설
                Intent intent4 = new Intent(activity_main.this, PopUpActivity.class);
                intent4.putExtra("kind", 4);
                startActivity(intent4);
                break;
            default:
                break;
        }

    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem) {

    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem
            mapPOIItem, MapPOIItem.CalloutBalloonButtonType calloutBalloonButtonType) {

    }

    @Override
    public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem mapPOIItem, MapPoint
            mapPoint) {

    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("activity_main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }


    class List_Adapter extends BaseAdapter {
        Context context = null;

        List_Adapter(Context context) {
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
            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.woongbi_activity_main_item, viewGroup, false);
            }

//            TextView textView = (TextView)view.findViewById(R.id.tv_num_of_like);
//            textView.setText(arrayList.get(i));
            ImageView image_I = (ImageView) view.findViewById(R.id.image_I);
            TextView title_T = (TextView) view.findViewById(R.id.title_T);
            TextView artist_T = (TextView) view.findViewById(R.id.artist_T);
            ImageView item_hart = (ImageView) view.findViewById(R.id.item_hart);
            TextView item_hart_count = (TextView) view.findViewById(R.id.item_hart_count);

            image_I.setImageResource(data_list.get(i).image);
            title_T.setText(data_list.get(i).title);
            artist_T.setText(data_list.get(i).artist);
            item_hart_count.setText(data_list.get(i).hartcount + "");


            if (data_list.get(i).now_play) {
                title_T.setTextColor(Color.parseColor("#F75B3B"));
                artist_T.setTextColor(Color.parseColor("#F75B3B"));
                artist_T.setAlpha(0.6f);
            } else {
                title_T.setTextColor(Color.parseColor("#000000"));
                artist_T.setTextColor(Color.parseColor("#000000"));
                artist_T.setAlpha(0.6f);
            }

            Log.d("list_nowalpha", data_list.get(i).alpha + "");
            if (data_list.get(i).alpha) {
                ((LinearLayout) view.findViewById(R.id.linear)).setAlpha(0.3f);
            } else {
                ((LinearLayout) view.findViewById(R.id.linear)).setAlpha(1f);
            }


            return view;
        }
    }

    void Listener() {
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
                int scrollY = getScrollY();
                //sticky actionbar
                Log.d("scrollY", scrollY + "");
                if (down)
                    mHeader.setTranslationY(Math.max(-scrollY, -scrollY));


                float ratio = clamp(mHeader.getTranslationY() / 0, 0.0f, 1.0f);
                //actionbar title alpha
//                ((Button)findViewById(R.id.up)).setAlpha(clamp(5.0F * ratio - 4.0F, 0.0F, 1.0F));
            }
        });

        ((Button) findViewById(R.id.up)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listView.removeHeaderView(fakeView2);
                listView.addHeaderView(fakeView);
                ((Button) findViewById(R.id.up)).setVisibility(View.GONE);
                mHeader.setTranslationY(Math.max(0, 0));
                listView.setSelection(0);
                once = true;
                down = true;
            }
        });

        ((Button) findViewById(R.id.mapButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (serviceManager != null)
                    serviceManager.stop();

                Log.d("nowplay", nowPlay + "");
                data_list.get(nowPlay).now_play = false;
                nowPlay = i - 1;
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
                if (start_btn_condition) {
                    down_image_btn.setImageResource(R.drawable.btn_pause);
                    serviceManager.restart();
                    start_btn_condition = false;
                } else {
                    down_image_btn.setImageResource(R.drawable.btn_play);
                    serviceManager.pause();
                    start_btn_condition = true;
                }
            }
        });

        ((RelativeLayout) findViewById(R.id.relative)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MusicMainScreenActivity.class);
                Log.d("nowplay", nowPlay + "");
                intent.putExtra("position", nowPlay);
                startActivity(intent);
            }
        });
        down_image_I.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MusicMainScreenActivity.class);
                Log.d("nowplay", nowPlay + "");
                intent.putExtra("position", nowPlay);
                startActivity(intent);
            }
        });
    }

    public static float clamp(float value, float max, float min) {
        return Math.max(Math.min(value, min), max);
    }

    void New_class() {
        mHeader = (FrameLayout) findViewById(R.id.header);
        listView = (SwipeMenuListView) findViewById(R.id.listview);
        arrayList = new ArrayList<>();
        data_list = new ArrayList<>();
        handler = new Handler(this);

        down_image_I = (ImageButton) findViewById(R.id.down_image_btn);
        down_title_T = (TextView) findViewById(R.id.title_T);
        down_artist_T = (TextView) findViewById(R.id.artist_T);
        down_image_btn = (ImageButton) findViewById(R.id.image_btn);
    }


    void Get_Map() {
        mapView = new MapView(this);
        mapView.setDaumMapApiKey("83464058cb1b1d7ee53c7ee5f0b99170");

        ViewGroup viewGroup = (ViewGroup) findViewById(R.id.mapView);
        viewGroup.addView(mapView);

        mapView.setMapViewEventListener(this); // this에 MapView.MapViewEventListener 구현.
        mapView.setPOIItemEventListener(this);

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
        if (result > button_hight) {
            down = false;
            result = button_hight;
            if (once) {
                listView.removeHeaderView(fakeView);
                listView.addHeaderView(fakeView2);
                listView.setSelection(0);
                ((Button) findViewById(R.id.up)).setVisibility(View.VISIBLE);
//                DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics();
//                int height = dm.heightPixels;


//                ViewGroup.LayoutParams params = listView.getLayoutParams();
//                params.height = height-button_hight+40;
//                listView.setLayoutParams(params);

                once = false;
            }
        } else {

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

        Handler(activity_main main) {
            activity = new WeakReference<activity_main>(main);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            activity_main activity = this.activity.get();
            if (activity != null) {
                activity.handleMessage(msg);
            }

        }
    }

    @Override
    protected void onStart() {
        super.onStart();// ATTENTION: This was auto-generated to implement the App Indexing API.
// See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        RelativeLayout playing = (RelativeLayout) findViewById(R.id.relative);
        playing.bringToFront();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }
}

