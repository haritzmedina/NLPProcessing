package eus.ehu.sia.bw.processing.nlp;

/**
 * Created by Haritz on 27/11/2015.
 */
public class Entity {
    private String name;
    private Integer value;

    @Override
    public String toString() {
        return "Entity{" +
                "name='" + name + '\'' +
                ", value=" + value +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Entity entity = (Entity) o;

        return name.equals(entity.name);

    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Entity(String name, Integer value) {

        this.name = name;
        this.value = value;
    }
}
