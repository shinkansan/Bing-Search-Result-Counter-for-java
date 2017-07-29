/**
 * Created by shinkansan on 2017. 4. 6..
 */



import apple.laf.JRSUIConstants;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.io.*;
import java.util.List;
import java.util.Map;
import javax.net.ssl.HttpsURLConnection;



public class main {

    static String url = "https://api.cognitive.microsoft.com/bing/v5.0/search";
    static String Hits;


    static String SubCode;

    {
        SubCode = ("63ba6767052648e188ddbb08ed259320"); //your sub code
    }

    static String Auth_header;

    {
        Auth_header = ("Ocp-Apim-Subscription-Key");
    }

    static String Query;


    public static void main(String[] args) {
        new main().pre_condition("Mun Jae in");
        new main().pre_condition("Donald Trump");
    }
    public void pre_condition(String q) {
        q = q.replaceAll("\\s", "+"); //검색 쿼리 값의 빈칸을 +으로 대체하여 입력
        main.url = main.url + "?q=" + q;
        URL url;
        main.Query = q;

        try {
            int ResponseCode;
            String RespondMsg;
            url = new URL(main.url); // 변수 url 의 내용을 url 형식으로
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
            SubscriptionAuth(con);

            ResponseCode = con.getResponseCode();
            RespondMsg = con.getResponseMessage();


            if (ResponseCode != 200) // 서버 상태 확인
            {
                System.out.println("API Server is not ready on your request! | Code:" + ResponseCode + " | " + RespondMsg);
                System.out.println(main.url);
                print_mapping(con);
            } else {
                //  System.out.println("API Server is Ready | Code: 200 | " + RespondMsg);
                //  print_mapping(con);
                System.out.println(jsonparsing(con));

            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * Method Section
     **/


    private void SubscriptionAuth(HttpsURLConnection con) {
        con.setRequestProperty(main.Auth_header,main.SubCode);
        //  System.out.println(main.Auth_header + " | " + main.SubCode);
    }

    private String GetHits() {

        return main.Hits;


    }


    private void print_mapping(HttpsURLConnection con) {
        Map<String, List<String>> map = con.getHeaderFields();

        System.out.println("Printing Response Header...\n");

        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            System.out.println("Key : " + entry.getKey()
                    + " ,Value : " + entry.getValue());
        }

    }

    /*
        private void getJson(HttpsURLConnection con) {
            try {

                BufferedReader org = new BufferedReader(new.InputStreamReder(con.getInputStream()));
                StringBuilder sb = new StringBuilder();

                String line;
                while ((line=org.readLine() != null)) {
                    sb.append(line+"\n");
                }

                {

                }

            }catch(IOException e){e.printStackTrace();}
        }
        */
    private String getJSON(HttpsURLConnection con) {

        try

        {


            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            br.close();

            return sb.toString();

        } catch (MalformedURLException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public String jsonparsing(HttpsURLConnection con) {
        try {
            String data = getJSON(con);

            //  System.out.println("Original json from JsonParser : " + getJSON(con));
            // System.out.println("json to string val : " + data);
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(data);

            JSONObject jo = (JSONObject) obj;
            JSONObject roadWebpages = (JSONObject) jo.get("webPages"); //Root
            String totalCnt = roadWebpages.get("totalEstimatedMatches").toString(); //Element

        /*

        JSONArray arrElement = (JSONArray)roadRoot.get("element"); // Array Formed element
        for(int i=0; i<arrElement.size(); i++) {
            JSONObject j = (JSONObject)arrElement.get(i);
            String name = (String)j.get("name").toString(); // name Componet in Array formed
        */
            return totalCnt;
        } catch (Exception e) {
            e.printStackTrace();
        }
        String ErrDiag = "Error! The Value is Null";
        return ErrDiag;
    }




}

