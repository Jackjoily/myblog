package code.jack.myblog.provider;

import code.jack.myblog.dto.Accessdto;
import code.jack.myblog.dto.GithubUser;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.support.springfox.SwaggerJsonSerializer;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GithubProvider {
    public String getAccesstoken(Accessdto dto) {
        MediaType mediaType
                = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(dto));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String str = response.body().string();
            String[] split = str.split("&");
            String tokenString = split[0];
            String[] split1 = tokenString.split("=");
            String token = split1[1];
            return token;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public GithubUser getUser(String accesstoken) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token=" + accesstoken)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String str = response.body().string();
            GithubUser githubUser = JSON.parseObject(str, GithubUser.class);
            return githubUser;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
