package teamnova.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class PopUpActivity extends Activity {

    myAdapter Adapter;
    Switch ar_switch;
    Handler handler = new Handler();
    TextView title;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_dialog_popup);

        ar_switch = (Switch) findViewById(R.id.popup_ar_switch);
        title = (TextView) findViewById(R.id.location_title);

        ar_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
//                Log.e("TAG","CHEKC 진입");
                if(isChecked) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }


                            Intent intent = new Intent(PopUpActivity.this, ARActivity.class);
                            intent.putExtra("kind", getIntent().getIntExtra("kind", -1));
                            startActivity(intent);

                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                ar_switch.setChecked(false);
                            }
                        });
                        }
                    }).start();
                }


            }

        });


        switch(getIntent().getIntExtra("kind", -1)){
            case 2://위워크
                title.setText("위워크 을지로 점");
                Adapter = new myAdapter(getApplicationContext(), R.layout.dialog_popup_item, MusicListUtil.위워크에등록된음악리스트);
                ListView list = (ListView) findViewById(R.id.popup_list);

                list.setAdapter(Adapter);
                list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                list.setDividerHeight(2);
                break;
            case 3://교회
                title.setText("평린 교회");
                ar_switch.setVisibility(View.INVISIBLE);
                Adapter = new myAdapter(getApplicationContext(), R.layout.dialog_popup_item, MusicListUtil.평린교회에등록된음악리스트);
                ListView list2 = (ListView) findViewById(R.id.popup_list);

                list2.setAdapter(Adapter);
                list2.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                list2.setDividerHeight(2);
                break;
            case 4://건설
                title.setText("SK 건설 을지로점");
                ar_switch.setVisibility(View.INVISIBLE);
                Adapter = new myAdapter(getApplicationContext(), R.layout.dialog_popup_item, MusicListUtil.SK건설에등록된음악리스트);
                ListView list3 = (ListView) findViewById(R.id.popup_list);

                list3.setAdapter(Adapter);
                list3.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                list3.setDividerHeight(2);
                break;
            default:
                break;
        }

    }


    public class myAdapter extends BaseAdapter {
        Context con;
        LayoutInflater inflater;
        ArrayList<Data> components_list;
        int layout;

        myAdapter(Context context, int layout, ArrayList<Data> components_list) {
            con = context;
            this.layout = layout;
            this.components_list = components_list;
            inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            // 멤버변수 초기화
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return components_list.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return components_list.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            ViewHolder holder;

            if (null == convertView) {
                convertView = inflater.inflate(layout, parent, false);
                holder = new ViewHolder();

                holder.Image = (ImageView) convertView.findViewById(R.id.popup_image_I);
                holder.artist_name = (TextView) convertView.findViewById(R.id.popup_artist_T);
                holder.music_title = (TextView) convertView.findViewById(R.id.popup_title_T);
                //cells 를 뷰화시켜서 아이템목록으로 삽입
                convertView.setTag(holder);
            }else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.music_title.setText(components_list.get(position).title);
            holder.artist_name.setText(components_list.get(position).artist);
            Glide.with(PopUpActivity.this).load(components_list.get(position).image).into(holder.Image);

            return convertView;
        }

    }
    static class ViewHolder {
        ImageView Image;
        TextView music_title;
        TextView artist_name;

    }

}
