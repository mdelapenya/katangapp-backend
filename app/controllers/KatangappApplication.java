package controllers;

import business.Finder;
import business.http.HttpService;
import business.store.JsonStore;
import business.store.Store;

import internal.business.BusStopsFinder;
import internal.business.http.UnautoHttpService;
import internal.business.store.BusStopsJsonStore;
import internal.business.store.RoutesJsonStore;

import models.BusStop;
import models.QueryResult;
import models.Route;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.TextNode;

import java.util.Map;

import play.libs.Json;

import play.mvc.Controller;
import play.mvc.Result;

/**
 * @author mdelapenya
 */
public class KatangappApplication extends Controller {

    public static Result busStop(String id) {
        Store store = busStopFinder.getStore();

        BusStop busStop = store.getBusStop(id);

        if (busStop != null) {
            return ok(Json.toJson(busStop));
        }

        return notFound(NOT_FOUND_MESSAGE);
    }

    public static Result busStops() {
        return ok(busStops.getJson());
    }

    public static Result main(String lt, String ln, int r) {
        double dLatitude = Double.parseDouble(lt);
        double dLongitude = Double.parseDouble(ln);

        QueryResult queryResult = busStopFinder.findRoutes(
            dLatitude, dLongitude, r);

        boolean isPrettyPrint = isPrettyPrint();

        JsonNode node = Json.toJson(queryResult);

        if (isPrettyPrint) {
            try {
                return ok(prettyPrint(node)).as("application/json");
            }
            catch (JsonProcessingException e) {
                // fall back to default JSON print
            }
        }

        return ok(node);
    }

    public static Result route(String id) {
        Store store = busStopFinder.getStore();

        Route route = store.getRoute(id);

        if (route != null) {
            return ok(Json.toJson(route));
        }

        return notFound(NOT_FOUND_MESSAGE);
    }

    public static Result routes() {
        return ok(routes.getJson());
    }

    public static void setBusStopFinder(Finder finder) {
        busStopFinder = finder;
    }

    public static void setHttpService(HttpService service) {
        httpClient = service;
    }

    public static Result unauto(String idl, String idp, String ido) {
        String response = httpClient.get(idl, idp, ido);

        return ok(response);
    }

    private static boolean isPrettyPrint() {
        Map<String, String[]> queryStringParametersMap =
            request().queryString();

        boolean isPrettyPrint = queryStringParametersMap.containsKey(
            "prettyPrint");

        if (isPrettyPrint) {
            final String[] pretties = queryStringParametersMap.get(
                "prettyPrint");

            if (pretties[0].equalsIgnoreCase("true") ||
                pretties[0].equalsIgnoreCase("1")) {

               return true;
            }
        }

        return false;
    }

    private static String prettyPrint(JsonNode node)
        throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();

        final ObjectWriter objectWriter =
            mapper.writerWithDefaultPrettyPrinter();

        return objectWriter.writeValueAsString(node);
    }

    private static final JsonNode NOT_FOUND_MESSAGE =
        Json.newObject().set("message", new TextNode("Not Found"));

    private static JsonStore busStops = new BusStopsJsonStore();
    private static Finder busStopFinder = new BusStopsFinder();
    private static HttpService httpClient = new UnautoHttpService();
    private static JsonStore routes = new RoutesJsonStore();

}
