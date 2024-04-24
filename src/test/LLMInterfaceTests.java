package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import bankapp.LLMInterface;

public class LLMInterfaceTests {
    
    @Test
    public void testSendQueryToLLM_InvalidEndpointURL() {
        String userInput = "Can you please let us know more details about your ";
        String context = "";
        LLMInterface.setEndpointUrl("www.invalid-url.com");
        assertThrows(IOException.class, () -> LLMInterface.sendQueryToLLM(userInput, context));
    }

    @Test
    public void testSendQueryToLLMMissingAcceptHeader() throws IOException {
        String userInput = "Can you please let us know more details about your ";
        String context = "";

        LLMInterface.setAcceptHeader("");
        assertThrows(IOException.class, () -> LLMInterface.sendQueryToLLM(userInput, context));
    }

    @Test
    public void testSendQueryToLLM_MissingContentTypeHeader() throws IOException {
        String userInput = "Can you please let us know more details about your ";
        String context = "";
        LLMInterface.setContentTypeHeader("");
        assertThrows(IOException.class, () -> LLMInterface.sendQueryToLLM(userInput, context));

    }

    private static void setEndpointUrl(String url) {
        try {
            URL endpointUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) endpointUrl.openConnection();
            LLMInterface.setUrl(endpointUrl);
        } catch (IOException e) {
            fail("Incorrect endpoint URL: " + e.getMessage());
        }
    }

    private static void setAcceptHeader(String header) {
        LLMInterface.setAcceptHeader(header);
    }

    private static void setContentTypeHeader(String header) {
        LLMInterface.setContentTypeHeader(header);
    }
}