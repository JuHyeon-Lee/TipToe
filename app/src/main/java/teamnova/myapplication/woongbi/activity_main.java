package teamnova.myapplication.woongbi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import net.daum.mf.map.api.MapCircle;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.util.ArrayList;

import teamnova.myapplication.PopUpActivity;
import teamnova.myapplication.R;


public class activity_main extends Activity implements MapView.MapViewEventListener, MapView.POIItemEventListener {
    FrameLayout mHeader = null;
    ListView listView = null;
    ArrayList<String> arrayList = null;
    View fakeView,fakeView2;
    int button_hight =700;
    boolean down = true, once = true;
    MapView mapView;
    MapPOIItem customMarker;
    ArrayList<Data> data_list;

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
        List_Adapter list_adapter = new List_Adapter(getApplicationContext());
//        for(int i = 0; i < 30; i++)
//            arrayList.add("abc"+i);
        for(int i = 0; i < 20; i++){
            Data data = new Data(R.drawable.elbum_ex_002, "노래"+i, "사람"+i, true, i, false , R.raw.background ,false);


            data_list.add(data);
        }

        listView.setAdapter(list_adapter);
        listView.addHeaderView(fakeView);


    }

    @Override
    public void onMapViewInitialized(MapView mapView) {


        // 중심점 변경
        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(37.5649932,126.9872227), true);

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
        customMarker.setMapPoint(MapPoint.mapPointWithGeoCoord(37.5649952,126.9872267));
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
        customMarker.setCustomImageResourceId(R.drawable.marker02); // 마커 이미지.
        customMarker.setCustomImageAutoscale(false); // hdpi, xhdpi 등 안드로이드 플랫폼의 스케일을 사용할 경우 지도 라이브러리의 스케일 기능을 꺼줌.
        customMarker.setCustomImageAnchor(0.5f, 1.0f); // 마커 이미지중 기준이 되는 위치(앵커포인트) 지정 - 마커 이미지 좌측 상단 기준 x(0.0f ~ 1.0f), y(0.0f ~ 1.0f) 값.

        mapView.addPOIItem(customMarker);




        customMarker = new MapPOIItem();
        customMarker.setItemName("SK건설");
        customMarker.setTag(4);
        customMarker.setMapPoint(MapPoint.mapPointWithGeoCoord(37.565653, 126.987947));
        customMarker.setMarkerType(MapPOIItem.MarkerType.CustomImage); // 마커타입을 커스텀 마커로 지정.
        customMarker.setCustomImageResourceId(R.drawable.marker02); // 마커 이미지.
        customMarker.setCustomImageAutoscale(false); // hdpi, xhdpi 등 안드로이드 플랫폼의 스케일을 사용할 경우 지도 라이브러리의 스케일 기능을 꺼줌.
        customMarker.setCustomImageAnchor(0.5f, 1.0f); // 마커 이미지중 기준이 되는 위치(앵커포인트) 지정 - 마커 이미지 좌측 상단 기준 x(0.0f ~ 1.0f), y(0.0f ~ 1.0f) 값.

        mapView.addPOIItem(customMarker);












        MapCircle circle1 = new MapCircle(
                MapPoint.mapPointWithGeoCoord(37.5649932,126.9872227), // center
                50, // radius
                Color.argb(128, 255, 0, 0), // strokeColor
                Color.argb(128, 255, 255, 0) // fillColor
        );
        circle1.setTag(1234);
        mapView.addCircle(circle1);

//
//// 지도뷰의 중심좌표와 줌레벨을 Circle이 모두 나오도록 조정.
//        MapPointBounds[] mapPointBoundsArray = { circle1.getBound()};
//        MapPointBounds mapPointBounds = new MapPointBounds(mapPointBoundsArray);
//        int padding = 50; // px
//        mapView.moveCamera(CameraUpdateFactory.newMapPointBounds(mapPointBounds, padding));
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

        switch (mapPOIItem.getTag()){
            case 1: //내위치
//                Intent intent = new Intent(activity_main.this , PopUpActivity.class);
//                intent.putExtra("kind",1);
//                startActivity(intent);
                break;
            case 2: //워크
                Intent intent2 = new Intent(activity_main.this , PopUpActivity.class);
                intent2.putExtra("kind",2);
                startActivity(intent2);
                break;
            case 3: //교회
                Intent intent3 = new Intent(activity_main.this , PopUpActivity.class);
                intent3.putExtra("kind",3);

                startActivity(intent3);
                break;
            case 4: //건설
                Intent intent4 = new Intent(activity_main.this , PopUpActivity.class);
                intent4.putExtra("kind",4);
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
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem, MapPOIItem.CalloutBalloonButtonType calloutBalloonButtonType) {

    }

    @Override
    public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem mapPOIItem, MapPoint mapPoint) {

    }

    public class Data {
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
            this.sound = sound;
            this.now_play = now_play;
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

            ImageView image_I = (ImageView) view.findViewById(R.id.image_I);
            TextView title_T = (TextView)view.findViewById(R.id.title_T);
            TextView artist_T = (TextView)view.findViewById(R.id.artist_T);
            ImageView item_hart = (ImageView)view.findViewById(R.id.item_hart);
            TextView item_hart_count = (TextView)view.findViewById(R.id.item_hart_count);

            image_I.setImageResource(data_list.get(i).image);
            title_T.setText(data_list.get(i).title);
            artist_T.setText(data_list.get(i).artist);
            item_hart_count.setText(data_list.get(i).hartcount+"");


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
//                getActionBarTitleView().setAlpha(clamp(5.0F * ratio - 4.0F, 0.0F, 1.0F));
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
    }

    public static float clamp(float value, float max, float min) {
        return Math.max(Math.min(value, min), max);
    }

    void New_class(){
        mHeader = (FrameLayout)findViewById(R.id.header);
        listView = (ListView)findViewById(R.id.listview);
        arrayList = new ArrayList<>();
        data_list = new ArrayList<>();
    }


    void Get_Map(){
        mapView = new MapView(this);
        mapView.setDaumMapApiKey("83464058cb1b1d7ee53c7ee5f0b99170");

        ViewGroup viewGroup = (ViewGroup)findViewById(R.id.mapView);
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

}
