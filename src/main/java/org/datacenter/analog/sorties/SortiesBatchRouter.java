package org.datacenter.analog.sorties;

import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author : [wangminan]
 * @description : 批次路由
 */
@Slf4j
public class SortiesBatchRouter {
    public static void sortiesBatch(Router router) {
        log.info("SortiesBatchRouter is being registered.");
        Route route = router.route("/task/dataAsset/queryAllBatches");
        route.handler(BodyHandler.create());
        route.handler(routingContext -> {
            log.info("SortiesBatchRouter is being handled.");
            // 先检查请求体是否为空
            if (routingContext.body() == null || routingContext.body().asJsonObject() == null) {
                log.error("Request body is null or not a valid JSON");
                HttpServerResponse response = routingContext.response();
                response.setStatusCode(400).end("Bad Request: Missing or invalid JSON body");
                return;
            }

            // 取内容
            String acmiTimeEnd = routingContext.body().asJsonObject().getString("acmiTimeEnd");
            String acmiTimeStart = routingContext.body().asJsonObject().getString("acmiTimeStart");

            if (acmiTimeEnd == null || acmiTimeStart == null) {
                log.error("Request body is null or not a valid JSON");
                HttpServerResponse response = routingContext.response();
                response.setStatusCode(400).end("Bad Request: Missing required fields");
                return;
            }

            // 返回数据
            HttpServerResponse response = routingContext.response();
            response.putHeader("content-type", "application/json");
            response.end("""
                    {
                    	"code": "",
                    	"data": [
                    		{
                    			"batchNumber": "1",
                    			"id": "1"
                    		}
                    	],
                    	"message": "",
                    	"success": true
                    }
                    """);
        });
    }
}
