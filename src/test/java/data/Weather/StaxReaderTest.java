package data.Weather;

import org.junit.Assert;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StaxReaderTest extends StaxReader {

    @TestFactory
    Collection<DynamicTest> parseWeatherForecast() {
        FiveDaysForecast fiveDaysForecast = null;
        InputStream inputStream = StaxReaderTest.class.getResourceAsStream("/forecast.xml");
        fiveDaysForecast = parseWeatherForecast(inputStream);

        List<DayForecast> fourth = fiveDaysForecast.getFirstDayForecast();
        List<DayForecast> sixth = fiveDaysForecast.getThirdDayForecast();
        List<DayForecast> seventhy = fiveDaysForecast.getFourthDayForecast();

        return Arrays.asList(
                DynamicTest.dynamicTest("6 day, day",
                        () -> assertEquals("6", fourth.get(0).getDay())),
                DynamicTest.dynamicTest("4 day, weather state",
                        () -> assertEquals("слегка облачно", fourth.get(0).getWeatherState())),
                DynamicTest.dynamicTest("6 day, temperature",
                        () -> assertEquals("14", sixth.get(1).getTemperature())),
                DynamicTest.dynamicTest("6 day, Humidity",
                        () -> assertEquals("87", sixth.get(1).getHumidity())),
                DynamicTest.dynamicTest("7 day,  weather state",
                        () -> assertEquals("легкий дождь", seventhy.get(0).getWeatherState())),
                DynamicTest.dynamicTest("7 day, preciptation",
                        () -> assertEquals("1.562", seventhy.get(0).getPrecipitation()))
                );
    }


}