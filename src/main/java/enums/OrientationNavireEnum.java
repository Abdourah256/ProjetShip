package main.java.enums;

public enum OrientationNavireEnum {
    HORIZONTAL("Horizontal"),
    VERTICAL("Vertical");
    private final String orientationNavire;

    OrientationNavireEnum(String orientationNavire) {
        this.orientationNavire = orientationNavire;
    }

    public String getOrientationNavire() {
        return orientationNavire;
    }
    public boolean estHorizontal(){
        return equals(HORIZONTAL);
    }
    public boolean estVertical(){
        return equals(VERTICAL);
    }
}
