package teamnova.myapplication.woongbi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapView;

import java.util.ArrayList;

import teamnova.myapplication.MusicMainScreenActivity;
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
            boolean alpha = true;
            if(i > 10)
                alpha = false;

            Data data = new Data(R.drawable.elbum_ex_002, "노래"+i, "사람"+i, true, i, R.raw.background, alpha);
            data_list.add(data);
        }

        listView.setAdapter(list_adapter);
        listView.addHeaderView(fakeView);
    }

    public class Data {
        int image;
        String title;
        String artist;
        boolean hart;
        int hartcount;
        int sound;
        boolean alpha;
        Data(int image, String title, String artist, boolean hart, int hartcount, int sound, boolean alpha){
            this.image = image;
            this.title = title;
            this.artist = artist;
            this.hart = hart;
            this.hartcount = hartcount;
            this.sound = sound;
            this.alpha = alpha;
        }

        public int getSound() {
            return sound;
        }

        public int getHartcount() {
            return hartcount;
        }

        public String getArtist() {
            return artist;
        }

        public String getTitle() {
            return title;
        }

        public int getImage() {
            return image;
        }

        public boolean isHart() {
            return hart;
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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), MusicMainScreenActivity.class);
                Log.d("main_position", ""+(i-1));
                intent.putExtra("position",i-1);
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

}
