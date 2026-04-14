package com.niushi.fortune.service;

import com.niushi.fortune.model.Horoscope;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 星座运势服务
 */
@Service
public class HoroscopeService {

    private static final Map<String, String> ZODIAC_NAMES = Map.of(
            "aries", "白羊座", "taurus", "金牛座", "gemini", "双子座",
            "cancer", "巨蟹座", "leo", "狮子座", "virgo", "处女座",
            "libra", "天秤座", "scorpio", "天蝎座", "sagittarius", "射手座",
            "capricorn", "摩羯座", "aquarius", "水瓶座", "pisces", "双鱼座"
    );

    private static final Map<String, String> LEVEL_NAMES = Map.of(
            "excellent", "大吉", "good", "吉", "average", "平", "poor", "凶", "terrible", "大凶"
    );

    // 星座幸运色映射
    private static final Map<String, String[]> LUCKY_COLORS = Map.of(
            "aries", new String[]{"红色", "橙色"},
            "taurus", new String[]{"绿色", "粉色"},
            "gemini", new String[]{"黄色", "蓝色"},
            "cancer", new String[]{"银色", "白色"},
            "leo", new String[]{"金色", "黄色"},
            "virgo", new String[]{"灰色", "绿色"},
            "libra", new String[]{"粉色", "淡蓝色"},
            "scorpio", new String[]{"深红色", "黑色"},
            "sagittarius", new String[]{"紫色", "蓝色"},
            "capricorn", new String[]{"深棕色", "黑色"},
            "aquarius", new String[]{"蓝色", "青色"},
            "pisces", new String[]{"紫色", "海蓝色"}
    );

    // 星座知识解读
    private static final Map<String, String> KNOWLEDGE_NOTES = Map.of(
            "aries", "白羊座是火象星座，代表勇气、热情和冒险精神。今天是展现自我、开拓新事业的好时机。",
            "taurus", "金牛座是土象星座，代表稳定、务实和享受生活。今天适合专注于财务规划和物质享受。",
            "gemini", "双子座是风象星座，代表沟通、变化和多面性。今天适合学习新知识、拓展人脉。",
            "cancer", "巨蟹座是水象星座，代表情感、家庭和安全感。今天适合陪伴家人、关注心理健康。",
            "leo", "狮子座是火象星座，代表自信、领导力和创造力。今天是展现才华、获得认可的日子。",
            "virgo", "处女座是土象星座，代表完美、分析和服务。今天适合处理细节工作、整理生活。",
            "libra", "天秤座是风象星座，代表平衡、和谐和美感。今天适合处理人际关系、追求艺术生活。",
            "scorpio", "天蝎座是水象星座，代表深度、热情和直觉。今天适合深入思考、探索神秘领域。",
            "sagittarius", "射手座是火象星座，代表自由、乐观和冒险。今天适合旅行、学习和哲学思考。",
            "capricorn", "摩羯座是土象星座，代表责任、耐心和成就。今天适合设定目标、稳步前进。",
            "aquarius", "水瓶座是风象星座，代表创新、独立和人道主义。今天适合思考未来、尝试新事物。",
            "pisces", "双鱼座是水象星座，代表梦想、情感和直觉。今天适合艺术创作、冥想休息。"
    );

    /**
     * 获取所有星座列表
     */
    public List<Horoscope> getAllHoroscopes(String dateType) {
        List<Horoscope> list = new ArrayList<>();
        for (String zodiac : ZODIAC_NAMES.keySet()) {
            list.add(getHoroscope(zodiac, dateType));
        }
        return list;
    }

    /**
     * 获取单个星座运势
     */
    public Horoscope getHoroscope(String zodiac, String dateType) {
        Horoscope horoscope = new Horoscope();
        horoscope.setZodiac(zodiac);
        horoscope.setZodiacName(ZODIAC_NAMES.getOrDefault(zodiac, zodiac));
        horoscope.setDateType(dateType != null ? dateType : "today");

        // 根据日期生成稳定的运势数据
        int seed = generateSeed(zodiac, dateType);
        String level = getLevelBySeed(seed);
        horoscope.setLevel(level);
        horoscope.setLevelName(LEVEL_NAMES.getOrDefault(level, "平"));

        // 生成运势描述
        horoscope.setDescription(generateDescription(zodiac, level));
        horoscope.setCareer(generateCareer(zodiac, level));
        horoscope.setLove(generateLove(zodiac, level));
        horoscope.setWealth(generateWealth(zodiac, level));
        horoscope.setHealth(generateHealth(zodiac, level));

        // 幸运元素
        String[] colors = LUCKY_COLORS.getOrDefault(zodiac, new String[]{"红色", "蓝色"});
        horoscope.setLuckyColor(colors[seed % colors.length]);
        horoscope.setLuckyNumber((seed % 9) + 1);
        horoscope.setLuckyDirection(getDirection(seed));
        horoscope.setLuckyZodiac(getLuckyZodiac(zodiac, seed));

        // 知识解读
        horoscope.setKnowledgeNote(KNOWLEDGE_NOTES.getOrDefault(zodiac, ""));

        return horoscope;
    }

    private int generateSeed(String zodiac, String dateType) {
        String key = zodiac + (dateType != null ? dateType : "today");
        return key.hashCode();
    }

    private String getLevelBySeed(int seed) {
        String[] levels = {"excellent", "good", "good", "average", "average", "poor"};
        return levels[Math.abs(seed) % levels.length];
    }

    private String generateDescription(String zodiac, String level) {
        String base = switch (level) {
            case "excellent" -> "今天的运势非常出色！";
            case "good" -> "今天的运势很不错";
            case "average" -> "今天的运势较为平稳";
            case "poor" -> "今天需要多加注意";
            default -> "保持平常心即可";
        };
        return base + "，" + ZODIAC_NAMES.get(zodiac) + "的你将迎来充满机遇的一天。";
    }

    private String generateCareer(String zodiac, String level) {
        return switch (level) {
            case "excellent" -> "事业运势极佳，适合开展新项目或提出创意方案";
            case "good" -> "工作进展顺利，有望获得意外收获";
            case "average" -> "按部就班完成工作即可";
            case "poor" -> "注意与同事沟通方式，避免冲突";
            default -> "保持稳定心态";
        };
    }

    private String generateLove(String zodiac, String level) {
        return switch (level) {
            case "excellent" -> "魅力大爆发，适合表白或约会";
            case "good" -> "感情运势良好，单身有望遇到有缘人";
            case "average" -> "适合与伴侣共同规划未来";
            case "poor" -> "避免冲动行为，多沟通化解矛盾";
            default -> "保持真诚态度";
        };
    }

    private String generateWealth(String zodiac, String level) {
        return switch (level) {
            case "excellent" -> "财运亨通，有望获得额外收入";
            case "good" -> "理财运势不错，可适当投资";
            case "average" -> "收支平衡，注意控制支出";
            case "poor" -> "避免大额消费，谨防诈骗";
            default -> "理性消费";
        };
    }

    private String generateHealth(String zodiac, String level) {
        return switch (level) {
            case "excellent" -> "精力充沛，适合运动锻炼";
            case "good" -> "身体健康，注意休息即可";
            case "average" -> "适当运动，保持规律作息";
            case "poor" -> "注意身体健康，适当调养";
            default -> "注意保养";
        };
    }

    private String getDirection(int seed) {
        String[] directions = {"正东", "东南", "正南", "西南", "正西", "西北", "正北", "东北"};
        return directions[Math.abs(seed) % directions.length];
    }

    private String getLuckyZodiac(String zodiac, int seed) {
        List<String> allZodiac = new ArrayList<>(ZODIAC_NAMES.keySet());
        allZodiac.remove(zodiac);
        return ZODIAC_NAMES.get(allZodiac.get(Math.abs(seed) % allZodiac.size()));
    }
}
