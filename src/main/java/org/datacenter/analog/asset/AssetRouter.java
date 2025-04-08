package org.datacenter.analog.asset;

import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;
import lombok.extern.slf4j.Slf4j;

/**
 * @author : [wangminan]
 * @description : 数据资产路由
 */
@Slf4j
public class AssetRouter {

    public static void assetList(Router router) {
        log.info("Asset list API is being registered");
        Route route = router.route("/datahandle/asset/getObjectifyAsset");
        route.handler(routingContext -> {
            log.info("Asset list API is being called.");
            // 检查参数
            String weaponModel = routingContext.request().getParam("weaponModel");
            String icd = routingContext.request().getParam("icd");
            if (weaponModel == null || icd == null) {
                routingContext.response().setStatusCode(400).end("Bad Request");
                return;
            }

            // 处理请求
            routingContext.response().putHeader("content-type", "application/json");
            routingContext.response().end("""
                    {
                        "code": 1,
                        "msg": "success",
                        "result": [
                            {
                               "id": 36,
                               "name": "test_lp",
                               "fullName": "T80_111_test_lp",
                               "model": "T80",
                               "icdId": 55,
                               "icd": "111",
                               "dbName": "T80_111",
                               "source": 0,
                               "remark": null,
                               "objectifyFlag": 1,
                               "copyFlag": 0,
                               "labels": "[\\"飞机\\"]",
                               "labelList": [
                                 "飞机"
                               ],
                               "timeFrame": 50,
                               "timeType": 0
                            }
                        ]
                    }
                    """);
        });
    }

    public static void assetConfig(Router router) {
        log.info("Asset config API is being registered");
        Route route = router.route("/datahandle/asset/getAssetValidConfig");
        route.handler(routingContext -> {
            log.info("Asset config API is being called.");
            String idStr = routingContext.request().getParam("id");
            if (idStr == null) {
                routingContext.response().setStatusCode(400).end("Bad Request");
                return;
            }
            Long id = Long.parseLong(idStr);
            log.info("Asset config ID: {}", id);
            routingContext.response().putHeader("content-type", "application/json");
            routingContext.response().end("""
                    {
                      "code": 1,
                      "msg": "success",
                      "result": [
                        {
                          "assetModel": {
                            "id": 73,
                            "assetId": 36,
                            "icdId": 55,
                            "name": "T80_111_test_lp",
                            "isMaster": 1,
                            "repeatInterval": 0,
                            "repeatTimes": 0
                          },
                          "propertyList": [
                            {
                              "code": "code1",
                              "name": "消息发生时间",
                              "type": "LONG",
                              "isTime": 1,
                              "twoDDisplay": 0,
                              "label": "飞机"
                            },
                            {
                              "code": "code2",
                              "name": "[消息发布时间][时间_系统RTC]",
                              "type": "STRING",
                              "isTime": 0,
                              "twoDDisplay": 0,
                              "label": null
                            },
                            {
                              "code": "code3",
                              "name": "[消息发布者ID][功能子域单元ID]",
                              "type": "STRING",
                              "isTime": 0,
                              "twoDDisplay": 0,
                              "label": "雷达"
                            },
                            {
                              "code": "code4",
                              "name": "[控制]",
                              "type": "STRING",
                              "isTime": 0,
                              "twoDDisplay": 1,
                              "label": "飞机"
                            },
                            {
                              "code": "code5",
                              "name": "[等级]",
                              "type": "STRING",
                              "isTime": 0,
                              "twoDDisplay": 1,
                              "label": "飞机"
                            },
                            {
                              "code": "code6",
                              "name": "[状态]",
                              "type": "STRING",
                              "isTime": 0,
                              "twoDDisplay": 1,
                              "label": "飞机"
                            },
                            {
                              "code": "code7",
                              "name": "[对比度]",
                              "type": "STRING",
                              "isTime": 0,
                              "twoDDisplay": 1,
                              "label": "光雷"
                            },
                            {
                              "code": "code8",
                              "name": "[伪色彩状态]",
                              "type": "STRING",
                              "isTime": 0,
                              "twoDDisplay": 1,
                              "label": "光雷"
                            },
                            {
                              "code": "code9",
                              "name": "[图像扩展]",
                              "type": "STRING",
                              "isTime": 0,
                              "twoDDisplay": 1,
                              "label": "雷达"
                            },
                            {
                              "code": "code10",
                              "name": "[回放状态]",
                              "type": "STRING",
                              "isTime": 0,
                              "twoDDisplay": 1,
                              "label": "光雷"
                            },
                            {
                              "code": "code11",
                              "name": "[回放画面编号][图像编号]",
                              "type": "STRING",
                              "isTime": 0,
                              "twoDDisplay": 1,
                              "label": "雷达"
                            },
                            {
                              "code": "code12",
                              "name": "[视角模式]",
                              "type": "STRING",
                              "isTime": 0,
                              "twoDDisplay": 1,
                              "label": "雷达"
                            },
                            {
                              "code": "code13",
                              "name": "[图像记录状态]",
                              "type": "STRING",
                              "isTime": 0,
                              "twoDDisplay": 1,
                              "label": null
                            },
                            {
                              "code": "code14",
                              "name": "[图像重新定位状态]",
                              "type": "STRING",
                              "isTime": 0,
                              "twoDDisplay": 1,
                              "label": "雷达"
                            }
                          ]
                        }
                      ]
                    }
                    """);
        });
    }
}
