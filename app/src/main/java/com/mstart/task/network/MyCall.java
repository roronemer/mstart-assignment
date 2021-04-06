package com.mstart.task.network;



public interface MyCall<T> {

    void cancel();

    void enqueue(MyCallback<T> callback);

    MyCall<T> clone();
}
