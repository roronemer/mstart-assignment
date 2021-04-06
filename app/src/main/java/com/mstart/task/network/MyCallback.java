package com.mstart.task.network;



public interface MyCallback<T> {

    void onResult( HttpResult<T> result);

}
