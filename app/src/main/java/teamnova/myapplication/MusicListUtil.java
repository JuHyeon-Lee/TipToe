package teamnova.myapplication;

import java.util.ArrayList;

class MusicListUtil {

    public static ArrayList<Data> 위워크에등록된음악리스트 = new ArrayList<Data>();
    public static ArrayList<Data> 평린교회에등록된음악리스트 = new ArrayList<Data>();
    public static ArrayList<Data> SK건설에등록된음악리스트 = new ArrayList<Data>();
    public static ArrayList<Data> 내가등록한음악리스트 = new ArrayList<Data>();

    MusicListUtil(){
        for(int i = 0; i < 20; i++){
            Data data = new Data(R.drawable.elbum_ex_001, "위워크노래"+i, "사람"+i, true, i, false , R.raw.background ,false);
            위워크에등록된음악리스트.add(data);
        }
        for(int i = 0; i < 20; i++){
            Data data = new Data(R.drawable.elbum_ex_002, "평린교회노래"+i, "사람"+i, true, i, false , R.raw.background ,false);
            평린교회에등록된음악리스트.add(data);
        }
        for(int i = 0; i < 20; i++){
            Data data = new Data(R.drawable.elbum_ex_003, "SK건설노래"+i, "사람"+i, true, i, false , R.raw.background ,false);
            SK건설에등록된음악리스트.add(data);
        }

        for(int i = 0; i < 20; i++){
            Data data = new Data(R.drawable.elbum_ex_004, "좋은노래"+i, "사람"+i, true, i, false , R.raw.background ,false);
            내가등록한음악리스트.add(data);
        }

    }


}