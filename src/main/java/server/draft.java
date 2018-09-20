package server;

import com.google.gson.Gson;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;
import server.connection.service.Request;
import server.connection.service.RequestType;

public class draft {

    private static final RestTemplate restTemplate = new RestTemplate();
    private static final HttpHeaders httpHeaders = new HttpHeaders();


    private static final String uri = "http://165.227.239.245:8091/matchmaker/score";


    public static void main(String[] args) {
        String Str = new String("Добро пожаловать на ProgLang.su");

        System.out.print("Возвращаемое значение: ");
        System.out.println(Str.substring(5));

        System.out.print("Возвращаемое значение: ");
        System.out.println(Str.substring(0, 1));


    }
}
