package com.niushi.fortune.model;

import lombok.Data;

/**
 * 星座运势
 */
@Data
public class Horoscope {
    /** 星座标识 */
    private String zodiac;
    /** 星座中文名 */
    private String zodiacName;
    /** 日期类型：today/week/month */
    private String dateType;
    /** 运势等级：excellent/good/average/poor/terrible */
    private String level;
    /** 运势等级中文 */
    private String levelName;
    /** 运势描述 */
    private String description;
    /** 幸运色 */
    private String luckyColor;
    /** 幸运数字 */
    private Integer luckyNumber;
    /** 幸运方位 */
    private String luckyDirection;
    /** 幸运星座 */
    private String luckyZodiac;
    /** 事业运势 */
    private String career;
    /** 爱情运势 */
    private String love;
    /** 财运 */
    private String wealth;
    /** 健康运势 */
    private String health;
    /** 知识解读 */
    private String knowledgeNote;
}
