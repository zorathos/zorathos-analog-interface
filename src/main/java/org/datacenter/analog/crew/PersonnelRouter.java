package org.datacenter.analog.crew;

import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import lombok.extern.slf4j.Slf4j;

/**
 * @author : [wangminan]
 * @description : 人员路由
 */
@Slf4j
public class PersonnelRouter {

    public static void crews(Router router) {
        log.info("Crews API is being registered");
        // 人员系统查询
        Route route = router.route("/FXY/BindFxyLb");
        route.handler(routingContext -> {
                    // 检查cookie
                    if (PersonnelRouter.checkCookie(routingContext)) return;

                    log.info("Personnel system query API is being called.");
                    // 检查参数
                    String dwdmStr = routingContext.request().getParam("dwdm");
                    if (!dwdmStr.equals("90121")) {
                        routingContext.response().setStatusCode(400).end("Bad Request");
                        return;
                    }

                    // 处理请求
                    routingContext.response().putHeader("content-type", "application/json");
                    routingContext.response().end("""
                            [
                                 {
                                     "DWDM": "90121",
                                     "GRBS": "9428051",
                                     "XM": "王小明",
                                     "ZW": "副主任",
                                     "PXH": "1.0",
                                     "DH": "2602",
                                     "BM": "军直",
                                     "DZ": "许",
                                     "SFKZZHY": "是",
                                     "ZBZT": "是",
                                     "SFXY": "是",
                                     "SFJG": "0",
                                     "XGSJ": "2025-01-07",
                                     "LNZSJ": "28:39"
                                 },
                                 {
                                     "DWDM": "90121",
                                     "DW": "试验基地",
                                     "GRBS": "9890538",
                                     "XM": "吴昊",
                                     "ZW": "主任",
                                     "XB": "男",
                                     "RZNY": "2016-06-01",
                                     "JG": "北京",
                                     "JTCS": "军人",
                                     "WHCD": "本科",
                                     "SR": "1998-07-01",
                                     "RWNY": "1998-08-01",
                                     "PJNY": "2016-03-02",
                                     "BYXY": "三飞院",
                                     "BYNY": "2004-05-01",
                                     "JX": "大校",
                                     "ZFJS": "前后舱",
                                     "FXDJ": "特级",
                                     "XFJX": "歼-xx,歼-xx",
                                     "PXH": "2.0",
                                     "DZ": "吴",
                                     "SFKZZHY": "是",
                                     "FXTG": "歼-xx",
                                     "ZJ": "四代机",
                                     "ZHSP_ZJ": "师级",
                                     "ZHSP_YJ": "师级",
                                     "JY": "是",
                                     "LLJY": "4,飞行原理，战术理论",
                                     "ZBZT": "是",
                                     "SFXY": "是",
                                     "SFJG": "是",
                                     "QB": "",
                                     "ZHTSSJ_L": "2021-05-13",
                                     "ZHTSSJ_S": "2022-06-16",
                                     "XGSJ": "2025-01-07",
                                     "LNZSJ": "2055:47",
                                     "BNZSJ": "5:57",
                                     "LNRJSJ": "86:29"
                                 }
                             ]
                            """);
                });
    }

    private static boolean checkCookie(RoutingContext routingContext) {
        // 检查header
        if (routingContext.request().getHeader("Cookie").isEmpty() ||
                routingContext.request().getHeader("Set-Cookie").isEmpty()) {
            HttpServerResponse response = routingContext.response();
            response.setStatusCode(401).end("Unauthorized");
            return true;
        }
        return false;
    }
}
