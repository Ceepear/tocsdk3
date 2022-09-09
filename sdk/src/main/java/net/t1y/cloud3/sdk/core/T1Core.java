package net.t1y.cloud3.sdk.core;
import android.content.Context;
import android.util.ArrayMap;
import net.t1y.cloud3.sdk.external.T1Service;
import net.t1y.cloud3.sdk.external.handler.T1Handler;
import java.util.Map;
import java.util.Objects;


public final class T1Core {
    private Context context;
    private T1Handler handler = new T1HandlerImp();
    private ArrayMap<Class<? extends T1Service>,T1Service> t1Services = new ArrayMap<>();

    public T1Core(Context context){
        this.context = context.getApplicationContext();
    }

    public <T extends T1Service> T getService(Class<? extends T1Service> cls){
        return (T) t1Services.get(cls);
    }

    public void initService(T1Service service) {
        T1Service service1;
        service1 = Objects.requireNonNull(service);
        if(!this.t1Services.containsValue(service1)){
            this.t1Services.put(service1.getClass(),service1);
            service1.initThisService(handler);
        }
    }

    public void close() {
        ((T1HandlerImp)handler).close();
        for(Map.Entry<Class<? extends T1Service>, T1Service> t1ServiceEntry:t1Services.entrySet()){
            t1ServiceEntry.getValue().close();
        }
        t1Services.clear();
    }
}
