package reactive;

enum Currency {
    USD, EUR, RUB;

    @Override
    public String toString() {
        switch (this) {
            case USD -> {
                return "$";
            }
            case EUR -> {
                return "€";
            }
            case RUB -> {
                return "₽";
            }
            default -> {
                return "";
            }
        }
    }
}
