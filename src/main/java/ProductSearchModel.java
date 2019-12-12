public class ProductSearchModel {

    public String mProductName;
    public int mMinPrice, mMaxPrice;

    public String toString() {

        StringBuilder sb = new StringBuilder(("("));
        sb.append("\"").append(mProductName).append("\"").append(",");
        sb.append(mMinPrice).append(",");
        sb.append(mMaxPrice).append(",");
        sb.append(")");

        return sb.toString();
    }

}
