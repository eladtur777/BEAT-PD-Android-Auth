package com.example.eltur.parkinsonbp.Utils;

import android.os.AsyncTask;
import android.util.Base64;

import com.example.eltur.parkinsonbp.HttpClient.HttpClient;
import com.example.eltur.parkinsonbp.Security.AuthEnc;
import com.example.eltur.parkinsonbp.Security.RSAUtils;
import com.example.eltur.parkinsonbp.ServerClass.Patient;
import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import static com.example.eltur.parkinsonbp.Utils.UtilsMethod.convertStreamToString;

/**
 * Created by elad on 11/11/2017.
 */
public class AsyncLoginAuth extends AsyncTask<Object, Object, String> {
    String MyPublicKey= "";
    private HttpURLConnection urlConnection;
   // private Exception exception;
    private URL url;
    private Exception exception;
    private InputStream inputStream;
    private OutputStream outputStream;


    protected String doInBackground(Object... urls) {

        try {
            url = new URL(urls[0].toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

            String result2 = "";

            try {
                initiateURLConnection("GET");
                //read the body response
                inputStream = new BufferedInputStream(urlConnection.getInputStream());
                String result = convertStreamToString(inputStream);

                if (result.contains("publicKey")) {
                    result2 = result.substring(13, result.length() - 2);
                    inputStream.close();
                }
                //HttpClient htt = new HttpClient();
                return InitialPublicKey(result);

            }
            catch (ProtocolException pr) {
               // System.out.println(String.format("Error:%s", pr.getMessage()));
                return null;
            } catch (IOException IO) {
                return null;
              //  System.out.println(String.format("Error:%s", IO.getMessage()));
            }

    }

    protected void onPostExecute(String feed) {
        // TODO: check this.exception
        // TODO: do something with the feed
    }

    private void initiateURLConnection(String httpMethod)throws IOException,ProtocolException {
        urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setConnectTimeout(5000);
        urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        if (!httpMethod.equals("GET")) {
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setRequestMethod(httpMethod);
        }

    }

    public String InitialPublicKey(String PublicKeyFromServer) throws IOException {

        Patient patient = new Patient("125","12345");

        if(PublicKeyFromServer != ""){
            //  inputStream = new BufferedInputStream(urlConnection.getInputStream());
            // String result = convertStreamToString(inputStream);
            ;  initiateURLConnection("GET");
            Gson gson = new Gson();
            HashMap serverKey = gson.fromJson(PublicKeyFromServer, HashMap.class);
            String serverPubKey = (String)serverKey.get("publicKey");
            if (serverPubKey != null && !serverPubKey.equals("")) {
                try {
                    String encryptMsg = RSAUtils.encryptAsString(gson.toJson(patient), serverPubKey);
                    int flag = 0;
                    KeyPair clientKeyPair = RSAUtils.generateKeyPair();
                    AuthEnc authEnc = new AuthEnc(encryptMsg, Base64.encodeToString(clientKeyPair.getPublic().getEncoded(),flag));


                    String sessionID = urlConnection.getHeaderField("Set-Cookie").split("=|;")[1];
                    inputStream.close();
                    urlConnection.disconnect();
                    url = new URL("http://localhost:8080/BEAT-PD/Patient/Login");
                    initiateURLConnection("POST");
                    urlConnection.setRequestProperty("Cookie", "JSESSIONID=" + sessionID);

                    //write the body
                    outputStream = urlConnection.getOutputStream();
                    outputStream.write(gson.toJson(authEnc).getBytes("UTF-8"));
                    outputStream.close();


                    if (urlConnection.getResponseCode() == 200) {
                        inputStream = new BufferedInputStream(urlConnection.getInputStream());
                        String response = (String) gson.fromJson(convertStreamToString(inputStream), HashMap.class).get("success");
                        // return (RSAUtils.decrypt(response, clientKeyPair.getPrivate()).equals("OK"));
                        return "success";
                    }
                }catch (NoSuchPaddingException |NoSuchAlgorithmException |InvalidKeyException |
                        IllegalBlockSizeException | BadPaddingException |InvalidKeySpecException e) {
                    e.printStackTrace();
                }
                return "faild";
            }
        }
        return "success";


    }
}