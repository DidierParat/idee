package idee.provider.trip;

import idee.provider.trip.model.ProviderTrip;

import java.util.List;

public interface TripProvider {

  List<ProviderTrip> getTripsInArea(
      final String longitude, final String latitude, final String searchRadiusKm);
}
