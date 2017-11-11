package com.example.eltur.parkinsonbp.HttpClient;

import android.util.Base64;

import com.example.eltur.parkinsonbp.Security.AuthEnc;
import com.example.eltur.parkinsonbp.Security.RSAUtils;
import com.example.eltur.parkinsonbp.ServerClass.Activity;
import com.example.eltur.parkinsonbp.ServerClass.Habit;
import com.example.eltur.parkinsonbp.ServerClass.Links;
import com.example.eltur.parkinsonbp.ServerClass.Medicine;
import com.example.eltur.parkinsonbp.ServerClass.MoodCondition;
import com.example.eltur.parkinsonbp.ServerClass.Patient;
import com.example.eltur.parkinsonbp.ServerClass.PatientRecord;
import com.example.eltur.parkinsonbp.ServerClass.SleepCondition;
import com.example.eltur.parkinsonbp.ServerClass.SleepDisorder;
import com.example.eltur.parkinsonbp.ServerClass.SleepQuality;
import com.example.eltur.parkinsonbp.ServerClass.SubMenu;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import static com.example.eltur.parkinsonbp.Utils.UtilsMethod.convertStreamToString;

/**
 * Created by liran on 5/30/17.
 */

public class HttpClient {

    private static HttpClient httpClient = new HttpClient();
    private URL url;
    private HttpURLConnection urlConnection;
    private static ObjectMapper mapper = new ObjectMapper();
    private ArrayList<String> arractivities = new ArrayList<>();
    private InputStream inputStream;
    private  OutputStream outputStream;
    public static HttpClient getClient(){return httpClient;};
    private static ArrayList<String> activitiesList = new ArrayList<>();
    private static ArrayList<SubMenu> subMenuList = new ArrayList<>();
  private String MyPublicKey = "";
    public static ArrayList<String> getSubMenuListSleepCondition() {
        return subMenuListSleepCondition;
    }

    public static void setSubMenuListSleepCondition(ArrayList<String> subMenuListSleepCondition) {
        HttpClient.subMenuListSleepCondition = subMenuListSleepCondition;
    }

    public static ArrayList<String> getMedicineSerialNumbers() {
        return MedicineSerialNumbers;
    }

    public static void setMedicineSerialNumbers(ArrayList<String> medicineSerialNumbers) {
        MedicineSerialNumbers = medicineSerialNumbers;
    }

    private static  ArrayList<String> MedicineSerialNumbers = new ArrayList<>();
    private static  ArrayList<String> subMenuListSleepCondition = new ArrayList<>();
    private static  ArrayList<String> LinksList = new ArrayList<>();
    public static List<List<SubMenu>> getSubMenuListActivities() {
        return subMenuListActivities;
    }

    public static void setSubMenuListActivities(List<List<SubMenu>> subMenuListActivities) {
        HttpClient.subMenuListActivities = subMenuListActivities;
    }

    private static List<List<SubMenu>> subMenuListActivities = new ArrayList<List<SubMenu>>();

    public static List<List<SubMenu>> getSubMenuListHabit() {
        return subMenuListHabit;
    }

    public static void setSubMenuListHabit(List<List<SubMenu>> subMenuListHabit) {
        HttpClient.subMenuListHabit = subMenuListHabit;
    }

    private static List<List<SubMenu>> subMenuListHabit = new ArrayList<List<SubMenu>>();

    public static ArrayList<Long> getSubMenuListHabitMenuGroupId() {
        return subMenuListHabitMenuGroupId;
    }

    public static void setSubMenuListHabitMenuGroupId(ArrayList<Long> subMenuListHabitMenuGroupId) {
        HttpClient.subMenuListHabitMenuGroupId = subMenuListHabitMenuGroupId;
    }

    private static ArrayList<Long> subMenuListHabitMenuGroupId = new ArrayList<Long>();
    private static ArrayList<String> HergelimsList = new ArrayList<>();
    private static ArrayList<String> MoodsList = new ArrayList<>();
    private static ArrayList<String> MedicineList = new ArrayList<>();
    private static ArrayList<String> SleepDisorderList = new ArrayList<>();

    public static ArrayList<String> getSleepConditionList() {
        return SleepConditionList;
    }

    public static void setSleepConditionList(ArrayList<String> sleepConditionList) {
        SleepConditionList = sleepConditionList;
    }

    private static ArrayList<String> SleepConditionList = new ArrayList<>();

    public static ArrayList<String> getMedicineList() {
        return MedicineList;
    }

    public static void setMedicineList(ArrayList<String> medicineList) {
        MedicineList = medicineList;
    }

    public static ArrayList<String> getSleepDisorderList() {
        return SleepDisorderList;
    }

    public static void setSleepDisorderList(ArrayList<String> sleepDisorderList) {
        SleepDisorderList = sleepDisorderList;
    }



    public static Activity[] getJson() {
        return json;
    }

    private static Activity[] json;

    public static Habit[] getJsonHabit() {
        return jsonHabit;
    }

    public static void setJsonHabit(Habit[] jsonHabit) {
        HttpClient.jsonHabit = jsonHabit;
    }

    private static Habit[] jsonHabit;

    public static SleepDisorder[] getJsonSleepDisorder() {
        return jsonSleepDisorder;
    }

    public static void setJsonSleepDisorder(SleepDisorder[] jsonSleepDisorder) {
        HttpClient.jsonSleepDisorder = jsonSleepDisorder;
    }

    private static SleepDisorder[] jsonSleepDisorder;

    public static SleepCondition[] getJsonSleepCondition() {
        return jsonSleepCondition;
    }

    public static void setJsonSleepCondition(SleepCondition[] jsonSleepCondition) {
        HttpClient.jsonSleepCondition = jsonSleepCondition;
    }

    private static SleepCondition[] jsonSleepCondition;

    public static SleepQuality[] getJsonSleepQualitySubMenu() {
        return jsonSleepQualitySubMenu;
    }

    public static void setJsonSleepQualitySubMenu(SleepQuality[] jsonSleepQualitySubMenu) {
        HttpClient.jsonSleepQualitySubMenu = jsonSleepQualitySubMenu;
    }

    private static SleepQuality[] jsonSleepQualitySubMenu;

    public static Links[] getJsonLinks() {
        return jsonLinks;
    }

    public static void setJsonLinks(Links[] jsonLinks) {
        HttpClient.jsonLinks = jsonLinks;
    }

    private static Links[] jsonLinks;
    public static MoodCondition[] getJsonMood() {
        return jsonMood;
    }

    public static void setJsonMood(MoodCondition[] jsonMood) {
        HttpClient.jsonMood = jsonMood;
    }

    private static MoodCondition[] jsonMood;

    public static Medicine[] getJsonMedicine() {
        return jsonMedicine;
    }

    public static void setJsonMedicine(Medicine[] jsonMedicine) {
        HttpClient.jsonMedicine = jsonMedicine;
    }

    private static Medicine[] jsonMedicine;






    public Collection<Activity> getCollection() {
        return collection;
    }

    private Collection<Activity> collection;

    public Boolean SendPatientRecordToServer(String i_URL, PatientRecord pr)throws MalformedURLException,JsonProcessingException {

        Gson gson = new Gson();
        String jsonInString = gson.toJson(pr);

       // String jsonInString = mapper.writeValueAsString(pr);
        url = new URL(i_URL);

        try{
            initiateURLConnection("PUT");

            //write the body
            outputStream = urlConnection.getOutputStream();
            outputStream.write(jsonInString.getBytes("UTF-8"));
            outputStream.close();

            //read the body response
            inputStream = new BufferedInputStream(urlConnection.getInputStream());
            String result = convertStreamToString(inputStream);
            System.out.print(result);
            inputStream.close();
            if(result.contains("success"))
                return true;
        }catch (ProtocolException prt){
          //  System.out.println(String.format("Error:%s",pr.getMessage()));
        }
        catch (IOException IO){

            System.out.println(String.format("Error:%s",IO.getMessage()));
        }
        return false;
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

    public ArrayList<String> GetAllActiviesFromServer(String i_URL)throws MalformedURLException {
       // json=null;
       // activitiesList =null;
       // activitiesList.clear();
        if(activitiesList.isEmpty()) {
            url = new URL(i_URL);
           // List<Activity> getActivitiesFromServer;
            String result2 = "";

            try {
                initiateURLConnection("GET");
                //read the body response
                inputStream = new BufferedInputStream(urlConnection.getInputStream());
                String result = convertStreamToString(inputStream);

                if (result.contains("success")) {
                    result2 = result.substring(54, result.length() - 2);
                    mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
                    json = mapper.readValue(result2.getBytes("UTF-8"),Activity[].class);

                    for (int i = 0; i < json.length; i++) {
                        if (!json[i].getActivityName().isEmpty()) {
                            activitiesList.add(json[i].getActivityName());
                            subMenuListActivities.add(json[i].getSubMenus());
                           // subMenuList.add(json[i].getSubMenus().get(i).toString());
                        }

                    }
                    //  System.out.println(activitiesList);
                    inputStream.close();
                }
            } catch (ProtocolException pr) {
                System.out.println(String.format("Error:%s", pr.getMessage()));
            } catch (IOException IO) {
                System.out.println(String.format("Error:%s", IO.getMessage()));
            }
        }
            return activitiesList;

    }



    public ArrayList<String> GetAllHergelimFromServer(String i_URL)throws MalformedURLException {

       // HergelimsList.clear();
        if(HergelimsList.isEmpty()) {
            url = new URL(i_URL);


            try {
                initiateURLConnection("GET");
                //read the body response
                inputStream = new BufferedInputStream(urlConnection.getInputStream());
                String result = convertStreamToString(inputStream);

                if (result.contains("success")) {
                    String result2 = result.substring(46, result.length() - 2);
                    mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
                    jsonHabit = mapper.readValue(result2.getBytes("UTF-8"), Habit[].class);

                    for (int i = 0; i < jsonHabit.length; i++) {
                        if (!jsonHabit[i].getHabitName().isEmpty()) {
                            HergelimsList.add(jsonHabit[i].getHabitName());
                            subMenuListHabit.add(jsonHabit[i].getSubMenus());
                            subMenuListHabitMenuGroupId.add(jsonHabit[i].getGroupID());

                        }

                    }
                  //  System.out.println(HergelimsList);
                    inputStream.close();
                }
            } catch (ProtocolException pr) {
                System.out.println(String.format("Error:%s", pr.getMessage()));
            } catch (IOException IO) {
                System.out.println(String.format("Error:%s", IO.getMessage()));
            }
        }
        return HergelimsList;

    }



    public ArrayList<String> GetAllMoodsFromServer(String i_URL)throws MalformedURLException {

       // MoodsList.clear();
        if(MoodsList.isEmpty()) {
        url = new URL(i_URL);


        try {
            initiateURLConnection("GET");
            //read the body response
            inputStream = new BufferedInputStream(urlConnection.getInputStream());
            String result = convertStreamToString(inputStream);

            if (result.contains("success")) {
                String result2 = result.substring(62, result.length() - 2);
                mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
                jsonMood = mapper.readValue(result2.getBytes("UTF-8"), MoodCondition[].class);

                for (int i = 0; i < jsonMood.length; i++) {
                    if (!jsonMood[i].getMoodConditionName().isEmpty()) {
                        MoodsList.add(jsonMood[i].getMoodConditionName());
                    }

                }
                System.out.println(MoodsList);
                inputStream.close();
            }
        } catch (ProtocolException pr) {
            System.out.println(String.format("Error:%s", pr.getMessage()));
        } catch (IOException IO) {
            System.out.println(String.format("Error:%s", IO.getMessage()));
        }
    }
        return MoodsList;

    }

    public ArrayList<String> GetAllMedicineFromServer(String i_URL)throws MalformedURLException {

        //MedicineList.clear();
        if(MedicineList.isEmpty()) {
            url = new URL(i_URL);

            try {
                initiateURLConnection("GET");
                //read the body response
                inputStream = new BufferedInputStream(urlConnection.getInputStream());
                String result = convertStreamToString(inputStream);

                if (result.contains("success")) {
                    String result2 = result.substring(52, result.length() - 2);
                    mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
                    jsonMedicine = mapper.readValue(result2.getBytes("UTF-8"), Medicine[].class);

                    for (int i = 0; i < jsonMedicine.length; i++) {
                        if (!jsonMedicine[i].getMedicineName().isEmpty()) {
                            MedicineList.add(jsonMedicine[i].getMedicineName());
                            MedicineSerialNumbers.add(jsonMedicine[i].getMedicineSerialNumber());


                        }

                    }
                   // System.out.println(MedicineList);
                    inputStream.close();
                }
            } catch (ProtocolException pr) {
                System.out.println(String.format("Error:%s", pr.getMessage()));
            } catch (IOException IO) {
                System.out.println(String.format("Error:%s", IO.getMessage()));
            }
        }

        return MedicineList;

    }
    public ArrayList<String> GetAllSleepDisorderFromServer(String i_URL)throws MalformedURLException {

       // SleepDisorderList.clear();
        if(SleepDisorderList.isEmpty()) {
            url = new URL(i_URL);


            try {
                initiateURLConnection("GET");
                //read the body response
                inputStream = new BufferedInputStream(urlConnection.getInputStream());
                String result = convertStreamToString(inputStream);

                if (result.contains("success")) {
                    String result2 = result.substring(62, result.length() - 2);
                    mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
                    jsonSleepDisorder = mapper.readValue(result2.getBytes("UTF-8"), SleepDisorder[].class);

                    for (int i = 0; i < jsonSleepDisorder.length; i++) {
                        if (!jsonSleepDisorder[i].getSleepDisorderName().isEmpty()) {
                            SleepDisorderList.add(jsonSleepDisorder[i].getSleepDisorderName());

                        }

                    }
                    System.out.println(SleepDisorderList);
                    inputStream.close();
                }
            } catch (ProtocolException pr) {
                System.out.println(String.format("Error:%s", pr.getMessage()));
            } catch (IOException IO) {
                System.out.println(String.format("Error:%s", IO.getMessage()));
            }
        }
        return SleepDisorderList;

    }

    //Sleep Quality Sub Menu
    public ArrayList<String> GetAllSleepQualitySubMenu(String i_URL)throws MalformedURLException {

            url = new URL(i_URL);
            try {
                initiateURLConnection("GET");
                //read the body response
                inputStream = new BufferedInputStream(urlConnection.getInputStream());
                String result = convertStreamToString(inputStream);

                if (result.contains("success")) {
                    String result2 = result.substring(58, result.length() - 2);
                     mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
                    jsonSleepQualitySubMenu = mapper.readValue(result2.getBytes("UTF-8"), SleepQuality[].class);


                    for (int i = 0; i < jsonSleepQualitySubMenu.length; i++) {
                        if (!jsonSleepQualitySubMenu[i].getSleepQualityName().isEmpty()) {
                            subMenuListSleepCondition.add(jsonSleepQualitySubMenu[i].getSleepQualityName());
                        }

                    }
                   // System.out.println(MoodsList);
                    inputStream.close();
                }
            } catch (ProtocolException pr) {
                System.out.println(String.format("Error:%s", pr.getMessage()));
            } catch (IOException IO) {
               System.out.println(String.format("Error:%s", IO.getMessage()));
            }

        return subMenuListSleepCondition;
    }

    public ArrayList<String> GetAllLinks(String i_URL)throws MalformedURLException {

        url = new URL(i_URL);
        try {
            initiateURLConnection("GET");
            //read the body response
            inputStream = new BufferedInputStream(urlConnection.getInputStream());
            String result = convertStreamToString(inputStream);

            if (result.contains("success")) {
                String result2 = result.substring(44, result.length() - 2);
                mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
                jsonLinks = mapper.readValue(result2.getBytes("UTF-8"), Links[].class);


                for (int i = 0; i < jsonLinks.length; i++) {
                    if (!jsonLinks[i].getLinkURL().isEmpty()) {
                        LinksList.add(jsonLinks[i].getLinkURL());
                    }

                }
                // System.out.println(MoodsList);
                inputStream.close();
            }
        } catch (ProtocolException pr) {
            System.out.println(String.format("Error:%s", pr.getMessage()));
        } catch (IOException IO) {
            System.out.println(String.format("Error:%s", IO.getMessage()));
        }

        return LinksList;
    }



    private void initiateURLConnection(String httpMethod)throws IOException,ProtocolException {
        urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setConnectTimeout(100000);
        urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        if (!httpMethod.equals("GET")) {
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setRequestMethod(httpMethod);
        }

    }




    public List<String> GetUserName(String My_URL) throws MalformedURLException {
        List<String> list = null;
        url = new URL(My_URL);
        String UserName ="";
        ArrayList<String> ConvertResult= new ArrayList<>();
        try {
            initiateURLConnection("GET");
            //read the body response
            inputStream = new BufferedInputStream(urlConnection.getInputStream());
            String result = convertStreamToString(inputStream);
           // String result2 = result.substring(169);
            //mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
           // ArrayList<String> stringResultIntoArray = new ArrayList<>();
            //stringResultIntoArray = mapper.readValue(result.getBytes("UTF-8"),);
         list = new ArrayList<String>(Arrays.asList(result.split("}")));
           // ConvertResult.add(result);
          //  if(list.contains("success")) {
             //   for (int i = 0; i < list.size(); i++) {
                //    if (list.get(i).contains("FirstName")) {
                    //    UserName = list.get(i).toString();
                    //    inputStream.close();
                   //  inputStream.close();
                      //  return list;
          //  }

                inputStream.close();


        } catch (ProtocolException pr) {
            System.out.println(String.format("Error:%s", pr.getMessage()));
        } catch (IOException IO) {
            System.out.println(String.format("Error:%s", IO.getMessage()));
        }

        return list;

    }
}