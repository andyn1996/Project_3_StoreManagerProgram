public class ProductModel {

        public int mProductID;
        public String mName, mSupplier, mExpirationDate, mManufacturedDate;
        public double mQuantity, mPrice, mTaxRate;

        public String toString() {

                StringBuilder sb = new StringBuilder(("("));
                sb.append(mProductID).append(",");
                sb.append("\"").append(mName).append("\"").append(",");
                sb.append("\"").append(mExpirationDate).append("\"").append(",");
                sb.append(mPrice).append(",");
                sb.append(mTaxRate).append(",");
                sb.append(mQuantity).append(",");
                sb.append("\"").append(mSupplier).append("\"").append(",");
                sb.append("\"").append(mManufacturedDate).append("\"").append(")");

                return sb.toString();
        }

}
