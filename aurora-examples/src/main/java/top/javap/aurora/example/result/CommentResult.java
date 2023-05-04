package top.javap.aurora.example.result;

/**
 * @author: pch
 * @description:
 * @date: 2023/5/4
 **/
public class CommentResult {

    private int code;
    private Comment data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Comment getData() {
        return data;
    }

    public void setData(Comment data) {
        this.data = data;
    }

    public static class Comment {
        private String name;
        private String url;
        private String picurl;
        private String artistsname;
        private String avatarurl;
        private String nickname;
        private String content;


        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getPicurl() {
            return picurl;
        }

        public void setPicurl(String picurl) {
            this.picurl = picurl;
        }

        public String getArtistsname() {
            return artistsname;
        }

        public void setArtistsname(String artistsname) {
            this.artistsname = artistsname;
        }

        public String getAvatarurl() {
            return avatarurl;
        }

        public void setAvatarurl(String avatarurl) {
            this.avatarurl = avatarurl;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}