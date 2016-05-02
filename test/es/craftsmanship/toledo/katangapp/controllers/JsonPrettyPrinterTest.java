package es.craftsmanship.toledo.katangapp.controllers;

import static org.fest.assertions.Assertions.assertThat;

import static play.mvc.Http.Status.OK;

import static play.test.Helpers.contentAsString;
import static play.test.Helpers.contentType;
import static play.test.Helpers.status;

import es.craftsmanship.toledo.katangapp.internal.controllers.JsonPrettyPrinter;
import es.craftsmanship.toledo.katangapp.mocks.MockController;
import es.craftsmanship.toledo.katangapp.internal.store.KatangappStore;
import es.craftsmanship.toledo.katangapp.test.SpecsContants;
import es.craftsmanship.toledo.katangapp.models.BusStop;

import org.junit.Test;

import play.libs.Json;

import play.mvc.Http;
import play.mvc.Result;

import play.test.WithApplication;

/**
 * @author mdelapenya
 */
public class JsonPrettyPrinterTest extends WithApplication {

	@Test
	public void testDoNotPrettyPrint() throws Exception {
		Http.Request request = MockController.mockRequest(false);

		BusStop busStop = KatangappStore.getInstance().getBusStop("P001");

		PrettyPrinter prettyPrinter = new JsonPrettyPrinter(
			request, Json.toJson(busStop));

		Result result = prettyPrinter.prettyPrint();

		assertThat(status(result)).isEqualTo(OK);
		assertThat(contentType(result)).isEqualTo("application/json");

		assertThat(contentAsString(result)).
			isEqualTo(SpecsContants.BUS_STOP_JSON);
	}

	@Test
	public void testPrettyPrint() throws Exception {
		Http.Request request = MockController.mockRequest(true);

		BusStop busStop = KatangappStore.getInstance().getBusStop("P001");

		PrettyPrinter prettyPrinter = new JsonPrettyPrinter(
			request, Json.toJson(busStop));

		Result result = prettyPrinter.prettyPrint();

		assertThat(status(result)).isEqualTo(OK);
		assertThat(contentType(result)).isEqualTo("application/json");

		assertThat(contentAsString(result)).
			isEqualTo(SpecsContants.BUS_STOP_PRETTIFIED_JSON);
	}

}