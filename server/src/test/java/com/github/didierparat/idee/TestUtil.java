package com.github.didierparat.idee;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

public class TestUtil {

  public static final String RESOURCE_TRIPS = "trips.json";
  public static final String RESOURCE_WEATHER_SUNNY = "weather_sunny.json";
  public static final String RESOURCE_WEATHER_RAINY = "weather_rainy.json";

  /**
   * Read value from a resource file.
   * @param resourceFilePath Path to the resource file.
   * @param valueType Type to convert to file read into.
   * @return Resource file parsed as valueType.
   * @throws IOException If the resource file could not be converted into the value type.
   */
  public static <T> T readValue(String resourceFilePath, Class<T> valueType) throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    InputStream resourceFileAsStream
        = TestUtil.class.getClassLoader().getResourceAsStream(resourceFilePath);
    return objectMapper.readValue(resourceFileAsStream, valueType);
  }

  /**
   * Read value from a resource file. Used for collections for example.
   * @param resourceFilePath Path to the resource file.
   * @param valueType  Type to convert to file read into.
   * @return Resource file parsed as valueType.
   * @throws IOException If the resource file could not be converted into the value type.
   */
  public static <T> T readValue(
      String resourceFilePath, TypeReference<T> valueType) throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    InputStream resourceFileAsStream
        = TestUtil.class.getClassLoader().getResourceAsStream(resourceFilePath);
    return objectMapper.readValue(resourceFileAsStream, valueType);
  }
}
