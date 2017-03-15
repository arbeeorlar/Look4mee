package folaoyewole.look4mee.Utilities;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by sp_developer on 11/2/16.
 */
public class Connection_Service {
    private static final String Tag = "connection_To";
    String response;
    private static String BASE = "http://62.173.41.6:3390/lookforme/Service/Mobile.ashx?";
    //private static String BASE = "http://10.10.10.20:3390/lookforme/Service/Mobile.ashx?";

    public String getResponsefromUrl(String url_string) throws IOException {

        InputStream is = null;
        // Only display the first 3000 characters of the retrieved
        // web page content.
        int len = 300000000;
        StringBuilder sb = new StringBuilder();

        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();




        try {

            URL url = new URL(BASE+url_string);
            //Log.d("json_url",sb.toString());
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(6000); /* milliseconds */
            InputStreamReader in = new InputStreamReader(conn.getInputStream());

            // load the results into a string builder
            int read;
            char[] buff = new char[1024];
            while( (read = in.read(buff)) != -1){
                jsonResults.append(buff,0,read);

            }

            response = jsonResults.toString();
            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            // Toast.makeText(mContext,"No Internet Connection Found",Toast.LENGTH_LONG).show();
            response = "Connection Lost, Try Again";



        } finally {
            if (is != null) {
                is.close();
            }
        }

        return response;
    }

    // Reads an InputStream and converts it to a String.
    private String readIt(InputStream stream, int len) throws IOException {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[len];
        reader.read(buffer);
        return new String(buffer);
    }

    public JSONObject getJSONOBJfromURL(String url_string) throws IOException {

        InputStream is = null;
        String contentAsString = "";
        JSONObject jsonObject = null;
        // Only display the first 3000 characters of the retrieved
        // web page content.
        int len = 300000000;
        StringBuilder sb = new StringBuilder();

        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();


        try {

            URL url = new URL(BASE+url_string);
            //Log.d("json_url",sb.toString());
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(6000); /* milliseconds */
            InputStreamReader in = new InputStreamReader(conn.getInputStream());

            // load the results into a string builder
            int read;
            char[] buff = new char[2024];
            while( (read = in.read(buff)) != -1){
                jsonResults.append(buff,0,read);

            }

            // return contentAsString;

            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            // Toast.makeText(mContext,"No Internet Connection Found",Toast.LENGTH_LONG).show();
        } finally {
            if (is != null) {
                is.close();
            }
        }

        try {

            // create a JSON array hierarchy from the result

            jsonObject = new JSONObject(jsonResults.toString());

            Log.d(Tag,"my json object = "+jsonObject.toString());


        } catch (Exception e) {
            e.printStackTrace();
            Log.e(Tag, "Cannot process JSON results", e);
        }

        return jsonObject;
    }



    public JSONArray getJSONfromUrl(String url_string) throws IOException {

        InputStream is = null;
        String contentAsString = "";
        JSONArray jsonArray = null;
        // Only display the first 3000 characters of the retrieved
        // web page content.
        int len = 300000000;
        StringBuilder sb = new StringBuilder();



        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();


        try {

            URL url = new URL(BASE+url_string);
            //Log.d("json_url",sb.toString());
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(6000); /* milliseconds */
            InputStreamReader in = new InputStreamReader(conn.getInputStream());

            // load the results into a string builder
            int read;
            char[] buff = new char[2024];
            while( (read = in.read(buff)) != -1){
                jsonResults.append(buff,0,read);

            }

            // return contentAsString;

            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            // Toast.makeText(mContext,"No Internet Connection Found",Toast.LENGTH_LONG).show();
        } finally {
            if (is != null) {
                is.close();
            }
        }

        try {

            // create a JSON array hierarchy from the result

            jsonArray = new JSONArray(jsonResults.toString());


            Log.d(Tag,"my json array = "+jsonArray.toString());


        } catch (Exception e) {
            e.printStackTrace();
            Log.e(Tag, "Cannot process JSON results", e);
        }

        return jsonArray;
    }

    public String getJSONArrayString(String url_string) throws IOException{

        InputStream is = null;
        String contentAsString = "";
        JSONArray jsonArray = null;
        // Only display the first 3000 characters of the retrieved
        // web page content.
        int len = 300000000;
        StringBuilder sb = new StringBuilder();



        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();


        try {

            URL url = new URL(BASE+url_string);
            //Log.d("json_url",sb.toString());
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(6000); /* milliseconds */
            InputStreamReader in = new InputStreamReader(conn.getInputStream());

            // load the results into a string builder
            int read;
            char[] buff = new char[2024];
            while( (read = in.read(buff)) != -1){
                jsonResults.append(buff,0,read);

            }

             contentAsString = jsonResults.toString();

            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } catch (MalformedURLException e) {
            e.printStackTrace();
            contentAsString = "Connection Lost";
        } catch (ProtocolException e) {
            e.printStackTrace();
            contentAsString = "Connection Lost";

        } catch (IOException e) {
            e.printStackTrace();
            contentAsString = "Connection Lost";

            // Toast.makeText(mContext,"No Internet Connection Found",Toast.LENGTH_LONG).show();
        } finally {
            if (is != null) {
                is.close();
            }
        }

        return contentAsString;

    }
}
