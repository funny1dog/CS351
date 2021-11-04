public class HondaCar extends Car {
    private final String color;

    public static class HondaBuilder extends Car.Builder<HondaBuilder> {
        private final String color;

        public HondaBuilder(int doors, int seats, String color) {
            super(doors, seats);
            this.color = color;
        }

        @Override
        public HondaCar build() {
            return new HondaCar(this);
        }

        @Override
        protected HondaBuilder self() {
            return this;
        }
    }

    private HondaCar(HondaBuilder builder) {
        super(builder);
        color = builder.color;
    }

    public String getColor() {
        return color;
    }
}
