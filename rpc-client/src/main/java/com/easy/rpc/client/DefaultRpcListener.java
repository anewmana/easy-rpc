package com.easy.rpc.client;

/**
 * @author ljl
 * @since 2019/8/25
 */
public class DefaultRpcListener implements RpcListener {
    private volatile Object value;

    @Override
    public Object get() {
        synchronized (this){
            try {
                this.wait();
            }catch (InterruptedException ie){
                return null;
            }
            return value;
        }
    }

    @Override
    public void notifyWithValue(Object o) {
        synchronized (this){
            this.value = o;
            this.notifyAll();
        }
    }
}
