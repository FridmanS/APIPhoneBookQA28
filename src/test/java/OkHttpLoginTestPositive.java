import com.google.gson.Gson;
import dto.AuthRequestDto;
import dto.AuthResponseDto;
import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class OkHttpLoginTestPositive {

    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    @Test
    public void LoginTestPos() throws IOException {
        Gson gson = new Gson();
        OkHttpClient client = new OkHttpClient();

        AuthRequestDto requestDto = AuthRequestDto.builder()
                .email("asd@asd.com")
                .password("aA123456$")
                .build();

        RequestBody requestBody = RequestBody.create(gson.toJson(requestDto), JSON);

        Request request = new Request.Builder()
                .url("https://contacts-telran.herokuapp.com/api/login")
                .post(requestBody)
                .build();
        Response response = client.newCall(request).execute();
        Assert.assertTrue(response.isSuccessful());
        System.out.println(gson.fromJson(response.body().string(), AuthResponseDto.class).getToken());
    }
}
