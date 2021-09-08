import com.google.gson.Gson;
import dto.AuthRequestDto;
import dto.ErrorDto;
import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class OkHttpLoginTestNegative {
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    @Test
    public void LoginTestEmailInvalid() throws IOException {
        Gson gson = new Gson();
        OkHttpClient client = new OkHttpClient();

        AuthRequestDto requestDto = AuthRequestDto.builder()
                .email("asdasd.com")
                .password("aA123456$")
                .build();

        RequestBody requestBody = RequestBody.create(gson.toJson(requestDto), JSON);

        Request request = new Request.Builder()
                .url("https://contacts-telran.herokuapp.com/api/login")
                .post(requestBody)
                .build();
        Response response = client.newCall(request).execute();
        Assert.assertFalse(response.isSuccessful());
        Assert.assertEquals(response.code(), 400);
        ErrorDto errorDto = gson.fromJson(response.body().string(), ErrorDto.class);
        Assert.assertEquals(errorDto.getCode(), response.code());
        Assert.assertTrue(errorDto.getMessage().contains("Wrong email format!"));
    }
    @Test
    public void LoginTestPasswordInvalid() throws IOException {
        Gson gson = new Gson();
        OkHttpClient client = new OkHttpClient();

        AuthRequestDto requestDto = AuthRequestDto.builder()
                .email("asd@asd.com")
                .password("aA12$")
                .build();

        RequestBody requestBody = RequestBody.create(gson.toJson(requestDto), JSON);

        Request request = new Request.Builder()
                .url("https://contacts-telran.herokuapp.com/api/login")
                .post(requestBody)
                .build();
        Response response = client.newCall(request).execute();
        Assert.assertFalse(response.isSuccessful());
        Assert.assertEquals(response.code(), 400);
        ErrorDto errorDto = gson.fromJson(response.body().string(), ErrorDto.class);
        Assert.assertEquals(errorDto.getCode(), response.code());
        System.out.println(errorDto.getMessage());
    }
    @Test
    public void LoginTestWithoutRegistration() throws IOException {
        Gson gson = new Gson();
        OkHttpClient client = new OkHttpClient();

        AuthRequestDto requestDto = AuthRequestDto.builder()
                .email("aasdsd@asd.com")
                .password("aA13432512$")
                .build();

        RequestBody requestBody = RequestBody.create(gson.toJson(requestDto), JSON);

        Request request = new Request.Builder()
                .url("https://contacts-telran.herokuapp.com/api/login")
                .post(requestBody)
                .build();
        Response response = client.newCall(request).execute();
        Assert.assertFalse(response.isSuccessful());
        Assert.assertEquals(response.code(), 401);
        ErrorDto errorDto = gson.fromJson(response.body().string(), ErrorDto.class);
        Assert.assertEquals(errorDto.getCode(), response.code());
        Assert.assertEquals(errorDto.getMessage(), "Wrong email or password!");
        System.out.println(errorDto.getMessage());
    }
}
