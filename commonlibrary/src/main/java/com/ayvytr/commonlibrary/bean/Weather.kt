package com.ayvytr.commonlibrary.bean

/**
 * @author Do
 */
class Weather {

    /**
     * msg : success
     * result : [{"airCondition":"良","city":"北京","coldIndex":"低发期","updateTime":"20150908153820","date":"2015-09-08","distrct":"门头沟","dressingIndex":"短袖类","exerciseIndex":"适宜","future":[{"date":"2015-09-09","dayTime":"阵雨","night":"阴","temperature":"24°C/18°C","week":"星期三","wind":"无持续风向小于3级"},{"date":"2015-09-10","dayTime":"阵雨","night":"阵雨","temperature":"22°C/15°C","week":"星期四","wind":"无持续风向小于3级"},{"date":"2015-09-11","dayTime":"阴","night":"晴","temperature":"23°C/15°C","week":"星期五","wind":"北风3～4级无持续风向小于3级"},{"date":"2015-09-12","dayTime":"晴","night":"晴","temperature":"26°C/13°C","week":"星期六","wind":"北风3～4级无持续风向小于3级"},{"date":"2015-09-13","dayTime":"晴","night":"晴","temperature":"27°C/16°C","week":"星期日","wind":"无持续风向小于3级"},{"date":"2015-09-14","dayTime":"晴","night":"多云","temperature":"27°C/16°C","week":"星期一","wind":"无持续风向小于3级"},{"date":"2015-09-15","dayTime":"少云","night":"晴","temperature":"26°C/14°C","week":"星期二","wind":"南风3级南风2级"},{"date":"2015-09-16","dayTime":"局部多云","night":"少云","temperature":"26°C/15°C","week":"星期三","wind":"南风3级南风2级"},{"date":"2015-09-17","dayTime":"阴天","night":"局部多云","temperature":"26°C/15°C","week":"星期四","wind":"东南风2级"}],"humidity":"湿度：46%","province":"北京","sunset":"18:37","sunrise":"05:49","temperature":"25℃","time":"14:35","washIndex":"不适宜","weather":"多云","week":"周二","wind":"南风2级"}]
     * retCode : 200
     */

    var msg: String? = null
    var retCode: String? = null
    var result: List<ResultBean>? = null

    class ResultBean {
        /**
         * airCondition : 良
         * city : 北京
         * coldIndex : 低发期
         * updateTime : 20150908153820
         * date : 2015-09-08
         * distrct : 门头沟
         * dressingIndex : 短袖类
         * exerciseIndex : 适宜
         * future : [{"date":"2015-09-09","dayTime":"阵雨","night":"阴","temperature":"24°C/18°C","week":"星期三","wind":"无持续风向小于3级"},{"date":"2015-09-10","dayTime":"阵雨","night":"阵雨","temperature":"22°C/15°C","week":"星期四","wind":"无持续风向小于3级"},{"date":"2015-09-11","dayTime":"阴","night":"晴","temperature":"23°C/15°C","week":"星期五","wind":"北风3～4级无持续风向小于3级"},{"date":"2015-09-12","dayTime":"晴","night":"晴","temperature":"26°C/13°C","week":"星期六","wind":"北风3～4级无持续风向小于3级"},{"date":"2015-09-13","dayTime":"晴","night":"晴","temperature":"27°C/16°C","week":"星期日","wind":"无持续风向小于3级"},{"date":"2015-09-14","dayTime":"晴","night":"多云","temperature":"27°C/16°C","week":"星期一","wind":"无持续风向小于3级"},{"date":"2015-09-15","dayTime":"少云","night":"晴","temperature":"26°C/14°C","week":"星期二","wind":"南风3级南风2级"},{"date":"2015-09-16","dayTime":"局部多云","night":"少云","temperature":"26°C/15°C","week":"星期三","wind":"南风3级南风2级"},{"date":"2015-09-17","dayTime":"阴天","night":"局部多云","temperature":"26°C/15°C","week":"星期四","wind":"东南风2级"}]
         * humidity : 湿度：46%
         * province : 北京
         * sunset : 18:37
         * sunrise : 05:49
         * temperature : 25℃
         * time : 14:35
         * washIndex : 不适宜
         * weather : 多云
         * week : 周二
         * wind : 南风2级
         */

        var airCondition: String? = null
        var city: String? = null
        var coldIndex: String? = null
        var updateTime: String? = null
        var date: String? = null
        var distrct: String? = null
        var dressingIndex: String? = null
        var exerciseIndex: String? = null
        var humidity: String? = null
        var province: String? = null
        var sunset: String? = null
        var sunrise: String? = null
        var temperature: String? = null
        var time: String? = null
        var washIndex: String? = null
        var weather: String? = null
        var week: String? = null
        var wind: String? = null
        var future: List<FutureBean>? = null

        class FutureBean {
            /**
             * date : 2015-09-09
             * dayTime : 阵雨
             * night : 阴
             * temperature : 24°C/18°C
             * week : 星期三
             * wind : 无持续风向小于3级
             */

            var date: String? = null
            var dayTime: String? = null
            var night: String? = null
            var temperature: String? = null
            var week: String? = null
            var wind: String? = null
        }
    }
}
