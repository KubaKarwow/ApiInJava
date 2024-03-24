/**
 * @author Karwowski Jakub S27780
 */

package zad1;
//Napisać aplikację, udostępniającą GUI, w którym po podanu miasta i nazwy kraju pokazywane są:
//Informacje o aktualnej pogodzie w tym mieście.
//Informacje o kursie wymiany walutu kraju wobec podanej przez uzytkownika waluty.
//Informacje o kursie NBP złotego wobec tej waluty podanego kraju.
//Strona wiki z opisem miasta.
//W p. 1 użyć serwisu api.openweathermap.org, w p. 2 - serwisu open.er-api.com (dok: https://www.exchangerate-api.com/docs/free),
// w p. 3 - informacji ze stron NBP: https://nbp.pl/statystyka-i-sprawozdawczosc/kursy/tabela-a/ i analogicznie dla tabel B i C.
//W p. 4 użyć klasy WebEngine z JavaFX dla wbudowania przeglądarki w aplikację Swingową.
//
//Program winien zawierać klasę Service z konstruktorem Service(String kraj) i metodami::
//String getWeather(String miasto) - zwraca informację o pogodzie w podanym mieście danego kraju w formacie JSON (to ma być pełna informacja uzyskana z serwisu openweather - po prostu tekst w formacie JSON),
//Double getRateFor(String kod_waluty) - zwraca kurs waluty danego kraju wobec waluty podanej jako argument,
//Double getNBPRate() - zwraca kurs złotego wobec waluty danego kraju

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Currency;
import java.util.Locale;
import java.util.stream.Collectors;

public class Service {
    private String kraj;

    public void setKraj(String kraj) {
        this.kraj = kraj;
    }

    public String getKraj() {
        return kraj;
    }

    public Service(String kraj) {
        this.kraj=kraj;
        System.out.println(getCountryCode(kraj));
        System.out.println(getCurrencyCode());
    }
    private String getCountryCode(String country){
        Locale[] locales = Locale.getAvailableLocales();
        for( Locale locale: locales){
            if(locale.getDisplayCountry(Locale.ENGLISH).equals(country)){
                return locale.getCountry();
            }
        }
        return null;
    }

    public String getWeather(String miasto) {
        //coordinates part
        double lon=0.0;
        double lat=0.0;
        String apiKey = "9d0d88dac57771341828f5408a6bdcbe";
        String countryCode= getCountryCode(kraj);
        String urlC="http://api.openweathermap.org/geo/1.0/direct?q="+miasto+","+countryCode+"&limit=1&appid="+apiKey;
        System.out.println(urlC);
        try (BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(
                        new URI(urlC).toURL().openConnection().getInputStream()
                )
        );){
            String json= bufferedReader.lines().collect(Collectors.joining());
            System.out.println(json);
            JSONArray parse = (JSONArray) new JSONParser().parse(json);
            for (Object object : parse){
                JSONObject jsonObject = (JSONObject) object;
                System.out.println(jsonObject.get("name"));
                System.out.println(jsonObject.get("country"));
                System.out.println(miasto);
                System.out.println(kraj);
                if(jsonObject.get("name").equals(miasto) && jsonObject.get("country").equals(countryCode)){
                    lon = (double) jsonObject.get("lon");
                    lat = (double) jsonObject.get("lat");
                }
            }
        } catch (URISyntaxException | IOException | ParseException e) {
            throw new RuntimeException(e);
        }
        System.out.println(lon+" " + lat);

        String url = "https://api.openweathermap.org/data/2.5/weather?lat="+lat+"&lon="+lon+"&units:imperial"+"&appid=" + apiKey;
        try (
                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(
                                new URI(url).toURL().openConnection().getInputStream()));
        ) {
            String json = bufferedReader.lines().collect(Collectors.joining());
            System.out.println(json);
            Object parse = new JSONParser().parse(json);
            System.out.println(parse);
            // here find out how exactly do you "weather" so like wind,temp,cisnienie etc

        } catch (URISyntaxException | IOException | ParseException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public Double getRateFor(String kod_waluty) {
        String currencyCode = getCurrencyCode();
        String url = "https://open.er-api.com/v6/latest/"+currencyCode;
        double result=0.0;
        try(
                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(new URI(url).toURL().openConnection().getInputStream())
                );
                ){
            String json = bufferedReader.lines().collect(Collectors.joining());
            JSONObject jsonObject = (JSONObject) new JSONParser().parse(json);
            JSONObject jsonObject1 =(JSONObject) jsonObject.get("rates");
            return Double.parseDouble(jsonObject1.get(kod_waluty).toString());
        } catch (IOException | URISyntaxException | ParseException e) {
            throw new RuntimeException(e);
        }
    }
    private String getCurrencyCode(){
        String countryCode= getCountryCode(kraj);
        Locale locale = new Locale("",countryCode);
        Currency currency = Currency.getInstance(locale);
        return currency.getCurrencyCode();
    }
    public Double getNBPRate() {
        String url = "https://static.nbp.pl/dane/kursy/xml/a059z240322.xml";
        try (
                BufferedReader bufferedReader= new BufferedReader(
                        new InputStreamReader(new URI(url).toURL().openConnection().getInputStream()));
                ){
                String json = bufferedReader.lines().collect(Collectors.joining());
            System.out.println(json);
               Object jsonObject= new JSONParser().parse(json);
                System.out.println(jsonObject + "XDXDDXDXD");
        } catch (IOException | URISyntaxException | ParseException e) {
            throw new RuntimeException(e);
        }
        return 0.0;
    }
}
