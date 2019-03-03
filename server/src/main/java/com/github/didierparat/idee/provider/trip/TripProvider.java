package com.github.didierparat.idee.provider.trip;

import com.github.didierparat.idee.provider.trip.model.ProviderTrip;

import java.util.List;

public interface TripProvider {

  List<ProviderTrip> getTripsInArea(
      final String longitude, final String latitude, final String searchRadiusKm);
}
