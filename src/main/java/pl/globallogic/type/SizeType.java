package pl.globallogic.type;

public enum SizeType {
    S(1),
    M(2),
    L(3);
    private final int value;
    SizeType(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
