package teamnova.myapplication;

import java.util.ArrayList;

public class MusicListUtil {

    public static ArrayList<Data> 위워크에등록된음악리스트 = new ArrayList<Data>();
    public static ArrayList<Data> 평린교회에등록된음악리스트 = new ArrayList<Data>();
    public static ArrayList<Data> SK건설에등록된음악리스트 = new ArrayList<Data>();
    public static ArrayList<Data> 내가등록한음악리스트 = new ArrayList<Data>();
    public static ArrayList<Data> 서버에있는음악리스트 = new ArrayList<Data>();

    MusicListUtil(){

//
//        for(int i = 0; i < 20; i++){
//            Data data = new Data(R.drawable.elbum_ex_001, "위워크노래"+i, "사람"+i, true, i, false , R.raw.background ,false);
//            위워크에등록된음악리스트.add(data);
//        }
//        for(int i = 0; i < 20; i++){
//            Data data = new Data(R.drawable.elbum_ex_002, "평린교회노래"+i, "사람"+i, true, i, false , R.raw.background ,false);
//            평린교회에등록된음악리스트.add(data);
//        }
//        for(int i = 0; i < 20; i++){
//            Data data = new Data(R.drawable.elbum_ex_003, "SK건설노래"+i, "사람"+i, true, i, false , R.raw.background ,false);
//            SK건설에등록된음악리스트.add(data);
//        }
//
//        for(int i = 0; i < 20; i++){
//            Data data = new Data(R.drawable.elbum_ex_004, "좋은노래"+i, "사람"+i, true, i, false , R.raw.background ,false);
//            내가등록한음악리스트.add(data);
//        }
//
//        for(int i = 0; i < 20; i++){
//            Data data = new Data(R.drawable.elbum_ex_005, "서버에있는노래"+i, "사람"+i, true, i, false , R.raw.background ,false);
//            서버에있는음악리스트.add(data);
//        }

        위워크노래추가();
        평린교회노래추가();
        SK건설노래추가();
        내노래추가();
        서버노래추가();

    }

    void 위워크노래추가(){
        위워크에등록된음악리스트.add(new Data(R.drawable.p025, "소원", "어반자카파", true, 34, false , R.raw.m025wish ,false));
        위워크에등록된음악리스트.add(new Data(R.drawable.p026, "널 생각해", "원 모어 찬스", true, 40, false , R.raw.m026ithinku ,false));
        위워크에등록된음악리스트.add(new Data(R.drawable.p027, "백색소음", "자연의 소리", true, 18, false , R.raw.m027whitenoise ,false));
        위워크에등록된음악리스트.add(new Data(R.drawable.p028, "너였다면", "정승환", true, 7, false , R.raw.m028ifiamdyou ,false));
        위워크에등록된음악리스트.add(new Data(R.drawable.p029, "월광 1악장", "베토벤 교향곡", true,44, false , R.raw.m029beethoven ,false));
        위워크에등록된음악리스트.add(new Data(R.drawable.p030, "Lazenca, Save Us", "하현우", true,49, false , R.raw.m030lazenca ,false));
    }
    void 평린교회노래추가(){
        평린교회에등록된음악리스트.add(new Data(R.drawable.p020, "벚꽃엔딩", "버스커 버스커", true, 6, false , R.raw.m020sakuraending ,false));
        평린교회에등록된음악리스트.add(new Data(R.drawable.p021, "기도", "비투비", true, 5, false , R.raw.m021praise ,false));
        평린교회에등록된음악리스트.add(new Data(R.drawable.p022, "비오는거리", "소울스타", true, 42, false , R.raw.m022roadrain ,false));
        평린교회에등록된음악리스트.add(new Data(R.drawable.p023, "비가와", "소유", true, 27, false , R.raw.m023itsrainy ,false));
        평린교회에등록된음악리스트.add(new Data(R.drawable.p024, "널 사랑하지 않아", "어반자카파", true, 8, false , R.raw.m024ihateu ,false));


    }
    void SK건설노래추가(){
        SK건설에등록된음악리스트.add(new Data(R.drawable.p015, "챠우챠우", "델리스파이스", true,47, false , R.raw.m015listenyourvoice,false));
        SK건설에등록된음악리스트.add(new Data(R.drawable.p016, "Love", "마마무", true,49, false , R.raw.m016love ,false));
        SK건설에등록된음악리스트.add(new Data(R.drawable.p017, "추억은 사랑을 닮아", "박효신", true,8 , false , R.raw.m017chuokenlikelove ,false));
        SK건설에등록된음악리스트.add(new Data(R.drawable.p018, "숨", "박효신", true, 26, false , R.raw.m018breathe ,false));
        SK건설에등록된음악리스트.add(new Data(R.drawable.p019, "봄날", "방탄소년단", true, 5, false , R.raw.m019springday ,false));


    }
    void 내노래추가(){

        내가등록한음악리스트.add(new Data(R.drawable.p001, "Day Day", "BeWhy", true,10, false , R.raw.m001dayday ,true));
        내가등록한음악리스트.add(new Data(R.drawable.p002, "Forever", "BeWhy", true, 4, false , R.raw.m002forever ,true));
        내가등록한음악리스트.add(new Data(R.drawable.p003, "휘파람", "블랙핑크", true, 18, false , R.raw.m003whistle ,false));
        내가등록한음악리스트.add(new Data(R.drawable.p004, "Beautiful", "Crush", true, 36, false , R.raw.m004beautiful ,false));
        내가등록한음악리스트.add(new Data(R.drawable.p005, "I`m not the only one", "Sam Smith", true, 17, false , R.raw.m005imnottheonlyone ,true));
        내가등록한음악리스트.add(new Data(R.drawable.p006, "KNOCK KNOCK", "TWICE", true, 44, false , R.raw.m006knockknock ,true));
        내가등록한음악리스트.add(new Data(R.drawable.p007, "TT", "TWICE", true, 33, false , R.raw.m007tt ,true));
        내가등록한음악리스트.add(new Data(R.drawable.p008, "녹아요", "TWICE", true, 19, false , R.raw.m008melt ,false));
        내가등록한음악리스트.add(new Data(R.drawable.p009, "낙인", "김경호", true, 47, false , R.raw.m009stamp ,false));
        내가등록한음악리스트.add(new Data(R.drawable.p010, "비정", "김경호", true, 40, false , R.raw.m010beejung ,false));
        내가등록한음악리스트.add(new Data(R.drawable.p011, "서른즈음에", "김광석", true, 48, false , R.raw.m011atthirty ,false));
        내가등록한음악리스트.add(new Data(R.drawable.p012, "사랑했지만", "김광석", true, 30, false , R.raw.m012loveubut ,false));
        내가등록한음악리스트.add(new Data(R.drawable.p013, "여전히 아름다운지", "TOY", true, 26, false , R.raw.m013stillbeautiful ,false));
        내가등록한음악리스트.add(new Data(R.drawable.p014, "나비 잠", "민경훈", true, 47, false , R.raw.m014buterflysleep ,false));


    }
    void 서버노래추가(){
        서버에있는음악리스트.add(new Data(R.drawable.p001, "Day Day", "BeWhy", true, 7, false , R.raw.m001dayday ,false));
        서버에있는음악리스트.add(new Data(R.drawable.p002, "Forever", "BeWhy", true,30, false , R.raw.m002forever ,false));
        서버에있는음악리스트.add(new Data(R.drawable.p003, "휘파람", "블랙핑크", true,31, false , R.raw.m003whistle ,false));
        서버에있는음악리스트.add(new Data(R.drawable.p004, "Beautiful", "Crush", true,43, false , R.raw.m004beautiful ,false));
        서버에있는음악리스트.add(new Data(R.drawable.p005, "I`m not the only one", "Sam Smith", true,30, false , R.raw.m005imnottheonlyone ,false));
        서버에있는음악리스트.add(new Data(R.drawable.p006, "KNOCK KNOCK", "TWICE", true,28, false , R.raw.m006knockknock ,false));
        서버에있는음악리스트.add(new Data(R.drawable.p007, "TT", "TWICE", true,20, false , R.raw.m007tt ,false));
        서버에있는음악리스트.add(new Data(R.drawable.p008, "녹아요", "TWICE", true,11, false , R.raw.m008melt ,false));
        서버에있는음악리스트.add(new Data(R.drawable.p009, "낙인", "김경호", true, 16, false , R.raw.m009stamp ,false));
        서버에있는음악리스트.add(new Data(R.drawable.p010, "비정", "김경호", true, 50, false , R.raw.m010beejung ,false));
        서버에있는음악리스트.add(new Data(R.drawable.p011, "서른즈음에", "김광석", true, 45, false , R.raw.m011atthirty ,false));
        서버에있는음악리스트.add(new Data(R.drawable.p012, "사랑했지만", "김광석", true, 15, false , R.raw.m012loveubut ,false));
        서버에있는음악리스트.add(new Data(R.drawable.p013, "여전히 아름다운지", "TOY", true, 36, false , R.raw.m013stillbeautiful ,false));
        서버에있는음악리스트.add(new Data(R.drawable.p014, "나비 잠", "민경훈", true, 22, false , R.raw.m014buterflysleep ,false));
        서버에있는음악리스트.add(new Data(R.drawable.p015, "챠우챠우", "델리스파이스", true, 50, false , R.raw.m015listenyourvoice,false));
        서버에있는음악리스트.add(new Data(R.drawable.p016, "Love", "마마무", true, 38, false , R.raw.m016love ,false));
        서버에있는음악리스트.add(new Data(R.drawable.p017, "추억은 사랑을 닮아", "박효신", true, 22, false , R.raw.m017chuokenlikelove ,false));
        서버에있는음악리스트.add(new Data(R.drawable.p018, "숨", "박효신", true, 8, false , R.raw.m018breathe ,false));
        서버에있는음악리스트.add(new Data(R.drawable.p019, "봄날", "방탄소년단", true, 26, false , R.raw.m019springday ,false));
        서버에있는음악리스트.add(new Data(R.drawable.p020, "벚꽃엔딩", "버스커 버스커", true, 5, false , R.raw.m020sakuraending ,false));
        서버에있는음악리스트.add(new Data(R.drawable.p021, "기도", "비투비", true,10, false , R.raw.m021praise ,false));
        서버에있는음악리스트.add(new Data(R.drawable.p022, "비오는거리", "소울스타", true,4, false , R.raw.m022roadrain ,false));
        서버에있는음악리스트.add(new Data(R.drawable.p023, "비가와", "소유", true, 18, false , R.raw.m023itsrainy ,false));
        서버에있는음악리스트.add(new Data(R.drawable.p024, "널 사랑하지 않아", "어반자카파", true, 36, false , R.raw.m024ihateu ,false));
        서버에있는음악리스트.add(new Data(R.drawable.p025, "소원", "어반자카파", true, 17, false , R.raw.m025wish ,false));
        서버에있는음악리스트.add(new Data(R.drawable.p026, "널 생각해", "원 모어 찬스", true, 44, false , R.raw.m026ithinku ,false));
        서버에있는음악리스트.add(new Data(R.drawable.p027, "백색소음", "자연의 소리", true, 27, false , R.raw.m027whitenoise ,false));
        서버에있는음악리스트.add(new Data(R.drawable.p028, "너였다면", "정승환", true, 34, false , R.raw.m028ifiamdyou ,false));
        서버에있는음악리스트.add(new Data(R.drawable.p029, "월광 1악장", "베토벤 교향곡", true, 40, false , R.raw.m029beethoven ,false));
        서버에있는음악리스트.add(new Data(R.drawable.p030, "Lazenca, Save Us", "하현우", true, 44, false , R.raw.m030lazenca ,false));

    }
}