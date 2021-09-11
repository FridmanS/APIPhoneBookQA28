import com.google.gson.Gson;
import dto.ContactDto;
import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class AddNewContactTest {

    static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    @Test
    public void addNewContactTest() throws IOException {
        Gson gson = new Gson();
        OkHttpClient client = new OkHttpClient();

        ContactDto contact = ContactDto.builder()
                .name("asdasd")
                .lastName("asdasd")
                .email("asdasd@asdasd.asd" + (System.currentTimeMillis()/1000) % 3600)
                .phone("1234567" + (System.currentTimeMillis()/1000) % 3600)
                .address("sdfaasdf")
                .description("asdasd")
                .build();
        RequestBody requestBody = RequestBody.create(gson.toJson(contact), JSON);

        Request request = new Request.Builder()
                .url("https://contacts-telran.herokuapp.com/api/contact")
                .post(requestBody)
                .addHeader("Authorization", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6Im1haWwzQC5jb20ifQ.8hMaLWf2Omhx43YmOwROYSU3b_pF4aSxtwcKirAOAbM")
                .build();

        Response response = client.newCall(request).execute();
        Assert.assertTrue(response.isSuccessful());
        System.out.println(gson.fromJson(response.body().string(), ContactDto.class).getId());
    }
}
