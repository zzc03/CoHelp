package Entity;

import java.util.Arrays;
import java.util.List;

public class ItemResult {
    private Result result;
    private String name;
    private List<String> pictures;
    public ItemResult() {
    }

    public ItemResult(Result result, String name) {
        this.result = result;
        this.name = name;
    }

    public ItemResult(Result result, String name, List<String> pictures) {
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

    public List<String> getPictures() {
        return pictures;
    }

    public void setPictures(List<String> pictures) {
        this.pictures = pictures;
    }

    @Override
    public String toString() {
        return "ItemResult{" +
                "result=" + result +
                ", name='" + name + '\'' +
                ", pictures=" +pictures +
                '}';
    }
}