package info.jaredq.ppiv.models;

/**
 * Created by 1218 on 24/02/2015.
 */
public class Forum {

    public static final int CATEGORY_ID_QUESTIONS = 1;

    private int fid;
    private String name;
    private String description;

    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return name;
    }
}
