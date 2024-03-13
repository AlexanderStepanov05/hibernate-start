package org.example.mapper;

import lombok.RequiredArgsConstructor;
import org.example.dao.CompanyRepository;
import org.example.dto.UserCreateDto;
import org.example.entity.User;

@RequiredArgsConstructor
public class UserCreateMapper implements Mapper<UserCreateDto, User> {
    private final CompanyRepository companyRepository;

    @Override
    public User mapFrom(UserCreateDto object) {
        return User.builder()
                .personalInfo(object.personalInfo())
                .role(object.role())
                .info(object.info())
                .company(companyRepository.findById(object.companyId()).orElseThrow(IllegalArgumentException::new))
                .build();
    }
}
