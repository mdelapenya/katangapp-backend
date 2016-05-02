package es.craftsmanship.toledo.katangapp.business;

import static org.fest.assertions.Assertions.assertThat;

import es.craftsmanship.toledo.katangapp.internal.BusStopsFinder;
import es.craftsmanship.toledo.katangapp.internal.algorithm.SegmentsAlgorithm;
import es.craftsmanship.toledo.katangapp.internal.parser.HTMLParser;
import es.craftsmanship.toledo.katangapp.mocks.MockHttpService;
import es.craftsmanship.toledo.katangapp.models.BusStopResult;
import es.craftsmanship.toledo.katangapp.models.Point;
import es.craftsmanship.toledo.katangapp.models.QueryResult;
import es.craftsmanship.toledo.katangapp.models.TestPointFactory;
import es.craftsmanship.toledo.katangapp.test.SpecsContants;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import play.libs.F.Promise;

import play.test.WithApplication;

/**
 * @author mdelapenya
 */
public class FinderTest extends WithApplication {

	@Before
	public void setUp() {
		busStopFinder = new BusStopsFinder(
			new SegmentsAlgorithm(), new HTMLParser(),
			new MockHttpService("P001"));
	}

	@Test
	public void testFindRoutes() throws Exception {
		Point puertaBisagra = TestPointFactory.getPuertaBisagra();
		int radius = 2000;

		Promise<QueryResult> queryResultPromise = busStopFinder.findRoutes(
			puertaBisagra.getLatitude(), puertaBisagra.getLongitude(), radius);

		QueryResult queryResult = queryResultPromise.get(SpecsContants.TIMEOUT);

		List<BusStopResult> results = queryResult.getResults();

		assertThat(results).hasSize(SegmentsAlgorithm.DEFAULT_MAX_ELEMENTS);
	}

	@Test
	public void testFindRoutesWithoutRadiusShouldNotReturnRoutes()
		throws Exception {

		Point puertaBisagra = TestPointFactory.getPuertaBisagra();
		int radius = 0;

		Promise<QueryResult> queryResultPromise = busStopFinder.findRoutes(
			puertaBisagra.getLatitude(), puertaBisagra.getLongitude(), radius);

		QueryResult queryResult = queryResultPromise.get(SpecsContants.TIMEOUT);

		List<BusStopResult> results = queryResult.getResults();

		assertThat(results).hasSize(0);
	}

	private Finder busStopFinder;

}
