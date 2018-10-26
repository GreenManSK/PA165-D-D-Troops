package cz.muni.fi.pa165.w2018.dndtroops.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


/**
 * Group Entity
 *
 * @author Marek Valko <valko.marek@gmail.com>
 */
@Entity(name = "groups")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @OneToMany(mappedBy = "group")
    private Set<Hero> heroes = new HashSet<>();

    public Group() {
    }

    public void addHero(Hero hero) {
        heroes.add(hero);
        hero.setGroup(this);
    }

    public void removeHero(Hero hero) {
        heroes.remove(hero);
        hero.setGroup(null);
    }

    public Set<Hero> getHeroes() {
        return Collections.unmodifiableSet(heroes);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Group)) return false;
        Group group = (Group) o;
        return Objects.equals(getName(), group.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}
