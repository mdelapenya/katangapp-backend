package es.craftsmanship.toledo.katangapp.controllers;

import com.fasterxml.jackson.databind.JsonNode;

import com.google.inject.Inject;

import es.craftsmanship.toledo.katangapp.business.Finder;
import es.craftsmanship.toledo.katangapp.business.exception.APIException;
import es.craftsmanship.toledo.katangapp.internal.controllers.JsonPrettyPrinter;
import es.craftsmanship.toledo.katangapp.models.QueryResult;

import play.libs.F.Function;
import play.libs.F.Function0;
import play.libs.F.Promise;
import play.libs.Json;

import play.mvc.Controller;
import play.mvc.Result;

/**
 * @author mdelapenya
 */
public class KatangappApplication extends Controller {

    @Inject
    public KatangappApplication(Finder busStopFinder) {
        this.busStopFinder = busStopFinder;
    }

    public Promise<Result> main(
        final String lt, final String ln, final int r) {
        
        final double dLatitude = Double.parseDouble(lt);
        final double dLongitude = Double.parseDouble(ln);

        try {
            Promise<QueryResult> queryResultPromise = busStopFinder.findRoutes(
                dLatitude, dLongitude, r);

            Promise<Result> resultPromise = queryResultPromise.map(
                new Function<QueryResult, Result>() {

                    @Override
                    public Result apply(QueryResult queryResult)
                        throws Throwable {

                        JsonNode node = Json.toJson(queryResult);

                        PrettyPrinter prettyPrinter = new JsonPrettyPrinter(
                            request(), node);

                        return prettyPrinter.prettyPrint();
                    }
                });

            return resultPromise;
        }
        catch (InterruptedException ie) {
            final APIException apiException = new APIException(ie.getMessage());

            return Promise.promise(new Function0<Result>() {

                @Override
                public Result apply() throws Throwable {
                    return notFound(apiException.getApiError());
                }

            });
        }
    }

    private Finder busStopFinder;

}