/**
 *
 *  @author Karwowski Jakub S27780
 *
 */

package zad1;

//Napisać aplikację, udostępniającą GUI, w którym po podanu miasta i nazwy kraju pokazywane są:
//Informacje o aktualnej pogodzie w tym mieście.
//Informacje o kursie wymiany walutu kraju wobec podanej przez uzytkownika waluty.
//Informacje o kursie NBP złotego wobec tej waluty podanego kraju.
//Strona wiki z opisem miasta.
//W p. 1 użyć serwisu api.openweathermap.org, w p. 2 - serwisu open.er-api.com (dok: https://www.exchangerate-api.com/docs/free), w p. 3 - informacji ze stron NBP: https://nbp.pl/statystyka-i-sprawozdawczosc/kursy/tabela-a/ i analogicznie dla tabel B i C.
//W p. 4 użyć klasy WebEngine z JavaFX dla wbudowania przeglądarki w aplikację Swingową.
//
//Program winien zawierać klasę Service z konstruktorem Service(String kraj) i metodami::
//String getWeather(String miasto) - zwraca informację o pogodzie w podanym mieście danego kraju w formacie JSON (to ma być pełna informacja uzyskana z serwisu openweather - po prostu tekst w formacie JSON),
//Double getRateFor(String kod_waluty) - zwraca kurs waluty danego kraju wobec waluty podanej jako argument,
//Double getNBPRate() - zwraca kurs złotego wobec waluty danego kraju
public class Main {
  public static void main(String[] args) {
    Service s = new Service("Poland");
 //  String weatherJson = s.getWeather("Warsaw");
     // System.out.println("_______________________________________");
  // Double rate1 = s.getRateFor("USD");
     // System.out.println(rate1);
    Double rate2 = s.getNBPRate();
    // ...
    // część uruchamiająca GUI
  }
}
