package br.com.alura.literAlura.service;

public interface IconvertData {
    <T> T getData(String json, Class<T> tClass);
}
