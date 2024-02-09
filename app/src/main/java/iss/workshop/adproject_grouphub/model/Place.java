package iss.workshop.adproject_grouphub.model;

public class Place {
    private String name;
    private String description;
    private int imageResourceId; // 用于列表中显示的图像
    private double latitude;     // 地理位置纬度
    private double longitude;    // 地理位置经度

    // 构造函数
    public Place(String name, String description, int imageResourceId, double latitude, double longitude) {
        this.name = name;
        this.description = description;
        this.imageResourceId = imageResourceId;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Getters and Setters
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

    public int getImageResourceId() {
        return imageResourceId;
    }

    public void setImageResourceId(int imageResourceId) {
        this.imageResourceId = imageResourceId;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    // 可以根据需要添加其他方法，比如计算与另一个地点的距离等
}
