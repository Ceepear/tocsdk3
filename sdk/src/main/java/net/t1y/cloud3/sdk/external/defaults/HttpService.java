package net.t1y.cloud3.sdk.external.defaults;
import net.t1y.cloud3.sdk.external.T1Service;


public class HttpService extends T1Service {
    private Request request;
    @Override
    public void close() {
        super.close();
        this.request = null;
    }

    public final void setRequest(Request request) {
        this.request = request;
    }

    public HttpService(String pKey, String sKey) {
        super(pKey, sKey);
    }

    public void post(final String url,final String parameter,final OnRequestCallback callback){
        load(() -> callback.callback(request.post(url,parameter)));
    }

    public String postSync(final String url,final String p){
        return request.post(url,p);
    }

    public interface Request{
        String post(String url,String parameter);
        @Deprecated
        String get(String url);
    }

    public interface OnRequestCallback{
        void callback(String string);
    }



}
