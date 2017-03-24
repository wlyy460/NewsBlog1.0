package com.inventec.newsblog.inter;

/**
 * 网络请求相关配置接口
 * Created by Administrator on 2017/3/7.
 */

public interface BizInterface {

    /**
     * 百度API接口
     */
    String BAIDU_API = "http://apis.baidu.com";
    /**
     * 易源API接口（官方）
     */
    String SHOW_API = "http://route.showapi.com";
    /**
     * 百度开发者API密钥
     */
    String API_KEY = "4720bdbcfb3aa457eefd38d2f8fa580f";
    /**
     * 易源api密钥
     */
    String SHOW_API_KEY = "ae06b1ecff2847dba442b9433032f489";//5556e296fd184518a4b6dafb4d169372
    /**
     * 易源appid
     */
    String SHOW_API_APPID = "31108";//32530

    /**
     * 新闻接口
     服务商： 易源接口
     */
    String NEWS_URL = "/109-35";
    /**
     * 天气预报 (根据地名)
     服务商： 易源接口
     */
    String WEATHER_URL = "/showapi_open_bus/weather_showapi/address";

    /**
     * 美图大全 (根据类型)
     * "list":
     * ["name": "社会百态",
     {
     "id": 1001,
     "name": "社会新闻"
     }
     {
     "id": 1002,
     "name": "国内新闻"
     }
     {
     "id": 1003,
     "name": "环球动态"
     }
     {
     "id": 1004,
     "name": "军事新闻"
     }]

     "name": "明星写真",
     "list": [
     {
     "id": 2001,
     "name": "中国明星"//
     },
     {
     "id": 2002,
     "name": "欧美明星"
     },
     {
     "id": 2003,
     "name": "中国女明星"
     },
     {
     "id": 2004,
     "name": "中国男明星"
     },
     {
     "id": 2005,
     "name": "韩国明星"
     },
     {
     "id": 2006,
     "name": "欧美女明星"
     },
     {
     "id": 2007,
     "name": "欧美男明星"
     }
     ]

     "name": "美女图片"[
     {
     "id": 4001, //此id很重要，在【图片查询】接口里将使用此id进行分类查询
     "name": "清纯"
     },
     {
     "id": 4002,
     "name": "气质"
     },
     {
     "id": 4003,
     "name": "萌女"
     },
     {
     "id": 4004,
     "name": "校花"
     },
     {
     "id": 4005,
     "name": "婚纱"
     },
     {
     "id": 4006,
     "name": "街拍"
     },
     {
     "id": 4007,
     "name": "非主流"
     },
     {
     "id": 4008,
     "name": "美腿"
     },
     {
     "id": 4009,
     "name": "性感"
     },
     {
     "id": 4010,
     "name": "车模"
     },
     {
     "id": 4011,
     "name": "男色图片"
     },
     {
     "id": 4012,
     "name": "模特美女"
     },
     {
     "id": 4013,
     "name": "美女魅惑"
     },
     {
     "id": 4014,
     "name": "日韩美女"
     }
     ],

     "name": "生活趣味",
     "list": [
     {
     "id": 6001,
     "name": "居家"
     },
     {
     "id": 6002,
     "name": "萌宠"
     },
     {
     "id": 6003,
     "name": "美食图片"
     },
     {
     "id": 6004,
     "name": "美丽风景"
     },
     {
     "id": 6005,
     "name": "奇趣自然"
     },
     {
     "id": 6006,
     "name": "植物花卉"
     }
     ]
     "name": "娱乐八卦",
     "list": [
     {
     "id": 3001,
     "name": "娱乐组图"
     },
     {
     "id": 3002,
     "name": "八卦爆料"
     },
     {
     "id": 3003,
     "name": "电影海报"
     },
     {
     "id": 3004,
     "name": "影视剧照"
     },
     {
     "id": 3005,
     "name": "活动现场"
     }
     ]

     "name": "时尚伊人",
     "list": [
     {
     "id": 5001,
     "name": "秀场"//
     },
     {
     "id": 5002,
     "name": "霓裳"
     },
     {
     "id": 5003,
     "name": "鞋包"
     },
     {
     "id": 5004,
     "name": "婚嫁"
     },
     {
     "id": 5005,
     "name": "妆容"
     },
     {
     "id": 5006,
     "name": "广告大片"
     }
     ]
     服务商： 易源接口
     */
    String PICTURES_URL = "/showapi_open_bus/pic/pic_search";
}
