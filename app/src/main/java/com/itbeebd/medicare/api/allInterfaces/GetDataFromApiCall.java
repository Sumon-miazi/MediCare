package com.itbeebd.medicare.api.allInterfaces;

import java.util.ArrayList;

public interface GetDataFromApiCall<T> {
    void data(ArrayList<T> dataListFromApiCall, String message);
}
