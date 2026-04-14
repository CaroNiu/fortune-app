package com.niushi.fortune.model;

import lombok.Data;
import java.util.List;

/**
 * 生肖运势
 */
@Data
public class ChineseZodiac {
    /** 生肖标识 */
    private String animal;
    /** 生肖中文名 */
    private String animalName;
    /** 年份排名 */
    private Integer rank;
    /** 整体运势 */
    private String overall;
    /** 事业运势 */
    private String career;
    /** 爱情运势 */
    private String love;
    /** 财运 */
    private String wealth;
    /** 健康运势 */
    private String health;
    /** 幸运色 */
    private List<String> luckyColors;
    /** 幸运数字 */
    private List<Integer> luckyNumbers;
    /** 幸运方位 */
    private String luckyDirection;
    /** 贵人生肖 */
    private String luckyAnimal;
    /** 知识解读 */
    private String knowledgeNote;
}
