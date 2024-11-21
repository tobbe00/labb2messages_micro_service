package com.fullstack.labb2messages.mappers;

public interface Mapper<A,B> {
    B mapToDTO(A a);

    A mapToEntity(B b);
}

