package cz.muni.fi.pa165.w2018.dndtroops.api.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

public class SearchMissionDTO {
	@NotNull
	@Size(min=1)
	private String mission;

	public String getMission() {
		return mission;
	}

	public void setMission(String mission) {
		this.mission = mission;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof SearchMissionDTO)) return false;
		SearchMissionDTO that = (SearchMissionDTO) o;
		return Objects.equals(getMission(), that.getMission());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getMission());
	}
}
