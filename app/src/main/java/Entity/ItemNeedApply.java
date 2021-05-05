package Entity;

public class ItemNeedApply {
    String name;
    NeedApply needApply;
    String icon;

    public ItemNeedApply(NeedApply needApply, String icon) {
        this.needApply = needApply;
        this.icon = icon;
    }

    public ItemNeedApply(String name, NeedApply needApply, String icon) {
        this.name = name;
        this.needApply = needApply;
        this.icon = icon;
    }

    public ItemNeedApply() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public NeedApply getNeedApply() {
        return needApply;
    }

    public void setNeedApply(NeedApply needApply) {
        this.needApply = needApply;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
