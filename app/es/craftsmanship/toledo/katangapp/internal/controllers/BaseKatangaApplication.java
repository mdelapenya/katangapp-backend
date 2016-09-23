/**
 *    Copyright 2016-today Software Craftmanship Toledo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package es.craftsmanship.toledo.katangapp.internal.controllers;

import es.craftsmanship.toledo.katangapp.api.controllers.PrettyPrinter;
import es.craftsmanship.toledo.katangapp.api.exception.APIException;

import com.fasterxml.jackson.databind.JsonNode;

import play.libs.F;

import play.mvc.Controller;
import play.mvc.Result;

/**
 * @author mdelapenya
 */
public abstract class BaseKatangaApplication extends Controller {

	protected Result prettyPrint(JsonNode jsonNode) {
		PrettyPrinter prettyPrinter = new JsonPrettyPrinter(
			request(), jsonNode);

		return prettyPrinter.prettyPrint();
	}

	protected F.Promise<Result> recover(F.Promise<Result> promise) {
		return promise.recover(
			throwable -> {
				final APIException apiException = new APIException(
					throwable.getMessage());

				return badRequest(apiException.getApiError());
			}
		);
	}
}