package com.niushi.fortune.service;

import com.niushi.fortune.model.ChineseZodiac;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 生肖运势服务
 */
@Service
public class ChineseZodiacService {

    private static final String[] ANIMALS = {"鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊", "猴", "鸡", "狗", "猪"};
    private static final String[] ANIMAL_IDS = {"rat", "ox", "tiger", "rabbit", "dragon", "snake", "horse", "goat", "monkey", "rooster", "dog", "pig"};

    // 生肖知识解读
    private static final Map<String, String> KNOWLEDGE_NOTES = Map.of(
            "rat", "鼠年出生的人聪明机敏、适应力强。2026年丙火年份，老鼠财运亨通，但需注意守财。",
            "ox", "牛年出生的人踏实勤劳、稳重可靠。2026年事业上有贵人相助，适合稳扎稳打。",
            "tiger", "虎年出生的人勇敢自信、有领导力。2026年宜主动出击，把握发展机遇。",
            "rabbit", "兔年出生的人温柔善良、细腻敏感。2026年感情运势上升，需注意身体健康。",
            "dragon", "龙年出生的人热情洋溢、事业心强。2026年是施展才华的好年份，财运尤佳。",
            "snake", "蛇年出生的人智慧深沉、直觉敏锐。2026年适合思考规划，为未来做准备。",
            "horse", "马年出生的人热情奔放、追求自由。2026年有望遇到事业发展良机。",
            "goat", "羊年出生的人温柔和善、艺术气质。2026年需注意人际关系处理。",
            "monkey", "猴年出生的人聪明活泼、善于交际。2026年财运不错，但需防小人。",
            "rooster", "鸡年出生的人勤奋诚实、注重细节。2026年工作上有突破，适合求新求变。",
            "dog", "狗年出生的人忠诚正直、富有正义感。2026年家庭运势好，适合静心养性。",
            "pig", "猪年出生的人真诚善良、宽容大度。2026年福气满满，适合修身养性。"
    );

    // 2026年各生肖排名（模拟数据）
    private static final Map<String, Integer> YEAR_RANKS = Map.of(
            "rat", 3, "ox", 6, "tiger", 2, "rabbit", 5,
            "dragon", 1, "snake", 7, "horse", 4, "goat", 9,
            "monkey", 8, "rooster", 10, "dog", 11, "pig", 12
    );

    /**
     * 获取所有生肖运势
     */
    public List<ChineseZodiac> getAllZodiac() {
        List<ChineseZodiac> list = new ArrayList<>();
        for (int i = 0; i < ANIMALS.length; i++) {
            list.add(getZodiac(ANIMAL_IDS[i]));
        }
        // 按排名排序
        list.sort(Comparator.comparingInt(ChineseZodiac::getRank));
        return list;
    }

    /**
     * 获取单个生肖运势
     */
    public ChineseZodiac getZodiac(String animalId) {
        ChineseZodiac zodiac = new ChineseZodiac();
        int index = Arrays.binarySearch(ANIMAL_IDS, animalId);
        if (index < 0) index = 0;

        zodiac.setAnimal(animalId);
        zodiac.setAnimalName(ANIMALS[index]);
        zodiac.setRank(YEAR_RANKS.getOrDefault(animalId, 6));

        // 生成运势描述
        zodiac.setOverall(generateOverall(animalId));
        zodiac.setCareer(generateCareer(animalId));
        zodiac.setLove(generateLove(animalId));
        zodiac.setWealth(generateWealth(animalId));
        zodiac.setHealth(generateHealth(animalId));

        // 幸运元素
        zodiac.setLuckyColors(getLuckyColors(animalId));
        zodiac.setLuckyNumbers(getLuckyNumbers(animalId));
        zodiac.setLuckyDirection(getLuckyDirection(animalId));
        zodiac.setLuckyAnimal(getLuckyAnimal(animalId));

        // 知识解读
        zodiac.setKnowledgeNote(KNOWLEDGE_NOTES.getOrDefault(animalId, ""));

        return zodiac;
    }

    private String generateOverall(String animal) {
        int rank = YEAR_RANKS.getOrDefault(animal, 6);
        return switch (rank) {
            case 1, 2, 3 -> "2026年运势极佳，各方面都将迎来丰收之年，事业发展顺遂，财运亨通。";
            case 4, 5, 6 -> "2026年运势平稳，整体呈现上升趋势，需要把握机遇，积极进取。";
            case 7, 8, 9 -> "2026年运势平缓，建议稳中求进，注意调整心态，保持积极乐观。";
            default -> "2026年需修身养性，养精蓄锐，为来年做好准备。";
        };
    }

    private String generateCareer(String animal) {
        int rank = YEAR_RANKS.getOrDefault(animal, 6);
        return switch (rank) {
            case 1, 2 -> "事业运势旺盛，有望升职加薪或创业成功";
            case 3, 4 -> "事业发展顺利，工作中有不错的表现机会";
            case 5, 6 -> "按部就班做好本职工作即可，注意提升技能";
            case 7, 8 -> "需更加努力，避免工作中的失误";
            default -> "保持低调，积累经验，等待时机";
        };
    }

    private String generateLove(String animal) {
        int rank = YEAR_RANKS.getOrDefault(animal, 6);
        return switch (rank) {
            case 1, 2, 3 -> "感情运势极佳，单身有望遇到正缘，已婚者家庭和睦";
            case 4, 5 -> "桃花运不错，适合扩大社交圈";
            case 6, 7 -> "感情生活平稳发展";
            default -> "建议专注事业，感情顺其自然";
        };
    }

    private String generateWealth(String animal) {
        int rank = YEAR_RANKS.getOrDefault(animal, 6);
        return switch (rank) {
            case 1, 2 -> "财运大旺，正财偏财均有收获，可适当进行投资";
            case 3, 4 -> "财运不错，收入有望增加";
            case 5, 6 -> "财运平稳，保持理性消费";
            case 7, 8 -> "注意理财，避免冲动消费";
            default -> "财运较弱，需谨慎理财";
        };
    }

    private String generateHealth(String animal) {
        int rank = YEAR_RANKS.getOrDefault(animal, 6);
        return switch (rank) {
            case 1, 2, 3 -> "身体健康，精力充沛，适合运动锻炼";
            case 4, 5, 6 -> "健康状况良好，注意作息规律";
            case 7, 8 -> "需要注意身体健康，适当调养";
            default -> "多加注意身体，定期体检";
        };
    }

    private List<String> getLuckyColors(String animal) {
        Map<String, String[]> colors = Map.of(
                "rat", new String[]{"蓝色", "金色"},
                "ox", new String[]{"黄色", "棕色"},
                "tiger", new String[]{"橙色", "蓝色"},
                "rabbit", new String[]{"粉色", "绿色"},
                "dragon", new String[]{"金色", "红色"},
                "snake", new String[]{"黑色", "红色"},
                "horse", new String[]{"棕色", "绿色"},
                "goat", new String[]{"绿色", "白色"},
                "monkey", new String[]{"白色", "蓝色"},
                "rooster", new String[]{"金色", "棕色"},
                "dog", new String[]{"红色", "绿色"},
                "pig", new String[]{"黄色", "灰色"}
        );
        return Arrays.asList(colors.getOrDefault(animal, new String[]{"红色", "蓝色"}));
    }

    private List<Integer> getLuckyNumbers(String animal) {
        // 基于生肖索引生成幸运数字
        int index = Arrays.binarySearch(ANIMAL_IDS, animal);
        if (index < 0) index = 0;
        return List.of((index + 1) * 2, (index + 1) * 3 + 1, (index + 1) * 5);
    }

    private String getLuckyDirection(String animal) {
        String[] directions = {"正东", "东南", "正南", "西南", "正西", "西北", "正北", "东北",
                "正东", "东南", "正南", "西南"};
        int index = Arrays.binarySearch(ANIMAL_IDS, animal);
        return directions[index < 0 ? 0 : index];
    }

    private String getLuckyAnimal(String animal) {
        // 相合生肖
        Map<String, String> compatible = Map.of(
                "rat", "龙", "ox", "蛇", "tiger", "马", "rabbit", "狗",
                "dragon", "猴", "snake", "牛", "horse", "虎", "goat", "兔",
                "monkey", "蛇", "rooster", "龙", "dog", "兔", "pig", "羊"
        );
        return compatible.getOrDefault(animal, "龙");
    }
}
