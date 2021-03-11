package ru.sfedu.market.utils;

public class Result<T> {
    private final Status status;
    private final T bean;
    private final String log;

    public Result(Status status, T bean, String log) {
        this.status = status;
        this.bean = bean;
        this.log = log;
    }

    public Status getStatus() {
        return status;
    }

    public T getBean() {
        return bean;
    }

    public String getLog() {
        return log;
    }

    @Override
    public String toString() {
        return "Result{" +
                "status=" + status +
                ", bean=" + bean +
                ", log='" + log + '\'' +
                '}';
    }
}
