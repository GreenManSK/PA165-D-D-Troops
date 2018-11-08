package cz.muni.fi.pa165.w2018.dndtroops.api.dto;

import com.google.common.base.MoreObjects;
import cz.muni.fi.pa165.w2018.dndtroops.api.enums.Race;

import java.util.List;
import java.util.Objects;

/**
 * Hero Data Transfer Object
 *
 * @author Lukáš Kurčík
 */
public class HeroDTO {
    private Long id;
    private String name;
    private Long experience;
    private Race race;
    private GroupDTO group;
    private List<RoleDTO> roles;

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

    public Long getExperience() {
        return experience;
    }

    public void setExperience(Long experience) {
        this.experience = experience;
    }

    public Race getRace() {
        return race;
    }

    public void setRace(Race race) {
        this.race = race;
    }

    public GroupDTO getGroup() {
        return group;
    }

    public void setGroup(GroupDTO group) {
        this.group = group;
    }

    public List<RoleDTO> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleDTO> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HeroDTO)) return false;
        HeroDTO heroDTO = (HeroDTO) o;
        return Objects.equals(getName(), heroDTO.getName()) &&
                getRace() == heroDTO.getRace();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getRace());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("name", name)
                .add("experience", experience)
                .add("race", race)
                .add("group", group.getName())
                .add("roles", roles)
                .toString();
    }
}
