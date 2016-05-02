package es.craftsmanship.toledo.katangapp.internal.store;

import com.fasterxml.jackson.databind.JsonNode;

import es.craftsmanship.toledo.katangapp.business.store.JsonStore;

import java.io.InputStream;

import play.api.Play;

import play.libs.Json;

import scala.Option;

/**
 * @author mdelapenya
 */
public abstract class BaseJsonStore implements JsonStore {

	public BaseJsonStore() {
		jsonNode = readJsonStore();
	}

	@Override
	public JsonNode getJson() {
		return jsonNode;
	}

	protected abstract String getPath();

	protected JsonNode readJsonStore() {
		final play.api.Application application = Play.current();

		final Option<InputStream> routesInputStream =
			application.resourceAsStream(getPath());

		return Json.parse(routesInputStream.get());
	}

	protected JsonNode jsonNode;

}
