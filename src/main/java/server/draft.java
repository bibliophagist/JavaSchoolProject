package server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import server.connection.service.Request;
import server.connection.service.RequestType;
import server.connection.service.Requests;
import server.connection.service.request.controller.ForeignBank;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class draft {
    private static final Gson gson = new GsonBuilder().create();
    private static final Logger LOGGER = LoggerFactory.getLogger(draft.class);
    private static Type listType = new TypeToken<List<Request>>() {
    }.getType();

    private static List<Request> requestList() {
        Request request = new Request(RequestType.LOGIN, "login1", "test", "password1", "test", 1);
        Request request1 = new Request(RequestType.LOGIN, "login1", "test", "password1", "test", 1);
        Request request2 = new Request(RequestType.LOGIN, "login1", "test", "password1", "test", 1);
        List<Request> target = new LinkedList<Request>();
        target.add(request);
        target.add(request1);
        target.add(request2);
        return target;
    }

    public static String toJson(List<Request> requestList) {
        Type listType = new TypeToken<List<Request>>() {
        }.getType();
        return gson.toJson(requestList, listType);
    }

    private static void readJsonFile() {
        try (Writer writer = new FileWriter("src/main/resources/requestsInJson.json")) {
            gson.toJson(requestList(), listType, writer);
        } catch (IOException ex) {
            LOGGER.debug("You can not write to a file src/main/resources/requestsInJson.json", ex);
        }
        try (JsonReader reader = new JsonReader(new FileReader("src/main/resources/requestsInJson.json"))) {
            List<Request> requests = gson.fromJson(reader, listType);
            System.out.println(requests);
            String json1 = gson.toJson(requests, listType);
            System.out.println(json1);
        } catch (IOException ex) {
            LOGGER.debug("Request with json file was received, but file wasn't open", ex);
        }
    }

    private static void readXmlFile() {
        Request request123 = new Request(RequestType.LOGIN, "login1", "password1");

        try {
            JAXBContext context = JAXBContext.newInstance(Request.class);
            Marshaller marshaller = context.createMarshaller();
            Unmarshaller unmarshaller = context.createUnmarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            StringWriter stringWriter = new StringWriter();
            marshaller.marshal(request123, stringWriter);
            System.out.println(stringWriter.toString());

            Requests requests = new Requests(requestList());
            marshaller.marshal(requests, System.out);
            marshaller.marshal(requests, new FileOutputStream("src/main/resources/requestsInXml.xml"));

            Requests requests1 = (Requests) unmarshaller.unmarshal(new FileInputStream("src/main/resources/requestsInXml.xml"));

            String json = gson.toJson(requests1.getRequests(), listType);
            System.out.println(json);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {

        Type type = new TypeToken<Map<String, String>>() {
        }.getType();

        ForeignBank foreignBank = new ForeignBank();

        Request request = new Request(RequestType.REMOVE_FUNDS, "1111111111", null, "1", "701",
                Long.decode("100"));

        ResponseEntity<String> responseEntity = foreignBank.beforeSendingRequest("20", "2011", request);

    }
}
