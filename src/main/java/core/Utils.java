package core;

/**
 *  Custom class to generate HMAC MD5/SHA1/SHA256 and POST parameter strings from HashMaps
 *  (taken from https://gist.github.com/MaximeFrancoeur/bcb7fc2db08c704f322a)
 *
 * @author Alfredo Natale
 * @version 1.0
 */

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.net.URLEncoder;
import java.util.Map;

public class Utils {

    public static String hmacDigest(String msg, String keyString, String algo) {
        String digest = null;
        try {
            SecretKeySpec key = new SecretKeySpec((keyString).getBytes("UTF-8"), algo);
            Mac mac = Mac.getInstance(algo);
            mac.init(key);

            byte[] bytes = mac.doFinal(msg.getBytes("ASCII"));

            StringBuffer hash = new StringBuffer();
            for (int i = 0; i < bytes.length; i++) {
                String hex = Integer.toHexString(0xFF & bytes[i]);
                if (hex.length() == 1) {
                    hash.append('0');
                }
                hash.append(hex);
            }
            digest = hash.toString();
        } catch (UnsupportedEncodingException e) {
        } catch (InvalidKeyException e) {
        } catch (NoSuchAlgorithmException e) {
        }
        return digest;
    }

    public static String getRequest(String getUrl)
    {
        try {
            URL url = new URL(getUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json");
            int status = con.getResponseCode();
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            return content.toString();
        } catch (IOException e) {
            return null;
        }
    }

    public static String postRequest(String postUrl, String... pars) {
        try {
            URL url = new URL(postUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            con.setDoOutput(true);
            DataOutputStream out = new DataOutputStream(con.getOutputStream());
            out.writeBytes(concat(pars));
            out.flush();
            out.close();
            int status = con.getResponseCode();
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            return content.toString();
        } catch (IOException e) {
            return null;
        }
    }

    public static JsonObject getReq(String getUrl)
    {
        try {
            URL url = new URL(getUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json");
            int status = con.getResponseCode();
            InputStream inputStream = con.getInputStream();
            JsonParser jsonParser = new JsonParser();
            return (JsonObject)jsonParser.parse(
                    new InputStreamReader(inputStream, "UTF-8"));
        } catch (IOException e) {
            return null;
        }
    }

    public static JsonObject postReq(String postUrl, String... pars) {
        try {
            URL url = new URL(postUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            con.setDoOutput(true);
            DataOutputStream out = new DataOutputStream(con.getOutputStream());
            out.writeBytes(concat(pars));
            out.flush();
            out.close();
            int status = con.getResponseCode();
            InputStream inputStream = con.getInputStream();
            JsonParser jsonParser = new JsonParser();
            return (JsonObject)jsonParser.parse(
                    new InputStreamReader(inputStream, "UTF-8"));
        } catch (IOException e) {
            return null;
        }
    }


    /**
     * Concatenate strings of parameters
     * @param args The parameters
     * @return The string
     */
    private static String concat(String... args)
    {
        StringBuilder sb = new StringBuilder();
        int l = args.length-2;
        for (int i = 0; i < l; i+=2) {
            sb.append(args[i]);
            sb.append("=");
            sb.append(args[i+1]);
            sb.append("&");
        }
        sb.append(args[l]);
        sb.append("=");
        sb.append(args[l+1]);
        return sb.toString();
    }
}