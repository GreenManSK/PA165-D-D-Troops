package cz.muni.fi.pa165.w2018.dndtroops.dto;

import com.google.common.base.MoreObjects;
import cz.muni.fi.pa165.w2018.dndtroops.enums.Race;

import java.util.Objects;

/**
 * Class HeroSearchDTO
 *
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */
public class HeroSearchDTO {
    private RoleDTO role;
    private Race race;
    private GroupDTO group;
    private Long fromExperience;
    private Long toExperience;

    public HeroSearchDTO() {
    }

    public RoleDTO getRole() {
        return role;
    }

    public void setRole(RoleDTO role) {
        this.role = role;
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

    public Long getFromExperience() {
        return fromExperience;
    }

    public void setFromExperience(Long fromExperience) {
        this.fromExperience = fromExperience;
    }

    public Long getToExperience() {
        return toExperience;
    }

    public void setToExperience(Long toExperience) {
        this.toExperience = toExperience;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HeroSearchDTO)) return false;
        HeroSearchDTO that = (HeroSearchDTO) o;
        return Objects.equals(getRole(), that.getRole()) &&
                getRace() == that.getRace() &&
                Objects.equals(getGroup(), that.getGroup()) &&
                Objects.equals(getFromExperience(), that.getFromExperience()) &&
                Objects.equals(getToExperience(), that.getToExperience());
    }

    @Override
    public int hashCode() {
        return Objects.hash(role, race, group, fromExperience, toExperience);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("role", role)
                .add("race", race)
                .add("group", group)
                .add("fromExperience", fromExperience)
                .add("toExperience", toExperience)
                .toString();
    }
}
