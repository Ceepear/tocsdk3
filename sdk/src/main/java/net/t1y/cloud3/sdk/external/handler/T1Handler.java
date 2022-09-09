package net.t1y.cloud3.sdk.external.handler;

public interface T1Handler {
    void post(Runnable runnable);
    void load(Runnable runnable);
}
