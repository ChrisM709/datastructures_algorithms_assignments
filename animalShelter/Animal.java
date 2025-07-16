public class Animal {
    public enum Type { DOG, CAT } 

    private final Type type;
    private final int order;       

    public Animal(Type type, int order) {
        this.type = type;
        this.order = order;
    }

    public Type getType() { return type; }
    public int getOrder() { return order; }
}
