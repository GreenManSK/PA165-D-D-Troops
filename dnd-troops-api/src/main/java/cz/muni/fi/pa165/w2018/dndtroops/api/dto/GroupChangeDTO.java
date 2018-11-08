package cz.muni.fi.pa165.w2018.dndtroops.api.dto;


import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * Data Transfer Object for changes in Group
 *
 * @author Dusan Hetlerovic
 */
public class GroupChangeDTO {

    private Long id;

    @NotNull
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GroupChangeDTO)) return false;
        GroupChangeDTO that = (GroupChangeDTO) o;
        return Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }

    @Override
    public String toString() {
        return "GroupChangeDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
