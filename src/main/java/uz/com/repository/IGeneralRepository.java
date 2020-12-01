package uz.com.repository;

import uz.com.dto.auth.AttachPermissionRoleDto;
import uz.com.hibernate.base._Entity;

public interface IGeneralRepository extends IGenericRepository<_Entity> {

    Boolean attachRole(AttachPermissionRoleDto dto);

}
