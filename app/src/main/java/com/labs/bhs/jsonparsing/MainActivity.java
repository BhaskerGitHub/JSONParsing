package com.labs.bhs.jsonparsing;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity
{
    String []urls = {"http://searchkero.com/a/data.json", "http://searchkero.com/a/math.json", "http://searchkero.com/a/file.json"};
    //String url2 = "http://searchkero.com/a/math.json";
    //String url3 = "http://searchkero.com/a/file.json";
    String mResultData = "";
    boolean []mIsGetData = {false, false, false};

    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sendServerRequest1(urls[0]);
        sendServerRequest2(urls[1]);
        sendServerRequest3(urls[2]);

/*
        String url = "http://searchkero.com/ABC/fetchadmin.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        parseJSONData(response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {

                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
*/
    }

    void sendServerRequest1 (String url)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        parseServerResponse1(response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        setServerError1(error.getMessage());
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    void sendServerRequest2 (String url)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        parseServerResponse2(response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        setServerError2(error.getMessage());
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    void sendServerRequest3 (String url)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        parseServerResponse3(response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        setServerError3(error.getMessage());
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    /*
        public void parseJSONData (String data)
        {
            try
            {
                String showData = "";
                JSONObject jsonObject = new JSONObject(data);
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                JSONObject jobj;
                for (int i = 0; i < jsonArray.length(); ++i)
                {
                    jobj = jsonArray.getJSONObject(i);
                    showData += jobj.getString("name") + ", " + jobj.getString("email") + ", " + jobj.getString("blog") + "\n";
                }
                TextView textView = findViewById(R.id.tv_text);
                textView.setText(showData);
            }
            catch (JSONException e)
            {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    */
    void parseServerResponse1(String resultData)
    {
        try
        {
            JSONObject jsonObjectRoot = new JSONObject(resultData).getJSONObject("web-app");
            JSONArray jsonArrayRoot = jsonObjectRoot.getJSONArray("servlet");
            JSONObject jsonObject01 = jsonArrayRoot.getJSONObject(4);
            JSONObject jsonObject02 = jsonObject01.getJSONObject("init-param");
            mResultData += "\nURL1 = log: " + jsonObject02.getString("log");
        }
        catch (JSONException excp)
        {
            mResultData += "\nURL1: JSON Exception - " + excp.getMessage();
        }
        mIsGetData[0] = true;
        showResult();
    }

    void setServerError1(String serverError)
    {
        mResultData += "\nURL1: Server Error - " + serverError;
        mIsGetData[0] = true;
        showResult();
    }

    void parseServerResponse2(String resultData)
    {
        String result = "";
        try
        {
            JSONObject jsonObjectRoot = new JSONObject(resultData).getJSONObject("quiz");
            JSONObject jsonObject01 = jsonObjectRoot.getJSONObject("maths").getJSONObject("q2");
            JSONArray jsonArray = jsonObject01.getJSONArray("options");
            JSONObject jobj;
            result += "\nURL2 = options: ";
            for (int i = 0; i < jsonArray.length(); ++i)
            {
                //jobj = jsonArray.getString(i);
                result += i + ":" + jsonArray.getString(i) + ", ";
            }
            mResultData += result;
        }
        catch (JSONException excp)
        {
            mResultData += "\nURL2: JSON Exception - " + excp.getMessage();
        }
        mIsGetData[1] = true;
        showResult();
    }

    void setServerError2(String serverError)
    {
        mResultData += "\nURL2: Server Error - " + serverError;
        mIsGetData[1] = true;
        showResult();
    }

    void parseServerResponse3(String resultData)
    {
        String result = "\nURL3 = ";
        try
        {
            JSONObject jsonObjectRoot = new JSONObject(resultData).getJSONObject("glossary");
            JSONObject jsonObject01 = jsonObjectRoot.getJSONObject("GlossDiv")
                    .getJSONObject("GlossList")
                    .getJSONObject("GlossEntry");
            JSONObject jsonObject02 = jsonObject01.getJSONObject("GlossDef");
            JSONArray jsonArray = jsonObject02.getJSONArray("GlossSeeAlso");
            result += " GlossSeeAlso[0]: " + jsonArray.getString(0) + ", ";
            result += " GlossSee: " + jsonObject01.getString("GlossSee");
            mResultData += result;
        }
        catch (JSONException excp)
        {
            mResultData += "\nURL3: JSON Exception - " + excp.getMessage();
        }
        mIsGetData[2] = true;
        showResult();
    }

    void setServerError3(String serverError)
    {
        mResultData += "\nURL3: Server Error - " + serverError;
        mIsGetData[2] = true;
        showResult();
    }

    void showResult ()
    {
        if (mIsGetData[0] && mIsGetData[1] && mIsGetData[2])
        {
            TextView textView = findViewById(R.id.tv_text);
            textView.setText(mResultData);
        }
    }
}
