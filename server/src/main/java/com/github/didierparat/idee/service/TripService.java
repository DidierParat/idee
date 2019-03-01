package com.github.didierparat.idee.service;

import com.github.didierparat.idee.model.Trip;
import com.github.didierparat.idee.provider.trip.TripProvider;
import com.github.didierparat.idee.provider.trip.model.ProviderTrip;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TripService {

  private final TripProvider tripProvider;

  public TripService(final TripProvider tripProvider) {
    this.tripProvider = tripProvider;
  }

  public List<Trip> getTripsInArea(
      final String longitude, final String latitude, final String searchRadiusKm) {
    return tripProvider
        .getTripsInArea(longitude, latitude, searchRadiusKm)
        .stream()
        .map(TripService::providerTripToTrip)
        .collect(Collectors.toList());
  }

  private static Trip providerTripToTrip(final ProviderTrip providerTrip) {
    final Trip trip = null;
    // TODO
    return trip;
  }
}
