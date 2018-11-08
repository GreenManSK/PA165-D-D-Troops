package cz.muni.fi.pa165.w2018.dndtroops.api.dto;

import java.util.List;
import java.util.Objects;

/**
 * Data Transfer Object for Group
 *
 * @author Dusan Hetlerovic
 */
public class GroupDTO {

    private Long id;
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
        if (!(o instanceof GroupDTO)) return false;
        GroupDTO groupDTO = (GroupDTO) o;
        return Objects.equals(getName(), groupDTO.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }

    @Override
    public String toString() {
        return "GroupDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
