package net.t1y.cloud3.sdk.external.defaults;
import net.t1y.cloud3.sdk.external.T1Service;
import java.util.Objects;

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
        exec(()->{
            callback.callback(Objects.requireNonNull(request).post(url, parameter));
        });
    }

    @Deprecated
    public void get(final String url,final OnRequestCallback callback){
        exec(()->{
            callback.callback(Objects.requireNonNull(request).get(url));
        });
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
