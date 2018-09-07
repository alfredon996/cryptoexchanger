package core;
import com.google.gson.Gson;

public abstract class Profile {
    private String name;
    private String api_key;
    private String api_secret;
    private String pair;

    public Profile(String name, String api_key, String api_secret, String pair)
    {
        this.name = name;
        this.api_key = api_key;
        this.api_secret = api_secret;
        this.pair = pair;
    }

    public String getApi_key() {
        return api_key;
    }

    public String getApi_secret() {
        return api_secret;
    }

    public String getPair() {
        return pair;
    }

    public abstract String signature();

    public String timestamp()
    {
        Long time = System.currentTimeMillis() / 1000L;
        return time.toString();
    }
}
