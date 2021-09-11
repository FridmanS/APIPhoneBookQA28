import com.google.gson.Gson;
import dto.ContactDto;
import dto.GetAllContactsDto;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class OkHttpGetAllContactsTest {

    @Test
    public void testGetAllContacts() throws IOException {
        Gson gson = new Gson();
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://contacts-telran.herokuapp.com/api/contact")
                .addHeader("Authorization", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6Im1haWwzQC5jb20ifQ.8hMaLWf2Omhx43YmOwROYSU3b_pF4aSxtwcKirAOAbM")
                .build();

        Response response = client.newCall(request).execute();
        Assert.assertTrue(response.isSuccessful());

        GetAllContactsDto contactsList = gson.fromJson(response.body().string(), GetAllContactsDto.class);
        for(ContactDto contact : contactsList.getContacts()){
            System.out.println(contact.getId() + " " + contact.getEmail());
            System.out.println("===========================================");
        }
    }
}
