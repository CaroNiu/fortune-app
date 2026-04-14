package com.niushi.fortune.service;

import com.niushi.fortune.model.LuckyElement;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * 幸运元素服务
 */
@Service
public class LuckyElementService {

    private static final String[] LUCKY_COLORS = {
            "红色", "橙色", "黄色", "绿色", "青色", "蓝色", "紫色", "粉色", "白色", "黑色"
    };

    private static final String[] COLOR_HEX = {
            "#FF6B6B", "#FFA94D", "#FFE066", "#69DB7C", "#38D9A9",
            "#4DABF7", "#DA77F2", "#F783AC", "#FFFFFF", "#212529"
    };

    private static final String[] LUCKY_COLOR_NOTES = {
            "红色代表热情与活力，能激发你的行动力",
            "橙色带来愉悦与创造力，让你更有热情",
            "黄色象征智慧与快乐，提升你的正能量",
            "绿色代表生长与平衡，带来和谐与希望",
            "青色寓意清新与自由，让你保持年轻心态",
            "蓝色象征深邃与宁静，提升你的专注力",
            "紫色代表神秘与优雅，提升你的气质",
            "粉色带来温柔与爱情，提升桃花运",
            "白色象征纯洁与平静，让你保持清醒",
            "黑色代表稳重与神秘，增强你的气场"
    };

    private static final String[] DIRECTIONS = {
            "正东", "东南", "正南", "西南", "正西", "西北", "正北", "东北"
    };

    private static final String[] DIRECTION_NOTES = {
            "正东代表木之气，适合发展事业",
            "东南代表木之气，适合学习新技能",
            "正南代表火之气，适合社交活动",
            "西南代表土之气，适合感情发展",
            "正西代表金之气，适合财运提升",
            "西北代表金之气，适合人脉拓展",
            "正北代表水之气，适合思考规划",
            "东北代表土之气，适合稳定发展"
    };

    /**
     * 获取今日幸运元素
     */
    public LuckyElement getTodayLuckyElement() {
        return getLuckyElement(LocalDate.now());
    }

    /**
     * 获取指定日期的幸运元素
     */
    public LuckyElement getLuckyElement(LocalDate date) {
        LuckyElement lucky = new LuckyElement();
        lucky.setDate(date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        // 根据日期生成稳定的幸运元素
        int seed = date.toEpochDay();

        // 幸运色
        int colorIndex = Math.abs(seed) % LUCKY_COLORS.length;
        lucky.setLuckyColor(LUCKY_COLORS[colorIndex]);
        lucky.setLuckyColorHex(COLOR_HEX[colorIndex]);
        lucky.setLuckyColorNote(COLOR_COLOR_NOTES[colorIndex]);

        // 幸运数字
        int numberIndex = Math.abs(seed) % 9 + 1;
        lucky.setLuckyNumber(numberIndex);
        lucky.setLuckyNumberNote("数字" + numberIndex + "代表能量与创造力，今天多关注这个数字会带来好运");

        // 幸运方位
        int dirIndex = Math.abs(seed) % DIRECTIONS.length;
        lucky.setLuckyDirection(DIRECTIONS[dirIndex]);
        lucky.setLuckyDirectionNote(DIRECTION_NOTES[dirIndex]);

        // 财神方位（与幸运方位不同）
        int wealthDirIndex = (dirIndex + 3) % DIRECTIONS.length;
        lucky.setWealthGodDirection(DIRECTIONS[wealthDirIndex]);

        // 喜神方位
        int joyDirIndex = (dirIndex + 2) % DIRECTIONS.length;
        lucky.setJoyGodDirection(DIRECTIONS[joyDirIndex]);

        // 福神方位
        int blessDirIndex = (dirIndex + 4) % DIRECTIONS.length;
        lucky.setBlessingGodDirection(DIRECTIONS[blessDirIndex]);

        return lucky;
    }

    private static final String[] COLOR_COLOR_NOTES = LUCKY_COLOR_NOTES;
}
