package com.agh.givealift;

import java.util.Arrays;
import java.util.List;

public class Configuration {
    public static final Integer SEARCH_BEFORE_SEC = 0;
    public static final Integer SEARCH_AFTER_SEC = 24 * 60 * 60;
    public static final Integer SEARCH_INTERCHANGE_AFTER_SEC = 2 * 60 * 60;
    public static final long HOURS_DIFFERENCE = 0;
    public static final String BOT_NOTIFY_URL = "https://givealift-bot.herokuapp.com/notifyBot";
    //    public static final String BOT_NOTIFY_URL = "http://localhost:8080/api/route/testBot";
    public static final String WEB_NOTIFY_URL = "https://fcm.googleapis.com/fcm/send";
//    public static final String WEB_NOTIFY_URL = "https://mysterious-lowlands-82501.herokuapp.com/api/route/testWeb";
    public static final String MOBILE_NOTIFY_URL = "https://fcm.googleapis.com/fcm/send";
//    public static final String MOBILE_NOTIFY_URL = "https://mysterious-lowlands-82501.herokuapp.com/api/route/testMobile";

//    public static final int BOT_NOTIFY_TIMEOUT_SEC = 5;

    public static final String DATE_SEARCH_PATTERN = "yyyy-MM-dd";
    public static final String DATA_PATTERN = "yyyy-MM-dd HH:mm";

    public static final List<List<Object>> TMP_CITIES_LIST = Arrays.asList(
            Arrays.asList("Warszawa", "powiat Warszawa", "mazowieckie", (long) 1724404, (long) 517),
            Arrays.asList("Kraków", "powiat Kraków", "małopolskie", (long) 758992, (long) 327),
            Arrays.asList("Łódź", "powiat Łódź", "łódzkie", (long) 711332, (long) 293),
            Arrays.asList("Wrocław", "powiat Wrocław", "dolnośląskie", (long) 632067, (long) 293),
            Arrays.asList("Poznań", "powiat Poznań", "wielkopolskie", (long) 548028, (long) 262),
            Arrays.asList("Gdańsk", "powiat Gdańsk", "pomorskie", (long) 461531, (long) 262),
            Arrays.asList("Szczecin", "powiat Szczecin", "zachodniopomorskie", (long) 408172, (long) 301),
            Arrays.asList("Bydgoszcz", "powiat Bydgoszcz", "kujawsko-pomorskie", (long) 359428, (long) 176),
            Arrays.asList("Lublin", "powiat Lublin", "lubelskie", (long) 343598, (long) 147),
            Arrays.asList("Katowice", "powiat Katowice", "śląskie", (long) 304362, (long) 165),
            Arrays.asList("Białystok", "powiat Białystok", "podlaskie", (long) 295282, (long) 102),
            Arrays.asList("Gdynia", "powiat Gdynia", "pomorskie", (long) 248042, (long) 135),
            Arrays.asList("Częstochowa", "powiat Częstochowa", "śląskie", (long) 232318, (long) 160),
            Arrays.asList("Radom", "powiat Radom", "mazowieckie", (long) 218466, (long) 112),
            Arrays.asList("Sosnowiec", "powiat Sosnowiec", "śląskie", (long) 211275, (long) 91),
            Arrays.asList("Toruń", "powiat Toruń", "kujawsko-pomorskie", (long) 203447, (long) 116),
            Arrays.asList("Kielce", "powiat Kielce", "świętokrzyskie", (long) 199870, (long) 110),
            Arrays.asList("Gliwice", "powiat Gliwice", "śląskie", (long) 185450, (long) 134),
            Arrays.asList("Rzeszów", "powiat Rzeszów", "podkarpackie", (long) 183108, (long) 117),
            Arrays.asList("Zabrze", "powiat Zabrze", "śląskie", (long) 178357, (long) 80),
            Arrays.asList("Olsztyn", "powiat Olsztyn", "warmińsko-mazurskie", (long) 174675, (long) 88),
            Arrays.asList("Bielsko-Biała", "powiat Bielsko-Biała", "śląskie", (long) 173699, (long) 125),
            Arrays.asList("Bytom", "powiat Bytom", "śląskie", (long) 173439, (long) 69),
            Arrays.asList("Ruda Śląska", "powiat Ruda Śląska", "śląskie", (long) 141521, (long) 78),
            Arrays.asList("Rybnik", "powiat Rybnik", "śląskie", (long) 140173, (long) 148),
            Arrays.asList("Tychy", "powiat Tychy", "śląskie", (long) 128799, (long) 82),
            Arrays.asList("Gorzów Wielkopolski", "powiat Gorzów Wielkopolski", "lubuskie", (long) 124344, (long) 86),
            Arrays.asList("Dąbrowa Górnicza", "powiat Dąbrowa Górnicza", "śląskie", (long) 123994, (long) 189),
            Arrays.asList("Elbląg", "powiat Elbląg", "warmińsko-mazurskie", (long) 122899, (long) 80),
            Arrays.asList("Płock", "powiat Płock", "mazowieckie", (long) 122815, (long) 88),
            Arrays.asList("Opole", "powiat Opole", "opolskie", (long) 120146, (long) 97),
            Arrays.asList("Zielona Góra", "powiat Zielona Góra", "lubuskie", (long) 118405, (long) 58),
            Arrays.asList("Wałbrzych", "powiat Wałbrzych", "dolnośląskie", (long) 117926, (long) 85),
            Arrays.asList("Włocławek", "powiat Włocławek", "kujawsko-pomorskie", (long) 114885, (long) 84),
            Arrays.asList("Tarnów", "powiat Tarnów", "małopolskie", (long) 112120, (long) 72),
            Arrays.asList("Chorzów", "powiat Chorzów", "śląskie", (long) 110761, (long) 33),
            Arrays.asList("Koszalin", "powiat Koszalin", "zachodniopomorskie", (long) 109170, (long) 98),
            Arrays.asList("Kalisz", "powiat Kalisz", "wielkopolskie", (long) 103997, (long) 69),
            Arrays.asList("Legnica", "powiat Legnica", "dolnośląskie", (long) 101992, (long) 56),
            Arrays.asList("Grudziądz", "powiat Grudziądz", "kujawsko-pomorskie", (long) 97676, (long) 58),
            Arrays.asList("Słupsk", "powiat Słupsk", "pomorskie", (long) 93936, (long) 43),
            Arrays.asList("Jaworzno", "powiat Jaworzno", "śląskie", (long) 93708, (long) 153),
            Arrays.asList("Jastrzębie-Zdrój", "powiat Jastrzębie-Zdrój", "śląskie", (long) 91235, (long) 85),
            Arrays.asList("Nowy Sącz", "powiat Nowy Sącz", "małopolskie", (long) 83943, (long) 58),
            Arrays.asList("Jelenia Góra", "powiat Jelenia Góra", "dolnośląskie", (long) 81985, (long) 109),
            Arrays.asList("Konin", "powiat Konin", "wielkopolskie", (long) 77224, (long) 82),
            Arrays.asList("Siedlce", "powiat Siedlce", "mazowieckie", (long) 76347, (long) 32),
            Arrays.asList("Piotrków Trybunalski", "powiat Piotrków Trybunalski", "łódzkie", (long) 75903, (long) 67),
            Arrays.asList("Mysłowice", "powiat Mysłowice", "śląskie", (long) 75129, (long) 66),
            Arrays.asList("Inowrocław", "powiat inowrocławski", "kujawsko-pomorskie", (long) 75001, (long) 30),
            Arrays.asList("Piła", "powiat pilski", "wielkopolskie", (long) 74609, (long) 103),
            Arrays.asList("Lubin", "powiat lubiński", "dolnośląskie", (long) 74053, (long) 41),
            Arrays.asList("Ostrów Wielkopolski", "powiat ostrowski", "wielkopolskie", (long) 72890, (long) 42),
            Arrays.asList("Ostrowiec Świętokrzyski", "powiat ostrowiecki", "świętokrzyskie", (long) 72277, (long) 46),
            Arrays.asList("Gniezno", "powiat gnieźnieński", "wielkopolskie", (long) 69883, (long) 41),
            Arrays.asList("Stargard", "powiat stargardzki", "zachodniopomorskie", (long) 69328, (long) 48),
            Arrays.asList("Suwałki", "powiat Suwałki", "podlaskie", (long) 69317, (long) 66),
            Arrays.asList("Głogów", "powiat głogowski", "dolnośląskie", (long) 68997, (long) 35),
            Arrays.asList("Siemianowice Śląskie", "powiat Siemianowice Śląskie", "śląskie", (long) 68844, (long) 25),
            Arrays.asList("Pabianice", "powiat pabianicki", "łódzkie", (long) 67688, (long) 33),
            Arrays.asList("Chełm", "powiat Chełm", "lubelskie", (long) 65481, (long) 35),
            Arrays.asList("Zamość", "powiat Zamość", "lubelskie", (long) 65255, (long) 30),
            Arrays.asList("Tomaszów Mazowiecki", "powiat tomaszowski", "łódzkie", (long) 64893, (long) 42),
            Arrays.asList("Leszno", "powiat Leszno", "wielkopolskie", (long) 64589, (long) 32),
            Arrays.asList("Stalowa Wola", "powiat stalowowolski", "podkarpackie", (long) 63692, (long) 83),
            Arrays.asList("Przemyśl", "powiat Przemyśl", "podkarpackie", (long) 63638, (long) 46),
            Arrays.asList("Kędzierzyn-Koźle", "powiat kędzierzyńsko-kozielski", "opolskie", (long) 63194, (long) 124),
            Arrays.asList("Łomża", "powiat Łomża", "podlaskie", (long) 62711, (long) 33),
            Arrays.asList("Żory", "powiat Żory", "śląskie", (long) 62038, (long) 65),
            Arrays.asList("Mielec", "powiat mielecki", "podkarpackie", (long) 61096, (long) 47),
            Arrays.asList("Tarnowskie Góry", "powiat tarnogórski", "śląskie", (long) 60957, (long) 84),
            Arrays.asList("Tczew", "powiat tczewski", "pomorskie", (long) 60610, (long) 22),
            Arrays.asList("Ełk", "powiat ełcki", "warmińsko-mazurskie", (long) 59790, (long) 21),
            Arrays.asList("Pruszków", "powiat pruszkowski", "mazowieckie", (long) 59570, (long) 19),
            Arrays.asList("Bełchatów", "powiat bełchatowski", "łódzkie", (long) 59565, (long) 35),
            Arrays.asList("Świdnica", "powiat świdnicki", "dolnośląskie", (long) 59182, (long) 22),
            Arrays.asList("Będzin", "powiat będziński", "śląskie", (long) 58425, (long) 37),
            Arrays.asList("Biała Podlaska", "powiat Biała Podlaska", "lubelskie", (long) 57658, (long) 49),
            Arrays.asList("Zgierz", "powiat zgierski", "łódzkie", (long) 57503, (long) 42),
            Arrays.asList("Piekary Śląskie", "powiat Piekary Śląskie", "śląskie", (long) 57148, (long) 40),
            Arrays.asList("Racibórz", "powiat raciborski", "śląskie", (long) 55930, (long) 75),
            Arrays.asList("Legionowo", "powiat legionowski", "mazowieckie", (long) 54231, (long) 14),
            Arrays.asList("Ostrołęka", "powiat Ostrołęka", "mazowieckie", (long) 52917, (long) 29),
            Arrays.asList("Świętochłowice", "powiat Świętochłowice", "śląskie", (long) 51824, (long) 13),
            Arrays.asList("Zawiercie", "powiat zawierciański", "śląskie", (long) 51258, (long) 85),
            Arrays.asList("Starachowice", "powiat starachowicki", "świętokrzyskie", (long) 51158, (long) 32),
            Arrays.asList("Wejherowo", "powiat wejherowski", "pomorskie", (long) 50340, (long) 27),
            Arrays.asList("Puławy", "powiat puławski", "lubelskie", (long) 49100, (long) 50),
            Arrays.asList("Wodzisław Śląski", "powiat wodzisławski", "śląskie", (long) 48731, (long) 50),
            Arrays.asList("Skierniewice", "powiat Skierniewice", "łódzkie", (long) 48634, (long) 35),
            Arrays.asList("Starogard Gdański", "powiat starogardzki", "pomorskie", (long) 48621, (long) 25),
            Arrays.asList("Tarnobrzeg", "powiat Tarnobrzeg", "podkarpackie", (long) 48217, (long) 85),
            Arrays.asList("Radomsko", "powiat radomszczański", "łódzkie", (long) 47643, (long) 51),
            Arrays.asList("Skarżysko-Kamienna", "powiat skarżyski", "świętokrzyskie", (long) 47538, (long) 64),
            Arrays.asList("Rumia", "powiat wejherowski", "pomorskie", (long) 47374, (long) 30),
            Arrays.asList("Krosno", "powiat Krosno", "podkarpackie", (long) 47223, (long) 44),
            Arrays.asList("Kołobrzeg", "powiat kołobrzeski", "zachodniopomorskie", (long) 46897, (long) 26),
            Arrays.asList("Dębica", "powiat dębicki", "podkarpackie", (long) 46854, (long) 34),
            Arrays.asList("Kutno", "powiat kutnowski", "łódzkie", (long) 45657, (long) 34),
            Arrays.asList("Otwock", "powiat otwocki", "mazowieckie", (long) 45044, (long) 47)
    );
}
