package com.qn_org.backend.services;


public interface QnuService<T> {
    public boolean handleSaveRepository(T entity);
}
