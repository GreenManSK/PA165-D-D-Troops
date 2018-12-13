package cz.muni.fi.pa165.w2018.dndtroops.api.dto;

import java.util.List;
import java.util.Objects;

/**
 * DTO for finding groups without certain roles
 *
 * @author Dusan Hetlerovic
 */
public class GroupRoleSearchDTO {

    private List<Long> roleIds;

    public List<Long> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<Long> roleIds) {
        this.roleIds = roleIds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GroupRoleSearchDTO)) return false;
        GroupRoleSearchDTO that = (GroupRoleSearchDTO) o;
        return Objects.equals(getRoleIds(), that.getRoleIds());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRoleIds());
    }
}
