package Entity;

import java.util.Arrays;

public class ItemResult {
    private Result result;
    private String name;
    private byte[][] pictures;
    public ItemResult() {
    }

    public ItemResult(Result result, String name) {
        this.result = result;
        this.name = name;
    }

    public ItemResult(Result result, String name, byte[][] pictures) {
        this.result = result;
        this.name = name;
        this.pictures = pictures;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[][] getPictures() {
        return pictures;
    }

    public void setPictures(byte[][] pictures) {
        this.pictures = pictures;
    }

    @Override
    public String toString() {
        return "ItemResult{" +
                "result=" + result +
                ", name='" + name + '\'' +
                ", pictures=" + Arrays.toString(pictures) +
                '}';
    }
}