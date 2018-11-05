package cz.muni.fi.pa165.w2018.dndtroops.api.dto;

import java.util.Objects;

/**
 * Troop Data Transfer Object
 *
 * @author Daniel Fecko 445539
 */
public class TroopDTO {

	private Long id;
	private String name;
	private String mission;
	private Long gold;

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
		if (!(o instanceof TroopDTO)) return false;
		TroopDTO troop = (TroopDTO) o;
		return Objects.equals(getName(), troop.getName()) &&
				Objects.equals(getMission(), troop.getMission());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getName(), getMission());
	}

	@Override
	public String toString() {
		return "TroopDTO{" +
				"id=" + id +
				", name='" + name + '\'' +
				", mission='" + mission + '\'' +
				", gold=" + gold +
				'}';
	}
}
