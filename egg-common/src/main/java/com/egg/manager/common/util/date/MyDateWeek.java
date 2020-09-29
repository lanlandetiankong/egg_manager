package com.egg.manager.common.util.date;

/**
 *
 * 星期
 *
 * @author 段
 *
 */
public enum MyDateWeek {
    MONDAY("星期一", "Monday", "Mon.", 1), TUESDAY("星期二", "Tuesday", "Tues.", 2), WEDNESDAY(
            "星期三", "Wednesday", "Wed.", 3), THURSDAY("星期四", "Thursday",
            "Thur.", 4), FRIDAY("星期五", "Friday", "Fri.", 5), SATURDAY("星期六",
            "Saturday", "Sat.", 6), SUNDAY("星期日", "Sunday", "Sun.", 7);

    String nameCn;
    String nameEn;
    String nameEnShort;
    int number;

    MyDateWeek(String nameCn, String nameEn, String nameEnShort, int number) {
        this.nameCn = nameCn;
        this.nameEn = nameEn;
        this.nameEnShort = nameEnShort;
        this.number = number;
    }

    public String getChineseName() {
        return nameCn;
    }

    public String getName() {
        return nameEn;
    }

    public String getShortName() {
        return nameEnShort;
    }

    public int getNumber() {
        return number;
    }
}
