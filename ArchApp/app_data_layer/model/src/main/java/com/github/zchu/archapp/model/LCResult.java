package com.github.zchu.archapp.model;

import java.io.Serializable;
import java.util.Date;

/**
 * author : zchu
 * date   : 2017/11/7
 * desc   :
 */

public class LCResult<T> implements Serializable {

    public int code;

    public String error;

    public T results;

    public Date updatedAt;

    public String objectId;

    public boolean isSuccessful(){
        return code==0;
    }
}
