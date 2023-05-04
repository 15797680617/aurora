package top.javap.aurora.example.result;

/**
 * @author: pch
 * @description:
 * @date: 2023/5/4
 **/
public class MusicResult {
    private int code;
    private Music data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Music getData() {
        return data;
    }

    public void setData(Music data) {
        this.data = data;
    }

    public static class Music {
        private String name;
        private String url;
        private String picurl;
        private String artistsname;

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
    }
}