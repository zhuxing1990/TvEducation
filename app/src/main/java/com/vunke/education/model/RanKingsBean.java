package com.vunke.education.model;

import java.util.List;

/**
 * Created by zhuxi on 2017/4/10.
 */
public class RanKingsBean {


    /**
     * all : [{"authname":"李鸿","blockid":"010102","content":"本课程有几个特点: 设置及系统化、趣味化、生活化于一体，让学生在动脑的过程中，构建数学概念，激发学习兴趣培养思维能力","createtime":"17-2-20","id":"3","info_name":"儿童趣味数学","infoid":1,"inter1":0,"inter2":0,"inter3":0,"inter4":0,"istop":"1,2,3,4,5,6","lastauthtime":"","lasttime":"2017-04-08 09:39:59","mainpicurl":"","modeno":"1.0","pic1":"http://124.232.136.239:8080/image/chanping.png","pic2":"","pic3":"","pic4":"","pic5":"","relblocks":"","seqno":2,"stataccessnum":0,"state":"10.2","string1":"10:36","string10":"","string11":"","string12":"","string13":"","string14":"","string15":"","string2":"新东方教育平台","string3":"","string4":"","string5":"","string6":"","string7":"","string8":"","string9":"","tag":"1001","title":"儿童趣味数学","txt1":"","txt2":"","txt3":"","type1":"","type2":"","type3":"","type4":"","type5":"","type6":"","updata_time":{"date":16,"day":4,"hours":8,"minutes":56,"month":2,"seconds":6,"time":1489625766394,"timezoneOffset":-480,"year":117},"userid":103,"username":"李嘉伦"},{"authname":"李鸿","blockid":"010101","content":"数学教育","createtime":"2017-03-06 14:47:29","id":"2","info_name":"你好世界","infoid":2,"inter1":0,"inter2":0,"inter3":0,"inter4":0,"istop":"1002","lastauthtime":"","lasttime":"2017-03-06 14:47:29","mainpicurl":"","modeno":"1.0","pic1":"","pic2":"","pic3":"","pic4":"","pic5":"","relblocks":"","seqno":1,"stataccessnum":0,"state":"10.2","string1":"","string10":"","string11":"","string12":"","string13":"","string14":"","string15":"","string2":"","string3":"","string4":"","string5":"","string6":"","string7":"","string8":"","string9":"","tag":"1002","title":"一年级教学视频","txt1":"","txt2":"","txt3":"","type1":"","type2":"","type3":"","type4":"","type5":"","type6":"","updata_time":{"date":10,"day":1,"hours":1,"minutes":55,"month":3,"seconds":36,"time":1491760536540,"timezoneOffset":-480,"year":117},"userid":103,"username":"李嘉伦"},{"authname":"李鸿","blockid":"01","content":"天天向上是中国最受欢迎的节目","createtime":"2017-02-28 12:58:11","id":"1","info_name":"天天向上","infoid":3,"inter1":0,"inter2":0,"inter3":0,"inter4":0,"istop":"1003","lastauthtime":"","lasttime":"2017-02-28 12:58:11","mainpicurl":"","modeno":"1.0","pic1":"","pic2":"","pic3":"","pic4":"","pic5":"","relblocks":"","seqno":1,"stataccessnum":0,"state":"10.2","string1":"","string10":"","string11":"","string12":"","string13":"","string14":"","string15":"","string2":"","string3":"","string4":"","string5":"","string6":"","string7":"","string8":"","string9":"","tag":"ddddddddddddd","title":"天天看天天向上","txt1":"","txt2":"","txt3":"","type1":"","type2":"","type3":"","type4":"","type5":"","type6":"","updata_time":{"date":10,"day":1,"hours":1,"minutes":56,"month":3,"seconds":9,"time":1491760569551,"timezoneOffset":-480,"year":117},"userid":103,"username":"李嘉伦"}]
     * code : 200
     * message : 请求正常
     */
    private String code;
    private String message;
    /**
     * authname : 李鸿
     * blockid : 010102
     * content : 本课程有几个特点: 设置及系统化、趣味化、生活化于一体，让学生在动脑的过程中，构建数学概念，激发学习兴趣培养思维能力
     * createtime : 17-2-20
     * id : 3
     * info_name : 儿童趣味数学
     * infoid : 1
     * inter1 : 0
     * inter2 : 0
     * inter3 : 0
     * inter4 : 0
     * istop : 1,2,3,4,5,6
     * lastauthtime :
     * lasttime : 2017-04-08 09:39:59
     * mainpicurl :
     * modeno : 1.0
     * pic1 : http://124.232.136.239:8080/image/chanping.png
     * pic2 :
     * pic3 :
     * pic4 :
     * pic5 :
     * relblocks :
     * seqno : 2
     * stataccessnum : 0
     * state : 10.2
     * string1 : 10:36
     * string10 :
     * string11 :
     * string12 :
     * string13 :
     * string14 :
     * string15 :
     * string2 : 新东方教育平台
     * string3 :
     * string4 :
     * string5 :
     * string6 :
     * string7 :
     * string8 :
     * string9 :
     * tag : 1001
     * title : 儿童趣味数学
     * txt1 :
     * txt2 :
     * txt3 :
     * type1 :
     * type2 :
     * type3 :
     * type4 :
     * type5 :
     * type6 :
     * updata_time : {"date":16,"day":4,"hours":8,"minutes":56,"month":2,"seconds":6,"time":1489625766394,"timezoneOffset":-480,"year":117}
     * userid : 103
     * username : 李嘉伦
     */

    private List<AllBean> all;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<AllBean> getAll() {
        return all;
    }

    public void setAll(List<AllBean> all) {
        this.all = all;
    }

    public static class AllBean {
        private String authname;
        private String blockid;
        private String content;
        private String createtime;
        private String id;
        private String info_name;
        private int infoid;
        private int inter1;
        private int inter2;
        private int inter3;
        private int inter4;
        private String istop;
        private String lastauthtime;
        private String lasttime;
        private String mainpicurl;
        private String modeno;
        private String pic1;
        private String pic2;
        private String pic3;
        private String pic4;
        private String pic5;
        private String relblocks;
        private int seqno;
        private int stataccessnum;
        private String state;
        private String string1;
        private String string10;
        private String string11;
        private String string12;
        private String string13;
        private String string14;
        private String string15;
        private String string2;
        private String string3;
        private String string4;
        private String string5;
        private String string6;
        private String string7;
        private String string8;
        private String string9;
        private String tag;
        private String title;
        private String txt1;
        private String txt2;
        private String txt3;
        private String type1;
        private String type2;
        private String type3;
        private String type4;
        private String type5;
        private String type6;
        /**
         * date : 16
         * day : 4
         * hours : 8
         * minutes : 56
         * month : 2
         * seconds : 6
         * time : 1489625766394
         * timezoneOffset : -480
         * year : 117
         */

        private UpdataTimeBean updata_time;
        private int userid;
        private String username;

        public String getAuthname() {
            return authname;
        }

        public void setAuthname(String authname) {
            this.authname = authname;
        }

        public String getBlockid() {
            return blockid;
        }

        public void setBlockid(String blockid) {
            this.blockid = blockid;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getInfo_name() {
            return info_name;
        }

        public void setInfo_name(String info_name) {
            this.info_name = info_name;
        }

        public int getInfoid() {
            return infoid;
        }

        public void setInfoid(int infoid) {
            this.infoid = infoid;
        }

        public int getInter1() {
            return inter1;
        }

        public void setInter1(int inter1) {
            this.inter1 = inter1;
        }

        public int getInter2() {
            return inter2;
        }

        public void setInter2(int inter2) {
            this.inter2 = inter2;
        }

        public int getInter3() {
            return inter3;
        }

        public void setInter3(int inter3) {
            this.inter3 = inter3;
        }

        public int getInter4() {
            return inter4;
        }

        public void setInter4(int inter4) {
            this.inter4 = inter4;
        }

        public String getIstop() {
            return istop;
        }

        public void setIstop(String istop) {
            this.istop = istop;
        }

        public String getLastauthtime() {
            return lastauthtime;
        }

        public void setLastauthtime(String lastauthtime) {
            this.lastauthtime = lastauthtime;
        }

        public String getLasttime() {
            return lasttime;
        }

        public void setLasttime(String lasttime) {
            this.lasttime = lasttime;
        }

        public String getMainpicurl() {
            return mainpicurl;
        }

        public void setMainpicurl(String mainpicurl) {
            this.mainpicurl = mainpicurl;
        }

        public String getModeno() {
            return modeno;
        }

        public void setModeno(String modeno) {
            this.modeno = modeno;
        }

        public String getPic1() {
            return pic1;
        }

        public void setPic1(String pic1) {
            this.pic1 = pic1;
        }

        public String getPic2() {
            return pic2;
        }

        public void setPic2(String pic2) {
            this.pic2 = pic2;
        }

        public String getPic3() {
            return pic3;
        }

        public void setPic3(String pic3) {
            this.pic3 = pic3;
        }

        public String getPic4() {
            return pic4;
        }

        public void setPic4(String pic4) {
            this.pic4 = pic4;
        }

        public String getPic5() {
            return pic5;
        }

        public void setPic5(String pic5) {
            this.pic5 = pic5;
        }

        public String getRelblocks() {
            return relblocks;
        }

        public void setRelblocks(String relblocks) {
            this.relblocks = relblocks;
        }

        public int getSeqno() {
            return seqno;
        }

        public void setSeqno(int seqno) {
            this.seqno = seqno;
        }

        public int getStataccessnum() {
            return stataccessnum;
        }

        public void setStataccessnum(int stataccessnum) {
            this.stataccessnum = stataccessnum;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getString1() {
            return string1;
        }

        public void setString1(String string1) {
            this.string1 = string1;
        }

        public String getString10() {
            return string10;
        }

        public void setString10(String string10) {
            this.string10 = string10;
        }

        public String getString11() {
            return string11;
        }

        public void setString11(String string11) {
            this.string11 = string11;
        }

        public String getString12() {
            return string12;
        }

        public void setString12(String string12) {
            this.string12 = string12;
        }

        public String getString13() {
            return string13;
        }

        public void setString13(String string13) {
            this.string13 = string13;
        }

        public String getString14() {
            return string14;
        }

        public void setString14(String string14) {
            this.string14 = string14;
        }

        public String getString15() {
            return string15;
        }

        public void setString15(String string15) {
            this.string15 = string15;
        }

        public String getString2() {
            return string2;
        }

        public void setString2(String string2) {
            this.string2 = string2;
        }

        public String getString3() {
            return string3;
        }

        public void setString3(String string3) {
            this.string3 = string3;
        }

        public String getString4() {
            return string4;
        }

        public void setString4(String string4) {
            this.string4 = string4;
        }

        public String getString5() {
            return string5;
        }

        public void setString5(String string5) {
            this.string5 = string5;
        }

        public String getString6() {
            return string6;
        }

        public void setString6(String string6) {
            this.string6 = string6;
        }

        public String getString7() {
            return string7;
        }

        public void setString7(String string7) {
            this.string7 = string7;
        }

        public String getString8() {
            return string8;
        }

        public void setString8(String string8) {
            this.string8 = string8;
        }

        public String getString9() {
            return string9;
        }

        public void setString9(String string9) {
            this.string9 = string9;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTxt1() {
            return txt1;
        }

        public void setTxt1(String txt1) {
            this.txt1 = txt1;
        }

        public String getTxt2() {
            return txt2;
        }

        public void setTxt2(String txt2) {
            this.txt2 = txt2;
        }

        public String getTxt3() {
            return txt3;
        }

        public void setTxt3(String txt3) {
            this.txt3 = txt3;
        }

        public String getType1() {
            return type1;
        }

        public void setType1(String type1) {
            this.type1 = type1;
        }

        public String getType2() {
            return type2;
        }

        public void setType2(String type2) {
            this.type2 = type2;
        }

        public String getType3() {
            return type3;
        }

        public void setType3(String type3) {
            this.type3 = type3;
        }

        public String getType4() {
            return type4;
        }

        public void setType4(String type4) {
            this.type4 = type4;
        }

        public String getType5() {
            return type5;
        }

        public void setType5(String type5) {
            this.type5 = type5;
        }

        public String getType6() {
            return type6;
        }

        public void setType6(String type6) {
            this.type6 = type6;
        }

        public UpdataTimeBean getUpdata_time() {
            return updata_time;
        }

        public void setUpdata_time(UpdataTimeBean updata_time) {
            this.updata_time = updata_time;
        }

        public int getUserid() {
            return userid;
        }

        public void setUserid(int userid) {
            this.userid = userid;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public static class UpdataTimeBean {
            private int date;
            private int day;
            private int hours;
            private int minutes;
            private int month;
            private int seconds;
            private long time;
            private int timezoneOffset;
            private int year;

            public int getDate() {
                return date;
            }

            public void setDate(int date) {
                this.date = date;
            }

            public int getDay() {
                return day;
            }

            public void setDay(int day) {
                this.day = day;
            }

            public int getHours() {
                return hours;
            }

            public void setHours(int hours) {
                this.hours = hours;
            }

            public int getMinutes() {
                return minutes;
            }

            public void setMinutes(int minutes) {
                this.minutes = minutes;
            }

            public int getMonth() {
                return month;
            }

            public void setMonth(int month) {
                this.month = month;
            }

            public int getSeconds() {
                return seconds;
            }

            public void setSeconds(int seconds) {
                this.seconds = seconds;
            }

            public long getTime() {
                return time;
            }

            public void setTime(long time) {
                this.time = time;
            }

            public int getTimezoneOffset() {
                return timezoneOffset;
            }

            public void setTimezoneOffset(int timezoneOffset) {
                this.timezoneOffset = timezoneOffset;
            }

            public int getYear() {
                return year;
            }

            public void setYear(int year) {
                this.year = year;
            }
        }
    }
}
