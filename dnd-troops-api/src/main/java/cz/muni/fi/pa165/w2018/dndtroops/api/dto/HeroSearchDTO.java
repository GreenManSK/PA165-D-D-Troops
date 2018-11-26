package cz.muni.fi.pa165.w2018.dndtroops.api.dto;

import com.google.common.base.MoreObjects;
import cz.muni.fi.pa165.w2018.dndtroops.api.enums.Race;

import java.util.Objects;

/**
 * Class HeroSearchDTO
 *
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */
public class HeroSearchDTO {
    private Long roleId;
    private Race race;
    private Long fromExperience;
    private Long toExperience;

    public HeroSearchDTO() {
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Race getRace() {
        return race;
    }

    public void setRace(Race race) {
        this.race = race;
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
        return Objects.equals(getRoleId(), that.getRoleId()) &&
                getRace() == that.getRace() &&
                Objects.equals(getFromExperience(), that.getFromExperience()) &&
                Objects.equals(getToExperience(), that.getToExperience());
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleId, race, fromExperience, toExperience);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("roleId", roleId)
                .add("race", race)
                .add("fromExperience", fromExperience)
                .add("toExperience", toExperience)
                .toString();
    }
}
