package net.t1y.cloud3.sdk.external;
import android.util.Pair;
import net.t1y.cloud3.sdk.external.handler.T1Handler;
import java.util.concurrent.Future;
public abstract class T1Service {
    private Pair<String,String> pair;
    private T1Handler handler;
    public T1Service(String pKey,String sKey){
        this.pair = new Pair<>(pKey,sKey);
    }
    public final String[] getKey(){
        return new String[]{pair.first,pair.second};
    }
    public final void initThisService(T1Handler handler){
        this.handler = handler;
    }
    public final void post(Runnable runnable){
        this.handler.post(runnable);
    }
    public final Future<?> exec(Runnable runnable){
       return this.handler.exec(runnable);
    }
    public void close(){
        this.handler = null;
    }
}
