package bridge;

import java.util.Objects;

public abstract class Shape {

    private final String id;
    protected Renderer renderer;

    protected Shape(String id, Renderer renderer) {
        this.id = Objects.requireNonNull(id);
        this.renderer = Objects.requireNonNull(renderer);
    }

    public String getId() {
        return id;
    }

    public void setImplementation(Renderer renderer) {
        this.renderer = Objects.requireNonNull(renderer);
    }

    public abstract String execute();
}