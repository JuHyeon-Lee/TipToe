package teamnova.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    myAdapter Adapter;
    EditText search_text;
    ArrayList<Data> search_list = new ArrayList<>();
    ProgressDialog dialog;
    ListView list;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        Adapter = new myAdapter(getApplicationContext(), R.layout.activity_search_list_item, search_list);
        list = (ListView) findViewById(R.id.search_list);
        list.setAdapter(Adapter);
        list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        list.setDividerHeight(2);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                dialog = ProgressDialog.show(SearchActivity.this, "", "등록 중입니다.", true);
                dialog.show();

                EndDialog endDialog = new EndDialog();
                endDialog.start();
            }
        });



        search_text = (EditText) findViewById(R.id.searchtext);
        search_text.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                // you can call or do what you want with your EditText here

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {

//                Log.e("SEARCH", String.valueOf(s));
                search_list.clear();
                for (Data data : MusicListUtil.서버에있는음악리스트) {
                    if (data != null && data.title.contains(s)) {
                        //something here

//                        Log.e("SEARCH", data.title);
                        search_list.add(data);
                    }

                }
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        dialog = ProgressDialog.show(SearchActivity.this, "", "등록 중입니다.", true);
                        dialog.show();

                        MusicListUtil.내가등록한음악리스트.add(search_list.get(i));

                        EndDialog endDialog = new EndDialog();
                        endDialog.start();
                    }
                });

                Adapter.notifyDataSetChanged();



            }

        });


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

                holder.Image = (ImageView) convertView.findViewById(R.id.search_image);
                holder.artist_name = (TextView) convertView.findViewById(R.id.search_artist);
                holder.music_title = (TextView) convertView.findViewById(R.id.search_title);
                holder.like_cnt = (TextView) convertView.findViewById(R.id.search_like_cnt);
                holder.like_boolean = (ImageView) convertView.findViewById(R.id.search_like);


                //cells 를 뷰화시켜서 아이템목록으로 삽입
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.music_title.setText(components_list.get(position).title);
            holder.artist_name.setText(components_list.get(position).artist);
            Glide.with(SearchActivity.this).load(components_list.get(position).image).into(holder.Image);
            holder.like_cnt.setText(String.valueOf(components_list.get(position).hartcount));

            return convertView;
        }

    }

    class ViewHolder {
        ImageView Image;
        TextView music_title;
        TextView artist_name;
        TextView like_cnt;
        ImageView like_boolean;

    }
    private Handler DialogHandler = new Handler (){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            dialog.dismiss();

            finish();
//            startActivity(new Intent(getApplicationContext(), activity_main.class));
//            startActivity(intent);
        }
    };

    private class EndDialog extends Thread {
        public void run(){

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            DialogHandler.sendEmptyMessage(0);
        }
    }
}
