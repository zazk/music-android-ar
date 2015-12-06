package pe.com.vencedor.historiasdefamilias.utils;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Cesar Beyond on 6/15/2015.
 */
public class StringRequestPost extends StringRequest {
    public static final int TYPE_OBJECT = 0;
    public static final int TYPE_ARRAY = 1;
    public static final String KEY_ARRAY = "array";
    public static final String KEY_RAW = "raw";

    private Map<String, String> params;
    private Response.Listener<String> listener;
    private boolean rawInfoSent =false,contentTypeJSon = false,urlencoded =false;
    private int type;

    public StringRequestPost(boolean urlencoded, int type, String url, Map<String, String> params, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Method.POST, url,listener, errorListener);
        this.urlencoded = urlencoded;
        this.type = type;
        this.listener = listener;
        this.params = params;
    }

    public StringRequestPost(String url, Map<String, String> params, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Method.POST, url,listener, errorListener);
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
            return "application/x-www-form-urlencoded; charset=UTF-8";
        }

        else {
            return super.getBodyContentType();
        }
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        checkParams(params);
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
    protected void deliverResponse(String response) {
        super.deliverResponse(response);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return super.getHeaders();
    }

    private Map<String, String> checkParams(Map<String, String> map){
        Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> pairs = (Map.Entry<String, String>)it.next();
            if(pairs.getValue()==null){
                map.put(pairs.getKey(), "");
            }
        }
        return map;
    }
}
