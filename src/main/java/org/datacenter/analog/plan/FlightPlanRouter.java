package org.datacenter.analog.plan;

import io.vertx.core.MultiMap;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author : [wangminan]
 * @description : 飞行计划路由
 */
@Slf4j
public class FlightPlanRouter {


    public static void flightDates(Router router) {
        log.info("FlightDates API is being registered");
        Route flightDates = router.route(HttpMethod.GET, "/FXJH/GetFXRQ");
        flightDates.handler(routingContext -> {
            if (checkCookie(routingContext)) return;

            // 检查参数
            MultiMap map = routingContext.queryParams();
            String fromDateStr = map.get("from");
            String toDateStr = map.get("to");
            String dwdmStr = map.get("dwdm");

            if (fromDateStr.isEmpty() || toDateStr.isEmpty() || !dwdmStr.equals("90121")) {
                HttpServerResponse response = routingContext.response();
                response.setStatusCode(400).end("Bad Request");
                return;
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(toDateStr, formatter);
            log.info("Flight dates from 1970-01-01 to {} is being requested.", localDate);
            // 处理请求
            HttpServerResponse response = routingContext.response();
            response.putHeader("content-type", "application/json");
            response.end("""
                    [
                        {"FXRQ": "2023-10-01"},
                        {"FXRQ": "2023-10-02"},
                        {"FXRQ": "2023-10-03"},
                        {"FXRQ": "2023-10-04"},
                        {"FXRQ": "2023-10-05"},
                        {"FXRQ": "2023-10-06"},
                        {"FXRQ": "2023-10-07"},
                        {"FXRQ": "2023-10-08"},
                        {"FXRQ": "2023-10-09"}
                    ]
                    """);
        });
    }

    public static void planCodes(Router router) {
        log.info("PlanCodes API is being registered");
        Route planCodes = router.route(HttpMethod.GET, "/FXDT/BindJHxx");
        planCodes.handler(routingContext -> {
            log.info("PlanCodes API is being handled.");
            if (checkCookie(routingContext)) return;

            // 检查参数
            MultiMap map = routingContext.queryParams();
            String rqStr = map.get("rq");
            String dwdmStr = map.get("dwdm");
            String extraStr = map.get("_");
            if (rqStr.isEmpty() || !dwdmStr.equals("90121") || extraStr.isEmpty()) {
                HttpServerResponse response = routingContext.response();
                response.setStatusCode(400).end("Bad Request");
                return;
            }
            // 让我们随机返回一点就好
            log.info("Flight plan codes for date: {} is being requested.", rqStr);
            // 处理请求
            HttpServerResponse response = routingContext.response();
            response.putHeader("content-type", "application/json");
            // 一天只有一条计划编号
            response.end("""
                    {
                        "LB": "【主计划】评估中 鼎新xxx",
                        "JHBH": "%s-90121-65321",
                        "DWDM": "90121",
                        "BDMC": "评估中"
                    }
                    """.formatted(rqStr.replace("-", "")));
        });
    }

    public static void flightXml(Router router) {
        log.info("FlightXml API is being registered");
        Route flightXml = router.route(HttpMethod.GET, "/FXDT/GetXML");
        flightXml.handler(routingContext -> {
            if (checkCookie(routingContext)) return;

            // 检查参数
            MultiMap map = routingContext.queryParams();
            String jhbhStr = map.get("jhbh");
            if (jhbhStr.isEmpty()) {
                HttpServerResponse response = routingContext.response();
                response.setStatusCode(400).end("Bad Request");
                return;
            }
            log.info("Flight XML for plan code: {} is being requested.", jhbhStr);
            String xml = "";
            try {
                xml = Files.readString(Path.of("D:\\0_大学\\2024.9\\实验室\\数据中台\\数据模型\\0_业务和武器_定\\4_人员档案及飞行计划\\飞行计划样例数据.xml"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            // 处理请求
            HttpServerResponse response = routingContext.response();
            response.putHeader("content-type", "application/json");
            response.end("""
                    {
                        "RQ": "%s",
                        "XML": "%s"
                    }
                    """.formatted(jhbhStr.split("-")[0], xml));
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
