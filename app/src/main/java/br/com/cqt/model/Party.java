package br.com.cqt.model;

public class Party {
    private int id;
    private String name;
    private String img;
    private String Local;
    private String date;
    private String desc;
    private String msg;

    public Party() {
    }

    public Party(int id, String name, String img, String local, String date, String desc, String msg) {
        this.id = id;
        this.name = name;
        this.img = img;
        Local = local;
        this.date = date;
        this.desc = desc;
        this.msg = msg;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getLocal() {
        return Local;
    }

    public void setLocal(String local) {
        Local = local;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
