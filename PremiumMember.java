package model;

/** Premium member: more books, a longer loan period and a lower fine per day. */
public class PremiumMember extends Member {

    public PremiumMember(String memberId, String name, String email, String phone) {
        super(memberId, name, email, phone);
    }

    @Override
    public int getMaxBooks() {
        return 6;
    }

    @Override
    public int getLoanDays() {
        return 30;
    }

    @Override
    public double getFinePerDay() {
        return 2.0;
    }

    @Override
    public String getMembershipType() {
        return "Premium";
    }
}
