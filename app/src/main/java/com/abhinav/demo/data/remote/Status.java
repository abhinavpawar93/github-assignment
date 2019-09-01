package com.abhinav.demo.data.remote;

/**
 * Status of a resource that is provided to the UI.
 * <p>
 * These are usually created by the Repository classes where they return
 * {@code LiveData<Resource<T>>} to pass back the latest data to the UI with its fetch status.
 * Author: Abhinav Pawar
 */
public enum Status {
    SUCCESS,
    ERROR,
    LOADING
}
