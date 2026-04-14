package com.niushi.fortune.model;

import lombok.Data;

/**
 * 今日幸运元素
 */
@Data
public class LuckyElement {
    /** 日期 */
    private String date;
    /** 幸运色 */
    private String luckyColor;
    /** 幸运色HEX值 */
    private String luckyColorHex;
    /** 幸运色说明 */
    private String luckyColorNote;
    /** 幸运数字 */
    private Integer luckyNumber;
    /** 幸运数字说明 */
    private String luckyNumberNote;
    /** 幸运方位 */
    private String luckyDirection;
    /** 方位说明 */
    private String luckyDirectionNote;
    /** 财神方位 */
    private String wealthGodDirection;
    /** 喜神方位 */
    private String joyGodDirection;
    /** 福神方位 */
    private String blessingGodDirection;
}
