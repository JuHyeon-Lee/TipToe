package teamnova.myapplication.woongbi;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import net.daum.mf.map.api.MapView;

import java.util.ArrayList;

import teamnova.myapplication.R;


public class activity_main extends Activity {
    FrameLayout mHeader = null;
    ListView listView = null;
    ArrayList<String> arrayList = null;
    View fakeView,fakeView2;
    int button_hight = 370;
    boolean down = true, once = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.woongbi_activity_main);

        New_class(); // 선언
        Get_Map(); // 맵 가져오기
        Listener(); // 리스너

        fakeView = getLayoutInflater().inflate(R.layout.woongbi_activity_main_item2, listView, false);
        fakeView2 = getLayoutInflater().inflate(R.layout.woongbi_activity_main_item3, listView, false);

        List_Adapter list_adapter = new List_Adapter(getApplicationContext());
        for(int i = 0; i < 30; i++)
            arrayList.add("abc"+i);
        listView.setAdapter(list_adapter);
        listView.addHeaderView(fakeView);

    }


    class List_Adapter extends BaseAdapter{
        Context context = null;

        List_Adapter(Context context){
            this.context = context;
        }

        @Override
        public int getCount() {
            return arrayList.size();
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

            TextView textView = (TextView)view.findViewById(R.id.tv_num_of_like);
            textView.setText(arrayList.get(i));

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
    }

    public static float clamp(float value, float max, float min) {
        return Math.max(Math.min(value, min), max);
    }

    void New_class(){
        mHeader = (FrameLayout)findViewById(R.id.header);
        listView = (ListView)findViewById(R.id.listview);
        arrayList = new ArrayList<>();
    }


    void Get_Map(){
        MapView mapView = new MapView(this);
        mapView.setDaumMapApiKey("83464058cb1b1d7ee53c7ee5f0b99170");

        ViewGroup viewGroup = (ViewGroup)findViewById(R.id.mapView);
        viewGroup.addView(mapView);
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
