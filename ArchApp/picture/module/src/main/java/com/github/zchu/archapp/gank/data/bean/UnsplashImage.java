package com.github.zchu.archapp.gank.data.bean;

public class UnsplashImage {

    /**
     * categories : []
     * color : #000103
     * created_at : 2018-02-10T16:01:10-05:00
     * current_user_collections : []
     * height : 2937
     * id : jucl7bCZ7Mk
     * liked_by_user : false
     * likes : 38
     * links : {"download":"https://unsplash.com/photos/jucl7bCZ7Mk/download","download_location":"https://api.unsplash.com/photos/jucl7bCZ7Mk/download","html":"https://unsplash.com/photos/jucl7bCZ7Mk","self":"https://api.unsplash.com/photos/jucl7bCZ7Mk"}
     * sponsored : false
     * updated_at : 2018-02-11T05:50:14-05:00
     * urls : {"full":"https://images.unsplash.com/photo-1518296211993-21e8337a08c5?ixlib=rb-0.3.5&q=85&fm=jpg&crop=entropy&cs=srgb&ixid=eyJhcHBfaWQiOjIxMDQ5fQ&s=416fc152269af856dc3cfd0dc96c4183","raw":"https://images.unsplash.com/photo-1518296211993-21e8337a08c5?ixlib=rb-0.3.5&ixid=eyJhcHBfaWQiOjIxMDQ5fQ&s=51533187385b04cb0d1958500cc9cc0c","regular":"https://images.unsplash.com/photo-1518296211993-21e8337a08c5?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=1080&fit=max&ixid=eyJhcHBfaWQiOjIxMDQ5fQ&s=19ce3822f7a365e61ef67a576436cb16","small":"https://images.unsplash.com/photo-1518296211993-21e8337a08c5?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=400&fit=max&ixid=eyJhcHBfaWQiOjIxMDQ5fQ&s=c3c664842b0ef1bea4165f8ca9b0417a","thumb":"https://images.unsplash.com/photo-1518296211993-21e8337a08c5?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=200&fit=max&ixid=eyJhcHBfaWQiOjIxMDQ5fQ&s=fa1ed02837c79dfd698d9cda741a96db"}
     * user : {"first_name":"Jessica","id":"_3RIiCpIyZE","last_name":"Arends","links":{"followers":"https://api.unsplash.com/users/pixelwanted/followers","following":"https://api.unsplash.com/users/pixelwanted/following","html":"https://unsplash.com/@pixelwanted","likes":"https://api.unsplash.com/users/pixelwanted/likes","photos":"https://api.unsplash.com/users/pixelwanted/photos","portfolio":"https://api.unsplash.com/users/pixelwanted/portfolio","self":"https://api.unsplash.com/users/pixelwanted"},"name":"Jessica Arends","portfolio_url":"https://creativemarket.com/pixelwanted","profile_image":{"large":"https://images.unsplash.com/profile-1508502887362-b39a30b72d0c?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=128&w=128&s=30212351e6fee43896a206c2857d2326","medium":"https://images.unsplash.com/profile-1508502887362-b39a30b72d0c?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=64&w=64&s=b3844d8422e9eae318ddcb64759b6b73","small":"https://images.unsplash.com/profile-1508502887362-b39a30b72d0c?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=32&w=32&s=98a8c00516eac4785adc49d16dab02f2"},"total_collections":1,"total_likes":157,"total_photos":6,"updated_at":"2018-02-12T03:12:20-05:00","username":"pixelwanted"}
     * width : 4405
     */

    public String color;
    public String created_at;
    public int height;
    public String id;
    public boolean liked_by_user;
    public int likes;
    public LinksBean links;
    public boolean sponsored;
    public String updated_at;
    public UrlsBean urls;
    public UserBean user;
    public int width;

    public static class LinksBean {
        /**
         * download : https://unsplash.com/photos/jucl7bCZ7Mk/download
         * download_location : https://api.unsplash.com/photos/jucl7bCZ7Mk/download
         * html : https://unsplash.com/photos/jucl7bCZ7Mk
         * self : https://api.unsplash.com/photos/jucl7bCZ7Mk
         */

        public String download;
        public String download_location;
        public String html;
        public String self;
    }

    public static class UrlsBean {
        /**
         * full : https://images.unsplash.com/photo-1518296211993-21e8337a08c5?ixlib=rb-0.3.5&q=85&fm=jpg&crop=entropy&cs=srgb&ixid=eyJhcHBfaWQiOjIxMDQ5fQ&s=416fc152269af856dc3cfd0dc96c4183
         * raw : https://images.unsplash.com/photo-1518296211993-21e8337a08c5?ixlib=rb-0.3.5&ixid=eyJhcHBfaWQiOjIxMDQ5fQ&s=51533187385b04cb0d1958500cc9cc0c
         * regular : https://images.unsplash.com/photo-1518296211993-21e8337a08c5?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=1080&fit=max&ixid=eyJhcHBfaWQiOjIxMDQ5fQ&s=19ce3822f7a365e61ef67a576436cb16
         * small : https://images.unsplash.com/photo-1518296211993-21e8337a08c5?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=400&fit=max&ixid=eyJhcHBfaWQiOjIxMDQ5fQ&s=c3c664842b0ef1bea4165f8ca9b0417a
         * thumb : https://images.unsplash.com/photo-1518296211993-21e8337a08c5?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=200&fit=max&ixid=eyJhcHBfaWQiOjIxMDQ5fQ&s=fa1ed02837c79dfd698d9cda741a96db
         */

        public String full;
        public String raw;
        public String regular;
        public String small;
        public String thumb;
    }

    public static class UserBean {
        /**
         * first_name : Jessica
         * id : _3RIiCpIyZE
         * last_name : Arends
         * links : {"followers":"https://api.unsplash.com/users/pixelwanted/followers","following":"https://api.unsplash.com/users/pixelwanted/following","html":"https://unsplash.com/@pixelwanted","likes":"https://api.unsplash.com/users/pixelwanted/likes","photos":"https://api.unsplash.com/users/pixelwanted/photos","portfolio":"https://api.unsplash.com/users/pixelwanted/portfolio","self":"https://api.unsplash.com/users/pixelwanted"}
         * name : Jessica Arends
         * portfolio_url : https://creativemarket.com/pixelwanted
         * profile_image : {"large":"https://images.unsplash.com/profile-1508502887362-b39a30b72d0c?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=128&w=128&s=30212351e6fee43896a206c2857d2326","medium":"https://images.unsplash.com/profile-1508502887362-b39a30b72d0c?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=64&w=64&s=b3844d8422e9eae318ddcb64759b6b73","small":"https://images.unsplash.com/profile-1508502887362-b39a30b72d0c?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=32&w=32&s=98a8c00516eac4785adc49d16dab02f2"}
         * total_collections : 1
         * total_likes : 157
         * total_photos : 6
         * updated_at : 2018-02-12T03:12:20-05:00
         * username : pixelwanted
         */

        public String first_name;
        public String id;
        public String last_name;
        public LinksBeanX links;
        public String name;
        public String portfolio_url;
        public ProfileImageBean profile_image;
        public int total_collections;
        public int total_likes;
        public int total_photos;
        public String updated_at;
        public String username;


    }

    public static class LinksBeanX {
        /**
         * followers : https://api.unsplash.com/users/pixelwanted/followers
         * following : https://api.unsplash.com/users/pixelwanted/following
         * html : https://unsplash.com/@pixelwanted
         * likes : https://api.unsplash.com/users/pixelwanted/likes
         * photos : https://api.unsplash.com/users/pixelwanted/photos
         * portfolio : https://api.unsplash.com/users/pixelwanted/portfolio
         * self : https://api.unsplash.com/users/pixelwanted
         */

        public String followers;
        public String following;
        public String html;
        public String likes;
        public String photos;
        public String portfolio;
        public String self;
    }

    public static class ProfileImageBean {
        /**
         * large : https://images.unsplash.com/profile-1508502887362-b39a30b72d0c?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=128&w=128&s=30212351e6fee43896a206c2857d2326
         * medium : https://images.unsplash.com/profile-1508502887362-b39a30b72d0c?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=64&w=64&s=b3844d8422e9eae318ddcb64759b6b73
         * small : https://images.unsplash.com/profile-1508502887362-b39a30b72d0c?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=32&w=32&s=98a8c00516eac4785adc49d16dab02f2
         */

        public String large;
        public String medium;
        public String small;
    }
}
