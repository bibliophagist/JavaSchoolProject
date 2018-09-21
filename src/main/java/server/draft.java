package server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server.connection.service.Request;
import server.connection.service.RequestType;
import server.connection.service.Requests;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

public class draft {
    private static final Gson gson = new GsonBuilder().create();
    private static final Logger LOGGER = LoggerFactory.getLogger(draft.class);
    private static Type listType = new TypeToken<List<Request>>() {
    }.getType();

    private static List<Request> requestList() {
        Request request = new Request(RequestType.LOGIN, "login1", null, "password1");
        Request request1 = new Request(RequestType.LOGIN, "login1", null, "password1");
        Request request2 = new Request(RequestType.LOGIN, "login1", null, "password1");
        List<Request> target = new LinkedList<Request>();
        target.add(request);
        target.add(request1);
        target.add(request2);
        return target;
    }

    public static final String toJson() {
        String json = gson.toJson(requestList(), listType);
        return json;
    }

    public static void main(String[] args) {
        try {
            JsonReader reader = new JsonReader(new FileReader("src/main/java/resources/file.json"));
            Type listType = new TypeToken<List<Request>>() {
            }.getType();
            List<Request> requests = gson.fromJson(reader, listType);
            System.out.println(requests);
            String json = gson.toJson(requests, listType);
            System.out.println(json);
        } catch (FileNotFoundException ex) {
            LOGGER.debug("Request with json file was received, but file wasn't open", ex);
        }


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
            marshaller.marshal(requests, new FileOutputStream("src/main/java/resources/requestsInXml.xml"));

            Requests requests1 = (Requests) unmarshaller.unmarshal(new FileInputStream("src/main/java/resources/requestsInXml.xml"));

            String json = gson.toJson(requests1.getRequests(), listType);
            System.out.println(json);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
