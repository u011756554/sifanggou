package com.app.sifanggou.bean;

/**
 * Created by Administrator on 2018/1/18.
 */

public enum CategoryType {

    CZY("菜籽油"),DDY("大豆油"),GTJY("固态鸡油"),GTZY("固态猪油"),HJY("花椒油"),HSY("花生油"),HGNY("火锅牛油"),MM("米面"),SLY("色拉油"),TJY("藤椒油"),YMY("玉米油"),ZL("杂粮"),ZMY("芝麻油"),ZHY("棕榈油"),QTYL("其他油类"),
    BC("白醋"),DB("豆瓣"),DG("豆豉"),DFR("豆腐乳"),HGL("火锅料"),JJ("鸡精"),JL("酱类"),JY("酱油"),LC("老抽"),LJ("料酒"),SC("生抽"),SSX("十三香"),SCC("食醋"),SY("食盐"),WJ("味精"),XC("西餐"),ED("粤调"),QTTW("其他调味"),
    BJ("八角"),BK("白扣"),BB("荜拨"),CG("草果"),CK("草扣"),DX("丁香"),GC("甘草"),GD("桂丁"),GP("桂皮"),HJ("胡椒"),HJJ("花椒"),HX("茴香"),LJJ("辣椒"),PC("排草"),SR("砂仁"),SN("山奈"),XG("香果"),XY("香叶"),ZR("孜然"),QTXL("其他香料"),
    CGG("草菇"),CYG("茶树菇"),CZH("虫草花"),JZG("金针菇"),LZ("灵芝"),LHS("罗汉笋"),MR("木耳"),NGJ("牛肝菌"),NWS("牛尾笋"),SRR("松茸"),XGG("香菇"),YR("银耳"),ZS("竹荪"),QTJL("其他菌类"),
    GJD("干豇豆"),GS("干笋"),HHC("黄花菜"),LBG("萝卜干"),MGC("梅干菜"),YC("芽菜"),ZC("榨菜"),QTGC("其他干菜"),
    GJ("枸杞"),HT("核桃"),KXG("开心果"),PTG("葡萄干"),ZZ("枣子"),QTJG("其他坚果"),
    GT("罐头"),HTC("火腿肠"),JD("鸡蛋"),JDG("鸡蛋干"),LR("腊肉"),PD("皮蛋"),WCR("午餐肉"),XD("咸蛋"),XCC("香肠"),YD("鸭蛋"),QTRD("其他肉蛋"),
    PCC("泡菜"),PJ("泡姜"),PJD("泡豇豆"),PJJ("泡椒"),PLB("泡萝卜"),TS("糖蒜"),QTYZP("其他腌制品"),
    BL("贝类"),JZL("海藻类"),YXJ("鱼虾蟹"),QTSC("其他水产"),
    FZ("粉状"),GZ("膏状"),JMF("酵母粉"),MYT("麦芽糖"),SS("色素"),XJ("香精"),YT("液体"),QTTJJ("其他添加剂");
    
    private String childName;
    CategoryType(String childName) {
        this.childName = childName;
    }

    public String getChildName() {
        return this.childName;
    }
}
