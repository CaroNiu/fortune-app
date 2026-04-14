package com.niushi.fortune.controller;

import com.niushi.fortune.model.ApiResponse;
import com.niushi.fortune.model.ChineseZodiac;
import com.niushi.fortune.model.Horoscope;
import com.niushi.fortune.model.LuckyElement;
import com.niushi.fortune.service.ChineseZodiacService;
import com.niushi.fortune.service.HoroscopeService;
import com.niushi.fortune.service.LuckyElementService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 运势相关API
 */
@RestController
@RequestMapping("/api/fortune")
@CrossOrigin(origins = "*")
public class FortuneController {

    private final HoroscopeService horoscopeService;
    private final ChineseZodiacService chineseZodiacService;
    private final LuckyElementService luckyElementService;

    public FortuneController(HoroscopeService horoscopeService,
                             ChineseZodiacService chineseZodiacService,
                             LuckyElementService luckyElementService) {
        this.horoscopeService = horoscopeService;
        this.chineseZodiacService = chineseZodiacService;
        this.luckyElementService = luckyElementService;
    }

    // ==================== 星座运势 ====================

    /**
     * 获取所有星座今日运势
     */
    @GetMapping("/horoscope/today")
    public ApiResponse<List<Horoscope>> getTodayHoroscopes() {
        return ApiResponse.success(horoscopeService.getAllHoroscopes("today"));
    }

    /**
     * 获取所有星座本周运势
     */
    @GetMapping("/horoscope/week")
    public ApiResponse<List<Horoscope>> getWeekHoroscopes() {
        return ApiResponse.success(horoscopeService.getAllHoroscopes("week"));
    }

    /**
     * 获取指定星座运势
     * @param zodiac 星座标识 (aries, taurus, gemini, cancer, leo, virgo, libra, scorpio, sagittarius, capricorn, aquarius, pisces)
     * @param dateType today/week/month
     */
    @GetMapping("/horoscope/{zodiac}")
    public ApiResponse<Horoscope> getHoroscope(
            @PathVariable String zodiac,
            @RequestParam(defaultValue = "today") String dateType) {
        return ApiResponse.success(horoscopeService.getHoroscope(zodiac.toLowerCase(), dateType));
    }

    // ==================== 生肖运势 ====================

    /**
     * 获取所有生肖运势
     */
    @GetMapping("/zodiac")
    public ApiResponse<List<ChineseZodiac>> getAllZodiac() {
        return ApiResponse.success(chineseZodiacService.getAllZodiac());
    }

    /**
     * 获取指定生肖运势
     * @param animal 生肖标识 (rat, ox, tiger, rabbit, dragon, snake, horse, goat, monkey, rooster, dog, pig)
     */
    @GetMapping("/zodiac/{animal}")
    public ApiResponse<ChineseZodiac> getZodiac(@PathVariable String animal) {
        return ApiResponse.success(chineseZodiacService.getZodiac(animal.toLowerCase()));
    }

    // ==================== 幸运元素 ====================

    /**
     * 获取今日幸运元素
     */
    @GetMapping("/lucky")
    public ApiResponse<LuckyElement> getTodayLucky() {
        return ApiResponse.success(luckyElementService.getTodayLuckyElement());
    }
}
