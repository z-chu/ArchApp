package com.github.zchu.archapp.data.bean;

/**
 * author : zchu
 * date   : 2017/12/26
 * desc   :
 */

public class Welcome {

    /**
     * duration : 3000
     * fromAuthor : 琢磨先生
     * ACL : {"*":{"write":true,"read":true}}
     * content : 爱上一个人，便爱上一座城，\n城市再大，都觉得很小，\n小到自己的爱溢满全城。\n失去一个人，便失去一座城，\n城市再小，都觉得很大，\n大到任何一个地方都找不到自己。
     * fromBook : 以幽默的方式过一生
     * likeNumber : 99
     * objectId : 5a41bbeed50eee348a81f777
     * createdAt : 2017-12-26T03:03:10.053Z
     * updatedAt : 2017-12-26T06:39:39.742Z
     */
    public String objectId;
    public long duration;//显示时间
    public String fromAuthor;//作者
    public String fromBook;//出自哪本书
    public String content;
    public int likeNumber;
    public String createdAt;
    public String updatedAt;

    public long timeMillis;


}
