package pe.com.vencedor.historiasdefamilias.utils;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Cesar Beyond on 6/15/2015.
 */
public class RequestPost extends Request<JSONObject>{
    public static final int TYPE_OBJECT = 0;
    public static final int TYPE_ARRAY = 1;
    public static final String KEY_ARRAY = "array";
    public static final String KEY_RAW = "raw";

    private Map<String, String> params;
    private Response.Listener<JSONObject> listener;
    private boolean rawInfoSent =false,contentTypeJSon = false,urlencoded =false;
    private int type;

    //Constructor para mandar request post
    public RequestPost(String url, Map<String, String> params, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(Method.POST, url, errorListener);
        this.listener = listener;
        this.params = params;
        this.type = TYPE_OBJECT;
    }

    //En este constructor especificas si quieres recibir JsonObject o JsonArray
    //form-data
    public RequestPost(int type, String url, Map<String, String> params, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(Method.POST, url, errorListener);
        this.listener = listener;
        this.params = params;
        this.type = type;
    }

    //en este constructor seteas el contentetype
    public RequestPost(int type, boolean contentTypeJSon,String url, Map<String, String> params, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(Method.POST, url, errorListener);
        this.listener = listener;
        this.contentTypeJSon= contentTypeJSon;
        this.params = params;
        this.type = type;
    }


    //Constructor para cuando mandamos un raw info
    public RequestPost(boolean rawInfoSent, boolean contentTypeJSon, int type, String url, Map<String, String> params, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(Method.POST, url, errorListener);
        this.rawInfoSent = rawInfoSent;
        this.contentTypeJSon = contentTypeJSon;
        this.type = type;
        this.listener = listener;
        this.params = params;
    }

    //Constructor para url encoded
    public RequestPost(boolean urlencoded, int type, String url, Map<String, String> params, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(Method.POST, url, errorListener);
        this.urlencoded = urlencoded;
        this.type = type;
        this.listener = listener;
        this.params = params;
    }

    public RequestPost(int method, boolean rawInfoSent,boolean contentTypeJSon,int type, String url, Map<String, String> params, Response.Listener<JSONObject> listener,  Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.rawInfoSent = rawInfoSent;
        this.contentTypeJSon = contentTypeJSon;
        this.type = type;
        this.listener = listener;
        this.params = params;
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }

    @Override
    public String getBodyContentType() {
        if (contentTypeJSon) {
            return "application/json";
        }
        else if(urlencoded){
            System.out.println("ES URLUNCODED");
            return "application/x-www-form-urlencoded; charset=UTF-8";
        }

        else {
            return super.getBodyContentType();
        }
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        if (rawInfoSent){
            String data = params.get(KEY_RAW);
            try {
                return data.toString().getBytes(getParamsEncoding());
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("Encoding not supported: " + data, e);
            }
        }
        else{
            return super.getBody();
        }

    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse networkResponse) {
        try {
            JSONObject result = null;
            String jsonString = new String(networkResponse.data, HttpHeaderParser.parseCharset(networkResponse.headers));
            if (type==TYPE_OBJECT){
                result = new JSONObject(jsonString);
            }else if (type ==TYPE_ARRAY){
                JSONArray array = new JSONArray(jsonString);
                result = new JSONObject();
                result.put(KEY_ARRAY, array);
            }

            Log.d("OBJETO",jsonString);
            Cache.Entry entry = HttpHeaderParser.parseCacheHeaders(networkResponse);
            return Response.success(result,entry);
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
    }

    @Override
    protected void deliverResponse(JSONObject response) {
        listener.onResponse(response);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String>  headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json;charset=utf-8");
        return headers;
    }


}
