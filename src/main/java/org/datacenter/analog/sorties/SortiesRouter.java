package org.datacenter.analog.sorties;

import io.vertx.core.MultiMap;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;
import lombok.extern.slf4j.Slf4j;

/**
 * @author : [wangminan]
 * @description : 架次路由
 */
@Slf4j
public class SortiesRouter {

    public static void sorties(Router router) {
        log.info("SortiesRouter is being registered.");
        Route route = router.route("/task/dataAsset/querySortiesByBatchId");
        route.handler(routingContext -> {
            log.info("SortiesRouter is being handled.");
            MultiMap map = routingContext.queryParams();
            String batchId = map.get("batchId");
            if (batchId == null) {
                log.error("Request body is null or not a valid JSON");
                routingContext.response().setStatusCode(400).end("Bad Request: Missing required fields");
                return;
            }
            Long id = Long.parseLong(routingContext.request().getParam("batchId"));
            log.info("Sortie id:{}", id);
            // 返回数据
            routingContext.response().putHeader("content-type", "application/json");
            routingContext.response().end("""
                    {
                    	"code": "",
                    	"data": [
                    		{
                    			"airplaneModel": "",
                    			"airplaneNumber": "",
                    			"armType": "",
                    			"batchNumber": "1",
                    			"camp": 0,
                    			"campStr": "",
                    			"carEndTime": "",
                    			"carStartTime": "",
                    			"createTime": "",
                    			"downPilot": "",
                    			"folderId": 0,
                    			"folderName": "",
                    			"icdVersion": "",
                    			"interpolation": 0,
                    			"isEffective": 0,
                    			"isEffectiveName": "",
                    			"location": "",
                    			"pilot": "",
                    			"qxId": "",
                    			"remark": "",
                    			"role": 0,
                    			"roleStr": "",
                    			"skyTime": "",
                    			"sortieId": "1",
                    			"sortieNumber": "20250303_五_01_ACT-3_邱陈_J16_07#02",
                    			"source": 0,
                    			"stealth": 0,
                    			"stealthStr": "",
                    			"subject": "",
                    			"syncSystem": 0,
                    			"syncSystemStr": "",
                    			"testDrive": 0,
                    			"testDriveStr": "",
                    			"upPilot": ""
                    		}
                    	],
                    	"message": "",
                    	"success": true
                    }
                    """);
        });
    }
}
