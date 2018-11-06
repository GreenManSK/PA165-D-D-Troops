package cz.muni.fi.pa165.w2018.dndtroops.api.dto;

import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * Data Transfer Object for changes with Role entity
 *
 * @author Marek Valko
 */
public class RoleChangeDTO {
    private Long id;
    @NotNull
    private String name;
    private String description;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "RoleChangeDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RoleChangeDTO)) return false;
        RoleChangeDTO that = (RoleChangeDTO) o;
        return Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}
