package es.craftsmanship.toledo.katangapp.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;

import es.craftsmanship.toledo.katangapp.business.Finder;
import es.craftsmanship.toledo.katangapp.business.http.HttpService;
import es.craftsmanship.toledo.katangapp.internal.BusStopsFinder;
import es.craftsmanship.toledo.katangapp.internal.http.UnautoHttpService;

/**
 * @author mdelapenya
 */
public class FinderModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(Finder.class).to(BusStopsFinder.class);
	}

	@Provides
	HttpService provideHttpService() {
		HttpService httpService = new UnautoHttpService();

		return httpService;
	}

}