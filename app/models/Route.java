package models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author mdelapenya
 */
public class Route {

	public Route() {
		this.busStops = Collections.emptyList();
	}

	public Route(String id, String name) {
		this.busStops = Collections.emptyList();
		this.id = id;
		this.name = name;
	}

	public void addBusStop(BusStop busStop) {
		if (busStops == null) {
			busStops = new ArrayList<>();
		}

		this.busStops.add(busStop);
	}

	public List<BusStop> getBusStops() {
		return busStops;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setBusStops(List<BusStop> busStops) {
		this.busStops = busStops;
	}

	private List<BusStop> busStops;
	private String id;
	private String name;

}
