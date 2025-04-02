package org.datacenter.analog;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import lombok.extern.slf4j.Slf4j;

import static org.datacenter.analog.crew.PersonnelRouter.crews;
import static org.datacenter.analog.plan.FlightPlanRouter.flightDates;
import static org.datacenter.analog.plan.FlightPlanRouter.flightXml;
import static org.datacenter.analog.plan.FlightPlanRouter.planCodes;

@Slf4j
public class Main extends AbstractVerticle {

    private static final int PORT = 8080;

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        HttpServer server = vertx.createHttpServer();

        Router router = Router.router(vertx);

        personnelAndPlanLogin(router);
        flightDates(router);
        planCodes(router);
        flightXml(router);

        crews(router);

        Route base = router.route("/");
        base.handler(routingContext -> {
            HttpServerResponse response = routingContext.response();
            response.putHeader("content-type", "text/plain");
            response.end("Hello from Vert.x!");
        });

        log.info("Server prepared to start, will listen on port: {}", PORT);
        server.requestHandler(router).listen(PORT);
    }

    private static void personnelAndPlanLogin(Router router) {
        log.info("Login API is being registered");
        Route personnelAndPlanLogin = router.route(HttpMethod.POST, "/Home/Login");

        personnelAndPlanLogin.handler(BodyHandler.create());

        personnelAndPlanLogin.handler(routingContext -> {
            log.info("User tried to login to personnel and flight plan system.");

            // 先检查请求体是否为空
            if (routingContext.body() == null || routingContext.body().asJsonObject() == null) {
                log.error("Request body is null or not a valid JSON");
                HttpServerResponse response = routingContext.response();
                response.setStatusCode(400).end("Bad Request: Missing or invalid JSON body");
                return;
            }

            try {
                /*
                 * 检验传入参数是否如下
                 * {
                 *   "userInput": "user",
                 *   "grbsInput": "user",
                 *   "passwordInput": "123456",
                 * }
                 */
                String userInput = routingContext.body().asJsonObject().getString("userInput");
                String grbsInput = routingContext.body().asJsonObject().getString("grbsInput"); // 修正字段名
                String passwordInput = routingContext.body().asJsonObject().getString("passwordInput"); // 修正字段名

                // 检查字段是否为空
                if (userInput == null || grbsInput == null || passwordInput == null) {
                    HttpServerResponse response = routingContext.response();
                    response.setStatusCode(400).end("Bad Request: Missing required fields");
                    return;
                }

                // 逻辑判断修正 (之前使用了 !equals，应该用 equals)
                if (!("user".equals(userInput) && "user".equals(grbsInput) && "123456".equals(passwordInput))) {
                    HttpServerResponse response = routingContext.response();
                    response.setStatusCode(401).end("Unauthorized");
                    return;
                }

                // 随机生成一段像cookie的东西 包含多条文本 用分号隔开
                String cookies = "sessionId=1234567890; userId=1234567890; grbsId=1234567890";
                HttpServerResponse response = routingContext.response();
                response.putHeader("Set-Cookie", cookies);
                response.putHeader("content-type", "application/json");
                response.end("{\"code\": 200, \"msg\": \"success\"}"); // 修正JSON格式

            } catch (Exception e) {
                log.error("Error processing login request", e);
                HttpServerResponse response = routingContext.response();
                response.setStatusCode(500).end("Internal Server Error");
            }
        });
    }


}
