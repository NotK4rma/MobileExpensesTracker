package tn.rnu.isi;

import android.util.Log;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GeminiClient {

    private static final String BASE_URL = "https://generativelanguage.googleapis.com/";
    private static Retrofit retrofit;
    private static GeminiApi api;
    //private static boolean firstprompt = true;

    private String apiKey = "AIzaSyC5qw2R9pV7gU6pFIF6md6oeRd8gHYVSMQ";

    public interface GeminiCallback {
        void onResult(String text);
        void onError(String error);
    }

    public GeminiClient() {
        if (api == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            api = retrofit.create(GeminiApi.class);
        }
    }

    public void generateResponse(String prompt, GeminiCallback callback) {
        prompt = "You are an expense tracker assistant based in Tunisia Try to assist the user with his following request, brief and professional responses befitting that of an assistant.Don't use special syntax or try to make text Bold italic or anything just UTF-8. Dont add Ai or anything in front of ur prompts.Answer in English. Don't Greet the user  " + prompt ;
        GeminiRequest body = new GeminiRequest(prompt);

        api.generateContent(apiKey, body).enqueue(new Callback<GeminiResponse>() {
            @Override
            public void onResponse(Call<GeminiResponse> call, Response<GeminiResponse> response) {

                if (!response.isSuccessful() || response.body() == null) {
                    callback.onError("API error");
                    return;
                }

                String text = response.body()
                        .candidates.get(0)
                        .content.parts.get(0)
                        .text;

                callback.onResult(text);
            }

            @Override
            public void onFailure(Call<GeminiResponse> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }
}
