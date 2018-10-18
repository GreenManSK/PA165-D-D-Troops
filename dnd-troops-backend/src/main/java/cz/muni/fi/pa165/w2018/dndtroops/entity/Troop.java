package cz.muni.fi.pa165.w2018.dndtroops.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * Troop entity
 *
 * @author Dusan Hetlerovic
 */
@Entity
public class Troop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String mission;

    @NotNull
    @Min(0)
    private Long gold;

    public Troop() {
    }

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

    public String getMission() {
        return mission;
    }

    public void setMission(String mission) {
        this.mission = mission;
    }

    public Long getGold() {
        return gold;
    }

    public void setGold(Long gold) {
        this.gold = gold;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Troop)) return false;
        Troop troop = (Troop) o;
        return Objects.equals(getName(), troop.getName()) &&
                Objects.equals(getMission(), troop.getMission());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getMission());
    }
}
