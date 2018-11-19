package cz.muni.fi.pa165.w2018.dndtroops.api.dto;

import com.google.common.base.MoreObjects;
import cz.muni.fi.pa165.w2018.dndtroops.api.enums.Race;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

/**
 * Data Transfer Object for changes with Hero entity
 *
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */
public class HeroChangeDTO {
    private Long id;

    @NotNull
    private String name;

    @NotNull
    @Min(0)
    private Long experience;

    @NotNull
    private Race race;

    private Long groupId;

    @NotNull
    private List<Long> roleIds;

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

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public List<Long> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<Long> roleIds) {
        this.roleIds = roleIds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HeroChangeDTO)) return false;
        HeroChangeDTO that = (HeroChangeDTO) o;
        return Objects.equals(getName(), that.getName()) &&
                getRace() == that.getRace();
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
                .add("groupId", groupId)
                .add("roleIds", roleIds)
                .toString();
    }
}
